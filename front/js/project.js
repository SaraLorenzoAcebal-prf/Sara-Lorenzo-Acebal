async function loadProject() {
    const container = document.getElementById('project-container');

    const params = new URLSearchParams(window.location.search);
    const slug = params.get('slug');

    if (!slug) {
        container.innerHTML = '<p class="empty-state">Proyecto no especificado.</p>';
        return;
    }

    try {
        const project = await apiGet(`/projects/${slug}`);

        document.title = `${project.title} — Sara Lorenzo`;

        container.innerHTML = `
            <article class="post-detail">
                ${project.coverImage ? `<img src="${safeUrl(project.coverImage)}" alt="${escapeHtml(project.title)}" class="post-cover">` : ''}
                <h1 class="page-title">${escapeHtml(project.title)}</h1>
                ${renderTechStack(project.technologies)}
                <div class="post-content">${escapeHtml(project.content).replace(/\n/g, '<br>')}</div>
                ${renderLinks(project)}
            </article>
        `;

    } catch (error) {
        container.innerHTML = '<p class="empty-state">No se pudo cargar el proyecto. Puede que no exista o no esté publicado.</p>';
        console.error(error);
    }
}

function renderTechStack(technologies) {
    if (!technologies) return '';
    const chips = technologies.split(',').map(t => t.trim()).filter(Boolean);
    return `<div class="tech-stack">${chips.map(t => `<span class="tech-chip">${escapeHtml(t)}</span>`).join('')}</div>`;
}

function renderLinks(project) {
    const links = [];
    if (project.repoUrl) {
        links.push(`<a href="${safeUrl(project.repoUrl)}" target="_blank" rel="noopener noreferrer" class="btn">Ver código</a>`);
    }
    if (project.demoUrl) {
        links.push(`<a href="${safeUrl(project.demoUrl)}" target="_blank" rel="noopener noreferrer" class="btn">Ver demo</a>`);
    }
    if (links.length === 0) return '';
    return `<div style="margin-top: 1.5rem; display: flex; gap: 1rem;">${links.join('')}</div>`;
}

loadProject();