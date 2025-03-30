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
package meta.entidad.comun.operacion.basica;

import adalid.core.*;
import adalid.core.annotations.*;
import adalid.core.enums.*;
import adalid.core.interfaces.*;
import adalid.core.properties.*;
import java.lang.reflect.Field;

/**
 * @author Jorge Campins
 */
@EntityClass(independent = Kleenean.TRUE, resourceType = ResourceType.CONFIGURATION, resourceGender = ResourceGender.FEMININE)
@EntityCodeGen(bws = Kleenean.FALSE, fws = Kleenean.FALSE)
@EntitySelectOperation(enabled = Kleenean.TRUE, access = OperationAccess.PUBLIC, rowsLimit = 0)
@EntityInsertOperation(enabled = Kleenean.FALSE)
@EntityUpdateOperation(enabled = Kleenean.FALSE)
@EntityDeleteOperation(enabled = Kleenean.FALSE)
@EntityTableView(enabled = Kleenean.FALSE)
@EntityDetailView(enabled = Kleenean.FALSE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
@EntityReferenceDisplay(filterType = EntityReferenceFilterType.LIST)
public class CondicionTarea extends AbstractPersistentEnumerationEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public CondicionTarea(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public IntegerProperty numero;

    @BusinessKey
    public StringProperty codigo;

    public Instance ASIGNADA;

    public Instance DISPONIBLE;

    public Instance EJECUTADA;

    public Instance CANCELADA;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of CondicionTarea's attributes">
        setLocalizedLabel(ENGLISH, "task condition");
        setLocalizedLabel(SPANISH, "condición de tarea");
        setLocalizedShortLabel(ENGLISH, "condition");
        setLocalizedShortLabel(SPANISH, "condición");
        setLocalizedCollectionLabel(ENGLISH, "Task Conditions");
        setLocalizedCollectionLabel(SPANISH, "Condiciones de Tarea");
        setLocalizedCollectionShortLabel(ENGLISH, "Conditions");
        setLocalizedCollectionShortLabel(SPANISH, "Condiciones");
        // </editor-fold>
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        // <editor-fold defaultstate="collapsed" desc="localization of CondicionTarea's properties">
        numero.setLocalizedLabel(ENGLISH, "task condition number");
        numero.setLocalizedLabel(SPANISH, "número de la condición de tarea");
        numero.setLocalizedShortLabel(ENGLISH, "number");
        numero.setLocalizedShortLabel(SPANISH, "número");
        /**/
        codigo.setLocalizedLabel(ENGLISH, "task condition code");
        codigo.setLocalizedLabel(SPANISH, "código de la condición de tarea");
        codigo.setLocalizedShortLabel(ENGLISH, "code");
        codigo.setLocalizedShortLabel(SPANISH, "código");
        // </editor-fold>
    }

    @Override
    protected void settleInstances() {
        super.settleInstances();
        /**/
        ASIGNADA.setCustomTag(InstanceTag.LIGHT_GREEN.copy().setPillShaped(true));
        DISPONIBLE.setCustomTag(InstanceTag.BLUE.copy().setPillShaped(true));
        EJECUTADA.setCustomTag(InstanceTag.GREEN.copy().setPillShaped(true));
        CANCELADA.setCustomTag(InstanceTag.YELLOW.copy().setPillShaped(true));
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of CondicionTarea's instances">
        ASIGNADA.newInstanceField(codigo, "Assigned", ENGLISH);
        ASIGNADA.newInstanceField(codigo, "Asignada", SPANISH);
        /**/
        DISPONIBLE.newInstanceField(codigo, "Available", ENGLISH);
        DISPONIBLE.newInstanceField(codigo, "Disponible", SPANISH);
        /**/
        EJECUTADA.newInstanceField(codigo, "Executed", ENGLISH);
        EJECUTADA.newInstanceField(codigo, "Ejecutada", SPANISH);
        /**/
        CANCELADA.newInstanceField(codigo, "Cancelled", ENGLISH);
        CANCELADA.newInstanceField(codigo, "Cancelada", SPANISH);
        // </editor-fold>
    }

}
