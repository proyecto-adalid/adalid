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
        setDefaultLabel("Módulo 7");
        setDefaultDescription("Módulo 7");
        /**/
        setLocalizedLabel(ENGLISH, "Module 7");
        setLocalizedDescription(ENGLISH, "Module 7");
        /**/
    }

}