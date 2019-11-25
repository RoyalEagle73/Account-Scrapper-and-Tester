import java.awt.Color;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JLabel;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

class getCombos{
	Set<String> urls_to_check;
	Set<String> sites_to_check;
	Set<String> finalCombos;
	Set<String> finalSet;
	Grabber grabber;
	final WebClient webClient;
	String output;
	FileWriter fw;
	JLabel resultLabel;
	
	getCombos(String myCombos, JLabel resultLabel){
		this.resultLabel = new JLabel();
		this.resultLabel = resultLabel;
		try {
			fw = new FileWriter("combos.txt");
		} catch (IOException e) {
		}
		finalSet = new HashSet<String>();
		output = "";
		urls_to_check = new HashSet<String>();
		sites_to_check = new HashSet<String>();
		sites_to_check.add(myCombos);
		this.finalCombos = new HashSet<String>();
		webClient = new WebClient(BrowserVersion.CHROME);
		webClient.getOptions().setThrowExceptionOnScriptError(false);
	}
	
	Set<String> getCombinations() throws NullPointerException{
		grabber = new Grabber(sites_to_check);
		urls_to_check=grabber.getFinalUrls(resultLabel);
		int done = 1;
		for(String url:urls_to_check) {
			for(String combo:retCombos(url)) {
				if(combo.length() > 1)
					finalCombos.add(combo);
			}
			System.out.println(done+"/"+urls_to_check.size()+" Done");
			System.out.println("\n\n***************************************************\\n\\n\\n");
			done++;
		}
		return finalCombos;
	}
	
	public Set<String> retCombos(String url) throws NullPointerException {
//		WebClient webClient = new WebClient();
		Set<String> tempCombos = new HashSet<String>();
		tempCombos.add("");
		java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
		HtmlPage page = null;
		boolean continues = true;
		//Opening Page In webClient
		try {
			System.out.println("\n\n\n***************************************************");
			resultLabel.setText("Opening "+url+ " to get Combos");
			System.out.println("Opening "+url+ " to get Combos");
			page = webClient.getPage(url);
			System.out.println("Page Loaded");
		} catch (Exception e) {
			System.out.println("Could Not Load Page" + e +"\n Check Internet Connection");
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
					resultLabel.setText("Got "+ tempCombos.size()+ "Combos...");
					return tempCombos;
				}
			} catch(Exception e) {}
			System.out.println("Got "+ tempCombos.size()+ "Combos...");
		}
		// Returning Urls
		return tempCombos;
	}
	
	private static Set<String> extractCombos(String text) throws TimeoutException
	{	
		System.out.println("Size of text is "+text.length());
	    Set<String> containedCombos = new HashSet<String>();

	    String urlRegex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"+"(:[\\w]+)";
	    Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
	    Matcher urlMatcher = pattern.matcher(text);
	    
	    while (urlMatcher.find())
	    {
	    	try {
	    	    containedCombos.add(text.substring(urlMatcher.start(0),
		                urlMatcher.end(0)));
	    	    } catch (Exception e) {
			}
	    }
	    return containedCombos;
	}
	
	void finish() {
		finalSet = getCombinations();
		for(String combo: finalSet) {
			output += combo + "\n";
	}
		try {
//			resultLabel.setText("Writing Combos into file.");
			fw.write(output);
//			resultLabel.setText("Check combos.txt file in Output folder. Scrapping done.");
		} catch (IOException e) {
//			resultLabel.setText("Writing Failed");
		}finally {
			try {
				fw.close();
			} catch (IOException e) {}
		}
	}
	
}