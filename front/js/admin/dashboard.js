requireAuth(); // redirige a login.html si no hay token

const FORM_DRAFT_KEY = 'admin_form_draft';

function saveFormDraft(form) {
    const values = {};
    form.querySelectorAll('input, textarea').forEach(field => {
        if (field.type !== 'file') {
            values[field.id] = field.type === 'checkbox' ? field.checked : field.value;
        }
    });
    sessionStorage.setItem(FORM_DRAFT_KEY, JSON.stringify({
        formId: form.id,
        values
    }));
}

function restoreFormDraft() {
    const rawDraft = sessionStorage.getItem(FORM_DRAFT_KEY);
    if (!rawDraft) return;

    try {
        const draft = JSON.parse(rawDraft);
        const form = document.getElementById(draft.formId);
        if (!form) return;

        form.hidden = false;
        Object.entries(draft.values).forEach(([id, value]) => {
            const field = document.getElementById(id);
            if (!field) return;
            if (field.type === 'checkbox') {
                field.checked = value;
            } else {
                field.value = value;
            }
        });
        sessionStorage.removeItem(FORM_DRAFT_KEY);
    } catch (error) {
        sessionStorage.removeItem(FORM_DRAFT_KEY);
        console.error('No se pudo restaurar el formulario', error);
    }
}

document.querySelectorAll('.admin-form-panel').forEach(form => {
    form.addEventListener('input', () => saveFormDraft(form));
});

// --- Cambio de pestañas ---

document.querySelectorAll('.admin-tab').forEach(tab => {
    tab.addEventListener('click', () => {
        document.querySelectorAll('.admin-tab').forEach(t => t.classList.remove('active'));
        document.querySelectorAll('.admin-panel').forEach(p => p.hidden = true);

        tab.classList.add('active');
        document.getElementById(`tab-${tab.dataset.tab}`).hidden = false;

        if (tab.dataset.tab === 'projects') loadProjectsTable();
        if (tab.dataset.tab === 'certifications') loadCertificationsTable();
    });
});

// --- Posts: listado ---

async function loadPostsTable() {
    const tbody = document.getElementById('posts-table-body');
    try {
        const posts = await apiAuthFetch('/posts/admin/all');

        if (posts.length === 0) {
            tbody.innerHTML = '<tr><td colspan="4">No hay posts todavía.</td></tr>';
            return;
        }

        tbody.innerHTML = posts.map(post => `
            <tr>
                <td>${post.title}</td>
                <td><span class="admin-badge ${post.status === 'PUBLISHED' ? 'published' : 'draft'}">${post.status}</span></td>
                <td>${post.createdAt ? new Date(post.createdAt).toLocaleDateString('es-ES') : ''}</td>
                <td class="admin-actions">
                    <button onclick='editPost(${JSON.stringify(post)})'>Editar</button>
                    <button class="delete-btn" onclick="deletePost(${post.id})">Borrar</button>
                </td>
            </tr>
        `).join('');

    } catch (error) {
        tbody.innerHTML = '<tr><td colspan="4">Error al cargar los posts.</td></tr>';
        console.error(error);
    }
}

// --- Posts: mostrar/ocultar formulario ---

function showPostForm() {
    document.getElementById('post-form').hidden = false;
}

function hidePostForm() {
    document.getElementById('post-form').hidden = true;
    document.getElementById('post-form').reset();
    document.getElementById('post-id').value = '';
}

function editPost(post) {
    showPostForm();
    document.getElementById('post-id').value = post.id;
    document.getElementById('post-title').value = post.title;
    document.getElementById('post-excerpt').value = post.excerpt ?? '';
    document.getElementById('post-content').value = post.content;
    document.getElementById('post-cover').value = post.coverImage ?? '';
    document.getElementById('post-publish').checked = post.status === 'PUBLISHED';
    document.getElementById('post-cover-preview').textContent = post.coverImage ? 'Actual: ' + post.coverImage : '';
}

// --- Posts: guardar (crear o editar) ---

document.getElementById('post-form').addEventListener('submit', async (event) => {
    event.preventDefault();

    const id = document.getElementById('post-id').value;

    const body = {
        title: document.getElementById('post-title').value,
        excerpt: document.getElementById('post-excerpt').value,
        content: document.getElementById('post-content').value,
        coverImage: document.getElementById('post-cover').value,
        publish: document.getElementById('post-publish').checked
    };

    try {
        if (id) {
            await apiAuthFetch(`/posts/admin/${id}`, { method: 'PUT', body: JSON.stringify(body) });
        } else {
            await apiAuthFetch('/posts/admin', { method: 'POST', body: JSON.stringify(body) });
        }

        hidePostForm();
        loadPostsTable();

    } catch (error) {
        alert('Error al guardar: ' + error.message);
    }
});

// --- Posts: borrar ---

async function deletePost(id) {
    if (!confirm('¿Seguro que quieres borrar este post?')) return;

    try {
        await apiAuthFetch(`/posts/admin/${id}`, { method: 'DELETE' });
        loadPostsTable();
    } catch (error) {
        alert('Error al borrar: ' + error.message);
    }
}

// --- Inicio ---

loadPostsTable();

// --- Projects: listado ---

async function loadProjectsTable() {
    const tbody = document.getElementById('projects-table-body');
    try {
        const projects = await apiAuthFetch('/projects/admin/all');

        if (projects.length === 0) {
            tbody.innerHTML = '<tr><td colspan="4">No hay proyectos todavía.</td></tr>';
            return;
        }

        tbody.innerHTML = projects.map(project => `
            <tr>
                <td>${project.title}</td>
                <td><span class="admin-badge ${project.status === 'PUBLISHED' ? 'published' : 'draft'}">${project.status}</span></td>
                <td>${project.createdAt ? new Date(project.createdAt).toLocaleDateString('es-ES') : ''}</td>
                <td class="admin-actions">
                    <button onclick='editProject(${JSON.stringify(project)})'>Editar</button>
                    <button class="delete-btn" onclick="deleteProject(${project.id})">Borrar</button>
                </td>
            </tr>
        `).join('');

    } catch (error) {
        tbody.innerHTML = '<tr><td colspan="4">Error al cargar los proyectos.</td></tr>';
        console.error(error);
    }
}

// --- Projects: mostrar/ocultar formulario ---

function showProjectForm() {
    document.getElementById('project-form').hidden = false;
}

function hideProjectForm() {
    document.getElementById('project-form').hidden = true;
    document.getElementById('project-form').reset();
    document.getElementById('project-id').value = '';
}

function editProject(project) {
    showProjectForm();
    document.getElementById('project-id').value = project.id;
    document.getElementById('project-title').value = project.title;
    document.getElementById('project-description').value = project.description ?? '';
    document.getElementById('project-content').value = project.content;
    document.getElementById('project-technologies').value = project.technologies ?? '';
    document.getElementById('project-repo').value = project.repoUrl ?? '';
    document.getElementById('project-demo').value = project.demoUrl ?? '';
    document.getElementById('project-cover').value = project.coverImage ?? '';
    document.getElementById('project-publish').checked = project.status === 'PUBLISHED';
    document.getElementById('project-cover-preview').textContent = project.coverImage ? 'Actual: ' + project.coverImage : '';
}

// --- Projects: guardar (crear o editar) ---

document.getElementById('project-form').addEventListener('submit', async (event) => {
    event.preventDefault();

    const id = document.getElementById('project-id').value;

    const body = {
        title: document.getElementById('project-title').value,
        description: document.getElementById('project-description').value,
        content: document.getElementById('project-content').value,
        technologies: document.getElementById('project-technologies').value,
        repoUrl: document.getElementById('project-repo').value,
        demoUrl: document.getElementById('project-demo').value,
        coverImage: document.getElementById('project-cover').value,
        publish: document.getElementById('project-publish').checked
    };

    try {
        if (id) {
            await apiAuthFetch(`/projects/admin/${id}`, { method: 'PUT', body: JSON.stringify(body) });
        } else {
            await apiAuthFetch('/projects/admin', { method: 'POST', body: JSON.stringify(body) });
        }

        hideProjectForm();
        loadProjectsTable();

    } catch (error) {
        alert('Error al guardar: ' + error.message);
    }
});

// --- Projects: borrar ---

async function deleteProject(id) {
    if (!confirm('¿Seguro que quieres borrar este proyecto?')) return;

    try {
        await apiAuthFetch(`/projects/admin/${id}`, { method: 'DELETE' });
        loadProjectsTable();
    } catch (error) {
        alert('Error al borrar: ' + error.message);
    }
}

// --- Subida de imagenes de portada (posts y proyectos) ---

function setupCoverUpload(fileInputId, hiddenInputId, previewId) {
    const fileInput = document.getElementById(fileInputId);
    const hiddenInput = document.getElementById(hiddenInputId);
    const preview = document.getElementById(previewId);

    fileInput.addEventListener('change', async (event) => {
        event.preventDefault();
        const file = fileInput.files[0];
        if (!file) return;

        saveFormDraft(fileInput.form);
        const localPreviewUrl = URL.createObjectURL(file);
        renderFilePreview(preview, file, localPreviewUrl, 'Seleccionado');
        hiddenInput.value = '';

        try {
            const path = await uploadFile(file);
            hiddenInput.value = path;
            saveFormDraft(fileInput.form);
            renderFilePreview(preview, file, getAssetUrl(path), 'Subido');
        } catch (error) {
            preview.textContent = 'Error al subir el archivo: ' + error.message;
            console.error(error);
        }
    });
}

function getAssetUrl(path) {
    if (path.startsWith('http://') || path.startsWith('https://')) return path;
    return `${API_BASE_URL.replace(/\/api$/, '')}${path}`;
}

function renderFilePreview(container, file, url, status) {
    container.replaceChildren();

    const statusText = document.createElement('span');
    statusText.textContent = `${status}: ${file.name}`;
    container.appendChild(statusText);

    if (file.type.startsWith('image/')) {
        const image = document.createElement('img');
        image.src = url;
        image.alt = `Vista previa de ${file.name}`;
        container.appendChild(image);
        return;
    }

    if (file.type === 'application/pdf') {
        const link = document.createElement('a');
        link.href = url;
        link.target = '_blank';
        link.rel = 'noopener noreferrer';
        link.textContent = 'Abrir vista previa del PDF';
        container.appendChild(link);
    }
}

setupCoverUpload('post-cover-file', 'post-cover', 'post-cover-preview');
setupCoverUpload('project-cover-file', 'project-cover', 'project-cover-preview');

// --- Certifications: listado ---

async function loadCertificationsTable() {
    const tbody = document.getElementById('certifications-table-body');
    try {
        const certs = await apiAuthFetch('/certifications'); // no hay ruta admin/all: no tiene DRAFT/PUBLISHED

        if (certs.length === 0) {
            tbody.innerHTML = '<tr><td colspan="4">No hay certificaciones todavía.</td></tr>';
            return;
        }

        tbody.innerHTML = certs.map(cert => `
            <tr>
                <td>${cert.title}</td>
                <td>${cert.issuer}</td>
                <td>${cert.issueDate ?? ''}</td>
                <td class="admin-actions">
                    <button onclick='editCert(${JSON.stringify(cert)})'>Editar</button>
                    <button class="delete-btn" onclick="deleteCert(${cert.id})">Borrar</button>
                </td>
            </tr>
        `).join('');

    } catch (error) {
        tbody.innerHTML = '<tr><td colspan="4">Error al cargar las certificaciones.</td></tr>';
        console.error(error);
    }
}

// --- Certifications: mostrar/ocultar formulario ---

function showCertForm() {
    document.getElementById('cert-form').hidden = false;
}

function hideCertForm() {
    document.getElementById('cert-form').hidden = true;
    document.getElementById('cert-form').reset();
    document.getElementById('cert-id').value = '';
    document.getElementById('cert-logo-preview').textContent = '';
    document.getElementById('cert-document-preview').textContent = '';
}

function editCert(cert) {
    showCertForm();
    document.getElementById('cert-id').value = cert.id;
    document.getElementById('cert-title').value = cert.title;
    document.getElementById('cert-issuer').value = cert.issuer;
    document.getElementById('cert-issue-date').value = cert.issueDate ?? '';
    document.getElementById('cert-expiration-date').value = cert.expirationDate ?? '';
    document.getElementById('cert-skills').value = cert.skillsLearned ?? '';
    document.getElementById('cert-credential-url').value = cert.credentialUrl ?? '';
    document.getElementById('cert-logo').value = cert.issuerLogo ?? '';
    document.getElementById('cert-document').value = cert.documentPath ?? '';
    document.getElementById('cert-logo-preview').textContent = cert.issuerLogo ? 'Actual: ' + cert.issuerLogo : '';
    document.getElementById('cert-document-preview').textContent = cert.documentPath ? 'Actual: ' + cert.documentPath : '';
}

// --- Certifications: guardar (crear o editar) ---

document.getElementById('cert-form').addEventListener('submit', async (event) => {
    event.preventDefault();

    const id = document.getElementById('cert-id').value;

    const body = {
        title: document.getElementById('cert-title').value,
        issuer: document.getElementById('cert-issuer').value,
        issueDate: document.getElementById('cert-issue-date').value,
        expirationDate: document.getElementById('cert-expiration-date').value || null,
        skillsLearned: document.getElementById('cert-skills').value,
        credentialUrl: document.getElementById('cert-credential-url').value,
        issuerLogo: document.getElementById('cert-logo').value,
        documentPath: document.getElementById('cert-document').value
    };

    try {
        if (id) {
            await apiAuthFetch(`/certifications/admin/${id}`, { method: 'PUT', body: JSON.stringify(body) });
        } else {
            await apiAuthFetch('/certifications/admin', { method: 'POST', body: JSON.stringify(body) });
        }

        hideCertForm();
        loadCertificationsTable();

    } catch (error) {
        alert('Error al guardar: ' + error.message);
    }
});

// --- Certifications: borrar ---

async function deleteCert(id) {
    if (!confirm('¿Seguro que quieres borrar esta certificación?')) return;

    try {
        await apiAuthFetch(`/certifications/admin/${id}`, { method: 'DELETE' });
        loadCertificationsTable();
    } catch (error) {
        alert('Error al borrar: ' + error.message);
    }
}

// --- Certifications: subida de logo y documento ---

setupCoverUpload('cert-logo-file', 'cert-logo', 'cert-logo-preview');
setupCoverUpload('cert-document-file', 'cert-document', 'cert-document-preview');

restoreFormDraft();