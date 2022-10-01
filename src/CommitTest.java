import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CommitTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		
	}

	@Test
	void testCommit() throws IOException, NoSuchAlgorithmException {
		Index index = new Index();
		index.initialize();
		index.addBlob("test.txt");
		
		index.addBlob("test1.txt");
		
		Commit first=new Commit("this sit he first one","jeff",null);
		
		index.addBlob("test2.txt");
		
		Commit second=new Commit("second oen here","bofa",first);
		
	}

}
