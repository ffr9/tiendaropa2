package tiendaropa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tiendaropa.authentication.ManagerUserSession;
import tiendaropa.controller.exception.ProductoNotFoundException;
import tiendaropa.controller.exception.UsuarioNoLogeadoException;
import tiendaropa.dto.*;
import tiendaropa.model.*;
import tiendaropa.service.CategoriaService;
import tiendaropa.service.ProductoService;
import tiendaropa.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Set;
import tiendaropa.service.CarritoService;

@Controller
public class CarritoController {
    @Autowired
    private CarritoService carritoService;
    @Autowired
    private CarritoService lineaCarritoService;
    @Autowired
    private ManagerUserSession managerUserSession;
    @Autowired
    UsuarioService usuarioService;

    @GetMapping("/tiendaropa/carrito")
    public String verCarrito(Model model) {
        // Obtener el usuario actual (puedes modificar esto según tu lógica de autenticación)
        Long usuarioId = managerUserSession.usuarioLogeado();
        UsuarioData user = usuarioService.findById(usuarioId);

        List<Usuario> usuarios = usuarioService.listadoCompleto();
        Usuario usuario = usuarioService.buscarUsuarioPorId(usuarios, usuarioId);


        // Obtener el carrito del usuario
        Carrito carrito = carritoService.obtenerCarritoUsuario(usuario);

        // Obtener todas las líneas de ese carrito
        List<LineaCarrito> lineasCarrito = carritoService.allLineasCarrito(carrito);

        // Pasar las líneas del carrito al modelo para que la vista pueda mostrarlas
        model.addAttribute("lineasCarrito", lineasCarrito);
        model.addAttribute("usuario", user);

        return "carrito";
    }

    @PostMapping("/tiendaropa/carrito")
    public String añadirProductosCarrito(@RequestParam String productoId,
                                         @RequestParam int cantidad,
                                         RedirectAttributes redirectAttributes) {

        Long prodId = Long.parseLong(productoId);
        // Obtener el usuario actual (puedes modificar esto según tu lógica de autenticación)
        Long usuarioId = managerUserSession.usuarioLogeado();

        List<Usuario> usuarios = usuarioService.listadoCompleto();
        Usuario usuario = usuarioService.buscarUsuarioPorId(usuarios, usuarioId);

        // Obtener el carrito del usuario
        Carrito carrito = carritoService.obtenerCarritoUsuario(usuario);

        // Añadir productos al carrito
        try {
            lineaCarritoService.añadirProductos(carrito, prodId, cantidad);
            redirectAttributes.addFlashAttribute("mensaje", "Producto añadido al carrito correctamente.");
        } catch (ProductoNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", "Producto no encontrado.");
        } catch (UsuarioNoLogeadoException e) {
            redirectAttributes.addFlashAttribute("error", "Usuario no logeado.");
        }

        return "redirect:/tiendaropa/catalogo";
    }
}
