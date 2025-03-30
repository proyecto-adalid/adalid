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
import meta.entidad.comun.control.acceso.Usuario;

/**
 * @author Jorge Campins
 */
@EntityClass(independent = Kleenean.TRUE, resourceType = ResourceType.TESTING, resourceGender = ResourceGender.MASCULINE)
@EntityCodeGen(bws = Kleenean.FALSE, fws = Kleenean.FALSE)
@EntitySelectOperation(enabled = Kleenean.TRUE, onload = SelectOnloadOption.EXECUTE)
@EntityInsertOperation(enabled = Kleenean.TRUE)
@EntityUpdateOperation(enabled = Kleenean.TRUE)
@EntityDeleteOperation(enabled = Kleenean.TRUE)
@EntityTableView(enabled = Kleenean.TRUE, inserts = Kleenean.FALSE)
@EntityDetailView(enabled = Kleenean.TRUE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.TRUE)
//@EntityDataGen(start = 1, stop = 10)
public class ProgramaPrueba extends PersistentEntityBase {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public ProgramaPrueba(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of ProgramaPrueba's attributes">
        setLocalizedLabel(ENGLISH, "test program");
        setLocalizedLabel(SPANISH, "programa de prueba");
        setLocalizedShortLabel(ENGLISH, "program");
        setLocalizedShortLabel(SPANISH, "programa");
        setLocalizedCollectionLabel(ENGLISH, "Test Programs");
        setLocalizedCollectionLabel(SPANISH, "Programas de Prueba");
        setLocalizedCollectionShortLabel(ENGLISH, "Programs");
        setLocalizedCollectionShortLabel(SPANISH, "Programas");
        // </editor-fold>
    }

    @BusinessKey
    @StringField(maxLength = 60)
    public StringProperty codigo;

    @NameProperty
    public StringProperty nombre;

    @DescriptionProperty
    @PropertyField(create = Kleenean.TRUE)
    @StringField(maxLength = 0)
    public StringProperty descripcion;

    @PropertyField(create = Kleenean.TRUE)
    @StringField(maxLength = 0)
    public StringProperty comentarios;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.TABLE, viewSequence = 20)
    @PropertyField(create = Kleenean.TRUE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public PaquetePrueba paquete;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE, viewSequence = 20)
    @PropertyField(create = Kleenean.TRUE, update = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public CasoPrueba caso;

    @OwnerProperty
    @ColumnField(nullable = Kleenean.FALSE)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.FALSE, report = Kleenean.FALSE) //, defaultCheckpoint = Checkpoint.USER_INTERFACE)
    public Usuario propietario;

    @Override
    protected void settleProperties() {
        super.settleProperties();
        // <editor-fold defaultstate="collapsed" desc="localization of ProgramaPrueba's properties">
        codigo.setLocalizedLabel(ENGLISH, "test program code");
        codigo.setLocalizedLabel(SPANISH, "código del programa de prueba");
        codigo.setLocalizedShortLabel(ENGLISH, "code");
        codigo.setLocalizedShortLabel(SPANISH, "código");
        /**/
        nombre.setLocalizedLabel(ENGLISH, "test program name");
        nombre.setLocalizedLabel(SPANISH, "nombre del programa de prueba");
        nombre.setLocalizedShortLabel(ENGLISH, "name");
        nombre.setLocalizedShortLabel(SPANISH, "nombre");
        /**/
        descripcion.setLocalizedLabel(ENGLISH, "test program description");
        descripcion.setLocalizedLabel(SPANISH, "descripción del programa de prueba");
        descripcion.setLocalizedShortLabel(ENGLISH, "description");
        descripcion.setLocalizedShortLabel(SPANISH, "descripción");
        /**/
        comentarios.setLocalizedLabel(ENGLISH, "comments");
        comentarios.setLocalizedLabel(SPANISH, "comentarios");
        /**/
        paquete.setLocalizedLabel(ENGLISH, "package");
        paquete.setLocalizedLabel(SPANISH, "paquete");
        /**/
        caso.setLocalizedLabel(ENGLISH, "case");
        caso.setLocalizedLabel(SPANISH, "caso");
        /**/
        propietario.setLocalizedLabel(ENGLISH, "test program owner");
        propietario.setLocalizedLabel(SPANISH, "propietario del programa de prueba");
        propietario.setLocalizedShortLabel(ENGLISH, "owner");
        propietario.setLocalizedShortLabel(SPANISH, "propietario");
        // </editor-fold>
    }

    protected Tab tab1, tab2;

    @Override
    protected void settleTabs() {
        super.settleTabs();
        tab1.newTabField(descripcion, comentarios);
        tab2.newTabField(paquete, caso, propietario);
        // <editor-fold defaultstate="collapsed" desc="localization of ProgramaPrueba's tabs">
        tab1.setLocalizedLabel(ENGLISH, "basic data");
        tab1.setLocalizedLabel(SPANISH, "datos básicos");
        /**/
        tab2.setLocalizedLabel(ENGLISH, "links");
        tab2.setLocalizedLabel(SPANISH, "enlances");
        // </editor-fold>
    }

    protected Cargar cargar;

    @ProcessOperationClass
    public class Cargar extends ProcessOperation {

        @InstanceReference
        protected ProgramaPrueba programa;

        @FileReference
        @ParameterField(required = Kleenean.TRUE)
        protected StringParameter archivo;

        @ParameterField(required = Kleenean.FALSE)
        protected StringParameter descripcionArchivo;

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            // <editor-fold defaultstate="collapsed" desc="localization of Cargar's attributes">
            setLocalizedLabel(ENGLISH, "upload");
            setLocalizedLabel(SPANISH, "cargar");
            // </editor-fold>
        }

        @Override
        protected void settleParameters() {
            super.settleParameters();
            // <editor-fold defaultstate="collapsed" desc="localization of Cargar's parameters">
            programa.setLocalizedLabel(ENGLISH, "program");
            programa.setLocalizedLabel(SPANISH, "programa");
            /**/
            archivo.setLocalizedLabel(ENGLISH, "file");
            archivo.setLocalizedLabel(SPANISH, "archivo");
            /**/
            descripcionArchivo.setLocalizedLabel(ENGLISH, "file description");
            descripcionArchivo.setLocalizedLabel(SPANISH, "descripción archivo");
            // </editor-fold>
        }

    }

}
