/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package meta.entidad.comun.control.prueba;

import adalid.core.ProcessOperation;
import adalid.core.annotations.Allocation;
import adalid.core.annotations.BusinessKey;
import adalid.core.annotations.ColumnField;
import adalid.core.annotations.DescriptionProperty;
import adalid.core.annotations.EntityClass;
import adalid.core.annotations.EntityConsoleView;
import adalid.core.annotations.EntityDataGen;
import adalid.core.annotations.EntityDeleteOperation;
import adalid.core.annotations.EntityDetailView;
import adalid.core.annotations.EntityInsertOperation;
import adalid.core.annotations.EntityReferenceSearch;
import adalid.core.annotations.EntitySelectOperation;
import adalid.core.annotations.EntityTableView;
import adalid.core.annotations.EntityTreeView;
import adalid.core.annotations.EntityUpdateOperation;
import adalid.core.annotations.InstanceReference;
import adalid.core.annotations.NameProperty;
import adalid.core.annotations.ProcessOperationClass;
import adalid.core.annotations.PropertyField;
import adalid.core.enums.DefaultCondition;
import adalid.core.enums.Kleenean;
import adalid.core.enums.ResourceGender;
import adalid.core.enums.ResourceType;
import adalid.core.enums.SearchType;
import adalid.core.interfaces.Artifact;
import adalid.core.properties.BooleanProperty;
import adalid.core.properties.StringProperty;
import java.lang.reflect.Field;
import meta.entidad.base.PersistentEntityBase;
import meta.proyecto.base.ProyectoBase;

/**
 * @author Jorge Campins
 */
@EntityClass(independent = Kleenean.TRUE, resourceType = ResourceType.TESTING, resourceGender = ResourceGender.MASCULINE)
@EntitySelectOperation(enabled = Kleenean.TRUE)
@EntityInsertOperation(enabled = Kleenean.TRUE)
@EntityUpdateOperation(enabled = Kleenean.TRUE)
@EntityDeleteOperation(enabled = Kleenean.TRUE)
@EntityTableView(enabled = Kleenean.TRUE, inserts = Kleenean.FALSE)
@EntityDetailView(enabled = Kleenean.TRUE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.TRUE)
@EntityDataGen(start = 1, stop = 10, step = 0)
@EntityReferenceSearch(searchType = SearchType.LIST)
public class AmbientePrueba extends PersistentEntityBase {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    @Deprecated
    private AmbientePrueba() {
        this(null, null);
    }

    public AmbientePrueba(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        setDefaultLabel("ambiente de prueba");
        setDefaultShortLabel("ambiente");
        setDefaultCollectionLabel("Ambientes de Prueba");
        setDefaultCollectionShortLabel("Ambientes");
    }

    @BusinessKey
    public StringProperty codigo;

    @NameProperty
    public StringProperty nombre;

    @DescriptionProperty
    @PropertyField(create = Kleenean.TRUE)
    public StringProperty descripcion;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.TRUE, defaultCondition = DefaultCondition.IF_NULL_ON_INSERT)
    BooleanProperty actual;

    @Override
    protected void settleProperties() {
        super.settleProperties();
        actual.setInitialValue(false);
        actual.setDefaultValue(false);
    }

    protected DesignarActual designarActual;

    @ProcessOperationClass(overloading = Kleenean.FALSE)
    public class DesignarActual extends ProcessOperation {

        @InstanceReference
        @Allocation(maxDepth = 1, maxRound = 0)
        protected AmbientePrueba ambiente;

    }

}
