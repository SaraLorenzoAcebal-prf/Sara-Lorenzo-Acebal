async function loadStats() {
    try {
        const [projects, certifications, posts] = await Promise.all([
            apiGet('/projects'),
            apiGet('/certifications'),
            apiGet('/posts')
        ]);

        document.getElementById('stat-projects').textContent = projects.length;
        document.getElementById('stat-certifications').textContent = certifications.length;
        document.getElementById('stat-posts').textContent = posts.length;
        renderFeaturedProjects(projects);
        renderFeaturedCertifications(certifications);

    } catch (error) {
        console.error('No se pudieron cargar las estadísticas:', error);
        document.getElementById('featured-projects').innerHTML =
            '<p class="empty-state">No se pudieron cargar los proyectos.</p>';
        document.getElementById('featured-certifications').innerHTML =
            '<p class="empty-state">No se pudieron cargar las certificaciones.</p>';
    }
}

function escapeHtml(value) {
    return String(value ?? '').replace(/[&<>"']/g, character => ({
        '&': '&amp;', '<': '&lt;', '>': '&gt;', '"': '&quot;', "'": '&#039;'
    })[character]);
}

function renderFeaturedProjects(projects) {
    const container = document.getElementById('featured-projects');
    const featured = projects.slice(0, 3);
    container.innerHTML = featured.length
        ? featured.map(project => `
            <article class="card">
                ${project.coverImage ? `<img src="${safeUrl(project.coverImage)}" alt="${escapeHtml(project.title)}">` : ''}
                <div class="card-body">
                    <h3>${escapeHtml(project.title)}</h3>
                    <p>${escapeHtml(project.description)}</p>
                    <a href="project.html?slug=${encodeURIComponent(project.slug)}" class="btn">Ver proyecto</a>
                </div>
            </article>
        `).join('')
        : '<p class="empty-state">Todavia no hay proyectos publicados.</p>';
}

function renderFeaturedCertifications(certifications) {
    const container = document.getElementById('featured-certifications');
    const featured = certifications.slice(0, 3);
    container.innerHTML = featured.length
        ? featured.map(cert => `
            <article class="cert-card">
                <div class="cert-info">
                    <h3>${escapeHtml(cert.title)}</h3>
                    <p class="cert-meta">${escapeHtml(cert.issuer)}</p>
                </div>
                ${cert.documentPath ? `<a href="${safeUrl(cert.documentPath)}" target="_blank" rel="noopener noreferrer" class="btn">Ver documento</a>` : ''}
            </article>
        `).join('')
        : '<p class="empty-state">Todavia no hay certificaciones publicadas.</p>';
}

loadStats();