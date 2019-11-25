import java.awt.Color;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JLabel;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class url_leech{
	
	String query;
	String final_url;
	String temp_url;
	boolean continues = true;
	List<HtmlAnchor> anchor_list;
	Set<String> url_list;
	final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_60);
	
	
	url_leech(String q) {
		this.query = q;
		this.final_url = "https://pastebin.com/search?q=" + query ;
	}
	
	public Set<String> ret_url(JLabel resultLabel) {
//		webClient.getOptions().setThrowExceptionOnScriptError(true);
		java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
//		webClient.getOptions().setJavaScriptEnabled(true);
//		webClient.getOptions().setCssEnabled(true);
		HtmlPage page = null;
		//Opening Page In webClient
		try {
			System.out.println("Opening URL " + final_url);
			page = webClient.getPage(final_url);
			System.out.println("Page Loaded");
		} catch (FailingHttpStatusCodeException | IOException e) {
			System.out.println("Could Not Load Page for " + query);
			resultLabel.setForeground(Color.RED);
			resultLabel.setText("Check Internet Connection");
			continues = false;
		}
	
		//grabbing URL's from Page
		if(continues) {
			System.out.println("Getting Source code");
			String temp = page.asXml();
			System.out.println("Returning Urls");
			url_list = extractUrls(temp);
		}
		
		// Returning Urls
		return url_list;
	}
	
	private static Set<String> extractUrls(String text)
	{
	    Set<String> containedUrls = new HashSet<String>();
	    String urlRegex = "((https):((//)|(\\\\))+Pastebin.com/[\\w\\d]{8})";
	    Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
	    Matcher urlMatcher = pattern.matcher(text);

	    while (urlMatcher.find())
	    {
	        containedUrls.add(text.substring(urlMatcher.start(0),
	                urlMatcher.end(0)));
	    }

	    return containedUrls;
	}
	
}