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

	<div class="content">
		<div>
			<form action="/DropCube/register" method="POST">
				<table>
					<tr>
						<td>First name:</td>
						<td><input type="text" placeholder="First name..."
							name="firstName"></td>
					</tr>

					<tr>
						<td>Last name:</td>
						<td><input type="text" placeholder="Last name..."
							name="lastName"></td>
					</tr>

					<tr>
						<td>Email:</td>
						<td><input type="text" placeholder="Email..." name="email"></td>
					</tr>

					<tr>
						<td>Phone number(Optional):</td>
						<td><input type="text" placeholder="Phone number..."
							name="phoneNumber"></td>
					</tr>

					<tr>
						<td>Password:</td>
						<td><input type="password" placeholder="Password..."
							name="password"></td>
					</tr>
					<tr>
						<td>Upgrade to premium account!:</td>
						<td><input type="checkbox" name="premiumAccount"></td>
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
