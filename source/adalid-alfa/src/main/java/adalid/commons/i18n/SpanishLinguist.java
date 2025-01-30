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
public class SpanishLinguist implements Linguist {

    @Override
    public Set<String> getArticles() {
        return SpanishNoun.getArticles();
    }

    @Override
    public Set<String> getConjunctions() {
        return SpanishNoun.getConjunctions();
    }

    @Override
    public Set<String> getPrepositions() {
        return SpanishNoun.getPrepositions();
    }

    @Override
    public String capitalize(String noun) {
        return SpanishNoun.capitalize(noun);
    }

    @Override
    public boolean isPlural(String word) {
        return SpanishNoun.isPlural(word);
    }

    @Override
    public boolean isSingular(String word) {
        return SpanishNoun.isSingular(word);
    }

    @Override
    public String pluralOf(String word) {
        return SpanishNoun.pluralOf(word);
    }

    @Override
    public String pluralOfMultiwordExpression(String mwe) {
        return SpanishNoun.pluralOfMultiwordExpression(mwe);
    }

    @Override
    public String singularOf(String word) {
        return SpanishNoun.singularOf(word);
    }

    @Override
    public String singularOfMultiwordExpression(String mwe) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
