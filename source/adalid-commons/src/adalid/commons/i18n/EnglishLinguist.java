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
public class EnglishLinguist implements Linguist {

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
