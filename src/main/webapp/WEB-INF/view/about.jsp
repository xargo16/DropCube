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
			<h1>DropCube is the #1 hosting platform in the whole universe!</h1>
			<h2>We promise to keep your file safe. Nothing to worry about.</h2>
			<img src='<c:url value="/resources/img/t.png" />' alt="Troll face" />
		</div>
	</div>


	<jsp:include page="include/footer.jsp" />
</body>
</html>
