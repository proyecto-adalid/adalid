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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * La anotación EntityViewLocation se utiliza para configurar la URL de las vistas (páginas) de la entidad.
 *
 * @author Jorge Campins
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EntityViewLocation {

    /**
     * origin consta del protocolo, el nombre o dirección IP del servidor y el número de puerto.
     * <p>
     * Por ejemplo, <b>http://86.48.31.84:8080</b>
     * <p>
     * Si este elemento se omite o está en blanco, que es su valor por omisión, entonces se utiliza el origen de la aplicación generada.
     *
     * @return origin
     */
    String origin() default "";

    /**
     * application corresponde a la parte inicial del path, es decir, a la raíz de contexto de la aplicación que contiene las vistas (páginas).
     * <p>
     * Por ejemplo, en la siguiente URL
     * <pre>http://86.48.31.84:8080/showcase102-web/faces/views/base/crop/procesamiento/recursos/control/acceso/Usuario34.xhtml</pre> su valor sería
     * <b>showcase102-web</b>
     * <p>
     * Si este elemento se omite o está en blanco, que es su valor por omisión, entonces se utiliza la raíz de contexto de la aplicación generada.
     *
     * @return application
     */
    String application() default "";

    /**
     * consolePath corresponde a la parte intermedia del path de las consolas de procesamiento.
     * <p>
     * Por ejemplo, en la siguiente URL
     * <pre>http://86.48.31.84:8080/showcase102-web/faces/views/base/crop/procesamiento/recursos/control/acceso/Usuario34.xhtml</pre> su valor sería
     * <b>faces/views/base/crop/procesamiento/recursos/control/acceso</b>
     * <p>
     * Si este elemento se omite o está en blanco, que es su valor por omisión, entonces se utiliza el path del subdirectorio correspondiente al
     * paquete que contiene la vista, cuyo valor predeterminado es <b>faces/views/base/crop/procesamiento</b>
     *
     * @return consolePath
     */
    String consolePath() default "";

    /**
     * readingPath corresponde a la parte intermedia del path de las vistas (páginas) de solo consulta.
     * <p>
     * Por ejemplo, en la siguiente URL
     * <pre>http://86.48.31.84:8080/showcase102-web/faces/views/base/crop/consulta/recursos/control/acceso/Usuario11.xhtml</pre> su valor sería
     * <b>faces/views/base/crop/consulta/recursos/control/acceso</b>
     * <p>
     * Si este elemento se omite o está en blanco, que es su valor por omisión, entonces se utiliza el path del subdirectorio correspondiente al
     * paquete que contiene la vista, cuyo valor predeterminado es <b>faces/views/base/crop/consulta</b>
     *
     * @return readingPath
     */
    String readingPath() default "";

    /**
     * writingPath corresponde a la parte intermedia del path de las vistas (páginas) de registro.
     * <p>
     * Por ejemplo, en la siguiente URL
     * <pre>http://86.48.31.84:8080/showcase102-web/faces/views/base/crop/registro/recursos/control/acceso/Usuario21.xhtml</pre> su valor sería
     * <b>faces/views/base/crop/registro/recursos/control/acceso</b>
     * <p>
     * Si este elemento se omite o está en blanco, que es su valor por omisión, entonces se utiliza el path del subdirectorio correspondiente al
     * paquete que contiene la vista, cuyo valor predeterminado es <b>faces/views/base/crop/registro</b>
     *
     * @return writingPath
     */
    String writingPath() default "";

}
