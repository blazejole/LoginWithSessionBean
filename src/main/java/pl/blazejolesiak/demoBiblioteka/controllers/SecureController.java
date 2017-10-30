package pl.blazejolesiak.demoBiblioteka.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.blazejolesiak.demoBiblioteka.models.UserInfo;
import pl.blazejolesiak.demoBiblioteka.models.UserModel;
import pl.blazejolesiak.demoBiblioteka.models.repositories.IUserRepository;

import java.util.Optional;

@Controller
public class SecureController {


    @Autowired
    UserInfo userInfo;

    @Autowired
    IUserRepository iUserRepository;


    @GetMapping("/checkuser")
    @ResponseBody
    public String checkUser(){
            return "Czy user jest zalogowany? "+ userInfo.isLogged();
    }

    @GetMapping("/")
    public String main(){
        if(userInfo.isLogged()){
            return "index";
        }
        return "login";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @PostMapping("/login")
    public String postLoginPage (@RequestParam("username")String username,
                                 @RequestParam("password") String password,
                                 Model model){
        if(userInfo.isLogged()){
            return "index";
        }
        Optional<UserModel> user = iUserRepository.findByUsername(username);
        if(!user.isPresent()){
            model.addAttribute("info", "Bledny login lub haslo");
            return "login";
        }
        if(! password.equals(user.get().getPassword())){
            model.addAttribute("info", "Bledny login lub haslo");
            return "login";
        }
        userInfo.setLogged(true);
        userInfo.setUserModel(user.get());
        return "index";
    }

    @GetMapping("/logout")
    @ResponseBody
    public String logout(){
        userInfo.setLogged(false);
        return "<center><br>WYLOGOWANO!</br></center>";
    }

    @GetMapping("/admin")
    public String adminPage(Model model){
        if(userInfo.getUserModel().getRole().equals("ADMIN")){
            return "adminview";
        }
        model.addAttribute("admin", "Nie masz dostepu do tej strony, musisz byc adminem");
       return "index";
    }

    @GetMapping("/403")
    @ResponseBody
    public String error403(){
        return "<center><h1>Nie masz odstepu do tej strony</h1></center>";
    }

}
