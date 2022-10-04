import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import javax.swing.filechooser.FileSystemView;

public class Index {
	
	private HashMap<String, String> codes;

	public Index () throws IOException {
		codes = new HashMap<String, String>();
		initialize();
	}
	
	//creates objects and index.txt file
	public void initialize () throws IOException {
		
		new File("objects").mkdirs();
		new File("index.txt").createNewFile();
		
		/*
		switch (System.getProperty("os.name")) {
			case "Windows 10":
				new File(".\\objects").mkdirs();
				new File(".\\index.txt");
				break;
			case "Mac OS X":
				new File("./objects").mkdirs(); 
				new File("./index.txt");
				break;
			default:
				//should work for all UNIX systems but still have set up to easily implement
				new File("./objects").mkdirs();
				new File("./index.txt");
		}
		*/
	}
	
	public void remove(String fileName) throws IOException {// marks file to remove in index file
		codes.put("*deleted*", fileName);
		codes.remove(fileName);
		printHashMap();
	}
	public void edit(String fileName) throws IOException {// marks file to remove in index file
		codes.put("*edited*", fileName);
		codes.remove(fileName);
		printHashMap();
	}
	
	
	
	public void addBlob(String fileLoc) throws NoSuchAlgorithmException, IOException { //adds blob
		Blob blob = new Blob (fileLoc); //creates new blob
		codes.put(fileLoc, blob.getSha()); //adds blob to hash map
		printHashMap(); //updates index file
	}
	
	
	public void deleteBlob(String fileLoc) throws IOException { //deletes blob
		//overwrites file with dummy file to be deleted
		File myObj =  new File("./objects/" + codes.get(fileLoc) + ".txt");
		
		/*
		switch (System.getProperty("os.name")) {
		case "Windows 10":
			myObj =  new File(".\\objects\\" + codes.get(fileLoc) + ".txt");
			break;
		case "Mac OS X":
			myObj =  new File("./objects/" + codes.get(fileLoc) + ".txt");
			break;
		default:
			//should work for all UNIX systems but still have set up to easily implement
			myObj =  new File("./objects/" + codes.get(fileLoc) + ".txt");
		}
		*/

		myObj.delete(); //deletes it
		codes.remove(fileLoc);
		printHashMap();
	}
	
	public void printHashMap() throws IOException { //fixes index file based on current hashmap
		Writer output = new BufferedWriter(new FileWriter("index.txt"));;
		
		/*
		switch (System.getProperty("os.name")) {
		case "Windows 10":
			output = new BufferedWriter(new FileWriter(".\\index.txt"));
			break;
		case "Mac OS X":
			output = new BufferedWriter(new FileWriter("./index.txt"));
			break;
		default:
			//should work for all UNIX systems but still have set up to easily implement
			output = new BufferedWriter(new FileWriter("./index.txt"));
		}
		*/
		
		for (String s: codes.keySet()) {
			output.append(s + " : " + codes.get(s) + "\n");
		}
		output.close();
	}
	
	public void delete() {
		new File("objects").delete();
		new File("index.txt").delete();
	}
}
