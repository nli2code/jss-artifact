package dataStructure.CreationPath;

import utils.LastNameFinder;

public class CreationPathVariableNode implements CreationPathNode {
    private String variableType;
    private String variableName;
    private String shortType;
    public CreationPathVariableNode(String type, String name){
        variableType = type;
        variableName = name;
        shortType = LastNameFinder.getLastName(type);
    }
    @Override
    public String toString(){
        return shortType;
    }
}
