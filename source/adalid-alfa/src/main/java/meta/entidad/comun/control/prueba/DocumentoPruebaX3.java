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
@DiscriminatorValue(TipoDocumentoPrueba.TIPO_PROGRAMA)
public class DocumentoPruebaX3 extends DocumentoPrueba {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public DocumentoPruebaX3(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @Override
    protected void addAllocationStrings() {
        super.addAllocationStrings();
        super.addAllocationStrings(
            "programa.propietario"
        );
    }

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of DocumentoPruebaX3's attributes">
        setLocalizedLabel(ENGLISH, "test program document");
        setLocalizedLabel(SPANISH, "documento de programa de prueba");
        setLocalizedShortLabel(ENGLISH, "document");
        setLocalizedShortLabel(SPANISH, "documento");
        setLocalizedCollectionLabel(ENGLISH, "Test Program Documents");
        setLocalizedCollectionLabel(SPANISH, "Documentos de Programas de Prueba");
        setLocalizedCollectionShortLabel(ENGLISH, "Documents");
        setLocalizedCollectionShortLabel(SPANISH, "Documentos");
        // </editor-fold>
    }

    @ColumnField(nullable = Kleenean.FALSE)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.TABLE_AND_DETAIL)
    @PropertyField(create = Kleenean.TRUE, update = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public ProgramaPrueba programa;

    @Override
    protected void settleProperties() {
        super.settleProperties();
        // <editor-fold defaultstate="collapsed" desc="localization of DocumentoPruebaX3's properties">
        programa.setLocalizedLabel(ENGLISH, "program");
        programa.setLocalizedLabel(SPANISH, "programa");
        // </editor-fold>
    }

    @Override
    protected void settleLinks() {
        super.settleLinks();
        linkForeignOwnerProperty(programa.propietario);
    }

}
