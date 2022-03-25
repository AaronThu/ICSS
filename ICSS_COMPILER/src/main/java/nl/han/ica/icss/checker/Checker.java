package nl.han.ica.icss.checker;

import nl.han.ica.datastructures.ScopeChecker;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.types.ExpressionType;

import java.util.ArrayList;

public class Checker {

//    private IHANLinkedList<HashMap<String, ExpressionType>> variableTypes;
        private ScopeChecker<String, ExpressionType> variableTypes;
        private CheckerExpression expressionChecker;
        private CheckerVariable variableChecker;

        public Checker(){
            this.variableTypes = new ScopeChecker<>();
            this.variableChecker = new CheckerVariable(variableTypes);
            this.expressionChecker = new CheckerExpression(variableChecker);
        }

    public void check(AST ast) {
        checkEntireStyleSheet(ast.root);
    }

    private void checkEntireStyleSheet(ASTNode astNode){
        Stylesheet stylesheet = (Stylesheet) astNode;
        variableTypes.push();

        for (ASTNode childNode : stylesheet.getChildren()){
            if(childNode instanceof VariableAssignment){
                variableChecker.checkVariableAssignment(childNode);
            }
            if(childNode instanceof Stylerule){
                variableTypes.push();
                checkStyleRule(childNode);
                variableTypes.pop();
            }
        }
        variableTypes.pop();
    }



    private void checkStyleRule(ASTNode astNode) {
        Stylerule styleRule = (Stylerule) astNode;
        checkStyleBody(styleRule.body);
    }

    private void checkStyleBody(ArrayList<ASTNode> body) {
        for(ASTNode bodyChildNode : body){
            if(bodyChildNode instanceof Declaration){
                checkDeclaration(bodyChildNode);
            }
            if(bodyChildNode instanceof VariableAssignment){
                variableChecker.checkVariableAssignment(bodyChildNode);
            }
        }
    }

    private void checkDeclaration(ASTNode astNode) {
        Declaration declaration = (Declaration) astNode;
        String propertyName = declaration.property.name;
        ExpressionType expression = expressionChecker.checkExpressionType(declaration.expression);
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




    private ExpressionType checkOperation(Expression expression) {
        return null;
    }

}
    

