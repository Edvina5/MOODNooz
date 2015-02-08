import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Vector;

public class AffectMap {
	
private	Scanner file;
	
private HashMap<String,Vector<String>> hmap = new HashMap<String,Vector<String>>();	
	
public AffectMap(String filename) throws FileNotFoundException{
	file = new Scanner(new File (filename));
	store();
	
}	

private void store(){
	String head = null;
	while(file.hasNextLine()){
		Vector<String>	associations = new Vector<String>();
		StringTokenizer t = new StringTokenizer(file.nextLine());
		head = t.nextToken();
		while(t.hasMoreTokens()){
			associations.add(t.nextToken());	
		}
		
		hmap.put(head, associations);
	}
}

public boolean containsWord(String word){
	if(hmap.containsKey(word)||hmap.containsValue(word)){
		return true;
	}
	else 
		return false;
}

public Vector<String> getAssociationsOfWord(String word){
	Vector<String> allwords = hmap.get(word);
	if(allwords == null){
		System.out.println("No associations with word\t" + word);
		return null;
	}
	return allwords;
}


public static void main(String Args[]) throws FileNotFoundException{
		AffectMap positiveMap = new AffectMap("negative map tabbed.idx");
		AffectMap negativeMap = new AffectMap("positive map tabbed.idx");
		System.out.println(positiveMap.getAssociationsOfWord("ready"));
		System.out.println(negativeMap.getAssociationsOfWord("intimidated"));
		
		
	}
}