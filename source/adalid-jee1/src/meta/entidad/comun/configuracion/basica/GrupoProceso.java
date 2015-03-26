/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package meta.entidad.comun.configuracion.basica;

import adalid.core.*;
import adalid.core.annotations.*;
import adalid.core.enums.*;
import adalid.core.interfaces.*;
import adalid.core.properties.*;
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
        setDefaultCollectionLabel("grupos de procesos");
        setDefaultCollectionShortLabel("grupos");
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        idRastroProceso.setDefaultLabel("rastro de proceso");
    }

    // <editor-fold defaultstate="collapsed" desc="Operations">
    public class Cancelar extends ProcessOperation {

        @InstanceReference
        @Allocation(maxDepth = 1, maxRound = 0)
        protected GrupoProceso grupoProceso;

    }
    // </editor-fold>

}
