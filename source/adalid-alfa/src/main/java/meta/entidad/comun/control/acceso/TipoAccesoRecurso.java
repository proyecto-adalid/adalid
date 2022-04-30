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
public class TipoAccesoRecurso extends AbstractPersistentEnumerationEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public TipoAccesoRecurso(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public IntegerProperty numero;

    @BusinessKey
    public StringProperty codigo;

    public Instance NINGUNO;

    public Instance LECTURA;

    public Instance ESCRITURA;

    public Instance ILIMITADO;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of TipoAccesoRecurso's attributes">
        setLocalizedLabel(ENGLISH, "resource access type");
        setLocalizedLabel(SPANISH, "tipo de acceso a recursos");
        setLocalizedShortLabel(ENGLISH, "access type");
        setLocalizedShortLabel(SPANISH, "tipo de acceso");
        setLocalizedCollectionLabel(ENGLISH, "Resource Access Types");
        setLocalizedCollectionLabel(SPANISH, "Tipos de Acceso a Recursos");
        setLocalizedCollectionShortLabel(ENGLISH, "Access Types");
        setLocalizedCollectionShortLabel(SPANISH, "Tipos de Acceso");
        // </editor-fold>
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        // <editor-fold defaultstate="collapsed" desc="localization of TipoAccesoRecurso's properties">
        /**/
        numero.setLocalizedLabel(ENGLISH, "resource access type number");
        numero.setLocalizedLabel(SPANISH, "número del tipo de acceso");
        numero.setLocalizedShortLabel(ENGLISH, "number");
        numero.setLocalizedShortLabel(SPANISH, "número");
        /**/
        codigo.setLocalizedLabel(ENGLISH, "resource access type code");
        codigo.setLocalizedLabel(SPANISH, "código del tipo de acceso");
        codigo.setLocalizedShortLabel(ENGLISH, "code");
        codigo.setLocalizedShortLabel(SPANISH, "código");
        /**/
        // </editor-fold>
    }

    @Override
    protected void settleInstances() {
        super.settleInstances();
        /**/
        NINGUNO.newInstanceField(numero, 0);
        NINGUNO.newInstanceField(codigo, "Ninguno");
        /**/
        LECTURA.newInstanceField(numero, 1);
        LECTURA.newInstanceField(codigo, "Lectura");
        /**/
        ESCRITURA.newInstanceField(numero, 2);
        ESCRITURA.newInstanceField(codigo, "Escritura");
        /**/
        ILIMITADO.newInstanceField(numero, 4);
        ILIMITADO.newInstanceField(codigo, "Ilimitado");
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of TipoAccesoRecurso's instances">
        /**/
        NINGUNO.newInstanceField(codigo, "None", ENGLISH);
        NINGUNO.newInstanceField(codigo, "Ninguno", SPANISH);
        /**/
        LECTURA.newInstanceField(codigo, "Reading", ENGLISH);
        LECTURA.newInstanceField(codigo, "Lectura", SPANISH);
        /**/
        ESCRITURA.newInstanceField(codigo, "Writing", ENGLISH);
        ESCRITURA.newInstanceField(codigo, "Escritura", SPANISH);
        /**/
        ILIMITADO.newInstanceField(codigo, "Unlimited", ENGLISH);
        ILIMITADO.newInstanceField(codigo, "Ilimitado", SPANISH);
        /**/
        // </editor-fold>
    }

}
