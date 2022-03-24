grammar ICSS;
//--- LEXER: ---

// IF support:
IF: 'if';
ELSE: 'else';
BOX_BRACKET_OPEN: '[';
BOX_BRACKET_CLOSE: ']';


//Literals
TRUE: 'TRUE';
FALSE: 'FALSE';
PIXELSIZE: [0-9]+ 'px';
PERCENTAGE: [0-9]+ '%';
SCALAR: [0-9]+;


//Color value takes precedence over id idents
COLOR: '#' [0-9a-f] [0-9a-f] [0-9a-f] [0-9a-f] [0-9a-f] [0-9a-f];

//Specific identifiers for id's and css classes
ID_IDENT: '#' [a-z0-9\-]+;
CLASS_IDENT: '.' [a-z0-9\-]+;

//General identifiers
LOWER_IDENT: [a-z] [a-z0-9\-]*;
CAPITAL_IDENT: [A-Z] [A-Za-z0-9_]*;

//All whitespace is skipped
WS: [ \t\r\n]+ -> skip;

//
OPEN_BRACE: '{';
CLOSE_BRACE: '}';
SEMICOLON: ';';
COLON: ':';
PLUS: '+';
MIN: '-';
MUL: '*';
ASSIGNMENT_OPERATOR: ':=';

//--- PARSER: ---


//STYLESHEET
stylesheet:  variables* styleRule* EOF;

//VARIABLES
variables: variableName ASSIGNMENT_OPERATOR value+ SEMICOLON;
variableName: CAPITAL_IDENT;
//STYLEBODY
selector: classSelector | idSelector | tagSelector;// voorbeeld: p {
styleBody: declaration+; // gehele body
declaration: property COLON expressionType+ SEMICOLON | ifStatement+; //declaratie vb: background-color: #ffffff;

//STYLERULE
styleRule: selector OPEN_BRACE styleBody CLOSE_BRACE; // de opsomming van alle componenten in de stylebody

//EXPRESSION
expressionType: value | expressionType (MUL) expressionType | expressionType (PLUS | MIN) expressionType ;

//IF/ELSE
ifStatement: IF BOX_BRACKET_OPEN variableName BOX_BRACKET_CLOSE OPEN_BRACE declaration+ CLOSE_BRACE elseStatement*;
elseStatement: ELSE OPEN_BRACE declaration+ CLOSE_BRACE;
//SELECTORS
classSelector: CLASS_IDENT;
idSelector: ID_IDENT;
tagSelector: LOWER_IDENT;

//OTHER
value: allLiterals | variableName;  // de waarde die tot de property behoort
property: LOWER_IDENT; // de property die een waarde krijgt vb: background-color:

//LITERALS
allLiterals: colorLiteral | pixelLiteral | booleanLiteral | scalarLiteral | variableName; // alle literals die value een waarde geven
scalarLiteral: SCALAR;
colorLiteral: COLOR;
booleanLiteral: TRUE | FALSE;
pixelLiteral: PIXELSIZE;

