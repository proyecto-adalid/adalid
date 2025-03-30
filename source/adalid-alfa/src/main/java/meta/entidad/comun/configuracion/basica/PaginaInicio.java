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
@EntityClass(independent = Kleenean.TRUE, resourceType = ResourceType.CONFIGURATION, resourceGender = ResourceGender.FEMININE, startWith = 0)
@EntityCodeGen(bws = Kleenean.FALSE, fws = Kleenean.FALSE)
@EntitySelectOperation(enabled = Kleenean.TRUE, access = OperationAccess.PUBLIC, rowsLimit = 0)
@EntityInsertOperation(enabled = Kleenean.FALSE)
@EntityUpdateOperation(enabled = Kleenean.FALSE)
@EntityDeleteOperation(enabled = Kleenean.FALSE)
@EntityTableView(enabled = Kleenean.FALSE)
@EntityDetailView(enabled = Kleenean.FALSE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
public class PaginaInicio extends AbstractPersistentEnumerationEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public PaginaInicio(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public IntegerProperty numero;

    @BusinessKey
    public StringProperty codigo;

    @UniformResourceLocator
    public StringProperty pagina;

    public Instance PREDETERMINADA;

    public Instance MENU;

    public Instance FAVORITOS;

    public Instance TAREAS;

    public Instance PAGINA_MENU;

    public Instance OTRA_PAGINA;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of PaginaInicio's attributes">
        setLocalizedLabel(ENGLISH, "start page");
        setLocalizedLabel(SPANISH, "página de inicio");
        setLocalizedCollectionLabel(ENGLISH, "Start Pages");
        setLocalizedCollectionLabel(SPANISH, "Páginas de Inicio");
        /**/
        setLocalizedDescription(ENGLISH, "Each instance of " + b("Start Pages") + " represents a "
            + "web page displayed after starting a new session."
            + "");
        setLocalizedDescription(SPANISH, "Cada instancia de " + b("Páginas de Inicio") + " representa una "
            + "página web mostrada después de iniciar una nueva sesión de trabajo."
            + "");
        /**/
        setLocalizedShortDescription(ENGLISH, "web page displayed after starting a new session");
        setLocalizedShortDescription(SPANISH, "página web mostrada después de iniciar una nueva sesión de trabajo");
        /**/
        // </editor-fold>
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        // <editor-fold defaultstate="collapsed" desc="localization of PaginaInicio's properties">
        numero.setLocalizedLabel(ENGLISH, "start page number");
        numero.setLocalizedLabel(SPANISH, "número de la página de inicio");
        numero.setLocalizedShortLabel(ENGLISH, "number");
        numero.setLocalizedShortLabel(SPANISH, "número");
        /**/
        codigo.setLocalizedLabel(ENGLISH, "start page code");
        codigo.setLocalizedLabel(SPANISH, "código de la página de inicio");
        codigo.setLocalizedShortLabel(ENGLISH, "code");
        codigo.setLocalizedShortLabel(SPANISH, "código");
        /**/
        pagina.setLocalizedLabel(ENGLISH, "page");
        pagina.setLocalizedLabel(SPANISH, "página");
        // </editor-fold>
    }

    @Override
    protected void settleInstances() {
        super.settleInstances();
        MENU.newInstanceField(codigo, "Menú");
        PAGINA_MENU.newInstanceField(codigo, "Página del menú");
        OTRA_PAGINA.newInstanceField(codigo, "Otra página");
        /*
        PREDETERMINADA.newInstanceField(pagina, "/faces/views/base/code/Menu.xhtml");
        MENU.newInstanceField(pagina, "/faces/views/base/code/Menu.xhtml");
        FAVORITOS.newInstanceField(pagina, "/faces/views/base/code/Favoritos.xhtml");
        TAREAS.newInstanceField(pagina, "/faces/views/base/crop/consulta/recursos/basicos/tarea/TareaUsuario11.xhtml");
        **/
        // <editor-fold defaultstate="collapsed" desc="localization of PaginaInicio's instances">
        PREDETERMINADA.newInstanceField(codigo, "Default", ENGLISH);
        PREDETERMINADA.newInstanceField(codigo, "Predeterminada", SPANISH);
        /**/
        MENU.newInstanceField(codigo, "Menu", ENGLISH);
        MENU.newInstanceField(codigo, "Menú", SPANISH);
        /**/
        FAVORITOS.newInstanceField(codigo, "Favorites", ENGLISH);
        FAVORITOS.newInstanceField(codigo, "Favoritos", SPANISH);
        /**/
        TAREAS.newInstanceField(codigo, "Tasks", ENGLISH);
        TAREAS.newInstanceField(codigo, "Tareas", SPANISH);
        /**/
        PAGINA_MENU.newInstanceField(codigo, "Menu page", ENGLISH);
        PAGINA_MENU.newInstanceField(codigo, "Página del menú", SPANISH);
        /**/
        OTRA_PAGINA.newInstanceField(codigo, "Other page", ENGLISH);
        OTRA_PAGINA.newInstanceField(codigo, "Otra página", SPANISH);
        // </editor-fold>
    }

}
