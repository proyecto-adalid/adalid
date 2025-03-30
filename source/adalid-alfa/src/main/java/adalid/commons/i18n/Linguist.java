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
package adalid.commons.i18n;

import adalid.commons.util.StrUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
public interface Linguist {

    Set<String> getArticles();

    Set<String> getConjunctions();

    Set<String> getPrepositions();

    /**
     * Returns the noun without conjunctions and prepositions.
     *
     * @param noun a noun
     * @return the noun without conjunctions and prepositions
     */
    default String prune(String noun) {
        String trimmed = StringUtils.trimToNull(noun);
        if (trimmed == null) {
            return noun;
        }
        String lc;
        String[] words = trimmed.contains(" ") ? StringUtils.split(trimmed) : StringUtils.splitByCharacterTypeCamelCase(trimmed);
        Set<String> conjunctions = getConjunctions();
        Set<String> prepositions = getPrepositions();
        List<String> list = new ArrayList<>();
        for (String word : words) {
            lc = word.toLowerCase();
            if (conjunctions.contains(lc) || prepositions.contains(lc)) {
                continue;
            }
            list.add(word);
        }
        return list.isEmpty() ? noun : StringUtils.join(list, " ");
    }

    /**
     * Tests whether the given nouns produce the same string after pruning them, removing their diacritics and converting them to lowercase.
     *
     * @param noun a noun
     * @param anotherNoun another noun
     * @return true if the given nouns produce the same string after pruning them, removing their diacritics and converting them to lowercase; false
     * otherwise
     */
    default boolean equalAfterNormalization(String noun, String anotherNoun) {
        return StringUtils.isNotBlank(noun)
            && StringUtils.isNotBlank(anotherNoun)
            && StrUtils.diacriticless(prune(noun)).toLowerCase().equals(StrUtils.diacriticless(prune(anotherNoun)).toLowerCase());
    }

    String capitalize(String noun);

    boolean isPlural(String word);

    boolean isSingular(String word);

    String pluralOf(String word);

    String pluralOfMultiwordExpression(String mwe);

    String singularOf(String word);

    String singularOfMultiwordExpression(String mwe);

}
