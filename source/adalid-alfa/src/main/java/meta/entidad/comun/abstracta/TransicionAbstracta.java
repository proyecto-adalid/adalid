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
package meta.entidad.comun.abstracta;

import adalid.core.*;
import adalid.core.annotations.*;
import adalid.core.enums.*;
import adalid.core.interfaces.*;
import adalid.core.properties.*;
import java.lang.reflect.Field;
import meta.entidad.base.PersistentEntityBase;
import meta.entidad.comun.control.acceso.Usuario;

/**
 * @author Jorge Campins
 */
@EntityClass(resourceType = ResourceType.OPERATION, resourceGender = ResourceGender.FEMININE, independent = Kleenean.FALSE)
@EntitySelectOperation(enabled = Kleenean.TRUE)
@EntityInsertOperation(enabled = Kleenean.FALSE)
@EntityUpdateOperation(enabled = Kleenean.FALSE)
@EntityDeleteOperation(enabled = Kleenean.FALSE)
public abstract class TransicionAbstracta extends PersistentEntityBase {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public TransicionAbstracta(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of TransicionAbstracta's attributes">
        setLocalizedLabel(ENGLISH, "transition");
        setLocalizedLabel(SPANISH, "transición");
        setLocalizedShortLabel(ENGLISH, "transition");
        setLocalizedShortLabel(SPANISH, "transición");
        setLocalizedCollectionLabel(ENGLISH, "Transitions");
        setLocalizedCollectionLabel(SPANISH, "Transiciones");
        setLocalizedCollectionShortLabel(ENGLISH, "Transitions");
        setLocalizedCollectionShortLabel(SPANISH, "Transiciones");
        // </editor-fold>
    }

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(sequence = 1010, table = Kleenean.TRUE, report = Kleenean.TRUE, search = Kleenean.TRUE)
    public TimestampProperty fechaHora;

    @UserProperty
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(sequence = 1020, table = Kleenean.FALSE, report = Kleenean.TRUE, search = Kleenean.TRUE)
    public Usuario usuario;

    @PropertyField(sequence = 1030)
    @StringField(maxLength = 0)
    public StringProperty comentarios;

    @Override
    protected void settleProperties() {
        super.settleProperties();
        /**/
        fechaHora.setInitialValue(SpecialTemporalValue.CURRENT_TIMESTAMP);
        fechaHora.setDefaultValue(SpecialTemporalValue.CURRENT_TIMESTAMP);
        /*
        usuario.setInitialValue(SpecialEntityValue.CURRENT_USER);
        usuario.setDefaultValue(SpecialEntityValue.CURRENT_USER);
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of TransicionAbstracta's properties">
        /**/
        fechaHora.setLocalizedLabel(ENGLISH, "timestamp");
        fechaHora.setLocalizedLabel(SPANISH, "fecha/hora");
        fechaHora.setLocalizedDescription(ENGLISH, "transition timestamp");
        fechaHora.setLocalizedDescription(SPANISH, "fecha/hora de la transición");
        /**/
        usuario.setLocalizedLabel(ENGLISH, "user");
        usuario.setLocalizedLabel(SPANISH, "usuario");
        usuario.setLocalizedDescription(ENGLISH, "user who executed the operation that caused the transition");
        usuario.setLocalizedDescription(SPANISH, "usuario que ejecutó la operación que produjo la transición");
        /**/
        comentarios.setLocalizedLabel(ENGLISH, "comentarios");
        comentarios.setLocalizedLabel(SPANISH, "comentarios");
        comentarios.setLocalizedDescription(ENGLISH, "comments from the user who executed the operation that caused the transition");
        comentarios.setLocalizedDescription(SPANISH, "comentarios del usuario que ejecutó la operación que produjo la transición");
        /**/
        // </editor-fold>
    }

}
