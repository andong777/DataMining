package com.cnsoftbei.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.cnsoftbei.share.Data;
import com.cnsoftbei.ui.material.Blank;
import com.cnsoftbei.ui.material.BorderedJPanel;
import com.cnsoftbei.util.ExportHelper;

public class ExportPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 8953422929937996026L;
	private JPanel toppnl = null;
	private JLabel titlelbl = null;
	private JLabel label = null;
	private JTextField text = null;
	private JPanel mainpnl = null;
	private JPanel txtpnl = null;
	private JCheckBox summary = null;
	private JCheckBox head = null;
	private JCheckBox missing = null;
	private JButton txtbtn = null;
	private JPanel picspnl = null;
	private JButton picsbtn = null;
	private JPanel csvpnl = null;
	private JButton csvbtn = null;
	private JPanel pdfpnl = null;
	private JCheckBox haspic = null;
	private JButton pdfbtn = null;

	public ExportPanel() {

		setLayout(new BorderLayout());
		toppnl = new JPanel();
		toppnl.setLayout(new BorderLayout());
		titlelbl = new JLabel("选择数据的导出形式");
		titlelbl.setFont(new Font("微软雅黑", 0, 24));
		titlelbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		toppnl.add(titlelbl, BorderLayout.CENTER);
		JPanel pnl = new JPanel();
		pnl.setLayout(new FlowLayout(FlowLayout.RIGHT));
		label = new JLabel("输入导出变量的名称:");
		text = new JTextField(10);
		pnl.add(label);
		pnl.add(text);
		toppnl.add(pnl, BorderLayout.SOUTH);
		add(toppnl, BorderLayout.NORTH);

		mainpnl = new JPanel();
		mainpnl.setLayout(new GridLayout(1, 4));
		txtpnl = new BorderedJPanel("摘要");
		txtpnl.setLayout(new BoxLayout(txtpnl, BoxLayout.Y_AXIS));
		summary = new JCheckBox("概况");
		head = new JCheckBox("头部");
		missing = new JCheckBox("缺失值");
		txtbtn = new JButton("导出");
		txtpnl.add(new Blank(1));
		txtpnl.add(summary);
		txtpnl.add(new Blank(1));
		txtpnl.add(head);
		txtpnl.add(new Blank(1));
		txtpnl.add(missing);
		txtpnl.add(new Blank(2));
		txtpnl.add(txtbtn);
		txtpnl.add(new Blank(1));
		mainpnl.add(txtpnl);

		picspnl = new BorderedJPanel("图像");
		picspnl.setLayout(new BorderLayout());
		picsbtn = new JButton("导出");
		pnl = new JPanel();
		pnl.add(new Blank(5));
		pnl.add(picsbtn);
		pnl.add(new Blank(5));
		picspnl.add(pnl, BorderLayout.CENTER);
		mainpnl.add(picspnl);

		csvpnl = new BorderedJPanel("数据");
		csvpnl.setLayout(new BorderLayout());
		csvbtn = new JButton("导出");
		pnl = new JPanel();
		pnl.add(new Blank(5));
		pnl.add(csvbtn);
		pnl.add(new Blank(5));
		csvpnl.add(pnl, BorderLayout.CENTER);
		mainpnl.add(csvpnl);

		pdfpnl = new BorderedJPanel("报告");
		pdfpnl.setLayout(new BoxLayout(pdfpnl, BoxLayout.Y_AXIS));
		haspic = new JCheckBox("包含图像");
		pdfbtn = new JButton("导出");
		pdfpnl.add(new Blank(2));
		pdfpnl.add(new JPanel().add(haspic));
		pdfpnl.add(new Blank(2));
		pdfpnl.add(new JPanel().add(pdfbtn));
		pdfpnl.add(new Blank(2));
		mainpnl.add(pdfpnl);

		add(mainpnl, BorderLayout.CENTER);
		
		txtbtn.addActionListener(this);
		picsbtn.addActionListener(this);
		csvbtn.addActionListener(this);
		pdfbtn.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == txtbtn) {
			String varname = text.getText().trim();
			if (varname == null || varname.length() == 0) {
				JOptionPane.showMessageDialog(null, "请输入变量名！");
				return;
			}
			ExportHelper.exportTxt(varname, summary.isSelected(),
					head.isSelected(), missing.isSelected());
		} else if (e.getSource() == picsbtn) {
			ExportHelper.exportPictures();
		} else if (e.getSource() == csvbtn) {
			String varname = text.getText().trim();
			if (varname == null || varname.length() == 0) {
				JOptionPane.showMessageDialog(null, "请输入变量名！");
				return;
			}
			ExportHelper.exportCSV(varname);
		} else if (e.getSource() == pdfbtn) {
			String varname = text.getText().trim();
			if (varname == null || varname.length() == 0) {
				JOptionPane.showMessageDialog(null, "请输入变量名！");
				return;
			}
			boolean haspic_bool = haspic.isSelected()?true:false;
			ExportHelper.exportPDF(varname, haspic_bool);
		}
	}

}
