cd C:\Users\Sara\Documents\miWebPorfolio\back

$env:DB_URL="jdbc:mysql://localhost:3306/porfolio_blog?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC"
$env:DB_USERNAME="root"
$env:DB_PASSWORD="TU_PASSWORD_MYSQL"

$env:ADMIN_USERNAME="admin"
$env:ADMIN_PASSWORD="admin123"
$env:JWT_SECRET="mi-clave-jwt-local-de-32-caracteres"

.\mvnw.cmd spring-boot:run# Portfolio de Sara Lorenzo

Portfolio personal con frontend estático y backend Spring Boot + MySQL.

## Arranque local

1. Crea la base de datos `porfolio_blog` en MySQL.
2. Configura las variables del backend:

```powershell
$env:DB_USERNAME="root"
$env:DB_PASSWORD="tu-password-de-mysql"
$env:ADMIN_USERNAME="admin"
$env:ADMIN_PASSWORD="tu-password-de-admin"
$env:JWT_SECRET="una-clave-aleatoria-de-al-menos-32-caracteres"
$env:MAIL_PASSWORD="tu-password-de-aplicacion-de-gmail"
```

3. Arranca el backend:

```powershell
cd back
.\mvnw.cmd spring-boot:run
```

4. Sirve `front` con Live Server en `http://localhost:5500`.

El formulario de contacto envía los mensajes a `saralorenzoacebal05@gmail.com`.

## Pruebas

Las pruebas usan H2 en memoria y no necesitan MySQL:

```powershell
cd back
.\mvnw.cmd test
```

No subas contraseñas, tokens ni archivos `.env` al repositorio.
