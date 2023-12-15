# PROYECTO TIENDA DE ROPA

## Integrantes
- Francisco Fernández Ruiz
- Juan Carlos Barbero Castejón
- Noel Martínez Pomares
- Adrian Rubio Candil

## Documentación API

### GET Obtener página de registro:

* Breve descripción: Obtiene la página de registro para que los usuarios puedan crear una cuenta en la tienda.
* Ruta: GET /tiendaropa/registro
* Método: GET
* Funcionalidad: Retorna la página de registro para que los usuarios puedan completar el formulario y crear una cuenta.
* Estructura de la petición: No aplica (GET request).
* Estructura de la respuesta: HTML de la página de registro.
* Parámetros: No aplica.
* Gestión de errores:
    * 404: Si la página de registro no se encuentra.
    * 500: Error interno del servidor.
* Ejemplo de uso:
    * Petición: GET localhost:8080/tiendaropa/registro
    * Respuesta: HTML de la página de registro.
 
### POST Registrar usuario en la base de datos:

* Breve descripción: Registra un nuevo usuario en la base de datos.
* Ruta: POST /tiendaropa/registro
* Método: POST
* Funcionalidad: Registra un nuevo usuario con la información proporcionada en el formulario de registro.
* Estructura de la petición:
    * Body:
````json
{
  "nombre": string,
  "apellidos": string,
  "email": string,
  "telefono": string,
  "contrasena": string
}
````
* Estructura de la respuesta:
    * 201: Se crea correctamente.
````json
{
  "id": int
}
````
    * 400: Formato de datos incorrecto.
````json
{
  "errors": {
    string,
    ...
  }
}
````
* Parámetros: No aplica.
* Gestión de errores:
    * 400: Si el formato de datos es incorrecto.
    * 500: Error interno del servidor.
* Ejemplo de uso:
    * Petición:
````json
POST localhost:8080/tiendaropa/registro

Body:
{
  "nombre": "John",
  "apellidos": "Doe",
  "email": "john.doe@example.com",
  "telefono": "123456789",
  "contrasena": "securepassword"
}
````
    * Respuesta:
````json
201 Created

{
  "id": 123
}
````
