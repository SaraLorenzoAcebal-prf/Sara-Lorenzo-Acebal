async function loadPosts() {
    const container = document.getElementById('posts-container');

    try {
        const posts = await apiGet('/posts');

        if (posts.length === 0) {
            container.innerHTML = '<p class="empty-state">Todavía no hay posts publicados.</p>';
            return;
        }

        container.innerHTML = posts.map(post => `
            <article class="card">
                ${post.coverImage ? `<img src="${safeUrl(post.coverImage)}" alt="${escapeHtml(post.title)}">` : ''}
                <div class="card-body">
                    <h2>${escapeHtml(post.title)}</h2>
                    <p>${escapeHtml(post.excerpt)}</p>
                    <a href="post.html?slug=${encodeURIComponent(post.slug)}" class="btn">Leer más</a>
                </div>
            </article>
        `).join('');

    } catch (error) {
        container.innerHTML = '<p class="empty-state">No se pudieron cargar los posts. ¿Está el backend arrancado?</p>';
        console.error(error);
    }
}

loadPosts();