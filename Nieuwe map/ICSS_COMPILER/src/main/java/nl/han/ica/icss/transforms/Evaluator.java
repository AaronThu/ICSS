package nl.han.ica.icss.transforms;

import nl.han.ica.datastructures.ScopeMap;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.BoolLiteral;
import nl.han.ica.icss.ast.literals.PercentageLiteral;
import nl.han.ica.icss.ast.literals.PixelLiteral;
import nl.han.ica.icss.ast.literals.ScalarLiteral;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;

import java.util.ArrayList;

public class Evaluator implements Transform {

    private ScopeMap<String, Literal> variableValues;

    public Evaluator() {
        variableValues = new ScopeMap<>();
    }

    @Override
    public void apply(AST ast) {
        transformStyleSheet(ast.root);
    }

    private void transformStyleSheet(Stylesheet stylesheet){
        ArrayList<ASTNode> nodesToRemove = new ArrayList<>();

        variableValues.push();

        for(ASTNode childNode : stylesheet.getChildren()){
            if(childNode instanceof VariableAssignment){
                transformVariableAssignment((VariableAssignment) childNode);
                nodesToRemove.add(childNode);
            }
            if(childNode instanceof Stylerule){
                transformStyleRule((Stylerule) childNode);
            }
        }
        variableValues.pop();
        nodesToRemove.forEach(stylesheet::removeChild);
    }

    private void transformStyleRule(Stylerule stylerule){
        ArrayList<ASTNode> astNodesToAdd = new ArrayList<>();
        variableValues.push();

        for(ASTNode childNode: stylerule.body){
            transformStyleBody(childNode, astNodesToAdd);
        }

        variableValues.pop();
        stylerule.body = astNodesToAdd;
    }

    private void transformStyleBody(ASTNode astNode, ArrayList<ASTNode> parent) {
        if(astNode instanceof Declaration){
            transformDeclaration((Declaration) astNode);
            parent.add(astNode);
        }
        if(astNode instanceof VariableAssignment){
            transformVariableAssignment((VariableAssignment) astNode);
        }

        if(astNode instanceof IfClause){
            IfClause ifClause = (IfClause) astNode;
            ifClause.conditionalExpression = transformExpression(ifClause.getConditionalExpression());

            if(((BoolLiteral) ifClause.conditionalExpression).value) {
                if (ifClause.getElseClause() != null) {
                    ifClause.body = new ArrayList<>();
                }
            }else{
                    ifClause.body = ifClause.getElseClause().body;
                    ifClause.getElseClause().body = new ArrayList<>();
                }

            transformIfClause((IfClause) astNode, parent);
            }
        }

    private void transformIfClause(IfClause ifClause, ArrayList<ASTNode> parent) {
        for(ASTNode childNode : ifClause.getChildren()){
            transformStyleBody(childNode, parent);
        }
    }

    private void transformVariableAssignment(VariableAssignment variableAssignment) {
        Expression expression = variableAssignment.expression;
        Literal literal = transformExpression(expression);
        variableValues.put(variableAssignment.name.name, literal);
    }

    private void transformDeclaration(Declaration declaration){
        declaration.expression = transformExpression(declaration.expression);
    }

    private Literal transformExpression(Expression expression) {
        if(expression instanceof VariableReference){
            return variableValues.getVariableByKey(((VariableReference) expression).name);
        }

        if(expression instanceof Operation){
            return transformOperationToLiteral((Operation) expression);
        }

        return (Literal) expression;
    }

    private Literal transformOperationToLiteral(Operation operation){
        Literal r;
        Literal l;

        int valueRight;
        int valueLeft;

        if(operation.lhs instanceof Operation){
            l = transformOperationToLiteral((Operation) operation.lhs);
        } else if( operation.lhs instanceof VariableReference){
            l = variableValues.getVariableByKey(((VariableReference) operation.lhs).name);
        } else{
            l = (Literal) operation.lhs;
        }
        if(operation.rhs instanceof Operation){
            r = transformOperationToLiteral((Operation) operation.rhs);
        } else if( operation.rhs instanceof VariableReference){
            r = variableValues.getVariableByKey(((VariableReference) operation.rhs).name);
        } else{
            r = (Literal) operation.rhs;
        }

        valueRight = getLiteralValue(r);
        valueLeft = getLiteralValue(l);

        if(operation instanceof MultiplyOperation){
            if(r instanceof ScalarLiteral){
                return calculatedLiteral(l, valueLeft * valueRight);
            } else{
                return calculatedLiteral(r, valueLeft * valueRight);
            }
            }else if(operation instanceof AddOperation){
                return calculatedLiteral(l, valueLeft + valueRight);
        } else if(operation instanceof SubtractOperation){
            return calculatedLiteral(l, valueLeft - valueRight);
        } else{
            return null;
        }

    }

    private Literal calculatedLiteral(Literal literal, int value) {
        if(literal instanceof ScalarLiteral){
            return new ScalarLiteral(value);
        } else if(literal instanceof PixelLiteral){
            return new PixelLiteral(value);
        } else{
            return new PercentageLiteral(value);
        }
    }

    private int getLiteralValue(Literal literal) {
        if(literal instanceof ScalarLiteral){
            return ((ScalarLiteral) literal).value;
        }
       else if(literal instanceof PixelLiteral){
            return ((PixelLiteral) literal).value;
        } else{
           return ((PercentageLiteral)literal).value;
        }
    }

}
