/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package meta.entidad.comun.configuracion.basica;

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
        setDefaultCollectionLabel("tipos de dato de parámetro");
        setDefaultCollectionShortLabel("tipos de dato");
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
