public class Sudoku {
	int[][] decCell	      = new int[9][9],
			binCell       = new int[9][9],
			binCandidVals = new int[9][9];

	int[] 	rowCandidVals = new int[9],
	    	colCandidVals = new int[9],
	    	boxCandidVals = new int[9];
	
	int	 	iteration = 0;
	boolean madeProgress = false;

	public static boolean isValid(int[][] binCell) {
		for (int i = 1; i < 9; i++) {
			int sumRow = 0, sumCol = 0;
			for (int j = 0; j < 9; j++) {
				sumRow += binCell[i][j];
				sumCol += binCell[j][i];
			}
		    if (sumRow != 511 || sumCol != 511)
		    	return false;
		}

		return true;
	}

	public static int findCellProspects(int[][] binCell, int ref_rowidx, int ref_colidx) {	/**
		Compute all potential values of a referenced empty cell - '0' if already filled
		Output: casesSequence - A binary array (sequence) of possible values **/
		
	    int   rowValues = 0, 
	    	  colValues = 0, 
	    	  boxValues = 0, 
	      casesSequence = 0;
	    
	    if (binCell[ref_rowidx][ref_colidx] == 0) {

	        // Calculate and assign row and columns values
	        for (int idx = 0; idx < 9; idx++) {
	        	rowValues += binCell[ref_rowidx][idx];
	        	colValues += binCell[idx][ref_colidx];
	        }
	        // Calculate and assign box values
	        int box_row_min = (int) Math.floor(ref_rowidx / 3) * 3;
	        int box_col_min = (int) Math.floor(ref_colidx / 3) * 3;
	        for (int rowidx = box_row_min; rowidx < box_row_min + 3; rowidx++)
	            for (int colidx = box_col_min; colidx < box_col_min + 3; colidx++)
	            	boxValues += binCell[rowidx][colidx];

	        // Then, apply all the constraints (row, column, and box) collectively using binary logic
	        casesSequence = 511 - (rowValues | colValues | boxValues);        

	    } else // If the value is already known, there are no alternatives
	        casesSequence = 0;
		
	    return casesSequence;
	}

	public void findAllCellProspects() { /**
	    AN ALTERNATIVE IMPLEMENTATION WOULD UES THE findCellProspects() ABOVE IN TWO FOR LOOPS...
	    (Fairly easier, probably more overhead)
	    
	    Compute all potential values for the entire puzzle, base on collective
	    constraints imposed on each cell, from its row, its column, and its box.
	    Output: A binary coded 2D array of integers, containing possible values per cell.

		Box logic:    1  2  3
	                  4  5  6
	                  7  8  9 **/
		
		int[]	row = new int[9],
	    		col = new int[9],
	    		box = new int[9];

	    int  rowidx = 0, 
	    	 colidx = 0, 
	    	 boxidx = 0;
		
	    for (rowidx = 0; rowidx < 9; rowidx++)
	        for (colidx = 0; colidx < 9; colidx++)
	        	if (binCell[rowidx][colidx] != 0) { // Skip if the value is already known

	        		// Compute the box index, based on row and column indices
		            boxidx = (int) (3 * Math.floor(rowidx / 3) + Math.ceil(colidx / 3));
	
	                // Collect constraints from row, column, and box and store them using binary logic (bitwise OR)
	                row[rowidx] |= binCell[rowidx][colidx];
	                col[colidx] |= binCell[rowidx][colidx];
	                box[boxidx] |= binCell[rowidx][colidx];
	            }

        //int[][] binCandidVals = new int[9][9];
        
	    for (rowidx = 0; rowidx < 9; rowidx++) {
	        for (colidx = 0; colidx < 9; colidx++)
	        	if (binCell[rowidx][colidx] == 0) { // Do this for unknown values

	        		// Calculate the appropriate box index based on the row index and column index 
	        		boxidx = (int) (3 * Math.floor(rowidx / 3) + Math.ceil(colidx / 3));

	        		// The "511 - ..." arithmetic makes the number representations reverse (binary logic)
		        	binCandidVals[rowidx][colidx] = 511 - (row[rowidx] | col[colidx] | box[boxidx]);

	        	} else // If the value is already known, assign zero to the candidVal sequence
	        		binCandidVals[rowidx][colidx] = 0;
	        /**
	        System.out.println( Integer.toBinaryString(binCandidVals[rowidx][0]) + " " + 
	        					Integer.toBinaryString(binCandidVals[rowidx][1]) + " " + 
	        					Integer.toBinaryString(binCandidVals[rowidx][2]) + " " + 
	        					Integer.toBinaryString(binCandidVals[rowidx][3]) + " " + 
	        					Integer.toBinaryString(binCandidVals[rowidx][4]) + " " + 
	        					Integer.toBinaryString(binCandidVals[rowidx][5]) + " " +
	        					Integer.toBinaryString(binCandidVals[rowidx][6]) + " " + 
	        					Integer.toBinaryString(binCandidVals[rowidx][7]) + " " + 
	        					Integer.toBinaryString(binCandidVals[rowidx][8]));
			**/
	    }
        //System.out.println(" ");
        
        //return binCandidVals;
	}
	
    // To be added
    // Function to identify and eliminate mutually exclusive pairs from
    // other unknown cells of each row, column, and box
    //
	
	public void placeCellUniques() { /**
		Place unique candid values (if any) for empty cells **/  

	    /** THIS PIECE OF CODE IS AN ALTERNATIVE IMPLEMENTATION OF THIS FUNCTION
	     *  THAT USES RangeProspects to compute CellProspects (Makes use of some of already computed logic)
	     *  (MIGHT BE USEFUL IN THE FUTURE)
	     * 
		for (int rowidx = 0; rowidx  < 9; rowidx ++) {
		    for (int colidx = 0; colidx  < 9; colidx ++) {
		    	// Find which box the cell resides in
		    	int boxidx = (int) (3 * Math.floor(rowidx / 3) + Math.ceil(colidx / 3));
            	
		    	// Find a collective prospectus sequence on the cell's row, column, and box altogether
		    	int cellCollectiveProspects = (rowCandidVals[rowidx] & colCandidVals[colidx] & boxCandidVals[boxidx]);
            	
		    	// If this collective prospectus sequence indicates only one possibility, assign that to the cell
		    	if (Bin.isBinaryPower(cellCollectiveProspects)) {
            		binCell[rowidx][colidx] = cellCollectiveProspects;
            		madeProgress = true;
		    	}
		    }
	    }**/
		
	    //int[][] binCandidVals = new int[9][9]; 
	    //binCandidVals = computeProspects(binCell);

	    madeProgress = false;

	    for (int rowidx = 0; rowidx < 9; rowidx++)
	        for (int colidx = 0; colidx < 9; colidx++)
	        	if (binCell[rowidx][colidx] == 0 && Bin.isBinaryPower(binCandidVals[rowidx][colidx])) {
	                // If the actual cell value is unknown AND there is only one possible candid value, assign it
	                binCell[rowidx][colidx] = binCandidVals[rowidx][colidx];
	                // binCandidVals[rowidx][colidx] = 0; !! THIS MAKES AN ERROR
	                madeProgress = true;
	            }

	    //return binCell;
	}
	
	public void findRangeProspects() { /**
		Calculate candid values per row, column, and box **/
	    // Compute a string that represents already known numbers in the rows and columns, and then boxes
	    for (int i = 0; i < 9; i++) {
	        for (int j = 0; j < 9; j++) {
	        	rowCandidVals[i] |= binCandidVals[i][j]; // Bitwise OR
	        	colCandidVals[i] |= binCandidVals[j][i]; // Bitwise OR
	        }

			// Aim of mapping: 0:2 --> 0; 3:5 --> 3; 6:8 --> 6
	        int boxrowidx = ((int) (i / 3)) * 3;
			
			// Aim of mapping: 0,3,6 --> 0; 1,4,7 --> 3; 2,5,8 --> 6
	        int boxcolidx = (i % 3) * 3;

	        for (int j = boxrowidx; j < boxrowidx + 3; j++)
	            for (int k = boxcolidx; k < boxcolidx + 3; k++)
	            	boxCandidVals[i] |= binCandidVals[j][k]; // Bitwise OR
	    }		
	}

	public void placeRangeUniques() { /**
		Find if within a row, column, or box, there is a value that is only permissible
		in one and only one of the cells of that row, column, or box **/

		madeProgress = false;

	    for (int i = 0; i < 9; i++) {
	    	// Check the solely available candid place for each cell in the row i 
	    	for (int j = 0; j < 9; j++) {
	        	int bitwiseAnd = rowCandidVals[i] & binCandidVals[i][j];
	            if (binCandidVals[i][j] != 0 && Bin.isBinaryPower(bitwiseAnd)) {
	                      binCell[i][j] = bitwiseAnd;
	                binCandidVals[i][j] = 0;
	                rowCandidVals[i]   -= bitwiseAnd;
	                madeProgress = true;
	                j = -1;
	                //break;
	            }
	        }

	    	// Check the solely available candid place for each cell in the column i 
	        for (int j = 0; j < 9; j++) {
	        	int bitwiseAnd = colCandidVals[i] & binCandidVals[j][i];
	            if (binCandidVals[j][i] != 0 && Bin.isBinaryPower(bitwiseAnd)) {
	                      binCell[j][i]  = bitwiseAnd;
	                binCandidVals[j][i]  = 0;
	                colCandidVals[i]    -= bitwiseAnd;
	                madeProgress = true;
	                j = -1;
	                //break;
	            }
	        }

	        // Check the solely available candid place for each cell in the box i 
            int boxrowidx = (int) Math.floor(i / 3) * 3;
            int boxcolidx = (i % 3) * 3;
            for (int j = boxrowidx; j < boxrowidx + 3; j++) {
                for (int k = boxcolidx; k < boxcolidx + 3; k++) {
    	        	int bitwiseAnd = boxCandidVals[i] & binCandidVals[j][k];
    	            if (binCandidVals[j][k] != 0 && Bin.isBinaryPower(bitwiseAnd)) {
		                      binCell[j][k]  = bitwiseAnd;
		                binCandidVals[j][k]  = 0;
		                boxCandidVals[i]    -= bitwiseAnd;
		                madeProgress = true;
		                j = -1;
		                break;
    	            }
	            }
            }
	    }
	}

	public static int[][] placeCasesRecursively(int[][] input_binPuzzle, int rowidx, int colidx) {
		
	    // Find the next unknown cell after or on the i and j indices
	    while (input_binPuzzle[rowidx][colidx] != 0 && (rowidx != 8 || colidx != 8)) {
	        if (colidx == 8) {
	        	rowidx += 1;
	        	colidx = 0;
	        } else
	        	colidx += 1;
	    }
	    
	    int cellCases = findCellProspects(input_binPuzzle, rowidx, colidx);
	    int currentCasePwr = 0;

	    while (cellCases > 0) { // If the cell does not have a value
	        while (cellCases % 2 == 0) { // Get the first '1' in the binary sequence of prospects
	            cellCases = cellCases >> 1; // Right shift; similar to division by 2 in binary logic
	            currentCasePwr += 1;
	        }

	        input_binPuzzle[rowidx][colidx] = (int) Math.pow(2, currentCasePwr);

	        // Call this function recursively with the additional cell filled to be tested...
	        input_binPuzzle = placeCasesRecursively(input_binPuzzle, rowidx, colidx);

	        /** This line is sought only after the recursive call above is returned **/

	        if (isValid(input_binPuzzle)) { // In case a solution was found
	            break;
	        } else { // If the returned sudoku is invalid
	        	// Reset the cell value, get ready for another iteration
	        	input_binPuzzle[rowidx][colidx] = 0;
	            // Remove the unsuccessful digit from candidVals
	            cellCases -= 1;
	        }
	    }
	    //iteration += 1;

	    return input_binPuzzle;
	}
}
