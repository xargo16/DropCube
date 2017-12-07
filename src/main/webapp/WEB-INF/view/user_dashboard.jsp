<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src='<c:url value="/resources/js/files.js" />'></script>
<link rel="stylesheet" type="text/css"
	href='<c:url value="/resources/css/user_dashboard.css" />' />
</head>
<body>
	<noscript>
		<style>
body {
	display: none;
}
</style>
	</noscript>

	<div class="user-dashboard-container">
		<div class="left-column-container">
			<div class="header">
				<h1>DropCube</h1>
			</div>
			<div class="all-files-container">
				<c:if test="${files == null}">
					<h3>You didn't upload any files yet</h3>
				</c:if>

				<c:forEach items="${files}" var="file">

					<div class="file-checkbox-container">
						<input type="checkbox" class="file-checkbox"
							id="check${file.fileId}" />
					</div>

					<div class="file-container" id="${file.fileId}">
						<input type="hidden" name="data-title" value="${file.name }" /> <input
							type="hidden" name="data-upload-date"
							value="${file.dateOfUpload }" /> <input type="hidden"
							name="data-content-type" value="${file.contentType }" />
						<p>${file.name }</p>
					</div>
					<br>
				</c:forEach>
			</div>
		</div>

		<div class="middle-column-container">

			<div class="file-description-container">
				<div class="file-title-container">
					<h2 id="file-title"></h2>
				</div>

				<div class="file-content-container"></div>
				<div class="file-meta-data-container">
					<h4 id="file-upload-date"></h4>
				</div>
			</div>

			<h2 class="no-file-selected">Click any file on the left for
				details!</h2>

		</div>
		<div class="right-column-container">

			<input type="search" id="search-field" name="search"
				placeholder="Search...">


			<!-------- File upload form ---------->

			<form action="/DropCube/user/upload" method="POST"
				enctype="multipart/form-data">
				<c:if test="${param.err == 'upload'}">
					<h3 class="error" id="upload-error">Error occured!</h3>
					<script>
						$('#upload-error').delay(5000).fadeOut('slow');
					</script>
				</c:if>
				<c:if test="${param.act == 'success' }">
					<h3 class="success" id="upload-complete">Upload complete!</h3>

					<script>
						$('#upload-complete').delay(5000).fadeOut('slow');
					</script>

				</c:if>

				<div class="upload-btn-wrapper">
					<span class="upload-span">Upload files</span> <input type="file"
						id="id-upload-btn" name="file" multiple />
				</div>
			</form>

			<c:if test="${param.delete != null}">
				<h3 class="success" id="delete-success">
					<c:out value='${param.delete }' />
				</h3>
				<script>
					$('#delete-success').delay(5000).fadeOut('slow');
				</script>
			</c:if>
			<c:if test="${param.err == 'download'}">
				<h3 class="error" id="download-error">Error occured!</h3>
				<script>
					$('#download-error').delay(5000).fadeOut('slow');
				</script>
			</c:if>

			<div class="hidden" id="download-delete-btn-container">
				<div class="number-of-files-container">
					<h3 id="number-of-files-header">No files</h3>
				</div>
				<input type="button" id="download-btn" value="Download" /> <br>
				<input type="button" id="delete-btn" value="Delete" />
			</div>

			<form action="/DropCube/logout" method="POST">
				<input type="submit" value="Logout" />
			</form>
		</div>
	</div>
</body>
</html>
