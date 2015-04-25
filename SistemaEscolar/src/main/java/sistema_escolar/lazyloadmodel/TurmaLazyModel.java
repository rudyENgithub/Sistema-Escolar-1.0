/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sistema_escolar.lazyloadmodel;

import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import sistema_escolar.model.Turma;
import sistema_escolar.repository.TurmaRepository;

/**
 *
 * @author Vitor Freitas
 */
public class TurmaLazyModel extends LazyDataModel<Turma>
{
    private List<Turma> turmas;
    
    @Inject
    private TurmaRepository turmaRep;
    
    @Override
    public List<Turma> load(int first, int pageSize, String sortField,
            SortOrder sortOrder, Map<String, Object> filters)
    {
        try
        {            
            turmas = turmaRep.getAllLazyLoad(first, pageSize);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
         
        if(getRowCount() <= 0)
            setRowCount(turmaRep.getTotalCount());
        
        
        setPageSize(pageSize);
        
        return turmas;
    }
}
