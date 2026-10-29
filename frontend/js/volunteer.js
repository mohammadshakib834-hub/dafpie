let currentRequestId = null;
let statusModal = null;

async function loadVolunteerDashboard() {
    loadAvailablePickups();
    loadMyPickups();
}

async function loadAvailablePickups() {
    try {
        const pickups = await apiCall('/volunteers/pickups/available');
        const container = document.getElementById('availablePickupsList');
        
        if (pickups.length === 0) {
            container.innerHTML = '<p class="text-muted text-center py-4">No available pickups at the moment.</p>';
            return;
        }

        container.innerHTML = pickups.map(p => `
            <div class="card bg-light border-0 mb-3">
                <div class="card-body">
                    <div class="d-flex justify-content-between">
                        <h6 class="fw-bold">${p.donation.foodName} (${p.donation.quantity} ${p.donation.unit})</h6>
                        <span class="badge bg-primary">Available</span>
                    </div>
                    <p class="small mb-1"><i class="bi bi-geo-alt"></i> <strong>From:</strong> ${p.donation.pickupAddress}, ${p.donation.city}</p>
                    <p class="small mb-3"><i class="bi bi-geo"></i> <strong>To (NGO):</strong> ${p.ngo.name}</p>
                    <button onclick="acceptPickup(${p.id})" class="btn btn-sm btn-primary-custom w-100">Accept Pickup</button>
                </div>
            </div>
        `).join('');
    } catch (e) {
        console.error(e);
    }
}

async function loadMyPickups() {
    try {
        const pickups = await apiCall('/volunteers/pickups/my');
        const container = document.getElementById('myPickupsList');
        
        if (pickups.length === 0) {
            container.innerHTML = '<p class="text-muted text-center py-4">You have no active deliveries.</p>';
            return;
        }

        container.innerHTML = pickups.map(p => {
            const isCompleted = p.status === 'DELIVERED' || p.status === 'COMPLETED';
            const actionBtn = !isCompleted ? 
                `<button onclick="openStatusModal(${p.id}, '${p.status}')" class="btn btn-sm btn-outline-custom w-100 mt-2">Update Status</button>` : 
                '';

            return `
            <div class="card ${isCompleted ? 'bg-light text-muted' : 'border-primary'} mb-3">
                <div class="card-body">
                    <div class="d-flex justify-content-between mb-2">
                        <h6 class="fw-bold m-0">${p.donation.foodName}</h6>
                        <span class="badge bg-secondary">${p.status.replace(/_/g, ' ')}</span>
                    </div>
                    <p class="small mb-1"><i class="bi bi-geo-alt"></i> <strong>Pickup:</strong> ${p.donation.pickupAddress}</p>
                    <p class="small mb-2"><i class="bi bi-building"></i> <strong>Deliver to:</strong> ${p.ngo.name} (${p.ngo.address})</p>
                    ${p.specialInstructions ? `<div class="alert alert-info py-1 px-2 small mb-2"><i class="bi bi-info-circle"></i> ${p.specialInstructions}</div>` : ''}
                    ${actionBtn}
                </div>
            </div>
            `;
        }).join('');
    } catch (e) {
        console.error(e);
    }
}

async function acceptPickup(id) {
    if(!confirm('Are you sure you want to accept this pickup?')) return;
    try {
        await apiCall(`/volunteers/pickups/${id}/accept`, { method: 'POST' });
        showAlert('Pickup accepted successfully!', 'success');
        loadVolunteerDashboard();
    } catch (e) {
        showAlert(e.message, 'danger');
    }
}

function openStatusModal(id, currentStatus) {
    currentRequestId = id;
    if (!statusModal) {
        statusModal = new bootstrap.Modal(document.getElementById('statusModal'));
    }
    document.getElementById('deliveryNotes').value = '';
    statusModal.show();
}

document.addEventListener('DOMContentLoaded', () => {
    const confirmBtn = document.getElementById('confirmStatusBtn');
    if (confirmBtn) {
        confirmBtn.addEventListener('click', async () => {
            const status = document.getElementById('newStatus').value;
            const notes = document.getElementById('deliveryNotes').value;
            try {
                await apiCall(`/volunteers/pickups/${currentRequestId}/status`, {
                    method: 'PUT',
                    body: JSON.stringify({ status, notes })
                });
                
                statusModal.hide();
                showAlert('Status updated successfully!', 'success');
                loadVolunteerDashboard();
            } catch (e) {
                showAlert(e.message, 'danger');
            }
        });
    }
});
