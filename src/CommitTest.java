import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
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
		//System.out.println(fileContent("./objects/"+first.getFileName()));
		assertTrue((fileContent("./objects/"+first.getFileName())).contains("68fd51de288de3dbf356c69e0bf0dd050cc44b77.txt\n"
				+ "\n"
				+ "\n"
				+ "jeff senior\n"));// CANT CHECK FOR DATE BC ALWAYS CHANGING
		
		index.addBlob("test1.txt");
		Commit second=new Commit("this is second one, for test 1 text for some reason","bezos the second",first);
		assertTrue((fileContent("./objects/"+second.getFileName())).contains("5b4a8af079c94379c124dadf8e910ef5425a9435.txt\n"
				+ first.getFileName()+"\n"
				+ "\n"
				+ "bezos the second"));
		
		System.out.println(first.getFileName());
		
		index.addBlob("test2.txt");
		Commit third=new Commit("third oen here","3 bofas",second);
		assertTrue((fileContent("./objects/"+third.getFileName())).contains("5f7e526a7695c0a95847e4bcff942d6f7404d41b.txt\n"
				+ second.getFileName()+"\n"
				+ "\n"
				+ "3 bofas\n"));
		
		index.addBlob("testBean.txt");
		Commit beans=new Commit("moar b e a n s","beanlover4",third);
		assertTrue((fileContent("./objects/"+beans.getFileName())).contains("62aa875616d4c796f49470546ef603a6c1d0d23d"
				+ ".txt\n"
				+ third.getFileName()+"\n"
				+ "\n"
				+ "beanlover4\n")); 
		
		System.out.println(first.getFileName());
			
			
		assertTrue((fileContent("./objects/"+first.getFileName())).contains("68fd51de288de3dbf356c69e0bf0dd050cc44b77.txt\n"
				+ "\n"
				+ second.getFileName()+"\n"
				+ "jeff senior"));
		
		assertTrue((fileContent("./objects/"+second.getFileName())).contains("5b4a8af079c94379c124dadf8e910ef5425a9435.txt\n"
				+ first.getFileName()+"\n"
				+ third.getFileName()+"\n"
				+ "bezos the second"));
		assertTrue((fileContent("./objects/"+third.getFileName())).contains("5f7e526a7695c0a95847e4bcff942d6f7404d41b.txt\n"
				+ second.getFileName()+"\n"
				+ beans.getFileName()+"\n"
				+ "3 bofas\n"));
		
	}
	
	private String fileContent(String path) throws IOException {
		Path treePath= Paths.get(path);
		return (Files.readString(treePath));
	}
	public String getHash(String input) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-1"); //generates sha1
		byte[] messageDigest = md.digest(input.getBytes());
		BigInteger no = new BigInteger(1, messageDigest);
		String hashtext = no.toString(16);
		while (hashtext.length() < 40) {
            hashtext = "0" + hashtext;
		}
        return hashtext;
	}

}
