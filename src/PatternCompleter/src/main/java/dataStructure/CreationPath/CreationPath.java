package dataStructure.CreationPath;

import java.util.ArrayList;
import java.util.List;

public class CreationPath {
    private List<CreationPathNode> creationPathNodes = new ArrayList<>();
    public void addNode(CreationPathNode node){
        creationPathNodes.add(node);
    }
    @Override
    public String toString(){
        String result = "";
        for (CreationPathNode node : creationPathNodes){
            result = node.toString() + "." + result;
        }
        if (result!=""){
            result = result.substring(0,result.length()-1);
        }
        return result;
    }
    public CreationPathNode get(int index){
        return creationPathNodes.get(creationPathNodes.size()-(1+index));
    }
}
