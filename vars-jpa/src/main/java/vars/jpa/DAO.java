package vars.jpa;

import vars.IDAO;
import vars.VARSPersistenceException;
import org.mbari.jpaxx.EAO;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Persistence service implementation for use in Java SE environments
 */
public class DAO implements IDAO {

    private final EAO eao;

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Inject
    public DAO(EAO eao) {
        this.eao = eao;
    }

    public <T> T makePersistent(T object) {
        if (object instanceof JPAEntity) {
            if (((JPAEntity) object).getId() == null) {
                eao.insert(object);
            }
            else {
                object = eao.update(object);
            }
            return object;
        }
        else {
            throw new VARSPersistenceException(object + " is not an instance of JPAEntity");
        }
    }

    public <T> T makeTransient(T object) {

        T returnValue = null;
        if (object instanceof JPAEntity) {
            JPAEntity entity = (JPAEntity) object;
            if (entity.getId() == null) {
                log.warn("Unable to delete an entity that has a null primary key: " + entity);
                returnValue = object;
            }
            else {
                returnValue = eao.delete(object);
            }
        }
        else {
            throw new VARSPersistenceException(object + " is not an instance of JPAEntity");
        }
        return returnValue;
    }

    public <T> T update(T object) {
        if (object instanceof JPAEntity) {
            return eao.update(object);
        }
        else {
            throw new VARSPersistenceException(object + " is not an instance of JPAEntity");
        }
    }

    public <T> T findByPrimaryKey(Class<T> clazz, Object primaryKey) {
        return eao.find(clazz, primaryKey);
    }

    protected EAO getEAO() {
        return eao;
    }
}
