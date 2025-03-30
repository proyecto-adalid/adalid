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
 * La anotación EntityCodeGen se utiliza para especificar los generadores de código opcionales que se utilizarán para la entidad.
 *
 * @author Jorge Campins
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EntityCodeGen {

    /**
     * bpl indica si se debe, o no, generar código BPL (Business Process Logic) para la entidad. Su valor es uno de los elementos de la enumeración
     * Kleenean. Seleccione TRUE para generar código BPL; en caso contrario, seleccione FALSE. Alternativamente, omita el elemento o seleccione
     * UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es TRUE.
     *
     * @return bpl
     */
    Kleenean bpl() default Kleenean.UNSPECIFIED; // TRUE

    /**
     * bws indica si se debe, o no, generar código BWS (Business Web Service) para la entidad. Su valor es uno de los elementos de la enumeración
     * Kleenean. Seleccione TRUE para generar código BWS; en caso contrario, seleccione FALSE. Alternativamente, omita el elemento o seleccione
     * UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es FALSE. La generación del código BWS
     * también depende del valor especificado mediante el método <b>setInternetAccessAllowed</b> del proyecto maestro, y del elemento
     * <b>serviceable</b> de la anotación <b>ProcessOperationClass</b> de cada operación de la entidad.
     *
     * @return bws
     */
    Kleenean bws() default Kleenean.UNSPECIFIED; // FALSE

    /**
     * dao indica si se debe, o no, generar un objeto de acceso a datos (DAO, por las siglas en inglés de Data Access Object) para la entidad. Su
     * valor es uno de los elementos de la enumeración Kleenean. Seleccione TRUE para generar un objeto; en caso contrario, seleccione FALSE.
     * Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del
     * atributo es TRUE.
     *
     * @return dao
     */
    Kleenean dao() default Kleenean.UNSPECIFIED; // TRUE

    /**
     * daf indica si se debe, o no, generar una fachada de acceso a datos (DAF, por las siglas en inglés de Data Access Facade) para la entidad. Su
     * valor es uno de los elementos de la enumeración Kleenean. Seleccione TRUE para generar una fachada; en caso contrario, seleccione FALSE.
     * Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del
     * atributo es TRUE.
     *
     * @return daf
     */
    Kleenean daf() default Kleenean.UNSPECIFIED; // TRUE

    /**
     * fws indica si se debe, o no, generar código FWS (Facade Web Service) para la entidad. Su valor es uno de los elementos de la enumeración
     * Kleenean. Seleccione TRUE para generar código FWS; en caso contrario, seleccione FALSE. Alternativamente, omita el elemento o seleccione
     * UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es FALSE. La generación del código FWS
     * también depende del valor especificado mediante el método <b>setInternetAccessAllowed</b> del proyecto maestro.
     *
     * @return fws
     */
    Kleenean fws() default Kleenean.UNSPECIFIED; // FALSE

    /**
     * gui indica si se debe, o no, generar código GUI (Graphical User Interface) para la entidad. Su valor es uno de los elementos de la enumeración
     * Kleenean. Seleccione TRUE para generar código GUI; en caso contrario, seleccione FALSE. Alternativamente, omita el elemento o seleccione
     * UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es TRUE.
     *
     * @return gui
     */
    Kleenean gui() default Kleenean.UNSPECIFIED; // TRUE

    /**
     * sql indica si se debe, o no, generar código SQL para la entidad. Su valor es uno de los elementos de la enumeración Kleenean. Seleccione TRUE
     * para generar código SQL; en caso contrario, seleccione FALSE. Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el
     * valor predeterminado del atributo. El valor predeterminado del atributo es TRUE.
     *
     * @return sql
     */
    Kleenean sql() default Kleenean.UNSPECIFIED; // TRUE

    /**
     * state indica si se debe, o no, generar código SMC (State Machine Code) para la entidad. Su valor es uno de los elementos de la enumeración
     * Kleenean. Seleccione TRUE para generar código SMC; en caso contrario, seleccione FALSE. Alternativamente, omita el elemento o seleccione
     * UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es TRUE.
     *
     * @return state
     */
    Kleenean state() default Kleenean.UNSPECIFIED; // TRUE

}
