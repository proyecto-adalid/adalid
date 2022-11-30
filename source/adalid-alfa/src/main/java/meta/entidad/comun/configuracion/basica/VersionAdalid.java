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
@EntityUpdateOperation(enabled = Kleenean.TRUE)
@EntityDeleteOperation(enabled = Kleenean.FALSE)
@EntityTableView(enabled = Kleenean.FALSE)
@EntityDetailView(enabled = Kleenean.FALSE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
public class VersionAdalid extends AbstractPersistentEnumerationEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public VersionAdalid(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public IntegerProperty numero;

    @VersionProperty
    public LongProperty version;

    @BusinessKey
    public StringProperty codigo;

    @ColumnField(nullable = Kleenean.TRUE)
    @PropertyField(update = Kleenean.TRUE, table = Kleenean.TRUE)
    public DateProperty fecha;

    public Instance V1R0;

    public Instance V2R0, V2R1;

    public Instance V3R0;

    public Instance V4R0, V4R1, V4R2, V4R3, V4R4, V4R5, V4R6;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of VersionAdalid's attributes">
        setLocalizedLabel(ENGLISH, "Adalid version");
        setLocalizedLabel(SPANISH, "versión de Adalid");
        setLocalizedCollectionLabel(ENGLISH, "Adalid Versions");
        setLocalizedCollectionLabel(SPANISH, "Versiones de Adalid");
        // </editor-fold>
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        // <editor-fold defaultstate="collapsed" desc="localization of VersionAdalid's properties">
        /**/
        numero.setLocalizedLabel(ENGLISH, "Adalid version number");
        numero.setLocalizedLabel(SPANISH, "número de la versión de Adalid");
        numero.setLocalizedShortLabel(ENGLISH, "number");
        numero.setLocalizedShortLabel(SPANISH, "número");
        /**/
        version.setLocalizedLabel(ENGLISH, "Adalid version version");
        version.setLocalizedLabel(SPANISH, "versión de la versión de Adalid");
        version.setLocalizedShortLabel(ENGLISH, "version");
        version.setLocalizedShortLabel(SPANISH, "versión");
        /**/
        codigo.setLocalizedLabel(ENGLISH, "Adalid version code");
        codigo.setLocalizedLabel(SPANISH, "código de la versión de Adalid");
        codigo.setLocalizedShortLabel(ENGLISH, "code");
        codigo.setLocalizedShortLabel(SPANISH, "código");
        /**/
        fecha.setLocalizedLabel(ENGLISH, "installation date");
        fecha.setLocalizedLabel(SPANISH, "fecha de instalación");
        /**/
        // </editor-fold>
    }

    @Override
    protected void settleInstances() {
        super.settleInstances();
        V1R0.newInstanceField(codigo, "V1R0");
        V2R0.newInstanceField(codigo, "V2R0");
        V2R1.newInstanceField(codigo, "V2R1");
        V3R0.newInstanceField(codigo, "V3R0");
        V4R0.newInstanceField(codigo, "V4R0");
        V4R1.newInstanceField(codigo, "V4R1");
        V4R2.newInstanceField(codigo, "V4R2");
        V4R3.newInstanceField(codigo, "V4R3");
        V4R4.newInstanceField(codigo, "V4R4");
        V4R5.newInstanceField(codigo, "V4R5");
        V4R6.newInstanceField(codigo, "V4R6");
    }

}
