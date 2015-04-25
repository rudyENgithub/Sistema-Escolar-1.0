/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema_escolar.annotation;

import sistema_escolar.qualifier.MyDatabase;
import java.io.Serializable;
import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 *
 * @author Vitor Freitas
 */
@Interceptor
@Transactional
@Priority(Interceptor.Priority.APPLICATION + 1)
public class TransactionInterceptor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject @MyDatabase
    private EntityManager manager;

    @AroundInvoke
    public Object invoke(InvocationContext context) throws Exception 
    {
        EntityTransaction transaction = manager.getTransaction();
        boolean owner = false;

        try 
        {
            if (!transaction.isActive()) 
            {

                    // truque para fazer rollback no que já passou
                    // (senão, um futuro commit, confirmaria até mesmo operações sem transação)
                    transaction.begin();
                    transaction.rollback();

                    // agora sim inicia a transação
                    transaction.begin();

                    owner = true;
            }

            return context.proceed();
        } 
        catch (Exception e) 
        {
            if (transaction != null && owner) 
                transaction.rollback();
            throw e;
        } 
        finally 
        {
            if (transaction != null && transaction.isActive() && owner) 
                transaction.commit();
        }
    }

}
