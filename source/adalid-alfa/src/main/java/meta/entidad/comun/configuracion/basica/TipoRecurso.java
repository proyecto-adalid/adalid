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
public class TipoRecurso extends AbstractPersistentEnumerationEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public TipoRecurso(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public IntegerProperty numero;

    @BusinessKey
    public StringProperty codigo;

    public Instance CONFIGURACION_BASICA;

    public Instance CONFIGURACION_FIJA;

    public Instance CONFIGURACION_MANUAL;

    public Instance CONFIGURACION_AUTOMATICA;

    public Instance GESTION_MANUAL;

    public Instance GESTION_AUTOMATICA;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of TipoRecurso's attributes">
        setLocalizedLabel(ENGLISH, "resource type");
        setLocalizedLabel(SPANISH, "tipo de recurso");
        setLocalizedCollectionLabel(ENGLISH, "Resource Types");
        setLocalizedCollectionLabel(SPANISH, "Tipos de Recurso");
        // </editor-fold>
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        // <editor-fold defaultstate="collapsed" desc="localization of TipoRecurso's properties">
        numero.setLocalizedLabel(ENGLISH, "resource type number");
        numero.setLocalizedLabel(SPANISH, "número del tipo de recurso");
        numero.setLocalizedShortLabel(ENGLISH, "number");
        numero.setLocalizedShortLabel(SPANISH, "número");
        /**/
        codigo.setLocalizedLabel(ENGLISH, "resource type code");
        codigo.setLocalizedLabel(SPANISH, "código del tipo de recurso");
        codigo.setLocalizedShortLabel(ENGLISH, "code");
        codigo.setLocalizedShortLabel(SPANISH, "código");
        // </editor-fold>
    }

    @Override
    protected void settleInstances() {
        super.settleInstances();
        CONFIGURACION_BASICA.newInstanceField(numero, 11);
        CONFIGURACION_BASICA.newInstanceField(codigo, "Configuraci\u00F3n b\u00E1sica");
        CONFIGURACION_FIJA.newInstanceField(numero, 12);
        CONFIGURACION_FIJA.newInstanceField(codigo, "Configuraci\u00F3n fija");
        CONFIGURACION_MANUAL.newInstanceField(numero, 13);
        CONFIGURACION_MANUAL.newInstanceField(codigo, "Configuraci\u00F3n manual");
        CONFIGURACION_AUTOMATICA.newInstanceField(numero, 14);
        CONFIGURACION_AUTOMATICA.newInstanceField(codigo, "Configuraci\u00F3n autom\u00E1tica");
        GESTION_MANUAL.newInstanceField(numero, 21);
        GESTION_MANUAL.newInstanceField(codigo, "Gesti\u00F3n manual");
        GESTION_AUTOMATICA.newInstanceField(numero, 22);
        GESTION_AUTOMATICA.newInstanceField(codigo, "Gesti\u00F3n autom\u00E1tica");
        // <editor-fold defaultstate="collapsed" desc="localization of TipoRecurso's instances">
        CONFIGURACION_BASICA.newInstanceField(codigo, "Basic configuration", ENGLISH);
        CONFIGURACION_BASICA.newInstanceField(codigo, "Configuración básica", SPANISH);
        /**/
        CONFIGURACION_FIJA.newInstanceField(codigo, "Fixed configuration", ENGLISH);
        CONFIGURACION_FIJA.newInstanceField(codigo, "Configuración fija", SPANISH);
        /**/
        CONFIGURACION_MANUAL.newInstanceField(codigo, "Manual configuration", ENGLISH);
        CONFIGURACION_MANUAL.newInstanceField(codigo, "Configuración manual", SPANISH);
        /**/
        CONFIGURACION_AUTOMATICA.newInstanceField(codigo, "Automatic configuration", ENGLISH);
        CONFIGURACION_AUTOMATICA.newInstanceField(codigo, "Configuración automática", SPANISH);
        /**/
        GESTION_MANUAL.newInstanceField(codigo, "Manual management", ENGLISH);
        GESTION_MANUAL.newInstanceField(codigo, "Gestión manual", SPANISH);
        /**/
        GESTION_AUTOMATICA.newInstanceField(codigo, "Automatic management", ENGLISH);
        GESTION_AUTOMATICA.newInstanceField(codigo, "Gestión automática", SPANISH);
        // </editor-fold>
    }

}
