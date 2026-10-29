// Handle donation operations

async function loadDonorDashboard() {
    try {
        const donations = await apiCall('/donations/my');
        
        let pending = 0, accepted = 0, completed = 0;
        const tbody = document.getElementById('donationsTableBody');
        tbody.innerHTML = '';
        
        if (donations.length === 0) {
            tbody.innerHTML = '<tr><td colspan="5" class="text-center text-muted py-4">No donations found. Donate some food!</td></tr>';
        } else {
            donations.forEach(d => {
                if (d.status === 'AVAILABLE') pending++;
                else if (d.status === 'COMPLETED' || d.status === 'DELIVERED') completed++;
                else accepted++;
                
                const statusBadge = getStatusBadge(d.status);
                
                tbody.innerHTML += `
                    <tr>
                        <td>
                            <strong>${d.foodName}</strong><br>
                            <small class="text-muted">${d.foodType} • ${d.category}</small>
                        </td>
                        <td>${d.quantity} ${d.unit}</td>
                        <td>${formatDate(d.expiryTime)}</td>
                        <td>${statusBadge}</td>
                        <td>
                            <a href="food-details.html?id=${d.id}" class="btn btn-sm btn-outline-custom">View</a>
                        </td>
                    </tr>
                `;
            });
        }
        
        document.getElementById('totalDonations').textContent = donations.length;
        document.getElementById('pendingDonations').textContent = pending;
        document.getElementById('acceptedDonations').textContent = accepted;
        document.getElementById('completedDonations').textContent = completed;
        
    } catch (e) {
        showAlert('Failed to load dashboard data', 'danger');
    }
}

function getStatusBadge(status) {
    const map = {
        'AVAILABLE': '<span class="badge bg-primary">Available</span>',
        'ACCEPTED': '<span class="badge bg-info">Accepted</span>',
        'PICKED_UP': '<span class="badge bg-warning text-dark">Picked Up</span>',
        'ON_THE_WAY': '<span class="badge bg-warning text-dark">On The Way</span>',
        'DELIVERED': '<span class="badge bg-success">Delivered</span>',
        'COMPLETED': '<span class="badge bg-success">Completed</span>',
        'EXPIRED': '<span class="badge bg-danger">Expired</span>',
    };
    return map[status] || `<span class="badge bg-secondary">${status}</span>`;
}

function getUrgencyBadge(urgencyLevel) {
    const map = {
        'HIGH': '<span class="badge badge-urgency-high"><i class="bi bi-clock-history"></i> High Urgency</span>',
        'MEDIUM': '<span class="badge badge-urgency-medium"><i class="bi bi-clock"></i> Medium Urgency</span>',
        'LOW': '<span class="badge badge-urgency-low">Low Urgency</span>',
    };
    return map[urgencyLevel] || '';
}

// Donate Form Submit
const donationForm = document.getElementById('donationForm');
if (donationForm) {
    // Pre-fill user data if available
    const user = AuthState.getUser();
    if (user) {
        document.getElementById('city').value = user.city || '';
        document.getElementById('contactNumber').value = user.phone || '';
    }

    // Set min time to now
    const now = new Date();
    now.setMinutes(now.getMinutes() - now.getTimezoneOffset());
    const minTimeStr = now.toISOString().slice(0,16);
    document.getElementById('preparationTime').max = minTimeStr;
    document.getElementById('expiryTime').min = minTimeStr;

    donationForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        
        const payload = {
            foodName: document.getElementById('foodName').value,
            category: document.getElementById('category').value,
            foodType: document.getElementById('foodType').value,
            quantity: parseInt(document.getElementById('quantity').value),
            unit: document.getElementById('unit').value,
            preparationTime: document.getElementById('preparationTime').value,
            expiryTime: document.getElementById('expiryTime').value,
            pickupAddress: document.getElementById('pickupAddress').value,
            city: document.getElementById('city').value,
            contactNumber: document.getElementById('contactNumber').value,
            description: document.getElementById('description').value,
        };

        try {
            await apiCall('/donations', {
                method: 'POST',
                body: JSON.stringify(payload)
            });
            showAlert('Donation submitted successfully!', 'success');
            setTimeout(() => {
                window.location.href = 'donor-dashboard.html';
            }, 1500);
        } catch (error) {
            showAlert(error.message, 'danger');
        }
    });
}
