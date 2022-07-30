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
package meta.entidad.comun.control.prueba;

import adalid.core.annotations.*;
import adalid.core.enums.*;
import adalid.core.interfaces.*;
import java.lang.reflect.Field;

/**
 * @author Jorge Campins
 */
@DiscriminatorValue(TipoDocumentoPrueba.TIPO_ESCENARIO)
public class DocumentoPruebaX2 extends DocumentoPrueba {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public DocumentoPruebaX2(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @Override
    protected void addAllocationStrings() {
        super.addAllocationStrings();
        super.addAllocationStrings(
            "escenario.caso.propietario"
        );
    }

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of DocumentoPruebaX2's attributes">
        setLocalizedLabel(ENGLISH, "test case scenario document");
        setLocalizedLabel(SPANISH, "documento de escenario de caso de prueba");
        setLocalizedShortLabel(ENGLISH, "document");
        setLocalizedShortLabel(SPANISH, "documento");
        setLocalizedCollectionLabel(ENGLISH, "Test Case Scenario Documents");
        setLocalizedCollectionLabel(SPANISH, "Documentos de Escenarios de Casos de Prueba");
        setLocalizedCollectionShortLabel(ENGLISH, "Documents");
        setLocalizedCollectionShortLabel(SPANISH, "Documentos");
        // </editor-fold>
    }

    @ColumnField(nullable = Kleenean.FALSE)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.TABLE_AND_DETAIL)
    @PropertyField(create = Kleenean.TRUE, update = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public EscenarioPrueba escenario;

    @Override
    protected void settleProperties() {
        super.settleProperties();
        // <editor-fold defaultstate="collapsed" desc="localization of DocumentoPruebaX2's properties">
        escenario.setLocalizedLabel(ENGLISH, "scenario");
        escenario.setLocalizedLabel(SPANISH, "escenario");
        // </editor-fold>
    }

    @Override
    protected void settleLinks() {
        super.settleLinks();
        linkForeignOwnerProperty(escenario.caso.propietario);
    }

}
