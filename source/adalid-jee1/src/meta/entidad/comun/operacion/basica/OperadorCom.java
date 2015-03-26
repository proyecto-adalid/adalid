/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package meta.entidad.comun.operacion.basica;

import adalid.core.*;
import adalid.core.annotations.*;
import adalid.core.enums.*;
import adalid.core.interfaces.*;
import adalid.core.properties.*;
import java.lang.reflect.Field;
import meta.proyecto.base.ProyectoBase;

/**
 * @author Jorge Campins
 */
@EntityClass(independent = Kleenean.TRUE, resourceType = ResourceType.CONFIGURATION, resourceGender = ResourceGender.MASCULINE)
@EntitySelectOperation(enabled = Kleenean.TRUE, access = OperationAccess.PUBLIC, rowsLimit = 0)
@EntityInsertOperation(enabled = Kleenean.FALSE)
@EntityUpdateOperation(enabled = Kleenean.FALSE)
@EntityDeleteOperation(enabled = Kleenean.FALSE)
@EntityTableView(enabled = Kleenean.FALSE)
@EntityDetailView(enabled = Kleenean.FALSE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
public class OperadorCom extends AbstractPersistentEnumerationEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    @Deprecated
    private OperadorCom() {
        this(null, null);
    }

    public OperadorCom(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public IntegerProperty numeroOperadorCom;

    @BusinessKey
    public StringProperty codigoOperadorCom;

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
        setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        setDefaultLabel("operador de comparación");
        setDefaultShortLabel("operador");
        setDefaultCollectionLabel("operadores de comparación");
        setDefaultCollectionShortLabel("operadores");
    }

    @Override
    protected void settleInstances() {
        super.settleInstances();
        ES_NULO.newInstanceField(numeroOperadorCom, 1);
        ES_NULO.newInstanceField(codigoOperadorCom, "Es nulo");
        NO_ES_NULO.newInstanceField(numeroOperadorCom, 2);
        NO_ES_NULO.newInstanceField(codigoOperadorCom, "No es nulo");
        ES_VERDADERO.newInstanceField(numeroOperadorCom, 3);
        ES_VERDADERO.newInstanceField(codigoOperadorCom, "Es verdadero");
        NO_ES_VERDADERO.newInstanceField(numeroOperadorCom, 4);
        NO_ES_VERDADERO.newInstanceField(codigoOperadorCom, "No es verdadero");
        ES_FALSO.newInstanceField(numeroOperadorCom, 5);
        ES_FALSO.newInstanceField(codigoOperadorCom, "Es falso");
        NO_ES_FALSO.newInstanceField(numeroOperadorCom, 6);
        NO_ES_FALSO.newInstanceField(codigoOperadorCom, "No es falso");
        ES_IGUAL.newInstanceField(numeroOperadorCom, 11);
        ES_IGUAL.newInstanceField(codigoOperadorCom, "Es igual");
        NO_ES_IGUAL.newInstanceField(numeroOperadorCom, 12);
        NO_ES_IGUAL.newInstanceField(codigoOperadorCom, "No es igual");
        ES_MAYOR.newInstanceField(numeroOperadorCom, 21);
        ES_MAYOR.newInstanceField(codigoOperadorCom, "Es mayor");
        ES_MENOR_O_IGUAL.newInstanceField(numeroOperadorCom, 22);
        ES_MENOR_O_IGUAL.newInstanceField(codigoOperadorCom, "Es menor o igual");
        ES_MAYOR_O_IGUAL.newInstanceField(numeroOperadorCom, 31);
        ES_MAYOR_O_IGUAL.newInstanceField(codigoOperadorCom, "Es mayor o igual");
        ES_MENOR.newInstanceField(numeroOperadorCom, 32);
        ES_MENOR.newInstanceField(codigoOperadorCom, "Es menor");
        COMIENZA_POR.newInstanceField(numeroOperadorCom, 41);
        COMIENZA_POR.newInstanceField(codigoOperadorCom, "Comienza por");
        NO_COMIENZA_POR.newInstanceField(numeroOperadorCom, 42);
        NO_COMIENZA_POR.newInstanceField(codigoOperadorCom, "No comienza por");
        CONTIENE.newInstanceField(numeroOperadorCom, 43);
        CONTIENE.newInstanceField(codigoOperadorCom, "Contiene");
        NO_CONTIENE.newInstanceField(numeroOperadorCom, 44);
        NO_CONTIENE.newInstanceField(codigoOperadorCom, "No contiene");
        TERMINA_EN.newInstanceField(numeroOperadorCom, 45);
        TERMINA_EN.newInstanceField(codigoOperadorCom, "Termina en");
        NO_TERMINA_EN.newInstanceField(numeroOperadorCom, 46);
        NO_TERMINA_EN.newInstanceField(codigoOperadorCom, "No termina en");
        ESTA_ENTRE.newInstanceField(numeroOperadorCom, 51);
        ESTA_ENTRE.newInstanceField(codigoOperadorCom, "Est\u00E1 entre");
        NO_ESTA_ENTRE.newInstanceField(numeroOperadorCom, 52);
        NO_ESTA_ENTRE.newInstanceField(codigoOperadorCom, "No est\u00E1 entre");
        ES_NULO_O_ES_IGUAL.newInstanceField(numeroOperadorCom, 111);
        ES_NULO_O_ES_IGUAL.newInstanceField(codigoOperadorCom, "Es nulo o es igual");
        ES_NULO_O_NO_ES_IGUAL.newInstanceField(numeroOperadorCom, 112);
        ES_NULO_O_NO_ES_IGUAL.newInstanceField(codigoOperadorCom, "Es nulo o no es igual");
        ES_NULO_O_ES_MAYOR.newInstanceField(numeroOperadorCom, 121);
        ES_NULO_O_ES_MAYOR.newInstanceField(codigoOperadorCom, "Es nulo o es mayor");
        ES_NULO_O_ES_MENOR_O_IGUAL.newInstanceField(numeroOperadorCom, 122);
        ES_NULO_O_ES_MENOR_O_IGUAL.newInstanceField(codigoOperadorCom, "Es nulo o es menor o igual");
        ES_NULO_O_ES_MAYOR_O_IGUAL.newInstanceField(numeroOperadorCom, 131);
        ES_NULO_O_ES_MAYOR_O_IGUAL.newInstanceField(codigoOperadorCom, "Es nulo o es mayor o igual");
        ES_NULO_O_ES_MENOR.newInstanceField(numeroOperadorCom, 132);
        ES_NULO_O_ES_MENOR.newInstanceField(codigoOperadorCom, "Es nulo o es menor");
        ES_NULO_O_COMIENZA_POR.newInstanceField(numeroOperadorCom, 141);
        ES_NULO_O_COMIENZA_POR.newInstanceField(codigoOperadorCom, "Es nulo o comienza por");
        ES_NULO_O_NO_COMIENZA_POR.newInstanceField(numeroOperadorCom, 142);
        ES_NULO_O_NO_COMIENZA_POR.newInstanceField(codigoOperadorCom, "Es nulo o no comienza por");
        ES_NULO_O_CONTIENE.newInstanceField(numeroOperadorCom, 143);
        ES_NULO_O_CONTIENE.newInstanceField(codigoOperadorCom, "Es nulo o contiene");
        ES_NULO_O_NO_CONTIENE.newInstanceField(numeroOperadorCom, 144);
        ES_NULO_O_NO_CONTIENE.newInstanceField(codigoOperadorCom, "Es nulo o no contiene");
        ES_NULO_O_TERMINA_EN.newInstanceField(numeroOperadorCom, 145);
        ES_NULO_O_TERMINA_EN.newInstanceField(codigoOperadorCom, "Es nulo o termina en");
        ES_NULO_O_NO_TERMINA_EN.newInstanceField(numeroOperadorCom, 146);
        ES_NULO_O_NO_TERMINA_EN.newInstanceField(codigoOperadorCom, "Es nulo o no termina en");
        ES_NULO_O_ESTA_ENTRE.newInstanceField(numeroOperadorCom, 151);
        ES_NULO_O_ESTA_ENTRE.newInstanceField(codigoOperadorCom, "Es nulo o est\u00E1 entre");
        ES_NULO_O_NO_ESTA_ENTRE.newInstanceField(numeroOperadorCom, 152);
        ES_NULO_O_NO_ESTA_ENTRE.newInstanceField(codigoOperadorCom, "Es nulo o no est\u00E1 entre");
        EXISTE.newInstanceField(numeroOperadorCom, 161);
        EXISTE.newInstanceField(codigoOperadorCom, "Existe");
        NO_EXISTE.newInstanceField(numeroOperadorCom, 162);
        NO_EXISTE.newInstanceField(codigoOperadorCom, "No existe");
    }

}
