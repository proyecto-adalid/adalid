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
import adalid.core.annotations.ForeignKey;
import adalid.core.annotations.ManyToOne;
import adalid.core.annotations.PrimaryKey;
import adalid.core.annotations.PropertyField;
import adalid.core.annotations.UrlProperty;
import adalid.core.enums.Kleenean;
import adalid.core.enums.MasterDetailView;
import adalid.core.enums.Navigability;
import adalid.core.enums.OnDeleteAction;
import adalid.core.enums.OnUpdateAction;
import adalid.core.enums.ResourceGender;
import adalid.core.enums.ResourceType;
import adalid.core.enums.UrlDisplayType;
import adalid.core.enums.UrlType;
import adalid.core.interfaces.Artifact;
import adalid.core.properties.BooleanProperty;
import adalid.core.properties.LongProperty;
import adalid.core.properties.StringProperty;
import java.lang.reflect.Field;
import meta.entidad.comun.configuracion.basica.Parametro;
import meta.proyecto.base.ProyectoBase;

/**
 * @author Jorge Campins
 */
@EntityClass(independent = Kleenean.FALSE, resourceType = ResourceType.OPERATION, resourceGender = ResourceGender.MASCULINE)
@EntitySelectOperation(enabled = Kleenean.TRUE)
@EntityInsertOperation(enabled = Kleenean.FALSE)
@EntityUpdateOperation(enabled = Kleenean.FALSE)
@EntityDeleteOperation(enabled = Kleenean.FALSE)
@EntityTableView(enabled = Kleenean.FALSE)
@EntityDetailView(enabled = Kleenean.FALSE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
@EntityTriggers(afterValue = Kleenean.TRUE)
public class RastroFuncionPar extends AbstractPersistentEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    @Deprecated
    private RastroFuncionPar() {
        this(null, null);
    }

    public RastroFuncionPar(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public LongProperty idRastroFuncionPar;

    @ForeignKey(onDelete = OnDeleteAction.CASCADE, onUpdate = OnUpdateAction.CASCADE)
    @ManyToOne(navigability = Navigability.BIDIRECTIONAL, view = MasterDetailView.TABLE)
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(table = Kleenean.TRUE, report = Kleenean.TRUE)
    @Allocation(maxDepth = 2, maxRound = 0)
    public RastroFuncion idRastroFuncion;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.CASCADE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(table = Kleenean.TRUE, report = Kleenean.TRUE)
    @Allocation(maxDepth = 1, maxRound = 0)
    public Parametro idParametro;

    @PropertyField(table = Kleenean.TRUE, report = Kleenean.TRUE)
    public StringProperty valorAnterior;

    @PropertyField(table = Kleenean.TRUE, report = Kleenean.TRUE)
    public StringProperty valorParametro;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(table = Kleenean.TRUE, report = Kleenean.TRUE)
    public BooleanProperty diferenteValor;

    @PropertyField(hidden = Kleenean.TRUE)
    public LongProperty idClaseRecursoValor;

    @UrlProperty(urlType = UrlType.INTERNAL, urlDisplayType = UrlDisplayType.BUTTON)
    @PropertyField(table = Kleenean.TRUE, report = Kleenean.FALSE, search = Kleenean.FALSE, headertextless = Kleenean.TRUE)
    public StringProperty paginaRecurso;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        setDefaultLabel("parámetro de rastro de función");
        setDefaultShortLabel("parámetro");
        setDefaultCollectionLabel("Parámetros de Rastro de Función");
        setDefaultCollectionShortLabel("Parámetros");
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        valorParametro.setDefaultLabel("valor");
        paginaRecurso.setDefaultLabel("detalle");
        diferenteValor.setInitialValue(false);
        diferenteValor.setDefaultValue(false);
        diferenteValor.setDefaultLabel("diferente");
        diferenteValor.setDefaultTooltip("el valor es diferente al anterior");
    }

    @Override
    protected void settleLinks() {
        super.settleLinks();
        linkForeignOwnerProperty(idRastroFuncion.idUsuario);
        linkForeignSegmentProperty(idRastroFuncion.idUsuario);
    }

}
