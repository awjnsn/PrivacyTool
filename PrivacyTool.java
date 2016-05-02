import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.RandomAccessFile;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class PrivacyTool {
	public static void main(String[] args) {
		JFrame f = new JFrame("Privacy Tool");
		f.setSize(392, 512);
		f.setLocation(0, 0);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setContentPane(new PrivacyPanel());
		f.setVisible(true);
		f.setResizable(false);
	}
}

class PrivacyPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	JFileChooser f = new JFileChooser();
	File[] selectedFiles;
	File currentFile;
	String[] fileNames;
	JLabel currentSelection;
	JLabel imageArea;
	int currentPos = 0;
	
	public PrivacyPanel() {
		setLayout(null);
		
		JButton chooseFileButton = new JButton("Choose File(s)");
		JButton leftButton = new JButton("Left");
		JButton rightButton = new JButton("Right");
		JButton singleDeleteButton = new JButton("Delete One");
		JButton allDeleteButton = new JButton("Delete All");
		currentSelection = new JLabel("Please select a file / files.");
		imageArea = new JLabel();
		
		chooseFileButton.setBounds(8,448,128,32);//
		leftButton.setBounds(8,392,128,32);
		rightButton.setBounds(256,392,128,32);
		singleDeleteButton.setBounds(132,392,128,32);
		allDeleteButton.setBounds(132,416,128,32);
		currentSelection.setBounds(136,448,256,32);//
		imageArea.setBounds(16, 16, 360, 360);//
		
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
		
		f.setMultiSelectionEnabled(true);
		f.setFileFilter(new FileNameExtensionFilter("JPEG Images", "jpg"));
	}
	
	public void updatePreview(){
		currentFile = selectedFiles[currentPos];
		Image img = new ImageIcon(currentFile.toString()).getImage();
		Image scaledImg = img.getScaledInstance(360, 360, Image.SCALE_FAST);
		imageArea.setIcon(new ImageIcon(scaledImg));
		currentSelection.setText(currentFile.getName());
	}

	public void removeExif(File f){
		try{
			BufferedImage b = ImageIO.read(f);
			ImageIO.write(b, "jpg", f);
		}
		catch(Exception e){
			System.out.println("Privacy tool failed to clean the image!");
		}
	}
	
	private class chooseFileListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int returnValue = f.showOpenDialog(PrivacyPanel.this);
			if(returnValue == JFileChooser.APPROVE_OPTION){
				selectedFiles = f.getSelectedFiles();
			}
			currentPos = 0;
			updatePreview();
		}
	}
	
	private class leftListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(currentPos == 0){
				currentPos = selectedFiles.length - 1;
			}
			else{
				currentPos--;
			}
			updatePreview();
		}
	}
	
	private class rightListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(currentPos == selectedFiles.length - 1){
				currentPos = 0;
			}
			else{
				currentPos++;
			}
			updatePreview();
		}
	}
	
	private class singleDeleteListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			removeExif(currentFile);
		}
	}
	
	private class allDeleteListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			for(int i = 0; i < selectedFiles.length; i++){
				removeExif(selectedFiles[i]);
			}
		}
	}
}