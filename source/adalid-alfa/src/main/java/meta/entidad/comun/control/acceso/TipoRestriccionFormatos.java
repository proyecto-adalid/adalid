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
package meta.entidad.comun.control.acceso;

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
public class TipoRestriccionFormatos extends AbstractPersistentEnumerationEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public TipoRestriccionFormatos(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public IntegerProperty numero;

    @BusinessKey
    public StringProperty codigo;

    public Instance NADA;

    public Instance DESCARGA;

    public Instance GENERACION;

    public Instance TODO;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of TipoRestriccionFormatos's attributes">
        setLocalizedLabel(ENGLISH, "resource access type");
        setLocalizedLabel(SPANISH, "tipo de acceso a recursos");
        setLocalizedShortLabel(ENGLISH, "access type");
        setLocalizedShortLabel(SPANISH, "tipo de acceso");
        setLocalizedCollectionLabel(ENGLISH, "Resource Access Types");
        setLocalizedCollectionLabel(SPANISH, "Tipos de Acceso a Recursos");
        setLocalizedCollectionShortLabel(ENGLISH, "Access Types");
        setLocalizedCollectionShortLabel(SPANISH, "Tipos de Acceso");
        // </editor-fold>
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        // <editor-fold defaultstate="collapsed" desc="localization of TipoRestriccionFormatos's properties">
        /**/
        numero.setLocalizedLabel(ENGLISH, "resource access type number");
        numero.setLocalizedLabel(SPANISH, "número del tipo de acceso");
        numero.setLocalizedShortLabel(ENGLISH, "number");
        numero.setLocalizedShortLabel(SPANISH, "número");
        /**/
        codigo.setLocalizedLabel(ENGLISH, "resource access type code");
        codigo.setLocalizedLabel(SPANISH, "código del tipo de acceso");
        codigo.setLocalizedShortLabel(ENGLISH, "code");
        codigo.setLocalizedShortLabel(SPANISH, "código");
        /**/
        // </editor-fold>
    }

    @Override
    protected void settleInstances() {
        super.settleInstances();
        // <editor-fold defaultstate="collapsed" desc="localization of TipoRestriccionFormatos's instances">
        /**/
        NADA.newInstanceField(codigo, "No restrictions", ENGLISH);
        NADA.newInstanceField(codigo, "Sin restricción", SPANISH);
        /**/
        DESCARGA.newInstanceField(codigo, "Restricted Downloading", ENGLISH);
        DESCARGA.newInstanceField(codigo, "Descarga restringida", SPANISH);
        /**/
        GENERACION.newInstanceField(codigo, "Restricted Generation", ENGLISH);
        GENERACION.newInstanceField(codigo, "Generación restringida", SPANISH);
        /**/
        TODO.newInstanceField(codigo, "All restricted", ENGLISH);
        TODO.newInstanceField(codigo, "Todo restringido", SPANISH);
        /**/
        // </editor-fold>
    }

}
