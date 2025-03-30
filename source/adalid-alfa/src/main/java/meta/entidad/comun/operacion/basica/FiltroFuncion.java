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
package meta.entidad.comun.operacion.basica;

import adalid.core.*;
import adalid.core.annotations.*;
import adalid.core.enums.*;
import adalid.core.interfaces.*;
import adalid.core.properties.*;
import java.lang.reflect.Field;
import meta.entidad.comun.configuracion.basica.ext.Funcion;
import meta.entidad.comun.control.acceso.Usuario;

/**
 * @author Jorge Campins
 */
@EntityClass(base = Kleenean.TRUE, independent = Kleenean.FALSE, resourceType = ResourceType.OPERATION, resourceGender = ResourceGender.MASCULINE)
@EntityCodeGen(bws = Kleenean.FALSE, fws = Kleenean.FALSE)
@EntitySelectOperation(enabled = Kleenean.TRUE)
@EntityInsertOperation(enabled = Kleenean.TRUE)
@EntityUpdateOperation(enabled = Kleenean.TRUE)
@EntityDeleteOperation(enabled = Kleenean.TRUE)
@EntityTableView(enabled = Kleenean.TRUE, responsiveMode = TableResponsiveMode.PRIORITY, menu = ViewMenuOption.NONE)
@EntityDetailView(enabled = Kleenean.TRUE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.TRUE, menu = Kleenean.FALSE)
public class FiltroFuncion extends AbstractPersistentEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public FiltroFuncion(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public LongProperty id;

    @VersionProperty
    public LongProperty version;

    @BusinessKey
    @StringField(maxLength = 100)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.FALSE, heading = Kleenean.FALSE/*, overlay = Kleenean.FALSE*/, defaultCondition = DefaultCondition.UNCONDITIONALLY_ON_INSERT, defaultCheckpoint = Checkpoint.USER_INTERFACE)
    public StringProperty codigoFiltroFuncion;

    @NameProperty
    public StringProperty nombreFiltroFuncion;

    @DescriptionProperty
    @StringField(maxLength = 0)
    public StringProperty descripcionFiltroFuncion;

//  20171213: remove foreign-key referring to Funcion
//  @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.BIDIRECTIONAL, view = MasterDetailView.TABLE_AND_DETAIL)
    @ColumnField(nullable = Kleenean.FALSE)
    public Funcion funcion;

    @OwnerProperty
    @ForeignKey(onDelete = OnDeleteAction.CASCADE, onUpdate = OnUpdateAction.CASCADE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @ColumnField(nullable = Kleenean.TRUE)
    @PropertyField(responsivePriority = 6, create = Kleenean.FALSE, update = Kleenean.FALSE, search = Kleenean.TRUE, table = Kleenean.TRUE, report = Kleenean.TRUE, defaultCondition = DefaultCondition.IF_NULL_ON_INSERT, defaultCheckpoint = Checkpoint.USER_INTERFACE)
    public Usuario usuario;

    @ColumnField(nullable = Kleenean.TRUE)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(responsivePriority = 6, create = Kleenean.FALSE, update = Kleenean.FALSE, search = Kleenean.TRUE, table = Kleenean.TRUE, report = Kleenean.FALSE)
    public Usuario remitente;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(responsivePriority = 6, create = Kleenean.FALSE, update = Kleenean.FALSE, search = Kleenean.TRUE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public BooleanProperty esPublico;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(hidden = Kleenean.TRUE)
    public BooleanProperty esEspecial;

    @PropertyField(hidden = Kleenean.TRUE)
    public LongProperty idFiltroFuncionOriginal;

    @Override
    protected void addAllocationStrings() {
        super.addAllocationStrings();
        super.addAllocationStrings(
            "funcion.dominio"
        );
    }

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        /**/
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of FiltroFuncion's attributes">
        setLocalizedLabel(ENGLISH, "query filter");
        setLocalizedLabel(SPANISH, "filtro de búsqueda");
        setLocalizedShortLabel(ENGLISH, "filter");
        setLocalizedShortLabel(SPANISH, "filtro");
        setLocalizedCollectionLabel(ENGLISH, "Query Filters");
        setLocalizedCollectionLabel(SPANISH, "Filtros de Búsqueda");
        setLocalizedCollectionShortLabel(ENGLISH, "Filters");
        setLocalizedCollectionShortLabel(SPANISH, "Filtros");
        /**/
        setLocalizedDescription(ENGLISH, "Each instance of " + b("Query Filters") + " represents a "
            + "query filter defined by the user."
            + "");
        setLocalizedDescription(SPANISH, "Cada instancia de " + b("Filtros de Búsqueda") + " representa un "
            + "filtro de búsqueda definido por el usuario."
            + "");
        /**/
        setLocalizedShortDescription(ENGLISH, "query filter defined by the user");
        setLocalizedShortDescription(SPANISH, "filtro de búsqueda definido por el usuario");
        /**/
        // </editor-fold>
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        /**/
//      setOrderBy(funcion, esPublico, esEspecial, nombreFiltroFuncion);
        setOrderBy(funcion, esPublico, nombreFiltroFuncion);
        /**/
        codigoFiltroFuncion.setDefaultValue(concat(funcion.dominio.codigoDominio, HYPHEN, id));
        /**/
        esPublico.setInitialValue(false);
        esPublico.setDefaultValue(false);
        /**/
        esEspecial.setInitialValue(false);
        esEspecial.setDefaultValue(false);
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of FiltroFuncion's properties">
        /**/
        codigoFiltroFuncion.setLocalizedLabel(ENGLISH, "query filter code");
        codigoFiltroFuncion.setLocalizedLabel(SPANISH, "código del filtro de búsqueda");
        codigoFiltroFuncion.setLocalizedShortLabel(ENGLISH, "code");
        codigoFiltroFuncion.setLocalizedShortLabel(SPANISH, "código");
        codigoFiltroFuncion.setLocalizedDefaultValueTag(ENGLISH, b("code") + _of_ + b("domain") + _of_ + b("function") + " + " + b("id"));
        codigoFiltroFuncion.setLocalizedDefaultValueTag(SPANISH, b("código") + _de_ + b("dominio") + _de_ + b("función") + " + " + b("id"));
        /**/
        nombreFiltroFuncion.setLocalizedLabel(ENGLISH, "query filter name");
        nombreFiltroFuncion.setLocalizedLabel(SPANISH, "nombre del filtro de búsqueda");
        nombreFiltroFuncion.setLocalizedShortLabel(ENGLISH, "name");
        nombreFiltroFuncion.setLocalizedShortLabel(SPANISH, "nombre");
        /**/
        descripcionFiltroFuncion.setLocalizedLabel(ENGLISH, "query filter description");
        descripcionFiltroFuncion.setLocalizedLabel(SPANISH, "descripción del filtro de búsqueda");
        descripcionFiltroFuncion.setLocalizedShortLabel(ENGLISH, "description");
        descripcionFiltroFuncion.setLocalizedShortLabel(SPANISH, "descripción");
        /**/
        funcion.setLocalizedLabel(ENGLISH, "function");
        funcion.setLocalizedLabel(SPANISH, "función");
        /**/
        usuario.setLocalizedLabel(ENGLISH, "owner");
        usuario.setLocalizedLabel(SPANISH, "propietario");
        /**/
        remitente.setLocalizedLabel(ENGLISH, "sender");
        remitente.setLocalizedLabel(SPANISH, "remitente");
        /**/
        esPublico.setLocalizedLabel(ENGLISH, "public");
        esPublico.setLocalizedLabel(SPANISH, "público");
        esPublico.setLocalizedDescription(ENGLISH, "Public query filter indicator; "
            + "public query filters can be used by all users authorized to query the entity");
        esPublico.setLocalizedDescription(SPANISH, "indicador de filtro de búsqueda público; "
            + "los filtros de búsqueda públicos pueden ser utilizados por todos los usuarios autorizados a hacer consultas sobre la entidad");
        /**/
        esEspecial.setLocalizedLabel(ENGLISH, "special");
        esEspecial.setLocalizedLabel(SPANISH, "especial");
        esEspecial.setLocalizedDescription(ENGLISH, "Special query filter indicator; "
            + "special query filters are predefined filters of the application; they are initially public, but can be privatized");
        esEspecial.setLocalizedDescription(SPANISH, "indicador de filtro de búsqueda especial; "
            + "los filtros de búsqueda especiales son filtros predefinidos de la aplicación; inicialmente son públicos, pero pueden ser privatizados");
        /**/
        idFiltroFuncionOriginal.setLocalizedLabel(ENGLISH, "original query filter");
        idFiltroFuncionOriginal.setLocalizedLabel(SPANISH, "filtro original");
        idFiltroFuncionOriginal.setLocalizedShortLabel(ENGLISH, "original");
        idFiltroFuncionOriginal.setLocalizedShortLabel(SPANISH, "original");
        /**/
        // </editor-fold>
    }

    protected Segment privados; //, ordinarios, personales;

    @Override
    protected void settleExpressions() {
        super.settleExpressions();
        /*
        predeterminados = esPublico.or(usuario.id.isEqualTo(CURRENT_USER_ID));
        /**/
        privados = esPublico.isFalse();
//      ordinarios = esEspecial.isFalse();
//      personales = privados.and(ordinarios);
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of VistaFuncionCol's expressions">
        /*
        predeterminados.setLocalizedCollectionLabel(ENGLISH, "all user's own filters and all public filters");
        predeterminados.setLocalizedCollectionLabel(SPANISH, "todos los filtros propios del usuario y todos los filtros públicos");
        predeterminados.setLocalizedCollectionShortLabel(ENGLISH, "My filters and public filters");
        predeterminados.setLocalizedCollectionShortLabel(SPANISH, "Mis filtros y los filtros públicos");
        predeterminados.setLocalizedDescription(ENGLISH, "the filter is own or public");
        predeterminados.setLocalizedDescription(SPANISH, "el filtro es propio o público");
        predeterminados.setLocalizedErrorMessage(ENGLISH, "the filter is private and of another user");
        predeterminados.setLocalizedErrorMessage(SPANISH, "el filtro es privado y de otro usuario");
        /**/
        privados.setLocalizedErrorMessage(ENGLISH, "the filter is public");
        privados.setLocalizedErrorMessage(SPANISH, "el filtro es público");
        /*
        ordinarios.setLocalizedErrorMessage(ENGLISH, "the filter is special");
        ordinarios.setLocalizedErrorMessage(SPANISH, "el filtro es especial");
        /*
        personales.setLocalizedErrorMessage(ENGLISH, "the filter is public or special");
        personales.setLocalizedErrorMessage(SPANISH, "el filtro es público o especial");
        /**/
        // </editor-fold>
    }

    @Override
    protected void settleFilters() {
        super.settleFilters();
        /**/
        setUpdateFilter(privados);
        setDeleteFilter(privados);
        /*
        addSelectSegment(predeterminados, true);
        /**/
    }

    protected EnviarCopia enviarCopia;

    @OperationClass(access = OperationAccess.PROTECTED)
    public class EnviarCopia extends ProcessOperation {

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            /**/
            // <editor-fold defaultstate="collapsed" desc="localization of EnviarCopia's attributes">
            /**/
            setLocalizedLabel(ENGLISH, "send a copy");
            setLocalizedLabel(SPANISH, "enviar copia");
            /**/
            setLocalizedDescription(ENGLISH, "send a copy of the filter to the specified recipient");
            setLocalizedDescription(SPANISH, "enviar una copia del filtro al destinatario especificado");
            /**/
            setLocalizedSuccessMessage(ENGLISH, "a copy of the filter was sent to the specified recipient");
            setLocalizedSuccessMessage(SPANISH, "se envió una copia del filtro al destinatario especificado");
            /**/
            // </editor-fold>
            /**/
        }

        @InstanceReference
        protected FiltroFuncion filtro;

        @ParameterField(required = Kleenean.TRUE)
        @EntityReferenceConversionValidation(restrictedAccess = Kleenean.FALSE)
        protected Usuario destinatario;

        @Override
        protected void settleParameters() {
            super.settleParameters();
            /**/
            // <editor-fold defaultstate="collapsed" desc="localization of EnviarCopia's parameters">
            /**/
            filtro.setLocalizedLabel(ENGLISH, "filter");
            filtro.setLocalizedLabel(SPANISH, "filtro");
            /**/
            destinatario.setLocalizedLabel(ENGLISH, "recipient user");
            destinatario.setLocalizedLabel(SPANISH, "usuario destinatario");
            destinatario.setLocalizedShortLabel(ENGLISH, "recipient");
            destinatario.setLocalizedShortLabel(SPANISH, "destinatario");
            destinatario.setLocalizedShortDescription(ENGLISH, "filter recipient");
            destinatario.setLocalizedShortDescription(SPANISH, "destinatario del filtro");
            destinatario.setLocalizedTooltip(ENGLISH, "user code of the filter recipient");
            destinatario.setLocalizedTooltip(SPANISH, "código de usuario del destinatario del filtro");
            /**/
            destinatario.codigoUsuario.setLocalizedShortLabel(ENGLISH, "recipient code");
            destinatario.codigoUsuario.setLocalizedShortLabel(SPANISH, "destinatario");
            /**/
            // </editor-fold>
            /**/
        }

        protected Check check101, check102, check201, check202;

        @Override
        protected void settleExpressions() {
            super.settleExpressions();
            /**/
            check101 = filtro.esPublico.isFalse();
            check102 = filtro.usuario.id.isEqualTo(CURRENT_USER_ID);
            /**/
            check201 = destinatario.esUsuarioEspecial.isFalse();
            check202 = destinatario.id.isNotEqualTo(CURRENT_USER_ID);
            /**/
            // <editor-fold defaultstate="collapsed" desc="localization of EnviarCopia's expressions">
            /**/
            check101.setLocalizedDescription(ENGLISH, "the filter is not a public filter");
            check101.setLocalizedDescription(SPANISH, "el filtro no es un filtro público");
            check101.setLocalizedErrorMessage(ENGLISH, "it is not allowed to send copies of public filters");
            check101.setLocalizedErrorMessage(SPANISH, "no está permitido enviar copias de filtros públicos");
            /**/
            check102.setLocalizedDescription(ENGLISH, "the filter belongs to the current user");
            check102.setLocalizedDescription(SPANISH, "el filtro le pertenece al usuario actual");
            check102.setLocalizedErrorMessage(ENGLISH, "it is not allowed to send copies of filters that do not belong to you");
            check102.setLocalizedErrorMessage(SPANISH, "no está permitido enviar copias de filtros que no le pertenecen");
            /**/
            check201.setLocalizedDescription(ENGLISH, "the recipient is not a special user");
            check201.setLocalizedDescription(SPANISH, "el destinatario no es un usuario especial");
            check201.setLocalizedErrorMessage(ENGLISH, "it is not allowed to send copies of filters to special users");
            check201.setLocalizedErrorMessage(SPANISH, "no está permitido enviar copias de filtros a usuarios especiales");
            /**/
            check202.setLocalizedDescription(ENGLISH, "the recipient is not the current user");
            check202.setLocalizedDescription(SPANISH, "el destinatario no es el usuario actual");
            check202.setLocalizedErrorMessage(ENGLISH, "it is not allowed to send copies of filters to yourself");
            check202.setLocalizedErrorMessage(SPANISH, "no está permitido enviarse copias de filtros a uno mismo");
            /**/
            // </editor-fold>
            /**/
        }

        @Override
        protected void settleFilters() {
            super.settleFilters();
            /**/
            destinatario.setSearchQueryFilter(and(check201, check202));
            /**/
        }

    }

    protected Publicar publicar;

    @OperationClass(access = OperationAccess.PROTECTED)
    public class Publicar extends ProcessOperation {

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            /**/
            // <editor-fold defaultstate="collapsed" desc="localization of Publicar's attributes">
            /**/
            setLocalizedLabel(ENGLISH, "publish");
            setLocalizedLabel(SPANISH, "publicar");
            /**/
            setLocalizedDescription(ENGLISH, "make a private filter public");
            setLocalizedDescription(SPANISH, "convertir un fitro privado en público");
            /**/
            setLocalizedSuccessMessage(ENGLISH, "the filter was successfully published");
            setLocalizedSuccessMessage(SPANISH, "el filtro se publicó con éxito");
            /**/
            // </editor-fold>
            /**/
        }

        @InstanceReference
        protected FiltroFuncion filtro;

        @Override
        protected void settleParameters() {
            super.settleParameters();
            /**/
            filtro.esPublico.setCurrentValue(true);
            filtro.usuario.setCurrentValue(SpecialEntityValue.NULL);
            /**/
            // <editor-fold defaultstate="collapsed" desc="localization of Publicar's parameters">
            /**/
            filtro.setLocalizedLabel(ENGLISH, "filter");
            filtro.setLocalizedLabel(SPANISH, "filtro");
            /**/
            // </editor-fold>
            /**/
        }

        protected Check check101, check102;

        @Override
        protected void settleExpressions() {
            super.settleExpressions();
            /**/
            check101 = filtro.esPublico.isFalse();
            check102 = filtro.usuario.id.isEqualTo(CURRENT_USER_ID);
            /**/
            // <editor-fold defaultstate="collapsed" desc="localization of Publicar's expressions">
            /**/
            check101.setLocalizedDescription(ENGLISH, "the filter is not a public filter");
            check101.setLocalizedDescription(SPANISH, "el filtro no es un filtro público");
            check101.setLocalizedErrorMessage(ENGLISH, "the filter is already public");
            check101.setLocalizedErrorMessage(SPANISH, "el filtro ya es público");
            /**/
            check102.setLocalizedDescription(ENGLISH, "the filter belongs to the current user");
            check102.setLocalizedDescription(SPANISH, "el filtro le pertenece al usuario actual");
            check102.setLocalizedErrorMessage(ENGLISH, "it is not allowed to publish filters that do not belong to you");
            check102.setLocalizedErrorMessage(SPANISH, "no está permitido publicar filtros que no le pertenecen");
            /**/
            // </editor-fold>
            /**/
        }

    }

    protected Privatizar privatizar;

    @OperationClass(access = OperationAccess.PROTECTED)
    public class Privatizar extends ProcessOperation {

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            /**/
            // <editor-fold defaultstate="collapsed" desc="localization of Privatizar's attributes">
            /**/
            setLocalizedLabel(ENGLISH, "privatize");
            setLocalizedLabel(SPANISH, "privatizar");
            /**/
            setLocalizedDescription(ENGLISH, "make a public filter private");
            setLocalizedDescription(SPANISH, "convertir un fitro público en privado");
            /**/
            setLocalizedSuccessMessage(ENGLISH, "the filter was successfully privatized");
            setLocalizedSuccessMessage(SPANISH, "el filtro se privatizó con éxito");
            /**/
            // </editor-fold>
            /**/
        }

        @InstanceReference
        protected FiltroFuncion filtro;

        @Override
        protected void settleParameters() {
            super.settleParameters();
            /**/
            filtro.esPublico.setCurrentValue(false);
            filtro.usuario.setCurrentValue(SpecialEntityValue.CURRENT_USER);
            /**/
            // <editor-fold defaultstate="collapsed" desc="localization of Privatizar's parameters">
            /**/
            filtro.setLocalizedLabel(ENGLISH, "filter");
            filtro.setLocalizedLabel(SPANISH, "filtro");
            /**/
            // </editor-fold>
            /**/
        }

        protected Check check101;

        @Override
        protected void settleExpressions() {
            super.settleExpressions();
            /**/
            check101 = filtro.usuario.isNull().and(filtro.esPublico.isTrue());
            /**/
            // <editor-fold defaultstate="collapsed" desc="localization of Privatizar's expressions">
            /**/
            check101.setLocalizedDescription(ENGLISH, "the filter is not a private filter");
            check101.setLocalizedDescription(SPANISH, "el filtro no es un filtro privado");
            check101.setLocalizedErrorMessage(ENGLISH, "the filter is already private");
            check101.setLocalizedErrorMessage(SPANISH, "el filtro ya es privado");
            /**/
            // </editor-fold>
            /**/
        }

    }

}
