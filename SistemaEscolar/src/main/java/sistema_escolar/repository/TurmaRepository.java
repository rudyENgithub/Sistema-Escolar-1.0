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
import sistema_escolar.model.Turma;

/**
 *
 * @author Vitor Freitas
 */
public class TurmaRepository extends AbstractRepository<Long, Turma>
{
    @Inject @MyDatabase
    private EntityManager manager;
    
    @Override
    public List<Turma> getAll() 
    {
        return manager.createNamedQuery(Turma.GET_ALL).getResultList(); 
    }
    
    @Override
    public List<Turma> getAllLazyLoad(int startingAt, int maxPerPage)
    {
        return (List<Turma>) manager.createNamedQuery(Turma.GET_ALL)
                .setFirstResult(startingAt)
                .setMaxResults(maxPerPage)
                .getResultList(); 
    }
}
