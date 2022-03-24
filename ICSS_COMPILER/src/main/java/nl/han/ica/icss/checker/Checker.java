package nl.han.ica.icss.checker;

import nl.han.ica.datastructures.HANLinkedList;
import nl.han.ica.datastructures.IHANLinkedList;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.types.ExpressionType;

import java.util.HashMap;

public class Checker {

    private IHANLinkedList<HashMap<String, ExpressionType>> variableTypes;

    public void check(AST ast) {
        variableTypes = new HANLinkedList<>();
//        checkNodes(ast.root);
        checkEntireStyleSheet(ast.root);
    }

    private void checkEntireStyleSheet(ASTNode astNode){
        for (ASTNode childNode : astNode.getChildren()){

            if(childNode instanceof VariableAssignment){
                checkVariableAssignment(childNode);
            }
            if(childNode instanceof Stylerule){
                checkStyleRule(childNode);
            }
        }
    }

    private void checkStyleRule(ASTNode astNode) {
        for(ASTNode childNode : astNode.getChildren()){
            System.out.println(childNode.getNodeLabel());
        }
    }

    private void checkVariableAssignment(ASTNode childNode) {
    }


    private void checkNodes(ASTNode astNode) {
        for(ASTNode childNode : astNode.getChildren()){
            System.out.println(childNode.getNodeLabel());
            if(childNode.getChildren() != null) {
                checkNodes(childNode);
            } else{
                checkNode(childNode);
            }
        }
    }

    private void checkNode(ASTNode astNode){
        Declaration d = (Declaration) astNode;
        System.out.println(d.expression.toString());
    }

}
    

