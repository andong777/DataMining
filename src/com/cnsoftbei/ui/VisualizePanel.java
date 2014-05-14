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
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

import com.cnsoftbei.analysis.Tools;
import com.cnsoftbei.share.Data;
import com.cnsoftbei.ui.material.BorderedJPanel;
import static com.cnsoftbei.share.Component.*;

public class VisualizePanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = -5621394778291890709L;
	
	private BorderedJPanel method = null;
	private ButtonGroup group = null;
	private JRadioButton hist = null;
	private JRadioButton qq = null;
	private JRadioButton box = null;
	private JRadioButton bw = null;
	private JRadioButton sandian = null;
	private JPanel rightpnl;
	private JButton run = null;
	private JLabel image = null;
	private JPanel mainpnl = null;
	private Image img = null;
	private Image scaleImg = null;
	
	public VisualizePanel(){
		setLayout(new BorderLayout());
		method = new BorderedJPanel("数据可视化方法");
		mainpnl = new JPanel();
		mainpnl.setLayout(new BorderLayout());
		add(mainpnl,BorderLayout.CENTER);
		group = new ButtonGroup();
		hist = new JRadioButton("直方图");
		qq = new JRadioButton("Q-Q图");
		box = new JRadioButton("箱图");
		bw = new JRadioButton("分位箱图");
		sandian = new JRadioButton("散点图");
		hist.setSelected(true);
		
		hist.addActionListener(this);
		qq.addActionListener(this);
		box.addActionListener(this);
		bw.addActionListener(this);
		sandian.addActionListener(this);
		group.add(hist);
		group.add(qq);
		group.add(box);
		group.add(bw);
		group.add(sandian);
		method.add(hist);
		method.add(qq);
		method.add(box);
		method.add(bw);
		method.add(sandian);
		run = new JButton("执行");
		JPanel leftpnl = new JPanel();
		leftpnl.add(method);
		leftpnl.add(run);
		if(Data.names!=null){
			visualizeBox1 = new JComboBox<String>(Data.names);
			visualizeBox2 = new JComboBox<String>(Data.names);
		}else{
			visualizeBox1 = new JComboBox<String>();
			visualizeBox2 = new JComboBox<String>();
		}
		visualizeBox2.setEnabled(false);
		visualizeBox1.setToolTipText("属性1");
		visualizeBox2.setToolTipText("属性2");
		rightpnl = new BorderedJPanel("属性");
		rightpnl.setLayout(new BoxLayout(rightpnl,BoxLayout.Y_AXIS));
		rightpnl.add(visualizeBox1);
		rightpnl.add(visualizeBox2);
		JPanel panel = new JPanel();
		panel.add(leftpnl);
		panel.add(rightpnl);
		add(panel,BorderLayout.NORTH);
		
		image = new JLabel();
		panel.add(image,BorderLayout.CENTER);
		run.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==hist||e.getSource()==qq||e.getSource()==box){
			visualizeBox2.setEnabled(false);
		}else if(e.getSource()==bw||e.getSource()==sandian){
			visualizeBox2.setEnabled(true);
		}else if(e.getSource()==run){
			int col1 = visualizeBox1.getSelectedIndex()+1;
			if(col1<=0)
				return;
			
			if(hist.isSelected())
				img = Tools.Hist(Data.tablename, col1);
			else if(qq.isSelected())
				img = Tools.QQPlot(Data.tablename, col1);
			else if(box.isSelected())
				img = Tools.BoxPlot(Data.tablename, col1);
			else if(bw.isSelected()){
				int col2 = visualizeBox2.getSelectedIndex()+1;
				if(col2<=0)
					return;
				img = Tools.Bwplot(Data.tablename, col1, col2);
			}
			else if(sandian.isSelected()){
				int col2 = visualizeBox2.getSelectedIndex();
				if(col2<=0)
					return;
				img = Tools.SanDian(Data.tablename, col1, col2);
			}
//			scaleImg = img.getScaledInstance(image.getWidth(), image.getHeight(), Image.SCALE_SMOOTH);
			mainpnl.removeAll();
			image.setIcon(new ImageIcon(img));
			mainpnl.add(new JScrollPane(image),BorderLayout.CENTER);
			mainpnl.revalidate();
			mainpnl.repaint();
//			repaint();
		}
	}

}
