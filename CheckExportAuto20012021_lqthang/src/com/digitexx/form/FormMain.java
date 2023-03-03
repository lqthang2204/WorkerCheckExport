package com.digitexx.form;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.digitexx.bean.BeanConfig;
import com.digitexx.bean.FieldCheck;
import com.digitexx.bean.FieldsBean;
import com.digitexx.bean.FunctionBean;
import com.digitexx.dao.DataDao;
import com.digitexx.util.Util;
import com.digitexx.util.Utilities;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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
public class FormMain extends javax.swing.JFrame {
	private JPanel jPanel1;
	private String id;
	private DataDao dao;
	private JScrollPane jScrollPane2;
	private JScrollPane jScrollPane1;
	private JPanel jPanel3;
	private JPanel jPanel2;
	private Map<String, LinkedList<FieldsBean>> mapFields;
	private Object[] listNameDB = null;
	private Object[] listNameFile = null;
	private BeanConfig config= new BeanConfig();
	private LinkedList<String> list;
	private DefaultTableModel tblmodelData;
	private DefaultTableModel tblmodelFile;
	private JList listFile;
	private JList listDB;
	private DefaultTableModel tblCompareModel;
	private JScrollPane jScrollPane3;
	private JPanel jPanel4;
	private String[] headerCompare = { "colum data", "Colum file", "Rule "};
	private Object[][] objCompare = {};
	private JTable tblCompare;
	private JComboBox comboBox;
	private Map<String, FunctionBean> mapFunction;
	private String data;
	private boolean flag = false;
	private JButton btnSave;
	private JButton btndelete;
	private JButton btnAdd;
	private JButton btnGetdata;
	private JPanel jPanel5;
	private int rowTable;
	private int colTable;
	private JCheckBox chkIndex;
	private FieldCheck fieldCheck;
	private LinkedList<FieldCheck> ListCheck;
	private String projectName;
	private boolean exitsProject;
	private LinkedList<String> listXML;
	
	private JButton btnAuto;
	private JLabel lblProjectName;
	private JPanel jPanel6;
	private LinkedList<String> listExcel;
	private LinkedList<String> listJson;
	private Utilities Utilies;
	private String Section;
	private Util util;
	/**
	* Auto-generated main method to display this JFrame
	*/
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				FormMain inst = new FormMain();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}
	
	public FormMain() {
		super();
		initGUI();
	}
	
	public FormMain(String id_project, Map<String, LinkedList<FieldsBean>> mapFields, BeanConfig bean, Map<String, FunctionBean> mapFunction, String projectName, boolean exitsProject,String section){
		this.mapFields = mapFields;
		this.id = id_project;
		this.config = bean;
		this.Section = section;
		dao = new DataDao();
		this.mapFunction = mapFunction;
		
		if(config.getFile_filter().toLowerCase().equals("csv")){
			readFileCSV();
		}
		else if(config.getFile_filter().toLowerCase().equals("xml")){
			listXML = new LinkedList<>();
			
			readFileXML();
		}
		else if(config.getFile_filter().toLowerCase().equals("json")){
			listJson = new LinkedList<>();
			readFileJSON();
		}
		else if(config.getFile_filter().toLowerCase().equals("xlsx") || config.getFile_filter().toLowerCase().equals("xls")){
			listExcel = new LinkedList<>();
			readFileExcel();
		}
		this.projectName = projectName;
		this.exitsProject = exitsProject;
		initGUI();
//		loadData();
//		loadFile();
		
		loadTableCompare();
		lblProjectName.setText(config.getProject_name());
		
	}
	
	private void initGUI() {
		try {
			GroupLayout thisLayout = new GroupLayout((JComponent)getContentPane());
			getContentPane().setLayout(thisLayout);
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			{
				jPanel1 = new JPanel();
				GroupLayout jPanel1Layout = new GroupLayout((JComponent)jPanel1);
				jPanel1.setLayout(jPanel1Layout);
				jPanel1.setBackground(new java.awt.Color(173,216,230));
				{
					jPanel2 = new JPanel();
					GroupLayout jPanel2Layout = new GroupLayout((JComponent)jPanel2);
					jPanel2.setLayout(jPanel2Layout);
					jPanel2.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
					{
						jScrollPane1 = new JScrollPane();
						{
							DefaultListModel<String> listDBModel = new DefaultListModel<>();
							listDB = new JList();
							jScrollPane1.setViewportView(listDB);
							Set<String> keySet = mapFields.keySet();
							for (String id : keySet) {
								LinkedList<FieldsBean> listFieldsBean = mapFields.get(id);
								listDBModel.addElement(listFieldsBean.get(0).getName());
								
							}
							
							
							
							
							
							listDB.setModel(listDBModel);
							listDB.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
							listDB.setBorder(BorderFactory.createTitledBorder("Column Database"));
							listDB.setSize(381, 378);
							listDB.addKeyListener(new KeyAdapter() {
								@Override
								public void keyPressed(KeyEvent e) {
									
									
									if(e.getKeyCode() == KeyEvent.VK_ALT){
										System.out.println("bam nnnnnneeeeeeeeeee");
										
										
										Object[] listTemp;
										if (listNameFile == null) {
											listTemp = listNameDB;
										} else {
											listTemp = listNameFile;
										}
										
										// tblCompare =
												for (int i = 0; i < listTemp.length; i++) {
													
													if (rowTable >= 0 && colTable >= 0) {
														// tblModel.setValueAt(name[i], rowTable,
																// colTable);
														Object valueAt = tblCompare.getValueAt(
																rowTable, colTable);
														
														if (valueAt.equals("") || valueAt == null) {
															
															tblCompare.setValueAt(listTemp[i],
																	rowTable, colTable);
														} else {
															if (valueAt.equals(listTemp[i])) {
																return;
															}
															tblCompare.setValueAt(valueAt + ","
																	+ listTemp[i], rowTable,
																	colTable);
														}
														
													} else {
														JOptionPane.showMessageDialog(null,
																"Vui long chon hang muon them",
																"Thong bao",
																JOptionPane.ERROR_MESSAGE);
													}
													
													
													
												}
										
										
										
										
										
										
										
										
										
									}
								}
							});
							listDB.addListSelectionListener(new ListSelectionListener() {
								public void valueChanged(ListSelectionEvent e) {
									if (!e.getValueIsAdjusting()) {
										listNameFile = null;
										listNameDB = listDB.getSelectedValues();
										for (int i = 0; i < listNameDB.length; i++) {
											System.out.println(listNameDB[i]);
										}
									}
								}
							});
						}
					}
					jPanel2Layout.setHorizontalGroup(jPanel2Layout.createSequentialGroup()
						.addComponent(jScrollPane1, 0, 344, Short.MAX_VALUE));
					jPanel2Layout.setVerticalGroup(jPanel2Layout.createSequentialGroup()
						.addComponent(jScrollPane1, 0, 381, Short.MAX_VALUE));
				}
				{
					jPanel3 = new JPanel();
					GroupLayout jPanel3Layout = new GroupLayout((JComponent)jPanel3);
					jPanel3.setLayout(jPanel3Layout);
					jPanel3.setEnabled(false);
					jPanel3.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
					{
						jScrollPane2 = new JScrollPane();
						{
							
							DefaultListModel<String> listFileModel = new DefaultListModel<>();
							listFile = new JList();
							jScrollPane2.setViewportView(listFile);
							if(config.getFile_filter().toLowerCase().equals("csv")){
								if(list==null){
									JOptionPane.showMessageDialog(null, "please, insert file template in config Export");
								}
								for (int i=0; i<list.size(); i++) {
//								String nameHeader = mapFile.get(index);
									
									listFileModel.addElement(list.get(i));
									
								}
							}
							else if(config.getFile_filter().toLowerCase().equals("xml")){
								if(listXML==null){
									JOptionPane.showMessageDialog(null, "please, insert file template in config Export");
								}
								for (int i=0; i<listXML.size(); i++) {
//								String nameHeader = mapFile.get(index);
									
									listFileModel.addElement(listXML.get(i));
									
								}
							}
							else if(config.getFile_filter().toLowerCase().equals("json")){
								if(listJson==null){
									JOptionPane.showMessageDialog(null, "please, insert file template in config Export");
								}
								for (int i=0; i<listJson.size(); i++) {
//									String nameHeader = mapFile.get(index);
										
										listFileModel.addElement(listJson.get(i));
										
									}
							}
							else if(config.getFile_filter().toLowerCase().equals("xlsx") || config.getFile_filter().toLowerCase().equals("xls")){
								if(listExcel==null){
									JOptionPane.showMessageDialog(null, "please, insert file template in config Export");
								}
								for (int i=0; i<listExcel.size(); i++) {
//									String nameHeader = mapFile.get(index);
										
										listFileModel.addElement(listExcel.get(i));
										
									}
							}
							
							
							listFile.setModel(listFileModel);
							listFile.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
							listFile.setBorder(BorderFactory.createTitledBorder("Column File"));
							listFile.setSize(381, 378);
							listFile.addKeyListener(new KeyAdapter() {
								@Override
								public void keyPressed(KeyEvent e) {
									
									
									if(e.getKeyCode() == KeyEvent.VK_ALT){
										
										
										
										Object[] listTemp;
										if (listNameFile == null) {
											listTemp = listNameDB;
										} else {
											listTemp = listNameFile;
										}
										
										// tblCompare =
												for (int i = 0; i < listTemp.length; i++) {
													
													if (rowTable >= 0 && colTable >= 0) {
														// tblModel.setValueAt(name[i], rowTable,
																// colTable);
														Object valueAt = tblCompare.getValueAt(
																rowTable, colTable);
														
														if (valueAt.equals("") || valueAt == null) {
															
															tblCompare.setValueAt(listTemp[i],
																	rowTable, colTable);
														} else {
															if (valueAt.equals(listTemp[i])) {
																return;
															}
															tblCompare.setValueAt(valueAt + ","
																	+ listTemp[i], rowTable,
																	colTable);
														}
														
													} else {
														JOptionPane.showMessageDialog(null,
																"Vui long chon hang muon them",
																"Thong bao",
																JOptionPane.ERROR_MESSAGE);
													}
													
													
													
												}
										
										
										
										
										
										
										
										
										
									}
								}
							});
							listFile.addListSelectionListener(new ListSelectionListener() {
								public void valueChanged(ListSelectionEvent e) {
									if (!e.getValueIsAdjusting()) {
										listNameDB = null;
										listNameFile = listFile.getSelectedValues();
										
										for (int i = 0; i < listNameFile.length; i++) {
											System.out.println("test "+ listNameFile[i]);
											
										}
									}
								}
							});
						}
					}
					jPanel3Layout.setHorizontalGroup(jPanel3Layout.createSequentialGroup()
						.addComponent(jScrollPane2, 0, 384, Short.MAX_VALUE));
					jPanel3Layout.setVerticalGroup(jPanel3Layout.createSequentialGroup()
						.addComponent(jScrollPane2, 0, 381, Short.MAX_VALUE));
				}
				{
					jPanel4 = new JPanel();
					GroupLayout jPanel4Layout = new GroupLayout((JComponent)jPanel4);
					jPanel4.setLayout(jPanel4Layout);
					jPanel4.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
					{
						jScrollPane3 = new JScrollPane();
						jScrollPane3.setBackground(new java.awt.Color(173,216,230));
						{
							tblCompareModel = new DefaultTableModel(objCompare, headerCompare);
							tblCompare = new JTable(tblCompareModel){

								private static final long serialVersionUID = 1L;
								 public Class getColumnClass(int column) {
									 switch (column) {
					                    case 0:
					                        return String.class;
					                    case 1:
					                        return String.class;
					                    case 2:
					                        return String.class;
					                    default:
					                        return String.class;
					                }
					            }	 
							
							};
							
							jScrollPane3.setViewportView(tblCompare);
							
							tblCompare.setRowHeight(30);
							tblCompare.setModel(tblCompareModel);
							tblCompare.setBackground(new java.awt.Color(173,216,230));
							tblCompare.addMouseListener(new MouseAdapter() {
								
								@Override
								public void mouseClicked(MouseEvent e) {
									rowTable = tblCompare.rowAtPoint(e.getPoint());
									colTable = tblCompare.columnAtPoint(e
											.getPoint());
									
									System.out.println(tblCompare.getSelectedRows());
									
									int[] selectedRows = tblCompare.getSelectedRows();
									for (int i = 0; i < selectedRows.length; i++) {
										
										System.out.println("==="+selectedRows[i]);
										
									}
									
//									System.out.println(rowTable);
//									System.out.println(colTable);
									//
								}
							});
							TableColumn colum = tblCompare.getColumnModel()
									.getColumn(2);
							comboBox = new JComboBox();
							comboBox.setForeground(Color.RED);
							comboBox.setMaximumRowCount(5);
							colum.setCellEditor(new DefaultCellEditor(comboBox));
						}
					}
					jPanel4Layout.setHorizontalGroup(jPanel4Layout.createSequentialGroup()
						.addComponent(jScrollPane3, 0, 278, Short.MAX_VALUE));
					jPanel4Layout.setVerticalGroup(jPanel4Layout.createSequentialGroup()
						.addComponent(jScrollPane3, 0, 381, Short.MAX_VALUE));
				}
				{
					jPanel6 = new JPanel();
					jPanel6.setBackground(new java.awt.Color(173,216,230));
					{
						lblProjectName = new JLabel();
						jPanel6.add(lblProjectName);
						lblProjectName.setPreferredSize(new java.awt.Dimension(310, 35));
						lblProjectName.setFont(new java.awt.Font("AnjaliOldLipi",1,16));
						lblProjectName.setForeground(new java.awt.Color(255,165,0));
					}
				}
				{
					jPanel5 = new JPanel();
					GroupLayout jPanel5Layout = new GroupLayout((JComponent)jPanel5);
					jPanel5.setLayout(jPanel5Layout);
					jPanel5.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
					jPanel5.setBackground(new java.awt.Color(173,216,230));
					{
						btnGetdata = new JButton();
						btnGetdata.setText(">>");
						btnGetdata.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								
								


								Object[] listTemp;
								if (listNameFile == null) {
									listTemp = listNameDB;
								} else {
									listTemp = listNameFile;
								}

								// tblCompare =
									for (int i = 0; i < listTemp.length; i++) {

										if (rowTable >= 0 && colTable >= 0) {
											// tblModel.setValueAt(name[i], rowTable,
											// colTable);
											Object valueAt = tblCompare.getValueAt(
													rowTable, colTable);

											if (valueAt.equals("") || valueAt == null) {

												tblCompare.setValueAt(listTemp[i],
														rowTable, colTable);
											} else {
												if (valueAt.equals(listTemp[i])) {
													return;
												}
												tblCompare.setValueAt(valueAt + ","
														+ listTemp[i], rowTable,
														colTable);
											}

										} else {
											JOptionPane.showMessageDialog(null,
													"Vui long chon hang muon them",
													"Thong bao",
													JOptionPane.ERROR_MESSAGE);
										}
										
										

									}
									
									
								
								
								

								

							
							}
						});
					}
					{
						btndelete = new JButton();
						btndelete.setText("Delete Row");
						btndelete.setSize(109, 46);
						btndelete.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {


								tblCompareModel = (DefaultTableModel) tblCompare
										.getModel();
								int selectedRow = tblCompare.getSelectedRow();
								System.out.println(selectedRow);
								if (selectedRow == -1) {
									JOptionPane.showMessageDialog(null,
											"Please choose Row to delete",
											"warning", JOptionPane.WARNING_MESSAGE);
								} else {

									tblCompareModel.removeRow(selectedRow);

								}

							
							}
						});
					}
					{
						btnSave = new JButton();
						btnSave.setText("Save Config");
						btnSave.addActionListener(new ActionListener() {
							
							public void actionPerformed(ActionEvent evt) {
								Utilies = new Utilities();
								ListCheck = new LinkedList<>();
								for (int i = 0; i < tblCompare.getRowCount(); i++) {

									fieldCheck = new FieldCheck();
								
									fieldCheck.setColumDb(tblCompareModel
											.getValueAt(i, 0).toString());
									fieldCheck.setColumnFile(tblCompareModel
											.getValueAt(i, 1).toString());
									fieldCheck.setRule(tblCompareModel
											.getValueAt(i, 2).toString());
									
									if(chkIndex.isSelected()){
										String Header = tblCompareModel.getValueAt(i, 1).toString();
										if(config.getFile_filter().equalsIgnoreCase("xlsx")){
											
											LinkedList<Integer> listIndex = Utilies.getIndex("xlsx",((JSONArray)config.getTemplate()).get(0).toString(),Header,config.getSeperate());
											String index ="";
											for (int j = 0; j < listIndex.size(); j++) {
												index = index+listIndex.get(j)+",";
											}
											int lastIndexOf = index.lastIndexOf(",");
											fieldCheck.setIndex(index.substring(0, lastIndexOf));
											
										}
										else if(config.getFile_filter().equalsIgnoreCase("csv")){
												
											LinkedList<Integer> listIndex = Utilies.getIndex("csv",((JSONArray)config.getTemplate()).get(0).toString(),Header,config.getSeperate());
											String index ="";
											for (int j = 0; j < listIndex.size(); j++) {
												index = index+listIndex.get(j)+",";
											}
											int lastIndexOf = index.lastIndexOf(",");
											fieldCheck.setIndex(index.substring(0, lastIndexOf));
											
										}
									}
									else{
										fieldCheck.setIndex("0");
									}
									ListCheck.add(fieldCheck);
									

								}
								try {
									
									dao.saveRule(id, ListCheck, projectName,Section);
									JOptionPane.showMessageDialog(null, "update successful","Message",JOptionPane.CLOSED_OPTION);
								} catch (Exception e) {
									// TODO: handle exception
								}
								
							}
						});
					}
					{
						btnAuto = new JButton();
						btnAuto.setText("AutoMapping");
						btnAuto.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								if(config.getFile_filter().toLowerCase().equals("csv")){
									Map<String, LinkedList<String>> mapCSV = dao.getFieldsMapToHeader(id, list, mapFields);
									tblCompareModel = (DefaultTableModel) tblCompare
											.getModel();
									Set<String> keySet = mapCSV.keySet();
									for (String key : keySet) {
										LinkedList<String> listFields = mapCSV.get(key);
										String field = "";
										for (int i = 0; i < listFields.size(); i++) {
											field = listFields.get(i)+","+ field;											
										}
										int lastIndexOf = field.lastIndexOf(",");
										field = field.subSequence(0, lastIndexOf).toString();
										tblCompareModel.addRow(new Object[] {
												field, key, ""});
									}
								}
								else if(config.getFile_filter().toLowerCase().equals("xml")){
									
									Map<String, LinkedList<String>> mapXML = dao.getFieldsMapToHeadertoXML(id, listXML, mapFields);
									tblCompareModel = (DefaultTableModel) tblCompare
											.getModel();
									Set<String> keySet = mapXML.keySet();
									for (String key : keySet) {
										LinkedList<String> listFields = mapXML.get(key);
										String field = "";
										for (int i = 0; i < listFields.size(); i++) {
											field = listFields.get(i)+","+ field;											
										}
										int lastIndexOf = field.lastIndexOf(",");
										field = field.subSequence(0, lastIndexOf).toString();
										tblCompareModel.addRow(new Object[] {
												field, key, ""});
									}
									
								}
								else if(config.getFile_filter().toLowerCase().equals("xlsx") || config.getFile_filter().toLowerCase().equals("xls")){

									Map<String, LinkedList<String>> map = dao.getFieldsMapToHeader(id, listExcel, mapFields);
									tblCompareModel = (DefaultTableModel) tblCompare
											.getModel();
									Set<String> keySet = map.keySet();
									for (String key : keySet) {
										LinkedList<String> listFields = map.get(key);
										String field = "";
										for (int i = 0; i < listFields.size(); i++) {
											field = listFields.get(i)+","+ field;											
										}
										int lastIndexOf = field.lastIndexOf(",");
										field = field.subSequence(0, lastIndexOf).toString();
										tblCompareModel.addRow(new Object[] {
												field, key, ""});
									}
								
									
								}
								
								
								
							}
						});
					}
					{
						chkIndex = new JCheckBox();
						chkIndex.setText("index");
						chkIndex.setBackground(new java.awt.Color(173,216,230));
					}
					{
						btnAdd = new JButton();
						btnAdd.setText("Add Row");
						btnAdd.setSize(109, 46);
						btnAdd.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								tblCompareModel = (DefaultTableModel) tblCompare
										.getModel();
								
								tblCompareModel.addRow(new Object[] {
										"", "", ""});
							}
						});
					}
						jPanel5Layout.setHorizontalGroup(jPanel5Layout.createSequentialGroup()
						.addContainerGap(261, 261)
						.addComponent(btnAdd, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
						.addGap(24)
						.addComponent(btnGetdata, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
						.addGap(17)
						.addComponent(btndelete, GroupLayout.PREFERRED_SIZE, 122, GroupLayout.PREFERRED_SIZE)
						.addGap(17)
						.addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE)
						.addGap(20)
						.addComponent(btnAuto, GroupLayout.PREFERRED_SIZE, 156, GroupLayout.PREFERRED_SIZE)
						.addGap(520)
						.addComponent(chkIndex, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(64, Short.MAX_VALUE));
						jPanel5Layout.setVerticalGroup(jPanel5Layout.createSequentialGroup()
						.addContainerGap(27, 27)
						.addGroup(jPanel5Layout.createParallelGroup()
						    .addGroup(GroupLayout.Alignment.LEADING, jPanel5Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						        .addComponent(btnGetdata, GroupLayout.Alignment.BASELINE, 0, 46, Short.MAX_VALUE)
						        .addComponent(btnAdd, GroupLayout.Alignment.BASELINE, 0, 43, Short.MAX_VALUE)
						        .addComponent(btndelete, GroupLayout.Alignment.BASELINE, 0, 46, Short.MAX_VALUE)
						        .addComponent(btnSave, GroupLayout.Alignment.BASELINE, 0, 46, Short.MAX_VALUE)
						        .addComponent(btnAuto, GroupLayout.Alignment.BASELINE, 0, 46, Short.MAX_VALUE))
						    .addGroup(GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
						        .addComponent(chkIndex, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
						        .addGap(0, 27, Short.MAX_VALUE)))
						.addContainerGap());
				}
				jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup()
					.addGroup(jPanel1Layout.createParallelGroup()
					    .addGroup(GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
					        .addComponent(jPanel5, 0, 1655, Short.MAX_VALUE)
					        .addGap(9))
					    .addGroup(GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
					        .addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, 349, GroupLayout.PREFERRED_SIZE)
					        .addGap(35)
					        .addComponent(jPanel3, GroupLayout.PREFERRED_SIZE, 389, GroupLayout.PREFERRED_SIZE)
					        .addGap(18)
					        .addComponent(jPanel4, 0, 873, Short.MAX_VALUE)))
					.addGroup(GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
					    .addGap(7)
					    .addComponent(jPanel6, 0, 451, Short.MAX_VALUE)
					    .addContainerGap(1206, 1206)));
				jPanel1Layout.setVerticalGroup(jPanel1Layout.createSequentialGroup()
					.addComponent(jPanel6, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
					.addGroup(jPanel1Layout.createParallelGroup()
					    .addComponent(jPanel3, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 385, GroupLayout.PREFERRED_SIZE)
					    .addComponent(jPanel4, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 385, GroupLayout.PREFERRED_SIZE)
					    .addComponent(jPanel2, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 385, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
					.addComponent(jPanel5, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE));
			}
			thisLayout.setVerticalGroup(thisLayout.createSequentialGroup()
				.addComponent(jPanel1, 0, 521, Short.MAX_VALUE));
			thisLayout.setHorizontalGroup(thisLayout.createSequentialGroup()
				.addComponent(jPanel1, 0, 1664, Short.MAX_VALUE)
				.addGap(6));
			pack();
			this.setSize(1680, 551);
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}

	public void readFileCSV(){
		JSONArray template = (JSONArray) config.getTemplate();
		BufferedReader csvReader = null;
		list = new LinkedList<>();
		for (int i = 0; i < template.length(); i++) {
			File csvFile = new File(template.get(i).toString());
			
			if (csvFile.isFile()) {
				String row="";
				try {
					csvReader = new BufferedReader(new FileReader(csvFile));
					String readLine = csvReader.readLine();
					String[] rowData = readLine.split(config.getSeperate());
					for (int j = 0; j < rowData.length; j++) {
						
						System.out.println(rowData[j].toString());
						list.add(rowData[j].toString());
//						mapFile.put(template.get(i).toString(),rowData[j].toString());
					}

					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			}
		
		}
		try {
			csvReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("sixze" + list.size());
		
		
		
	}
	public void readFileXML(){
		JSONArray template = (JSONArray) config.getTemplate();
		
	
		for (int i = 0; i < template.length(); i++) {
			File xmlFile = new File(template.get(i).toString());
			
			if (xmlFile.isFile()) {
				
				try {
					 DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			         Document doc = dBuilder.parse(xmlFile);
			         NodeList nl=doc.getDocumentElement().getChildNodes();
			         int level=0;
			         for(int k=0;k<nl.getLength();k++){
			             printTags((Node)nl.item(k), level);  
			         }
				} catch (ParserConfigurationException | SAXException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					
					

			}
				
		}
		
	
	}
	public void readFileJSON(){
		JSONArray template = (JSONArray) config.getTemplate();
		
	
		for (int i = 0; i < template.length(); i++) {
			String file_name = template.get(i).toString();
			File jsonFile = new File(template.get(i).toString());
			
			if (jsonFile.isFile()) {
				
				try {
					JSONObject obj = parseJSONFile(file_name);
					Gson gson = new GsonBuilder().setPrettyPrinting().create();
					JsonParser jp = new JsonParser();
					JsonElement je = jp.parse(obj.toString());
					String prettyJsonString = gson.toJson(je);
					listJson.add(prettyJsonString);
//					JSONArray names = obj.names();
//					for (int j = 0; j < names.length(); j++) {
//						
//						String parent = names.getString(j);
//						Object object = obj.get(parent);
//						System.out.println("ttttttttt===== "+object);
//						listJson.add(names.getString(j));
//					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					
					

			}
				
		}
		
	
	}
	public  void printTags(Node nodes, int level){
	       if(nodes.hasChildNodes()){
	           NodeList nl=nodes.getChildNodes();
	           
	           if(nl.getLength()>1){
	        	   level = level+1;
	        	   for(int j=0;j<nl.getLength();j++){
	        		   
		        	   printTags(nl.item(j),level);
		           }
	           }else{ 
	        	   if(!listXML.contains(((Node) nl).getNodeName())){	
	        		   Node parentNode = ((Node) nl).getParentNode();
	        		   System.out.println("parent = "+ parentNode.getNodeName());
	        		   listXML.add(parentNode.getNodeName()+":"+((Node) nl).getNodeName());
	        		  
	        	   }
	           }
  
	       }
		   
	      
	   }
	public  JSONObject parseJSONFile(String filename) throws JSONException, IOException {
        String content = new String(Files.readAllBytes(Paths.get(filename)));
        return new JSONObject(content);
    }
//	public void loadData(){
//		
//		tblmodelData = (DefaultTableModel) tblData.getModel();
//		Set<String> keySet = mapFields.keySet();
//		for (String key : keySet) {
//			LinkedList<FieldsBean> linkedList = mapFields.get(key);
//			for (int i = 0; i < linkedList.size(); i++) {
//				tblmodelData.addRow(new Object[] {
//						linkedList.get(i).getName(),
//						linkedList.get(i).getDisplay() });
//			}
//		}
//		
//	}
//	public void loadFile(){
//		tblmodelFile = (DefaultTableModel) tblFile.getModel();
//		Set<Integer> keySet = mapFile.keySet();
//		for (Integer key : keySet) {
//			String Header = mapFile.get(key);
//			
//				tblmodelFile.addRow(new Object[] {
//						Header,key });
//			
//		}
//	}
	public void loadTableCompare(){
		if(!this.exitsProject){
			
			tblCompareModel = (DefaultTableModel) tblCompare.getModel();
			Set<String> keySet = mapFunction.keySet();
			
			for(String id : keySet){
				FunctionBean functionBean = mapFunction.get(id);
				List<String> listFunction = functionBean.getListFunction();
				for (int j = 0; j < listFunction.size(); j++) {
					
					comboBox.addItem(listFunction.get(j));
					tblCompareModel.addRow(new Object[] {
							"",
							"",
							listFunction.get(j)});
				}
			}
		
//			for(int i=0;i<listFunction.size();i++){
//				comboBox.addItem(listFunction.get(i));
//				tblCompareModel.addRow(new Object[] {
//						"",
//						"",
//						listFunction.get(i)});
//			}
		}
		else{
			Map<String, FieldCheck> mapRule = dao.getMapRule(id, mapFields);
			tblCompareModel = (DefaultTableModel) tblCompare.getModel();
			
			Set<String> keySet = mapRule.keySet();
			for(String key : keySet){
				FieldCheck fieldCheck = mapRule.get(key);
			
				int itemCount = comboBox.getItemCount();
				if(itemCount>=1){
					for (int i = 0; i < itemCount; i++) {
						if(!comboBox.getItemAt(i).equals(fieldCheck.getRule().replace("\"", ""))){
							comboBox.addItem(fieldCheck.getRule().replace("\"", ""));
						
						}
					}
				}
				else{
					comboBox.addItem(fieldCheck.getRule().replace("\"", ""));
				
				}
				tblCompareModel.addRow(new Object[] {
						fieldCheck.getColumDb().replace("\"", ""),
						fieldCheck.getColumnFile().replace("\"", ""),
						fieldCheck.getRule().replace("\"", "")});
				
				
			}
//			List<String> list = new LinkedList<>();
			Set<String> keySet2 = mapFunction.keySet();
			for (String key : keySet2) {
				FunctionBean functionBean = mapFunction.get(key);
				List<String> listFunction = functionBean.getListFunction();
					int itemCount = comboBox.getItemCount();
					for (int i = 0; i < listFunction.size(); i++) {
						for (int j = 0; j < itemCount; j++) {
						if(!comboBox.getItemAt(j).equals(listFunction.get(i).toString().replace("\"", ""))){
								comboBox.addItem(listFunction.get(i).toString().replace("\"", ""));
								break;		
						}
					}	
				}
			}
//			if(list!=null){
//				for (int i = 0; i < list.size(); i++) {
//					comboBox.addItem(list.get(i).toString());
//				}
//			}
		
			
//			int itemCount = comboBox.getItemCount();
//			
//				for (int i = 0; i < itemCount; i++) {
//					for (int j = i+1; j < itemCount; j++) {
//						if(comboBox.getItemAt(i).equals(comboBox.getItemAt(j))){
//							list.add(comboBox.getItemAt(j).toString());
//						}
//					}
//				}
//				for (int i = 0; i < list.size(); i++) {
//					for (int j = 0; j < itemCount; j++) {
//						if(comboBox.getItemAt(j).equals(list.get(i))){
//							comboBox.remove(j);
//						}
//					}
//				}
		}
		
	}
//	public String getDataCSV(String path, int index, String columnName){
//		File csvFile = new File(path);
//		if (csvFile.isFile()) {
//			String row="";
//			try {
//				BufferedReader csvReader = new BufferedReader(new FileReader(config.getTemplate()));
//				String readLine = csvReader.readLine();
//			
//
//				csvReader.close();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}
	public void readFileExcel(){
		util = new Util();
		JSONArray template = (JSONArray) config.getTemplate();
		
		
		for (int i = 0; i < template.length(); i++) {
			try {
				String file_name = template.get(i).toString();
				File f = new File(file_name);
				FileInputStream fis = new FileInputStream(f);
				Workbook wb = util.getWorkbook(fis, file_name);
				Sheet sheet = wb.getSheetAt(0);
				Row row = sheet.getRow(0);
				int lastCellNum = row.getLastCellNum();
				for (int j = 0; j < lastCellNum; j++) {
					Cell cell = row.getCell(j);
					DataFormatter formatter = new DataFormatter();
					String data = formatter.formatCellValue(cell);
					if(!listExcel.contains(data)){
						listExcel.add(data);
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	
}
