/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sistema_escolar.converter;


import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import sistema_escolar.model.Serie;
import sistema_escolar.repository.SerieRepository;

/**
 *
 * @author Vitor Freitas
 */
@ManagedBean
@RequestScoped
public class SerieConverter implements Converter
{

    private SerieRepository serieRep;
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string)
    {
        serieRep = new SerieRepository();
        
        if (string == null || string.isEmpty()) {
            return null;
        }

        try {
            return serieRep.getById(Long.valueOf(string));
        } catch (NumberFormatException e) {
            throw new ConverterException(new FacesMessage(String.format("%s is not a valid User ID", string)));
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o)
    {     
        if (o == null) {
            return "";
        }

        if (o instanceof Serie) {
            return String.valueOf(((Serie) o).getId());
        } else {
            throw new ConverterException(new FacesMessage(String.format("%s is not a valid User", o)));
        }
    }
    
}
