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
@EntityClass(independent = Kleenean.TRUE, resourceType = ResourceType.CONFIGURATION, resourceGender = ResourceGender.MASCULINE)
@EntityCodeGen(bws = Kleenean.FALSE, fws = Kleenean.FALSE)
@EntitySelectOperation(enabled = Kleenean.TRUE, access = OperationAccess.PUBLIC, rowsLimit = 0)
@EntityInsertOperation(enabled = Kleenean.FALSE)
@EntityUpdateOperation(enabled = Kleenean.FALSE)
@EntityDeleteOperation(enabled = Kleenean.FALSE)
@EntityTableView(enabled = Kleenean.FALSE)
@EntityDetailView(enabled = Kleenean.FALSE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
public class SubtipoGrafico extends AbstractPersistentEnumerationEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public SubtipoGrafico(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public IntegerProperty numero;

    @BusinessKey
    public StringProperty codigo;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(immutable = Kleenean.TRUE)
    @StringField(maxLength = 30)
    public StringProperty tipoJasper;

    public Instance SERIES_POR_AGREGACION;

    public Instance SERIES_POR_GRUPO;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of SubtipoGrafico's attributes">
        setLocalizedLabel(ENGLISH, "chart sybtype");
        setLocalizedLabel(SPANISH, "subtipo de gráfico");
        setLocalizedCollectionLabel(ENGLISH, "Chart Subtypes");
        setLocalizedCollectionLabel(SPANISH, "Subtipos de Gráfico");
        // </editor-fold>
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        // <editor-fold defaultstate="collapsed" desc="localization of SubtipoGrafico's properties">
        /**/
        numero.setLocalizedLabel(ENGLISH, "chart sybtype number");
        numero.setLocalizedLabel(SPANISH, "número del subtipo de gráfico");
        numero.setLocalizedShortLabel(ENGLISH, "number");
        numero.setLocalizedShortLabel(SPANISH, "número");
        /**/
        codigo.setLocalizedLabel(ENGLISH, "chart sybtype code");
        codigo.setLocalizedLabel(SPANISH, "código del subtipo de gráfico");
        codigo.setLocalizedShortLabel(ENGLISH, "code");
        codigo.setLocalizedShortLabel(SPANISH, "código");
        /**/
        tipoJasper.setLocalizedLabel(ENGLISH, "jasper type");
        tipoJasper.setLocalizedLabel(SPANISH, "tipo jasper");
        /**/
        // </editor-fold>
    }

    @Override
    protected void settleInstances() {
        super.settleInstances();
        /**/
        SERIES_POR_AGREGACION.newInstanceField(tipoJasper, "seriesByAggregate");
        SERIES_POR_GRUPO.newInstanceField(tipoJasper, "seriesByGroup");
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of SubtipoGrafico's instances">
        /**/
        SERIES_POR_AGREGACION.newInstanceField(codigo, "Series by Aggregation", ENGLISH);
        SERIES_POR_AGREGACION.newInstanceField(codigo, "Series por Agregación", SPANISH);
        /**/
        SERIES_POR_GRUPO.newInstanceField(codigo, "Series by Group", ENGLISH);
        SERIES_POR_GRUPO.newInstanceField(codigo, "Series por Grupo", SPANISH);
        /**/
        // </editor-fold>
    }

}
