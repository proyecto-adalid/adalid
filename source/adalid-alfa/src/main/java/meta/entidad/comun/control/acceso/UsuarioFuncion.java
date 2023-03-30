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
@EntityTableView(enabled = Kleenean.TRUE)
@EntityDetailView(enabled = Kleenean.FALSE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
public class UsuarioFuncion extends AbstractPersistentEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public UsuarioFuncion(Artifact declaringArtifact, Field declaringField) {
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
        // <editor-fold defaultstate="collapsed" desc="localization of UsuarioFuncion's attributes">
        /**/
        setLocalizedLabel(ENGLISH, "user/function association");
        setLocalizedLabel(SPANISH, "asociación Usuario/Función");
        setLocalizedCollectionLabel(ENGLISH, "User/Function Associations");
        setLocalizedCollectionLabel(SPANISH, "Asociaciones Usuario/Función");
        /**/
        setLocalizedCollectionLabel(ENGLISH, usuario, "Functions by User");
        setLocalizedCollectionLabel(SPANISH, usuario, "Funciones por Usuario");
        setLocalizedCollectionShortLabel(ENGLISH, usuario, "Functions");
        setLocalizedCollectionShortLabel(SPANISH, usuario, "Funciones");
        /**/
        setLocalizedDescription(ENGLISH, "Each instance of " + b("User/Function Associations") + " represents a "
            + "function associated with an application user."
            + "");
        setLocalizedDescription(SPANISH, "Cada instancia de " + b("Asociaciones Usuario/Función") + " representa una "
            + "función asociada con un usuario de la aplicación."
            + "");
        /**/
        setLocalizedShortDescription(ENGLISH, "function associated with an application user");
        setLocalizedShortDescription(SPANISH, "función asociada con un usuario de la aplicación");
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
    @PropertyField(table = Kleenean.TRUE, report = Kleenean.TRUE)
    public TipoFuncion tipoFuncion;

    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(table = Kleenean.TRUE, report = Kleenean.TRUE)
    public ClaseRecurso claseRecurso;

    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(table = Kleenean.TRUE, report = Kleenean.TRUE)
    public Rol rol;

    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(table = Kleenean.TRUE, report = Kleenean.TRUE)
    public ConjuntoSegmento conjuntoSegmento;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(table = Kleenean.TRUE, report = Kleenean.TRUE)
    public BooleanProperty esAccesoPersonalizado;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(table = Kleenean.TRUE, report = Kleenean.TRUE)
    public BooleanProperty esTarea;

    @Override
    protected void settleProperties() {
        super.settleProperties();
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of UsuarioFuncion's properties">
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
        esAccesoPersonalizado.setLocalizedShortLabel(ENGLISH, "personalized");
        esAccesoPersonalizado.setLocalizedShortLabel(SPANISH, "personalizado");
        /**/
        esTarea.setLocalizedLabel(ENGLISH, "task");
        esTarea.setLocalizedLabel(SPANISH, "tarea");
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
