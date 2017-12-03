<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src='<c:url value="/resources/js/register.js" />'></script>

<link rel="stylesheet" type="text/css"
	href='<c:url value="/resources/css/styles.css" />' />
</head>
<body>
	<jsp:include page="include/header.jsp" />
	<jsp:include page="include/menu.jsp" />
	<jsp:include page="include/no_javascript.jsp" />

	<div id="content" class="content register">
		<div class="centered">
			<c:if test="${err!=null}">
				<h3 class="error">${err}</h3>
			</c:if>

			<form action="/DropCube/register" method="POST">
				<table>
					<tr>
						<td>First name:</td>
						<td><input type="text" id="first-name" placeholder="First name..." 
							name="user.firstName" required></td>
					</tr>

					<tr>
						<td>Last name:</td>
						<td><input type="text" id="last-name" placeholder="Last name..."
							name="user.lastName" required></td>
					</tr>

					<tr>
						<td>Email:  <span id="email-invalid"></span></td>
						<td><input id="email" type="text" placeholder="Email..." 
							name="user.email" required></td>
					</tr>

					<tr>
						<td>Password: <span id="password-invalid"></span></td>
						<td><input id="password" type="password" placeholder="Password..." 
							name="user.password" autocomplete="new-password" required></td>
					</tr>
					<tr>
						<td>Upgrade to premium account!:</td>
						<td><input type="checkbox"
							name="user.premiumAccount"></td>
					</tr>

				</table>
				<input id="register-submit" type="submit" value="Register">

			</form>

		</div>
	</div>


	<jsp:include page="include/footer.jsp" />

	<script>
		
	</script>
</body>
</html>
