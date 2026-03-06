Al Angulo - Sistema de Gestión de Complejos Deportivos ⚽
Al Angulo es una API REST desarrollada con Java y Spring Boot para la gestión integral de reservas en complejos deportivos. El sistema permite a los usuarios buscar complejos cercanos por geolocalización, verificar el clima en tiempo real, reservar canchas y gestionar sus turnos.

🚀 Tecnologías Principales
Java 17

Spring Boot 4.0.1

Spring Security + OAuth2 (Google Login)

Spring Data JPA (PostgreSQL)

OpenWeather API (Integración externa para clima)

Lombok

JUnit 5 + Mockito (Testing)

🏗️ Estructura del Proyecto
El proyecto sigue una arquitectura en capas:

Model: Entidades JPA que representan la base de datos (User, Complex, Court, Booking, Review).

Repository: Interfaces que extienden de JpaRepository para el acceso a datos y consultas personalizadas mediante @Query.

Service: Contiene la lógica de negocio (validaciones de horarios, cálculo de precios, envío de emails).

Controller: Endpoints REST que gestionan las peticiones HTTP y la seguridad por roles.

DTOs: Objetos de transferencia de datos para desacoplar la API de las entidades internas.

🛠️ Funcionalidades Clave
🛡️ Seguridad y Roles
El sistema utiliza Google OAuth2 para la autenticación. Los roles se definen en eRol:

USER: Puede realizar reservas y dejar reseñas.

OWNER: Puede crear complejos y canchas.

📅 Sistema de Reservas
Validaciones: No se permiten reservas con menos de una hora de duración ni fuera del horario de apertura del complejo.

Cálculo de Precio: Se calcula automáticamente en base al precio por hora de la cancha y el tiempo seleccionado.

Notificaciones: Envío automático de correos electrónicos tras confirmar una reserva.

📍 Geolocalización y Clima
Búsqueda Cercana: Implementa una consulta nativa (fórmula de Haversine) para encontrar complejos dentro de un radio específico de kilómetros.

Clima: Integración con OpenWeather para prever las condiciones climáticas del complejo.

⚙️ Procesos Automáticos
Scheduled Tasks: Un proceso automático se ejecuta cada hora para marcar como FINALIZED las reservas cuya fecha y hora ya hayan pasado.

📦 Configuración Necesaria
Para correr el proyecto localmente, debes configurar las siguientes variables de entorno en tu application.properties:

DB_PASSWORD: Contraseña de PostgreSQL.

GOOGLE_CLIENT_SECRET: Secreto de cliente de Google Cloud Console.

WEATHER_API_KEY: API Key de OpenWeatherMap.

MAIL_PASSWORD: Contraseña de aplicación para el envío de correos.
