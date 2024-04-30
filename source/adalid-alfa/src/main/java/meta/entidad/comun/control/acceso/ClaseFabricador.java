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

import adalid.commons.bundles.*;
import adalid.core.*;
import adalid.core.annotations.*;
import adalid.core.enums.*;
import adalid.core.interfaces.*;
import adalid.core.properties.*;
import java.lang.reflect.Field;
import java.util.Locale;

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
//@EntityReferenceDisplay(filterType = EntityReferenceFilterType.TEXTBOX)
public class ClaseFabricador extends AbstractPersistentEnumerationEntity {

    public static final String FCSS_VALIDATOR = "fcssValidator";

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

    @StringField(maxLength = 200)
    public StringProperty codigoConjuntoSegmento;

    @StringField(maxLength = 200)
    public StringProperty nombreConjuntoSegmento;

    @DescriptionProperty
    @StringField(maxLength = 0)
    public StringProperty descripcionConjuntoSegmento;

    @ColumnField(nullable = Kleenean.FALSE)
    public BooleanProperty elemental;

    public Instance C01, C02, C03, C04, C05, C06;

    public Instance C0A, C0B, C0C, C0D, C0E, C0F, C0G;

    public Instance C07, C08;

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
        /**/
        elemental.setInitialValue(false);
        elemental.setDefaultValue(false);
        /**/
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
        codigoConjuntoSegmento.setLocalizedLabel(ENGLISH, "segment set code");
        codigoConjuntoSegmento.setLocalizedLabel(SPANISH, "código del conjunto de segmentos");
        /**/
        nombreConjuntoSegmento.setLocalizedLabel(ENGLISH, "segment set name");
        nombreConjuntoSegmento.setLocalizedLabel(SPANISH, "nombre del conjunto de segmentos");
        /**/
        descripcionConjuntoSegmento.setLocalizedLabel(ENGLISH, "segment set description");
        descripcionConjuntoSegmento.setLocalizedLabel(SPANISH, "descripción del conjunto de segmentos");
        /**/
        elemental.setLocalizedLabel(ENGLISH, "requires elements");
        elemental.setLocalizedLabel(SPANISH, "requiere elementos");
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
        setInstanceFields(C07, "usuario", "UsuariosSupervisadosIndirectamente");
        setInstanceFields(C08, "usuario", "UsuariosSupervisadosIndirectamenteSinIncluirSupervisor");
        /**/
        setInstanceFields(C0A, "grupo_usuario", "GrupoDelUsuarioActual");
        setInstanceFields(C0B, "grupo_usuario", "GrupoDelUsuarioActualConSusSubgrupos");
        setInstanceFields(C0C, "grupo_usuario", "GrupoDelUsuarioActualConSusSubgruposDirectos");
        setInstanceFields(C0D, "grupo_usuario", "SubgruposDelGrupoDelUsuarioActual");
        setInstanceFields(C0E, "grupo_usuario", "SubgruposDirectosDelGrupoDelUsuarioActual");
        setInstanceFields(C0F, "grupo_usuario", "GruposDelConjuntoConSusSubgrupos", true);
        setInstanceFields(C0G, "grupo_usuario", "GruposDelConjuntoConSusSubgruposDirectos", true);
        /**/
    }

    private static final String PAQUETE = "project_root_package_name.lib.core.control";

    private static final String PREFIJO = "FabricadorConjunto";

    private void setInstanceFields(Instance clase, String tabla, String conjunto) {
        setInstanceFields(clase, tabla, conjunto, false);
    }

    private void setInstanceFields(Instance clase, String tabla, String conjunto, boolean elementado) {
        String codigoClaseFabricador = PREFIJO + conjunto;
        clase.newInstanceField(codigo, codigoClaseFabricador);
        clase.newInstanceField(codigoClaseRecurso, tabla);
        clase.newInstanceField(nombreClaseFabricador, PAQUETE + "." + codigoClaseFabricador);
        clase.newInstanceField(elemental, elementado);
        setInstanceFields(clase, codigoClaseFabricador, ENGLISH);
        setInstanceFields(clase, codigoClaseFabricador, SPANISH);
    }

    private void setInstanceFields(Instance clase, String codigoClaseFabricador, Locale locale) {
        String code = Bundle.getTrimmedToNullString(codigoClaseFabricador + ".code", locale);
        String name = Bundle.getTrimmedToNullString(codigoClaseFabricador + ".name", locale);
        String description = Bundle.getTrimmedToNullString(codigoClaseFabricador + ".description", locale);
        clase.newInstanceField(codigoConjuntoSegmento, code, locale);
        clase.newInstanceField(nombreConjuntoSegmento, name, locale);
        clase.newInstanceField(descripcionConjuntoSegmento, description, locale);
    }

}
