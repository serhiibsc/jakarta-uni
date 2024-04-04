<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<!DOCTYPE html>
<c:set var="title" value="Main: Exchange Rates"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body class="gradient-custom-for-page">
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<h1 class="font-family text-center" >Exchange Rates for Today</h1>
<table class="table-bordered">
    <thead>
    <tr>
        <th>Source Currency</th>
        <th>Source Unit</th>
        <th>Target Unit</th>
        <th>Target Currency</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${exchangeRates}" var="exchangeRate">
        <tr>
            <td>
                <c:choose>
                    <c:when test="${exchangeRate.rate >= 1}">
                        <span style="color:green">
                            <c:out value="${exchangeRate.sourceCurrency.name}"/>
                        </span>
                    </c:when>
                    <c:otherwise>
                        <span style="color:red">
                            <c:out value="${exchangeRate.sourceCurrency.name}"/>
                        </span>
                    </c:otherwise>
                </c:choose>
            </td>
            <td>1</td>
            <td>
                <c:out value="${exchangeRate.rate}"/>
            </td>
            <td>
                <c:choose>
                    <c:when test="${exchangeRate.rate < 1}">
                        <span style="color:green">
                            <c:out value="${exchangeRate.targetCurrency.name}"/>
                        </span>
                    </c:when>
                    <c:otherwise>
                        <span style="color:red">
                            <c:out value="${exchangeRate.targetCurrency.name}"/>
                        </span>
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<%@ include file="/WEB-INF/jspf/bootstrapScripts.jspf" %>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
