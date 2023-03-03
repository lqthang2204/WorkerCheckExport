package com.digitexx.form;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;
import javax.swing.SwingUtilities;

import com.digitexx.bean.BeanConfig;
import com.digitexx.bean.FieldsBean;
import com.digitexx.bean.FunctionBean;
import com.digitexx.dao.DataDao;
import com.digitexx.dao.projectDao;

import java.awt.Dimension;
import java.awt.List;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
public class LoadForm extends javax.swing.JFrame {
	private JPanel jPanel1;
	private JComboBox cbbProject;
	private JButton cbbNext;
	private JLabel lblTitle;
	private Map<String, String> mapProjects;
	private DataDao dao;
	private JMenu mnbReadFile;
	private JMenu jMenu1;
	private JMenuBar jMenuBar1;
	private JComboBox cbbTask;
	private projectDao project;
	private String projectID;
	/**
	* Auto-generated main method to display this JFrame
	*/
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				LoadForm inst = new LoadForm();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}
	
	public LoadForm() {
		super();
		initGUI();
		loadProject();
	}
	
	private void initGUI() {
		try {
			GroupLayout thisLayout = new GroupLayout((JComponent)getContentPane());
			getContentPane().setLayout(thisLayout);
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			{
				jMenuBar1 = new JMenuBar();
				setJMenuBar(jMenuBar1);
				{
					jMenu1 = new JMenu();
					jMenuBar1.add(jMenu1);
					jMenu1.setText("Edit");
					{
						mnbReadFile = new JMenu();
						jMenu1.add(mnbReadFile);
						mnbReadFile.setText("GetFunctionTest");
						mnbReadFile.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseClicked(MouseEvent arg0) {
								ReadFileForm formRead= new  ReadFileForm(projectID, cbbProject.getSelectedItem().toString(),cbbTask.getSelectedItem().toString());
								formRead.setVisible(true);
								centreWindow(formRead);
							}
						});
					
					}
				}
			}
			{
				jPanel1 = new JPanel();
				GroupLayout jPanel1Layout = new GroupLayout((JComponent)jPanel1);
				jPanel1.setLayout(jPanel1Layout);
				jPanel1.setBackground(new java.awt.Color(173,216,230));
				{
					lblTitle = new JLabel();
					lblTitle.setText("Load Project Config");
					lblTitle.setFont(new java.awt.Font("Aakar",1,22));
					lblTitle.setForeground(new java.awt.Color(255,57,0));
				}
				{
					ComboBoxModel cbbTaskModel = 
							new DefaultComboBoxModel(
									new String[] {  });
					cbbTask = new JComboBox();
					cbbTask.setModel(cbbTaskModel);
				}
				{
					ComboBoxModel cbbProjectModel = 
							new DefaultComboBoxModel(
									new String[] {  });
					cbbProject = new JComboBox();
					cbbProject.setModel(cbbProjectModel);
					cbbProject.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							Object selectedItem = cbbProject.getSelectedItem();
							
							 projectID = mapProjects.get(selectedItem);
							
							project = new projectDao();
				            LinkedList<String> listTask = project.getProjectTask(projectID);
							cbbTask.removeAllItems();
							for (int i = 0; i < listTask.size(); i++) {
							cbbTask.addItem(listTask.get(i).toString());
													
}
						}
					});
				}
				{
					cbbNext = new JButton();
					cbbNext.setText("Set Rule for Field");
							cbbNext.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent evt) {
								
									Object selectedItem = cbbProject.getSelectedItem();
									 projectID = mapProjects.get(selectedItem);
									Map<String, LinkedList<FieldsBean>> mapFields = dao.getListField(projectID);
									Map<String, FunctionBean> mapFunction = dao.getFunction(projectID);
									BeanConfig config = dao.getConfig(projectID,cbbTask.getSelectedItem().toString());
									boolean checkProjectExits = dao.checkProjectExits(projectID);
									FormMain main = new FormMain(projectID,mapFields,config,mapFunction, selectedItem.toString(), checkProjectExits,cbbTask.getSelectedItem().toString());
									main.setVisible(true);
									centreWindow(main);
									dispose();
								}
							});
				}
					jPanel1Layout.setHorizontalGroup(jPanel1Layout.createSequentialGroup()
					.addContainerGap(141, 141)
					.addGroup(jPanel1Layout.createParallelGroup()
					    .addGroup(GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
					        .addComponent(cbbProject, GroupLayout.PREFERRED_SIZE, 219, GroupLayout.PREFERRED_SIZE)
					        .addGap(0, 39, Short.MAX_VALUE))
					    .addGroup(GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
					        .addComponent(cbbNext, GroupLayout.PREFERRED_SIZE, 187, GroupLayout.PREFERRED_SIZE)
					        .addGap(0, 71, Short.MAX_VALUE))
					    .addComponent(lblTitle, GroupLayout.Alignment.LEADING, 0, 258, Short.MAX_VALUE)
					    .addGroup(GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
					        .addComponent(cbbTask, GroupLayout.PREFERRED_SIZE, 219, GroupLayout.PREFERRED_SIZE)
					        .addGap(0, 39, Short.MAX_VALUE)))
					.addContainerGap(102, 102));
					jPanel1Layout.setVerticalGroup(jPanel1Layout.createSequentialGroup()
					.addContainerGap(35, 35)
					.addComponent(lblTitle, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(cbbProject, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
					.addGap(25)
					.addComponent(cbbTask, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
					.addGap(95)
					.addComponent(cbbNext, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(24, Short.MAX_VALUE));
			}
			thisLayout.setVerticalGroup(thisLayout.createSequentialGroup()
				.addComponent(jPanel1, 0, 351, Short.MAX_VALUE));
			thisLayout.setHorizontalGroup(thisLayout.createSequentialGroup()
				.addComponent(jPanel1, 0, 501, Short.MAX_VALUE));
			pack();
			this.setSize(511, 406);
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}
	public void loadProject(){
		
		dao = new DataDao();
		mapProjects = new LinkedHashMap<String, String>();
		mapProjects= dao.getListProject();
		
		Object[] array = mapProjects.keySet().toArray();
		
		for (int i = 0; i < array.length; i++) {
			

			cbbProject.addItem(array[i]);
		}
		
	}
	public  void centreWindow(Window frame) {
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - frame.getWidth()) / 4);
	    int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
	    frame.setLocation(x, y);
	}

}
