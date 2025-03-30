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
@EntityReferenceSearch(searchType = SearchType.RADIO)
public class AccionArchivoCargado extends AbstractPersistentEnumerationEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public AccionArchivoCargado(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public IntegerProperty numero;

    @BusinessKey
    public StringProperty codigo;

    public Instance CONSERVAR;

    public Instance REEMPLAZAR;

    public Instance ELIMINAR;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of AccionArchivoCargado's attributes">
        setLocalizedLabel(ENGLISH, "uploaded-file action");
        setLocalizedLabel(SPANISH, "acción de archivo cargado");
        setLocalizedShortLabel(ENGLISH, "action");
        setLocalizedShortLabel(SPANISH, "acción");
        setLocalizedCollectionLabel(ENGLISH, "Uploaded-file Actions");
        setLocalizedCollectionLabel(SPANISH, "Acciones de Archivo Cargado");
        setLocalizedCollectionShortLabel(ENGLISH, "Actions");
        setLocalizedCollectionShortLabel(SPANISH, "Acciones");
        // </editor-fold>
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        // <editor-fold defaultstate="collapsed" desc="localization of AccionArchivoCargado's properties">
        numero.setLocalizedLabel(ENGLISH, "uploaded-file action number");
        numero.setLocalizedLabel(SPANISH, "número de la acción de archivo cargado");
        numero.setLocalizedShortLabel(ENGLISH, "number");
        numero.setLocalizedShortLabel(SPANISH, "número");
        /**/
        codigo.setLocalizedLabel(ENGLISH, "uploaded-file action code");
        codigo.setLocalizedLabel(SPANISH, "código de la acción de archivo cargado");
        codigo.setLocalizedShortLabel(ENGLISH, "code");
        codigo.setLocalizedShortLabel(SPANISH, "código");
        // </editor-fold>
    }

    @Override
    protected void settleInstances() {
        super.settleInstances();
        // <editor-fold defaultstate="collapsed" desc="localization of AccionArchivoCargado's instances">
        /**/
        CONSERVAR.newInstanceField(codigo, "Keep", ENGLISH);
        CONSERVAR.newInstanceField(codigo, "Conservar", SPANISH);
        /**/
        REEMPLAZAR.newInstanceField(codigo, "Replace", ENGLISH);
        REEMPLAZAR.newInstanceField(codigo, "Reemplazar", SPANISH);
        /**/
        ELIMINAR.newInstanceField(codigo, "Delete", ENGLISH);
        ELIMINAR.newInstanceField(codigo, "Eliminar", SPANISH);
        /**/
        // </editor-fold>
    }

}
