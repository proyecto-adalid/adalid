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

import java.util.Locale;

/**
 * @author Jorge Campins
 */
public interface IntervalizedArtifact extends BoundedArtifact, DataArtifact {

    /**
     * @return the default range error message
     */
    String getDefaultRangeErrorMessage();

    /**
     * El método setDefaultRangeErrorMessage se utiliza para establecer el mensaje de error asociado al rango de valores que se almacena en el archivo
     * de recursos por defecto. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la interfaz de la
     * aplicación utiliza el archivo de recursos por defecto para obtener el valor del mensaje.
     *
     * El rango de valores es definido mediante los métodos setMinValue y setMaxValue de propiedades y parámetros numéricos y temporales.
     *
     * @param message mensaje de error. El mensaje puede contener los marcadores de posición {0}, {1} y {2} para mostrar al usuario el valor
     * suministrado, el valor mínimo permitido y el valor máximo permitido, respectivamente.
     */
    void setDefaultRangeErrorMessage(String message);

    /**
     * @param locale the locale for the range error message
     * @return the localized range error message
     */
    String getLocalizedRangeErrorMessage(Locale locale);

    /**
     * El método setLocalizedRangeErrorMessage se utiliza para establecer el mensaje de error asociado al rango de valores que se almacena en el
     * archivo de recursos por defecto. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la
     * interfaz de la aplicación utiliza el archivo de recursos por defecto para obtener el valor del mensaje.
     *
     * El rango de valores es definido mediante los métodos setMinValue y setMaxValue de propiedades y parámetros numéricos y temporales.
     *
     * @param locale configuración regional
     * @param message mensaje de error. El mensaje puede contener los marcadores de posición {0}, {1} y {2} para mostrar al usuario el valor
     * suministrado, el valor mínimo permitido y el valor máximo permitido, respectivamente.
     */
    void setLocalizedRangeErrorMessage(Locale locale, String message);

}
