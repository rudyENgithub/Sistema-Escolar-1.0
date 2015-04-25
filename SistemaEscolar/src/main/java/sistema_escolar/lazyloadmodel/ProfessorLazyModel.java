/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sistema_escolar.lazyloadmodel;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import sistema_escolar.model.Professor;
import sistema_escolar.repository.ProfessorRepository;

/**
 *
 * @author Vitor Freitas
 */
public class ProfessorLazyModel extends LazyDataModel<Professor> 
implements Serializable
{

    private List<Professor> professores;
    
    @Inject
    private ProfessorRepository profRep;

    @Override
    public List<Professor> load(int first, int pageSize, String sortField,
            SortOrder sortOrder, Map<String, Object> filters)
    {
        try
        {
            
            professores = profRep.getAllLazyLoad(first, pageSize);
            
//            professores = profRep.getAllFiltredSortedLazyLoad(
//                    new FilterSortLazyModel(first, pageSize, sortField, filters,
//                            SortOrder.ASCENDING.equals(sortOrder), true, "materias"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
         
        if(getRowCount() <= 0)
            setRowCount(profRep.getTotalCount());
        
        
        setPageSize(pageSize);
        
        return professores;
    }  
}
