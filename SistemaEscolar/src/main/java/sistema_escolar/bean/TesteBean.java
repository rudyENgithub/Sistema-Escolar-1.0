/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema_escolar.bean;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;

/**
 *
 * @author Aluno
 */
@ManagedBean
@ViewScoped
public class TesteBean 
{
    private String choosen;
    private List teste;
    private List children;
    
    @PostConstruct
    public void init()
    {
        teste = new ArrayList<>();
        teste.add("Value 1");
        teste.add("Value 2");    
    }
    
    /* Pesquisa no banco, whatever;;; */
    public void onChange(AjaxBehaviorEvent event)
    {
        if(choosen.equalsIgnoreCase("Value 1"))
        {
            children = new ArrayList<>();
            children.add("Ref 1 - Value 1");
            children.add("Ref 2 - Value 1");
        }
        else if(choosen.equalsIgnoreCase("Value 2"))
        {
            children = new ArrayList<>();
            children.add("Ref 3 - Value 2");
            children.add("Ref 4 - Value 2"); 
        }
    }

    /**
     * @return the choosen
     */
    public String getChoosen() {
        return choosen;
    }

    /**
     * @param choosen the choosen to set
     */
    public void setChoosen(String choosen) {
        this.choosen = choosen;
    }

    /**
     * @return the teste
     */
    public List getTeste() {
        return teste;
    }

    /**
     * @param teste the teste to set
     */
    public void setTeste(List teste) {
        this.teste = teste;
    }

    /**
     * @return the children
     */
    public List getChildren() {
        return children;
    }

    /**
     * @param children the children to set
     */
    public void setChildren(List children) {
        this.children = children;
    }
    
}
