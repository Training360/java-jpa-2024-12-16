package employees;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.apache.deltaspike.jpa.api.entitymanager.PersistenceUnitName;
import org.apache.deltaspike.jpa.api.transaction.TransactionScoped;

@ApplicationScoped
public class EntityManagerProducer {

    @Inject
    @PersistenceUnitName("pu")
    private EntityManagerFactory emf;

    @Produces
    @TransactionScoped
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void closeEntityManager(@Disposes EntityManager em) {
        em.close();
    }
}
