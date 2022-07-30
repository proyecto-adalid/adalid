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
package adalid.jee2.meta.proyecto.base;

import adalid.commons.util.*;
import adalid.core.*;
import adalid.core.interfaces.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import meta.psm.EntityAttributeKeys;
import meta.psm.PageAttributeKeys;
import meta.psm.ProjectAttributeKeys;

/**
 * @author Jorge Campins
 */
public class ProyectoJava2 extends ProyectoJava1 {

    public ProyectoJava2() {
        super();
    }

    private static final String[] X_PAGE_ARCHETYPE_NAMES = {
        "ArchivoAdjunto",
        "ElementoSegmento",
        "FuncionParametro",
        "RastroFuncion",
        "RastroFuncionPar",
        "RastroInforme",
        "RastroProceso",
        "TareaUsuario",
        "VistaFuncionCol"
    };

    private static final String[] X_PAGE_NAMES = {
        "Usuario34",
        "VistaFuncionCol21PorVista"
    };

    private static final String[] STRICTLY_PERSONALIZED_READING_PAGE_NAMES = {
        "FiltroFuncion21PorFuncion",
        "VistaFuncion21PorFuncion"
    };

    private static final String[] SPECIAL_PAGE_NAMES = {
        "Favoritos",
        "Menu",
        "Personalizacion",
        "HypertextFileViewer",
        "MultimediaPlayer",
        "StreamedContentViewer",
        "TextFileViewer"
    };

    private static final String[] SPECIAL_HELP_PAGE_NAMES = {
        "Favoritos",
        "Menu",
        "Personalizacion"
    };

    public List<String> getSpecialHelpPageNamesList() {
        return Arrays.asList(SPECIAL_HELP_PAGE_NAMES);
    }

    @Override
    public void addDirectives() {
        super.addDirectives();
//      addFileExclusionPattern("^.*/sun-ejb-jar\\.xml$"); // excluir los archivos sun-ejb-jar.xml
        /* commented since 20200216
        Map<String, ? extends Display> displays = getDisplaysMap();
        for (String name : X_PAGE_NAMES) {
            if (!displays.containsKey(name)) {
                addFileExclusionPattern("^.*" + "/pages/impl/" + name + "\\.java$");
            }
        }
        /**/
    }

    @Override
    public void addAttributes() {
        super.addAttributes();
        /**/
        addAttribute(ProjectAttributeKeys.CUSTOM_LAYOUT, false);
        addAttribute(ProjectAttributeKeys.HLB_RENDERING, false);
        addAttribute(ProjectAttributeKeys.HCB_RENDERING, false);
        addAttribute(ProjectAttributeKeys.HRB_RENDERING, false);
        addAttribute(ProjectAttributeKeys.INLINE_HELP_RENDERING, true);
        addAttribute(ProjectAttributeKeys.PARTIAL_STATE_SAVING, true);
//      addAttribute(ProjectAttributeKeys.SESSION_TIMEOUT, 30);
        addAttribute("primefaces_messages_escape", false);
        addAttribute("primefaces_inline_help_escape", false);
        /**/
        Entity entity;
        Map<String, Entity> entitiesMap = getEntitiesMap();
        for (String name : X_PAGE_ARCHETYPE_NAMES) {
            if (entitiesMap.containsKey(name)) {
                entity = entitiesMap.get(name);
//              entity.addAttribute(EntityAttributeKeys.PAGE_ARCHETYPE_CLASS_NAME_SUFFIX, "X");
                entity.addAttribute(EntityAttributeKeys.PAGE_ARCHETYPE_PACKAGE_NAME_SUFFIX, "ext");
            }
        }
        /**/
        for (String name : X_PAGE_NAMES) {
            addAttribute(Page.class, name,
                KVP.join(PageAttributeKeys.ABSTRACT, true)
            );
        }
        /**/
        for (String name : STRICTLY_PERSONALIZED_READING_PAGE_NAMES) {
            addAttribute(Page.class, name,
                KVP.join(PageAttributeKeys.STRICTLY_PERSONALIZED_READING, true)
            );
        }
        /**/
        final String directory = "special-page/"; // /language/ and /country/ are replaced by Locale getLanguage() and getCountry()
        final String extension = ".xhtml";
        for (String name : SPECIAL_PAGE_NAMES) {
            addAttribute(Page.class, name,
                KVP.join(PageAttributeKeys.HELP_FILE_NAME, directory + name + extension)
            );
        }
        /**/
        addHelpPageTextAttributes();
    }

    public void addHolidaysListBeanAttribute() {
        final String beanName = "holidaysList";
        addHolidaysListBeanAttribute(beanName);
    }

    public void addHolidaysListBeanAttribute(String beanName) {
        final String classSimpleName = "HolidaysList";
        addBeanAttribute(classSimpleName, beanName);
    }

    /**
     * @param name attribute name
     * @return the help document
     */
    public String getHelpDocumentAttribute(String name) {
        Object attribute = getKeyValuePairAttribute(name, PageAttributeKeys.HELP_EMBEDDED_DOCUMENT);
        return attribute == null ? getHelpDocument() : attribute.toString();
    }

    /**
     * @param name attribute name
     * @return the help file name
     */
    public String getHelpFileNameAttribute(String name) {
        Object attribute = getKeyValuePairAttribute(name, PageAttributeKeys.HELP_FILE_NAME);
        return attribute == null ? getHelpFileName() : attribute.toString();
    }

    /**
     * @param name attribute name
     * @return the help text
     */
    public String getHelpPageTextAttribute(String name) {
        Object attribute = getKeyValuePairAttribute(name, PageAttributeKeys.HELP_PAGE_TEXT);
        return attribute == null ? null : attribute.toString();
    }

    /**
     * add text to special help pages
     */
    private void addHelpPageTextAttributes() {
        final String directory = "/faces/resources/help/base/";
        final String extension = ".xhtml";
        String pid, url, bid, str;
        /**/
        //<editor-fold defaultstate="collapsed" desc="Favoritos">
        /**/
        pid = "Favoritos";
        url = "/" + getWebProjectName() + directory + "entity/PaginaUsuario" + extension;
        /**/
        bid = b("Favorites");
        str = ""
            + "The page " + bid + " is an special " + i("Registration Table") + " "
            + "that works with instances of the entity " + a(url, "Favorites") + ". "
            + "The rows in the table contain hyperlinks that allow you to open application pages. "
            + "Clicking on the name of a page in a row of the table opens the corresponding page. "
            + BR + BR
            + "The page " + bid + " only shows the hyperlinks of pages that the user has added to his favorite page list, "
            + "as long as those pages are still authorized to the user; the pages added but not authorized "
            + "remain hidden until, eventually, the user obtains the necessary authorization again. "
            + BR + BR
            + "The page " + bid + " does not allow adding or editing, but deleting. "
            + "To add pages to your favorite page list use the "
            + ic("fa fa-star-o") + " button on the toolbar when you are on the page you want to add."
            + "";
        addAttribute(Page.class, pid + ":en",
            KVP.join(PageAttributeKeys.HELP_PAGE_TEXT, str)
        );
        /**/
        bid = b("Favoritos");
        str = ""
            + "La página " + bid + " es una " + i("Tabla de Registro") + " especial "
            + "que trabaja con instancias de la entidad " + a(url, "Favoritos") + ". "
            + "Las filas de la tabla contienen hipervínculos que permiten abrir páginas de la aplicación. "
            + "Al hacer clic sobre el nombre de una página en una fila de la tabla, se abre la página correspondiente. "
            + BR + BR
            + "La página " + bid + " solo muestra los hipervínculos de páginas que el usuario ha agregado a su lista de páginas favoritas, "
            + "siempre y cuando esas páginas sigan estando autorizadas al usuario; las páginas agregadas, pero no autorizadas, "
            + "permanecen ocultas hasta que, eventualmente, el usuario obtenga nuevamente la autorización necesaria. "
            + BR + BR
            + "La página " + bid + " no permite agregar ni editar, pero si eliminar. "
            + "Para agregar páginas a su lista de páginas favoritas, utilice el botón "
            + ic("fa fa-star-o") + " de la " + i("Barra de Botones de Acción") + " cuando se encuentre en la página que desea agregar."
            + "";
        addAttribute(Page.class, pid + ":es",
            KVP.join(PageAttributeKeys.HELP_PAGE_TEXT, str)
        );
        /**/
        //</editor-fold>
        /**/
        //<editor-fold defaultstate="collapsed" desc="Menu">
        /**/
        pid = "Menu";
        url = "/" + getWebProjectName() + directory + getAlias() + extension;
        /**/
        bid = b("Start Menu");
        str = ""
            + "The page " + bid + " is an special " + i("Query Tree") + ". "
            + "The top nodes of the tree correspond to the " + a(url, "application modules") + "."
            + "In the node of each module are the branches that correspond to the packages of the module: Inquiry, Processing and Registration. "
            + "In the node of each package are the leaves of the tree (terminal nodes) that correspond to the "
            + "pages of the package operation entities; and, if they exist, there could also be a branch for "
            + "the leaves that correspond to the pages of the package configuration entities. "
            + "The leaves of the tree are hyperlinks that allow you to open pages of the application. "
            + BR + BR
            + "Clicking on a collapsed node of the tree expands it. "
            + "Clicking on an expanded node of the tree collapses it. "
            + "Clicking on a leaf of the tree opens the corresponding page. "
            + BR + BR
            + "The page " + bid + " only shows the hyperlinks of pages authorized to the user. "
            + "";
        addAttribute(Page.class, pid + ":en",
            KVP.join(PageAttributeKeys.HELP_PAGE_TEXT, str)
        );
        /**/
        bid = b("Menú de Inicio");
        str = ""
            + "La página " + bid + " es un " + i("Árbol de Consulta") + " especial. "
            + "Los nodos superiores del árbol corresponden a los " + a(url, "módulos de la aplicación") + ". "
            + "En el nodo de cada módulo están las ramas que corresponden a los paquetes del módulo: Consulta, Procesamiento y Registro. "
            + "En el nodo de cada paquete se encuentran las hojas del árbol (nodos terminales) que corresponden a las "
            + "páginas de las entidades de operación del paquete; y, si existen, también podría haber una rama para "
            + "las hojas que corresponden a las páginas de las entidades de configuración del paquete. "
            + "Las hojas del árbol son hipervínculos que permiten abrir páginas de la aplicación. "
            + BR + BR
            + "Al hacer clic sobre un nodo contraído del árbol, éste se expande. "
            + "Al hacer clic sobre un nodo expandido del árbol, éste se contrae. "
            + "Al hacer clic sobre una hoja del árbol, se abre la página correspondiente. "
            + BR + BR
            + "La página " + bid + " solo muestra los hipervínculos de páginas autorizadas al usuario. "
            + "";
        addAttribute(Page.class, pid + ":es",
            KVP.join(PageAttributeKeys.HELP_PAGE_TEXT, str)
        );
        /**/
        //</editor-fold>
        /**/
        //<editor-fold defaultstate="collapsed" desc="Personalizacion">
        /**/
        pid = "Personalizacion";
        url = "/" + getWebProjectName() + directory + "entity/PaginaEspecial" + extension;
        /**/
        bid = b("Settings");
        str = ""
            + "The page " + bid + " is an special " + i("Processing Console") + " "
            + "that allows the user to perform the following functions:"
            + ul(
                b("Change settings") + ": change your personal settings, that is, your home page, GUI theme, etc.",
                b("Change password") + ": change your password to access the application.",
                b("Upload portrait") + ": Upload an image file (usually your portrait),"
                + "to display as button image in the " + i("Page Header") + ".",
                b("Crop portrait") + ": Crop the previously loaded image.",
                b("Take portrait") + ": Take a photo (usually your portrait) using your computer's camera,"
                + "to display as button image in the " + i("Page Header") + "."
            )
            + h3("Change settings")
            + "Using the function " + b("Change settings") + " the user can modify his personal settings. "
            + "The parameters of this function are:"
            + ul(
                b("landing page") + ": specifies the page that opens when the user starts a new session. "
                + "Possible values are:",
                ul(
                    u("Default") + ": opens a page selected by the application. "
                    + "If the user has notifications of pending tasks, it would be the page " + b("Query of Tasks") + "; "
                    + "otherwise page " + b("Start Menu") + ".",
                    u("Menu") + ": opens the page " + b("Start Menu") + ".",
                    u("Favorites") + ": opens the page " + b("Favorites") + ".",
                    u("Tasks") + ": opens the page " + b("Query of Tasks") + ".",
                    u("Menu page") + ": opens the page specified in the parameter " + b("menu page") + ". "
                    + "Usually this would be a generated page that can also be opened from the " + b("Start Menu") + ".",
                    u("Other page") + ": opens the page specified in the parameter " + b("other page") + ". "
                    + "Usually this would be a special (non-generated) page that cannot be opened from the " + b("Start Menu") + ". "
                    + "Special pages are defined using the registration page of " + a(url, "Special Pages") + " "
                    + "from the " + b("Configuration") + " menu of the " + b("Access Control") + " module."
                ),
                b("theme") + ": specifies the theme of the interface. "
                + "A theme is a series of graphic elements that modify the external appearance of the application. "
                + "The list of themes includes at least all the " + i("community") + " themes from the component library (PrimeFaces), "
                + "and might include some " + i("premium") + " themes as well. "
                + "This parameter is only available when the application was generated with the option to allow changing the theme.",
                b("rows per page") + ": specifies the initial number of rows per page in the tabular query and/or registration pages.",
                b("inline help") + ": specifies whether or not the field descriptions should be displayed "
                + "in the detailed query and/or registration pages and in the processing consoles.",
                b("confirm discarding") + ": specifies whether or not to confirm when discarding changes on the registration pages.",
                b("confirm end session") + ": specifies whether or not to confirm the end of the session, "
                + "by clicking the button " + ic("fa fa-sign-out") + " on the toolbar."
            )
            + h3("Change password")
            + "The function " + b("Change password") + " is only available when the application uses the JDBC authentication mechanism, "
            + "which uses the credentials stored in the application's database. "
            + "Such an authentication mechanism is very useful for development and test environments. "
            + "In production environments another authentication mechanism is normally used, such as LDAP."
            + BR + BR
            + "To change your password, type your current password in the field " + b("password") + ", "
            + "and then type your new password twice, in the fields " + b("new password") + ". "
            + "Finally click the button " + b("Process") + " on the toolbar."
            + "";
        addAttribute(Page.class, pid + ":en",
            KVP.join(PageAttributeKeys.HELP_PAGE_TEXT, str)
        );
        /**/
        bid = b("Personalización");
        str = ""
            + "La página " + bid + " es una " + i("Consola de Procesamiento") + " especial "
            + "que permite al usuario ejecutar las siguientes funciones:"
            + ul(
                b("Cambiar configuración") + ": cambiar su configuración personal, es decir, su página de inicio, tema de la interfaz gráfica, etc.",
                b("Cambiar contraseña") + ": cambiar su contraseña para acceder a la aplicación.",
                b("Cargar retrato") + ": Cargar un archivo de imagen (usualmente su retrato), "
                + "para mostrar como imagen del botón en el " + i("Encabezado de Página") + ".",
                b("Recortar retrato") + ": Recortar la imagen previamente cargada.",
                b("Tomar retrato") + ": Tomar una fotografía (usualmente su retrato) utilizando la cámara de su computador, "
                + "para mostrar como imagen del botón en el " + i("Encabezado de Página") + "."
            )
            + h3("Cambiar configuración")
            + "Mediante la función " + b("Cambiar configuración") + " el usuario puede modificar su configuración personal. "
            + "Los parámetros de esta función son:"
            + ul(
                b("página de inicio") + ": especifica la página que se abre cuando el usuario inicia una nueva sesión de trabajo. "
                + "Los valores posibles son:",
                ul(
                    u("Predeterminada") + ": abre una página seleccionada por la aplicación. "
                    + "Si el usuario tiene notificaciones de tareas pendientes, sería la página " + b("Consulta de Tareas") + "; "
                    + "de lo contrario, la página " + b("Menú de Inicio") + ".",
                    u("Menú") + ": abre la página " + b("Menú de Inicio") + ".",
                    u("Favoritos") + ": abre la página " + b("Favoritos") + ".",
                    u("Tareas") + ": abre la página " + b("Consulta de Tareas") + ".",
                    u("Página del menú") + ": abre la página que se especifica en el parámetro " + b("página del menú") + ". "
                    + "Por lo general, esta sería una página generada que también se puede abrir desde el " + b("Menú de Inicio") + ".",
                    u("Otra página") + ": abre la página que se especifica en el parámetro " + b("otra página") + ". "
                    + "Por lo general, esta sería una página especial (no generada), que no se puede abrir desde el " + b("Menú de Inicio") + ". "
                    + "Las páginas especiales se definen utilizando la página de registro de " + a(url, "Páginas Especiales") + " "
                    + "del menú de " + b("Configuración") + " del módulo " + b("Control de Acceso") + "."
                ),
                b("tema") + ": especifica el tema de la interfaz. "
                + "Un tema es una serie de elementos gráficos que modifican la apariencia externa de la aplicación. "
                + "La lista de temas incluye al menos todos los temas " + i("comunitarios") + " de la biblioteca de componentes (PrimeFaces), "
                + "y podría incluir también algunos temas " + i("premium") + ". "
                + "Este parámetro solo está disponible cuando la aplicación fue generada con la opción de permitir cambiar el tema.",
                b("filas por página") + ": especifica el número inicial de filas por página en las páginas de consulta y/o registro tabular.",
                b("ayuda en línea") + ": especifica si se deben mostrar, o no, las descripciones de los campos "
                + "en las páginas de consulta y/o registro detallado y en las consolas de procesamiento.",
                b("confirmar al descartar") + ": especifica si se debe confirmar, o no, al descartar los cambios en las páginas de registro.",
                b("confirmar al finalizar la sesión") + ": especifica si se debe confirmar, o no, la finalización de la sesión de trabajo, "
                + "al hacer clic en el botón " + ic("fa fa-sign-out") + " de la " + i("Barra de Botones de Acción") + "."
            )
            + h3("Cambiar contraseña")
            + "La función " + b("Cambiar contraseña") + " solo está disponible cuando la aplicación usa el mecanismo de autenticación JDBC, "
            + "el cual utiliza las credenciales almacenadas en la base de datos de la aplicación. "
            + "Tal mecanismo de autenticación es muy útil para ambientes de desarrollo y pruebas. "
            + "En ambientes de producción normalmente se utiliza otro mecanismo de autenticación, como LDAP."
            + BR + BR
            + "Para cambiar su contraseña, escriba su contraseña actual en el campo " + b("contraseña") + ", "
            + "y luego escriba su nueva contraseña dos veces, en los campos " + b("nueva contraseña") + ". "
            + "Finalmente haga clic en el botón " + b("Procesar") + " de la " + i("Barra de Botones de Acción") + "."
            + "";
        addAttribute(Page.class, pid + ":es",
            KVP.join(PageAttributeKeys.HELP_PAGE_TEXT, str)
        );
        /**/
        //</editor-fold>
        /**/
    }

}
