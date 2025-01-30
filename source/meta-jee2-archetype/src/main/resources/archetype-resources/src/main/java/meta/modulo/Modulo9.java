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
public class Modulo9 extends adalid.core.Project {

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        /**/
        setLocalizedLabel(SPANISH, "Módulo 9");
        setLocalizedDescription(SPANISH, "Descripción del Módulo 9");
        setLocalizedShortDescription(SPANISH, "Descripción corta del Módulo 9");
        /**/
        setLocalizedLabel(ENGLISH, "Module 9");
        setLocalizedDescription(ENGLISH, "Module 9 description");
        setLocalizedShortDescription(ENGLISH, "Module 9 short description");
        /**/
    }

}
