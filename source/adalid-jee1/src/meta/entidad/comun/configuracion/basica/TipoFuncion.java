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
public class TipoFuncion extends AbstractPersistentEnumerationEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    @Deprecated
    private TipoFuncion() {
        this(null, null);
    }

    public TipoFuncion(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public IntegerProperty numeroTipoFuncion;

    @BusinessKey
    public StringProperty codigoTipoFuncion;

    public Instance PROCEDIMIENTO_FILA;

    public Instance PROCEDIMIENTO_FILA_PARAMETROS;

    public Instance PROCEDIMIENTO_PARAMETROS;

    public Instance CONSULTA;

    public Instance INFORME;

    public Instance ARCHIVO;

    public Instance CREACION;

    public Instance MODIFICACION;

    public Instance ELIMINACION;

    public Instance PROCESO;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        setDefaultLabel("tipo de función");
        setDefaultShortLabel("tipo");
        setDefaultCollectionLabel("Tipos de Función");
        setDefaultCollectionShortLabel("Tipos");
    }

    @Override
    protected void settleInstances() {
        super.settleInstances();
        PROCEDIMIENTO_FILA.newInstanceField(numeroTipoFuncion, 11);
        PROCEDIMIENTO_FILA.newInstanceField(codigoTipoFuncion, "Procedimiento fila");
        PROCEDIMIENTO_FILA_PARAMETROS.newInstanceField(numeroTipoFuncion, 12);
        PROCEDIMIENTO_FILA_PARAMETROS.newInstanceField(codigoTipoFuncion, "Procedimiento fila+par\u00E1metros");
        PROCEDIMIENTO_PARAMETROS.newInstanceField(numeroTipoFuncion, 13);
        PROCEDIMIENTO_PARAMETROS.newInstanceField(codigoTipoFuncion, "Procedimiento par\u00E1metros");
        CONSULTA.newInstanceField(numeroTipoFuncion, 21);
        CONSULTA.newInstanceField(codigoTipoFuncion, "Consulta");
        INFORME.newInstanceField(numeroTipoFuncion, 22);
        INFORME.newInstanceField(codigoTipoFuncion, "Informe");
        ARCHIVO.newInstanceField(numeroTipoFuncion, 23);
        ARCHIVO.newInstanceField(codigoTipoFuncion, "Archivo");
        CREACION.newInstanceField(numeroTipoFuncion, 31);
        CREACION.newInstanceField(codigoTipoFuncion, "Creaci\u00F3n");
        MODIFICACION.newInstanceField(numeroTipoFuncion, 32);
        MODIFICACION.newInstanceField(codigoTipoFuncion, "Modificaci\u00F3n");
        ELIMINACION.newInstanceField(numeroTipoFuncion, 33);
        ELIMINACION.newInstanceField(codigoTipoFuncion, "Eliminaci\u00F3n");
        PROCESO.newInstanceField(numeroTipoFuncion, 41);
        PROCESO.newInstanceField(codigoTipoFuncion, "Proceso");
    }

}
