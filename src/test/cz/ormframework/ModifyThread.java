package test.cz.ormframework;

import cz.ormframework.EntityManager;
import cz.ormframework.log.Debug;

/**
 * siOnzee.cz
 * JDBC ORM Framework
 * Created 28.07.2016
 */
public class ModifyThread extends Thread implements Runnable {

    private TestEntity te;
    private EntityManager em;
    private boolean result;
    private boolean persist = true;

    public ModifyThread(EntityManager em, TestEntity te, boolean result, boolean persist) {
        this.em = em;
        this.te = te;
        this.result = result;
        this.persist = persist;
    }

    @Override
    public void run() {
        te.setTestingBoolean(result);
        if(!persist)
            return;
        em.persist(te).flush();
        Debug.info("Persisted and flushing!");
    }
}
