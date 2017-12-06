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

	<div class="content">
		<div class="centered">
			<br>
			<h1>Sorry, but it seems that something went wrong.</h1>
			<br>
			<img width="40%" height="auto" src='<c:url value="/resources/img/error.png" />' alt="error" />
		</div>
	</div>


	<jsp:include page="include/footer.jsp" />
</body>
</html>
