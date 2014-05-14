package com.cnsoftbei.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.cnsoftbei.analysis.Tools;
import com.cnsoftbei.dialog.DBDialog;
import com.cnsoftbei.dialog.InnerDialog;
import com.cnsoftbei.dialog.ReadDialog;
import com.cnsoftbei.error.Error;
import com.cnsoftbei.share.Data;
import com.cnsoftbei.ui.material.BorderedJPanel;

public class DataPanel extends JPanel {

	private static final long serialVersionUID = -5461102858395792425L;

	private BorderedJPanel importpnl = null;
	private JButton filebtn = null;
	private JButton dbbtn = null;
	private JButton innerbtn = null;

	private BorderedJPanel operatepnl = null;
	private ButtonGroup group = null;
	private JRadioButton summary = null;
	private JRadioButton head = null;
	private JRadioButton missing = null;
	private JRadioButton outliers = null;
	private JPanel mainpnl = null;

	public DataPanel() {

		setLayout(new BorderLayout());
		mainpnl = new JPanel();
		mainpnl.setLayout(new BorderLayout());
		add(mainpnl, BorderLayout.CENTER);
		initImport();
		initOperate();
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.add(importpnl);
		panel.add(operatepnl);
		add(panel, BorderLayout.NORTH);

	}

	private void initImport() {
		importpnl = new BorderedJPanel("数据源");
		filebtn = new JButton("文件...");
		dbbtn = new JButton("数据库...");
		innerbtn = new JButton("R数据集...");
		importpnl.add(filebtn);
		importpnl.add(dbbtn);
		importpnl.add(innerbtn);
		filebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReadDialog.open();
			}
		});
		dbbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new DBDialog();
			}
		});
		innerbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new InnerDialog();
			}
		});
	}

	private void initOperate() {
		operatepnl = new BorderedJPanel("操作");
		group = new ButtonGroup();
		summary = new JRadioButton("概况");
		head = new JRadioButton("内容");
		missing = new JRadioButton("缺失值");
		outliers = new JRadioButton("离群值");
		group.add(summary);
		group.add(head);
		group.add(missing);
		group.add(outliers);
		operatepnl.add(summary);
		operatepnl.add(head);
		operatepnl.add(missing);
		operatepnl.add(outliers);

		summary.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					mainpnl.removeAll();
					JTextArea text = new JTextArea();
					text.setEditable(false);
					mainpnl.add(new JScrollPane(text), BorderLayout.CENTER);
					String result = Tools.summary(Data.tablename);
					text.setText(result);
				} catch (Exception ex) {
					Error.showOtherErrorMessage();
				}
				paintAll();
			}
		});
		head.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					mainpnl.removeAll();
					JTextArea text = new JTextArea();
					text.setEditable(false);
					String result = Tools.head(Data.tablename);
					mainpnl.add(new JScrollPane(text));
					text.setText(result+"\n ... ...\n ... ...");
				} catch (Exception ex) {
					Error.showOtherErrorMessage();
				}
				paintAll();
			}
		});
		missing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainpnl.removeAll();
				BorderedJPanel tmppnl = new BorderedJPanel("处理方式");
				ButtonGroup tmpgrp = new ButtonGroup();
				JRadioButton del = new JRadioButton("直接删除");
				JRadioButton center = new JRadioButton("用中心趋势值填补缺失值");
				JRadioButton similar = new JRadioButton("用案例之间的相似性填补缺失值");
				tmpgrp.add(del);
				tmpgrp.add(center);
				tmpgrp.add(similar);
				tmppnl.add(del);
				tmppnl.add(center);
				tmppnl.add(similar);
				del.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Tools.DeleteSolve(Data.tablename);
					}
				});
				center.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Tools.CenterSolve(Data.tablename);
					}
				});
				similar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Tools.Solve(Data.tablename);
					}
				});
				mainpnl.add(tmppnl, BorderLayout.NORTH);
				JTextArea text = new JTextArea();
				text.setEditable(false);
				mainpnl.add(text, BorderLayout.CENTER);
				paintAll();
				String result = Tools.Missing(Data.tablename);
				text.setText(result);
			}
		});
		outliers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainpnl.removeAll();
				BorderedJPanel tmppnl = new BorderedJPanel("参数");
				JLabel label1 = new JLabel("列号:");
				final JTextField text1 = new JTextField(3);
				JLabel label2 = new JLabel("Factor:");
				final JTextField text2 = new JTextField(8);
				JButton ok = new JButton("确定");
				tmppnl.add(label1);
				tmppnl.add(text1);
				tmppnl.add(label2);
				tmppnl.add(text2);
				tmppnl.add(ok);
				mainpnl.add(tmppnl, BorderLayout.NORTH);
				final JTextArea text = new JTextArea();
				text.setEditable(false);
				mainpnl.add(new JScrollPane(text), BorderLayout.CENTER);
				paintAll();
				ok.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							int colnum = Integer.parseInt(text1.getText());
							String factor = text2.getText();
							String result = Tools.outliers(Data.tablename,
									colnum, factor);
							text.setText(result);
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(null, "输入数据非法！");
						}
					}
				});
			}
		});

	}

	private void paintAll() {
		paintAll(getGraphics());
	}
}
