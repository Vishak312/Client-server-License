package server;

import java.sql.*;

public class feastDB 
{
    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    feastDB()
    {
        try{
			String url="jdbc:mysql://localhost:3306/feast";
			String username = "root";
			String password="ROOT";
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection(url,username,password);
			System.out.println("Database connection established");
		
        }
        catch (Exception e) 
        {
            System.out.println(e);
        }
    }
    
   //refreshing the data base 
    
    public boolean refresh()
    {
    	try {
    		pst=con.prepareStatement("update users set status='LOGOUT'");
			if(pst.executeUpdate()==1)
			{
				System.out.println("DataBase updated successfully");
			}
			else
			{
				System.out.println("DataBase cannot updted");
			}
    	}
    	catch(Exception e)
    	{
    		System.out.println(e);
    	}
		return true;
    }
    
    //admin login
    public boolean checkadminLogin(String uname,String pwd)
    {
    	try {
        	pst=con.prepareStatement("select * from adminlogin where adminID=? and password=?");
            System.out.println("Administrator = "+uname);
            System.out.println("Password = "+pwd);
            pst.setString(1, uname);
            pst.setString(2, pwd);
            rs=pst.executeQuery();
            if(rs.next())
            {
            	System.out.println("admin password matched");
            	
            	return true;
            	
            }
            else
            {
            	System.out.println("admin password not matched");
            	return false;
            }
    	}
    	catch(Exception e)
    	{
    		System.out.println(e);
    	}
		return true;
    }
    
    //adding new user
    
    public Boolean adduser(String user , String pass, String name, String section, String empid, String phno, String mailid)
    {
    	try
    	{
    		pst=con.prepareStatement("insert into users(userID,password,name,section,empid,phno,mail_id)values (? , ? , ? , ? , ? , ? , ? ) ");
			System.out.println("user id " + user);	
			System.out.println("password " + pass);
			System.out.println("name " + name);
			System.out.println("section " + section);
			System.out.println("emp id " + empid);
			System.out.println("phno " + phno);
			System.out.println("mailid " + mailid);
			//System.out.println("CDate " + cdate);
		//	System.out.println("CTime " + ctime);
			//System.out.println("status " + status);
			//System.out.println("last status " + laststatus);
			pst.setString(1, user);
			pst.setString(2, pass);
			pst.setString(3, name);
			pst.setString(4, section);
			pst.setString(5, empid);
			pst.setString(6, phno);
			pst.setString(7, mailid);
			//pst.setString(8, cdate);
			//pst.setString(9, ctime);
			//pst.setString(10, status);
			//pst.setString(11, laststatus);
			
			if(pst.executeUpdate()==1)
			{
				System.out.println("new user added");
			}
			
			else
			{
				System.out.println("new user not added");
			}
    		
    	}
    	catch(Exception e)
    	{
    		System.out.println(e);
    	}
    	return true;
    	
    }
    public Boolean createtime(String user,String ctime)
    {
    	try
    	{
    		pst=con.prepareStatement("update users set C_Time= ? where userID= ?");
    		System.out.println("UserID =" +user);
			System.out.println("C_Time= " +ctime);
			pst.setString(2, user);
			pst.setString(1, ctime);		
			if(pst.executeUpdate()==1)
			{
				System.out.println("creation time added");
			}
			else
			{
				System.out.println("creation time not added");
			}
//			con.commit();
		}
    		
    	
    	catch(Exception e)
    	{
    		System.out.println("exception" +e);
    	}
    	return true;
    }
    public Boolean createdate(String user,String cdate)
    {
    	try
    	{
    		pst=con.prepareStatement("update users set C_Date= ? where userID= ?");
    		System.out.println("UserID =" +user);
			System.out.println("C_Time= " +cdate);
			pst.setString(2, user);
			pst.setString(1, cdate);		
			if(pst.executeUpdate()==1)
			{
				System.out.println("Creation date added");
			}
			else
			{
				System.out.println("not added");
			}
//			con.commit();
		}
    		
    	
    	catch(Exception e)
    	{
    		System.out.println("exception" +e);
    	}
    	return true;
    }
    
   
    
    //delete user
    public String deletecheckuser()
    {
    	String str = "";
    	try
    	{
    		pst = con.prepareStatement("select userID from users");
    		rs=pst.executeQuery();
    		while(rs.next())
    		{
    			 
    			str+= rs.getString(1);
    			str +=",";
    			System.out.println(str);
    		}
    		int a=rs.getRow();
    		System.out.println("number of Rows : " +a);
    	}
    	catch(Exception e)
    	{
    		System.out.println(e);
    	}
    	return str;
    	
    }
    
    public Boolean deleteuser(String userid)
    {
    	try
    	{
    		pst=con.prepareStatement("delete from users where userID= ? ");
    		System.out.println("userID : " +userid);
    		pst.setString(1,userid);
    		if(pst.executeUpdate()==1)
    		{
    			System.out.println("user deleted successsfully");
    		}
    		else
    		{
    			System.out.println("user not deleted successfully");
    		}
    			
    	}
    	catch(Exception e)
    	{
    		System.out.println(e);
    	}
    	return true;
    }
    
    //updating admin password
    public Boolean updateadminpwd(String admin,String conpwd)
    {
		try
		{
			pst=con.prepareStatement("update adminlogin set password=? where adminID=?");
			System.out.println("AdminID=" +admin);
			System.out.println("Password=" +conpwd);
			pst.setString(2, admin);
			pst.setString(1, conpwd);		
			if(pst.executeUpdate()==1)
			{
				System.out.println("Password updated");
			}
			else
			{
				System.out.println("not updated");
			}
//			con.commit();
		}
		catch(Exception e)
		{
			System.out.println("error while updating");
		}
		return true;
		
    	
    	//return true;    	
    }
    
    //updating user
    public Boolean updateusers(String user,String pass,String name,String section,String empid,String phno,String mailid,String cdate,String ctime,String status,String laststatus) 
	{
		try
		{
			pst=con.prepareStatement("update users set userID= ?, password= ?, name= ?, section= ?, empid= ?, phno= ?, mail_id= ?, C_Date= ?, C_Time= ?, status= ?, laststatus= ? where userID= ? ");
			pst.setString(1, user);
			pst.setString(2, pass);
			pst.setString(3, name);
			pst.setString(4, section);
			pst.setString(5, empid);
			pst.setString(6, phno);
			pst.setString(7, mailid);
			pst.setString(8, cdate);
			pst.setString(9, ctime);
			pst.setString(10, status);
			pst.setString(11, laststatus);
			pst.setString(12, user);
			if(pst.executeUpdate()==1)
			{
				System.out.println("user updated");
			}
			else
			{
				System.out.println("user not updated");
			}
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
    	
    	return true;
		
	}
    
    public String userupdatecheck()
    {
    		String str = "";
        	try
        	{
        		pst = con.prepareStatement("select userID from users");
        		rs=pst.executeQuery();
        		while(rs.next())
        		{
        			 
        			str+= rs.getString(1);
        			str +=",";
        			System.out.println(str);
        		}
        		int a=rs.getRow();
        		System.out.println("number of Rows : " +a);
        	}
        	catch(Exception e)
        	{
        		System.out.println(e);
        	}
        	return str;
    	
    }
    
    public String selectuser(String userid)
    {
    	String str = null;
    	 try 
    	 {
    		String user = null;
    		String pass = null;
    		String name = null;
    		String section = null;
    		String empid = null;
    		String phno = null;
    		String mailid = null;
    		String cdate = null;
    		String ctime = null;
    		String status = null;
    		String laststatus = null;
    		
    		pst=con.prepareStatement("select * from users where userID= ? ");
            System.out.println("userID : " +userid);
            pst.setString(1, userid);
            rs=pst.executeQuery();
            if(rs.next())
            {
	            user=rs.getString(1);
	            pass=rs.getString(2);
	            name=rs.getString(3);
	            section=rs.getString(4);
	            empid=rs.getString(5);
	            phno=rs.getString(6);
	            mailid=rs.getString(7);
	            cdate=rs.getString(8);
	            ctime=rs.getString(9);
	            status=rs.getString(10);
	            laststatus=rs.getString(11);
            
				System.out.println("UserID = "+user);
				System.out.println("Password = "+pass);
				System.out.println("Username = "+name);
				System.out.println("Division = "+section);
				System.out.println("EmpID = "+empid);
				System.out.println("PhNo = "+phno);
				System.out.println("MailID = "+mailid);
				System.out.println("C_date = "+cdate);
				System.out.println("C_time = "+ctime);
				System.out.println("STATUS = "+status);
				System.out.println("LASTSTATUS = "+laststatus);
  //  pst.setString(1, userid);
          //  System.out.println("userID :" +userid);
            }
            str=user.trim()+","+pass.trim()+","+name.trim()+","+section.trim()+","+empid.trim()+"," +phno.trim()+","+mailid.trim()+","+cdate.trim()+","+ctime.trim()+","+status.trim()+","+laststatus.trim();
            return str;
             
            
    	 }
    	 catch(Exception e)
    	 {
    		 
    	 }
		
    	return str;
    }
    
//viewing user details
    
    public String view()
    {
    	String str = "";
    	try
    	{
    		pst = con.prepareStatement("select name from users");
    		rs=pst.executeQuery();
    		while(rs.next())
    		{
    			 
    			str+= rs.getString(1);
    			str +=",";
    			System.out.println(str);
    		}
    		int a=rs.getRow();
    		System.out.println("number of Rows : " +a);
    	}
    	catch(Exception e)
    	{
    		System.out.println(e);
    	}
    	return str;
    }
    
    public String viewuserdetails(String user)
    {
    	String str = null;
    	 try 
    	 {
    		String userID = null;
    		String pass = null;
    		String name = null;
    		String section = null;
    		String empid = null;
    		String phno = null;
    		String mailid = null;
    		String cdate = null;
    		String ctime = null;
    		String status = null;
    		String laststatus = null;
    		
    		pst=con.prepareStatement("select * from users where name= ? ");
            System.out.println("username : " +user);
            pst.setString(1, user);
            rs=pst.executeQuery();
            if(rs.next())
            {
	            userID=rs.getString(1);
	            pass=rs.getString(2);
	            name=rs.getString(3);
	            section=rs.getString(4);
	            empid=rs.getString(5);
	            phno=rs.getString(6);
	            mailid=rs.getString(7);
	            cdate=rs.getString(8);
	            ctime=rs.getString(9);
	            status=rs.getString(10);
	            laststatus=rs.getString(11);
            
				System.out.println("UserID = "+user);
				System.out.println("Password = "+pass);
				System.out.println("Username = "+name);
				System.out.println("Division = "+section);
				System.out.println("EmpID = "+empid);
				System.out.println("PhNo = "+phno);
				System.out.println("MailID = "+mailid);
				System.out.println("C_date = "+cdate);
				System.out.println("C_time = "+ctime);
				System.out.println("STATUS = "+status);
				System.out.println("LASTSTATUS = "+laststatus);
  //  pst.setString(1, userid);
          //  System.out.println("userID :" +userid);
            }
            str=userID.trim()+","+pass.trim()+","+name.trim()+","+section.trim()+","+empid.trim()+"," +phno.trim()+","+mailid.trim()+","+cdate.trim()+","+ctime.trim()+","+status.trim()+","+laststatus.trim();
            return str;
             
            
    	 }
    	 catch(Exception e)
    	 {
    		 System.out.println(e);
    	 }
		
    	return str;
    }
    
    // Login Client
    
  public int checkLogin(String uname,String pwd)
    {
        try {
        	pst=con.prepareStatement("select status from users where userID=? and password=?");
            System.out.println("Username = "+uname);
            System.out.println("Password = "+pwd);
            pst.setString(1, uname);
            pst.setString(2, pwd);
            rs=pst.executeQuery();
            if(rs.next())
            {
            	String status = rs.getString(1);
            	System.out.println("status :" +status);
            	if(status.equals("LOGIN"))
            	{
                    System.out.println("Already Logged In");              
            		return 2;
            	}
            	else
            	{         		
                    System.out.println("username and password matched");                                       
                    return 1; 
            	}
            }
            else
            {
                return 0;
            }
            
        	}
         catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println("error while validating"+e);
            return 0;
        }
    
    }
   
   	//
   public Boolean userstatus(String user,String sts)
    {
    	try
    	{
    		pst=con.prepareStatement("update users set status=? where userID=?");
    		System.out.println("Username :" +user);
    		System.out.println("status :" +sts);
    		pst.setString(1, sts);
    		pst.setString(2, user);
    		if(pst.executeUpdate()==1)
    		{
    			System.out.println("status updated");
    		}
    		else
    		{
    			System.out.println("status not updated");
    		}
    		
    		
    	}
    	catch(Exception e)
    	{
    		System.out.println("status not yet received");
    	}
    	return true;
    }
   
   
   public boolean insertlog(String user)
   {
	  try
	  {
		   
		   pst=con.prepareStatement("insert into usagestatus (userID) values(?)");
		   System.out.println("Username : " + user);
		   pst.setString(1,user);
		   pst.executeUpdate();
	   }   
	   catch(Exception e)
	   {
		   System.out.println("over writting the details");
	   }
	return true;
	   
   }
   
   
    public Boolean logindate(String user,String lndate)
    {
    	try
    	{ 		
    		pst=con.prepareStatement("update usagestatus set lldate=? where userID=?");
    		System.out.println("Username : " +user);
    		System.out.println("login date : " +lndate);
    		pst.setString(1,lndate);
    		pst.setString(2,user);
    		if(pst.executeUpdate()==1)
    		{
    			System.out.println("login date added");
    		}
    		else
    		{
    			System.out.println("login date not added");
    		} 
    		pst=con.prepareStatement("update usagestatus set hitcount=hitcount + 1 where userID=?");
    		pst.setString(1, user);
    		pst.executeUpdate();
    	}
    	catch(Exception e)
    	{
    		System.out.println("login date cannot updated");
    	}
    
		return true;
    }
    public Boolean logindetails(String user,String lntime)
    {
    	try
    	{ 		
    		pst=con.prepareStatement("update usagestatus set llitime=? where userID=?");
    		System.out.println("Username : " +user);
    		System.out.println("login time : " +lntime);
    		pst.setString(1,lntime);
    		pst.setString(2,user);
    		if(pst.executeUpdate()==1)
    		{
    			System.out.println("login time added");
    		}
    		else
    		{
    			System.out.println("login time not added");
    		}
    		//con.commit();
    	}
    	catch(Exception e)
    	{
    		System.out.println("login time cannot updated");
    	}
    	
		return true;
    }
    
    public Boolean logoutdetails(String user,String logouttime)
    {
    	try
    	{
    		pst=con.prepareStatement("update usagestatus set llotime=? where userID=?");
    		System.out.println("Username : " +user);
    		System.out.println("logout time : " +logouttime);
    		pst.setString(1,logouttime);
    		pst.setString(2,user);
    		if(pst.executeUpdate()==1)
    		{
    			System.out.println("logout time added");
    		}
    		else
    		{
    			System.out.println("logout time not added");
    		}
    	}
    	catch(Exception e)
    	{
    		System.out.println(e);
    	}
		return true;
     }
    public Boolean totaltimedetails(String user,double totaltime)
    {
    	try
    	{ 		
    		pst=con.prepareStatement("select totalusage from  usagestatus where userID=?");
    		System.out.println("Username : " +user);
    		System.out.println("total time : " +totaltime);
 //   		pst.setDouble(1,totaltime);
    		pst.setString(1,user);
    		rs=pst.executeQuery();
    		
    		if(rs.next())
    		{
    			double totalusage=rs.getDouble(1);
    			pst=con.prepareStatement("update usagestatus set totalusage=? where userID=?");
    			System.out.println("totalusage is : " + (totaltime+totalusage));
    			System.out.println("user name : "+user);
    			pst.setDouble(1, (totaltime+totalusage));
    			pst.setString(2, user);
    			if(pst.executeUpdate()==1)
    			{
    				System.out.println("total time added");
    			}
    			else
    			{
    				System.out.println("total time not added");
    			}
    		}
    		else
    		{
    				System.out.println("total usage not selected ");
    		}
    	}
    	catch(Exception e)
    	{
    		System.out.println(e);
    	}
		return true;
     }
    public Boolean lastloginstatus(String user,String status)
    {
    	try
    	{ 		
    		pst=con.prepareStatement("update users set laststatus=? where userID=?");
    		System.out.println("Username : " +user);
    		System.out.println("last status : " +status);
    		pst.setString(1,status);
    		pst.setString(2,user);
    		if(pst.executeUpdate()==1)
    		{
    			System.out.println("status added");
    		}
    		else
    		{
    			System.out.println("status not added");
    		}
    	}
    	catch(Exception e)
    	{
    		System.out.println(e);
    	}
		return true;
     }

    public Boolean updatepwd(String user,String conpwd)
    {
		try
		{
			pst=con.prepareStatement("update users set password=? where userID=?");
			System.out.println("Username=" +user);
			System.out.println("Password=" +conpwd);
			pst.setString(2, user);
			pst.setString(1, conpwd);		
			if(pst.executeUpdate()==1)
			{
				System.out.println("Password updated");
			}
			else
			{
				System.out.println("not updated");
			}
			//con.commit();
		}
		catch(Exception e)
		{
			System.out.println("error while updating" +e);
		}
		return true;
		
    	
    	//return true;    	
    }
   
    

    protected void close()
    {
        try{
            if(con != null) con.close();
        } catch(Exception e)
        {
            System.out.println("error while closing DB connection"+e);        	
        }
    }
}

