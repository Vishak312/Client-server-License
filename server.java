package server;

import java.io.BufferedInputStream;


import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;
import java.util.Set;

public class server {

	public static void main(String[] args) throws Exception
	{
		ServerSocket clientRequest=new ServerSocket(11122);		
		boolean flag=true;
		while(flag)
		{
			System.out.println("waiting for client request");
			Socket clientSocket=clientRequest.accept();
			new CommunicationThread(clientSocket);			
		}
		clientRequest.close();
	}

}
	
//client side communication
	
class CommunicationThread extends Thread
{
	Socket socket;
	feastDB db=new feastDB();	
	Runtime r=Runtime.getRuntime();
	String fs;
	Process p;
//	int hitcount=0;
	int flag=0;
    //String p=null;
	CommunicationThread(Socket s) //Constructor
	{
		socket=s;
		start();//Thread Starting
	}
	public void run()
	{
		
		try
		{
			InputStream source=socket.getInputStream();
			OutputStream des=socket.getOutputStream();
			DataInputStream dis=new DataInputStream(source);
			PrintStream ps=new PrintStream(des, true);	
			DataOutputStream dos= new DataOutputStream(des);
			OutputStream os = socket.getOutputStream();
		    String line=dis.readLine();
			String[] st;
			st=line.split(" ");
			if(st.length < 3)
			{
				System.out.println("Invalid Input argument");
				ps.println("failed");
				return;
			}
			
			switch(st[0])
			{
			case "LOGIN":			
				int ret = db.checkLogin(st[1], st[2]);
				
				if( ret== 1)
				{
					System.out.println("Sending SUCCESS");
					ps.println("SUCCESS");
					String status=dis.readLine();
					System.out.println("login status : " +status);
					File myFile = new File("D:\\intranet local-copy/FEAST-32Bit.zip");
					byte[] mybytearray = new byte[(int) myFile.length()];
					FileInputStream fis = new FileInputStream(myFile);
					BufferedInputStream bis = new BufferedInputStream(fis);
					DataInputStream dis_1 = new DataInputStream(bis);   
					dis_1.readFully(mybytearray, 0, mybytearray.length);				    
					dos.writeUTF(myFile.getName());   
			        dos.writeLong(mybytearray.length);   
			        dos.write(mybytearray, 0, mybytearray.length);   
			        dos.flush();
			       	dis_1.close();
			       	fis.close();
			       	bis.close();		       	
			       // os.write(mybytearray, 0, mybytearray.length);
					os.flush();
					Calendar date =Calendar.getInstance();
					int day = date.get(Calendar.DAY_OF_MONTH); 
			        int month = date.get(Calendar.MONTH);
			        int year = date.get(Calendar.YEAR);
			 
			        int second = date.get(Calendar.SECOND);
			        int minute = date.get(Calendar.MINUTE);
			        int hour = date.get(Calendar.HOUR);
			        String logintime=+hour+" : "+minute+": "+second;
			        String lndate=+day+"/"+(month+1)+"/"+year;
			        System.out.println("login date is : " +lndate);
//			        System.out.println("login time is  "+hour+" : "+minute+" : "+second);
			        System.out.println("login time :" +logintime);				 
					System.out.println("login Date received : " +lndate);
					System.out.println("logintime received : " +logintime);												
					if(db.userstatus(st[1],status))
					{
						System.out.println("status received");		
						ps.println("LOGIN");
						System.out.println(st[1]);
						
					}
					else
					{
						System.out.println("status not received");
					}	
					
				//
					if(db.insertlog(st[1]))
					{
						System.out.println("user Added");
					}
					else
					{
						System.out.println("user not added ");
					}
					
					if(db.logindate(st[1], lndate))
					{
						System.out.println("login date entered");
					}
					else
					{
						System.out.println("login date not entered");
					}
					
				
					if(db.logindetails(st[1], logintime))
					{
						System.out.println("login details entered");
					}
					else
					{
						System.out.println("login details not entered");
					}
					
				//	
					status=dis.readLine();
					System.out.println("logout status:" +status );
					
					if(db.userstatus(st[1], status))
					{
						System.out.println("status changed");
						
					}
					else
					{
						System.out.println("status not yet changed");
					}
					
					Calendar date1 =Calendar.getInstance();	
					int second1 = date1.get(Calendar.SECOND);
					int minute1 = date1.get(Calendar.MINUTE);
					int hour1 = date1.get(Calendar.HOUR);
					String logouttime=+hour1+" : "+minute1+": "+second1;
					System.out.println("logouttime : " +logouttime);					    

					if(db.logoutdetails(st[1], logouttime))
					{
						System.out.println("logout details entered");
					}
					
					else
					{
						System.out.println("logout details not entered");
					}
					
					double totaltime;
					if(second1>=second)
					{
						double totalsec=(second1-second)/60.0;						
						double totalmin=minute1-minute;								
						double totalhour=((hour1-hour)*60.0);							
						totaltime=totalsec+totalmin+totalhour;								
						System.out.println("Used Time: " +totaltime);
						
					}  
					else
					{
						double totalsec=(((second1+60)-second)/60.0);								
						double totalmin=((minute1-1)-minute);
						double totalhour=((hour1-hour)*60.0);
						totaltime=totalsec+totalmin+totalhour;
						System.out.println("Used time: " +totaltime);
					}
					
					if(db.totaltimedetails(st[1], totaltime))
					{
						System.out.println("logout details entered");
					}
					else
					{
						System.out.println("logout details not entered");
					}
					
					
					String laststatus=dis.readLine();
					System.out.println("laststatus : " +laststatus);
					if(db.lastloginstatus(st[1], laststatus))
					{
						System.out.println("last status added");
					}
					else
					{
						System.out.println("last status not added");
					}
					return;
				}
				else if(ret == 2)
				{
					System.out.println("Sending Already logged In");
					ps.println("DUPLICATE");
				}else{
					System.out.println("Sending FAILED");	
					ps.println("FAILED");						
				}
				
				break;
			case "UPDATE":
				if(db.updatepwd(st[1],st[2]))
				{
					System.out.println("updation success");
					ps.println("UPDATED");
				}
				else
				{ 
					System.out.println("Updation Failed");
					ps.println("FAILED");
				}
				
				break;
				
			default:
				System.out.println("Invalid Input argument");
				ps.println("failed");
				break;			
			}
			db.close();
			dos.close();
			os.close();
			ps.close();
			dis.close();
			des.close();
			source.close();
			socket.close();
			
		}
		catch(Exception e1)
		{
			System.out.println(e1);
			
		}
	}
			
			
}
	


