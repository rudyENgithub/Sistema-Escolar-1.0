/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sistema_escolar.bean;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.LazyDataModel;
import sistema_escolar.annotation.Transactional;
import sistema_escolar.model.Endereco;
import sistema_escolar.model.Materia;
import sistema_escolar.model.Professor;
import sistema_escolar.repository.EnderecoRepository;
import sistema_escolar.repository.MateriaRepository;
import sistema_escolar.repository.ProfessorRepository;
import sistema_escolar.util.FacesMessages;


/**
 *
 * @author Vitor Freitas
 */
@Named
@ViewScoped
public class ProfessoresMB implements Serializable
{   
    @Inject
    private FacesMessages messages;
    
    
    private Professor professor;
    private Endereco profEndereco;
    
    @Inject
    private ProfessorRepository profRep;
    @Inject
    private EnderecoRepository endRep;
    @Inject
    private MateriaRepository materiaRep;
    
    private List<Professor> professores;
    
//    @Inject @LazyModel(LazyModel.Type.PROFESSOR)
    @Inject 
    private LazyDataModel<Professor> lazyProfessores;
    
    private boolean isEditable = false;
    
    
    @PostConstruct
    public void init()
    {
        professor = new Professor();
        profEndereco = new Endereco();
    }          
    
    @Transactional
    public void salvar()
    {
        if(isEditable)
        {
            try
            {
                endRep.merge(profEndereco);
                profRep.merge(professor);

                messages.info("Professor " + professor.toString() 
                + " foi atualizado com sucesso!");
                
                newProfessor();
            } 
            catch (Exception e)
            {
                messages.error("Ocorreu um erro ao atualizar o professor!");
                e.printStackTrace();
                newProfessor();
            }

        }
        else
        {
            try
            {
                endRep.save(profEndereco);
                professor.setEndereco(profEndereco);

                profRep.save(professor);

                messages.info("Professor " + professor.toString() 
                        + " foi adicionado com sucesso!");

                newProfessor();
            } 
            catch (Exception e)
            {
                messages.error("Ocorreu um erro ao cadastrar o professor!");
                e.printStackTrace();
                newProfessor();
            }
        }
    }
    
    @Transactional
    public void deletar(Professor professor)
    {
        try
        {
            String nome = professor.toString();
            if(professor.getMaterias() != null)
            {
                List<Materia> mats = professor.getMaterias();
                mats.forEach(s ->
                {
                    s.setProfessor(null);
                    materiaRep.merge(s);
                });
                
            }
           
            profRep.remove(professor);
            
            messages.info("O Professor "+ nome + " foi excluido com sucesso!");
        } 
        catch (Exception e)
        {
            messages.error("Houve um erro ao excluir o professor!");
            e.printStackTrace();
        }
    }
    
    public void newProfessor()
    {
        professor = new Professor();
        profEndereco = new Endereco();
        setIsEditable(false);
    }
    
    public void selecionaProfessor(Professor professor)
    {
        this.professor = professor;
        this.profEndereco = professor.getEndereco();
        setIsEditable(true);
    }

    /**
     * @return the lazyProfessores
     */
    public LazyDataModel<Professor> getLazyProfessores()
    { 
        return lazyProfessores;
    }

    /**
     * @return the professor
     */
    public Professor getProfessor()
    {
        return professor;
    }

    /**
     * @param professor the professor to set
     */
    public void setProfessor(Professor professor)
    {
        this.professor = professor;
    }

    /**
     * @return the profEndereco
     */
    public Endereco getProfEndereco()
    {
        return profEndereco;
    }

    /**
     * @param profEndereco the profEndereco to set
     */
    public void setProfEndereco(Endereco profEndereco)
    {
        this.profEndereco = profEndereco;
    }

    /**
     * @return the isEditable
     */
    public boolean isIsEditable()
    {
        return isEditable;
    }

    /**
     * @param isEditable the isEditable to set
     */
    public void setIsEditable(boolean isEditable)
    {
        this.isEditable = isEditable;
    }
}
