package com.cnsoftbei.dialog;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.cnsoftbei.share.Variable;
import com.cnsoftbei.util.ExportHelper;

public class Export extends JDialog implements ActionListener {

	private static final long serialVersionUID = 3193768405824157223L;
	private JButton expInfo = null;
	private JButton expImage = null;
	private JButton expData = null;
	private JButton expReport = null;
	
	public Export(){
		setTitle("导出");
		setLayout(new GridLayout(4,1));
		expInfo = new JButton("导出摘要到info.txt");
		expImage = new JButton("导出图像到pics.tar.gz");
		expData = new JButton("导出数据到data.csv");
		expReport = new JButton("导出报告到report.pdf");
		expInfo.addActionListener(this);
		expImage.addActionListener(this);
		expData.addActionListener(this);
		expReport.addActionListener(this);
		JPanel panel = new JPanel();
		panel.add(expInfo);
		add(panel);
		panel = new JPanel();
		panel.add(expImage);
		add(panel);
		panel = new JPanel();
		panel.add(expData);
		add(panel);
		panel = new JPanel();
		panel.setLayout(new FlowLayout());
		panel.add(expReport);
		add(panel);
		
		setSize(Variable.scrWidth/4,Variable.scrHeight/4);
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(this.getFocusOwner());
		setVisible(true);
	}
	
	
	public void actionPerformed(ActionEvent e) {
		String varname = null;
		varname = JOptionPane.showInputDialog(null, "输入导出变量的名称:",
				"导出", JOptionPane.INFORMATION_MESSAGE);
		if(e.getSource()==expData){
			if (varname != null) {				
				ExportHelper.exportCSV(varname);
			}
		}else if(e.getSource()==expImage){
			ExportHelper.exportPictures();
		}else if(e.getSource() == expInfo){
			ExportHelper.exportTxt(varname, true, true, true);
		}else if(e.getSource() == expReport){
			ExportHelper.exportPDF(varname, true);
		}
		
	}
	
}
