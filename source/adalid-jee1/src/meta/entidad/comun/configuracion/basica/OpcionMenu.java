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
import adalid.core.annotations.InactiveIndicator;
import adalid.core.annotations.InstanceReference;
import adalid.core.annotations.ManyToOne;
import adalid.core.annotations.NameProperty;
import adalid.core.annotations.OperationClass;
import adalid.core.annotations.ParentProperty;
import adalid.core.annotations.PrimaryKey;
import adalid.core.annotations.ProcessOperationClass;
import adalid.core.annotations.PropertyField;
import adalid.core.annotations.StringField;
import adalid.core.annotations.UrlProperty;
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
@EntityTableView(enabled = Kleenean.FALSE)
@EntityDetailView(enabled = Kleenean.FALSE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
//@EntityTriggers(afterCheck = Kleenean.TRUE)
public class OpcionMenu extends AbstractPersistentEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    @Deprecated
    private OpcionMenu() {
        this(null, null);
    }

    public OpcionMenu(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public LongProperty idOpcionMenu;

    @VersionProperty
    public LongProperty versionOpcionMenu;

    @BusinessKey
    @StringField(maxLength = 200)
    public StringProperty codigoOpcionMenu;

    @NameProperty
    @StringField(maxLength = 200)
    public StringProperty nombreOpcionMenu;

    @DescriptionProperty
    public StringProperty descripcionOpcionMenu;

    @UrlProperty
    public StringProperty urlOpcionMenu;

    public IntegerProperty secuenciaOpcionMenu;

    @StringField(maxLength = 30)
    public StringProperty claveOpcionMenu;

    @InactiveIndicator
    @PropertyField(table = Kleenean.FALSE)
    @ColumnField(nullable = Kleenean.FALSE)
    public BooleanProperty esOpcionMenuInactiva;

    @ColumnField(nullable = Kleenean.FALSE)
    public BooleanProperty esOpcionMenuSincronizada;

    @ColumnField(nullable = Kleenean.FALSE)
    public BooleanProperty esEspecial;

    @ParentProperty
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(table = Kleenean.TRUE)
    @Allocation(maxDepth = 1, maxRound = 0)
    public OpcionMenu idOpcionMenuSuperior;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @Allocation(maxDepth = 1, maxRound = 0)
    public TipoNodo numeroTipoNodo;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @Allocation(maxDepth = 1, maxRound = 0)
    public NivelOpcionMenu numeroNivelOpcionMenu;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        setDefaultLabel("opción de menú");
        setDefaultShortLabel("opción");
        setDefaultCollectionLabel("Opciones de Menú");
        setDefaultCollectionShortLabel("Opciones");
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        esOpcionMenuInactiva.setInitialValue(false);
        esOpcionMenuInactiva.setDefaultValue(false);
        esOpcionMenuSincronizada.setInitialValue(false);
        esOpcionMenuSincronizada.setDefaultValue(false);
        esEspecial.setInitialValue(false);
        esEspecial.setDefaultValue(false);
        idOpcionMenuSuperior.setDefaultLabel("opción superior");
//      idOpcionMenuSuperior.setDefaultShortLabel("superior");
//
//      ArtifactWrapper.getSomeLabel() obtiene el valor por omisión de las etiquetas de código, nombre, etc.
//
//      idOpcionMenuSuperior.codigoOpcionMenu.setDefaultLabel("código opción superior");
//      idOpcionMenuSuperior.codigoOpcionMenu.setDefaultShortLabel("código opción superior");
//      idOpcionMenuSuperior.nombreOpcionMenu.setDefaultLabel("nombre opción superior");
//      idOpcionMenuSuperior.nombreOpcionMenu.setDefaultShortLabel("nombre opción superior");
    }

    // <editor-fold defaultstate="collapsed" desc="Operations">
    protected Desactivar desactivar;

    @ProcessOperationClass(overloading = Kleenean.FALSE)
    public class Desactivar extends ProcessOperation {

        @InstanceReference
        @Allocation(maxDepth = 1, maxRound = 0)
        protected OpcionMenu opcionMenu;

    }

    protected Reactivar reactivar;

    @ProcessOperationClass(overloading = Kleenean.FALSE)
    public class Reactivar extends ProcessOperation {

        @InstanceReference
        @Allocation(maxDepth = 1, maxRound = 0)
        protected OpcionMenu opcionMenu;

    }

    protected Reconstruir reconstruir;

    @OperationClass(asynchronous = Kleenean.TRUE)
    @ProcessOperationClass(overloading = Kleenean.FALSE)
    public class Reconstruir extends ProcessOperation {
    }
    // </editor-fold>

}
