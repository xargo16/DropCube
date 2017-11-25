<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:if test="${files == null}">
	<h3 class="no-files">You didn't upload any files yet</h3>
</c:if>

<c:forEach items="${files}" var="file">
	</c:forEach>