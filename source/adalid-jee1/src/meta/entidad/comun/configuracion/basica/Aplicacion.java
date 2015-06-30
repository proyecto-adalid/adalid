/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package meta.entidad.comun.configuracion.basica;

import adalid.core.AbstractPersistentEntity;
import adalid.core.annotations.BusinessKey;
import adalid.core.annotations.ColumnField;
import adalid.core.annotations.DescriptionProperty;
import adalid.core.annotations.EntityClass;
import adalid.core.annotations.EntityConsoleView;
import adalid.core.annotations.EntityDeleteOperation;
import adalid.core.annotations.EntityDetailView;
import adalid.core.annotations.EntityInsertOperation;
import adalid.core.annotations.EntitySelectOperation;
import adalid.core.annotations.EntityTableView;
import adalid.core.annotations.EntityTreeView;
import adalid.core.annotations.EntityUpdateOperation;
import adalid.core.annotations.NameProperty;
import adalid.core.annotations.PrimaryKey;
import adalid.core.annotations.StringField;
import adalid.core.annotations.UrlProperty;
import adalid.core.annotations.VersionProperty;
import adalid.core.enums.Kleenean;
import adalid.core.enums.OperationAccess;
import adalid.core.enums.ResourceGender;
import adalid.core.enums.ResourceType;
import adalid.core.interfaces.Artifact;
import adalid.core.properties.BooleanProperty;
import adalid.core.properties.LongProperty;
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
        setDefaultCollectionLabel("Aplicaciones");
//      setDefaultCollectionShortLabel("Aplicaciones");
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
