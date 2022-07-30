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
import adalid.core.properties.*;
import java.lang.reflect.Field;

/**
 * @author Jorge Campins
 */
@DiscriminatorValue(TipoDocumentoPrueba.TIPO_EJECUCION_LINEA)
public class DocumentoPruebaX6 extends DocumentoPrueba {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public DocumentoPruebaX6(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @Override
    protected void addAllocationStrings() {
        super.addAllocationStrings();
        super.addAllocationStrings(
            "ejecucionLinea.ejecucion.responsable"
        );
    }

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of DocumentoPruebaX6's attributes">
        setLocalizedLabel(ENGLISH, "test program line execution document");
        setLocalizedLabel(SPANISH, "documento de ejecución de línea de programa de prueba");
        setLocalizedShortLabel(ENGLISH, "document");
        setLocalizedShortLabel(SPANISH, "documento");
        setLocalizedCollectionLabel(ENGLISH, "Test Program Line Execution Documents");
        setLocalizedCollectionLabel(SPANISH, "Documentos de Ejecuciones de Líneas de Programas de Prueba");
        setLocalizedCollectionShortLabel(ENGLISH, "Documents");
        setLocalizedCollectionShortLabel(SPANISH, "Documentos");
        // </editor-fold>
    }

    @ColumnField(nullable = Kleenean.FALSE)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.TABLE_AND_DETAIL)
    @PropertyField(create = Kleenean.TRUE, update = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public EjecucionLineaPrueba ejecucionLinea;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(hidden = Kleenean.TRUE)
    public BooleanProperty registrado;

    @Override
    protected void settleProperties() {
        super.settleProperties();
        registrado.setInitialValue(false);
        registrado.setDefaultValue(false);
        // <editor-fold defaultstate="collapsed" desc="localization of DocumentoPruebaX6's properties">
        ejecucionLinea.setLocalizedLabel(ENGLISH, "line execution");
        ejecucionLinea.setLocalizedLabel(SPANISH, "ejecución línea");
        /**/
        registrado.setLocalizedLabel(ENGLISH, "registered");
        registrado.setLocalizedLabel(SPANISH, "registrado");
        // </editor-fold>
    }

    @Override
    protected void settleLinks() {
        super.settleLinks();
        linkForeignOwnerProperty(ejecucionLinea.ejecucion.responsable);
    }

}
