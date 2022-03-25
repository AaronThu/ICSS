// Generated from C:/Users/Aaron Thunnissen/Documents/GitHub/ASD/APP/ICSS Compiler AN Thunnissen/ICSS_COMPILER/src/main/antlr4/nl/han/ica/icss/parser\ICSS.g4 by ANTLR 4.9.2
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ICSSParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface ICSSVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link ICSSParser#stylesheet}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStylesheet(ICSSParser.StylesheetContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#variables}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariables(ICSSParser.VariablesContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#variableName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableName(ICSSParser.VariableNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#selector}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelector(ICSSParser.SelectorContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#styleBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStyleBody(ICSSParser.StyleBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaration(ICSSParser.DeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#styleRule}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStyleRule(ICSSParser.StyleRuleContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#expressionType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionType(ICSSParser.ExpressionTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#classSelector}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassSelector(ICSSParser.ClassSelectorContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#idSelector}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdSelector(ICSSParser.IdSelectorContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#tagSelector}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTagSelector(ICSSParser.TagSelectorContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValue(ICSSParser.ValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#property}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProperty(ICSSParser.PropertyContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#allLiterals}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAllLiterals(ICSSParser.AllLiteralsContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#scalarLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitScalarLiteral(ICSSParser.ScalarLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#colorLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitColorLiteral(ICSSParser.ColorLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#booleanLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBooleanLiteral(ICSSParser.BooleanLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#pixelLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPixelLiteral(ICSSParser.PixelLiteralContext ctx);
}