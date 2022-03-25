package nl.han.ica.icss.checker;

import nl.han.ica.datastructures.HANLinkedList;
import nl.han.ica.datastructures.IHANLinkedList;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.ColorLiteral;
import nl.han.ica.icss.ast.literals.PercentageLiteral;
import nl.han.ica.icss.ast.literals.PixelLiteral;
import nl.han.ica.icss.ast.types.ExpressionType;

import java.util.Collections;
import java.util.HashMap;

public class Checker {

    private IHANLinkedList<HashMap<String, ExpressionType>> variableTypes;

    public void check(AST ast) {
        variableTypes = new HANLinkedList<>();
        checkerEntireStyleSheet(ast.root);
    }

    private void checkerEntireStyleSheet(ASTNode astNode){
        for (ASTNode childNode : astNode.getChildren()){
            if(childNode instanceof VariableAssignment){
                checkVariableAssignment(childNode);
            }
            if(childNode instanceof Stylerule){
                checkerStyleRule(childNode);
            }
        }
    }

    private void checkVariableAssignment(ASTNode astNode) {
        VariableAssignment assignment = (VariableAssignment) astNode;
        Expression expression = assignment.expression;

        if(expression instanceof ColorLiteral){
            HashMap<String, ExpressionType> hashMap = new HashMap(); hashMap.put(astNode.getNodeLabel(), ExpressionType.COLOR);

            System.out.println(astNode.getNodeLabel());
            System.out.println(hashMap.toString());
            variableTypes.addFirst(hashMap);
        }
    }

    private void checkerStyleRule(ASTNode astNode) {
        for(ASTNode childNode : astNode.getChildren()){
            if(childNode instanceof Declaration){
                checkerDeclaration(childNode);
            }
        }
    }

    private void checkerDeclaration(ASTNode astNode) {
        Declaration declaration = (Declaration) astNode;
        String propertyName = declaration.property.name;
        ExpressionType expression = checkExpressionType(astNode);

        switch(propertyName){
            case("width"):
                if(expression != ExpressionType.PIXEL){
                    astNode.setError("Width can only be in pixel literals");
                }
                break;
            case("height"):
                if(expression != ExpressionType.PIXEL){
                    astNode.setError("Height can only be in  pixel literals");
                }
                break;
            case("color"):
                if(expression != ExpressionType.COLOR){
                    astNode.setError("Color can only be in color literals");
                }
                break;
            case("background-color"):
                if(expression != ExpressionType.COLOR){
                    astNode.setError("Background-color can only be in color literals");
                }
                break;
        }
    }


    private ExpressionType checkExpressionType(ASTNode astNode){
        Declaration declaration = (Declaration) astNode;
        Expression expression = declaration.expression;

        if(expression instanceof ColorLiteral){
            return ExpressionType.COLOR;
        }
        if(expression instanceof PercentageLiteral){
            return ExpressionType.PERCENTAGE;
        }
        if(expression instanceof PixelLiteral){
            return ExpressionType.PIXEL;
        }
        if(expression instanceof Operation){
            return checkOperation(expression);
        }
        if(expression instanceof VariableReference){
            return checkVariableReference(astNode);

        }
        return ExpressionType.UNDEFINED;
    }

    private ExpressionType checkVariableReference(ASTNode astNode) {
        System.out.println(astNode.toString());

        

        return null;
    }

    private ExpressionType checkOperation(Expression expression) {
        return null;
    }

}
    

