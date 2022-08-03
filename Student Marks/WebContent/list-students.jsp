<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>

	<title>Student Tracker App</title>

	<link type="text/css" rel="stylesheet" href="css/style.css">
</head>
<body>

	<div id="wrapper">
		<div id="wrapper">
			<h2>Rutgers University</h2>
		</div>
	</div>
	
	<div id="container">
	
		<!-- Add a button to add student -->
		<input type="button" value="Add Student" class="add-student-button" 
			onclick="window.location.href='add-student-form.jsp'; return false;"/>
		
		<div id="content">
			<table>
				<tr>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Email</th>
					<th>Action</th>
				</tr>
				<c:forEach var="student" items="${STUDENT_LIST}">
				
					<!-- Set up a link for each student -->
					<c:url var="updateLink" value="StudentControllerServlet">
						<c:param name="command" value="LOAD"></c:param>
						<c:param name="studentId" value="${student.id}"></c:param>
					</c:url>
					<c:url var="deleteLink" value="StudentControllerServlet">
						<c:param name="command" value="DELETE"></c:param>
						<c:param name="studentId" value="${student.id}"></c:param>
					</c:url>
				
					<tr>
						<td>${student.firstName}</td>
						<td>${student.lastName}</td>
						<td>${student.email}</td>
						<td>
						<a href="${updateLink}">Update</a>
						 | 
						<a href="${deleteLink}"
						onclick="if(!(confirm('Are you sure you want to delete this student?'))) return false">
						Delete</a>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>

</body>
</html>