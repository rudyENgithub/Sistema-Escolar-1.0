/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sistema_escolar.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;
import sistema_escolar.model.Professor;
import sistema_escolar.repository.ProfessorRepository;

/**
 *
 * @author Vitor Freitas
 */
@ManagedBean
@ViewScoped
public class SearchProfessorMB implements Serializable
{

    private ProfessorRepository profRep;
    
    private List<Professor> professores;
    
    private String matchingText;
    
    @PostConstruct
    public void init()
    {
        profRep = new ProfessorRepository();
    }
    
    public void search()
    {
        if(getMatchingText() == null)
            professores = profRep.getAll();
        else
            professores = profRep.getMatchingValuesLucene(getMatchingText(), "nome",
                "sobrenome", "formacao");    
    }
    
    public void createDialog()
    {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("contentHeight", 550);
        opcoes.put("contentWidth", 700);
        
        getContext().openDialog("searchProfessor", opcoes, null);
    }
    
    public void selectProfessor(Professor professor)
    {
        getContext().closeDialog(professor);
    }
    
    public void cancel()
    {
        getContext().closeDialog(null);
    }
    
    public RequestContext getContext()
    {
        return RequestContext.getCurrentInstance();
    }

    /**
     * @return the professores
     */
    public List<Professor> getProfessores()
    {
        return professores;
    }

    /**
     * @return the matchingText
     */
    public String getMatchingText()
    {
        return matchingText;
    }

    /**
     * @param matchingText the matchingText to set
     */
    public void setMatchingText(String matchingText)
    {
        this.matchingText = matchingText;
    }
    
}
