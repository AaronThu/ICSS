package nl.han.ica.icss.transforms;

import nl.han.ica.datastructures.HANLinkedList;
import nl.han.ica.datastructures.IHANLinkedList;
import nl.han.ica.datastructures.SymbolTable;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.PercentageLiteral;
import nl.han.ica.icss.ast.literals.PixelLiteral;
import nl.han.ica.icss.ast.literals.ScalarLiteral;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Evaluator implements Transform {

    private SymbolTable<String, Literal> variableValues;

    public Evaluator() {
        variableValues = new SymbolTable<>();
    }

    @Override
    public void apply(AST ast) {
        transformStyleSheet(ast.root);
    }

    private void transformStyleSheet(Stylesheet stylesheet){
        ArrayList<ASTNode> nodesToRemove = new ArrayList<>();

        variableValues.push();

        for(ASTNode childNode : stylesheet.getChildren()){
            if(childNode instanceof Stylerule){
                transformStyleRule((Stylerule) childNode);
            }
            if(childNode instanceof VariableAssignment){
                transformVariableAssignment((VariableAssignment) childNode);
                nodesToRemove.add(childNode);
            }
        }
        variableValues.pop();
        nodesToRemove.forEach(stylesheet::removeChild);
    }

    private void transformVariableAssignment(VariableAssignment variableAssignment) {
        Expression expression = variableAssignment.expression;
        Literal literal = transformExpression(expression);
        variableValues.put(variableAssignment.name.name, literal);
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
    }

    private void transformDeclaration(Declaration declaration){
        declaration.expression = transformExpression(declaration.expression);
    }

    private Literal transformExpression(Expression expression) {
        if(expression instanceof VariableReference){
            return variableValues.getVariableByKey((((VariableReference) expression).name));
        }
        return (Literal) expression;
    }


}
