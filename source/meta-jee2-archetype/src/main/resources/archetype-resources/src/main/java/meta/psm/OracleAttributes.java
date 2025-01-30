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

import static meta.psm.OracleAttributeKeys.DEFERRED_SEGMENT_CREATION;
import static meta.psm.OracleAttributeKeys.ENABLE_CONSTRAINT;
import static meta.psm.OracleAttributeKeys.PHYSICAL_ATTRIBUTES;
import static meta.psm.OracleAttributeKeys.STORAGE;
import static meta.psm.OracleAttributeKeys.TABLESPACE;

/**
 * @author ADALID meta-jee2-archetype, version 6.0.0
 */
public class OracleAttributes {

    @AddAttributesMethod
    public static void addAttributes(PersistentEntity entity) {
        String tablespace = TLC.getProject().getAlias().toUpperCase();
        entity.addAttribute(DEFERRED_SEGMENT_CREATION, "segment creation immediate");
        entity.addAttribute(PHYSICAL_ATTRIBUTES,
            StrUtils.getString(" ", " ", "", "",
                KVP.join("pctfree", 10),
                KVP.join("pctused", 40),
                KVP.join("initrans", 1),
                KVP.join("maxtrans", 255),
                "nocompress",
                "logging"
            )
        );
        entity.addAttribute(STORAGE,
            StrUtils.getString(" ", " ", "(", ")",
                KVP.join("initial", 65536),
                KVP.join("next", 1048576),
                KVP.join("minextents", 1),
                KVP.join("maxextents", 2147483645),
                KVP.join("pctincrease", 0),
                KVP.join("freelists", 1),
                KVP.join("freelist groups", 1),
                KVP.join("buffer_pool", "default"),
                KVP.join("flash_cache", "default"),
                KVP.join("cell_flash_cache", "default")
            )
        );
        entity.addAttribute(TABLESPACE, tablespace);
        /**/
        entity.addAttribute(entity.getPrimaryKeyProperty(), PHYSICAL_ATTRIBUTES,
            StrUtils.getString(" ", " ", "", "",
                KVP.join("pctfree", 10),
                KVP.join("initrans", 2),
                KVP.join("maxtrans", 255),
                "compute statistics"
            )
        );
        entity.addAttribute(entity.getPrimaryKeyProperty(), STORAGE,
            StrUtils.getString(" ", " ", "(", ")",
                KVP.join("initial", 65536),
                KVP.join("next", 1048576),
                KVP.join("minextents", 1),
                KVP.join("maxextents", 2147483645),
                KVP.join("pctincrease", 0),
                KVP.join("freelists", 1),
                KVP.join("freelist groups", 1),
                KVP.join("buffer_pool", "default"),
                KVP.join("flash_cache", "default"),
                KVP.join("cell_flash_cache", "default")
            )
        );
        entity.addAttribute(entity.getPrimaryKeyProperty(), TABLESPACE, tablespace);
        entity.addAttribute(entity.getPrimaryKeyProperty(), ENABLE_CONSTRAINT, "enable");
        /**/
        entity.addAttribute(entity.getVersionProperty(), PHYSICAL_ATTRIBUTES,
            StrUtils.getString(" ", " ", "", "",
                KVP.join("pctfree", 10),
                KVP.join("initrans", 2),
                KVP.join("maxtrans", 255),
                "compute statistics"
            )
        );
        entity.addAttribute(entity.getVersionProperty(), STORAGE,
            StrUtils.getString(" ", " ", "(", ")",
                KVP.join("initial", 65536),
                KVP.join("next", 1048576),
                KVP.join("minextents", 1),
                KVP.join("maxextents", 2147483645),
                KVP.join("pctincrease", 0),
                KVP.join("freelists", 1),
                KVP.join("freelist groups", 1),
                KVP.join("buffer_pool", "default"),
                KVP.join("flash_cache", "default"),
                KVP.join("cell_flash_cache", "default")
            )
        );
        entity.addAttribute(entity.getVersionProperty(), TABLESPACE, tablespace);
        entity.addAttribute(entity.getVersionProperty(), ENABLE_CONSTRAINT, "enable");
        /**/
        entity.addAttribute(entity.getBusinessKeyProperty(), PHYSICAL_ATTRIBUTES,
            StrUtils.getString(" ", " ", "", "",
                KVP.join("pctfree", 10),
                KVP.join("initrans", 2),
                KVP.join("maxtrans", 255),
                "compute statistics"
            )
        );
        entity.addAttribute(entity.getBusinessKeyProperty(), STORAGE,
            StrUtils.getString(" ", " ", "(", ")",
                KVP.join("initial", 65536),
                KVP.join("next", 1048576),
                KVP.join("minextents", 1),
                KVP.join("maxextents", 2147483645),
                KVP.join("pctincrease", 0),
                KVP.join("freelists", 1),
                KVP.join("freelist groups", 1),
                KVP.join("buffer_pool", "default"),
                KVP.join("flash_cache", "default"),
                KVP.join("cell_flash_cache", "default")
            )
        );
        entity.addAttribute(entity.getBusinessKeyProperty(), TABLESPACE, tablespace);
        entity.addAttribute(entity.getBusinessKeyProperty(), ENABLE_CONSTRAINT, "enable");
        /**/
        entity.addAttribute(entity.getNameProperty(), PHYSICAL_ATTRIBUTES,
            StrUtils.getString(" ", " ", "", "",
                KVP.join("pctfree", 10),
                KVP.join("initrans", 2),
                KVP.join("maxtrans", 255),
                "compute statistics"
            )
        );
        entity.addAttribute(entity.getNameProperty(), STORAGE,
            StrUtils.getString(" ", " ", "(", ")",
                KVP.join("initial", 65536),
                KVP.join("next", 1048576),
                KVP.join("minextents", 1),
                KVP.join("maxextents", 2147483645),
                KVP.join("pctincrease", 0),
                KVP.join("freelists", 1),
                KVP.join("freelist groups", 1),
                KVP.join("buffer_pool", "default"),
                KVP.join("flash_cache", "default"),
                KVP.join("cell_flash_cache", "default")
            )
        );
        entity.addAttribute(entity.getNameProperty(), TABLESPACE, tablespace);
        entity.addAttribute(entity.getNameProperty(), ENABLE_CONSTRAINT, "enable");
    }

    @AddAttributesMethod
    public static void addAttributes(Key key) {
        String tablespace = TLC.getProject().getAlias().toUpperCase();
        key.addAttribute(PHYSICAL_ATTRIBUTES,
            StrUtils.getString(" ", " ", "", "",
                KVP.join("pctfree", 10),
                KVP.join("initrans", 2),
                KVP.join("maxtrans", 255),
                "compute statistics"
            )
        );
        key.addAttribute(STORAGE,
            StrUtils.getString(" ", " ", "(", ")",
                KVP.join("initial", 65536),
                KVP.join("next", 1048576),
                KVP.join("minextents", 1),
                KVP.join("maxextents", 2147483645),
                KVP.join("pctincrease", 0),
                KVP.join("freelists", 1),
                KVP.join("freelist groups", 1),
                KVP.join("buffer_pool", "default"),
                KVP.join("flash_cache", "default"),
                KVP.join("cell_flash_cache", "default")
            )
        );
        key.addAttribute(TABLESPACE, tablespace);
        key.addAttribute(ENABLE_CONSTRAINT, "enable");
    }

}
