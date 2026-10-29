let currentDonationId = null;
let volunteerModal = null;

async function loadNgoDashboard() {
    try {
        const donations = await apiCall('/ngos/donations/accepted');
        
        const tbody = document.getElementById('ngoDonationsTableBody');
        tbody.innerHTML = '';
        
        if (donations.length === 0) {
            tbody.innerHTML = '<tr><td colspan="5" class="text-center text-muted py-4">You have not accepted any donations yet. <a href="food-list.html">Find Food</a></td></tr>';
        } else {
            donations.forEach(d => {
                // If it's accepted, show "Request Volunteer" button
                let actionBtn = '';
                if (d.status === 'ACCEPTED') {
                    actionBtn = `<button onclick="openVolunteerModal(${d.id})" class="btn btn-sm btn-outline-custom">Request Volunteer</button>`;
                } else {
                    actionBtn = `<a href="food-details.html?id=${d.id}" class="btn btn-sm btn-light border">Details</a>`;
                }

                const statusBadge = getStatusBadge(d.status); // from donation.js if loaded globally, but we'll inline a simple one for safety

                tbody.innerHTML += `
                    <tr>
                        <td>
                            <strong>${d.foodName}</strong><br>
                            <small class="text-muted">Donor: ${d.donor.name}</small>
                        </td>
                        <td>${d.quantity} ${d.unit}</td>
                        <td>${d.pickupAddress}, ${d.city}</td>
                        <td>${statusBadge || `<span class="badge bg-secondary">${d.status}</span>`}</td>
                        <td>${actionBtn}</td>
                    </tr>
                `;
            });
        }
    } catch (e) {
        showAlert('Failed to load dashboard data', 'danger');
    }
}

function openVolunteerModal(id) {
    currentDonationId = id;
    if (!volunteerModal) {
        volunteerModal = new bootstrap.Modal(document.getElementById('volunteerModal'));
    }
    document.getElementById('volunteerInstructions').value = '';
    volunteerModal.show();
}

document.addEventListener('DOMContentLoaded', () => {
    const confirmBtn = document.getElementById('confirmVolunteerBtn');
    if (confirmBtn) {
        confirmBtn.addEventListener('click', async () => {
            const instructions = document.getElementById('volunteerInstructions').value;
            try {
                await apiCall(`/ngos/donations/${currentDonationId}/volunteer`, {
                    method: 'POST',
                    body: JSON.stringify({ instructions })
                });
                
                volunteerModal.hide();
                showAlert('Volunteer requested successfully!', 'success');
                loadNgoDashboard(); // Refresh
            } catch (e) {
                showAlert(e.message, 'danger');
            }
        });
    }
});

// Helper if donation.js isn't loaded
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
