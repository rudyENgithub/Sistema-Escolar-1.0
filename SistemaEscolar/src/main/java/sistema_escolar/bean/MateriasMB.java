/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sistema_escolar.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import sistema_escolar.lazyloadmodel.MateriaLazyModel;
import sistema_escolar.model.Materia;
import sistema_escolar.model.Professor;
import sistema_escolar.model.Serie;
import sistema_escolar.repository.MateriaRepository;
import sistema_escolar.repository.ProfessorRepository;
import sistema_escolar.repository.SerieRepository;
import sistema_escolar.util.FacesMessages;

/**
 *
 * @author Vitor Freitas
 */
@ManagedBean
@ViewScoped
public class MateriasMB implements Serializable
{
    private FacesMessages messages = new FacesMessages();
    
    private MateriaRepository matRep;
    
    private List<Materia> materias;
    
    private LazyDataModel<Materia> lazyMaterias;
    
    private Materia materia;
    
    private ProfessorRepository profRep;
    private List<Professor> professores;
    private Professor selectedProfessor;
    private SerieRepository serRep;
    private List<Serie> series;
    
    private Long serieId;
    
    private boolean isEditable = false;
    
    @PostConstruct
    public void init()
    {
        matRep = new MateriaRepository();      
        
        materia = new Materia();       
        
        serRep = new SerieRepository();
        series = serRep.getAll();
        
        
    }
    
    public void saveMateria()
    {
        if(isEditable)
        {
            try
            {
                matRep.merge(materia);

                messages.info("A Matéria " + materia.getNome()
                + " foi atualizada com sucesso!");
                
                newMateria();
            } 
            catch (Exception e)
            {
                messages.error("Ocorreu um erro ao atualizar a matéria!");
                e.printStackTrace();
                newMateria();
            }

        }
        else
        {
            try
            {
               matRep.save(materia);

                messages.info("A Matéria " + materia.getNome() 
                        + " foi adicionada com sucesso!");

                newMateria();
            } 
            catch (Exception e)
            {
                messages.error("Ocorreu um erro ao cadastrar a matéria!");
                e.printStackTrace();
                newMateria();
            }
        }
    }
    
    public void deleteMateria(Materia materia)
    {
        try
        {
            String nome = materia.getNome();
            matRep.remove(materia);
            
            messages.info("A matéria "+ nome + " foi excluida com sucesso!");
        } 
        catch (Exception e)
        {
            messages.error("Houve um erro ao excluir a matéria!");
            e.printStackTrace();
        }
    }
    
    public void newMateria()
    {
        materia = new Materia();
        selectedProfessor = new Professor();
        serieId = null;
        setIsEditable(false);
    }
    
    public void selectMateria(Materia materia)
    {
        this.materia = materia;
        selectedProfessor = materia.getProfessor();
        serieId = materia.getSerie().getId();
        setIsEditable(true);
    }
    
    public void selectProfessor(SelectEvent event)
    {
        selectedProfessor = (Professor) event.getObject();
        materia.setProfessor(selectedProfessor);
    }
    
    public void selectSerie(final AjaxBehaviorEvent event)
    {
        series.forEach(s ->
        {
            if(Objects.equals(s.getId(), serieId))
                materia.setSerie(s);
        }); 
    }
    
    /**
     * @return the lazyMaterias
     */
    public LazyDataModel<Materia> getLazyMaterias()
    {
        if(lazyMaterias == null)
            lazyMaterias = new MateriaLazyModel();
        
        return lazyMaterias;
    }

    /**
     * @return the materias
     */
    public List<Materia> getMaterias() {
        return materias;
    }

    /**
     * @return the materia
     */
    public Materia getMateria() {
        return materia;
    }

    /**
     * @param materia the materia to set
     */
    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    /**
     * @return the professores
     */
    public List<Professor> getProfessores() {
        return professores;
    }

    /**
     * @return the series
     */
    public List<Serie> getSeries() {
        return series;
    }

    /**
     * @return the selectedProfessor
     */
    public Professor getSelectedProfessor() {
        return selectedProfessor;
    }

    /**
     * @param selectedProfessor the selectedProfessor to set
     */
    public void setSelectedProfessor(Professor selectedProfessor) {
        this.selectedProfessor = selectedProfessor;
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

    /**
     * @return the serieId
     */
    public Long getSerieId()
    {
        return serieId;
    }

    /**
     * @param serieId the serieId to set
     */
    public void setSerieId(Long serieId)
    {
        this.serieId = serieId;
    }
}
