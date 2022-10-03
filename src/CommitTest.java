import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
		Commit first=new Commit("this sit he first one","jeff senior",null);
		System.out.println(fileContent("./objects/"+first.getFileName()));
		assertTrue((fileContent("./objects/"+first.getFileName())).contains("68fd51de288de3dbf356c69e0bf0dd050cc44b77\n"
				+ "\n"
				+ "\n"
				+ "jeff senior\n"));// CANT CHECK FOR DATE BC ALWAYS CHANGING
		
		index.addBlob("test1.txt");
		Commit second=new Commit("this is second one, for test 1 text for some reason","bezos the second",first);
		assertTrue((fileContent("./objects/"+second.getFileName())).contains("37c4ad416afa261e740c7566f08b66d471d93854\n"
				+ "3c68385f4137f071adeaae853568a43fb2e14879\n"
				+ "\n"
				+ "bezos the second\n"));
		
		index.addBlob("test2.txt");
		Commit third=new Commit("second oen here","3 bofas",second);
		assertTrue((fileContent("./objects/"+third.getFileName())).contains("ed3fb19f1c1e4206e550aee21c0013a1115d6601\n"
				+ "e3504374e824b2289dfa21b2547a5a4ef9116d45\n"
				+ "\n"
				+ "3 bofas\n"));
		
	
		index.addBlob("testBean.txt");
		Commit beans=new Commit("moar b e a n s","beanlover4",third);
		assertTrue((fileContent("./objects/"+beans.getFileName())).contains("ec255eca93effb460ef9dc6815636eff7ae3bf93f\n"
				+ "5cb7982b23f4c6499403a851d0130d11502b7bc1\n"
				+ "\n"
				+ "beanlover4\n"));
	}
	
	private String fileContent(String path) throws IOException {
		Path treePath= Paths.get(path);
		return (Files.readString(treePath));
	}

}
