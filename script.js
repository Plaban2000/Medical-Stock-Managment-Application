document.addEventListener('DOMContentLoaded', () => {
    const registerForm = document.getElementById('registerForm');
    const responseDiv = document.getElementById('response');

    registerForm.addEventListener('submit', (e) => {
        e.preventDefault();
        const formData = new FormData(registerForm);
        fetch('/ShopOwnerServlet?action=register', {
            method: 'POST',
            body: new URLSearchParams(formData)
        })
        .then(response => response.text())
        .then(data => {
            responseDiv.innerText = data;
        })
        .catch(error => console.error('Error:', error));
    });
});
document.addEventListener('DOMContentLoaded', () => {
    const medicineForm = document.getElementById('medicineForm');
    const responseDiv = document.getElementById('response');

    medicineForm.addEventListener('submit', (e) => {
        e.preventDefault();
        const formData = new FormData(medicineForm);
        fetch('/MedicineServlet?action=addMedicine', {
            method: 'POST',
            body: new URLSearchParams(formData)
        })
        .then(response => response.text())
        .then(data => {
            responseDiv.innerText = data;
        })
        .catch(error => console.error('Error:', error));
    });
});
document.addEventListener('DOMContentLoaded', () => {
    const customerForm = document.getElementById('customerForm');
    const responseDiv = document.getElementById('response');

    customerForm.addEventListener('submit', (e) => {
        e.preventDefault();
        const formData = new FormData(customerForm);
        fetch('/CustomerServlet?action=addCustomer', {
            method: 'POST',
            body: new URLSearchParams(formData)
        })
        .then(response => response.text())
        .then(data => {
            responseDiv.innerText = data;
        })
        .catch(error => console.error('Error:', error));
    });
});
document.addEventListener('DOMContentLoaded', () => {
    const salesForm = document.getElementById('salesForm');
    const responseDiv = document.getElementById('response');

    salesForm.addEventListener('submit', (e) => {
        e.preventDefault();
        const formData = new FormData(salesForm);
        fetch('/SalesServlet?action=addSale', {
            method: 'POST',
            body: new URLSearchParams(formData)
        })
        .then(response => response.text())
        .then(data => {
            responseDiv.innerText = data;
        })
        .catch(error => console.error('Error:', error));
    });
});

document.addEventListener('DOMContentLoaded', () => {
    const subscriptionForm = document.getElementById('subscriptionForm');
    const responseDiv = document.getElementById('response');

    subscriptionForm.addEventListener('submit', (e) => {
        e.preventDefault();
        const formData = new FormData(subscriptionForm);
        fetch('/SubscriptionServlet?action=addSubscription', {
            method: 'POST',
            body: new URLSearchParams(formData)
        })
        .then(response => response.text())
        .then(data => {
            responseDiv.innerText = data;
        })
        .catch(error => console.error('Error:', error));
    });
});

document.addEventListener('DOMContentLoaded', () => {
    const adminForm = document.getElementById('adminForm');
    const responseDiv = document.getElementById('response');

    adminForm.addEventListener('submit', (e) => {
        e.preventDefault();
        const formData = new FormData(adminForm);
        fetch('/AdminServlet?action=addAdmin', {
            method: 'POST',
            body: new URLSearchParams(formData)
        })
        .then(response => response.text())
        .then(data => {
            responseDiv.innerText = data;
        })
        .catch(error => console.error('Error:', error));
    });
});
document.addEventListener('DOMContentLoaded', () => {
    const paymentForm = document.getElementById('paymentForm');
    const responseDiv = document.getElementById('response');

    paymentForm.addEventListener('submit', (e) => {
        e.preventDefault();
        const formData = new FormData(paymentForm);
        fetch('/PaymentServlet?action=makePayment', {
            method: 'POST',
            body: new URLSearchParams(formData)
        })
        .then(response => response.text())
        .then(data => {
            responseDiv.innerText = data;
        })
        .catch(error => console.error('Error:', error));
    });
});
