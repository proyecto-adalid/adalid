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
@EntityClass(independent = Kleenean.TRUE, resourceType = ResourceType.CONFIGURATION, resourceGender = ResourceGender.FEMININE)
@EntitySelectOperation(enabled = Kleenean.TRUE, access = OperationAccess.PUBLIC, rowsLimit = 0)
@EntityInsertOperation(enabled = Kleenean.FALSE)
@EntityUpdateOperation(enabled = Kleenean.FALSE)
@EntityDeleteOperation(enabled = Kleenean.FALSE)
@EntityTableView(enabled = Kleenean.FALSE)
@EntityDetailView(enabled = Kleenean.FALSE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
public class ClaseJava extends AbstractPersistentEnumerationEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    @Deprecated
    private ClaseJava() {
        this(null, null);
    }

    public ClaseJava(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        setDefaultLabel("clase de dato");
        setDefaultShortLabel("clase");
        setDefaultCollectionLabel("clases de datos");
        setDefaultCollectionShortLabel("clases");
    }

    @PrimaryKey
    public IntegerProperty numero;

    @BusinessKey
    public StringProperty codigo;

    public Instance JAVA_BIG_DECIMAL;

    public Instance JAVA_BIG_INTEGER;

    public Instance JAVA_BINARY;

    public Instance JAVA_BOOLEAN;

    public Instance JAVA_BYTE;

    public Instance JAVA_CHARACTER;

    public Instance JAVA_DATE;

    public Instance JAVA_DOUBLE;

    public Instance JAVA_FLOAT;

    public Instance JAVA_INTEGER;

    public Instance JAVA_LONG;

    public Instance JAVA_SHORT;

    public Instance JAVA_STRING;

    public Instance JAVA_TIME;

    public Instance JAVA_TIMESTAMP;

    public Instance JAVA_OBJECT;

}
