/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.jee;

import adalid.commons.bundles.Bundle;
import adalid.commons.util.ColUtils;
import adalid.core.DisplaySet;
import adalid.core.Page;
import adalid.core.comparators.ByEntityName;
import adalid.core.comparators.ByPageName;
import adalid.core.enums.DisplayFormat;
import adalid.core.enums.DisplayMode;
import adalid.core.enums.MasterDetailView;
import adalid.core.interfaces.Entity;
import adalid.core.interfaces.EntityReference;
import adalid.core.interfaces.Property;
import adalid.core.predicates.IsConsoleViewDisplay;
import adalid.core.predicates.IsPersistentEntityWithTable;
import adalid.core.predicates.IsProcessingDisplay;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public abstract class AbstractJavaWebModule extends AbstractJavaModule implements JavaWebModule {

    private final Logger logger = Logger.getLogger(JavaWebModule.class);

    private static final String BY = Bundle.getString("java.web.page_name_connective");

    public AbstractJavaWebModule() {
        super();
        setModuleType(JavaModuleType.WEB);
    }

    @Override
    protected boolean assemble() {
        boolean compiled = super.assemble();
        if (compiled) {
            buildPagesMap();
            buildPageSetsMap();
            buildDisplayableEntitiesMap();
        }
        return compiled;
    }

    private final Map<String, Page> _pages = new LinkedHashMap<>();

    private final Map<String, DisplaySet> _pageSets = new LinkedHashMap<>();

    private final Map<String, Entity> _displayableEntities = new LinkedHashMap<>();

    /**
     * @return the displays list
     */
    @Override
    public List<Page> getDisplaysList() {
        return getPagesList();
    }

    /**
     * @return the displays map
     */
    @Override
    public Map<String, Page> getDisplaysMap() {
        return getPagesMap();
    }

    /**
     * @return the pages list
     */
    @Override
    public List<Page> getPagesList() {
        return new ArrayList<>(getPagesMap().values());
    }

    /**
     * @return the pages map
     */
    public Map<String, Page> getPagesMap() {
        return _pages;
    }

    /**
     * @return the display sets list
     */
    @Override
    public List<DisplaySet> getPageSetsList() {
        return new ArrayList<>(getPageSetsMap().values());
    }

    /**
     * @return the display sets map
     */
    public Map<String, DisplaySet> getPageSetsMap() {
        return _pageSets;
    }

    /**
     * @return the entities list
     */
    @Override
    public List<Entity> getDisplayableEntitiesList() {
        return new ArrayList<>(getDisplayableEntitiesMap().values());
    }

    /**
     * @return the entities map
     */
    public Map<String, Entity> getDisplayableEntitiesMap() {
        return _displayableEntities;
    }

    @SuppressWarnings("unchecked")
    private void buildPagesMap() {
        _pages.clear();
        Predicate isPersistentEntityWithTable = new IsPersistentEntityWithTable();
        Comparator byEntityName = new ByEntityName();
        List<Entity> entities = getEntitiesList();
        Collection<Entity> validEntities = ColUtils.filter(entities, isPersistentEntityWithTable);
        ColUtils.sort(validEntities, byEntityName);
        for (Entity entity : validEntities) {
            put(entity);
        }
    }

    private void buildPageSetsMap() {
        String pageName;
        Entity entity;
        EntityReference reference;
        Entity master;
        DisplayMode mode;
        DisplayFormat format;
        String setName;
        _pageSets.clear();
        List<Page> pagesList = getPagesList();
        for (Page page : pagesList) {
            mode = page.getDisplayMode();
            if (DisplayMode.READING.equals(mode) || DisplayMode.WRITING.equals(mode)) {
                format = page.getDisplayFormat();
                if (DisplayFormat.TABLE.equals(format) || DisplayFormat.DETAIL.equals(format)) {
                    pageName = page.getName();
                    entity = page.getEntity();
                    reference = page.getReference();
                    master = page.getMaster();
                    setName = pageSetName(entity, reference);
                    if (_pageSets.containsKey(setName)) {
                        DisplaySet set = _pageSets.get(setName);
                        set.getDisplaysMap().put(pageName, page);
                        page.setDisplaySet(set);
                    } else {
                        DisplaySet set = new DisplaySet(setName);
                        set.setEntity(entity);
                        set.setReference(reference);
                        set.setMaster(master);
                        set.getDisplaysMap().put(pageName, page);
                        _pageSets.put(setName, set);
                        page.setDisplaySet(set);
                    }
                }
            }
        }
    }

    private void buildDisplayableEntitiesMap() {
        List<Page> list = getPagesList();
        Predicate isConsoleViewDisplay = new IsConsoleViewDisplay();
        Predicate isProcessingDisplay = new IsProcessingDisplay();
        Comparator<Page> byPageName = new ByPageName();
        Collection<Page> pages = ColUtils.noneFilter(list, isConsoleViewDisplay, isProcessingDisplay);
        Collection<Page> sortedPages = ColUtils.sort(pages, byPageName);
        Entity entity, master;
        _displayableEntities.clear();
        for (Page page : sortedPages) {
            entity = page.getEntity();
            if (entity != null) {
                _displayableEntities.put(entity.getName(), entity);
            }
            master = page.getMaster();
            if (master != null) {
                _displayableEntities.put(master.getName(), master);
            }
        }
    }

    private void put(Entity entity) {
        putReadingPages(entity);
        putWritingPages(entity);
        putConsolePages(entity);
        EntityReference reference;
        Class<? extends Entity> declaring;
        Entity detail;
        List<Property> properties = entity.getReferencesList();
        for (Property property : properties) {
            reference = (EntityReference) property;
            declaring = reference.getDeclaringEntity().getClass();
            detail = declaring == null ? null : getEntity(declaring);
            if (detail != null && reference.isManyToOne()) {
                putMasterDetailPages(detail, reference, entity);
            }
        }
    }

    private boolean putReadingPages(Entity entity) {
        boolean put = false;
        if (entity.isSelectEnabled()) {
            if (entity.isTableViewEnabled()) {
                put |= putPage(entity, DisplayMode.READING, DisplayFormat.TABLE);
            }
            if (entity.isDetailViewEnabled()) {
                put |= putPage(entity, DisplayMode.READING, DisplayFormat.DETAIL);
            }
            if (entity.isTreeViewEnabled() && entity.getParentProperty() != null) {
                put |= putPage(entity, DisplayMode.READING, DisplayFormat.TREE);
            }
        }
        return put;
    }

    private boolean putWritingPages(Entity entity) {
        boolean put = false;
//      if (entity.isSelectEnabled()) {
        if (entity.isInsertEnabled() || entity.isUpdateEnabled() || entity.isDeleteEnabled() || entity.isWritingPageMaster()) {
            if (entity.isTableViewEnabled()) {
                put |= putPage(entity, DisplayMode.WRITING, DisplayFormat.TABLE);
            }
            if (entity.isDetailViewEnabled()) {
                put |= putPage(entity, DisplayMode.WRITING, DisplayFormat.DETAIL);
            }
            if (entity.isTreeViewEnabled() && entity.getParentProperty() != null) {
                put |= putPage(entity, DisplayMode.WRITING, DisplayFormat.TREE);
            }
        }
//      }
        return put;
    }

    private boolean putConsolePages(Entity entity) {
        boolean put = false;
        if (entity.isConsoleViewEnabled() && !entity.getBusinessOperationsList().isEmpty()) {
            put |= putPage(entity, null, null, DisplayMode.PROCESSING, DisplayFormat.CONSOLE);
        }
        return put;
    }

    private boolean putMasterDetailPages(Entity entity, EntityReference reference, Entity master) {
        boolean put = false;
//      if (entity.isSelectEnabled()) {
        MasterDetailView masterDetailView = reference.getMasterDetailView();
        if (entity.isSelectEnabled()) {
            switch (masterDetailView) {
                case TABLE:
                    put |= putPage(entity, reference, master, DisplayMode.READING, DisplayFormat.TABLE);
                    break;
//              case DETAIL:
//                  put |= putPage(entity, reference, master, DisplayMode.READING, DisplayFormat.DETAIL);
//                  break;
                case TABLE_AND_DETAIL:
                    put |= putPage(entity, reference, master, DisplayMode.READING, DisplayFormat.TABLE);
                    put |= putPage(entity, reference, master, DisplayMode.READING, DisplayFormat.DETAIL);
                    break;
            }
        }
        if (entity.isInsertEnabled() || entity.isUpdateEnabled() || entity.isDeleteEnabled()) {
            switch (masterDetailView) {
                case TABLE:
                    put |= putPage(entity, reference, master, DisplayMode.WRITING, DisplayFormat.TABLE);
                    break;
//                  case DETAIL:
//                      put |= putPage(entity, reference, master, DisplayMode.WRITING, DisplayFormat.DETAIL);
//                      break;
                case TABLE_AND_DETAIL:
                    put |= putPage(entity, reference, master, DisplayMode.WRITING, DisplayFormat.TABLE);
                    put |= putPage(entity, reference, master, DisplayMode.WRITING, DisplayFormat.DETAIL);
                    break;
            }
        }
//      }
        return put;
    }

    private boolean putPage(Entity entity, DisplayMode mode, DisplayFormat format) {
        return putPage(entity, null, null, mode, format);
    }

    private boolean putPage(Entity entity, EntityReference reference, Entity master, DisplayMode mode, DisplayFormat format) {
        String pageName = pageName(entity, reference, mode, format);
        Page page;
        switch (format) {
            case TABLE:
            case DETAIL:
            case TREE:
                page = new CrudJavaServerPage(pageName);
                break;
            case CONSOLE:
                page = new ConsoleJavaServerPage(pageName);
                break;
            default:
                page = new Page(pageName);
                break;
        }
        page.setModule(this);
        page.setEntity(entity);
        page.setReference(reference);
        page.setMaster(master);
        page.setDisplayMode(mode);
        page.setDisplayFormat(format);
        _pages.put(pageName, page);
        return true;
    }

    private String pageName(Entity entity, EntityReference reference, DisplayMode mode, DisplayFormat format) {
        String name = entity.getName() + mode.ordinal() + format.ordinal();
        if (reference != null) {
            name += BY + StringUtils.capitalize(reference.getName());
        }
        logger.debug("page=" + name);
        return name;
    }

    private String pageSetName(Entity entity, EntityReference reference) {
        String name = entity.getName();
        if (reference != null) {
            name += BY + StringUtils.capitalize(reference.getName());
        }
        logger.debug("pageset=" + name);
        return name;
    }

}
