
public class Interact {

	public static Sudoku getInputPuzzle() {
		Sudoku inputPuzzle = new Sudoku();
		
		int [][] puzzle_A01 =
			{{ 0, 2, 0, 0, 3, 0, 0, 4, 0},
			 { 6, 1, 0, 5, 0, 4, 0, 9, 3},
			 { 0, 0, 4, 0, 8, 0, 5, 0, 0},
			 { 0, 4, 0, 8, 0, 6, 0, 3, 0},
			 { 8, 0, 2, 0, 1, 0, 9, 0, 6},
			 { 0, 6, 0, 7, 0, 5, 0, 8, 0},
			 { 0, 0, 7, 0, 5, 0, 6, 0, 0},
			 { 4, 9, 0, 2, 0, 7, 0, 5, 8},
			 { 0, 3, 0, 0, 4, 0, 0, 2, 0}};

		int [][] puzzle_A02 = // Cannot be solved without the recursive function
			{{ 3, 0, 6, 5, 0, 8, 4, 0, 0},
			 { 5, 2, 0, 0, 0, 0, 0, 0, 0},
			 { 0, 8, 7, 0, 0, 0, 0, 3, 1},
			 { 0, 0, 3, 0, 1, 0, 0, 8, 0},
			 { 9, 0, 0, 8, 6, 3, 0, 0, 5},
			 { 0, 5, 0, 0, 9, 0, 6, 0, 0},
			 { 1, 3, 0, 0, 0, 0, 2, 5, 0},
			 { 0, 0, 0, 0, 0, 0, 0, 7, 4},
			 { 0, 0, 5, 2, 0, 6, 3, 0, 0}};

		int [][] puzzle_A03 =
			{{ 7, 0, 0, 0, 0, 0, 0, 3, 1},
			 { 1, 0, 4, 2, 9, 3, 0, 0, 0},
			 { 0, 0, 8, 0, 0, 0, 6, 2, 0},
			 { 0, 2, 0, 4, 0, 7, 0, 5, 0},
			 { 0, 4, 0, 0, 0, 0, 0, 7, 0},
			 { 0, 3, 0, 6, 0, 1, 0, 9, 0},
			 { 0, 7, 6, 0, 0, 0, 3, 0, 0},
			 { 0, 0, 0, 7, 3, 4, 8, 0, 2},
			 { 4, 8, 0, 0, 0, 0, 0, 0, 5}};

		int [][] puzzle_A04 = // Significantly improved by using the range prospects and placing
			{{ 0, 3, 0, 5, 0, 1, 0, 2, 0},
			 { 0, 0, 4, 0, 0, 0, 9, 0, 0},
			 { 0, 0, 0, 0, 0, 0, 0, 0, 0},
			 { 8, 0, 5, 0, 4, 0, 6, 0, 7},
			 { 0, 0, 0, 0, 0, 0, 0, 0, 0},
			 { 6, 0, 1, 0, 3, 0, 4, 0, 2},
			 { 0, 0, 0, 0, 0, 0, 0, 0, 0},
			 { 0, 0, 6, 0, 0, 0, 3, 0, 0},
			 { 0, 7, 0, 2, 0, 5, 0, 1, 0}};		
		
		int [][] puzzle_B01 = // Cannot be solved without the recursive function
			{{ 0, 2, 0, 0, 3, 0, 0, 4, 0},
			 { 6, 0, 0, 0, 0, 0, 0, 0, 3},
			 { 0, 0, 4, 0, 0, 0, 5, 0, 0},
			 { 0, 0, 0, 8, 0, 6, 0, 0, 0},
			 { 8, 0, 0, 0, 1, 0, 0, 0, 6},
			 { 0, 0, 0, 7, 0, 5, 0, 0, 0},
			 { 0, 0, 7, 0, 0, 0, 6, 0, 0},
			 { 4, 0, 0, 0, 0, 0, 0, 0, 8},
			 { 0, 3, 0, 0, 4, 0, 0, 2, 0}};
				
		int [][] puzzle_empty = // Cannot be solved without the recursive function
			{{ 0, 0, 0, 0, 0, 0, 0, 0, 0},
			 { 0, 0, 0, 0, 0, 0, 0, 0, 0},
			 { 0, 0, 0, 0, 0, 0, 0, 0, 0},
			 { 0, 0, 0, 0, 0, 0, 0, 0, 0},
			 { 0, 0, 0, 0, 0, 0, 0, 0, 0},
			 { 0, 0, 0, 0, 0, 0, 0, 0, 0},
			 { 0, 0, 0, 0, 0, 0, 0, 0, 0},
			 { 0, 0, 0, 0, 0, 0, 0, 0, 0},
			 { 0, 0, 0, 0, 0, 0, 0, 0, 0}};
				
		inputPuzzle.decCell = puzzle_empty;
				 
		return inputPuzzle;
	}
	
	public static void drawPuzzle(int[][] puzzle) {
		
		String[][] stringPuzzle = new String[9][9];
		
		for (int rowidx = 0; rowidx < 9; rowidx++)
			for (int colidx = 0; colidx < 9; colidx++)
				if (puzzle[rowidx][colidx] != 0)
					stringPuzzle[rowidx][colidx] = String.valueOf(puzzle[rowidx][colidx]);
						
				else
					stringPuzzle[rowidx][colidx] = " ";
		
		System.out.println(" +-------+-------+-------+");
		for (int rowidx = 0; rowidx < 9; rowidx++) {
			System.out.println( " | " + 
								stringPuzzle[rowidx][0] + " " + 
								stringPuzzle[rowidx][1] + " " + 
								stringPuzzle[rowidx][2] + " | " +
								stringPuzzle[rowidx][3] + " " + 
								stringPuzzle[rowidx][4] + " " + 
								stringPuzzle[rowidx][5] + " | " +
								stringPuzzle[rowidx][6] + " " + 
								stringPuzzle[rowidx][7] + " " + 
								stringPuzzle[rowidx][8] + " | ");
			
			if (rowidx % 3 == 2) System.out.println(" +-------+-------+-------+");
		}
		System.out.println(" ");
	}
}

/*

0 2 0 0 3 0 0 4 0
6 1 0 5 0 4 0 9 3
0 0 4 0 8 0 5 0 0
0 4 0 8 0 6 0 3 0
8 0 2 0 1 0 9 0 6
0 6 0 7 0 5 0 8 0
0 0 7 0 5 0 6 0 0
4 9 0 2 0 7 0 5 8
0 3 0 0 4 0 0 2 0

*/