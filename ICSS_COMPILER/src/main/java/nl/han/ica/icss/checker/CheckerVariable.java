package nl.han.ica.icss.checker;

import nl.han.ica.datastructures.SymbolTable;
import nl.han.ica.icss.ast.ASTNode;
import nl.han.ica.icss.ast.VariableReference;
import nl.han.ica.icss.ast.VariableAssignment;
import nl.han.ica.icss.ast.types.ExpressionType;

public class CheckerVariable {

    private SymbolTable<String, ExpressionType> variableTypes;
    CheckerExpression expressionChecker;

    public CheckerVariable(SymbolTable<String, ExpressionType> variableTypes){
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
        ExpressionType expressionType = expressionChecker.checkIfExpressionIsTypeOrOperation(variableAssignment.expression);

        if(expressionType == ExpressionType.UNDEFINED || expressionType == null){
            astNode.setError("Variable has not been assigned, error in expression: " + expressionType);
        }
        variableTypes.put(variableReference.name, expressionType);
    }
}
