package adalid.util.meta;

/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */


import com.sun.source.tree.AnnotationTree;
import com.sun.source.tree.ArrayAccessTree;
import com.sun.source.tree.ArrayTypeTree;
import com.sun.source.tree.AssertTree;
import com.sun.source.tree.AssignmentTree;
import com.sun.source.tree.BinaryTree;
import com.sun.source.tree.BlockTree;
import com.sun.source.tree.BreakTree;
import com.sun.source.tree.CaseTree;
import com.sun.source.tree.CatchTree;
import com.sun.source.tree.ClassTree;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.CompoundAssignmentTree;
import com.sun.source.tree.ConditionalExpressionTree;
import com.sun.source.tree.ContinueTree;
import com.sun.source.tree.DoWhileLoopTree;
import com.sun.source.tree.EmptyStatementTree;
import com.sun.source.tree.EnhancedForLoopTree;
import com.sun.source.tree.ErroneousTree;
import com.sun.source.tree.ExpressionStatementTree;
import com.sun.source.tree.ForLoopTree;
import com.sun.source.tree.IdentifierTree;
import com.sun.source.tree.IfTree;
import com.sun.source.tree.ImportTree;
import com.sun.source.tree.InstanceOfTree;
import com.sun.source.tree.LabeledStatementTree;
import com.sun.source.tree.LiteralTree;
import com.sun.source.tree.MemberSelectTree;
import com.sun.source.tree.MethodInvocationTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.ModifiersTree;
import com.sun.source.tree.NewArrayTree;
import com.sun.source.tree.NewClassTree;
import com.sun.source.tree.ParameterizedTypeTree;
import com.sun.source.tree.ParenthesizedTree;
import com.sun.source.tree.PrimitiveTypeTree;
import com.sun.source.tree.ReturnTree;
import com.sun.source.tree.SwitchTree;
import com.sun.source.tree.SynchronizedTree;
import com.sun.source.tree.ThrowTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.TryTree;
import com.sun.source.tree.TypeCastTree;
import com.sun.source.tree.TypeParameterTree;
import com.sun.source.tree.UnaryTree;
import com.sun.source.tree.VariableTree;
import com.sun.source.tree.WhileLoopTree;
import com.sun.source.tree.WildcardTree;
import com.sun.source.util.TreePath;
import com.sun.source.util.TreePathScanner;
import com.sun.source.util.Trees;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public class CodeAnalyzerTreeVisitor extends TreePathScanner<Object, Trees> {

    private static final Logger logger = Logger.getLogger(CodeAnalyzerTreeVisitor.class);

    private static final String CM = ", ";

    private static final String CL = ": ";

    private static final String SP = " ";

    private static final String EOL = "\n";

    private static final String TAB = "\t";

    private void info(String string) {
        string = StringUtils.removeEnd(string, CM);
//      string = StringUtils.remove(string, NL);
        string = StringUtils.replace(string, EOL, SP); // "<n>"
//      string = StringUtils.remove(string, TB);
        string = StringUtils.replace(string, TAB, SP); // "<t>"
        string = StringUtils.remove(string, SP + SP);
        string = StringUtils.trimToEmpty(string);
        logger.info(tabs() + string);
    }

    private int depth() {
        return depth(getCurrentPath());
    }

    private int depth(TreePath tp) {
        return tp.getParentPath() == null ? 1 : 1 + depth(tp.getParentPath());
    }

    private String tabs() {
        return StringUtils.repeat(SP, 4 * (depth() - 1));
    }

    @Override
    public Object reduce(Object r, Object r1) {
//      info("reduce" + CL + r + CM + r1);
        return super.reduce(r, r1);
    }

    @Override
    public Object scan(Iterable<? extends Tree> itrbl, Trees p) {
//      info("scan" + CL + itrbl + CM + p);
        return super.scan(itrbl, p);
    }

    @Override
    public Object visitAnnotation(AnnotationTree t, Trees p) {
        info("AnnotationTree" + CL + t.getKind() + SP + t);
        return super.visitAnnotation(t, p);
    }

    @Override
    public Object visitArrayAccess(ArrayAccessTree t, Trees p) {
        info("ArrayAccessTree" + CL + t.getKind() + SP + t);
        return super.visitArrayAccess(t, p);
    }

    @Override
    public Object visitArrayType(ArrayTypeTree t, Trees p) {
        info("ArrayTypeTree" + CL + t.getKind() + SP + t);
        return super.visitArrayType(t, p);
    }

    @Override
    public Object visitAssert(AssertTree t, Trees p) {
        info("AssertTree" + CL + t.getKind() + SP + t);
        return super.visitAssert(t, p);
    }

    @Override
    public Object visitAssignment(AssignmentTree t, Trees p) {
        info("AssignmentTree" + CL + t.getKind() + SP + t);
        return super.visitAssignment(t, p);
    }

    @Override
    public Object visitBinary(BinaryTree t, Trees p) {
        info("BinaryTree" + CL + t.getKind() + SP + t);
        return super.visitBinary(t, p);
    }

    @Override
    public Object visitBlock(BlockTree t, Trees p) {
        info("BlockTree" + CL + t.getKind() + SP + t);
        return super.visitBlock(t, p);
    }

    @Override
    public Object visitBreak(BreakTree t, Trees p) {
        info("BreakTree" + CL + t.getKind() + SP + t);
        return super.visitBreak(t, p);
    }

    @Override
    public Object visitCase(CaseTree t, Trees p) {
        info("CaseTree" + CL + t.getKind() + SP + t);
        return super.visitCase(t, p);
    }

    @Override
    public Object visitCatch(CatchTree t, Trees p) {
        info("CatchTree" + CL + t.getKind() + SP + t);
        return super.visitCatch(t, p);
    }

    @Override
    public Object visitClass(ClassTree t, Trees p) {
        info("ClassTree" + CL + t.getKind() + SP + t.getSimpleName()
            + CM + t.getModifiers()
            + CM + t.getTypeParameters()
            + CM + t.getExtendsClause()
            + CM + t.getImplementsClause());
        return super.visitClass(t, p);
    }

    @Override
    public Object visitCompilationUnit(CompilationUnitTree t, Trees p) {
        info("CompilationUnitTree" + CL + t.getKind() + SP + t.getSourceFile());
        return super.visitCompilationUnit(t, p);
    }

    @Override
    public Object visitCompoundAssignment(CompoundAssignmentTree t, Trees p) {
        info("CompoundAssignmentTree" + CL + t.getKind() + SP + t);
        return super.visitCompoundAssignment(t, p);
    }

    @Override
    public Object visitConditionalExpression(ConditionalExpressionTree t, Trees p) {
        info("ConditionalExpressionTree" + CL + t.getKind() + SP + t);
        return super.visitConditionalExpression(t, p);
    }

    @Override
    public Object visitContinue(ContinueTree t, Trees p) {
        info("ContinueTree" + CL + t.getKind() + SP + t);
        return super.visitContinue(t, p);
    }

    @Override
    public Object visitDoWhileLoop(DoWhileLoopTree t, Trees p) {
        info("DoWhileLoopTree" + CL + t.getKind() + SP + t);
        return super.visitDoWhileLoop(t, p);
    }

    @Override
    public Object visitEmptyStatement(EmptyStatementTree t, Trees p) {
        info("EmptyStatementTree" + CL + t.getKind() + SP + t);
        return super.visitEmptyStatement(t, p);
    }

    @Override
    public Object visitEnhancedForLoop(EnhancedForLoopTree t, Trees p) {
        info("EnhancedForLoopTree" + CL + t.getKind() + SP + t);
        return super.visitEnhancedForLoop(t, p);
    }

    @Override
    public Object visitErroneous(ErroneousTree t, Trees p) {
        info("ErroneousTree" + CL + t.getKind() + SP + t);
        return super.visitErroneous(t, p);
    }

    @Override
    public Object visitExpressionStatement(ExpressionStatementTree t, Trees p) {
        info("ExpressionStatementTree" + CL + t.getKind() + SP + t);
        return super.visitExpressionStatement(t, p);
    }

    @Override
    public Object visitForLoop(ForLoopTree t, Trees p) {
        info("ForLoopTree" + CL + t.getKind() + SP + t);
        return super.visitForLoop(t, p);
    }

    @Override
    public Object visitIdentifier(IdentifierTree t, Trees p) {
        info("IdentifierTree" + CL + t.getKind() + SP + t.getName());
        return super.visitIdentifier(t, p);
    }

    @Override
    public Object visitIf(IfTree t, Trees p) {
        info("IfTree" + CL + t.getKind() + SP + t);
        return super.visitIf(t, p);
    }

    @Override
    public Object visitImport(ImportTree t, Trees p) {
        info("ImportTree" + CL + t.getKind() + SP + t);
        return super.visitImport(t, p);
    }

    @Override
    public Object visitInstanceOf(InstanceOfTree t, Trees p) {
        info("InstanceOfTree" + CL + t.getKind() + SP + t);
        return super.visitInstanceOf(t, p);
    }

    @Override
    public Object visitLabeledStatement(LabeledStatementTree t, Trees p) {
        info("LabeledStatementTree" + CL + t.getKind() + SP + t);
        return super.visitLabeledStatement(t, p);
    }

    @Override
    public Object visitLiteral(LiteralTree t, Trees p) {
        info("LiteralTree" + CL + t.getKind() + SP + t);
        return super.visitLiteral(t, p);
    }

    @Override
    public Object visitMemberSelect(MemberSelectTree t, Trees p) {
        info("MemberSelectTree" + CL + t.getKind() + SP + t);
        return super.visitMemberSelect(t, p);
    }

    @Override
    public Object visitMethod(MethodTree t, Trees p) {
        info("MethodTree" + CL + t.getKind() + SP + t.getName());
        return super.visitMethod(t, p);
    }

    @Override
    public Object visitMethodInvocation(MethodInvocationTree t, Trees p) {
        info("MethodInvocationTree" + CL + t.getKind() + SP + t);
        return super.visitMethodInvocation(t, p);
    }

    @Override
    public Object visitModifiers(ModifiersTree t, Trees p) {
        info("ModifiersTree" + CL + t.getKind() + SP + t);
        return super.visitModifiers(t, p);
    }

    @Override
    public Object visitNewArray(NewArrayTree t, Trees p) {
        info("NewArrayTree" + CL + t.getKind() + SP + t);
        return super.visitNewArray(t, p);
    }

    @Override
    public Object visitNewClass(NewClassTree t, Trees p) {
        info("NewClassTree" + CL + t.getKind() + SP + t);
        return super.visitNewClass(t, p);
    }

    @Override
    public Object visitOther(Tree t, Trees p) {
        info("Tree" + CL + t.getKind() + SP + t);
        return super.visitOther(t, p);
    }

    @Override
    public Object visitParameterizedType(ParameterizedTypeTree t, Trees p) {
        info("ParameterizedTypeTree" + CL + t.getKind() + SP + t);
        return super.visitParameterizedType(t, p);
    }

    @Override
    public Object visitParenthesized(ParenthesizedTree t, Trees p) {
        info("ParenthesizedTree" + CL + t.getKind() + SP + t);
        return super.visitParenthesized(t, p);
    }

    @Override
    public Object visitPrimitiveType(PrimitiveTypeTree t, Trees p) {
        info("PrimitiveTypeTree" + CL + t.getKind() + SP + t);
        return super.visitPrimitiveType(t, p);
    }

    @Override
    public Object visitReturn(ReturnTree t, Trees p) {
        info("ReturnTree" + CL + t.getKind() + SP + t);
        return super.visitReturn(t, p);
    }

    @Override
    public Object visitSwitch(SwitchTree t, Trees p) {
        info("SwitchTree" + CL + t.getKind() + SP + t);
        return super.visitSwitch(t, p);
    }

    @Override
    public Object visitSynchronized(SynchronizedTree t, Trees p) {
        info("SynchronizedTree" + CL + t.getKind() + SP + t);
        return super.visitSynchronized(t, p);
    }

    @Override
    public Object visitThrow(ThrowTree t, Trees p) {
        info("ThrowTree" + CL + t.getKind() + SP + t);
        return super.visitThrow(t, p);
    }

    @Override
    public Object visitTry(TryTree t, Trees p) {
        info("TryTree" + CL + t.getKind() + SP + t);
        return super.visitTry(t, p);
    }

    @Override
    public Object visitTypeCast(TypeCastTree t, Trees p) {
        info("TypeCastTree" + CL + t.getKind() + SP + t);
        return super.visitTypeCast(t, p);
    }

    @Override
    public Object visitTypeParameter(TypeParameterTree t, Trees p) {
        info("TypeParameterTree" + CL + t.getKind() + SP + t);
        return super.visitTypeParameter(t, p);
    }

    @Override
    public Object visitUnary(UnaryTree t, Trees p) {
        info("UnaryTree" + CL + t.getKind() + SP + t);
        return super.visitUnary(t, p);
    }

    @Override
    public Object visitVariable(VariableTree t, Trees p) {
        info("VariableTree" + CL + t.getKind() + SP + t
            + CM + t.getName()
            + CM + t.getModifiers()
            + CM + t.getType().getKind() + SP + t.getType()
            + CM + t.getInitializer());
        return super.visitVariable(t, p);
    }

    @Override
    public Object visitWhileLoop(WhileLoopTree t, Trees p) {
        info("WhileLoopTree" + CL + t.getKind() + SP + t);
        return super.visitWhileLoop(t, p);
    }

    @Override
    public Object visitWildcard(WildcardTree t, Trees p) {
        info("WildcardTree" + CL + t.getKind() + SP + t);
        return super.visitWildcard(t, p);
    }

}
