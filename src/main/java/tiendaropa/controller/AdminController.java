package tiendaropa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import tiendaropa.authentication.ManagerUserSession;
import tiendaropa.controller.exception.UsuarioNoAutorizadoException;
import tiendaropa.controller.exception.UsuarioNoLogeadoException;
import tiendaropa.dto.RegistroData;
import tiendaropa.dto.UsuarioData;
import tiendaropa.model.Usuario;
import tiendaropa.service.UsuarioService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
public class AdminController {
    @Autowired
    private ManagerUserSession managerUserSession;

    @Autowired
    UsuarioService usuarioService;

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
    public String administracionUsuarioscrear(Model model, HttpSession session) {
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
    public String administracionUsuarioscrearSubmit(@Valid RegistroData registroData, BindingResult result, Model model) {
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
}
