/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package meta.entidad.comun.control.acceso;

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
public class TipoRol extends AbstractPersistentEnumerationEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    @Deprecated
    private TipoRol() {
        this(null, null);
    }

    public TipoRol(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public IntegerProperty numeroTipoRol;

    @BusinessKey
    public StringProperty codigoTipoRol;

    public Instance OPERADOR;

    public Instance REGISTRADOR;

    public Instance PROCESADOR;

    public Instance LECTOR;

    public Instance CONFIGURADOR;

    public Instance GESTOR;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        setDefaultLabel("tipo de rol");
        setDefaultShortLabel("tipo");
        setDefaultCollectionLabel("Tipos de Rol");
        setDefaultCollectionShortLabel("Tipos");
    }

    @Override
    protected void settleInstances() {
        super.settleInstances();
        OPERADOR.newInstanceField(numeroTipoRol, 0);
        OPERADOR.newInstanceField(codigoTipoRol, "Operador");
        REGISTRADOR.newInstanceField(numeroTipoRol, 1);
        REGISTRADOR.newInstanceField(codigoTipoRol, "Registrador");
        PROCESADOR.newInstanceField(numeroTipoRol, 2);
        PROCESADOR.newInstanceField(codigoTipoRol, "Procesador");
        LECTOR.newInstanceField(numeroTipoRol, 4);
        LECTOR.newInstanceField(codigoTipoRol, "Lector");
        CONFIGURADOR.newInstanceField(numeroTipoRol, 8);
        CONFIGURADOR.newInstanceField(codigoTipoRol, "Configurador");
        GESTOR.newInstanceField(numeroTipoRol, 16);
        GESTOR.newInstanceField(codigoTipoRol, "Gestor");
    }

}
