/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package meta.entidad.comun.auditoria;

import adalid.core.*;
import adalid.core.annotations.*;
import adalid.core.enums.*;
import adalid.core.interfaces.*;
import adalid.core.properties.*;
import java.lang.reflect.Field;
import meta.entidad.comun.control.acceso.Usuario;
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
public class ArchivoAdjunto extends AbstractPersistentEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    @Deprecated
    private ArchivoAdjunto() {
        this(null, null);
    }

    public ArchivoAdjunto(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        setDefaultLabel("archivo adjunto");
        setDefaultCollectionLabel("archivos adjuntos");
    }

    @PrimaryKey
    public LongProperty id;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(table = Kleenean.TRUE, report = Kleenean.TRUE)
    public StringProperty archivoCliente;

    @FileReference
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(table = Kleenean.FALSE, report = Kleenean.FALSE)
    public StringProperty archivoServidor;

    @OwnerProperty
    @SegmentProperty
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(hidden = Kleenean.TRUE)
    public Usuario propietario;

    @PropertyField(table = Kleenean.TRUE, search = Kleenean.TRUE, report = Kleenean.TRUE)
    @StringField(maxLength = 30)
    public StringProperty codigoUsuarioPropietario;

    @PropertyField(table = Kleenean.TRUE, search = Kleenean.TRUE, report = Kleenean.TRUE)
    @StringField(maxLength = 100)
    public StringProperty nombreUsuarioPropietario;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(table = Kleenean.TRUE, report = Kleenean.TRUE, search = Kleenean.FALSE)
    public TimestampProperty fechaHoraCarga;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(table = Kleenean.TRUE, report = Kleenean.TRUE)
    public StringProperty tipoContenido;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(table = Kleenean.TRUE, report = Kleenean.TRUE, search = Kleenean.FALSE)
    public LongProperty longitud;

    @ColumnField(nullable = Kleenean.FALSE)
    public BinaryProperty octetos;

    @Override
    protected void settleProperties() {
        super.settleProperties();
        propietario.setInitialValue(SpecialEntityValue.CURRENT_USER);
        propietario.setDefaultValue(SpecialEntityValue.CURRENT_USER);
        codigoUsuarioPropietario.setDefaultLabel("propietario");
        codigoUsuarioPropietario.setDefaultTooltip("propietario");
        nombreUsuarioPropietario.setDefaultLabel("nombre del propietario");
        nombreUsuarioPropietario.setDefaultTooltip("nombre del propietario");
        fechaHoraCarga.setInitialValue(SpecialTemporalValue.CURRENT_TIMESTAMP);
        fechaHoraCarga.setDefaultValue(SpecialTemporalValue.CURRENT_TIMESTAMP);
    }

    protected EliminarArchivoServidorWeb eliminarArchivoServidorWeb;

    public class EliminarArchivoServidorWeb extends ProcessOperation {

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            setDefaultLabel("eliminar el archivo del servidor web");
            setDefaultDescription("elimina el archivo solo del servidor web; el archivo quedará almacenado en la base de datos");
        }

        @InstanceReference
        @Allocation(maxDepth = 1, maxRound = 0)
        protected ArchivoAdjunto archivoAdjunto;

    }

    protected RestaurarArchivoServidorWeb restaurarArchivoServidorWeb;

    public class RestaurarArchivoServidorWeb extends ProcessOperation {

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            setDefaultLabel("restaurar el archivo del servidor web");
            setDefaultDescription("restaura el archivo del servidor web con el contenido del archivo almacenado en la base de datos");
        }

        @InstanceReference
        @Allocation(maxDepth = 1, maxRound = 0)
        protected ArchivoAdjunto archivoAdjunto;

    }

    protected EliminarTotalmente eliminarTotalmente;

    public class EliminarTotalmente extends ProcessOperation {

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            setDefaultLabel("eliminar totalmente el archivo");
            setDefaultDescription("elimina el archivo tanto del servidor web como de la base de datos");
        }

        @InstanceReference
        @Allocation(maxDepth = 1, maxRound = 0)
        protected ArchivoAdjunto archivoAdjunto;

    }

}
