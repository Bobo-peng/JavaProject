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
	    //四个方法  
	    //method1：创建数据库的连接  
	    private void getConntion(){ 
	    	String durl="jdbc:sqlserver://"+ip+":1433;databaseName="+database;  
		    String duser=user;  
		    String dpassword=password;  
	    	try{   
	            //加载连接驱动  
	            Class.forName(driver);  
	            //连接sqlserver数据库  
	           conn=DriverManager.getConnection(durl,duser,dpassword);  
	           System.out.println("数据库连接成功");
	        } catch(ClassNotFoundException e){  
	            e.printStackTrace();  
	        } catch(SQLException e){  
	            e.printStackTrace();  
         }  
	    }
	    
	    //method2:关闭数据库连接  
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
	      
	    //method3:专门用于发送增删改语句的方法  
	    public int execOther(final String strSQL, final Object[] params){  
	        //连接  
	        
	        System.out.println("SQL:>"+strSQL);  
	        try{  
	            //创建statement接口对象  
	            pstmt=conn.prepareStatement(strSQL);  
	            //动态为pstmt对象赋值  
	            for(int i=0;i<params.length;i++){  
	                pstmt.setObject(i+1, params[i]);  
	            }  
	            //使用Statement对象发送SQL语句  
	            int affectedRows=pstmt.executeUpdate();  
	            return affectedRows;  
	              
	              
	        } catch(SQLException e){  
	            e.printStackTrace();  
	            return -1;  
	        }  
	    }  
	      
	    //method4:专门用于发送查询语句  
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
