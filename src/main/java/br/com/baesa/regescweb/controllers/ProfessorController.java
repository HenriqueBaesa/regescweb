package br.com.baesa.regescweb.controllers;

import br.com.baesa.regescweb.dto.RequisicaoNovoProfessor;
import br.com.baesa.regescweb.models.Professor;
import br.com.baesa.regescweb.models.StatusProfessor;
import br.com.baesa.regescweb.repositories.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ProfessorController {
    @Autowired
    private ProfessorRepository professorRepository;

//    public ProfessorController(ProfessorRepository professorRepository){
//        this.professorRepository = professorRepository;
//    }

    @GetMapping("/professores")
    public ModelAndView index(){
        List<Professor> professores = this.professorRepository.findAll();
        ModelAndView mv = new ModelAndView("professores/index");
        mv.addObject("professores", professores);

        return mv;
    }

    @GetMapping("/professores/new")
    public ModelAndView nnew() {
        ModelAndView mv = new ModelAndView("professores/new");
        mv.addObject("statusProfessor", StatusProfessor.values());
        return mv;
    }

    @PostMapping("/professores")
    public String create(RequisicaoNovoProfessor requisicao){
        Professor professor = requisicao.toProfessor();
        this.professorRepository.save(professor);

        return "redirect:/professores";
    }
}
