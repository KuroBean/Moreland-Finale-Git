import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;

public class Commit {

	private String parent;
	private String child;
	private String ptree;
	private String summary;
	private String author;
	private String date;
	
	public Commit ( String nsummary, String nauthor, Commit nparent) throws IOException {
		if (parent != null) {
			parent = nparent.getLoc();
		}
		else {
			parent = null;
		}
		child = null;
		
		
		summary = nsummary;
		author = nauthor;
		date = Calendar.getInstance().getTime().toString();
		
		//make tree obj
		TreeObject tree=new TreeObject();
		
	}
	
	public String getDate() {
		return date;
	}
	
	public String getLoc() {
		return "objects/" + getContents();
	}
	
	public String getContents() {
		return "" + ptree + "\n" + parent + "\n" + child + "\n" + author + "\n" + date + "\n" + summary;

	}
	
	public String getHash() throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-1"); //generates sha1
		byte[] messageDigest = md.digest(getContents().getBytes());
		BigInteger no = new BigInteger(1, messageDigest);
		String hashtext = no.toString(16);
		while (hashtext.length() < 40) {
            hashtext = "0" + hashtext;
		}
        return hashtext;
	}
		
	public void create() throws IOException, NoSuchAlgorithmException {
		
        
        File file = new File("./objects/" + getHash() + ".txt");
        file.createNewFile();
        
        Path p = Paths.get("./objects/" + getHash() + ".txt");
		try {
            //Files.writeString(p, content, StandardCharsets.ISO_8859_1); //creates file
            Files.writeString(p, getContents());
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
}
