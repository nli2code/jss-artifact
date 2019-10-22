package dataStructure;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.resolution.UnsolvedSymbolException;
import com.github.javaparser.resolution.declarations.ResolvedMethodDeclaration;
import com.github.javaparser.resolution.declarations.ResolvedValueDeclaration;
import com.github.javaparser.symbolsolver.javaparsermodel.declarations.JavaParserParameterDeclaration;
import com.github.javaparser.symbolsolver.javaparsermodel.declarations.JavaParserSymbolDeclaration;
import dataStructure.CreationPath.*;
import utils.Config;
import utils.LastNameFinder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PatternInstanceLine implements Cloneable {
    public enum Mode {ASSIGN, METHODCALL, ERROR}

    public Mode mode;
    private boolean callerExist;

    private String MethodSignature;
    private String MethodName;
    private String MethodLocation;
    private List<String> MethodParameterTypes = new ArrayList<>();
    private int MethodParameterNumber;

    private List<String> MethodParameters = new ArrayList<>();
    private String MethodCaller;

    private String AssignTarget;
    private String AssignTargetType;
    private Node AssignTargetNode;

    public List<Node> trackingFillers = new ArrayList<>();


    public List<String> serialize() {
        ArrayList<String> holeInstance = new ArrayList<>();
        if (mode == Mode.ASSIGN) {
            holeInstance.add(AssignTarget);
        }
        if (callerExist) {
            holeInstance.add(MethodCaller);
        }
        holeInstance.addAll(MethodParameters);
        return holeInstance;
    }

    public int deserialize(List<String> stream, int startPoint) {
        if (mode != Mode.METHODCALL) {
            AssignTarget = stream.get(startPoint++);
        }
        if (callerExist) {
            MethodCaller = stream.get(startPoint++);
        }
        MethodParameters = new ArrayList<>();
        for (int i = 0; i < MethodParameterNumber; i++) {
            MethodParameters.add(stream.get(startPoint++));
        }
        return startPoint;
    }

    public List<String> typeSerialize() {
        ArrayList<String> types = new ArrayList<>();
        if (mode == Mode.ASSIGN) {
            types.add(AssignTargetType);
        }
        if (callerExist) {
            types.add(MethodLocation);
        }
        types.addAll(MethodParameterTypes);
        return types;
    }

    public List<String> getSymbols() {
        ArrayList<String> pairs = new ArrayList<>();
        if (mode == Mode.ASSIGN) {
            int lastDotPosition = AssignTargetType.lastIndexOf(".");
            if (lastDotPosition != -1) {
                pairs.add(AssignTargetType.substring(lastDotPosition + 1) + "  :  " + AssignTargetType);
            } else {
                pairs.add(AssignTargetType + "  :  " + AssignTargetType);
            }
        }
        pairs.add(MethodName + "  :  " + MethodSignature);
        return pairs;
    }


    private void fillSignature(String signature) {
        MethodSignature = signature;
        String[] temp = signature.split("\\(");
        int lastDotPosition = temp[0].lastIndexOf(".");
        MethodLocation = temp[0].substring(0, lastDotPosition);
        MethodName = temp[0].substring(lastDotPosition + 1);
        temp = temp[1].split("\\)|, ");
        MethodParameterNumber = temp.length;
        for (String parameter : temp) {
            MethodParameterTypes.add(parameter);
        }
    }

    public PatternInstanceLine(MethodCallExpr n) {

        ResolvedMethodDeclaration rn;
        try {
            rn = n.resolve();
        } catch (Exception e) {
            if (Config.debug()) {
                System.out.println(e);
            }

            mode = Mode.ERROR;
            return;
        }

        n.getParentNode().ifPresent(parent -> {
            if (parent instanceof VariableDeclarator) {
                mode = Mode.ASSIGN;
                AssignTargetType = ((VariableDeclarator) parent).getTypeAsString();
                AssignTarget = ((VariableDeclarator) parent).getNameAsString();
                AssignTargetNode = parent;
            } else if (parent instanceof AssignExpr) {
                mode = Mode.ASSIGN;
                AssignTargetType = rn.getReturnType().describe();
                AssignTarget = ((AssignExpr) parent).getTarget().toString();
                AssignTargetNode = ((AssignExpr) parent).getTarget();
            } else if (parent instanceof ExpressionStmt) {
                mode = Mode.METHODCALL;
            } else {
                mode = Mode.ERROR;
                if (Config.debug()) {
                    System.out.println(
                            "Method call <" + MethodName + ">'s parent node type <"
                                    + parent.getMetaModel() + "> unknown: " + parent.toString()
                    );
                }
                return;
            }
        });

        fillSignature(rn.getQualifiedSignature());

        if (mode == Mode.ASSIGN) {
            trackingFillers.add(AssignTargetNode);
        }

        callerExist = n.getScope().isPresent();
        n.getScope().ifPresent(caller -> {
            MethodCaller = caller.toString();
            trackingFillers.add(caller);
        });

        for (Expression e : n.getArguments()) {
            MethodParameters.add(e.toString());
            trackingFillers.add(e);
        }

        /*
        if (serialize().size()!=pathSerialize().size()){
            System.out.println(serialize());
            System.out.println(pathSerialize());
        }
        */
    }

    public String getLineSignature() {
        String signature = mode.toString() + " " + callerExist + " " + MethodSignature;
        if (mode != Mode.METHODCALL) {
            signature += " " + AssignTargetType;
        }
        return signature;
    }

    public String toString() {
        String result = "";
        if (mode == Mode.ASSIGN) {
            result += LastNameFinder.getLastName(AssignTargetType) + " ";
            result += AssignTarget + " = ";
        }
        if (callerExist) {
            result += MethodCaller + ".";
        }
        result += MethodName + "(";
        for (String parameter : MethodParameters) {
            result += parameter + ", ";
        }
        if (result.endsWith(", ")) {
            result = result.substring(0, result.length() - 2);
        }
        result += ");";

        /*
        for (CreationPath creationPath : creationPaths){
            result += "\nPath : " + creationPath.toString();
        }
        */

        return result;
    }

    @Override
    public Object clone() {
        PatternInstanceLine clone = null;
        try {
            clone = (PatternInstanceLine) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            System.exit(1);
        }

        clone.MethodParameters = new ArrayList<>();
        for (String parameter : MethodParameters) {
            clone.MethodParameters.add(parameter);
        }

        clone.MethodParameterTypes = new ArrayList<>();
        for (String parameterType : MethodParameterTypes) {
            clone.MethodParameterTypes.add(parameterType);
        }
        return clone;
    }
}
