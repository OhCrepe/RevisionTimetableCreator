package main.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
import main.Task;

public class TaskPanel extends JPanel{
	
	static final int ADD = 0;
	static final int REMOVE = 1;
	public static JComboBox<ExamSelect> examDropDown;
	
	public TaskPanel(){
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JLabel label = new JLabel("Add or Remove Tasks");
		label.setFont(new Font(label.getFont().getFontName(), Font.BOLD, label.getFont().getSize()+2));
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		label.setBorder(new EmptyBorder(0, 0, 10, 0));
		add(label);
		setBorder(new EmptyBorder(30, 0, 0, 0));
		
		JPanel inputPanel = new JPanel(new GridLayout(3, 2));
		JLabel nameLabel = new JLabel("Task Name: ");
		nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		inputPanel.add(nameLabel);
		
		JTextField nameField = new JTextField(10);
		nameField.setDocument(new LengthRestrictedDocument(40));
		inputPanel.add(nameField);
		
		JLabel examLabel = new JLabel("Exam/Module Code: ");
		examLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		inputPanel.add(examLabel);
				
		examDropDown = new JComboBox<ExamSelect>();
		examDropDown.setMaximumSize(new Dimension(100, examDropDown.getPreferredSize().height));
		for(Exam e: Storage.exams) {
			examDropDown.addItem(new ExamSelect(e));
		}
		inputPanel.add(examDropDown);
		
		JLabel dateLabel = new JLabel("Due Date: ");
		dateLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		inputPanel.add(dateLabel);
			
		JFormattedTextField dateInput = new JFormattedTextField(Constants.DATE_FORMAT);
		dateInput.setText("" + Constants.DATE_FORMAT.format(new Date(System.currentTimeMillis())));
		inputPanel.add(dateInput);

		add(inputPanel);
		
		//Commands for the panel
		JPanel buttonPanel = new JPanel(new FlowLayout());
		JButton addTask = new JButton("Create Task");
		JButton removeTask = new JButton("Remove Task");
		addTask.addActionListener(new TaskHandler(nameField, examDropDown, dateInput, ADD));
		removeTask.addActionListener(new TaskHandler(nameField, examDropDown, dateInput, REMOVE));
		buttonPanel.add(addTask);
		buttonPanel.add(removeTask);
		add(buttonPanel);
						
	}
	
}

class TaskHandler implements ActionListener{

	private JTextField taskField;
	private JComboBox<ExamSelect> examField;
	private JFormattedTextField dateField;
	private int action;
	
	public TaskHandler(JTextField taskField, JComboBox<ExamSelect> examField, JFormattedTextField dateField, int action) {
		this.taskField = taskField;
		this.examField = examField;
		this.dateField = dateField;
		this.action = action;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {

		if(taskField.getText().length() < 1) {
			JOptionPane.showMessageDialog(null, "Task field should not be left blank");
		}

		if(dateField.getText().length() < 1) {
			JOptionPane.showMessageDialog(null, "Task due date field should not be left blank\nDate format should be dd-MM-yyyy");
			return;
		}
		
		if(taskField.getText().contains(",") || taskField.getText().contains("\\") || taskField.getText().contains("\"")){
			JOptionPane.showMessageDialog(null, "Make sure the task contains no forbidden characters   ,\"\\");
			return;
		}
		
		Task task = null;
		try {
			task = new Task(((ExamSelect)examField.getSelectedItem()).getExam(), taskField.getText(), Constants.DATE_FORMAT.parse(dateField.getText()), false);
		} catch (ParseException e2) {
			JOptionPane.showMessageDialog(null, "There was a problem creating this exam with the given date;\n" + 
					"is the date given in the correct format?");
		}
		if(Storage.exists(task) && action == TaskPanel.ADD) {
			JOptionPane.showMessageDialog(null, "The task \"" + task.getTask() + "\" for exam with code " +
						task.getExam().getModCode() + " already exists.");
			return;
		}else if(!Storage.exists(task) && action == TaskPanel.REMOVE) {
			JOptionPane.showMessageDialog(null, "The task \"" + task.getTask() + "\" for exam with code " +
						task.getExam().getModCode() + " does not exist.");
			return;
		}
		
		if(action == TaskPanel.ADD)Storage.addTask(task);
		if(action == TaskPanel.REMOVE)Storage.removeTask(task);
		
		DisplayFrame.output.setSelectedIndex(1);
		
	}
	
}