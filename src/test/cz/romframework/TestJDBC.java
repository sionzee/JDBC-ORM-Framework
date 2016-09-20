package test.cz.romframework;

import cz.ormframework.EntityManager;
import cz.ormframework.database.MySQL;
import cz.ormframework.events.objects.ExecuteQueryEvent;
import cz.ormframework.events.objects.QueryDoneEvent;
import cz.ormframework.log.Debug;
import cz.ormframework.utils.EntityUtils;
import cz.ormframework.utils.QueryBuilder;
import cz.ormframework.utils.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import test.cz.romframework.codeexamples.manager.User;
import test.cz.romframework.codeexamples.manager.UserManager;
import test.cz.romframework.repositories.UserRepository;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * siOnzee.cz
 * JDBC ORM Framework Release
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestJDBC {

    /**
     * The Mysql.
     */
    MySQL mysql;
    /**
     * The Em.
     */
    EntityManager em;

    UserRepository userRepository;

    /**
     * Init.
     */
    @Before
    public void Init() {
        mysql = new MySQL("localhost", "minecraft", "sionzee", System.getenv("MYSQL_PASSWORD"), 3306);
        em = new EntityManager(mysql);
        userRepository = em.registerRepository(UserRepository.class, User.class);
    }

    /**
     * A register events.
     */
    @Test
    public void ARegisterEvents() {
        ExecuteQueryEvent.getHandlerList().addListener(this::onExecute);
        QueryDoneEvent.getHandlerList().addListener(this::onDone);
    }

    private void onDone(QueryDoneEvent event) {
        Debug.info(EntityUtils.getId(event.getEntity()) + " - " + event.getQuery());
    }

    private void onExecute(ExecuteQueryEvent event) {

    }


    /**
     * B create table.
     */
    @Test
    public void BCreateTable() {
        em.getTableCreator().createTable(TestEntityOnEntity.class, true);
        em.getTableCreator().createTable(TestEntity.class, true);
    }

    /**
     * C insert to table.
     */
    @Test(timeout = 1000L)
    public void CInsertToTable() {

        TestEntityOnEntity te0 = new TestEntityOnEntity();
        te0.setTestValue(new Random().nextInt() + "");
        TestEntityOnEntity te1 = new TestEntityOnEntity();
        te1.setTestValue(new Random().nextInt() + "");
        TestEntityOnEntity te2 = new TestEntityOnEntity();
        te2.setTestValue(new Random().nextInt() + "");
        TestEntityOnEntity te3 = new TestEntityOnEntity();
        te3.setTestValue(new Random().nextInt() + "");
        TestEntityOnEntity te4 = new TestEntityOnEntity();
        te4.setTestValue(new Random().nextInt() + "");

        em.persist(te0).persist(te1).persist(te2).persist(te3).persist(te4).flush();

        TestEntityOnEntity[] tes = new TestEntityOnEntity[] {te1, te2, te3, te4};

        for(int i = 0; i < 10; i++) {
            TestEntity te = new TestEntity();
            te.setTestingBoolean(true);
            te.setTestingBooleanArray(new boolean[] {true, false, true, false});
            te.setTestingByte((byte) 1);
            te.setTestingByteArray(new byte[]{(byte) 1, (byte)0, (byte)1,(byte) 0});
            te.setTestingChar('a');
            te.setTestingCharArray(new char[]{'a', 'b', 'c', 'd'});
            te.setTestingDate(new Date(new java.util.Date().getTime()));
            te.setTestingDateArray(new Date[]{new Date(new java.util.Date().getTime()), new Date(new java.util.Date(200000000000L).getTime()), new Date(new java.util.Date(30000000000L).getTime()), new Date(new java.util.Date(400000000000L).getTime())} );
            te.setTestingDouble(1d);
            te.setTestingDoubleArray(new double[] {0.25545d, 1.54654654d, 165165.61897166231d, 1231.0000005d});
            te.setTestingEntity(te0);
            te.setTestingEntityArray(tes);
            te.setTestingEnum(TestingEnum.VALUE_A);
            te.setTestingEnumArray(new TestingEnum[]{TestingEnum.VALUE_A, TestingEnum.VALUE_B, TestingEnum.VALUE_C, TestingEnum.VALUE_B});
            te.setTestingFloat(1f);
            te.setTestingFloatArray(new float[]{0.16156156156f, 1.24564564f, 56156.5464f, 48948.4545f});
            te.setTestingInt(1);
            te.setTestingIntArray(new int[]{0, 1231321, 23161, 165165});
            te.setTestingLong(1L);
            te.setTestingLongArray(new long[]{651561561616156161L, 41136181892L, 4168151L, 428764156315361L});
            te.setTestingText("TestingWord");
            te.setTestingTextArray(new String[]{"This is a testing string", "the nd string", "rd string", "th string"});
            te.setTestingTimestamp(new Timestamp(new java.util.Date().getTime()));
            te.setTestingTimestampArray(new Timestamp[]{new Timestamp(new java.util.Date().getTime()), new Timestamp(new java.util.Date().getTime() + 2000L), new Timestamp(new java.util.Date().getTime() + 20000L), new Timestamp(new java.util.Date().getTime() + 200000L)});
            te.setTestingShort((short) 1);
            te.setTestingShortArray(new short[]{(short) 51561, (short) 1617, (short) 5561, (short) 646545644});
            em.persist(te);
        }
        em.flush();
    }

    /**
     * D select one.
     */
    @Test(timeout = 2000L)
    public void DSelectOne() {
        TestEntity te = em.getRepository(TestEntity.class).find().where("id = {0}", 1).one();
        Assert.assertNotNull(te);
        Debug.entity(te, false);
    }

    /**
     * E select all.
     */
    @Test(timeout = 3000L)
    public void ESelectAll() {
        Collection<TestEntity> te = em.getRepository(TestEntity.class).find().all();
        Assert.assertNotNull(te);
        te.parallelStream().forEach(Assert::assertNotNull);
    }

    /**
     * F select filter.
     */
    @Test(timeout = 4000L)
    public void FSelectFilter() {
        TestEntity te = em.getRepository(TestEntity.class).find().where("id = {0}", 5).order("id", QueryBuilder.ORDERING.ASC).one();
        Assert.assertNotNull(te);
    }

    /**
     * G update inserts.
     */
    @Test(timeout = 5000L)
    public void GUpdateInserts() {
        TestEntity te = em.getRepository(TestEntity.class).find().where("id = {0}", 5).one();
        Assert.assertNotNull(te);
        te.setTestingBoolean(false);
        em.persist(te);
        em.flush();
        Assert.assertFalse(em.getRepository(TestEntity.class).find().where("id = {0}", 5).one().getTestingBoolean());
    }

    /**
     * H get inner entity.
     */
    @Test
    public void HGetInnerEntity() {
        TestEntityOnEntity te = em.getRepository(TestEntity.class).find().where("id = {0}", 1).one().getTestingEntity();
        Assert.assertNotNull(te);
        Debug.info(te.getTestValue());
    }

    /**
     * Replace inner entity for id one.
     */
    @Test
    public void IReplaceInnerEntityForIDOne() {
        TestEntityOnEntity test = new TestEntityOnEntity();
        test.setTestValue("NEW TEST VALUE");
        Assert.assertNotNull(test);
        TestEntity te = em.getRepository(TestEntity.class).find().where("id = {0}", 1).one();
        Assert.assertNotNull(te);
        te.setTestingEntity(test);
        em.persist(test).persist(te).flush();
    }

    /**
     * J check replaced entity for id one.
     */
    @Test
    public void JCheckReplacedEntityForIDOne() {
        TestEntityOnEntity te = em.getRepository(TestEntity.class).find().where("id = {0}", 1).one().getTestingEntity();
        Assert.assertNotNull(te);
        Debug.info(te.getTestValue());
    }

    /**
     * K test many entities.
     */
    @Test
    public void KTestManyEntities() {
        TestEntity te = em.getRepository(TestEntity.class).find().where("id = {0}", 1).one();
        Assert.assertNotNull(te);

        TestEntityOnEntity[] entities = te.getTestingEntityArray();
        Assert.assertNotNull(entities);
        for(int i = 0; i < entities.length; i++) {
            TestEntityOnEntity e = entities[i];
            Debug.info(e.getTestValue());
        }
    }

    /**
     * L modify many entities.
     */
    @Test
    public void LModifyManyEntities() {
        TestEntity te = em.getRepository(TestEntity.class).find().where("id = {0}", 1).one();
        Assert.assertNotNull(te);

        TestEntityOnEntity[] entities = te.getTestingEntityArray();
        Assert.assertNotNull(entities);
        for(int i = 0; i < entities.length; i++) {
            TestEntityOnEntity e = entities[i];
            e.setTestValue("MODIFED VALUE");
            this.em.persist(e);
        }
        this.em.flush();
    }

    /**
     * M test modifed entities.
     */
    @Test
    public void MTestModifedEntities() {
        TestEntity te = em.getRepository(TestEntity.class).find().where("id = {0}", 1).one();
        Assert.assertNotNull(te);

        TestEntityOnEntity[] entities = te.getTestingEntityArray();
        Assert.assertNotNull(entities);
        for (TestEntityOnEntity e : entities) {
            Assert.assertEquals(e.getTestValue(), "MODIFED VALUE");
        }
    }

    /**
     * N remove entity.
     */
    @Test
    public void NRemoveEntity() {
        em.delete(em.getRepository(TestEntity.class).find().where("id = {0}", 1).one());
        em.getRepository(TestEntity.class).delete().where("id = {0}", 2).one();
        em.flush();
    }

    /**
     * O test removed entity.
     */
    @Test
    public void OTestRemovedEntity() {
        Assert.assertNull(em.getRepository(TestEntity.class).find().where("id = {0}", 1).one());
        Assert.assertNull(em.getRepository(TestEntity.class).find().where("id = {0}", 2).one());
    }
    @Test
    public void PCheckThreadSafeEntity() {
        TestEntity te = em.getRepository(TestEntity.class).find().where("id = {0}", 3).one();

        Debug.info("TE-Boolean: " + te.getTestingBoolean());
        ModifyThread mt = new ModifyThread(em, te, false, true);
        try {
            mt.start();
            mt.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Debug.info("TE-Boolean: " + te.getTestingBoolean());

        mt = new ModifyThread(em, te, true, false);
        try {
            mt.start();
            mt.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        em.persist(te).flush();
        Debug.info("TE-Boolean: " + te.getTestingBoolean());
    }

    @Test
    public void ZCodeExampleWithTest() {
        em.getTableCreator().createTable(User.class, true);
        UserManager um = new UserManager(em);
        for(int i = 0; i < 200; i++) {
            um.addUser(StringUtils.randomString(new Random().nextInt(20)));
        }

        em.flush();

        um.filter(u -> u.getAge() > 80).forEach(user -> {
            Debug.info("User " + user.getUsername() + " is going to dead, he is " + user.getAge() + " years old.");
        });

        um.filter(u -> u.getAge() >= 100).forEach(user -> {
            Debug.info("User " + user.getUsername() + " is dead, removing.");
            um.removeUser(user);
        });
    }

    @Test
    public void ZYATestRepository() {
        User user = new User();
        user.setUsername("TestUsername");
        user.setAge(10);
        em.persist(user).flush();

        user = userRepository.getUserByName("TestUsername");
        Assert.assertNotNull(user);
    }
}
