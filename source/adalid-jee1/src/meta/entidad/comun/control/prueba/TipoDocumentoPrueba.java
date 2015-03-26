/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package meta.entidad.comun.control.prueba;

import adalid.core.*;
import adalid.core.annotations.*;
import adalid.core.enums.*;
import adalid.core.interfaces.*;
import java.lang.reflect.Field;
import meta.entidad.base.PersistentEnumerationEntityBase;
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
public class TipoDocumentoPrueba extends PersistentEnumerationEntityBase {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    @Deprecated()
    private TipoDocumentoPrueba() {
        this(null, null);
    }

    public TipoDocumentoPrueba(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        setDefaultLabel("tipo de documento de prueba");
        setDefaultShortLabel("tipo de documento");
        setDefaultCollectionLabel("tipos de documentos de prueba");
        setDefaultCollectionShortLabel("tipos de documentos");
    }

    static final String TIPO_CASO = "1";

    static final String TIPO_ESCENARIO = "2";

    static final String TIPO_PROGRAMA = "3";

    static final String TIPO_LINEA = "4";

    static final String TIPO_EJECUCION_PROGRAMA = "5";

    static final String TIPO_EJECUCION_LINEA = "6";

    public Instance CASO;

    public Instance ESCENARIO;

    public Instance PROGRAMA;

    public Instance LINEA;

    public Instance EJECUCION_PROGRAMA;

    public Instance EJECUCION_LINEA;

}
