<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="menu">
	<a class= "${pageContext.request.requestURI eq '/DropCube/WEB-INF/view/index.jsp' ? 'active' : ''}" href="/DropCube/">Home</a>
	<c:if test="${sessionScope.user == null }">
		<a class= "${pageContext.request.requestURI eq '/DropCube/WEB-INF/view/reg-form.jsp' ? 'active' : ''}" href="/DropCube/register">Register</a>
	</c:if>	
	<a class= "${pageContext.request.requestURI eq '/DropCube/WEB-INF/view/about.jsp' ? 'active' : ''}" href="/DropCube/about">About</a>
</div>