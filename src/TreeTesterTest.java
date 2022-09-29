import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TreeTesterTest {

	private TreeObject tree;
	
	@Before
	public void setUp() throws Exception {
		ArrayList<String> list = new ArrayList<String>();
		list.add("thing1");
		list.add("thing232");
		list.add("tieowjifo");
		
		String[] listee = new String[3];
		listee[0] = "jeiw";
		listee[1] = "fjieowfew";
		listee[2] = "fjieowjfewoijfew";
		
		//tree = new TreeObject(listee);
	}

	@After
	public void tearDown() throws Exception {
		File f1 = new File("objects/" + tree.sha);
		f1.delete();
	}

	@Test
	public void test() {
		assertTrue(tree.sha.length()==40);
	}

}
