package com.siddhant.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.siddhant.web.beans.Student;
import com.siddhant.web.db.StudentDbUtil;

/**
 * Servlet implementation class StudentControllerServlet
 */
@WebServlet("/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private StudentDbUtil studentDbUtil;
	
	@Resource(name="jdbc/web_student_tracker")
	private DataSource datasource;

	
	@Override
	public void init() throws ServletException {
		super.init();
		
		try{
			studentDbUtil = new StudentDbUtil(datasource);
		}catch(Exception e){
			throw new ServletException(e);
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String command = request.getParameter("command");
			if(command == null) command = "LIST";
			
			switch(command){
			
			case "ADD":
				addStudent(request,response);
				break;
				
			case "LIST":
				listStudents(request,response);
				break;
				
			case "LOAD":
				loadStudent(request,response);
				break;
				
			case "UPDATE":
				updateStudent(request,response);
				break;
				
			case "DELETE":
				deleteStudent(request,response);
				break;
				
			default:
				listStudents(request,response);
				
			}
			
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int studentId = Integer.parseInt(request.getParameter("studentId"));
		studentDbUtil.deleteStudent(studentId);
		listStudents(request,response);
	}

	private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int studentId = Integer.parseInt(request.getParameter("studentId"));
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		
		Student student = new Student(studentId,firstName,lastName,email);
		studentDbUtil.updateStudent(student);
		
		listStudents(request,response);
	}

	private void loadStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String studentId = request.getParameter("studentId");
		
		Student student = studentDbUtil.loadStudent(studentId);
		request.setAttribute("STUDENT", student);
		
		RequestDispatcher rd = request.getRequestDispatcher("/update-student-form.jsp");
		rd.forward(request, response);
		
	}

	private void addStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		
		Student student = new Student(firstName,lastName,email);
		
		studentDbUtil.addStudent(student);
		
		listStudents(request,response);
	}

	private void listStudents(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		List<Student> students = studentDbUtil.getStudents();
		request.setAttribute("STUDENT_LIST", students);
		
		RequestDispatcher rd = request.getRequestDispatcher("/list-students.jsp");
		rd.forward(request, response);
	}

}
