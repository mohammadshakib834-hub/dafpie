const API_BASE_URL = 'http://localhost:8080/api';

// Simple state management for auth
const AuthState = {
    getToken: () => localStorage.getItem('token'), // Using simple cookie/session for now, but keeping token logic ready
    getUser: () => JSON.parse(localStorage.getItem('user') || 'null'),
    setUser: (user) => localStorage.setItem('user', JSON.stringify(user)),
    clear: () => {
        localStorage.removeItem('user');
        localStorage.removeItem('token');
    },
    isAuthenticated: () => !!localStorage.getItem('user')
};

// Generic API fetch wrapper
async function apiCall(endpoint, options = {}) {
    const defaultHeaders = {
        'Content-Type': 'application/json'
    };
    
    const config = {
        ...options,
        headers: {
            ...defaultHeaders,
            ...options.headers
        }
    };
    
    // Spring Security defaults to JSESSIONID cookie, so include credentials
    config.credentials = 'include';

    try {
        const response = await fetch(`${API_BASE_URL}${endpoint}`, config);
        
        if (!response.ok) {
            let errorMessage = 'Something went wrong. Please try again.';
            try {
                const errorData = await response.json();
                errorMessage = errorData.message || errorMessage;
            } catch (e) {
                // Ignore JSON parse error if response is not JSON
            }
            throw new Error(errorMessage);
        }
        
        return await response.json();
    } catch (error) {
        console.error('API Error:', error);
        throw error;
    }
}

// Show alert message
function showAlert(message, type = 'success') {
    const alertDiv = document.createElement('div');
    alertDiv.className = `alert alert-${type} alert-dismissible fade show position-fixed top-0 end-0 m-3 z-3`;
    alertDiv.style.minWidth = '300px';
    alertDiv.innerHTML = `
        ${message}
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    `;
    document.body.appendChild(alertDiv);
    
    setTimeout(() => {
        alertDiv.classList.remove('show');
        setTimeout(() => alertDiv.remove(), 150);
    }, 5000);
}

// Format date
function formatDate(dateString) {
    if (!dateString) return 'N/A';
    const date = new Date(dateString);
    return date.toLocaleString();
}
