package dataStructure.CreationPath;

public class CreationPathReferenceNode implements CreationPathNode {
    public int referencePosition;
    public String referenceName;

    public CreationPathReferenceNode(String referenceName, int referencePosition){
        this.referenceName = referenceName;
        this.referencePosition = referencePosition;
    }
    @Override
    public String toString(){
        return "";
    }
}
