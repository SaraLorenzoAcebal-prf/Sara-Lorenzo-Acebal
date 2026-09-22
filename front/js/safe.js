function escapeHtml(value) {
    return String(value ?? '').replace(/[&<>"']/g, character => ({
        '&': '&amp;', '<': '&lt;', '>': '&gt;', '"': '&quot;', "'": '&#039;'
    })[character]);
}

function safeUrl(value) {
    try {
        if (typeof value === 'string' && value.startsWith('/uploads/')) {
            return `${API_BASE_URL.replace(/\/api$/, '')}${value}`;
        }
        const url = new URL(value, window.location.origin);
        return ['http:', 'https:'].includes(url.protocol) ? url.href : '#';
    } catch {
        return '#';
    }
}
