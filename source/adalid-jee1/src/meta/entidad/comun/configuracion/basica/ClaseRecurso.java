/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package meta.entidad.comun.configuracion.basica;

import adalid.core.AbstractPersistentEntity;
import adalid.core.annotations.Allocation;
import adalid.core.annotations.BusinessKey;
import adalid.core.annotations.ColumnField;
import adalid.core.annotations.DescriptionProperty;
import adalid.core.annotations.EntityClass;
import adalid.core.annotations.EntityConsoleView;
import adalid.core.annotations.EntityDeleteOperation;
import adalid.core.annotations.EntityDetailView;
import adalid.core.annotations.EntityInsertOperation;
import adalid.core.annotations.EntitySelectOperation;
import adalid.core.annotations.EntityTableView;
import adalid.core.annotations.EntityTreeView;
import adalid.core.annotations.EntityUpdateOperation;
import adalid.core.annotations.ForeignKey;
import adalid.core.annotations.ManyToOne;
import adalid.core.annotations.NameProperty;
import adalid.core.annotations.PrimaryKey;
import adalid.core.annotations.StringField;
import adalid.core.annotations.VersionProperty;
import adalid.core.enums.Kleenean;
import adalid.core.enums.MasterDetailView;
import adalid.core.enums.Navigability;
import adalid.core.enums.OnDeleteAction;
import adalid.core.enums.OnUpdateAction;
import adalid.core.enums.OperationAccess;
import adalid.core.enums.ResourceGender;
import adalid.core.enums.ResourceType;
import adalid.core.interfaces.Artifact;
import adalid.core.properties.BooleanProperty;
import adalid.core.properties.IntegerProperty;
import adalid.core.properties.LongProperty;
import adalid.core.properties.StringProperty;
import java.lang.reflect.Field;
import meta.proyecto.base.ProyectoBase;

/**
 * @author Jorge Campins
 */
@EntityClass(independent = Kleenean.TRUE, resourceType = ResourceType.CONFIGURATION, resourceGender = ResourceGender.FEMININE)
@EntitySelectOperation(enabled = Kleenean.TRUE, access = OperationAccess.PUBLIC, rowsLimit = 0)
@EntityInsertOperation(enabled = Kleenean.FALSE)
@EntityUpdateOperation(enabled = Kleenean.FALSE)
@EntityDeleteOperation(enabled = Kleenean.FALSE)
@EntityTableView(enabled = Kleenean.TRUE)
@EntityDetailView(enabled = Kleenean.TRUE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
public class ClaseRecurso extends AbstractPersistentEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    @Deprecated
    private ClaseRecurso() {
        this(null, null);
    }

    public ClaseRecurso(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public LongProperty idClaseRecurso;

    @VersionProperty
    public LongProperty versionClaseRecurso;

    @BusinessKey
    @StringField(maxLength = 100)
    public StringProperty codigoClaseRecurso;

    @NameProperty
    public StringProperty nombreClaseRecurso;

    @DescriptionProperty
    public StringProperty descripcionClaseRecurso;

    @StringField(maxLength = 200)
    public StringProperty paqueteClaseRecurso;

    @ColumnField(nullable = Kleenean.FALSE)
    public BooleanProperty esClaseRecursoIndependiente;

    @ColumnField(nullable = Kleenean.FALSE)
    public BooleanProperty esClaseRecursoSinDetalle;

    @ColumnField(nullable = Kleenean.FALSE)
    public BooleanProperty esClaseRecursoConArbol;

    @ColumnField(nullable = Kleenean.FALSE)
    public BooleanProperty esClaseRecursoSegmento;
//
//  public LongProperty idAplicacionWeb;

    public IntegerProperty limiteFilasConsulta;

    public IntegerProperty limiteFilasInforme;

    public IntegerProperty ordenPresentacion;

    @ColumnField(nullable = Kleenean.FALSE)
    public BooleanProperty esClaseRecursoInsertable;

    @ColumnField(nullable = Kleenean.FALSE)
    public BooleanProperty esClaseRecursoModificable;

    @ColumnField(nullable = Kleenean.FALSE)
    public BooleanProperty esClaseRecursoEliminable;

    @ColumnField(nullable = Kleenean.FALSE)
    public BooleanProperty esClaseRecursoExtendida;

    @StringField(maxLength = 20)
    public StringProperty etiquetaHipervinculo;

    @ColumnField(nullable = Kleenean.FALSE)
    public BooleanProperty esEnumeradorConNumero;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @ColumnField(nullable = Kleenean.FALSE)
    @Allocation(maxDepth = 1, maxRound = 0)
    public TipoClaseRecurso numeroTipoClaseRecurso;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @ColumnField(nullable = Kleenean.FALSE)
    @Allocation(maxDepth = 1, maxRound = 0)
    public TipoRecurso numeroTipoRecurso;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @Allocation(maxDepth = 1, maxRound = 0)
    public Funcion idFuncionSeleccion;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @Allocation(maxDepth = 1, maxRound = 0)
    public Pagina idPaginaSeleccion;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @Allocation(maxDepth = 1, maxRound = 0)
    public Pagina idPaginaDetalle;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @Allocation(maxDepth = 1, maxRound = 0)
    public Pagina idPaginaFuncion;

    @StringField(maxLength = 100)
    public StringProperty paginaSeleccion;

    @StringField(maxLength = 100)
    public StringProperty paginaDetalle;

    @StringField(maxLength = 100)
    public StringProperty paginaFuncion;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @Allocation(maxDepth = 1, maxRound = 0)
    public ClaseRecurso idClaseRecursoMaestro;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @Allocation(maxDepth = 1, maxRound = 0)
    public ClaseRecurso idClaseRecursoSegmento;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @Allocation(maxDepth = 1, maxRound = 0)
    public ClaseRecurso idClaseRecursoBase;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        setDefaultLabel("clase de recurso");
        setDefaultShortLabel("clase");
        setDefaultCollectionLabel("Clases de Recurso");
        setDefaultCollectionShortLabel("Clases");
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        esClaseRecursoIndependiente.setInitialValue(false);
        esClaseRecursoIndependiente.setDefaultValue(false);
        esClaseRecursoSinDetalle.setInitialValue(false);
        esClaseRecursoSinDetalle.setDefaultValue(false);
        esClaseRecursoConArbol.setInitialValue(false);
        esClaseRecursoConArbol.setDefaultValue(false);
        esClaseRecursoSegmento.setInitialValue(false);
        esClaseRecursoSegmento.setDefaultValue(false);
        numeroTipoClaseRecurso.setInitialValue(numeroTipoClaseRecurso.TABLA);
        numeroTipoClaseRecurso.setDefaultValue(numeroTipoClaseRecurso.TABLA);
        esClaseRecursoInsertable.setInitialValue(true);
        esClaseRecursoInsertable.setDefaultValue(true);
        esClaseRecursoModificable.setInitialValue(true);
        esClaseRecursoModificable.setDefaultValue(true);
        esClaseRecursoEliminable.setInitialValue(true);
        esClaseRecursoEliminable.setDefaultValue(true);
        esClaseRecursoExtendida.setInitialValue(false);
        esClaseRecursoExtendida.setDefaultValue(false);
        esEnumeradorConNumero.setInitialValue(false);
        esEnumeradorConNumero.setDefaultValue(false);
        idFuncionSeleccion.setDefaultLabel("función de selección");
//      idFuncionSeleccion.setDefaultShortLabel("función de selección");
        idPaginaSeleccion.setDefaultLabel("página de selección");
//      idPaginaSeleccion.setDefaultShortLabel("página de selección");
        idPaginaDetalle.setDefaultLabel("página de detalle");
//      idPaginaDetalle.setDefaultShortLabel("página de detalle");
        idClaseRecursoMaestro.setDefaultLabel("clase de recurso maestro");
        idClaseRecursoMaestro.setDefaultShortLabel("clase de maestro");
        idClaseRecursoSegmento.setDefaultLabel("clase de recurso segmento");
        idClaseRecursoSegmento.setDefaultShortLabel("clase de segmento");
        idClaseRecursoBase.setDefaultLabel("clase de recurso base");
        idClaseRecursoBase.setDefaultShortLabel("clase base");
    }

}
