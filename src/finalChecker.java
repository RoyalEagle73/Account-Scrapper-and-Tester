import java.io.IOException;

import javax.swing.JLabel;

public class finalChecker {

	String data;
	String file;
	JLabel outputLabel;
	
	finalChecker(String site, String fileAddress, JLabel label){
		this.data = site;
		this.file = fileAddress;
		this.outputLabel = label;
	}
	
	public void doFinalCheck() throws IOException {
		if(data == "Facebook") {
			try {
				facebookLoginCheck checker = new facebookLoginCheck(file, outputLabel);
				outputLabel.setText("Testing Started");
				checker.doCheck(outputLabel);
			} catch (Exception e) {
				System.out.println(e);
				outputLabel.setText("Unexpected Errors Ocurred, Please restart Software");
			}
		}
		else {
			try {
				outputLabel.setText("Testing Started");
				netflixLoginCheck checker = new netflixLoginCheck(file, outputLabel);
				checker.doCheck(outputLabel);
			} catch (Exception e) {
				System.out.println(e);
				outputLabel.setText("Unexpected Errors Ocurred, Please restart Software");
			}
		}
	}

}
