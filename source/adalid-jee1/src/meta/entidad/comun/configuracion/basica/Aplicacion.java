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
public class Aplicacion extends AbstractPersistentEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    @Deprecated
    private Aplicacion() {
        this(null, null);
    }

    public Aplicacion(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public LongProperty idAplicacion;

    @VersionProperty
    public LongProperty versionAplicacion;

    @BusinessKey
    @StringField(maxLength = 100)
    public StringProperty codigoAplicacion;

    @NameProperty
    public StringProperty nombreAplicacion;

    @DescriptionProperty
    public StringProperty descripcionAplicacion;

    @ColumnField(nullable = Kleenean.FALSE)
    @StringField(maxLength = 100)
    public StringProperty servidorAplicacion;

    @ColumnField(nullable = Kleenean.FALSE)
    @StringField(maxLength = 10)
    public StringProperty puertoAplicacion;

    @UrlProperty
    public StringProperty urlAplicacion;

    @ColumnField(nullable = Kleenean.FALSE)
    public BooleanProperty esPublica;

    @ColumnField(nullable = Kleenean.FALSE)
    public BooleanProperty esEspecial;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        setDefaultLabel("aplicación");
//      setDefaultShortLabel("aplicación");
        setDefaultCollectionLabel("aplicaciones");
//      setDefaultCollectionShortLabel("aplicaciones");
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        servidorAplicacion.setInitialValue("localhost");
        servidorAplicacion.setDefaultValue("localhost");
        puertoAplicacion.setInitialValue("8080");
        puertoAplicacion.setDefaultValue("8080");
        esPublica.setInitialValue(false);
        esPublica.setDefaultValue(false);
        esEspecial.setInitialValue(false);
        esEspecial.setDefaultValue(false);
    }

}
