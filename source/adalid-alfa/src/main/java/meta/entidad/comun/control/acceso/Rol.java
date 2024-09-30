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
package meta.entidad.comun.control.acceso;

import adalid.core.*;
import adalid.core.annotations.*;
import adalid.core.enums.*;
import adalid.core.interfaces.*;
import adalid.core.parameters.*;
import adalid.core.properties.*;
import java.lang.reflect.Field;

/**
 * @author Jorge Campins
 */
@EntityClass(independent = Kleenean.TRUE, resourceType = ResourceType.OPERATION, resourceGender = ResourceGender.MASCULINE)
@EntityCodeGen(bws = Kleenean.FALSE, fws = Kleenean.FALSE)
@EntitySelectOperation(enabled = Kleenean.TRUE, onload = SelectOnloadOption.EXECUTE)
@EntityInsertOperation(enabled = Kleenean.TRUE)
@EntityUpdateOperation(enabled = Kleenean.TRUE)
@EntityDeleteOperation(enabled = Kleenean.TRUE)
@EntityTableView(enabled = Kleenean.TRUE, responsiveMode = TableResponsiveMode.PRIORITY, inserts = Kleenean.TRUE, updates = Kleenean.TRUE, quickFilter = Kleenean.TRUE)
@EntityDetailView(enabled = Kleenean.TRUE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.TRUE)
public class Rol extends AbstractPersistentEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public Rol(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public LongProperty id;

    @VersionProperty
    public LongProperty version;

    @BusinessKey
    @StringField(maxLength = 100)
    @PropertyField(overlay = Kleenean.TRUE)
    public StringProperty codigoRol;

    @NameProperty
    public StringProperty nombreRol;

    @DescriptionProperty
    @StringField(maxLength = 0)
    public StringProperty descripcionRol;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(responsivePriority = 6, create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.TRUE, overlay = Kleenean.TRUE)
    public BooleanProperty esSuperRol;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(responsivePriority = 6, create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.TRUE, overlay = Kleenean.TRUE)
    public BooleanProperty esRolEspecial;

    @InactiveIndicator
    @ColumnField(nullable = Kleenean.FALSE)
//  @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.TRUE, overlay = Kleenean.TRUE)
    @PropertyField(hidden = Kleenean.TRUE)
    public BooleanProperty esRolInactivo;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(responsivePriority = 5, create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.TRUE, overlay = Kleenean.TRUE)
    public TipoRol tipoRol;

    @SegmentProperty
    @EntityReferenceSearch(searchType = SearchType.LIST, listStyle = ListStyle.CHARACTER_KEY_AND_NAME, displayMode = DisplayMode.WRITING)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(view = MasterDetailView.TABLE_AND_DETAIL)
    @PropertyField(responsivePriority = 6, create = Kleenean.TRUE, update = Kleenean.TRUE, required = Kleenean.TRUE, table = Kleenean.TRUE, report = Kleenean.TRUE, heading = Kleenean.TRUE, overlay = Kleenean.TRUE)
    public GrupoUsuario grupo;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of Rol's attributes">
        setLocalizedLabel(ENGLISH, "role");
        setLocalizedLabel(SPANISH, "rol");
        setLocalizedCollectionLabel(ENGLISH, "Roles");
        setLocalizedCollectionLabel(SPANISH, "Roles");
        /**/
        setLocalizedDescription(ENGLISH, "Each instance of " + b("Roles") + " represents an "
            + "application role."
            + "");
        setLocalizedDescription(SPANISH, "Cada instancia de " + b("Roles") + " representa un "
            + "rol de la aplicación."
            + "");
        /**/
        setLocalizedShortDescription(ENGLISH, "application role");
        setLocalizedShortDescription(SPANISH, "rol de la aplicación");
        /**/
        // </editor-fold>
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        /*
        setOrderBy(codigoRol);
        /**/
        esSuperRol.setInitialValue(false);
        esSuperRol.setDefaultValue(false);
        /**/
        esRolEspecial.setInitialValue(false);
        esRolEspecial.setDefaultValue(false);
        /**/
        esRolInactivo.setInitialValue(false);
        esRolInactivo.setDefaultValue(false);
        /**/
//      grupo.setInitialValue(esRolEspecial.then(grupo.USUARIOS_ESPECIALES).otherwise(grupo.USUARIOS_ORDINARIOS));
//      grupo.setDefaultValue(esRolEspecial.then(grupo.USUARIOS_ESPECIALES).otherwise(grupo.USUARIOS_ORDINARIOS));
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of Rol's properties">
        /**/
        codigoRol.setLocalizedLabel(ENGLISH, "role code");
        codigoRol.setLocalizedLabel(SPANISH, "código del rol");
        codigoRol.setLocalizedShortLabel(ENGLISH, "code");
        codigoRol.setLocalizedShortLabel(SPANISH, "código");
        /**/
        nombreRol.setLocalizedLabel(ENGLISH, "role name");
        nombreRol.setLocalizedLabel(SPANISH, "nombre del rol");
        nombreRol.setLocalizedShortLabel(ENGLISH, "name");
        nombreRol.setLocalizedShortLabel(SPANISH, "nombre");
        /**/
        descripcionRol.setLocalizedLabel(ENGLISH, "role description");
        descripcionRol.setLocalizedLabel(SPANISH, "descripción del rol");
        descripcionRol.setLocalizedShortLabel(ENGLISH, "description");
        descripcionRol.setLocalizedShortLabel(SPANISH, "descripción");
        /**/
        esSuperRol.setLocalizedLabel(ENGLISH, "super role");
        esSuperRol.setLocalizedLabel(SPANISH, "súper rol");
        esSuperRol.setLocalizedShortLabel(ENGLISH, "super");
        esSuperRol.setLocalizedShortLabel(SPANISH, "súper");
        /**/
        esRolEspecial.setLocalizedLabel(ENGLISH, "special role");
        esRolEspecial.setLocalizedLabel(SPANISH, "rol especial");
        esRolEspecial.setLocalizedShortLabel(ENGLISH, "special");
        esRolEspecial.setLocalizedShortLabel(SPANISH, "especial");
        /**/
        esRolInactivo.setLocalizedLabel(ENGLISH, "inactive role");
        esRolInactivo.setLocalizedLabel(SPANISH, "rol inactivo");
        esRolInactivo.setLocalizedShortLabel(ENGLISH, "inactive");
        esRolInactivo.setLocalizedShortLabel(SPANISH, "inactivo");
        esRolInactivo.setLocalizedDescription(ENGLISH, "inactive role");
        esRolInactivo.setLocalizedDescription(SPANISH, "rol inactivo");
        /**/
        tipoRol.setLocalizedLabel(ENGLISH, "role type");
        tipoRol.setLocalizedLabel(SPANISH, "tipo de rol");
        tipoRol.setLocalizedShortLabel(ENGLISH, "type");
        tipoRol.setLocalizedShortLabel(SPANISH, "tipo");
        /**/
        grupo.setLocalizedLabel(ENGLISH, "user group");
        grupo.setLocalizedLabel(SPANISH, "grupo de usuarios");
        grupo.setLocalizedShortLabel(ENGLISH, "group");
        grupo.setLocalizedShortLabel(SPANISH, "grupo");
        grupo.setLocalizedDescription(ENGLISH, "group to which this role belongs");
        grupo.setLocalizedDescription(SPANISH, "grupo al que pertenece este rol");
        grupo.setLocalizedTooltip(ENGLISH, "code of the group to which this role belongs");
        grupo.setLocalizedTooltip(SPANISH, "código del grupo al que pertenece este rol");
        /**/
        // </editor-fold>
    }

    public Instance SUPER_GESTOR;

    public Instance SUPER_LECTOR;

    public Instance OPERADOR;

    /**
     * Administrator role instance getter (for velocity templates)
     *
     * @return the administrator role instance
     */
    public Instance getAdministratorRoleInstance() {
        return SUPER_GESTOR;
    }

    /**
     * Auditor role instance getter (for velocity templates)
     *
     * @return the auditor role instance
     */
    public Instance getAuditorRoleInstance() {
        return SUPER_LECTOR;
    }

    /**
     * Basic role instance getter (for velocity templates)
     *
     * @return the basic role instance
     */
    public Instance getBasicRoleInstance() {
        return OPERADOR;
    }

    @Override
    protected void settleInstances() {
        super.settleInstances();
        SUPER_GESTOR.newInstanceField(id, 101);
        SUPER_GESTOR.newInstanceField(codigoRol, "SuperGestor");
        SUPER_GESTOR.newInstanceField(nombreRol, "Súper Gestor");
        SUPER_GESTOR.newInstanceField(descripcionRol, "Permite ejecutar todas las funciones del sistema");
        SUPER_GESTOR.newInstanceField(esSuperRol, true);
        SUPER_GESTOR.newInstanceField(esRolEspecial, true);
        SUPER_GESTOR.newInstanceField(tipoRol, tipoRol.GESTOR);
//      SUPER_GESTOR.newInstanceField(idGrupoAplicacion, idGrupoAplicacion.SERVICIOS_COMUNES);
        SUPER_LECTOR.newInstanceField(id, 102);
        SUPER_LECTOR.newInstanceField(codigoRol, "SuperLector");
        SUPER_LECTOR.newInstanceField(nombreRol, "Súper Lector");
        SUPER_LECTOR.newInstanceField(descripcionRol, "Permite ejecutar todas las funciones de lectura del sistema");
        SUPER_LECTOR.newInstanceField(esSuperRol, true);
        SUPER_LECTOR.newInstanceField(esRolEspecial, true);
        SUPER_LECTOR.newInstanceField(tipoRol, tipoRol.LECTOR);
//      SUPER_LECTOR.newInstanceField(idGrupoAplicacion, idGrupoAplicacion.SERVICIOS_COMUNES);
        OPERADOR.newInstanceField(id, 103);
        OPERADOR.newInstanceField(codigoRol, "Operador");
        OPERADOR.newInstanceField(nombreRol, "Operador");
        OPERADOR.newInstanceField(descripcionRol, "Permite ejecutar todas las funciones públicas del sistema");
        OPERADOR.newInstanceField(esSuperRol, false);
        OPERADOR.newInstanceField(esRolEspecial, true);
        OPERADOR.newInstanceField(tipoRol, tipoRol.OPERADOR);
        // <editor-fold defaultstate="collapsed" desc="localization of Rol's instances">
        /**/
        SUPER_GESTOR.newInstanceField(codigoRol, "SuperManager", ENGLISH);
        SUPER_GESTOR.newInstanceField(codigoRol, "SuperGestor", SPANISH);
        /**/
        SUPER_GESTOR.newInstanceField(nombreRol, "Super Manager", ENGLISH);
        SUPER_GESTOR.newInstanceField(nombreRol, "Súper Gestor", SPANISH);
        /**/
        SUPER_GESTOR.newInstanceField(descripcionRol, "Allows to execute all application's functions", ENGLISH);
        SUPER_GESTOR.newInstanceField(descripcionRol, "Permite ejecutar todas las funciones del sistema", SPANISH);
        /**/
        SUPER_LECTOR.newInstanceField(codigoRol, "SuperReader", ENGLISH);
        SUPER_LECTOR.newInstanceField(codigoRol, "SuperLector", SPANISH);
        /**/
        SUPER_LECTOR.newInstanceField(nombreRol, "Super Reader", ENGLISH);
        SUPER_LECTOR.newInstanceField(nombreRol, "Súper Lector", SPANISH);
        /**/
        SUPER_LECTOR.newInstanceField(descripcionRol, "Allows to execute all application's reading functions", ENGLISH);
        SUPER_LECTOR.newInstanceField(descripcionRol, "Permite ejecutar todas las funciones de lectura del sistema", SPANISH);
        /**/
        OPERADOR.newInstanceField(codigoRol, "Operator", ENGLISH);
        OPERADOR.newInstanceField(codigoRol, "Operador", SPANISH);
        /**/
        OPERADOR.newInstanceField(nombreRol, "Operator", ENGLISH);
        OPERADOR.newInstanceField(nombreRol, "Operador", SPANISH);
        /**/
        OPERADOR.newInstanceField(descripcionRol, "Allows to execute all application's public functions", ENGLISH);
        OPERADOR.newInstanceField(descripcionRol, "Permite ejecutar todas las funciones públicas del sistema", SPANISH);
        /**/
        // </editor-fold>
    }

    protected Segment modificables;

    protected Segment rolesBasicos, rolesAgregados;

    protected Segment rolesEspeciales, rolesOrdinarios, rolesActivos, rolesInactivos;

    protected State desactivable, reactivable;

    @Override
    protected void settleExpressions() {
        super.settleExpressions();
        /**/
        modificables = id.isGreaterOrEqualTo(10000L);
        /**/
        rolesBasicos = id.isLessThan(10000L);
        rolesAgregados = id.isGreaterOrEqualTo(10000L);
        /**/
        rolesEspeciales = esRolEspecial.isTrue();
        rolesOrdinarios = esRolEspecial.isFalse();
        /**/
        rolesActivos = esRolInactivo.isFalse();
        rolesInactivos = esRolInactivo.isTrue();
        /**/
        reactivable = rolesOrdinarios.and(rolesInactivos);
        desactivable = rolesOrdinarios.and(rolesActivos);
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of Rol's expressions">
        /**/
        modificables.setLocalizedDescription(ENGLISH, "the role is not a basic configuration role");
        modificables.setLocalizedDescription(SPANISH, "el rol no es un rol de configuración básica del sistema");
        modificables.setLocalizedErrorMessage(ENGLISH, "the role is a basic configuration role; "
            + "it can't be modified or deleted");
        modificables.setLocalizedErrorMessage(SPANISH, "el rol es un rol de configuración básica del sistema; "
            + "no se permite modificarlo ni eliminarlo");
        /**/
        rolesBasicos.setLocalizedCollectionLabel(ENGLISH, "Basic configuration roles");
        rolesBasicos.setLocalizedCollectionLabel(SPANISH, "Roles de configuración básica del sistema");
        rolesBasicos.setLocalizedCollectionShortLabel(ENGLISH, "Basic roles");
        rolesBasicos.setLocalizedCollectionShortLabel(SPANISH, "Roles básicos");
        rolesBasicos.setLocalizedDescription(ENGLISH, "the role is a basic configuration role");
        rolesBasicos.setLocalizedDescription(SPANISH, "el rol es un rol de configuración básica del sistema");
        rolesBasicos.setLocalizedErrorMessage(ENGLISH, "the role is not a basic configuration role");
        rolesBasicos.setLocalizedErrorMessage(SPANISH, "el rol no es un rol de configuración básica del sistema");
        /**/
        rolesAgregados.setLocalizedCollectionLabel(ENGLISH, "User defined roles");
        rolesAgregados.setLocalizedCollectionLabel(SPANISH, "Roles definidos por el usuario");
        rolesAgregados.setLocalizedCollectionShortLabel(ENGLISH, "User defined roles");
        rolesAgregados.setLocalizedCollectionShortLabel(SPANISH, "Roles definidos por el usuario");
        rolesAgregados.setLocalizedDescription(ENGLISH, "the role is a user defined role");
        rolesAgregados.setLocalizedDescription(SPANISH, "el rol es un rol definido por el usuario");
        rolesAgregados.setLocalizedErrorMessage(ENGLISH, "the role is not a user defined role");
        rolesAgregados.setLocalizedErrorMessage(SPANISH, "el rol no es un rol definido por el usuario");
        /**/
        rolesEspeciales.setLocalizedDescription(ENGLISH, "the role is a special role");
        rolesEspeciales.setLocalizedDescription(SPANISH, "el rol es un rol especial");
        rolesEspeciales.setLocalizedErrorMessage(ENGLISH, "the role is not a special role");
        rolesEspeciales.setLocalizedErrorMessage(SPANISH, "el rol no es un rol especial");
        /**/
        rolesOrdinarios.setLocalizedDescription(ENGLISH, "the role is not a special role");
        rolesOrdinarios.setLocalizedDescription(SPANISH, "el rol no es un rol especial");
        rolesOrdinarios.setLocalizedErrorMessage(ENGLISH, "the role is a special role");
        rolesOrdinarios.setLocalizedErrorMessage(SPANISH, "el rol es un rol especial");
        /**/
        rolesActivos.setLocalizedDescription(ENGLISH, "the role is an active role");
        rolesActivos.setLocalizedDescription(SPANISH, "el rol es un rol activo");
        rolesActivos.setLocalizedErrorMessage(ENGLISH, "the role is an inactive role");
        rolesActivos.setLocalizedErrorMessage(SPANISH, "el rol es un rol inactivo");
        /**/
        rolesInactivos.setLocalizedDescription(ENGLISH, "the role is an inactive role");
        rolesInactivos.setLocalizedDescription(SPANISH, "el rol es un rol inactivo");
        rolesInactivos.setLocalizedErrorMessage(ENGLISH, "the role is an active role");
        rolesInactivos.setLocalizedErrorMessage(SPANISH, "el rol es un rol activo");
        /**/
        desactivable.setLocalizedDescription(ENGLISH, "the role is not a special role and is an active role");
        desactivable.setLocalizedDescription(SPANISH, "el rol no es un rol especial y es un rol activo");
//      desactivable.setLocalizedErrorMessage(ENGLISH, "the role is not active");
//      desactivable.setLocalizedErrorMessage(SPANISH, "el rol no está activo");
        /**/
        reactivable.setLocalizedDescription(ENGLISH, "the role is not a special role and is an inactive role");
        reactivable.setLocalizedDescription(SPANISH, "el rol no es un rol especial y es un rol inactivo");
//      reactivable.setLocalizedErrorMessage(ENGLISH, "the role is not inactive");
//      reactivable.setLocalizedErrorMessage(SPANISH, "el rol no está inactivo");
        /**/
        // </editor-fold>
    }

    @Override
    protected void settleFilters() {
        super.settleFilters();
        /**/
        addSelectSegment(rolesBasicos, rolesAgregados);
        /**/
        setUpdateFilter(modificables);
        setDeleteFilter(modificables);
        /**/
    }

    protected Copiar copiar;

    protected Desactivar desactivar;

    protected Reactivar reactivar;

    protected ModificarConjunto modificarConjunto;

    protected SuprimirConjunto suprimirConjunto;

    protected PersonalizarAcceso personalizarAcceso;

    protected ImpersonalizarAcceso impersonalizarAcceso;

    protected PropagarFavoritos propagarFavoritos;

    protected PropagarFiltros propagarFiltros;

    protected PropagarVistas propagarVistas;

    // <editor-fold defaultstate="collapsed" desc="Operations">
    @ProcessOperationClass
    @ConstructionOperationClass(type = Rol.class, onsuccess = OnConstructionOperationSuccess.DISPLAY_NEW_INSTANCE)
    public class Copiar extends ProcessOperation {

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            // <editor-fold defaultstate="collapsed" desc="localization of Copiar's attributes">
            /**/
            setLocalizedLabel(ENGLISH, "copy");
            setLocalizedLabel(SPANISH, "copiar");
            /**/
            setLocalizedDescription(ENGLISH, "copy a role with another code and name; "
                + "the copy includes filters, views, functions, special pages and favorites of the original role, but not users");
            setLocalizedDescription(SPANISH, "copiar un rol con otro código y nombre; "
                + "la copia incluye los filtros, vistas, funciones, páginas especiales y favoritos asociados al rol original, pero no los usuarios");
            /**/
            setLocalizedSuccessMessage(ENGLISH, "the role was successfully copied");
            setLocalizedSuccessMessage(SPANISH, "el rol fue copiado con éxito");
            /**/
            // </editor-fold>
        }

        @InstanceReference
        protected Rol rol;

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
            rol.setLocalizedLabel(ENGLISH, "role");
            rol.setLocalizedLabel(SPANISH, "rol");
            rol.setLocalizedDescription(ENGLISH, "Code of the role you want to copy; "
                + "it is a required field and has no default value");
            rol.setLocalizedDescription(SPANISH, "Código del rol que desea copiar; "
                + "es un dato obligatorio y no tiene valor por omisión");
            /**/
            codigo.setLocalizedLabel(ENGLISH, "code");
            codigo.setLocalizedLabel(SPANISH, "código");
            codigo.setLocalizedDescription(ENGLISH, "Code of the new role produced when copying; "
                + "it is a required field and has no default value");
            codigo.setLocalizedDescription(SPANISH, "Código del nuevo rol que produce la copia; "
                + "es un dato obligatorio y no tiene valor por omisión");
            /**/
            nombre.setLocalizedLabel(ENGLISH, "name");
            nombre.setLocalizedLabel(SPANISH, "nombre");
            nombre.setLocalizedDescription(ENGLISH, "Name of the new role produced when copying; "
                + "it is an optional field; by default, the name of the original role is used");
            nombre.setLocalizedDescription(SPANISH, "Nombre del nuevo rol que produce la copia; "
                + "es un dato opcional; por omisión se utiliza el nombre del rol original");
            // </editor-fold>
        }

    }

    @OperationClass(access = OperationAccess.PRIVATE)
    @ProcessOperationClass(serviceable = Kleenean.FALSE)
    public class Desactivar extends ProcessOperation {

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            // <editor-fold defaultstate="collapsed" desc="localization of Desactivar's attributes">
            setLocalizedLabel(ENGLISH, "deactivate");
            setLocalizedLabel(SPANISH, "desactivar");
            setLocalizedDescription(ENGLISH, "deactivate an active role");
            setLocalizedDescription(SPANISH, "desactivar un rol activo");
            setLocalizedSuccessMessage(ENGLISH, "the role was deactivated");
            setLocalizedSuccessMessage(SPANISH, "el rol fue desactivado");
            // </editor-fold>
        }

        @InstanceReference
        protected Rol rol;

        @Override
        protected void settleParameters() {
            super.settleParameters();
            /**/
            rol.esRolInactivo.setCurrentValue(true);
            /**/
            // <editor-fold defaultstate="collapsed" desc="localization of Desactivar's parameters">
            rol.setLocalizedLabel(ENGLISH, "role");
            rol.setLocalizedLabel(SPANISH, "rol");
            rol.setLocalizedDescription(ENGLISH, "Code of the role you want to deactivate; "
                + "it is a required field and has no default value");
            rol.setLocalizedDescription(SPANISH, "Código del rol que desea desactivar; "
                + "es un dato obligatorio y no tiene valor por omisión");
            // </editor-fold>
        }

    }

    @OperationClass(access = OperationAccess.PRIVATE)
    @ProcessOperationClass(serviceable = Kleenean.FALSE)
    public class Reactivar extends ProcessOperation {

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            // <editor-fold defaultstate="collapsed" desc="localization of Reactivar's attributes">
            setLocalizedLabel(ENGLISH, "reactivate");
            setLocalizedLabel(SPANISH, "reactivar");
            setLocalizedDescription(ENGLISH, "reactivate an inactive role");
            setLocalizedDescription(SPANISH, "reactivar un rol inactivo");
            setLocalizedSuccessMessage(ENGLISH, "the role was reactivated");
            setLocalizedSuccessMessage(SPANISH, "el rol fue reactivado");
            // </editor-fold>
        }

        @InstanceReference
        protected Rol rol;

        @Override
        protected void settleParameters() {
            super.settleParameters();
            /**/
            rol.esRolInactivo.setCurrentValue(false);
            /**/
            // <editor-fold defaultstate="collapsed" desc="localization of Reactivar's parameters">
            rol.setLocalizedLabel(ENGLISH, "role");
            rol.setLocalizedLabel(SPANISH, "rol");
            rol.setLocalizedDescription(ENGLISH, "Code of the role you want to reactivate; "
                + "it is a required field and has no default value");
            rol.setLocalizedDescription(SPANISH, "Código del rol que desea reactivar; "
                + "es un dato obligatorio y no tiene valor por omisión");
            // </editor-fold>
        }

    }

    @OperationClass(confirmation = Kleenean.TRUE)
    @ProcessOperationClass
    public class ModificarConjunto extends ProcessOperation {

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            // <editor-fold defaultstate="collapsed" desc="localization of ModificarConjunto's attributes">
            setLocalizedLabel(ENGLISH, "change segment set");
            setLocalizedLabel(SPANISH, "modificar conjunto");
            setLocalizedDescription(ENGLISH, "modify the segment set of the functions of the role");
            setLocalizedDescription(SPANISH, "modificar el conjunto de segmentos de las funciones asociadas al rol");
            setLocalizedSuccessMessage(ENGLISH, "the segment set of the functions of the role have been successfully changed");
            setLocalizedSuccessMessage(SPANISH, "el conjunto de segmentos de las funciones asociadas al rol se ha modificado con éxito");
            // </editor-fold>
        }

        @InstanceReference
        protected Rol rol;

        @ParameterField(required = Kleenean.TRUE)
        protected ConjuntoSegmento conjuntoSegmento;

        @ParameterField(required = Kleenean.TRUE)
        protected BooleanParameter soloSegmentadas;

        @Override
        protected void settleParameters() {
            super.settleParameters();
            // <editor-fold defaultstate="collapsed" desc="localization of ModificarConjunto's parameters">
            rol.setLocalizedLabel(ENGLISH, "role");
            rol.setLocalizedLabel(SPANISH, "rol");
            rol.setLocalizedDescription(ENGLISH, "Code of the role whose functions you want to modify; "
                + "it is a required field and has no default value");
            rol.setLocalizedDescription(SPANISH, "Código del rol cuyas funciones asociadas desea modificar; "
                + "es un dato obligatorio y no tiene valor por omisión");
            /**/
            conjuntoSegmento.setLocalizedLabel(ENGLISH, "segment set");
            conjuntoSegmento.setLocalizedLabel(SPANISH, "conjunto de segmentos");
            conjuntoSegmento.setLocalizedShortLabel(ENGLISH, "segment set");
            conjuntoSegmento.setLocalizedShortLabel(SPANISH, "conjunto");
            conjuntoSegmento.setLocalizedDescription(ENGLISH, "Code of the new segment set for functions; "
                + "it is a required field and has no default value");
            conjuntoSegmento.setLocalizedDescription(SPANISH, "Código del nuevo conjunto de segmentos para las funciones; "
                + "es un dato obligatorio y no tiene valor por omisión");
            /**/
            soloSegmentadas.setLocalizedLabel(ENGLISH, "only those already segmented");
            soloSegmentadas.setLocalizedLabel(SPANISH, "solo las ya segmentadas");
            soloSegmentadas.setLocalizedDescription(ENGLISH, "Determines if all functions will be modified, "
                + "or only those already authorized to a segment set; "
                + "it is a required field and has no default value");
            soloSegmentadas.setLocalizedDescription(SPANISH, "Determina si serán modificadas todas las funciones, "
                + "o solo las que ya están autorizadas a un conjunto de segmentos; "
                + "es un dato obligatorio y no tiene valor por omisión");
            // </editor-fold>
        }

        protected Check check101;

        @Override
        protected void settleExpressions() {
            super.settleExpressions();
            /**/
            check101 = rol.id.isGreaterOrEqualTo(10000L);
            /**/
            // <editor-fold defaultstate="collapsed" desc="localization of Rol's expressions">
            check101.setLocalizedDescription(ENGLISH, "the role is not a basic configuration role");
            check101.setLocalizedDescription(SPANISH, "el rol no es un rol de configuración básica del sistema");
            check101.setLocalizedErrorMessage(ENGLISH, "the role is a basic configuration role; "
                + "it can't be modified");
            check101.setLocalizedErrorMessage(SPANISH, "el rol es un rol de configuración básica del sistema; "
                + "no se permite modificarlo");
            // </editor-fold>
        }

    }

    @OperationClass(confirmation = Kleenean.TRUE)
    @ProcessOperationClass
    public class SuprimirConjunto extends ProcessOperation {

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            // <editor-fold defaultstate="collapsed" desc="localization of SuprimirConjunto's attributes">
            setLocalizedLabel(ENGLISH, "suppress segment set");
            setLocalizedLabel(SPANISH, "suprimir conjunto");
            setLocalizedDescription(ENGLISH, "suppress the segment set to all the functions of the role");
            setLocalizedDescription(SPANISH, "suprimir el conjunto de segmentos a todas las funciones del rol");
            setLocalizedSuccessMessage(ENGLISH, "all the functions of the role that had a segment set have been successfully changed");
            setLocalizedSuccessMessage(SPANISH, "todas las funciones del rol que tenían un conjunto de segmentos se han modificado con éxito");
            // </editor-fold>
        }

        @InstanceReference
        protected Rol rol;

        @Override
        protected void settleParameters() {
            super.settleParameters();
            // <editor-fold defaultstate="collapsed" desc="localization of SuprimirConjunto's parameters">
            rol.setLocalizedLabel(ENGLISH, "role");
            rol.setLocalizedLabel(SPANISH, "rol");
            rol.setLocalizedDescription(ENGLISH, "Code of the role whose functions you want to modify; "
                + "it is a required field and has no default value");
            rol.setLocalizedDescription(SPANISH, "Código del rol cuyas funciones asociadas desea suprimir; "
                + "es un dato obligatorio y no tiene valor por omisión");
            /**/
            // </editor-fold>
        }

        protected Check check101;

        @Override
        protected void settleExpressions() {
            super.settleExpressions();
            /**/
            check101 = rol.id.isGreaterOrEqualTo(10000L);
            /**/
            // <editor-fold defaultstate="collapsed" desc="localization of Rol's expressions">
            check101.setLocalizedDescription(ENGLISH, "the role is not a basic configuration role");
            check101.setLocalizedDescription(SPANISH, "el rol no es un rol de configuración básica del sistema");
            check101.setLocalizedErrorMessage(ENGLISH, "the role is a basic configuration role; "
                + "it can't be modified");
            check101.setLocalizedErrorMessage(SPANISH, "el rol es un rol de configuración básica del sistema; "
                + "no se permite suprimirlo");
            // </editor-fold>
        }

    }

    @OperationClass(confirmation = Kleenean.TRUE)
    @ProcessOperationClass
    public class PersonalizarAcceso extends ProcessOperation {

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            // <editor-fold defaultstate="collapsed" desc="localization of Personalizar's attributes">
            setLocalizedLabel(ENGLISH, "set personalized access");
            setLocalizedLabel(SPANISH, "dar acceso personalizado");
            setLocalizedDescription(ENGLISH, "set personalized access to all the functions of the role that can be personalized");
            setLocalizedDescription(SPANISH, "dar acceso personalizado para todas las funciones personalizables del rol");
            setLocalizedSuccessMessage(ENGLISH, "all the functions of the role that can be personalized have been successfully changed");
            setLocalizedSuccessMessage(SPANISH, "todas las funciones personalizables del rol se han modificado con éxito");
            // </editor-fold>
        }

        @InstanceReference
        protected Rol rol;

        @Override
        protected void settleParameters() {
            super.settleParameters();
            // <editor-fold defaultstate="collapsed" desc="localization of Personalizar's parameters">
            rol.setLocalizedLabel(ENGLISH, "role");
            rol.setLocalizedLabel(SPANISH, "rol");
            rol.setLocalizedDescription(ENGLISH, "Code of the role whose functions you want to modify; "
                + "it is a required field and has no default value");
            rol.setLocalizedDescription(SPANISH, "Código del rol cuyas funciones asociadas desea modificar; "
                + "es un dato obligatorio y no tiene valor por omisión");
            /**/
            // </editor-fold>
        }

        protected Check check101;

        @Override
        protected void settleExpressions() {
            super.settleExpressions();
            /**/
            check101 = rol.id.isGreaterOrEqualTo(10000L);
            /**/
            // <editor-fold defaultstate="collapsed" desc="localization of Rol's expressions">
            check101.setLocalizedDescription(ENGLISH, "the role is not a basic configuration role");
            check101.setLocalizedDescription(SPANISH, "el rol no es un rol de configuración básica del sistema");
            check101.setLocalizedErrorMessage(ENGLISH, "the role is a basic configuration role; "
                + "it can't be modified");
            check101.setLocalizedErrorMessage(SPANISH, "el rol es un rol de configuración básica del sistema; "
                + "no se permite modificarlo");
            // </editor-fold>
        }

    }

    @OperationClass(confirmation = Kleenean.TRUE)
    @ProcessOperationClass
    public class ImpersonalizarAcceso extends ProcessOperation {

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            // <editor-fold defaultstate="collapsed" desc="localization of Impersonalizar's attributes">
            setLocalizedLabel(ENGLISH, "suppress personalized access");
            setLocalizedLabel(SPANISH, "suprimir acceso personalizado");
            setLocalizedDescription(ENGLISH, "suppress personalized access to all the functions of the role");
            setLocalizedDescription(SPANISH, "suprimir el acceso personalizado a todas las funciones del rol");
            setLocalizedSuccessMessage(ENGLISH, "all the functions of the role that had personalized access have been successfully changed");
            setLocalizedSuccessMessage(SPANISH, "todas las funciones del rol que tenían acceso personalizado se han modificado con éxito");
            // </editor-fold>
        }

        @InstanceReference
        protected Rol rol;

        @Override
        protected void settleParameters() {
            super.settleParameters();
            // <editor-fold defaultstate="collapsed" desc="localization of Impersonalizar's parameters">
            rol.setLocalizedLabel(ENGLISH, "role");
            rol.setLocalizedLabel(SPANISH, "rol");
            rol.setLocalizedDescription(ENGLISH, "Code of the role whose functions you want to modify; "
                + "it is a required field and has no default value");
            rol.setLocalizedDescription(SPANISH, "Código del rol cuyas funciones asociadas desea modificar; "
                + "es un dato obligatorio y no tiene valor por omisión");
            /**/
            // </editor-fold>
        }

        protected Check check101;

        @Override
        protected void settleExpressions() {
            super.settleExpressions();
            /**/
            check101 = rol.id.isGreaterOrEqualTo(10000L);
            /**/
            // <editor-fold defaultstate="collapsed" desc="localization of Rol's expressions">
            check101.setLocalizedDescription(ENGLISH, "the role is not a basic configuration role");
            check101.setLocalizedDescription(SPANISH, "el rol no es un rol de configuración básica del sistema");
            check101.setLocalizedErrorMessage(ENGLISH, "the role is a basic configuration role; "
                + "it can't be modified");
            check101.setLocalizedErrorMessage(SPANISH, "el rol es un rol de configuración básica del sistema; "
                + "no se permite modificarlo");
            // </editor-fold>
        }

    }

    @OperationClass(asynchronous = Kleenean.TRUE)
    @ProcessOperationClass
    public class PropagarFavoritos extends ProcessOperation {

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            // <editor-fold defaultstate="collapsed" desc="localization of PropagarFavoritos's attributes">
            setLocalizedLabel(ENGLISH, "propagate favorites");
            setLocalizedLabel(SPANISH, "propagar favoritos");
            setLocalizedDescription(ENGLISH, "produce a copy of the favorites of the role for each user of the role");
            setLocalizedDescription(SPANISH, "producir una copia de los favoritos asociados al rol para cada usuario asociado al rol");
            setLocalizedSuccessMessage(ENGLISH, "the favorites of the role have been successfully propagated");
            setLocalizedSuccessMessage(SPANISH, "los favoritos asociados al rol se han propagado con éxito");
            // </editor-fold>
        }

        @InstanceReference
        protected Rol rol;

        @Override
        protected void settleParameters() {
            super.settleParameters();
            // <editor-fold defaultstate="collapsed" desc="localization of PropagarFavoritos's parameters">
            rol.setLocalizedLabel(ENGLISH, "role");
            rol.setLocalizedLabel(SPANISH, "rol");
            rol.setLocalizedDescription(ENGLISH, "Code of the role whose favorites you want to propagate; "
                + "it is a required field and has no default value");
            rol.setLocalizedDescription(SPANISH, "Código del rol cuyos favoritos desea propagar; "
                + "es un dato obligatorio y no tiene valor por omisión");
            // </editor-fold>
        }

    }

    @OperationClass(asynchronous = Kleenean.TRUE)
    @ProcessOperationClass
    public class PropagarFiltros extends ProcessOperation {

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            // <editor-fold defaultstate="collapsed" desc="localization of PropagarFiltros's attributes">
            setLocalizedLabel(ENGLISH, "propagate filters");
            setLocalizedLabel(SPANISH, "propagar filtros");
            setLocalizedDescription(ENGLISH, "produce a copy of the filters of the role for each user of the role");
            setLocalizedDescription(SPANISH, "producir una copia de los filtros asociados al rol para cada usuario asociado al rol");
            setLocalizedSuccessMessage(ENGLISH, "the filters of the role have been successfully propagated");
            setLocalizedSuccessMessage(SPANISH, "los filtros asociados al rol se han propagado con éxito");
            // </editor-fold>
        }

        @InstanceReference
        protected Rol rol;

        @Override
        protected void settleParameters() {
            super.settleParameters();
            // <editor-fold defaultstate="collapsed" desc="localization of PropagarFiltros's parameters">
            rol.setLocalizedLabel(ENGLISH, "role");
            rol.setLocalizedLabel(SPANISH, "rol");
            rol.setLocalizedDescription(ENGLISH, "Code of the role whose filters you want to propagate; "
                + "it is a required field and has no default value");
            rol.setLocalizedDescription(SPANISH, "Código del rol cuyos filtros desea propagar; "
                + "es un dato obligatorio y no tiene valor por omisión");
            // </editor-fold>
        }

    }

    @OperationClass(asynchronous = Kleenean.TRUE)
    @ProcessOperationClass
    public class PropagarVistas extends ProcessOperation {

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            // <editor-fold defaultstate="collapsed" desc="localization of PropagarVistas's attributes">
            setLocalizedLabel(ENGLISH, "propagate views");
            setLocalizedLabel(SPANISH, "propagar vistas");
            setLocalizedDescription(ENGLISH, "produce a copy of the views of the role for each user of the role");
            setLocalizedDescription(SPANISH, "producir una copia de las vistas asociadas al rol para cada usuario asociado al rol");
            setLocalizedSuccessMessage(ENGLISH, "the views of the role have been successfully propagated");
            setLocalizedSuccessMessage(SPANISH, "las vistas asociadas al rol se han propagado con éxito");
            // </editor-fold>
        }

        @InstanceReference
        protected Rol rol;

        @Override
        protected void settleParameters() {
            super.settleParameters();
            // <editor-fold defaultstate="collapsed" desc="localization of PropagarVistas's parameters">
            rol.setLocalizedLabel(ENGLISH, "role");
            rol.setLocalizedLabel(SPANISH, "rol");
            rol.setLocalizedDescription(ENGLISH, "Code of the role whose views you want to propagate; "
                + "it is a required field and has no default value");
            rol.setLocalizedDescription(SPANISH, "Código del rol cuyas vistas desea propagar; "
                + "es un dato obligatorio y no tiene valor por omisión");
            // </editor-fold>
        }

    }
    // </editor-fold>

    @Override
    protected void settleOperations() {
        super.settleOperations();
        /**/
        desactivar.addTransition(desactivable, reactivable);
        reactivar.addTransition(reactivable, desactivable);
        /**/
    }

}
