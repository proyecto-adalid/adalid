/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.commons.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ComparatorUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.PredicateUtils;

/**
 * @author Jorge Campins
 */
public class ColUtils {

    public static <T> Collection<T> filter(Collection<T> collection, Predicate predicate) {
        if (collection == null || collection.isEmpty() || predicate == null) {
            return collection;
        } else {
//          Collection<T> list = new ArrayList<T>();
//          list.addAll(collection);
            Collection<T> list = new ArrayList<>(collection);
            CollectionUtils.filter(list, predicate);
            return list;
        }
    }

    public static <T> Collection<T> filter(Collection<T> collection, Predicate... predicates) {
        return allFilter(collection, predicates);
    }

    public static <T> Collection<T> allFilter(Collection<T> collection, Predicate... predicates) {
        if (collection == null || collection.isEmpty() || predicates == null) {
            return collection;
        } else {
//          Collection<T> list = new ArrayList<T>();
//          list.addAll(collection);
            Collection<T> list = new ArrayList<>(collection);
            Predicate predicate = PredicateUtils.allPredicate(predicates);
            CollectionUtils.filter(list, predicate);
            return list;
        }
    }

    public static <T> Collection<T> anyFilter(Collection<T> collection, Predicate... predicates) {
        if (collection == null || collection.isEmpty() || predicates == null) {
            return collection;
        } else {
//          Collection<T> list = new ArrayList<T>();
//          list.addAll(collection);
            Collection<T> list = new ArrayList<>(collection);
            Predicate predicate = PredicateUtils.anyPredicate(predicates);
            CollectionUtils.filter(list, predicate);
            return list;
        }
    }

    public static <T> Collection<T> oneFilter(Collection<T> collection, Predicate... predicates) {
        if (collection == null || collection.isEmpty() || predicates == null) {
            return collection;
        } else {
//          Collection<T> list = new ArrayList<T>();
//          list.addAll(collection);
            Collection<T> list = new ArrayList<>(collection);
            Predicate predicate = PredicateUtils.onePredicate(predicates);
            CollectionUtils.filter(list, predicate);
            return list;
        }
    }

    public static <T> Collection<T> noneFilter(Collection<T> collection, Predicate... predicates) {
        if (collection == null || collection.isEmpty() || predicates == null) {
            return collection;
        } else {
//          Collection<T> list = new ArrayList<T>();
//          list.addAll(collection);
            Collection<T> list = new ArrayList<>(collection);
            Predicate predicate = PredicateUtils.nonePredicate(predicates);
            CollectionUtils.filter(list, predicate);
            return list;
        }
    }

    public static <T extends Comparable<? super T>> Collection<T> sort(Collection<T> collection) {
        if (collection instanceof List && !collection.isEmpty()) {
            List<T> list = (List<T>) collection;
            Collections.sort(list);
        }
        return collection;
    }

    public static <T> Collection<T> sort(Collection<T> collection, Comparator<T> comparator) {
        if (collection instanceof List && !collection.isEmpty() && comparator != null) {
            List<T> list = (List<T>) collection;
            Collections.sort(list, comparator);
        }
        return collection;
    }

    @SuppressWarnings("unchecked") // unchecked cast
    public static <T> Collection<T> sort(Collection<T> collection, Comparator<T>... comparators) {
        if (collection instanceof List && !collection.isEmpty() && comparators != null) {
            List<T> list = (List<T>) collection;
            Comparator<T> comparator = (Comparator<T>) ComparatorUtils.chainedComparator(comparators); // unchecked cast
            Collections.sort(list, comparator);
        }
        return collection;
    }

}
