/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.interfaces;

/**
 * @author Jorge
 */
public interface ValuedArtifact extends Artifact {

    /**
     * @return the initial value
     */
    Object getInitialValue();

    /**
     * @return the default value
     */
    Object getDefaultValue();

    /**
     * @return the current value
     */
    Object getCurrentValue();

}
