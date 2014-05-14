package com.cnsoftbei.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.cnsoftbei.analysis.Tools;
import com.cnsoftbei.share.Data;

public class AssociatePanel extends JPanel {

	private static final long serialVersionUID = -6855929800245818809L;
	private JTextField text1 = null;
	private JTextField text2 = null;
	private JButton ok = null;
	private JTextArea area = null;
	
	public AssociatePanel(){
		
		setLayout(new BorderLayout());
		
		JPanel panel = new JPanel();
		panel.add(new JLabel("起始列号："));
		text1 = new JTextField(5);
		panel.add(text1);
		panel.add(new JLabel("结束列号："));
		text2 = new JTextField(5);
		panel.add(text2);
		ok = new JButton("确定");
		panel.add(ok);
		add(panel,BorderLayout.NORTH);
		
		area = new JTextArea();
		area.setEditable(false);
		add(area,BorderLayout.CENTER);
		
		ok.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				try {
					int col1 = Integer.parseInt(text1.getText());
					int col2 = Integer.parseInt(text2.getText());
					String result = Tools.Associate(Data.tablename, col1, col2);
					area.setText(result);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "输入数据非法！");
				}
			}
		});
		
		
	}
}
