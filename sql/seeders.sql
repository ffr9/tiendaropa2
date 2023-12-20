-- Seeder para la tabla Usuario
INSERT INTO Usuario (Email, Nombre, Apellidos, Password, Telefono, Codigopostal, Pais, Poblacion, Direccion)
VALUES
    ('usuario1@example.com', 'Usuario1', 'Apellidos1', 'contraseña1', '123456789', 12345, 'País1', 'Población1', 'Dirección1'),
    ('usuario2@example.com', 'Usuario2', 'Apellidos2', 'contraseña2', '987654321', 54321, 'País2', 'Población2', 'Dirección2');

-- Seeder para la tabla Categoria
INSERT INTO Categoria (Nombre, Descripcion, SubcategoriaId)
VALUES
    ('Electrónicos', 'Productos electrónicos', NULL),
    ('Ropa', 'Ropa de moda', NULL),
    ('Smartphones', 'Teléfonos inteligentes', 1); -- Subcategoría de Electrónicos

-- Seeder para la tabla Producto
INSERT INTO Producto (Nombre, Precio, Stock, NumRef, Destacado, CategoriaId)
VALUES
    ('Smartphone Modelo A', 599.99, 50, '123456789', true, 3), -- Asociado a la categoría de Smartphones
    ('Laptop Modelo B', 1299.99, 30, '987654321', false, 1); -- Asociado a la categoría de Electrónicos

-- Seeder para la tabla Carrito
INSERT INTO Carrito (UsuarioId)
VALUES
    (1), -- Asociado al Usuario con Id 1
    (2); -- Asociado al Usuario con Id 2

-- Seeder para la tabla LineaCarrito
INSERT INTO LineaCarrito (Cantidad, ProductoId, CarritoId)
VALUES
    (2, 1, 1), -- 2 unidades del Producto con Id 1 en el Carrito con Id 1
    (1, 2, 2); -- 1 unidad del Producto con Id 2 en el Carrito con Id 2

-- Seeder para la tabla Comentario
INSERT INTO Comentario (Descripcion, Fecha, UsuarioId, ProductoId)
VALUES
    ('Excelente producto', '2023-01-01', 1, 1), -- Comentario del Usuario con Id 1 al Producto con Id 1
    ('Buena calidad', '2023-01-02', 2, 2); -- Comentario del Usuario con Id 2 al Producto con Id 2

-- Seeder para la tabla Pedido
INSERT INTO Pedido (Fecha, UsuarioId)
VALUES
    ('2023-01-03', 1), -- Pedido del Usuario con Id 1
    ('2023-01-04', 2); -- Pedido del Usuario con Id 2

-- Seeder para la tabla LineaPedido
INSERT INTO LineaPedido (Fecha, Cantidad, Precio, PedidoId, ProductoId)
VALUES
    ('2023-01-03', 2, 1199.98, 1, 1), -- Línea de pedido para el Producto con Id 1 en el Pedido con Id 1
    ('2023-01-04', 1, 1299.99, 2, 2); -- Línea de pedido para el Producto con Id 2 en el Pedido con Id 2
