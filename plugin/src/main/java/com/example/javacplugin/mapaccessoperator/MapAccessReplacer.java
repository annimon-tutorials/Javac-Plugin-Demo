package com.example.javacplugin.mapaccessoperator;

import com.sun.source.tree.Tree;
import com.sun.source.util.JavacTask;
import com.sun.tools.javac.api.BasicJavacTask;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.JCTree.JCArrayAccess;
import com.sun.tools.javac.tree.JCTree.JCAssign;
import com.sun.tools.javac.tree.JCTree.JCExpression;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.tree.TreeTranslator;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.Names;

public class MapAccessReplacer extends TreeTranslator {

    private final TreeMaker make;
    private final Names names;

    public MapAccessReplacer(JavacTask task) {
        Context context = ((BasicJavacTask)task).getContext();
        make = TreeMaker.instance(context);
        names = Names.instance(context);
    }

    @Override
    public void visitMethodDef(JCTree.JCMethodDecl jcmd) {
        super.visitMethodDef(jcmd);
    }

    @Override
    public void visitAssign(JCAssign tree) {
        if (tree.getVariable().getKind() == Tree.Kind.ARRAY_ACCESS) {
            JCArrayAccess arrayAccess = (JCArrayAccess) tree.getVariable();
            if (arrayAccess.getIndex().getKind() == Tree.Kind.STRING_LITERAL) {
                JCExpression methodSelect = make.Select(arrayAccess.getExpression(), names.fromString("put"));
                result = make.Apply(List.nil(), methodSelect, List.of(arrayAccess.getIndex(), tree.getExpression()));
                return;
            }
        }
        super.visitAssign(tree);
    }

    @Override
    public void visitIndexed(JCArrayAccess tree) {
        if (tree.getIndex().getKind() == Tree.Kind.STRING_LITERAL) {
            JCExpression methodSelect = make.Select(tree.getExpression(), names.fromString("get"));
            result = make.Apply(List.nil(), methodSelect, List.of(tree.getIndex()));
            return;
        }
        super.visitIndexed(tree);
    }
}