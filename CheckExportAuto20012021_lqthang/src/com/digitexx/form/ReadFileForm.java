package com.digitexx.form;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.SwingUtilities;

import com.digitexx.dao.DataDao;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class ReadFileForm extends javax.swing.JFrame {
	private JPanel jPanel1;
	private JButton btnSetFunction;
	private JButton btnSetPath;
	private JTextField txtPath;
	private LinkedList<String> listFunction;
	private String _id;
	private String name;
	private String section;
	private DataDao dao;
	/**
	* Auto-generated main method to display this JFrame
	*/
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				ReadFileForm inst = new ReadFileForm();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}
	
	public ReadFileForm() {
		super();
		initGUI();
	}
	public ReadFileForm(String id, String name,String section) {
		super();
		this._id = id;
		this.name = name;
		this.section = section;
		initGUI();
		dao = new DataDao();
		
	}
	
	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			getContentPane().setLayout(null);
			{
				jPanel1 = new JPanel();
				getContentPane().add(jPanel1);
				jPanel1.setBounds(0, 0, 766, 211);
				jPanel1.setLayout(null);
				jPanel1.setBackground(new java.awt.Color(173,216,230));
				jPanel1.setBorder(new LineBorder(new java.awt.Color(0,0,0), 1, false));
				{
					txtPath = new JTextField();
					jPanel1.add(txtPath);
					txtPath.setBounds(24, 50, 532, 33);
				}
				{
					btnSetPath = new JButton();
					jPanel1.add(btnSetPath);
					btnSetPath.setText("Set path");
					btnSetPath.setBounds(602, 44, 144, 44);
					btnSetPath.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {

							JFileChooser choose = new JFileChooser(txtPath.getText().toString());
							int retunVal= choose.showOpenDialog(btnSetPath);
							
							
							if(retunVal == JFileChooser.FILES_ONLY)
							{
								File selectFile = choose.getSelectedFile();
								  
								txtPath.setText(selectFile.getAbsolutePath());
							}
						
						}
					});
				}
				{
					btnSetFunction = new JButton();
					jPanel1.add(btnSetFunction);
					btnSetFunction.setText("Get Function");
					btnSetFunction.setBounds(326, 159, 159, 34);
					btnSetFunction.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							listFunction = new LinkedList<String>();

							try {
								File functionscript = new File(txtPath.getText().trim());
								Reader reader = new FileReader(functionscript);
								String result ="";
								 int i; 
								    while ((i=reader.read()) != -1) 
								    {
								    	
								    	result = result+(char)i;
								    }
//								    System.out.println("result = "+ result);
									String regex = ".*function.*\\(.*";
									Pattern pattern = Pattern.compile(regex);

									Matcher matcher = pattern.matcher(result);

									while (matcher.find()) {
										String group = matcher.group();
										String funcion_name = group.replace("function", "").split("\\(")[0].trim();
										if(!listFunction.contains(funcion_name)){
											listFunction.add(funcion_name);
										}
									}
							
									boolean exist = dao.checkProjectAndSectionExits(_id, section);
								
										
										boolean updateFunction = dao.updateFunction(_id, section, listFunction, name,exist,txtPath.getText().trim().toString());
										if(updateFunction){
											JOptionPane.showMessageDialog(null, "Update Successfull");
											dispose();
										}else{
											JOptionPane.showMessageDialog(null, "Update fail","Message",JOptionPane.ERROR_MESSAGE);
										}
										
									
									
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
				}
			}
			pack();
			this.setSize(776, 241);
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}

}
