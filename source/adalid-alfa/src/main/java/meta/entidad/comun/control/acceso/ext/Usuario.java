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
package meta.entidad.comun.control.acceso.ext;

import adalid.core.*;
import adalid.core.annotations.*;
import adalid.core.enums.*;
import adalid.core.interfaces.*;
import adalid.core.parameters.*;
import adalid.core.properties.*;
import java.lang.reflect.Field;
import meta.entidad.comun.configuracion.basica.Pagina;
import meta.entidad.comun.configuracion.basica.PaginaInicio;
import meta.entidad.comun.control.acceso.PaginaEspecial;
import meta.entidad.comun.control.acceso.RolUsuario;
import meta.entidad.comun.control.acceso.TipoRestriccionFormatos;
import meta.entidad.comun.control.acceso.UsuarioModulo;

/**
 * @author Jorge Campins
 */
@EntityClass(independent = Kleenean.TRUE, resourceType = ResourceType.OPERATION, resourceGender = ResourceGender.MASCULINE)
@EntityCodeGen(fws = Kleenean.FALSE)
@EntityDocGen(stateDiagram = Kleenean.FALSE)
@EntitySelectOperation(enabled = Kleenean.TRUE, onload = SelectOnloadOption.EXECUTE)
@EntityInsertOperation(enabled = Kleenean.TRUE)
@EntityUpdateOperation(enabled = Kleenean.TRUE)
@EntityDeleteOperation(enabled = Kleenean.TRUE)
@EntityTableView(enabled = Kleenean.TRUE, inserts = Kleenean.FALSE, updates = Kleenean.TRUE)
@EntityDetailView(enabled = Kleenean.TRUE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.TRUE)
@EntityTriggers(afterValue = Kleenean.TRUE)
public class Usuario extends meta.entidad.comun.control.acceso.Usuario {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public Usuario(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @Allocation(maxDepth = 1, maxRound = 0)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.TRUE, overlay = Kleenean.TRUE)
    public Usuario usuarioSupervisor;

    @ImageProperty
    @PropertyField(table = Kleenean.TRUE)
    public BinaryProperty octetos;

    @FileReference
    @PropertyField(hidden = Kleenean.TRUE)
    public StringProperty archivo;

    @ColumnField(nullable = Kleenean.FALSE)
//  @NumericField(symbol = "?", symbolPosition = SymbolPosition.SUFFIX)
    @PropertyField(create = Kleenean.TRUE)
    public IntegerProperty limiteArchivoDetalle;

    @ColumnField(nullable = Kleenean.FALSE)
//  @NumericField(symbol = "?", symbolPosition = SymbolPosition.SUFFIX)
    @PropertyField(create = Kleenean.TRUE)
    public IntegerProperty limiteArchivoResumen;

    @ColumnField(nullable = Kleenean.FALSE)
//  @NumericField(symbol = "?", symbolPosition = SymbolPosition.SUFFIX)
    @PropertyField(create = Kleenean.TRUE)
    public IntegerProperty limiteInformeDetalle;

    @ColumnField(nullable = Kleenean.FALSE)
//  @NumericField(symbol = "?", symbolPosition = SymbolPosition.SUFFIX)
    @PropertyField(create = Kleenean.TRUE)
    public IntegerProperty limiteInformeResumen;

    @ColumnField(nullable = Kleenean.FALSE)
//  @NumericField(symbol = "?", symbolPosition = SymbolPosition.SUFFIX)
    @PropertyField(create = Kleenean.TRUE)
    public IntegerProperty limiteInformeGrafico;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(create = Kleenean.TRUE)
    public BooleanProperty menusRegistrados;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(create = Kleenean.TRUE)
    public BooleanProperty menusRestringidos;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(create = Kleenean.TRUE)
    public BooleanProperty operadoresRestringidos;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(create = Kleenean.TRUE)
    public BooleanProperty filtrosRestringidos;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(create = Kleenean.TRUE)
    public BooleanProperty vistasRestringidas;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(create = Kleenean.TRUE)
    public BooleanProperty descargasRestringidas;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(create = Kleenean.TRUE)
    public TipoRestriccionFormatos restriccionFormatos;

    @Allocation(maxDepth = 1, maxRound = 0)
    @ColumnField(nullable = Kleenean.TRUE)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(create = Kleenean.TRUE, update = Kleenean.TRUE)
    public PaginaInicio paginaInicio;

    @Allocation(maxDepth = 2, maxRound = 0)
    @ColumnField(nullable = Kleenean.TRUE)
//  @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(create = Kleenean.TRUE, update = Kleenean.TRUE)
    public Pagina paginaMenu;

    @Allocation(maxDepth = 1, maxRound = 0)
    @ColumnField(nullable = Kleenean.TRUE)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(create = Kleenean.TRUE, update = Kleenean.TRUE)
    public PaginaEspecial otraPagina;

    @ColumnField(nullable = Kleenean.TRUE)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE)
    @StringField(maxLength = 100)
    public StringProperty temaInterfaz;

    @ColumnField(nullable = Kleenean.TRUE)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE)
    public IntegerProperty filasPorPagina;

    @ColumnField(nullable = Kleenean.TRUE)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE)
    public BooleanProperty ayudaPorCampos;

    @ColumnField(nullable = Kleenean.TRUE)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE)
    public BooleanProperty confirmarAlDescartar;

    @ColumnField(nullable = Kleenean.TRUE)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE)
    public BooleanProperty confirmarAlFinalizar;

    @EntityCollectionField() // set element values with setters so they can be easily overriden
    @OneToMany(targetEntity = UsuarioModulo.class, mappedBy = "usuario") // set other element values with setters so they can be easily overriden
    public EntityCollection modulos;

    @EntityCollectionField() // set element values with setters so they can be easily overriden
    @OneToMany(targetEntity = RolUsuario.class, mappedBy = "usuario") // set other element values with setters so they can be easily overriden
    public EntityCollection roles;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setOrderBy(codigoUsuario);
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of Usuario's attributes">
        setLocalizedLabel(ENGLISH, "user");
        setLocalizedLabel(SPANISH, "usuario");
        setLocalizedCollectionLabel(ENGLISH, "Users");
        setLocalizedCollectionLabel(SPANISH, "Usuarios");
        // </editor-fold>
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        /**/
        esSuperUsuario.setInitialValue(false);
        esSuperUsuario.setDefaultValue(false);
        esSuperAuditor.setInitialValue(false);
        esSuperAuditor.setDefaultValue(false);
        esUsuarioEspecial.setInitialValue(false);
        esUsuarioEspecial.setDefaultValue(false);
        esUsuarioInactivo.setInitialValue(false);
        esUsuarioInactivo.setDefaultValue(false);
        esUsuarioModificado.setInitialValue(false);
        esUsuarioModificado.setDefaultValue(false);
        esUsuarioAutomatico.setInitialValue(false);
        esUsuarioAutomatico.setDefaultValue(false);
        /**/
        limiteArchivoDetalle.setInitialValue(10000);
        limiteArchivoDetalle.setDefaultValue(10000);
        limiteArchivoDetalle.setMinValue(0);
        limiteArchivoDetalle.setMaxValue(1000000);
        limiteArchivoResumen.setInitialValue(10000);
        limiteArchivoResumen.setDefaultValue(10000);
        limiteArchivoResumen.setMinValue(0);
        limiteArchivoResumen.setMaxValue(1000000);
        limiteInformeDetalle.setInitialValue(10000);
        limiteInformeDetalle.setDefaultValue(10000);
        limiteInformeDetalle.setMinValue(0);
        limiteInformeDetalle.setMaxValue(1000000);
        limiteInformeResumen.setInitialValue(10000);
        limiteInformeResumen.setDefaultValue(10000);
        limiteInformeResumen.setMinValue(0);
        limiteInformeResumen.setMaxValue(1000000);
        limiteInformeGrafico.setInitialValue(10000);
        limiteInformeGrafico.setDefaultValue(10000);
        limiteInformeGrafico.setMinValue(0);
        limiteInformeGrafico.setMaxValue(1000000);
        menusRegistrados.setInitialValue(false);
        menusRegistrados.setDefaultValue(false);
        menusRestringidos.setInitialValue(false);
        menusRestringidos.setDefaultValue(false);
        operadoresRestringidos.setInitialValue(false);
        operadoresRestringidos.setDefaultValue(false);
        filtrosRestringidos.setInitialValue(false);
        filtrosRestringidos.setDefaultValue(false);
        vistasRestringidas.setInitialValue(false);
        vistasRestringidas.setDefaultValue(false);
        descargasRestringidas.setInitialValue(false);
        descargasRestringidas.setDefaultValue(false);
        restriccionFormatos.setInitialValue(restriccionFormatos.NADA);
        restriccionFormatos.setDefaultValue(restriccionFormatos.NADA);
        paginaInicio.setDefaultValue(paginaInicio.PREDETERMINADA);
        paginaInicio.setInitialValue(paginaInicio.PREDETERMINADA);
        filasPorPagina.setDefaultValue(0);
        filasPorPagina.setMaxValue(Constants.MAXIMUM_ROWS_PER_PAGE_LIMIT);
        filasPorPagina.setMinValue(0);
        ayudaPorCampos.setInitialValue(true);
        ayudaPorCampos.setDefaultValue(true);
        confirmarAlDescartar.setInitialValue(true);
        confirmarAlDescartar.setDefaultValue(true);
        confirmarAlFinalizar.setInitialValue(true);
        confirmarAlFinalizar.setDefaultValue(true);
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of Usuario's properties">
        /**/
        usuarioSupervisor.setLocalizedLabel(ENGLISH, "supervisor");
        usuarioSupervisor.setLocalizedLabel(SPANISH, "usuario supervisor");
        usuarioSupervisor.setLocalizedShortLabel(ENGLISH, "supervisor");
        usuarioSupervisor.setLocalizedShortLabel(SPANISH, "supervisor");
        usuarioSupervisor.setLocalizedDescription(ENGLISH, "user that supervises this user");
        usuarioSupervisor.setLocalizedDescription(SPANISH, "usuario que supervisa a este usuario");
        usuarioSupervisor.setLocalizedTooltip(ENGLISH, "supervisor code");
        usuarioSupervisor.setLocalizedTooltip(SPANISH, "código del usuario supervisor");
        /**/
        octetos.setLocalizedLabel(ENGLISH, "portrait");
        octetos.setLocalizedLabel(SPANISH, "retrato");
        octetos.setLocalizedTooltip(ENGLISH, "user portrait");
        octetos.setLocalizedTooltip(SPANISH, "retrato del usuario");
        /**/
        archivo.setLocalizedLabel(ENGLISH, "file");
        archivo.setLocalizedLabel(SPANISH, "archivo");
        archivo.setLocalizedTooltip(ENGLISH, "user file");
        archivo.setLocalizedTooltip(SPANISH, "archivo del usuario");
        /**/
        limiteArchivoDetalle.setLocalizedLabel(ENGLISH, "detailed file limit");
        limiteArchivoDetalle.setLocalizedLabel(SPANISH, "limite archivo detalle");
        limiteArchivoDetalle.setLocalizedDescription(ENGLISH, "limit of rows of user-defined views that the user can save in "
            + "files of type \"Detail\"; if it is 0 the user has no limit");
        limiteArchivoDetalle.setLocalizedDescription(SPANISH, "límite de filas al guardar los resultados de la consulta, "
            + "mediante vistas definidas por el usuario, en archivos de tipo \"Detalle\"; si es 0 el usuario no tiene límite");
        limiteArchivoDetalle.setLocalizedTooltip(ENGLISH, "limit of rows for files of type \"Detail\"");
        limiteArchivoDetalle.setLocalizedTooltip(SPANISH, "límite de filas para archivos de tipo \"Detalle\"");
//      limiteArchivoDetalle.setLocalizedSymbol(ENGLISH, "rows");
//      limiteArchivoDetalle.setLocalizedSymbol(SPANISH, "filas");
        /**/
        limiteArchivoResumen.setLocalizedLabel(ENGLISH, "summary file limit");
        limiteArchivoResumen.setLocalizedLabel(SPANISH, "limite archivo resumen");
        limiteArchivoResumen.setLocalizedDescription(ENGLISH, "limit of rows of user-defined views that the user can save in "
            + "files of type \"Summary\"; if it is 0 the user has no limit");
        limiteArchivoResumen.setLocalizedDescription(SPANISH, "límite de filas al guardar los resultados de la consulta, "
            + "mediante vistas definidas por el usuario, en archivos de tipo \"Resumen\"; si es 0 el usuario no tiene límite");
        limiteArchivoResumen.setLocalizedTooltip(ENGLISH, "limit of rows for files of type \"Summary\"");
        limiteArchivoResumen.setLocalizedTooltip(SPANISH, "límite de filas para archivos de tipo \"Resumen\"");
//      limiteArchivoResumen.setLocalizedSymbol(ENGLISH, "rows");
//      limiteArchivoResumen.setLocalizedSymbol(SPANISH, "filas");
        /**/
        limiteInformeDetalle.setLocalizedLabel(ENGLISH, "detailed report limit");
        limiteInformeDetalle.setLocalizedLabel(SPANISH, "limite informe detalle");
        limiteInformeDetalle.setLocalizedDescription(ENGLISH, "limit of rows of user-defined views that the user can save in "
            + "reports of type \"Detail\"; if it is 0 the user has no limit");
        limiteInformeDetalle.setLocalizedDescription(SPANISH, "límite de filas al guardar los resultados de la consulta, "
            + "mediante vistas definidas por el usuario, en informes de tipo \"Detalle\"; si es 0 el usuario no tiene límite");
        limiteInformeDetalle.setLocalizedTooltip(ENGLISH, "limit of rows for reports of type \"Detail\"");
        limiteInformeDetalle.setLocalizedTooltip(SPANISH, "límite de filas para informes de tipo \"Detalle\"");
//      limiteInformeDetalle.setLocalizedSymbol(ENGLISH, "rows");
//      limiteInformeDetalle.setLocalizedSymbol(SPANISH, "filas");
        /**/
        limiteInformeResumen.setLocalizedLabel(ENGLISH, "summary report limit");
        limiteInformeResumen.setLocalizedLabel(SPANISH, "limite informe resumen");
        limiteInformeResumen.setLocalizedDescription(ENGLISH, "limit of rows of user-defined views that the user can save in "
            + "reports of type \"Summary\"; if it is 0 the user has no limit");
        limiteInformeResumen.setLocalizedDescription(SPANISH, "límite de filas al guardar los resultados de la consulta, "
            + "mediante vistas definidas por el usuario, en informes de tipo \"Resumen\"; si es 0 el usuario no tiene límite");
        limiteInformeResumen.setLocalizedTooltip(ENGLISH, "limit of rows for reports of type \"Summary\"");
        limiteInformeResumen.setLocalizedTooltip(SPANISH, "límite de filas para informes de tipo \"Resumen\"");
//      limiteInformeResumen.setLocalizedSymbol(ENGLISH, "rows");
//      limiteInformeResumen.setLocalizedSymbol(SPANISH, "filas");
        /**/
        limiteInformeGrafico.setLocalizedLabel(ENGLISH, "chart report limit");
        limiteInformeGrafico.setLocalizedLabel(SPANISH, "limite informe grafico");
        limiteInformeGrafico.setLocalizedDescription(ENGLISH, "limit of rows of user-defined views that the user can save in "
            + "reports of type \"Chart\"; if it is 0 the user has no limit");
        limiteInformeGrafico.setLocalizedDescription(SPANISH, "límite de filas al guardar los resultados de la consulta, "
            + "mediante vistas definidas por el usuario, en informes de tipo \"Gráfico\"; si es 0 el usuario no tiene límite");
        limiteInformeGrafico.setLocalizedTooltip(ENGLISH, "limit of rows for reports of type \"Chart\"");
        limiteInformeGrafico.setLocalizedTooltip(SPANISH, "límite de filas para informes de tipo \"Gráfico\"");
//      limiteInformeGrafico.setLocalizedSymbol(ENGLISH, "rows");
//      limiteInformeGrafico.setLocalizedSymbol(SPANISH, "filas");
        /**/
        menusRegistrados.setLocalizedLabel(ENGLISH, "registered menus");
        menusRegistrados.setLocalizedLabel(SPANISH, "menús registrados");
        menusRegistrados.setLocalizedDescription(ENGLISH, "access only to menus of modules registered in the user's list of modules");
        menusRegistrados.setLocalizedDescription(SPANISH, "acceso solo a los menús de los módulos registrados en la lista de módulos del usuario");
        menusRegistrados.setLocalizedTooltip(ENGLISH, "access only to menus of modules registered in the user's list of modules");
        menusRegistrados.setLocalizedTooltip(SPANISH, "acceso solo a los menús de los módulos registrados en la lista de módulos del usuario");
        /**/
        menusRestringidos.setLocalizedLabel(ENGLISH, "restricted menus");
        menusRestringidos.setLocalizedLabel(SPANISH, "menús restringidos");
        menusRestringidos.setLocalizedDescription(ENGLISH, "restricted access to query page menus");
        menusRestringidos.setLocalizedDescription(SPANISH, "acceso restringido a menús de páginas de consulta");
        menusRestringidos.setLocalizedTooltip(ENGLISH, "restricted access to query menus");
        menusRestringidos.setLocalizedTooltip(SPANISH, "acceso restringido a menús de consulta");
        /**/
        operadoresRestringidos.setLocalizedLabel(ENGLISH, "restricted operators");
        operadoresRestringidos.setLocalizedLabel(SPANISH, "operadores restringidos");
        operadoresRestringidos.setLocalizedDescription(ENGLISH, "restricted access to comparison operators in search criteria");
        operadoresRestringidos.setLocalizedDescription(SPANISH, "acceso restringido a operadores de comparación en criterios de búsqueda");
        operadoresRestringidos.setLocalizedTooltip(ENGLISH, "restricted access to comparison operators");
        operadoresRestringidos.setLocalizedTooltip(SPANISH, "acceso restringido a operadores de comparación");
        /**/
        filtrosRestringidos.setLocalizedLabel(ENGLISH, "restricted filters");
        filtrosRestringidos.setLocalizedLabel(SPANISH, "filtros restringidos");
        filtrosRestringidos.setLocalizedDescription(ENGLISH, "restricted access to search filters on query or registration pages");
        filtrosRestringidos.setLocalizedDescription(SPANISH, "acceso restringido a filtros de búsqueda en páginas de consulta o registro");
        filtrosRestringidos.setLocalizedTooltip(ENGLISH, "restricted access to query filters");
        filtrosRestringidos.setLocalizedTooltip(SPANISH, "acceso restringido a filtros de búsqueda");
        /**/
        vistasRestringidas.setLocalizedLabel(ENGLISH, "restricted views");
        vistasRestringidas.setLocalizedLabel(SPANISH, "vistas restringidas");
        vistasRestringidas.setLocalizedDescription(ENGLISH, "restricted access to save query results, "
            + "via user-defined views, in files and reports of any format");
        vistasRestringidas.setLocalizedDescription(SPANISH, "acceso restringido para guardar los resultados de la consulta, "
            + "mediante vistas definidas por el usuario, en archivos e informes de cualquier formato");
        vistasRestringidas.setLocalizedTooltip(ENGLISH, "restricted access to files and reports");
        vistasRestringidas.setLocalizedTooltip(SPANISH, "acceso restringido a archivos e informes");
        /**/
        descargasRestringidas.setLocalizedLabel(ENGLISH, "restricted downloads");
        descargasRestringidas.setLocalizedLabel(SPANISH, "descargas restringidas");
        descargasRestringidas.setLocalizedDescription(ENGLISH, "restricted access to download files of any format");
        descargasRestringidas.setLocalizedDescription(SPANISH, "acceso restringido para descargar archivos de cualquier formato");
        descargasRestringidas.setLocalizedTooltip(ENGLISH, "restricted access to file downloads");
        descargasRestringidas.setLocalizedTooltip(SPANISH, "acceso restringido a descargas de archivos");
        /**/
        restriccionFormatos.setLocalizedLabel(ENGLISH, "restricted formats");
        restriccionFormatos.setLocalizedLabel(SPANISH, "formatos restringidos");
        restriccionFormatos.setLocalizedDescription(ENGLISH, "restricted access to download files of restricted formats and/or "
            + "to generate files and reports in editable formats, such as CSV, TSV, RTF, ODT, ODS, HTML, DOCX, PPTX and XLSX");
        restriccionFormatos.setLocalizedDescription(SPANISH, "acceso restringido para descargar archivos de formatos restringidos y/o "
            + "para generar archivos e informes en formatos editables, tales como CSV, TSV, RTF, ODT, ODS, HTML, DOCX, PPTX y XLSX");
        restriccionFormatos.setLocalizedTooltip(ENGLISH, "restricted access to file formats"
            + ", such as CSV, TSV, RTF, ODT, ODS, HTML, DOCX, PPTX and XLSX");
        restriccionFormatos.setLocalizedTooltip(SPANISH, "acceso restringido a formatos de archivo"
            + ", tales como CSV, TSV, RTF, ODT, ODS, HTML, DOCX, PPTX y XLSX");
        /**/
        paginaInicio.setLocalizedLabel(ENGLISH, "landing page");
        paginaInicio.setLocalizedLabel(SPANISH, "página de inicio");
        /**/
        paginaMenu.setLocalizedLabel(ENGLISH, "menu page");
        paginaMenu.setLocalizedLabel(SPANISH, "página del menú");
        paginaMenu.setLocalizedDescription(ENGLISH, "page to be used as home page; must be specified if landing page is \"Menu page\"");
        paginaMenu.setLocalizedDescription(SPANISH, "página que se ha de utilizar como página de inicio; "
            + "se debe especificar si página de inicio es \"Página del menú\"");
        /**/
        otraPagina.setLocalizedLabel(ENGLISH, "other page");
        otraPagina.setLocalizedLabel(SPANISH, "otra página");
        otraPagina.setLocalizedDescription(ENGLISH, "special page to be used as home page; must be specified if landing page is \"Other page\"");
        otraPagina.setLocalizedDescription(SPANISH, "página especial que se ha de utilizar como página de inicio; "
            + "se debe especificar si página de inicio es \"Otra página\"");
        /**/
        temaInterfaz.setLocalizedLabel(ENGLISH, "user interface theme");
        temaInterfaz.setLocalizedLabel(SPANISH, "tema de la interfaz");
        /**/
        filasPorPagina.setLocalizedLabel(ENGLISH, "rows per page");
        filasPorPagina.setLocalizedLabel(SPANISH, "filas por página");
        filasPorPagina.setLocalizedDescription(ENGLISH, "initial number of rows per page");
        filasPorPagina.setLocalizedDescription(SPANISH, "número inicial de filas por página en las páginas de consulta y/o registro tabular");
        /**/
        ayudaPorCampos.setLocalizedLabel(ENGLISH, "inline help");
        ayudaPorCampos.setLocalizedLabel(SPANISH, "ayuda en línea");
        ayudaPorCampos.setLocalizedDescription(ENGLISH, "show the descriptions of the fields on the page");
        ayudaPorCampos.setLocalizedDescription(SPANISH, "mostrar las descripciones de los campos de la página");
        /**/
        confirmarAlDescartar.setLocalizedLabel(ENGLISH, "confirm discarding");
        confirmarAlDescartar.setLocalizedLabel(SPANISH, "confirmar al descartar");
        confirmarAlDescartar.setLocalizedDescription(ENGLISH, "confirm discarding on registration pages");
        confirmarAlDescartar.setLocalizedDescription(SPANISH, "confirmar al descartar en las páginas de registro");
        /**/
        confirmarAlFinalizar.setLocalizedLabel(ENGLISH, "confirm end session");
        confirmarAlFinalizar.setLocalizedLabel(SPANISH, "confirmar al finalizar la sesión");
        confirmarAlFinalizar.setLocalizedDescription(ENGLISH, "confirm end session");
        confirmarAlFinalizar.setLocalizedDescription(SPANISH, "confirmar al finalizar la sesión de trabajo");
        /**/
        // </editor-fold>
    }

    @Override
    protected void settleCollections() {
        super.settleCollections();
        /**/
        modulos.setCascadeType(CascadeType.PERSIST, CascadeType.MERGE);
        /**/
        roles.setCascadeType(CascadeType.PERSIST, CascadeType.MERGE);
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of Usuario's collections">
        /**/
        modulos.setLocalizedLabel(ENGLISH, "user modules");
        modulos.setLocalizedLabel(SPANISH, "módulos del usuario");
        modulos.setLocalizedShortLabel(ENGLISH, "modules");
        modulos.setLocalizedShortLabel(SPANISH, "módulos");
        modulos.setLocalizedDescription(ENGLISH, "user module collection");
        modulos.setLocalizedDescription(SPANISH, "colección de módulos del usuario");
        /**/
        roles.setLocalizedLabel(ENGLISH, "user roles");
        roles.setLocalizedLabel(SPANISH, "roles del usuario");
        roles.setLocalizedShortLabel(ENGLISH, "roles");
        roles.setLocalizedShortLabel(SPANISH, "roles");
        roles.setLocalizedDescription(ENGLISH, "user role collection");
        roles.setLocalizedDescription(SPANISH, "colección de roles del usuario");
        /**/
        // </editor-fold>
    }

    protected Step step10, step20, step30, step40, step50, step60;

    @Override
    protected void settleSteps() {
        super.settleSteps();
        /**/
        step10.newStepField(codigoUsuario, nombreUsuario, correoElectronico, numeroTelefonoMovil);
        /**/
        step20.newStepField(paginaInicio, paginaMenu, otraPagina, temaInterfaz, filasPorPagina, ayudaPorCampos, confirmarAlDescartar, confirmarAlFinalizar);
        /**/
        step30.newStepField(limiteArchivoDetalle, limiteArchivoResumen, limiteInformeDetalle, limiteInformeResumen, limiteInformeGrafico);
        /**/
        step40.newStepField(menusRegistrados, menusRestringidos, operadoresRestringidos, filtrosRestringidos, vistasRestringidas, descargasRestringidas, restriccionFormatos);
        /**/
        step50.newStepField(modulos);
        /**/
        step60.newStepField(roles);
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of Usuario's steps">
        /**/
        step10.setLocalizedLabel(ENGLISH, "General User Information");
        step10.setLocalizedLabel(SPANISH, "Información General del Usuario");
        step10.setLocalizedShortLabel(ENGLISH, "general");
        step10.setLocalizedShortLabel(SPANISH, "general");
        /**/
        step20.setLocalizedLabel(ENGLISH, "Custom Settings");
        step20.setLocalizedLabel(SPANISH, "Configuración Personalizada");
        step20.setLocalizedShortLabel(ENGLISH, "settings");
        step20.setLocalizedShortLabel(SPANISH, "configuración");
        /**/
        step30.setLocalizedLabel(ENGLISH, "Export and Report Limits");
        step30.setLocalizedLabel(SPANISH, "Límites de Archivos e Informes");
        step30.setLocalizedShortLabel(ENGLISH, "limits");
        step30.setLocalizedShortLabel(SPANISH, "límites");
        /**/
        step40.setLocalizedLabel(ENGLISH, "Restriction of Actions and Options");
        step40.setLocalizedLabel(SPANISH, "Restricción de Acciones y Opciones");
        step40.setLocalizedShortLabel(ENGLISH, "restrictions");
        step40.setLocalizedShortLabel(SPANISH, "restricciones");
        /**/
        step50.setLocalizedLabel(ENGLISH, "User Modules");
        step50.setLocalizedLabel(SPANISH, "Módulos del Usuario");
        step50.setLocalizedShortLabel(ENGLISH, "modules");
        step50.setLocalizedShortLabel(SPANISH, "módulos");
        /**/
        step60.setLocalizedLabel(ENGLISH, "User Roles");
        step60.setLocalizedLabel(SPANISH, "Roles del Usuario");
        step60.setLocalizedShortLabel(ENGLISH, "roles");
        step60.setLocalizedShortLabel(SPANISH, "roles");
        /**/
        // </editor-fold>
    }

    protected Tab tab10, tab20, tab30, tab40, tab50, tab60;

    @Override
    protected void settleTabs() {
        super.settleTabs();
        /**/
        tab10.copy(step10, false); // false para una copia superficial, excluyendo los campos del paso.
        tab10.newTabField(octetos, archivo, correoElectronico, numeroTelefonoMovil);
        tab10.newTabField(esSuperUsuario, esSuperAuditor, esUsuarioEspecial, esUsuarioInactivo, esUsuarioAutomatico);
        tab10.newTabField(fechaHoraRegistro, usuarioSupervisor);
        /**/
        tab20.copy(step20);
        /**/
        tab30.copy(step30);
        /**/
        tab40.copy(step40);
        /**/
        tab50.copy(step50);
        /**/
        tab60.copy(step60);
        /**/
    }

    protected Segment usuarioActual, demasUsuarios;

    protected Segment superUsuarios, superAuditores, usuariosEstandar;

    protected Segment usuariosActivos, usuariosInactivos;

    protected Segment conSupervisor, sinSupervisor;

    protected Segment conPaginaMenu, conOtraPagina;

    protected Segment filtroPaginaMenu, filtroOtraPagina;

    protected Check check101, check102, check111, check112;

    protected State eliminable, modificable, desactivable, reactivable, supervisorAnulable, supervisorAsignable;

    protected State designableComoSuperUsuario, superUsuarioAnulable;

    protected State designableComoSuperAuditor, superAuditorAnulable;

    /**/
    @Override
    protected void settleExpressions() {
        super.settleExpressions();
        /**/
        usuarioActual = id.isEqualTo(CURRENT_USER_ID);
        demasUsuarios = id.isNotEqualTo(CURRENT_USER_ID);
        /**/
        superUsuarios = esSuperUsuario.isTrue();
        superAuditores = esSuperAuditor.isTrue();
        usuariosEstandar = esSuperUsuario.isFalse();
        usuariosActivos = esUsuarioInactivo.isFalse();
        usuariosInactivos = esUsuarioInactivo.isTrue();
        conSupervisor = usuarioSupervisor.isNotNull();
        sinSupervisor = usuarioSupervisor.isNull();
        /**/
        conPaginaMenu = paginaInicio.isEqualTo(paginaInicio.PAGINA_MENU);
        conOtraPagina = paginaInicio.isEqualTo(paginaInicio.OTRA_PAGINA);
        /*
        filtroPaginaMenu = and(
            paginaMenu.dominioMaestro.isNull(),
            paginaMenu.parametro.isNull(),
            paginaMenu.tipoPagina.isNotEqualTo(paginaMenu.tipoPagina.DETALLE),
            paginaMenu.tipoPagina.isNotEqualTo(paginaMenu.tipoPagina.CONSULTA_DETALLE),
            paginaMenu.urlPagina.notContains("/consulta/recursos/basicos/"),
            paginaMenu.urlPagina.notContains("/procesamiento/recursos/basicos/"),
            paginaMenu.urlPagina.notContains("/registro/recursos/basicos/")
        );
        /**/
        filtroPaginaMenu = paginaMenu.opcionMenu.isTrue();
        /**/
        filtroOtraPagina = otraPagina.inactiva.isFalse();
        /**/
        check101 = conPaginaMenu.implies(paginaMenu.isNotNull());
        check102 = conPaginaMenu.and(paginaMenu.isNotNull()).implies(filtroPaginaMenu);
        check102.setCheckEvent(CheckEvent.UPDATE);
        check102.setCheckpoint(Checkpoint.USER_INTERFACE);
        /**/
        check111 = conOtraPagina.implies(otraPagina.isNotNull());
        check112 = conOtraPagina.and(otraPagina.isNotNull()).implies(filtroOtraPagina);
        check112.setCheckEvent(CheckEvent.UPDATE);
        check112.setCheckpoint(Checkpoint.USER_INTERFACE);
        /**/
        eliminable = usuariosOrdinarios.and(demasUsuarios);
        modificable = usuariosOrdinarios.and(usuariosActivos);
        reactivable = eliminable.and(usuariosInactivos);
        desactivable = eliminable.and(usuariosActivos);
        superUsuarioAnulable = desactivable.and(superUsuarios);
        designableComoSuperUsuario = desactivable.and(usuariosEstandar);
        superAuditorAnulable = desactivable.and(superAuditores);
        designableComoSuperAuditor = desactivable.and(usuariosEstandar).and(esSuperAuditor.isFalse());
        supervisorAnulable = eliminable.and(conSupervisor);
        supervisorAsignable = usuariosOrdinarios.and(demasUsuarios);
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of Usuario's expressions">
        /**/
        usuarioActual.setLocalizedDescription(ENGLISH, "the user is your own user");
        usuarioActual.setLocalizedDescription(SPANISH, "el usuario es su propio usuario");
        usuarioActual.setLocalizedErrorMessage(ENGLISH, "the user is not your own user");
        usuarioActual.setLocalizedErrorMessage(SPANISH, "el usuario no es su propio usuario");
        /**/
        demasUsuarios.setLocalizedDescription(ENGLISH, "the user is not your own user");
        demasUsuarios.setLocalizedDescription(SPANISH, "el usuario no es su propio usuario");
        demasUsuarios.setLocalizedErrorMessage(ENGLISH, "the user is your own user");
        demasUsuarios.setLocalizedErrorMessage(SPANISH, "el usuario es su propio usuario");
        /**/
        usuariosEspeciales.setLocalizedDescription(ENGLISH, "the user is a special user");
        usuariosEspeciales.setLocalizedDescription(SPANISH, "el usuario es un usuario especial");
        usuariosEspeciales.setLocalizedErrorMessage(ENGLISH, "the user is not a special user");
        usuariosEspeciales.setLocalizedErrorMessage(SPANISH, "el usuario no es un usuario especial");
        /**/
        usuariosOrdinarios.setLocalizedDescription(ENGLISH, "the user is not a special user");
        usuariosOrdinarios.setLocalizedDescription(SPANISH, "el usuario no es un usuario especial");
        usuariosOrdinarios.setLocalizedErrorMessage(ENGLISH, "the user is a special user");
        usuariosOrdinarios.setLocalizedErrorMessage(SPANISH, "el usuario es un usuario especial");
        /**/
        superUsuarios.setLocalizedDescription(ENGLISH, "the user is a super-user");
        superUsuarios.setLocalizedDescription(SPANISH, "el usuario es un súper-usuario");
        superUsuarios.setLocalizedErrorMessage(ENGLISH, "the user is not a super-user");
        superUsuarios.setLocalizedErrorMessage(SPANISH, "el usuario no es un súper-usuario");
        /**/
        superAuditores.setLocalizedDescription(ENGLISH, "the user is a super-auditor");
        superAuditores.setLocalizedDescription(SPANISH, "el usuario es un súper-auditor");
        superAuditores.setLocalizedErrorMessage(ENGLISH, "the user is not a super-auditor");
        superAuditores.setLocalizedErrorMessage(SPANISH, "el usuario no es un súper-auditor");
        /**/
        usuariosEstandar.setLocalizedDescription(ENGLISH, "the user is not a super-user");
        usuariosEstandar.setLocalizedDescription(SPANISH, "el usuario no es un súper-usuario");
        usuariosEstandar.setLocalizedErrorMessage(ENGLISH, "the user is a super-user");
        usuariosEstandar.setLocalizedErrorMessage(SPANISH, "el usuario es un súper-usuario");
        /**/
        usuariosActivos.setLocalizedDescription(ENGLISH, "the user is an active user");
        usuariosActivos.setLocalizedDescription(SPANISH, "el usuario es un usuario activo");
        usuariosActivos.setLocalizedErrorMessage(ENGLISH, "the user is an inactive user");
        usuariosActivos.setLocalizedErrorMessage(SPANISH, "el usuario es un usuario inactivo");
        /**/
        conSupervisor.setLocalizedDescription(ENGLISH, "the user has a supervisor");
        conSupervisor.setLocalizedDescription(SPANISH, "el usuario tiene supervisor");
        conSupervisor.setLocalizedErrorMessage(ENGLISH, "the user does not have a supervisor");
        conSupervisor.setLocalizedErrorMessage(SPANISH, "el usuario no tiene supervisor");
        /**/
        sinSupervisor.setLocalizedDescription(ENGLISH, "the user does not have a supervisor");
        sinSupervisor.setLocalizedDescription(SPANISH, "el usuario no tiene supervisor");
        sinSupervisor.setLocalizedErrorMessage(ENGLISH, "the user has a supervisor");
        sinSupervisor.setLocalizedErrorMessage(SPANISH, "el usuario tiene supervisor");
        /**/
        usuariosInactivos.setLocalizedDescription(ENGLISH, "the user is an inactive user");
        usuariosInactivos.setLocalizedDescription(SPANISH, "el usuario es un usuario inactivo");
        usuariosInactivos.setLocalizedErrorMessage(ENGLISH, "the user is an active user");
        usuariosInactivos.setLocalizedErrorMessage(SPANISH, "el usuario es un usuario activo");
        /**/
        conPaginaMenu.setLocalizedDescription(ENGLISH, "landing page is \"Menu page\"");
        conPaginaMenu.setLocalizedDescription(SPANISH, "página de inicio es \"Página del menú\"");
        conPaginaMenu.setLocalizedErrorMessage(ENGLISH, "landing page is not \"Menu page\"");
        conPaginaMenu.setLocalizedErrorMessage(SPANISH, "página de inicio no es \"Página del menú\"");
        /**/
        conOtraPagina.setLocalizedDescription(ENGLISH, "landing page is \"Other page\"");
        conOtraPagina.setLocalizedDescription(SPANISH, "página de inicio es \"Otra página\"");
        conOtraPagina.setLocalizedErrorMessage(ENGLISH, "landing page is not \"Other page\"");
        conOtraPagina.setLocalizedErrorMessage(SPANISH, "página de inicio no es \"Otra página\"");
        /**/
        check101.setLocalizedDescription(ENGLISH, "menu page must be specified if landing page is \"Menu page\"");
        check101.setLocalizedDescription(SPANISH, "página del menú se debe especificar si página de inicio es \"Página del menú\"");
        check101.setLocalizedErrorMessage(ENGLISH, "menu page must be specified if landing page is \"Menu page\"");
        check101.setLocalizedErrorMessage(SPANISH, "página del menú se debe especificar si página de inicio es \"Página del menú\"");
        /**/
        check102.setLocalizedDescription(ENGLISH, "menu page must be a valid menu page");
        check102.setLocalizedDescription(SPANISH, "página del menú debe ser una página del menú válida");
        check102.setLocalizedErrorMessage(ENGLISH, "invalid menu page");
        check102.setLocalizedErrorMessage(SPANISH, "página del menú inválida");
        /**/
        check111.setLocalizedDescription(ENGLISH, "other page must be specified if landing page is \"Other page\"");
        check111.setLocalizedDescription(SPANISH, "otra página se debe especificar si página de inicio es \"Otra página\"");
        check111.setLocalizedErrorMessage(ENGLISH, "other page must be specified if landing page is \"Other page\"");
        check111.setLocalizedErrorMessage(SPANISH, "otra página se debe especificar si página de inicio es \"Otra página\"");
        /**/
        check112.setLocalizedDescription(ENGLISH, "other page must be active");
        check112.setLocalizedDescription(SPANISH, "otra página debe estar activa");
        check112.setLocalizedErrorMessage(ENGLISH, "other page is inactive");
        check112.setLocalizedErrorMessage(SPANISH, "otra página se encuentra inactiva");
        /**/
        eliminable.setLocalizedDescription(ENGLISH, "the user is not a special user and is not your own user");
        eliminable.setLocalizedDescription(SPANISH, "el usuario no es un usuario especial y no es su propio usuario");
//      eliminable.setLocalizedErrorMessage(ENGLISH, "the user is not deletable");
//      eliminable.setLocalizedErrorMessage(SPANISH, "el usuario no es eliminable");
        /**/
        modificable.setLocalizedDescription(ENGLISH, "the user is not a special user and is an active user");
        modificable.setLocalizedDescription(SPANISH, "el usuario no es un usuario especial y es un usuario activo");
//      modificable.setLocalizedErrorMessage(ENGLISH, "the user is not updatable");
//      modificable.setLocalizedErrorMessage(SPANISH, "el usuario no es modificable");
        /**/
        designableComoSuperUsuario.setLocalizedDescription(ENGLISH, "the user "
            + "is not a special user and "
            + "is not your own user and "
            + "is an active user and "
            + "is not a super-user"
            + "");
        designableComoSuperUsuario.setLocalizedDescription(SPANISH, "el usuario "
            + "no es un usuario especial y "
            + "no es su propio usuario y "
            + "es un usuario activo y "
            + "no es un súper-usuario"
            + "");
//      designableComoSuperUsuario.setLocalizedErrorMessage(ENGLISH, "cannot designate the user as super-user");
//      designableComoSuperUsuario.setLocalizedErrorMessage(SPANISH, "no puede designar el usuario como súper-usuario");
        /**/
        superUsuarioAnulable.setLocalizedDescription(ENGLISH, "the user "
            + "is not a special user and "
            + "is not your own user and "
            + "is an active user and "
            + "is a super-user"
            + "");
        superUsuarioAnulable.setLocalizedDescription(SPANISH, "el usuario "
            + "no es un usuario especial y "
            + "no es su propio usuario y "
            + "es un usuario activo y "
            + "es un súper-usuario"
            + "");
//      superUsuarioAnulable.setLocalizedErrorMessage(ENGLISH, "the super-user designation cannot be annulled");
//      superUsuarioAnulable.setLocalizedErrorMessage(SPANISH, "la designación como super-usuario no puede ser anulada");
        /**/
        designableComoSuperAuditor.setLocalizedDescription(ENGLISH, "the user "
            + "is not a special user and "
            + "is not your own user and "
            + "is an active user and "
            + "is not a super-user and "
            + "is not a super-auditor"
            + "");
        designableComoSuperAuditor.setLocalizedDescription(SPANISH, "el usuario "
            + "no es un usuario especial y "
            + "no es su propio usuario y "
            + "es un usuario activo y "
            + "no es un súper-usuario y "
            + "no es un súper-auditor"
            + "");
        designableComoSuperAuditor.setLocalizedErrorMessage(ENGLISH, "the user cannot be designated as super-auditor");
        designableComoSuperAuditor.setLocalizedErrorMessage(SPANISH, "el usuario no se puede designar como súper-auditor");
        /**/
        superAuditorAnulable.setLocalizedDescription(ENGLISH, "the user "
            + "is not a special user and "
            + "is not your own user and "
            + "is an active user and "
            + "is a super-auditor"
            + "");
        superAuditorAnulable.setLocalizedDescription(SPANISH, "el usuario "
            + "no es un usuario especial y "
            + "no es su propio usuario y "
            + "es un usuario activo y "
            + "es un súper-auditor"
            + "");
//      superAuditorAnulable.setLocalizedErrorMessage(ENGLISH, "the super-auditor designation cannot be annulled");
//      superAuditorAnulable.setLocalizedErrorMessage(SPANISH, "la designación como super-auditor no puede ser anulada");
        /**/
        desactivable.setLocalizedDescription(ENGLISH, "the user "
            + "is not a special user and "
            + "is not your own user and "
            + "is an active user"
            + "");
        desactivable.setLocalizedDescription(SPANISH, "el usuario "
            + "no es un usuario especial y "
            + "no es su propio usuario y "
            + "es un usuario activo"
            + "");
//      desactivable.setLocalizedErrorMessage(ENGLISH, "the user is not active");
//      desactivable.setLocalizedErrorMessage(SPANISH, "el usuario no está activo");
        /**/
        reactivable.setLocalizedDescription(ENGLISH, "the user "
            + "is not a special user and "
            + "is not your own user and "
            + "is an inactive user"
            + "");
        reactivable.setLocalizedDescription(SPANISH, "el usuario "
            + "no es un usuario especial y "
            + "no es su propio usuario y "
            + "es un usuario inactivo"
            + "");
//      reactivable.setLocalizedErrorMessage(ENGLISH, "the user is not inactive");
//      reactivable.setLocalizedErrorMessage(SPANISH, "el usuario no está inactivo");
        /**/
        supervisorAnulable.setLocalizedDescription(ENGLISH, "the user is not a special user and is not your own user and has a supervisor");
        supervisorAnulable.setLocalizedDescription(SPANISH, "el usuario no es un usuario especial y no es su propio usuario y tiene supervisor");
//      supervisorAnulable.setLocalizedErrorMessage(ENGLISH, "cannot annul the supervisor of the user");
//      supervisorAnulable.setLocalizedErrorMessage(SPANISH, "no puede anular el supervisor del usuario");
        /**/
        supervisorAsignable.setLocalizedDescription(ENGLISH, "the user is not a special user and is not your own user");
        supervisorAsignable.setLocalizedDescription(SPANISH, "el usuario no es un usuario especial y no es su propio usuario");
//      supervisorAsignable.setLocalizedErrorMessage(ENGLISH, "cannot assign a supervisor to the user");
//      supervisorAsignable.setLocalizedErrorMessage(SPANISH, "no puede asignar un supervisor al usuario");
        /**/
        // </editor-fold>
    }

    @Override
    protected void settleFilters() {
        super.settleFilters();
        /**/
        setUpdateFilter(modificable);
        setDeleteFilter(eliminable);
        /*
        paginaInicio.setRemoveInstanceArray(paginaInicio.OTRA_PAGINA);
        **/
        paginaMenu.setModifyingFilter(conPaginaMenu);
        paginaMenu.setRenderingFilter(conPaginaMenu, true);
        paginaMenu.setRequiringFilter(conPaginaMenu);
//      paginaMenu.setNullifyingFilter(not(conPaginaMenu));
        paginaMenu.setSearchQueryFilter(filtroPaginaMenu);
        /**/
        otraPagina.setModifyingFilter(conOtraPagina);
        otraPagina.setRenderingFilter(conOtraPagina, true);
        otraPagina.setRequiringFilter(conOtraPagina);
//      otraPagina.setNullifyingFilter(not(conOtraPagina));
        otraPagina.setSearchQueryFilter(filtroOtraPagina);
        /**/
        modulos.setRenderingFilter(menusRegistrados);
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of Usuario's expressions">
        /**/
        paginaMenu.setLocalizedRequiringFilterTag(ENGLISH, "if " + b("landing page") + " is \"Menu page\"");
        paginaMenu.setLocalizedRequiringFilterTag(SPANISH, "sí " + b("página de inicio") + " es \"Página del menú\"");
        /**/
        otraPagina.setLocalizedRequiringFilterTag(ENGLISH, "if " + b("landing page") + " is \"Other page\"");
        otraPagina.setLocalizedRequiringFilterTag(SPANISH, "sí " + b("página de inicio") + " es \"Otra página\"");
        /**/
        // </editor-fold>
    }

    protected Agregar agregar;

    protected EliminarRoles eliminarRoles;

    protected AsignarPassword asignarPassword;

    protected Copiar copiar;

    protected Desactivar desactivar;

    protected Reactivar reactivar;

    protected DesignarSuperUsuario designarSuperUsuario;

    protected AnularSuperUsuario anularSuperUsuario;

    protected DesignarSuperAuditor designarSuperAuditor;

    protected AnularSuperAuditor anularSuperAuditor;

    protected AsignarSupervisor asignarSupervisor;

    protected AnularSupervisor anularSupervisor;

    protected Sincronizar sincronizar;

    protected CambiarConfiguracion cambiarConfiguracion;

    protected CambiarPassword cambiarPassword;

    protected CargarArchivo cargarArchivo;

    protected CargarRetrato cargarRetrato;

    protected RecortarRetrato recortarRetrato;

    protected TomarRetrato tomarRetrato;

    // <editor-fold defaultstate="collapsed" desc="Operations">
    @OperationClass(access = OperationAccess.PRIVATE)
    @ProcessOperationClass(builtIn = true, serviceable = Kleenean.TRUE)
    public class Agregar extends ProcessOperation {

        @ParameterField(required = Kleenean.TRUE)
        protected StringParameter codigo;

        @ParameterField(required = Kleenean.FALSE)
        protected StringParameter nombre;

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            // <editor-fold defaultstate="collapsed" desc="localization of Agregar's attributes">
            setLocalizedLabel(ENGLISH, "add");
            setLocalizedLabel(SPANISH, "agregar");
            setLocalizedSuccessMessage(ENGLISH, "the user was successfully added");
            setLocalizedSuccessMessage(SPANISH, "el usuario fue agregado con éxito");
            // </editor-fold>
        }

        @Override
        protected void settleParameters() {
            super.settleParameters();
            // <editor-fold defaultstate="collapsed" desc="localization of Agregar's parameters">
            codigo.setLocalizedLabel(ENGLISH, "code");
            codigo.setLocalizedLabel(SPANISH, "código");
            /**/
            nombre.setLocalizedLabel(ENGLISH, "name");
            nombre.setLocalizedLabel(SPANISH, "nombre");
            // </editor-fold>
        }

    }

    @OperationClass(access = OperationAccess.PRIVATE)
    @ProcessOperationClass(serviceable = Kleenean.FALSE)
    public class EliminarRoles extends ProcessOperation {

        @InstanceReference
//      @Allocation(maxDepth = 1, maxRound = 0)
        protected meta.entidad.comun.control.acceso.Usuario usuario;

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            // <editor-fold defaultstate="collapsed" desc="localization of EliminarRoles's attributes">
            setLocalizedLabel(ENGLISH, "delete roles");
            setLocalizedLabel(SPANISH, "eliminar roles");
            setLocalizedSuccessMessage(ENGLISH, "all user's role associations were removed");
            setLocalizedSuccessMessage(SPANISH, "todas las asociaciones rol/usuario del usuario fueron eliminadas");
            // </editor-fold>
        }

        @Override
        protected void settleParameters() {
            super.settleParameters();
            // <editor-fold defaultstate="collapsed" desc="localization of EliminarRoles's parameters">
            usuario.setLocalizedLabel(ENGLISH, "user");
            usuario.setLocalizedLabel(SPANISH, "usuario");
            // </editor-fold>
        }

    }

    @ConstructionOperationClass(type = Usuario.class)
    public class Copiar extends ProcessOperation {

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            // <editor-fold defaultstate="collapsed" desc="localization of Copiar's attributes">
            /**/
            setLocalizedLabel(ENGLISH, "copy");
            setLocalizedLabel(SPANISH, "copiar");
            /**/
            setLocalizedDescription(ENGLISH, "copy a user with another code and name; "
                + "the copy includes the roles of the original user");
            setLocalizedDescription(SPANISH, "copiar un usuario con otro código y nombre; "
                + "la copia incluye los roles del usuario original");
            /**/
            setLocalizedSuccessMessage(ENGLISH, "the user was successfully copied");
            setLocalizedSuccessMessage(SPANISH, "el usuario fue copiado con éxito");
            /**/
            // </editor-fold>
        }

        @InstanceReference
//      @Allocation(maxDepth = 1, maxRound = 0)
        protected meta.entidad.comun.control.acceso.Usuario usuario;

        @ParameterField(required = Kleenean.TRUE)
        @StringField(maxLength = 100)
        protected StringParameter codigo;

        @ParameterField(required = Kleenean.FALSE)
        @StringField(maxLength = 100)
        protected StringParameter nombre;

        @Override
        protected void settleParameters() {
            super.settleParameters();
            // <editor-fold defaultstate="collapsed" desc="localization of Copiar's parameters">
            usuario.setLocalizedLabel(ENGLISH, "user");
            usuario.setLocalizedLabel(SPANISH, "usuario");
            usuario.setLocalizedDescription(ENGLISH, "Code of the user you want to copy; "
                + "it is a required field and has no default value");
            usuario.setLocalizedDescription(SPANISH, "Código del usuario que desea copiar; "
                + "es un dato obligatorio y no tiene valor por omisión");
            /**/
            codigo.setLocalizedLabel(ENGLISH, "code");
            codigo.setLocalizedLabel(SPANISH, "código");
            codigo.setLocalizedDescription(ENGLISH, "Code of the new user produced when copying; "
                + "it is a required field and has no default value");
            codigo.setLocalizedDescription(SPANISH, "Código del nuevo rol que produce la copia; "
                + "es un dato obligatorio y no tiene valor por omisión");
            /**/
            nombre.setLocalizedLabel(ENGLISH, "name");
            nombre.setLocalizedLabel(SPANISH, "nombre");
            nombre.setLocalizedDescription(ENGLISH, "Name of the new user produced when copying; "
                + "it is an optional field; by default, the name of the original user is used");
            nombre.setLocalizedDescription(SPANISH, "Nombre del nuevo rol que produce la copia; "
                + "es un dato opcional; por omisión se utiliza el nombre del rol original");
            /**/
            // </editor-fold>
        }

    }

    @ProcessOperationClass(builtIn = true)
    public class DesignarSuperUsuario extends ProcessOperation {

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            // <editor-fold defaultstate="collapsed" desc="localization of DesignarSuper's attributes">
            setLocalizedLabel(ENGLISH, "designate super-user");
            setLocalizedLabel(SPANISH, "designar súper-usuario");
            setLocalizedDescription(ENGLISH, "designate a user as a super-user");
            setLocalizedDescription(SPANISH, "designar un usuario como súper-usuario");
            setLocalizedSuccessMessage(ENGLISH, "the user was designated as super-user");
            setLocalizedSuccessMessage(SPANISH, "el usuario fue designado como súper-usuario");
            // </editor-fold>
        }

        @InstanceReference
//      @Allocation(maxDepth = 1, maxRound = 0)
        protected Usuario usuario;

        @Override
        protected void settleParameters() {
            super.settleParameters();
            // <editor-fold defaultstate="collapsed" desc="localization of DesignarSuper's parameters">
            usuario.setLocalizedLabel(ENGLISH, "user");
            usuario.setLocalizedLabel(SPANISH, "usuario");
            usuario.setLocalizedDescription(ENGLISH, "Code of the user you want to designate as super-user; "
                + "it is a required field and has no default value");
            usuario.setLocalizedDescription(SPANISH, "Código del usuario que desea designar como súper-usuario; "
                + "es un dato obligatorio y no tiene valor por omisión");
            // </editor-fold>
        }

    }

    @ProcessOperationClass(builtIn = true)
    public class AnularSuperUsuario extends ProcessOperation {

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            // <editor-fold defaultstate="collapsed" desc="localization of AnularSuper's attributes">
            setLocalizedLabel(ENGLISH, "annul super-user");
            setLocalizedLabel(SPANISH, "anular súper-usuario");
            setLocalizedDescription(ENGLISH, "annul the designation of a user as a super-user");
            setLocalizedDescription(SPANISH, "anular la designación de un usuario como súper-usuario");
            setLocalizedSuccessMessage(ENGLISH, "the user's super-user designation was annuled");
            setLocalizedSuccessMessage(SPANISH, "se anuló la designación de super-usuario del usuario");
            // </editor-fold>
        }

        @InstanceReference
//      @Allocation(maxDepth = 1, maxRound = 0)
        protected Usuario usuario;

        @Override
        protected void settleParameters() {
            super.settleParameters();
            // <editor-fold defaultstate="collapsed" desc="localization of AnularSuper's parameters">
            usuario.setLocalizedLabel(ENGLISH, "user");
            usuario.setLocalizedLabel(SPANISH, "usuario");
            usuario.setLocalizedDescription(ENGLISH, "Code of the super-user whose designation you want to annul; "
                + "it is a required field and has no default value");
            usuario.setLocalizedDescription(SPANISH, "Código del súper-usuario cuya designación desea anular; "
                + "es un dato obligatorio y no tiene valor por omisión");
            // </editor-fold>
        }

    }

    @ProcessOperationClass(builtIn = true)
    public class DesignarSuperAuditor extends ProcessOperation {

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            // <editor-fold defaultstate="collapsed" desc="localization of DesignarSuper's attributes">
            setLocalizedLabel(ENGLISH, "designate super-auditor");
            setLocalizedLabel(SPANISH, "designar súper-auditor");
            setLocalizedDescription(ENGLISH, "designate a user as a super-auditor");
            setLocalizedDescription(SPANISH, "designar un usuario como súper-auditor");
            setLocalizedSuccessMessage(ENGLISH, "the user was designated as super-auditor");
            setLocalizedSuccessMessage(SPANISH, "el usuario fue designado como súper-auditor");
            // </editor-fold>
        }

        @InstanceReference
//      @Allocation(maxDepth = 1, maxRound = 0)
        protected Usuario usuario;

        @Override
        protected void settleParameters() {
            super.settleParameters();
            // <editor-fold defaultstate="collapsed" desc="localization of DesignarSuper's parameters">
            usuario.setLocalizedLabel(ENGLISH, "user");
            usuario.setLocalizedLabel(SPANISH, "usuario");
            usuario.setLocalizedDescription(ENGLISH, "Code of the user you want to designate as super-auditor; "
                + "it is a required field and has no default value");
            usuario.setLocalizedDescription(SPANISH, "Código del usuario que desea designar como súper-auditor; "
                + "es un dato obligatorio y no tiene valor por omisión");
            // </editor-fold>
        }

    }

    @ProcessOperationClass(builtIn = true)
    public class AnularSuperAuditor extends ProcessOperation {

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            // <editor-fold defaultstate="collapsed" desc="localization of AnularSuper's attributes">
            setLocalizedLabel(ENGLISH, "annul super-auditor");
            setLocalizedLabel(SPANISH, "anular súper-auditor");
            setLocalizedDescription(ENGLISH, "annul the designation of a user as a super-auditor");
            setLocalizedDescription(SPANISH, "anular la designación de un usuario como súper-auditor");
            setLocalizedSuccessMessage(ENGLISH, "the user's super-auditor designation was annuled");
            setLocalizedSuccessMessage(SPANISH, "se anuló la designación de super-auditor del usuario");
            // </editor-fold>
        }

        @InstanceReference
//      @Allocation(maxDepth = 1, maxRound = 0)
        protected Usuario usuario;

        @Override
        protected void settleParameters() {
            super.settleParameters();
            // <editor-fold defaultstate="collapsed" desc="localization of AnularSuper's parameters">
            usuario.setLocalizedLabel(ENGLISH, "user");
            usuario.setLocalizedLabel(SPANISH, "usuario");
            usuario.setLocalizedDescription(ENGLISH, "Code of the super-auditor whose designation you want to annul; "
                + "it is a required field and has no default value");
            usuario.setLocalizedDescription(SPANISH, "Código del súper-auditor cuya designación desea anular; "
                + "es un dato obligatorio y no tiene valor por omisión");
            // </editor-fold>
        }

    }

    @ProcessOperationClass(builtIn = true, serviceable = Kleenean.TRUE)
    public class Desactivar extends ProcessOperation {

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            // <editor-fold defaultstate="collapsed" desc="localization of Desactivar's attributes">
            setLocalizedLabel(ENGLISH, "deactivate");
            setLocalizedLabel(SPANISH, "desactivar");
            setLocalizedDescription(ENGLISH, "deactivate an active user");
            setLocalizedDescription(SPANISH, "desactivar un usuario activo");
            setLocalizedSuccessMessage(ENGLISH, "the user was deactivated");
            setLocalizedSuccessMessage(SPANISH, "el usuario fue desactivado");
            // </editor-fold>
        }

        @InstanceReference
//      @Allocation(maxDepth = 1, maxRound = 0)
        protected Usuario usuario;

        @Override
        protected void settleParameters() {
            super.settleParameters();
            // <editor-fold defaultstate="collapsed" desc="localization of Desactivar's parameters">
            usuario.setLocalizedLabel(ENGLISH, "user");
            usuario.setLocalizedLabel(SPANISH, "usuario");
            usuario.setLocalizedDescription(ENGLISH, "Code of the user you want to deactivate; "
                + "it is a required field and has no default value");
            usuario.setLocalizedDescription(SPANISH, "Código del usuario que desea desactivar; "
                + "es un dato obligatorio y no tiene valor por omisión");
            // </editor-fold>
        }

    }

    @ProcessOperationClass(builtIn = true, serviceable = Kleenean.TRUE)
    public class Reactivar extends ProcessOperation {

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            // <editor-fold defaultstate="collapsed" desc="localization of Reactivar's attributes">
            setLocalizedLabel(ENGLISH, "reactivate");
            setLocalizedLabel(SPANISH, "reactivar");
            setLocalizedDescription(ENGLISH, "reactivate an inactive user");
            setLocalizedDescription(SPANISH, "reactivar un usuario inactivo");
            setLocalizedSuccessMessage(ENGLISH, "the user was reactivated");
            setLocalizedSuccessMessage(SPANISH, "el usuario fue reactivado");
            // </editor-fold>
        }

        @InstanceReference
//      @Allocation(maxDepth = 1, maxRound = 0)
        protected Usuario usuario;

        @Override
        protected void settleParameters() {
            super.settleParameters();
            // <editor-fold defaultstate="collapsed" desc="localization of Reactivar's parameters">
            usuario.setLocalizedLabel(ENGLISH, "user");
            usuario.setLocalizedLabel(SPANISH, "usuario");
            usuario.setLocalizedDescription(ENGLISH, "Code of the user you want to reactivate; "
                + "it is a required field and has no default value");
            usuario.setLocalizedDescription(SPANISH, "Código del usuario que desea reactivar; "
                + "es un dato obligatorio y no tiene valor por omisión");
            // </editor-fold>
        }

    }

    @OperationClass(logging = OperationLogging.FAILURE)
    @ProcessOperationClass(builtIn = true)
    public class Sincronizar extends ProcessOperation {

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            // <editor-fold defaultstate="collapsed" desc="localization of Sincronizar's attributes">
            /**/
            setLocalizedLabel(ENGLISH, "synchronize");
            setLocalizedLabel(SPANISH, "sincronizar");
            /**/
            setLocalizedDescription(ENGLISH, "synchronize the authorizations of a user; "
                + "the user's authorizations will be updated the next time the user starts a session");
            setLocalizedDescription(SPANISH, "sincronizar las autorizaciones de un usuario; "
                + "las autorizaciones del usuario serán actualizadas la próxima vez que el usuario inicie una sesión de trabajo");
            /**/
            setLocalizedSuccessMessage(ENGLISH, "the user's authorizations were synchronized");
            setLocalizedSuccessMessage(SPANISH, "se sincronizaron las autorizaciones del usuario");
            /**/
            // </editor-fold>
        }

        @InstanceReference
//      @Allocation(maxDepth = 1, maxRound = 0)
        protected meta.entidad.comun.control.acceso.Usuario usuario;

        @Override
        protected void settleParameters() {
            super.settleParameters();
            // <editor-fold defaultstate="collapsed" desc="localization of Sincronizar's parameters">
            usuario.setLocalizedLabel(ENGLISH, "user");
            usuario.setLocalizedLabel(SPANISH, "usuario");
            usuario.setLocalizedDescription(ENGLISH, "Code of the user you want to synchronize; "
                + "it is a required field and has no default value");
            usuario.setLocalizedDescription(SPANISH, "Código del usuario que desea sincronizar; "
                + "es un dato obligatorio y no tiene valor por omisión");
            // </editor-fold>
        }

    }

    @ProcessOperationClass(builtIn = true)
    public class AsignarPassword extends ProcessOperation {

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            // <editor-fold defaultstate="collapsed" desc="localization of AsignarPassword's attributes">
            setLocalizedLabel(ENGLISH, "assign password");
            setLocalizedLabel(SPANISH, "asignar password");
            setLocalizedDescription(ENGLISH, "assign a new password to a user");
            setLocalizedDescription(SPANISH, "asignar una nueva contraseña a un usuario");
            setLocalizedSuccessMessage(ENGLISH, "a new password was assigned to the user");
            setLocalizedSuccessMessage(SPANISH, "se asignó una nueva contraseña al usuario");
            // </editor-fold>
        }

        @InstanceReference
//      @Allocation(maxDepth = 1, maxRound = 0)
        protected meta.entidad.comun.control.acceso.Usuario usuario;

        @ParameterField(auditable = Kleenean.FALSE, password = Kleenean.TRUE, required = Kleenean.TRUE)
        @StringField(maxLength = 128)
        protected StringParameter nuevoPassword;

        @ParameterField(auditable = Kleenean.FALSE, password = Kleenean.TRUE, required = Kleenean.TRUE)
        @StringField(maxLength = 128)
        protected StringParameter confirmacionPassword;

        @Override
        protected void settleParameters() {
            super.settleParameters();
            // <editor-fold defaultstate="collapsed" desc="localization of AsignarPassword's parameters">
            /**/
            usuario.setLocalizedLabel(ENGLISH, "user");
            usuario.setLocalizedLabel(SPANISH, "usuario");
            usuario.setLocalizedDescription(ENGLISH, "Code of the user you want to assign a new password; "
                + "it is a required field and has no default value");
            usuario.setLocalizedDescription(SPANISH, "Código del usuario al que desea asignar una nueva contraseña; "
                + "es un dato obligatorio y no tiene valor por omisión");
            /**/
            nuevoPassword.setLocalizedLabel(ENGLISH, "new password");
            nuevoPassword.setLocalizedLabel(SPANISH, "nueva contraseña");
            nuevoPassword.setLocalizedDescription(ENGLISH, "The new password must be a string of up to 128 letters, numbers and other characters; "
                + "it is a required field and has no default value");
            nuevoPassword.setLocalizedDescription(SPANISH, "La nueva contraseña debe ser una secuencia de hasta 128 letras, números y otros caracteres; "
                + "es un dato obligatorio y no tiene valor por omisión");
            /**/
            confirmacionPassword.setLocalizedLabel(ENGLISH, "password confirmation");
            confirmacionPassword.setLocalizedLabel(SPANISH, "confirmación de la contraseña");
            confirmacionPassword.setLocalizedDescription(ENGLISH, "The password confirmation must match the new password; "
                + "it is a required field and has no default value");
            confirmacionPassword.setLocalizedDescription(SPANISH, "La confirmación de la contraseña debe coincidir con la nueva contraseña; "
                + "es un dato obligatorio y no tiene valor por omisión");
            /**/
            // </editor-fold>
        }

    }

    @ProcessOperationClass(builtIn = true)
    public class AsignarSupervisor extends ProcessOperation {

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            // <editor-fold defaultstate="collapsed" desc="localization of AsignarSupervisor's attributes">
            setLocalizedLabel(ENGLISH, "assign supervisor");
            setLocalizedLabel(SPANISH, "asignar supervisor");
            setLocalizedDescription(ENGLISH, "assign a new supervisor to a user");
            setLocalizedDescription(SPANISH, "asignar un nuevo supervisor a un usuario");
            setLocalizedSuccessMessage(ENGLISH, "a new supervisor was assigned to the user");
            setLocalizedSuccessMessage(SPANISH, "se asignó un nuevo supervisor al usuario");
            // </editor-fold>
        }

        @InstanceReference
//      @Allocation(maxDepth = 1, maxRound = 0)
        protected Usuario usuario;

        @ParameterField(required = Kleenean.TRUE)
//      @Allocation(maxDepth = 1, maxRound = 0)
        protected meta.entidad.comun.control.acceso.Usuario supervisor;

        @Override
        protected void settleParameters() {
            super.settleParameters();
            // <editor-fold defaultstate="collapsed" desc="localization of AsignarSupervisor's parameters">
            usuario.setLocalizedLabel(ENGLISH, "user");
            usuario.setLocalizedLabel(SPANISH, "usuario");
            usuario.setLocalizedDescription(ENGLISH, "Code of the user you want to assign a new supervisor; "
                + "it is a required field and has no default value");
            usuario.setLocalizedDescription(SPANISH, "Código del usuario al que desea asignar un nuevo supervisor; "
                + "es un dato obligatorio y no tiene valor por omisión");
            /**/
            supervisor.setLocalizedLabel(ENGLISH, "supervisor");
            supervisor.setLocalizedLabel(SPANISH, "supervisor");
            supervisor.setLocalizedDescription(ENGLISH, "User code of the new supervisor; "
                + "it is a required field and has no default value");
            supervisor.setLocalizedDescription(SPANISH, "Código de usuario del nuevo supervisor; "
                + "es un dato obligatorio y no tiene valor por omisión");
            // </editor-fold>
        }

    }

    @ProcessOperationClass(builtIn = true)
    public class AnularSupervisor extends ProcessOperation {

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            // <editor-fold defaultstate="collapsed" desc="localization of AnularSupervisor's attributes">
            setLocalizedLabel(ENGLISH, "annul supervisor");
            setLocalizedLabel(SPANISH, "anular supervisor");
            setLocalizedDescription(ENGLISH, "annul the supervisor to a user");
            setLocalizedDescription(SPANISH, "anular el supervisor a un usuario");
            setLocalizedSuccessMessage(ENGLISH, "the user supervisor was annuled");
            setLocalizedSuccessMessage(SPANISH, "se anuló el supervisor del usuario");
            // </editor-fold>
        }

        @InstanceReference
//      @Allocation(maxDepth = 1, maxRound = 0)
        protected Usuario usuario;

        @Override
        protected void settleParameters() {
            super.settleParameters();
            // <editor-fold defaultstate="collapsed" desc="localization of AnularSupervisor's parameters">
            usuario.setLocalizedLabel(ENGLISH, "user");
            usuario.setLocalizedLabel(SPANISH, "usuario");
            usuario.setLocalizedDescription(ENGLISH, "Code of the user whose supervisor you want to annul; "
                + "it is a required field and has no default value");
            usuario.setLocalizedDescription(SPANISH, "Código del usuario cuyo supervisor desea anular; "
                + "es un dato obligatorio y no tiene valor por omisión");
            // </editor-fold>
        }

    }

    @OperationClass(access = OperationAccess.PRIVATE)
    @ProcessOperationClass(builtIn = true)
    public class CambiarConfiguracion extends ProcessOperation {

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            // <editor-fold defaultstate="collapsed" desc="localization of CambiarConfiguracion's attributes">
            setLocalizedLabel(ENGLISH, "change settings");
            setLocalizedLabel(SPANISH, "cambiar configuración");
            setLocalizedSuccessMessage(ENGLISH, "settings successfully updated");
            setLocalizedSuccessMessage(SPANISH, "configuración actualizada con éxito");
            // </editor-fold>
        }

        @InstanceReference
//      @Allocation(maxDepth = 1, maxRound = 0)
        protected Usuario usuario;

        @ParameterField(required = Kleenean.TRUE)
//      @Allocation(maxDepth = 1, maxRound = 0)
        protected PaginaInicio paginaInicio;

        @ParameterField
        protected Pagina paginaMenu;

        @ParameterField
        protected PaginaEspecial otraPagina;

        @ParameterField(required = Kleenean.FALSE) // since 26/03/2022
        protected StringParameter tema;

        @ParameterField(required = Kleenean.TRUE)
        protected IntegerParameter filasPorPagina;

        @ParameterField(required = Kleenean.TRUE)
        protected BooleanParameter ayudaPorCampos;

        @ParameterField(required = Kleenean.TRUE)
        protected BooleanParameter confirmarAlDescartar;

        @ParameterField(required = Kleenean.TRUE)
        protected BooleanParameter confirmarAlFinalizar;

        @Override
        protected void settleParameters() {
            super.settleParameters();
            paginaInicio.setDefaultValue(paginaInicio.PREDETERMINADA);
            paginaInicio.setInitialValue(usuario.paginaInicio);
            paginaMenu.setInitialValue(usuario.paginaMenu);
            otraPagina.setInitialValue(usuario.otraPagina);
            filasPorPagina.setDefaultValue(0);
            filasPorPagina.setInitialValue(usuario.filasPorPagina);
            filasPorPagina.setMaxValue(Constants.MAXIMUM_ROWS_PER_PAGE_LIMIT);
            filasPorPagina.setMinValue(0);
            ayudaPorCampos.setDefaultValue(true);
            ayudaPorCampos.setInitialValue(usuario.ayudaPorCampos);
            confirmarAlDescartar.setDefaultValue(true);
            confirmarAlDescartar.setInitialValue(usuario.confirmarAlDescartar);
            confirmarAlFinalizar.setDefaultValue(true);
            confirmarAlFinalizar.setInitialValue(usuario.confirmarAlFinalizar);
            /**/
            // <editor-fold defaultstate="collapsed" desc="localization of CambiarConfiguracion's parameters">
            usuario.setLocalizedLabel(ENGLISH, "user");
            usuario.setLocalizedLabel(SPANISH, "usuario");
            usuario.setLocalizedDescription(ENGLISH, "Code of the user whose configuration you want to change; "
                + "it is a required field and has no default value");
            usuario.setLocalizedDescription(SPANISH, "Código del usuario cuya configuración desea cambiar; "
                + "es un dato obligatorio y no tiene valor por omisión");
            /**/
            paginaInicio.setLocalizedLabel(ENGLISH, "landing page");
            paginaInicio.setLocalizedLabel(SPANISH, "página de inicio");
            /**/
            paginaMenu.setLocalizedLabel(ENGLISH, "menu page");
            paginaMenu.setLocalizedLabel(SPANISH, "página del menú");
            paginaMenu.setLocalizedDescription(ENGLISH, "page to be used as home page; "
                + "must be specified if landing page is \"Menu page\"");
            paginaMenu.setLocalizedDescription(SPANISH, "página que se ha de utilizar como página de inicio; "
                + "se debe especificar si página de inicio es \"Página del menú\"");
            /**/
            otraPagina.setLocalizedLabel(ENGLISH, "other page");
            otraPagina.setLocalizedLabel(SPANISH, "otra página");
            otraPagina.setLocalizedDescription(ENGLISH, "special page to be used as home page; must be specified if landing page is \"Other page\"");
            otraPagina.setLocalizedDescription(SPANISH, "página especial que se ha de utilizar como página de inicio; "
                + "se debe especificar si página de inicio es \"Otra página\"");
            /**/
            tema.setLocalizedLabel(ENGLISH, "theme");
            tema.setLocalizedLabel(SPANISH, "tema");
            /**/
            filasPorPagina.setLocalizedLabel(ENGLISH, "rows per page");
            filasPorPagina.setLocalizedLabel(SPANISH, "filas por página");
            filasPorPagina.setLocalizedDescription(ENGLISH, "initial number of rows per page");
            filasPorPagina.setLocalizedDescription(SPANISH, "número inicial de filas por página de presentación tabular");
            /**/
            ayudaPorCampos.setLocalizedLabel(ENGLISH, "inline help");
            ayudaPorCampos.setLocalizedLabel(SPANISH, "ayuda en línea");
            ayudaPorCampos.setLocalizedDescription(ENGLISH, "show the descriptions of the fields on the page");
            ayudaPorCampos.setLocalizedDescription(SPANISH, "mostrar las descripciones de los campos de la página");
            /**/
            confirmarAlDescartar.setLocalizedLabel(ENGLISH, "confirm discarding");
            confirmarAlDescartar.setLocalizedLabel(SPANISH, "confirmar al descartar");
            confirmarAlDescartar.setLocalizedDescription(ENGLISH, "confirm discarding on registration pages");
            confirmarAlDescartar.setLocalizedDescription(SPANISH, "confirmar al descartar en las páginas de registro");
            /**/
            confirmarAlFinalizar.setLocalizedLabel(ENGLISH, "confirm end session");
            confirmarAlFinalizar.setLocalizedLabel(SPANISH, "confirmar al finalizar la sesión");
            confirmarAlFinalizar.setLocalizedDescription(ENGLISH, "confirm end session");
            confirmarAlFinalizar.setLocalizedDescription(SPANISH, "confirmar al finalizar la sesión de trabajo");
            /**/
            // </editor-fold>
        }

        BooleanExpression conPaginaMenu, conOtraPagina;

        BooleanExpression filtroPaginaMenu, filtroOtraPagina;

        protected Check check101, check102, check111, check112;

        @Override
        protected void settleExpressions() {
            super.settleExpressions();
            /**/
            conPaginaMenu = paginaInicio.isEqualTo(paginaInicio.PAGINA_MENU);
            conOtraPagina = paginaInicio.isEqualTo(paginaInicio.OTRA_PAGINA);
            /*
            filtroPaginaMenu = and(
                paginaMenu.dominioMaestro.isNull(),
                paginaMenu.parametro.isNull(),
                paginaMenu.tipoPagina.isNotEqualTo(paginaMenu.tipoPagina.DETALLE),
                paginaMenu.tipoPagina.isNotEqualTo(paginaMenu.tipoPagina.CONSULTA_DETALLE),
                paginaMenu.urlPagina.notContains("/consulta/recursos/basicos/"),
                paginaMenu.urlPagina.notContains("/procesamiento/recursos/basicos/"),
                paginaMenu.urlPagina.notContains("/registro/recursos/basicos/")
            );
            /**/
            filtroPaginaMenu = paginaMenu.opcionMenu.isTrue();
            /**/
            filtroOtraPagina = otraPagina.inactiva.isFalse();
            /**/
            check101 = conPaginaMenu.implies(paginaMenu.isNotNull());
            check102 = conPaginaMenu.and(paginaMenu.isNotNull()).implies(filtroPaginaMenu);
            /**/
            check111 = conOtraPagina.implies(otraPagina.isNotNull());
            check112 = conOtraPagina.and(otraPagina.isNotNull()).implies(filtroOtraPagina);
            /**/
            // <editor-fold defaultstate="collapsed" desc="localization of CambiarConfiguracion's expressions">
            conPaginaMenu.setLocalizedDescription(ENGLISH, "landing page is \"Menu page\"");
            conPaginaMenu.setLocalizedDescription(SPANISH, "página de inicio es \"Página del menú\"");
            conPaginaMenu.setLocalizedErrorMessage(ENGLISH, "landing page is not \"Menu page\"");
            conPaginaMenu.setLocalizedErrorMessage(SPANISH, "página de inicio no es \"Página del menú\"");
            /**/
            conOtraPagina.setLocalizedDescription(ENGLISH, "landing page is \"Other page\"");
            conOtraPagina.setLocalizedDescription(SPANISH, "página de inicio es \"Otra página\"");
            conOtraPagina.setLocalizedErrorMessage(ENGLISH, "landing page is not \"Other page\"");
            conOtraPagina.setLocalizedErrorMessage(SPANISH, "página de inicio no es \"Otra página\"");
            /**/
            check101.setLocalizedDescription(ENGLISH, "menu page must be specified if landing page is \"Menu page\"");
            check101.setLocalizedDescription(SPANISH, "página del menú se debe especificar si página de inicio es \"Página del menú\"");
            check101.setLocalizedErrorMessage(ENGLISH, "menu page must be specified if landing page is \"Menu page\"");
            check101.setLocalizedErrorMessage(SPANISH, "página del menú se debe especificar si página de inicio es \"Página del menú\"");
            /**/
            check102.setLocalizedDescription(ENGLISH, "menu page must be a valid menu page");
            check102.setLocalizedDescription(SPANISH, "página del menú debe ser una página del menú válida");
            check102.setLocalizedErrorMessage(ENGLISH, "invalid menu page");
            check102.setLocalizedErrorMessage(SPANISH, "página del menú inválida");
            /**/
            check111.setLocalizedDescription(ENGLISH, "other page must be specified if landing page is \"Other page\"");
            check111.setLocalizedDescription(SPANISH, "otra página se debe especificar si página de inicio es \"Otra página\"");
            check111.setLocalizedErrorMessage(ENGLISH, "other page must be specified if landing page is \"Other page\"");
            check111.setLocalizedErrorMessage(SPANISH, "otra página se debe especificar si página de inicio es \"Otra página\"");
            /**/
            check112.setLocalizedDescription(ENGLISH, "other page is active");
            check112.setLocalizedDescription(SPANISH, "otra página se encuentra activa");
            check112.setLocalizedErrorMessage(ENGLISH, "other page is inactive");
            check112.setLocalizedErrorMessage(SPANISH, "otra página se encuentra inactiva");
            /**/
            // </editor-fold>
        }

        @Override
        protected void settleFilters() {
            super.settleFilters();
            /*
            paginaInicio.setRemoveInstanceArray(paginaInicio.OTRA_PAGINA);
            **/
            paginaMenu.setModifyingFilter(conPaginaMenu);
//          paginaMenu.setRenderingFilter(conPaginaMenu);
            paginaMenu.setRequiringFilter(conPaginaMenu);
//          paginaMenu.setNullifyingFilter(not(conPaginaMenu));
            paginaMenu.setSearchQueryFilter(filtroPaginaMenu);
            /**/
            otraPagina.setModifyingFilter(conOtraPagina);
//          otraPagina.setRenderingFilter(conOtraPagina);
            otraPagina.setRequiringFilter(conOtraPagina);
//          otraPagina.setNullifyingFilter(not(conOtraPagina));
            otraPagina.setSearchQueryFilter(filtroOtraPagina);
        }

    }

    @OperationClass(access = OperationAccess.PRIVATE)
    @ProcessOperationClass(builtIn = true)
    public class CambiarPassword extends ProcessOperation {

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            // <editor-fold defaultstate="collapsed" desc="localization of CambiarPassword's attributes">
            setLocalizedLabel(ENGLISH, "change password");
            setLocalizedLabel(SPANISH, "cambiar contraseña");
            setLocalizedSuccessMessage(ENGLISH, "password successfully updated");
            setLocalizedSuccessMessage(SPANISH, "contraseña actualizada con éxito");
            // </editor-fold>
        }

        @InstanceReference
//      @Allocation(maxDepth = 1, maxRound = 0)
        protected meta.entidad.comun.control.acceso.Usuario usuario;

        @ParameterField(auditable = Kleenean.FALSE, password = Kleenean.TRUE, required = Kleenean.TRUE)
        @StringField(maxLength = 128)
        protected StringParameter password;

        @ParameterField(auditable = Kleenean.FALSE, password = Kleenean.TRUE, required = Kleenean.TRUE)
        @StringField(maxLength = 128)
        protected StringParameter nuevoPassword;

        @ParameterField(auditable = Kleenean.FALSE, password = Kleenean.TRUE, required = Kleenean.TRUE)
        @StringField(maxLength = 128)
        protected StringParameter confirmacionPassword;

        @Override
        protected void settleParameters() {
            super.settleParameters();
            // <editor-fold defaultstate="collapsed" desc="localization of CambiarPassword's parameters">
            /**/
            usuario.setLocalizedLabel(ENGLISH, "user");
            usuario.setLocalizedLabel(SPANISH, "usuario");
            usuario.setLocalizedDescription(ENGLISH, "Code of the user you want to assign a new password; "
                + "it is a required field and has no default value");
            usuario.setLocalizedDescription(SPANISH, "Código del usuario al que desea asignar una nueva contraseña; "
                + "es un dato obligatorio y no tiene valor por omisión");
            /**/
            password.setLocalizedLabel(ENGLISH, "password");
            password.setLocalizedLabel(SPANISH, "contraseña");
            password.setLocalizedDescription(ENGLISH, "The user's current password; "
                + "it is a required field and has no default value");
            password.setLocalizedDescription(SPANISH, "La contraseña actual del usuario; "
                + "es un dato obligatorio y no tiene valor por omisión");
            /**/
            nuevoPassword.setLocalizedLabel(ENGLISH, "new password");
            nuevoPassword.setLocalizedLabel(SPANISH, "nueva contraseña");
            nuevoPassword.setLocalizedDescription(ENGLISH, "The new password must be a string of up to 128 letters, numbers and other characters; "
                + "it is a required field and has no default value");
            nuevoPassword.setLocalizedDescription(SPANISH, "La nueva contraseña debe ser una secuencia de hasta 128 letras, números y otros caracteres; "
                + "es un dato obligatorio y no tiene valor por omisión");
            /**/
            confirmacionPassword.setLocalizedLabel(ENGLISH, "password confirmation");
            confirmacionPassword.setLocalizedLabel(SPANISH, "confirmación de la contraseña");
            confirmacionPassword.setLocalizedDescription(ENGLISH, "The password confirmation must match the new password; "
                + "it is a required field and has no default value");
            confirmacionPassword.setLocalizedDescription(SPANISH, "La confirmación de la contraseña debe coincidir con la nueva contraseña; "
                + "es un dato obligatorio y no tiene valor por omisión");
            /**/
            // </editor-fold>
        }

    }

    @OperationClass(access = OperationAccess.PRIVATE)
    @ProcessOperationClass(builtIn = true)
    public class CargarArchivo extends ProcessOperation {

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            // <editor-fold defaultstate="collapsed" desc="localization of CargarArchivo's attributes">
            setLocalizedLabel(ENGLISH, "upload file");
            setLocalizedLabel(SPANISH, "cargar archivo");
            setLocalizedSuccessMessage(ENGLISH, "file successfully uploaded");
            setLocalizedSuccessMessage(SPANISH, "archivo cargado con éxito");
            // </editor-fold>
        }

        @InstanceReference
//      @Allocation(maxDepth = 1, maxRound = 0)
        protected meta.entidad.comun.control.acceso.Usuario usuario;

        @FileReference(max = 100000000)
        @ParameterField(required = Kleenean.TRUE, linkedField = "archivo")
        protected StringParameter archivo;

        @Override
        protected void settleParameters() {
            super.settleParameters();
            // <editor-fold defaultstate="collapsed" desc="localization of CargarArchivo's parameters">
            usuario.setLocalizedLabel(ENGLISH, "user");
            usuario.setLocalizedLabel(SPANISH, "usuario");
            usuario.setLocalizedDescription(ENGLISH, "Code of the user whose file you want to upload; "
                + "it is a required field and has no default value");
            usuario.setLocalizedDescription(SPANISH, "Código del usuario cuyo archivo desea cargar; "
                + "es un dato obligatorio y no tiene valor por omisión");
            /**/
            archivo.setLocalizedLabel(ENGLISH, "file");
            archivo.setLocalizedLabel(SPANISH, "archivo");
            // </editor-fold>
        }

    }

    @OperationClass(access = OperationAccess.PRIVATE)
    @ProcessOperationClass(builtIn = true)
    public class CargarRetrato extends ProcessOperation {

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            // <editor-fold defaultstate="collapsed" desc="localization of CargarRetrato's attributes">
            setLocalizedLabel(ENGLISH, "upload portrait");
            setLocalizedLabel(SPANISH, "cargar retrato");
            setLocalizedSuccessMessage(ENGLISH, "portrait successfully uploaded");
            setLocalizedSuccessMessage(SPANISH, "retrato cargado con éxito");
            // </editor-fold>
        }

        @InstanceReference
//      @Allocation(maxDepth = 1, maxRound = 0)
        protected meta.entidad.comun.control.acceso.Usuario usuario;

        @FileReference(max = 500000, types = MimeType.IMAGE, storage = UploadStorageOption.ROW, blobField = "octetos")
        @ParameterField(required = Kleenean.TRUE)
        protected StringParameter retrato;

        @Override
        protected void settleParameters() {
            super.settleParameters();
            // <editor-fold defaultstate="collapsed" desc="localization of CargarRetrato's parameters">
            usuario.setLocalizedLabel(ENGLISH, "user");
            usuario.setLocalizedLabel(SPANISH, "usuario");
            usuario.setLocalizedDescription(ENGLISH, "Code of the user whose portrait you want to upload; "
                + "it is a required field and has no default value");
            usuario.setLocalizedDescription(SPANISH, "Código del usuario cuyo retrato desea cargar; "
                + "es un dato obligatorio y no tiene valor por omisión");
            /**/
            retrato.setLocalizedLabel(ENGLISH, "portrait");
            retrato.setLocalizedLabel(SPANISH, "retrato");
            retrato.setLocalizedDescription(ENGLISH, "The portrait must be a graphic file with a maximum size of 500 KB");
            retrato.setLocalizedDescription(SPANISH, "El retrato debe ser un archivo gráfico con un tamaño máximo de 500 KB");
            // </editor-fold>
        }

    }

    @OperationClass(access = OperationAccess.PRIVATE)
    @ProcessOperationClass(builtIn = true)
    public class RecortarRetrato extends ProcessOperation {

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            // <editor-fold defaultstate="collapsed" desc="localization of RecortarRetrato's attributes">
            setLocalizedLabel(ENGLISH, "crop portrait");
            setLocalizedLabel(SPANISH, "recortar retrato");
            setLocalizedSuccessMessage(ENGLISH, "portrait successfully cropped");
            setLocalizedSuccessMessage(SPANISH, "retrato recortado con éxito");
            // </editor-fold>
        }

        @InstanceReference
//      @Allocation(maxDepth = 1, maxRound = 0)
        protected meta.entidad.comun.control.acceso.Usuario usuario;

        @Override
        protected void settleParameters() {
            super.settleParameters();
            // <editor-fold defaultstate="collapsed" desc="localization of RecortarRetrato's parameters">
            usuario.setLocalizedLabel(ENGLISH, "user");
            usuario.setLocalizedLabel(SPANISH, "usuario");
            usuario.setLocalizedDescription(ENGLISH, "Code of the user whose portrait you want to crop; "
                + "it is a required field and has no default value");
            usuario.setLocalizedDescription(SPANISH, "Código del usuario cuyo retrato desea recortar; "
                + "es un dato obligatorio y no tiene valor por omisión");
            // </editor-fold>
        }

    }

    @OperationClass(access = OperationAccess.PRIVATE)
    @ProcessOperationClass(builtIn = true)
    public class TomarRetrato extends ProcessOperation {

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            // <editor-fold defaultstate="collapsed" desc="localization of TomarRetrato's attributes">
            setLocalizedLabel(ENGLISH, "take portrait");
            setLocalizedLabel(SPANISH, "tomar retrato");
            setLocalizedSuccessMessage(ENGLISH, "portrait successfully captured");
            setLocalizedSuccessMessage(SPANISH, "retrato capturado con éxito");
            // </editor-fold>
        }

        @InstanceReference
//      @Allocation(maxDepth = 1, maxRound = 0)
        protected meta.entidad.comun.control.acceso.Usuario usuario;

        @Override
        protected void settleParameters() {
            super.settleParameters();
            // <editor-fold defaultstate="collapsed" desc="localization of TomarRetrato's parameters">
            usuario.setLocalizedLabel(ENGLISH, "user");
            usuario.setLocalizedLabel(SPANISH, "usuario");
            usuario.setLocalizedDescription(ENGLISH, "Code of the user you want to portray; "
                + "it is a required field and has no default value");
            usuario.setLocalizedDescription(SPANISH, "Código del usuario que desea retratar; "
                + "es un dato obligatorio y no tiene valor por omisión");
            // </editor-fold>
        }

    }
    // </editor-fold>

    @Override
    protected void settleOperations() {
        super.settleOperations();
        designarSuperUsuario.addTransition(designableComoSuperUsuario, superUsuarioAnulable);
        anularSuperUsuario.addTransition(superUsuarioAnulable, designableComoSuperUsuario);
        designarSuperAuditor.addTransition(designableComoSuperAuditor, superAuditorAnulable);
        anularSuperAuditor.addTransition(superAuditorAnulable, designableComoSuperAuditor);
        desactivar.addTransition(desactivable, reactivable);
        reactivar.addTransition(reactivable, desactivable);
        anularSupervisor.addTransition(supervisorAnulable, supervisorAsignable);
        asignarSupervisor.addTransition(supervisorAsignable, supervisorAnulable);
    }

}