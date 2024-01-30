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
package meta.entidad.comun.control.acceso;

import adalid.core.*;
import adalid.core.annotations.*;
import adalid.core.enums.*;
import adalid.core.interfaces.*;
import adalid.core.parameters.*;
import adalid.core.properties.*;
import java.lang.reflect.Field;

/**
 * @author Jorge Campins
 */
@EntityClass(independent = Kleenean.TRUE, resourceType = ResourceType.OPERATION, resourceGender = ResourceGender.FEMININE)
@EntityCodeGen(bws = Kleenean.FALSE, fws = Kleenean.FALSE)
@EntitySelectOperation(enabled = Kleenean.FALSE, access = OperationAccess.PRIVATE)
@EntityInsertOperation(enabled = Kleenean.FALSE, access = OperationAccess.PRIVATE)
@EntityUpdateOperation(enabled = Kleenean.FALSE, access = OperationAccess.PRIVATE)
@EntityDeleteOperation(enabled = Kleenean.FALSE, access = OperationAccess.PRIVATE)
@EntityTableView(enabled = Kleenean.FALSE)
@EntityDetailView(enabled = Kleenean.FALSE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
public class UsuarioSegmento extends AbstractPersistentEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public UsuarioSegmento(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of UsuarioSegmento's attributes">
        /**/
        setLocalizedLabel(ENGLISH, "user/segment association");
        setLocalizedLabel(SPANISH, "asociación Usuario/Segmento");
        setLocalizedCollectionLabel(ENGLISH, "User/Segment Associations");
        setLocalizedCollectionLabel(SPANISH, "Asociaciones Usuario/Segmento");
        /**/
        setLocalizedDescription(ENGLISH, "Each instance of " + b("User/Segment Associations") + " represents a "
            + "segment associated with an application user."
            + "");
        setLocalizedDescription(SPANISH, "Cada instancia de " + b("Asociaciones Usuario/Segmento") + " representa un "
            + "segmento asociado con un usuario de la aplicación."
            + "");
        /**/
        setLocalizedShortDescription(ENGLISH, "segment associated with an application user");
        setLocalizedShortDescription(SPANISH, "segmento asociado con un usuario de la aplicación");
        /**/
        // </editor-fold>
    }

    @PrimaryKey
    public LongProperty id;

    @VersionProperty
    public LongProperty version;

    @ColumnField(nullable = Kleenean.FALSE)
    public LongProperty usuario;

    @ColumnField(nullable = Kleenean.FALSE)
    public LongProperty conjunto;

    @ColumnField(nullable = Kleenean.FALSE)
    public LongProperty segmento;

    @Override
    protected void settleProperties() {
        super.settleProperties();
        // <editor-fold defaultstate="collapsed" desc="localization of UsuarioSegmento's properties">
        /**/
        usuario.setLocalizedLabel(ENGLISH, "user ID");
        usuario.setLocalizedLabel(SPANISH, "ID de usuario");
        /**/
        conjunto.setLocalizedLabel(ENGLISH, "set ID");
        conjunto.setLocalizedLabel(SPANISH, "ID de conjunto");
        /**/
        segmento.setLocalizedLabel(ENGLISH, "segment ID");
        segmento.setLocalizedLabel(SPANISH, "ID de segmento");
        /**/
        // </editor-fold>
    }

    protected Key ix_usuario_segmento_0001;

    @Override
    protected void settleKeys() {
        super.settleKeys();
        /**/
        ix_usuario_segmento_0001.setUnique(false);
        ix_usuario_segmento_0001.newKeyField(usuario, conjunto);
        /**/
    }

    protected Agregar agregar;

    @ProcessOperationClass
    @OperationClass(access = OperationAccess.PRIVATE)
    @ConstructionOperationClass(type = UsuarioSegmento.class)
    public class Agregar extends ProcessOperation {

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            // <editor-fold defaultstate="collapsed" desc="localization of Agregar's attributes">
            /**/
            setLocalizedLabel(ENGLISH, "add user segment");
            setLocalizedLabel(SPANISH, "agregar segmento de usuario");
            /**/
            setLocalizedSuccessMessage(ENGLISH, "a user/segment association was successfully added");
            setLocalizedSuccessMessage(SPANISH, "una asociación usuario/segmento se agregó con éxito");
            /**/
            // </editor-fold>
        }

        @ParameterField(required = Kleenean.TRUE)
        protected LongParameter usuario;

        @ParameterField(required = Kleenean.TRUE)
        protected LongParameter conjunto;

        @ParameterField(required = Kleenean.TRUE)
        protected LongParameter segmento;

        @Override
        protected void settleParameters() {
            super.settleParameters();
            // <editor-fold defaultstate="collapsed" desc="localization of Agregar's parameters">
            /**/
            usuario.setLocalizedLabel(ENGLISH, "user ID");
            usuario.setLocalizedLabel(SPANISH, "ID de usuario");
            /**/
            conjunto.setLocalizedLabel(ENGLISH, "set ID");
            conjunto.setLocalizedLabel(SPANISH, "ID de conjunto");
            /**/
            segmento.setLocalizedLabel(ENGLISH, "segment ID");
            segmento.setLocalizedLabel(SPANISH, "ID de segmento");
            /**/
            // </editor-fold>
        }

    }

    protected Eliminar eliminar;

    @ProcessOperationClass
    @OperationClass(access = OperationAccess.PRIVATE)
    public class Eliminar extends ProcessOperation {

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            // <editor-fold defaultstate="collapsed" desc="localization of Eliminar's attributes">
            /**/
            setLocalizedLabel(ENGLISH, "delete all user segments");
            setLocalizedLabel(SPANISH, "eliminar todos los segmentos del usuario");
            /**/
            setLocalizedSuccessMessage(ENGLISH, "all user's user/segment associations were removed");
            setLocalizedSuccessMessage(SPANISH, "todas las asociaciones usuario/segmento del usuario fueron eliminadas");
            /**/
            // </editor-fold>
        }

        @ParameterField(required = Kleenean.TRUE)
        protected LongParameter usuario;

        @Override
        protected void settleParameters() {
            super.settleParameters();
            // <editor-fold defaultstate="collapsed" desc="localization of Eliminar's parameters">
            /**/
            usuario.setLocalizedLabel(ENGLISH, "user ID");
            usuario.setLocalizedLabel(SPANISH, "ID de usuario");
            /**/
            // </editor-fold>
        }

    }

    protected Remover remover;

    @ProcessOperationClass
    @OperationClass(access = OperationAccess.PRIVATE)
    public class Remover extends ProcessOperation {

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            // <editor-fold defaultstate="collapsed" desc="localization of Remover's attributes">
            /**/
            setLocalizedLabel(ENGLISH, "delete all user and set segments");
            setLocalizedLabel(SPANISH, "eliminar todos los segmentos del usuario y conjunto");
            /**/
            setLocalizedSuccessMessage(ENGLISH, "all user and set segments were removed");
            setLocalizedSuccessMessage(SPANISH, "todos los segmentos del usuario y conjunto fueron eliminados");
            /**/
            // </editor-fold>
        }

        @ParameterField(required = Kleenean.TRUE)
        protected LongParameter usuario;

        @ParameterField(required = Kleenean.TRUE)
        protected LongParameter conjunto;

        @Override
        protected void settleParameters() {
            super.settleParameters();
            // <editor-fold defaultstate="collapsed" desc="localization of Remover's parameters">
            /**/
            usuario.setLocalizedLabel(ENGLISH, "user ID");
            usuario.setLocalizedLabel(SPANISH, "ID de usuario");
            /**/
            conjunto.setLocalizedLabel(ENGLISH, "set ID");
            conjunto.setLocalizedLabel(SPANISH, "ID de conjunto");
            /**/
            // </editor-fold>
        }

    }

}
