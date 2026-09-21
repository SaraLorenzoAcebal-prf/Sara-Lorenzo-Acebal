async function loadProjects() {
    const container = document.getElementById('projects-container');

    try {
        const projects = await apiGet('/projects');

        if (projects.length === 0) {
            container.innerHTML = '<p class="empty-state">Todavía no hay proyectos publicados.</p>';
            return;
        }

        container.innerHTML = projects.map(project => `
            <article class="card">
                ${project.coverImage ? `<img src="${safeUrl(project.coverImage)}" alt="${escapeHtml(project.title)}">` : ''}
                <div class="card-body">
                    <h3>${escapeHtml(project.title)}</h3>
                    <p>${escapeHtml(project.description)}</p>
                    ${renderTechStack(project.technologies)}
                    <a href="project.html?slug=${encodeURIComponent(project.slug)}" class="btn">Ver más</a>
                </div>
            </article>
        `).join('');

    } catch (error) {
        container.innerHTML = '<p class="empty-state">No se pudieron cargar los proyectos.</p>';
        console.error(error);
    }
}

function renderTechStack(technologies) {
    if (!technologies) return '';
    const chips = technologies.split(',').map(t => t.trim()).filter(Boolean);
    return `<div class="tech-stack">${chips.map(t => `<span class="tech-chip">${escapeHtml(t)}</span>`).join('')}</div>`;
}

loadProjects();