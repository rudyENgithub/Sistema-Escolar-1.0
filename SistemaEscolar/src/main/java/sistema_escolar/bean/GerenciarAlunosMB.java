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
import org.primefaces.event.SelectEvent;
import sistema_escolar.model.Aluno;
import sistema_escolar.model.Turma;
import sistema_escolar.repository.AlunoRepository;
import sistema_escolar.repository.TurmaRepository;
import sistema_escolar.util.FacesMessages;

/**
 *
 * @author Vitor Freitas
 */
@ManagedBean
@ViewScoped
public class GerenciarAlunosMB implements Serializable
{
    private FacesMessages messages = new FacesMessages();
    
    private TurmaRepository turmaRep;
    private AlunoRepository alunoRep;
    
    private Turma turma;
    
    private List<Aluno> turmaAlunos;
    
    
    @PostConstruct
    public void init()
    {
        turmaRep = new TurmaRepository();
        alunoRep = new AlunoRepository();
    }
    
    
    public void selectTurma(Turma turma)
    {
        this.setTurma(turma);
        this.turmaAlunos = turma.getAlunos();
    }

    public void removeFromTurma(Aluno aluno)
    {
        turmaAlunos.remove(aluno);
        try
        {
            aluno.setTurma(null);
            alunoRep.merge(aluno);
            messages.error("Aluno removido da Turma " + turma.getNome() + " com "
                    + "sucesso!");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            messages.error("Houve um erro ao excluir o aluno ");
        }
    }
    
    public void getAddedAlunos(SelectEvent event)
    {
        List<Aluno> addedAlunos = (List<Aluno>) event.getObject();
        
        if(addedAlunos == null || addedAlunos.isEmpty())
            return;
        
        try
        {
            addedAlunos.forEach(s ->
            {
                turmaAlunos.add(s);
                s.setTurma(turma);
                alunoRep.merge(s); 
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * @return the turma
     */
    public Turma getTurma()
    {
        return turma;
    }

    /**
     * @param turma the turma to set
     */
    public void setTurma(Turma turma)
    {
        this.turma = turma;
    }

    /**
     * @return the turmaAlunos
     */
    public List<Aluno> getTurmaAlunos()
    {
        return turmaAlunos;
    }

    /**
     * @param turmaAlunos the turmaAlunos to set
     */
    public void setTurmaAlunos(List<Aluno> turmaAlunos)
    {
        this.turmaAlunos = turmaAlunos;
    }
}
