
public interface SudokuSolver {
	/**
	 * Sets the number nbr in box r, c.
	 * 
	 * @param r
	 *            The row
	 * @param c
	 *            The column
	 * @param nbr
	 *            The number to insert in box r, c
	 * @throws IllegalArgumentException        
	 *             if r or c is outside [0..getDimension()-1] or
	 *             number is outside [1..9] 
	 */
	public void setNumber(int r, int c, int nbr);
	
	/**
	 * Returns the number in box r,c. If the box i empty 0 is returned.
	 * 
	 * @param r
	 *            The row
	 * @param c
	 *            The column
	 * @param number
	 *            The number to insert in r, c
	 * @return the number in box r,c or 0 if the box is empty.
	 * @throws IllegalArgumentException
	 *             if r or c is outside [0..getDimension()-1]
	 */
	public int getNumber(int r, int c);
	
	/**
	 * Clears the cell in row r, columns c.
	 * @param r
	 * 			The cell's row
	 * @param c
	 * 			The cell's column
	 * @throws IllegalArgumentException
	 * 			if r or c is outside [0..getDimension()-1]
	 */
	public void clearNumber(int r, int c);
	
	/**
	 * Checks if the sudoku rules allow a given number to be put in
	 * the cell (r,c).
	 * @param r
	 * 			The cell's row
	 * @param c
	 * 			The cell's column
	 * @param nbr
	 * 			The number to check against the rules
	 * @return true if it is allowed, false if it breaks the rules
	 * @throws IllegalArgumentException
	 * 			if r, c or nbr are out of bounds
	 */
	public boolean isValid(int r, int c, int nbr);

	/**
	 * Checks if the entire grid complies with the sudoku rules.
	 * Empty cells (with value 0) are allowed.
	 * @return true if the grid is valid, false if any rules are broken
	 */
	public boolean isAllValid();
		
	/**
	 * Solves the sudoku and returns true if it was successfully solved.
	 * Otherwise returns false.
	 * @return true if the sudoku is solved, false otherwise
	 */
	public boolean solve();
		
	/**
	 * Clears all the cells in the grid.
	 */
	public void clear();
		
	/**
	 * Returns the numbers in the grid. An empty box i represented
	 * by the value 0.
	 * 
	 * @return the numbers in the grid
	 */
	public int[][] getMatrix();

	/**
	 * Fills the grid with the numbers in nbrs.
	 * 
	 * @param nbrs the matrix with the numbers to insert
	 * @throws IllegalArgumentException
	 *             if nbrs have wrong dimension or containing values not in [0..9] 
	 */
	public void setMatrix(int[][] nbrs);
		
	
	/**
	 * Returns the dimension of the grid
	 * 
	 * @return the dimension of the grid
	 */
	public default int getDimension() {
		return 9;
	}

}
