package nl.han.ica.icss.checker;

import nl.han.ica.datastructures.HANLinkedList;
import nl.han.ica.datastructures.HashChecker;
import nl.han.ica.datastructures.IHANLinkedList;
import nl.han.ica.icss.ast.ASTNode;
import nl.han.ica.icss.ast.VariableAssignment;
import nl.han.ica.icss.ast.VariableReference;
import nl.han.ica.icss.ast.types.ExpressionType;

import java.util.HashMap;

public class CheckerVariable {

    private  HashChecker<String, ExpressionType> variableTypes;
    CheckerExpression expressionChecker;

    public CheckerVariable(HashChecker<String, ExpressionType> variableTypes){
        this.variableTypes = variableTypes;
        this.expressionChecker = new CheckerExpression(this);
    }

    public ExpressionType checkVariableReference(VariableReference reference) {
        ExpressionType expressionType = variableTypes.getVariableByKey((reference).name);

        if(expressionType == null){
            reference.setError("Variable "+ reference.name  +" has not been declared in scope.");
            return null;
        }
        return expressionType;
    }

    public void checkVariableAssignment(ASTNode astNode) {
        VariableAssignment variableAssignment = (VariableAssignment) astNode;
        VariableReference variableReference = variableAssignment.name;
        ExpressionType expressionType = expressionChecker.checkExpressionType(variableAssignment.expression);

        if(expressionType == ExpressionType.UNDEFINED || expressionType == null){
            astNode.setError("Variable has not been assigned, error in expression: " + expressionType);
        }
        variableTypes.put(variableReference.name, expressionType);
    }
}