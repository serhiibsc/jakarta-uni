<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<!DOCTYPE html>
<c:set var="title" value="Login"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body class="gradient-custom-for-page">

<%@ include file="/WEB-INF/jspf/header.jspf" %>
<div class="text-center mt-5">
    <div class="container message" style="max-width: 480px;margin: auto">
        <c:if test="${not empty sessionScope.login_error_message}">
            <div class="alert alert-danger alert-dismissible fade show mt-2 d-flex justify-content-between"
                 role="alert">
                <div class="left">
                    <c:out value="${sessionScope.login_error_message}"/>
                </div>
                <div class="right">
            <span class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </span>
                </div>
            </div>
        </c:if>
    </div>
    <form action="${pageContext.request.contextPath}/controller" method="post" class="mx-auto"
          style="max-width: 480px;">
        <input type="hidden" name="command" value="login">
        <h1 class="h3 mb-3 font-weight-normal">Sign in</h1>

        <div class="form-group">
            <label for="username" class="mb-1">Username</label>
            <input type="text" id="username" class="form-control mb-1" placeholder="..." name="username" required autofocus>
        </div>

        <div class="form-group">
            <label for="password" class="mb-1">Password</label>
            <input type="password" id="password" class="form-control mb-1" placeholder="..." name="password" required
                   autocomplete="off">
        </div>

        <div class="mt-3 mb-3 d-grid">
            <button class="btn btn-sm btn-primary btn-block" type="submit">Sign in</button>
        </div>
    </form>
</div>
<%@ include file="/WEB-INF/jspf/bootstrapScripts.jspf" %>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
