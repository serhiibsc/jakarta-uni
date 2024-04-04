<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<!DOCTYPE html>
<c:set var="title" value="Currencies"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body class="gradient-custom-for-page">
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<h1 class="text-center">Currencies</h1>
<div class="d-flex justify-content-center my-3">
    <table class="table-bordered">
        <thead>
        <tr>
            <th style="font-size: 22px; padding: 16px 32px; text-align: center">Name</th>
            <th style="font-size: 22px; padding: 16px 32px; text-align: center">Abbreviation</th>
            <th style="font-size: 22px; padding: 16px 32px; text-align: center">Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${currencies}" var="currency">
            <tr>
                <td style="font-size: 20px; padding: 16px 32px; text-align: center">
                    <a href="${pageContext.request.contextPath}/controller?command=currency_data&currencyId=${currency.id}" style="color: black">
                            ${currency.name}
                    </a>
                </td>
                <td style="font-size: 20px; padding: 16px 32px; text-align: center">${currency.abbreviation}</td>
                <td style="font-size: 20px; padding: 16px 32px; text-align: center">
                    <img
                            src="${pageContext.request.contextPath}/assets/pencil.png"
                            alt="edit"
                            width="32"
                            height="32"
                            style="cursor: pointer"
                            onclick="toggleEditForm('${currency.id}', '${currency.name}', '${currency.abbreviation}')"
                    />
                    <span style="font-size: 32px; margin: 10px">|</span>
                    <img
                            src="${pageContext.request.contextPath}/assets/trash.png"
                            alt="delete"
                            width="32"
                            height="32"
                            style="cursor: pointer"
                            onclick="handleDeleteCurrency('${currency.id}')"
                    />
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div style="display: flex; flex-direction: column; justify-content: center; align-items: center; margin-bottom: 20px">
    <form style="display: none" id="add-currency-form">
        <div class="form-group">
                <label for="name" class="mb-1 text-center">Name</label>
                <input type="text" id="name" class="form-control mb-1" style="width: 400px" placeholder="Name" name="name" required autofocus>
        </div>
        <div class="form-group mt-3">
                <label for="abbreviation" class="mb-1 text-center">Abbreviation</label>
                <input type="text" id="abbreviation" class="form-control mb-1" style="width: 400px" placeholder="Abbreviation" name="abbreviation" required autofocus>
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
</div>
<%@ include file="/WEB-INF/jspf/bootstrapScripts.jspf" %>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
<script>
    let currencyId;

    const toggleForm = () => {
        const addCurrencyForm = document.getElementById('add-currency-form');
        addCurrencyForm.style.display = addCurrencyForm.style.display === 'none' ? 'block' : 'none';

        const addButton = document.getElementById('add-button');
        addButton.innerText = addButton.innerText === 'Add currency' ? 'Cancel' : 'Add currency';

        const submitButton = document.getElementById('submit-button');
        submitButton.style.display = submitButton.style.display === 'none' ? 'block' : 'none';

        document.getElementById('name').value = '';
        document.getElementById('abbreviation').value = '';
    };

    const toggleEditForm = (id, name, abbreviation) => {
        currencyId = id;

        const addCurrencyForm = document.getElementById('add-currency-form');
        addCurrencyForm.style.display = 'block';

        const addButton = document.getElementById('add-button');
        addButton.innerText = addButton.innerText = 'Cancel';

        const submitButton = document.getElementById('submit-button');
        submitButton.style.display = 'block';

        document.getElementById('name').value = name;
        document.getElementById('abbreviation').value = abbreviation;
    };

    const handleSubmit = async () => {
        console.log({
            name: document.getElementById('name').value,
            abbreviation: document.getElementById('abbreviation').value,
            currencyId
        });
        const url = currencyId ?
            'http://localhost:8080/jakarta-uni-1.0-SNAPSHOT/api/currency/' + currencyId :
            'http://localhost:8080/jakarta-uni-1.0-SNAPSHOT/api/currency';
        await fetch(url, {
            method: currencyId ? 'PUT' : 'POST',
            body: JSON.stringify({
                name: document.getElementById('name').value,
                abbreviation: document.getElementById('abbreviation').value,
            }),
            headers: new Headers({ 'content-type': 'application/json' }),
        });
        window.location.reload();
    };

    const handleDeleteCurrency = async id => {
        await fetch('http://localhost:8080/jakarta-uni-1.0-SNAPSHOT/api/currency/' + id, {
            method: 'DELETE',
            headers: new Headers({ 'content-type': 'application/json' }),
        });
        window.location.reload();
    };
</script>
</body>
