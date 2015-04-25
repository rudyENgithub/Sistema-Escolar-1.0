/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sistema_escolar.repository;

import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import sistema_escolar.model.Professor;
import sistema_escolar.qualifier.MyDatabase;

/**
 *
 * @author Vitor Freitas
 */
public class ProfessorRepository extends AbstractRepository<Long, Professor>
{
    @Inject @MyDatabase
    private EntityManager manager;

    public ProfessorRepository()
    {
        
    }

//    @Override
//    public List<Professor> getAll() 
//    {
//        return manager.createNamedQuery(Professor.GET_ALL)
//                .getResultList(); 
//    }
    
    @Override
    public List<Professor> getAllLazyLoad(int startingAt, int maxPerPage)
    {
        return (List<Professor>) manager.createNamedQuery(Professor.GET_ALL)
                .setFirstResult(startingAt)
                .setMaxResults(maxPerPage)
                .getResultList(); 
    }
}
