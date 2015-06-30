/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package meta.entidad.comun.auditoria;

import adalid.core.AbstractPersistentEntity;
import adalid.core.annotations.Allocation;
import adalid.core.annotations.ColumnField;
import adalid.core.annotations.EntityClass;
import adalid.core.annotations.EntityConsoleView;
import adalid.core.annotations.EntityDeleteOperation;
import adalid.core.annotations.EntityDetailView;
import adalid.core.annotations.EntityInsertOperation;
import adalid.core.annotations.EntitySelectOperation;
import adalid.core.annotations.EntityTableView;
import adalid.core.annotations.EntityTreeView;
import adalid.core.annotations.EntityTriggers;
import adalid.core.annotations.EntityUpdateOperation;
import adalid.core.annotations.EntityWarnings;
import adalid.core.annotations.ForeignKey;
import adalid.core.annotations.ManyToOne;
import adalid.core.annotations.OwnerProperty;
import adalid.core.annotations.PrimaryKey;
import adalid.core.annotations.PropertyField;
import adalid.core.annotations.SegmentProperty;
import adalid.core.annotations.StringField;
import adalid.core.enums.Kleenean;
import adalid.core.enums.MasterDetailView;
import adalid.core.enums.Navigability;
import adalid.core.enums.OnDeleteAction;
import adalid.core.enums.OnUpdateAction;
import adalid.core.enums.ResourceGender;
import adalid.core.enums.ResourceType;
import adalid.core.enums.SortOption;
import adalid.core.enums.SpecialTemporalValue;
import adalid.core.interfaces.Artifact;
import adalid.core.properties.LongProperty;
import adalid.core.properties.StringProperty;
import adalid.core.properties.TimestampProperty;
import java.lang.reflect.Field;
import meta.entidad.comun.configuracion.basica.CondicionEjeFun;
import meta.entidad.comun.configuracion.basica.Funcion;
import meta.entidad.comun.control.acceso.Usuario;
import meta.entidad.comun.operacion.basica.RecursoValor;
import meta.proyecto.base.ProyectoBase;

/**
 * @author Jorge Campins
 */
@EntityClass(independent = Kleenean.TRUE, resourceType = ResourceType.OPERATION, resourceGender = ResourceGender.MASCULINE)
@EntitySelectOperation(enabled = Kleenean.TRUE, sortOption = SortOption.DESC)
@EntityInsertOperation(enabled = Kleenean.FALSE)
@EntityUpdateOperation(enabled = Kleenean.FALSE)
@EntityDeleteOperation(enabled = Kleenean.FALSE)
@EntityTableView(enabled = Kleenean.TRUE)
@EntityDetailView(enabled = Kleenean.TRUE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
@EntityWarnings(enabled = Kleenean.FALSE)
@EntityTriggers(afterValue = Kleenean.TRUE)
public class RastroFuncion extends AbstractPersistentEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    @Deprecated
    private RastroFuncion() {
        this(null, null);
    }

    public RastroFuncion(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public LongProperty idRastroFuncion;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(table = Kleenean.TRUE, report = Kleenean.TRUE, search = Kleenean.FALSE)
    public TimestampProperty fechaHoraEjecucion;

    @OwnerProperty
    @SegmentProperty
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(hidden = Kleenean.TRUE)
    @Allocation(maxDepth = 1, maxRound = 0)
    public Usuario idUsuario;

    @PropertyField(table = Kleenean.TRUE, search = Kleenean.TRUE, report = Kleenean.TRUE)
    @StringField(maxLength = 30)
    public StringProperty codigoUsuario;

    @PropertyField(table = Kleenean.TRUE, search = Kleenean.TRUE, report = Kleenean.TRUE)
    @StringField(maxLength = 100)
    public StringProperty nombreUsuario;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(table = Kleenean.TRUE, report = Kleenean.TRUE)
    @Allocation(maxDepth = 1, maxRound = 0)
    public Funcion idFuncion;

    @PropertyField(hidden = Kleenean.TRUE)
    public LongProperty idClaseRecursoValor;

    @Allocation(maxDepth = 1, maxRound = 0)
    @ColumnField(nullable = Kleenean.TRUE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(table = Kleenean.TRUE, detail = Kleenean.TRUE, search = Kleenean.FALSE, report = Kleenean.FALSE, heading = Kleenean.FALSE)
    public RecursoValor recursoValor;

    @PropertyField(hidden = Kleenean.TRUE)
    public LongProperty idRecurso;

    @PropertyField(hidden = Kleenean.TRUE)
    public LongProperty versionRecurso;

    @PropertyField(table = Kleenean.FALSE, detail = Kleenean.FALSE, search = Kleenean.TRUE, report = Kleenean.TRUE)
    public StringProperty codigoRecurso;

    @PropertyField(table = Kleenean.FALSE, detail = Kleenean.FALSE, search = Kleenean.TRUE, report = Kleenean.TRUE)
    public StringProperty nombreRecurso;

    @PropertyField(hidden = Kleenean.TRUE)
    public LongProperty idPropietarioRecurso;

    @PropertyField(hidden = Kleenean.TRUE)
    public LongProperty idSegmentoRecurso;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(table = Kleenean.TRUE, report = Kleenean.TRUE)
    @Allocation(maxDepth = 1, maxRound = 0)
    public CondicionEjeFun numeroCondicionEjeFun;

//  @PropertyField(table = Kleenean.TRUE, report = Kleenean.TRUE)
    public StringProperty descripcionError;

    @PropertyField(hidden = Kleenean.TRUE)
    @StringField(maxLength = 100)
    public StringProperty paginaFuncion;

    @PropertyField(hidden = Kleenean.TRUE)
    @StringField(maxLength = 100)
    public StringProperty paginaRecurso;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        setDefaultLabel("rastro de función");
        setDefaultShortLabel("rastro");
        setDefaultCollectionLabel("Rastros de Función");
        setDefaultCollectionShortLabel("Rastros");
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        codigoUsuario.setDefaultLabel("usuario");
        codigoUsuario.setDefaultTooltip("usuario");
        nombreUsuario.setDefaultLabel("nombre del usuario");
        nombreUsuario.setDefaultTooltip("nombre del usuario");
        recursoValor.setDefaultLabel("recurso");
        recursoValor.setDefaultTooltip("recurso");
        recursoValor.codigo.setDefaultLabel("recurso");
        recursoValor.codigo.setDefaultTooltip("recurso");
        recursoValor.nombre.setDefaultLabel("nombre del recurso");
        recursoValor.nombre.setDefaultTooltip("nombre del recurso");
        codigoRecurso.setDefaultLabel("recurso");
        codigoRecurso.setDefaultTooltip("recurso");
        nombreRecurso.setDefaultLabel("nombre del recurso");
        nombreRecurso.setDefaultTooltip("nombre del recurso");
        fechaHoraEjecucion.setInitialValue(SpecialTemporalValue.CURRENT_TIMESTAMP);
        fechaHoraEjecucion.setDefaultValue(SpecialTemporalValue.CURRENT_TIMESTAMP);
        fechaHoraEjecucion.setDefaultLabel("fecha y hora");
        numeroCondicionEjeFun.setInitialValue(numeroCondicionEjeFun.EJECUTADO_SIN_ERRORES);
        numeroCondicionEjeFun.setDefaultValue(numeroCondicionEjeFun.EJECUTADO_SIN_ERRORES);
        descripcionError.setDefaultLabel("mensaje");
    }

//  protected Tab tab1, tab2;
//
//  @Override
//  protected void settleTabs() {
//      super.settleTabs();
//      tab1.setDefaultLabel("general");
//      tab1.newTabField(fechaHoraEjecucion, idUsuario, idFuncion, numeroCondicionEjeFun, descripcionError);
//      tab2.setDefaultLabel("recurso");
//      tab2.newTabField(idRecurso, versionRecurso, codigoRecurso, nombreRecurso, idPropietarioRecurso, idSegmentoRecurso);
//  }
//
}
