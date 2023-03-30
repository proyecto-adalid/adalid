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
 * @author ADALID meta-jee2-archetype
 */
public class Modulo3 extends adalid.core.Project {

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        /**/
        setLocalizedLabel(SPANISH, "Módulo 3");
        setLocalizedDescription(SPANISH, "Descripción del Módulo 3");
        setLocalizedShortDescription(SPANISH, "Descripción corta del Módulo 3");
        /**/
        setLocalizedLabel(ENGLISH, "Module 3");
        setLocalizedDescription(ENGLISH, "Module 3 description");
        setLocalizedShortDescription(ENGLISH, "Module 3 short description");
        /**/
    }

}
