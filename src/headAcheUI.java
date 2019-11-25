import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.Choice;
import java.awt.Color;
import javax.swing.JFileChooser;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.UIManager;


public class headAcheUI {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					headAcheUI window = new headAcheUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public headAcheUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBackground(Color.WHITE);
		frame.getContentPane().setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 18));
		frame.setBounds(100, 100, 450, 565);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		JLabel title = new JLabel("LEECH");
		title.setHorizontalAlignment(0);
		title.setForeground(UIManager.getColor("ToolBar.dockingForeground"));
		title.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 25));
		title.setBounds(131, 0, 182, 27);
		frame.getContentPane().add(title);
		
		JLabel version = new JLabel("v3.0 Beta");
		version.setHorizontalAlignment(0);
		version.setForeground(UIManager.getColor("TextPane.selectionBackground"));
		version.setFont(new Font("Tahoma", Font.ITALIC, 11));
		version.setBackground(new Color(240, 240, 240));
		version.setBounds(189, 27, 62, 14);
		frame.getContentPane().add(version);
		
		JLabel lblScrapper = new JLabel("Step 1: SCRAPING");
		lblScrapper.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblScrapper.setBounds(147, 55, 166, 14);
		frame.getContentPane().add(lblScrapper);
		
		Choice ScrapChoice = new Choice();
		ScrapChoice.setName("Choose");
		ScrapChoice.add("Facebook");
		ScrapChoice.add("Netflix");
		ScrapChoice.setBounds(17, 103, 97, 27);
		ScrapChoice.setFont(new Font("Tahoma", Font.PLAIN, 13));
	    frame.getContentPane().add(ScrapChoice);
		
		JLabel lblTickServiceTo = new JLabel("Choose to scrape :");
		lblTickServiceTo.setForeground(new Color(255, 20, 147));
		lblTickServiceTo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblTickServiceTo.setBounds(7, 80, 133, 14);
		frame.getContentPane().add(lblTickServiceTo);
		
		JButton ScrapButton = new JButton("Scrape");
		ScrapButton.setBounds(147, 161, 133, 23);
		frame.getContentPane().add(ScrapButton);
		
		JLabel ScrapResult = new JLabel("Select Service to scrap and press Button to start.\n"
				+ "\nWait for next update :)");
		ScrapResult.setForeground(Color.BLUE);
		ScrapResult.setFont(new Font("Tahoma", Font.PLAIN, 9));
		ScrapResult.setHorizontalAlignment(SwingConstants.CENTER);
		ScrapResult.setBounds(7, 183, 417, 74);
		frame.getContentPane().add(ScrapResult);
		

		ScrapButton.addActionListener(new ActionListener(){ 
		    public void actionPerformed(ActionEvent e){
		    	try {
		    		getCombos get = new getCombos(ScrapChoice.getSelectedItem(),ScrapResult);
			    	get.finish();
				} catch (Exception e2) {
					ScrapResult.setText("Check Internet Connection");
				}
		    	
		    	ScrapResult.setForeground(Color.GREEN);
		    	ScrapResult.setText("Procedure Completed for " + ScrapChoice.getSelectedItem() + ", you can see combos in file Combos.txt.");
		    	}
		    } 
		);
		
		
		JLabel lblStepTesting = new JLabel("Step 2: TESTING");
		lblStepTesting.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblStepTesting.setBounds(149, 268, 143, 14);
		frame.getContentPane().add(lblStepTesting);
		
		JLabel lblChooseServiceTo = new JLabel("CHOOSE TO TEST FOR :");
		lblChooseServiceTo.setForeground(new Color(255, 20, 147));
		lblChooseServiceTo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblChooseServiceTo.setBounds(7, 297, 259, 14);
		frame.getContentPane().add(lblChooseServiceTo);
		
		JButton TestingButton = new JButton("Test");
		TestingButton.setBounds(147, 387, 119, 23);
		frame.getContentPane().add(TestingButton);

		
		JTextField FilePath = new JTextField();
		FilePath.setText("Choose Combo file...");
		    FilePath.setBounds(20, 355, 298, 21);
		    frame.getContentPane().add(FilePath);
		    FilePath.setColumns(10);
		         
		    JButton BrowseButton = new JButton("Browse");
		    BrowseButton.setBounds(328, 355, 87, 23);
		    frame.getContentPane().add(BrowseButton);
		    
		    JLabel TestingResult = new JLabel("Choose combo file and press button to start.\nWait for next update :)");
		    TestingResult.setHorizontalAlignment(SwingConstants.CENTER);
		    TestingResult.setForeground(Color.BLUE);
		    TestingResult.setFont(new Font("Tahoma", Font.PLAIN, 9));
		    TestingResult.setBounds(7, 436, 417, 74);
		    frame.getContentPane().add(TestingResult);
		    
		    Choice TestChoice = new Choice();
		    TestChoice.setName("Choose");
		    TestChoice.add("Facebook");
		    TestChoice.add("Netflix");
		    TestChoice.setBounds(20, 317, 97, 27);
		    TestChoice.setFont(new Font("Tahoma", Font.PLAIN, 13));
		    frame.getContentPane().add(TestChoice);
		    
		    TestingButton.addActionListener(new ActionListener() { 
			    public void actionPerformed(ActionEvent e) {
			    	finalChecker checker = new finalChecker(TestChoice.getSelectedItem(), FilePath.getText(), TestingResult);
			    	try {
			    		TestingResult.setText("testing Started...");
						checker.doFinalCheck();
					} catch (IOException e1) {
						TestingResult.setText("Unexpected Errors Ocurred, Please restart Software");
					}
			    } 
			});
			
		         
		    BrowseButton.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent e) {
		        JFileChooser fileChooser = new JFileChooser();
		 
		        // For File
		        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		 
		        fileChooser.setAcceptAllFileFilterUsed(false);
		 
		        int rVal = fileChooser.showOpenDialog(null);
		        if (rVal == JFileChooser.APPROVE_OPTION) {
		          FilePath.setText(fileChooser.getSelectedFile().toString());
		        }
		      }
		    });
		    
		    
	}
}
