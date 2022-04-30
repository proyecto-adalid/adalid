#set($dollar = '$')
#set($pound = '#')
#set($backslash = '\')
/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package ${package}.meta.entidad.modelo;

import adalid.core.*;
import adalid.core.annotations.*;
import adalid.core.enums.*;
import adalid.core.expressions.*;
import adalid.core.interfaces.*;
import adalid.core.parameters.*;
import adalid.core.properties.*;
import adalid.jee2.constants.*;
import java.lang.reflect.Field;

/**
 * @author nombre del responsable de la entidad
 */
@EntityDataGen
public class Entidad extends AbstractPersistentEntity {

    @Deprecated()
    private Entidad() {
        this(null, null);
    }

    public Entidad(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }

    @Override
    protected void addAllocationStrings() {
        super.addAllocationStrings();
//      super.addAllocationStrings("fee", "faa", "foo");
    }

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
    }

    @PrimaryKey
    public LongProperty id;

    @VersionProperty
    public LongProperty version;

    @BusinessKey
    @CharacterDataGen(function = "util.string_codigo_entidad")
    public StringProperty codigo;

    @NameProperty
    @CharacterDataGen(function = "util.string_nombre_entidad")
    public StringProperty nombre;

    @DescriptionProperty
//  @CharacterDataGen(function = "util.string_descripcion_entidad")
    public StringProperty descripcion;

    @Override
    protected void settleProperties() {
        super.settleProperties();
    }

    @Override
    protected void settleCollections() {
        super.settleCollections();
    }

    @Override
    protected void settleLinks() {
        super.settleLinks();
    }

    @Override
    protected void settleKeys() {
        super.settleKeys();
    }

    @Override
    protected void settleTabs() {
        super.settleTabs();
    }

    @Override
    protected void settleViews() {
        super.settleViews();
    }

    @Override
    protected void settleExpressions() {
        super.settleExpressions();
    }

    @Override
    protected void settleFilters() {
        super.settleFilters();
    }

    protected ProcesarClase procesarClase;

    @OperationClass(access = OperationAccess.RESTRICTED)
    public class ProcesarClase extends ProcessOperation {

        @Override
        protected void addAllocationStrings() {
            super.addAllocationStrings();
//          super.addAllocationStrings("fee", "faa", "foo");
        }

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
        }

        @Override
        protected void settleParameters() {
            super.settleParameters();
        }

        @Override
        protected void settleExpressions() {
            super.settleExpressions();
        }

        @Override
        protected void settleFilters() {
            super.settleFilters();
        }

    }

    protected ProcesarInstancia procesarInstancia;

    @OperationClass(access = OperationAccess.RESTRICTED)
    public class ProcesarInstancia extends ProcessOperation {

        @Override
        protected void addAllocationStrings() {
            super.addAllocationStrings();
//          super.addAllocationStrings("fee", "faa", "foo");
        }

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
        }

        @InstanceReference
        protected Entidad entidad;

        @Override
        protected void settleParameters() {
            super.settleParameters();
        }

        @Override
        protected void settleExpressions() {
            super.settleExpressions();
        }

        @Override
        protected void settleFilters() {
            super.settleFilters();
        }

    }

    protected ExportarArchivo exportarArchivo;

    @ExportOperationClass()
    @OperationClass(access = OperationAccess.RESTRICTED)
    public class ExportarArchivo extends ExportOperation {

        @Override
        protected void addAllocationStrings() {
            super.addAllocationStrings();
//          super.addAllocationStrings("fee", "faa", "foo");
        }

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
        }

        @Override
        protected void settleParameters() {
            super.settleParameters();
        }

        @Override
        protected void settleExpressions() {
            super.settleExpressions();
        }

        @Override
        protected void settleFilters() {
            super.settleFilters();
        }

    }

    protected GenerarInforme generarInforme;

    @ReportOperationClass()
    @OperationClass(access = OperationAccess.RESTRICTED)
    public class GenerarInforme extends ReportOperation {

        @Override
        protected void addAllocationStrings() {
            super.addAllocationStrings();
//          super.addAllocationStrings("fee", "faa", "foo");
        }

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
        }

        @Override
        protected void settleParameters() {
            super.settleParameters();
        }

        @Override
        protected void settleExpressions() {
            super.settleExpressions();
        }

        @Override
        protected void settleFilters() {
            super.settleFilters();
        }

    }

    @Override
    protected void settleOperations() {
        super.settleOperations();
    }

}
