<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<!DOCTYPE html>
<c:set var="title" value="Currency Dashboard"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body class="gradient-custom-for-page">
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<h1 class="text-center">Exchange Rates for ${selectedCurrency.name} Over Selected Period</h1>
<table border="1">
    <thead>
    <tr>
        <th>Date</th>
        <th>Rate</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${exchangeRates}" var="exchangeRate">
        <tr>
            <td>${exchangeRate.date}</td>
            <td>${exchangeRate.rate}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<%@ include file="/WEB-INF/jspf/bootstrapScripts.jspf" %>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>