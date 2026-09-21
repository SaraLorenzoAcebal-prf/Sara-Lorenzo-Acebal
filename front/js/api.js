const isLocalFrontend = ['localhost', '127.0.0.1'].includes(window.location.hostname);
const API_BASE_URL = isLocalFrontend ? 'http://localhost:8080/api' : '/api';

async function apiGet(path) {
    const response = await fetch(`${API_BASE_URL}${path}`);
    if (!response.ok) {
        throw new Error(`Error ${response.status} al llamar a ${path}`);
    }
    return response.json();
}