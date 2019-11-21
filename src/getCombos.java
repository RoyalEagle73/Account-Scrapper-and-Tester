import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.sun.corba.se.impl.orbutil.threadpool.TimeoutException;

class getCombos {
	Set<String> urls_to_check;
	Set<String> sites_to_check;
	Set<String> finalCombos;
	Set<String> finalSet;
	Grabber grabber;
	final WebClient webClient;
	String output;
	FileWriter fw;
	
	getCombos(){
		try {
			fw = new FileWriter("combos.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finalSet = new HashSet<String>();
		output = "";
		urls_to_check = new HashSet<String>();
		sites_to_check = new HashSet<String>();
		finalCombos = new HashSet<String>();
		sites_to_check.add("facebook");
		sites_to_check.add("netflix");
		sites_to_check.add("gmail");
		sites_to_check.add("10K+Combo");
		webClient = new WebClient(BrowserVersion.FIREFOX_60);
	}
	
	Set<String> getCombinations(){
		grabber = new Grabber(sites_to_check);
		urls_to_check=grabber.getFinalUrls();
		int done = 1;
		for(String url:urls_to_check) {
			for(String combo:retCombos(url)) {
				finalCombos.add(combo);
//				System.out.println(urls_to_check);
			}
			System.out.println(done+"/"+urls_to_check.size()+" Done");
			System.out.println("\n\n***************************************************\\n\\n\\n");
			done++;
		}
		return finalCombos;
	}
	
	public Set<String> retCombos(String url) {
		Set<String> tempCombos = new HashSet<String>();
		java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
		HtmlPage page = null;
		boolean continues = true;
		//Opening Page In webClient
		try {
			System.out.println("\n\n\n***************************************************");
			System.out.println("Opening "+url+ " to get Combos");
			page = webClient.getPage(url);
//			webClient.waitForBackgroundJavaScript(10000);
			System.out.println("Page Loaded");
		} catch (FailingHttpStatusCodeException | IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Could Not Load Page");
			continues  = false;
		}
	
		//grabbing URL's from Page
		if(continues) {
			System.out.println("Getting Source code");
			String temp = page.asXml();
			System.out.println("Getting Combos...");
			try {
				if(temp.contains("@") && temp.contains(".")&& temp.contains(":")) {
					tempCombos = extractCombos(temp);
				}
				else {
					System.out.println("Got "+ tempCombos.size()+ "Combos...");
					return tempCombos;
				}
			} catch(Exception e) {
				// TODO: handle exception
			}
			System.out.println("Got "+ tempCombos.size()+ "Combos...");
		}
		// Returning Urls
		return tempCombos;
	}
	
	private static Set<String> extractCombos(String text)
	{	
		System.out.println("Size of text is "+text.length());
	    Set<String> containedCombos = new HashSet<String>();
//	    System.out.println("2");
	    String urlRegex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"+"(:[\\w]+)";
//	    System.out.println("3");
	    Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
//	    System.out.println("4");
	    Matcher urlMatcher = pattern.matcher(text);
//	    System.out.println("5");
	    
	    while (urlMatcher.find())
	    {
//	    	System.out.println("6");
	    	try {
//	    		System.out.println("7");
	    	    containedCombos.add(text.substring(urlMatcher.start(0),
		                urlMatcher.end(0)));
//	    	    System.out.println("8");
	    	    } catch (Exception e) {
				// TODO: handle exception
			}
//	    	System.out.println("9");
	    }
//	    System.out.println("10");
	    
	    return containedCombos;
	}
	
	void finish() {
		finalSet = getCombinations();
		for(String combo: finalSet) {
			output += combo + "\n";
	}
		try {
			System.out.println("Writing Combos into file.");
			fw.write(output);
			System.out.println("Writing Combos Done.");
		} catch (IOException e) {
			System.out.println("Writing Failed");
		}finally {
			try {
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}