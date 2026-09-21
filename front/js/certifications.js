async function loadCertifications() {
    const container = document.getElementById('certifications-container');

    try {
        const certifications = await apiGet('/certifications');

        if (certifications.length === 0) {
            container.innerHTML = '<p class="empty-state">Todavía no hay certificaciones cargadas.</p>';
            return;
        }

        container.innerHTML = certifications.map(cert => `
            <div class="cert-card">
                ${cert.issuerLogo ? `<img src="${safeUrl(cert.issuerLogo)}" alt="${escapeHtml(cert.issuer)}" class="cert-logo">` : ''}
                <div class="cert-info">
                    <h3>${escapeHtml(cert.title)}</h3>
                    <p class="cert-meta">${escapeHtml(cert.issuer)} — ${formatDate(cert.issueDate)}</p>
                </div>
                ${cert.documentPath ? `<a href="${safeUrl(cert.documentPath)}" target="_blank" rel="noopener noreferrer" class="btn">Ver documento</a>` : ''}
            </div>
        `).join('');

    } catch (error) {
        container.innerHTML = '<p class="empty-state">No se pudieron cargar las certificaciones.</p>';
        console.error(error);
    }
}

function formatDate(isoDate) {
    if (!isoDate) return '';
    const date = new Date(isoDate);
    return date.toLocaleDateString('es-ES', { year: 'numeric', month: 'long' });
}

loadCertifications();