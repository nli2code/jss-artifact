package dataStructure;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.resolution.UnsolvedSymbolException;
import com.github.javaparser.resolution.declarations.ResolvedMethodDeclaration;
import com.github.javaparser.resolution.declarations.ResolvedValueDeclaration;
import com.github.javaparser.symbolsolver.javaparsermodel.declarations.JavaParserParameterDeclaration;
import com.github.javaparser.symbolsolver.javaparsermodel.declarations.JavaParserSymbolDeclaration;
import dataStructure.CreationPath.*;
import utils.Config;

import java.util.*;

public class PatternInstance implements Cloneable {
    private List<PatternInstanceLine> lines = new ArrayList<>();
    private String Signature = "";
    private List<Node> trackingFillers = new ArrayList<>();
    private List<CreationPath> creationPaths = new ArrayList<>();

    public List<CreationPath> getCreationPaths(){
        return creationPaths;
    }

    public List<String> getCreationPathsAsString() {
        List<String> fillers = new ArrayList<>();
        for (CreationPath path : creationPaths){
            fillers.add(path.toString());
        }
        return fillers;
    }

    public void addLine(PatternInstanceLine line) {
        lines.add(line);
    }

    private CreationPath parse(Node focusNode, List<Node> allFillers){
        CreationPath creationPath = new CreationPath();
        Node currentNode = focusNode;
        while (currentNode != null){
            if (currentNode instanceof VariableDeclarator){
                if (((VariableDeclarator) currentNode).getInitializer().isPresent()){
                    currentNode = ((VariableDeclarator) currentNode).getInitializer().get();
                }else {
                    creationPath.addNode(new CreationPathVariableNode(
                            ((VariableDeclarator) currentNode).getTypeAsString(),
                            ((VariableDeclarator) currentNode).getNameAsString()
                    ));
                    break;
                }
            } else if (currentNode instanceof MethodCallExpr){
                MethodCallExpr methodCallExpr = (MethodCallExpr) currentNode;
                ResolvedMethodDeclaration resolvedMethodDeclaration = methodCallExpr.resolve();

                creationPath.addNode(new CreationPathMethodNode(
                        resolvedMethodDeclaration.getQualifiedSignature(),
                        methodCallExpr.getNameAsString()
                ));

                Optional<Expression> caller = methodCallExpr.getScope();
                if (caller.isPresent()){
                    currentNode = caller.get();
                } else {
                    break;
                }
            } else if(currentNode instanceof ObjectCreationExpr || currentNode instanceof FieldAccessExpr){
                creationPath.addNode(new CreationPathOthersNode( currentNode.toString()));
                break;
            } else if (currentNode instanceof NameExpr){
                if (currentNode!=focusNode){
                    for (Node elseNode : allFillers){
                        if (elseNode == focusNode){
                            continue;
                        }
                        if (elseNode instanceof NameExpr || elseNode instanceof VariableDeclarator){
                            String elseNodeName;
                            if (elseNode instanceof NameExpr){
                                elseNodeName = ((NameExpr) elseNode).getNameAsString();
                            }else{
                                elseNodeName = ((VariableDeclarator) elseNode).getNameAsString();
                            }
                            if (elseNodeName.equals(((NameExpr) currentNode).getNameAsString())){
                                creationPath.addNode(new CreationPathReferenceNode(
                                        elseNodeName,
                                        allFillers.indexOf(elseNode)
                                ));
                                return creationPath;
                            }
                        }
                    }
                }

                ResolvedValueDeclaration resolvedValueDeclaration = ((NameExpr) currentNode).resolve();
                if (resolvedValueDeclaration instanceof JavaParserSymbolDeclaration){
                    JavaParserSymbolDeclaration symbolDeclaration = ((JavaParserSymbolDeclaration)resolvedValueDeclaration);
                    VariableDeclarator variableDeclarator = (VariableDeclarator)symbolDeclaration.getWrappedNode();
                    Optional<Expression> initializer = variableDeclarator.getInitializer();

                    if (initializer.isPresent()){
                        currentNode = initializer.get();
                    }else {
                        creationPath.addNode(new CreationPathVariableNode(
                                variableDeclarator.getTypeAsString(),
                                variableDeclarator.getNameAsString()
                        ));
                        break;
                    }
                }else if (resolvedValueDeclaration instanceof JavaParserParameterDeclaration){
                    JavaParserParameterDeclaration parameterDeclaration = ((JavaParserParameterDeclaration)resolvedValueDeclaration);
                    Parameter parameter = parameterDeclaration.getWrappedNode();
                    creationPath.addNode(new CreationPathVariableNode(parameter.getTypeAsString(), parameter.getNameAsString()));
                    break;
                }else{
                    break;
                }
            } else {
                creationPath.addNode(new CreationPathOthersNode(currentNode.toString()));
                break;
            }
        }
        return creationPath;
    }

    public void trackCreationPaths(){
        List<Node> fillers = new ArrayList<>();
        for (PatternInstanceLine line : lines){
            fillers.addAll(line.trackingFillers);
        }
        for (Node filler : fillers){
            CreationPath path;
            try {
                path = parse(filler,fillers);
            }catch (UnsolvedSymbolException e){
                if (Config.debug()){
                    e.printStackTrace();
                }
                path = new CreationPath();
                path.addNode(new CreationPathOthersNode("unsolved"));
                //System.out.println(filler.toString());
            }
            creationPaths.add(path);
        }
        /*
        for (CreationPath creationPath : creationPaths){
            System.out.println(creationPath);
        }
        System.out.println("+++++");
        */
    }

    public boolean isIllegalPattern() {
        boolean isIllegal = false;
        for (PatternInstanceLine line : lines) {
            if (line.mode == PatternInstanceLine.Mode.ERROR) {
                isIllegal = true;
            }
        }
        return isIllegal;
    }


    public String getSignature() {
        if (Signature != "") {
            return Signature;
        }
        for (PatternInstanceLine line : lines) {
            Signature += line.getLineSignature() + '\n';
        }
        return Signature;
    }

    public List<String> serialize() {
        List<String> fillers = new ArrayList<>();
        for (PatternInstanceLine line : lines) {
            fillers.addAll(line.serialize());
        }
        return fillers;
    }

    public void deserialize(List<String> fillers) {
        int startPoint = 0;
        for (PatternInstanceLine line : lines) {
            startPoint = line.deserialize(fillers, startPoint);
        }
    }

    public List<String> typeSerialize() {
        List<String> types = new ArrayList<>();
        for (PatternInstanceLine line : lines) {
            types.addAll(line.typeSerialize());
        }
        return types;
    }

    public List<String> getSymbols() {
        List<String> symbols = new ArrayList<>();
        for (PatternInstanceLine line : lines) {
            symbols.addAll(line.getSymbols());
        }
        Set<String> reducedSymbols = new HashSet<>(symbols);
        return new ArrayList<>(reducedSymbols);
    }

    public List<Integer> getIntermediateHoles() {
        int startPos = 0;
        List<Integer> intermediateHoles = new ArrayList<>();
        for (PatternInstanceLine line : lines) {
            if (line.mode == PatternInstanceLine.Mode.ASSIGN) {
                intermediateHoles.add(startPos);
            }
            startPos += line.serialize().size();
        }
        return intermediateHoles;
    }

    public String toString() {
        String pattern = "";
        for (PatternInstanceLine line : lines) {
            pattern += line.toString() + "\n";
        }
        return pattern;
    }

    @Override
    public Object clone() {
        PatternInstance clone = null;
        try {
            clone = (PatternInstance) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            System.exit(1);
        }

        clone.lines = new ArrayList<>();
        for (PatternInstanceLine line : lines) {
            clone.lines.add((PatternInstanceLine) line.clone());
        }

        return clone;
    }
}
