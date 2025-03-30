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
 * La anotación ProcessOperationClass se utiliza para establecer atributos de meta operaciones que extienden la clase ProcedureOperation.
 *
 * @author Jorge Campins
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ProcedureOperationClass {

//  String name() default "";
//
    /**
     * type especifica el tipo de procedimiento. Su valor es uno de los elementos de la enumeración ProcedureType. Seleccione COMPOUND, SIMPLE o VOID
     * si el procedimiento retorna un tipo de dato compuesto (tabla, record, etc.), un tipo de dato simple (primitivo), o no retorna un valor,
     * respectivamente. Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor
     * predeterminado es VOID.
     *
     * @return type
     */
    ProcedureType type() default ProcedureType.VOID;

    /**
     * dataType especifica el tipo de dato que retorna el procedimiento. Este elemento es relevante solo si el procedimiento es SIMPLE, ya que todo
     * procedimiento COMPOUND retorna OTHER y todo procedimiento VOID retorna NULL. Su valor es uno de los elementos de la enumeración
     * ProcedureDataType. Seleccione el elemento que corresponda al tipo de dato retornado por el procedimiento SQL. Alternativamente, omita el
     * elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado de un procedimiento SIMPLE es
     * BIGINT.
     *
     * @return dataType
     */
    ProcedureDataType dataType() default ProcedureDataType.BIGINT;

}
