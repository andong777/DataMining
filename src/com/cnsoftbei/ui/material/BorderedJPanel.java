package com.cnsoftbei.ui.material;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * 自定义的带边框的面板<br/>
 * 
 * Version 0.1<br/>
 * 2013/6/2
 * @author andong
 *
 */
public class BorderedJPanel extends JPanel{

	private static final long serialVersionUID = 9012267429254398149L;
	
	public BorderedJPanel(String title){
		super();
		setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.WHITE), title,
				javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
				javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
				new Color(0, 0, 0)));
	}
	
	
}
