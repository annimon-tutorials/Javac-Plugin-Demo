package com.example.javacplugin.operatoroverloading;

import com.sun.source.tree.Tree;
import com.sun.source.util.JavacTask;
import com.sun.tools.javac.api.BasicJavacTask;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.JCTree.JCExpression;
import com.sun.tools.javac.tree.JCTree.JCIdent;
import com.sun.tools.javac.tree.JCTree.JCMethodInvocation;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.tree.TreeTranslator;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.Name;
import com.sun.tools.javac.util.Names;
import java.util.EnumMap;

public final class OperatorOverloadingVisitor extends TreeTranslator {

    private final EnumMap<Tree.Kind, Name> operators;
    private final TreeMaker treeMaker;


    public OperatorOverloadingVisitor(JavacTask task) {
        final Context context = ((BasicJavacTask) task).getContext();
        treeMaker = TreeMaker.instance(context);

        final Names names = Names.instance(context);
        operators = new EnumMap<>(Tree.Kind.class);
        operators.put(Tree.Kind.PLUS, names.fromString("add"));
        operators.put(Tree.Kind.MINUS, names.fromString("subtract"));
        operators.put(Tree.Kind.MULTIPLY, names.fromString("multiply"));
        operators.put(Tree.Kind.DIVIDE, names.fromString("divide"));
    }

    @Override
    public void visitBinary(JCTree.JCBinary bt) {
        super.visitBinary(bt);
        if (!operators.containsKey(bt.getKind())) return;

        JCExpression methodCall;
        final JCExpression left = bt.getLeftOperand();
        switch (left.getKind()) {
            case IDENTIFIER:
                JCIdent lIdent = (JCIdent) left;
                methodCall = treeMaker.Ident(lIdent.getName());
                break;
            case METHOD_INVOCATION:
                methodCall = left;
                break;
            default:
                return;
        }

        methodCall = treeMaker.Select(methodCall, operators.get(bt.getKind()));
        final JCMethodInvocation app = treeMaker.Apply(
                List.nil(), methodCall, List.of(bt.getRightOperand())
        );
        result = app;
    }
}
