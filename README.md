# bStrongGym - Android App

Aplicación Android para la gestión de un gimnasio, desarrollada como Actividad de Aprendizaje de la asignatura **Programación Multimedia y Dispositivos Móviles** del ciclo DAM en Centro San Valero (Zaragoza).

## Descripción

bStrongGym permite gestionar los recursos de un gimnasio desde un dispositivo Android. La aplicación consume la API REST desarrollada en la asignatura de Acceso a Datos e implementa un sistema de roles local para controlar el acceso a las distintas funcionalidades.

## Tecnologías utilizadas

- **Lenguaje:** Java
- **SDK mínimo:** Android 10 (API 29)
- **Patrón de diseño:** MVP (Model View Presenter)
- **Base de datos local:** Room (SQLite)
- **Consumo de API:** Retrofit 2 + Gson
- **Mapas:** OSMDroid (OpenStreetMap)
- **Control de versiones:** Git + GitHub con Gitflow

## Funcionalidades

### Requisitos obligatorios
- **Login y registro** con sistema de roles (ADMIN / MEMBER) gestionado localmente con Room
- **Consumo de API REST** con operaciones GET, POST, PUT y DELETE sobre 5 entidades: Socios, Monitores, Actividades, Reservas y Suscripciones
- **Base de datos local** con CRUD completo de entrenamientos personales (Room + RecyclerView con adaptador personalizado)
- **Patrón MVP** aplicado en todos los módulos
- **Mapa** con la ubicación del gimnasio usando OSMDroid
- **Multiidioma:** español e inglés
- **ActionBar** con menú de opciones en todas las Activities

### Funcionalidades opcionales implementadas
- **Git y GitHub Issues** para el seguimiento del desarrollo con Gitflow
- **Diálogos de confirmación** al eliminar cualquier registro
- **Búsqueda en listados** para filtrar información en tiempo real

## Control de acceso por roles

| Funcionalidad | ADMIN | MEMBER |
|---|---|---|
| Socios | ✅ | ❌ |
| Actividades | ✅ | ✅ |
| Reservas | ✅ | ✅ |
| Monitores | ✅ | ✅ |
| Suscripciones (gestión) | ✅ | ❌ |
| Mis entrenamientos | ✅ | ✅ |
| Mapa del gimnasio | ✅ | ✅ |

> El control de acceso se implementa a nivel de interfaz. La API no dispone de autenticación JWT, por lo que un sistema de autorización completo a nivel de servidor se contempla como mejora futura.

## Estructura del proyecto

```
com.svalero.bstronggym
├── adapter       # Adaptadores de RecyclerView
├── api           # ApiClient y ApiService (Retrofit)
├── contract      # Interfaces del patrón MVP
├── domain        # Clases que mapean las respuestas de la API
├── model         # Entidades de Room (User, Workout) y DAOs
├── presenter     # Presenters del patrón MVP
├── util          # SessionManager y utilidades
└── view          # Activities
```

## API

La aplicación consume la API REST **bStrongGym** desarrollada con Spring Boot. Los endpoints disponibles son:

- `GET/POST /members` — Gestión de socios
- `GET/POST /monitors` — Gestión de monitores
- `GET/POST /activities` — Gestión de actividades
- `GET/POST /bookings` — Gestión de reservas
- `GET/POST /subscriptions` — Gestión de suscripciones

Cada entidad dispone además de `GET /{id}`, `PUT /{id}` y `DELETE /{id}`.

> Repositorio de la API: [bStrongGym API](https://github.com/MarcosMartinezVijuesca/bStrongGym)

## Instalación y ejecución

1. Clona el repositorio:
```bash
git clone https://github.com/MarcosMartinezVijuesca/bStrongGym-Android.git
```

2. Abre el proyecto en **Android Studio**

3. Asegúrate de tener la API de Spring Boot corriendo en local en el puerto `8080`

4. Ejecuta la app en un emulador o dispositivo físico con Android 10 o superior

> La URL base de la API está configurada como `http://10.0.2.2:8080/` para el emulador de Android Studio. Si usas un dispositivo físico deberás cambiarla por la IP de tu máquina en la red local.

## Base de datos local

La app usa **Room** para dos propósitos:

- **Usuarios** (`users`): almacena las credenciales y el rol de los usuarios registrados en la app
- **Entrenamientos** (`workouts`): CRUD completo de entrenamientos personales del usuario

## Autor

**Marcos Martínez Vijuesca**
DAM — Centro San Valero, Zaragoza
Curso 2025-2026
