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
public class TipoRecurso extends AbstractPersistentEnumerationEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    @Deprecated
    private TipoRecurso() {
        this(null, null);
    }

    public TipoRecurso(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public IntegerProperty numeroTipoRecurso;

    @BusinessKey
    public StringProperty codigoTipoRecurso;

    public Instance CONFIGURACION_BASICA;

    public Instance CONFIGURACION_FIJA;

    public Instance CONFIGURACION_MANUAL;

    public Instance CONFIGURACION_AUTOMATICA;

    public Instance GESTION_MANUAL;

    public Instance GESTION_AUTOMATICA;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        setDefaultLabel("tipo de recurso");
//      setDefaultShortLabel("tipo de recurso");
        setDefaultCollectionLabel("Tipos de Recurso");
//      setDefaultCollectionShortLabel("Tipos de Recurso");
    }

    @Override
    protected void settleInstances() {
        super.settleInstances();
        CONFIGURACION_BASICA.newInstanceField(numeroTipoRecurso, 11);
        CONFIGURACION_BASICA.newInstanceField(codigoTipoRecurso, "Configuraci\u00F3n b\u00E1sica");
        CONFIGURACION_FIJA.newInstanceField(numeroTipoRecurso, 12);
        CONFIGURACION_FIJA.newInstanceField(codigoTipoRecurso, "Configuraci\u00F3n fija");
        CONFIGURACION_MANUAL.newInstanceField(numeroTipoRecurso, 13);
        CONFIGURACION_MANUAL.newInstanceField(codigoTipoRecurso, "Configuraci\u00F3n manual");
        CONFIGURACION_AUTOMATICA.newInstanceField(numeroTipoRecurso, 14);
        CONFIGURACION_AUTOMATICA.newInstanceField(codigoTipoRecurso, "Configuraci\u00F3n autom\u00E1tica");
        GESTION_MANUAL.newInstanceField(numeroTipoRecurso, 21);
        GESTION_MANUAL.newInstanceField(codigoTipoRecurso, "Gesti\u00F3n manual");
        GESTION_AUTOMATICA.newInstanceField(numeroTipoRecurso, 22);
        GESTION_AUTOMATICA.newInstanceField(codigoTipoRecurso, "Gesti\u00F3n autom\u00E1tica");
    }

}
