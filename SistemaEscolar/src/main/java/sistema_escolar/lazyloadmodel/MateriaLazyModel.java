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
import sistema_escolar.model.Materia;
import sistema_escolar.repository.MateriaRepository;

/**
 *
 * @author Vitor Freitas
 */
public class MateriaLazyModel extends LazyDataModel<Materia>
{
    private List<Materia> materias;
    
    @Inject
    private MateriaRepository materiaRep;
    
    @Override
    public List<Materia> load(int first, int pageSize, String sortField,
            SortOrder sortOrder, Map<String, Object> filters)
    {
        try
        {            
            materias = materiaRep.getAllLazyLoad(first, pageSize);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
         
        if(getRowCount() <= 0)
            setRowCount(materiaRep.getTotalCount());
        
        
        setPageSize(pageSize);
        
        return materias;
    }
}
