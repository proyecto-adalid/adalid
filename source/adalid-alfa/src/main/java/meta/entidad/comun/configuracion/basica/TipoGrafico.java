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
public class TipoGrafico extends AbstractPersistentEnumerationEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public TipoGrafico(Artifact declaringArtifact, Field declaringField) {
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

    public Instance BARRA;

    public Instance BARRA_APILADA;

    public Instance AREA;

    public Instance AREA_APILADA;

    public Instance LINEA;

    public Instance TORTA;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of TipoGrafico's attributes">
        setLocalizedLabel(ENGLISH, "chart type");
        setLocalizedLabel(SPANISH, "tipo de gráfico");
        setLocalizedCollectionLabel(ENGLISH, "Chart Types");
        setLocalizedCollectionLabel(SPANISH, "Tipos de Gráfico");
        // </editor-fold>
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        // <editor-fold defaultstate="collapsed" desc="localization of TipoGrafico's properties">
        /**/
        numero.setLocalizedLabel(ENGLISH, "chart type number");
        numero.setLocalizedLabel(SPANISH, "número del tipo de gráfico");
        numero.setLocalizedShortLabel(ENGLISH, "number");
        numero.setLocalizedShortLabel(SPANISH, "número");
        /**/
        codigo.setLocalizedLabel(ENGLISH, "chart type code");
        codigo.setLocalizedLabel(SPANISH, "código del tipo de gráfico");
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
        BARRA.newInstanceField(tipoJasper, "bar3DChart");
        BARRA_APILADA.newInstanceField(tipoJasper, "stackedBar3DChart");
        /**/
        AREA.newInstanceField(tipoJasper, "areaChart");
        AREA_APILADA.newInstanceField(tipoJasper, "stackedAreaChart");
        /**/
        LINEA.newInstanceField(tipoJasper, "lineChart");
        /**/
        TORTA.newInstanceField(tipoJasper, "pie3DChart");
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of TipoGrafico's instances">
        /**/
        BARRA.newInstanceField(codigo, "Bar", ENGLISH);
        BARRA.newInstanceField(codigo, "Barra", SPANISH);
        /**/
        BARRA_APILADA.newInstanceField(codigo, "Stacked Bar", ENGLISH);
        BARRA_APILADA.newInstanceField(codigo, "Barra Apilada", SPANISH);
        /**/
        AREA.newInstanceField(codigo, "Area", ENGLISH);
        AREA.newInstanceField(codigo, "Area", SPANISH);
        /**/
        AREA_APILADA.newInstanceField(codigo, "Stacked Area", ENGLISH);
        AREA_APILADA.newInstanceField(codigo, "Area Apilada", SPANISH);
        /**/
        LINEA.newInstanceField(codigo, "Line", ENGLISH);
        LINEA.newInstanceField(codigo, "Línea", SPANISH);
        /**/
        TORTA.newInstanceField(codigo, "Pie", ENGLISH);
        TORTA.newInstanceField(codigo, "Torta", SPANISH);
        /**/
        // </editor-fold>
    }

}
