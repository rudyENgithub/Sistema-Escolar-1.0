/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sistema_escolar.bean;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import sistema_escolar.model.Serie;
import sistema_escolar.repository.SerieRepository;

/**
 *
 * @author Vitor Freitas
 */
@Named(value = "appMB")
@ApplicationScoped
public class ApplicationMB
{
    @Inject
    private SerieRepository rep;
    
    @PostConstruct
    public void init()
    {
        Serie serie = rep.getById(3L);
    }
    
    public void indexar()
    {
        rep.initLucene();
    }
    
    public String welcome()
    {
        return "Bem Vindo ao Sistema Escolar 1.0!";
    }
}
