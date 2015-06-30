/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package meta.entidad.comun.configuracion.basica;

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
@EntityClass(independent = Kleenean.TRUE, resourceType = ResourceType.CONFIGURATION, resourceGender = ResourceGender.MASCULINE)
@EntitySelectOperation(enabled = Kleenean.TRUE, access = OperationAccess.PUBLIC, rowsLimit = 0)
@EntityInsertOperation(enabled = Kleenean.FALSE)
@EntityUpdateOperation(enabled = Kleenean.FALSE)
@EntityDeleteOperation(enabled = Kleenean.FALSE)
@EntityTableView(enabled = Kleenean.FALSE)
@EntityDetailView(enabled = Kleenean.FALSE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
public class TipoDatoPar extends AbstractPersistentEnumerationEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    @Deprecated
    private TipoDatoPar() {
        this(null, null);
    }

    public TipoDatoPar(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public IntegerProperty numeroTipoDatoPar;

    @BusinessKey
    public StringProperty codigoTipoDatoPar;

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
        setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        setDefaultLabel("tipo de dato de parámetro");
        setDefaultShortLabel("tipo de dato");
        setDefaultCollectionLabel("Tipos de Dato de Parámetro");
        setDefaultCollectionShortLabel("Tipos de Dato");
    }

    @Override
    protected void settleInstances() {
        super.settleInstances();
        ALFANUMERICO.newInstanceField(numeroTipoDatoPar, 1);
        ALFANUMERICO.newInstanceField(codigoTipoDatoPar, "Alfanum\u00E9rico");
        NUMERICO.newInstanceField(numeroTipoDatoPar, 2);
        NUMERICO.newInstanceField(codigoTipoDatoPar, "Num\u00E9rico");
        FECHA_HORA.newInstanceField(numeroTipoDatoPar, 3);
        FECHA_HORA.newInstanceField(codigoTipoDatoPar, "Fecha/Hora");
        ENTERO.newInstanceField(numeroTipoDatoPar, 4);
        ENTERO.newInstanceField(codigoTipoDatoPar, "Entero");
        ENTERO_GRANDE.newInstanceField(numeroTipoDatoPar, 5);
        ENTERO_GRANDE.newInstanceField(codigoTipoDatoPar, "Entero Grande");
        LOGICO.newInstanceField(numeroTipoDatoPar, 6);
        LOGICO.newInstanceField(codigoTipoDatoPar, "Lógico");
        BINARIO.newInstanceField(numeroTipoDatoPar, 7);
        BINARIO.newInstanceField(codigoTipoDatoPar, "Binario");
    }

}
