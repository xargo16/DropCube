<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css"
	href='<c:url value="/resources/css/styles.css" />' />
</head>
<body>
	<jsp:include page="include/header.jsp" />
	<jsp:include page="include/menu.jsp" />

	<h1 class="black-centered-text">We are the best file hosting
		platform on the market!</h1>
	<h3 class="black-centered-text">We don't provide any kind of security, but do not worry... We hope for 
	the best!</h3>

	<div class="gray-box login-form">
		<form>
			<table>
				<tr>
					<td>Username:</td>
					<td><input type="text" placeholder="Username..." name="username"></td>
				</tr>
				<tr>
					<td>Password:</td>
					<td><input type="password" placeholder="Password..." name="password"></td>
				</tr>
				
			</table>
			<input class="login-button" type="submit" value="Login">

		</form>
	</div>



	<jsp:include page="include/footer.jsp" />
</body>
</html>
