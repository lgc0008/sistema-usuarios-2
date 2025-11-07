# Sistema de Usuarios

Este proyecto es una API REST para gestión de usuarios implementada con Spring Boot y base de datos H2.

## Requisitos Previos

- Java 8+
- Maven
- Postman

## Ejecución del Proyecto

1. Clonar el repositorio
2. Ejecutar el proyecto con Maven:
```bash
mvn spring-boot:run
```

## Verificación de Base de Datos H2

1. Acceder a la consola H2:
    - URL: http://localhost:8080/h2-console
    - JDBC URL: jdbc:h2:mem:testdb
    - Usuario: sa
    - Contraseña: {sin contraseña}

## Pruebas con Postman

### Crear Usuario
- Método: POST
- URL: http://localhost:8080/sistema/usuarios/crear
- Body Ejemplo:
```json
{
  "name": "Luis González",
  "email": "luis.gonzalez@example.cl",
  "password": "Clave123",
  "phones": [
    {
      "number": "9876543",
      "citycode": "2",
      "contrycode": "56"
    },
    {
      "number": "1234567",
      "citycode": "9",
      "contrycode": "56"
    }
  ]
}
```

## Diagrama Servicio

```plaintext
┌────────────────────────────┐
│        Cliente/API         │
│ (Postman, Frontend, curl) │
└────────────┬──────────────┘
             │
             ▼
┌────────────────────────────┐
│   UsuarioController.java   │
│  - POST /api/usuarios      │
│  - @Valid UsuarioRequest   │
└────────────┬──────────────┘
             │
             ▼
┌────────────────────────────┐
│     UsuarioService.java    │◄──────────────┐
│ UsuarioServiceImpl.java    │               │
│  - Validación lógica extra │               │
│  - Generación de token JWT │               │
└────────────┬──────────────┘               │
             │                              │
             ▼                              │
┌────────────────────────────┐              │
│   TokenGeneradorUtil.java  │              │
│  - generateToken(email)    │              │
└────────────┬──────────────┘              │
             │                              │
             ▼                              │
┌────────────────────────────┐              │
│   UsuarioRepository.java   │              │
│  - save(usuario)           │              │
│  - findByEmail(email)      │              │
└────────────┬──────────────┘              │
             │                              │
             ▼                              │
┌────────────────────────────┐              │
│     Base de datos H2       │◄─────────────┘
│  - Tabla Usuario           │
│  - Tabla Telefono          │
└────────────────────────────┘
```

## Notas
- Si se intenta crear un usuario con un mail ya existente, se responderá con un mensaje de error.
- La base de datos H2 es en memoria y se reinicia cada vez que se reinicia la aplicación