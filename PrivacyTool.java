/**
 * Privacy Tool
 */

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
	JFileChooser fileFinder = new JFileChooser();
	File[] selectedFiles;
	File currentFile;
	String[] fileNames;
	JLabel currentSelection;
	JLabel imageArea;
	int currentPos = 0;

	//Panel that holds the preview image and buttons
	public PrivacyPanel() {
		setLayout(null);

		JButton chooseFileButton = new JButton("Choose File(s)");
		JButton leftButton = new JButton("Left");
		JButton rightButton = new JButton("Right");
		JButton singleDeleteButton = new JButton("Delete One");
		JButton allDeleteButton = new JButton("Delete All");
		currentSelection = new JLabel("Please select a file / files.");
		imageArea = new JLabel();

		chooseFileButton.setBounds(8, 448, 128, 32);
		leftButton.setBounds(8, 392, 128, 32);
		rightButton.setBounds(256, 392, 128, 32);
		singleDeleteButton.setBounds(132, 392, 128, 32);
		allDeleteButton.setBounds(132, 416, 128, 32);
		currentSelection.setBounds(136, 448, 256, 32);
		imageArea.setBounds(16, 16, 360, 360);

		chooseFileButton.addActionListener(new chooseFileListener());
		leftButton.addActionListener(new leftListener());
		rightButton.addActionListener(new rightListener());
		singleDeleteButton.addActionListener(new singleDeleteListener());
		allDeleteButton.addActionListener(new allDeleteListener());

		imageArea.setOpaque(true);
		imageArea.setBackground(Color.LIGHT_GRAY);

		add(chooseFileButton);
		add(currentSelection);
		add(leftButton);
		add(rightButton);
		add(singleDeleteButton);
		add(allDeleteButton);
		add(imageArea);

		fileFinder.setMultiSelectionEnabled(true);
		fileFinder.setFileFilter(new FileNameExtensionFilter("JPEG Images", "jpg"));
	}
	/*
	 * Sets the current file to the file at the specified index in the file array
	 * Updates the preview image
	 * Updates the current selection text
	 */
	public void updatePreview() {
		currentFile = selectedFiles[currentPos];
		Image img = new ImageIcon(currentFile.toString()).getImage();
		Image scaledImg = img.getScaledInstance(360, 360, Image.SCALE_FAST);
		imageArea.setIcon(new ImageIcon(scaledImg));
		currentSelection.setText(currentFile.getName());
	}
	//Removes exit data from file by rewriting over file with only image data
	public void removeExif(File f) {
		try {
			BufferedImage b = ImageIO.read(f);
			ImageIO.write(b, "jpg", f);
		} catch (Exception e) {
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