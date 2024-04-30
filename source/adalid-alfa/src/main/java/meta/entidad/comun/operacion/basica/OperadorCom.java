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
//@EntityReferenceDisplay(filterType = EntityReferenceFilterType.TEXTBOX)
public class OperadorCom extends AbstractPersistentEnumerationEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public OperadorCom(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public IntegerProperty numero;

    @BusinessKey
    public StringProperty codigo;

    @ColumnField(nullable = Kleenean.FALSE)
    public BooleanProperty necesitaValor;

    @StringField(maxLength = 10)
    public StringProperty rangos;

    public Instance ES_NULO;

    public Instance NO_ES_NULO;

    public Instance ES_VERDADERO;

    public Instance NO_ES_VERDADERO;

    public Instance ES_FALSO;

    public Instance NO_ES_FALSO;

    public Instance ES_IGUAL;

    public Instance NO_ES_IGUAL;

    public Instance ES_MAYOR;

    public Instance ES_MENOR_O_IGUAL;

    public Instance ES_MAYOR_O_IGUAL;

    public Instance ES_MENOR;

    public Instance COMIENZA_POR;

    public Instance NO_COMIENZA_POR;

    public Instance CONTIENE;

    public Instance NO_CONTIENE;

    public Instance TERMINA_EN;

    public Instance NO_TERMINA_EN;

    public Instance ESTA_ENTRE;

    public Instance NO_ESTA_ENTRE;

    public Instance ES_NULO_O_ES_IGUAL;

    public Instance ES_NULO_O_NO_ES_IGUAL;

    public Instance ES_NULO_O_ES_MAYOR;

    public Instance ES_NULO_O_ES_MENOR_O_IGUAL;

    public Instance ES_NULO_O_ES_MAYOR_O_IGUAL;

    public Instance ES_NULO_O_ES_MENOR;

    public Instance ES_NULO_O_COMIENZA_POR;

    public Instance ES_NULO_O_NO_COMIENZA_POR;

    public Instance ES_NULO_O_CONTIENE;

    public Instance ES_NULO_O_NO_CONTIENE;

    public Instance ES_NULO_O_TERMINA_EN;

    public Instance ES_NULO_O_NO_TERMINA_EN;

    public Instance ES_NULO_O_ESTA_ENTRE;

    public Instance ES_NULO_O_NO_ESTA_ENTRE;

    public Instance EXISTE;

    public Instance NO_EXISTE;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of OperadorCom's attributes">
        setLocalizedLabel(ENGLISH, "comparison operator");
        setLocalizedLabel(SPANISH, "operador de comparación");
        setLocalizedShortLabel(ENGLISH, "operator");
        setLocalizedShortLabel(SPANISH, "operador");
        setLocalizedCollectionLabel(ENGLISH, "Comparison Operators");
        setLocalizedCollectionLabel(SPANISH, "Operadores de Comparación");
        setLocalizedCollectionShortLabel(ENGLISH, "Operators");
        setLocalizedCollectionShortLabel(SPANISH, "Operadores");
        // </editor-fold>
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        necesitaValor.setDefaultValue(true);
        // <editor-fold defaultstate="collapsed" desc="localization of OperadorCom's properties">
        /**/
        numero.setLocalizedLabel(ENGLISH, "comparison operator number");
        numero.setLocalizedLabel(SPANISH, "número del operador de comparación");
        numero.setLocalizedShortLabel(ENGLISH, "number");
        numero.setLocalizedShortLabel(SPANISH, "número");
        /**/
        codigo.setLocalizedLabel(ENGLISH, "comparison operator code");
        codigo.setLocalizedLabel(SPANISH, "código del operador de comparación");
        codigo.setLocalizedShortLabel(ENGLISH, "code");
        codigo.setLocalizedShortLabel(SPANISH, "código");
        /**/
        necesitaValor.setLocalizedLabel(ENGLISH, "value required");
        necesitaValor.setLocalizedLabel(SPANISH, "necesita valor");
        /**/
        rangos.setLocalizedLabel(ENGLISH, "ranges");
        rangos.setLocalizedLabel(SPANISH, "rangos");
        /**/
        // </editor-fold>
    }

    @Override
    protected void settleInstances() {
        super.settleInstances();
        /**/
        ES_NULO.newInstanceField(numero, 1);
        ES_NULO.newInstanceField(codigo, "Es nulo");
        NO_ES_NULO.newInstanceField(numero, 2);
        NO_ES_NULO.newInstanceField(codigo, "No es nulo");
        ES_VERDADERO.newInstanceField(numero, 3);
        ES_VERDADERO.newInstanceField(codigo, "Es verdadero");
        NO_ES_VERDADERO.newInstanceField(numero, 4);
        NO_ES_VERDADERO.newInstanceField(codigo, "No es verdadero");
        ES_FALSO.newInstanceField(numero, 5);
        ES_FALSO.newInstanceField(codigo, "Es falso");
        NO_ES_FALSO.newInstanceField(numero, 6);
        NO_ES_FALSO.newInstanceField(codigo, "No es falso");
        ES_IGUAL.newInstanceField(numero, 11);
        ES_IGUAL.newInstanceField(codigo, "Es igual a");
        NO_ES_IGUAL.newInstanceField(numero, 12);
        NO_ES_IGUAL.newInstanceField(codigo, "No es igual a");
        ES_MAYOR.newInstanceField(numero, 21);
        ES_MAYOR.newInstanceField(codigo, "Es mayor que");
        ES_MENOR_O_IGUAL.newInstanceField(numero, 22);
        ES_MENOR_O_IGUAL.newInstanceField(codigo, "Es menor o igual que");
        ES_MAYOR_O_IGUAL.newInstanceField(numero, 31);
        ES_MAYOR_O_IGUAL.newInstanceField(codigo, "Es mayor o igual que");
        ES_MENOR.newInstanceField(numero, 32);
        ES_MENOR.newInstanceField(codigo, "Es menor que");
        COMIENZA_POR.newInstanceField(numero, 41);
        COMIENZA_POR.newInstanceField(codigo, "Comienza por");
        NO_COMIENZA_POR.newInstanceField(numero, 42);
        NO_COMIENZA_POR.newInstanceField(codigo, "No comienza por");
        CONTIENE.newInstanceField(numero, 43);
        CONTIENE.newInstanceField(codigo, "Contiene");
        NO_CONTIENE.newInstanceField(numero, 44);
        NO_CONTIENE.newInstanceField(codigo, "No contiene");
        TERMINA_EN.newInstanceField(numero, 45);
        TERMINA_EN.newInstanceField(codigo, "Termina en");
        NO_TERMINA_EN.newInstanceField(numero, 46);
        NO_TERMINA_EN.newInstanceField(codigo, "No termina en");
        ESTA_ENTRE.newInstanceField(numero, 51);
        ESTA_ENTRE.newInstanceField(codigo, "Est\u00E1 entre");
        NO_ESTA_ENTRE.newInstanceField(numero, 52);
        NO_ESTA_ENTRE.newInstanceField(codigo, "No est\u00E1 entre");
        ES_NULO_O_ES_IGUAL.newInstanceField(numero, 111);
        ES_NULO_O_ES_IGUAL.newInstanceField(codigo, "Es nulo o es igual a");
        ES_NULO_O_NO_ES_IGUAL.newInstanceField(numero, 112);
        ES_NULO_O_NO_ES_IGUAL.newInstanceField(codigo, "Es nulo o no es igual a");
        ES_NULO_O_ES_MAYOR.newInstanceField(numero, 121);
        ES_NULO_O_ES_MAYOR.newInstanceField(codigo, "Es nulo o es mayor que");
        ES_NULO_O_ES_MENOR_O_IGUAL.newInstanceField(numero, 122);
        ES_NULO_O_ES_MENOR_O_IGUAL.newInstanceField(codigo, "Es nulo o es menor o igual que");
        ES_NULO_O_ES_MAYOR_O_IGUAL.newInstanceField(numero, 131);
        ES_NULO_O_ES_MAYOR_O_IGUAL.newInstanceField(codigo, "Es nulo o es mayor o igual que");
        ES_NULO_O_ES_MENOR.newInstanceField(numero, 132);
        ES_NULO_O_ES_MENOR.newInstanceField(codigo, "Es nulo o es menor que");
        ES_NULO_O_COMIENZA_POR.newInstanceField(numero, 141);
        ES_NULO_O_COMIENZA_POR.newInstanceField(codigo, "Es nulo o comienza por");
        ES_NULO_O_NO_COMIENZA_POR.newInstanceField(numero, 142);
        ES_NULO_O_NO_COMIENZA_POR.newInstanceField(codigo, "Es nulo o no comienza por");
        ES_NULO_O_CONTIENE.newInstanceField(numero, 143);
        ES_NULO_O_CONTIENE.newInstanceField(codigo, "Es nulo o contiene");
        ES_NULO_O_NO_CONTIENE.newInstanceField(numero, 144);
        ES_NULO_O_NO_CONTIENE.newInstanceField(codigo, "Es nulo o no contiene");
        ES_NULO_O_TERMINA_EN.newInstanceField(numero, 145);
        ES_NULO_O_TERMINA_EN.newInstanceField(codigo, "Es nulo o termina en");
        ES_NULO_O_NO_TERMINA_EN.newInstanceField(numero, 146);
        ES_NULO_O_NO_TERMINA_EN.newInstanceField(codigo, "Es nulo o no termina en");
        ES_NULO_O_ESTA_ENTRE.newInstanceField(numero, 151);
        ES_NULO_O_ESTA_ENTRE.newInstanceField(codigo, "Es nulo o est\u00E1 entre");
        ES_NULO_O_NO_ESTA_ENTRE.newInstanceField(numero, 152);
        ES_NULO_O_NO_ESTA_ENTRE.newInstanceField(codigo, "Es nulo o no est\u00E1 entre");
        EXISTE.newInstanceField(numero, 161);
        EXISTE.newInstanceField(codigo, "Existe");
        NO_EXISTE.newInstanceField(numero, 162);
        NO_EXISTE.newInstanceField(codigo, "No existe");
        /**/
        ES_NULO.newInstanceField(necesitaValor, false);
        NO_ES_NULO.newInstanceField(necesitaValor, false);
        ES_VERDADERO.newInstanceField(necesitaValor, false);
        NO_ES_VERDADERO.newInstanceField(necesitaValor, false);
        ES_FALSO.newInstanceField(necesitaValor, false);
        NO_ES_FALSO.newInstanceField(necesitaValor, false);
        /**/
        ES_NULO.newInstanceField(rangos, "0123");
        NO_ES_NULO.newInstanceField(rangos, "0123");
        ES_VERDADERO.newInstanceField(rangos, "1");
        NO_ES_VERDADERO.newInstanceField(rangos, "1");
        ES_FALSO.newInstanceField(rangos, "1");
        NO_ES_FALSO.newInstanceField(rangos, "1");
        ES_IGUAL.newInstanceField(rangos, "023");
        NO_ES_IGUAL.newInstanceField(rangos, "023");
        ES_MAYOR.newInstanceField(rangos, "23");
        ES_MENOR_O_IGUAL.newInstanceField(rangos, "23");
        ES_MAYOR_O_IGUAL.newInstanceField(rangos, "23");
        ES_MENOR.newInstanceField(rangos, "23");
        COMIENZA_POR.newInstanceField(rangos, "2");
        NO_COMIENZA_POR.newInstanceField(rangos, "2");
        CONTIENE.newInstanceField(rangos, "2");
        NO_CONTIENE.newInstanceField(rangos, "2");
        TERMINA_EN.newInstanceField(rangos, "2");
        NO_TERMINA_EN.newInstanceField(rangos, "2");
        ES_NULO_O_ES_IGUAL.newInstanceField(rangos, "023");
        ES_NULO_O_NO_ES_IGUAL.newInstanceField(rangos, "023");
        ES_NULO_O_ES_MAYOR.newInstanceField(rangos, "23");
        ES_NULO_O_ES_MENOR_O_IGUAL.newInstanceField(rangos, "23");
        ES_NULO_O_ES_MAYOR_O_IGUAL.newInstanceField(rangos, "23");
        ES_NULO_O_ES_MENOR.newInstanceField(rangos, "23");
        ES_NULO_O_COMIENZA_POR.newInstanceField(rangos, "2");
        ES_NULO_O_NO_COMIENZA_POR.newInstanceField(rangos, "2");
        ES_NULO_O_CONTIENE.newInstanceField(rangos, "2");
        ES_NULO_O_NO_CONTIENE.newInstanceField(rangos, "2");
        ES_NULO_O_TERMINA_EN.newInstanceField(rangos, "2");
        ES_NULO_O_NO_TERMINA_EN.newInstanceField(rangos, "2");
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of OperadorCom's instances">
        /**/
        ES_NULO.newInstanceField(codigo, "Is null", ENGLISH);
        ES_NULO.newInstanceField(codigo, "Es nulo", SPANISH);
        /**/
        NO_ES_NULO.newInstanceField(codigo, "Is not null", ENGLISH);
        NO_ES_NULO.newInstanceField(codigo, "No es nulo", SPANISH);
        /**/
        ES_VERDADERO.newInstanceField(codigo, "Is true", ENGLISH);
        ES_VERDADERO.newInstanceField(codigo, "Es verdadero", SPANISH);
        /**/
        NO_ES_VERDADERO.newInstanceField(codigo, "Is not true", ENGLISH);
        NO_ES_VERDADERO.newInstanceField(codigo, "No es verdadero", SPANISH);
        /**/
        ES_FALSO.newInstanceField(codigo, "Is false", ENGLISH);
        ES_FALSO.newInstanceField(codigo, "Es falso", SPANISH);
        /**/
        NO_ES_FALSO.newInstanceField(codigo, "Is not false", ENGLISH);
        NO_ES_FALSO.newInstanceField(codigo, "No es falso", SPANISH);
        /**/
        ES_IGUAL.newInstanceField(codigo, "Is equal to", ENGLISH);
        ES_IGUAL.newInstanceField(codigo, "Es igual a", SPANISH);
        /**/
        NO_ES_IGUAL.newInstanceField(codigo, "Is not equal to", ENGLISH);
        NO_ES_IGUAL.newInstanceField(codigo, "No es igual a", SPANISH);
        /**/
        ES_MAYOR.newInstanceField(codigo, "Is greater than", ENGLISH);
        ES_MAYOR.newInstanceField(codigo, "Es mayor que", SPANISH);
        /**/
        ES_MENOR_O_IGUAL.newInstanceField(codigo, "Is less or equal to", ENGLISH);
        ES_MENOR_O_IGUAL.newInstanceField(codigo, "Es menor o igual que", SPANISH);
        /**/
        ES_MAYOR_O_IGUAL.newInstanceField(codigo, "Is greater or equal to", ENGLISH);
        ES_MAYOR_O_IGUAL.newInstanceField(codigo, "Es mayor o igual que", SPANISH);
        /**/
        ES_MENOR.newInstanceField(codigo, "Is less than", ENGLISH);
        ES_MENOR.newInstanceField(codigo, "Es menor que", SPANISH);
        /**/
        COMIENZA_POR.newInstanceField(codigo, "Starts with", ENGLISH);
        COMIENZA_POR.newInstanceField(codigo, "Comienza por", SPANISH);
        /**/
        NO_COMIENZA_POR.newInstanceField(codigo, "Doesn't start with", ENGLISH);
        NO_COMIENZA_POR.newInstanceField(codigo, "No comienza por", SPANISH);
        /**/
        CONTIENE.newInstanceField(codigo, "Contains", ENGLISH);
        CONTIENE.newInstanceField(codigo, "Contiene", SPANISH);
        /**/
        NO_CONTIENE.newInstanceField(codigo, "Doesn't contain", ENGLISH);
        NO_CONTIENE.newInstanceField(codigo, "No contiene", SPANISH);
        /**/
        TERMINA_EN.newInstanceField(codigo, "Ends with", ENGLISH);
        TERMINA_EN.newInstanceField(codigo, "Termina en", SPANISH);
        /**/
        NO_TERMINA_EN.newInstanceField(codigo, "Doesn't end with", ENGLISH);
        NO_TERMINA_EN.newInstanceField(codigo, "No termina en", SPANISH);
        /**/
        ESTA_ENTRE.newInstanceField(codigo, "Is between", ENGLISH);
        ESTA_ENTRE.newInstanceField(codigo, "Está entre", SPANISH);
        /**/
        NO_ESTA_ENTRE.newInstanceField(codigo, "Is not between", ENGLISH);
        NO_ESTA_ENTRE.newInstanceField(codigo, "No está entre", SPANISH);
        /**/
        ES_NULO_O_ES_IGUAL.newInstanceField(codigo, "Is null or equal to", ENGLISH);
        ES_NULO_O_ES_IGUAL.newInstanceField(codigo, "Es nulo o es igual a", SPANISH);
        /**/
        ES_NULO_O_NO_ES_IGUAL.newInstanceField(codigo, "Is null or not equal to", ENGLISH);
        ES_NULO_O_NO_ES_IGUAL.newInstanceField(codigo, "Es nulo o no es igual a", SPANISH);
        /**/
        ES_NULO_O_ES_MAYOR.newInstanceField(codigo, "Is null or greater than", ENGLISH);
        ES_NULO_O_ES_MAYOR.newInstanceField(codigo, "Es nulo o es mayor que", SPANISH);
        /**/
        ES_NULO_O_ES_MENOR_O_IGUAL.newInstanceField(codigo, "Is null or less or equal to", ENGLISH);
        ES_NULO_O_ES_MENOR_O_IGUAL.newInstanceField(codigo, "Es nulo o es menor o igual que", SPANISH);
        /**/
        ES_NULO_O_ES_MAYOR_O_IGUAL.newInstanceField(codigo, "Is null or greater or equal to", ENGLISH);
        ES_NULO_O_ES_MAYOR_O_IGUAL.newInstanceField(codigo, "Es nulo o es mayor o igual que", SPANISH);
        /**/
        ES_NULO_O_ES_MENOR.newInstanceField(codigo, "Is null or less than", ENGLISH);
        ES_NULO_O_ES_MENOR.newInstanceField(codigo, "Es nulo o es menor que", SPANISH);
        /**/
        ES_NULO_O_COMIENZA_POR.newInstanceField(codigo, "Is null or starts with", ENGLISH);
        ES_NULO_O_COMIENZA_POR.newInstanceField(codigo, "Es nulo o comienza por", SPANISH);
        /**/
        ES_NULO_O_NO_COMIENZA_POR.newInstanceField(codigo, "Is null or doesn't start with", ENGLISH);
        ES_NULO_O_NO_COMIENZA_POR.newInstanceField(codigo, "Es nulo o no comienza por", SPANISH);
        /**/
        ES_NULO_O_CONTIENE.newInstanceField(codigo, "Is null or contains", ENGLISH);
        ES_NULO_O_CONTIENE.newInstanceField(codigo, "Es nulo o contiene", SPANISH);
        /**/
        ES_NULO_O_NO_CONTIENE.newInstanceField(codigo, "Is null or doesn't contain", ENGLISH);
        ES_NULO_O_NO_CONTIENE.newInstanceField(codigo, "Es nulo o no contiene", SPANISH);
        /**/
        ES_NULO_O_TERMINA_EN.newInstanceField(codigo, "Is null or ends with", ENGLISH);
        ES_NULO_O_TERMINA_EN.newInstanceField(codigo, "Es nulo o termina en", SPANISH);
        /**/
        ES_NULO_O_NO_TERMINA_EN.newInstanceField(codigo, "Is null or doesn't end with", ENGLISH);
        ES_NULO_O_NO_TERMINA_EN.newInstanceField(codigo, "Es nulo o no termina en", SPANISH);
        /**/
        ES_NULO_O_ESTA_ENTRE.newInstanceField(codigo, "Is null or between", ENGLISH);
        ES_NULO_O_ESTA_ENTRE.newInstanceField(codigo, "Es nulo o está entre", SPANISH);
        /**/
        ES_NULO_O_NO_ESTA_ENTRE.newInstanceField(codigo, "Is null or not between", ENGLISH);
        ES_NULO_O_NO_ESTA_ENTRE.newInstanceField(codigo, "Es nulo o no está entre", SPANISH);
        /**/
        EXISTE.newInstanceField(codigo, "Exists", ENGLISH);
        EXISTE.newInstanceField(codigo, "Existe", SPANISH);
        /**/
        NO_EXISTE.newInstanceField(codigo, "Doesn't exist", ENGLISH);
        NO_EXISTE.newInstanceField(codigo, "No existe", SPANISH);
        /**/
        // </editor-fold>
    }

}
