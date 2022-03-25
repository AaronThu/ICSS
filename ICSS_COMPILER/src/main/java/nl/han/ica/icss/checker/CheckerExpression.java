package nl.han.ica.icss.checker;

import nl.han.ica.datastructures.ScopeChecker;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.BoolLiteral;
import nl.han.ica.icss.ast.literals.ColorLiteral;
import nl.han.ica.icss.ast.literals.PercentageLiteral;
import nl.han.ica.icss.ast.literals.PixelLiteral;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.types.ExpressionType;

public class CheckerExpression {

    private CheckerVariable variableChecker;

    public CheckerExpression(CheckerVariable variableChecker){
    this.variableChecker = variableChecker;
    }

    public ExpressionType checkIfExpressionIsTypeOrOperation(ASTNode astNode){
        Expression expression = (Expression) astNode;

        if(expression instanceof Operation){
            return checkOperation((Operation) expression);
        }

        return checkExpressionType(expression);
    }


    public ExpressionType checkExpressionType(Expression expression){

        if(expression instanceof Operation){
            checkOperation((Operation) expression);
        }

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

    public ExpressionType checkOperation(Operation operation){
            ExpressionType leftExpressionType = checkExpressionType(operation.lhs);
            ExpressionType rightExpressionType = checkExpressionType((operation.rhs));

            if(leftExpressionType == ExpressionType.COLOR || rightExpressionType == ExpressionType.COLOR){
                operation.setError("Operations must not include color literals.");
                return null;
            }
            return leftExpressionType;
        }

    }


