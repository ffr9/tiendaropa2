package tiendaropa.controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import tiendaropa.authentication.ManagerUserSession;
import tiendaropa.dto.BusquedaData;
import tiendaropa.dto.UsuarioData;
import tiendaropa.model.Producto;
import tiendaropa.model.ProductoData;
import tiendaropa.service.ProductoService;
import tiendaropa.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ProductoController {

    @Autowired
    ManagerUserSession managerUserSession;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    ProductoService productoService;

    private boolean comprobarUsuarioLogeado() {
        Long idUsuarioLogeado = managerUserSession.usuarioLogeado();
        if (idUsuarioLogeado == null)
            return false;

        return true;
    }

    @GetMapping("/tiendaropa/catalogo")
    public String mostrarCatalogo(Model model) {

        if(comprobarUsuarioLogeado()) {
            UsuarioData usuario = usuarioService.findById(managerUserSession.usuarioLogeado());
            model.addAttribute("usuario", usuario);
        }else{
            model.addAttribute("usuario", null);
        }
        List<ProductoData> productos = productoService.allProductos();
        model.addAttribute("productos", productos);

        return "catalogo";
    }

    @GetMapping("/tiendaropa/catalogo/busqueda")
    public String buscarProducto(@ModelAttribute BusquedaData busquedaData, Model model){
        if(comprobarUsuarioLogeado()) {
            UsuarioData usuario = usuarioService.findById(managerUserSession.usuarioLogeado());
            model.addAttribute("usuario", usuario);
        }else{
            model.addAttribute("usuario", null);
        }
        List<ProductoData> productos = productoService.buscarProductos(busquedaData.getBusqueda());
        model.addAttribute("busquedaData", new BusquedaData());
        model.addAttribute("productos", productos);

        return "catalogo";
    }

}
