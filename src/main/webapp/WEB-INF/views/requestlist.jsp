<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<html>
<head>
	<title>Email Address Verification | Email Request List</title>
	<link href="<c:url value="/resources/form.css" />" rel="stylesheet" type="text/css" />
</head>
<body>

<div>
	<h2>Submitted Email Validation Requests</h2>
	
	<form:form id="requestList" action="new" method="get">
		<fieldset>
			<button type="submit">Create new Request</button>
		</fieldset>
		<table class="tablesorter" border="1">
			<thead>
			<tr>
				<th>Domain</th>
				<th>First Name</th>
				<th>Last Name</th>
			</tr>
			</thead>		
			<c:forEach items="${emailRequestList}" var="emailRequest">
			<tr>
				<td>${emailRequest.domain}</td>
				<td>${emailRequest.firstName}</td>
				<td>${emailRequest.lastName}</td>
			</tr>	
			</c:forEach>
		</table>
	</form:form>	
</div>


</body>
</html>
