/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema_escolar.producer;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import sistema_escolar.qualifier.MyDatabase;

/**
 *
 * @author Vitor Freitas
 */
@ApplicationScoped
public class EntityManagerProducer 
{
    private final EntityManagerFactory factory;
    
    public EntityManagerProducer()
    {
        this.factory = Persistence.createEntityManagerFactory("sistema_escolar");
    }
    
    @Produces
    @MyDatabase
    @RequestScoped
    public EntityManager createEntityManager()
    {
        return this.factory.createEntityManager();
    }
    
    public void closeEntityManager(@Disposes @MyDatabase EntityManager manager)
    {
        manager.close();
        System.out.println("FECHADO " + manager.isOpen() );
    }
}
