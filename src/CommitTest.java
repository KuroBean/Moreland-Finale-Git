import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
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
	
	//UNCOMMENT TO ACTIVATE COMMIT CLASS TESTER, NOT THE REMOVE/EDIT TESTER
	/**
	@Test
	void testCommit() throws IOException, NoSuchAlgorithmException {
		Index index = new Index();
		index.initialize();
		index.addBlob("test.txt");
		Commit first=new Commit(index,"this sit he first one","jeff senior",null);
		//System.out.println(fileContent("./objects/"+first.getFileName()));
		assertTrue((fileContent("./objects/"+first.getFileName())).contains("68fd51de288de3dbf356c69e0bf0dd050cc44b77.txt\n"
				+ "\n"
				+ "\n"
				+ "jeff senior\n"));// CANT CHECK FOR DATE BC ALWAYS CHANGING
		
		index.addBlob("test1.txt");
		Commit second=new Commit(index,"this is second one, for test 1 text for some reason","bezos the second",first);
		assertTrue((fileContent("./objects/"+second.getFileName())).contains("5b4a8af079c94379c124dadf8e910ef5425a9435.txt\n"
				+ first.getFileName()+"\n"
				+ "\n"
				+ "bezos the second"));
		
		System.out.println(first.getFileName());
		
		index.addBlob("test2.txt");
		Commit third=new Commit(index,"third oen here","3 bofas",second);
		assertTrue((fileContent("./objects/"+third.getFileName())).contains("5f7e526a7695c0a95847e4bcff942d6f7404d41b.txt\n"
				+ second.getFileName()+"\n"
				+ "\n"
				+ "3 bofas\n"));
		
		index.addBlob("testBean.txt");
		Commit beans=new Commit(index,"moar b e a n s","beanlover4",third);
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
		
	}*/
	
	@Test 
	void testRemoveAndEdit() throws IOException, NoSuchAlgorithmException{
		Index dexy=new Index();
		dexy.addBlob("test.txt");
		dexy.addBlob("test1.txt");
		Commit first=new Commit(dexy,"this sit he first one","jeff senior",null);
		
		
		dexy.addBlob("test2.txt");
		dexy.remove("test.txt");
		Commit second=new Commit(dexy, "this is second one, for test 1 text for some reason","bezos the second",first);
		
		
		dexy.addBlob("testBean.txt");
		dexy.addBlob("test4.txt");
		Commit third=new Commit(dexy,"third oen here","3 bofas",second);
		//dexy.addBlob("test2.txt");
		//dexy.edit("text4.txt", "hehe edited");
		dexy.edit("test4.txt","hehe edited");
		dexy.remove("testBean.txt");

		Commit fourth=new Commit(dexy,"4thh beeth here","4 beans",third);
		
		dexy.edit("test1.txt","changed rip");
		Commit fifth=new Commit(dexy,"5th is the worst movie","knuckels 5",fourth);
		
		
		assertTrue((fileContent("./objects/"+first.getFileName())).contains("0e962778adfb8df2b491f243f5ce4c7897ae6596.txt\n"
				+ "\n"
				+ "960929af442ebdc58e9433998ee3fe974405ccec.txt\n"
				+ "jeff senior"));
		assertTrue((fileContent("./objects/"+second.getFileName())).contains("9d71307eb78705ed1339d2e7b531efad19dc0ee7.txt\n"
				+ "149cc1599f6bba316f21a4b415f05964bc7c1549.txt\n"
				+ "5fe4a82fb909967a7de23df771212ff242a2c0ef.txt\n"
				+ "bezos the second"));
		assertTrue((fileContent("./objects/"+third.getFileName())).contains("e6109268dc0ed929cf6cbbeeb7708401229a481a.txt\n"
				+ "960929af442ebdc58e9433998ee3fe974405ccec.txt\n"
				+ "9fe60f63d913c9f46c29e1386fb3cb52ad9168c8.txt\n"
				+ "3 bofas"));
		assertTrue((fileContent("./objects/"+fourth.getFileName())).contains("7a414eccab4c75458bdc6dd9fe22a5bb3e039cb1.txt\n"
				+ "5fe4a82fb909967a7de23df771212ff242a2c0ef.txt\n"
				+ "c6b224ba15f38789b0004a549abc9bfeb9bcfe46.txt\n"
				+ "4 beans"));
		assertTrue((fileContent("./objects/"+fourth.getFileName())).contains("b88fffba22415d2c8119c50a5bcad25a8e25a6b0.txt\n"
				+ "9fe60f63d913c9f46c29e1386fb3cb52ad9168c8.txt\n"
				+ "\n"
				+ "knuckels 5"));
		
		
		
		
		File file = new File("test1.txt");
		PrintWriter writer = new PrintWriter(file);
		writer.print("og first");
		writer.close();
		File filey = new File("test4.txt");
		PrintWriter writery = new PrintWriter(filey);
		writery.print("4th before change");
		writery.close();
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
