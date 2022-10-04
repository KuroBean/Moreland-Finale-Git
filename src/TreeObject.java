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
		File indexFile=new File("index.txt");
		BufferedReader indexReader=new BufferedReader(new FileReader(indexFile));
		
		String line=indexReader.readLine();
		things=new ArrayList<String[]>();
		
		while(line!=null) {
			things.add(line.split(" "));//each arraylist entry is og name and sha1 name
			//first in array is og name, second is sha 1 anem
			line=indexReader.readLine();
		}
		/**
		//test split
		for(String[] lin:things) {
			System.out.println(lin[0]);
			System.out.println(lin[1]);
			System.out.println(lin[2]); 
		}*/
		
		for (int i=0;i<things.size();i++) {
			fileContents +="blob : "+things.get(i)[2]+" "+things.get(i)[0]+"\n";
		}
		if(prevCommitTree!=null) {
			fileContents+="tree : "+prevCommitTree;
		}
		
		
		sha = GitUtils.StringToSha(fileContents);
		writing = new File("objects/"+sha+".txt");
		writing.createNewFile();
		printFile();
		
	}
	public String treePath() {
		return writing.getName();
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
