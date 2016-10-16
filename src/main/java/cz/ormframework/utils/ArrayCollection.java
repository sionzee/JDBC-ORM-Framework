package cz.ormframework.utils;

import java.lang.reflect.Array;

/**
 * Created by siOnzee on 21.09.2016.
 */
public class ArrayCollection<ArrayType> {

    private Class<ArrayType> clazz;
    private ArrayType[] array;

    public int length;

    private ArrayType[] newInstance(int length) {
        return (ArrayType[]) Array.newInstance(clazz, length);
    }

    public static <A> ArrayCollection<A> of(A...arrayTypes) {
        return new ArrayCollection<A>((Class<A>) arrayTypes.getClass().getComponentType(), arrayTypes);
    }

    public static <A> ArrayCollection<A> of(ArrayCollection<A> array) {
        return array.clone();
    }

    public ArrayCollection(Class<ArrayType> clazz, int length) {
        this.clazz = clazz;
        array = newInstance(length);
        this.length = length;
    }

    public ArrayCollection(Class<ArrayType> clazz, ArrayType[] array) {
        this.clazz = clazz;
        this.array = (ArrayType[]) Array.newInstance(clazz, array.length);
        System.arraycopy(array, 0, this.array, 0, array.length);
        this.length = array.length;
    }

    public ArrayType insert(ArrayType arrayType) {
        ArrayType[] array = newInstance(this.array.length + 1);
        System.arraycopy(this.array, 0, array, 0, this.array.length);
        array[this.array.length] = arrayType;
        this.array = array;
        this.length = array.length;
        return array[this.array.length - 1];
    }

    /*public ArrayType insertAt(ArrayType arrayType, int index) {

    }*/

    public ArrayType remove(ArrayType arrayType) {
        ArrayType[] array = newInstance(this.array.length + 1);
        ArrayType entity = null;
        int index = -1;

        for(int i = 0; i < this.array.length; i++) {
            ArrayType value = get(i);
            if(value == arrayType) {
                entity = value;
                index = i;
                break;
            }
        }

        if(index < 0)
            return null;

        ArrayType[] result = newInstance(this.array.length - 1);
        System.arraycopy(this.array, 0, result, 0, index - 1);
        System.arraycopy(this.array, index + 1, result, index + 1, this.array.length - index);
        this.array = result;
        this.length = array.length;
        return entity;
    }

    /*public ArrayType removeAt(ArrayType arrayType, int index) {

    }*/

    public ArrayType get(int index) {
        return array[index];
    }

    public ArrayType[] toArray() {
        return array;
    }

    public ArrayCollection<ArrayType> clone() {
        return new ArrayCollection<ArrayType>(clazz, array);
    }

}
