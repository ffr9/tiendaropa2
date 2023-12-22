package tiendaropa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import tiendaropa.authentication.ManagerUserSession;
import tiendaropa.controller.exception.UsuarioNoAutorizadoException;
import tiendaropa.controller.exception.UsuarioNoLogeadoException;
import tiendaropa.dto.UsuarioData;
import tiendaropa.service.UsuarioService;

import javax.servlet.http.HttpSession;

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
}
