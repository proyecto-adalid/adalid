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
@EntityClass(independent = Kleenean.FALSE, variant = Kleenean.TRUE, resourceType = ResourceType.UNSPECIFIED, resourceGender = ResourceGender.MASCULINE)
@EntityCodeGen(bws = Kleenean.FALSE, fws = Kleenean.FALSE)
@EntitySelectOperation(enabled = Kleenean.FALSE)
@EntityInsertOperation(enabled = Kleenean.FALSE)
@EntityUpdateOperation(enabled = Kleenean.FALSE)
@EntityDeleteOperation(enabled = Kleenean.FALSE)
@EntityTableView(enabled = Kleenean.FALSE)
@EntityDetailView(enabled = Kleenean.FALSE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
public class Segmento extends AbstractPersistentEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public Segmento(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public LongProperty idSegmento;

    @BusinessKey
    public StringProperty codigoSegmento;

    @NameProperty
    public StringProperty nombreSegmento;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of Segmento's attributes">
        setLocalizedLabel(ENGLISH, "segment");
        setLocalizedLabel(SPANISH, "segmento");
        setLocalizedCollectionLabel(ENGLISH, "Segments");
        setLocalizedCollectionLabel(SPANISH, "Segmentos");
        setLocalizedDescription(ENGLISH, "reference to a row of a table in the database");
        setLocalizedDescription(SPANISH, "referencia a una fila de una tabla en la base de datos");
        // </editor-fold>
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        // <editor-fold defaultstate="collapsed" desc="localization of Segmento's properties">
        codigoSegmento.setLocalizedLabel(ENGLISH, "segment code");
        codigoSegmento.setLocalizedLabel(SPANISH, "código del segmento");
        codigoSegmento.setLocalizedShortLabel(ENGLISH, "code");
        codigoSegmento.setLocalizedShortLabel(SPANISH, "código");
        /**/
        nombreSegmento.setLocalizedLabel(ENGLISH, "segment name");
        nombreSegmento.setLocalizedLabel(SPANISH, "nombre del segmento");
        nombreSegmento.setLocalizedShortLabel(ENGLISH, "name");
        nombreSegmento.setLocalizedShortLabel(SPANISH, "nombre");
        // </editor-fold>
    }

}
