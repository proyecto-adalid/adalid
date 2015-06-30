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
        setDefaultCollectionLabel("Tipos de Comparación");
//      setDefaultCollectionShortLabel("Tipos de Comparación");
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
