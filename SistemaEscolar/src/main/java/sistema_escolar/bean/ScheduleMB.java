/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sistema_escolar.bean;

import java.io.Serializable;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import sistema_escolar.model.Evento;
import sistema_escolar.repository.EventoRepository;

/**
 *
 * @author Vitor Freitas
 */
@Named
@ViewScoped
public class ScheduleMB implements Serializable
{
    @Inject
    private EventoRepository eventoRep;
    
    private ScheduleModel model;
    private ScheduleEvent event;
    
    private Evento evento;

    @PostConstruct
    public void init()
    {
        evento = new Evento();
        model = new DefaultScheduleModel();
        model.addEvent(new DefaultScheduleEvent("Ola", new Date(), new Date()));
    }
    
    public void saveEvento()
    {
        
        try
        {
            System.out.println(evento.getTitulo());
            System.out.println(evento.getDataInicial());
            System.out.println(evento.getDataFinal());
            eventoRep.save(evento);
           
//            event = new DefaultScheduleEvent(evento.getTitulo(), evento.getDataInicial()
//                    , evento.getDataFinal(), evento.isAllDay());
            
//            model.addEvent(event);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    
    public void addEvent(ActionEvent actionEvent) 
    {
        if(event.getId() == null)
            model.addEvent(event);
        else
            model.updateEvent(event);
         
        event = new DefaultScheduleEvent();
    }
     
    public void onEventSelect(SelectEvent selectEvent) 
    {
        
    }
     
    public void onDateSelect(SelectEvent selectEvent) 
    {
        evento = new Evento();
        evento.setDataInicial((Date) selectEvent.getObject());
        System.out.println("DateSelect");
    }
     
    public void onEventMove(ScheduleEntryMoveEvent event) 
    {
        
    }
     
    public void onEventResize(ScheduleEntryResizeEvent event) 
    {
        
      
    }
    
    /**
     * @return the model
     */
    public ScheduleModel getModel()
    {
        return model;
    }

    /**
     * @return the evento
     */
    public Evento getEvento()
    {
        return evento;
    }

    /**
     * @param evento the evento to set
     */
    public void setEvento(Evento evento)
    {
        this.evento = evento;
    }
}
