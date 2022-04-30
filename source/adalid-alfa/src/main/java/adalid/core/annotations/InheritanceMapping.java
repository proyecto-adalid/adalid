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
 * La anotación InheritanceMapping se utiliza para especificar la estrategia que se debe utilizar para generar las tablas de la base de datos que
 * corresponden a la entidad y a las entidades que la extienden; por lo tanto, esta anotación es relevante solo si la entidad es extendida por otras
 * entidades.
 *
 * @author Jorge Campins
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface InheritanceMapping {

    /**
     * strategy especifica la estrategia que se debe utilizar para generar las tablas de la base de datos. Su valor es uno de los elementos de la
     * enumeración InheritanceMappingStrategy. Seleccione SINGLE_TABLE para generar una sola tabla para almacenar las filas que corresponden a
     * instancias de todas las entidades de la jerarquía. Seleccione JOINED para generar una tabla para cada entidad, abstracta o concreta, de la
     * jerarquía; salvo que correspondan a propiedades básicas de la entidad (vea Anotación BaseField), las tablas generadas no tienen columnas para
     * propiedades heredadas pero tienen las relaciones uno-a-uno necesarias para hacer el enlace (join) entre ellas. Seleccione TABLE_PER_CLASS para
     * generar una tabla para cada entidad concreta de la jerarquía; las tablas generadas tienen columnas para todas las propiedades de la entidad,
     * incluyendo las heredadas, y no tienen relación unas con otras. Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el
     * valor predeterminado del atributo. El valor predeterminado del atributo es SINGLE_TABLE.
     *
     * @return strategy
     */
    InheritanceMappingStrategy strategy() default InheritanceMappingStrategy.UNSPECIFIED;

}
