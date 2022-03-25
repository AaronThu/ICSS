package nl.han.ica.icss.parser;

import nl.han.ica.datastructures.HANStack;
import nl.han.ica.datastructures.IHANStack;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.BoolLiteral;
import nl.han.ica.icss.ast.literals.ColorLiteral;
import nl.han.ica.icss.ast.literals.PixelLiteral;
import nl.han.ica.icss.ast.literals.ScalarLiteral;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;
import nl.han.ica.icss.ast.selectors.ClassSelector;
import nl.han.ica.icss.ast.selectors.IdSelector;
import nl.han.ica.icss.ast.selectors.TagSelector;

/**
 * This class extracts the ICSS Abstract Syntax Tree from the Antlr Parse tree.
 */
public class ASTListener extends ICSSBaseListener {

	//Accumulator attributes:
	private final AST ast;

	//Use this to keep track of the parent nodes when recursively traversing the ast
	private final IHANStack<ASTNode> currentContainer;

	public ASTListener() {
		ast = new AST();
		currentContainer = new HANStack<>();
	}

	@Override
	public void enterStylesheet(ICSSParser.StylesheetContext ctx) {
		ASTNode styleSheet = new Stylesheet();
		currentContainer.push(styleSheet);
	}

	@Override
	public void exitStylesheet(ICSSParser.StylesheetContext ctx) {
		ast.setRoot((Stylesheet) currentContainer.pop());
	}

	@Override
	public void enterStyleRule(ICSSParser.StyleRuleContext ctx) {
		ASTNode styleRule = new Stylerule();
//		System.out.println(styleRule.toString());
		currentContainer.push(styleRule);
	}

	@Override
	public void exitStyleRule(ICSSParser.StyleRuleContext ctx) {
		ASTNode styleRule = currentContainer.pop();
		currentContainer.peek().addChild(styleRule);
//		System.out.println(styleRule.toString());
	}

	@Override
	public void enterTagSelector(ICSSParser.TagSelectorContext ctx) {
		ASTNode tagSelector = new TagSelector(ctx.getText());
		currentContainer.push(tagSelector);
	}

	@Override
	public void exitTagSelector(ICSSParser.TagSelectorContext ctx) {
		ASTNode tagSelector = currentContainer.pop();
		currentContainer.peek().addChild(tagSelector);
	}

	@Override
	public void enterIdSelector(ICSSParser.IdSelectorContext ctx) {
		ASTNode idSelector = new IdSelector(ctx.getText());
		currentContainer.push(idSelector);
	}

	@Override
	public void exitIdSelector(ICSSParser.IdSelectorContext ctx) {
		ASTNode idSelector = currentContainer.pop();
		currentContainer.peek().addChild(idSelector);
	}

	@Override
	public void enterClassSelector(ICSSParser.ClassSelectorContext ctx) {
		ASTNode classSelector = new ClassSelector(ctx.getText());
		currentContainer.push(classSelector);
	}

	@Override
	public void exitClassSelector(ICSSParser.ClassSelectorContext ctx) {
		ASTNode classSelector = currentContainer.pop();
		currentContainer.peek().addChild(classSelector);
	}

	@Override
	public void enterDeclaration(ICSSParser.DeclarationContext ctx) {
		ASTNode declaration = new Declaration();
		currentContainer.push(declaration);
	}

	@Override
	public void exitDeclaration(ICSSParser.DeclarationContext ctx) {
		ASTNode declaration = currentContainer.pop();
		currentContainer.peek().addChild(declaration);
	}

	@Override
	public void enterColorLiteral(ICSSParser.ColorLiteralContext ctx) {
		ASTNode colorLiteral = new ColorLiteral(ctx.getText());
		currentContainer.push(colorLiteral);
	}

	@Override
	public void exitColorLiteral(ICSSParser.ColorLiteralContext ctx) {
		ASTNode colorLiteral = currentContainer.pop();
		currentContainer.peek().addChild(colorLiteral);
	}

	@Override
	public void enterPixelLiteral(ICSSParser.PixelLiteralContext ctx) {
		ASTNode pixelLiteral = new PixelLiteral(ctx.getText());
		currentContainer.push(pixelLiteral);
	}

	@Override
	public void exitPixelLiteral(ICSSParser.PixelLiteralContext ctx) {
		ASTNode pixelLiteral = currentContainer.pop();
		currentContainer.peek().addChild(pixelLiteral);
	}

	@Override
	public void enterProperty(ICSSParser.PropertyContext ctx) {
		ASTNode property = new PropertyName(ctx.getText());
		currentContainer.push(property);
	}

	@Override
	public void exitProperty(ICSSParser.PropertyContext ctx) {
		ASTNode property = currentContainer.pop();
		currentContainer.peek().addChild(property);
	}

	@Override
	public void enterVariables(ICSSParser.VariablesContext ctx) {
		ASTNode variable = new VariableAssignment();
		currentContainer.push(variable);
	}

	@Override
	public void exitVariables(ICSSParser.VariablesContext ctx) {
		ASTNode variable = currentContainer.pop();
		currentContainer.peek().addChild(variable);
	}

	@Override
	public void enterBooleanLiteral(ICSSParser.BooleanLiteralContext ctx) {
		ASTNode booleanLiteral = new BoolLiteral(ctx.getText());
		currentContainer.push(booleanLiteral);
	}

	@Override
	public void exitBooleanLiteral(ICSSParser.BooleanLiteralContext ctx) {
		ASTNode booleanLiteral = currentContainer.pop();
		currentContainer.peek().addChild(booleanLiteral);
	}

	@Override
	public void enterVariableName(ICSSParser.VariableNameContext ctx) {
		ASTNode variableName = new VariableReference(ctx.getText());
		currentContainer.push(variableName);
	}

	@Override
	public void exitVariableName(ICSSParser.VariableNameContext ctx) {
		ASTNode variableName = currentContainer.pop();
		currentContainer.peek().addChild(variableName);
	}

	@Override
	public void enterScalarLiteral(ICSSParser.ScalarLiteralContext ctx) {
		ASTNode scalarLiteral = new ScalarLiteral(ctx.getText());
		currentContainer.push(scalarLiteral);
	}

	@Override
	public void exitScalarLiteral(ICSSParser.ScalarLiteralContext ctx) {
		ASTNode scalarLiteral = currentContainer.pop();
		currentContainer.peek().addChild(scalarLiteral);
	}

	@Override
	public void enterExpressionType(ICSSParser.ExpressionTypeContext ctx) {
		if(ctx.getChildCount() == 3){
			Operation operation;
			switch(ctx.getChild(1).getText()){
				case "*":
					operation = new MultiplyOperation();
					break;
				case "-":
					operation = new SubtractOperation();
					break;
				default:
					operation = new AddOperation();
			}
			currentContainer.push(operation);
		}
	}

	@Override
	public void exitExpressionType(ICSSParser.ExpressionTypeContext ctx) {
		if(ctx.PLUS() != null || ctx.MIN() != null || ctx.MUL() != null){
			ASTNode operation = currentContainer.pop();
			currentContainer.peek().addChild(operation);
		}
	}

	@Override
	public void enterIfStatement(ICSSParser.IfStatementContext ctx) {
		ASTNode ifStatement = new IfClause();
			currentContainer.push(ifStatement);
	}

	@Override
	public void exitIfStatement(ICSSParser.IfStatementContext ctx) {
		ASTNode ifStatement = currentContainer.pop();
		currentContainer.peek().addChild(ifStatement);
	}

	@Override
	public void enterElseStatement(ICSSParser.ElseStatementContext ctx) {
		ASTNode elseStatement = new ElseClause();
		currentContainer.push(elseStatement);
	}

	@Override
	public void exitElseStatement(ICSSParser.ElseStatementContext ctx) {
		ASTNode elseStatement = currentContainer.pop();
		currentContainer.peek().addChild(elseStatement);
	}

	public AST getAST() {
		return ast;
	}
}


