#set($dollar = '$')
#set($pound = '#')
#set($backslash = '\')
/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package ${package}.meta.psm;

import adalid.commons.util.*;
import adalid.core.*;
import adalid.core.annotations.*;
import adalid.core.interfaces.*;

import static meta.psm.PostgreSQLAttributeKeys.AUTOVACUUM_ENABLED;
import static meta.psm.PostgreSQLAttributeKeys.FILLFACTOR;
import static meta.psm.PostgreSQLAttributeKeys.TABLESPACE;
import static meta.psm.PostgreSQLAttributeKeys.WITH;

/**
 * @author ADALID meta-jee2-archetype
 */
public class PostgreSQLAttributes {

    @AddAttributesMethod
    public static void addAttributes(PersistentEntity entity) {
        String tablespace = TLC.getProject().getAlias();
        entity.addAttribute(WITH,
            KVP.join(FILLFACTOR, 80),
            KVP.join(AUTOVACUUM_ENABLED, true)
        );
        entity.addAttribute(TABLESPACE, tablespace);
        entity.addAttribute(entity.getPrimaryKeyProperty(), WITH, KVP.join(FILLFACTOR, 100));
//      entity.addAttribute(entity.getPrimaryKeyProperty(), TABLESPACE, tablespace);
        entity.addAttribute(entity.getVersionProperty(), WITH, KVP.join(FILLFACTOR, 100));
//      entity.addAttribute(entity.getVersionProperty(), TABLESPACE, tablespace);
        entity.addAttribute(entity.getBusinessKeyProperty(), WITH, KVP.join(FILLFACTOR, 90));
//      entity.addAttribute(entity.getBusinessKeyProperty(), TABLESPACE, tablespace);
        entity.addAttribute(entity.getNameProperty(), WITH, KVP.join(FILLFACTOR, 80));
//      entity.addAttribute(entity.getNameProperty(), TABLESPACE, tablespace);
    }

    @AddAttributesMethod
    public static void addAttributes(Key key) {
//      String tablespace = getTablespace();
        key.addAttribute(WITH, KVP.join(FILLFACTOR, 90));
//      key.addAttribute(TABLESPACE, tablespace);
    }

}
