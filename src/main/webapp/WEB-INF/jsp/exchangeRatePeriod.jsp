<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<!DOCTYPE html>
<c:set var="title" value="Currency Dashboard"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body class="gradient-custom-for-page">
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<h1 class="text-center">Exchange Rates (${param.source} &#8594; ${param.target}) for the Selected Period (${param.startDate} &mdash; ${param.endDate})</h1>
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
            Add exchange rate
        </button>
        <button
                id="submit-button"
                style="background-color: black; color: white; border-radius: 10px; border: none; padding: 12px; display: none"
                onclick="handleSubmit()">
            Submit
        </button>
    </div>

    <form style="display: none" id="pick-dates-form">
        <div class="form-group">
            <label for="start-date" class="mb-1 text-center">
                Start date
            </label>
            <input
                    type="date"
                    id="start-date"
                    class="form-control mb-1"
                    style="width: 400px"
                    placeholder="Start date"
                    name="start-date"
                    required
                    autofocus
            >
        </div>
        <div class="form-group">
            <label for="end-date" class="mb-1 text-center">
                End date
            </label>
            <input
                    type="date"
                    id="end-date"
                    class="form-control mb-1"
                    style="width: 400px"
                    placeholder="End date"
                    name="end-date"
                    required
                    autofocus
            >
        </div>
    </form>
    <div id="date-buttons" style="display: flex; gap: 10px; margin-top: 10px">
        <button
                id="change-dates-button"
                style="background-color: black; color: white; border-radius: 10px; border: none; padding: 12px"
                onclick="toggleDateForm()">
            Change period
        </button>
        <button
                id="date-submit-button"
                style="background-color: black; color: white; border-radius: 10px; border: none; padding: 12px; display: none; cursor: pointer"
                onclick="handleDateFormSubmit()">
            Submit
        </button>
    </div>

    <div class="my-3">
        <canvas id="myChart" width="600" height="600"></canvas>
    </div>
</div>

<%@ include file="/WEB-INF/jspf/bootstrapScripts.jspf" %>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
<%
    Object exchangeRatesData = request.getAttribute("exchangeRates");
    String exchangeRates = exchangeRatesData != null ? exchangeRatesData.toString() : "[]";
%>
<script>
    const toggleForm = () => {
        const addCurrencyForm = document.getElementById('add-currency-form');
        addCurrencyForm.style.display = addCurrencyForm.style.display === 'none' ? 'block' : 'none';

        const addButton = document.getElementById('add-button');
        addButton.innerText = addButton.innerText === 'Add exchange rate' ? 'Cancel' : 'Add exchange rate';

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

    const toggleDateForm = () => {
        const pickDatesForm = document.getElementById('pick-dates-form');
        pickDatesForm.style.display = pickDatesForm.style.display === 'none' ? 'block' : 'none';

        const changeDatesButton = document.getElementById('change-dates-button');
        changeDatesButton.innerText = changeDatesButton.innerText === 'Change period' ? 'Cancel' : 'Change period';

        const submitButton = document.getElementById('date-submit-button');
        submitButton.style.display = submitButton.style.display === 'none' ? 'block' : 'none';

        if (document.getElementById('start-date')) document.getElementById('start-date').value = '';
        if (document.getElementById('end-date')) document.getElementById('end-date').value = '';
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

    const handleDateFormSubmit = () => {
        const params = new URLSearchParams(document.location.search);
        const source = params.get('source');
        const target = params.get('target');
        const startDate = document.getElementById('start-date').value;
        const endDate = document.getElementById('end-date').value;

        document.getElementById('start-date').value = '';
        document.getElementById('end-date').value = '';
        document.getElementById('add-currency-form').style.display = 'none';
        document.getElementById('pick-dates-form').style.display = 'none';

        window.location.href =
            '/jakarta-uni-1.0-SNAPSHOT/controller?command=EXCHANGE_RATE_PERIOD&source=' +
            source +
            '&target=' +
            target +
            '&startDate=' +
            startDate +
            '&endDate=' +
            endDate;
    };

    const ctx = document.getElementById('myChart').getContext('2d');
    let exchangeRates =  '<%= exchangeRates %>';

    const regexPattern = /rate=([\d.]+).*?date=(\d{4}-\d{2}-\d{2})/g;
    const dataArray = [];
    let match;

    while (match = regexPattern.exec(exchangeRates)) {
        const object = {
            rate: match[1],
            date: match[2]
        };
        dataArray.push(object);
    }

    dataArray.sort((a, b) => new Date(a.date) - new Date(b.date));

    const myChart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: dataArray.map(exchangeRate => exchangeRate.date),
            datasets: [{
                label: '- Exchange rate',
                data: dataArray.map(exchangeRate => exchangeRate.rate),
                backgroundColor: 'rgba(255, 99, 132, 0)',
                borderColor: 'rgba(255, 99, 132, 1)',
                borderWidth: 2
            }]
        },
        option: {
            legend: {
                labels: {
                    boxWidth: 0
                }
            }
        }
    });
</script>
</body>