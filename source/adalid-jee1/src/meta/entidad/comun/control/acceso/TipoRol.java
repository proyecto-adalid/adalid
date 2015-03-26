/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package meta.entidad.comun.control.acceso;

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
        setDefaultCollectionLabel("tipos de rol");
        setDefaultCollectionShortLabel("tipos");
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
