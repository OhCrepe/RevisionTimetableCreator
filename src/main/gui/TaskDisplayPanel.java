package main.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import main.Exam;
import main.Storage;
import main.Task;

public class TaskDisplayPanel extends JPanel{

	public DisplayFrame frame;
	
	public TaskDisplayPanel(DisplayFrame f) {
		
		frame = f;
		TaskMouseListener taskMouseListener = new TaskMouseListener(this);
		
		//setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setLayout(new BorderLayout());
		
		JPanel headerTitle = new JPanel(new GridLayout(1, 3));
		JScrollPane headScroll = new JScrollPane(headerTitle);
		headScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		headerTitle.setBorder(new EmptyBorder(8, 12, 8, 42));
		headerTitle.add(new JLabel("Exam/Module Code"));
		headerTitle.add(new JLabel("Task"));
		JLabel dateLabel = new JLabel("Due Date");
		dateLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		headerTitle.add(dateLabel);
		
		JPanel taskList = new JPanel();
		JScrollPane taskScroll = new JScrollPane(taskList);
		taskScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		taskList.setLayout(new GridLayout(Storage.tasks.size(), 1));
		
		for(Task t: Storage.tasks) {
			
			TaskRow taskPanel = new TaskRow(t);
			taskPanel.addMouseListener(taskMouseListener);
			taskList.add(taskPanel);
			
		}
		
		add(headerTitle, BorderLayout.NORTH);
		add(taskScroll, BorderLayout.CENTER);
		
	}
	
}

class TaskMouseListener implements MouseListener{
	
	TaskDisplayPanel tdp;
	
	public TaskMouseListener(TaskDisplayPanel tdp) {
		this.tdp = tdp;
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		TaskRow source = (TaskRow)(e.getSource());
		Task task = source.task;
		task.toggleComplete();
		Storage.saveTasks();
		tdp.frame.refreshOutput();
		tdp.frame.output.setSelectedIndex(1);
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}

