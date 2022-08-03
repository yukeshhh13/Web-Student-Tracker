package com.siddhant.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// DEFINE CONNECTION POOL FOR RESOURCE INJECTION
	@Resource(name="jdbc/web_student_tracker")
	private DataSource datasource;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		//SETTING UP THE PRINTWRITER
		
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");
		
		//GET A CONNECTION TO DB
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try{
			
			conn = datasource.getConnection();
			
			//CREATE A SQL STATEMENT
			String sql = "select * from student";
			stmt = conn.createStatement();
			
			//EXECUTE THE SQL STATEMENT
			rs = stmt.executeQuery(sql);
			
			//PROCESS THE RESULT
			while(rs.next()){
				String email = rs.getString("email");
				out.println(email);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
