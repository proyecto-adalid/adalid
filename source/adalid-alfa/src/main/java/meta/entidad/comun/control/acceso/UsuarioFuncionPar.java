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
import adalid.core.properties.*;
import java.lang.reflect.Field;
import meta.entidad.comun.configuracion.basica.ClaseRecurso;
import meta.entidad.comun.configuracion.basica.Funcion;
import meta.entidad.comun.configuracion.basica.FuncionParametro;
import meta.entidad.comun.configuracion.basica.TipoFuncion;

/**
 * @author Jorge Campins
 */
@EntityClass(base = Kleenean.FALSE, independent = Kleenean.TRUE, resourceType = ResourceType.OPERATION, resourceGender = ResourceGender.FEMININE)
@EntityCodeGen(bpl = Kleenean.FALSE, bws = Kleenean.FALSE, fws = Kleenean.FALSE, gui = Kleenean.TRUE, sql = Kleenean.FALSE)
@EntitySelectOperation(enabled = Kleenean.TRUE)
@EntityInsertOperation(enabled = Kleenean.FALSE)
@EntityUpdateOperation(enabled = Kleenean.FALSE)
@EntityDeleteOperation(enabled = Kleenean.FALSE)
@EntityTableView(enabled = Kleenean.TRUE, responsiveMode = TableResponsiveMode.PRIORITY)
@EntityDetailView(enabled = Kleenean.TRUE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
public class UsuarioFuncionPar extends AbstractPersistentEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public UsuarioFuncionPar(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @Override
    protected void addAllocationStrings() {
        super.addAllocationStrings();
        super.addAllocationStrings("usuario.grupo");
    }

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of UsuarioFuncionPar's attributes">
        /**/
        setLocalizedLabel(ENGLISH, "user/function/parameter association");
        setLocalizedLabel(SPANISH, "asociación Usuario/Función/Parámetro");
        setLocalizedCollectionLabel(ENGLISH, "User/Function/Parameter Associations");
        setLocalizedCollectionLabel(SPANISH, "Asociaciones Usuario/Función/Parámetro");
        /**/
        setLocalizedCollectionLabel(ENGLISH, usuario, "Function Parameters by User");
        setLocalizedCollectionLabel(SPANISH, usuario, "Parámetros de Funciones por Usuario");
        setLocalizedCollectionShortLabel(ENGLISH, usuario, "Parameters");
        setLocalizedCollectionShortLabel(SPANISH, usuario, "Parámetros");
        /**/
        setLocalizedDescription(ENGLISH, "Each instance of " + b("User/Function/Parameter Associations") + " represents a "
            + "parameter of a function associated with an application user."
            + "");
        setLocalizedDescription(SPANISH, "Cada instancia de " + b("Asociaciones Usuario/Función/Parámetro") + " representa un "
            + "parámetro de una función asociada con un usuario de la aplicación."
            + "");
        /**/
        setLocalizedShortDescription(ENGLISH, "parameter of a function associated with an application user");
        setLocalizedShortDescription(SPANISH, "parámetro de una función asociada con un usuario de la aplicación");
        /**/
        // </editor-fold>
        /**/
    }

    @PrimaryKey
    public LongProperty id;

    @VersionProperty
    public LongProperty version;

    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(table = Kleenean.TRUE, report = Kleenean.TRUE)
    public Usuario usuario;

    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(table = Kleenean.TRUE, report = Kleenean.TRUE)
    public Funcion funcion;

    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(responsivePriority = 6, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public TipoFuncion tipoFuncion;

    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(responsivePriority = 6, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public ClaseRecurso claseRecurso;

    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(responsivePriority = 6, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public Rol rol;

    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(responsivePriority = 6, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public ConjuntoSegmento conjuntoSegmento;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(responsivePriority = 6, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public BooleanProperty esAccesoPersonalizado;

    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE, quickAdding = QuickAddingFilter.MISSING)
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(table = Kleenean.TRUE, report = Kleenean.TRUE)
    public FuncionParametro funcionParametro;

    @Override
    protected void settleProperties() {
        super.settleProperties();
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of UsuarioFuncionPar's properties">
        /**/
        usuario.setLocalizedLabel(ENGLISH, "user");
        usuario.setLocalizedLabel(SPANISH, "usuario");
        /**/
        funcion.setLocalizedLabel(ENGLISH, "function");
        funcion.setLocalizedLabel(SPANISH, "función");
        /**/
        tipoFuncion.setLocalizedLabel(ENGLISH, "function type");
        tipoFuncion.setLocalizedLabel(SPANISH, "tipo de función");
        tipoFuncion.setLocalizedShortLabel(ENGLISH, "type");
        tipoFuncion.setLocalizedShortLabel(SPANISH, "tipo");
        /**/
        claseRecurso.setLocalizedLabel(ENGLISH, "resource class");
        claseRecurso.setLocalizedLabel(SPANISH, "clase de recurso");
        claseRecurso.setLocalizedShortLabel(ENGLISH, "class");
        claseRecurso.setLocalizedShortLabel(SPANISH, "clase");
        /**/
        rol.setLocalizedLabel(ENGLISH, "role");
        rol.setLocalizedLabel(SPANISH, "rol");
        /**/
        conjuntoSegmento.setLocalizedLabel(ENGLISH, "segment set");
        conjuntoSegmento.setLocalizedLabel(SPANISH, "conjunto de segmentos");
        conjuntoSegmento.setLocalizedShortLabel(ENGLISH, "set");
        conjuntoSegmento.setLocalizedShortLabel(SPANISH, "conjunto");
        /**/
        esAccesoPersonalizado.setLocalizedLabel(ENGLISH, "personalized access");
        esAccesoPersonalizado.setLocalizedLabel(SPANISH, "acceso personalizado");
        esAccesoPersonalizado.setLocalizedColumnHeader(ENGLISH, "personalized", "access");
        esAccesoPersonalizado.setLocalizedColumnHeader(SPANISH, "acceso", "personalizado");
        /**/
        funcionParametro.setLocalizedLabel(ENGLISH, "function parameter");
        funcionParametro.setLocalizedLabel(SPANISH, "parámetro de función");
        funcionParametro.setLocalizedShortLabel(ENGLISH, "parameter");
        funcionParametro.setLocalizedShortLabel(SPANISH, "parámetro");
        /**/
        // </editor-fold>
        /**/
    }

    @Override
    protected void settleLinks() {
        super.settleLinks();
        linkForeignSegmentProperty(usuario.grupo);
    }

}
