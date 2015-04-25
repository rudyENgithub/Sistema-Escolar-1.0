/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sistema_escolar.bean;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.FlowEvent;

/**
 *
 * @author Vitor Freitas
 */
@ManagedBean
@ViewScoped
public class WizardMB implements Serializable
{
    private boolean skip;
    
    public String onFlowProcess(FlowEvent event) 
    {
        if(skip) 
        {
            skip = false;   //reset in case user goes back
            return "confirm";
        }
        else 
        {
            return event.getNewStep();
        }
    } 
    
    public boolean isSkip() 
    {
        return skip;
    }
 
    public void setSkip(boolean skip) 
    {
        this.skip = skip;
    }
}
