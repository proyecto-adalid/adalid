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
public class TipoParametro extends AbstractPersistentEnumerationEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public TipoParametro(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public IntegerProperty numero;

    @BusinessKey
    public StringProperty codigo;

    public Instance OBLIGATORIO;

    public Instance OMITIDO;

    public Instance OPCIONAL;

    public Instance PROGRAMATICO;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of TipoParametro's attributes">
        setLocalizedLabel(ENGLISH, "parameter type");
        setLocalizedLabel(SPANISH, "tipo de parámetro");
        setLocalizedCollectionLabel(ENGLISH, "Parameter Types");
        setLocalizedCollectionLabel(SPANISH, "Tipos de Parámetro");
        // </editor-fold>
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        // <editor-fold defaultstate="collapsed" desc="localization of TipoParametro's properties">
        numero.setLocalizedLabel(ENGLISH, "parameter type number");
        numero.setLocalizedLabel(SPANISH, "número del tipo de parámetro");
        numero.setLocalizedShortLabel(ENGLISH, "number");
        numero.setLocalizedShortLabel(SPANISH, "número");
        /**/
        codigo.setLocalizedLabel(ENGLISH, "parameter type code");
        codigo.setLocalizedLabel(SPANISH, "código del tipo de parámetro");
        codigo.setLocalizedShortLabel(ENGLISH, "code");
        codigo.setLocalizedShortLabel(SPANISH, "código");
        // </editor-fold>
    }

    @Override
    protected void settleInstances() {
        super.settleInstances();
        // <editor-fold defaultstate="collapsed" desc="localization of TipoParametro's instances">
        OBLIGATORIO.newInstanceField(codigo, "Required", ENGLISH);
        OBLIGATORIO.newInstanceField(codigo, "Obligatorio", SPANISH);
        /**/
        OMITIDO.newInstanceField(codigo, "Omitted", ENGLISH);
        OMITIDO.newInstanceField(codigo, "Omitido", SPANISH);
        /**/
        OPCIONAL.newInstanceField(codigo, "Optional", ENGLISH);
        OPCIONAL.newInstanceField(codigo, "Opcional", SPANISH);
        /**/
        PROGRAMATICO.newInstanceField(codigo, "Programmatic", ENGLISH);
        PROGRAMATICO.newInstanceField(codigo, "Programatico", SPANISH);
        // </editor-fold>
    }

}
