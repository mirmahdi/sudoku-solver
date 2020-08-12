
public class Main {

	public static void main (String[] args) {

		// Import and draw a Sudoku puzzle
		//puzzleInput = importdata('blank.m');

		Sudoku puzzle = Interact.getInputPuzzle();
		Interact.drawPuzzle(puzzle.decCell);
		
		// Initialize the binary arrays corresponding puzzle cells
		puzzle.binCell = Bin.dec2bin(puzzle.decCell);
		
		do { // Start solving the puzzle from conventional ways
		    puzzle.iteration++; // Track of the number of iterations
		    //puzzle.madeProgress = false;

		    // Identify and place unique possible values; the most obvious technique
		    puzzle.findAllCellProspects();
		    puzzle.placeCellUniques();

			// Identify and place unique possible values that are hidden from the previous technique
			puzzle.findRangeProspects();
			puzzle.placeRangeUniques();
		    
			// To add: Use mutually exclusive possible values to find new unique cases

		} while (puzzle.madeProgress);
		
		// If the previous techniques did not yield a complete solution, then try solving recursively 
		if (!Sudoku.isValid(puzzle.binCell))
			puzzle.placeCasesRecursively(puzzle.binCell, 0, 0);

		puzzle.decCell = Bin.bin2dec(puzzle.binCell);
		System.out.println("Iterations: " + puzzle.iteration);
		Interact.drawPuzzle(puzzle.decCell);
	}
}
