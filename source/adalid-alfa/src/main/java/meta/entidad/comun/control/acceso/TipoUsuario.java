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
@EntityReferenceDisplay(filterType = EntityReferenceFilterType.LIST)
public class TipoUsuario extends AbstractPersistentEnumerationEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public TipoUsuario(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public IntegerProperty numero;

    @BusinessKey
    public StringProperty codigo;

    public Instance Local, Google, Facebook, Twitter;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        /**/
        setLocalizedLabel(ENGLISH, "user type");
        setLocalizedLabel(SPANISH, "tipo de usuario");
        setLocalizedCollectionLabel(ENGLISH, "User Types");
        setLocalizedCollectionLabel(SPANISH, "Tipos de Usuario");
        /**/
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        /**/
        numero.setLocalizedLabel(ENGLISH, "user type number");
        numero.setLocalizedLabel(SPANISH, "número del tipo de usuario");
        numero.setLocalizedShortLabel(ENGLISH, "number");
        numero.setLocalizedShortLabel(SPANISH, "número");
        /**/
        codigo.setLocalizedLabel(ENGLISH, "user type code");
        codigo.setLocalizedLabel(SPANISH, "código del tipo de usuario");
        codigo.setLocalizedShortLabel(ENGLISH, "code");
        codigo.setLocalizedShortLabel(SPANISH, "código");
        /**/
    }

}
