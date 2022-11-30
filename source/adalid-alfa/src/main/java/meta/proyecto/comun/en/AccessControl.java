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
package meta.proyecto.comun.en;

import adalid.core.*;
import meta.entidad.comun.control.acceso.ClaseFabricador;
import meta.entidad.comun.control.acceso.ConjuntoSegmento;
import meta.entidad.comun.control.acceso.ElementoSegmento;
import meta.entidad.comun.control.acceso.GrupoUsuario;
import meta.entidad.comun.control.acceso.PaginaEspecial;
import meta.entidad.comun.control.acceso.Rol;
import meta.entidad.comun.control.acceso.RolFiltroFuncion;
import meta.entidad.comun.control.acceso.RolFuncion;
import meta.entidad.comun.control.acceso.RolFuncionPar;
import meta.entidad.comun.control.acceso.RolPagina;
import meta.entidad.comun.control.acceso.RolPaginaEspecial;
import meta.entidad.comun.control.acceso.RolUsuario;
import meta.entidad.comun.control.acceso.RolVistaFuncion;
import meta.entidad.comun.control.acceso.Segmento;
import meta.entidad.comun.control.acceso.TipoRol;
import meta.entidad.comun.control.acceso.Usuario;
import meta.entidad.comun.control.acceso.UsuarioFuncion;
import meta.entidad.comun.control.acceso.UsuarioModulo;
import meta.entidad.comun.control.acceso.UsuarioSegmento;
import meta.paquete.comun.PaqueteConsultaControlAcceso;
import meta.paquete.comun.PaqueteProcesamientoControlAcceso;
import meta.paquete.comun.PaqueteRegistroControlAcceso;

/**
 * @author Jorge Campins
 */
public class AccessControl extends Project {

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        // <editor-fold defaultstate="collapsed" desc="localization of ControlAcceso's attributes">
        setLocalizedLabel(ENGLISH, "Access Control");
        setLocalizedLabel(SPANISH, "Control de Acceso");
//      setLocalizedShortDescription(ENGLISH, "Access Control Module");
//      setLocalizedShortDescription(SPANISH, "Módulo de Control de Acceso");
        setLocalizedDescription(ENGLISH, ""
            + "The access control module allows you to set user permissions to execute the functions of the application. "
            + "The access control module is based on the role access control model, where functions (operations) and users are assigned to "
            + "roles, and the permissions of a user are the sum of the permissions of the roles to which the user has been assigned. "
            + "The model is extended to incorporate read and/or write authorizations at the field level and also the "
            + "personalization, segmentation and supervision schemes. "
            + "Personalization consists of defining an owner for an entity class and then granting permissions to execute a function "
            + "only on the instances of the class that belong to the user. "
            + "Segmentation consists of defining sets of segments for an entity class and then granting permissions to execute a "
            + "function only on instances of the class that belong to the selected set. "
            + "An entity class can be personalized and segmented at the same time; and when it is segmented by the owner you get the "
            + "supervision scheme. "
            + "Using supervision scheme, a user can be authorized not only to the instances of the class that belong to him, but also to those that "
            + "belong to the users that he supervises, directly and indirectly. "
            + "On the other hand, when assigning a function to a role you can specify whether or not the function is a task of the users assigned to "
            + "the role; this allows to differentiate who can and who \"can and should\" execute a function. "
            + "The module provides an authentication mechanism, very useful for development and testing environments. "
            + "A standard authentication mechanism, such as LDAP, must be used in the production environment."
            + "");
        setLocalizedDescription(SPANISH, ""
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
                "Usuarios por Rol",
                "Usuarios",
                "Roles por Usuario"
            )
            + "");
        // </editor-fold>
    }

    // <editor-fold defaultstate="collapsed" desc="print">
    @Override
    public void print() {
        super.print();
        System.out.println(ClaseFabricador);
        System.out.println(ConjuntoSegmento);
        System.out.println(ElementoSegmento);
        System.out.println(PaginaEspecial);
        System.out.println(Rol);
        System.out.println(RolFiltroFuncion);
        System.out.println(RolFuncion);
        System.out.println(RolFuncionPar);
        System.out.println(RolPagina);
        System.out.println(RolPaginaEspecial);
        System.out.println(RolUsuario);
        System.out.println(RolVistaFuncion);
        System.out.println(Segmento);
        System.out.println(TipoRol);
        System.out.println(Usuario);
        System.out.println(UsuarioFuncion);
        System.out.println(UsuarioModulo);
        System.out.println(UsuarioSegmento);
        System.out.println(UsuarioX1);
        System.out.println(consulta);
        System.out.println(procesamiento);
        System.out.println(registro);
    }
    // </editor-fold>

    ClaseFabricador ClaseFabricador;

    ConjuntoSegmento ConjuntoSegmento;

    ElementoSegmento ElementoSegmento;

    GrupoUsuario UsuarioX1;

    PaginaEspecial PaginaEspecial;

    Rol Rol;

    RolFiltroFuncion RolFiltroFuncion;

    RolFuncion RolFuncion;

    RolFuncionPar RolFuncionPar;

    RolPagina RolPagina;

    RolPaginaEspecial RolPaginaEspecial;

    RolUsuario RolUsuario;

    RolVistaFuncion RolVistaFuncion;

    Segmento Segmento;

    TipoRol TipoRol;

    Usuario Usuario;

    UsuarioFuncion UsuarioFuncion;

    UsuarioModulo UsuarioModulo;

    UsuarioSegmento UsuarioSegmento;

    PaqueteConsultaControlAcceso consulta;

    PaqueteProcesamientoControlAcceso procesamiento;

    PaqueteRegistroControlAcceso registro;

}
