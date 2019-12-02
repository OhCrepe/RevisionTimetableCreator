package main.gui;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import main.Constants;
import main.Exam;
import main.ExamSelect;
import main.Storage;

public class ExamPanel extends JPanel{

	static final int ADD = 0; //Add value
	static final int REMOVE = 1; //Remove value
	
	/**
	 * Constructor for ExamPanel
	 */
	public ExamPanel() {
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		//Creates the title of the panel
		JLabel label = new JLabel("Add or Remove Exams");
		label.setFont(new Font(label.getFont().getFontName(), Font.BOLD, label.getFont().getSize()+2));
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		label.setBorder(new EmptyBorder(0, 0, 10, 0));
		add(label);
		setBorder(new EmptyBorder(30, 0, 0, 0));
		
		/*
		//The input for the panel
		JTextField nameInput = new JTextField(10);
		JPanel namePanel = new JPanel(new FlowLayout());
		namePanel.add(new JLabel("Exam Name: "));
		namePanel.add(nameInput);
		add(namePanel);
		
		JTextField dateInput = new JTextField(10);
		JPanel datePanel = new JPanel(new FlowLayout());
		datePanel.add(new JLabel("Exam Date: "));
		datePanel.add(dateInput);
		add(datePanel);
		*/
		
		//Input panel with grid layout
		JPanel inputPanel = new JPanel(new GridLayout(3, 2));
		
		JLabel codeLabel = new JLabel("Exam/Module Code: ");
		codeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		inputPanel.add(codeLabel);
		
		JTextField codeInput = new JTextField(10);
		codeInput.setDocument(new LengthRestrictedDocument(8));
		inputPanel.add(codeInput);
		
		JLabel nameLabel = new JLabel("Exam Name: ");
		nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		inputPanel.add(nameLabel);
		
		JTextField nameInput = new JTextField(10);
		nameInput.setDocument(new LengthRestrictedDocument(40));
		inputPanel.add(nameInput);
		
		JLabel dateLabel = new JLabel("Exam Date: ");
		dateLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		inputPanel.add(dateLabel);
			
		JFormattedTextField dateInput = new JFormattedTextField(Constants.DATE_FORMAT);
		dateInput.setText("" + Constants.DATE_FORMAT.format(new Date(System.currentTimeMillis())));
		inputPanel.add(dateInput);
		
		add(inputPanel);
		 
		//Commands for the panel
		JPanel buttonPanel = new JPanel(new FlowLayout());
		JButton addExam = new JButton("Create Exam");
		JButton removeExam = new JButton("Remove Exam");
		addExam.addActionListener(new ExamHandler(nameInput, codeInput, dateInput, ADD));
		removeExam.addActionListener(new ExamHandler(nameInput, codeInput, dateInput, REMOVE));
		buttonPanel.add(addExam);
		buttonPanel.add(removeExam);
		add(buttonPanel);
		
	}
	
}

class ExamHandler implements ActionListener{

	private JTextField nameField, codeField;
	private JFormattedTextField dateField;
	private int action;
	
	public ExamHandler(JTextField nameField, JTextField codeField, JFormattedTextField dateField, int action) {
		this.nameField = nameField;
		this.dateField = dateField;
		this.codeField = codeField;
		this.action = action;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(codeField.getText().length() < 1) {
			JOptionPane.showMessageDialog(null, "Module code field should not be left blank");
		}
		
		if(nameField.getText().length() < 1) {
			JOptionPane.showMessageDialog(null, "Exam name field should not be left blank");
			return;
		}
		
		if(dateField.getText().length() < 1) {
			JOptionPane.showMessageDialog(null, "Exam date field should not be left blank\nDate format should be dd-MM-yyyy");
			return;
		}
		
		if(nameField.getText().contains(",") || nameField.getText().contains("\\") || nameField.getText().contains("\"")){
			JOptionPane.showMessageDialog(null, "Make sure the exam name contains no forbidden characters   ,\"\\");
			return;
		}
		
		if(codeField.getText().contains(",") || codeField.getText().contains("\\") || codeField.getText().contains("\"")){
			JOptionPane.showMessageDialog(null, "Make sure the exam code contains no forbidden characters   ,\"\\");
			return;
		}
		
		Exam exam = null;
		try {
			exam = new Exam(codeField.getText(), nameField.getText(), Constants.DATE_FORMAT.parse(dateField.getText()), false);
		} catch (ParseException e2) {
			JOptionPane.showMessageDialog(null, "There was a problem creating this exam with the given date;\n" + 
					"is the date given in the correct format?");
		}
		if(Storage.exists(exam) && action == ExamPanel.ADD) {
			JOptionPane.showMessageDialog(null, "An exam called " + exam.getTitle() + " with code " +
						exam.getModCode() + " already exists.");
			return;
		}else if(!Storage.exists(exam) && action == ExamPanel.REMOVE) {
			JOptionPane.showMessageDialog(null, "An exam called " + exam.getTitle() + " with code " +
						exam.getModCode() + " does not exist.");
			return;
		}
		
		if(action == ExamPanel.REMOVE) {
			int confirm = JOptionPane.showConfirmDialog(null, "Deleting an exam will delete all tasks associated with it." +
												"\nWould you like to continue?");
			if(confirm != 0)return;
		}
		
		if(action == ExamPanel.ADD)Storage.addExam(exam);
		if(action == ExamPanel.REMOVE)Storage.removeExam(exam);
		
		TaskPanel.examDropDown.removeAllItems();
		for(Exam ex: Storage.exams) {
			TaskPanel.examDropDown.addItem(new ExamSelect(ex));
		}
		
	}
	
}
