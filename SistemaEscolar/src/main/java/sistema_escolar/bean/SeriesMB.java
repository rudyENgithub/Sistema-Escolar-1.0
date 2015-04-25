/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sistema_escolar.bean;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.LazyDataModel;
import sistema_escolar.lazyloadmodel.SerieLazyModel;
import sistema_escolar.model.Aluno;
import sistema_escolar.model.Serie;
import sistema_escolar.repository.AlunoRepository;
import sistema_escolar.repository.SerieRepository;
import sistema_escolar.util.FacesMessages;

/**
 *
 * @author Vitor Freitas
 */
@ManagedBean
@ViewScoped
public class SeriesMB implements Serializable
{
    private FacesMessages messages = new FacesMessages();
    
    private Serie serie;
    
    private SerieRepository serieRep;
    private AlunoRepository alunoRep;
    
    private LazyDataModel<Serie> lazySeries;
    
    private boolean isEditable = false;
    
    @PostConstruct
    public void init()
    {
        serieRep = new SerieRepository();
        
        setSerie(new Serie());
    }
    
    public void saveSerie()
    {
        if(isEditable)
        {
            try
            {
                serieRep.merge(serie);

                messages.info("A Série " + serie.getNome()
                + " foi atualizada com sucesso!");
                
                newSerie();
            } 
            catch (Exception e)
            {
                messages.error("Ocorreu um erro ao atualizar a série!");
                e.printStackTrace();
                newSerie();
            }

        }
        else
        {
            try
            {
                serieRep.save(serie);

                messages.info("A Série " + serie.getNome()
                        + " foi adicionada com sucesso!");

                newSerie();
            } 
            catch (Exception e)
            {
                messages.error("Ocorreu um erro ao cadastrar a série!");
                e.printStackTrace();
                newSerie();
            }
        }
    }
    
    public void deleteSerie(Serie serie)
    {
        if(!serie.getTurmas().isEmpty())
        {
            messages.error("Você não pode excluir uma Série que possua Turmas"
                    + " com Alunos! Exclua as Turmas primeiro!");
            return;
        }
        
        try
        {
            alunoRep = new AlunoRepository();

            if(!serie.getAlunos().isEmpty())
            {
                List<Aluno> temp = serie.getAlunos();
                temp.forEach(s -> 
                {
                    s.setSerie(null);
                    alunoRep.merge(s);
                });
            }
            
            String nome = serie.getNome();
            serieRep.remove(serie);
            
            messages.info("A Série "+ nome + " foi excluida com sucesso!");
        } 
        catch (Exception e)
        {
            messages.error("Houve um erro ao excluir a série!");
            e.printStackTrace(); 
        }
    }
    
    public void newSerie()
    {
        serie = new Serie();
        setIsEditable(false);
    }
    
    public void selectSerie(Serie serie)
    {
        this.serie = serie;
        setIsEditable(true);
//        Hibernate.initialize(serie.getMaterias());
//        Hibernate.initialize(serie.getTurmas());
    }
    
    /**
     * @return the lazySeries
     */
    public LazyDataModel<Serie> getLazySeries()
    {
        if(lazySeries == null)
            lazySeries = new SerieLazyModel();
        
        return lazySeries;
    }

    /**
     * @return the serie
     */
    public Serie getSerie()
    {
        return serie;
    }

    /**
     * @param serie the serie to set
     */
    public void setSerie(Serie serie)
    {
        this.serie = serie;
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
