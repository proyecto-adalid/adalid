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
import adalid.core.annotations.StringField;
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
public class TipoParametroDom extends AbstractPersistentEnumerationEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    @Deprecated
    private TipoParametroDom() {
        this(null, null);
    }

    public TipoParametroDom(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public IntegerProperty numeroTipoParametroDom;

    @BusinessKey
    public StringProperty codigoTipoParametroDom;

//  @UniqueKey
    @StringField(maxLength = 30)
    public StringProperty codigoPropiedadInterfaz;

//  @UniqueKey
    @StringField(maxLength = 100)
    public StringProperty nombreInterfaz;

//  @ColumnField(nullable = Kleenean.FALSE)
    @StringField(maxLength = 30)
    public StringProperty etiquetaParametro;

    public Instance ID;

    public Instance VERSION;

    public Instance CODIGO;

    public Instance NOMBRE;

    public Instance PROPIETARIO;

    public Instance SEGMENTO;

    public Instance SUPERIOR;

    public Instance INACTIVO;

    public Instance NUMERO;

    public Instance CLAVE;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        setDefaultLabel("tipo de parámetro de dominio");
        setDefaultShortLabel("tipo de parámetro");
        setDefaultCollectionLabel("Tipos de Parámetro de Dominio");
        setDefaultCollectionShortLabel("Tipos de Parámetro");
    }

    @Override
    protected void settleInstances() {
        super.settleInstances();
        ID.newInstanceField(numeroTipoParametroDom, 1);
        ID.newInstanceField(codigoTipoParametroDom, "id");
        ID.newInstanceField(codigoPropiedadInterfaz, "identificacion_recurso");
        ID.newInstanceField(nombreInterfaz, "RecursoIdentificable");
        ID.newInstanceField(etiquetaParametro, "Identificación");
        VERSION.newInstanceField(numeroTipoParametroDom, 2);
        VERSION.newInstanceField(codigoTipoParametroDom, "version");
        VERSION.newInstanceField(codigoPropiedadInterfaz, "version_recurso");
        VERSION.newInstanceField(nombreInterfaz, "RecursoVersionable");
        VERSION.newInstanceField(etiquetaParametro, "Versión");
        CODIGO.newInstanceField(numeroTipoParametroDom, 3);
        CODIGO.newInstanceField(codigoTipoParametroDom, "codigo");
        CODIGO.newInstanceField(codigoPropiedadInterfaz, "codigo_recurso");
        CODIGO.newInstanceField(nombreInterfaz, "RecursoCodificable");
        CODIGO.newInstanceField(etiquetaParametro, "Código");
        NOMBRE.newInstanceField(numeroTipoParametroDom, 4);
        NOMBRE.newInstanceField(codigoTipoParametroDom, "nombre");
        NOMBRE.newInstanceField(codigoPropiedadInterfaz, "nombre_recurso");
        NOMBRE.newInstanceField(nombreInterfaz, "RecursoNombrable");
        NOMBRE.newInstanceField(etiquetaParametro, "Nombre");
        PROPIETARIO.newInstanceField(numeroTipoParametroDom, 5);
        PROPIETARIO.newInstanceField(codigoTipoParametroDom, "propietario");
        PROPIETARIO.newInstanceField(codigoPropiedadInterfaz, "propietario_recurso");
        PROPIETARIO.newInstanceField(nombreInterfaz, "RecursoPersonalizable");
        PROPIETARIO.newInstanceField(etiquetaParametro, "Propietario");
        SEGMENTO.newInstanceField(numeroTipoParametroDom, 6);
        SEGMENTO.newInstanceField(codigoTipoParametroDom, "segmento");
        SEGMENTO.newInstanceField(codigoPropiedadInterfaz, "segmento_recurso");
        SEGMENTO.newInstanceField(nombreInterfaz, "RecursoSegmentable");
        SEGMENTO.newInstanceField(etiquetaParametro, "Segmento");
        SUPERIOR.newInstanceField(numeroTipoParametroDom, 7);
        SUPERIOR.newInstanceField(codigoTipoParametroDom, "superior");
        SUPERIOR.newInstanceField(codigoPropiedadInterfaz, "recurso_superior");
        SUPERIOR.newInstanceField(nombreInterfaz, "RecursoJerarquizable");
        SUPERIOR.newInstanceField(etiquetaParametro, "Superior");
        INACTIVO.newInstanceField(numeroTipoParametroDom, 8);
        INACTIVO.newInstanceField(codigoTipoParametroDom, "inactivo");
        INACTIVO.newInstanceField(codigoPropiedadInterfaz, "es_recurso_inactivo");
        INACTIVO.newInstanceField(nombreInterfaz, "RecursoDesactivable");
        INACTIVO.newInstanceField(etiquetaParametro, "Inactivo");
        NUMERO.newInstanceField(numeroTipoParametroDom, 9);
        NUMERO.newInstanceField(codigoTipoParametroDom, "numero");
        NUMERO.newInstanceField(codigoPropiedadInterfaz, "numero_recurso");
        NUMERO.newInstanceField(nombreInterfaz, "RecursoEnumerable");
        NUMERO.newInstanceField(etiquetaParametro, "Número");
        CLAVE.newInstanceField(numeroTipoParametroDom, 10);
        CLAVE.newInstanceField(codigoTipoParametroDom, "clave");
        CLAVE.newInstanceField(etiquetaParametro, "Clave");
    }

}
