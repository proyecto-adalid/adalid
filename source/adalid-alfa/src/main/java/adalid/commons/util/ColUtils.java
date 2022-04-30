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

import java.util.ArrayList;
import java.util.Collection;
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
        return collection == null || collection.isEmpty() || predicate == null ? collection
            : _filter(collection, predicate);
    }

    public static <T> Collection<T> filter(Collection<T> collection, Predicate... predicates) {
        return allFilter(collection, predicates);
    }

    public static <T> Collection<T> allFilter(Collection<T> collection, Predicate... predicates) {
        return collection == null || collection.isEmpty() || predicates == null || predicates.length == 0 ? collection
            : _filter(collection, PredicateUtils.allPredicate(predicates));
    }

    public static <T> Collection<T> anyFilter(Collection<T> collection, Predicate... predicates) {
        return collection == null || collection.isEmpty() || predicates == null || predicates.length == 0 ? collection
            : _filter(collection, PredicateUtils.anyPredicate(predicates));
    }

    public static <T> Collection<T> oneFilter(Collection<T> collection, Predicate... predicates) {
        return collection == null || collection.isEmpty() || predicates == null || predicates.length == 0 ? collection
            : _filter(collection, PredicateUtils.onePredicate(predicates));
    }

    public static <T> Collection<T> noneFilter(Collection<T> collection, Predicate... predicates) {
        return collection == null || collection.isEmpty() || predicates == null || predicates.length == 0 ? collection
            : _filter(collection, PredicateUtils.nonePredicate(predicates));
    }

    private static <T> List<T> _filter(Collection<T> collection, Predicate predicate) {
        List<T> list = new ArrayList<>(collection);
        CollectionUtils.filter(list, predicate);
        return list;
    }

    public static <T extends Comparable<? super T>> Collection<T> sort(Collection<T> collection) {
        return collection == null || collection.isEmpty() ? collection
            : _sort(collection, null);
    }

    public static <T> Collection<T> sort(Collection<T> collection, Comparator<T> comparator) {
        return collection == null || collection.isEmpty() || comparator == null ? collection
            : _sort(collection, comparator);
    }

    @SuppressWarnings("unchecked") // unchecked cast
    public static <T> Collection<T> sort(Collection<T> collection, Comparator<T>... comparators) {
        return collection == null || collection.isEmpty() || comparators == null || comparators.length == 0 ? collection
            : _sort(collection, (Comparator<T>) ComparatorUtils.chainedComparator(comparators));
    }

    private static <T> List<T> _sort(Collection<T> collection, Comparator<T> comparator) {
        List<T> list = new ArrayList<>(collection);
        list.sort(comparator);
        return list;
    }

    public static <T> List<T> toList(Collection<T> collection) {
        return collection == null ? null : collection instanceof List ? (List<T>) collection : new ArrayList<>(collection);
    }

}
