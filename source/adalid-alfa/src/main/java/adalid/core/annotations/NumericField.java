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
 * La anotación NumericField se utiliza para establecer atributos de propiedades y parámetros numéricos.
 *
 * @author Jorge Campins
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface NumericField {

    /**
     * type especifica el patrón de análisis que se utilizará al formatear y analizar el valor de la propiedad o parámetro. Su valor es uno de los
     * elementos de la enumeración NumericFieldType. Seleccione CURRENCY para monedas, PERCENT para porcentajes o NUMBER para los demás tipos de
     * números.
     *
     * @return type
     */
    NumericFieldType type() default NumericFieldType.UNSPECIFIED;

    /**
     * divisor específica el divisor para la regla divisorRule. Su valor debe ser un número entero entre 1 y otro número que depende de la clase de
     * propiedad o parámetro: 100, para Byte; 10.000, para Short; y 1.000.000, para las demás clases. El valor predeterminado es 100.
     *
     * @return divisor
     */
    int divisor() default -1; // Constants.DEFAULT_NUMERIC_DIVISOR;

    /**
     * divisorRule especifica la regla que se debe aplicar al almacenar valores de la propiedad en la base de datos, o al ejecutar la operación. Su
     * valor es uno de los elementos de la enumeración DivisorRule. Seleccione CHECK para comprobar que el valor de la propiedad o parámetro sea
     * múltiplo de divisor, Seleccione CEILING para ajustar el valor de la propiedad o parámetro al menor múltiplo de divisor que sea mayor o igual al
     * valor suministrado. Seleccione FLOOR para ajustar el valor de la propiedad o parámetro al mayor múltiplo de divisor que sea menor o igual al
     * valor suministrado. Seleccione ROUND para ajustar el valor de la propiedad o parámetro al múltiplo de divisor más cercano al valor
     * suministrado. Alternativamente, omita el elemento o seleccione UNSPECIFIED para no ejecutar acción alguna.
     *
     * @return divisorRule
     */
    DivisorRule divisorRule() default DivisorRule.UNSPECIFIED;

    /**
     * symbol específica el símbolo o unidad en la que se expresa el valor de la propiedad o parámetro. Si se necesita un valor que varíe dependiendo
     * de la configuración regional, adicionalmente especifique los valores regionales mediante los métodos <b>setDefaultSymbol</b> o
     * <b>setLocalizedSymbol</b>. El valor predeterminado del atributo es <b>$</b> o <b>%</b> si el patrón de análisis especificado en el elemento
     * <b>type</b> es CURRENCY o PERCENT, respectivamente.
     *
     * @return symbol
     */
    String symbol() default "";

    /**
     * symbolPosition específica la posición del símbolo o unidad en la que se expresa el valor de la propiedad o parámetro. Su valor es uno de los
     * elementos de la enumeración SymbolPosition. Seleccione PREFIX para mostrar el símbolo antes del valor. Seleccione SUFFIX para mostrar el
     * símbolo después del valor. Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El
     * valor predeterminado del atributo es SUFFIX.
     *
     * @return symbolPosition
     */
    SymbolPosition symbolPosition() default SymbolPosition.UNSPECIFIED;

    /**
     * symbolSeparator específica si se debe incluir, o no, un espacio de separación entre el símbolo o unidad y el valor de la propiedad o parámetro.
     * Su valor es uno de los elementos de la enumeración Kleenean. Seleccione TRUE para incluir un espacio de separación; en caso contrario,
     * seleccione FALSE. Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor
     * predeterminado del atributo es TRUE si el símbolo comienza o termina por una letra o un número, dependiendo del valor del elemento
     * <b>symbolPosition</b>; de lo contrario, FALSE.
     *
     * @return symbolSeparator
     */
    Kleenean symbolSeparator() default Kleenean.UNSPECIFIED;

    /**
     * converter específica el nombre de un componente personalizado que permite convertir el valor de la propiedad o parámetro.
     *
     * @return converter
     */
    String converter() default "";

    /**
     * validator específica el nombre de un componente personalizado que permite validar el valor de la propiedad o parámetro.
     *
     * @return validator
     */
    String validator() default "";

}
