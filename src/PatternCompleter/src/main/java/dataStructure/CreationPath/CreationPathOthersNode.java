package dataStructure.CreationPath;

public class CreationPathOthersNode implements CreationPathNode {
    private String representation;
    public CreationPathOthersNode(String representation){
        this.representation = representation;
    }
    @Override
    public String toString(){
        return representation;
    }
}
