package com.windfall.interview;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * This class reads and writes CSV files.
 * 
 * The input and output of the class are arrays of Cell objects.  
 * 
 * When reading, the Cells get stuffed with the text that exists between CSV
 * file comma separators without any evaluation or processing.
 * 
 * Similarly, when writing, the Cell object reported text is written to the 
 * CSV file, with comma separators, but no expression evaluation.
 *
 */
public class SpreadsheetCsvFile {
	
	private File file = null;
	
	private boolean reading = false;
	private BufferedReader br = null;
	
	private boolean writing = false;
	private PrintStream printStream = null;

	private int columnCount = 0;
	
	// On construction, create the enclose File object, but
	// leave it NULL if no filename is specified (may be an indication
	// to use STDOUT)
	//
	public SpreadsheetCsvFile( String fileName ) {
		
		if((fileName!=null) && (fileName.length() > 0)) {
			this.file = new File( fileName );
		}
	}
	
	// private method invoked once per file read to validate the presence and accessibility
	// of the specified file.
	//
	private void checkReadableCsv() throws Exception {
		
		if( ! this.file.exists() ) {
			throw new Exception( "The specified file is not found" );
		}
		
		if( ! this.file.isFile() ) {
			throw new Exception( "The specified file name does not refer to a file (its a dir?)" );
		}
		
		this.br = new BufferedReader( new FileReader( this.file ));
		this.reading = true;
	}
	
	// private method invoked once per file write to validate the ability to create a 
	// writeable file at the specified path
	//
	private void checkWriteableCsv() throws Exception {
		
		// if not file was specified, then use STDOUT
		if( this.file == null ) {
			
			this.printStream = System.out;
			
		} else {
			
			if( ! this.file.createNewFile() ) {
				throw new Exception( "The specified file could not be created" );
			}
			
			this.printStream = new PrintStream( this.file );
		}
		
		this.writing = true;
	}
	
	// key public method that reads a line of the CSV into a row (array) of Cell objects
	//
	public Cell[] getRow() throws Exception {
		
		if( !reading ) {
			this.checkReadableCsv();
		}
		
		ArrayList<Cell> rowOfCells = new ArrayList<Cell>();

		try {
			String line = br.readLine();
		
			if( line!=null ) {
				
				// Split the line into individual cells using the comma delimiter
				String[] commaSeparatedCells = line.split(",");
				
				// validate the number of cells per row (column count has to be consistent)
				if( this.columnCount==0 ) {
					this.columnCount = commaSeparatedCells.length;
				} else if( this.columnCount != commaSeparatedCells.length ) {
						throw new Exception( "Inconsisted column count in CSV input file" );
				}
				
				// create Cell objects for each of the spreadsheet cells
				for( String rawCellStr : commaSeparatedCells ) {
					Cell cell = new Cell( rawCellStr );
					rowOfCells.add( cell );
				}
			}
			
		} catch( IOException ioex ) {
			System.out.println("Unhandled IO Exception in file reading: " + ioex);
		}
		
		Cell[] cells = new Cell[0];
		cells = rowOfCells.toArray(cells);
		return cells;
	}
	
	
	// key public method that writes a row (array) of Cell objects to the specified output file
	//
	public void putRow( Cell[] cells ) throws Exception {
		
		if( ! this.writing ) {
			this.checkWriteableCsv();
		}
		
		String line = "";	
		for( int idx=0; idx<cells.length; idx++ ) {
			
			Double cellValue = cells[idx].getFloatVal();
			DecimalFormat df = new DecimalFormat("0.00");
			line += df.format(cellValue);
			if( idx<(cells.length-1) ) line += ',';
			
		}
		
		this.printStream.println( line );
	}
	
	// a basic close and flush kinda thing that is used to be careful about
	// buffer flushes - especially important when running tests on streams that
	// won't get flushed because our App doesn't exit between test cases - between
	// consecutive runs of our application
	//
	public void close() {
		
		try {
			
			if( this.reading ) {
				this.br.close();
				this.br = null;
				this.reading = false;
			}
			
			if( this.writing )  {
				this.printStream.flush();
				this.printStream.close();
				this.printStream = null;
				this.writing = false;
			}
			
		} catch( Exception ignore ) {
			// we are ignore exceptions on close
		}
	}
}
