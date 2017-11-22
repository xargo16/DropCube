<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<h1>We are the best file hosting platform on the market!</h1>

<div>

	<c:if test="${err!=null}">
		<h3 class="error">${err}</h3>
	</c:if>
	
	<c:if test="${param.act eq 'reg'}">
        <h3 class="success">User Registered Successfully. Please login</h3>
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
		<input type="submit" value="Login">

	</form>
</div>