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
//@EntityReferenceDisplay(filterType = EntityReferenceFilterType.LIST)
public class CampoValorTemporal extends AbstractPersistentEnumerationEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public CampoValorTemporal(Artifact declaringArtifact, Field declaringValue) {
        super(declaringArtifact, declaringValue);
    }
    // </editor-fold>

    @PrimaryKey
    public IntegerProperty numero;

    @BusinessKey
    public StringProperty codigo;

    @ColumnField(nullable = Kleenean.FALSE)
    @StringField(maxLength = 1)
    public StringProperty letra;

    @StringField(maxLength = 10)
    public StringProperty tipos;

    public Instance ANYS, MESES, DIAS, HORAS, MINUTOS, SEGUNDOS;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of CampoValorTemporal's attributes">
        setLocalizedLabel(ENGLISH, "temporal value field");
        setLocalizedLabel(SPANISH, "campo de valor temporal");
        setLocalizedCollectionLabel(ENGLISH, "Temporal Value Fields");
        setLocalizedCollectionLabel(SPANISH, "Campos de Valor Temporal");
        // </editor-fold>
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        // <editor-fold defaultstate="collapsed" desc="localization of CampoValorTemporal's properties">
        /**/
        numero.setLocalizedLabel(ENGLISH, "temporal value field number");
        numero.setLocalizedLabel(SPANISH, "número del campo de valor temporal");
        numero.setLocalizedShortLabel(ENGLISH, "number");
        numero.setLocalizedShortLabel(SPANISH, "número");
        /**/
        codigo.setLocalizedLabel(ENGLISH, "temporal value field code");
        codigo.setLocalizedLabel(SPANISH, "código del campo de valor temporal");
        codigo.setLocalizedShortLabel(ENGLISH, "code");
        codigo.setLocalizedShortLabel(SPANISH, "código");
        /**/
        // </editor-fold>
    }

    @Override
    protected void settleInstances() {
        super.settleInstances();
        /**/
        ANYS.newInstanceField(letra, "A");
        ANYS.newInstanceField(tipos, "12");
        /**/
        MESES.newInstanceField(letra, "M");
        MESES.newInstanceField(tipos, "12");
        /**/
        DIAS.newInstanceField(letra, "D");
        DIAS.newInstanceField(tipos, "12");
        /**/
        HORAS.newInstanceField(letra, "h");
        HORAS.newInstanceField(tipos, "34");
        /**/
        MINUTOS.newInstanceField(letra, "m");
        MINUTOS.newInstanceField(tipos, "34");
        /**/
        SEGUNDOS.newInstanceField(letra, "s");
        SEGUNDOS.newInstanceField(tipos, "34");
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of CampoValorTemporal's instances">
        /**/
        ANYS.newInstanceField(codigo, "Years", ENGLISH);
        ANYS.newInstanceField(codigo, "Años", SPANISH);
        /**/
        MESES.newInstanceField(codigo, "Months", ENGLISH);
        MESES.newInstanceField(codigo, "Meses", SPANISH);
        /**/
        DIAS.newInstanceField(codigo, "Days", ENGLISH);
        DIAS.newInstanceField(codigo, "Días", SPANISH);
        /**/
        HORAS.newInstanceField(codigo, "Hours", ENGLISH);
        HORAS.newInstanceField(codigo, "Horas", SPANISH);
        /**/
        MINUTOS.newInstanceField(codigo, "Minutes", ENGLISH);
        MINUTOS.newInstanceField(codigo, "Minutos", SPANISH);
        /**/
        SEGUNDOS.newInstanceField(codigo, "Seconds", ENGLISH);
        SEGUNDOS.newInstanceField(codigo, "Segundos", SPANISH);
        /**/
        ANYS.newInstanceField(letra, "Y", ENGLISH);
        ANYS.newInstanceField(letra, "A", SPANISH);
        /**/
        // </editor-fold>
    }

}
