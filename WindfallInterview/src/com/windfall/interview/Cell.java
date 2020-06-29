package com.windfall.interview;

import java.util.ArrayList;
import java.util.Stack;

/** 
 * This class encapsulates the contents of one cell of a spreadsheet.  The cell 
 * contents are ingested/created as a string, then a decimal value is ultimately 
 * desired from evaluation activity, so a caller invoking getFloatValue() will be 
 * the goal of Cell processing.
 */
public class Cell {

	private String stringVal = null;
	private Double floatVal = null;
	
	private boolean isEvaluating = false;
	private boolean evaluated = false;

	
	public Cell( String cellString ) {
		this.stringVal = cellString;
	}
	
	public String getStringVal() {
		return new String( this.stringVal );
	}
	
	public Double getFloatVal() {
		return new Double( this.floatVal );
	}
	
	// here we go - the fun part - evaluation of the value of an individual cell
	//
	// this call is recursive, so cell references that are included in the 
	// cell value will be invoked with .evaluate().  Detection of recursion violation
	// is accomplished with the ".isEvaluating" boolean; if we invoke .evaluate() on 
	// a cell where .isEvaluating is already true, we know that we have a circular
	// reference that is can not be evaluated and we throw an exception to unwind
	// and exit with a descriptive error message.
	//
	public void evaluate(ArrayList<Cell[]> spreadsheetCells)  throws Exception {
		
		if( ! this.evaluated ) {
			
			if( this.isEvaluating ) {
				
				throw new Exception( "Circular reference found evaluating cell value " + this.stringVal);
			
			} else {
				
				this.isEvaluating = true;
			
				// tokenize the expression
				Stack<Token> tokens = this.tokenize();
				
				// walk the tokens to evaluate to decimal number
				this.floatVal = this.walkTokens(tokens, spreadsheetCells);
				
				this.isEvaluating = false;
			}
		}
	
		this.evaluated = true;
	}

	private Stack<Token> tokenize() throws Exception {
		
		Stack<Token> stack = new Stack<Token>();
		String operand = new String();
		
		// We will walk the characters of the cell contents and assemble a series
		// of mathmatical operations in a stack.
		
		// The test specifies only + and - operations and does not refer to
		// any nesting with '(' characters, so the tokenization is very straightforward
		
		// The only "cutesie" part of this activity is the addition of an initial
		// "+" operation before tokenization when the first operand is not negative.  
		// This is effectively adding a "0 + " or "0 - " operation at the beginning 
		// depending on whether the first operand is negative or not.  The allows 
		// exactly consistent handling of the tokens when walking the stack later.
		
		if( this.stringVal.charAt(0) != '-' ) {
			stack.push( new Token('+'));
		}
		
		for( char current : this.stringVal.toCharArray()) {
			
			// when an operator is encountered, push any ongoing operand and refresh it
			if( Character.isWhitespace(current)) {
				
				// do nothing with whitespace - effectively eating any whitespace out
				// of the input stream
				
			} else if( current=='-' ) {
				
				if( operand.length() > 0 ) {
					stack.push( new Token(operand) );
					operand = new String();
				}
				stack.push( new Token(current) );
				
			} else if( current=='+') {
				
				if( operand.length() > 0 ) {
					stack.push( new Token(operand) );
					operand = new String();
				}
				stack.push( new Token(current) );
				
		    // when not an operator, add the character to the ongoing operand
			} else {
				
				operand += current;
				
			}
		}
		
		if( operand.length() > 0 ) {
			stack.push( new Token(operand) );
		}
		
		return stack;
	}
	
	private Double walkTokens(Stack<Token> tokens, ArrayList<Cell[]> spreadsheetCells) throws Exception {
		
		Double result = new Double(0);
		Double lastOperand = new Double(0);
		
		while( ! tokens.isEmpty() ) {
			
			Token current = tokens.pop();
			
			// If our token is a cell reference, then recursively call Cell.evalutate()
			if( current.getTokenType() == Token.CELLREF ) {
				
				Cell currentCell = this.mapCellrefToCell( current.getTokenStr(), spreadsheetCells);
				currentCell.evaluate(spreadsheetCells);
				lastOperand = currentCell.getFloatVal();
				
		    // If our token is a number, then make reference to it in the local operand var
			} else if( current.getTokenType() == Token.OPERAND ) {
				
				lastOperand = Double.valueOf( current.getTokenStr() );
				
		    // If our token is an operator, then perform the requested operation - note that
		    // this is where an initial negative number (or an initial positive number) in the
		    // expression is handled by summing it in to the result like any other operation
			} else if( current.getTokenType() == Token.OPERATOR) {
				
				switch( current.getTokenStr().charAt(0) ) {
					case '-' : 
						result -= lastOperand;
						lastOperand = new Double(0);
						break;
					case '+' :
						result += lastOperand;
						lastOperand = new Double(0);
						break;
					default:
						// this default suggests that the operator could be an enum to
						// simplify this code and eliminate this exception
				}
			} else {
				throw new Exception("Unhandled token type case in evaluate->walkTokens() - ?bug?");
			}
		}
		
		return result;
	}
	
	// Convert a cell reference like "A1" or "C11" into indexes and use the indexes to 
	// return the specified cell
	//
	private Cell mapCellrefToCell( String cellref, ArrayList<Cell[]> spreadsheetCells ) throws Exception {
		
		char columnLetter = cellref.charAt(0);
		int columnIndex = (int)columnLetter - (int)'A';
		
		// note the "-1" converting from a cell reference to an index -> spreadsheets use A1 as the first cell
		String rowString = cellref.substring(1);
		int rowIndex = Integer.parseInt(rowString) - 1;
		
		if( rowIndex >= spreadsheetCells.size() ) {
			throw new Exception( "Cell reference to row that is not in the spreadsheet; cellref is " + cellref);
		}
		Cell[] rowOfCells = spreadsheetCells.get(rowIndex);
		
		if( columnIndex >= rowOfCells.length ) {
			throw new Exception( "Cell reference to column that is not in the spreadsheet; cellref is " + cellref);
		}
		Cell returnValue = rowOfCells[columnIndex];
		
		return returnValue;
	}
}
