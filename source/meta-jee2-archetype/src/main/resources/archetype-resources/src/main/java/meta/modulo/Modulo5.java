#set($dollar = '$')
#set($pound = '#')
#set($backslash = '\')
/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package ${package}.meta.modulo;

/**
 * @author ADALID meta-jee2-archetype, version 6.0.0
 */
public class Modulo5 extends adalid.core.Project {

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        /**/
        setLocalizedLabel(SPANISH, "Módulo 5");
        setLocalizedDescription(SPANISH, "Descripción del Módulo 5");
        setLocalizedShortDescription(SPANISH, "Descripción corta del Módulo 5");
        /**/
        setLocalizedLabel(ENGLISH, "Module 5");
        setLocalizedDescription(ENGLISH, "Module 5 description");
        setLocalizedShortDescription(ENGLISH, "Module 5 short description");
        /**/
    }

}
