import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;

public class Commit {

	private String parent;
	private String child;
	private String ptree;
	private String summary;
	private String author;
	private String date;
	private String commitFileName;
	
	//INDEX PARAM ADDED TO CLEAR AFTER EVERY COMMIT CONSTRUCTED
	public Commit (Index dexy,String nsummary, String nauthor) throws IOException, NoSuchAlgorithmException {
		File cabeza=new File("Head.txt");
		BufferedReader headRead=new BufferedReader(new FileReader(cabeza));
		parent=headRead.readLine();
		headRead.close();
		
		child = null;
		
		summary = nsummary;
		author = nauthor;
		date = Calendar.getInstance().getTime().toString();
		
		//make tree obj
		String prevTree=null; 
		File prevCommit;
		
		ArrayList<String> prevCommitContent=new ArrayList<String>();
		if(parent!=null) {//read parent commit's first lien fro prev tree
			prevCommit=new File("./objects/"+parent);
			System.out.println(prevCommit.exists());
			BufferedReader prevRead=new BufferedReader(new FileReader(prevCommit));
			prevTree=prevRead.readLine();
			prevCommitContent.add(prevTree);
			String line= prevRead.readLine();
			while (line!=null) {
				prevCommitContent.add(line);
				line= prevRead.readLine();
			}
			prevRead.close();
			
		}
		
		TreeObject newtree=new TreeObject(prevTree);
		ptree=newtree.treePath(); 
		commitFileName=getHash()+".txt";
		create();
		
		/**
		if(parent!=null) {
			parent.setChild(this); 
			parent.create();
		}*/
		if(parent!=null) {
			prevCommit=new File("./objects/"+parent);
			PrintWriter prevWrite=new PrintWriter(prevCommit);
			String prevContent=fileContent("./objects/"+parent);
			for(int i=0;i<prevCommitContent.size();i++) {
				if(i==2) {
					prevWrite.println(this.getFileName());
				}else if(i+1==prevCommitContent.size()){
					prevWrite.print(prevCommitContent.get(i));
				}else {
					prevWrite.println(prevCommitContent.get(i));
				}
			}
			prevWrite.close();
			}
		
		dexy.clearIndex();
		
		PrintWriter headWrite=new PrintWriter(new File("Head.txt"));
		headWrite.write(this.getFileName());
		headWrite.close();
	}
	/**
	public void setChild(Commit kid) {
		this.child=kid;
	}*/
	
	public String getFileName() {
		return commitFileName;
	}
	
	private void clearFile(String fileName) throws FileNotFoundException {
		File file = new File(fileName);
		PrintWriter writer = new PrintWriter(file);
		writer.print("");
		writer.close();
	}
	
	private String getLine(File fileName,int lineNum) throws IOException {
		BufferedReader reader=new BufferedReader(new FileReader(fileName));
		String line="";
		for(int i=0;i<lineNum;i++) {
			line=reader.readLine();
		}
		reader.close();
		return line;
	}
	/**
	private ArrayList<String[]> getFileContentTokens(String fileName) throws IOException{
		ArrayList<String[]> result=new ArrayList<String[]>();
		File file=new File(fileName);
		BufferedReader reader=new BufferedReader(new FileReader(file));

		String line=reader.readLine();
		
		while(line!=null) {
			result.add(line.split(" "));//each arraylist entry is og name and sha1 name
			//first in array is og name, second is sha 1 anem
			line=reader.readLine();
		}
		
		return result;
	}//when reading blob in a tree file, [0] is tree or blob, then colon, then sha'd file name, then og name
	*/
	public String getTree() {
		return ptree;
	}
	
	public String getDate() {
		return date;
	}
	
	public String getLoc() {
		return ptree;
	}
	
	public String getContents() throws NoSuchAlgorithmException {
		String total=ptree;
		if(parent==null) {
			total+="\n";
		}else {
			total+="\n"+parent;
		}
		
		if(child==null) {
			total+="\n";
		}else {
			total+="\n"+child;
		}
		
		total+="\n"+author+"\n"+date+"\n"+summary;
		
		return total;

	}
	
	public String getHash() throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-1"); //generates sha1
		byte[] messageDigest = md.digest((ptree+parent+author+date+summary).getBytes());
		BigInteger no = new BigInteger(1, messageDigest);
		String hashtext = no.toString(16);
		while (hashtext.length() < 40) {
            hashtext = "0" + hashtext;
		}
        return hashtext;
	}
	
	private String fileContent(String path) throws IOException {
		Path treePath= Paths.get(path);
		return (Files.readString(treePath));
	}
		
	public void create() throws IOException, NoSuchAlgorithmException {
        
        File file = new File("./objects/" + commitFileName);
        file.createNewFile();
        
        Path p = Paths.get("./objects/" + commitFileName);
		try {
            //Files.writeString(p, content, StandardCharsets.ISO_8859_1); //creates file
            Files.writeString(p, getContents());
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
}
