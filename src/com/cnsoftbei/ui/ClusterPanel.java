package com.cnsoftbei.ui;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.cnsoftbei.analysis.JuLei;
import com.cnsoftbei.share.Variable;
import com.cnsoftbei.ui.material.Blank;
import com.cnsoftbei.ui.material.BorderedJPanel;
import static com.cnsoftbei.share.Component.*;
import static com.cnsoftbei.share.Data.tablename;

public class ClusterPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = -2342087829551362120L;
	private JSplitPane spin = null;
	private JSplitPane spout = null;
	private JPanel left = null;
	private BorderedJPanel buttons = null;
	private JPanel parameters = null;
	private ButtonGroup group = null;
	private JRadioButton button1 = null;
	private JRadioButton button2 = null;
	private JRadioButton button3 = null;
	private JRadioButton button4 = null;
	private JLabel numlbl = null;
	private JTextField text = null;
	private JButton start = null;
	private JScrollPane scrollpane = null;
	private BorderedJPanel center = null;
	private JTextArea area = null;
	private BorderedJPanel right = null;
	private JLabel image = null;
	private Image img = null;
	private Image scaleImg = null;

	public ClusterPanel() {
		
		setLayout(new BorderLayout());

		initFunctionArea();
		
		center = new BorderedJPanel("分析结果");
		center.setLayout(new BorderLayout());
		area = new JTextArea();
		area.setEditable(false);
		center.add(new JScrollPane(area),BorderLayout.CENTER);
		
		right = new BorderedJPanel("图像");
		right.setLayout(new BorderLayout());
		image = new JLabel();
		right.add(new JScrollPane(image),BorderLayout.CENTER);
		
		spin = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, center, right);
		spin.setDividerLocation((int) (Variable.scrWidth / 5));
		spin.setDividerSize(1);
		spout = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left, spin);
		spout.setDividerLocation((int) (Variable.scrWidth / 6));
		spout.setDividerSize(1);
		
		add(spout,BorderLayout.CENTER);
		setVisible(true);
	}
	
	public void initFunctionArea(){
		left = new JPanel();
		left.setLayout(new BorderLayout());
		buttons = new BorderedJPanel("聚类方法");
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));
		group = new ButtonGroup();
		button1 = new JRadioButton("K-means算法");
		button2 = new JRadioButton("Pam算法");
		button3 = new JRadioButton("Clara算法");
		button4 = new JRadioButton("FCM算法");	
		button1.setSelected(true);
		group.add(button1);
		group.add(button2);
		group.add(button3);
		group.add(button4);
		buttons.add(new Blank(1));
		buttons.add(new JLabel("划分聚类方法："));
		buttons.add(new Blank(1));
		buttons.add(button1);
		buttons.add(button2);
		buttons.add(button3);
		buttons.add(new Blank(1));
		buttons.add(new JLabel("模糊聚类方法："));
		buttons.add(new Blank(1));
		buttons.add(button4);
		buttons.add(new Blank(1));
		left.add(buttons,BorderLayout.NORTH);

		parameters = new JPanel();
		parameters.setLayout(new BorderLayout());
		numlbl = new JLabel("请输入聚类个数：");
		text = new JTextField(4);
		JPanel pnl = new JPanel();
		pnl.add(new Blank(1));
		pnl.add(numlbl);
		pnl.add(text);
		pnl.add(new Blank(1));
		parameters.add(pnl,BorderLayout.NORTH);
		
		clusterPanel = new BorderedJPanel("属性");
		clusterPanel.setLayout(new BoxLayout(clusterPanel,BoxLayout.Y_AXIS));
		scrollpane = new JScrollPane();
		scrollpane.add(clusterPanel);
		parameters.add(clusterPanel,BorderLayout.CENTER);
		
		start = new JButton("开始");
		start.addActionListener(this);
		pnl = new JPanel();
		pnl.add(new Blank(1));
		pnl.add(start);
		pnl.add(new Blank(1));
		parameters.add(pnl,BorderLayout.SOUTH);
		left.add(parameters,BorderLayout.CENTER);
	}
	
	public void actionPerformed(ActionEvent e) {
			int number;
			try {
				number = Integer.parseInt(text.getText());
			} catch (NumberFormatException e1) {
				return;
			}
			if(clusterBoxes == null || clusterBoxes.length < 1){
				return;
			}
			StringBuffer buffer = new StringBuffer();
			for(int i=0;i<clusterBoxes.length;i++){
				if(clusterBoxes[i].isSelected()){
					buffer.append(i+1);
					buffer.append(",");
				}
			}
			buffer.deleteCharAt(buffer.length()-1);
			System.out.println(buffer.toString());
			if(buffer.length()<1){
				JOptionPane.showMessageDialog(null, "未选择属性！");
				return;
			}
			String cols = buffer.toString(); 
			if(button1.isSelected()){
				img = JuLei.Kmeans(tablename, cols, number);
				area.setText(JuLei.Kmean());
			}else if(button2.isSelected()){
				img = JuLei.Pam(tablename, cols, number);
				area.setText(JuLei.pam());
			}else if(button3.isSelected()){
				img = JuLei.Clara(tablename, cols, number);
				area.setText(JuLei.clara());
			}else if(button4.isSelected()){
				img = JuLei.FCM(tablename, cols, number);
				area.setText(JuLei.fcm());
			}
//			scaleImg = img.getScaledInstance(image.getWidth(), image.getHeight(), Image.SCALE_SMOOTH);
			image.setIcon(new ImageIcon(img));
	}
}
