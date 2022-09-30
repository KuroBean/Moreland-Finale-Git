import java.io.File;
import java.util.*;

import org.junit.Test;

import junit.framework.TestCase;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

class TreeTester{

	private TreeObject tree;
	
	void setUp() throws Exception {
		
		ArrayList<String> list = new ArrayList<String>();
		list.add("thing1");
		list.add("thing232");
		list.add("tieowjifo");
		
		String[] listee = new String[3];
		listee[0] = "jeiw";
		listee[1] = "fjieowfew";
		listee[2] = "fjieowjfewoijfew";
		
		//tree = new TreeObject(listee);
	}
	
	

	void tearDown() throws Exception {
		
		File f1 = new File("objects/" + tree.sha);
		f1.delete();
	}
	
	@Test
	void test(){
		assertTrue(tree.sha.length()==40);
	}

}
