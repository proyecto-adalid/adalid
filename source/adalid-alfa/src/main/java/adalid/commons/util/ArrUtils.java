/*
 * Copyright 2017 Jorge Campins y David Uzcategui
 *
 * Este archivo forma parte de Adalid.
 *
 * Adalid es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos de la
 * licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Adalid se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA; sin
 * siquiera las garantias implicitas de COMERCIALIZACION e IDONEIDAD PARA UN PROPOSITO PARTICULAR.
 *
 * Para mas detalles vea la licencia "GNU General Public License" en http://www.gnu.org/licenses
 */
package adalid.commons.util;

import java.lang.reflect.Array;

/**
 * @author Jorge Campins
 */
public class ArrUtils {

    @SuppressWarnings("unchecked")
    public static <T> T[] arrayOf(Class<T> clazz) {
        return clazz == null ? null : (T[]) Array.newInstance(clazz, 0);
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] arrayOf(T value) {
        if (value == null) {
            return null;
        }
        T[] array = (T[]) Array.newInstance(value.getClass(), 1);
        array[0] = value;
        return array;
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] addAll(T[] array1, T... array2) {
        if (array1 == null || array1.length == 0) {
            return clone(array2);
        }
        if (array2 == null || array2.length == 0) {
            return clone(array1);
        }
        Class<?> type1 = array1.getClass().getComponentType();
        T[] joinedArray = (T[]) Array.newInstance(type1, array1.length + array2.length);
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        try {
            System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        } catch (ArrayStoreException ase) {
            Class<?> type2 = array2.getClass().getComponentType();
            if (type1.isAssignableFrom(type2)) {
                throw ase;
            }
            throw new IllegalArgumentException("cannot store " + type2.getName() + " in an array of " + type1.getName(), ase);
        }
        return joinedArray;
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] join(Class<T> type, T[]... arrays) {
        if (type == null || arrays == null || arrays.length == 0) {
            return null;
        }
        int length = 0;
        for (T[] array : arrays) {
            if (array != null) {
                length += array.length;
            }
        }
        if (length == 0) {
            return null;
        }
        T[] join = (T[]) Array.newInstance(type, length);
        int position = 0;
        for (T[] array : arrays) {
            if (array != null && array.length > 0) {
                System.arraycopy(array, 0, join, position, array.length);
                position += array.length;
            }
        }
        return join;
    }

    public static <T> T[] clone(T[] array) {
        return array == null ? null : array.clone();
    }

}
