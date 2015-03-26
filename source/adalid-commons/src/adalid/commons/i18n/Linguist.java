/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.commons.i18n;

/**
 * @author Jorge
 */
public interface Linguist {

    boolean isPlural(String word);

    boolean isSingular(String word);

    String pluralOf(String word);

    String pluralOfMultiwordExpression(String mwe);

    String singularOf(String word);

    String singularOfMultiwordExpression(String mwe);

}
