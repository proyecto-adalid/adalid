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
package adalid.core.annotations;

import adalid.core.enums.*;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * La anotación BooleanField se utiliza para establecer atributos de propiedades y parámetros Boolean.
 *
 * @author Jorge Campins
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface BooleanField {

    /**
     * displayType indica el tipo de componente que se utiliza para mostrar el valor de la propiedad en las vistas (páginas) de consulta y registro, o
     * del parámetro en las vistas (páginas) de ejecución de operaciones de negocio. Su valor es uno de los elementos de la enumeración
     * BooleanDisplayType. Seleccione DROPDOWN, CHECKBOX o TOGGLE para utilizar una lista desplegable, una casilla de verificación o un interruptor de
     * palanca, respectivamente. Las opciones CHECKBOX y TOGGLE solo aplican si la columna de la base de datos que corresponde a la propiedad no
     * permite valores nulos (vea el elemento nullable de la Anotación ColumnField), o si el parámetro es requerido (vea el elemento required de la
     * Anotación ParameterField). Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El
     * valor predeterminado del atributo es DROPDOWN si la columna de la base de datos que corresponde a la propiedad permite valores nulos, o si el
     * parámetro no es requerido; en caso contrario, el valor establecido mediante el método setDefaultBooleanDisplayType del proyecto Maestro.
     *
     * @return displayType
     */
    BooleanDisplayType displayType() default BooleanDisplayType.UNSPECIFIED; // DROPDOWN

}
