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
	

	<div id="content" class="content">
		<div class="centered">
			<c:if test="${param.act eq 'reg'}">
				<h1 class="success" id="register">User Registered Successfully.
					Please login</h1>
			</c:if>
			<c:if test="${param.act eq 'lout'}">
				<h1 class="success" id="logout">We hope to see you soon!</h1>
			</c:if>
			<jsp:include page="include/login.jsp" />
		</div>
	</div>

	<jsp:include page="include/footer.jsp" />
	<script src='<c:url value="/resources/js/cookies.js" />'></script>
</body>
</html>
