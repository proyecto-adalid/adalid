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
public class TipoFuncion extends AbstractPersistentEnumerationEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public TipoFuncion(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public IntegerProperty numero;

    @BusinessKey
    public StringProperty codigo;

    public Instance PROCEDIMIENTO_FILA;

    public Instance PROCEDIMIENTO_FILA_PARAMETROS;

    public Instance PROCEDIMIENTO_PARAMETROS;

    public Instance CONSULTA;

    public Instance INFORME;

    public Instance ARCHIVO;

    public Instance CREACION;

    public Instance MODIFICACION;

    public Instance ELIMINACION;

    public Instance PROCESO;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of TipoFuncion's attributes">
        setLocalizedLabel(ENGLISH, "function type");
        setLocalizedLabel(SPANISH, "tipo de función");
//      setLocalizedShortLabel(ENGLISH, "type");
//      setLocalizedShortLabel(SPANISH, "tipo");
        setLocalizedCollectionLabel(ENGLISH, "Function Types");
        setLocalizedCollectionLabel(SPANISH, "Tipos de Función");
//      setLocalizedCollectionShortLabel(ENGLISH, "Types");
//      setLocalizedCollectionShortLabel(SPANISH, "Tipos");
        // </editor-fold>
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        // <editor-fold defaultstate="collapsed" desc="localization of TipoFuncion's properties">
        numero.setLocalizedLabel(ENGLISH, "function type number");
        numero.setLocalizedLabel(SPANISH, "número del tipo de función");
        numero.setLocalizedShortLabel(ENGLISH, "number");
        numero.setLocalizedShortLabel(SPANISH, "número");
        /**/
        codigo.setLocalizedLabel(ENGLISH, "function type code");
        codigo.setLocalizedLabel(SPANISH, "código del tipo de función");
        codigo.setLocalizedShortLabel(ENGLISH, "code");
        codigo.setLocalizedShortLabel(SPANISH, "código");
        // </editor-fold>
    }

    @Override
    protected void settleInstances() {
        super.settleInstances();
        PROCEDIMIENTO_FILA.newInstanceField(numero, 11);
        PROCEDIMIENTO_FILA.newInstanceField(codigo, "Procedimiento fila");
        PROCEDIMIENTO_FILA_PARAMETROS.newInstanceField(numero, 12);
        PROCEDIMIENTO_FILA_PARAMETROS.newInstanceField(codigo, "Procedimiento fila+par\u00E1metros");
        PROCEDIMIENTO_PARAMETROS.newInstanceField(numero, 13);
        PROCEDIMIENTO_PARAMETROS.newInstanceField(codigo, "Procedimiento par\u00E1metros");
        CONSULTA.newInstanceField(numero, 21);
        CONSULTA.newInstanceField(codigo, "Consulta");
        INFORME.newInstanceField(numero, 22);
        INFORME.newInstanceField(codigo, "Informe");
        ARCHIVO.newInstanceField(numero, 23);
        ARCHIVO.newInstanceField(codigo, "Archivo");
        CREACION.newInstanceField(numero, 31);
        CREACION.newInstanceField(codigo, "Creaci\u00F3n");
        MODIFICACION.newInstanceField(numero, 32);
        MODIFICACION.newInstanceField(codigo, "Modificaci\u00F3n");
        ELIMINACION.newInstanceField(numero, 33);
        ELIMINACION.newInstanceField(codigo, "Eliminaci\u00F3n");
        PROCESO.newInstanceField(numero, 41);
        PROCESO.newInstanceField(codigo, "Proceso");
        // <editor-fold defaultstate="collapsed" desc="localization of TipoFuncion's instances">
        PROCEDIMIENTO_FILA.newInstanceField(codigo, "Row procedure", ENGLISH);
        PROCEDIMIENTO_FILA.newInstanceField(codigo, "Procedimiento fila", SPANISH);
        /**/
        PROCEDIMIENTO_FILA_PARAMETROS.newInstanceField(codigo, "Parameterized row procedure", ENGLISH);
        PROCEDIMIENTO_FILA_PARAMETROS.newInstanceField(codigo, "Procedimiento fila+parámetros", SPANISH);
        /**/
        PROCEDIMIENTO_PARAMETROS.newInstanceField(codigo, "Parameterized procedure", ENGLISH);
        PROCEDIMIENTO_PARAMETROS.newInstanceField(codigo, "Procedimiento parámetros", SPANISH);
        /**/
        CONSULTA.newInstanceField(codigo, "Select", ENGLISH);
        CONSULTA.newInstanceField(codigo, "Consulta", SPANISH);
        /**/
        INFORME.newInstanceField(codigo, "Report", ENGLISH);
        INFORME.newInstanceField(codigo, "Informe", SPANISH);
        /**/
        ARCHIVO.newInstanceField(codigo, "Export", ENGLISH);
        ARCHIVO.newInstanceField(codigo, "Archivo", SPANISH);
        /**/
        CREACION.newInstanceField(codigo, "Insert", ENGLISH);
        CREACION.newInstanceField(codigo, "Creación", SPANISH);
        /**/
        MODIFICACION.newInstanceField(codigo, "Update", ENGLISH);
        MODIFICACION.newInstanceField(codigo, "Modificación", SPANISH);
        /**/
        ELIMINACION.newInstanceField(codigo, "Delete", ENGLISH);
        ELIMINACION.newInstanceField(codigo, "Eliminación", SPANISH);
        /**/
        PROCESO.newInstanceField(codigo, "Process", ENGLISH);
        PROCESO.newInstanceField(codigo, "Proceso", SPANISH);
        // </editor-fold>
    }

}
