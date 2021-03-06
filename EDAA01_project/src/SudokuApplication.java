
public class SudokuApplication {
	public static void main(String[] args) {
		SudokuBacktracker b = new SudokuBacktracker(9);
		SudokuWindow w = new SudokuWindow(b);
	}
}
