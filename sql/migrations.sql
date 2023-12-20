CREATE TABLE IF NOT EXISTS Usuario (
    Id SERIAL PRIMARY KEY,
    Email VARCHAR(255) NOT NULL,
    Nombre VARCHAR(255) NOT NULL,
    Apellidos VARCHAR(255),
    Password VARCHAR(255) NOT NULL,
    Telefono VARCHAR(20),
    Codigopostal INTEGER,
    Pais VARCHAR(255),
    Poblacion VARCHAR(255),
    Direccion VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS Categoria (
    Id SERIAL PRIMARY KEY,
    Nombre VARCHAR(255) NOT NULL,
    Descripcion VARCHAR(255),
    SubcategoriaId INT,
    FOREIGN KEY (SubcategoriaId) REFERENCES Categoria(Id)
);

CREATE TABLE IF NOT EXISTS Producto (
    Id SERIAL PRIMARY KEY,
    Nombre VARCHAR(255) NOT NULL,
    Precio FLOAT NOT NULL,
    Stock INT NOT NULL,
    NumRef VARCHAR(255),
    Destacado BOOLEAN,
    CategoriaId INT,
    FOREIGN KEY (CategoriaId) REFERENCES Categoria(Id)
);

CREATE TABLE IF NOT EXISTS Carrito (
    Id SERIAL PRIMARY KEY,
    UsuarioId INT,
    FOREIGN KEY (UsuarioId) REFERENCES Usuario(Id)
);

CREATE TABLE IF NOT EXISTS LineaCarrito (
    Id SERIAL PRIMARY KEY,
    Cantidad INT,
    ProductoId INT,
    CarritoId INT,
    FOREIGN KEY (ProductoId) REFERENCES Producto(Id),
    FOREIGN KEY (CarritoId) REFERENCES Carrito(Id)
);

CREATE TABLE IF NOT EXISTS Comentario (
    Id SERIAL PRIMARY KEY,
    Descripcion VARCHAR(255),
    Fecha DATE,
    UsuarioId INT,
    ProductoId INT,
    FOREIGN KEY (UsuarioId) REFERENCES Usuario(Id),
    FOREIGN KEY (ProductoId) REFERENCES Producto(Id)
);

CREATE TABLE IF NOT EXISTS Pedido (
    Id SERIAL PRIMARY KEY,
    Fecha DATE,
    UsuarioId INT,
    FOREIGN KEY (UsuarioId) REFERENCES Usuario(Id)
);

CREATE TABLE IF NOT EXISTS LineaPedido (
     Id SERIAL PRIMARY KEY,
     Fecha DATE,
     Cantidad INT,
     Precio FLOAT,
     PedidoId INT,
     ProductoId INT,
     FOREIGN KEY (PedidoId) REFERENCES Pedido(Id),
     FOREIGN KEY (ProductoId) REFERENCES Producto(Id)
);

