/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.interfaces;

import adalid.commons.interfaces.Programmer;

/**
 * @author Jorge Campins
 */
public interface BundleProgrammer extends Programmer {

    String getKeyString(Artifact artifact);

    String getKeyString(String string);

    String getValueString(String string);

    String getSqlSchemaQualifiedKey(PersistentEntity entity);

    String getSqlSchemaQualifiedShortKey(PersistentEntity entity);

    String getSqlSchemaUnqualifiedShortKey(PersistentEntity entity);

}
