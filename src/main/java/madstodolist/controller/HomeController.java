package madstodolist.controller;

import madstodolist.authentication.ManagerUserSession;
import madstodolist.dto.LoginData;
import madstodolist.dto.UsuarioData;
import madstodolist.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {
    @Autowired
    UsuarioService usuarioService;

    @Autowired
    ManagerUserSession managerUserSession;

    @GetMapping("/")
    public String init(Model model) {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home(Model model, HttpSession session) {
        Long id = managerUserSession.usuarioLogeado();

        if(id != null){
            UsuarioData user = usuarioService.findById(id);
            model.addAttribute("usuario", user);
        }

        return "home";
    }
}
