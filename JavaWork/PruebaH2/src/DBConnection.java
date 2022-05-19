import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	
	static Connection conn=null;
	
	static Connection getConnection() {
		
		try {
			Class.forName("org.h2.Driver");
			conn=DriverManager.getConnection("jdbc:h2:E:\\JavaWork\\PruebaH2\\lib\\database","sa","1234");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return conn;
		
	}

}


















/*import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
	
	Connection cn = null;
	Statement st;
	ResultSet rs;
	
		public DBConnection(){
			
			try {
				Class.forName("org.h2.Driver");
				cn = DriverManager.getConnection("jdbc:h2:lib//database","Samael","2281337");
				st = cn.createStatement();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		void probar() {
			
			try {
				rs = st.executeQuery("SELECT * FROM TEST");
				while(rs.next()) {
					System.out.println("ID: "+rs.getInt("id")+"\t"+ "Name: "+rs.getString("name")+"\t"+"Last Name "+rs.getString("last name"));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
}*/
