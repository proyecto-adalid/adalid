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
public class Modulo7 extends adalid.core.Project {

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        /**/
        setLocalizedLabel(SPANISH, "Módulo 7");
        setLocalizedDescription(SPANISH, "Descripción del Módulo 7");
        setLocalizedShortDescription(SPANISH, "Descripción corta del Módulo 7");
        /**/
        setLocalizedLabel(ENGLISH, "Module 7");
        setLocalizedDescription(ENGLISH, "Module 7 description");
        setLocalizedShortDescription(ENGLISH, "Module 7 short description");
        /**/
    }

}
