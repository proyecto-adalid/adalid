/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package meta.entidad.comun.configuracion.basica;

import adalid.core.*;
import adalid.core.annotations.*;
import adalid.core.enums.*;
import adalid.core.interfaces.*;
import adalid.core.properties.*;
import java.lang.reflect.Field;
import meta.proyecto.base.ProyectoBase;

/**
 * @author Jorge Campins
 */
@EntityClass(independent = Kleenean.TRUE, resourceType = ResourceType.CONFIGURATION, resourceGender = ResourceGender.MASCULINE)
@EntitySelectOperation(enabled = Kleenean.TRUE, access = OperationAccess.PUBLIC, rowsLimit = 0)
@EntityInsertOperation(enabled = Kleenean.FALSE)
@EntityUpdateOperation(enabled = Kleenean.FALSE)
@EntityDeleteOperation(enabled = Kleenean.FALSE)
@EntityTableView(enabled = Kleenean.FALSE)
@EntityDetailView(enabled = Kleenean.FALSE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
public class NivelOpcionMenu extends AbstractPersistentEnumerationEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    @Deprecated
    private NivelOpcionMenu() {
        this(null, null);
    }

    public NivelOpcionMenu(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public IntegerProperty numeroNivelOpcionMenu;

    @BusinessKey
    public StringProperty codigoNivelOpcionMenu;

    public IntegerProperty digitosNivelOpcionMenu;

    public Instance NIVEL_01;

    public Instance NIVEL_02;

    public Instance NIVEL_03;

    public Instance NIVEL_04;

    public Instance NIVEL_05;

    public Instance NIVEL_06;

    public Instance NIVEL_07;

    public Instance NIVEL_08;

    public Instance NIVEL_09;

    public Instance NIVEL_10;

    public Instance NIVEL_11;

    public Instance NIVEL_12;

    public Instance NIVEL_13;

    public Instance NIVEL_14;

    public Instance NIVEL_15;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        setDefaultLabel("nivel de opción del menú");
        setDefaultShortLabel("nivel");
        setDefaultCollectionLabel("niveles de opción del menú");
        setDefaultCollectionShortLabel("niveles");
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        digitosNivelOpcionMenu.setInitialValue(2);
        digitosNivelOpcionMenu.setDefaultValue(2);
    }

}
