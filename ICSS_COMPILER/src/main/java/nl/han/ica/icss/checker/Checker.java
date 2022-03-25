package nl.han.ica.icss.checker;

import nl.han.ica.datastructures.HANLinkedList;
import nl.han.ica.datastructures.HashChecker;
import nl.han.ica.datastructures.IHANLinkedList;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.ColorLiteral;
import nl.han.ica.icss.ast.literals.PercentageLiteral;
import nl.han.ica.icss.ast.literals.PixelLiteral;
import nl.han.ica.icss.ast.types.ExpressionType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Checker {

//    private IHANLinkedList<HashMap<String, ExpressionType>> variableTypes;
        private  HashChecker<String, ExpressionType> variableTypes;

    public void check(AST ast) {
        variableTypes = new HashChecker<>();
        checkerEntireStyleSheet(ast.root);
    }

    private void checkerEntireStyleSheet(ASTNode astNode){
        Stylesheet stylesheet = (Stylesheet) astNode;

        variableTypes.pushHashes();
        for (ASTNode childNode : stylesheet.getChildren()){
            if(childNode instanceof VariableAssignment){
                checkVariableAssignment(childNode);
                continue;
            }
            if(childNode instanceof Stylerule){
                checkerStyleRule(childNode);
            }
        }
        variableTypes.pushHashes();
    }

    private void checkVariableAssignment(ASTNode astNode) {
        VariableAssignment variableAssignment = (VariableAssignment) astNode;
        VariableReference variableReference = variableAssignment.name;
        ExpressionType expressionType = checkExpressionType(variableAssignment.expression);

        if(expressionType == ExpressionType.UNDEFINED || expressionType == null){
            astNode.setError("Variable has not been assigned, error in expression: " + expressionType);
        }
        variableTypes.put(variableReference.name, expressionType);
    }

    private void checkerStyleRule(ASTNode astNode) {
        Stylerule styleRule = (Stylerule) astNode;
        checkStyleBody(styleRule.body);
    }

    private void checkStyleBody(ArrayList<ASTNode> body) {
        for(ASTNode bodyChildNode : body){
            if(bodyChildNode instanceof Declaration){
                checkerDeclaration(bodyChildNode);
            }
            if(bodyChildNode instanceof VariableAssignment){
                checkVariableAssignment(bodyChildNode);
            }
        }
    }

    private void checkerDeclaration(ASTNode astNode) {
        Declaration declaration = (Declaration) astNode;
        String propertyName = declaration.property.name;
        ExpressionType expression = checkExpressionType(declaration.expression);

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


    private ExpressionType checkExpressionType(Expression expression){
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
            checkVariableReference((VariableReference) expression);
        }
        return ExpressionType.UNDEFINED;
    }

    private ExpressionType checkVariableReference(VariableReference reference) {
        ExpressionType expressionType = variableTypes.getVariableByKey((reference).name);

        if(expressionType == null){
            reference.setError("Variable "+ reference.name  +" has not been declared in scope.");
            return null;
        }
        return expressionType;
    }

    private ExpressionType checkOperation(Expression expression) {
        return null;
    }

}
    

