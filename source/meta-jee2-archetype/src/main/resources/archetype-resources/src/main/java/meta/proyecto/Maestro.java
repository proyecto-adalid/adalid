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

import adalid.core.annotations.*;
import adalid.core.enums.*;
import adalid.jee2.meta.proyecto.base.*;
import ${package}.meta.modulo.*;
import meta.proyecto.comun.*;

/**
 * @author ADALID meta-jee2-archetype
 */
public abstract class Maestro extends ProyectoPrime {

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
    }

    // <editor-fold defaultstate="collapsed" desc="print">
    @Override
    public void print() {
        System.out.println(mod1);
        System.out.println(mod2);
        System.out.println(mod3);
        System.out.println(mod4);
        System.out.println(mod5);
        System.out.println(mod6);
        System.out.println(mod7);
        System.out.println(mod8);
        System.out.println(mod9);
        System.out.println(modx1);
        System.out.println(modx2);
        System.out.println(modx3);
        System.out.println(modx4);
        System.out.println(modx5);
    }
    // </editor-fold>

    @ProjectModule(menu = Kleenean.TRUE, role = Kleenean.TRUE)
    Modulo1 mod1;

    @ProjectModule(menu = Kleenean.TRUE, role = Kleenean.TRUE)
    Modulo2 mod2;

    @ProjectModule(menu = Kleenean.TRUE, role = Kleenean.TRUE)
    Modulo3 mod3;

    @ProjectModule(menu = Kleenean.TRUE, role = Kleenean.TRUE)
    Modulo4 mod4;

    @ProjectModule(menu = Kleenean.TRUE, role = Kleenean.TRUE)
    Modulo5 mod5;

    @ProjectModule(menu = Kleenean.TRUE, role = Kleenean.TRUE)
    Modulo6 mod6;

    @ProjectModule(menu = Kleenean.TRUE, role = Kleenean.TRUE)
    Modulo7 mod7;

    @ProjectModule(menu = Kleenean.TRUE, role = Kleenean.TRUE)
    Modulo8 mod8;

    @ProjectModule(menu = Kleenean.TRUE, role = Kleenean.TRUE)
    Modulo9 mod9;

    @ProjectModule(menu = Kleenean.TRUE, role = Kleenean.TRUE, roleTypes = {RoleType.PROCESSOR, RoleType.READER})
    Auditoria modx1;

    @ProjectModule(menu = Kleenean.TRUE, role = Kleenean.TRUE)
    ControlAcceso modx2;

    @ProjectModule(role = Kleenean.TRUE, roleTypes = RoleType.PROCESSOR)
    ControlProcesos modx3;

    @ProjectModule(role = Kleenean.TRUE, roleTypes = {RoleType.PROCESSOR, RoleType.READER})
    ControlServicios modx4;

    @ProjectModule(role = Kleenean.TRUE, roleTypes = {RoleType.PROCESSOR, RoleType.READER})
    ControlTareas modx5;

}
