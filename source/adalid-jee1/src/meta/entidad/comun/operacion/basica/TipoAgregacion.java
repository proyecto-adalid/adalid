/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package meta.entidad.comun.operacion.basica;

import adalid.core.AbstractPersistentEnumerationEntity;
import adalid.core.Instance;
import adalid.core.annotations.BusinessKey;
import adalid.core.annotations.EntityClass;
import adalid.core.annotations.EntityConsoleView;
import adalid.core.annotations.EntityDeleteOperation;
import adalid.core.annotations.EntityDetailView;
import adalid.core.annotations.EntityInsertOperation;
import adalid.core.annotations.EntitySelectOperation;
import adalid.core.annotations.EntityTableView;
import adalid.core.annotations.EntityTreeView;
import adalid.core.annotations.EntityUpdateOperation;
import adalid.core.annotations.PrimaryKey;
import adalid.core.annotations.StringField;
import adalid.core.enums.Kleenean;
import adalid.core.enums.OperationAccess;
import adalid.core.enums.ResourceGender;
import adalid.core.enums.ResourceType;
import adalid.core.interfaces.Artifact;
import adalid.core.properties.IntegerProperty;
import adalid.core.properties.StringProperty;
import java.lang.reflect.Field;
import meta.proyecto.base.ProyectoBase;

/**
 * @author Jorge Campins
 */
@EntityClass(independent = Kleenean.TRUE, resourceType = ResourceType.CONFIGURATION, resourceGender = ResourceGender.MASCULINE, startWith = 0)
@EntitySelectOperation(enabled = Kleenean.TRUE, access = OperationAccess.PUBLIC, rowsLimit = 0)
@EntityInsertOperation(enabled = Kleenean.FALSE)
@EntityUpdateOperation(enabled = Kleenean.FALSE)
@EntityDeleteOperation(enabled = Kleenean.FALSE)
@EntityTableView(enabled = Kleenean.FALSE)
@EntityDetailView(enabled = Kleenean.FALSE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
public class TipoAgregacion extends AbstractPersistentEnumerationEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    @Deprecated()
    private TipoAgregacion() {
        this(null, null);
    }

    public TipoAgregacion(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public IntegerProperty numero;

    @BusinessKey
    @StringField(maxLength = 50)
    public StringProperty codigo;

    public Instance GRUPO;

    public Instance CUENTA;

    public Instance MINIMO;

    public Instance MAXIMO;

    public Instance SUMA;

    public Instance PROMEDIO;

    public Instance DESVIACION;

    public Instance CUENTA_MINIMO_MAXIMO;

    public Instance MINIMO_MAXIMO;

    public Instance SUMA_CUENTA_PROMEDIO;

    public Instance SUMA_CUENTA_PROMEDIO_DESVIACION_MINIMO_MAXIMO;

    public Instance PROMEDIO_DESVIACION;

    public Instance PROMEDIO_DESVIACION_MINIMO_MAXIMO;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        setDefaultLabel("tipo de agregación");
        setDefaultShortLabel("tipo");
        setDefaultCollectionLabel("Tipos de Agregación");
        setDefaultCollectionShortLabel("Tipos");
    }

    @Override
    protected void settleInstances() {
        super.settleInstances();
        /**/
        int grupo = 0;
        int cuenta = 1;
        int minimo = 2;
        int maximo = 4;
        int suma = 8;
        int promedio = 16;
        int desviacion = 32;
        /**/
        GRUPO.newInstanceField(numero, grupo);
        CUENTA.newInstanceField(numero, cuenta);
        MINIMO.newInstanceField(numero, minimo);
        MAXIMO.newInstanceField(numero, maximo);
        SUMA.newInstanceField(numero, suma);
        PROMEDIO.newInstanceField(numero, promedio);
        DESVIACION.newInstanceField(numero, desviacion);
        /**/
        CUENTA_MINIMO_MAXIMO.newInstanceField(numero, cuenta + minimo + maximo);
        MINIMO_MAXIMO.newInstanceField(numero, minimo + maximo);
        SUMA_CUENTA_PROMEDIO.newInstanceField(numero, suma + cuenta + promedio);
        SUMA_CUENTA_PROMEDIO_DESVIACION_MINIMO_MAXIMO.newInstanceField(numero, suma + cuenta + promedio + desviacion + minimo + maximo);
        PROMEDIO_DESVIACION.newInstanceField(numero, promedio + desviacion);
        PROMEDIO_DESVIACION_MINIMO_MAXIMO.newInstanceField(numero, promedio + desviacion + minimo + maximo);
        /**/
        MINIMO.newInstanceField(codigo, "Mínimo");
        MAXIMO.newInstanceField(codigo, "Máximo");
        DESVIACION.newInstanceField(codigo, "Desviación");
        /**/
        CUENTA_MINIMO_MAXIMO.newInstanceField(codigo, "Cuenta/Mínimo/Máximo");
        MINIMO_MAXIMO.newInstanceField(codigo, "Mínimo/Máximo");
        SUMA_CUENTA_PROMEDIO.newInstanceField(codigo, "Suma/Cuenta/Promedio");
        SUMA_CUENTA_PROMEDIO_DESVIACION_MINIMO_MAXIMO.newInstanceField(codigo, "Suma/Cuenta/Promedio/Desviación/Mínimo/Máximo");
        PROMEDIO_DESVIACION.newInstanceField(codigo, "Promedio/Desviación");
        PROMEDIO_DESVIACION_MINIMO_MAXIMO.newInstanceField(codigo, "Promedio/Desviación/Mínimo/Máximo");
    }

}
