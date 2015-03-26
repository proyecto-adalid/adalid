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
public class TipoComparacion extends AbstractPersistentEnumerationEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    @Deprecated
    private TipoComparacion() {
        this(null, null);
    }

    public TipoComparacion(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public IntegerProperty numeroTipoComparacion;

    @BusinessKey
    public StringProperty codigoTipoComparacion;

    public Instance IGUAL_NO_IGUAL;

    public Instance MAYOR_MENOR_O_IGUAL;

    public Instance MAYOR_O_IGUAL_MENOR;

    public Instance COMIENZA_NO_COMIENZA;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        setDefaultLabel("tipo de comparación");
//      setDefaultShortLabel("tipo de comparación");
        setDefaultCollectionLabel("tipos de comparación");
//      setDefaultCollectionShortLabel("tipos de comparación");
    }

    @Override
    protected void settleInstances() {
        super.settleInstances();
        IGUAL_NO_IGUAL.newInstanceField(numeroTipoComparacion, 1);
        IGUAL_NO_IGUAL.newInstanceField(codigoTipoComparacion, "Igual/No igual");
        MAYOR_MENOR_O_IGUAL.newInstanceField(numeroTipoComparacion, 2);
        MAYOR_MENOR_O_IGUAL.newInstanceField(codigoTipoComparacion, "Mayor/Menor o igual");
        MAYOR_O_IGUAL_MENOR.newInstanceField(numeroTipoComparacion, 3);
        MAYOR_O_IGUAL_MENOR.newInstanceField(codigoTipoComparacion, "Mayor o igual/Menor");
        COMIENZA_NO_COMIENZA.newInstanceField(numeroTipoComparacion, 4);
        COMIENZA_NO_COMIENZA.newInstanceField(codigoTipoComparacion, "Comienza/No comienza");
    }

}
