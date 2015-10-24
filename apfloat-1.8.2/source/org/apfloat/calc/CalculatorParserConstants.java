/* Generated By:JavaCC: Do not edit this line. CalculatorParserConstants.java */
package org.apfloat.calc;


/**
 * Token literal values and constants.
 * Generated by org.javacc.parser.OtherFilesGen#start()
 */
public interface CalculatorParserConstants {

  /** End of File. */
  int EOF = 0;
  /** RegularExpression Id. */
  int INTEGER = 3;
  /** RegularExpression Id. */
  int DECIMAL = 4;
  /** RegularExpression Id. */
  int DIGIT = 5;
  /** RegularExpression Id. */
  int IMAGINARY = 6;
  /** RegularExpression Id. */
  int IDENTIFIER = 7;
  /** RegularExpression Id. */
  int LETTER = 8;
  /** RegularExpression Id. */
  int DELIMITER = 9;
  /** RegularExpression Id. */
  int EOL = 10;
  /** RegularExpression Id. */
  int INVALID_CHAR = 21;

  /** Lexical state. */
  int DEFAULT = 0;

  /** Literal token values. */
  String[] tokenImage = {
    "<EOF>",
    "\" \"",
    "\"\\t\"",
    "<INTEGER>",
    "<DECIMAL>",
    "<DIGIT>",
    "<IMAGINARY>",
    "<IDENTIFIER>",
    "<LETTER>",
    "<DELIMITER>",
    "<EOL>",
    "\"=\"",
    "\"+\"",
    "\"-\"",
    "\"*\"",
    "\"/\"",
    "\"%\"",
    "\"^\"",
    "\"(\"",
    "\")\"",
    "\",\"",
    "<INVALID_CHAR>",
  };

}
