package com.example.javacplugin.extensionmethods;

import com.sun.source.tree.ClassTree;
import com.sun.source.tree.MethodInvocationTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.Tree;
import com.sun.source.util.JavacTask;
import com.sun.source.util.TreePathScanner;
import com.sun.tools.javac.api.BasicJavacTask;
import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.comp.Attr;
import com.sun.tools.javac.comp.AttrContext;
import com.sun.tools.javac.comp.Enter;
import com.sun.tools.javac.comp.Env;
import com.sun.tools.javac.comp.MemberEnter;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.JCTree.JCExpression;
import com.sun.tools.javac.tree.JCTree.JCFieldAccess;
import com.sun.tools.javac.tree.JCTree.JCMethodInvocation;
import com.sun.tools.javac.tree.TreeInfo;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.Context;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;

public final class ExtensionMethodVisitor extends TreePathScanner<Void, Void> {

    private  static final List<Modifier> PUBLIC_STATIC = Arrays.asList(
            Modifier.PUBLIC, Modifier.STATIC);

    private final Context context;
    private final Types typeUtils;
    private final TreeMaker make;
    private final Enter enter;
    private final MemberEnter memberEnter;
    private final Attr attr;

    private ExtensionMethods extensionMethods;
    private Env<AttrContext> methodEnv;

    public ExtensionMethodVisitor(JavacTask task) {
        typeUtils = task.getTypes();
        context = ((BasicJavacTask)task).getContext();
        make = TreeMaker.instance(context);
        enter = Enter.instance(context);
        memberEnter = MemberEnter.instance(context);
        attr = Attr.instance(context);
    }

    @Override
    public Void visitClass(ClassTree ct, Void p) {
        final TypeElement currentClass = (TypeElement) TreeInfo.symbolFor((JCTree) ct);
        // Get possible extension methods for this compilation unit
        extensionMethods = Optional.ofNullable(currentClass)
                .map(TypeElement::getEnclosedElements)
                .map(enclosedElements -> enclosedElements
                        .stream()
                        .filter(e -> e.getModifiers().containsAll(PUBLIC_STATIC))
                        .filter(e -> e.getKind() == ElementKind.METHOD)
                        .map(ExecutableElement.class::cast)
                        .filter(e -> !e.getParameters().isEmpty())
                        .map(ExtensionMethod::new)
                        .collect(Collectors.toCollection(ExtensionMethods::new))
                )
                .orElseGet(ExtensionMethods::new);

        return super.visitClass(ct, p);
    }

    @Override
    public Void visitMethod(MethodTree mt, Void p) {
        JCTree.JCMethodDecl tree = (JCTree.JCMethodDecl) mt;
        if (tree.sym == null) {
            methodEnv = null;
        } else {
            methodEnv = memberEnter.getMethodEnv(tree, enter.getClassEnv(tree.sym.enclClass()));
        }
        return super.visitMethod(mt, p);
    }

    @Override
    public Void visitMethodInvocation(MethodInvocationTree tree, Void p) {
        super.visitMethodInvocation(tree, p);
        if (!extensionMethods.isEmpty()) {
            processMethodInvocation((JCMethodInvocation) tree);
        }
        return p;
    }

    private void processMethodInvocation(JCMethodInvocation tree) {
        if (tree == null) return;
        if (tree.meth.getKind() != Tree.Kind.MEMBER_SELECT) return;

        // Get method name we try to invoke and check if there is ext method with the same name
        final String methodName = ((JCFieldAccess) tree.meth).name.toString();
        if (extensionMethods.names().noneMatch(m -> m.equals(methodName))) return;

        final JCExpression receiver = ((JCFieldAccess) tree.meth).getExpression();

        extensionMethods.stream()
                .filter(m -> methodName.equals(m.getName()))
                .filter(m -> m.getParametersCount() == tree.getArguments().length() + 1)
                .filter(m -> checkTypes(m, receiver))
                .findAny()
                .ifPresent(me -> {
                    tree.args = tree.args.prepend(receiver);

                    Symbol symbol = (Symbol) me.getElement();
                    tree.meth = make.Ident(symbol);
                    System.out.println(methodName + " processed");
                });
    }

    private boolean checkTypes(ExtensionMethod m, JCExpression receiver) {
        attr.attribExpr(receiver, methodEnv);
        TypeMirror type = receiver.type;
        TypeMirror param = typeUtils.erasure(m.getElement().getParameters().get(0).asType());
        if (type == null || param == null) return false;
        return typeUtils.isAssignable(param, type);
    }
}
