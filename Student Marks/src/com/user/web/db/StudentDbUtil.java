package com.siddhant.web.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.siddhant.web.beans.Student;

public class StudentDbUtil {
	
	private DataSource dataSource;

	public StudentDbUtil(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public Student loadStudent(String studentId) {
		
		Student student = null;
		
		//CONNECT TO DATABASE
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try{
			//GET A CONNECTION
			conn = dataSource.getConnection();
			
			//CREATE SQL STATEMENT
			String sql = "select * from student where id=?";
			
			//CREATE PREPARED STATEMENT
			stmt = conn.prepareStatement(sql);
			
			//SET PARAMS
			stmt.setInt(1,Integer.parseInt(studentId));
			
			//EXECUTE QUERY
			rs = stmt.executeQuery();
			
			//GET DATA
			if(rs.next()){
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String email = rs.getString("email");
				
				student = new Student(firstName, lastName, email);
				student.setId(Integer.parseInt(studentId));
			}else{
				throw new Exception("Couln't find the student id:"+studentId);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close(conn, stmt, rs);
		}
		
		return student;
	}
	
	public void addStudent(Student student){
		
		//CONNECT TO THE DATABASE
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try{
			
			//GET A CONNECTION
			conn = dataSource.getConnection();
			
			//CREATE SQL STATEMENT
			String sql = "insert into student"
					+ "(first_name,last_name,email)"
					+ "values (?,?,?)";
			
			//SET THE PARAM VALUES FOR STUDENT
			stmt = conn.prepareStatement(sql);
			stmt.setString(1,student.getFirstName());
			stmt.setString(2, student.getLastName());
			stmt.setString(3, student.getEmail());
			
			//EXECUTE THE QUERY
			stmt.execute();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close(conn, stmt, null);
		}
	}
	
	public List<Student> getStudents() throws Exception{
		List<Student> students = new ArrayList<Student>();
		
		//CONNECT TO DATABASE
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try{
			
		//GET A CONNECTION
		conn = dataSource.getConnection();
		
		//CREATE SQL STATEMENT
		String sql = "select * from student order by last_name";
		stmt = conn.createStatement();
		
		//EXECUTE QUERY
		rs = stmt.executeQuery(sql);
		
		//PROCESS RESULT SET
		while(rs.next()){
			
			int id = rs.getInt("id");
			String firstName = rs.getString("first_name");
			String lastName = rs.getString("last_name");
			String email = rs.getString("email");
			
			Student student = new Student(id,firstName,lastName,email);
			students.add(student);
		}
		
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			//CLOSE JDBC OBJECT
			close(conn,stmt,rs);
		}
		
		return students;
	}

	private void close(Connection conn, Statement stmt, ResultSet rs) {
		try {
			if(conn != null) conn.close();
			if(stmt != null) stmt.close();
			if(rs != null) rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateStudent(Student student) {
		
		//DECLARE CONNECTION VARIABLES
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try{
		//GET A CONNECTION
		conn = dataSource.getConnection();
		
		//PREPARE SQL QUERY
		String sql = "update student "
				+ "set first_name = ?,last_name = ?,email = ? "
				+ "where id=?";
		
		//CREATE PREPARED STATEMENT
		stmt = conn.prepareStatement(sql);
		
		//SET PARAMS
		stmt.setString(1, student.getFirstName());
		stmt.setString(2, student.getLastName());
		stmt.setString(3, student.getEmail());
		stmt.setInt(4, student.getId());
		
		//EXECUTE QUERY
		stmt.execute();
		
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close(conn, stmt, null);
		}
		
	}

	public void deleteStudent(int studentId) {
		
		//INIT CONNECTION VARIABLES
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try{
			//GET A CONNECTION
			conn = dataSource.getConnection();
			
			//PREPARE SQL QUERY
			String sql = "delete from student where id=?"; 
			
			//CREATE STATEMENT
			stmt = conn.prepareStatement(sql);
			
			//SET PARAMS
			stmt.setInt(1, studentId);
			
			//EXECUTE STATEMENT
			stmt.execute();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close(conn, stmt, null);
		}
		
	}

}
