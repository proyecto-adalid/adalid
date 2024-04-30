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
@EntityClass(independent = Kleenean.TRUE, resourceType = ResourceType.CONFIGURATION, resourceGender = ResourceGender.MASCULINE, startWith = 0)
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
public class TipoRastroFun extends AbstractPersistentEnumerationEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public TipoRastroFun(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public IntegerProperty numero;

    @BusinessKey
    public StringProperty codigo;

    public Instance NINGUNO;

    public Instance EXITO;

    public Instance ERROR;

    public Instance AMBOS;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of TipoRastroFun's attributes">
        setLocalizedLabel(ENGLISH, "function trail type");
        setLocalizedLabel(SPANISH, "tipo de rastro de función");
        setLocalizedShortLabel(ENGLISH, "trail type");
        setLocalizedShortLabel(SPANISH, "tipo de rastro");
        setLocalizedCollectionLabel(ENGLISH, "Function Trail Types");
        setLocalizedCollectionLabel(SPANISH, "Tipos de Rastro de Función");
        setLocalizedCollectionShortLabel(ENGLISH, "Trail Types");
        setLocalizedCollectionShortLabel(SPANISH, "Tipos de Rastro");
        // </editor-fold>
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        // <editor-fold defaultstate="collapsed" desc="localization of TipoRastroFun's properties">
        numero.setLocalizedLabel(ENGLISH, "function trail type number");
        numero.setLocalizedLabel(SPANISH, "número del tipo de rastro de función");
        numero.setLocalizedShortLabel(ENGLISH, "number");
        numero.setLocalizedShortLabel(SPANISH, "número");
        /**/
        codigo.setLocalizedLabel(ENGLISH, "function trail type code");
        codigo.setLocalizedLabel(SPANISH, "código del tipo de rastro de función");
        codigo.setLocalizedShortLabel(ENGLISH, "code");
        codigo.setLocalizedShortLabel(SPANISH, "código");
        // </editor-fold>
    }

    @Override
    protected void settleInstances() {
        super.settleInstances();
        // <editor-fold defaultstate="collapsed" desc="localization of TipoRastroFun's instances">
        NINGUNO.newInstanceField(codigo, "None", ENGLISH);
        NINGUNO.newInstanceField(codigo, "Ninguno", SPANISH);
        /**/
        EXITO.newInstanceField(codigo, "Success", ENGLISH);
        EXITO.newInstanceField(codigo, "Exito", SPANISH);
        /**/
        ERROR.newInstanceField(codigo, "Error", ENGLISH);
        ERROR.newInstanceField(codigo, "Error", SPANISH);
        /**/
        AMBOS.newInstanceField(codigo, "Both", ENGLISH);
        AMBOS.newInstanceField(codigo, "Ambos", SPANISH);
        // </editor-fold>
    }

}
