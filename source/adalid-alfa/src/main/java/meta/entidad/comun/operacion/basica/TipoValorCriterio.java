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
@EntityReferenceDisplay(filterType = EntityReferenceFilterType.LIST)
@EntityReferenceSearch(listStyle = ListStyle.CHARACTER_KEY)
public class TipoValorCriterio extends AbstractPersistentEnumerationEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public TipoValorCriterio(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public IntegerProperty numero;

    @BusinessKey
    public StringProperty codigo;

    @NameProperty
    public StringProperty nombre;

    @ColumnField(nullable = Kleenean.TRUE)
    public CharacterProperty signo;

    @StringField(maxLength = 10)
    public StringProperty clases;

    public Instance SIMPLE, HOY_MAS, HOY_MENOS, AHORA_MAS, AHORA_MENOS;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of TipoValorCriterio's attributes">
        /**/
        setLocalizedLabel(ENGLISH, "temporal value type");
        setLocalizedLabel(SPANISH, "tipo de valor temporal");
        setLocalizedCollectionLabel(ENGLISH, "Temporal Value Types");
        setLocalizedCollectionLabel(SPANISH, "Tipos de Valor Temporal");
        /**/
        // </editor-fold>
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        // <editor-fold defaultstate="collapsed" desc="localization of TipoValorCriterio's properties">
        /**/
        numero.setLocalizedLabel(ENGLISH, "temporal value type number");
        numero.setLocalizedLabel(SPANISH, "número del tipo de valor temporal");
        numero.setLocalizedShortLabel(ENGLISH, "number");
        numero.setLocalizedShortLabel(SPANISH, "número");
        /**/
        codigo.setLocalizedLabel(ENGLISH, "temporal value type code");
        codigo.setLocalizedLabel(SPANISH, "código del tipo de valor temporal");
        codigo.setLocalizedShortLabel(ENGLISH, "code");
        codigo.setLocalizedShortLabel(SPANISH, "código");
        /**/
        // </editor-fold>
    }

    @Override
    protected void settleInstances() {
        super.settleInstances();
        /**/
        // ADVERTENCIA: los números se usan para dar valor a la propiedad tipos de la entidad CampoValorTemporal
        /**/
        SIMPLE.newInstanceField(numero, 0);
        HOY_MAS.newInstanceField(numero, 1);
        HOY_MENOS.newInstanceField(numero, 2);
        AHORA_MAS.newInstanceField(numero, 3);
        AHORA_MENOS.newInstanceField(numero, 4);
        /**/
        HOY_MAS.newInstanceField(signo, "+");
        HOY_MENOS.newInstanceField(signo, "-");
        AHORA_MAS.newInstanceField(signo, "+");
        AHORA_MENOS.newInstanceField(signo, "-");
        /**/
        HOY_MAS.newInstanceField(clases, "GO");
        HOY_MENOS.newInstanceField(clases, "GO");
        AHORA_MAS.newInstanceField(clases, "NO");
        AHORA_MENOS.newInstanceField(clases, "NO");
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of TipoValorCriterio's instances">
        /**/
        SIMPLE.newInstanceField(codigo, "Simple", ENGLISH);
        SIMPLE.newInstanceField(codigo, "Simple", SPANISH);
        /**/
        HOY_MAS.newInstanceField(codigo, "Today plus", ENGLISH);
        HOY_MAS.newInstanceField(codigo, "Hoy más", SPANISH);
        /**/
        HOY_MENOS.newInstanceField(codigo, "Today minus", ENGLISH);
        HOY_MENOS.newInstanceField(codigo, "Hoy menos", SPANISH);
        /**/
        AHORA_MAS.newInstanceField(codigo, "Now plus", ENGLISH);
        AHORA_MAS.newInstanceField(codigo, "Ahora más", SPANISH);
        /**/
        AHORA_MENOS.newInstanceField(codigo, "Now minus", ENGLISH);
        AHORA_MENOS.newInstanceField(codigo, "Ahora menos", SPANISH);
        /**/
        SIMPLE.newInstanceField(nombre, "Text, Number, Date and/or Time, or code that represents an Entity", ENGLISH);
        SIMPLE.newInstanceField(nombre, "Texto, Número, Fecha y/o Hora, o código que representa una Entidad", SPANISH);
        /**/
        HOY_MAS.newInstanceField(nombre, "Current date plus a time interval", ENGLISH);
        HOY_MAS.newInstanceField(nombre, "Fecha actual más un intervalo de tiempo", SPANISH);
        /**/
        HOY_MENOS.newInstanceField(nombre, "Current date minus a time interval", ENGLISH);
        HOY_MENOS.newInstanceField(nombre, "Fecha actual menos un intervalo de tiempo", SPANISH);
        /**/
        AHORA_MAS.newInstanceField(nombre, "Current date and time plus a time interval", ENGLISH);
        AHORA_MAS.newInstanceField(nombre, "Fecha y hora actual más un intervalo de tiempo", SPANISH);
        /**/
        AHORA_MENOS.newInstanceField(nombre, "Current date and time minus a time interval", ENGLISH);
        AHORA_MENOS.newInstanceField(nombre, "Fecha y hora actual menos un intervalo de tiempo", SPANISH);
        /**/
        // </editor-fold>
    }

}
