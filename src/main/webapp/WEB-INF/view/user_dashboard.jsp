<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css"
	href='<c:url value="/resources/css/styles.css" />' />
</head>
<body>

	<div class="columns">

		<div class="header">
			<h1>DropCube</h1>
		</div>

		<div class="left-column">
			<jsp:include page="include/files.jsp" />
		</div>

		<div class="middle-column">
			<c:if test="${files == null}">
				<h2 class="no-files">Click any file on the left for details!</h2>
			</c:if>
		</div>
		<div class="right-column">

			<input type="search" name="search" placeholder="Search...">


			<form action="/DropCube/user/addFile" method="POST"
				enctype="multipart/form-data">
				<c:if test="${err != null}">
					<h3 class="error">${err}</h3>
				</c:if>
				<div class="upload-btn-wrapper">
					<span class="upload-span">Upload files</span> 
					<input type="file" onchange="this.form.submit()"
						name="file" />
				</div>
			</form>
			
			<form action="/DropCube/logout" method="POST">
				<input type="submit" value="Logout"/>
			</form>
		</div>
	</div>
</body>
</html>
