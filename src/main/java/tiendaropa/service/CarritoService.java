package tiendaropa.service;

import tiendaropa.controller.exception.ProductoNotFoundException;
import tiendaropa.controller.exception.SinStockException;
import tiendaropa.controller.exception.UsuarioNoLogeadoException;
import tiendaropa.dto.CategoriaData;
import tiendaropa.dto.ComentarioData;
import tiendaropa.model.*;
import tiendaropa.dto.ProductoData;
import tiendaropa.repository.*;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class CarritoService {

    Logger logger = LoggerFactory.getLogger(ProductoService.class);

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    ProductoService productoService;


    @Autowired
    private ModelMapper modelMapper;

    private final LineaCarritoRepository lineaCarritoRepository;

    @Autowired
    public CarritoService(LineaCarritoRepository lineaCarritoRepository) {
        this.lineaCarritoRepository = lineaCarritoRepository;
    }

    public List<LineaCarrito> allLineasCarrito(Carrito carrito) {
        return lineaCarritoRepository.findByCarrito(carrito);
    }

    public Carrito obtenerCarritoUsuario(Usuario usuario) {
        // Implementa la lógica para obtener el carrito asociado al usuario
        // Puedes ajustar esto según tu modelo de datos y lógica de negocio

        // En este ejemplo, se supone que hay una relación uno a uno entre Usuario y Carrito
        // y se utiliza el método findByUsuario del CarritoRepository
        return carritoRepository.findByUsuario(usuario)
                .orElseGet(() -> {
                    // Si no existe un carrito para el usuario, puedes crear uno nuevo y guardarlo
                    Carrito nuevoCarrito = new Carrito();
                    nuevoCarrito.setUsuario(usuario);
                    return carritoRepository.save(nuevoCarrito);
                });
    }

    public void añadirProductos(Carrito carrito, Long productoId, int cantidad)
            throws ProductoNotFoundException, UsuarioNoLogeadoException {
        // Verificar si el usuario está logeado
        if (carrito.getUsuario() == null) {
            throw new UsuarioNoLogeadoException();
        }

        // Obtener el producto
        //ProductoData productoData = productoService.findById(productoId);
        Producto producto = productoRepository.findById(productoId).orElse(null);
        if (producto == null) {
            throw new ProductoNotFoundException();
        }

        // Verificar si hay suficiente stock
        if (producto.getStock() < cantidad) {
            throw new SinStockException();
        }

        // Añadir el producto al carrito
        LineaCarrito lineaCarrito = new LineaCarrito();
        //carrito.addLineascarrito(lineaCarrito);

        lineaCarrito.setCarrito(carrito);
        lineaCarrito.setProducto(producto);
        lineaCarrito.setCantidad(cantidad);


        // Actualizar el stock del producto
        producto.setStock(producto.getStock() - cantidad);
        productoRepository.save(producto);


        // Guardar la línea en el carrito
        lineaCarritoRepository.save(lineaCarrito);
    }



}
