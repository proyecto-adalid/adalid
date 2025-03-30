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
public interface CalculableProperty extends Property {

    /**
     * @return the calculable value
     */
    Object getCalculableValue();

    /**
     * @return the calculable value tag
     */
    String getCalculableValueTag();

    /**
     * El método setCalculableValueTag se utiliza para establecer la descripción del valor calculable de propiedades y parámetros que se almacena en
     * el archivo de recursos por defecto. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la
     * interfaz de la aplicación utiliza el archivo de recursos por defecto para obtener el valor de la descripción.
     *
     * @param tag una o más oraciones que describen muy brevemente el valor calculable de la propiedad o el parámetro
     */
    void setCalculableValueTag(String tag);

}
