/*
 * Copyright 2017 Jorge Campins y David Uzcategui
 *
 * Este archivo forma parte de Adalid.
 *
 * Adalid es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos de la
 * licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Adalid se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA; sin
 * siquiera las garantias implicitas de COMERCIALIZACION e IDONEIDAD PARA UN PROPOSITO PARTICULAR.
 *
 * Para mas detalles vea la licencia "GNU General Public License" en http://www.gnu.org/licenses
 */
package meta.entidad.comun.auditoria;

import adalid.core.*;
import adalid.core.annotations.*;
import adalid.core.enums.*;
import adalid.core.interfaces.*;
import adalid.core.properties.*;
import java.lang.reflect.Field;
import meta.entidad.comun.control.acceso.Usuario;

/**
 * @author Jorge Campins
 */
@EntityClass(independent = Kleenean.TRUE, resourceType = ResourceType.OPERATION, resourceGender = ResourceGender.MASCULINE)
@EntityCodeGen(bws = Kleenean.FALSE, fws = Kleenean.FALSE)
@EntitySelectOperation(enabled = Kleenean.TRUE, onload = SelectOnloadOption.EXECUTE, sortOption = SortOption.DESC)
@EntityInsertOperation(enabled = Kleenean.FALSE)
@EntityUpdateOperation(enabled = Kleenean.FALSE)
@EntityDeleteOperation(enabled = Kleenean.FALSE)
@EntityTableView(enabled = Kleenean.TRUE)
@EntityDetailView(enabled = Kleenean.TRUE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
public class ArchivoAdjunto extends AbstractPersistentEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public ArchivoAdjunto(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of ArchivoAdjunto's attributes">
        setLocalizedLabel(ENGLISH, "attached file");
        setLocalizedLabel(SPANISH, "archivo adjunto");
        setLocalizedShortLabel(ENGLISH, "attachment");
        setLocalizedShortLabel(SPANISH, "adjunto");
        setLocalizedCollectionLabel(ENGLISH, "Attached Files");
        setLocalizedCollectionLabel(SPANISH, "Archivos Adjuntos");
        setLocalizedCollectionShortLabel(ENGLISH, "Attachments");
        setLocalizedCollectionShortLabel(SPANISH, "Adjuntos");
        /**/
        setLocalizedDescription(ENGLISH, "Each instance of " + b("Attached Files") + " represents an "
            + "audit trail of the execution of file upload operations on the server."
            + "");
        setLocalizedDescription(SPANISH, "Cada instancia de " + b("Archivos Adjuntos") + " representa un "
            + "rastro de auditoría de la ejecución de operaciones de carga de archivos en el servidor."
            + "");
        /**/
        setLocalizedShortDescription(ENGLISH, "audit trail of the execution of file upload operations on the server");
        setLocalizedShortDescription(SPANISH, "rastro de auditoría de la ejecución de operaciones de carga de archivos en el servidor");
        /**/
        // </editor-fold>
    }

    @PrimaryKey
    public LongProperty id;

    @VersionProperty
    public LongProperty version;

//  @BusinessKey
    @FileReference(blobField = "octetos", loadField = "fechaHoraCarga")
    @ColumnField(nullable = Kleenean.FALSE, unique = Kleenean.TRUE)
    @PropertyField(table = Kleenean.TRUE, report = Kleenean.FALSE)
    public StringProperty archivoServidor;

    @NameProperty
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(table = Kleenean.TRUE, report = Kleenean.TRUE)
    @StringField(maxLength = 256)
    public StringProperty archivoCliente;

    @OwnerProperty
    @SegmentProperty
    @ColumnField(nullable = Kleenean.TRUE)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(hidden = Kleenean.TRUE) //, defaultCheckpoint = Checkpoint.USER_INTERFACE)
    @QueryMapping(mapKeyProperties = Kleenean.FALSE)
    public Usuario propietario;

    @PropertyField(table = Kleenean.TRUE, search = Kleenean.TRUE, report = Kleenean.TRUE)
    @StringField(maxLength = 36)
    public StringProperty codigoUsuarioPropietario;

    @PropertyField(table = Kleenean.FALSE, search = Kleenean.TRUE, report = Kleenean.TRUE)
    @StringField(maxLength = 100)
    public StringProperty nombreUsuarioPropietario;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(table = Kleenean.TRUE, report = Kleenean.TRUE, search = Kleenean.TRUE, overlay = Kleenean.TRUE)
    public TimestampProperty fechaHoraCarga;

    @ColumnField(nullable = Kleenean.TRUE)
    @PropertyField(hidden = Kleenean.TRUE)
    @StringField(maxLength = 100)
    public StringProperty tipoCarga;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(table = Kleenean.TRUE, report = Kleenean.TRUE, search = Kleenean.TRUE, overlay = Kleenean.TRUE)
    @StringField(maxLength = 100)
    public StringProperty tipoContenido;

    @ColumnField(nullable = Kleenean.TRUE)
    @PropertyField(table = Kleenean.FALSE)
    @StringField(maxLength = 100)
    public StringProperty conjuntoCaracteres;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(table = Kleenean.TRUE, report = Kleenean.TRUE, search = Kleenean.TRUE, overlay = Kleenean.TRUE)
    public LongProperty longitud;

    @PropertyField(hidden = Kleenean.TRUE)
    public BinaryProperty octetos;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(table = Kleenean.FALSE, report = Kleenean.TRUE, search = Kleenean.TRUE, overlay = Kleenean.TRUE)
    public IntegerProperty referencias;

    @ColumnField(nullable = Kleenean.FALSE)
//  @PropertyField(table = Kleenean.TRUE, report = Kleenean.FALSE, search = Kleenean.TRUE)
    @PropertyField(hidden = Kleenean.TRUE)
    public BooleanProperty eliminable;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(table = Kleenean.TRUE, report = Kleenean.FALSE, search = Kleenean.TRUE)
    public BooleanProperty restaurable;

    /**
     * BLOB property getter (for velocity templates)
     *
     * @return the BLOB property
     */
    public BinaryProperty getBlobProperty() {
        return octetos;
    }

    /**
     * BLOB property getter (for velocity templates)
     *
     * @return the references property
     */
    public IntegerProperty getReferencesProperty() {
        return referencias;
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        /**/
        propietario.setInitialValue(SpecialEntityValue.CURRENT_USER);
        propietario.setDefaultValue(SpecialEntityValue.CURRENT_USER);
        /**/
        fechaHoraCarga.setInitialValue(SpecialTemporalValue.CURRENT_TIMESTAMP);
        fechaHoraCarga.setDefaultValue(SpecialTemporalValue.CURRENT_TIMESTAMP);
        /**/
        referencias.setInitialValue(0);
        referencias.setDefaultValue(0);
        /**/
//      eliminable=not(tipo_carga is null or tipo_carga = 'FILA');
        eliminable.setInitialValue(false);
        eliminable.setDefaultValue(not(tipoCarga.isNullOrEqualTo("FILA")));
        eliminable.setPrimalDefaultValue(true);
        /**/
//      restaurable=not(octetos is null or archivo_servidor like '/%/media/%-%-%-%-%/table-row-column/%.%');
//      archivo_servidor: /${ALIAS}-content-base/media/${UUID}/table-row-column/${NAME}.${TYPE} (FILA => table-row-column)
//      e.g. /jee2ap101-content-base/media/97dc9878-1927-4287-b02e-c5835fb8aa01/table-row-column/164843751567204330.jpg
        BooleanExpression likish = and(archivoServidor.contains("/media/"), archivoServidor.contains("/table-row-column/"));
        restaurable.setInitialValue(false);
        restaurable.setDefaultValue(not(octetos.isNull().or(likish))); // formerly just restaurable.setDefaultValue(octetos.isNotNull());
        restaurable.setPrimalDefaultValue(true);
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of ArchivoAdjunto's properties">
        /**/
        archivoServidor.setLocalizedDescription(ENGLISH, "file name on the application server");
        archivoServidor.setLocalizedDescription(SPANISH, "nombre del archivo en el servidor de aplicaciones");
        archivoServidor.setLocalizedLabel(ENGLISH, "attached file's server file name");
        archivoServidor.setLocalizedLabel(SPANISH, "archivo servidor del archivo adjunto");
        archivoServidor.setLocalizedShortLabel(ENGLISH, "server file");
        archivoServidor.setLocalizedShortLabel(SPANISH, "archivo servidor");
        archivoServidor.setLocalizedTooltip(ENGLISH, "open the attached file");
        archivoServidor.setLocalizedTooltip(SPANISH, "abrir el archivo adjunto");
        /**/
        archivoCliente.setLocalizedDescription(ENGLISH, "name of the original file (file on the computer of the user who made the upload)");
        archivoCliente.setLocalizedDescription(SPANISH, "nombre del archivo original (archivo en el computador del usuario que realizó la carga)");
        archivoCliente.setLocalizedLabel(ENGLISH, "attached file's client file name");
        archivoCliente.setLocalizedLabel(SPANISH, "archivo cliente del archivo adjunto");
        archivoCliente.setLocalizedShortLabel(ENGLISH, "client file");
        archivoCliente.setLocalizedShortLabel(SPANISH, "archivo cliente");
        /**/
        propietario.setLocalizedDescription(ENGLISH, "user who made the upload");
        propietario.setLocalizedDescription(SPANISH, "usuario que realizó la carga");
        propietario.setLocalizedLabel(ENGLISH, "attached file's owner");
        propietario.setLocalizedLabel(SPANISH, "propietario del archivo adjunto");
        propietario.setLocalizedShortLabel(ENGLISH, "owner");
        propietario.setLocalizedShortLabel(SPANISH, "propietario");
        /**/
        codigoUsuarioPropietario.setLocalizedDescription(ENGLISH, "code of the user who made the upload");
        codigoUsuarioPropietario.setLocalizedDescription(SPANISH, "código del usuario que realizó la carga");
        codigoUsuarioPropietario.setLocalizedLabel(ENGLISH, "owner");
        codigoUsuarioPropietario.setLocalizedLabel(SPANISH, "propietario");
        /**/
        nombreUsuarioPropietario.setLocalizedDescription(ENGLISH, "name of the user who made the upload");
        nombreUsuarioPropietario.setLocalizedDescription(SPANISH, "nombre del usuario que realizó la carga");
        nombreUsuarioPropietario.setLocalizedLabel(ENGLISH, "owner name");
        nombreUsuarioPropietario.setLocalizedLabel(SPANISH, "nombre del propietario");
        /**/
        fechaHoraCarga.setLocalizedDescription(ENGLISH, "date and time the upload was made");
        fechaHoraCarga.setLocalizedDescription(SPANISH, "fecha y hora en la que se realizó la carga");
        fechaHoraCarga.setLocalizedLabel(ENGLISH, "upload timestamp");
        fechaHoraCarga.setLocalizedLabel(SPANISH, "fecha hora carga");
        /**/
        tipoCarga.setLocalizedLabel(ENGLISH, "file upload type");
        tipoCarga.setLocalizedLabel(SPANISH, "tipo de carga de archivo");
        /**/
        tipoContenido.setLocalizedDescription(ENGLISH, "MIME type (Multipurpose Internet Mail Extensions) that corresponds to the content of the file");
        tipoContenido.setLocalizedDescription(SPANISH, "tipo MIME (Multipurpose Internet Mail Extensions) que corresponde al contenido del archivo");
        tipoContenido.setLocalizedLabel(ENGLISH, "content type");
        tipoContenido.setLocalizedLabel(SPANISH, "tipo contenido");
        /**/
        conjuntoCaracteres.setLocalizedDescription(ENGLISH, "character set used for file encoding; only applies to text files");
        conjuntoCaracteres.setLocalizedDescription(SPANISH, "conjunto de caracteres utilizado para la codificación del archivo; solo aplica a archivos de texto");
        conjuntoCaracteres.setLocalizedLabel(ENGLISH, "character set");
        conjuntoCaracteres.setLocalizedLabel(SPANISH, "conjunto de caracteres");
        /**/
        longitud.setLocalizedDescription(ENGLISH, "file size, in bytes");
        longitud.setLocalizedDescription(SPANISH, "tamaño del archivo, en bytes");
        longitud.setLocalizedLabel(ENGLISH, "length");
        longitud.setLocalizedLabel(SPANISH, "longitud");
        /**/
        octetos.setLocalizedDescription(ENGLISH, "file content, in bytes");
        octetos.setLocalizedDescription(SPANISH, "contenido del archivo, en bytes");
        octetos.setLocalizedLabel(ENGLISH, "bytes");
        octetos.setLocalizedLabel(SPANISH, "octetos");
        /**/
        referencias.setLocalizedLabel(ENGLISH, "references");
        referencias.setLocalizedLabel(SPANISH, "referencias");
        /**/
        eliminable.setLocalizedDescription(ENGLISH, "indicator that shows whether or not the file can be deleted from the web server's file system");
        eliminable.setLocalizedDescription(SPANISH, "indicador que muestra si el archivo puede, o no, ser eliminado del sistema de archivos del servidor web");
        eliminable.setLocalizedLabel(ENGLISH, "removable");
        eliminable.setLocalizedLabel(SPANISH, "eliminable");
        /**/
        restaurable.setLocalizedDescription(ENGLISH, "indicator that shows whether or not the file can be restored "
            + "after being removed from the application server");
        restaurable.setLocalizedDescription(SPANISH, "indicador que muestra si el archivo puede, o no, ser restaurado "
            + "después de ser eliminado del servidor de aplicaciones");
        restaurable.setLocalizedLabel(ENGLISH, "restorable");
        restaurable.setLocalizedLabel(SPANISH, "restaurable");
        /**/
        // </editor-fold>
    }

    protected RestaurarArchivoServidorWeb restaurarArchivoServidorWeb;

    protected EliminarArchivoServidorWeb eliminarArchivoServidorWeb;

    protected EliminarTotalmente eliminarTotalmente;

    // <editor-fold defaultstate="collapsed" desc="Operations">
    @OperationClass(access = OperationAccess.PRIVATE, confirmation = Kleenean.TRUE)
    @ProcessOperationClass(builtIn = true)
    public class EliminarArchivoServidorWeb extends ProcessOperation {

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            // <editor-fold defaultstate="collapsed" desc="localization of EliminarArchivoServidorWeb's attributes">
            /**/
            setLocalizedLabel(ENGLISH, "delete the file from the web server");
            setLocalizedLabel(SPANISH, "eliminar el archivo del servidor web");
            /**/
            setLocalizedDescription(ENGLISH, "delete the file only from the web server's file system; "
                + "if the file is stored in the database, "
                + "it will remain stored in the database and it could be restored later; "
                + "but if the file is not stored in the databas, it can no longer be restored");
            setLocalizedDescription(SPANISH, "eliminar el archivo solo del sistema de archivos del servidor web; "
                + "si el archivo está almacenado en la base de datos, "
                + "permanecerá almacenado en la base de datos y podrá ser restaurado posteriormente; "
                + "pero si el archivo no está almacenado en la base de datos, éste no podrá ser restaurado");
            /**/
            setLocalizedSuccessMessage(ENGLISH, "the file was removed from the web server's file system");
            setLocalizedSuccessMessage(SPANISH, "el archivo se eliminó del sistema de archivos del servidor web");
            /**/
            // </editor-fold>
        }

        @InstanceReference
        protected ArchivoAdjunto archivoAdjunto;

        @Override
        protected void settleParameters() {
            super.settleParameters();
            archivoAdjunto.eliminable.setCurrentValue(false);
            // <editor-fold defaultstate="collapsed" desc="localization of EliminarArchivoServidorWeb's parameters">
            archivoAdjunto.setLocalizedLabel(ENGLISH, "attached file");
            archivoAdjunto.setLocalizedLabel(SPANISH, "archivo adjunto");
            // </editor-fold>
        }

        protected Check check101;

        @Override
        protected void settleExpressions() {
            super.settleExpressions();
            check101 = archivoAdjunto.eliminable.isTrue();
            // <editor-fold defaultstate="collapsed" desc="localization of EliminarArchivoServidorWeb's expressions">
            check101.setLocalizedDescription(ENGLISH, "the file is removable");
            check101.setLocalizedDescription(SPANISH, "el archivo es eliminable");
            check101.setLocalizedErrorMessage(ENGLISH, "the file cannot be found or cannot be deleted from the web server's file system");
            check101.setLocalizedErrorMessage(SPANISH, "el archivo no se encuentra o no se puede eliminar del sistema de archivos del servidor web");
            // </editor-fold>
        }

    }

    @OperationClass(access = OperationAccess.PROTECTED)
    @ProcessOperationClass(builtIn = true)
    public class RestaurarArchivoServidorWeb extends ProcessOperation {

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            // <editor-fold defaultstate="collapsed" desc="localization of RestaurarArchivoServidorWeb's attributes">
            setLocalizedLabel(ENGLISH, "restore the file on the web server");
            setLocalizedLabel(SPANISH, "restaurar el archivo del servidor web");
            setLocalizedDescription(ENGLISH, "restore the file on the web server's file system using the file stored in the database");
            setLocalizedDescription(SPANISH, "restaurar el archivo al sistema de archivos del servidor web con el contenido del archivo almacenado en la base de datos");
            setLocalizedSuccessMessage(ENGLISH, "the file was restored to the web server's file system");
            setLocalizedSuccessMessage(SPANISH, "el archivo se restauró al sistema de archivos del servidor web");
            // </editor-fold>
        }

        @InstanceReference
        protected ArchivoAdjunto archivoAdjunto;

        @Override
        protected void settleParameters() {
            super.settleParameters();
            archivoAdjunto.eliminable.setCurrentValue(true);
            // <editor-fold defaultstate="collapsed" desc="localization of RestaurarArchivoServidorWeb's parameters">
            archivoAdjunto.setLocalizedLabel(ENGLISH, "attached file");
            archivoAdjunto.setLocalizedLabel(SPANISH, "archivo adjunto");
            // </editor-fold>
        }

        protected Check check101;

        @Override
        protected void settleExpressions() {
            super.settleExpressions();
            check101 = archivoAdjunto.restaurable.isTrue();
            // <editor-fold defaultstate="collapsed" desc="localization of RestaurarArchivoServidorWeb's expressions">
            check101.setLocalizedDescription(ENGLISH, "the file is restorable");
            check101.setLocalizedDescription(SPANISH, "el archivo es restaurable");
            check101.setLocalizedErrorMessage(ENGLISH, "the file is not restorable because it is not stored in the database");
            check101.setLocalizedErrorMessage(SPANISH, "el archivo no es restaurable porque no se encuentra almacenado en la base de datos");
            // </editor-fold>
        }

    }

    @OperationClass(access = OperationAccess.PRIVATE, confirmation = Kleenean.TRUE)
    @ProcessOperationClass(builtIn = true)
    public class EliminarTotalmente extends ProcessOperation {

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            // <editor-fold defaultstate="collapsed" desc="localization of EliminarTotalmente's attributes">
            /**/
            setLocalizedLabel(ENGLISH, "completely delete the file");
            setLocalizedLabel(SPANISH, "eliminar totalmente el archivo");
            /**/
            setLocalizedDescription(ENGLISH, "delete the file from both the web server's file system and the database;"
                + "after completely deleting the file, it can no longer be restored");
            setLocalizedDescription(SPANISH, "eliminar el archivo tanto del sistema de archivos del servidor web como de la base de datos; "
                + "después de eliminar totalmente el archivo, éste no podrá ser restaurado");
            /**/
            setLocalizedSuccessMessage(ENGLISH, "the file was deleted from both the web server's file system and the database");
            setLocalizedSuccessMessage(SPANISH, "el archivo se eliminó tanto del sistema de archivos del servidor web como de la base de datos");
            /**/
            // </editor-fold>
        }

        @InstanceReference
        protected ArchivoAdjunto archivoAdjunto;

        @Override
        protected void settleParameters() {
            super.settleParameters();
            archivoAdjunto.octetos.setCurrentValue(SpecialBinaryValue.NULL);
            archivoAdjunto.eliminable.setCurrentValue(false);
            archivoAdjunto.restaurable.setCurrentValue(false);
            // <editor-fold defaultstate="collapsed" desc="localization of EliminarTotalmente's parameters">
            archivoAdjunto.setLocalizedLabel(ENGLISH, "attached file");
            archivoAdjunto.setLocalizedLabel(SPANISH, "archivo adjunto");
            // </editor-fold>
        }

        protected Check check101, check102;

        @Override
        protected void settleExpressions() {
            super.settleExpressions();
            /**/
            check101 = archivoAdjunto.referencias.isEqualTo(0);
            check102 = or(archivoAdjunto.eliminable, archivoAdjunto.octetos.isNotNull());
            /**/
            // <editor-fold defaultstate="collapsed" desc="localization of EliminarTotalmente's expressions">
            /**/
            check101.setLocalizedDescription(ENGLISH, "the file is not being referenced");
            check101.setLocalizedDescription(SPANISH, "el archivo no está siendo referenciado");
            check101.setLocalizedErrorMessage(ENGLISH, "the file cannot be deleted because it is being referenced");
            check101.setLocalizedErrorMessage(SPANISH, "el archivo no se puede eliminar porque está siendo referenciado");
            /**/
            check102.setLocalizedDescription(ENGLISH, "the file is removable");
            check102.setLocalizedDescription(SPANISH, "el archivo es eliminable");
            check102.setLocalizedErrorMessage(ENGLISH, "the file cannot be deleted from the web server's file system and is not stored in the database");
            check102.setLocalizedErrorMessage(SPANISH, "el archivo no se puede eliminar del sistema de archivos del servidor web y no se encuentra almacenado en la base de datos");
            /**/
            // </editor-fold>
        }

    }
    // </editor-fold>

}
