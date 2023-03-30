/*
 * Copyright 2017 Jorge Campins y David Uzcategui
 *
 * Este archivo forma parte de Adalid.
 *
 * Adalid es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos de la
 * licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Adalid se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA; sin
 * siquiera las garantias implicitas de COMERCIALIZACION e IDONEIDAD PARA UN PROPOSITO PARTICULAR.
 *
 * Para mas detalles vea la licencia "GNU General Public License" en http://www.gnu.org/licenses
 */
package adalid.core.interfaces;

import java.io.Serializable;

/**
 * @author Jorge Campins
 */
public interface DataArtifact extends Nullable, Serializable, TypedArtifact {

    /**
     * @return the data class
     */
    Class<?> getDataClass();

    /**
     * @return the segment entity class
     */
    Class<?> getSegmentEntityClass(); // since 20210218

    /**
     * @return the Serial Version UID of the data class
     */
    Long getSerialVersionUID(); // since 19/01/2023

    /**
     * @return true if is a Parameter; otherwise false
     */
    boolean isParameter();

    /**
     * @return true if is a Property; otherwise false
     */
    boolean isProperty();

}
