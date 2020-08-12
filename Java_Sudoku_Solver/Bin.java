
public class Bin {

	public static boolean isBinaryPower(int n) {
		if (n != 0 && log2(n) % 1 == 0) return true;
		else return false;
	}

	public static double log2(double n) {
		if (n == 0) return 0;
		else return (Math.log(n)/Math.log(2));
	}

	public static int[][] dec2bin(int[][] decCell) {
		// Initialize the binary equivalence for the decCell: binCell
		int[][] binCell = new int[9][9];
		for (int rowidx = 0; rowidx < 9; rowidx++)
		    for (int colidx = 0; colidx < 9; colidx++)
		        if (decCell[rowidx][colidx] > 0)
		            // If the number is known, map it to a unique binary string
		            binCell[rowidx][colidx] = (int) Math.pow(2 ,(decCell[rowidx][colidx] - 1));
		        else
		            // If the number is missing, initiate it with 
		            // a binary string of nine 1s
		            binCell[rowidx][colidx] = 0;
		
		return binCell;
	}

	/*
	Coding pattern:
	0 -->  0       = 000000000
	1 -->  1 = 2^0 = 000000001
	2 -->  2 = 2^1 = 000000010
	3 -->  4 = 2^2 = 000000100
	4 -->  8 = 2^3 = 000001000
	5 --> 16 = 2^4 = 000010000
	6 --> 32 = 2^5 = 000100000
	7 --> 64 = 2^6 = 001000000
	8 --> 128= 2^7 = 010000000
	9 --> 256= 2^8 = 100000000
	*/

	public static int[][] bin2dec(int[][] binCell) {

		int[][] decCell = new int[9][9]; 
		
		for (int row = 0; row < 9; row++)
		    for (int col = 0; col < 9; col++)
		        // If the binary value was anything other than a product of 2.
		        // (i.e. if the matrix cell contained an array of possible entries 
		        // and not a distinct value, as coded in this solution.)
		    	if (binCell[row][col] > 0)
		        	decCell[row][col] = (int) Bin.log2(binCell[row][col]) + 1;
		        else
		            decCell[row][col] = 0;

		return decCell; 
	}
/*
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
*/
}
