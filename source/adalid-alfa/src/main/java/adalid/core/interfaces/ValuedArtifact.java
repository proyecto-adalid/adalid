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

/**
 * @author Jorge Campins
 */
public interface ValuedArtifact extends DataArtifact {

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

    /**
     * @return the initial value tag
     */
    String getInitialValueTag();

    /**
     * El método setInitialValueTag se utiliza para establecer la descripción del valor inicial de propiedades y parámetros que se almacena en el
     * archivo de recursos por defecto. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la
     * interfaz de la aplicación utiliza el archivo de recursos por defecto para obtener el valor de la descripción.
     *
     * @param tag una o más oraciones que describen muy brevemente el valor inicial de la propiedad o el parámetro
     */
    void setInitialValueTag(String tag);

    /**
     * @return the default value tag
     */
    String getDefaultValueTag();

    /**
     * El método setDefaultValueTag se utiliza para establecer la descripción del valor por omisión de propiedades y parámetros que se almacena en el
     * archivo de recursos por defecto. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la
     * interfaz de la aplicación utiliza el archivo de recursos por defecto para obtener el valor de la descripción.
     *
     * @param tag una o más oraciones que describen muy brevemente el valor por omisión de la propiedad o el parámetro
     */
    void setDefaultValueTag(String tag);

    /**
     * @return the current value tag
     */
    String getCurrentValueTag();

    /**
     * El método setCurrentValueTag se utiliza para establecer la descripción del valor actual de propiedades y parámetros que se almacena en el
     * archivo de recursos por defecto. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la
     * interfaz de la aplicación utiliza el archivo de recursos por defecto para obtener el valor de la descripción.
     *
     * @param tag una o más oraciones que describen muy brevemente el valor actual de la propiedad o el parámetro
     */
    void setCurrentValueTag(String tag);

}
