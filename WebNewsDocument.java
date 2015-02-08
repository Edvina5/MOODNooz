import java.net.*;
import java.io.*;
import java.util.regex.*;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class WebNewsDocument {
		
	private URL u;
	
	public WebNewsDocument(String URL) throws MalformedURLException{
		 u = new URL(URL);
		
	}

	
	public String getRawText() throws IOException{
		
		String input = null;
		BufferedReader in = new BufferedReader(new InputStreamReader (u.openStream()));
		StringBuffer inputLine = new StringBuffer();
		
		
		String line;
		while(( line = in.readLine())!= null)
		{
			input+=line+"/n";
		}
		in.close();
		return  input;
	}
	
		public String getURL(){	
		
			return u.toString();	
			
		}
	
		public String getWebSite(){
			return u.getHost().toString();	
		}
		
		
		public String getHeadlineText() throws IOException{
			String allText = new String();
			allText = getRawText();
			
			int firstIndex = allText.indexOf("<title>");
			int endIndex = allText.indexOf("</title>");
			allText= allText.substring(firstIndex +7, endIndex);
			if(allText.contains("-")){
			endIndex = allText.indexOf("-");
			allText = allText.substring(0,endIndex);
			}
			
			return allText;
		}
		
		public String getBodyText() throws IOException{
			String allText = getRawText();
			Document doc = Jsoup.parse(allText);
			Element cont = doc.select("div[id=content]").first();
			allText = cont.toString();
			
			int firstIndex = allText.indexOf("<p>");
			int endIndex = allText.lastIndexOf("</p>");
			allText= allText.substring(firstIndex +3, endIndex);
			
			
			
			allText = allText.replaceAll("\\<.*?>","");
			
			return allText;
		}


		public static void main(String args[]) throws IOException{
				
			WebNewsDocument doc = new WebNewsDocument("http://www.irishtimes.com/newspaper/breaking/2013/0205/breaking46.html");
			
			//System.out.println(doc.getURL());
			//System.out.println(doc.getWebSite());
			//System.out.println(doc.getRawText());
			//System.out.println(doc.getHeadlineText());
			System.out.println(doc.getBodyText());
			
	}

}


