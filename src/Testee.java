import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class Testee {
	public static void main (String[]args) throws NoSuchAlgorithmException, IOException {
		Commit commit = new Commit("sumamr","meauthor",null);
		commit.create();
		
		System.out.print("gaming:\n" + commit.getContents() + "\nend");
	}
}
