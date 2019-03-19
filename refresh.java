package server;

public class refresh 
{
	public static void main(String[] args) throws Exception
	{
		feastDB db=new feastDB();	
		db.refresh();
		System.out.println("database updted suiccessfully");
	}
	
}
