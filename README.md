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
201: Se crea correctamente.
````json
{
  "id": int
}
````  
400: Formato de datos incorrecto.
```json
{
  "errors": {
    string,
    ...
  }
}
```
* Parámetros: No aplica.
* Gestión de errores:
    * 400: Si el formato de datos es incorrecto.
    * 500: Error interno del servidor.
* Ejemplo de uso:  
Petición:
```json
POST localhost:8080/tiendaropa/registro

Body:
{
  "nombre": "John",
  "apellidos": "Doe",
  "email": "john.doe@example.com",
  "telefono": "123456789",
  "contrasena": "securepassword"
}
```  
Respuesta
```json
201 Created

{
  "id": 123
}
```

### GET Obtener página de login:

* Breve descripción: Obtiene la página de inicio de sesión para que los usuarios ingresen a sus cuentas.
* Ruta: GET /tiendaropa/login
* Método: GET
* Funcionalidad: Retorna la página de inicio de sesión para que los usuarios puedan ingresar.
* Estructura de la petición: No aplica (GET request).
* Estructura de la respuesta: HTML de la página de inicio de sesión.
* Parámetros: No aplica.
* Gestión de errores:
    * 404: Si la página de inicio de sesión no se encuentra.
    * 500: Error interno del servidor.
* Ejemplo de uso:
    * Petición: GET localhost:8080/tiendaropa/login
    * Respuesta: HTML de la página de inicio de sesión.
 
### POST Comprobar en la base de datos que el usuario existe:

* Breve descripción: Verifica en la base de datos si las credenciales de inicio de sesión son válidas.
* Ruta: POST /tiendaropa/login
* Método: POST
* Funcionalidad: Comprueba las credenciales de inicio de sesión en la base de datos.
* Estructura de la petición:
    * Body:
````json
{
  "email": string,
  "contrasena": string
}
````
* Estructura de la respuesta:
    * 200: Credenciales válidas.
    * 401: Credenciales inválidas.
````json
{
  "error": "Credenciales inválidas"
}
````
* Parámetros: No aplica.
* Gestión de errores:
    * 401: Si las credenciales son inválidas.
    * 500: Error interno del servidor.
* Ejemplo de uso:  
Petición:
````json
POST localhost:8080/tiendaropa/login

Body:
{
  "email": "john.doe@example.com",
  "contrasena": "securepassword"
}
````  
Respuesta:  
* 200 OK (Credenciales válidas)  
* 401 Unauthorized (Credenciales inválidas)

### GET Obtener página del catálogo de productos:

* Breve descripción: Obtiene la página del catálogo de productos con la lista de productos disponibles.
* Ruta: GET /tiendaropa/catalogo
* Método: GET
* Funcionalidad: Devuelve la página del catálogo de productos para que los usuarios vean los productos disponibles.
* Estructura de la petición: No aplica (GET request).
* Estructura de la respuesta: HTML de la página del catálogo de productos.
* Parámetros: No aplica.
* Gestión de errores:
    * 404: Si la página del catálogo de productos no se encuentra.
    * 500: Error interno del servidor.
* Ejemplo de uso:
    * Petición: GET localhost:8080/tiendaropa/catalogo
    * Respuesta: HTML de la página del catálogo de productos.
 
### GET Obtener página del catálogo de productos con cierto filtro concreto:

* Breve descripción: Obtiene la página del catálogo de productos aplicando un filtro específico.
* Ruta: GET /tiendaropa/catalogo/filtro
* Método: GET
* Funcionalidad: Retorna la página del catálogo de productos con productos que cumplen un filtro específico.
* Estructura de la petición: No aplica (GET request).
* Estructura de la respuesta: HTML de la página del catálogo de productos con productos filtrados.
* Parámetros:
    * Query Params:
        * filtro: string (Ej. "Ofertas")
* Gestión de errores:
    * 404: Si la página del catálogo con filtro no se encuentra.
    * 500: Error interno del servidor.
* Ejemplo de uso:
    * Petición: GET localhost:8080/tiendaropa/catalogo/filtro?filtro=Ofertas
    * Respuesta: HTML de la página del catálogo de productos con productos en oferta.

### GET Obtener página del catálogo de productos con cierta búsqueda:

* Breve descripción: Obtiene la página del catálogo de productos con resultados específicos de búsqueda.
* Ruta: GET /tiendaropa/catalogo/busqueda
* Método: GET
* Funcionalidad: Retorna la página del catálogo de productos con productos que coinciden con la búsqueda.
* Estructura de la petición: No aplica (GET request).
* Estructura de la respuesta: HTML de la página del catálogo de productos con resultados de búsqueda.
* Parámetros:
    * Query Params:
        * q: string (Ej. "Camiseta")
* Gestión de errores:
    * 404: Si la página del catálogo con búsqueda no se encuentra.
    * 500: Error interno del servidor.
* Ejemplo de uso:
    * Petición: GET localhost:8080/tiendaropa/catalogo/busqueda?q=Camiseta
    * Respuesta: HTML de la página del catálogo de productos con resultados de búsqueda para "Camiseta".
 
### POST Administrador crea nuevo producto en la base de datos:

* Breve descripción: Crea un nuevo producto en la base de datos.
* Ruta: POST /tiendaropa/productos
* Método: POST
* Funcionalidad: Crea un nuevo producto en la base de datos con la información proporcionada.
* Estructura de la petición:
    * Body:
````json
{
  "nombre": string,
  "precio": float,
  "stock": int,
  "numRef": string,
  "destacado": boolean,
  "categoriaId": int
}
````
* Estructura de la respuesta:  
201: Se crea correctamente.
````json
{
  "id": int
}
````  
400: Formato de datos incorrecto.
````json
{
  "errors": {
    string,
    ...
  }
}
````
* Parámetros:
    * Headers:
        * Authorization: string (Token de administrador)
* Gestión de errores:
    * 400: Si el formato de datos es incorrecto.
    * 401: Si la autorización es incorrecta.
    * 500: Error interno del servidor.
* Ejemplo de uso:  
Petición:
````json
POST localhost:8080/tiendaropa/productos

Headers:
{
  "Authorization": "Bearer <token>"
}

Body:
{
  "nombre": "Camiseta Deportiva",
  "precio": 29.99,
  "stock": 50,
  "numRef": "CD123",
  "destacado": true,
  "categoriaId": 1
}
````  
Respuesta:
````json
201 Created

{
  "id": 456
}
````

### DELETE Administrador elimina producto de la base de datos:

* Breve descripción: Elimina un producto de la base de datos.
* Ruta: DELETE /tiendaropa/productos/{id}
* Método: DELETE
* Funcionalidad: Elimina un producto de la base de datos según su ID.
* Estructura de la petición: No aplica (DELETE request).
* Estructura de la respuesta:
     * 204: Eliminado correctamente.
     * 404: Producto no encontrado.
* Parámetros:
    * Path Params:
        * id: int (ID del producto a eliminar)
    * Headers:
        * Authorization: string (Token de administrador)
* Gestión de errores:
    * 401: Si la autorización es incorrecta.
    * 404: Si el producto no se encuentra.
    * 500: Error interno del servidor.
* Ejemplo de uso:  
Petición:
````json
DELETE localhost:8080/tiendaropa/productos/456

Headers:
{
  "Authorization": "Bearer <token>"
}
````  
Respuesta:
* 204 No Content (Eliminado correctamente)
* 404 Not Found (Producto no encontrado)

### PUT Administrador modifica el producto de la base de datos:

* Breve descripción: Modifica un producto en la base de datos.
* Ruta: PUT /tiendaropa/productos/{id}
* Método: PUT
* Funcionalidad: Modifica un producto en la base de datos según su ID con la información proporcionada.
* Estructura de la petición:
    * Body:
````json
{
  "nombre": string,
  "precio": float,
  "stock": int,
  "numRef": string,
  "destacado": boolean,
  "categoriaId": int

}
````
* Estructura de la respuesta:  
200: Modificado correctamente.
````json
{
  "id": int
}
```` 
400: Formato de datos incorrecto.
````json
{
  "errors": {
    string,
    ...
  }
}
````` 
404: Producto no encontrado.
* Parámetros:
    * Path Params:
        * id: int (ID del producto a modificar)
    * Headers:
        * Authorization: string (Token de administrador)
* Gestión de errores:
    * 400: Si el formato de datos es incorrecto.
    * 401: Si la autorización es incorrecta.
    * 404: Si el producto no se encuentra.
    * 500: Error interno del servidor.
* Ejemplo de uso:  
Petición:
````json
PUT localhost:8080/tiendaropa/productos/456

Headers:
{
  "Authorization": "Bearer <token>"
}

Body:
{
  "nombre": "Camiseta Deportiva",
  "precio": 34.99,
  "stock": 40,
  "numRef": "CD123",
  "destacado": true,
  "categoriaId": 1
}
````  
Respuesta:
````json
200 OK

{
  "id": 456
}
````
````json
404 Not Found

{
  "error": "Producto no encontrado"
}
````
