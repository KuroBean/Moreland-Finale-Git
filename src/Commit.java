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

	private Commit parent;
	private Commit child;
	private String ptree;
	private String summary;
	private String author;
	private String date;
	private String commitFileName;
	
	//INDEX PARAM ADDED TO CLEAR AFTER EVERY COMMIT CONSTRUCTED
	public Commit (Index dexy,String nsummary, String nauthor, Commit nparent) throws IOException, NoSuchAlgorithmException {
		if (nparent != null) {
			parent = nparent;
		}
		else {
			parent = null;
		} 
		child = null;
		
		summary = nsummary;
		author = nauthor;
		date = Calendar.getInstance().getTime().toString();
		
		//make tree obj
		String prevTree=null; 
		if(parent!=null) {
			prevTree=parent.getTree();
		}
		TreeObject newtree=new TreeObject(prevTree);
		ptree=newtree.treePath(); 
		commitFileName=getHash()+".txt";
		create();
		
		if(parent!=null) {
			parent.setChild(this); 
			parent.create();
		}
		dexy.clearIndex();
	}
	
	public void setChild(Commit kid) {
		this.child=kid;
	}
	
	public String getFileName() {
		return commitFileName;
	}
	
	private void clearFile(String fileName) throws FileNotFoundException {
		File file = new File(fileName);
		PrintWriter writer = new PrintWriter(file);
		writer.print("");
		writer.close();
	}
	/**
	private String getLine(File fileName,int lineNum) throws IOException {
		BufferedReader reader=new BufferedReader(new FileReader(fileName));
		String line="";
		for(int i=0;i<lineNum;i++) {
			line=reader.readLine();
		}
		reader.close();
		return line;
	}
	
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
			total+="\n"+parent.getFileName();
		}
		
		if(child==null) {
			total+="\n";
		}else {
			total+="\n"+child.getFileName();
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
