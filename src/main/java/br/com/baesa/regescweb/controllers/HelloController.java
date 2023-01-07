package br.com.baesa.regescweb.controllers;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloController {

    @GetMapping("/hello")
    public ModelAndView hello(){
        ModelAndView mv = new ModelAndView("hello"); // nome do arquivo html a ser renderizado
        mv.addObject("nome", "Baesa");
        return mv; // renderiza a view templates/hello.html
    }

    @GetMapping("/hello-model")
    public String hello(Model model){
        model.addAttribute("nome","Luis");
        return "hello"; // renderiza a view templates/hello.html
    }

    @GetMapping("/hello-servlet")
    public String hello(HttpServletRequest request){
        request.setAttribute("nome","Henrique");
        return "hello"; // renderiza a view templates/hello.html
    }

}
