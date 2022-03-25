package nl.han.ica.icss.generator;


import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.ColorLiteral;
import nl.han.ica.icss.ast.literals.PixelLiteral;

import java.util.List;
import java.util.stream.Collectors;

public class Generator {

	private StringBuilder sb;

	public Generator(){
		this.sb = new StringBuilder();
	}

	public String generate(AST ast) {
        generateNode(ast.root);
		return sb.toString();
	}

	private void generateNode(ASTNode rootNode) {
		for(ASTNode childNode : rootNode.getChildren()){
			if(childNode instanceof Stylerule){
				generateSelector(childNode);
				generateDeclaration(childNode);
				sb.append("}\n");
			}
		}
	}

	private void generateDeclaration(ASTNode rootNode) {
		for(ASTNode childNode : rootNode.getChildren()){
			if(childNode instanceof Declaration){
				Declaration declaration = (Declaration) childNode;
				sb.append(" ")
						.append(declaration.property.name)
						.append(": ")
						.append(generateStringFromExpression(declaration.expression))
						.append(";\n");

			}
		}
	}

	private String generateStringFromExpression(Expression expression) {
		if(expression instanceof ColorLiteral){
			return ((ColorLiteral) expression).value +"";
		}

		if(expression instanceof PixelLiteral){
			return ((PixelLiteral) expression).value +"";
		}

		return "";
	}

	private void generateSelector(ASTNode rootNode) {
		Stylerule stylerule = (Stylerule) rootNode;

		List<String> allSelectors = stylerule.selectors.stream().map(ASTNode::toString).collect(Collectors.toList());

		String selectorString = String.join("",allSelectors);
		sb.append(selectorString);
		sb.append(" {\n");
	}


}
