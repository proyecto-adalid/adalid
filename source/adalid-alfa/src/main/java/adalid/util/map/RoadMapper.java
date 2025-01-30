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
package adalid.util.map;

import adalid.commons.util.ColUtils;
import adalid.core.Project;
import adalid.core.interfaces.Entity;
import adalid.core.interfaces.Property;
import adalid.core.jee.JavaWebProject;
import adalid.core.predicates.IsProjectModule;
import adalid.util.Utility;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public class RoadMapper extends Utility {

    private static final Logger logger = Logger.getLogger(RoadMapper.class);

    public static void map(Class<?>... masterClasses) throws Exception {
        List<Project> masters = new ArrayList<>();
        for (Class<?> masterClass : masterClasses) {
            Object instance = masterClass.getDeclaredConstructor().newInstance();
            if (instance instanceof Project project) {
                if (project.getMaster() == null) {
                    JavaWebProject jwp = jwp(project);
                    if (jwp == null) {
                        throw new IllegalArgumentException(instance + " is not an instance of JavaWebProject");
                    }
                    masters.add(project);
                } else {
                    throw new IllegalArgumentException(instance + " is not a master project");
                }
            } else {
                throw new IllegalArgumentException(instance + " is not an instance of Project");
            }
        }
        RoadMapper mapper = new RoadMapper();
        mapper.mapMasters(masters);
        updateProjectBuilderDictionary(RoadMapper.class);
    }

    private static JavaWebProject jwp(Project project) {
        return project instanceof JavaWebProject ? (JavaWebProject) project : null;
    }

    private final Set<String> applications = new TreeSet<>();

    private final Map<String, Set<String>> appSources = new TreeMap<>();

    private final Map<String, Set<String>> appTargets = new TreeMap<>();

    private final Map<String, Set<String>> entityApps = new TreeMap<>();

    private final Map<String, Set<String>> entityRefs = new TreeMap<>();

    private final Map<String, Set<EntityVertex>> vertices = new TreeMap<>();

    private final Map<EntityVertex, Set<ReferenceArc>> vertexArcs = new TreeMap<>();

    private void mapMasters(List<Project> masters) {
        for (Project master : masters) {
            mapMaster(master);
        }
        /**/
        // <editor-fold defaultstate="collapsed">
        /*
        for (String application : vertices.keySet()) {
            Set<EntityVertex> set = vertices.get(application);
            logger.info("map " + application + ", vertices=" + set.size());
            for (EntityVertex vertex : set) {
                logger.info("\t" + vertex);
            }
        }
        /**/
        // </editor-fold>
        /**/
        Set<String> sources, targets;
        Set<String> crossing = new TreeSet<>();
        for (String application : applications) {
            sources = appSources.get(application);
            targets = appTargets.get(application);
            crossing.clear();
            crossing.addAll(sources);
            crossing.retainAll(targets);
            logger.info("map " + application + ", incoming=" + sources.size() + ", outgoing=" + targets.size() + ", crossing=" + crossing.size());
            logger.info("\t" + "incoming=" + sources);
            logger.info("\t" + "outgoing=" + targets);
            if (!crossing.isEmpty()) {
                logger.warn("\t" + "crossing=" + crossing);
            }
        }
        /**/
        List<String> pdq = new ArrayList<>();
        for (String application : vertices.keySet()) {
            Set<EntityVertex> set = vertices.get(application);
            logger.info("map " + application + ", vertices=" + set.size());
            String entityClass;
            Set<String> eas;
            pdq.clear();
            int easize, exclusive = 0;
            for (EntityVertex vertex : set) {
                entityClass = vertex.getEntityClass();
                eas = entityApps.get(entityClass);
                easize = eas == null ? 0 : eas.size();
                if (easize == 1) {
                    exclusive++;
                }
                pdq.add(String.format("%04d", easize) + "·" + vertex.getEntityClass() + (easize == 1 ? "" : " (" + easize + ")"));
            }
            for (String string : ColUtils.sort(pdq)) {
                logger.info("\t" + StringUtils.substringAfter(string, "·"));
            }
            logger.info("map " + application + ", vertices=" + set.size() + ", exclusive=" + exclusive);
        }
        /**/
        pdq.clear();
        for (String entityClass : entityApps.keySet()) {
            Set<String> eas = entityApps.get(entityClass);
            int easize = eas == null ? 0 : eas.size();
            if (easize > 1) {
                pdq.add(String.format("%04d", easize) + "·" + entityClass + ", applications=" + easize + " " + eas);
            }
        }
        if (!pdq.isEmpty()) {
            logger.warn("the following " + pdq.size() + " entities that are included in several applications");
            for (String string : ColUtils.sort(pdq)) {
                logger.info("\t" + StringUtils.substringAfter(string, "·"));
            }
        }
        pdq.clear();
        for (String entityClass : entityRefs.keySet()) {
            Set<String> exr = entityRefs.get(entityClass);
            int exrsize = exr == null ? 0 : exr.size();
            if (exrsize > 0) {
                pdq.add(String.format("%04d", exrsize) + "·" + entityClass + ", applications=" + exrsize + " " + exr);
            }
        }
        if (!pdq.isEmpty()) {
            logger.warn("the following " + pdq.size() + " entities that are referenced from another application");
            for (String string : ColUtils.sort(pdq)) {
                logger.info("\t" + StringUtils.substringAfter(string, "·"));
            }
        }
        /**/
        // <editor-fold defaultstate="collapsed">
        /*
        for (EntityVertex source : vertexArcs.keySet()) {
            Set<ReferenceArc> set = vertexArcs.get(source);
            logger.info("map " + source + ", arcs=" + set.size());
            for (ReferenceArc arc : set) {
                logger.info("\t" + arc);
            }
        }
        /**/
        // </editor-fold>
        /**/
        List<ReferencePath> rpl = new ArrayList<>();
        for (String application : vertices.keySet()) {
            Set<EntityVertex> set = vertices.get(application);
            logger.info("processing " + set.size() + " vertices of " + application);
            for (EntityVertex vertex : set) {
                rpl.addAll(expand(vertex, null));
            }
        }
        /**/
        int cr = 0;
        for (ReferencePath rp : rpl) {
            if (rp.isCircular()) {
                cr++;
                logger.info("CR " + rp.getVertices());
            }
        }
        /**/
        logger.info("reference-path-list=" + rpl.size());
        logger.info("circular-references=" + cr);
    }

    private List<ReferencePath> expand(EntityVertex vertex, ReferencePath incomingReferencePath) {
        List<ReferencePath> rpl = new ArrayList<>();
        Set<ReferenceArc> set = vertexArcs.get(vertex);
        if (set == null || set.isEmpty()) {
            if (incomingReferencePath != null) {
                incomingReferencePath.finalise();
            }
            return rpl;
        }
        EntityVertex target;
        ReferencePath outgoingReferencePath;
        List<ReferencePath> expansion;
        for (ReferenceArc arc : set) {
            target = arc.getTarget();
            outgoingReferencePath = incomingReferencePath == null ? new ReferencePath(arc) : new ReferencePath(incomingReferencePath, target);
            expansion = outgoingReferencePath.isComplete() ? null : expand(target, outgoingReferencePath);
            if (expansion == null || expansion.isEmpty()) {
                rpl.add(outgoingReferencePath);
            } else {
                rpl.addAll(expansion);
            }
        }
        return rpl;
    }

    /**/
    private void mapMaster(Project master) {
        if (master.build()) {
            master.configureGenerator();
            logger.info(signature("map", master));
            /**/
            String masterApplicationOrigin = StringUtils.trimToEmpty(master.getApplicationOrigin());
            String masterApplicationContextRoot = StringUtils.defaultIfBlank(master.getApplicationContextRoot(), jwp(master).getWebProjectName());
            String masterApplication = masterApplicationOrigin + "/" + masterApplicationContextRoot;
            addApplication(masterApplication);
            Collection<Project> modules = ColUtils.filter(master.getModulesList(), new IsProjectModule());
            logger.info("map " + master.getName() + ", modules=" + modules.size());
            for (Project module : modules) {
                if (module.isAnnotatedWithModule()) {
                    mapModule(module, masterApplicationOrigin, masterApplicationContextRoot, masterApplication);
                }
            }
        }
    }

    private void mapModule(Project module, String masterApplicationOrigin, String masterApplicationContextRoot, String masterApplication) {
        String message = signature("map", module.getClass().getSimpleName());
        logger.trace(message);
        /**/
        message = "map " + module.getName();
        message += " {" + "application=" + getApplication(module, masterApplicationOrigin, masterApplicationContextRoot);
//      message += ", " + "foreignEntityClasses=" + module.getForeignEntityClass();
//      message += ", " + "privateEntityClasses=" + module.getPrivateEntityClass();
        message += "}";
        logger.trace(message);
        /**/
        List<Entity> entities = module.getEntitiesList();
        message = "map " + module.getName() + ", entities=" + entities.size();
        message += " " + entities;
        logger.trace(message);
        /**/
        for (Entity entity : entities) {
            mapEntity(entity, masterApplicationOrigin, masterApplicationContextRoot, masterApplication);
        }
        /**/
    }

    private void mapEntity(Entity entity, String masterApplicationOrigin, String masterApplicationContextRoot, String masterApplication) {
        String message = signature("map", entity.getClass().getSimpleName());
        logger.trace(message);
        List<Property> properties = entity.getPropertiesList();
        /**/
        message = "map " + entity.getName() + ", application=" + getApplication(entity, masterApplicationOrigin, masterApplicationContextRoot);
        logger.trace(message);
        /**/
        message = "map " + entity.getName() + ", properties=" + properties.size();
        message += " " + properties;
        logger.trace(message);
        /**/
        Entity root;
        EntityVertex source, target;
        ReferenceArc reference;
        String application = getApplication(entity, masterApplicationOrigin, masterApplicationContextRoot);
        if (masterApplication.equals(application) && entity.isDisplayAvailable()) {
            source = new EntityVertex(application, entity);
            put(source);
            for (Property property : properties) {
                if (property.isEntity()) {
                    root = ((Entity) property).getRoot();
                    if (root.isDisplayAvailable()) {
                        target = new EntityVertex(getApplication(root, masterApplicationOrigin, masterApplicationContextRoot), root);
                        reference = new ReferenceArc(source, target);
                        put(reference);
                    }
                }
            }
        }
        /**/
    }

    private String signature(String method, Object... parameters) {
        String pattern = "{0}({1})";
        return MessageFormat.format(pattern, method, StringUtils.join(parameters, ", "));
    }

    private String getApplication(Project project, String masterApplicationOrigin, String masterApplicationContextRoot) {
        String host = StringUtils.defaultIfBlank(project.getApplicationOrigin(), masterApplicationOrigin);
        String root = StringUtils.defaultIfBlank(project.getApplicationContextRoot(), masterApplicationContextRoot);
        return host + "/" + root;
    }

    private String getApplication(Entity entity, String masterApplicationOrigin, String masterApplicationContextRoot) {
        String host = StringUtils.defaultIfBlank(entity.getApplicationOrigin(), masterApplicationOrigin);
        String root = StringUtils.defaultIfBlank(entity.getApplicationContextRoot(), masterApplicationContextRoot);
        return host + "/" + root;
    }

    private void addApplication(String application) {
        if (applications.contains(application)) {
        } else {
            applications.add(application);
            appSources.put(application, new TreeSet<>());
            appTargets.put(application, new TreeSet<>());
        }
    }

    private void put(EntityVertex vertex) {
        String application = vertex.getApplication();
        String entityClass = vertex.getEntityClass();
        Set<EntityVertex> set = vertices.get(application);
        if (set == null) {
            set = new TreeSet<>();
            set.add(vertex);
            vertices.put(application, set);
        } else {
            set.add(vertex);
        }
        Set<String> eas = entityApps.get(entityClass);
        if (eas == null) {
            eas = new TreeSet<>();
            eas.add(application);
            entityApps.put(entityClass, eas);
        } else {
            eas.add(application);
        }
    }

    private void put(ReferenceArc arc) {
        EntityVertex source = arc.getSource();
        Set<ReferenceArc> set = vertexArcs.get(source);
        if (set == null) {
            set = new TreeSet<>();
            set.add(arc);
            vertexArcs.put(source, set);
        } else {
            set.add(arc);
        }
        if (arc.isAcross()) {
            EntityVertex target = arc.getTarget();
            String sourceApplication = source.getApplication();
            String targetApplication = target.getApplication();
            addApplication(sourceApplication);
            addApplication(targetApplication);
            appSources.get(targetApplication).add(sourceApplication);
            appTargets.get(sourceApplication).add(targetApplication);
            String entityClass = target.getEntityClass();
            Set<String> exr = entityRefs.get(entityClass);
            if (exr == null) {
                exr = new TreeSet<>();
                exr.add(sourceApplication);
                entityRefs.put(entityClass, exr);
            } else {
                exr.add(sourceApplication);
            }
        }
    }

}
