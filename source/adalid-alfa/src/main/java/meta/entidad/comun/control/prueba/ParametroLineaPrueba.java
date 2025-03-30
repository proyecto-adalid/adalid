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

import adalid.core.annotations.*;
import adalid.core.enums.*;
import adalid.core.interfaces.*;
import adalid.core.properties.*;
import java.lang.reflect.Field;
import meta.entidad.base.PersistentEntityBase;
import meta.entidad.comun.configuracion.basica.TipoDatoPar;

/**
 * @author Jorge Campins
 */
@EntityClass(independent = Kleenean.FALSE, resourceType = ResourceType.TESTING, resourceGender = ResourceGender.MASCULINE)
@EntityCodeGen(bws = Kleenean.FALSE, fws = Kleenean.FALSE)
@EntitySelectOperation(enabled = Kleenean.TRUE, onload = SelectOnloadOption.EXECUTE)
@EntityInsertOperation(enabled = Kleenean.TRUE)
@EntityUpdateOperation(enabled = Kleenean.TRUE)
@EntityDeleteOperation(enabled = Kleenean.TRUE)
@EntityTableView(enabled = Kleenean.TRUE, inserts = Kleenean.FALSE)
@EntityDetailView(enabled = Kleenean.TRUE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.TRUE)
//@EntityDataGen(start = 1, stop = 10000)
public class ParametroLineaPrueba extends PersistentEntityBase {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public ParametroLineaPrueba(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of ParametroLineaPrueba's attributes">
        setLocalizedLabel(ENGLISH, "test line execution parameter");
        setLocalizedLabel(SPANISH, "parámetro de ejecución de línea de prueba");
        setLocalizedShortLabel(ENGLISH, "parameter");
        setLocalizedShortLabel(SPANISH, "parámetro");
        setLocalizedCollectionLabel(ENGLISH, "Test Line Execution Parameters");
        setLocalizedCollectionLabel(SPANISH, "Parámetros de Ejecuciones de Líneas de Prueba");
        setLocalizedCollectionShortLabel(ENGLISH, "Parameters");
        setLocalizedCollectionShortLabel(SPANISH, "Parámetros");
        // </editor-fold>
    }

    @ColumnField(nullable = Kleenean.FALSE)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.TABLE_AND_DETAIL)
    @PropertyField(create = Kleenean.TRUE, update = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public EjecucionLineaPrueba ejecucion;

    @ColumnField(nullable = Kleenean.FALSE)
    @StringField(maxLength = 200)
    @PropertyField(create = Kleenean.TRUE)
    public StringProperty codigo;

    @StringField(maxLength = 200)
    @PropertyField(create = Kleenean.TRUE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public StringProperty nombre;

    @ColumnField(nullable = Kleenean.FALSE)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(create = Kleenean.TRUE)
    public TipoDatoPar tipo;

    @PropertyField(create = Kleenean.TRUE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    @StringField(maxLength = 0)
    public StringProperty valor;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(hidden = Kleenean.TRUE)
    public BooleanProperty registrado;

    @Override
    protected void settleProperties() {
        super.settleProperties();
        registrado.setInitialValue(false);
        registrado.setDefaultValue(false);
        // <editor-fold defaultstate="collapsed" desc="localization of ParametroLineaPrueba's properties">
        ejecucion.setLocalizedLabel(ENGLISH, "line execution");
        ejecucion.setLocalizedLabel(SPANISH, "ejecución de línea");
        /**/
        codigo.setLocalizedLabel(ENGLISH, "code");
        codigo.setLocalizedLabel(SPANISH, "código");
        /**/
        nombre.setLocalizedLabel(ENGLISH, "name");
        nombre.setLocalizedLabel(SPANISH, "nombre");
        /**/
        tipo.setLocalizedLabel(ENGLISH, "type");
        tipo.setLocalizedLabel(SPANISH, "tipo");
        /**/
        valor.setLocalizedLabel(ENGLISH, "value");
        valor.setLocalizedLabel(SPANISH, "valor");
        /**/
        registrado.setLocalizedLabel(ENGLISH, "registered");
        registrado.setLocalizedLabel(SPANISH, "registrado");
        // </editor-fold>
    }

    @Override
    protected void settleFilters() {
        super.settleFilters();
        setInsertFilter(ejecucion.funcional);
        setMasterDetailFilter(ejecucion.funcional);
    }

}
