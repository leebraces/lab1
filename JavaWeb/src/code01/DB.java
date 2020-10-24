package code01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DB {
	public static Connection getConnection() throws Exception {
		// TODO Auto-generated method stub
		String driver = "com.mysql.cj.jdbc.Driver";
		String userName = "root";
		String password = "123456";
		String url = "jdbc:mysql://localhost:3306/data2?serverTimezone=UTC";
	
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, userName, password);
		return conn;
	}
	
	public static ArrayList<Student> getAllStudents() {
		ArrayList<Student> students = new ArrayList<Student>();
		try {
			Connection conn=getConnection();
			PreparedStatement st=conn.prepareStatement("select * from c");
			st.execute();
			ResultSet rs=st.getResultSet();
			while(rs.next()) {
				Student student=new Student();
				student.setId(rs.getString("id"));
				student.setName(rs.getString("name"));
				student.setPassword(rs.getString("password"));
				students.add(student);
			}
			conn.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i=0;i<students.size();i++)
			System.out.println(((Student) students.get(i)).getId()+"   "+((Student) students.get(i)).getName()+"    "+((Student) students.get(i)).getPassword());
		return students;
	}
	public static boolean deleteStudent(String id) {
		try {
			Connection conn=getConnection();
			PreparedStatement st=conn.prepareStatement("delete from c where id=?");
			st.setString(1,id);
			st.execute();
			conn.close();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	public static boolean addStudent(String id,String name,String password) {
		try {
			Connection conn=getConnection();
			PreparedStatement st=conn.prepareStatement("insert into c values(?,?,?)");
			st.setString(1,id);
			st.setString(2,name);
			st.setString(3,password);
			st.execute();
			conn.close();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	public static boolean Login(String id,String password) {
		ArrayList students=getAllStudents();
		for(int i=0;i<students.size();i++) {
			if(((Student) students.get(i)).getId().equals(id)&&(((Student) students.get(i)).getPassword().equals(password))) {
		
				return true;
			}
		}
		return false;
	}
}
