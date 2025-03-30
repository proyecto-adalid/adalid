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
package meta.modulo.base;

import adalid.core.*;
import adalid.core.annotations.*;
import adalid.core.enums.*;
import adalid.core.interfaces.*;
import adalid.core.jee.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import meta.enumeracion.base.TipoModuloBase;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

/**
 * @author Jorge Campins
 */
@ProjectModuleDocGen(classDiagram = Kleenean.FALSE)
public abstract class ModuloBase extends AbstractJavaWebModule {

    /**
     * propiedad enabled de la clase FragmentoVisor
     */
    private boolean _fragmentoVisorEnabled = true;

    /**
     * @return la propiedad enabled de la clase FragmentoVisor
     */
    public boolean isFragmentoVisorEnabled() {
        return _fragmentoVisorEnabled;
    }

    /**
     * @param enabled propiedad enabled de la clase FragmentoVisor
     */
    public void setFragmentoVisorEnabled(boolean enabled) {
        _fragmentoVisorEnabled = enabled;
    }

    /**
     * propiedad enabled de la lista desplegable listaFiltro1 de la clase FragmentoFiltro
     */
    private boolean _fragmentoFiltroListaFiltroEnabled = true;

    /**
     * @return la propiedad enabled de la lista desplegable listaFiltro1 de la clase FragmentoFiltro
     */
    public boolean isFragmentoFiltroListaFiltroEnabled() {
        return _fragmentoFiltroListaFiltroEnabled;
    }

    /**
     * @param enabled propiedad enabled de la lista desplegable listaFiltro1 de la clase FragmentoFiltro
     */
    public void setFragmentoFiltroListaFiltroEnabled(boolean enabled) {
        _fragmentoFiltroListaFiltroEnabled = enabled;
    }

    /**
     * propiedad enabled de los botones botonBuscarFiltro1, botonBuscarFiltro2 y botonBorrarFiltro1 de la clase FragmentoFiltro
     */
    private boolean _fragmentoFiltroBotonFiltroEnabled = true;

    /**
     * @return la propiedad enabled de los botones botonBuscarFiltro1, botonBuscarFiltro2 y botonBorrarFiltro1 de la clase FragmentoFiltro
     */
    public boolean isFragmentoFiltroBotonFiltroEnabled() {
        return _fragmentoFiltroBotonFiltroEnabled;
    }

    /**
     * @param enabled propiedad enabled de los botones botonBuscarFiltro1, botonBuscarFiltro2 y botonBorrarFiltro1 de la clase FragmentoFiltro
     */
    public void setFragmentoFiltroBotonFiltroEnabled(boolean enabled) {
        _fragmentoFiltroBotonFiltroEnabled = enabled;
    }

    /**
     * propiedad enabled del boton botonFavoritos1 de la clase FragmentoCabeza2
     */
    private boolean _fragmentoCabezaBotonAbrirFavoritosEnabled = true;

    /**
     * @return la propiedad enabled del boton botonFavoritos1 de la clase FragmentoCabeza2
     */
    public boolean isFragmentoCabezaBotonAbrirFavoritosEnabled() {
        return _fragmentoCabezaBotonAbrirFavoritosEnabled;
    }

    /**
     * @param enabled propiedad enabled del boton botonFavoritos1 de la clase FragmentoCabeza2
     */
    public void setFragmentoCabezaBotonAbrirFavoritosEnabled(boolean enabled) {
        _fragmentoCabezaBotonAbrirFavoritosEnabled = enabled;
    }

    /**
     * propiedad enabled del boton botonFavoritos2 de la clase FragmentoCabeza2
     */
    private boolean _fragmentoCabezaBotonAgregarFavoritosEnabled = true;

    /**
     * @return la propiedad enabled del boton botonFavoritos2 de la clase FragmentoCabeza2
     */
    public boolean isFragmentoCabezaBotonAgregarFavoritosEnabled() {
        return _fragmentoCabezaBotonAgregarFavoritosEnabled;
    }

    /**
     * @param enabled propiedad enabled del boton botonFavoritos2 de la clase FragmentoCabeza2
     */
    public void setFragmentoCabezaBotonAgregarFavoritosEnabled(boolean enabled) {
        _fragmentoCabezaBotonAgregarFavoritosEnabled = enabled;
    }

    /**
     * propiedad enabled del boton botonTareas1 de la clase FragmentoCabeza2
     */
    private boolean _fragmentoCabezaBotonAbrirTareasEnabled = true;

    /**
     * @return la propiedad enabled del boton botonTareas1 de la clase FragmentoCabeza2
     */
    public boolean isFragmentoCabezaBotonAbrirTareasEnabled() {
        return _fragmentoCabezaBotonAbrirTareasEnabled;
    }

    /**
     * @param enabled propiedad enabled del boton botonTareas1 de la clase FragmentoCabeza2
     */
    public void setFragmentoCabezaBotonAbrirTareasEnabled(boolean enabled) {
        _fragmentoCabezaBotonAbrirTareasEnabled = enabled;
    }

    /**
     * propiedad enabled del boton botonCambiarPassword1 de la clase FragmentoCabeza2
     */
    private boolean _fragmentoCabezaBotonCambiarPasswordEnabled = false;

    /**
     * @return la propiedad enabled del boton botonCambiarPassword1 de la clase FragmentoCabeza2
     */
    public boolean isFragmentoCabezaBotonCambiarPasswordEnabled() {
        return _fragmentoCabezaBotonCambiarPasswordEnabled;
    }

    /**
     * @param enabled propiedad enabled del boton botonCambiarPassword1 de la clase FragmentoCabeza2
     */
    public void setFragmentoCabezaBotonCambiarPasswordEnabled(boolean enabled) {
        _fragmentoCabezaBotonCambiarPasswordEnabled = enabled;
    }

    @Override
    public Entity getEntity(Class<?> type) {
        return getMaster().getEntity(type);
    }

    @Override
    public List<Entity> getEntitiesList() {
        return getMaster().getEntitiesList();
    }

    @Override
    public Map<String, Entity> getEntitiesMap() {
        return getMaster().getEntitiesMap();
    }

    /**
     * @return the pages list
     */
    @Override
    public List<Page> getPagesList() {
        List<Page> pagesList = super.getPagesList();
        if (_pagePredicate != null) {
            CollectionUtils.filter(pagesList, _pagePredicate);
        }
        return pagesList;
    }

    /**
     * the page predicate.
     */
    private Predicate _pagePredicate;

    /**
     * @return the page predicate
     */
    public Predicate getPagePredicate() {
        return _pagePredicate;
    }

    /**
     * @param predicate the page predicate to set
     */
    public void setPagePredicate(Predicate predicate) {
        _pagePredicate = predicate;
    }

    public abstract TipoModuloBase getTipo();

    public List<? extends ModuloBase> getSiblings() {
        List<ModuloBase> siblings = new ArrayList<>();
        TipoModuloBase tipo = getTipo();
        if (tipo == null) {
            return siblings;
        }
//      Project project = TLC.getProject();
        Project project = getMaster();
        if (project == null) {
            return siblings;
        }
        List<Project> modules = project.getModulesList();
        for (Project module : modules) {
            if (module instanceof ModuloBase modulo) {
                if (module == this) {
                    continue;
                }
                if (tipo.equals(modulo.getTipo())) {
                    siblings.add(modulo);
                }
            }
        }
        return siblings;
    }

}
