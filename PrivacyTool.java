import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

//http://stackoverflow.com/questions/11922152/jfilechooser-to-open-multiple-txt-files
//Added file select 4-21, buttons added week earlier

public class PrivacyTool {
	public static void main(String[] args) {
		JFrame f = new JFrame("Privacy Tool");
		f.setSize(512, 512);
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
	//JLabel previewImageLabel;
	//ImageIcon previewImageIcon;
	
	public PrivacyPanel() {
		setLayout(null);
		
		JButton chooseFileButton = new JButton("Choose File(s)");
		JButton leftButton = new JButton("Left");
		JButton rightButton = new JButton("Right");
		JButton singleDeleteButton = new JButton("Delete One");
		JButton allDeleteButton = new JButton("Delete All");
		currentSelection = new JLabel("Please select a file / files.");
		
		chooseFileButton.setBounds(0,448,128,32);
		leftButton.setBounds(0,0,1,1);
		rightButton.setBounds(0,0,1,1);
		singleDeleteButton.setBounds(0,0,0,0);
		allDeleteButton.setBounds(0,0,0,0);
		currentSelection.setBounds(128,448,384,32);
		//previewImageLabel.setBounds(0,0,10,10);
		
		chooseFileButton.addActionListener(new chooseFileListener());
		leftButton.addActionListener(new leftListener());
		rightButton.addActionListener(new rightListener());
		singleDeleteButton.addActionListener(new singleDeleteListener());
		allDeleteButton.addActionListener(new allDeleteListener());
		
		add(chooseFileButton);
		add(currentSelection);
		add(leftButton);
		add(rightButton);
		add(singleDeleteButton);
		add(allDeleteButton);
		//add(previewImageLabel);
		
		f.setMultiSelectionEnabled(true);
		f.setFileFilter(new FileNameExtensionFilter("JPEG Images", "jpg"));
	}

	private class chooseFileListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int returnValue = f.showOpenDialog(PrivacyPanel.this);
			if(returnValue == JFileChooser.APPROVE_OPTION){
				selectedFiles = f.getSelectedFiles();
			}
			currentSelection.setText(selectedFiles[0].getName());
			currentFile = selectedFiles[0];
		}
	}
	
	private class leftListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
		}
	}
	
	private class rightListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
		}
	}
	
	private class singleDeleteListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
		}
	}
	
	private class allDeleteListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
		}
	}
}