<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
	<title>Email Address Verification | Submit Email Validation Request</title>
	<link href="<c:url value="/resources/form.css" />" rel="stylesheet" type="text/css" />
</head>
<body>
	<div id="formsContent">
	
		<form:form id="form" method="post" modelAttribute="emailRequest" cssClass="cleanform">
			<div class="header">
				<h2>Form</h2>
				<span class="success">${success}</span>
			</div>
			<fieldset>
				<legend>Email Validation Request</legend>
				<form:label path="domain">
		  			Domain <form:errors path="domain" cssClass="error" />
				</form:label>
				<form:input path="domain" />

				<form:label path="firstName">
		  			First Name <form:errors path="firstName" cssClass="error" />
				</form:label>
				<form:input path="firstName" />

				<form:label path="lastName">
		  			Last Name <form:errors path="lastName" cssClass="error" />
				</form:label>
				<form:input path="lastName" />

			</fieldset>
			<p>
				<button type="submit">Submit</button>
				<a href="<c:url value='cancel'/>">
					<button type="button" onclick="">Cancel</button>
				</a>	
			</p>
		</form:form>
	</div>		
</body>
</html>