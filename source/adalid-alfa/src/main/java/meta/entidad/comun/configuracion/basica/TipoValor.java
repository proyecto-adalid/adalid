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
public class TipoValor extends AbstractPersistentEnumerationEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public TipoValor(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public IntegerProperty numero;

    @BusinessKey
    public StringProperty codigo;

    public Instance CONTINUO;

    public Instance DISCRETO;

    public Instance OBJETO;

    public Instance RECURSO;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of TipoValor's attributes">
        setLocalizedLabel(ENGLISH, "value type");
        setLocalizedLabel(SPANISH, "tipo de valor");
        setLocalizedCollectionLabel(ENGLISH, "Value Types");
        setLocalizedCollectionLabel(SPANISH, "Tipos de Valor");
        // </editor-fold>
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        // <editor-fold defaultstate="collapsed" desc="localization of TipoValor's properties">
        numero.setLocalizedLabel(ENGLISH, "value type number");
        numero.setLocalizedLabel(SPANISH, "número del tipo de valor");
        numero.setLocalizedShortLabel(ENGLISH, "number");
        numero.setLocalizedShortLabel(SPANISH, "número");
        /**/
        codigo.setLocalizedLabel(ENGLISH, "value type code");
        codigo.setLocalizedLabel(SPANISH, "código del tipo de valor");
        codigo.setLocalizedShortLabel(ENGLISH, "code");
        codigo.setLocalizedShortLabel(SPANISH, "código");
        // </editor-fold>
    }

    @Override
    protected void settleInstances() {
        super.settleInstances();
        CONTINUO.newInstanceField(numero, 11);
        CONTINUO.newInstanceField(codigo, "Cont\u00EDnuo");
        DISCRETO.newInstanceField(numero, 21);
        DISCRETO.newInstanceField(codigo, "Discreto");
        OBJETO.newInstanceField(numero, 22);
        OBJETO.newInstanceField(codigo, "Objeto");
        RECURSO.newInstanceField(numero, 23);
        RECURSO.newInstanceField(codigo, "Recurso");
        // <editor-fold defaultstate="collapsed" desc="localization of TipoValor's instances">
        CONTINUO.newInstanceField(codigo, "Continuous", ENGLISH);
        CONTINUO.newInstanceField(codigo, "Contínuo", SPANISH);
        /**/
        DISCRETO.newInstanceField(codigo, "Discrete", ENGLISH);
        DISCRETO.newInstanceField(codigo, "Discreto", SPANISH);
        /**/
        OBJETO.newInstanceField(codigo, "Object", ENGLISH);
        OBJETO.newInstanceField(codigo, "Objeto", SPANISH);
        /**/
        RECURSO.newInstanceField(codigo, "Resource", ENGLISH);
        RECURSO.newInstanceField(codigo, "Recurso", SPANISH);
        // </editor-fold>
    }

}
