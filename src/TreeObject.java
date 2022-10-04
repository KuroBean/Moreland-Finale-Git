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
		
		
		//entire removing bit
		if(removeReferences.size()!=0) {
			ArrayList<String> removeFileNames = new ArrayList<String>();
			for (int i=0;i<removeReferences.size();i++) { 
				removeFileNames.add(removeReferences.get(i)[2]);
			}
			ArrayList<String[]> totalReferences=referencesNeededFromTree(this.treePath(), removeFileNames);
			
			//delete previous tree file with writiting ref
			writing.delete();
			
			StringBuilder sb=new StringBuilder();
			//make file contents=result from recursion, re-sha and re create file
			for(int m=0;m<totalReferences.size();m++) {
				for(int i=0;i<totalReferences.get(m).length;i++) {
					sb.append(totalReferences.get(m)[i]);
					if(i+1!=totalReferences.get(m).length) {
						sb.append(" ");
					}
				}
				if(m+1!=totalReferences.size()) {
					sb.append("\n");
				}
			}
			fileContents=sb.toString();
			sha = GitUtils.StringToSha(fileContents);
			writing = new File("objects/"+sha+".txt");
			writing.createNewFile();
			printFile();
			printRemovedFilesToIndex(removeReferences);
		}
	}
	private void printRemovedFilesToIndex(ArrayList<String[]> removingFileLines) throws FileNotFoundException {
		StringBuilder sb=new StringBuilder();
		for(int m=0;m<removingFileLines.size();m++) {
			for(int i=0;i<removingFileLines.get(m).length;i++) {
				sb.append(removingFileLines.get(m)[i]);
				if(i+1!=removingFileLines.get(m).length) {
					sb.append(" ");
				}
			}
			if(m+1!=removingFileLines.size()) {
				sb.append("\n");
			}
		}
		PrintWriter indexWriter=new PrintWriter(new File("index.txt"));
		indexWriter.print(sb.toString());
		indexWriter.close();
	}
	
	//takes in fiel ogname for remove file naems
	private ArrayList<String[]> referencesNeededFromTree(String treePath, ArrayList<String> removingFiles) throws IOException{
		 ArrayList<String[]> treeContent=getFileContentTokens("./objects/"+treePath);
		 //in each thing in result, [0] is type,[1] is :, [2] is sha name, [3] is og name (for blobs onlu)
		 ArrayList<String[]> result=new ArrayList<String[]>();
		 
		 if(!fileInthisTree(removingFiles,treeContent)&&!treesInTreeHaveRemovedFiles(removingFiles,treeContent)) {
			 //base case: files not in given tree and all trees in tree not connected to any of the files
			 //result.addAll(referencesNeededFromTree("./objects/"+line[2],removingFiles));
			 result.add(("tree : "+treePath).split(" "));
		 }else {
			 for(String[] line:treeContent) {
				 //add all blob lines except the removing ones
				 if(line.length==4) {//if blob
					 if(!fileIsInRemovedList(removingFiles,line[3])) {
						 result.add(line);
					 }
				 }else if(line.length==3) {//if tree
					 result.addAll(referencesNeededFromTree(line[2],removingFiles));
				 }
			 }
		 }
		 return result;
	}
	
	private boolean fileIsInRemovedList(ArrayList<String>  removingFiles, String fileName){
		for (String name:removingFiles) {
			if(name.equals(fileName)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean treesInTreeHaveRemovedFiles(ArrayList<String>  removingFiles,ArrayList<String[]> treeContent) throws IOException {
		ArrayList<String[]> neededContentofMiniTree=new ArrayList<String[]>();
		for(String[] line:treeContent) {
			if(line.length==3){//if tree info line
				neededContentofMiniTree=referencesNeededFromTree(line[2],removingFiles);
				if(neededContentofMiniTree.size()!=1) {//jsut the tree couldnt be returnd bc there was file refercning to the nonos soemwhere
					return true;
				}else if(neededContentofMiniTree.size()>0&&!neededContentofMiniTree.get(0).equals(line[2])) {
						return true;
					
				}
			}
		}
		return false;
	}
	
	private boolean fileInthisTree(ArrayList<String>  removingFiles,ArrayList<String[]> treeContent) {
		for(String[] line:treeContent) {
			if(line.length==4) {//is blob
				for(String files:removingFiles) {
					if(files.equals(line[3])) {
						return true;
					}
				}
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
