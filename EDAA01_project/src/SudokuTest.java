import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SudokuTest {
	private SudokuBacktracker solver;

	@BeforeEach
	public void setUp() throws Exception {
		solver = new SudokuBacktracker(9);
	}

	@AfterEach
	public void tearDown() throws Exception {
		solver = null;
	}

	@Test
	void testEmptyGrid() {
		assertTrue(solver.solve());
	}

	@Test
	void testUnsolvableWrongInput() {
		solver.setNumber(0, 0, 5);
		solver.setNumber(8, 0, 5);
		assertFalse(solver.solve());
		solver.clear();
		solver.setNumber(0, 0, 5);
		solver.setNumber(0, 8, 5);
		assertFalse(solver.solve());
		solver.clear();
		solver.setNumber(0, 0, 5);
		solver.setNumber(1, 1, 5);
		assertFalse(solver.solve());
	}

	@Test
	void testUnsolvable() {
		solver.setNumber(0, 0, 1);
		solver.setNumber(1, 0, 2);
		solver.setNumber(2, 0, 3);
		solver.setNumber(0, 1, 4);
		solver.setNumber(1, 1, 5);
		solver.setNumber(2, 1, 6);
		solver.setNumber(3, 2, 7);
		assertFalse(solver.solve());
		solver.clearNumber(3, 2);
		assertTrue(solver.solve());
	}

	@Test
	void testClear() {
		solver.setNumber(0, 0, 5);
		solver.setNumber(8, 0, 5);
		assertFalse(solver.solve());
		solver.clear();
		assertTrue(solver.solve());
	}

	@Test
	void testSolvable() {
		int[][] startPos = { { 0, 0, 8, 0, 0, 9, 0, 6, 2 }, { 0, 0, 0, 0, 0, 0, 0, 0, 5 },
				{ 1, 0, 2, 5, 0, 0, 0, 0, 0 }, { 0, 0, 0, 2, 1, 0, 0, 9, 0 }, { 0, 5, 0, 0, 0, 0, 6, 0, 0 },
				{ 6, 0, 0, 0, 0, 0, 0, 2, 8 }, { 4, 1, 0, 6, 0, 8, 0, 0, 0 }, { 8, 6, 0, 0, 3, 0, 1, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 4, 0, 0 } };
		solver.setMatrix(startPos);
		assertTrue(solver.solve());
	}

	@Test
	void testWrongInput() {
		try {
			solver.setNumber(4, 4, -1);
			fail("Should show IllegalArgumentException");
		} catch (Exception IllegalArgumentException) {
		}

		try {
			solver.setNumber(4, 4, 10);
			fail("Should show IllegalArgumentException");
		} catch (Exception IllegalArgumentException) {
		}
		
		// setting square to 0 equals resetting it, tested in application
		//solver.setNumber(4, 4, 0);
		//fail("Should show IllegalArgumentException");
		
		// can't test Sting input in Junit with suduko backtracking
		// solver.setNumber(4, 4, "a");
		// fail("Should show IllegalArgumentException");
	}
	
	@Test
	void testIsValid() {
		assertTrue(solver.isValid(0,0,0));
	}
	@Test
	void testGetMatrix() {
		int[][] i = solver.getMatrix();
		i[0][0] = 3;
		assertEquals(solver.getNumber(0, 0), 0);
	}
	@Test
	void testSetup() {
		solver = new SudokuBacktracker(-1);
		assertTrue(solver.solve());
		solver = new SudokuBacktracker(7);
		assertTrue(solver.solve());
	}
	@Test
	void testsetMatrix() {
		int[][] i = new int[5][5];
		solver.setMatrix(i);
		solver.setNumber(0, 0, 5);
		assertEquals(i[0][0], 0);
	}

}
