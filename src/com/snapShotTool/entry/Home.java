package com.snapShotTool.entry;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import com.snapShotTool.service.ExportToFile;
import com.snapShotTool.service.KeyLogger;
import com.snapShotTool.service.impl.ExportToPPT;
import com.snapShotTool.service.impl.ExportToWord;
import com.snapShotTool.util.ApplicationUtil;
import com.snapShotTool.util.Constants;

/**
 * A java class uses swing components to generate the UI.
 * 
 * @author Arpit K Tiwari
 * @version 1.0
 * @since 2017-08-22
 */

public class Home {

	private JFrame frame;
	private String msg = null;
	public static JLabel lblMsg = null;
	private JButton btnExport = null;
	private JComboBox extensions = null;
	public static JTextField basePath;
	public JButton btnAppStatusPause = null;
	public JButton btnAppStatusRecord = null;
	public JButton btnDelete = null;
	public static JTextField subFolder;
	public static JLabel lblCount = null;
	public JTextField fileNameTextField;

	/**
	 * Launch the application.
	 * 
	 * @author Arpit K Tiwari
	 * @version 1.0
	 * @since 2017-08-22
	 * @throws NativeHookException
	 * @throws UnsupportedLookAndFeelException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static void main(String[] args) throws NativeHookException, ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException {
		GlobalScreen.registerNativeHook();
		GlobalScreen.addNativeKeyListener(new KeyLogger());
		Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
		logger.setLevel(Level.OFF);
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		Home app = new Home();
		app.frame.setVisible(true);
	}

	/**
	 * Create the application.
	 * 
	 * @author Arpit K Tiwari
	 * @version 1.0
	 * @throws IOException
	 * @since 2017-08-22
	 */
	public Home() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @author Arpit K Tiwari
	 * @version 1.0
	 * @throws IOException
	 * @since 2017-08-22
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(Home.class.getResource("/resource/logo.png")));
		frame.setTitle(Constants.APPLICATION_TITLE);
		frame.setBounds(100, 100, 450, 300);
		frame.setLocation(400, 200);
		frame.setSize(460, 221);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 454, 192);
		panel.setBackground(new Color(255, 255, 255));
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		lblMsg = new JLabel("");
		lblMsg.setBounds(5, 160, 403, 28);
		panel.add(lblMsg);

		lblCount = new JLabel(String.valueOf(Constants.SCREENSHOT_COUNT));
		lblCount.setToolTipText("Screenshot current count");
		lblCount.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblCount.setBounds(418, 160, 37, 30);
		panel.add(lblCount);

		ApplicationUtil.getDataFromPropertyFile();
		Constants.BASE_FOLDER = Constants.SRC_FOLDER;
		if (Constants.SRC_FOLDER == null || Constants.SRC_FOLDER.trim().equalsIgnoreCase("")) {
			Constants.SRC_FOLDER = Constants.DEFAULT_SRC_FOLDER;
			Constants.BASE_FOLDER = Constants.DEFAULT_SRC_FOLDER;
			ApplicationUtil.setDataInPropertyFile();
		}

		JLabel lblBaseFolder = new JLabel("Base Folder :");
		lblBaseFolder.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblBaseFolder.setBounds(107, 13, 98, 25);
		panel.add(lblBaseFolder);

		basePath = new JTextField(Constants.BASE_FOLDER);
		basePath.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				Pattern pattern = Pattern.compile("([A-Z|a-z]:\\\\[^*|\"<>?\n]*)");
				Matcher matcher = pattern.matcher(basePath.getText());
				boolean matchStatus = matcher.matches();
				if (!matchStatus) {
					JOptionPane.showMessageDialog(frame, "Invalid base directory", "Error", JOptionPane.ERROR_MESSAGE);
					basePath.requestFocus();
					;
				} else if (!(Constants.BASE_FOLDER.equalsIgnoreCase(basePath.getText()))) {
					Constants.SCREENSHOT_COUNT = 0;
					Home.lblCount.setText(String.valueOf(Constants.SCREENSHOT_COUNT));
					Constants.BASE_FOLDER = basePath.getText();
					ApplicationUtil.setSrcFolder();
					ApplicationUtil.setDataInPropertyFile();
				}
			}
		});

		basePath.setToolTipText("Base Directory : " + Constants.BASE_FOLDER);
		basePath.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		basePath.setBounds(205, 11, 211, 26);
		panel.add(basePath);
		basePath.setColumns(10);
		basePath.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));

		JButton btnBrowseBaseFolder = new JButton("");
		btnBrowseBaseFolder.setToolTipText("Browse base folder directory");
		btnBrowseBaseFolder.setIcon(new ImageIcon(Home.class.getResource("/resource/browse.png")));
		btnBrowseBaseFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new java.io.File(Constants.BASE_FOLDER));
				chooser.setDialogTitle(Constants.FILE_CHOOSER_DIALOG_TITLE);
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.setAcceptAllFileFilterUsed(false);
				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					msg = Constants.FOLDER_SELECTED;
					Constants.BASE_FOLDER = chooser.getSelectedFile().getAbsolutePath() + File.separator;
					basePath.setText(Constants.BASE_FOLDER);
					ApplicationUtil.setSrcFolder();
					ApplicationUtil.setDataInPropertyFile();
					basePath.setToolTipText("Base Directory : " + Constants.BASE_FOLDER);
					Constants.SCREENSHOT_COUNT = 0;
					Home.lblCount.setText(String.valueOf(Constants.SCREENSHOT_COUNT));
				} else {
					basePath.setText(Constants.BASE_FOLDER);
					basePath.setToolTipText("Base Directory : " + Constants.BASE_FOLDER);
				}
			}
		});
		btnBrowseBaseFolder.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBrowseBaseFolder.setBounds(424, 13, 25, 25);
		panel.add(btnBrowseBaseFolder);

		JLabel lblSubFolder = new JLabel("Sub Folder  : ");
		lblSubFolder.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblSubFolder.setBounds(107, 49, 98, 25);
		panel.add(lblSubFolder);

		subFolder = new JTextField("");
		subFolder.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				if (!(subFolder.getText().equalsIgnoreCase(Constants.SUB_FOLDER))) {
					Constants.SUB_FOLDER = subFolder.getText().trim();
					Constants.SCREENSHOT_COUNT = 0;
					Home.lblCount.setText(String.valueOf(Constants.SCREENSHOT_COUNT));
					ApplicationUtil.setSrcFolder();
				}
			}
		});
		subFolder.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		subFolder.setColumns(10);
		subFolder.setBounds(205, 48, 211, 26);
		panel.add(subFolder);

		JLabel lblExtension = new JLabel("File Type     : ");
		lblExtension.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblExtension.setBounds(107, 85, 98, 25);
		panel.add(lblExtension);

		extensions = new JComboBox(Constants.EXTENSIONS);
		extensions.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		extensions.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		extensions.setForeground(new Color(0, 0, 0));
		extensions.setToolTipText("List of file format available to export the screenshots");
		extensions.setBackground(new Color(70, 130, 180));
		extensions.setBounds(205, 89, 211, 26);
		panel.add(extensions);

		btnExport = new JButton("");
		btnExport.setIcon(new ImageIcon(Home.class.getResource("/resource/export.png")));
		btnExport.setToolTipText(Constants.EXPORT_BUTTON_TOOLTIP);
		btnExport.setBackground(Color.BLACK);
		btnExport.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
					@Override
					protected Boolean doInBackground() throws Exception {
						String fileName = fileNameTextField.getText();
						if (fileName != null) {
							if ("".equalsIgnoreCase(fileName.trim())) {
								JOptionPane.showMessageDialog(frame, "File Name is mandatory", "Error",
										JOptionPane.ERROR_MESSAGE);
								return false;
							} else {
								Pattern pattern = Pattern.compile("([^*|:\"<>?/\\\\]*)");
								Matcher matcher = pattern.matcher(fileName);
								boolean matchStatus = matcher.matches();
								if (!matchStatus) {
									JOptionPane.showMessageDialog(frame, "Invalid File Name", "Error",
											JOptionPane.ERROR_MESSAGE);
									return false;
								} else {
									Constants.FILE_NAME = fileName;
									lblMsg.setText("");
									lblMsg.setIcon(new ImageIcon(Home.class.getResource("/resource/Saving.gif")));
									btnExport.setEnabled(false);
									btnDelete.setEnabled(false);
									ExportToFile exportToFile = null;
									if (extensions.getSelectedItem().toString().toUpperCase()
											.contains(Constants.FILE_FORMAT_DOCX)) {
										exportToFile = new ExportToWord();
										msg = exportToFile.export();
									} else if (extensions.getSelectedItem().toString().toUpperCase()
											.contains(Constants.FILE_FORMAT_PPTX)) {
										exportToFile = new ExportToPPT();
										msg = exportToFile.export();
									}
									return true;
								}
							}
						} else {
							return false;
						}
					}

					@Override
					protected void done() {
						boolean status;
						try {
							status = get();
							if (status == true) {
								lblMsg.setIcon(null);
								lblMsg.setText(msg);
								btnExport.setEnabled(true);
								btnDelete.setEnabled(true);
								if(Constants.EXPORT_STATUS){
									int fileOpenStatus = JOptionPane.showConfirmDialog(frame,
											"Do you want to open the exported file?", "Open the File",
											JOptionPane.YES_NO_OPTION);
									if (fileOpenStatus == 0 && Constants.EXPORTED_FILE_PATH != null) {
										if (Desktop.isDesktopSupported()) {
											Desktop.getDesktop().open(new File(Constants.EXPORTED_FILE_PATH));
										} else {
											lblMsg.setText("Unable to open the file.");
										}
										Constants.EXPORTED_FILE_PATH = "";
									}	
								}
							}
						} catch (InterruptedException e) {
							lblMsg.setText(e.getMessage());
						} catch (ExecutionException e) {
							lblMsg.setText(e.getMessage());
						} catch (IOException e) {
							lblMsg.setText(e.getMessage());
						}
					}
				};
				worker.execute();
			}
		});
		btnExport.setBounds(424, 123, 24, 25);
		panel.add(btnExport);

		JSeparator hSeparator = new JSeparator();
		hSeparator.setBounds(0, 158, 454, 1);
		panel.add(hSeparator);

		JSeparator vSeparator = new JSeparator();
		vSeparator.setOrientation(SwingConstants.VERTICAL);
		vSeparator.setBounds(413, 158, 1, 37);
		panel.add(vSeparator);

		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(96, 0, 1, 158);
		panel.add(separator);

		btnAppStatusRecord = new JButton("");
		btnAppStatusRecord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Constants.APPLICATION_RUNNING_STATUS = true;
				btnAppStatusPause.setEnabled(true);
				btnAppStatusRecord.setEnabled(false);
			}
		});
		btnAppStatusRecord.setToolTipText("Allows the application to capture screenshots");
		btnAppStatusRecord.setIcon(new ImageIcon(Home.class.getResource("/resource/record.png")));
		btnAppStatusRecord.setBounds(5, 5, 86, 48);
		btnAppStatusRecord.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAppStatusRecord.setEnabled(false);
		panel.add(btnAppStatusRecord);

		btnAppStatusPause = new JButton("");
		btnAppStatusPause.setToolTipText("Stop the application from capturing screenshots");
		btnAppStatusPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Constants.APPLICATION_RUNNING_STATUS = false;
				btnAppStatusRecord.setEnabled(true);
				btnAppStatusPause.setEnabled(false);

			}
		});
		btnAppStatusPause.setIcon(new ImageIcon(Home.class.getResource("/resource/pause.png")));
		btnAppStatusPause.setBounds(5, 55, 86, 48);
		btnAppStatusPause.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panel.add(btnAppStatusPause);
		
		JLabel lblFileName = new JLabel("File Name   : ");
		lblFileName.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblFileName.setBounds(107, 124, 98, 25);
		panel.add(lblFileName);
		
		fileNameTextField = new JTextField("");
		fileNameTextField.setToolTipText("Name of the file used to export screenshots");
		fileNameTextField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		fileNameTextField.setColumns(10);
		fileNameTextField.setBounds(205, 123, 211, 26);
		panel.add(fileNameTextField);
		
		btnDelete = new JButton("");
		btnDelete.setToolTipText("Delete all the files from the source folder");
		btnDelete.setIcon(new ImageIcon(Home.class.getResource("/resource/dustbin.png")));
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ApplicationUtil.clearCurrentDirectory();
				Constants.SCREENSHOT_COUNT = 0;
				Home.lblCount.setText(String.valueOf(Constants.SCREENSHOT_COUNT));
			}
		});
		btnDelete.setBounds(5, 105, 86, 48);
		panel.add(btnDelete);
	}
}