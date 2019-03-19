package client;

import java.awt.Color;


import java.awt.Graphics;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.naming.directory.AttributeModificationException;
import javax.swing.ImageIcon;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.text.AttributedCharacterIterator.Attribute;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.swing.AbstractAction;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.text.AbstractDocument.AttributeContext;
import javax.tools.FileObject;

public class LoginClient
{	
	
	static JFrame frame = new JFrame("Login");	
	static String ss;	
	public static void main(String[] args) throws Exception 
	{
		frame.setSize(450, 270);
		frame.setLocation(200, 300);
		frame.setResizable(false);
		frame.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		System.out.println("Operating System name : " +System.getProperty("os.name"));
		ss="os.name";
		System.out.println("OS_name: " +System.getProperty(ss));
		System.out.println("Operating System version :" +System.getProperty("os.version"));
		System.out.println("Operating System Architecture:" +System.getProperty("os.arch") +""+ "bit");
		String ip=System.getProperty("os.id");
		System.out.println("name" +ip);
		JPanel panel = new JPanel();
		frame.add(panel);
		placeComponents(panel);	
		frame.setUndecorated(true);
		frame.setVisible(true);
	}
	public static final void copyInputStream(InputStream in, OutputStream out)
			 throws IOException
			  {
			    byte[] buffer = new byte[1024];
			    int len;

			    while((len = in.read(buffer)) >= 0)
			      out.write(buffer, 0, len);

			    in.close();
			    out.close();
			  }

	
	
	private static  void placeComponents(final JPanel panel) throws IOException 
	{
		final String path = System.getenv("TEMP");
		System.out.println(path);
//		db=new db();
		panel.setLayout(null);
		panel.setBackground(Color.lightGray);			
        JLabel jLabel1=new JLabel();
		jLabel1.setIcon( new ImageIcon("//10.43.15.2/Common/NetSoft/FEAST/Logo_STR.gif"));
		jLabel1.setBounds(300, 20, 120, 120);
		panel.add(jLabel1);
		
		JLabel l1=new JLabel();
		l1.setText("   FEAST USER LOGIN HERE...");
		l1.setBounds(100,00 ,220, 30);
		l1.setForeground(Color.blue);
		panel.add(l1);
		
		JLabel userLabel = new JLabel("User Name");
		userLabel.setBounds(10, 40, 80, 25);
		userLabel.setForeground(Color.black);
		panel.add(userLabel);

		final JTextField userText = new JTextField(20);
		userText.setBounds(100, 40, 160, 25);
//		userText.setBackground(Color.gray);
		panel.add(userText);
		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(10, 80, 80, 25);
		passwordLabel.setForeground(Color.black);
		panel.add(passwordLabel);

		final JPasswordField passwordText = new JPasswordField(20);
		passwordText.setBounds(100, 80, 160, 25);
//		passwordText.setBackground(Color.gray);
		panel.add(passwordText);
				
//		String username = new String(userText.getText());
//		String password = new String(passwordText.getPassword());
		final JButton loginButton = new JButton("login");		
		loginButton.setBounds(60, 120, 80, 25);
		panel.add(loginButton);
		frame.getRootPane().setDefaultButton(loginButton);
	    
		loginButton.addActionListener(new ActionListener() {
			 
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//
				String username = new String(userText.getText());
				String password = new String(passwordText.getPassword());				
			if(e.getSource()==loginButton)
	        { 
				char[] pwd=passwordText.getPassword();

	            username = new String(userText.getText());
				password = new String(passwordText.getPassword());
	            password=String.copyValueOf(pwd);
	            Runtime r=Runtime.getRuntime(); 
	            Process p=null;	            
				try
				{
					BufferedReader reader = new BufferedReader(new FileReader("//10.43.15.2/Common/NetSoft/FEAST/config.txt "));
			    	String line = null;
			    	if((line = reader.readLine()) != null) {

			    		System.out.println("IP Address : " +line);
			    	}
			    	System.out.println("IP Address : " +line);
					String host=line;
					int port=11122;
					int bytesRead;
				    int current = 0;
					Socket sc=new Socket(host,port);
					InputStream source=sc.getInputStream();
					InputStream in = sc.getInputStream();	
					OutputStream des=sc.getOutputStream();
					DataInputStream dis=new DataInputStream(source);
					PrintStream ps=new PrintStream(des, true);
//					ps.println(System.getProperty(ss));
//					DataInputStream console=new DataInputStream(System.in);
//					String line=dis.readLine();
//					response=console.readLine();
//					ps.println(response);	
//					console.close();							
					String str[]=new String[3];
					str[0]=username;
					str[1]=password;
					String st="LOGIN "+ str[0].trim() + " " + str[1].trim();
					System.out.println("Senting LOGIN request :" + st);
					ps.println(st);
													
					
					String status=dis.readLine();				
					System.out.println(status);
					
					try
					{
						if(status.equals("SUCCESS") )
						{	
							
							
							JOptionPane.showMessageDialog(null, "Logged in !!!! \n  waiting for FEAST to open !!!","Success", JOptionPane.INFORMATION_MESSAGE);													        
							ps.println("LOGIN");
							System.out.println("LOGIN");
							System.out.println("waiting for feast");							
							String fileName = dis.readUTF(); 
							System.out.println(fileName);
							//String Path="C:\\Users/ssdl/Desktop"; 						
							OutputStream output = new FileOutputStream(path+"/"+fileName);   
//							FileWriter fileWriter = new FileWriter(Path, true);
							long size = dis.readLong();   
							byte[] buffer = new byte[1024];   
							while (size > 0 && (bytesRead = dis.read(buffer, 0, (int)Math.min(buffer.length, size))) != -1)   
							{   							    output.write(buffer, 0, bytesRead);   
							    size -= bytesRead;   
							}
							
							// Closing the FileOutputStream handle
							output.close();
							output.flush();
							System.out.println("file printed successfully");
							Enumeration PrivilegedAction;
							Enumeration zipEntries;
//							ZipFile zipFile;
							ZipEntry zipEntry = null;
						   
							try 
							{
								String path = System.getenv("TEMP");
								System.out.println(path);
								ZipFile zipFile = new ZipFile( path+"/"+"FEAST-32Bit.zip");
								Enumeration<?> enu = zipFile.entries();
								while (enu.hasMoreElements()) {
									zipEntry = (ZipEntry) enu.nextElement();

									String name = zipEntry.getName();
									System.out.printf("name: %-20s |\n", name);
									File file = new File(path+"/"+name);
									if (name.endsWith("/")) {
								 		file.mkdirs();
								 		
										continue;
									}

									File parent = file.getParentFile();
									if (parent != null) {
										parent.mkdirs();
									}

									InputStream is = zipFile.getInputStream(zipEntry);
									OutputStream fos = new FileOutputStream(file);
									byte[] bytes = new byte[1024];
									int length;
									while ((length = is.read(bytes)) >= 0) {
										fos.write(bytes, 0, length);
									}
										is.close();
										fos.close();
										
									}								
								zipFile.close();
								File f=new File(path+"/"+"FEAST-32Bit.zip");						
								boolean read=f.setReadable(true);
								System.out.println("readable : " +read);
								boolean success=f.delete();
								System.out.println(success);
								} 
							catch (IOException ioe) 
							{
								System.out.println("Unhandled exception:" +ioe);
								//ioe.printStackTrace();
								return;
						      
							}
							//ps.println("LOGIN");
							//p=r.exec(path+"/"+"FEAST-32Bit"+"/"+"PreWin95.exe");			
							ProcessBuilder pb = new ProcessBuilder(path+"/"+"FEAST-32Bit"+"/"+"PreWin.exe");
							pb.redirectErrorStream(true);
							p = pb.start();
							System.out.println("feast executed successfully");	
							
							String file =path+"/"+"FEAST-32Bit" ;							
							String cmd1[] = {"attrib","+h",file};
							Runtime.getRuntime().exec(cmd1);
							System.out.println("hide successfully");
							
							userText.setText("");
							passwordText.setText("");	
							frame.setVisible(false);
							
							int ret = p.waitFor();
							ps.println("LOGOUT");
							System.out.println("LOGOUT");
							System.out.println(ret);
							//p.destroy();
							//String file = "C:/Users/ssdl/Desktop/server/client/FEAST-64Bit";																										
							String pat=path+"/"+"FEAST-32Bit";	
							File fi=new File(path+"/"+"FEAST-32Bit");
							if(fi.exists())
							{
								String filepath=pat;
								File directory=new File(filepath);							
								String[] list = directory.list();
								if (list != null) 
								{
									for (int i = 0; i < list.length; i++) 
									{
										File entry = new File(directory, list[i]);
										System.out.println("\t removing entry " + entry);
										if (entry.isDirectory())
										{
											System.out.println("");
											return;
										}
										else
										{
											if (!entry.delete())
											{
												System.out.println("not deleted");
												return ;
												
											}
										}
									}
									System.out.println("directory removed successfully");
									System.out.println(directory.delete());
								}
								System.out.println("Temporary files are removed successfully");
							}
							else
							{
								System.out.println("No such file or directory");
							}
							
							if(ret==0)
							{
								ps.println("success");
								JOptionPane.showMessageDialog(null, "your session has expired !!!!!!","success", JOptionPane.INFORMATION_MESSAGE);  
								//frame.setVisible(true);
						    
							}
							else
							{
								ps.println("destroyed");
								JOptionPane.showMessageDialog(null, "your session has destroyed !!!!!!","success", JOptionPane.INFORMATION_MESSAGE);    
								//frame.setVisible(true);
							}
						
						}	
						else if(status.equals("DUPLICATE"))
						{
							JOptionPane.showMessageDialog(null, "duplicate user login...","Failed;", JOptionPane.INFORMATION_MESSAGE);
							userText.setText("");
							passwordText.setText("");
							System.out.println("error executing feast");
						}
						else if(userText.equals("")&&passwordText.equals(""))
						{
							JOptionPane.showMessageDialog(null, "you must enter a  username and password...","Failed;", JOptionPane.INFORMATION_MESSAGE);	
						}
						else
						{
							JOptionPane.showMessageDialog(null, "Invalid username or password...","Failed;", JOptionPane.INFORMATION_MESSAGE);
							userText.setText("");
							passwordText.setText("");
							System.out.println("error executing feast");
						}										
								
						
					}
					catch(Exception E)
					{
						System.out.println(E);
					}
					
					ps.close();
					dis.close();
					des.close();
					source.close();
					sc.close();
				}
				catch(Exception e1)
				{
					System.out.println("exception" +e1);
				}				
			}
	          
			}
			
		});
		
		
		final JButton cancelButton = new JButton("cancel");
		cancelButton.setBounds(180, 120, 80, 25);
		panel.add(cancelButton);
		cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
//				TODO Auto-generated method stub
//				JDialog jd=new JDialog();
//				JOptionPane.showMessageDialog(null, "re_enter your details");
				userText.setText("");
				passwordText.setText("");
				frame.setVisible(false);
				
			}
		});
		
		final JButton changepwd=new JButton("Change password");
		changepwd.setBounds(80,180,160,25);
		panel.add(changepwd);
		changepwd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(final ActionEvent e) {
// 				TODO Auto-generated method stub
				if(e.getSource()==changepwd)
				{
				System.out.println("change password");
				final JFrame frame=new JFrame("change password");
//				frame.setUndecorated(true);
				frame.setSize(300,300);
				frame.setLocation(300, 300);
				frame.setUndecorated(true);
				frame.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
				JPanel panel=new JPanel();
//				JFrame frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.add(panel);
//				placeComponents(panel);
				frame.setVisible(true);
		
//				placeComponents(JPanel panel)
				
//				LoginView lv=new LoginView();
//				lv.placeComponents(panel);
				panel.setLayout(null);
				panel.setBackground(Color.lightGray);
			
				JLabel userLabel3 = new JLabel("userID");
				userLabel3.setBounds(10, 20, 80, 25);
				panel.add(userLabel3);

				final JTextField userText3 = new JTextField(20);
				userText3.setBounds(120, 20, 160, 25);
				panel.add(userText3);
			
				JLabel passwordLabel = new JLabel("Old Password");
				passwordLabel.setBounds(10, 60, 80, 25);
				panel.add(passwordLabel);

				final JPasswordField userText = new JPasswordField(20);
				userText.setBounds(120, 60, 160, 25);
				panel.add(userText);
			
				JLabel passwordLabel1 = new JLabel("New Password");
				passwordLabel1.setBounds(10, 100, 160, 25);
				panel.add(passwordLabel1);
			
				final JPasswordField userText1 = new JPasswordField(20);
				userText1.setBounds(120, 100, 160, 25);
				panel.add(userText1);
			
			
				JLabel passwordLabel2 = new JLabel("confirm Password");
				passwordLabel2.setBounds(10, 140, 220, 25);
				panel.add(passwordLabel2);

				final JPasswordField userText2 = new JPasswordField(20);
				userText2.setBounds(120, 140, 160, 25);
				panel.add(userText2);
			
				final JButton submitButton=new JButton("OK");
				submitButton.setBounds(40, 180, 80, 25);
				panel.add(submitButton);	
				submitButton.addActionListener(new ActionListener() {
				
				//private Object String;

				@Override
					public void actionPerformed(ActionEvent arg) {
						// TODO Auto-generated method stub
						if(arg.getSource()==submitButton)
						{
							System.out.println("sending");
							String username=new String(userText3.getText());
							String oldpwd=new String(userText.getText());
							String newpwd= new String(userText1.getText());
							String password=new String(userText2.getText());
							//JDialog jd=new JDialog();
							if(newpwd.equals(password))
							{	
								System.out.println("matching");
								try
								{
									BufferedReader reader = new BufferedReader(new FileReader("D:/NetSoft/FEAST/config.txt"));
							    	String line = null;
							    	if((line = reader.readLine()) != null) {

							    		System.out.println("IP Address : " +line);
							    	}
							    	System.out.println("IP Address : " +line);
									String host=line;
									int port=11122;
									Socket sc=new Socket(host,port);
									String str[]=new String[2];
									str[0]=username;
									str[1]=password;
									String st= "UPDATE"+" "+str[0].trim() + " " + str[1].trim();
									System.out.println("Senting updation request:" +st);
									InputStream source=sc.getInputStream();
									OutputStream des=sc.getOutputStream();
									DataInputStream dis=new DataInputStream(source);
									PrintStream ps=new PrintStream(des, true);
//									DataInputStream console=new DataInputStream(System.in);
//									String line=dis.readLine();
									System.out.println("Senting data :" + st);
 //									response=console.readLine();
//									ps.println(response);
//									console.close();			
									ps.println(st);
									String status=dis.readLine();				
									System.out.println(status);
									if(status.equals("UPDATED"))
									{
										JOptionPane.showMessageDialog(null, "passsword changed successfully");
										frame.setVisible(false);
									}
									else
									{
										JOptionPane.showMessageDialog(null, "error !!! check username and password ");
									}
								}
							
								catch(Exception e)
								{
									System.out.println("Exception" +e);
								}														
							}
							else
							{
								JOptionPane.showMessageDialog(null, "check username and password ");	
							}
						}
					}	
				});
				
				JButton cancelbutton=new JButton("cancel");
				cancelbutton.setBounds(140, 180, 120, 25);
				panel.add(cancelbutton);	
				frame.getRootPane().setDefaultButton(cancelButton);
				cancelbutton.addActionListener(new ActionListener() {
				
						@Override
						public void actionPerformed(ActionEvent arg0) 
						{
//					 		TODO Auto-generated method stub
							frame.setVisible(false);
					
						}
					});
			
				
				}
			}
		});
		
	
	}
}