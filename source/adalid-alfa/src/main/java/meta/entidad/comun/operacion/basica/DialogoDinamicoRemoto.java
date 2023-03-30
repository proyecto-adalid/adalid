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
package meta.entidad.comun.operacion.basica;

import adalid.core.*;
import adalid.core.annotations.*;
import adalid.core.enums.*;
import adalid.core.interfaces.*;
import adalid.core.properties.*;
import java.lang.reflect.Field;

/**
 * @author Jorge Campins
 */
@EntityClass(independent = Kleenean.TRUE, resourceType = ResourceType.OPERATION, resourceGender = ResourceGender.MASCULINE)
@EntityCodeGen(bws = Kleenean.FALSE, fws = Kleenean.FALSE)
@EntitySelectOperation(enabled = Kleenean.FALSE, access = OperationAccess.PRIVATE)
@EntityInsertOperation(enabled = Kleenean.FALSE, access = OperationAccess.PRIVATE)
@EntityUpdateOperation(enabled = Kleenean.FALSE, access = OperationAccess.PRIVATE)
@EntityDeleteOperation(enabled = Kleenean.FALSE, access = OperationAccess.PRIVATE)
@EntityTableView(enabled = Kleenean.FALSE)
@EntityDetailView(enabled = Kleenean.FALSE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
public class DialogoDinamicoRemoto extends AbstractPersistentEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public DialogoDinamicoRemoto(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of DialogoDinamicoRemoto's attributes">
        setLocalizedLabel(ENGLISH, "RDD");
        setLocalizedLabel(SPANISH, "DDR");
        setLocalizedCollectionLabel(ENGLISH, "Remote Dynamic Dialogs");
        setLocalizedCollectionLabel(SPANISH, "Diálogos Dinámicos Remotos");
        // </editor-fold>
    }

    @PrimaryKey
    public LongProperty id;

    @VersionProperty
    public LongProperty version;

    @PropertyField(export = Kleenean.TRUE, report = Kleenean.TRUE)
    @StringField(maxLength = 200)
    public StringProperty clase;

    @PropertyField(export = Kleenean.TRUE, report = Kleenean.TRUE)
    @StringField(maxLength = Constants.MAX_STRING_LENGTH)
    public StringProperty valor;

    @Override
    protected void settleProperties() {
        super.settleProperties();
        /**/
        clase.setLocalizedLabel(ENGLISH, "class");
        clase.setLocalizedLabel(SPANISH, "clase");
        /**/
        valor.setLocalizedLabel(ENGLISH, "value");
        valor.setLocalizedLabel(SPANISH, "valor");
        /**/
    }

}
