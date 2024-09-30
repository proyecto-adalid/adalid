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
package meta.psm;

/**
 * @author Jorge Campins
 */
public class ProjectAttributeKeys {

    /**
     * true si la interfaz gráfica de la aplicación generada tiene un layout personalizado que se debe utilizar en lugar del layout generado; de lo
     * contrario false. El valor predeterminado es false. El nombre del archivo de layout generado es fullPageLayout.xhtml y se almacena en el
     * subdirectorio templates/base del directorio src/main/webapp del módulo alias-web de la aplicación, donde alias es el alias del proyecto
     * generado (vea Método setAlias); el archivo de layout personalizado debe tener el mismo nombre y se debe almacenar en el subdirectorio
     * templates/custom-made.
     */
    public static final String CUSTOM_LAYOUT = "custom_layout";

    /**
     * true si la interfaz gráfica de la aplicación generada puede utilizar interruptores de palanca en lugar de listas desplegables para propiedades
     * y parámetros de tipo Boolean que no permiten valores nulos; de lo contrario false. El valor predeterminado es true.
     */
    public static final String TOGGLE_SWITCHES_ALLOWED = "toggle_switches_allowed";

    /**
     * true si la interfaz gráfica de la aplicación generada puede utilizar casillas de verificación en lugar de listas desplegables para propiedades
     * y parámetros de tipo Boolean que no permiten valores nulos; de lo contrario false. El valor predeterminado es true.
     */
    public static final String CHECK_BOXES_ALLOWED = "check_boxes_allowed";

    /**
     * true si la interfaz gráfica de la aplicación generada puede utilizar componentes de entrada especializados en lugar de cuadros de texto para
     * propiedades y parámetros de tipos numéricos; de lo contrario false. El valor predeterminado es true.
     */
    public static final String NUMERIC_BOXES_ALLOWED = "numeric_boxes_allowed";

    /**
     * true si las páginas de presentación tabular de la interfaz gráfica de la aplicación generada deben mostrar los bordes de las celdas de la
     * tabla; de lo contrario, false. El valor predeterminado es false.
     */
    public static final String DATA_TABLE_SHOW_GRID_LINES = "primefaces_data_table_show_grid_lines";

    /**
     * true si las páginas de presentación tabular de la interfaz gráfica de la aplicación generada deben alternar el color de fondo de las filas de
     * la tabla para separar visualmente el contenido; de lo contrario, false. El valor predeterminado es false.
     */
    public static final String DATA_TABLE_SHOW_STRIPED_ROWS = "primefaces_data_table_striped_rows";

    /**
     * tamaño de las filas de la tabla en las páginas de presentación tabular de la interfaz gráfica de la aplicación generada. Su valor es uno de los
     * elementos de la enumeración DataTableSize. Especifique SMALL, MEDIUM o LARGE, para obtener filas pequeñas, medianas o grandes, respectivamente.
     * El valor predeterminado es MEDIUM.
     */
    public static final String DATA_TABLE_SIZE = "primefaces_data_table_size";

    /**
     * true si no desea generar plantillas de implementación de componentes de lógica de procesos de negocio; de lo contrario false. El valor por
     * omisión es false. Las plantillas se almacenan en subdirectorios del directorio srx del módulo EJB del proyecto generado; srx no es está entre
     * los directorios java ese módulo; copie las plantillas que necesite al correspondiente subdirectorio del directorio src del módulo EJB.
     */
    public static final String DISABLE_BPL_IMPL_GENERATION = "disable_business_process_logic_impl_generation";

    /**
     * true si no desea generar plantillas de asistentes de página; de lo contrario false. El valor predeterminado es false. Las plantillas se
     * almacenan en subdirectorios del directorio srx del módulo Web del proyecto generado; srx no es está entre los directorios java de ese módulo;
     * copie las plantillas que necesite al correspondiente subdirectorio del directorio src del módulo Web.
     */
    public static final String DISABLE_PAGE_ASSISTANT_GENERATION = "disable_page_assistant_generation";

    /**
     * Parámetro javax.faces.FACELETS_REFRESH_PERIOD. Define el período utilizado para actualizar el árbol de sintaxis abstracta de facelet desde el
     * archivo de definición de vista. El valor predeterminado para proyectos en etapas de Aceptación y Producción es -1. En las demás etapas el valor
     * por omisión depende del servidor de aplicaciones.
     */
    public static final String FACELETS_REFRESH_PERIOD = "facelets_refresh_period";

    /**
     * Parámetro SEPARATOR_CHAR_PARAM_NAME. Define el carácter utilizado para separar segmentos de un clientId. El valor predeterminado es ':'.
     */
    public static final String FACES_ID_SEGMENTS_SEPARATOR_CHAR = "faces_id_segments_separator_char";

    /**
     * true si el layout de la interfaz gráfica de la aplicación generada debe incluir el archivo de imagen index_hlb.png en el bloque izquierdo del
     * encabezado de página; de lo contrario false. El valor predeterminado es false. El archivo index_hlb.png se almacena en el subdirectorio
     * src/main/webapp/resources/images/base del módulo alias-web de la aplicación, donde alias es el alias del proyecto generado.
     */
    public static final String HLB_RENDERING = "hlb_rendering";

    /**
     * true si el layout de la interfaz gráfica de la aplicación generada debe incluir el nombre de la aplicación en el bloque central del encabezado
     * de página; de lo contrario false. El valor predeterminado es false.
     */
    public static final String HCB_RENDERING = "hcb_rendering";

    /**
     * true si el layout de la interfaz gráfica de la aplicación generada debe incluir el archivo de imagen index_hrb.png en el bloque derecho del
     * encabezado de página; de lo contrario false. El valor predeterminado es false. El archivo index_hrb.png se almacena en el subdirectorio
     * src/main/webapp/resources/images/base del módulo alias-web de la aplicación, donde alias es el alias del proyecto generado.
     */
    public static final String HRB_RENDERING = "hrb_rendering";

    /**
     * true si la interfaz gráfica de la aplicación generada siempre debe abrir los visores de contenido de archivos como un diálogo; de lo contrario
     * especifique false. El valor predeterminado es true.
     */
    public static final String ALWAYS_OPEN_CONTENT_VIEWER_AS_DIALOG = "always_open_content_viewer_as_dialog";

    /**
     * true si la interfaz gráfica de la aplicación generada debe utilizar el método básico de autenticación de HTTP; de lo contrario false. El valor
     * por omisión es false.
     */
    public static final String HTTP_BASIC_AUTHENTICATION = "http_basic_authentication";

    /**
     * true si los servicios web de la aplicación generada deben utilizar el método básico de autenticación de HTTP; de lo contrario false. El valor
     * por omisión es true.
     */
    public static final String HTTP_BASIC_AUTHENTICATION_FOR_WEB_API = "http_basic_authentication_for_web_api";

    /**
     * Nombre de la página inicial predeterminada de la interfaz gráfica de la aplicación generada. El valor predeterminado es Menu
     */
    public static final String INITIAL_PAGE_NAME = "initial_page_name";

    /**
     * Ruta relativa de la página inicial predeterminada de la interfaz gráfica de la aplicación generada. El valor predeterminado es
     * /faces/views/base/code/Menu.xhtml.
     */
    public static final String INITIAL_PAGE_LOCATION = "initial_page_location";

    /**
     * Nombre de la ventana inicial de la interfaz gráfica de la aplicación generada. El valor predeterminado es el alias del proyecto generado.
     */
    public static final String INITIAL_WINDOW_TARGET = "initial_window_target";

    /**
     * true si la interfaz gráfica de la aplicación generada debe mostrar componentes de ayuda-en-línea; de lo contrario false. El valor
     * predeterminado es true.
     */
    public static final String INLINE_HELP_RENDERING = "inline_help_rendering";

    /**
     * true si la interfaz gráfica de la aplicación generada debe salvar parcialmente el estado de las páginas; de lo contrario false. El valor por
     * omisión es true. El estado de las páginas es salvado cuando la interfaz abre una nueva página y sirve para reponer el estado de la página
     * salvada en el caso de que el usuario deba regresar a ella.
     */
    public static final String PARTIAL_STATE_SAVING = "partial_state_saving";

    /**
     * true sí se define un directorio de contenido estático seguro para la aplicación; de lo contrario false. El directorio es seguro si no se
     * configura como raíz de documentos alternativa de la aplicación o del servidor de aplicaciones; de esta manera, aun se podrá acceder a su
     * contenido a través de la interfaz gráfica de la aplicación, sujeto al control de acceso provisto por la aplicación. El valor predeterminado es
     * el especificado para el atributo MEDIA_FILE_EXPOSURE_RISK_ACCEPTABLE; si ese atributo también fue omitido, entonces el valor predeterminado es
     * false.
     */
    public static final String ALTERNATE_DOCUMENT_ROOT_SECURED = "alternate_document_root_secured";

    /**
     * true sí se define un directorio de contenido estático seguro para la aplicación, y la aplicación puede copiar archivos al directorio de
     * contenido estático principal; de lo contrario false. Para el correcto funcionamiento de los componentes utilizados para recortar imágenes y
     * para la reproducción de archivos de audio y video, es necesario copiar temporalmente los archivos al directorio de contenido estático
     * principal, por lo que habría riesgo de exposición mientras se recorta o lleva a cabo su reproducción. Para mitigar ese riesgo, la aplicación
     * genera la URL de la copia de manera aleatoria, y la interfaz gráfica no la muestra al usuario. El valor predeterminado es false.
     */
    public static final String MEDIA_FILE_EXPOSURE_RISK_ACCEPTABLE = "media_file_exposure_risk_acceptable";

    /**
     * Versión del Sanitizer HTML. El componente Web de las aplicaciones generadas con la plataforma jee2 incluye el jar de OWASP HTML Sanitizer entre
     * sus dependencias. OWASP HTML Sanitizer es un Sanitizer HTML de fácil configuración que permite prevenir ataques del tipo Cross-Site Scripting
     * (XSS), que podrían ocurrir por incluir HTML creado por terceros en su aplicación web. Como parte de la refinación de la aplicación, se podrían
     * definir e implementar políticas de saneamiento de los documentos incrustados utilizando OWASP HTML Sanitizer.
     *
     * @see <a href="https://github.com/OWASP/java-html-sanitizer/blob/master/docs/getting_started.md">OWASP HTML Sanitizer</a>
     *
     */
    public static final String HTML_SANITIZER_VERSION = "html_sanitizer_version";

    /**
     * Logger de EclipseLink. Especifique JavaLogger para utilizar java.util.logging; ServerLogger para integrarse con el logging del servidor de
     * aplicaciones; enabled o DefaultLogger para utilizar el logger predeterminado y poder establecer el nivel de logging en EclipseLink. El valor
     * por omisión es disabled.
     */
    public static final String PERSISTENCE_UNIT_ECLIPSELINK_LOGGER = "persistence_unit_eclipselink_logger";

    /**
     * enabled para habilitar la generación de estadísticas de Hibernate, de lo contrario disabled. El valor predeterminado es disabled.
     */
    public static final String PERSISTENCE_UNIT_HIBERNATE_STATISTICS = "persistence_unit_hibernate_statistics";

    /**
     * true si el encabezado de página de los informes generados debe ser de doble altura; de lo contrario false. El valor predeterminado es false.
     */
    public static final String RPH_DOUBLE_HEIGHT = "rph_double_height";

    /**
     * Número de pixeles de ancho de la imagen incluida en el bloque izquierdo del encabezado de página de los informes generados. El valor máximo es
     * 160. El valor predeterminado es 56.
     */
    public static final String RPH_LEFT_IMAGE_WIDTH = "rph_left_image_width";

    /**
     * Número de pixeles de alto de la imagen incluida en el bloque izquierdo del encabezado de página de los informes generados. El valor máximo es
     * 64 o 32, dependiendo de si el encabezado es o no es de doble altura, respectivamente. El valor predeterminado es 32.
     */
    public static final String RPH_LEFT_IMAGE_HEIGHT = "rph_left_image_height";

    /**
     * true si el encabezado de página de los informes generados no debe incluir el archivo de imagen alias1.png (donde alias es el alias del proyecto
     * generado) en el bloque izquierdo del encabezado; de lo contrario false. El valor predeterminado es false. Cada directorio de informes contiene
     * un subdirectorio resources donde se encuentra un archivo alias1.png.
     */
    public static final String RPH_NO_LEFT_IMAGE = "rph_no_left_image";

    /**
     * Número de pixeles de ancho de la imagen incluida en el bloque derecho del encabezado de página de los informes generados. El valor máximo es
     * 160. El valor predeterminado es 56.
     */
    public static final String RPH_RIGHT_IMAGE_WIDTH = "rph_right_image_width";

    /**
     * Número de pixeles de alto de la imagen incluida en el bloque derecho del encabezado de página de los informes generados. El valor máximo es 64
     * o 32, dependiendo de si el encabezado es o no es de doble altura, respectivamente. El valor predeterminado es 32.
     */
    public static final String RPH_RIGHT_IMAGE_HEIGHT = "rph_right_image_height";

    /**
     * true si el encabezado de página de los informes generados no debe incluir el archivo de imagen alias2.png (donde alias es el alias del proyecto
     * generado) en el bloque derecho del encabezado; de lo contrario false. El valor predeterminado es false. Cada directorio de informes contiene un
     * subdirectorio resources donde se encuentra un archivo alias2.png.
     */
    public static final String RPH_NO_RIGHT_IMAGE = "rph_no_right_image";

    /**
     * URL del documento de ayuda o de la página que contiene la documentación de la aplicación generada.
     */
    public static final String HELP_DOCUMENT_URL = "help_document_url";

    /**
     * Ruta del subdirectorio del módulo Web de la aplicación generada en donde se deben almacenar los archivos de ayuda para que la interfaz gráfica
     * los pueda presentar al usuario. La ruta debe ser relativa al directorio src/main/webapp del módulo Web de la aplicación. El valor
     * predeterminado es <b>resources/help/custom-made</b>.
     */
    public static final String HELP_RESOURCES_FOLDER = "help_resources_folder";

    /**
     * Número de ancho de la ventana de ayuda de la interfaz gráfica de la aplicación generada. El valor mínimo es 0. El valor máximo es 10. El valor
     * por omisión es 3. El ancho de la ventana de ayuda será el ancho disponible de la pantalla multiplicado por 2/(2+n), donde n es el valor de este
     * atributo. Por ejemplo, si es 0, el ancho de la ventana de ayuda será igual al ancho disponible de la pantalla; si es 5, 2/7 del ancho
     * disponible de la pantalla; y si es 10, 1/6 del ancho disponible de la pantalla.
     */
    public static final String HELP_WINDOW_WIDTH_KEY = "help_window_width_key";

    /**
     * Número máximo de elementos por nivel de menús escalonados. El valor mínimo es 5. El valor máximo es 20. El valor por omisión es 20.
     */
    public static final String TIERED_MENU_MAX_ITEMS_PER_TIER = "tiered_menu_max_items_per_tier";

    /**
     * Número de minutos que se debe especificar como tiempo de espera (valor del elemento session-timeout) al generar el descriptor de despliegue
     * (web.xml) del módulo alias-web de la aplicación, donde alias es el alias del proyecto generado. El valor predeterminado es 30.
     */
    public static final String SESSION_TIMEOUT = "session_timeout";

    /**
     * true si desea que el gestor de tareas envíe todos los mensajes, que deban ser enviados en cada revisión, antes de confirmar sus cambios a la
     * base de datos. false, si desea que el gestor de tareas confirme sus cambios a la base de datos a medida que envía los mensajes. El valor por
     * omisión es false.
     */
    public static final String TASK_MAIL_BEFORE_COMMIT = "task_mail_before_commit";

    /**
     * true si desea que el gestor de tareas envíe mensajes por cada tarea; de lo contrario false. El valor predeterminado es true.
     */
    public static final String TASK_MAIL_PER_TASK = "task_mail_per_task";

    /**
     * true si desea que el gestor de tareas envíe mensajes por cada tipo de tarea; de lo contrario false. El valor predeterminado es false. Este
     * atributo solo es relevante si el valor del atributo TASK_MAIL_BEFORE_COMMIT es false.
     */
    public static final String TASK_MAIL_PER_TYPE = "task_mail_per_type";

    /**
     * true si desea que el gestor de tareas envíe los mensajes a través de un solo hilo de ejecución. false, si desea que el gestor de tareas gestor
     * de tareas envíe los mensajes a través de múltiples hilos de ejecución. El valor predeterminado es false. Este atributo solo es relevante si el
     * valor del atributo TASK_MAIL_BEFORE_COMMIT es false.
     */
    public static final String TASK_MAIL_SINGLE_THREADED = "task_mail_single_threaded";

    /**
     * Especifique la dirección de correo electrónico a la que el gestor de tareas debe enviar los mensajes cuando no sea posible determinar una
     * dirección de correo válida para los mensajes de una tarea.
     */
    public static final String TASK_MAIL_ADDRESS = "task_mail_address";

    /**
     * true si desea que el gestor de tareas envíe mensajes de texto por cada tipo de tarea; de lo contrario false. El valor predeterminado es false.
     */
    public static final String TASK_TEXT_PER_TYPE = "task_text_per_type";

    /**
     * true si desea que la aplicación envíe una notificación al usuario cada vez que se detecta un intento fallido de inicio de sesión de trabajo con
     * su código de usuario; de lo contrario false. El valor predeterminado es false.
     */
    public static final String INVALID_LOGIN_NOTIFICATION = "invalid_login_notification";

    /**
     * true si desea que la aplicación envíe una notificación al usuario cada vez que se da inicio a una sesión de trabajo con su código de usuario;
     * de lo contrario false. El valor predeterminado es false.
     */
    public static final String LOGIN_NOTIFICATION = "login_notification";

    /**
     * Especifique el protocolo que se usa para comunicarse con la aplicación (normalmente http o https). El valor predeterminado es http.
     */
    public static final String URI_SCHEME = "uri_scheme";

    /**
     * Especifique el servidor (host) y el puerto de red en el servidor para comunicarse con la aplicación, separados por dos puntos. El valor por
     * omisión es localhost:8080.
     */
    public static final String URI_AUTHORITY = "uri_authority";

    /**
     * Especifique la extensión predeterminada de los archivos de páginas Web de la aplicación. El valor predeterminado es xhtml.
     */
    public static final String WEB_PAGES_FILE_EXTENSION = "web_pages_file_extension";

}
