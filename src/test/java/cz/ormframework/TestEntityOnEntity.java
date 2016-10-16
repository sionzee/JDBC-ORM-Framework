package cz.ormframework;

import cz.ormframework.annotations.Column;
import cz.ormframework.annotations.Table;

/**
 * siOnzee.cz
 * JDBC ORM Framework Release
 */
@Table
public class TestEntityOnEntity {

    @Column
    private int id;

    @Column
    private String testValue = "TestValue";

    /**
     * Gets test value.
     *
     * @return the test value
     */
    public String getTestValue() {
        return testValue;
    }

    /**
     * Sets test value.
     *
     * @param testValue the test value
     */
    public void setTestValue(String testValue) {
        this.testValue = testValue;
    }
}
