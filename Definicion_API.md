# Especificación de la API: Tienda de Ropa

Esta API sirve como método para enviar productos concretos a las diferentes Tiendas de ropa y Gimnasios.

La URL base de la API es: /tiendaropa/

Para la autenticación se deben seguir los siguientes pasos:
* Generación de Token: cuando un usuario se autentica en la aplicación, se genera un token de acceso único y se asocia al usuario.
* Envío del Token: cuando otra aplicación (tienda de ropa o gimnasio) necesita acceder a los servicios, deben enviar el token de acceso con cada solicitud.
* Validación del Token: se debe validar el token en cada solicitud recibida. Comprobar si el token ha expirado. Asegurar de que el usuario tenga los permisos necesarios para la acción solicitada.

Las operaciones disponibles para las otras aplicaciones son:
* Mostrar catálogo de productos disponible. Con posibilidad de realizar busquedas y de realizar filtrados por categoría.
* Mostrar catálogo de productos en oferta.
* Mostrar detalles de un producto concreto.
* Mostrar carrito de la compra. Con posibilidad de mandar productos al carrito y de eliminarlos del carrito.

## GET Obtener página del catálogo de productos:

* Breve descripción: Obtiene la página del catálogo de productos con la lista de productos disponibles.
* Ruta: GET /tiendaropa/catalogo
* Método: GET
* Funcionalidad: Devuelve la página del catálogo de productos para que la tienda de ropa pueda enseñarla también en su aplicación y así aumentar el catálogo.
* Estructura de la petición: No aplica (GET request).
* Estructura de la respuesta: HTML de la página del catálogo de productos.
* Parámetros: No aplica.
* Gestión de errores:
    * 404: Si la página del catálogo de productos no se encuentra.
    * 500: Error interno del servidor.
* Ejemplo de uso:
    * Petición: GET localhost:8080/tiendaropa/catalogo
    * Respuesta: HTML de la página del catálogo de productos.
 
## GET Obtener página del catálogo de productos con cierto filtro concreto:

* Breve descripción: Obtiene la página del catálogo de productos aplicando un filtro específico.
* Ruta: GET /tiendaropa/catalogo/filtro
* Método: GET
* Funcionalidad: Devuelve la página del catálogo de productos con productos que cumplen un filtro específico. Útil para la tienda de ropa y el gimnasio (ropa de deporte/fitness).
* Estructura de la petición: No aplica (GET request).
* Estructura de la respuesta: HTML de la página del catálogo de productos con productos filtrados.
* Parámetros:
    * Query Params:
        * filtro: string (Ej. "Deporte")
* Gestión de errores:
    * 404: Si la página del catálogo con filtro no se encuentra.
    * 500: Error interno del servidor.
* Ejemplo de uso:
    * Petición: GET localhost:8080/tiendaropa/catalogo/filtro?filtro=Deporte
    * Respuesta: HTML de la página del catálogo de productos con productos de deporte.

## GET Obtener página del catálogo de productos con cierta búsqueda:

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

## GET Obtener página con los detalles del producto concreto:

* Breve descripción: Obtiene la página con los detalles de un producto específico.
* Ruta: GET /tiendaropa/productos/{id}
* Método: GET
* Funcionalidad: Devuelve la página con los detalles de un producto específico.
* Estructura de la petición: No aplica (GET request).
* Estructura de la respuesta: HTML de la página con los detalles del producto.
* Parámetros:
    * Path Params:
        * id: int (ID del producto)
* Gestión de errores:
    * 404: Si el producto no se encuentra.
    * 500: Error interno del servidor.
* Ejemplo de uso:
    * Petición: GET localhost:8080/tiendaropa/productos/456
    * Respuesta: HTML de la página con los detalles del producto.

## POST Mandar el producto concreto al carrito de la compra:

* Breve descripción: Agrega un producto específico al carrito de la compra.
* Ruta: POST /tiendaropa/carrito
* Método: POST
* Funcionalidad: Agrega un producto al carrito de la compra.
* Estructura de la petición:
    * Body:
````
{
  "usuarioId": int,
  "productoId": int,
  "cantidad": int
}
````
* Estructura de la respuesta:  
201: Agregado al carrito correctamente.
````
{
  "id": int
}
````  
400: Formato de datos incorrecto o cantidad no disponible.
````
{
  "errors": {
    string,
    ...
  }
}
````
* Parámetros:
    * Headers:
        * Authorization: string (Token de usuario)
* Gestión de errores:
    * 400: Si el formato de datos es incorrecto o la cantidad no está disponible.
    * 401: Si la autorización es incorrecta.
    * 500: Error interno del servidor.
* Ejemplo de uso:  
Petición:
````
POST localhost:8080/tiendaropa/carrito

Headers:
{
  "Authorization": "Bearer <token>"
}

Body:
{
  "usuarioId": 123,
  "productoId": 456,
  "cantidad": 2
}
````  
Respuesta:
````
201 Created

{
  "id": 789
}
````
````
400 Bad Request

{
  "errors": {
    "cantidad": "La cantidad no está disponible"
  }
}
````

## GET Obtener carrito de la compra:

* Breve descripción: Obtiene el contenido actual del carrito de la compra de un usuario.
* Ruta: GET /tiendaropa/carrito
* Método: GET
* Funcionalidad: Retorna la información del carrito de la compra de un usuario.
* Estructura de la petición: No aplica (GET request).
* Estructura de la respuesta:
    * 200: Carrito obtenido correctamente.
````
{
  "productos": [
    {
      "id": int,
      "nombre": string,
      "precio": float,
      "cantidad": int
    },
    ...
  ],
  "total": float
}
````
* Parámetros:
    * Headers:
        * Authorization: string (Token de usuario)
* Gestión de errores:
    * 401: Si la autorización es incorrecta.
    * 500: Error interno del servidor.
* Ejemplo de uso:  
Petición:
````
GET localhost:8080/tiendaropa/carrito

Headers:
{
  "Authorization": "Bearer <token>"
}
````  
Respuesta:
````
200 OK

{
  "productos": [
    {
      "id": 456,
      "nombre": "Camiseta Deportiva",
      "precio": 34.99,
      "cantidad": 2
    }
  ],
  "total": 69.98
}
````

## DELETE Eliminar producto del carrito de la compra:

* Breve descripción: Elimina un producto específico del carrito de la compra.
* Ruta: DELETE /tiendaropa/carrito/{idProducto}
* Método: DELETE
* Funcionalidad: Elimina un producto específico del carrito de la compra.
* Estructura de la petición: No aplica (DELETE request).
* Estructura de la respuesta:
    * 204: Producto eliminado correctamente.
    * 404: Producto no encontrado en el carrito.
* Parámetros:
    * Path Params:
        * idProducto: int (ID del producto a eliminar)
    * Headers:
        * Authorization: string (Token de usuario)
* Gestión de errores:
    * 401: Si la autorización es incorrecta.
    * 404: Si el producto no se encuentra en el carrito.
    * 500: Error interno del servidor.
* Ejemplo de uso:  
Petición:
````
DELETE localhost:8080/tiendaropa/carrito/456

Headers:
{
  "Authorization": "Bearer <token>"
}
````  
Respuesta:
* 204 No Content (Eliminado correctamente)
* 404 Not Found (Producto no encontrado en el carrito)

## GET Obtener página con el listado de productos ofertados/destacados:

* Breve descripción: Obtiene la página con el listado de productos ofertados o destacados.
* Ruta: GET /tiendaropa/productos/ofertados
* Método: GET
* Funcionalidad: Retorna la página con el listado de productos ofertados o destacados. Útil para la tienda de ropa y el gimnasio.
* Estructura de la petición: No aplica (GET request).
* Estructura de la respuesta:
    * 200: Listado de productos ofertados o destacados.
````
{
  "productos": [
    {
      "id": int,
      "nombre": string,
      "precio": float,
      "ofertado": boolean,
      "destacado": boolean
    },
    ...
  ]
}
````
* Parámetros: No aplica.
* Gestión de errores:
    * 404: Si no hay productos ofertados o destacados.
    * 500: Error interno del servidor.
* Ejemplo de uso:
    * Petición: GET localhost:8080/tiendaropa/productos/ofertados
    * Respuesta:
````
200 OK

{
  "productos": [
    {
      "id": 456,
      "nombre": "Camiseta Deportiva",
      "precio": 34.99,
      "ofertado": true,
      "destacado": false
    },
    {
      "id": 789,
      "nombre": "Zapatillas Running",
      "precio": 79.99,
      "ofertado": false,
      "destacado": true
    }
  ]
}
````
````
{
  "error": "Formato de datos incorrecto"
}
````
