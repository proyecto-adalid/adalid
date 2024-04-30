/*
 * Copyright 2017 Jorge Campins y David Uzcategui
 *
 * Este archivo forma parte de Adalid.
 *
 * Adalid es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos de la
 * licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Adalid se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA; sin
 * siquiera las garantias implicitas de COMERCIALIZACION e IDONEIDAD PARA UN PROPOSITO PARTICULAR.
 *
 * Para mas detalles vea la licencia "GNU General Public License" en http://www.gnu.org/licenses
 */
package meta.entidad.comun.configuracion.basica;

import adalid.core.*;
import adalid.core.annotations.*;
import adalid.core.enums.*;
import adalid.core.interfaces.*;
import adalid.core.properties.*;
import java.lang.reflect.Field;

/**
 * @author Jorge Campins
 */
@EntityClass(independent = Kleenean.TRUE, resourceType = ResourceType.CONFIGURATION, resourceGender = ResourceGender.MASCULINE)
@EntityCodeGen(bws = Kleenean.FALSE, fws = Kleenean.FALSE)
@EntitySelectOperation(enabled = Kleenean.TRUE, access = OperationAccess.PUBLIC, rowsLimit = 0)
@EntityInsertOperation(enabled = Kleenean.FALSE)
@EntityUpdateOperation(enabled = Kleenean.FALSE)
@EntityDeleteOperation(enabled = Kleenean.FALSE)
@EntityTableView(enabled = Kleenean.FALSE)
@EntityDetailView(enabled = Kleenean.FALSE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
//@EntityReferenceDisplay(filterType = EntityReferenceFilterType.LIST)
public class TipoParametroDom extends AbstractPersistentEnumerationEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public TipoParametroDom(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public IntegerProperty numero;

    @BusinessKey
    public StringProperty codigo;

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
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of TipoParametroDom's attributes">
        setLocalizedLabel(ENGLISH, "domain parameter type");
        setLocalizedLabel(SPANISH, "tipo de parámetro de dominio");
        setLocalizedShortLabel(ENGLISH, "parameter type");
        setLocalizedShortLabel(SPANISH, "tipo de parámetro");
        setLocalizedCollectionLabel(ENGLISH, "Domain Parameter Types");
        setLocalizedCollectionLabel(SPANISH, "Tipos de Parámetro de Dominio");
        setLocalizedCollectionShortLabel(ENGLISH, "Parameter Types");
        setLocalizedCollectionShortLabel(SPANISH, "Tipos de Parámetro");
        // </editor-fold>
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        // <editor-fold defaultstate="collapsed" desc="localization of TipoParametroDom's properties">
        numero.setLocalizedLabel(ENGLISH, "domain parameter type number");
        numero.setLocalizedLabel(SPANISH, "número del tipo de parámetro de dominio");
        numero.setLocalizedShortLabel(ENGLISH, "number");
        numero.setLocalizedShortLabel(SPANISH, "número");
        /**/
        codigo.setLocalizedLabel(ENGLISH, "domain parameter type code");
        codigo.setLocalizedLabel(SPANISH, "código del tipo de parámetro de dominio");
        codigo.setLocalizedShortLabel(ENGLISH, "code");
        codigo.setLocalizedShortLabel(SPANISH, "código");
        /**/
        codigoPropiedadInterfaz.setLocalizedLabel(ENGLISH, "interface code");
        codigoPropiedadInterfaz.setLocalizedLabel(SPANISH, "código propiedad interfaz");
        /**/
        nombreInterfaz.setLocalizedLabel(ENGLISH, "interface name");
        nombreInterfaz.setLocalizedLabel(SPANISH, "nombre interfaz");
        /**/
        etiquetaParametro.setLocalizedLabel(ENGLISH, "parameter label");
        etiquetaParametro.setLocalizedLabel(SPANISH, "etiqueta parámetro");
        // </editor-fold>
    }

    @Override
    protected void settleInstances() {
        super.settleInstances();
        ID.newInstanceField(numero, 1);
        ID.newInstanceField(codigo, "id");
        ID.newInstanceField(codigoPropiedadInterfaz, "identificacion_recurso");
        ID.newInstanceField(nombreInterfaz, "RecursoIdentificable");
        ID.newInstanceField(etiquetaParametro, "Identificación");
        VERSION.newInstanceField(numero, 2);
        VERSION.newInstanceField(codigo, "versión");
        VERSION.newInstanceField(codigoPropiedadInterfaz, "version_recurso");
        VERSION.newInstanceField(nombreInterfaz, "RecursoVersionable");
        VERSION.newInstanceField(etiquetaParametro, "Versión");
        CODIGO.newInstanceField(numero, 3);
        CODIGO.newInstanceField(codigo, "código");
        CODIGO.newInstanceField(codigoPropiedadInterfaz, "codigo_recurso");
        CODIGO.newInstanceField(nombreInterfaz, "RecursoCodificable");
        CODIGO.newInstanceField(etiquetaParametro, "Código");
        NOMBRE.newInstanceField(numero, 4);
        NOMBRE.newInstanceField(codigo, "nombre");
        NOMBRE.newInstanceField(codigoPropiedadInterfaz, "nombre_recurso");
        NOMBRE.newInstanceField(nombreInterfaz, "RecursoNombrable");
        NOMBRE.newInstanceField(etiquetaParametro, "Nombre");
        PROPIETARIO.newInstanceField(numero, 5);
        PROPIETARIO.newInstanceField(codigo, "propietario");
        PROPIETARIO.newInstanceField(codigoPropiedadInterfaz, "propietario_recurso");
        PROPIETARIO.newInstanceField(nombreInterfaz, "RecursoPersonalizable");
        PROPIETARIO.newInstanceField(etiquetaParametro, "Propietario");
        SEGMENTO.newInstanceField(numero, 6);
        SEGMENTO.newInstanceField(codigo, "segmento");
        SEGMENTO.newInstanceField(codigoPropiedadInterfaz, "segmento_recurso");
        SEGMENTO.newInstanceField(nombreInterfaz, "RecursoSegmentable");
        SEGMENTO.newInstanceField(etiquetaParametro, "Segmento");
        SUPERIOR.newInstanceField(numero, 7);
        SUPERIOR.newInstanceField(codigo, "superior");
        SUPERIOR.newInstanceField(codigoPropiedadInterfaz, "recurso_superior");
        SUPERIOR.newInstanceField(nombreInterfaz, "RecursoJerarquizable");
        SUPERIOR.newInstanceField(etiquetaParametro, "Superior");
        INACTIVO.newInstanceField(numero, 8);
        INACTIVO.newInstanceField(codigo, "inactivo");
        INACTIVO.newInstanceField(codigoPropiedadInterfaz, "es_recurso_inactivo");
        INACTIVO.newInstanceField(nombreInterfaz, "RecursoDesactivable");
        INACTIVO.newInstanceField(etiquetaParametro, "Inactivo");
        NUMERO.newInstanceField(numero, 9);
        NUMERO.newInstanceField(codigo, "número");
        NUMERO.newInstanceField(codigoPropiedadInterfaz, "numero_recurso");
        NUMERO.newInstanceField(nombreInterfaz, "RecursoEnumerable");
        NUMERO.newInstanceField(etiquetaParametro, "Número");
        CLAVE.newInstanceField(numero, 10);
        CLAVE.newInstanceField(codigo, "clave");
        CLAVE.newInstanceField(etiquetaParametro, "Clave");
        // <editor-fold defaultstate="collapsed" desc="localization of TipoParametroDom's instances">
        ID.newInstanceField(codigo, "id", ENGLISH);
        ID.newInstanceField(codigo, "id", SPANISH);
        /**/
        ID.newInstanceField(etiquetaParametro, "Identification", ENGLISH);
        ID.newInstanceField(etiquetaParametro, "Identificación", SPANISH);
        /**/
        VERSION.newInstanceField(codigo, "version", ENGLISH);
        VERSION.newInstanceField(codigo, "versión", SPANISH);
        /**/
        VERSION.newInstanceField(etiquetaParametro, "Version", ENGLISH);
        VERSION.newInstanceField(etiquetaParametro, "Versión", SPANISH);
        /**/
        CODIGO.newInstanceField(codigo, "code", ENGLISH);
        CODIGO.newInstanceField(codigo, "código", SPANISH);
        /**/
        CODIGO.newInstanceField(etiquetaParametro, "Code", ENGLISH);
        CODIGO.newInstanceField(etiquetaParametro, "Código", SPANISH);
        /**/
        NOMBRE.newInstanceField(codigo, "name", ENGLISH);
        NOMBRE.newInstanceField(codigo, "nombre", SPANISH);
        /**/
        NOMBRE.newInstanceField(etiquetaParametro, "Name", ENGLISH);
        NOMBRE.newInstanceField(etiquetaParametro, "Nombre", SPANISH);
        /**/
        PROPIETARIO.newInstanceField(codigo, "owner", ENGLISH);
        PROPIETARIO.newInstanceField(codigo, "propietario", SPANISH);
        /**/
        PROPIETARIO.newInstanceField(etiquetaParametro, "Owner", ENGLISH);
        PROPIETARIO.newInstanceField(etiquetaParametro, "Propietario", SPANISH);
        /**/
        SEGMENTO.newInstanceField(codigo, "segment", ENGLISH);
        SEGMENTO.newInstanceField(codigo, "segmento", SPANISH);
        /**/
        SEGMENTO.newInstanceField(etiquetaParametro, "Segment", ENGLISH);
        SEGMENTO.newInstanceField(etiquetaParametro, "Segmento", SPANISH);
        /**/
        SUPERIOR.newInstanceField(codigo, "parent", ENGLISH);
        SUPERIOR.newInstanceField(codigo, "superior", SPANISH);
        /**/
        SUPERIOR.newInstanceField(etiquetaParametro, "Parent", ENGLISH);
        SUPERIOR.newInstanceField(etiquetaParametro, "Superior", SPANISH);
        /**/
        INACTIVO.newInstanceField(codigo, "inactive", ENGLISH);
        INACTIVO.newInstanceField(codigo, "inactivo", SPANISH);
        /**/
        INACTIVO.newInstanceField(etiquetaParametro, "Inactive", ENGLISH);
        INACTIVO.newInstanceField(etiquetaParametro, "Inactivo", SPANISH);
        /**/
        NUMERO.newInstanceField(codigo, "number", ENGLISH);
        NUMERO.newInstanceField(codigo, "número", SPANISH);
        /**/
        NUMERO.newInstanceField(etiquetaParametro, "Number", ENGLISH);
        NUMERO.newInstanceField(etiquetaParametro, "Número", SPANISH);
        /**/
        CLAVE.newInstanceField(codigo, "key", ENGLISH);
        CLAVE.newInstanceField(codigo, "clave", SPANISH);
        /**/
        CLAVE.newInstanceField(etiquetaParametro, "Key", ENGLISH);
        CLAVE.newInstanceField(etiquetaParametro, "Clave", SPANISH);
        // </editor-fold>
    }

}
