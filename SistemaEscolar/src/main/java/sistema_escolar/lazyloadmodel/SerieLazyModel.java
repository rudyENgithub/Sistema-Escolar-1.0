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
import sistema_escolar.model.Serie;
import sistema_escolar.repository.SerieRepository;

/**
 *
 * @author Vitor Freitas
 */
public class SerieLazyModel extends LazyDataModel<Serie>
{
    private List<Serie> series;
    
    @Inject
    private SerieRepository serieRep;
    
    @Override
    public List<Serie> load(int first, int pageSize, String sortField,
            SortOrder sortOrder, Map<String, Object> filters)
    {
        try
        {           
            series = serieRep.getAllLazyLoad(first, pageSize);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
         
        if(getRowCount() <= 0)
            setRowCount(serieRep.getTotalCount());
        
        
        setPageSize(pageSize);
        
        return series;
    }
}
