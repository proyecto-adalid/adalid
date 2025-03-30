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
 * La anotación ColumnField se utiliza para establecer atributos de la columna de la base de datos que corresponde a la propiedad.
 *
 * @author Jorge Campins
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ColumnField {

    /**
     * calculable indica si la columna es, o no, calculable. Su valor es uno de los elementos de la enumeración Kleenean. Seleccione TRUE si la
     * columna es calculable; en caso contrario, seleccione FALSE. Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor
     * predeterminado del atributo. El valor predeterminado del atributo es FALSE. El valor de las columnas calculables no se almacena en la base de
     * datos; su valor es calculado cuando es necesario. La expresión de valor de una columna calculable se especifica mediante el método
     * setCalculableValueExpression de la propiedad en el método settleProperties de la entidad. Toda columna calculable admite valores nulos y
     * duplicados, y no es insertable ni actualizable; por lo tanto, si la columna es calculable no se deben especificar los elementos nullable,
     * insertable, updateable y unique, descritos a continuación.
     *
     * @return calculable
     */
    Kleenean calculable() default Kleenean.UNSPECIFIED; // FALSE

    /**
     * nullable indica si la columna admite, o no, valores nulos. Su valor es uno de los elementos de la enumeración Kleenean. Seleccione TRUE si la
     * columna admite valores nulos; en caso contrario, seleccione FALSE. Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar
     * el valor predeterminado del atributo. El valor predeterminado del atributo es TRUE.
     *
     * @return nullable
     */
    Kleenean nullable() default Kleenean.UNSPECIFIED; // TRUE

    /**
     * insertable indica si la columna es, o no, insertable; es decir, si los componentes para el manejo de la persistencia incluyen, o no, la columna
     * en las operaciones insert. Su valor es uno de los elementos de la enumeración Kleenean. Seleccione TRUE si la columna es insertable; en caso
     * contrario, seleccione FALSE. Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo.
     * El valor predeterminado del atributo es TRUE.
     *
     * @return insertable
     */
    Kleenean insertable() default Kleenean.UNSPECIFIED; // TRUE

    /**
     * updateable indica si la columna es, o no, actualizable; es decir, si los componentes para el manejo de la persistencia incluyen, o no, la
     * columna en las operaciones update. Su valor es uno de los elementos de la enumeración Kleenean. Seleccione TRUE si la columna es actualizable;
     * en caso contrario, seleccione FALSE. Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del
     * atributo. El valor predeterminado del atributo es TRUE.
     *
     * @return updateable
     */
    Kleenean updateable() default Kleenean.UNSPECIFIED; // TRUE

    /**
     * unique indica si la columna admite, o no, valores duplicados. Su valor es uno de los elementos de la enumeración Kleenean. Seleccione FALSE si
     * la columna admite valores duplicados; en caso contrario, seleccione TRUE. Alternativamente, omita el elemento o seleccione UNSPECIFIED para
     * utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es FALSE.
     *
     * Las columnas que corresponden a claves primarias (vea Anotación PrimaryKey), secuencias (vea Anotación SequenceProperty), claves de negocio
     * (vea Anotación BusinessKey), claves únicas (vea Anotación UniqueKey), no admiten valores duplicados, independientemente del valor de este
     * elemento.
     *
     * @return unique
     */
    Kleenean unique() default Kleenean.UNSPECIFIED; // FALSE

    /**
     * indexed indica si la columna debe tener, o no, un índice. Su valor es uno de los elementos de la enumeración Kleenean. Seleccione FALSE si la
     * columna no necesita índice; en caso contrario, seleccione TRUE. Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el
     * valor predeterminado del atributo. El valor predeterminado del atributo es FALSE.
     *
     * Las columnas que corresponden a claves primarias (vea Anotación PrimaryKey), secuencias (vea Anotación SequenceProperty), claves de negocio
     * (vea Anotación BusinessKey), claves únicas (vea Anotación UniqueKey), nombres (vea Anotación NameProperty), las columnas que hacen referencia a
     * alguna entidad y las que no admiten duplicados (vea el elemento unique de esta misma anotación), tendrán índice, independientemente del valor
     * de este elemento.
     *
     * @return indexed
     */
    Kleenean indexed() default Kleenean.UNSPECIFIED; // FALSE

}
