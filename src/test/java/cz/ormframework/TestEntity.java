package cz.ormframework;

import cz.ormframework.annotations.Column;
import cz.ormframework.annotations.Table;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * siOnzee.cz
 * JDBC ORM Framework Release
 */
@Table("TestingEntity")
class TestEntity {

    @Column
    private int id;

    @Column
    private String testingText;

    @Column
    private int testingInt;

    @Column
    private long testingLong;

    @Column
    private char testingChar;

    @Column
    private short testingShort;

    @Column
    private boolean testingBoolean;

    @Column
    private byte testingByte;

    @Column
    private float testingFloat;

    @Column
    private double testingDouble;

    @Column
    private TestingEnum testingEnum;

    @Column
    private Timestamp testingTimestamp;

    @Column
    private Date testingDate;

    @Column
    private TestEntityOnEntity testingEntity;

    @Column
    private String[] testingTextArray;

    @Column
    private int[] testingIntArray;

    @Column
    private long[] testingLongArray;

    @Column
    private char[] testingCharArray;

    @Column
    private short[] testingShortArray;

    @Column
    private boolean[] testingBooleanArray;

    @Column
    private byte[] testingByteArray;

    @Column
    private float[] testingFloatArray;

    @Column
    private double[] testingDoubleArray;

    @Column
    private Timestamp[] testingTimestampArray;

    @Column
    private Date[] testingDateArray;

    @Column
    private TestEntityOnEntity[] testingEntityArray;

    @Column
    private TestingEnum[] testingEnumArray;

    @Column
    private Date nullDate = null;

    @Column
    private TestEntity selfEntity;

    TestEntity getSelfEntity() {
        return selfEntity;
    }

    void setSelfEntity(TestEntity selfEntity) {
        this.selfEntity = selfEntity;
    }

    /**
     * Sets testing enum.
     *
     * @param testingEnum the testing enum
     */
    void setTestingEnum(TestingEnum testingEnum) {
        this.testingEnum = testingEnum;
    }

    /**
     * Sets testing enum array.
     *
     * @param testingEnumArray the testing enum array
     */
    void setTestingEnumArray(TestingEnum[] testingEnumArray) {
        this.testingEnumArray = testingEnumArray;
    }

    /**
     * Sets testing entity.
     *
     * @param testingEntity the testing entity
     */
    void setTestingEntity(TestEntityOnEntity testingEntity) {
        this.testingEntity = testingEntity;
    }

    /**
     * Sets testing entity array.
     *
     * @param testingEntityArray the testing entity array
     */
    void setTestingEntityArray(TestEntityOnEntity[] testingEntityArray) {
        this.testingEntityArray = testingEntityArray;
    }

    /**
     * Sets testing text.
     *
     * @param testingText the testing text
     */
    void setTestingText(String testingText) {
        this.testingText = testingText;
    }

    /**
     * Sets testing int.
     *
     * @param testingInt the testing int
     */
    void setTestingInt(int testingInt) {
        this.testingInt = testingInt;
    }

    /**
     * Sets testing long.
     *
     * @param testingLong the testing long
     */
    void setTestingLong(long testingLong) {
        this.testingLong = testingLong;
    }

    /**
     * Sets testing char.
     *
     * @param testingChar the testing char
     */
    void setTestingChar(char testingChar) {
        this.testingChar = testingChar;
    }

    /**
     * Sets testing short.
     *
     * @param testingShort the testing short
     */
    void setTestingShort(short testingShort) {
        this.testingShort = testingShort;
    }

    /**
     * Sets testing boolean.
     *
     * @param testingBoolean the testing boolean
     */
    void setTestingBoolean(boolean testingBoolean) {
        this.testingBoolean = testingBoolean;
    }

    /**
     * Sets testing byte.
     *
     * @param testingByte the testing byte
     */
    void setTestingByte(byte testingByte) {
        this.testingByte = testingByte;
    }

    /**
     * Sets testing float.
     *
     * @param testingFloat the testing float
     */
    void setTestingFloat(float testingFloat) {
        this.testingFloat = testingFloat;
    }

    /**
     * Sets testing double.
     *
     * @param testingDouble the testing double
     */
    void setTestingDouble(double testingDouble) {
        this.testingDouble = testingDouble;
    }

    /**
     * Sets testing timestamp.
     *
     * @param testingTimestamp the testing timestamp
     */
    void setTestingTimestamp(Timestamp testingTimestamp) {
        this.testingTimestamp = testingTimestamp;
    }

    /**
     * Sets testing date.
     *
     * @param testingDate the testing date
     */
    void setTestingDate(Date testingDate) {
        this.testingDate = testingDate;
    }

    /**
     * Sets testing text array.
     *
     * @param testingTextArray the testing text array
     */
    void setTestingTextArray(String[] testingTextArray) {
        this.testingTextArray = testingTextArray;
    }

    /**
     * Sets testing int array.
     *
     * @param testingIntArray the testing int array
     */
    void setTestingIntArray(int[] testingIntArray) {
        this.testingIntArray = testingIntArray;
    }

    /**
     * Sets testing long array.
     *
     * @param testingLongArray the testing long array
     */
    void setTestingLongArray(long[] testingLongArray) {
        this.testingLongArray = testingLongArray;
    }

    /**
     * Sets testing char array.
     *
     * @param testingCharArray the testing char array
     */
    void setTestingCharArray(char[] testingCharArray) {
        this.testingCharArray = testingCharArray;
    }

    /**
     * Sets testing short array.
     *
     * @param testingShortArray the testing short array
     */
    void setTestingShortArray(short[] testingShortArray) {
        this.testingShortArray = testingShortArray;
    }

    /**
     * Sets testing boolean array.
     *
     * @param testingBooleanArray the testing boolean array
     */
    void setTestingBooleanArray(boolean[] testingBooleanArray) {
        this.testingBooleanArray = testingBooleanArray;
    }

    /**
     * Sets testing byte array.
     *
     * @param testingByteArray the testing byte array
     */
    void setTestingByteArray(byte[] testingByteArray) {
        this.testingByteArray = testingByteArray;
    }

    /**
     * Sets testing float array.
     *
     * @param testingFloatArray the testing float array
     */
    void setTestingFloatArray(float[] testingFloatArray) {
        this.testingFloatArray = testingFloatArray;
    }

    /**
     * Sets testing double array.
     *
     * @param testingDoubleArray the testing double array
     */
    void setTestingDoubleArray(double[] testingDoubleArray) {
        this.testingDoubleArray = testingDoubleArray;
    }

    /**
     * Sets testing timestamp array.
     *
     * @param testingTimestampArray the testing timestamp array
     */
    void setTestingTimestampArray(Timestamp[] testingTimestampArray) {
        this.testingTimestampArray = testingTimestampArray;
    }

    /**
     * Sets testing date array.
     *
     * @param testingDateArray the testing date array
     */
    void setTestingDateArray(Date[] testingDateArray) {
        this.testingDateArray = testingDateArray;
    }

    /**
     * Gets testing boolean.
     *
     * @return the testing boolean
     */
    boolean getTestingBoolean() {
        return testingBoolean;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    int getId() {
        return id;
    }

    /**
     * Gets testing text.
     *
     * @return the testing text
     */
    String getTestingText() {
        return testingText;
    }

    /**
     * Gets testing int.
     *
     * @return the testing int
     */
    int getTestingInt() {
        return testingInt;
    }

    /**
     * Gets testing long.
     *
     * @return the testing long
     */
    long getTestingLong() {
        return testingLong;
    }

    /**
     * Gets testing char.
     *
     * @return the testing char
     */
    char getTestingChar() {
        return testingChar;
    }

    /**
     * Gets testing short.
     *
     * @return the testing short
     */
    short getTestingShort() {
        return testingShort;
    }

    /**
     * Is testing boolean boolean.
     *
     * @return the boolean
     */
    boolean isTestingBoolean() {
        return testingBoolean;
    }

    /**
     * Gets testing byte.
     *
     * @return the testing byte
     */
    byte getTestingByte() {
        return testingByte;
    }

    /**
     * Gets testing float.
     *
     * @return the testing float
     */
    float getTestingFloat() {
        return testingFloat;
    }

    /**
     * Gets testing double.
     *
     * @return the testing double
     */
    double getTestingDouble() {
        return testingDouble;
    }

    /**
     * Gets testing enum.
     *
     * @return the testing enum
     */
    TestingEnum getTestingEnum() {
        return testingEnum;
    }

    /**
     * Gets testing timestamp.
     *
     * @return the testing timestamp
     */
    Timestamp getTestingTimestamp() {
        return testingTimestamp;
    }

    /**
     * Gets testing date.
     *
     * @return the testing date
     */
    Date getTestingDate() {
        return testingDate;
    }

    /**
     * Gets testing entity.
     *
     * @return the testing entity
     */
    TestEntityOnEntity getTestingEntity() {
        return testingEntity;
    }

    /**
     * Get testing text array string [ ].
     *
     * @return the string [ ]
     */
    String[] getTestingTextArray() {
        return testingTextArray;
    }

    /**
     * Get testing int array int [ ].
     *
     * @return the int [ ]
     */
    int[] getTestingIntArray() {
        return testingIntArray;
    }

    /**
     * Get testing long array long [ ].
     *
     * @return the long [ ]
     */
    long[] getTestingLongArray() {
        return testingLongArray;
    }

    /**
     * Get testing char array char [ ].
     *
     * @return the char [ ]
     */
    char[] getTestingCharArray() {
        return testingCharArray;
    }

    /**
     * Get testing short array short [ ].
     *
     * @return the short [ ]
     */
    short[] getTestingShortArray() {
        return testingShortArray;
    }

    /**
     * Get testing boolean array boolean [ ].
     *
     * @return the boolean [ ]
     */
    boolean[] getTestingBooleanArray() {
        return testingBooleanArray;
    }

    /**
     * Get testing byte array byte [ ].
     *
     * @return the byte [ ]
     */
    byte[] getTestingByteArray() {
        return testingByteArray;
    }

    /**
     * Get testing float array float [ ].
     *
     * @return the float [ ]
     */
    float[] getTestingFloatArray() {
        return testingFloatArray;
    }

    /**
     * Get testing double array double [ ].
     *
     * @return the double [ ]
     */
    double[] getTestingDoubleArray() {
        return testingDoubleArray;
    }

    /**
     * Get testing timestamp array timestamp [ ].
     *
     * @return the timestamp [ ]
     */
    Timestamp[] getTestingTimestampArray() {
        return testingTimestampArray;
    }

    /**
     * Get testing date array date [ ].
     *
     * @return the date [ ]
     */
    Date[] getTestingDateArray() {
        return testingDateArray;
    }

    /**
     * Get testing entity array test entity on entity [ ].
     *
     * @return the test entity on entity [ ]
     */
    TestEntityOnEntity[] getTestingEntityArray() {
        return testingEntityArray;
    }

    /**
     * Get testing enum array testing enum [ ].
     *
     * @return the testing enum [ ]
     */
    TestingEnum[] getTestingEnumArray() {
        return testingEnumArray;
    }

    /**
     * Get null date.
     * @return the null
     */
    Date getNullDate() {
        return nullDate;
    }
}
