<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<h1>Enter your credentials</h1>

<div class="form">

	<c:if test="${err!=null}">
		<h3 class="error">${err}</h3>
	</c:if>

	<form action="/DropCube/login" method="POST">
		<table>
			<tr>
				<td>Email:</td>
				<td><input type="text" placeholder="Email..." name="email"></td>
			</tr>
			<tr>
				<td>Password:</td>
				<td><input type="password" placeholder="Password..."
					name="password"></td>
			</tr>

		</table>
		<input type="submit" value="Login" id="login-button">

	</form>
</div>