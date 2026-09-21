const contactForm = document.getElementById('contact-form');
const contactStatus = document.getElementById('contact-status');

contactForm.addEventListener('submit', async (event) => {
    event.preventDefault();
    const submitButton = contactForm.querySelector('button[type="submit"]');
    submitButton.disabled = true;
    contactStatus.textContent = 'Enviando...';
    contactStatus.className = 'form-status';

    const formData = new FormData(contactForm);
    const payload = Object.fromEntries(formData.entries());

    try {
        const response = await fetch(`${API_BASE_URL}/contact`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        });
        const result = await response.json();
        if (!response.ok) {
            throw new Error(result.message || 'No se pudo enviar el mensaje.');
        }
        contactStatus.textContent = result.message;
        contactStatus.classList.add('success');
        contactForm.reset();
    } catch (error) {
        contactStatus.textContent = error.message;
        contactStatus.classList.add('error');
    } finally {
        submitButton.disabled = false;
    }
});
