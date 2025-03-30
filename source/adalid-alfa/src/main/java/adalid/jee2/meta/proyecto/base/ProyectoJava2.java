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
package adalid.jee2.meta.proyecto.base;

import adalid.commons.bundles.Bundle;
import adalid.commons.util.*;
import adalid.core.*;
import adalid.core.enums.Navigability;
import adalid.core.interfaces.*;
import adalid.core.jee.JavaWebModule;
import adalid.jee2.SpecialPage;
import java.awt.Component;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import meta.psm.EntityAttributeKeys;
import meta.psm.PageAttributeKeys;
import meta.psm.ProjectAttributeKeys;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public abstract class ProyectoJava2 extends ProyectoJava1 {

    private static final Logger logger = Logger.getLogger(Project.class);

    public ProyectoJava2() {
        super();
    }

    // <editor-fold defaultstate="collapsed" desc="static final arrays">
    private static final String[] NECESSARY_BPL_ENTITY_NAMES = {
        "Usuario"
    };

    private static final String[] NECESSARY_DAO_ENTITY_NAMES = {
        "AccionArchivoCargado",
        "ArchivoAdjunto",
        "ClaseRecurso",
        "ColumnasOcultas",
        "ConjuntoSegmento",
        "DialogoDinamicoRemoto",
        "Dominio",
        "DominioParametro",
        "ElementoSegmento",
        "FiltroFuncion",
        "Funcion",
        "FuncionParametro",
        "GrupoUsuario",
        "ModuloAplicacion",
        "Pagina",
        "PaginaEspecial",
        "Parametro",
        "RastroFuncion",
        "RastroInforme",
        "RastroProceso",
        "Rol",
        "RolAplicacion",
        "RolFiltroFuncion",
        "RolFuncion",
        "RolFuncionPar",
        "RolPagina",
        "RolPaginaEspecial",
        "RolUsuario",
        "RolVistaFuncion",
        "TareaUsuario",
        "TareaUsuarioCorreo",
        "Usuario",
        "UsuarioModulo",
        "UsuarioSegmento",
        "VistaFuncion"
    };

    // Found 54 matches of import (\w+\.)+\w+Facade\w+; in 17 files.
    // Among them are the following 20 non-enumeration entities.
    private static final String[] NECESSARY_DAF_ENTITY_NAMES = {
        "ArchivoAdjunto",
        "ClaseRecurso",
        "ColumnasOcultas",
        "ConjuntoSegmento",
        "DialogoDinamicoRemoto",
        "Dominio",
        "FiltroFuncion",
        "Funcion",
        "FuncionParametro",
        "GrupoUsuario",
        "Pagina",
        "PaginaEspecial",
        "Parametro",
        "RastroFuncion",
        "RastroInforme",
        "RastroProceso",
        "Rol",
        "RolAplicacion",
        "TareaUsuario",
        "TareaUsuarioCorreo",
        "Usuario",
        "VistaFuncion"
    };

    private static final String[] X_PAGE_ARCHETYPE_NAMES = {
        "ArchivoAdjunto",
        "ElementoSegmento",
        "FuncionParametro",
        "RastroFuncion",
        "RastroFuncionPar",
        "RastroInforme",
        "RastroProceso",
        "TareaUsuario",
        "VistaFuncionCol"
    };

    private static final String[] X_PAGE_NAMES = {
        "Usuario34",
        "VistaFuncionCol21PorVista"
    };

    private static final String[] STRICTLY_PERSONALIZED_READING_PAGE_NAMES = {
        "FiltroFuncion21PorFuncion",
        "VistaFuncion21PorFuncion"
    };

    private static final String[] SPECIAL_PAGE_NAMES = {
        "Favoritos",
        "Menu",
        "Personalizacion",
        "HypertextFileViewer",
        "MultimediaPlayer",
        "StreamedContentViewer",
        "TextFileViewer"
    };

    private static final String[] SPECIAL_PAGE_ENTITY_NAMES = {
        "PaginaUsuario"
    };

    private static final String[] SPECIAL_HELP_PAGE_NAMES = {
        "Favoritos",
        "Menu",
        "Personalizacion"
    };

    private static final String[] SPECIAL_HELP_COMPONENT_NAMES = {
        "barraBotonesAccion1",
        "barraBotonesAccion2",
        "dialogoFiltrarConsulta",
        "dialogoGuardarConsulta"
    };
    // </editor-fold>

    public List<String> getSpecialHelpPageNamesList() {
        return Arrays.asList(SPECIAL_HELP_PAGE_NAMES);
    }

    public List<String> getSpecialHelpComponentNamesList() {
        return Arrays.asList(SPECIAL_HELP_COMPONENT_NAMES);
    }

    @Override
    public void addDirectives() {
        super.addDirectives();
//      addFileExclusionPattern("^.*/sun-ejb-jar\\.xml$"); // excluir los archivos sun-ejb-jar.xml
        /* commented since 20200216
        Map<String, ? extends Display> displays = getDisplaysMap();
        for (String name : X_PAGE_NAMES) {
            if (!displays.containsKey(name)) {
                addFileExclusionPattern("^.*" + "/pages/impl/" + name + "\\.java$");
            }
        }
        /**/
    }

    @Override
    public void addAttributes() {
        super.addAttributes();
        /**/
        addAttribute(ProjectAttributeKeys.CUSTOM_LAYOUT, false);
        addAttribute(ProjectAttributeKeys.HLB_RENDERING, false);
        addAttribute(ProjectAttributeKeys.HCB_RENDERING, false);
        addAttribute(ProjectAttributeKeys.HRB_RENDERING, false);
        addAttribute(ProjectAttributeKeys.INLINE_HELP_RENDERING, true);
        addAttribute(ProjectAttributeKeys.PARTIAL_STATE_SAVING, true);
//      addAttribute(ProjectAttributeKeys.SESSION_TIMEOUT, 30);
        addAttribute("primefaces_messages_escape", false);
        addAttribute("primefaces_inline_help_escape", false);
        addAttribute("primefaces_output_label_escape", false);
        addAttribute("primefaces_tooltip_escape", false);
        addAttribute("check_mailer_bean_session_before_each_delivery", false);
        /**/
        Entity entity;
        Map<String, Entity> entitiesMap = getEntitiesMap();
        for (String name : X_PAGE_ARCHETYPE_NAMES) {
            if (entitiesMap.containsKey(name)) {
                entity = entitiesMap.get(name);
//              entity.addAttribute(EntityAttributeKeys.PAGE_ARCHETYPE_CLASS_NAME_SUFFIX, "X");
                entity.addAttribute(EntityAttributeKeys.PAGE_ARCHETYPE_PACKAGE_NAME_SUFFIX, "ext");
            }
        }
        /**/
        for (String name : X_PAGE_NAMES) {
            addAttribute(Page.class, name,
                KVP.join(PageAttributeKeys.ABSTRACT, true)
            );
        }
        /**/
        for (String name : STRICTLY_PERSONALIZED_READING_PAGE_NAMES) {
            addAttribute(Page.class, name,
                KVP.join(PageAttributeKeys.STRICTLY_PERSONALIZED_READING, true)
            );
        }
        /**/
        final String directory = "special-page/"; // /language/ and /country/ are replaced by Locale getLanguage() and getCountry()
        final String extension = "." + getWebPageFileExtension();
        for (String name : SPECIAL_PAGE_NAMES) {
            addAttribute(Page.class, name,
                KVP.join(PageAttributeKeys.HELP_FILE_NAME, directory + name + extension)
            );
        }
        /**/
        for (String name : SPECIAL_PAGE_ENTITY_NAMES) {
            if (entitiesMap.containsKey(name)) {
                entity = entitiesMap.get(name);
                entity.setDisplayAvailable(true); // true to generate the corresponding archetype
            }
        }
        /**/
        addHelpPageTextAttributes();
    }

    public void addHolidaysListBeanAttribute() {
        final String beanName = "holidaysList";
        addHolidaysListBeanAttribute(beanName);
    }

    public void addHolidaysListBeanAttribute(String beanName) {
        final String classSimpleName = "HolidaysList";
        addBeanAttribute(classSimpleName, beanName);
    }

    /**
     * @param name attribute name
     * @return the help document
     */
    public String getHelpDocumentAttribute(String name) {
        Object attribute = getKeyValuePairAttribute(name, PageAttributeKeys.HELP_EMBEDDED_DOCUMENT);
        return attribute == null ? getHelpDocument() : attribute.toString();
    }

    /**
     * @param name attribute name
     * @return the help file name
     */
    public String getHelpFileNameAttribute(String name) {
        Object attribute = getKeyValuePairAttribute(name, PageAttributeKeys.HELP_FILE_NAME);
        return attribute == null ? getHelpFileName() : attribute.toString();
    }

    /**
     * @param name attribute name
     * @return the help text
     */
    public String getHelpPageTextAttribute(String name) {
        Object attribute = getKeyValuePairAttribute(name, PageAttributeKeys.HELP_PAGE_TEXT);
        return attribute == null ? null : attribute.toString();
    }

    /**
     * add text to special help pages
     */
    private void addHelpPageTextAttributes() {
        String pid, str;
        pid = "Favoritos";
        str = (new adalid.jee2.help.en.Favoritos(this)).getHelpPageText();
        addAttribute(Page.class, pid + ":en",
            KVP.join(PageAttributeKeys.HELP_PAGE_TEXT, str)
        );
        str = (new adalid.jee2.help.es.Favoritos(this)).getHelpPageText();
        addAttribute(Page.class, pid + ":es",
            KVP.join(PageAttributeKeys.HELP_PAGE_TEXT, str)
        );
        pid = "Menu";
        str = (new adalid.jee2.help.en.Menu(this)).getHelpPageText();
        addAttribute(Page.class, pid + ":en",
            KVP.join(PageAttributeKeys.HELP_PAGE_TEXT, str)
        );
        str = (new adalid.jee2.help.es.Menu(this)).getHelpPageText();
        addAttribute(Page.class, pid + ":es",
            KVP.join(PageAttributeKeys.HELP_PAGE_TEXT, str)
        );
        pid = "Personalizacion";
        str = (new adalid.jee2.help.en.Personalizacion(this)).getHelpPageText();
        addAttribute(Page.class, pid + ":en",
            KVP.join(PageAttributeKeys.HELP_PAGE_TEXT, str)
        );
        str = (new adalid.jee2.help.es.Personalizacion(this)).getHelpPageText();
        addAttribute(Page.class, pid + ":es",
            KVP.join(PageAttributeKeys.HELP_PAGE_TEXT, str)
        );
        pid = "barraBotonesAccion1";
        str = (new adalid.jee2.help.en.BarraBotonesAccion1(this)).getHelpPageText();
        addAttribute(Component.class, pid + ":en",
            KVP.join(PageAttributeKeys.HELP_PAGE_TEXT, str)
        );
        str = (new adalid.jee2.help.es.BarraBotonesAccion1(this)).getHelpPageText();
        addAttribute(Component.class, pid + ":es",
            KVP.join(PageAttributeKeys.HELP_PAGE_TEXT, str)
        );
        pid = "barraBotonesAccion2";
        str = (new adalid.jee2.help.en.BarraBotonesAccion2(this)).getHelpPageText();
        addAttribute(Component.class, pid + ":en",
            KVP.join(PageAttributeKeys.HELP_PAGE_TEXT, str)
        );
        str = (new adalid.jee2.help.es.BarraBotonesAccion2(this)).getHelpPageText();
        addAttribute(Component.class, pid + ":es",
            KVP.join(PageAttributeKeys.HELP_PAGE_TEXT, str)
        );
        pid = "dialogoFiltrarConsulta";
        str = (new adalid.jee2.help.en.DialogoFiltrarConsulta(this)).getHelpPageText();
        addAttribute(Component.class, pid + ":en",
            KVP.join(PageAttributeKeys.HELP_PAGE_TEXT, str)
        );
        str = (new adalid.jee2.help.es.DialogoFiltrarConsulta(this)).getHelpPageText();
        addAttribute(Component.class, pid + ":es",
            KVP.join(PageAttributeKeys.HELP_PAGE_TEXT, str)
        );
        pid = "dialogoGuardarConsulta";
        str = (new adalid.jee2.help.en.DialogoGuardarConsulta(this)).getHelpPageText();
        addAttribute(Component.class, pid + ":en",
            KVP.join(PageAttributeKeys.HELP_PAGE_TEXT, str)
        );
        str = (new adalid.jee2.help.es.DialogoGuardarConsulta(this)).getHelpPageText();
        addAttribute(Component.class, pid + ":es",
            KVP.join(PageAttributeKeys.HELP_PAGE_TEXT, str)
        );
    }

    @Override
    public void configureGenerator() {
        super.configureGenerator();
        addSpecialPageCalendarioProcesos();
        addSpecialPageDistribucionTareas();
        addSpecialPageIndicadoresGestion();
        addSpecialPageMapaSitio();
    }

    protected final SpecialPage paginaCalendarioProcesos = SpecialPage.of("CalendarioProcesos", "/faces/views/base/code/CalendarioProcesos.xhtml");

    private void addSpecialPageCalendarioProcesos() {
        paginaCalendarioProcesos.setIconClass("fa fa-calendar-check-o");
        paginaCalendarioProcesos.setLocalizedLabel(Bundle.ENGLISH, "Scheduled processes");
        paginaCalendarioProcesos.setLocalizedLabel(Bundle.SPANISH, "Procesos calendarizados");
        paginaCalendarioProcesos.setLocalizedShortDescription(Bundle.ENGLISH, "Execution of scheduled processes");
        paginaCalendarioProcesos.setLocalizedShortDescription(Bundle.SPANISH, "Ejecución de procesos calendarizados");
        paginaCalendarioProcesos.setStartOption(false);
        addSpecialPage(paginaCalendarioProcesos);
    }

    protected final SpecialPage paginaDistribucionTareas = SpecialPage.of("DistribucionTareas", "/faces/views/base/code/DistribucionTareas.xhtml");

    private void addSpecialPageDistribucionTareas() {
        paginaDistribucionTareas.setIconClass("fa fa-bar-chart");
        paginaDistribucionTareas.setLocalizedLabel(Bundle.ENGLISH, "Task distribution");
        paginaDistribucionTareas.setLocalizedLabel(Bundle.SPANISH, "Distribución de tareas");
        paginaDistribucionTareas.setLocalizedShortDescription(Bundle.ENGLISH, "Task distribution charts");
        paginaDistribucionTareas.setLocalizedShortDescription(Bundle.SPANISH, "Gráficos de distribución de tareas");
        paginaDistribucionTareas.setStartOption(false);
        addSpecialPage(paginaDistribucionTareas);
    }

    protected final SpecialPage paginaIndicadoresGestion = SpecialPage.of("IndicadoresGestion", "/faces/views/base/code/IndicadoresGestion.xhtml");

    private void addSpecialPageIndicadoresGestion() {
        paginaIndicadoresGestion.setIconClass("fa fa-dashboard");
        paginaIndicadoresGestion.setLocalizedLabel(Bundle.ENGLISH, "Dashboard");
        paginaIndicadoresGestion.setLocalizedLabel(Bundle.SPANISH, "Tablero");
        paginaIndicadoresGestion.setLocalizedShortDescription(Bundle.ENGLISH, "Dashboard of management indicators");
        paginaIndicadoresGestion.setLocalizedShortDescription(Bundle.SPANISH, "Tablero de indicadores de gestión");
        paginaIndicadoresGestion.setStartOption(true);
        addSpecialPage(paginaIndicadoresGestion);
    }

    protected final SpecialPage paginaMapaSitio = SpecialPage.of("Mapa", "/faces/views/base/code/Mapa.xhtml");

    private void addSpecialPageMapaSitio() {
        paginaMapaSitio.setIconClass("fa fa-sitemap fa-rotate-270");
        paginaMapaSitio.setLocalizedLabel(Bundle.ENGLISH, "Site Map");
        paginaMapaSitio.setLocalizedLabel(Bundle.SPANISH, "Mapa del Sitio");
        paginaMapaSitio.setLocalizedShortDescription(Bundle.ENGLISH, "Site Map");
        paginaMapaSitio.setLocalizedShortDescription(Bundle.SPANISH, "Mapa del Sitio");
        paginaMapaSitio.setPublicView(true);
        paginaMapaSitio.setStartOption(true);
        addSpecialPage(paginaMapaSitio);
    }

    @Override
    protected void disablePrivateAndOtherContextEntitiesCodeGen() {
        addEntitiesReferencedByPageFields(true);
        addEntitiesReferencedByLocalEntities();
        super.disablePrivateAndOtherContextEntitiesCodeGen();
    }

    private final Set<String> _entitiesReferencedByLocalPageFields = new TreeSet<>();

    private final Set<String> _entitiesReferencedByLocalPageEntities = new TreeSet<>();

    private final Set<String> _contextsReferencedByLocalPageEntities = new TreeSet<>();

    protected void addEntitiesReferencedByPageFields(boolean hidden) {
        Set<Entity> entities;
        for (Project module : getModulesList()) {
            if (module instanceof JavaWebModule jwm) {
                for (Page page : jwm.getPagesList()) {
                    if (page.isApplicationDefaultLocation()) {
                        entities = page.getEntitiesReferencedByFields(hidden);
                        if (entities != null && !entities.isEmpty()) {
                            for (Entity entity : entities) {
                                /* until 22/08/2024
                                if (entity == null || entity instanceof EnumerationEntity) {
                                    continue;
                                }
                                /**/
                                _entitiesReferencedByLocalPageFields.add(entity.getDataClass().getSimpleName());
                            }
                        }
                        addLocalPageEntity(page.getEntity());
                        addLocalPageEntity(page.getMaster());
                    }
                }
            }
        }
//      System.out.println(_entitiesReferencedByLocalPageFields.toString().replace(",", "\n"));
        int size = _contextsReferencedByLocalPageEntities.size();
        String s = size == 1 ? "another app is" : size > 1 ? size + " other apps are" : null;
        if (s != null) {
            logger.info(s + " being referenced by local entities " + _contextsReferencedByLocalPageEntities);
        }
    }

    private void addLocalPageEntity(Entity entity) {
        if (entity == null) {
            return;
        }
        String entityName = entity.getDataClass().getSimpleName();
        if (_entitiesReferencedByLocalPageEntities.contains(entityName)) {
            return;
        }
        _entitiesReferencedByLocalPageEntities.add(entityName);
        Entity entityRoot = entity.getRoot();
        for (Property property : entityRoot.getPropertiesList()) {
            if (property instanceof Entity reference) {
                addLocalPageEntityReference(reference);
            }
        }
        for (Operation operation : entityRoot.getOperationsList()) {
            for (Parameter parameter : operation.getParametersList()) {
                if (parameter instanceof Entity reference) {
                    addLocalPageEntityReference(reference);
                }
            }
        }
    }

    private void addLocalPageEntityReference(Entity reference) {
        _entitiesReferencedByLocalPageEntities.add(reference.getDataClass().getSimpleName());
        Entity referenceRoot = reference.getRoot();
        if (referenceRoot.isApplicationDefaultLocation()) {
            return;
        }
        String host = StringUtils.trimToEmpty(referenceRoot.getApplicationOrigin());
        String root = StringUtils.trimToEmpty(referenceRoot.getApplicationContextRoot());
        _contextsReferencedByLocalPageEntities.add(host + "/" + root);
    }

    private final Set<String> _entitiesReferencedByLocalEntities = new TreeSet<>();

    protected void addEntitiesReferencedByLocalEntities() {
        for (Entity entity : getEntitiesList()) {
            if (entity.isApplicationDefaultLocation()) {
                addReferencedEntity(entity);
            }
        }
        if (isMultiApplication()) {
            int size = _entitiesReferencedByLocalEntities.size();
            if (size > 0) {
                logger.info(size + " entities being referenced by local entities "/* + _entitiesReferencedByLocalEntities*/);
            }
        }
    }

    private void addReferencedEntity(Entity entity) {
        String entityName = entity.getDataClass().getSimpleName();
        if (_entitiesReferencedByLocalEntities.contains(entityName)) {
            return;
        }
        _entitiesReferencedByLocalEntities.add(entityName);
        Entity entityRoot = entity.getRoot();
        for (Property property : entityRoot.getPropertiesList()) {
            if (property instanceof Entity reference) {
                addReferencedEntity(reference);
            }
        }
        for (EntityCollection collection : entityRoot.getEntityCollectionsList()) {
            addReferencedEntity(collection.getTargetEntity());
        }
        List<Property> referencesList = entityRoot.getReferencesList();
        for (Property reference : referencesList) {
            if (reference instanceof EntityReference entityReference) {
                if (Navigability.BIDIRECTIONAL.equals(entityReference.getNavigability())) {
                    addReferencedEntity(entityReference.getDeclaringEntityRoot());
                }
            }
        }
        for (Operation operation : entityRoot.getOperationsList()) {
            for (Parameter parameter : operation.getParametersList()) {
                if (parameter instanceof Entity reference) {
                    addReferencedEntity(reference);
                }
            }
        }
    }

    @Override
    protected boolean isOptionalBplCodeGen(Entity entity) {
        return !(entity == null || necessary(entity, NECESSARY_BPL_ENTITY_NAMES));
    }

    @Override
    protected boolean isOptionalDaoCodeGen(Entity entity) {
        return !(entity == null || necessaryDao(entity) || necessary(entity, NECESSARY_DAO_ENTITY_NAMES));
    }

    private boolean necessaryDao(Entity entity) {
        // Each entity requires Entity Class, Constants and Entity Base Interface
        // Each enumeration entity also requires Enumeration
        return _entitiesReferencedByLocalEntities.contains(entity.getDataClass().getSimpleName());
    }

    @Override
    protected boolean isOptionalDafCodeGen(Entity entity) {
        /* until 22/08/2024
        return !(entity == null || entity instanceof EnumerationEntity || necessaryDaf(entity) || necessary(entity, NECESSARY_DAF_ENTITY_NAMES));
        /**/
        return (entity instanceof EnumerationEntity) ? isOptionalDaoCodeGen(entity)
            : !(entity == null || necessaryDaf(entity) || necessary(entity, NECESSARY_DAF_ENTITY_NAMES));
    }

    private boolean necessaryDaf(Entity entity) {
        // Each entity reference page field requires conversion and each Converter requires the Facade
        return _entitiesReferencedByLocalPageFields.contains(entity.getDataClass().getSimpleName());
    }

    private boolean necessary(Entity entity, String[] simpleNames) {
        return ArrayUtils.contains(simpleNames, entity.getDataClass().getSimpleName());
    }

}
