package main.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import main.Storage;

public class DisplayFrame extends JFrame{
	
	public static JTabbedPane output; //The output pane of the frame
	private JPanel displayPanel; //The display panel
	private JPanel outputPanel; //The output panel
	
	/**
	 * Constructor for the display frame, calls super constructor of JFrame
	 * @param title - Title of the frame
	 */
	public DisplayFrame(String title) {
		
		super(title);
		Storage.setDisplayFrame(this);
		addComponentsToFrame();
	}
	
	/**
	 * Sets up the DisplayFrame
	 */
	private void addComponentsToFrame(){
		
		setMinimumSize(new Dimension(800, 600));
		setMaximumSize(new Dimension(800, 600));
		
		displayPanel = new JPanel(new BorderLayout());

		JPanel examPanel = new ExamPanel();
		JPanel inputPanel = new JPanel(new BorderLayout());
		JPanel taskPanel = new TaskPanel();
		outputPanel = new JPanel();
				
		
		displayPanel.add(inputPanel, BorderLayout.EAST);
		inputPanel.add(examPanel, BorderLayout.NORTH);
		JPanel taskOrgPanel = new JPanel(new BorderLayout());
		taskOrgPanel.add(taskPanel, BorderLayout.NORTH);
		inputPanel.add(taskOrgPanel, BorderLayout.CENTER);
		
		output = new JTabbedPane();
		output.setPreferredSize(new Dimension(600, 600));
		output.setTabPlacement(JTabbedPane.TOP);
		output.add("Exams", new ExamDisplayPanel(this));
		output.addTab("Tasks", new TaskDisplayPanel(this));
		outputPanel.add(output);
		displayPanel.add(outputPanel, BorderLayout.WEST);
		add(displayPanel);
		
		setResizable(false);
		pack();
		
	}
	
	/**
	 * Used to refresh the output after input is given by the user
	 */
	public void refreshOutput() {
		displayPanel.remove(outputPanel);
		outputPanel.removeAll();
		output = new JTabbedPane();
		output.setPreferredSize(new Dimension(600, 600));
		output.setTabPlacement(JTabbedPane.TOP);
		output.add("Exams", new ExamDisplayPanel(this));
		output.addTab("Tasks", new TaskDisplayPanel(this));
		outputPanel.add(output);
		displayPanel.add(outputPanel, BorderLayout.WEST);
		pack();
	}
	
}
