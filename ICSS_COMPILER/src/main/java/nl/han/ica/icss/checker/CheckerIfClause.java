package nl.han.ica.icss.checker;

import nl.han.ica.icss.ast.Expression;
import nl.han.ica.icss.ast.IfClause;
import nl.han.ica.icss.ast.types.ExpressionType;

public class CheckerIfClause {

    private CheckerExpressions expressionChecker;

    public CheckerIfClause(CheckerExpressions expressionChecker){
        this.expressionChecker = expressionChecker;
    }

    public ExpressionType checkIfClause(IfClause ifClause) {
        Expression ifClauseExpression = ifClause.getConditionalExpression();
        ExpressionType expressionType = expressionChecker.checkIfExpressionIsTypeOrOperation(ifClauseExpression);

        if(expressionType != ExpressionType.BOOL){
            ifClause.setError("If clause " + ifClauseExpression.toString() + " must be boolean literal.");
            return null;
        }

        return ExpressionType.BOOL;

    }
}
