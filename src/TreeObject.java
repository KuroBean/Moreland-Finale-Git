import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class TreeObject implements GitUtils {

	public ArrayList<String[]> things;
	public File writing;
	public String fileContents = "";
	public String sha;
	public String treeFilePath;
	
	public TreeObject (String prevCommitTree) throws IOException {
		File indexFile=new File("index");
		BufferedReader indexReader=new BufferedReader(new FileReader(indexFile));
		
		String line=indexReader.readLine();
		things=new ArrayList<String[]>();
				
		
		while(!line.equals(null)) {
			things.add(line.split(" "));//each arraylist entry is og name and sha1 name
			//first in array is og name, second is sha 1 anem
			line=indexReader.readLine();
		}
		
		
		
		for (int i=0;i<things.size();i++) {
			fileContents +="blob : "+things.get(i)[1]+" "+things.get(i)[0];
			if(i+1!=things.size()) {
				fileContents+="\n";
			}
		}
		fileCntents+
		
		//STILL NEED TO ADD PREVIOUS COMMIT'S TREE
		
		sha = GitUtils.StringToSha(fileContents);
		writing = new File(sha);
		writing.createNewFile();
		printFile();
		
	}
	public String treePath() {
		return writing.getPath();
	}
	
	private boolean printFile() {
		try {
			PrintWriter au = new PrintWriter(writing);
			au.print(fileContents);
			au.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
}
