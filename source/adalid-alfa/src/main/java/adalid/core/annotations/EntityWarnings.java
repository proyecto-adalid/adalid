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
 * La anotación EntityWarnings se utiliza para controlar la emisión de mensajes de advertencia al construir y generar la aplicación.
 *
 * @author Jorge Campins
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EntityWarnings {

    /**
     * enabled indica si se debe, o no, emitir mensajes de advertencia. Su valor es uno de los elementos de la enumeración Kleenean. Seleccione TRUE
     * para emitir los mensajes; en caso contrario, seleccione FALSE. Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el
     * valor predeterminado del atributo. El valor predeterminado del atributo es TRUE.
     *
     * @return enabled
     */
    Kleenean enabled() default Kleenean.UNSPECIFIED; // TRUE

    /**
     * aboutBusinessKey indica si se debe, o no, emitir mensajes de advertencia acerca de la clave de negocio de la entidad. Este elemento es
     * relevante solo si el valor especificado, o determinado, para el elemento enabled es TRUE. Su valor es uno de los elementos de la enumeración
     * Kleenean. Seleccione TRUE para emitir los mensajes; en caso contrario, seleccione FALSE. Alternativamente, omita el elemento o seleccione
     * UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es TRUE.
     *
     * @return aboutBusinessKey
     */
    Kleenean aboutBusinessKey() default Kleenean.UNSPECIFIED; // TRUE

    /**
     * aboutDiscriminatorProperty indica si se debe, o no, emitir mensajes de advertencia acerca de la columna discriminadora de la entidad. Este
     * elemento es relevante solo si el valor especificado, o determinado, para el elemento enabled es TRUE. Su valor es uno de los elementos de la
     * enumeración Kleenean. Seleccione TRUE para emitir los mensajes; en caso contrario, seleccione FALSE. Alternativamente, omita el elemento o
     * seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es TRUE.
     *
     * @return aboutDiscriminatorProperty
     */
    Kleenean aboutDiscriminatorProperty() default Kleenean.UNSPECIFIED; // TRUE

    /**
     * aboutPropertiesWithoutStep indica si se debe, o no, emitir mensajes de advertencia acerca de propiedades de la entidad que no pertenecen a
     * algún paso. Este elemento es relevante solo si el valor especificado, o determinado, para el elemento enabled es TRUE. Su valor es uno de los
     * elementos de la enumeración Kleenean. Seleccione TRUE para emitir los mensajes; en caso contrario, seleccione FALSE. Alternativamente, omita el
     * elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es TRUE.
     *
     * @return aboutPropertiesWithoutStep
     */
    Kleenean aboutPropertiesWithoutStep() default Kleenean.UNSPECIFIED; // TRUE

    /**
     * aboutTreeView indica si se debe, o no, emitir mensajes de advertencia acerca de las vistas (páginas) de consulta y/o registro jerárquico de la
     * entidad. Este elemento es relevante solo si el valor especificado, o determinado, para el elemento enabled es TRUE. Su valor es uno de los
     * elementos de la enumeración Kleenean. Seleccione TRUE para emitir los mensajes; en caso contrario, seleccione FALSE. Alternativamente, omita el
     * elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es TRUE.
     *
     * @return aboutTreeView
     */
    Kleenean aboutTreeView() default Kleenean.UNSPECIFIED; // TRUE

    /**
     * aboutTriggers indica si se debe, o no, emitir mensajes de advertencia acerca de los disparadores de la entidad. Este elemento es relevante solo
     * si el valor especificado, o determinado, para el elemento enabled es TRUE. Su valor es uno de los elementos de la enumeración Kleenean.
     * Seleccione TRUE para emitir los mensajes; en caso contrario, seleccione FALSE. Alternativamente, omita el elemento o seleccione UNSPECIFIED
     * para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es TRUE.
     *
     * @return aboutTriggers
     */
    Kleenean aboutTriggers() default Kleenean.UNSPECIFIED; // TRUE

    /**
     * aboutVersionProperty indica si se debe, o no, emitir mensajes de advertencia acerca de la propiedad versión de la entidad. Este elemento es
     * relevante solo si el valor especificado, o determinado, para el elemento enabled es TRUE. Su valor es uno de los elementos de la enumeración
     * Kleenean. Seleccione TRUE para emitir los mensajes; en caso contrario, seleccione FALSE. Alternativamente, omita el elemento o seleccione
     * UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es TRUE.
     *
     * @return aboutVersionProperty
     */
    Kleenean aboutVersionProperty() default Kleenean.UNSPECIFIED; // TRUE

    /**
     * aboutVisibleFields indica si se debe, o no, emitir mensajes de advertencia acerca de los campos visibles de la entidad. Este elemento es
     * relevante solo si el valor especificado, o determinado, para el elemento enabled es TRUE. Su valor es uno de los elementos de la enumeración
     * Kleenean. Seleccione TRUE para emitir los mensajes; en caso contrario, seleccione FALSE. Alternativamente, omita el elemento o seleccione
     * UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es TRUE.
     *
     * @return aboutVisibleFields
     */
    Kleenean aboutVisibleFields() default Kleenean.UNSPECIFIED; // TRUE

    /**
     * aboutSpecialExpressions indica si se debe, o no, emitir mensajes de advertencia acerca de las expresiones especiales de la entidad. Este
     * elemento es relevante solo si el valor especificado, o determinado, para el elemento enabled es TRUE. Su valor es uno de los elementos de la
     * enumeración Kleenean. Seleccione TRUE para emitir los mensajes; en caso contrario, seleccione FALSE. Alternativamente, omita el elemento o
     * seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es TRUE.
     *
     * @return aboutSpecialExpressions
     */
    Kleenean aboutSpecialExpressions() default Kleenean.UNSPECIFIED; // TRUE

    /**
     * aboutUnusualExpressions indica si se debe, o no, emitir mensajes de advertencia acerca de las expresiones inusuales de la entidad. Este
     * elemento es relevante solo si el valor especificado, o determinado, para el elemento enabled es TRUE. Su valor es uno de los elementos de la
     * enumeración Kleenean. Seleccione TRUE para emitir los mensajes; en caso contrario, seleccione FALSE. Alternativamente, omita el elemento o
     * seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es TRUE.
     *
     * @return aboutUnusualExpressions
     */
    Kleenean aboutUnusualExpressions() default Kleenean.UNSPECIFIED; // TRUE

}
