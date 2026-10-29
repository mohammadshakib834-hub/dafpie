async function loadAdminDashboard() {
    try {
        const stats = await apiCall('/admin/dashboard');
        
        // Update counters
        document.getElementById('statFoodSaved').textContent = stats.foodSavedKg + ' kg';
        document.getElementById('statPeopleHelped').textContent = stats.peopleHelped;
        document.getElementById('statTotalDonations').textContent = stats.totalDonations;
        document.getElementById('statCompleted').textContent = stats.completedDonations;
        
        document.getElementById('statDonors').textContent = stats.totalDonors;
        document.getElementById('statNgos').textContent = stats.totalNgos;
        document.getElementById('statVolunteers').textContent = stats.totalVolunteers;
        
        // Render Chart
        const ctx = document.getElementById('userChart').getContext('2d');
        new Chart(ctx, {
            type: 'doughnut',
            data: {
                labels: ['Donors', 'NGOs', 'Volunteers'],
                datasets: [{
                    data: [stats.totalDonors, stats.totalNgos, stats.totalVolunteers],
                    backgroundColor: ['#10B981', '#F59E0B', '#3B82F6'],
                    borderWidth: 0
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                cutout: '70%',
                plugins: {
                    legend: { position: 'bottom' }
                }
            }
        });
        
    } catch (e) {
        console.error(e);
        showAlert('Failed to load admin stats', 'danger');
    }
}
