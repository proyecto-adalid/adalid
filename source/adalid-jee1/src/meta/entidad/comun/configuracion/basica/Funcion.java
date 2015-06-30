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
public class Funcion extends AbstractPersistentEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    @Deprecated
    private Funcion() {
        this(null, null);
    }

    public Funcion(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public LongProperty idFuncion;

    @VersionProperty
    public LongProperty versionFuncion;

    @BusinessKey
    @StringField(maxLength = 200)
    public StringProperty codigoFuncion;

    @NameProperty
    @StringField(maxLength = 200)
    public StringProperty nombreFuncion;

    @DescriptionProperty
    public StringProperty descripcionFuncion;

    public StringProperty clausulaWhere;

    public StringProperty clausulaOrder;

    @ColumnField(nullable = Kleenean.FALSE)
    public BooleanProperty esPublica;

    @ColumnField(nullable = Kleenean.FALSE)
    public BooleanProperty esProgramatica;

    @ColumnField(nullable = Kleenean.FALSE)
    public BooleanProperty esProtegida;

    @ColumnField(nullable = Kleenean.FALSE)
    public BooleanProperty esPersonalizable;

    @ColumnField(nullable = Kleenean.FALSE)
    public BooleanProperty esSegmentable;

    @ColumnField(nullable = Kleenean.FALSE)
    public BooleanProperty esSupervisable;

    @ColumnField(nullable = Kleenean.FALSE)
    public BooleanProperty esHeredada;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @ColumnField(nullable = Kleenean.FALSE)
    @Allocation(maxDepth = 1, maxRound = 0)
    public TipoFuncion numeroTipoFuncion;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @ColumnField(nullable = Kleenean.FALSE)
    @Allocation(maxDepth = 1, maxRound = 0)
    public TipoRastroFun numeroTipoRastroFun;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @Allocation(maxDepth = 1, maxRound = 0)
    public Dominio idDominio;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.CASCADE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @Allocation(maxDepth = 1, maxRound = 0)
    public GrupoProceso idGrupoProceso;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        setDefaultLabel("función");
//      setDefaultShortLabel("función");
        setDefaultCollectionLabel("Funciones");
//      setDefaultCollectionShortLabel("Funciones");
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        numeroTipoRastroFun.setInitialValue(numeroTipoRastroFun.NINGUNO);
        numeroTipoRastroFun.setDefaultValue(numeroTipoRastroFun.NINGUNO);
        esPublica.setInitialValue(false);
        esPublica.setDefaultValue(false);
        esProgramatica.setInitialValue(false);
        esProgramatica.setDefaultValue(false);
        esProtegida.setInitialValue(false);
        esProtegida.setDefaultValue(false);
        esPersonalizable.setInitialValue(false);
        esPersonalizable.setDefaultValue(false);
        esSegmentable.setInitialValue(false);
        esSegmentable.setDefaultValue(false);
        esSupervisable.setInitialValue(false);
        esSupervisable.setDefaultValue(false);
        esHeredada.setInitialValue(false);
        esHeredada.setDefaultValue(false);
    }

}
