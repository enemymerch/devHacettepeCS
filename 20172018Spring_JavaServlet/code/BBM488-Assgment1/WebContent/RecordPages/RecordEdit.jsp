<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.*" %>
    <%@ page import="controller.Record" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Edit Records!</title>
</head>
<body>
	<div style="margin-left: 35%; margin-right: 40%">
		<center><h2>This is the RecordEdit Page. You can add new records to the system from this page and edit the current record.</h2></center>
		<br style="border: thick;">
		<h3>You're logged in as "<% 
			if(session.getAttribute("userName")!=null){
				out.print(session.getAttribute("userName"));
			}else{
				out.print("null");
			}
		%>"</h3>
		<form action="../UserController/LogOut" method="post">
			<input type="submit" value="Logout">
		</form>
	</div>
	<br style="border: thick;">
	
	<div>
		<div style="margin-left: 35%; margin-right: 40%">
			<center><h2>Create a new Record!</h2></center>
			<center><h4>New record will be in sessionScope!(Not Shared)</h4></center>
			<form method="post" action="../RecordController/Add">
				ID<input name="id" value="ID">
				<br>
				Title:<input name="title" value="Title">
				<br>
				Location:<input name="location" value="Location">
				<br>
				Description:<input name="description" value="Description">
				<br>
				StartDate:<input name="startDate" value="StartDate">
				<br>
				EndDate:<input name="endDate" value="EndDate">
				<br>
				<input type="submit" value="create">
			</form>
			<h3>
				<%
					if(session.getAttribute("addMessage")!=null){
						out.print(session.getAttribute("addMessage"));
					}
				%>
			</h3>
		</div>
		<br style="border: thick;">
		<div style="margin-left: 35%; margin-right: 40%">
			<h2>Records!</h2>
			<table>
				<tr>
					<td>ID</td>
					<td>Title</td>
					<td>Location</td>
					<td>StartDate</td>
					<td>EndDate</td>
					<td>Condition</td>
				</tr>
				<%	
					ArrayList<Record> myAppScopeRecordList = new ArrayList<Record>();
					if(application.getAttribute("RecordList")!=null){
						myAppScopeRecordList = (ArrayList<Record>) application.getAttribute("RecordList");
					}else{
						// do nothing: 
					}
				
					ArrayList<Record> mySessionScopeRecordList = new ArrayList<Record>();
					if(session.getAttribute("RecordList")!=null){
						
						mySessionScopeRecordList = (ArrayList<Record>) session.getAttribute("RecordList");
					}else{
						// do nothing
					}
					
					for(Record rec:myAppScopeRecordList){
						out.print("<tr>");
						out.print("<td>"+ rec.getID()+"</td>");
						out.print("<td>"+ rec.getTitle()+"</td>");
						out.print("<td>"+ rec.getLocation()+"</td>");
						out.print("<td>"+ rec.getDescription()+"</td>");
						out.print("<td>"+ rec.getStartTime()+"</td>");
						out.print("<td>"+ rec.getEndTime()+"</td>");
						out.print("<td>"+"Shared" + "</td>");
						out.print("</tr>");
					}
					
					for(Record rec:mySessionScopeRecordList){ 
						out.print("<tr>");
						out.print("<td>"+ rec.getID()+"</td>");
						out.print("<td>"+ rec.getTitle()+"</td>");
						out.print("<td>"+ rec.getLocation()+"</td>");
						out.print("<td>"+ rec.getDescription()+"</td>");
						out.print("<td>"+ rec.getStartTime()+"</td>");
						out.print("<td>"+ rec.getEndTime()+"</td>");
						out.print("<td>"+"Notshared" + "</td>");
						out.print("</tr>");
					}
				%>
			</table>
		</div>
		<br>
		<div style="margin-left: 35%; margin-right: 40%">
			<h2>Edit Records!</h2>
			<p> To edit the a record, enter the id of the record and new info for that record!</p>
			<p> Empty form parts will not be changed!</p>
			<form method="post" action="../RecordController/Edit">
				ID*:<input name="ID">
				<br>
				New Title: <input name="title">
				<br>
				New Location: <input name="location">
				<br>
				New Description: <input name="description">
				<br>
				New Start Date: <input name="startDate">
				<br>
				New End Date: <input name="endDate">
				<br>
				Shared:<input name="shared" value="checked"  type="checkbox">			
				<br>
				<input type="submit" value="Edit">
				<h3>
				<%
					if(session.getAttribute("editMessage")!=null){
						out.print(session.getAttribute("editMessage"));
					}
				%>
				</h3>
			</form>
		</div>
		<div style="margin-left: 35%; margin-right: 40%">
			<h2>Delete Records!</h2>
			<p> To delete a record, enter the id of the record, then submit!</p>
			<form method="post" action="../RecordController/Delete">
				ID:<input name="ID">
				<br>
				<input type="submit" value="Delete">
				<br>
				<h3>
				<%
					if(session.getAttribute("deleteMessage")!=null){
						out.print(session.getAttribute("deleteMessage"));
					}
				%>
				</h3>
			</form>
		</div>
	</div>
</body>
</html>