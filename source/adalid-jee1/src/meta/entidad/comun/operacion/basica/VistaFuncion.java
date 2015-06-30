/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package meta.entidad.comun.operacion.basica;

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
import adalid.core.annotations.OwnerProperty;
import adalid.core.annotations.PrimaryKey;
import adalid.core.annotations.PropertyField;
import adalid.core.annotations.StringField;
import adalid.core.annotations.VersionProperty;
import adalid.core.enums.DefaultCondition;
import adalid.core.enums.Kleenean;
import adalid.core.enums.MasterDetailView;
import adalid.core.enums.Navigability;
import adalid.core.enums.OnDeleteAction;
import adalid.core.enums.OnUpdateAction;
import adalid.core.enums.ResourceGender;
import adalid.core.enums.ResourceType;
import adalid.core.interfaces.Artifact;
import adalid.core.properties.BooleanProperty;
import adalid.core.properties.IntegerProperty;
import adalid.core.properties.LongProperty;
import adalid.core.properties.StringProperty;
import java.lang.reflect.Field;
import meta.entidad.comun.configuracion.basica.Funcion;
import meta.entidad.comun.control.acceso.Usuario;
import meta.proyecto.base.ProyectoBase;

/**
 * @author Jorge Campins
 */
@EntityClass(independent = Kleenean.FALSE, resourceType = ResourceType.OPERATION, resourceGender = ResourceGender.FEMININE)
@EntitySelectOperation(enabled = Kleenean.FALSE)
@EntityInsertOperation(enabled = Kleenean.TRUE)
@EntityUpdateOperation(enabled = Kleenean.TRUE)
@EntityDeleteOperation(enabled = Kleenean.TRUE)
@EntityTableView(enabled = Kleenean.TRUE, heading = Kleenean.FALSE)
@EntityDetailView(enabled = Kleenean.TRUE, heading = Kleenean.FALSE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
public class VistaFuncion extends AbstractPersistentEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    @Deprecated
    private VistaFuncion() {
        this(null, null);
    }

    public VistaFuncion(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public LongProperty id;

    @VersionProperty
    public LongProperty version;

    @BusinessKey
    @StringField(maxLength = 100)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.FALSE, defaultCondition = DefaultCondition.UNCONDITIONALLY_ON_INSERT)
    public StringProperty codigo;

    @NameProperty
    public StringProperty nombre;

    @DescriptionProperty
    public StringProperty descripcion;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.BIDIRECTIONAL, view = MasterDetailView.TABLE_AND_DETAIL)
    @ColumnField(nullable = Kleenean.FALSE)
    @Allocation(maxDepth = 2, maxRound = 0)
    public Funcion funcion;

    @OwnerProperty
    @ForeignKey(onDelete = OnDeleteAction.CASCADE, onUpdate = OnUpdateAction.CASCADE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @ColumnField(nullable = Kleenean.FALSE)
    @Allocation(maxDepth = 1, maxRound = 0)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, search = Kleenean.TRUE, table = Kleenean.TRUE)
    public Usuario propietario;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(hidden = Kleenean.TRUE)
    public BooleanProperty publica;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(hidden = Kleenean.TRUE)
    public BooleanProperty valida;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(hidden = Kleenean.TRUE)
    public IntegerProperty secuencia;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        setDefaultLabel("vista");
        setDefaultShortLabel("vista");
        setDefaultCollectionLabel("Vistas");
        setDefaultCollectionShortLabel("Vistas");
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        codigo.setDefaultValue(funcion.idDominio.codigoDominio.concat("-").concat(id.toCharString()));
        publica.setInitialValue(false);
        publica.setDefaultValue(false);
        valida.setInitialValue(false);
        valida.setDefaultValue(false);
        secuencia.setInitialValue(0);
        secuencia.setDefaultValue(0);
    }

}
