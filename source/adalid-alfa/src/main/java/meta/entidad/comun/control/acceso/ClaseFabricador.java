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
@EntityClass(independent = Kleenean.TRUE, resourceType = ResourceType.CONFIGURATION, resourceGender = ResourceGender.FEMININE)
@EntityCodeGen(bws = Kleenean.FALSE, fws = Kleenean.FALSE)
@EntitySelectOperation(enabled = Kleenean.TRUE, access = OperationAccess.PUBLIC, rowsLimit = 0)
@EntityInsertOperation(enabled = Kleenean.FALSE)
@EntityUpdateOperation(enabled = Kleenean.FALSE)
@EntityDeleteOperation(enabled = Kleenean.FALSE)
@EntityTableView(enabled = Kleenean.FALSE)
@EntityDetailView(enabled = Kleenean.FALSE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
public class ClaseFabricador extends AbstractPersistentEnumerationEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public ClaseFabricador(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public IntegerProperty numero;

    @BusinessKey
    @StringField(maxLength = 100)
    public StringProperty codigo;

    @ColumnField(nullable = Kleenean.FALSE)
    @StringField(maxLength = 100)
    public StringProperty codigoClaseRecurso;

    @ColumnField(nullable = Kleenean.FALSE)
    @StringField(maxLength = 200)
    public StringProperty nombreClaseFabricador;

    public Instance C01, C02, C03, C04, C05, C06;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of ClaseFabricador's attributes">
        setLocalizedLabel(ENGLISH, "segment set factory class");
        setLocalizedLabel(SPANISH, "clase de fabricador de conjunto de segmentos");
        setLocalizedShortLabel(ENGLISH, "factory class");
        setLocalizedShortLabel(SPANISH, "clase de fabricador");
        setLocalizedCollectionLabel(ENGLISH, "Segment Set Factory Classes");
        setLocalizedCollectionLabel(SPANISH, "Clases de Fabricador de Conjunto de Segmentos");
        setLocalizedCollectionShortLabel(ENGLISH, "Factory Classes");
        setLocalizedCollectionShortLabel(SPANISH, "Clases de Fabricador");
        // </editor-fold>
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        // <editor-fold defaultstate="collapsed" desc="localization of ClaseFabricador's properties">
        /**/
        numero.setLocalizedLabel(ENGLISH, "factory class number");
        numero.setLocalizedLabel(SPANISH, "número de la clase de fabricador");
        numero.setLocalizedShortLabel(ENGLISH, "number");
        numero.setLocalizedShortLabel(SPANISH, "número");
        /**/
        codigo.setLocalizedLabel(ENGLISH, "factory class code");
        codigo.setLocalizedLabel(SPANISH, "código de la clase de fabricador");
        codigo.setLocalizedShortLabel(ENGLISH, "code");
        codigo.setLocalizedShortLabel(SPANISH, "código");
        /**/
        codigoClaseRecurso.setLocalizedLabel(ENGLISH, "resource class code");
        codigoClaseRecurso.setLocalizedLabel(SPANISH, "código de la clase de recurso");
        codigoClaseRecurso.setLocalizedShortLabel(ENGLISH, "resource class code");
        codigoClaseRecurso.setLocalizedShortLabel(SPANISH, "código clase recurso");
        /**/
        nombreClaseFabricador.setLocalizedLabel(ENGLISH, "factory class name");
        nombreClaseFabricador.setLocalizedLabel(SPANISH, "nombre de la clase de fabricador");
        nombreClaseFabricador.setLocalizedShortLabel(ENGLISH, "factory class name");
        nombreClaseFabricador.setLocalizedShortLabel(SPANISH, "nombre clase fabricador");
        /**/
        // </editor-fold>
    }

    @Override
    protected void settleInstances() {
        super.settleInstances();
        /**/
        setInstanceFields(C01, "usuario", "UsuariosSupervisados");
        setInstanceFields(C02, "usuario", "UsuariosSupervisadosDirectamente");
        setInstanceFields(C03, "usuario", "UsuariosSupervisadosSinIncluirSupervisor");
        setInstanceFields(C04, "usuario", "UsuariosSupervisadosDirectamenteSinIncluirSupervisor");
        setInstanceFields(C05, "usuario", "UsuariosUsuarioActual");
        setInstanceFields(C06, "usuario", "UsuariosUsuarioOperacionesCalendarizadas");
        /**/
    }

    private static final String PAQUETE = "project_root_package_name.lib.core.control";

    private static final String PREFIJO = "FabricadorConjunto";

    private void setInstanceFields(Instance clase, String tabla, String conjunto) {
        clase.newInstanceField(codigo, PREFIJO + conjunto);
        clase.newInstanceField(codigoClaseRecurso, tabla);
        clase.newInstanceField(nombreClaseFabricador, PAQUETE + "." + PREFIJO + conjunto);
    }

}
