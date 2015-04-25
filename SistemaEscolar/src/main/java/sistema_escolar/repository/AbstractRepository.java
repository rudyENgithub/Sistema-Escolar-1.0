package sistema_escolar.repository;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import org.apache.lucene.search.Query;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import static org.hibernate.search.jpa.Search.getFullTextEntityManager;
import org.hibernate.search.query.dsl.QueryBuilder;
import sistema_escolar.qualifier.MyDatabase;
import sistema_escolar.util.FilterSortLazyModel;

/**
 * 
 * 
 * Usage:
 * public class UserRepository extends AbstractRepository<Long, User> implements Serializable
 * {
 *      //empty
 * }
 * 
 * ...
 * UserRepository rep = new UserRepository();
 * rep.save(user);
 * ...
 * 
 * Don't forget to implement Serializable in every entity and children dao,
 * the hibernate 'll be happy =)
 * 
 * @author Vitor Freitas - github(vFreitas)
 * @param <K> Type of the entity ID(Key).
 * @param <E> Type of the Entity.  
 */
abstract class AbstractRepository<K , E> implements Repository<K ,E>, Serializable
{       
    /* TransactionScoped EntityManager */
    @Inject @MyDatabase
    private EntityManager manager;
    
    /* The entity class type */
    protected Class<E> entityClass;
    
    /**
     * Builder, it gets the second(entity) parameterized type and
     * sets to entityClass variable.
     */
    public AbstractRepository()
    {
        ParameterizedType genericSuperClass 
                = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<E>) genericSuperClass.getActualTypeArguments()[1];
    }

    /**
     * 
     * @return The entity class type instance variable
     */
    protected Class<E> getEntityClassType()
    {
        return this.entityClass;
    }
    
    /**
     * 
     * @return The entity class type name
     */
    protected String getClassName()
    {
        return getEntityClassType().getSimpleName();
    }
    
    /**
     * Persist an entity into the database
     * - Creates a new instance of the EntityTransaction
     * - Initiate it, persist, commit and finally closes the EntityManager
     * - Catches and rollback any errors on the transaction.
     * @param entity E object to persist in the database
     */
    @Override
    public void save(E entity) 
    {
        manager.persist(entity);
    }

    /**
     * Remove an entity from the database
     * - Creates a new instance of the EntityTransaction
     * - Initiate it, merge, commit and finally closes the EntityManager
     * - Catches and rollback any errors on the transaction.
     * @param entity E object to merge in the database
     */
    @Override
    public void merge(E entity) 
    {
        manager.merge(entity);
    }

    /**
     * Remove an entity into the database
     * - Creates a new instance of the EntityTransaction
     * - Initiate it, remove, commit and finally closes the EntityManager
     * - Catches and rollback any errors on the transaction.
     * @param entity The E entity to remove from database
     */
    @Override
    public void remove(E entity) 
    {   /* Verify if the entity is on the transaction context or not */
        manager.remove( manager.contains(entity) ? entity : manager.merge(entity));
    }

    /**
     * Gets an entity by its ID
     * - It gets a new instance of the EntityManager
     * - It finds an E entity with the given id
     * @param id ID of the E entity
     * @return an E type object
     */
    @Override
    public E getById(K id) 
    {
        return (E) manager.find(getEntityClassType(), id);
    }

    /**
     * It gets a list of E objects
     * - It gets a new instance of the EntityManager
     * - Finds all E objects 
     * @return A list of E objects
     */
    @Override
    public List<E> getAll() 
    {
        return (List<E>) manager.createQuery("FROM " + getClassName(),
                getEntityClassType()).getResultList(); 
    }

    /**
     * It gets a list of E objects with the results of the given named query.
     * - It gets a new instance of the EntityManager
     * - Create the named query 
     * - And get the result list
     * @param namedQuery Name of the named query
     * @return A list of E objects
     */
    @Override
    public List<E> getAllNamedQuery(String namedQuery) 
    { 
        return (List<E>) manager.createNamedQuery(namedQuery)
            .getResultList();
    }
    
    /**
     * 
     * @param startingAt 
     * @param maxPerPage 
     * @return 
    */
    @Override
    public List<E> getAllLazyLoad(int startingAt, int maxPerPage)
    {
        return (List<E>) manager.createQuery("SELECT e FROM " + getEntityClassType()
                        .getSimpleName() + " e")
                .setFirstResult(startingAt)
                .setMaxResults(maxPerPage)
                .getResultList(); 
    }
    
    
    
    /**
     * 
     * @param startingAt 
     * @param maxPerPage 
     * @return 
    */
    public List<E> getAllFiltredSortedLazyLoad(FilterSortLazyModel data)
    {
        List<E> resultList = null;
        try
        {      
            Session session = manager.unwrap(Session.class);
            
            Criteria criteria = session.createCriteria(getEntityClassType());
            
            DetachedCriteria idsOnlyCriteria = DetachedCriteria
                    .forClass(getEntityClassType());
            
//            idsOnlyCriteria.setFetchMode("materias", FetchMode.JOIN);
            
            idsOnlyCriteria.setProjection(Projections.distinct(Projections.id()));
            
            if(!data.getFilters().isEmpty())
            {
                data.getFilters().forEach((k, v) -> 
                {
                    if(v instanceof String)
                        idsOnlyCriteria.add(Restrictions.ilike(k,(String) v, MatchMode.ANYWHERE));
                    else
                        idsOnlyCriteria.add(Restrictions.eq(k, v));
                    
                });
            }
            
            if(data.isIsAsc() && data.getSortField() != null)
                criteria.addOrder(Order.asc(data.getSortField()));
            else if(data.getSortField() != null)
                criteria.addOrder(Order.desc(data.getSortField()));
             
            
            criteria.add(Subqueries.propertyIn("id", idsOnlyCriteria));
          
            criteria.setFirstResult(data.getStartingAt());
            criteria.setMaxResults(data.getMaxPerPage());

  
            resultList = criteria.list(); 
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }
        return resultList;
    }
    
    /**
     * @return 
     */
    @Override
    public int getTotalCount()
    {
        return ((Number) manager
                .createQuery("SELECT COUNT(e) FROM " + getEntityClassType()
                .getSimpleName() + " e")
                .getSingleResult()).intValue();
    }

    /**
     *  It gets an E object with the result of the given named query
     * with the parameter.
     * - It gets a new instance of the EntityManager
     * - Create the named query 
     * - Set the parameter
     * - And get the single result
     * @param namedQuery Name of the named query
     * @param parameter Name of the parameter setted on the named query
     * @param value String value of the parameter
     * @return An E object
     */
    @Override
    public E getUniqueByRestriction(String namedQuery, String parameter, String value) 
    {
  
        return (E) manager.createNamedQuery(namedQuery)
            .setParameter(parameter, value)
            .getSingleResult();  

    }

    /**
     * It gets an E object with the result of the given named query
     * with the parameter.
     * - It creates a new instance of the EntityManager
     * - Create the named query 
     * - Set the parameter
     * - And get the single result
     * @param namedQuery Name of the named query
     * @param parameter Name of the parameter setted on the named query
     * @param value Object value of the parameter
     * @return An E object
     */
    @Override
    public E getUniqueByRestriction(String namedQuery, String parameter, Object value) 
    {  
        return (E) manager.createNamedQuery(namedQuery)
            .setParameter(parameter, value)
            .getSingleResult();  
    }

    /**
     * It gets a list of E objects with the results of the given named query
     * with the parameter.
     * - It gets a new instance of the EntityManager
     * - Create the named query 
     * - Set the parameter
     * - And get the result list
     * @param namedQuery Name of the named query
     * @param parameter Name of the parameter setted on the named query
     * @param value Object value of the parameter
     * @return A list of E objects
     */
    @Override
    public List<E> getByRestriction(String namedQuery, String parameter, Object value) 
    {  
        return (List<E>) manager.createNamedQuery(namedQuery)
            .setParameter(parameter, value)
            .getResultList();
    }

    /**
     * It gets a list of E objects with the results of the given named query
     * with the parameter.
     * - It gets a new instance of the EntityManager
     * - Create the named query 
     * - Set the parameter
     * - And get the result list
     * @param namedQuery Name of the named query
     * @param parameter Name of the parameter setted on the named query
     * @param value String value of the parameter
     * @return A list of E objects
     */
    @Override
    public List<E> getByRestriction(String namedQuery, String parameter, String value) 
    {
        return (List<E>) manager.createNamedQuery(namedQuery)
            .setParameter(parameter, value)
            .getResultList();
    }

    /**
     * 
     */
    @Override
    public void initLucene()
    {
        try
        {
            FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(manager);
            fullTextEntityManager.createIndexer().startAndWait();System.out.println("DONE");
        } 
        catch (InterruptedException ex)
        {
            ex.printStackTrace();
            Logger.getLogger(getClassName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * @param matchingText 
     * @param fields 
     * @return 
     */
    @Override
    public List<E> getMatchingValuesLucene(String matchingText, 
            String... fields)
    {
        List<E> resultList = null;
        try
        {
            FullTextEntityManager fullTextEntityManager = 
                    getFullTextEntityManager(manager);
            
            manager.getTransaction().begin();

            QueryBuilder qb = fullTextEntityManager.getSearchFactory()
                .buildQueryBuilder().forEntity(getEntityClassType()).get();
            
            Query luceneQuery = qb
                .keyword()
                .onFields(fields)
                .matching(matchingText)
                .createQuery();
            
            javax.persistence.Query jpaQuery =
                fullTextEntityManager.createFullTextQuery(luceneQuery, 
                        getEntityClassType());

            resultList = jpaQuery.getResultList();
            
            System.out.println(resultList.size());
            
            manager.getTransaction().commit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return resultList;
    }
}
