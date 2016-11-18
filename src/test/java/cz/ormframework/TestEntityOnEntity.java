package cz.ormframework;

import cz.ormframework.annotations.Column;
import cz.ormframework.annotations.Table;

/**
 * siOnzee.cz
 * JDBC ORM Framework Release
 */
@Table
class TestEntityOnEntity {

    @Column
    private int id;

    @Column
    private String testValue = "TestValue";

    @Column
    private TestEntity testedEntity;

    public TestEntity getTestedEntity() {
        return testedEntity;
    }

    void setTestedEntity(TestEntity testedEntity) {
        this.testedEntity = testedEntity;
    }

    /**
     * Gets test value.
     *
     * @return the test value
     */
    String getTestValue() {
        return testValue;
    }

    /**
     * Sets test value.
     *
     * @param testValue the test value
     */
    void setTestValue(String testValue) {
        this.testValue = testValue;
    }
}
