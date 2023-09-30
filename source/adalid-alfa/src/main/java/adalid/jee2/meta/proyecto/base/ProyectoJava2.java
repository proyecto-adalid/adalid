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

import adalid.commons.util.*;
import adalid.core.*;
import adalid.core.interfaces.*;
import adalid.core.jee.JavaWebModule;
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

/**
 * @author Jorge Campins
 */
public class ProyectoJava2 extends ProyectoJava1 {

    public ProyectoJava2() {
        super();
    }

    // <editor-fold defaultstate="collapsed" desc="static final arrays">
    private static final String[] NECESSARY_BPL_ENTITY_NAMES = {
        "Usuario"
    };

    // Found 54 matches of import (\w+\.)+\w+Facade\w+; in 17 files.
    // Among them are the following 20 non-enumeration entities.
    private static final String[] NECESSARY_DAF_ENTITY_NAMES = {
        "ArchivoAdjunto",
        "ClaseRecurso",
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
    protected void disablePrivateAndOtherContextEntitiesCodeGen() {
        addEntitiesReferencedByPageFields(true);
        super.disablePrivateAndOtherContextEntitiesCodeGen();
    }

    private final Set<String> _entitiesReferencedByLocalPageFields = new TreeSet<>();

    protected void addEntitiesReferencedByPageFields() {
        addEntitiesReferencedByPageFields(false);
    }

    protected void addEntitiesReferencedByPageFields(boolean hidden) {
        Set<Entity> entities;
        for (Project module : getModulesList()) {
            if (module instanceof JavaWebModule) {
                JavaWebModule jwm = (JavaWebModule) module;
                for (Page page : jwm.getPagesList()) {
                    if (page.isApplicationDefaultLocation()) {
                        entities = page.getEntitiesReferencedByFields(hidden);
                        if (entities != null && !entities.isEmpty()) {
                            for (Entity entity : entities) {
                                if (entity == null || entity instanceof EnumerationEntity) {
                                    continue;
                                }
                                _entitiesReferencedByLocalPageFields.add(entity.getDataClass().getSimpleName());
                            }
                        }
                    }
                }

            }
        }
//      System.out.println(_entitiesReferencedByLocalPageFields.toString().replace(",", "\n"));
    }

    @Override
    protected boolean isOptionalBplCodeGen(Entity entity) {
        return !(entity == null || necessary(entity, NECESSARY_BPL_ENTITY_NAMES));
    }

    @Override
    protected boolean isOptionalDafCodeGen(Entity entity) {
        return !(entity == null || entity instanceof EnumerationEntity || necessary(entity) || necessary(entity, NECESSARY_DAF_ENTITY_NAMES));
    }

    private boolean necessary(Entity entity) {
        // Each entity reference page field requires conversion and each Converter requires the Facade
        return _entitiesReferencedByLocalPageFields.contains(entity.getDataClass().getSimpleName());
    }

    private boolean necessary(Entity entity, String[] simpleNames) {
        return ArrayUtils.contains(simpleNames, entity.getDataClass().getSimpleName());
    }

}
