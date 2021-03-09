

public class SudokuBacktracker implements SudokuSolver {
	private int[][] numbers;
	private int sqDim;
	private int size;
	
	public SudokuBacktracker(int size) {
		if (size < 0) {
			size *= -1;
		}
		double sqrt = Math.sqrt(size);
		sqDim = (int) sqrt;
		this.size = sqDim * sqDim;
		numbers = new int[size][size];
	}
	
	
	@Override
	public void setNumber(int r, int c, int nbr) {
		if (nbr < 1 || nbr > size) {
			throw new IllegalArgumentException();
		}
		try {
			numbers[r][c] = nbr;
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public int getNumber(int r, int c) {
		try {
			return numbers[r][c];
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public void clearNumber(int r, int c) {
		try {
			numbers[r][c] = 0;
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public boolean isValid(int r, int c, int nbr) {
		if (r < 0 || c < 0 || r >= size || c >= size || nbr < 0 || nbr > size) {
			throw new IllegalArgumentException();
		}
		if (nbr == 0) return true;
		// Kolla rad
		for (int col = 0; col < size; col++) {
			if (nbr == numbers[r][col] && col != c) return false;
		}
		// Kolla kolumn
		for (int row = 0; row < size; row++) {
			if (nbr == numbers[row][c] && row != r) return false;
		}
		// Kolla stor ruta
		for (int row = (r / sqDim) * sqDim; row < (r / sqDim + 1) * sqDim; row++) {
			for (int col = (c / sqDim) * sqDim; col < (c / sqDim + 1) * sqDim; col++) {
				if (nbr == numbers[row][col] && row != r && col != c) return false;
			}
		}
		return true;
	}

	@Override
	public boolean isAllValid() {
		for (int r = 0; r < size; r++) {
			for (int c = 0; c < size; c++) {
				if (!isValid(r, c, numbers[r][c])) return false;
			}
		}
		return true;
	}
	
	@Override
	public boolean solve() {
		if (isAllValid()) {
			return solve(0, 0);
		} else {
			return false;
		}
	}

	private boolean solve(int r, int c) {
		if (r == size) return true;
		int nextRow, nextCol;
		if (c < size-1) {
			nextCol = c + 1;
			nextRow = r;
		} else {
			nextRow = r + 1;
			nextCol = 0;
		}
		
		boolean b = false;
		if (numbers[r][c] == 0) {
			for (int nbr = 1; nbr < size + 1; nbr++) {
				if (isValid(r, c, nbr)) {
					numbers[r][c] = nbr;
					b = solve(nextRow, nextCol);
					if (!b) numbers[r][c] = 0;
					else break;
				}
			}
		} else {
			b = solve(nextRow, nextCol);
		}
		return b;
	}

	private int[][] copyMatrix(int[][] matrix){
		if (matrix == null) return new int[size][size];
		if (matrix.length != size && matrix[0].length != size) {
			return new int[size][size];
		}
		int[][] copy = new int[size][size];
		for (int r = 0; r < size; r++) {
			for (int c = 0; c < size; c++) {
				copy[r][c] = matrix[r][c];
			}
		}
		return copy;
	}
	@Override
	public void clear() {
		numbers = new int[size][size];
	}

	@Override
	public int[][] getMatrix() {
		return copyMatrix(numbers);
	}

	@Override
	public void setMatrix(int[][] nbrs) {
		numbers = copyMatrix(nbrs);
	}
	
	@Override
	public int getDimension() {
		return size;
	}
}
