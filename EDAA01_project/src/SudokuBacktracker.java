
public class SudokuBacktracker implements SudokuSolver {
	private int[][] numbers;
	private int sqDim;
	private int[][] preKnown;
	private int size;
	
	public SudokuBacktracker(int size) {
		this.size = size;
		numbers = new int[size][size];
		preKnown = new int[size][size];
		sqDim = (int) Math.sqrt(size);
	}
	
	
	@Override
	public void setNumber(int r, int c, int nbr) {
		if (nbr < 0 || nbr > size) {
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
		if (r < 0 || c < 0 || r > size || c > size || nbr < 0 || nbr > size) {
			throw new IllegalArgumentException();
		}
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
	
	public boolean preKnownIsAllValid() {
		for (int r = 0; r < size; r++) {
			for (int c = 0; c < size; c++) {
				preKnown[r][c] = numbers[r][c];
				if (preKnown[r][c] != 0) {
					if (!isValid(r, c, preKnown[r][c])) return false;
				}
			}
		}
		return true;
	}
	
	@Override
	public boolean solve() {
		if (preKnownIsAllValid()) {
			solve(0, 0);
		} else {
			return false;
		}
		return isAllValid();
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
		if (preKnown[r][c] == 0) {
			for (int nbr = 1; nbr < size + 1; nbr++) {
				if (isValid(r, c, nbr)) {
					numbers[r][c] = nbr;
					b = solve(nextRow, nextCol);
					if (!b) numbers[r][c] = 0;
				}
			}
		} else {
			b = solve(nextRow, nextCol);
		}
		return b;
	}

	@Override
	public void clear() {
		numbers = new int[size][size];
	}

	@Override
	public int[][] getMatrix() {
		return numbers;
	}

	@Override
	public void setMatrix(int[][] nbrs) {
		numbers = nbrs;
	}
	
	@Override
	public int getDimension() {
		return size;
	}
}
