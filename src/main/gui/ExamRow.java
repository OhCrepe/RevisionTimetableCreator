package main.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import main.Constants;
import main.Exam;

public class ExamRow extends JPanel{

	public Exam exam;
	
	public ExamRow(Exam e) {
		
		this.exam = e;
		
		if(exam.isComplete()) {
			setBackground(new Color(138, 255, 130));
		}else if(exam.getDate().before(Constants.TODAY)) {
			setBackground(new Color(255, 142, 127));
		}else if(exam.getDate().equals(Constants.TODAY)) {
			setBackground(new Color(255, 252, 130));
		}
		
		setLayout(new GridLayout(1, 3));
		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(), new EmptyBorder(0, 12, 0, 24)));
		setPreferredSize(new Dimension(this.getWidth(), 75));
		
		JLabel modCode = new JLabel(exam.getModCode());
		modCode.setHorizontalAlignment(SwingConstants.LEFT);
		add(modCode);
		
		JLabel title = new JLabel(exam.getTitle());
		title.setHorizontalAlignment(SwingConstants.LEFT);
		add(title);
		
		JLabel dateLabel = new JLabel(Constants.DATE_FORMAT.format(exam.getDate()));
		dateLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		add(dateLabel);
		
	}
	
}
