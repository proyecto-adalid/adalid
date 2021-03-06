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
@EntityClass(independent = Kleenean.TRUE, resourceType = ResourceType.CONFIGURATION, resourceGender = ResourceGender.FEMININE)
@EntitySelectOperation(enabled = Kleenean.TRUE, access = OperationAccess.PUBLIC, rowsLimit = 0)
@EntityInsertOperation(enabled = Kleenean.FALSE)
@EntityUpdateOperation(enabled = Kleenean.FALSE)
@EntityDeleteOperation(enabled = Kleenean.FALSE)
@EntityTableView(enabled = Kleenean.FALSE)
@EntityDetailView(enabled = Kleenean.FALSE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
public class CondicionEjeFun extends AbstractPersistentEnumerationEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    @Deprecated
    private CondicionEjeFun() {
        this(null, null);
    }

    public CondicionEjeFun(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public IntegerProperty numeroCondicionEjeFun;

    @BusinessKey
    public StringProperty codigoCondicionEjeFun;

    public Instance EJECUCION_PENDIENTE;

    public Instance EJECUCION_EN_PROGRESO;

    public Instance EJECUTADO_SIN_ERRORES;

    public Instance EJECUTADO_CON_ERRORES;

    public Instance EJECUCION_CANCELADA;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        setDefaultLabel("condici??n de ejecuci??n de funci??n");
        setDefaultShortLabel("condici??n");
        setDefaultCollectionLabel("Condiciones de Ejecuci??n de Funci??n");
        setDefaultCollectionShortLabel("Condiciones");
    }

    @Override
    protected void settleInstances() {
        super.settleInstances();
        EJECUCION_PENDIENTE.newInstanceField(numeroCondicionEjeFun, 11);
        EJECUCION_PENDIENTE.newInstanceField(codigoCondicionEjeFun, "Ejecuci\u00F3n pendiente");
        EJECUCION_EN_PROGRESO.newInstanceField(numeroCondicionEjeFun, 12);
        EJECUCION_EN_PROGRESO.newInstanceField(codigoCondicionEjeFun, "Ejecuci\u00F3n en progreso");
        EJECUTADO_SIN_ERRORES.newInstanceField(numeroCondicionEjeFun, 21);
        EJECUTADO_SIN_ERRORES.newInstanceField(codigoCondicionEjeFun, "Ejecutado sin errores");
        EJECUTADO_CON_ERRORES.newInstanceField(numeroCondicionEjeFun, 22);
        EJECUTADO_CON_ERRORES.newInstanceField(codigoCondicionEjeFun, "Ejecutado con errores");
        EJECUCION_CANCELADA.newInstanceField(numeroCondicionEjeFun, 23);
        EJECUCION_CANCELADA.newInstanceField(codigoCondicionEjeFun, "Ejecuci\u00F3n cancelada");
    }

}
