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
package adalid.core.constants;

import adalid.core.Constants;

/**
 * @author Jorge Campins
 */
public class URLConstants {

    public static final String URL_REGEX = Constants.URL_REGEX;

    public static final String URL_REGEX_ENGLISH_DESCRIPTION = ""
        + "this is a website URL, i.e. the location of a specific website, page, or file on the Internet; "
        + "it must start with a protocol (usually http:// or https://), followed by / and a domain name; "
        + "optionally, after the domain name, the URL can include a \"path\", "
        + "to direct the browser to a specific page on the website; "
        + "for example: https://en.wikipedia.org/wiki/URL"
        + "";

    public static final String URL_REGEX_SPANISH_DESCRIPTION = ""
        + "esta es una URL de un sitio web, es decir, la ubicación de un sitio web, página o archivo específico en Internet; "
        + "debe comenzar con un protocolo (generalmente http:// o https://), seguido de / y un nombre de dominio; "
        + "opcionalmente, después del nombre de dominio, la URL puede incluir una \"ruta\", "
        + "para dirigir al navegador a una página específica en el sitio web; "
        + "por ejemplo: https://es.wikipedia.org/wiki/Localizador_de_recursos_uniforme"
        + "";

    public static final String URL_REGEX_ENGLISH_ERROR_MESSAGE = ""
        + "the URL does not meet the required pattern; "
        + "it must start with a protocol, followed by / and a domain name; "
        + "optionally, after the domain name, the URL can include a \"path\"."
        + "";

    public static final String URL_REGEX_SPANISH_ERROR_MESSAGE = ""
        + "la URL no cumple con el patrón requerido; "
        + "debe comenzar con un protocolo, seguido de / y un nombre de dominio; "
        + "opcionalmente, después del nombre de dominio, la URL puede incluir una \"ruta\"."
        + "";

}
