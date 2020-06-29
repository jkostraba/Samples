package com.windfall.interview;

/**
 * Class that is used to encapsulate the various elements of a mathematical expression such
 * as "+", cell ref "A2", "9.875", etc.
 * 
 * A bit of validation and classification is accomplished on creation to simplify other
 * code in the evaluation of cell expressions.
 */
public class Token {
	
	// enumeration of the possible type of tokens - this class could literally be
	// an enum, but I am not familiar enough with the format to create it without an
	// hour or so of fumbling around with syntax, so...
	public static final int UNDEFINED = 0;
	public static final int OPERAND = 1;
	public static final int OPERATOR = 2;
	public static final int CELLREF = 3;
	
	private String stringVal = "";
	private int tokenType = UNDEFINED;
	
	public Token( String tokenStr ) throws Exception {
		
		// a couple of quick checks for buggy code (forget a whitespace trim?)
		if( tokenStr==null ) throw new Exception("null Token encountered in processing (?bug?)");
		if( tokenStr.length()==0 ) throw new Exception("zero length Token encountered in processing (?bug?)");

		this.stringVal = tokenStr;
				
		// identify the token type for future evaluation (the case statement looks
		// cleaner here than in a big, complicated evaluation method)
		char firstChar = tokenStr.charAt(0);
		if((firstChar=='+') || (firstChar=='-')) {
			this.tokenType = OPERATOR;
		} else if( Character.isUpperCase(firstChar)) {
			this.tokenType = CELLREF;
		} else {
			this.tokenType = OPERAND;
		}
	}
	
	public Token( char tokenChar ) throws Exception {
		this( "" + tokenChar );
	}
	
	public int getTokenType() {
		return this.tokenType;
	}
	
	public String getTokenStr() {
		return new String( this.stringVal );
	}
}
