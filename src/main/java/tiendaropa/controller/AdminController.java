package tiendaropa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import tiendaropa.authentication.ManagerUserSession;
import tiendaropa.controller.exception.UsuarioNoAutorizadoException;
import tiendaropa.controller.exception.UsuarioNoLogeadoException;
import tiendaropa.dto.PedidoData;
import tiendaropa.dto.RegistroData;
import tiendaropa.dto.UsuarioData;
import tiendaropa.model.Pedido;
import tiendaropa.model.Usuario;
import tiendaropa.service.PedidoService;
import tiendaropa.service.PedidoServiceException;
import tiendaropa.service.UsuarioService;
import tiendaropa.service.UsuarioServiceException;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
public class AdminController {
    @Autowired
    private ManagerUserSession managerUserSession;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    PedidoService pedidoService;

    // Método que recupera al usuario que está logueado y comprueba si es admin o no
    // Si ni si quiera está logueado se lanza la excepción de no estar logueado
    private void comprobarAdmin(Long idUsuario) {
        if (idUsuario != null) {
            UsuarioData user = usuarioService.findById(idUsuario);
            if (user != null && !user.isAdmin()) {
                throw new UsuarioNoAutorizadoException();
            }
        } else {
            throw new UsuarioNoLogeadoException();
        }
    }

    @GetMapping("/admin")
    public String panelAdministracion(Model model, HttpSession session) {
        Long id = managerUserSession.usuarioLogeado();

        comprobarAdmin(id);

        // Si está logueado, lo buscamos en la base de datos y lo añadimos al atributo "usuario"
        UsuarioData user = usuarioService.findById(id);
        // "usuario" lo usaremos en la vista html
        model.addAttribute("usuario", user);

        return "panelAdministracion";
    }

    @GetMapping("/admin/usuarios")
    public String administracionUsuarios(Model model, HttpSession session) {
        Long id = managerUserSession.usuarioLogeado();

        comprobarAdmin(id);

        // Si está logueado, lo buscamos en la base de datos y lo añadimos al atributo "usuario"
        UsuarioData user = usuarioService.findById(id);
        // "usuario" lo usaremos en la vista html
        model.addAttribute("usuario", user);

        List<Usuario> usuarios = usuarioService.listadoCompleto();

        model.addAttribute("usuarios", usuarios);

        return "adminUsuarios";
    }

    @GetMapping("/admin/usuarios/crear")
    public String administracionUsuariosCrear(Model model, HttpSession session) {
        model.addAttribute("registroData", new RegistroData());

        Long id = managerUserSession.usuarioLogeado();

        comprobarAdmin(id);

        // Si está logueado, lo buscamos en la base de datos y lo añadimos al atributo "usuario"
        UsuarioData user = usuarioService.findById(id);
        // "usuario" lo usaremos en la vista html
        model.addAttribute("usuario", user);

        return "crearUsuario";
    }

    @PostMapping("/admin/usuarios/crear")
    public String administracionUsuariosCrearSubmit(@Valid RegistroData registroData, BindingResult result, Model model, HttpSession session) {
        Long id = managerUserSession.usuarioLogeado();

        comprobarAdmin(id);

        if (result.hasErrors()) {
            return "formRegistro";
        }

        if (usuarioService.findByEmail(registroData.getEmail()) != null) {
            model.addAttribute("registroData", registroData);
            model.addAttribute("error", "El usuario " + registroData.getEmail() + " ya existe.");
            return "formRegistro";
        }

        UsuarioData usuario = new UsuarioData();
        usuario.setEmail(registroData.getEmail());
        usuario.setPassword(registroData.getPassword());
        usuario.setNombre(registroData.getNombre());
        usuario.setApellidos(registroData.getApellidos());
        usuario.setTelefono(registroData.getTelefono());
        usuario.setCodigopostal(registroData.getCodigopostal());
        usuario.setPais(registroData.getPais());
        usuario.setPoblacion(registroData.getPoblacion());
        usuario.setDireccion(registroData.getDireccion());

        usuarioService.registrar(usuario);

        UsuarioData user = usuarioService.findById(id);
        model.addAttribute("usuario", user);

        List<Usuario> usuarios = usuarioService.listadoCompleto();
        model.addAttribute("usuarios", usuarios);

        return "redirect:/admin/usuarios";
    }

    @PostMapping("/admin/usuarios/eliminar/{usuarioId}")
    public String administracionUsuariosEliminar(@PathVariable(value = "usuarioId") Long usuarioId, Model model, HttpSession session) {
        Long id = managerUserSession.usuarioLogeado();

        comprobarAdmin(id);

        try {
            usuarioService.eliminarUsuario(usuarioId);
        } catch(UsuarioServiceException e) {
            throw new UsuarioServiceException(e.getMessage());
        }

        UsuarioData user = usuarioService.findById(id);
        model.addAttribute("usuario", user);

        List<Usuario> usuarios = usuarioService.listadoCompleto();
        model.addAttribute("usuarios", usuarios);

        return "adminUsuarios";
    }

    @GetMapping("/admin/usuarios/editar/{usuarioId}")
    public String administracionUsuariosEditar(@PathVariable(value = "usuarioId") Long usuarioId, Model model, HttpSession session) {
        Long id = managerUserSession.usuarioLogeado();

        comprobarAdmin(id);

        UsuarioData user = usuarioService.findById(id);
        model.addAttribute("logueado", user);

        List<Usuario> usuarios = usuarioService.listadoCompleto();
        Usuario usuario = usuarioService.buscarUsuarioPorId(usuarios, usuarioId);

        model.addAttribute("usuario", usuario);

        RegistroData nuevo = new RegistroData();
        nuevo.setEmail(usuario.getEmail());
        nuevo.setPassword(usuario.getPassword());
        nuevo.setNombre(usuario.getNombre());
        nuevo.setApellidos(usuario.getApellidos());
        nuevo.setTelefono(usuario.getTelefono());
        nuevo.setCodigopostal(usuario.getCodigopostal());
        nuevo.setPais(usuario.getPais());
        nuevo.setPoblacion(usuario.getPoblacion());
        nuevo.setDireccion(usuario.getDireccion());

        model.addAttribute("registroData", nuevo);

        return "editarUsuario";
    }

    @PostMapping("/admin/usuarios/editar/{usuarioId}")
    public String administracionUsuariosEditarSubmit(@PathVariable(value="usuarioId") Long usuarioId, @Valid RegistroData registroData, BindingResult result, Model model, HttpSession session) {
        Long id = managerUserSession.usuarioLogeado();

        comprobarAdmin(id);

        if (result.hasErrors()) {
            System.out.println("Ha ocurrido un error.");
        }
        else{
            try {
                UsuarioData nuevoUsuarioData = usuarioService.findById(usuarioId);

                if (registroData.getEmail() != null && registroData.getPassword() != null && registroData.getNombre() != null) {
                    nuevoUsuarioData.setEmail(registroData.getEmail());
                    nuevoUsuarioData.setPassword(registroData.getPassword());
                    nuevoUsuarioData.setNombre(registroData.getNombre());
                    nuevoUsuarioData.setApellidos(registroData.getApellidos());
                    nuevoUsuarioData.setTelefono(registroData.getTelefono());
                    nuevoUsuarioData.setPais(registroData.getPais());
                    nuevoUsuarioData.setPoblacion(registroData.getPoblacion());
                    nuevoUsuarioData.setDireccion(registroData.getDireccion());
                    nuevoUsuarioData.setCodigopostal(registroData.getCodigopostal());

                    // Validar y actualizar los datos del usuario en el servicio
                    usuarioService.actualizarUsuarioPorId(usuarioId, nuevoUsuarioData);

                    // Redirigir al perfil del usuario
                    return "redirect:/admin/usuarios";
                } else {
                    model.addAttribute("errorActualizar", "Ninguno de los campos puede estar vacio.");
                }

            } catch (UsuarioServiceException e) {
                model.addAttribute("errorActualizar", e.getMessage());
            }
        }

        model.addAttribute("registroData", registroData);

        UsuarioData user = usuarioService.findById(id);
        model.addAttribute("logueado", user);

        List<Usuario> usuarios = usuarioService.listadoCompleto();
        Usuario usuario = usuarioService.buscarUsuarioPorId(usuarios, usuarioId);

        model.addAttribute("usuario", usuario);

        return "editarUsuario";
    }

    @GetMapping("/admin/pedidos")
    public String administracionPedidos(Model model, HttpSession session) {
        Long id = managerUserSession.usuarioLogeado();

        comprobarAdmin(id);

        // Si está logueado, lo buscamos en la base de datos y lo añadimos al atributo "usuario"
        UsuarioData user = usuarioService.findById(id);
        // "usuario" lo usaremos en la vista html
        model.addAttribute("usuario", user);

        List<Pedido> pedidos = pedidoService.listadoCompleto();
        model.addAttribute("pedidos", pedidos);

        return "adminPedidos";
    }

    @PostMapping("/admin/pedidos/eliminar/{pedidoId}")
    public String administracionPedidosEliminar(@PathVariable(value = "pedidoId") Long pedidoId, Model model, HttpSession session) {
        Long id = managerUserSession.usuarioLogeado();

        comprobarAdmin(id);

        try {
            pedidoService.eliminarPedido(pedidoId);
        } catch(PedidoServiceException e) {
            throw new PedidoServiceException(e.getMessage());
        }

        UsuarioData user = usuarioService.findById(id);
        model.addAttribute("usuario", user);

        List<Pedido> pedidos = pedidoService.listadoCompleto();
        model.addAttribute("pedidos", pedidos);

        return "adminPedidos";
    }
}
