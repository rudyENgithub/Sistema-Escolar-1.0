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
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.LazyDataModel;
import sistema_escolar.lazyloadmodel.AlunoLazyModel;
import sistema_escolar.model.Aluno;
import sistema_escolar.model.Endereco;
import sistema_escolar.model.Serie;
import sistema_escolar.qualifier.LazyModel;
import sistema_escolar.repository.AlunoRepository;
import sistema_escolar.repository.EnderecoRepository;
import sistema_escolar.repository.SerieRepository;
import sistema_escolar.util.FacesMessages;

/**
 *
 * @author Vitor Freitas
 */
@Named
@ViewScoped
public class AlunosMB implements Serializable
{
    @Inject
    private FacesMessages messages;
    
    @Inject
    private AlunoRepository alunoRep;
    @Inject
    private EnderecoRepository endRep;
    @Inject
    private SerieRepository serieRep;

    private Aluno aluno;
    private Endereco endereco;
    
    private List<Aluno> alunos;
    private List<Serie> series;
    
    @Inject @LazyModel(LazyModel.Type.ALUNO)
    private LazyDataModel<Aluno> lazyAlunos;
    
    private Long selectedSerieId;
    
    private boolean isEditable = false;
    
    @PostConstruct
    public void init()
    {    
        aluno = new Aluno();
        endereco = new Endereco();
        
        series = serieRep.getAll();
    }
    
    public void saveAluno()
    {
        String nome = aluno.toString();
        if(isEditable)
        {
            try
            {
                endRep.merge(endereco);
                alunoRep.merge(aluno);

                messages.info("O Aluno " + nome 
                + " foi atualizado com sucesso!");
                
                newAluno();
            }
            catch (Exception e)
            {
                messages.error("Houve um erro ao atualizar o aluno "+ nome + "!");
                e.printStackTrace();
                newAluno();
            }
        }
        else
        {
            try
            {
                endRep.save(endereco);
                aluno.setEndereco(endereco);

                alunoRep.save(aluno);

                messages.info("Aluno " + nome + " foi adicionado com sucesso!");

                newAluno();
            }
            catch (Exception e)
            {
                messages.error("Houve um erro ao cadastrar o aluno "+ nome + "!");
                e.printStackTrace();
                newAluno();
            }
        }
        lazyAlunos = new AlunoLazyModel();
    }
    
    public void deleteAluno(Aluno aluno)
    {
        String nome = aluno.toString();
        try
        {
            alunoRep.remove(aluno);
            
            messages.info("O Aluno "+ nome + " foi excluido com sucesso!");
            lazyAlunos = new AlunoLazyModel();
        }
        catch (Exception e)
        {
            messages.error("Houve um erro ao excluir o aluno "+ nome + "!");
            e.printStackTrace();
        }
    }

    public void newAluno()
    {
        aluno = new Aluno();
        endereco = new Endereco();
        selectedSerieId = null;
        setIsEditable(false);
    }
    
    public void selectAluno(Aluno aluno)
    {
        this.aluno = aluno;
        this.endereco = aluno.getEndereco();
        this.selectedSerieId = aluno.getSerie().getId();
        setIsEditable(true);
    }
    
    public void onSerieSelect(final AjaxBehaviorEvent event)
    {
        series.forEach(s -> 
        {
            if(Objects.equals(s.getId(), selectedSerieId))
                aluno.setSerie(s);
        });
    }
    
    /**
     * @return the lazyAlunos
     */
    public LazyDataModel<Aluno> getLazyAlunos()
    {
        if(lazyAlunos == null)
            lazyAlunos = new AlunoLazyModel();
        
        return lazyAlunos;
    }

//    /**
//     * @return the alunos
//     */
//    public List<Aluno> getAlunos() 
//    {
//        return alunos;
//    }

    /**
     * @return the aluno
     */
    public Aluno getAluno() 
    {
        return aluno;
    }

    /**
     * @param aluno the aluno to set
     */
    public void setAluno(Aluno aluno) 
    {
        this.aluno = aluno;
    }

    /**
     * @return the endereco
     */
    public Endereco getEndereco() 
    {
        return endereco;
    }

    /**
     * @param endereco the endereco to set
     */
    public void setEndereco(Endereco endereco) 
    {
        this.endereco = endereco;
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
}
