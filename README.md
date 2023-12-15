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
