import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class TreeObject implements GitUtils {

	public ArrayList<String> things;
	public File writing;
	public String fileContents = "";
	public String sha;
	
	public TreeObject () throws IOException {
		File indexFile=new File("index");
		BufferedReader indexReader=new BufferedReader(new FileReader(indexFile));
		
		String line=indexReader.readLine();
		things=new ArrayList<String>();
				
		while(!line.equals(null)) {
			things.add(line);
			line=indexReader.readLine();
		}
		//do smth to convert index format to tree format w/ blob sha1name ogname
		
		for (String str : things) {
			fileContents = fileContents + str + "\n";
		}
		sha = GitUtils.StringToSha(fileContents);
		writing = new File(sha);
		printFile();
	}
	
	private boolean printFile() {
		try {
			PrintWriter au = new PrintWriter(writing);
			for (String str : things) {
				au.append(str + "\n");
			}
			au.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
}
