package nl.han.ica.icss.checker;

import nl.han.ica.datastructures.ScopeChecker;
import nl.han.ica.icss.ast.Expression;
import nl.han.ica.icss.ast.VariableReference;
import nl.han.ica.icss.ast.literals.BoolLiteral;
import nl.han.ica.icss.ast.literals.ColorLiteral;
import nl.han.ica.icss.ast.literals.PercentageLiteral;
import nl.han.ica.icss.ast.literals.PixelLiteral;
import nl.han.ica.icss.ast.types.ExpressionType;

public class CheckerExpression {

    private CheckerVariable variableChecker;

    public CheckerExpression(CheckerVariable variableChecker){
    this.variableChecker = variableChecker;
    }

    public ExpressionType checkExpressionType(Expression expression){

        if(expression instanceof VariableReference){
            return variableChecker.checkVariableReference((VariableReference) expression);
        } else {
            if (expression instanceof ColorLiteral) {
                return ExpressionType.COLOR;
            }
            if (expression instanceof PercentageLiteral) {
                return ExpressionType.PERCENTAGE;
            }
            if (expression instanceof PixelLiteral) {
                return ExpressionType.PIXEL;
            }
            if (expression instanceof BoolLiteral) {
                return ExpressionType.BOOL;
            }
        }
        return ExpressionType.UNDEFINED;
    }
}
