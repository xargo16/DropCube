<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src='<c:url value="/resources/js/files.js" />'></script>
<link rel="stylesheet" type="text/css"
	href='<c:url value="/resources/css/styles.css" />' />
</head>
<body>
	<noscript>
		<style>
body {
	display: none;
}
</style>
	</noscript>

	<div class="columns">
		<div class="left-column">
			<div class="header">
				<h1>DropCube</h1>
			</div>

			<div class="list-of-files">
				<c:if test="${files == null}">
					<h3 class="no-files">You didn't upload any files yet</h3>
				</c:if>

				<c:forEach items="${files}" var="file">

					<div>
						<input type="checkbox" class="file-checkbox"
							id="check${file.fileId}" />
					</div>
					<div class="file" id="${file.fileId}">
						<input type="hidden" name="data-title" value="${file.name }" />
						<p>${file.name }</p>
					</div>
					<br>
				</c:forEach>

			</div>
		</div>

		<div class="middle-column">

			<div class="file-description-wrapper">
				<div class="file-title-container">
					<h1 id="file-title"></h1>
				</div>
				<div class="file-data-container"></div>
				<div class="file-meta-data-container"></div>
			</div>

			<h2 class="no-file-selected">Click any file on the left for
				details!</h2>

		</div>
		<div class="right-column">

			<input type="search" id="search-field" name="search"
				placeholder="Search...">


			<form action="/DropCube/user/addFile" method="POST"
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
				<h3 id="number-of-files-header">No files</h3>
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
