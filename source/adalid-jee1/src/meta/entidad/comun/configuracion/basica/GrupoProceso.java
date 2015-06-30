/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package meta.entidad.comun.configuracion.basica;

import adalid.core.AbstractPersistentEntity;
import adalid.core.ProcessOperation;
import adalid.core.annotations.Allocation;
import adalid.core.annotations.BusinessKey;
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
import adalid.core.annotations.InstanceReference;
import adalid.core.annotations.ManyToOne;
import adalid.core.annotations.NameProperty;
import adalid.core.annotations.PrimaryKey;
import adalid.core.annotations.ProcessOperationClass;
import adalid.core.annotations.PropertyField;
import adalid.core.annotations.VersionProperty;
import adalid.core.enums.Kleenean;
import adalid.core.enums.MasterDetailView;
import adalid.core.enums.Navigability;
import adalid.core.enums.OnDeleteAction;
import adalid.core.enums.OnUpdateAction;
import adalid.core.enums.ResourceGender;
import adalid.core.enums.ResourceType;
import adalid.core.interfaces.Artifact;
import adalid.core.properties.LongProperty;
import adalid.core.properties.StringProperty;
import java.lang.reflect.Field;
import meta.proyecto.base.ProyectoBase;

/**
 * @author Jorge Campins
 */
@EntityClass(independent = Kleenean.TRUE, resourceType = ResourceType.OPERATION, resourceGender = ResourceGender.MASCULINE)
@EntitySelectOperation(enabled = Kleenean.TRUE, rowsLimit = 0)
@EntityInsertOperation(enabled = Kleenean.FALSE)
@EntityUpdateOperation(enabled = Kleenean.FALSE)
@EntityDeleteOperation(enabled = Kleenean.FALSE)
@EntityTableView(enabled = Kleenean.TRUE)
@EntityDetailView(enabled = Kleenean.TRUE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
public class GrupoProceso extends AbstractPersistentEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    @Deprecated
    private GrupoProceso() {
        this(null, null);
    }

    public GrupoProceso(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public LongProperty idGrupoProceso;

    @VersionProperty
    public LongProperty versionGrupoProceso;

    @BusinessKey
    public StringProperty codigoGrupoProceso;

    @NameProperty
    public StringProperty nombreGrupoProceso;

    @DescriptionProperty
    public StringProperty descripcionGrupoProceso;

    public LongProperty idRastroProceso;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(table = Kleenean.TRUE)
    public CondicionEjeFun numeroCondicionEjeFun;

    protected Cancelar cancelar;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        setDefaultLabel("grupo de procesos");
        setDefaultShortLabel("grupo");
        setDefaultCollectionLabel("Grupos de Procesos");
        setDefaultCollectionShortLabel("Grupos");
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        idRastroProceso.setDefaultLabel("rastro de proceso");
    }

    // <editor-fold defaultstate="collapsed" desc="Operations">
    @ProcessOperationClass(overloading = Kleenean.FALSE)
    public class Cancelar extends ProcessOperation {

        @InstanceReference
        @Allocation(maxDepth = 1, maxRound = 0)
        protected GrupoProceso grupoProceso;

    }
    // </editor-fold>

}
