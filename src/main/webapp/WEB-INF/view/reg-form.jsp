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
	<jsp:include page="include/no_javascript.jsp" />
	
	<div id="content" class="content register">
		<div class="centered">
			<c:if test="${err!=null}">
				<h3 class="error">${err}</h3>
			</c:if>

			<form action="/DropCube/register" method="POST" enctype="multipart/form-data">
				<table>
					<tr>
						<td>First name:</td>
						<td><input type="text" placeholder="First name..."
							name="user.firstName"></td>
					</tr>

					<tr>
						<td>Last name:</td>
						<td><input type="text" placeholder="Last name..."
							name="user.lastName"></td>
					</tr>

					<tr>
						<td>Email:</td>
						<td><input type="text" placeholder="Email..."
							name="user.email"></td>
					</tr>

					<tr>
						<td>Password:</td>
						<td><input type="password" placeholder="Password..."
							name="user.password"></td>
					</tr>
					<tr>
						<td>Upgrade to premium account!:</td>
						<td><input type="checkbox" name="user.premiumAccount"></td>
					</tr>

				</table>
				<input type="submit" value="Register">

			</form>
		
		</div>
	</div>


	<jsp:include page="include/footer.jsp" />

	<script>
		
	</script>
</body>
</html>
