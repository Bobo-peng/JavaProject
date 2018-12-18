import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataBase {
	    static private Connection conn=null;  
	    static private PreparedStatement pstmt=null;  
	    static private ResultSet rs=null; 
	    
	    String driver="com.microsoft.sqlserver.jdbc.SQLServerDriver";  

	   static String database=null;  
	   static String user=null;  
	   static String password=null;  
	   static String ip=null;  
	    //�ĸ�����  
	    //method1���������ݿ������  
	    private void getConntion(){ 
	    	String durl="jdbc:sqlserver://"+ip+":1433;databaseName="+database;  
		    String duser=user;  
		    String dpassword=password;  
	    	try{   
	            //������������  
	            Class.forName(driver);  
	            //����sqlserver���ݿ�  
	           conn=DriverManager.getConnection(durl,duser,dpassword);  
	           System.out.println("���ݿ����ӳɹ�");
	        } catch(ClassNotFoundException e){  
	            e.printStackTrace();  
	        } catch(SQLException e){  
	            e.printStackTrace();  
         }  
	    }
	    
	    //method2:�ر����ݿ�����  
	    public void closeConn(){  
	        if(rs!=null){  
	            try{  
	                rs.close();  
	            } catch(SQLException e){  
	                e.printStackTrace();  
	            }  
	        }  
	        if(pstmt!=null){  
	            try{  
	                pstmt.close();  
	                  
	            } catch(SQLException e){  
	                e.printStackTrace();  
	            }  
	        }  
	        if(conn!=null){  
	            try{  
	                conn.close();  
	            } catch(SQLException e){  
	                e.printStackTrace();  
	            }  
	        }  
	          
	    }  
	      
	    //method3:ר�����ڷ�����ɾ�����ķ���  
	    public int execOther(final String strSQL, final Object[] params){  
	        //����  
	        
	        System.out.println("SQL:>"+strSQL);  
	        try{  
	            //����statement�ӿڶ���  
	            pstmt=conn.prepareStatement(strSQL);  
	            //��̬Ϊpstmt����ֵ  
	            for(int i=0;i<params.length;i++){  
	                pstmt.setObject(i+1, params[i]);  
	            }  
	            //ʹ��Statement������SQL���  
	            int affectedRows=pstmt.executeUpdate();  
	            return affectedRows;  
	              
	              
	        } catch(SQLException e){  
	            e.printStackTrace();  
	            return -1;  
	        }  
	    }  
	      
	    //method4:ר�����ڷ��Ͳ�ѯ���  
	    public ResultSet execQuery(final String strSQL,final Object[] params){  
	        getConntion();  
	        System.out.println("SQL:>"+strSQL);  
	        try{  
	            pstmt=conn.prepareStatement(strSQL);  
	            for(int i=0;i<params.length;i++){  
	                pstmt.setObject(i+1, params[i]);  
	            }  
	            rs=pstmt.executeQuery();  
	            //closeConn();
	            return rs; 
	            
	        } catch(SQLException e){  
	            e.printStackTrace();  
	            return null;  
	        }  
	    } 
	    public void GetDataBaseSetting(String ip,String database,String usr,String passwd)
	    {
	    	String url="jdbc:sqlserver://"+ip+":1433;databaseName="+database;  
		    String user=usr;  
		    String password=passwd;  
	    }
	    public void setIP(String Ip) {  
	    	ip = Ip;  
	    }
	    public void setDataBase(String Database) {  
	    	database = Database;  
	    }
	    public void setUsr(String usr) {  
	    	user = usr;  
	    }
	    public void setPasswd(String Passwd) {  
	    	password = Passwd;  
	    }

}
