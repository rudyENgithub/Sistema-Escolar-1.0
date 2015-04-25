/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sistema_escolar.util;

import java.io.Serializable;
import java.util.Map;

/**
 *
 * @author Vitor Freitas
 */
public class FilterSortLazyModel implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private int startingAt;
    
    private int maxPerPage;
    
    private String sortField;
    
    private Map<String,Object> filters;
    
    private boolean asc;
    
    private boolean distinct;
    
    private String[] joinBags;
    
    public FilterSortLazyModel() {}

    public FilterSortLazyModel(int startingAt, int maxPerPage, String sortField, 
            Map<String, Object> filters, boolean asc, boolean distinct, String... bags)
    {
        this.startingAt = startingAt;
        this.maxPerPage = maxPerPage;
        this.sortField = sortField;
        this.filters = filters;
        this.asc = asc;
        this.distinct = distinct;
        this.joinBags = bags;
    }
    
    /**
     * @return the startingAt
     */
    public int getStartingAt()
    {
        return startingAt;
    }

    /**
     * @param startingAt the startingAt to set
     */
    public void setStartingAt(int startingAt)
    {
        this.startingAt = startingAt;
    }

    /**
     * @return the maxPerPage
     */
    public int getMaxPerPage()
    {
        return maxPerPage;
    }

    /**
     * @param maxPerPage the maxPerPage to set
     */
    public void setMaxPerPage(int maxPerPage)
    {
        this.maxPerPage = maxPerPage;
    }

    /**
     * @return the sortField
     */
    public String getSortField()
    {
        return sortField;
    }

    /**
     * @param sortField the sortField to set
     */
    public void setSortField(String sortField)
    {
        this.sortField = sortField;
    }

    /**
     * @return the filters
     */
    public Map<String,Object> getFilters()
    {
        return filters;
    }

    /**
     * @param filters the filters to set
     */
    public void setFilters(Map<String,Object> filters)
    {
        this.filters = filters;
    }

    /**
     * @return the isAsc
     */
    public boolean isIsAsc()
    {
        return asc;
    }

    /**
     * @param isAsc the isAsc to set
     */
    public void setIsAsc(boolean asc)
    {
        this.asc = asc;
    }

    /**
     * @return the distinct
     */
    public boolean isDistinct()
    {
        return distinct;
    }

    /**
     * @param distinct the distinct to set
     */
    public void setDistinct(boolean distinct)
    {
        this.distinct = distinct;
    }

    /**
     * @return the joinBags
     */
    public String[] getJoinBags()
    {
        return joinBags;
    }

    /**
     * @param joinBags the joinBags to set
     */
    public void setJoinBags(String[] joinBags)
    {
        this.joinBags = joinBags;
    }
            
}
