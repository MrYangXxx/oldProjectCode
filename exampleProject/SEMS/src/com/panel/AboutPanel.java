package com.panel;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.SystemColor;
import java.awt.Font;

public class AboutPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public AboutPanel() {
		setBackground(SystemColor.activeCaptionBorder);
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("IF YOU HAVE ANY QUESTION FOR THIS");
		lblNewLabel.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD | Font.ITALIC, 16));
		lblNewLabel.setBounds(67, 69, 329, 73);
		add(lblNewLabel);
		
		JLabel lblNewLabel_2 = new JLabel("JUST CHANGE FOR YOURSELF");
		lblNewLabel_2.setFont(new Font("Sitka Small", Font.BOLD | Font.ITALIC, 15));
		lblNewLabel_2.setBounds(86, 154, 310, 47);
		add(lblNewLabel_2);

	}
}
