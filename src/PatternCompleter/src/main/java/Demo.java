import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.TryStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.resolution.declarations.ResolvedMethodDeclaration;
import com.github.javaparser.resolution.declarations.ResolvedValueDeclaration;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.javaparsermodel.declarations.JavaParserFieldDeclaration;
import com.github.javaparser.symbolsolver.javaparsermodel.declarations.JavaParserParameterDeclaration;
import com.github.javaparser.symbolsolver.javaparsermodel.declarations.JavaParserSymbolDeclaration;
import com.github.javaparser.symbolsolver.model.resolution.TypeSolver;
import com.github.javaparser.symbolsolver.resolution.SymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;
import dataStructure.PatternInstanceLine;


import java.io.File;
import java.io.IOException;

public class Demo {
    public static SymbolSolver symbolSolver;

    public static void main(String[] args) throws IOException {
        TypeSolver typeSolver = new ReflectionTypeSolver();
        JavaSymbolSolver symbolSolver = new JavaSymbolSolver(typeSolver);
        Demo.symbolSolver = new SymbolSolver(typeSolver);
        JavaParser.getStaticConfiguration().setSymbolResolver(symbolSolver);

        CompilationUnit cu = JavaParser.parse(new File("data/sample.java"));
        System.out.println(cu);
        cu.accept(new MethodCallVistor(), null);
    }

    private static class MethodCallVistor extends VoidVisitorAdapter<Void> {

        @Override
        public void visit(BlockStmt n,Void arg){
            n.getParentNode().ifPresent(node ->{
                System.out.println(node.getMetaModel());
            });
            super.visit(n,arg);
        }
        @Override
        public void visit(MethodCallExpr n, Void arg) {
            /*
            PatternInstanceLine line = new PatternInstanceLine(n);

            System.out.println(n.getName());
            System.out.println(n.getArguments());

            ResolvedMethodDeclaration rn = n.resolve();
            System.out.println(rn.getQualifiedSignature());
            System.out.println(rn.getReturnType().describe());
            */


            n.getScope().ifPresent(node -> {
                ResolvedValueDeclaration resolvedValueDeclaration;
                if(node instanceof NameExpr){
                    resolvedValueDeclaration = node.asNameExpr().resolve();
                }else if(node instanceof FieldAccessExpr){
                    resolvedValueDeclaration = node.asFieldAccessExpr().resolve();
                }else{
                    System.out.println(node.getMetaModel());
                    return;
                }
                System.out.println(resolvedValueDeclaration.getClass());
                if (resolvedValueDeclaration instanceof JavaParserSymbolDeclaration){
                    JavaParserSymbolDeclaration symbolDeclaration = ((JavaParserSymbolDeclaration)resolvedValueDeclaration);
                    VariableDeclarator variableDeclarator = (VariableDeclarator)symbolDeclaration.getWrappedNode();
                    System.out.println(variableDeclarator.getInitializer());
                    variableDeclarator.getInitializer().ifPresent(initializer -> {
                        System.out.println(initializer.getMetaModel());
                    });
                }else if (resolvedValueDeclaration instanceof JavaParserParameterDeclaration){
                    JavaParserParameterDeclaration parameterDeclaration = ((JavaParserParameterDeclaration)resolvedValueDeclaration);
                    Parameter parameter = parameterDeclaration.getWrappedNode();
                    System.out.println(parameter.getParentNode());
                }else if(resolvedValueDeclaration instanceof JavaParserFieldDeclaration){
                    JavaParserFieldDeclaration javaParserFieldDeclaration = ((JavaParserFieldDeclaration)resolvedValueDeclaration);
                    FieldDeclaration fieldDeclaration = javaParserFieldDeclaration.getWrappedNode();
                    System.out.println(fieldDeclaration.getVariables().get(0).getInitializer());
                }
            });

            /*n.getParentNode().ifPresent(node -> {
                System.out.println(node);
                if (node instanceof AssignExpr) {
                    System.out.println(((AssignExpr) node).getTarget());
                }
                if (node instanceof VariableDeclarator) {
                    System.out.println(((VariableDeclarator) node).getType());
                    System.out.println(((VariableDeclarator) node).getName());
                } else {
                    System.out.println(node.getMetaModel());
                }
            });
            */
            super.visit(n, arg);
        }

        @Override
        public void visit(VariableDeclarator n, Void arg) {
            super.visit(n, arg);
        }
    }
}
