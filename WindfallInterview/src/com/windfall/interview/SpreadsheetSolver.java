package com.windfall.interview;

import java.util.ArrayList;

/** 
 * Class that sequences the reading of the specified spreadsheet, the evaluation of the
 * spreadsheet cell contents, and then the writing of the evaluated values back to an
 * output file (typically stdout).
 *
 */
public class SpreadsheetSolver {

	public static final int SUCCESS = 0;
	public static final int FAILED = -1;
	
	// our run() method will return 0 normally, <0 on fault
	private int returnValue = FAILED;
	
	private String inFileName = null;
	private String outFileName = null;
	
	public SpreadsheetSolver( SpreadsheetArgs args ) {
		
		this.inFileName = args.getInputFileName();
		this.outFileName = args.getOutputFileName();
	}
	
	// return 0 normally, <0 on fault
	//
	public int run() {
		
		SpreadsheetCsvFile inFile = new SpreadsheetCsvFile( this.inFileName );
		SpreadsheetCsvFile outFile = new SpreadsheetCsvFile( this.outFileName );

		ArrayList<Cell[]> spreadsheetCells = new ArrayList<Cell[]>();
		
		try {
			
			Cell[] rowOfCells = null;
			
			// read the input file
			do {
				
				rowOfCells = inFile.getRow();
				if( rowOfCells!=null && rowOfCells.length>0 ) {
					spreadsheetCells.add( rowOfCells );
				}
				
			} while( (rowOfCells!=null) && (rowOfCells.length>0));
			inFile.close();
			
			// now evaluate, row by row and then cell by cell - use an index instead of 
			// an iterator so that we have an index to report if there is a problem
			//
			for( int rowIdx=0; rowIdx<spreadsheetCells.size(); rowIdx++ ) {
				
				rowOfCells = spreadsheetCells.get(rowIdx);
				
				for( int colIdx=0; colIdx<rowOfCells.length; colIdx++ ) {
					try {
						
						rowOfCells[colIdx].evaluate( spreadsheetCells );
						
					} catch( Exception ex ) {
						
						System.out.println("Exception encountered trying to evalutate the cell at " +
					                       "column=" + colIdx + " and row=" + rowIdx );
						System.out.println(ex);
						// now re-throw the exception - processing should halt since the cell could
						// not be evaluated - it may be possible to create an implementation that
						// is more "fuzzy" and attempts to continue - if so, we would continue the
						// for loops here without re-throwing the exception
						throw ex;
						
					}
				}
			}
			
			// write the output file
			for( int idx=0; idx<spreadsheetCells.size(); idx++ ) {
				
				outFile.putRow( spreadsheetCells.get( idx ) );
			}
			outFile.close();
			
			// we made it to the end without exceptions, so...
			this.returnValue = SUCCESS;
			
		} catch( Exception ex ) {
			System.out.println("Exception: " + ex.getMessage() );
		}
		
		return this.returnValue;
	}
}
