import java.awt.AWTException;
import java.awt.Robot;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dtools.ini.BasicIniFile;
import org.dtools.ini.IniFile;
import org.dtools.ini.IniFileReader;
import org.dtools.ini.IniItem;
import org.dtools.ini.IniSection;

import com.casic.iot.client.DeviceStateClient;
import com.casic.iot.model.request.DeviceStateRequest;
import com.casic.iot.model.response.DeviceStateResponse;

public class HongHuaDev {
	static Map<String,String> DevStatemap = new HashMap<String,String>(); 
	static Map<String,String> HADevmap = new HashMap<String,String>(); 
	static String key = null;
	static String iot = null;
	static String url = null;
	static String ip = null;
	static String usr = null;
	static String passwd= null;
	static String database = null;
	static String totaltime = null;
	static String evetime = null;
	public static void main(String[] args) throws AWTException, IOException {
		try {
			ReadIniSetting();
			DataBase db=new DataBase(); 
			
			db.setDataBase(database);
			db.setIP(ip);
			db.setPasswd(passwd);
			db.setUsr(usr);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while(true)
		{
			GetDevState();
			GetHADev();
			FindData(DevStatemap,HADevmap);
			 Robot  r   =   new   Robot(); 
			 r.delay(1000*60*Integer.valueOf(totaltime).intValue());   
			
		}		
		// TODO Auto-generated method stub	
	}
	public static void GetDevState()
	{
		DataBase dbconn=new DataBase();  
        String strSQL="select DeviceID, Para8 from UPDATEDEVICESTATE";  
        ResultSet rs=dbconn.execQuery(strSQL,new Object[]{});  
        try{  
            while(rs.next()){ 
            	 String status = null;
            	 String aa = rs.getString(2);
            	 if(rs.getString(2) != null)
            	 {
            		 if(rs.getString(2).equals("1"))
                	 {
                		 status = "3000";
                	 }
                	 else if(rs.getString(2).equals("2") || rs.getString(2).equals("0"))
                	 {
                		 status = "2000";
                	 }
                	 else if(rs.getString(2).equals("4"))
                	 {
                		 status = "1000";
                	 }
                		 
                	DevStatemap.put(rs.getString(1), status);
            	 }
            	
            	
            }  
           
        } catch(Exception e){  
            e.printStackTrace();  
        }finally{  
            dbconn.closeConn();  
        }  
	}
	public static void GetHADev()
	{
		DataBase dbconn=new DataBase();  
        String strSQL="select CAXADevID,HUDevID from HUDEVLIST";  
        ResultSet rs=dbconn.execQuery(strSQL,new Object[]{});  
        try{  
            while(rs.next()){ 
            	
            	
            	HADevmap.put(rs.getString(1), rs.getString(2));
            	int a = 0;
            }  
           
        } catch(Exception e){  
            e.printStackTrace();  
        }finally{  
            dbconn.closeConn();  
        }  
	}
	public static void FindData(Map<String,String> devStatemap, Map<String,String> hADevmap)
	{
		Iterator<Map.Entry<String, String>> entries = hADevmap.entrySet().iterator(); 
		while (entries.hasNext()) { 
		  Map.Entry<String, String> entry = entries.next(); 
		  //System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue()); 
		    if(devStatemap.containsKey(entry.getKey()))
		    {
		    	SendData(entry.getValue(),devStatemap.get(entry.getKey()));
		    	Robot r;
				try {
					  r = new   Robot();
					  r.delay(Integer.valueOf(evetime).intValue());  
				} catch (AWTException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
		        
		    }
		}
	}
	public static void SendData(String devID, String status)
	{
		DeviceStateClient client = new DeviceStateClient(url, key);

		DeviceStateRequest req = new DeviceStateRequest();

		
		req.setIot(iot);
		req.addData(devID,Integer.valueOf(status).intValue(),0l);

		DeviceStateResponse result = client.execute(req);
	}
	public static void ReadIniSetting() throws IOException
	{
		IniFile iniFile=new BasicIniFile();
		File f = new File("");
		String cf = null;
		cf = f.getCanonicalPath();
		File file=new File(cf+"\\caxaconfig.ini");
		IniFileReader rad=new IniFileReader(iniFile, file);
		//IniFileWriter wir=new IniFileWriter(iniFile, file);
		try {
			//∂¡»°item
			rad.read();
			IniSection iniSection=iniFile.getSection(0);
			IniItem iniItem=iniSection.getItem("IP");
			ip=iniItem.getValue();
			iniItem = iniSection.getItem("Database");
			database=iniItem.getValue();
			iniItem = iniSection.getItem("UID");
			usr=iniItem.getValue();
			iniItem = iniSection.getItem("PWD");
			passwd=iniItem.getValue();
			iniItem = iniSection.getItem("TOTALTIME");
			totaltime=iniItem.getValue();
			iniItem = iniSection.getItem("EVYTIME");
			evetime=iniItem.getValue();
			iniItem = iniSection.getItem("KEY");
			key=iniItem.getValue();
			iniItem = iniSection.getItem("IOT");
			iot=iniItem.getValue();
			iniItem = iniSection.getItem("URL");
			url=iniItem.getValue();
//	      		 //–¥»Î–ﬁ∏ƒ
//°°°°		     iniItem.setValue("Konan");
//			iniSection.addItem(iniItem);
//			iniFile.addSection(iniSection);
//			wir.write();
                } catch (IOException e) {}


	}
}
