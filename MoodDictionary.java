import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;


public class MoodDictionary {
	

	private	Scanner file;
	
	public MoodDictionary(AffectMap posMap,AffectMap negMap) throws FileNotFoundException{
		this.posMap = posMap;
		this.negMap = negMap;          				//constructor for MoodDictionary
		storeIrregulars();
	}
	
	private HashMap<Vector<String>,String> hmap = new HashMap<Vector<String>,String>();	
	

	private AffectMap posMap;
	private AffectMap negMap;
	
	
	
	public boolean containsWord(String w){
		if(posMap.containsWord(w)||negMap.containsWord(w)){   //checking if w is in positive and negative maps.
			return true;
		}
		return false;
	}
	
	
	public double getPositivity(String w){
		if(posMap.containsWord(w) && !negMap.containsWord(w)){
			return 1;											//if word is all positive return 1
		}
		else if (!posMap.containsWord(w) && negMap.containsWord(w)){
			
		}
		Vector<String> value = posMap.getAssociationsOfWord(w);
		Vector<String> value2 = negMap.getAssociationsOfWord(w);
		double numPos = value.size();							//if word contains positive and negative associations computing the positivity.
		double numNeg = value2.size();
		return numPos/(numNeg + numPos);
		
		
	}
	public double getNegativity(String w){
		if(!posMap.containsWord(w) && negMap.containsWord(w)){
			return 1;											//if word is all negative return 1
		}
		else if (posMap.containsWord(w) && !negMap.containsWord(w)){
		
			
		}
		Vector<String> value = posMap.getAssociationsOfWord(w);
		Vector<String> value2 = negMap.getAssociationsOfWord(w);
		double numPos = value.size();							//if word contains positive and negative associations computing the negativity.
		double numNeg = value2.size();
		return numNeg/(numPos + numNeg);
		
	}
	
	private String changeWord(String w){
		
		if (w.endsWith("thes")||w.endsWith("zes")||w.endsWith("ses")||w.endsWith("xes")||w.endsWith("ies")){
			w = w.substring(0,w.lastIndexOf("es"));				//first rule 
			if(containsWord(w)){
				return w;
			}	
		}
		else if (w.endsWith("s")){
			w = w.substring(0,w.lastIndexOf("s"));			//second rule
			 if (containsWord(w)){
				return w;
			}
		}
		
		else if (w.endsWith("ies")){
			w = w.substring(0,w.lastIndexOf("ies"));   //third rule
			w = w.concat("y");
			 if (containsWord(w)){
					return w;
				}
		}
		
		else if (w.endsWith("ing")){
			w = w.substring(0,w.lastIndexOf("ing"));				//rule 4 and 7 
			if(w.charAt(w.length()-1)== w.charAt(w.length()-2)){ 	//checking if letters at last and second last index are the same letter
				w = w.substring(0,w.length()-1);				
				
			}
			
			if (containsWord(w)){
				return w;
			}
		}
		
		else if (w.endsWith("ed")){
			w = w.substring(0,w.lastIndexOf("ed"));		//rule 5 and 8
			
			if(w.charAt(w.length()-1)== w.charAt(w.length()-2)){ //checking if letter at last and second last index are the same letter
				w = w.substring(0,w.length()-1);
				
			}
			if (containsWord(w)){
				return w;
			}
		}
		
		else if (w.endsWith("ied")){
			w = w.substring(0,w.lastIndexOf("ied"));
			w = w.concat("y");
			if (containsWord(w)){
				return w;
			}
		}
		
		return null;
	}
	
	
	
	private void storeIrregulars() throws FileNotFoundException{
		String root = null;
		file = new Scanner(new File ("irregular verbs.idx"));
		while(file.hasNextLine()){
			Vector<String> irregulars = new Vector<String>();
			StringTokenizer t = new StringTokenizer(file.nextLine());		//storing irregular verbs into hashmap
			root = t.nextToken();
			while(t.hasMoreTokens()){
				irregulars.add(t.nextToken());
			}
			hmap.put(irregulars, root);
		}
		
	}
	
	private String getIrregular(String w){
		
		if(hmap.containsValue(w)){
			return w;
		}
		Set<Vector<String>> keys = hmap.keySet();
		for(Vector<String> key: keys){					//
			if(key.contains(w)){
				return hmap.get(key);
			}
		}
		
		return null;
	}
	
		
	
	public String getRootForm(String w){
	
		if(containsWord(w)){
			
			return w;			//checking if word is in dictionary
		}
		
		else if (getIrregular(w) != null){
									
			return getIrregular(w);  //checking if word is irregular verb and returns its root form
		}
		else{
			
			return w = changeWord(w);			//checking  if word was changed by rules and root form is in dictionary
		}
		
		
	}
	
	
	public static void main (String args[]) throws FileNotFoundException{
		MoodDictionary dic = new MoodDictionary(new AffectMap("positive map tabbed.idx"), new AffectMap("negative map tabbed.idx"));
		System.out.println(dic.getPositivity("good"));
		System.out.println(dic.getNegativity("bad"));
		System.out.println(dic.getRootForm("running"));
		System.out.println(dic.getRootForm("good"));
		System.out.println(dic.getRootForm("awoken"));
		
	}

}
