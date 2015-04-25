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
import javax.faces.event.AjaxBehaviorEvent;
import org.hibernate.Hibernate;
import sistema_escolar.model.Serie;
import sistema_escolar.repository.SerieRepository;
import sistema_escolar.repository.TurmaRepository;
import sistema_escolar.util.FacesMessages;

/**
 *
 * @author Vitor Freitas
 */
@ManagedBean
@ViewScoped
public class TurmasAlunosMB implements Serializable
{
    private FacesMessages messages = new FacesMessages();
    
    private List<Serie> series;
    private Long selectedSerieId;
    private Serie selectedSerie;
    
    
    private SerieRepository serieRep;
    private TurmaRepository turmaRep;
    
    
    @PostConstruct
    public void init()
    {
        serieRep = new SerieRepository();
        turmaRep = new TurmaRepository();
        
        series = serieRep.getAll();
    }
    
    public void onSerieChange(final AjaxBehaviorEvent event)    
    {        
        series.forEach(s ->
        {
            if(s.getId().compareTo(selectedSerieId) == 0)
                selectedSerie = s; 
        }); 
        
        Hibernate.initialize(selectedSerie.getTurmas());
    }
    
    /**
     * @return the series
     */
    public List<Serie> getSeries()
    {
        return series;
    }

    /**
     * @param selectedSerieId the selectedSerieId to set
     */
    public void setSelectedSerieId(Long selectedSerieId)
    {
        this.selectedSerieId = selectedSerieId;
    }

    /**
     * @return the selectedSerie
     */
    public Serie getSelectedSerie()
    {
        return selectedSerie;
    }

    /**
     * @return the selectedSerieId
     */
    public Long getSelectedSerieId()
    {
        return selectedSerieId;
    }

    /**
     * @param series the series to set
     */
    public void setSeries(List<Serie> series)
    {
        this.series = series;
    }

    /**
     * @param selectedSerie the selectedSerie to set
     */
    public void setSelectedSerie(Serie selectedSerie)
    {
        this.selectedSerie = selectedSerie;
    }
}
