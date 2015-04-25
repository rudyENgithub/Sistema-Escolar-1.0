/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sistema_escolar.repository;

import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import sistema_escolar.qualifier.MyDatabase;
import sistema_escolar.model.Aluno;
import sistema_escolar.model.Serie;

/**
 *
 * @author Vitor Freitas
 */
public class AlunoRepository extends AbstractRepository<Long, Aluno>
{
    @Inject @MyDatabase
    private EntityManager manager;
    
    @Override
    public List<Aluno> getAll() 
    {
        return manager.createNamedQuery(Aluno.GET_ALL).getResultList(); 
    }
    
    @Override
    public List<Aluno> getAllLazyLoad(int startingAt, int maxPerPage)
    {
        return (List<Aluno>) manager.createNamedQuery(Aluno.GET_ALL)
                .setFirstResult(startingAt)
                .setMaxResults(maxPerPage)
                .getResultList(); 
    }
    
    public List<Aluno> getAllBySerieWithoutTurma(Serie serie)
    {
        try 
        {
            return getByRestriction(Aluno.GET_ALL_BY_SERIE_WITHOUT_TURMA, "serie"
                , serie);
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            return null;
        }
        
    }
}
