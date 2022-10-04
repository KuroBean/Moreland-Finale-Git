import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class TreeObject implements GitUtils {

	public ArrayList<String[]> things;
	public ArrayList<String[]> removeReferences;
	public File writing;
	public String fileContents = "";
	public String sha;
	public String treeFilePath;
	
	public TreeObject (String prevCommitTree) throws IOException {
		things=getFileContentTokens("index.txt");
		
		/**
		//test split
		for(String[] lin:things) {
			System.out.println(lin[0]);
			System.out.println(lin[1]);
			System.out.println(lin[2]);
		}
		*/
		removeReferences=new ArrayList<String[]>();
		for (int i=0;i<things.size();i++) {
			if(things.get(i)[0].charAt(0)=='*') {
				removeReferences.add(things.get(i));//remove references hold lines with *deleted or edit*
				//0 is delete or edit, 1 is:, 2 is filename
			}else {
				fileContents +="blob : "+things.get(i)[2]+" "+things.get(i)[0]+"\n";
			}
		}
		
		
		if(prevCommitTree!=null) {
			fileContents+="tree : "+prevCommitTree;
			
		}

		sha = GitUtils.StringToSha(fileContents);
		writing = new File("objects/"+sha+".txt");
		writing.createNewFile();
		printFile();
		//finish writing everyhting for a hot sec
		
		if(removeReferences.size()!=0) {
			ArrayList<String> removeFileNames = new ArrayList<String>();
			for (int i=0;i<removeReferences.size();i++) { 
				removeFileNames.add(removeReferences.get(i)[2]);
			}
			ArrayList<String[]> extraReferences=referencesNeededFromTree(this, removeFileNames);
		
			//make file contents=result from recursion, re-sha and re create file
		}
	}
	
	//takes in fiel ogname for remove file naems
	private ArrayList<String[]> referencesNeededFromTree(TreeObject tree, ArrayList<String> removingFiles) throws IOException{
		 ArrayList<String[]> treeContent=getFileContentTokens("./objects/"+tree.treePath());
		 //in each thing in result, [0] is type,[1] is :, [2] is sha name, [3] is og name
		 ArrayList<String[]> result=new ArrayList<String[]>();
		 //check if file to be deleteed is in tree
		 for(String[] line:treeContent) {
			 if(line.length==3) {//if is tree data
				 
			 }
		 
		 }
		 
		 return result;
	}
	
	private boolean fileInTree(String fileName,ArrayList<String[]> treeContent) {
		for(String[] line:treeContent) {
			if(line.length==4&&line[3].equals(fileName)) {
				return true;
			}
		}
		return false;
	}
	
	
	public String treePath() {
		return writing.getName();
	}
	

	private ArrayList<String[]> getFileContentTokens(String filePath) throws IOException{
		ArrayList<String[]> result=new ArrayList<String[]>();
		File file=new File(filePath);
		//System.out.println(file.exists());
		BufferedReader reader=new BufferedReader(new FileReader(file));

		String line=reader.readLine();
		
		while(line!=null) {
			result.add(line.split(" "));//each arraylist entry is og name and sha1 name
			//first in array is og name, second is sha 1 anem
			line=reader.readLine();
		}
		
		return result;
	}//when reading blob in a tree file, [0] is tree or blob, then colon, then sha'd file name, then og name
	
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
