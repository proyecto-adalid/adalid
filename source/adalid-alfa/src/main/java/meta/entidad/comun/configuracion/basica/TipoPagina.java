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
public class TipoPagina extends AbstractPersistentEnumerationEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public TipoPagina(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public IntegerProperty numero;

    @BusinessKey
    public StringProperty codigo;

    public Instance TABLA;

    public Instance DETALLE;

    public Instance ARBOL;

    public Instance COMANDO;

    public Instance CONSULTA_TABLA;

    public Instance CONSULTA_DETALLE;

    public Instance CONSULTA_ARBOL;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of TipoPagina's attributes">
        setLocalizedLabel(ENGLISH, "page type");
        setLocalizedLabel(SPANISH, "tipo de página");
//      setLocalizedShortLabel(ENGLISH, "type");
//      setLocalizedShortLabel(SPANISH, "tipo");
        setLocalizedCollectionLabel(ENGLISH, "Page Types");
        setLocalizedCollectionLabel(SPANISH, "Tipos de Página");
//      setLocalizedCollectionShortLabel(ENGLISH, "Types");
//      setLocalizedCollectionShortLabel(SPANISH, "Tipos");
        // </editor-fold>
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        // <editor-fold defaultstate="collapsed" desc="localization of TipoPagina's properties">
        numero.setLocalizedLabel(ENGLISH, "page type number");
        numero.setLocalizedLabel(SPANISH, "número del tipo de página");
        numero.setLocalizedShortLabel(ENGLISH, "number");
        numero.setLocalizedShortLabel(SPANISH, "número");
        /**/
        codigo.setLocalizedLabel(ENGLISH, "page type code");
        codigo.setLocalizedLabel(SPANISH, "código del tipo de página");
        codigo.setLocalizedShortLabel(ENGLISH, "code");
        codigo.setLocalizedShortLabel(SPANISH, "código");
        // </editor-fold>
    }

    @Override
    protected void settleInstances() {
        super.settleInstances();
        // <editor-fold defaultstate="collapsed" desc="localization of TipoPagina's instances">
        TABLA.newInstanceField(codigo, "Table", ENGLISH);
        TABLA.newInstanceField(codigo, "Tabla", SPANISH);
        /**/
        DETALLE.newInstanceField(codigo, "Detail", ENGLISH);
        DETALLE.newInstanceField(codigo, "Detalle", SPANISH);
        /**/
        ARBOL.newInstanceField(codigo, "Tree", ENGLISH);
        ARBOL.newInstanceField(codigo, "Arbol", SPANISH);
        /**/
        COMANDO.newInstanceField(codigo, "Console", ENGLISH);
        COMANDO.newInstanceField(codigo, "Comando", SPANISH);
        /**/
        CONSULTA_TABLA.newInstanceField(codigo, "Read-only table", ENGLISH);
        CONSULTA_TABLA.newInstanceField(codigo, "Consulta tabla", SPANISH);
        /**/
        CONSULTA_DETALLE.newInstanceField(codigo, "Read-only detail", ENGLISH);
        CONSULTA_DETALLE.newInstanceField(codigo, "Consulta detalle", SPANISH);
        /**/
        CONSULTA_ARBOL.newInstanceField(codigo, "Read-only tree", ENGLISH);
        CONSULTA_ARBOL.newInstanceField(codigo, "Consulta arbol", SPANISH);
        // </editor-fold>
    }

}
