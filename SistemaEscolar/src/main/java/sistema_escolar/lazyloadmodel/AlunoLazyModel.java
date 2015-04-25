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
import sistema_escolar.model.Aluno;
import sistema_escolar.repository.AlunoRepository;

/**
 *
 * @author Vitor Freitas
 */
public class AlunoLazyModel extends LazyDataModel<Aluno>
{
    private List<Aluno> alunos;
    
    @Inject
    private AlunoRepository alunoRep;
    
    @Override
    public List<Aluno> load(int first, int pageSize, String sortField,
            SortOrder sortOrder, Map<String, Object> filters)
    {
        try
        {
            alunos = alunoRep.getAllLazyLoad(first, pageSize);
            
//            alunos = alunoRep.getAllFiltredSortedLazyLoad(
//                    new FilterSortLazyModel(first, pageSize, sortField, filters,
//                            SortOrder.ASCENDING.equals(sortOrder), true, "materias"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
         
        if(getRowCount() <= 0)
            setRowCount(alunoRep.getTotalCount());
        
        
        setPageSize(pageSize);
        
        return alunos;
    }
}
