const TOKEN_KEY = 'admin_token';

// --- Login (login.html) ---

const loginForm = document.getElementById('login-form');
if (loginForm) {
    loginForm.addEventListener('submit', async (event) => {
        event.preventDefault();

        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;
        const errorEl = document.getElementById('login-error');
        errorEl.hidden = true;

        try {
            const response = await fetch(`${API_BASE_URL}/auth/login`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ username, password })
            });

            if (!response.ok) {
                throw new Error('Usuario o contraseña incorrectos');
            }

            const data = await response.json();
            sessionStorage.setItem(TOKEN_KEY, data.token);
            window.location.href = 'dashboard.html';

        } catch (error) {
            errorEl.textContent = error.message;
            errorEl.hidden = false;
        }
    });
}

// --- Helpers compartidos con dashboard.html ---

function getToken() {
    return sessionStorage.getItem(TOKEN_KEY);
}

function logout() {
    sessionStorage.removeItem(TOKEN_KEY);
    window.location.href = 'login.html';
}

// Protege dashboard.html: si no hay token, redirige al login
function requireAuth() {
    if (!getToken()) {
        window.location.href = 'login.html';
    }
}

// Fetch autenticado, para crear/editar/borrar
async function apiAuthFetch(path, options = {}) {
    const token = getToken();

    const response = await fetch(`${API_BASE_URL}${path}`, {
        ...options,
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
            ...(options.headers || {})
        }
    });

    if (response.status === 401 || response.status === 403) {
        logout();
        throw new Error('Sesión expirada, vuelve a iniciar sesión');
    }

    if (!response.ok) {
        throw new Error(`Error ${response.status}`);
    }

    // DELETE devuelve 204 sin body
    if (response.status === 204) return null;



    return response.json();
}

// Sube un archivo autenticado y devuelve la ruta publica guardada
async function uploadFile(file) {
    const token = getToken();
    const formData = new FormData();
    formData.append('file', file);

    const response = await fetch(`${API_BASE_URL}/upload/admin`, {
        method: 'POST',
        headers: { 'Authorization': `Bearer ${token}` }, // sin Content-Type: el navegador lo pone solo
        body: formData
    });

    if (response.status === 401 || response.status === 403) {
        logout();
        throw new Error('Sesión expirada');
    }

    if (!response.ok) throw new Error('Error al subir el archivo');

    const data = await response.json();
    return data.path;
}
