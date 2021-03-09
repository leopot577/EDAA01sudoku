import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

public class SudokuWindow {
	private Container pane;
	private JFrame frame;
	private JTextField[][] grid;
	private int size;
	private JButton btnSolve, btnClear;
	private JPanel btnPanel, gridPanel;
	private JPanel[][] squarePanels;
	private Border squareBorder, cellBorder, gridBorder;
	private SudokuSolver b;
	
	public SudokuWindow(SudokuSolver b) {
		this.b = b;
		this.size = b.getDimension();
		grid = new JTextField[size][size];
		SwingUtilities.invokeLater(() -> makeGUI());
	}
	/**
	 * Makes the entire SudokuWindow GUI and connects it to the given
	 * SudokuBacktracker.
	 */
	private void makeGUI() {
		frame = new JFrame("Sudoku");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pane = frame.getContentPane();
		// Knappar
		btnSolve = new JButton("Solve");
		btnClear = new JButton("Clear");
		Dimension btnDim = new Dimension(100, 25);
		btnSolve.setPreferredSize(btnDim);
		btnClear.setPreferredSize(btnDim);
		// ActionListeners
		btnSolve.addActionListener(e -> {
			boolean errorFound = false;
			for (int r = 0; r < size; r++) {
				for (int c = 0; c < size; c++) {
					int nbr;
					String input = grid[r][c].getText();
					boolean isEmpty = input.equals("");
					try {
						if (input.strip().equals("0")) {
							throw new NumberFormatException();
						}
						nbr = Integer.parseInt(input);
					} catch (NumberFormatException err1) {
						nbr = 0;
						if (!isEmpty) {
							grid[r][c].setText("");
							showBadInputMessage();
							errorFound = true;
						}
					}
					try {
						if (nbr == 0) {
							b.clearNumber(r, c);
						} else {
							b.setNumber(r, c, nbr);
						}
					} catch (IllegalArgumentException err2) {
						nbr = 0;
						grid[r][c].setText("");
						showBadInputMessage();
						errorFound = true;
					}
				}
			}
			if (!errorFound) {
				boolean solved = b.solve();
				if (!solved) showUnsolvableMessage();
				else setMatrixText(b.getMatrix());
			}
		});
		btnClear.addActionListener(e -> {
			b.clear();
			clear();
		});
		// Dimensioner
		int sqDim = (int) Math.sqrt(size);
		// Ramar
		squareBorder = BorderFactory.createLineBorder(Color.BLACK, 2);
		cellBorder = BorderFactory.createLineBorder(Color.GRAY, 1);
		gridBorder = BorderFactory.createLineBorder(Color.DARK_GRAY, 2);
		// Lägg till alla textrutor i matrisen, sätt alla tal till 0
		for (int r = 0; r < size; r++) {
			for (int c = 0; c < size; c++) {
				b.getMatrix()[r][c] = 0;
				JTextField cell = new JTextField();
				cell.setBorder(cellBorder);
				cell.setHorizontalAlignment(JTextField.CENTER);
				cell.setFont(new Font("Verdana", Font.CENTER_BASELINE, 20));
				grid[r][c] = cell;
			}
		}
		//Paneler
		squarePanels = new JPanel[sqDim][sqDim];
		gridPanel = new JPanel();
		gridPanel.setLayout(new GridLayout(sqDim, sqDim));
		gridPanel.setBorder(gridBorder);
		
		btnPanel = new JPanel();
		btnPanel.setLayout(new BorderLayout());
		btnPanel.add(btnSolve,	BorderLayout.WEST);
		btnPanel.add(btnClear,	BorderLayout.EAST);
		// Lägg till alla stora rutor i matrisen och rutnätet
		for (int r = 0; r < sqDim; r++) {
			for (int c = 0; c < sqDim; c++) {
				JPanel square = new JPanel();
				square.setLayout(new GridLayout(sqDim, sqDim));
				square.setBorder(squareBorder);
				squarePanels[r][c] = square;
				gridPanel.add(square);
			}
		}
		// Lägg till textrutorna i de stora stora rutorna
		for (int r = 0; r < size; r++) {
			for (int c = 0; c < size; c++) {
				int currentRow = r / sqDim;
				int currentCol = c / sqDim;
				squarePanels[currentRow][currentCol].add(grid[r][c]);
			}
		}
		
		pane.add(gridPanel,	BorderLayout.CENTER);
		frame.add(btnPanel,	BorderLayout.SOUTH);
		pane.setPreferredSize(new Dimension(400,400));
		frame.pack();
		frame.setVisible(true);
	}
	
	/**
	 * Opens a dialog window with error message saying that the given
	 * grid is unsolvable.
	 */
	public void showUnsolvableMessage() {
		JOptionPane.showMessageDialog(frame, "Det angivna sudokut är olösbart");
	}
	
	/**
	 * Opens a dialog window with error message saying that the user
	 * has made an invalid input.
	 */
	public void showBadInputMessage() {
		JOptionPane.showMessageDialog(frame, "Vänligen skriv siffror mellan 1 och " + size);
	}
	
	/**
	 * Prints the numbers onto the grid in the given matrix.
	 * @param matrix
	 * 				The numbers to be printed
	 */
	public void setMatrixText(int[][] matrix) {
		for (int r = 0; r < size; r++) {
			for (int c = 0; c < size; c++) {
				grid[r][c].setText(matrix[r][c] + "");
			}
		}
	}
	
	/**
	 * Clears the grid.
	 */
	public void clear() {
		for (int r = 0; r < size; r++) {
			for (int c = 0; c < size; c++) {
				grid[r][c].setText("");
			}
		}
	}
}
