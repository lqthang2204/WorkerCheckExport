package com.digitexx.form;

import java.awt.GridLayout;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

public class test2 {
	public JComponent makeEditorPane(String bullet) {
	    JEditorPane pane = new JEditorPane();
	    pane.setContentType("text/html");
	    pane.setEditable(false);
	    if (bullet != null) {
	      HTMLEditorKit htmlEditorKit = (HTMLEditorKit) pane.getEditorKit();
	      StyleSheet styleSheet = htmlEditorKit.getStyleSheet();
	      String u = "http://i.stack.imgur.com/jV29K.png";
	      styleSheet.addRule(String.format(
	          "ul{list-style-image:url(%s);margin:0px 20px;", u));
	      // styleSheet.addRule("ul{list-style-type:disc;margin:0px 20px;}");

	    }
	    pane.setText("<html><h1>Heading</h1>Text<ul><li>Bullet point</li></ul></html>");
	    return pane;
	  }

	  public JComponent makeUI() {
	    JPanel p = new JPanel(new GridLayout(2, 1));
	    p.add(new JScrollPane(makeEditorPane(null)));
	    p.add(new JScrollPane(makeEditorPane("bullet.png")));
	    return p;
	  }

	  public static void main(String[] args) {
	    
		  
		  List<String> list = new LinkedList<String>();
		  list.add("/home/lqthang/Desktop/test Export/File check Export/008/505377_20200603 (copy).csv");
		  list.add("/home/lqthang/Desktop/test Export/File check Export/008/505377_20200603 (copy).pdf");
		  
		  for (int i = 0; i < list.size(); i++) {
			if(list.get(i).contains(".pdf")){
				
				list.remove(i);
			}
			
		}
		  for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
	  }

}
