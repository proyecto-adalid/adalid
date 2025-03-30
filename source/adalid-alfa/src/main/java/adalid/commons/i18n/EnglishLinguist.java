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

import java.util.Set;

/**
 * @author Jorge Campins
 */
public class EnglishLinguist implements Linguist {

    @Override
    public Set<String> getArticles() {
        return EnglishNoun.getArticles();
    }

    @Override
    public Set<String> getConjunctions() {
        return EnglishNoun.getConjunctions();
    }

    @Override
    public Set<String> getPrepositions() {
        return EnglishNoun.getPrepositions();
    }

    @Override
    public String capitalize(String noun) {
        return EnglishNoun.capitalize(noun);
    }

    @Override
    public boolean isPlural(String word) {
        return EnglishNoun.isPlural(word);
    }

    @Override
    public boolean isSingular(String word) {
        return EnglishNoun.isSingular(word);
    }

    @Override
    public String pluralOf(String word) {
        return EnglishNoun.pluralOf(word);
    }

    @Override
    public String pluralOfMultiwordExpression(String mwe) {
        return EnglishNoun.pluralOfMultiwordExpression(mwe);
    }

    @Override
    public String singularOf(String word) {
        return EnglishNoun.singularOf(word);
    }

    @Override
    public String singularOfMultiwordExpression(String mwe) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
