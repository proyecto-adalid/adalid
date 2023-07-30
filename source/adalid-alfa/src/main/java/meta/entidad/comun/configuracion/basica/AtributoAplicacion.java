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
@EntityClass(catalog = Kleenean.TRUE, independent = Kleenean.TRUE, resourceType = ResourceType.CONFIGURATION, resourceGender = ResourceGender.MASCULINE)
@EntityCodeGen(bws = Kleenean.FALSE, fws = Kleenean.FALSE)
@EntitySelectOperation(enabled = Kleenean.TRUE, access = OperationAccess.PUBLIC, rowsLimit = 0)
@EntityInsertOperation(enabled = Kleenean.FALSE)
@EntityUpdateOperation(enabled = Kleenean.FALSE)
@EntityDeleteOperation(enabled = Kleenean.FALSE)
@EntityTableView(enabled = Kleenean.FALSE)
@EntityDetailView(enabled = Kleenean.FALSE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
public class AtributoAplicacion extends AbstractPersistentEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public AtributoAplicacion(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of AtributoAplicacion's attributes">
        setLocalizedLabel(ENGLISH, "application attribute");
        setLocalizedLabel(SPANISH, "atributo de la aplicación");
        setLocalizedShortLabel(ENGLISH, "attribute");
        setLocalizedShortLabel(SPANISH, "atributo");
        setLocalizedCollectionLabel(ENGLISH, "Application Attributes");
        setLocalizedCollectionLabel(SPANISH, "Atributos de la Aplicación");
        setLocalizedCollectionShortLabel(ENGLISH, "Attributes");
        setLocalizedCollectionShortLabel(SPANISH, "Atributos");
        /**/
        setLocalizedDescription(ENGLISH, "Each instance of " + b("Application Attributes") + " represents an "
            + "application extraordinary attribute."
            + "");
        setLocalizedDescription(SPANISH, "Cada instancia de " + b("Atributos de la Aplicación") + " representa un "
            + "atributo extraordinario de la aplicación."
            + "");
        /**/
        setLocalizedShortDescription(ENGLISH, "application extraordinary attribute");
        setLocalizedShortDescription(SPANISH, "atributo extraordinario de la aplicación");
        /**/
        // </editor-fold>
    }

    @PrimaryKey
    public LongProperty id;

    @VersionProperty
    public LongProperty version;

    @BusinessKey
    @StringField(maxLength = 100)
    public StringProperty clave;

    @NameProperty
    public StringProperty valor;

    @Override
    protected void settleProperties() {
        super.settleProperties();
        /**/
        setOrderBy(clave);
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of AtributoAplicacion's properties">
        clave.setLocalizedLabel(ENGLISH, "application attribute key");
        clave.setLocalizedLabel(SPANISH, "clave del atributo de la aplicación");
        clave.setLocalizedShortLabel(ENGLISH, "key");
        clave.setLocalizedShortLabel(SPANISH, "clave");
        /**/
        valor.setLocalizedLabel(ENGLISH, "application attribute value");
        valor.setLocalizedLabel(SPANISH, "valor del atributo de la aplicación");
        valor.setLocalizedShortLabel(ENGLISH, "value");
        valor.setLocalizedShortLabel(SPANISH, "valor");
        // </editor-fold>
    }

}
