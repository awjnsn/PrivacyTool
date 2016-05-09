import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class PrivacyTool {
	//Opens panel
	public static void main(String[] args) {
		JFrame f = new JFrame("Privacy Tool");
		f.setSize(392, 512);
		f.setLocation(100, 100);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setContentPane(new PrivacyPanel());
		f.setVisible(true);
		f.setResizable(false);
	}
}

class PrivacyPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	JFileChooser fileFinder = new JFileChooser(); //File opener
	File[] selectedFiles; //Array of files selected by the user
	File currentFile; //File currently being previewed
	String[] fileNames; //Array of strings of the names of the selected files
	JLabel currentSelection; //Label that displays the name of the currently selected file
	JLabel imageArea; //Label that displays the preview image of the currently selected file
	int currentPos = 0; //Position in the selectedFiles array that currentFile will be taken from

	//Panel that holds the preview image and buttons
	public PrivacyPanel() {
		setLayout(null); //Allows buttons to be places manually

		JButton chooseFileButton = new JButton("Choose File(s)"); //Button to open file selection menu
		JButton leftButton = new JButton("Left"); //Button to move left in the list of files
		JButton rightButton = new JButton("Right"); //Button to move right in the list of files
		JButton singleDeleteButton = new JButton("Delete One"); //Button to remove metadata from the current image
		JButton allDeleteButton = new JButton("Delete All"); //Button to remove metadata from all images in the array selectedFiles
		currentSelection = new JLabel("Please select a file / files.");
		imageArea = new JLabel();
		
		//Sets button sizes based on operating system due to operating systems having different built in buttons
		if(System.getProperty("os.name").equals("Mac OS X")){ 
			chooseFileButton.setBounds(8, 448, 128, 32);
			leftButton.setBounds(8, 392, 128, 32);
			rightButton.setBounds(256, 392, 128, 32);
			singleDeleteButton.setBounds(132, 392, 128, 32);
			allDeleteButton.setBounds(132, 416, 128, 32);
			currentSelection.setBounds(136, 448, 256, 32);
			imageArea.setBounds(16, 16, 360, 360);
		}
		else{ //If it is another operating system
			chooseFileButton.setBounds(5, 440, 128, 32);
			leftButton.setBounds(5, 370, 128, 32);
			rightButton.setBounds(253, 370, 128, 32);
			singleDeleteButton.setBounds(129, 370, 128, 32);
			allDeleteButton.setBounds(132, 405, 122, 32);
			currentSelection.setBounds(133, 440, 256, 32);
			imageArea.setBounds(13, 4, 360, 360);
		}

		//Adds action listeners to all buttons
		chooseFileButton.addActionListener(new chooseFileListener());
		leftButton.addActionListener(new leftListener());
		rightButton.addActionListener(new rightListener());
		singleDeleteButton.addActionListener(new singleDeleteListener());
		allDeleteButton.addActionListener(new allDeleteListener());

		//Sets the image preview area as a different color than the surrounding panel
		imageArea.setOpaque(true);
		imageArea.setBackground(Color.LIGHT_GRAY);

		//Adds all buttons and labels to the panel
		add(chooseFileButton);
		add(currentSelection);
		add(leftButton);
		add(rightButton);
		add(singleDeleteButton);
		add(allDeleteButton);
		add(imageArea);

		fileFinder.setMultiSelectionEnabled(true); //Allows the users to select multiple files
		fileFinder.setFileFilter(new FileNameExtensionFilter("JPEG Images", "jpg")); //Allows the user to only select jpeg files
	}
	
	//Sets the current file to the file at the specified index in the file array
	//Updates the preview image
	//Updates the current selection text
	public void updatePreview() {
		currentFile = selectedFiles[currentPos]; //Sets the currentFile to the file from the selected location in the selectedFiles array
		Image img = new ImageIcon(currentFile.toString()).getImage(); //Reads image from the selected file
		Image scaledImg = img.getScaledInstance(360, 360, Image.SCALE_FAST); //Scales the image to fit the preview area
		imageArea.setIcon(new ImageIcon(scaledImg)); //Displays the selected image in the preview area
		currentSelection.setText(currentFile.getName()); //Displays the name of the currently selected file
	}
	//Removes exit data from file by rewriting over file with only image data
	public void removeExif(File f) {
		try {
			BufferedImage b = ImageIO.read(f); //Reads in the file from the argument
			ImageIO.write(b, "jpg", f); //Overwrites the selected file with only the image data, stripping the metadata
		} catch (Exception e) { //Catches issues reading the file in
			System.out.println("Privacy tool failed to clean the image!");
		}
	}
	//Opens file selector when clicked
	private class chooseFileListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int returnValue = fileFinder.showOpenDialog(PrivacyPanel.this);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				selectedFiles = fileFinder.getSelectedFiles();
			}
			currentPos = 0;
			updatePreview();
		}
	}
	//Moves left in the array of files, cycles to end of array at position 0
	private class leftListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (currentPos == 0) {
				currentPos = selectedFiles.length - 1;
			} else {
				currentPos--;
			}
			updatePreview();
		}
	}
	//Moves right in the array of files, cycles to 0 at position length - 1
	private class rightListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (currentPos == selectedFiles.length - 1) {
				currentPos = 0;
			} else {
				currentPos++;
			}
			updatePreview();
		}
	}
	//Removes exif data from currently selected file
	private class singleDeleteListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			removeExif(currentFile);
		}
	}
	//Removes exif data from all files in the file array, starting at position 0
	private class allDeleteListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			for (int i = 0; i < selectedFiles.length; i++) {
				removeExif(selectedFiles[i]);
			}
		}
	}
}