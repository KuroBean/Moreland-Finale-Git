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
		Commit first=new Commit(dexy,"this sit he first one","jeff senior");
		
		
		dexy.addBlob("test2.txt");
		dexy.remove("test.txt");
		Commit second=new Commit(dexy, "this is second one, for test 1 text for some reason","bezos the second");
		
		
		dexy.addBlob("testBean.txt");
		dexy.addBlob("test4.txt");
		Commit third=new Commit(dexy,"third oen here","3 bofas");
		//dexy.addBlob("test2.txt");
		//dexy.edit("text4.txt", "hehe edited");
		dexy.edit("test4.txt","hehe edited");
		dexy.remove("testBean.txt");

		Commit fourth=new Commit(dexy,"4thh beeth here","4 beans");
		
		dexy.edit("test1.txt","changed rip");
		Commit fifth=new Commit(dexy,"5th is the worst movie","knuckels 5");
		//System.out.println((fileContent("./objects/"+first.getFileName())).contains("a51be64c2a947773e132358cbe2afd347a775217.txt"));
		assertTrue((fileContent("./objects/"+first.getFileName())).contains("a51be64c2a947773e132358cbe2afd347a775217.txt"));
		assertTrue((fileContent("./objects/"+second.getFileName())).contains("0a810f1724396acbcaa5b1c13dc8f49f60a20900.txt"));
		
		assertTrue((fileContent("./objects/"+third.getFileName())).contains("d3b0b828f0ae8016312bd2c9110cdba7b34c1044.txt"));
		assertTrue((fileContent("./objects/"+fourth.getFileName())).contains("5e8c5afc17a9d74ddc90b21644a0ba9d595f2457.txt"));
		
		assertTrue((fileContent("./objects/"+fifth.getFileName())).contains("b88fffba22415d2c8119c50a5bcad25a8e25a6b0.txt"));
		
		
		
		
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
