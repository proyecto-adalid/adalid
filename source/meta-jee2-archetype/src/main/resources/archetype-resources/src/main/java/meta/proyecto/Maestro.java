#set($dollar = '$')
#set($pound = '#')
#set($backslash = '\')
/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package ${package}.meta.proyecto;

import adalid.core.*;
import adalid.core.annotations.*;
import adalid.core.enums.*;
import adalid.jee2.meta.proyecto.base.*;
import ${package}.meta.modulo.*;
import meta.proyecto.comun.*;

/**
 * @author ADALID meta-jee2-archetype, version 6.1.0
 */
public abstract class Maestro extends ProyectoPrime {

    public static final String ACRONYM = "jee2";

    /**
     * Guía de Operación, Proyecto Adalid, Plataforma jee2
     */
    public static final String HELP_DOCUMENT_URL = adalid.jee2.constants.JEE2.OPERACION_PDF;

    @Override
    protected void setStaticAttributes() {
        super.setStaticAttributes();
        /**/
        InstanceTag.BLUE.setPillShaped(true);
        InstanceTag.BLUISH.setPillShaped(true);
        InstanceTag.CYAN.setPillShaped(true);
        InstanceTag.CYANISH.setPillShaped(true);
        InstanceTag.GREEN.setPillShaped(true);
        InstanceTag.GREENISH.setPillShaped(true);
        InstanceTag.LIGHT_GREEN.setPillShaped(true);
        InstanceTag.ORANGE.setPillShaped(true);
        InstanceTag.ORANGISH.setPillShaped(true);
        InstanceTag.PURPLE.setPillShaped(true);
        InstanceTag.PURPLISH.setPillShaped(true);
        InstanceTag.RED.setPillShaped(true);
        InstanceTag.REDISH.setPillShaped(true);
        InstanceTag.SANDY_BEACH.setPillShaped(true);
        InstanceTag.YELLOW.setPillShaped(true);
        InstanceTag.YELLOWISH.setPillShaped(true);
        /**/
        setDefaultEntityTableViewResponsiveMode(TableResponsiveMode.AUTO);
        /**/
    }

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        /**/
        setLocalizedLabel(SPANISH, "Gestión de Recursos Empresariales");
        setLocalizedShortDescription(SPANISH, "Gestión de Recursos Empresariales");
        setLocalizedDescription(SPANISH, ""
            + "Gestión de Recursos Empresariales es un sistema de planificación de recursos empresariales o ERP, por las siglas en inglés de "
            + "Enterprise Resource Planning; es un sistema de información gerencial que integra y maneja muchos de los aspectos asociados con las "
            + "operaciones de producción y distribución de compañías en la producción de bienes y/o servicios. "
            + "");
        /**/
        setLocalizedLabel(ENGLISH, "Enterprise Resource Management");
        setLocalizedShortDescription(ENGLISH, "Enterprise Resource Management");
        setLocalizedDescription(ENGLISH, ""
            + "Enterprise Resource Management is an enterprise resource planning system; "
            + "it is a management information system that integrates and manages many of the aspects associated with the "
            + "production and distribution operations of companies in the production of goods and/or services. "
            + "");
        /**/
//      setHelpDocument(iframe("https://www.youtube.com/embed/yyF1rWcYYiA", 960, 540)); // URL del video tutorial almacenado en YouTube
        setHelpFileAutoName(HelpFileAutoName.ENTITY);
        setHelpFileAutoType("xhtml");
        /**/
    }

    @ProjectModule(menu = Kleenean.TRUE, role = Kleenean.TRUE)
    protected Modulo1 mod1;

    @ProjectModule(menu = Kleenean.TRUE, role = Kleenean.TRUE)
    protected Modulo2 mod2;

    @ProjectModule(menu = Kleenean.TRUE, role = Kleenean.TRUE)
    protected Modulo3 mod3;

    @ProjectModule(menu = Kleenean.TRUE, role = Kleenean.TRUE)
    protected Modulo4 mod4;

    @ProjectModule(menu = Kleenean.TRUE, role = Kleenean.TRUE)
    protected Modulo5 mod5;

    @ProjectModule(menu = Kleenean.TRUE, role = Kleenean.TRUE)
    protected Modulo6 mod6;

    @ProjectModule(menu = Kleenean.TRUE, role = Kleenean.TRUE)
    protected Modulo7 mod7;

    @ProjectModule(menu = Kleenean.TRUE, role = Kleenean.TRUE)
    protected Modulo8 mod8;

    @ProjectModule(menu = Kleenean.TRUE, role = Kleenean.TRUE)
    protected Modulo9 mod9;

    @ProjectModule(menu = Kleenean.TRUE, role = Kleenean.TRUE, roleTypes = {RoleType.PROCESSOR, RoleType.READER})
    protected Auditoria modx1;

    @ProjectModule(menu = Kleenean.TRUE, role = Kleenean.TRUE)
    protected ControlAcceso modx2;

    @ProjectModule(role = Kleenean.TRUE, roleTypes = RoleType.PROCESSOR)
    protected ControlProcesos modx3;

    @ProjectModule(role = Kleenean.TRUE, roleTypes = {RoleType.PROCESSOR, RoleType.READER})
    protected ControlServicios modx4;

    @ProjectModule(role = Kleenean.TRUE, roleTypes = {RoleType.PROCESSOR, RoleType.READER})
    protected ControlTareas modx5;

}
