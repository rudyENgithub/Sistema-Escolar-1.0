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
import org.primefaces.model.LazyDataModel;
import sistema_escolar.lazyloadmodel.TurmaLazyModel;
import sistema_escolar.model.Aluno;
import sistema_escolar.model.Serie;
import sistema_escolar.model.Turma;
import sistema_escolar.repository.AlunoRepository;
import sistema_escolar.repository.SerieRepository;
import sistema_escolar.repository.TurmaRepository;
import sistema_escolar.util.FacesMessages;

/**
 *
 * @author Vitor Freitas
 */
@ManagedBean
@ViewScoped
public class TurmasMB implements Serializable
{
    private FacesMessages messages = new FacesMessages();
    
    private Turma turma;
    
    private TurmaRepository turmaRep;
    private AlunoRepository alunoRep;
    private SerieRepository serieRep;
    
    private List<Turma> turmas;
    private List<Serie> series;
    
    private Long selectedSerieId;
    
    private LazyDataModel<Turma> lazyTurmas;
    
    private boolean isEditable = false;
    
    @PostConstruct
    public void init()
    {
        turmaRep = new TurmaRepository();
        
        turma = new Turma();
        
        serieRep = new SerieRepository();
        
        setSeries(serieRep.getAll());
    }
    
    public void saveTurma()
    {
        String nome = turma.toString();
        if(isEditable)
        {
            try
            {
                turmaRep.merge(turma);

                messages.info("A turma " + nome 
                + " foi atualizada com sucesso!");
                
                newTurma();
            }
            catch (Exception e)
            {
                messages.error("Houve um erro ao atualizar a turma "+ nome + "!");
                e.printStackTrace();
                newTurma();
            }
        }
        else
        {
            try
            {
                turmaRep.save(turma);

                messages.info("A Turma " + nome + " foi adicionada com sucesso!");

                newTurma();
            }
            catch (Exception e)
            {
                messages.error("Houve um erro ao cadastrar a turma "+ nome + "!");
                e.printStackTrace();
                newTurma();
            }
        }
    }
    
    public void deleteTurma(Turma turma)
    {
        String nome = turma.toString();
        try
        {
            alunoRep = new AlunoRepository();
            
            List<Aluno> temp = turma.getAlunos();
            temp.forEach(s ->
            {
                s.setTurma(null);
                alunoRep.merge(s);
            });
            
            turma.setSerie(null);
            turmaRep.merge(turma);
            turmaRep.remove(turma);
            
            messages.info("A turma "+ nome + " foi excluida com sucesso!");
        } 
        catch (Exception e)
        {
            messages.error("Houve um erro ao excluir a turma "+ nome + "!");
            e.printStackTrace();
        }
    }
    
    public void selectTurma(Turma turma)
    {
        this.turma = turma;
        this.selectedSerieId = turma.getSerie().getId();
        setIsEditable(true);
    }
    
    public void newTurma()
    {
        turma = new Turma();
        selectedSerieId = null;
        setIsEditable(false);
    }
    
    public void onSerieSelect(final AjaxBehaviorEvent event)
    {
        getSeries().forEach(s -> 
        {
            if(Objects.equals(s.getId(), getSelectedSerieId()))
                turma.setSerie(s);
        });
    }
    
    /**
     * @return the lazyTurmas
     */
    public LazyDataModel<Turma> getLazyTurmas()
    {
        if(lazyTurmas == null)
            lazyTurmas = new TurmaLazyModel();
        
        return lazyTurmas;
    }

//    /**
//     * @return the turmas
//     */
//    public List<Turma> getTurmas() 
//    {
//        return turmaRep.getAll();
//    }

    /**
     * @return the turma
     */
    public Turma getTurma() 
    {
        return turma;
    }

    /**
     * @param turma the turma to set
     */
    public void setTurma(Turma turma) 
    {
        this.turma = turma;
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
     * @return the series
     */
    public List<Serie> getSeries()
    {
        return series;
    }

    /**
     * @param series the series to set
     */
    public void setSeries(List<Serie> series)
    {
        this.series = series;
    }

    /**
     * @return the selectedSerieId
     */
    public Long getSelectedSerieId()
    {
        return selectedSerieId;
    }

    /**
     * @param selectedSerieId the selectedSerieId to set
     */
    public void setSelectedSerieId(Long selectedSerieId)
    {
        this.selectedSerieId = selectedSerieId;
    }
}
