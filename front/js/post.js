async function loadPost() {
    const container = document.getElementById('post-container');

    const params = new URLSearchParams(window.location.search);
    const slug = params.get('slug');

    if (!slug) {
        container.innerHTML = '<p class="empty-state">Post no especificado.</p>';
        return;
    }

    try {
        const post = await apiGet(`/posts/${slug}`);

        document.title = `${post.title} — Sara Lorenzo`;

        container.innerHTML = `
            <article class="post-detail">
                ${post.coverImage ? `<img src="${safeUrl(post.coverImage)}" alt="${escapeHtml(post.title)}" class="post-cover">` : ''}
                <h1 class="page-title">${escapeHtml(post.title)}</h1>
                <p class="cert-meta">${formatDate(post.publishedAt)}</p>
                <div class="post-content">${escapeHtml(post.content).replace(/\n/g, '<br>')}</div>
                ${renderRelated(post)}
            </article>
        `;

    } catch (error) {
        container.innerHTML = '<p class="empty-state">No se pudo cargar el post. Puede que no exista o no esté publicado.</p>';
        console.error(error);
    }
}

function renderRelated(post) {
    if (post.relatedProject) {
        return `<a href="project.html?slug=${encodeURIComponent(post.relatedProject.slug)}" class="btn" style="margin-top: 1.5rem; display: inline-block;">Ver proyecto relacionado</a>`;
    }
    if (post.relatedCertification) {
        return `<p class="cert-meta" style="margin-top: 1.5rem;">Relacionado con el certificado: ${escapeHtml(post.relatedCertification.title)}</p>`;
    }
    return '';
}

function formatDate(isoDate) {
    if (!isoDate) return '';
    const date = new Date(isoDate);
    return date.toLocaleDateString('es-ES', { year: 'numeric', month: 'long', day: 'numeric' });
}

loadPost();