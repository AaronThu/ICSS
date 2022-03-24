package nl.han.ica.icss.checker;

import nl.han.ica.datastructures.HANLinkedList;
import nl.han.ica.datastructures.IHANLinkedList;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.ColorLiteral;
import nl.han.ica.icss.ast.literals.PercentageLiteral;
import nl.han.ica.icss.ast.literals.PixelLiteral;
import nl.han.ica.icss.ast.types.ExpressionType;

import java.util.HashMap;

public class Checker {

    private IHANLinkedList<HashMap<String, ExpressionType>> variableTypes;

    public void check(AST ast) {
        variableTypes = new HANLinkedList<>();
//        checkNodes(ast.root);
        checkerEntireStyleSheet(ast.root);
    }

    private void checkerEntireStyleSheet(ASTNode astNode){
        for (ASTNode childNode : astNode.getChildren()){

            if(childNode instanceof VariableAssignment){
                //checkerVariableAssignment(childNode);
            }
            if(childNode instanceof Stylerule){
                checkerStyleRule(childNode);
            }
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
        ExpressionType expression = checkExpressionType(declaration);

        switch(propertyName){
            case("width"):
                if(expression != ExpressionType.PERCENTAGE || expression != ExpressionType.PIXEL){
                    astNode.setError("Width can only be in percentage or pixel literals" + astNode.toString());
                }
                break;
            case("height"):
                if(expression != ExpressionType.PERCENTAGE || expression != ExpressionType.PIXEL){
                    astNode.setError("Height can only be in percentage or pixel literals" + astNode.toString());
                }
                break;
            case("color"):
                if(expression != ExpressionType.COLOR){
                    astNode.setError("Color can only be in percentage or pixel literals" + astNode.toString());
                }
                break;
            case("background-color"):
                if(expression != ExpressionType.COLOR){
                    astNode.setError("Background-color can only be in percentage or pixel literals" + astNode.toString());
                }
                break;
        }
    }


    private ExpressionType checkExpressionType(Declaration declaration){
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

        return ExpressionType.UNDEFINED;
    }

    private ExpressionType checkOperation(Expression expression) {
        return null;
    }

}
    

