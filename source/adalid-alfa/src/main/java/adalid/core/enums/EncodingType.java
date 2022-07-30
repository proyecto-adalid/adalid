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
package adalid.core.enums;

/**
 * @author Jorge Campins
 * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/util/Base64.html">Class Base64</a>
 */
public enum EncodingType {

    /**
     * Utiliza el valor predeterminado de la plataforma.
     */
    UNSPECIFIED,
    /**
     * Utiliza el "Alfabeto Base64" como se especifica en la Tabla 1 de RFC 4648 y RFC 2045 para la operación de codificación y decodificación. El
     * codificador no agrega ningún carácter de avance de línea (separador de línea). El decodificador rechaza los datos que contienen caracteres
     * fuera del alfabeto base64.
     */
    BASIC,
    /**
     * Utiliza el "Alfabeto Base64 seguro para URL y nombre de archivo" como se especifica en la Tabla 2 de RFC 4648 para la codificación y
     * decodificación. El codificador no agrega ningún carácter de avance de línea (separador de línea). El decodificador rechaza los datos que
     * contienen caracteres fuera del alfabeto base64.
     */
    FILENAME,
    /**
     * Utiliza el "Alfabeto Base64" como se especifica en la Tabla 1 de RFC 2045 para la operación de codificación y decodificación. La salida
     * codificada debe representarse en líneas de no más de 76 caracteres cada una y utiliza un retorno de carro '\r' seguido inmediatamente por un
     * salto de línea '\n' como separador de línea. No se agrega ningún separador de línea al final de la salida codificada. Todos los separadores de
     * línea u otros caracteres que no se encuentran en la tabla alfabética base64 se ignoran en la operación de decodificación.
     */
    MIME,
    /**
     * Utiliza el "Alfabeto Base64 seguro para URL y nombre de archivo" como se especifica en la Tabla 2 de RFC 4648 para la codificación y
     * decodificación. El codificador no agrega ningún carácter de avance de línea (separador de línea). El decodificador rechaza los datos que
     * contienen caracteres fuera del alfabeto base64.
     */
    URL

}
