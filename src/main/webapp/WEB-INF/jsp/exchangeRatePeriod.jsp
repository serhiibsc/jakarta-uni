<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<!DOCTYPE html>
<c:set var="title" value="Currency Dashboard"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body class="gradient-custom-for-page">
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<h1 class="text-center">Exchange Rates for ${selectedCurrency.name} Over Selected Period</h1>
<div class="d-flex flex-column justify-content-center align-items-center my-3">
    <form style="display: none" id="add-currency-form">
        <div class="form-group">
            <label for="date" class="mb-1 text-center">
                Date
            </label>
            <input
                    type="date"
                    id="date"
                    class="form-control mb-1"
                    style="width: 400px"
                    placeholder="Date"
                    name="date"
                    required
                    autofocus
            >
        </div>
        <div class="form-group">
            <label for="rate" class="mb-1 text-center">
                Rate
            </label>
            <input
                    type="text"
                    id="rate"
                    class="form-control mb-1"
                    style="width: 400px"
                    placeholder="Rate"
                    name="rate"
                    required
                    autofocus
            >
        </div>
    </form>
    <div id="buttons" style="display: flex; gap: 10px; margin-top: 10px">
        <button
                id="add-button"
                style="background-color: black; color: white; border-radius: 10px; border: none; padding: 12px"
                onclick="toggleForm()">
            Add currency
        </button>
        <button
                id="submit-button"
                style="background-color: black; color: white; border-radius: 10px; border: none; padding: 12px; display: none"
                onclick="handleSubmit()">
            Submit
        </button>
    </div>
    <div class="my-3">
        <table class="table-bordered">
            <thead>
            <tr>
                <th style="font-size: 22px; padding: 16px 32px; text-align: center">Date</th>
                <th style="font-size: 22px; padding: 16px 32px; text-align: center">Rate</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${exchangeRates}" var="exchangeRate">
                <tr>
                    <td style="font-size: 20px; padding: 16px 32px; text-align: center">${exchangeRate.date}</td>
                    <td style="font-size: 20px; padding: 16px 32px; text-align: center">${exchangeRate.rate}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<%@ include file="/WEB-INF/jspf/bootstrapScripts.jspf" %>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
<script>
    const toggleForm = () => {
        const addCurrencyForm = document.getElementById('add-currency-form');
        addCurrencyForm.style.display = addCurrencyForm.style.display === 'none' ? 'block' : 'none';

        const addButton = document.getElementById('add-button');
        addButton.innerText = addButton.innerText === 'Add currency' ? 'Cancel' : 'Add currency';

        const submitButton = document.getElementById('submit-button');
        submitButton.style.display = submitButton.style.display === 'none' ? 'block' : 'none';

        if (document.getElementById('date')) document.getElementById('date').value = '';
        if (document.getElementById('rate')) document.getElementById('rate').value = '';
        if (document.getElementById('sourceCurrencyAbbreviation')) {
            document.getElementById('sourceCurrencyAbbreviation').value = '';
        }
        if (document.getElementById('targetCurrencyAbbreviation')) {
            document.getElementById('targetCurrencyAbbreviation').value = '';
        }
    };

    const handleSubmit = async () => {
        const params = new URLSearchParams(document.location.search);
        await fetch('http://localhost:8080/jakarta-uni-1.0-SNAPSHOT/api/exchange-rate', {
            method: 'POST',
            body: JSON.stringify({
                date: document.getElementById('date').value,
                rate: document.getElementById('rate').value,
                sourceCurrencyAbbreviation: params.get('source'),
                targetCurrencyAbbreviation: params.get('target'),
            }),
            headers: new Headers({'content-type': 'application/json'}),
        });
        window.location.reload();
    };
</script>
</body>