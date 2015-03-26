/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package meta.entidad.comun.control.prueba;

import adalid.core.annotations.*;
import adalid.core.enums.*;
import adalid.core.interfaces.*;
import java.lang.reflect.Field;
import meta.proyecto.base.ProyectoBase;

/**
 * @author Jorge Campins
 */
@DiscriminatorValue(TipoDocumentoPrueba.TIPO_LINEA)
public class DocumentoPruebaX4 extends DocumentoPrueba {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    @Deprecated()
    private DocumentoPruebaX4() {
        this(null, null);
    }

    public DocumentoPruebaX4(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        setDefaultLabel("documento de línea de programa de prueba");
        setDefaultShortLabel("documento");
        setDefaultCollectionLabel("documentos de líneas de programas de prueba");
        setDefaultCollectionShortLabel("documentos");
    }

    @Allocation(maxDepth = 3, maxRound = 0)
    @ColumnField(nullable = Kleenean.FALSE)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.TABLE_AND_DETAIL)
    @PropertyField(create = Kleenean.TRUE, update = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public LineaPrueba linea;

    @Override
    protected void settleLinks() {
        super.settleLinks();
        linkForeignOwnerProperty(linea.programa.propietario);
    }

}
