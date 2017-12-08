<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css"
	href='<c:url value="/resources/css/landing_page.css" />' />
</head>
<body>
	<jsp:include page="include/header.jsp" />
	<jsp:include page="include/menu.jsp" />
	<jsp:include page="include/no_javascript.jsp" />
	
	<div id="content" class="content">
			<h1>DropCube is the #1 hosting platform in the whole universe!</h1>
			<h2>We promise to keep your files safe. Nothing to worry about.</h2>
			<br>
			<img width="40%" height="auto" src='<c:url value="/resources/img/t.png" />' alt="Troll face" />
	</div>

	<jsp:include page="include/footer.jsp" />
</body>
</html>
