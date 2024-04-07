<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<!DOCTYPE html>
<c:set var="title" value="Main: Exchange Rates"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body class="gradient-custom-for-page">
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<h1 class="font-family text-center" >Exchange Rates for Today</h1>
<div class="d-flex justify-content-center my-3">
    <table class="table-bordered">
        <thead>
        <tr>
            <th style="font-size: 18px; padding: 16px 32px; text-align: center">Source Currency</th>
            <th style="font-size: 18px; padding: 16px 32px; text-align: center">Source Unit</th>
            <th style="font-size: 18px; padding: 16px 32px; text-align: center">Target Unit</th>
            <th style="font-size: 18px; padding: 16px 32px; text-align: center">Target Currency</th>
            <th style="font-size: 18px; padding: 16px 32px; text-align: center">Detailed Information</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${exchangeRates}" var="exchangeRate">
            <tr>
                <td>
                    <c:choose>
                        <c:when test="${exchangeRate.rate >= 1}">
                        <span style="font-size: 16px; padding: 16px 32px; text-align: center; color:green">
                            <c:out value="${exchangeRate.sourceCurrency.name}"/>
                        </span>
                        </c:when>
                        <c:otherwise>
                        <span style="font-size: 16px; padding: 16px 32px; text-align: center; color:red">
                            <c:out value="${exchangeRate.sourceCurrency.name}"/>
                        </span>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td style="font-size: 16px; padding: 16px 32px; text-align: center">1</td>
                <td style="font-size: 16px; padding: 16px 32px; text-align: center">
                    <c:out value="${exchangeRate.rate}"/>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${exchangeRate.rate < 1}">
                        <span style="font-size: 16px; padding: 16px 32px; text-align: center; color:green">
                            <c:out value="${exchangeRate.targetCurrency.name}"/>
                        </span>
                        </c:when>
                        <c:otherwise>
                        <span style="font-size: 16px; padding: 16px 32px; text-align: center; color:red">
                            <c:out value="${exchangeRate.targetCurrency.name}"/>
                        </span>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td style="font-size: 16px; padding: 16px 32px; text-align: center">
                    <a href="${pageContext.request.contextPath}/controller?command=EXCHANGE_RATE_PERIOD&source=${exchangeRate.sourceCurrency.abbreviation}&target=${exchangeRate.targetCurrency.abbreviation}&startDate=${startDate}&endDate=${endDate}" style="color: black">
                        Get info
                    </a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<%@ include file="/WEB-INF/jspf/bootstrapScripts.jspf" %>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
