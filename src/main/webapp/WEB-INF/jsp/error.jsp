<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<!DOCTYPE html>
<c:set var="title" value="Error"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body class="gradient-custom-for-page">
<h1 class="text-center">Error</h1>
<c:if test="${not empty errorMessage}">
    <p class="text-center fs-2 fw-light ">
        <c:out value="${errorMessage}"/>
    </p>
</c:if>
<%@ include file="/WEB-INF/jspf/bootstrapScripts.jspf" %>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>