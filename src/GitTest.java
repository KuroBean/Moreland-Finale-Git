import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class IndexTest {

	public static String filePath = "test.txt";
	public static String testerText = "tester text that is great";
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		//create file
		Files.writeString(Paths.get(filePath), testerText);

	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		//delete file
		File del = new File("/test.txt");
		del.delete();
	}

	@Test
	void makeBlob() {
		Blob a = null;
		try { a = new Blob(filePath); }
		catch (Exception e) {e.printStackTrace();}
		
		assertTrue(a != null);
		
		a.delete();
	}

	@Test
	void testGetBlob() {
		Blob a = null;
		try { a = new Blob(filePath); }
		catch (Exception e) {e.printStackTrace();}
		
		assertTrue(testerText.equals(a.getText()));
		
		a.delete();
	}
	
	@Test
	void testGetSha() {
		Blob a = null;
		try { a = new Blob(filePath); }
		catch (Exception e) {e.printStackTrace();}
		
		assertTrue("068f7350ecf50f6598bf98251a9c0cedfdf3ecde".equals(a.getSha()));
		
		a.delete();
	}
	
	@Test
	void makeIndex() throws IOException {
		Index b = new Index();
		assertTrue(new File("objects").exists());
		b.delete();
	}
	
	@Test
	void IndexAddAndRemove() throws IOException {
		Index b = new Index();
		try { b.addBlob(filePath); }
		catch (Exception e) {e.printStackTrace();}
		
		assertTrue(new File("objects/068f7350ecf50f6598bf98251a9c0cedfdf3ecde.txt").exists());
		
		try { b.deleteBlob(filePath); }
		catch (Exception e) {e.printStackTrace();}
		
		b.delete();
		
	}

}
