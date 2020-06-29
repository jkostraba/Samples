package com.windfall.interview.tests;

import java.util.ArrayList;

import com.windfall.interview.Cell;

public class CaseExpressionEvaluatorWithWhitespace extends TestCase {

	public CaseExpressionEvaluatorWithWhitespace() {
		this.caseName = "Expression evaluator with whitespace";
	}
	
	public void run() {
		
		String expressionStr = "-1.00  +  6		-  3.0 +  4.0000000000 -8-2+		3           ";
		double expectedResult = -1.0;
		Cell testCell = new Cell(expressionStr);
		
		ArrayList<Cell[]> emptySpreadsheet = new ArrayList<Cell[]>();
		try { testCell.evaluate(emptySpreadsheet); } catch( Exception ignore ) {}
		testCell.getFloatVal();
		
		if( (testCell.getFloatVal() != null) && 
		    (testCell.getFloatVal() == expectedResult) ) {
			super.returnText = TestCase.OK;
		} else {
			super.returnText = TestCase.Error + this.caseName;
		}
	}
}
