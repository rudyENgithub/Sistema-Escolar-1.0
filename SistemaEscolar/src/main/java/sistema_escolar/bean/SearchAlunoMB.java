/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sistema_escolar.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import sistema_escolar.model.Aluno;
import sistema_escolar.model.Serie;
import sistema_escolar.repository.AlunoRepository;
import sistema_escolar.repository.SerieRepository;

/**
 *
 * @author Vitor Freitas
 */
@ManagedBean
@ViewScoped
public class SearchAlunoMB implements Serializable
{
    private AlunoRepository alunoRep;
    private SerieRepository serieRep;
    
    private Serie filtredSerie;
    
    private String filtredSerieId;
    
    private List<Aluno> alunos;
    
    private List<Aluno> selectedAlunos;
    private Map<Long, Boolean> checkedAlunos = new HashMap<>();
    
    private String matchingText;
    
    @PostConstruct
    public void init()
    {
        alunoRep = new AlunoRepository();
        
        getSerie();
    }
    
    public void search()
    {
        if(matchingText == null)
            alunos = alunoRep.getAllBySerieWithoutTurma(filtredSerie);
        else
            alunos = alunoRep.getMatchingValuesLucene(matchingText, "nome",
                "sobrenome", "rg");       
    }
    
    public void getSerie()
    {
        Map<String,String> params = FacesContext.getCurrentInstance()
                .getExternalContext().getRequestParameterMap();
        String id = params.get("serie");
        
        if(id != null)
        {
            serieRep = new SerieRepository();
            filtredSerie = serieRep.getById(Long.valueOf(id));
        }  
    }
    
    public void createDialog(Serie serie)
    {     
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("contentHeight", 500);
        opcoes.put("contentWidth", 700);
        
        List<String> values = new ArrayList<>();
        values.add(serie.getId().toString());
        
        Map<String, List<String>> params = new HashMap<>();
        params.put("serie", values);

        getContext().openDialog("searchAluno", opcoes, params);
    }
    
    public void sendSelectedAlunos()
    {   
        if(alunos == null || checkedAlunos.isEmpty())
            return;
        selectedAlunos = new ArrayList<>();
        
        alunos.forEach(s -> 
        {
            if(checkedAlunos.get(s.getId()))
                selectedAlunos.add(s);
        });
        
        getContext().closeDialog(selectedAlunos);
    }
    
    public void cleanSelectedAlunos()
    {
        checkedAlunos = new HashMap<>();
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
     * @return the alunos
     */
    public List<Aluno> getAlunos()
    {
        return alunos;
    }

    /**
     * @return the checkedAlunos
     */
    public Map<Long, Boolean> getCheckedAlunos()
    {
        return checkedAlunos;
    }

    /**
     * @param checkedAlunos the checkedAlunos to set
     */
    public void setCheckedAlunos(Map<Long, Boolean> checkedAlunos)
    {
        this.checkedAlunos = checkedAlunos;
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

    /**
     * @return the filtredSerieId
     */
    public String getFiltredSerieId()
    {
        return filtredSerieId;
    }

    /**
     * @param filtredSerieId the filtredSerieId to set
     */
    public void setFiltredSerieId(String filtredSerieId)
    {
        this.filtredSerieId = filtredSerieId;
    }

    /**
     * @return the filtredSerie
     */
    public Serie getFiltredSerie()
    {
        return filtredSerie;
    }

    /**
     * @param filtredSerie the filtredSerie to set
     */
    public void setFiltredSerie(Serie filtredSerie)
    {
        this.filtredSerie = filtredSerie;
    }
}
