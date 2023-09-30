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
package meta.proyecto.comun.help.es;

import static adalid.commons.util.MarkupUtils.*;

/**
 * @author Jorge Campins
 */
public class ControlAcceso {

    public static String getHelpText() {
        String str = ""
            + "El módulo de control de acceso permite establecer los permisos de cada usuario para ejecutar las funciones de la aplicación. "
            + "El módulo de control de acceso se basa en el modelo de control de acceso por roles, donde se asignan funciones (operaciones) y "
            + "usuarios a roles, y los permisos de un usuario son la suma de los permisos de los roles a los que se ha asignado el usuario. "
            + "El modelo se extiende para incorporar autorizaciones de lectura y/o escritura a nivel de campos y también los esquemas de "
            + "personalización, segmentación y supervisión. "
            + "La personalización consiste en definir un propietario para una clase de entidad y luego otorgar permisos para ejecutar una función "
            + "solo sobre las instancias de la clase que pertenezcan al usuario. "
            + "La segmentación consiste en definir conjuntos de segmentos para una clase de entidad y luego otorgar permisos para ejecutar una "
            + "función solo sobre las instancias de la clase que pertenezcan al conjunto seleccionado. "
            + "Una clase de entidad puede estar personalizada y segmentada a la vez; y cuando se segmenta mediante el propietario se obtiene el "
            + "esquema de supervisión. "
            + "Utilizando ese esquema, un usuario se puede autorizar no solo a las instancias de la clase que le pertenecen, sino también a las que "
            + "pertenecen a los usuarios que supervisa, directa e indirectamente. "
            + "Por otra parte, al asignar una función a un rol se puede especificar si la función es o no una tarea de los usuarios asignados al rol; "
            + "esto permite diferenciar quien puede y quien \"puede y debe\" ejecutar una función. "
            + "El módulo provee un mecanismo de autenticación, muy útil para ambientes de desarrollo y pruebas. "
            + "En el ambiente de producción se debe utilizar un mecanismo de autenticación estándar, tal como LDAP."
            + BR + BR
            + "El módulo de Control de Acceso incluye consolas de procesamiento y páginas de consulta y registro para las siguientes clases de entidades: "
            + ul(
                "Conjuntos de Segmentos",
                "Elementos de Conjuntos de Segmentos",
                "Roles",
                "Filtros por Rol",
                "Funciones por Rol",
                "Parámetros de Funciones por Rol",
                "Favoritos por Rol",
                "Páginas Especiales por Rol",
                "Usuarios por Rol",
                "Vistas por Rol",
                "Usuarios",
                "Funciones por Usuario",
                "Parámetros de Funciones por Usuario",
                "Roles por Usuario",
                "Grupos de Usuarios"
            )
            + "";
        /**/
        return str;
    }

}
