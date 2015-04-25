package sistema_escolar.repository;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import sistema_escolar.qualifier.MyDatabase;

/**
 * 
 * abstract class, to use when you can't inject the EntityManager
 * - Lack of a trully Java EE Container(@PersistentContext)
 * - You can't use CDI(@ViewScoped)  
 * 
 * If you need a more complex EntityManager scope management, use a trully
 * Java EE Container(jBoss, TomEE, Glassfish) or Dependency Injection.
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
abstract class AbstractRepository1<K , E> implements Repository<K ,E>, Serializable
{   
    @Inject
    @MyDatabase
    /* TransactionScoped EntityManager */
    private EntityManager em;
    
    /* The entity class type */
    protected Class<E> entityClass;
    
    /**
     * Builder, it gets the second(entity) parameterized type and
     * sets to entityClass variable.
     */
    public AbstractRepository1()
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
        EntityTransaction trx = em.getTransaction();

            trx.begin();
            em.persist(entity);
            trx.commit();

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
        EntityTransaction trx = em.getTransaction();

            trx.begin();
            em.merge(entity);
            trx.commit();
 
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
    {
        EntityTransaction trx = em.getTransaction();

            trx.begin();
            /* Verify if the entity is on the transaction context or not */
            em.remove(
                em.contains(entity) ? entity : em
                        .merge(entity));
            trx.commit();

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
        E result = null;

            result = (E) em.find(getEntityClassType(), id);

        return result;
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
        System.out.println(em.toString());
        List<E> resultList = null;

            resultList = (List<E>) em
                    .createQuery("FROM " + getClassName(), getEntityClassType())
                    .getResultList(); 

        return resultList;
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
        List<E> resultList = null;
 
            resultList = (List<E>) em.createNamedQuery(namedQuery)
                .getResultList();

        return resultList;
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
        List<E> resultList = null;
        
        resultList = (List<E>) em
                .createQuery("SELECT e FROM " + getEntityClassType()
                        .getSimpleName() + " e")
                .setFirstResult(startingAt)
                .setMaxResults(maxPerPage); 
        
        return resultList;
    }
    
    /**
     * @return 
     */
    @Override
    public int getTotalCount()
    {
        Integer count = null;

        count = (Integer) em
                .createQuery("SELECT COUNT(e) FROM " + getEntityClassType()
                        .getSimpleName() + " e")
                .getSingleResult();
 
        return count;
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
        E result = null;
  
            result = (E) em.createNamedQuery(namedQuery)
                .setParameter(parameter, value)
                .getSingleResult();  

        return result;
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
        E result = null;
   
            result = (E) em.createNamedQuery(namedQuery)
                .setParameter(parameter, value)
                .getSingleResult();  

        return result;
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
        List<E> resultList = null;
  
            resultList = (List<E>) em.createNamedQuery(namedQuery)
                .setParameter(parameter, value)
                .getResultList();

        return resultList;
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
        List<E> resultList = null;
  
            resultList = (List<E>) em.createNamedQuery(namedQuery)
                .setParameter(parameter, value)
                .getResultList();

        return resultList;
    }
    
}
