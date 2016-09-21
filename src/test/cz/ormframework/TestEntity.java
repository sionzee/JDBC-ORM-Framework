package test.cz.ormframework;

import cz.ormframework.annotations.Column;
import cz.ormframework.annotations.Table;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * siOnzee.cz
 * JDBC ORM Framework Release
 */
@Table("TestingEntity")
public class TestEntity {

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
    private TestEntity selfEntity;

    public TestEntity getSelfEntity() {
        return selfEntity;
    }

    public void setSelfEntity(TestEntity selfEntity) {
        this.selfEntity = selfEntity;
    }

    /**
     * Sets testing enum.
     *
     * @param testingEnum the testing enum
     */
    public void setTestingEnum(TestingEnum testingEnum) {
        this.testingEnum = testingEnum;
    }

    /**
     * Sets testing enum array.
     *
     * @param testingEnumArray the testing enum array
     */
    public void setTestingEnumArray(TestingEnum[] testingEnumArray) {
        this.testingEnumArray = testingEnumArray;
    }

    /**
     * Sets testing entity.
     *
     * @param testingEntity the testing entity
     */
    public void setTestingEntity(TestEntityOnEntity testingEntity) {
        this.testingEntity = testingEntity;
    }

    /**
     * Sets testing entity array.
     *
     * @param testingEntityArray the testing entity array
     */
    public void setTestingEntityArray(TestEntityOnEntity[] testingEntityArray) {
        this.testingEntityArray = testingEntityArray;
    }

    /**
     * Sets testing text.
     *
     * @param testingText the testing text
     */
    public void setTestingText(String testingText) {
        this.testingText = testingText;
    }

    /**
     * Sets testing int.
     *
     * @param testingInt the testing int
     */
    public void setTestingInt(int testingInt) {
        this.testingInt = testingInt;
    }

    /**
     * Sets testing long.
     *
     * @param testingLong the testing long
     */
    public void setTestingLong(long testingLong) {
        this.testingLong = testingLong;
    }

    /**
     * Sets testing char.
     *
     * @param testingChar the testing char
     */
    public void setTestingChar(char testingChar) {
        this.testingChar = testingChar;
    }

    /**
     * Sets testing short.
     *
     * @param testingShort the testing short
     */
    public void setTestingShort(short testingShort) {
        this.testingShort = testingShort;
    }

    /**
     * Sets testing boolean.
     *
     * @param testingBoolean the testing boolean
     */
    public void setTestingBoolean(boolean testingBoolean) {
        this.testingBoolean = testingBoolean;
    }

    /**
     * Sets testing byte.
     *
     * @param testingByte the testing byte
     */
    public void setTestingByte(byte testingByte) {
        this.testingByte = testingByte;
    }

    /**
     * Sets testing float.
     *
     * @param testingFloat the testing float
     */
    public void setTestingFloat(float testingFloat) {
        this.testingFloat = testingFloat;
    }

    /**
     * Sets testing double.
     *
     * @param testingDouble the testing double
     */
    public void setTestingDouble(double testingDouble) {
        this.testingDouble = testingDouble;
    }

    /**
     * Sets testing timestamp.
     *
     * @param testingTimestamp the testing timestamp
     */
    public void setTestingTimestamp(Timestamp testingTimestamp) {
        this.testingTimestamp = testingTimestamp;
    }

    /**
     * Sets testing date.
     *
     * @param testingDate the testing date
     */
    public void setTestingDate(Date testingDate) {
        this.testingDate = testingDate;
    }

    /**
     * Sets testing text array.
     *
     * @param testingTextArray the testing text array
     */
    public void setTestingTextArray(String[] testingTextArray) {
        this.testingTextArray = testingTextArray;
    }

    /**
     * Sets testing int array.
     *
     * @param testingIntArray the testing int array
     */
    public void setTestingIntArray(int[] testingIntArray) {
        this.testingIntArray = testingIntArray;
    }

    /**
     * Sets testing long array.
     *
     * @param testingLongArray the testing long array
     */
    public void setTestingLongArray(long[] testingLongArray) {
        this.testingLongArray = testingLongArray;
    }

    /**
     * Sets testing char array.
     *
     * @param testingCharArray the testing char array
     */
    public void setTestingCharArray(char[] testingCharArray) {
        this.testingCharArray = testingCharArray;
    }

    /**
     * Sets testing short array.
     *
     * @param testingShortArray the testing short array
     */
    public void setTestingShortArray(short[] testingShortArray) {
        this.testingShortArray = testingShortArray;
    }

    /**
     * Sets testing boolean array.
     *
     * @param testingBooleanArray the testing boolean array
     */
    public void setTestingBooleanArray(boolean[] testingBooleanArray) {
        this.testingBooleanArray = testingBooleanArray;
    }

    /**
     * Sets testing byte array.
     *
     * @param testingByteArray the testing byte array
     */
    public void setTestingByteArray(byte[] testingByteArray) {
        this.testingByteArray = testingByteArray;
    }

    /**
     * Sets testing float array.
     *
     * @param testingFloatArray the testing float array
     */
    public void setTestingFloatArray(float[] testingFloatArray) {
        this.testingFloatArray = testingFloatArray;
    }

    /**
     * Sets testing double array.
     *
     * @param testingDoubleArray the testing double array
     */
    public void setTestingDoubleArray(double[] testingDoubleArray) {
        this.testingDoubleArray = testingDoubleArray;
    }

    /**
     * Sets testing timestamp array.
     *
     * @param testingTimestampArray the testing timestamp array
     */
    public void setTestingTimestampArray(Timestamp[] testingTimestampArray) {
        this.testingTimestampArray = testingTimestampArray;
    }

    /**
     * Sets testing date array.
     *
     * @param testingDateArray the testing date array
     */
    public void setTestingDateArray(Date[] testingDateArray) {
        this.testingDateArray = testingDateArray;
    }

    /**
     * Gets testing boolean.
     *
     * @return the testing boolean
     */
    public boolean getTestingBoolean() {
        return testingBoolean;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets testing text.
     *
     * @return the testing text
     */
    public String getTestingText() {
        return testingText;
    }

    /**
     * Gets testing int.
     *
     * @return the testing int
     */
    public int getTestingInt() {
        return testingInt;
    }

    /**
     * Gets testing long.
     *
     * @return the testing long
     */
    public long getTestingLong() {
        return testingLong;
    }

    /**
     * Gets testing char.
     *
     * @return the testing char
     */
    public char getTestingChar() {
        return testingChar;
    }

    /**
     * Gets testing short.
     *
     * @return the testing short
     */
    public short getTestingShort() {
        return testingShort;
    }

    /**
     * Is testing boolean boolean.
     *
     * @return the boolean
     */
    public boolean isTestingBoolean() {
        return testingBoolean;
    }

    /**
     * Gets testing byte.
     *
     * @return the testing byte
     */
    public byte getTestingByte() {
        return testingByte;
    }

    /**
     * Gets testing float.
     *
     * @return the testing float
     */
    public float getTestingFloat() {
        return testingFloat;
    }

    /**
     * Gets testing double.
     *
     * @return the testing double
     */
    public double getTestingDouble() {
        return testingDouble;
    }

    /**
     * Gets testing enum.
     *
     * @return the testing enum
     */
    public TestingEnum getTestingEnum() {
        return testingEnum;
    }

    /**
     * Gets testing timestamp.
     *
     * @return the testing timestamp
     */
    public Timestamp getTestingTimestamp() {
        return testingTimestamp;
    }

    /**
     * Gets testing date.
     *
     * @return the testing date
     */
    public Date getTestingDate() {
        return testingDate;
    }

    /**
     * Gets testing entity.
     *
     * @return the testing entity
     */
    public TestEntityOnEntity getTestingEntity() {
        return testingEntity;
    }

    /**
     * Get testing text array string [ ].
     *
     * @return the string [ ]
     */
    public String[] getTestingTextArray() {
        return testingTextArray;
    }

    /**
     * Get testing int array int [ ].
     *
     * @return the int [ ]
     */
    public int[] getTestingIntArray() {
        return testingIntArray;
    }

    /**
     * Get testing long array long [ ].
     *
     * @return the long [ ]
     */
    public long[] getTestingLongArray() {
        return testingLongArray;
    }

    /**
     * Get testing char array char [ ].
     *
     * @return the char [ ]
     */
    public char[] getTestingCharArray() {
        return testingCharArray;
    }

    /**
     * Get testing short array short [ ].
     *
     * @return the short [ ]
     */
    public short[] getTestingShortArray() {
        return testingShortArray;
    }

    /**
     * Get testing boolean array boolean [ ].
     *
     * @return the boolean [ ]
     */
    public boolean[] getTestingBooleanArray() {
        return testingBooleanArray;
    }

    /**
     * Get testing byte array byte [ ].
     *
     * @return the byte [ ]
     */
    public byte[] getTestingByteArray() {
        return testingByteArray;
    }

    /**
     * Get testing float array float [ ].
     *
     * @return the float [ ]
     */
    public float[] getTestingFloatArray() {
        return testingFloatArray;
    }

    /**
     * Get testing double array double [ ].
     *
     * @return the double [ ]
     */
    public double[] getTestingDoubleArray() {
        return testingDoubleArray;
    }

    /**
     * Get testing timestamp array timestamp [ ].
     *
     * @return the timestamp [ ]
     */
    public Timestamp[] getTestingTimestampArray() {
        return testingTimestampArray;
    }

    /**
     * Get testing date array date [ ].
     *
     * @return the date [ ]
     */
    public Date[] getTestingDateArray() {
        return testingDateArray;
    }

    /**
     * Get testing entity array test entity on entity [ ].
     *
     * @return the test entity on entity [ ]
     */
    public TestEntityOnEntity[] getTestingEntityArray() {
        return testingEntityArray;
    }

    /**
     * Get testing enum array testing enum [ ].
     *
     * @return the testing enum [ ]
     */
    public TestingEnum[] getTestingEnumArray() {
        return testingEnumArray;
    }
}
