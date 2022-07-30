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
package meta.entidad.comun.control.prueba;

import adalid.core.*;
import adalid.core.annotations.*;
import adalid.core.enums.*;
import adalid.core.interfaces.*;
import adalid.core.parameters.*;
import adalid.core.properties.*;
import java.lang.reflect.Field;
import meta.entidad.base.PersistentEntityBase;
import meta.entidad.comun.auditoria.ArchivoAdjunto;

/**
 * @author Jorge Campins
 */
@AbstractClass
@InheritanceMapping(strategy = InheritanceMappingStrategy.SINGLE_TABLE)
@EntityClass(independent = Kleenean.FALSE, resourceType = ResourceType.TESTING, resourceGender = ResourceGender.MASCULINE)
@EntityCodeGen(bws = Kleenean.FALSE, fws = Kleenean.FALSE)
@EntitySelectOperation(enabled = Kleenean.TRUE, onload = SelectOnloadOption.EXECUTE)
@EntityInsertOperation(enabled = Kleenean.FALSE)
@EntityUpdateOperation(enabled = Kleenean.TRUE)
@EntityDeleteOperation(enabled = Kleenean.TRUE)
@EntityTableView(enabled = Kleenean.TRUE, inserts = Kleenean.FALSE, updates = Kleenean.FALSE)
@EntityDetailView(enabled = Kleenean.TRUE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.TRUE)
//@EntityDataGen(start = 1, stop = 1000)
@EntityWarnings(aboutBusinessKey = Kleenean.FALSE)
public class DocumentoPrueba extends PersistentEntityBase {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public DocumentoPrueba(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of DocumentoPrueba's attributes">
        setLocalizedLabel(ENGLISH, "test document");
        setLocalizedLabel(SPANISH, "documento de prueba");
        setLocalizedShortLabel(ENGLISH, "document");
        setLocalizedShortLabel(SPANISH, "documento");
        setLocalizedCollectionLabel(ENGLISH, "Test Documents");
        setLocalizedCollectionLabel(SPANISH, "Documentos de Prueba");
        setLocalizedCollectionShortLabel(ENGLISH, "Documents");
        setLocalizedCollectionShortLabel(SPANISH, "Documentos");
        // </editor-fold>
    }

    @DiscriminatorColumn
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    public TipoDocumentoPrueba tipo;

    @FileReference(joinField = "adjunto", loadField = "fechaHoraCarga")
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.FALSE)
    public StringProperty archivo;

    @DescriptionProperty
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.TRUE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    @StringField(maxLength = 0)
    public StringProperty descripcion;

//  @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(hidden = Kleenean.TRUE)
    public ArchivoAdjunto adjunto;

    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public TimestampProperty fechaHoraCarga;

    @Override
    protected void settleProperties() {
        super.settleProperties();
        // <editor-fold defaultstate="collapsed" desc="localization of DocumentoPrueba's properties">
        tipo.setLocalizedLabel(ENGLISH, "type");
        tipo.setLocalizedLabel(SPANISH, "tipo");
        /**/
        archivo.setLocalizedLabel(ENGLISH, "file");
        archivo.setLocalizedLabel(SPANISH, "archivo");
        archivo.setLocalizedTooltip(ENGLISH, "open the file");
        archivo.setLocalizedTooltip(SPANISH, "abrir el archivo");
        /**/
        descripcion.setLocalizedLabel(ENGLISH, "test document description");
        descripcion.setLocalizedLabel(SPANISH, "descripción del documento de prueba");
        descripcion.setLocalizedShortLabel(ENGLISH, "description");
        descripcion.setLocalizedShortLabel(SPANISH, "descripción");
        /**/
        adjunto.setLocalizedLabel(ENGLISH, "attachment");
        adjunto.setLocalizedLabel(SPANISH, "adjunto");
        /**/
        fechaHoraCarga.setLocalizedLabel(ENGLISH, "upload timestamp");
        fechaHoraCarga.setLocalizedLabel(SPANISH, "fecha hora carga");
        // </editor-fold>
    }

    protected Cargar cargar;

    @ProcessOperationClass
    @OperationClass(access = OperationAccess.RESTRICTED, asynchronous = Kleenean.TRUE)
    public class Cargar extends ProcessOperation {

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            // <editor-fold defaultstate="collapsed" desc="localization of Cargar's attributes">
            setLocalizedLabel(ENGLISH, "upload");
            setLocalizedLabel(SPANISH, "cargar");
            // </editor-fold>
        }

        @InstanceReference
        protected DocumentoPrueba documento;

        @FileReference
        @ParameterField(required = Kleenean.TRUE, linkedField = "archivo")
        protected StringParameter archivo;

        @ParameterField(required = Kleenean.FALSE, linkedField = "descripcion")
        protected StringParameter descripcion;

        @Override
        protected void settleParameters() {
            super.settleParameters();
            // <editor-fold defaultstate="collapsed" desc="localization of Cargar's parameters">
            documento.setLocalizedLabel(ENGLISH, "document");
            documento.setLocalizedLabel(SPANISH, "documento");
            /**/
            archivo.setLocalizedLabel(ENGLISH, "file");
            archivo.setLocalizedLabel(SPANISH, "archivo");
            archivo.setLocalizedTooltip(ENGLISH, "file");
            archivo.setLocalizedTooltip(SPANISH, "archivo");
            /**/
            descripcion.setLocalizedLabel(ENGLISH, "description");
            descripcion.setLocalizedLabel(SPANISH, "descripción");
            // </editor-fold>
        }

    }

}
