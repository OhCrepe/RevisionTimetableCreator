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

public class ExamDisplayPanel extends JPanel{

	public DisplayFrame frame; //Frame this panel is attached to
	
	/**
	 * Constructor for the ExamDisplayPanel
	 * @param f - Frame this panel is attached to
	 */
	public ExamDisplayPanel(DisplayFrame f) {
		
		frame = f;
		ExamMouseListener examMouseListener = new ExamMouseListener(this);
		
		//setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setLayout(new BorderLayout());
		
		JPanel headerTitle = new JPanel(new GridLayout(1, 3));
		JScrollPane headScroll = new JScrollPane(headerTitle);
		headScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		headerTitle.setBorder(new EmptyBorder(8, 12, 8, 42));
		headerTitle.add(new JLabel("Exam/Module Code"));
		headerTitle.add(new JLabel("Exam Title"));
		JLabel dateLabel = new JLabel("Exam Date");
		dateLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		headerTitle.add(dateLabel);
		
		JPanel examList = new JPanel();
		JScrollPane examScroll = new JScrollPane(examList);
		examScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		examList.setLayout(new GridLayout(Storage.exams.size(), 1));
		
		for(Exam e: Storage.exams) {
			
			ExamRow examPanel = new ExamRow(e);
			examPanel.addMouseListener(examMouseListener);
			examList.add(examPanel);
			
		}
		
		add(headScroll, BorderLayout.NORTH);
		add(examScroll, BorderLayout.CENTER);
				
	}
	
}

class ExamMouseListener implements MouseListener{
	
	ExamDisplayPanel edp; //Exam display panel this is attached to
	
	/**
	 * Creates a new examMouseListener
	 * @param edp - Exam display panel this is attached to
	 */
	public ExamMouseListener(ExamDisplayPanel edp) {
		this.edp = edp;
	}

	@Override
	/**
	 * What to do when the panel in clicked on
	 */
	public void mouseClicked(MouseEvent e) {

		ExamRow source = (ExamRow)(e.getSource());
		Exam exam = source.exam;
		if(!exam.getDate().after(new Date(System.currentTimeMillis()))) {
			System.out.println(exam);
			exam.toggleComplete();
			edp.frame.refreshOutput();
		}
		
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
