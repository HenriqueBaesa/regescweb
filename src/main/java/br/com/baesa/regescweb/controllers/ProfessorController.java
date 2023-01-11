package br.com.baesa.regescweb.controllers;

import br.com.baesa.regescweb.dto.RequisicaoNovoProfessor;
import br.com.baesa.regescweb.models.Professor;
import br.com.baesa.regescweb.models.StatusProfessor;
import br.com.baesa.regescweb.repositories.ProfessorRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

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
    public ModelAndView nnew(RequisicaoNovoProfessor requisicao) {
        ModelAndView mv = new ModelAndView("professores/new");
        mv.addObject("listaStatusProfessor", StatusProfessor.values());
        return mv;
    }

    @PostMapping("/professores")
    public ModelAndView create(@Valid RequisicaoNovoProfessor requisicao, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            ModelAndView mv = new ModelAndView("professores/new");
            mv.addObject("listaStatusProfessor", StatusProfessor.values());
            return mv;
        }
        Professor professor = requisicao.toProfessor();
        this.professorRepository.save(professor);

        return new ModelAndView("redirect:/professores/" + professor.getId());
    }

    @GetMapping("/professores/{id}")
    public ModelAndView show(@PathVariable Long id){
        Optional<Professor> optional = this.professorRepository.findById(id);

        if (optional.isPresent() == false){
            return new ModelAndView("redirect:/professores");
        }

        Professor professor = optional.get();

        ModelAndView mv = new ModelAndView("professores/show");
        mv.addObject("professor", professor);

        return mv;
    }
}
