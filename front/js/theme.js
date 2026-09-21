// Se ejecuta lo antes posible para evitar parpadeo (flash del tema equivocado al cargar)
(function applyStoredTheme() {
    const stored = localStorage.getItem('theme');
    if (stored === 'light') {
        document.documentElement.setAttribute('data-theme', 'light');
    }
})();

function toggleTheme() {
    const isLight = document.documentElement.getAttribute('data-theme') === 'light';

    if (isLight) {
        document.documentElement.removeAttribute('data-theme');
        localStorage.setItem('theme', 'dark');
    } else {
        document.documentElement.setAttribute('data-theme', 'light');
        localStorage.setItem('theme', 'light');
    }

    updateToggleLabel();
}

function updateToggleLabel() {
    const btn = document.getElementById('theme-toggle-btn');
    if (!btn) return;
    const isLight = document.documentElement.getAttribute('data-theme') === 'light';
    btn.textContent = isLight ? '◑ oscuro' : '◐ claro';
}

// Menu hamburguesa (movil)
document.addEventListener('DOMContentLoaded', () => {
    const toggle = document.getElementById('menu-toggle-btn');
    const nav = document.querySelector('nav');
    const overlay = document.getElementById('nav-overlay');

    if (!toggle || !nav || !overlay) return;

    function closeMenu() {
        toggle.classList.remove('open');
        nav.classList.remove('open');
        overlay.classList.remove('open');
    }

    toggle.addEventListener('click', () => {
        toggle.classList.toggle('open');
        nav.classList.toggle('open');
        overlay.classList.toggle('open');
    });

    overlay.addEventListener('click', closeMenu);
    nav.querySelectorAll('a').forEach(link => link.addEventListener('click', closeMenu));
});

document.addEventListener('DOMContentLoaded', updateToggleLabel);