import static org.junit.jupiter.api.Assertions.*;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;

class SudokuTest {
	private SudokuApplication a;
	
	@Before
	public void setUp() throws Exception {
		a = new SudokuApplication();
	}
	
	@After
	public void tearDown() throws Exception {
		a = null;
	}
	
	@Test
	void testEmptyGrid() {
		
	}

}
