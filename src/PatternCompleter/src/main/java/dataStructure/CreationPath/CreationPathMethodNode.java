package dataStructure.CreationPath;

public class CreationPathMethodNode implements CreationPathNode {
    private String methodSignature;
    private String methodName;
    public CreationPathMethodNode(String signature, String name){
        methodSignature = signature;
        methodName = name;
    }
    @Override
    public String toString(){
        return methodName + "()";
    }
}
