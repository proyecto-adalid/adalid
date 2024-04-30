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
@EntityClass(independent = Kleenean.TRUE, resourceType = ResourceType.CONFIGURATION, resourceGender = ResourceGender.MASCULINE, startWith = 0)
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
public class TipoDatoPar extends AbstractPersistentEnumerationEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public TipoDatoPar(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public IntegerProperty numero;

    @BusinessKey
    public StringProperty codigo;

    public Instance INDETERMINADO;

    public Instance ALFANUMERICO;

    public Instance NUMERICO;

    public Instance FECHA_HORA;

    public Instance ENTERO;

    public Instance ENTERO_GRANDE;

    public Instance LOGICO;

    public Instance BINARIO;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of TipoDatoPar's attributes">
        setLocalizedLabel(ENGLISH, "parameter data type");
        setLocalizedLabel(SPANISH, "tipo de dato de parámetro");
        setLocalizedShortLabel(ENGLISH, "data type");
        setLocalizedShortLabel(SPANISH, "tipo de dato");
        setLocalizedCollectionLabel(ENGLISH, "Parameter Data Types");
        setLocalizedCollectionLabel(SPANISH, "Tipos de Dato de Parámetro");
        setLocalizedCollectionShortLabel(ENGLISH, "Data Types");
        setLocalizedCollectionShortLabel(SPANISH, "Tipos de Dato");
        // </editor-fold>
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        // <editor-fold defaultstate="collapsed" desc="localization of TipoDatoPar's properties">
        numero.setLocalizedLabel(ENGLISH, "parameter data type number");
        numero.setLocalizedLabel(SPANISH, "número del tipo de dato de parámetro");
        numero.setLocalizedShortLabel(ENGLISH, "number");
        numero.setLocalizedShortLabel(SPANISH, "número");
        /**/
        codigo.setLocalizedLabel(ENGLISH, "parameter data type code");
        codigo.setLocalizedLabel(SPANISH, "código del tipo de dato de parámetro");
        codigo.setLocalizedShortLabel(ENGLISH, "code");
        codigo.setLocalizedShortLabel(SPANISH, "código");
        // </editor-fold>
    }

    @Override
    protected void settleInstances() {
        super.settleInstances();
        INDETERMINADO.newInstanceField(numero, 0);
        ALFANUMERICO.newInstanceField(numero, 1);
        NUMERICO.newInstanceField(numero, 2);
        FECHA_HORA.newInstanceField(numero, 3);
        ENTERO.newInstanceField(numero, 4);
        ENTERO_GRANDE.newInstanceField(numero, 5);
        LOGICO.newInstanceField(numero, 6);
        BINARIO.newInstanceField(numero, 7);
        // <editor-fold defaultstate="collapsed" desc="localization of TipoDatoPar's instances">
        /**/
        INDETERMINADO.newInstanceField(codigo, "Indeterminate", ENGLISH);
        INDETERMINADO.newInstanceField(codigo, "Indeterminado", SPANISH);
        /**/
        ALFANUMERICO.newInstanceField(codigo, "Alphanumeric", ENGLISH);
        ALFANUMERICO.newInstanceField(codigo, "Alfanumérico", SPANISH);
        /**/
        NUMERICO.newInstanceField(codigo, "Numeric", ENGLISH);
        NUMERICO.newInstanceField(codigo, "Numérico", SPANISH);
        /**/
        FECHA_HORA.newInstanceField(codigo, "Timestamp", ENGLISH);
        FECHA_HORA.newInstanceField(codigo, "Fecha/Hora", SPANISH);
        /**/
        ENTERO.newInstanceField(codigo, "Integer", ENGLISH);
        ENTERO.newInstanceField(codigo, "Entero", SPANISH);
        /**/
        ENTERO_GRANDE.newInstanceField(codigo, "Big Integer", ENGLISH);
        ENTERO_GRANDE.newInstanceField(codigo, "Entero Grande", SPANISH);
        /**/
        LOGICO.newInstanceField(codigo, "Logic", ENGLISH);
        LOGICO.newInstanceField(codigo, "Lógico", SPANISH);
        /**/
        BINARIO.newInstanceField(codigo, "Binary", ENGLISH);
        BINARIO.newInstanceField(codigo, "Binario", SPANISH);
        // </editor-fold>
    }

}
