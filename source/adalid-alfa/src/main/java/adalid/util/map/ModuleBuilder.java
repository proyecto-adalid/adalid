/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.util.map;

import adalid.core.*;
import adalid.core.enums.*;
import adalid.core.interfaces.*;
import adalid.core.jee.JavaModule;
import java.util.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class ModuleBuilder extends adalid.util.Utility {

    private static final Logger logger = Logger.getLogger(ModuleBuilder.class);

    public final ModuleAssembler assembler = new ModuleAssembler();

    private final Class<? extends Project> masterClass;

    public ModuleBuilder(Class<? extends Project> masterClass) {
        this.masterClass = masterClass;
    }

    private final Set<String> excludedModules = new TreeSet<>();

    private boolean isExcludedModule(String moduleName) {
        for (String excludedModule : excludedModules) {
            if (moduleName.endsWith(excludedModule)) {
                return true;
            }
        }
        return false;
    }

    public boolean excludeModule(String moduleName) {
        return StringUtils.isNotBlank(moduleName) && excludedModules.add(moduleName);
    }

    private boolean excludeConfigurationEntities;

    public boolean isExcludeConfigurationEntities() {
        return excludeConfigurationEntities;
    }

    public void setExcludeConfigurationEntities(boolean excludeConfigurationEntities) {
        this.excludeConfigurationEntities = excludeConfigurationEntities;
    }

    public void build() throws Exception {
        Project project = newInstance(masterClass);
        if (project != null) {
            project.build();
            // Add entities
            Set<String> entities = getEntitySet(project);
            assembler.addTables(entities);
            // Add modules
            Map<String, Project> moduleMap = project.getModulesMap();
            for (String moduleName : moduleMap.keySet()) {
                if (isExcludedModule(moduleName)) {
                    continue;
                }
                Project module = moduleMap.get(moduleName);
                assert module != null;
                if (module instanceof JavaModule) {
                    continue;
                }
                Set<String> set = getEntitySet(module, excludeConfigurationEntities);
                if (set.isEmpty()) {
                    continue;
                }
                if (module.isImmutableModule()) {
                    assembler.addImmutableModules(moduleName);
                }
                assembler.addModuleTables(moduleName, set);
            }
            // Add references
            int e1 = 0, e2 = 0, er = 0;
            Entity entity;
            Set<String> references;
            Map<String, Entity> entityMap = project.getEntitiesMap();
            for (String entityName : entities) {
                e1++;
                entity = entityMap.get(entityName);
                references = getEntityReferences(entity);
                if (references.isEmpty()) {
                } else {
                    e2++;
                    for (String reference : references) {
                        er++;
                        assembler.addTableRelations(entityName, reference);
                    }
                }
            }
            logger.info(e1 + " entities");
            logger.info(e2 + " entities with references");
            logger.info(er + " references");
            // Build modules
            beforeBuildModules();
            assembler.buildModules();
        }
    }

    public Set<String> getEntitySet(Project project) {
        return getEntitySet(project, false);
    }

    public Set<String> getEntitySet(Project project, boolean excludeConfigurationEntities) {
        Entity entity;
        final Set<String> entities = new TreeSet<>();
        Map<String, Entity> map = project.getEntitiesMap();
        for (String entityName : map.keySet()) {
            entity = map.get(entityName);
            if (excludeConfigurationEntities && project.isMutableModule() && entity.getResourceType().equals(ResourceType.CONFIGURATION)) {
                continue;
            }
            entities.add(entityName);
        }
        return entities;
    }

    public Set<String> getEntityReferences(Entity entity) {
        String entityName = entity.getRoot().getName();
        String referenceName;
        final Set<String> references = new TreeSet<>();
        final List<Property> properties = entity.getRoot().getPropertiesList();
        for (Property property : properties) {
            if (property instanceof EntityReference reference) {
                referenceName = reference.getRoot().getName();
                if (referenceName.equals(entityName)) {
                    continue;
                }
                references.add(referenceName);
            }
        }
        return references;
    }

    public void beforeBuildModules() {
    }

}
