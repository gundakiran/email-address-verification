<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<title>Email Address Verification | Submit Email Validation
	Request</title>
<link href="<c:url value="/resources/form.css" />" rel="stylesheet"
	type="text/css" />
</head>
<body>
	<div id="formsContent">
		<form:form id="form" method="post" modelAttribute="formBean"
			cssClass="cleanform">
			<div class="header">
				<h2>Form</h2>
				<c:if test="${not empty message}">
					<div id="message" class="success">${message}</div>
				</c:if>
				<s:bind path="*">
					<c:if test="${status.error}">
						<div id="message" class="error">Form has errors</div>
					</c:if>
				</s:bind>
			</div>
			<fieldset>
				<legend>Personal Info</legend>
				<form:label path="name">
		  			Name <form:errors path="name" cssClass="error" />
				</form:label>
				<form:input path="name" />

				<form:label path="age">
		  			Age <form:errors path="age" cssClass="error" />
				</form:label>
				<form:input path="age" />

				<form:label path="birthDate">
		  			Birth Date (in form yyyy-mm-dd) <form:errors path="birthDate"
						cssClass="error" />
				</form:label>
				<form:input path="birthDate" />
			</fieldset>
			<p>
				<button type="submit">Submit</button>
			</p>
		</form:form>
	</div>		
</body>
</html>