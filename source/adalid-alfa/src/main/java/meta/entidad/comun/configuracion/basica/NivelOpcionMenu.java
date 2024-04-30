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
@EntityClass(independent = Kleenean.TRUE, resourceType = ResourceType.CONFIGURATION, resourceGender = ResourceGender.MASCULINE)
@EntityCodeGen(bws = Kleenean.FALSE, fws = Kleenean.FALSE)
@EntitySelectOperation(enabled = Kleenean.TRUE, access = OperationAccess.PUBLIC, rowsLimit = 0)
@EntityInsertOperation(enabled = Kleenean.FALSE)
@EntityUpdateOperation(enabled = Kleenean.FALSE)
@EntityDeleteOperation(enabled = Kleenean.FALSE)
@EntityTableView(enabled = Kleenean.FALSE)
@EntityDetailView(enabled = Kleenean.FALSE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
//@EntityReferenceDisplay(filterType = EntityReferenceFilterType.LIST)
public class NivelOpcionMenu extends AbstractPersistentEnumerationEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public NivelOpcionMenu(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public IntegerProperty numero;

    @BusinessKey
    public StringProperty codigo;

    public IntegerProperty digitos;

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
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of NivelOpcionMenu's attributes">
        setLocalizedLabel(ENGLISH, "menu option level");
        setLocalizedLabel(SPANISH, "nivel de opción de menú");
        setLocalizedShortLabel(ENGLISH, "level");
        setLocalizedShortLabel(SPANISH, "nivel");
        setLocalizedCollectionLabel(ENGLISH, "Menu Option Levels");
        setLocalizedCollectionLabel(SPANISH, "Niveles de Opción de Menú");
        setLocalizedCollectionShortLabel(ENGLISH, "Levels");
        setLocalizedCollectionShortLabel(SPANISH, "Niveles");
        // </editor-fold>
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        digitos.setInitialValue(2);
        digitos.setDefaultValue(2);
        // <editor-fold defaultstate="collapsed" desc="localization of NivelOpcionMenu's properties">
        numero.setLocalizedLabel(ENGLISH, "menu option level number");
        numero.setLocalizedLabel(SPANISH, "número del nivel de opción de menú");
        numero.setLocalizedShortLabel(ENGLISH, "number");
        numero.setLocalizedShortLabel(SPANISH, "número");
        /**/
        codigo.setLocalizedLabel(ENGLISH, "menu option level code");
        codigo.setLocalizedLabel(SPANISH, "código del nivel de opción de menú");
        codigo.setLocalizedShortLabel(ENGLISH, "code");
        codigo.setLocalizedShortLabel(SPANISH, "código");
        /**/
        digitos.setLocalizedLabel(ENGLISH, "menu option level digits");
        digitos.setLocalizedLabel(SPANISH, "digitos nivel opción menú");
        digitos.setLocalizedShortLabel(ENGLISH, "digits");
        digitos.setLocalizedShortLabel(SPANISH, "digitos");
        // </editor-fold>
    }

    @Override
    protected void settleInstances() {
        super.settleInstances();
        // <editor-fold defaultstate="collapsed" desc="localization of NivelOpcionMenu's instances">
        NIVEL_01.newInstanceField(codigo, "Level 01", ENGLISH);
        NIVEL_01.newInstanceField(codigo, "Nivel 01", SPANISH);
        /**/
        NIVEL_02.newInstanceField(codigo, "Level 02", ENGLISH);
        NIVEL_02.newInstanceField(codigo, "Nivel 02", SPANISH);
        /**/
        NIVEL_03.newInstanceField(codigo, "Level 03", ENGLISH);
        NIVEL_03.newInstanceField(codigo, "Nivel 03", SPANISH);
        /**/
        NIVEL_04.newInstanceField(codigo, "Level 04", ENGLISH);
        NIVEL_04.newInstanceField(codigo, "Nivel 04", SPANISH);
        /**/
        NIVEL_05.newInstanceField(codigo, "Level 05", ENGLISH);
        NIVEL_05.newInstanceField(codigo, "Nivel 05", SPANISH);
        /**/
        NIVEL_06.newInstanceField(codigo, "Level 06", ENGLISH);
        NIVEL_06.newInstanceField(codigo, "Nivel 06", SPANISH);
        /**/
        NIVEL_07.newInstanceField(codigo, "Level 07", ENGLISH);
        NIVEL_07.newInstanceField(codigo, "Nivel 07", SPANISH);
        /**/
        NIVEL_08.newInstanceField(codigo, "Level 08", ENGLISH);
        NIVEL_08.newInstanceField(codigo, "Nivel 08", SPANISH);
        /**/
        NIVEL_09.newInstanceField(codigo, "Level 09", ENGLISH);
        NIVEL_09.newInstanceField(codigo, "Nivel 09", SPANISH);
        /**/
        NIVEL_10.newInstanceField(codigo, "Level 10", ENGLISH);
        NIVEL_10.newInstanceField(codigo, "Nivel 10", SPANISH);
        /**/
        NIVEL_11.newInstanceField(codigo, "Level 11", ENGLISH);
        NIVEL_11.newInstanceField(codigo, "Nivel 11", SPANISH);
        /**/
        NIVEL_12.newInstanceField(codigo, "Level 12", ENGLISH);
        NIVEL_12.newInstanceField(codigo, "Nivel 12", SPANISH);
        /**/
        NIVEL_13.newInstanceField(codigo, "Level 13", ENGLISH);
        NIVEL_13.newInstanceField(codigo, "Nivel 13", SPANISH);
        /**/
        NIVEL_14.newInstanceField(codigo, "Level 14", ENGLISH);
        NIVEL_14.newInstanceField(codigo, "Nivel 14", SPANISH);
        /**/
        NIVEL_15.newInstanceField(codigo, "Level 15", ENGLISH);
        NIVEL_15.newInstanceField(codigo, "Nivel 15", SPANISH);
        // </editor-fold>
    }

}
