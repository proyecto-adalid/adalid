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
package adalid.core.jee;

import adalid.commons.util.*;
import adalid.core.*;
import adalid.core.comparators.*;
import adalid.core.enums.*;
import adalid.core.interfaces.*;
import adalid.core.predicates.*;
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

    private static final Logger logger = Logger.getLogger(JavaWebModule.class);

//  private static final String BY = Bundle.getString("java.web.page_name_connective");
    private static final String BY = "Por"; // part of class names, cannot be localized, must be final!

    public AbstractJavaWebModule() {
        super();
        init();
    }

    private void init() {
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
        Comparator<Entity> byEntityName = new ByEntityName();
        List<Entity> entities = getEntitiesList();
        Collection<Entity> validEntities = ColUtils.filter(entities, isPersistentEntityWithTable);
        validEntities = ColUtils.sort(validEntities, byEntityName);
        for (Entity entity : validEntities) {
            if (entity.isGuiCodeGenEnabled()) { // entity.isApplicationDefaultLocation() doesn't work here
                put(entity);
            }
        }
    }

    private void buildPageSetsMap() {
        String pageName;
        Entity entity;
        EntityReference reference;
        Entity master;
        DisplayMode mode;
        DisplayFormat format;
        String pageSetName;
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
                    pageSetName = pageSetName(entity, reference);
                    if (_pageSets.containsKey(pageSetName)) {
                        DisplaySet set = _pageSets.get(pageSetName);
                        set.getDisplaysMap().put(pageName, page);
                        page.setDisplaySet(set);
                    } else {
                        DisplaySet set = new DisplaySet(pageSetName);
                        set.setEntity(entity);
                        set.setReference(reference);
                        set.setMaster(master);
                        set.getDisplaysMap().put(pageName, page);
                        _pageSets.put(pageSetName, set);
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
            if (reference.isManyToOne() || reference.isOneToOne()) {
                declaring = reference.getDeclaringEntity().getClass();
                detail = declaring == null ? null : getEntity(declaring);
                if (detail != null) {
                    putMasterDetailPages(detail, reference, entity);
                }
            }
        }
    }

    private boolean putReadingPages(Entity entity) {
        boolean put = false;
        if (entity.isSelectEnabled()) {
            EntityViewType entityViewType = entity.getEntityViewType();
            if (EntityViewType.INDEPENDENT.equals(entityViewType) || EntityViewType.BOTH.equals(entityViewType)) {
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
        }
        return put;
    }

    private boolean putWritingPages(Entity entity) {
        boolean put = false;
        if (entity.isInsertEnabled() || entity.isUpdateEnabled() || entity.isDeleteEnabled() || entity.isWritingPageMaster()) {
            EntityViewType entityViewType = entity.getEntityViewType();
            if (EntityViewType.INDEPENDENT.equals(entityViewType) || EntityViewType.BOTH.equals(entityViewType)) {
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
        }
        return put;
    }

    private boolean putConsolePages(Entity entity) {
        boolean put = false;
        if (entity.isConsoleViewEnabled() && !entity.getBusinessOperationsList().isEmpty()) {
            put |= putPage(entity, DisplayMode.PROCESSING, DisplayFormat.CONSOLE);
        }
        return put;
    }

    private boolean putMasterDetailPages(Entity detail, EntityReference reference, Entity master) {
        boolean put = false;
        if (detail.isGuiCodeGenEnabled()) {
            EntityViewType entityViewType = detail.getEntityViewType();
            if (EntityViewType.MASTER_DETAIL.equals(entityViewType) || EntityViewType.BOTH.equals(entityViewType)) {
                if (reference.isOneToOne() && reference.isOneToOneDetailView()) {
                    if (detail.isSelectEnabled()) {
                        put |= putPage(detail, reference, master, DisplayMode.READING, DisplayFormat.DETAIL);
                    }
                    if (detail.isInsertEnabled() || detail.isUpdateEnabled() || detail.isDeleteEnabled()) {
                        put |= putPage(detail, reference, master, DisplayMode.WRITING, DisplayFormat.DETAIL);
                    }
                }
                if (reference.isManyToOne()) {
                    MasterDetailView masterDetailView = reference.getMasterDetailView();
                    if (detail.isSelectEnabled()) {
                        switch (masterDetailView) {
                            case TABLE:
                                put |= putPage(detail, reference, master, DisplayMode.READING, DisplayFormat.TABLE);
                                break;
                            case TABLE_AND_DETAIL:
                                put |= putPage(detail, reference, master, DisplayMode.READING, DisplayFormat.TABLE);
                                put |= putPage(detail, reference, master, DisplayMode.READING, DisplayFormat.DETAIL);
                                break;
                        }
                    }
                    if (detail.isInsertEnabled() || detail.isUpdateEnabled() || detail.isDeleteEnabled()) {
                        switch (masterDetailView) {
                            case TABLE:
                                put |= putPage(detail, reference, master, DisplayMode.WRITING, DisplayFormat.TABLE);
                                break;
                            case TABLE_AND_DETAIL:
                                put |= putPage(detail, reference, master, DisplayMode.WRITING, DisplayFormat.TABLE);
                                put |= putPage(detail, reference, master, DisplayMode.WRITING, DisplayFormat.DETAIL);
                                break;
                        }
                    }
                }
            }
        }
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
