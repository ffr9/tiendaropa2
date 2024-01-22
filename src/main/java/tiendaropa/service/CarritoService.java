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

import java.util.ArrayList;
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
                    usuario.setCarrito(nuevoCarrito);
                    return carritoRepository.save(nuevoCarrito);
                });
    }

    public float obtenerTotalCarrito(Carrito carrito) {
        float total = 0.0f;

        for (LineaCarrito lineaCarrito : carrito.getLineascarrito()) {
            int cantidad = lineaCarrito.getCantidad();
            float precioUnitario = lineaCarrito.getProducto().getPrecio();
            float subtotal = cantidad * precioUnitario;
            total += subtotal;
        }

        return total;
    }

    @Transactional
    public void eliminarTodasLasLineasCarrito(Carrito carrito) {
        try {
            // Verifica que la colección no sea nula
            if (carrito.getLineascarrito() != null) {
                // Elimina todas las líneas de carrito asociadas al carrito
                carrito.getLineascarrito().clear();
                // Actualiza el carrito en la base de datos para aplicar los cambios
                carritoRepository.save(carrito);
            } else {
                System.out.println("La colección de líneas de carrito es nula.");
            }
        } catch (Exception e) {
            System.err.println("Error al eliminar las líneas de carrito.");
            e.printStackTrace(); // Imprime la pila de llamadas para obtener más información
            throw e; // Reenvía la excepción para un manejo adecuado
        }
    }

}

