/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package meta.entidad.comun.control.acceso;

import adalid.core.AbstractPersistentEntity;
import adalid.core.Key;
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
import adalid.core.annotations.EntityUpdateOperation;
import adalid.core.annotations.ForeignKey;
import adalid.core.annotations.ManyToOne;
import adalid.core.annotations.PrimaryKey;
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
import java.lang.reflect.Field;
import meta.entidad.comun.operacion.basica.FiltroFuncion;
import meta.proyecto.base.ProyectoBase;

/**
 * @author Jorge Campins
 */
@EntityClass(independent = Kleenean.FALSE, resourceType = ResourceType.OPERATION, resourceGender = ResourceGender.FEMININE)
@EntitySelectOperation(enabled = Kleenean.TRUE)
@EntityInsertOperation(enabled = Kleenean.TRUE)
@EntityUpdateOperation(enabled = Kleenean.TRUE)
@EntityDeleteOperation(enabled = Kleenean.TRUE)
@EntityTableView(enabled = Kleenean.FALSE)
@EntityDetailView(enabled = Kleenean.FALSE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
public class RolFiltroFuncion extends AbstractPersistentEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    @Deprecated
    private RolFiltroFuncion() {
        this(null, null);
    }

    public RolFiltroFuncion(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public LongProperty idRolFiltroFuncion;

    @VersionProperty
    public LongProperty versionRolFiltroFuncion;

    @ForeignKey(onDelete = OnDeleteAction.CASCADE, onUpdate = OnUpdateAction.CASCADE)
    @ManyToOne(navigability = Navigability.BIDIRECTIONAL, view = MasterDetailView.TABLE)
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(required = Kleenean.TRUE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    @Allocation(maxDepth = 1, maxRound = 0)
    public Rol idRol;

    @ForeignKey(onDelete = OnDeleteAction.CASCADE, onUpdate = OnUpdateAction.CASCADE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(required = Kleenean.TRUE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    @Allocation(maxDepth = 1, maxRound = 0)
    public FiltroFuncion idFiltroFuncion;

    protected Key uk_rol_filtro_funcion_0001;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        setDefaultLabel("asociaci??n Rol/Filtro");
        setDefaultCollectionLabel("Asociaciones Rol/Filtro");
//      setDefaultLabel(idRol, "filtro por rol");
//      setDefaultShortLabel(idRol, "filtro");
        setDefaultCollectionLabel(idRol, "Filtros por Rol");
        setDefaultCollectionShortLabel(idRol, "Filtros");
    }

    @Override
    protected void settleKeys() {
        super.settleKeys();
        uk_rol_filtro_funcion_0001.setUnique(true);
        uk_rol_filtro_funcion_0001.newKeyField(idRol, idFiltroFuncion);
    }

}
