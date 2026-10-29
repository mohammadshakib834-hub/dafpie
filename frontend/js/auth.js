document.addEventListener('DOMContentLoaded', () => {
    // Check auth status on load
    updateNavbar();

    // Setup Login Form
    const loginForm = document.getElementById('loginForm');
    if (loginForm) {
        loginForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            const email = document.getElementById('email').value;
            const password = document.getElementById('password').value;

            try {
                const response = await apiCall('/auth/login', {
                    method: 'POST',
                    body: JSON.stringify({ email, password })
                });

                AuthState.setUser(response);
                showAlert('Login successful!', 'success');
                
                // Redirect based on role
                setTimeout(() => {
                    const role = response.role;
                    if (role === 'DONOR') window.location.href = 'donor-dashboard.html';
                    else if (role === 'NGO') window.location.href = 'ngo-dashboard.html';
                    else if (role === 'VOLUNTEER') window.location.href = 'volunteer-dashboard.html';
                    else if (role === 'ADMIN') window.location.href = 'admin-dashboard.html';
                }, 1000);
                
            } catch (error) {
                showAlert(error.message, 'danger');
            }
        });
    }

    // Setup Register Form
    const registerForm = document.getElementById('registerForm');
    if (registerForm) {
        // Toggle specific fields based on role selection
        const roleSelect = document.getElementById('role');
        const ngoFields = document.getElementById('ngoFields');
        const volunteerFields = document.getElementById('volunteerFields');

        if (roleSelect) {
            roleSelect.addEventListener('change', (e) => {
                const role = e.target.value;
                ngoFields.style.display = role === 'NGO' ? 'block' : 'none';
                volunteerFields.style.display = role === 'VOLUNTEER' ? 'block' : 'none';
            });
        }

        registerForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            
            const formData = {
                name: document.getElementById('name').value,
                email: document.getElementById('email').value,
                password: document.getElementById('password').value,
                phone: document.getElementById('phone').value,
                role: document.getElementById('role').value,
                city: document.getElementById('city').value,
                address: document.getElementById('address').value
            };

            if (formData.role === 'NGO') {
                formData.registrationNumber = document.getElementById('registrationNumber').value;
                formData.peopleServedCapacity = document.getElementById('peopleServedCapacity').value;
            } else if (formData.role === 'VOLUNTEER') {
                formData.vehicleType = document.getElementById('vehicleType').value;
                formData.vehicleNumber = document.getElementById('vehicleNumber').value;
            }

            try {
                const response = await apiCall('/auth/register', {
                    method: 'POST',
                    body: JSON.stringify(formData)
                });

                showAlert('Registration successful! Please login.', 'success');
                setTimeout(() => {
                    window.location.href = 'login.html';
                }, 1500);
            } catch (error) {
                showAlert(error.message, 'danger');
            }
        });
    }
});

function updateNavbar() {
    const user = AuthState.getUser();
    const guestNav = document.getElementById('guestNav');
    const userNav = document.getElementById('userNav');
    
    if (guestNav && userNav) {
        if (user) {
            guestNav.style.display = 'none';
            userNav.style.display = 'flex';
            
            const userNameDisplay = document.getElementById('userNameDisplay');
            if (userNameDisplay) userNameDisplay.textContent = user.name;
            
            const dashboardLink = document.getElementById('dashboardLink');
            if (dashboardLink) {
                const role = user.role;
                if (role === 'DONOR') dashboardLink.href = 'donor-dashboard.html';
                else if (role === 'NGO') dashboardLink.href = 'ngo-dashboard.html';
                else if (role === 'VOLUNTEER') dashboardLink.href = 'volunteer-dashboard.html';
                else if (role === 'ADMIN') dashboardLink.href = 'admin-dashboard.html';
            }
        } else {
            guestNav.style.display = 'flex';
            userNav.style.display = 'none';
        }
    }
}

async function logout() {
    try {
        await apiCall('/auth/logout', { method: 'POST' });
    } catch (e) {
        console.error("Logout error", e);
    } finally {
        AuthState.clear();
        window.location.href = 'index.html';
    }
}

// Global function mapping for onclick handlers
window.logout = logout;
