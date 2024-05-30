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

import adalid.core.*;
import adalid.core.annotations.*;
import adalid.core.enums.*;
import adalid.core.interfaces.*;
import adalid.core.properties.*;
import java.lang.reflect.Field;

/**
 * @author Jorge Campins
 */
@EntityClass(independent = Kleenean.TRUE, resourceType = ResourceType.CONFIGURATION, resourceGender = ResourceGender.FEMININE, startWith = 0)
@EntityCodeGen(bws = Kleenean.FALSE, fws = Kleenean.FALSE)
@EntitySelectOperation(enabled = Kleenean.TRUE, access = OperationAccess.PUBLIC, rowsLimit = 0)
@EntityInsertOperation(enabled = Kleenean.FALSE)
@EntityUpdateOperation(enabled = Kleenean.FALSE)
@EntityDeleteOperation(enabled = Kleenean.FALSE)
@EntityTableView(enabled = Kleenean.FALSE)
@EntityDetailView(enabled = Kleenean.FALSE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
public class SeveridadMensaje extends AbstractPersistentEnumerationEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public SeveridadMensaje(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public IntegerProperty numero;

    @BusinessKey
    public StringProperty codigo;

    public Instance INFORMATIVO;

    public Instance ADVERTENCIA;

    public Instance ERROR;

    public Instance FATAL;

    // <editor-fold defaultstate="collapsed" desc="instance getters">
    public Instance getInformativo() {
        return INFORMATIVO;
    }

    public Instance getAdvertencia() {
        return ADVERTENCIA;
    }

    public Instance getError() {
        return ERROR;
    }

    public Instance getFatal() {
        return FATAL;
    }
    // </editor-fold>

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of SeveridadMensaje's attributes">
        setLocalizedLabel(ENGLISH, "message severity");
        setLocalizedLabel(SPANISH, "severidad de mensaje");
        setLocalizedShortLabel(ENGLISH, "severity");
        setLocalizedShortLabel(SPANISH, "severidad");
        setLocalizedCollectionLabel(ENGLISH, "Message Severities");
        setLocalizedCollectionLabel(SPANISH, "Severidades de Mensaje");
        setLocalizedCollectionShortLabel(ENGLISH, "Severities");
        setLocalizedCollectionShortLabel(SPANISH, "Severidades");
        // </editor-fold>
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        // <editor-fold defaultstate="collapsed" desc="localization of SeveridadMensaje's properties">
        numero.setLocalizedLabel(ENGLISH, "message severity number");
        numero.setLocalizedLabel(SPANISH, "número de la severidad de mensaje");
        numero.setLocalizedShortLabel(ENGLISH, "number");
        numero.setLocalizedShortLabel(SPANISH, "número");
        /**/
        codigo.setLocalizedLabel(ENGLISH, "message severity code");
        codigo.setLocalizedLabel(SPANISH, "código de la severidad de mensaje");
        codigo.setLocalizedShortLabel(ENGLISH, "code");
        codigo.setLocalizedShortLabel(SPANISH, "código");
        // </editor-fold>
    }

    @Override
    protected void settleInstances() {
        super.settleInstances();
        // <editor-fold defaultstate="collapsed" desc="localization of SeveridadMensaje's instances">
        /**/
        INFORMATIVO.newInstanceField(codigo, "Informative", ENGLISH);
        INFORMATIVO.newInstanceField(codigo, "Informativo", SPANISH);
        /**/
        ADVERTENCIA.newInstanceField(codigo, "Warning", ENGLISH);
        ADVERTENCIA.newInstanceField(codigo, "Advertencia", SPANISH);
        /**/
        ERROR.newInstanceField(codigo, "Error", ENGLISH);
        ERROR.newInstanceField(codigo, "Error", SPANISH);
        /**/
        FATAL.newInstanceField(codigo, "Fatal", ENGLISH);
        FATAL.newInstanceField(codigo, "Fatal", SPANISH);
        /**/
        // </editor-fold>
    }

}
