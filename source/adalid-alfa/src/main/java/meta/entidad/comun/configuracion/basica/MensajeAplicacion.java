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
package meta.entidad.comun.configuracion.basica;

import adalid.commons.util.*;
import adalid.core.*;
import adalid.core.annotations.*;
import adalid.core.enums.*;
import adalid.core.interfaces.*;
import adalid.core.properties.*;
import java.lang.reflect.Field;
import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
@EntityClass(catalog = Kleenean.TRUE, independent = Kleenean.TRUE, resourceType = ResourceType.CONFIGURATION, resourceGender = ResourceGender.MASCULINE)
@EntityCodeGen(bws = Kleenean.FALSE, fws = Kleenean.FALSE)
@EntitySelectOperation(enabled = Kleenean.TRUE, access = OperationAccess.PUBLIC, rowsLimit = 0)
@EntityInsertOperation(enabled = Kleenean.FALSE)
@EntityUpdateOperation(enabled = Kleenean.FALSE)
@EntityDeleteOperation(enabled = Kleenean.FALSE)
@EntityTableView(enabled = Kleenean.TRUE)
@EntityDetailView(enabled = Kleenean.FALSE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
public class MensajeAplicacion extends AbstractPersistentEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public MensajeAplicacion(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="overriden methods">
    @Override
    public String getBusinessKeyValueOf(Instance instance) {
        return instance == null ? null : codigoMensaje(instance) + "~" + codigoLenguaje(instance);
    }

    @Override
    public Object getDefaultPropertyValueOf(Instance instance, Property property) {
        return instance == null || property == null ? null
            : property == codigoMensaje ? codigoMensaje(instance)
                : property == codigoLenguaje ? codigoLenguaje(instance)
                    : property == patron ? patron(instance)
                        : null;
    }

    private String codigoMensaje(Instance instance) {
        return StringUtils.substringAfter(instance.getName(), "_");
    }

    private String codigoLenguaje(Instance instance) {
        return StringUtils.substringBefore(instance.getName(), "_");
    }

    private String patron(Instance instance) {
        return StringUtils.capitalize(StrUtils.getWordyString(codigoMensaje(instance)));
    }
    // </editor-fold>

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        /**/
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        /**/
        setOrderBy(codigo);
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of MensajeAplicacion's messages">
        setLocalizedLabel(ENGLISH, "application message");
        setLocalizedLabel(SPANISH, "mensaje de la aplicación");
        setLocalizedShortLabel(ENGLISH, "message");
        setLocalizedShortLabel(SPANISH, "mensaje");
        setLocalizedCollectionLabel(ENGLISH, "Application Messages");
        setLocalizedCollectionLabel(SPANISH, "Mensajes de la Aplicación");
        setLocalizedCollectionShortLabel(ENGLISH, "Messages");
        setLocalizedCollectionShortLabel(SPANISH, "Mensajes");
        setLocalizedDescription(ENGLISH, "application's message");
        setLocalizedDescription(SPANISH, "mensaje de la aplicación");
        // </editor-fold>
        /**/
    }

    @PrimaryKey
    public LongProperty id;

    @VersionProperty
    public LongProperty version;

    @BusinessKey
    @PropertyField(table = Kleenean.FALSE)
    @StringField(maxLength = 200)
    public StringProperty codigo;

    @NameProperty
    @StringField(maxLength = 100)
    public StringProperty codigoMensaje;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(table = Kleenean.TRUE)
    @StringField(maxLength = 20)
    public StringProperty codigoLenguaje;

    @ColumnField(nullable = Kleenean.TRUE)
    @PropertyField(table = Kleenean.TRUE)
    @StringField(maxLength = 100)
    public StringProperty genero;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @ColumnField(nullable = Kleenean.TRUE)
    @PropertyField(table = Kleenean.FALSE, report = Kleenean.FALSE)
    public SeveridadMensaje severidad;

    @ColumnField(nullable = Kleenean.FALSE)
    public StringProperty patron;

    @Override
    protected void settleProperties() {
        super.settleProperties();
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of MensajeAplicacion's properties">
        /**/
        codigo.setLocalizedLabel(ENGLISH, "application message code");
        codigo.setLocalizedLabel(SPANISH, "código del mensaje");
        codigo.setLocalizedShortLabel(ENGLISH, "code");
        codigo.setLocalizedShortLabel(SPANISH, "código");
        /**/
        codigoMensaje.setLocalizedLabel(ENGLISH, "message");
        codigoMensaje.setLocalizedLabel(SPANISH, "mensaje");
        /**/
        codigoLenguaje.setLocalizedLabel(ENGLISH, "language");
        codigoLenguaje.setLocalizedLabel(SPANISH, "lenguaje");
        /**/
        genero.setLocalizedLabel(ENGLISH, "gender");
        genero.setLocalizedLabel(SPANISH, "género");
        /**/
        severidad.setLocalizedLabel(ENGLISH, "severity");
        severidad.setLocalizedLabel(SPANISH, "severidad");
        /**/
        patron.setLocalizedLabel(ENGLISH, "pattern");
        patron.setLocalizedLabel(SPANISH, "patrón");
        /**/
        // </editor-fold>
        /**/
    }

}
