package br.com.baesa.regescweb.controllers;

import br.com.baesa.regescweb.dto.RequisicaoFormProfessor;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/professores")
public class ProfessorController {
    @Autowired
    private ProfessorRepository professorRepository;

//    public ProfessorController(ProfessorRepository professorRepository){
//        this.professorRepository = professorRepository;
//    }

    @GetMapping("")
    public ModelAndView index(){
        List<Professor> professores = this.professorRepository.findAll();
        ModelAndView mv = new ModelAndView("professores/index");
        mv.addObject("professores", professores);

        return mv;
    }

    @GetMapping("/new")
    public ModelAndView nnew(RequisicaoFormProfessor requisicao) {
        ModelAndView mv = new ModelAndView("professores/new");
        mv.addObject("listaStatusProfessor", StatusProfessor.values());
        return mv;
    }

    @PostMapping("")
    public ModelAndView create(@Valid RequisicaoFormProfessor requisicao, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            ModelAndView mv = new ModelAndView("professores/new");
            mv.addObject("listaStatusProfessor", StatusProfessor.values());
            return mv;
        }
        Professor professor = requisicao.toProfessor();
        this.professorRepository.save(professor);

        return new ModelAndView("redirect:/professores/" + professor.getId());
    }

    @GetMapping("/{id}")
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

    @GetMapping("/{id}/edit")
    public ModelAndView edit(@PathVariable Long id, RequisicaoFormProfessor requisicao){
        Optional<Professor> optional = this.professorRepository.findById(id);

        if (optional.isPresent() == false){
            return new ModelAndView("redirect:/professores");
        }

        Professor professor = optional.get();
        requisicao.fromProfessor(professor);

        ModelAndView mv = new ModelAndView("professores/edit");
        mv.addObject("professorId", id);
        mv.addObject("listaStatusProfessor", StatusProfessor.values());

        return mv;
    }

    @PostMapping("{id}")
    public ModelAndView update(@PathVariable Long id, @Valid RequisicaoFormProfessor requisicao, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            ModelAndView mv = new ModelAndView("professores/edit");
            mv.addObject("professorId", id);
            mv.addObject("listaStatusProfessor", StatusProfessor.values());
            return mv;
        }

        Optional<Professor> optional = this.professorRepository.findById(id);
        if (optional.isPresent() == false){
            return new ModelAndView("redirect:/professores");
        }

        Professor professor = requisicao.toProfessor(optional.get());
        this.professorRepository.save(professor);

        return new ModelAndView("redirect:/professores/" + professor.getId());
    }

    @GetMapping("/{id}/delete")
    public ModelAndView delete(@PathVariable Long id){
        ModelAndView mv = new ModelAndView("redirect:/professores");

        Optional<Professor> optional = this.professorRepository.findById(id);
        if (optional.isPresent() == false){
            mv.addObject("mensagem", "Cadastro não encontrado.");
            mv.addObject("erro", true);
            return mv;
        }

        this.professorRepository.deleteById(id);
        mv.addObject("mensagem", "Deletado com sucesso!");
        mv.addObject("erro", false);
        return mv;
    }
}
