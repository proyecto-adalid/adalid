/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.commons.i18n;

/**
 * @author Jorge Campins
 */
public class SpanishLinguist implements Linguist {

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
