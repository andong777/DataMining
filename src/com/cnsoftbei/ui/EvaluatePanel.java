package com.cnsoftbei.ui;

import static com.cnsoftbei.share.Component.evaluateBox;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.cnsoftbei.analysis.Tools;
import com.cnsoftbei.share.Component;
import com.cnsoftbei.share.Data;
import com.cnsoftbei.ui.material.BorderedJPanel;

public class EvaluatePanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 4564478696444557944L;
	private BorderedJPanel classifypnl = null;
	private BorderedJPanel clusterpnl = null;
	private JTextArea area = null;
	private JButton button1 = null;
	private JButton button2 = null;
	private JButton button3 = null;
	private JButton button4 = null;
	
	public EvaluatePanel(){
		setLayout(new BorderLayout());
		classifypnl = new BorderedJPanel("分类模型评估");
		clusterpnl = new BorderedJPanel("聚类模型评估");
		button1 = new JButton("MAE");
		button2 = new JButton("MSE");
		button3 = new JButton("NMSE");
		button4 = new JButton("总体评估");
		if(Data.names!=null){
			evaluateBox = new JComboBox<String>(Data.names);
		}else{
			evaluateBox = new JComboBox<String>();
		}
		classifypnl.add(new JLabel("属性："));
		classifypnl.add(evaluateBox);
		classifypnl.add(button1);
		classifypnl.add(button2);
		classifypnl.add(button3);
		clusterpnl.add(button4);
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,2));
		panel.add(classifypnl);
		panel.add(clusterpnl);
		add(panel,BorderLayout.NORTH);
		
		area = new JTextArea();
		area.setEditable(false);
		add(area,BorderLayout.CENTER);
		
		button1.addActionListener(this);
		button2.addActionListener(this);
		button3.addActionListener(this);
		button4.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		String colname = (String) evaluateBox.getSelectedItem();
		if(colname == null||colname.equals(""))
			return;
		String result = null;
		if(e.getSource()==button1){
			result = Tools.MAE(Data.tablename, colname);
		}else if(e.getSource()==button2){
			result = Tools.MSE(Data.tablename, colname);
		}else if(e.getSource()==button3){
			result = Tools.NMSE(Data.tablename, colname);
		}else if(e.getSource()==button4){
			result = Tools.Evaluate();
		}
		area.setText(result);
	}
}
