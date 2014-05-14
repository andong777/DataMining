package com.cnsoftbei.ui;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.cnsoftbei.analysis.FenLei;
import com.cnsoftbei.share.Data;
import com.cnsoftbei.share.Variable;
import com.cnsoftbei.ui.material.Blank;
import com.cnsoftbei.ui.material.BorderedJPanel;
import static com.cnsoftbei.share.Component.*;

public class ClassifyPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = -3062000199026143551L;
	private JSplitPane spin = null;
	private JSplitPane spout = null;
	private JPanel left = null;
	private JPanel buttons = null;
	private JButton cutbtn = null;
	private JButton autocutbtn = null;
	private JButton selfcutbtn = null;
	private ButtonGroup group = null;
	private JRadioButton button1 = null;
	private JRadioButton button2 = null;
	private JRadioButton button3 = null;
	private JButton start = null;
	private BorderedJPanel center = null;
	private JTextArea area = null;
	private JTextField text = null;
	
	private BorderedJPanel right = null;
	private JLabel image = null;
	private Image img = null;
	private Image scaleImg = null;

	public ClassifyPanel() {

		setLayout(new BorderLayout());
		
		left = new JPanel();
		left.setLayout(new BorderLayout());
		if(Data.names!=null){
			classifyBox = new JComboBox<String>(Data.names);
		}else{
			classifyBox = new JComboBox<String>();
		}
		buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));
		group = new ButtonGroup();
		button1 = new JRadioButton("决策树分类方法");
		button2 = new JRadioButton("基于距离的分类方法");
		button3 = new JRadioButton("基于神经网络的分类方法");
		button1.setSelected(true);
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				text.setVisible(true);
				cutbtn.setVisible(true);
			}
		});
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				text.setVisible(false);
				cutbtn.setVisible(false);
				autocutbtn.setVisible(false);
				selfcutbtn.setVisible(false);
			}
		});
		button3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				text.setVisible(false);
				cutbtn.setVisible(false);
				autocutbtn.setVisible(false);
				selfcutbtn.setVisible(false);
			}
		});
		text = new JTextField("设置相关属性");
		text.setColumns(8);
		text.setToolTipText("各个属性用\"+\"连接，若是除variable之外的所有变量，可用\".\"");
		cutbtn = new JButton("剪枝...");
		autocutbtn = new JButton("1—SE自动剪枝");
		selfcutbtn = new JButton("自主设定cp值");
		autocutbtn.setVisible(false);
		selfcutbtn.setVisible(false);
		cutbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String result = FenLei.TreeCutting();
				area.setText(result);
				autocutbtn.setVisible(true);
				selfcutbtn.setVisible(true);
				
			}
		});
		autocutbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String colname = (String) classifyBox.getSelectedItem();
				String formula = text.getText();
				img = FenLei.AutoTreeCutting(Data.tablename, formula, colname);
				image.setIcon(new ImageIcon(img));
			}
		});
		selfcutbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				double cp = Double.parseDouble(JOptionPane.showInputDialog("键入cp值："));
				img = FenLei.SelfTreeCutting(cp);
				image.setIcon(new ImageIcon(img));
			}
		});
		
		group.add(button1);
		group.add(button2);
		group.add(button3);
		buttons.add(new Blank(2));
		buttons.add(button1);
		buttons.add(text);
		buttons.add(cutbtn);
		buttons.add(autocutbtn);
		buttons.add(selfcutbtn);
		buttons.add(new Blank(1));
		buttons.add(button2);
		buttons.add(new Blank(2));
		buttons.add(button3);
		buttons.add(new Blank(2));
		start = new JButton("开始");
		start.addActionListener(this);
		JPanel pnl = new BorderedJPanel("属性");
		pnl.add(classifyBox);
		pnl.add(new Blank(2));
		left.add(pnl, BorderLayout.NORTH);
		pnl = new BorderedJPanel("分类方法");
		pnl.add(buttons);
		pnl.add(new Blank(2));
		left.add(pnl, BorderLayout.CENTER);
		pnl = new JPanel();
		pnl.add(new Blank(1));
		pnl.add(start);
		pnl.add(new Blank(1));
		left.add(pnl, BorderLayout.SOUTH);

		center = new BorderedJPanel("分析结果");
		center.setLayout(new BorderLayout());
		area = new JTextArea();
		area.setEditable(false);
		center.add(new JScrollPane(area), BorderLayout.CENTER);

		right = new BorderedJPanel("图像");
		right.setLayout(new BorderLayout());
		image = new JLabel();
		right.add(new JScrollPane(image), BorderLayout.CENTER);

		spin = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, center, right);
		spin.setDividerLocation((int) (Variable.scrWidth / 5));
		spin.setDividerSize(1);
		spout = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left, spin);
		spout.setDividerLocation((int) (Variable.scrWidth / 6));
		spout.setDividerSize(1);

		add(spout, BorderLayout.CENTER);

		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		img = null;
		if(button1.isSelected()){
			String formula = text.getText();
			String attr = (String) classifyBox.getSelectedItem();
			img = FenLei.Trees(Data.tablename, attr, formula);	
//			scaleImg = img.getScaledInstance(image.getWidth(), image.getHeight(), Image.SCALE_SMOOTH);
			image.setIcon(new ImageIcon(img));
			area.setText(FenLei.Trees()+"\n");
		}else if(button2.isSelected()){
			int colnum = classifyBox.getSelectedIndex()+1;
			String result = FenLei.JuLi(Data.tablename, colnum);
			area.setText(result);
		}else if(button3.isSelected()){
			int colnum = classifyBox.getSelectedIndex()+1;
			String result = FenLei.Net(Data.tablename, colnum);
			area.setText(result);
		}
		
	}
	
}
