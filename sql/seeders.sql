-- Seeder para la tabla Usuario
INSERT INTO Usuario (Email, Nombre, Apellidos, Password, Telefono, Codigopostal, Pais, Poblacion, Direccion, Admin)
VALUES
    ('usuario1@example.com', 'Usuario1', 'Apellidos1', 'contraseña1', '123456789', 12345, 'País1', 'Población1', 'Dirección1', false),
    ('usuario2@example.com', 'Usuario2', 'Apellidos2', 'contraseña2', '987654321', 54321, 'País2', 'Población2', 'Dirección2', false),
    ('usuario3@example.com', 'Usuario3', 'Apellidos3', 'contraseña3', '456789012', 67890, 'País3', 'Población3', 'Dirección3', false),
    ('usuario4@example.com', 'Usuario4', 'Apellidos4', 'contraseña4', '321098765', 54321, 'País4', 'Población4', 'Dirección4', false),
    ('usuario5@example.com', 'Usuario5', 'Apellidos5', 'contraseña5', '876543210', 98765, 'País5', 'Población5', 'Dirección5', false);

-- Seeder para la tabla Categoria
INSERT INTO Categoria (Nombre, Descripcion, SubcategoriaId)
VALUES
    ('Electrónicos', 'Productos electrónicos', NULL),
    ('Ropa', 'Ropa de moda', NULL),
    ('Smartphones', 'Teléfonos inteligentes', 1), -- Subcategoría de Electrónicos
    ('Accesorios', 'Accesorios de moda', 2), -- Subcategoría de Ropa
    ('Ofertas', 'Productos en oferta', NULL);

-- Seeder para la tabla Producto
INSERT INTO Producto (Nombre, Precio, Stock, NumRef, Destacado, CategoriaId, Img)
VALUES
    ('Smartphone Modelo A', 599.99, 50, '123456789', true, 3, 'smartphone.jpg'), -- Asociado a la categoría de Smartphones
    ('Laptop Modelo B', 1299.99, 30, '987654321', false, 1, 'laptop.jpg'), -- Asociado a la categoría de Electrónicos
    ('Gorra de Moda', 19.99, 100, '567890123', true, 4, 'gorra.jpg'), -- Asociado a la categoría de Accesorios
    ('Falda Elegante', 49.99, 80, '456789012', false, 4, 'faldaMujer.jpg'), -- Asociado a la categoría de Accesorios
    ('Falda Casual', 39.99, 70, '345678901', false, 4, 'faldaHombre.jpg'); -- Asociado a la categoría de Accesorios

-- Seeder para la tabla Carrito
INSERT INTO Carrito (UsuarioId)
VALUES
    (1), -- Asociado al Usuario con Id 1
    (2), -- Asociado al Usuario con Id 2
    (3), -- Asociado al Usuario con Id 3
    (4), -- Asociado al Usuario con Id 4
    (5); -- Asociado al Usuario con Id 5

-- Seeder para la tabla LineaCarrito
INSERT INTO LineaCarrito (Cantidad, ProductoId, CarritoId)
VALUES
    (2, 1, 1), -- 2 unidades del Producto con Id 1 en el Carrito con Id 1
    (1, 2, 2), -- 1 unidad del Producto con Id 2 en el Carrito con Id 2
    (3, 3, 3), -- 3 unidades del Producto con Id 3 en el Carrito con Id 3
    (1, 5, 1), -- 1 unidad del Producto con Id 5 en el Carrito con Id 1
    (2, 5, 2); -- 2 unidades del Producto con Id 6 en el Carrito con Id 2

-- Seeder para la tabla Comentario
INSERT INTO Comentario (Descripcion, Fecha, UsuarioId, ProductoId)
VALUES
    ('Excelente producto', '2023-01-01', 1, 1), -- Comentario del Usuario con Id 1 al Producto con Id 1
    ('Buena calidad', '2023-01-02', 1, 2), -- Comentario del Usuario con Id 2 al Producto con Id 2
    ('Muy a la moda', '2023-01-05', 2, 3), -- Comentario del Usuario con Id 2 al Producto con Id 3
    ('Gran oferta', '2023-01-06', 3, 4); -- Comentario del Usuario con Id 3 al Producto con Id 7

-- Seeder para la tabla Pedido
INSERT INTO Pedido (Fecha, UsuarioId)
VALUES
    ('2023-01-03', 1), -- Pedido del Usuario con Id 1
    ('2023-01-04', 2), -- Pedido del Usuario con Id 2
    ('2023-01-07', 3); -- Pedido del Usuario con Id 3

-- Seeder para la tabla LineaPedido
INSERT INTO LineaPedido (Fecha, Cantidad, Precio, PedidoId, ProductoId)
VALUES
    ('2023-01-03', 2, 1199.98, 1, 1), -- Línea de pedido para el Producto con Id 1 en el Pedido con Id 1
    ('2023-01-04', 1, 1299.99, 2, 2), -- Línea de pedido para el Producto con Id 2 en el Pedido con Id 2
    ('2023-01-07', 1, 19.99, 3, 4), -- Línea de pedido para el Producto con Id 4 en el Pedido con Id 3
    ('2023-01-07', 2, 59.98, 3, 2); -- Línea de pedido para el Producto con Id 6 en el Pedido con Id 3
