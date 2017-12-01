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
		<div class="centered">
			<br>
			<h1>Please turn on JavaScript to use our website!</h1>
			<img width="60%" height="auto" src='<c:url value="/resources/img/cookie.png" />' alt="Enable cookies" />
		</div>
	</div>


	<jsp:include page="include/footer.jsp" />
</body>
</html>
