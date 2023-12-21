package tiendaropa.controller;

import tiendaropa.authentication.ManagerUserSession;
import tiendaropa.dto.UsuarioData;
import tiendaropa.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductoController {

    @Autowired
    ManagerUserSession managerUserSession;

    @Autowired
    UsuarioService usuarioService;

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

        return "catalogo";
    }

}
