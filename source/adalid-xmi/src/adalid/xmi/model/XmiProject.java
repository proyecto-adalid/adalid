/*
 * Copyright (C) 2011, EGT Consultores, C.A.
 *
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 *
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.xmi.model;

import adalid.commons.properties.PropertiesHandler;
import adalid.commons.util.FilUtils;
import adalid.commons.util.ThrowableUtils;
import adalid.commons.velocity.VelocityEngineer;
import adalid.xmi.elements.XmiDomTree;
import adalid.xmi.types.XmiPersistentEntity;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections.ExtendedProperties;
import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;

public class XmiProject {

    private static final String USER_DIR = System.getProperties().getProperty("user.dir");

    private static final String FILE_SEP = System.getProperties().getProperty("file.separator");

    private XmiDomTree xmiDomTree;

    private ExtendedProperties bootstrapping;

    // <editor-fold defaultstate="collapsed" desc="Velocity Context keys">
    private static final String VC_PACKAGE = "package";

    private static final String VC_ENTITY = "entity";
    // </editor-fold>

    private static final Logger logger = Logger.getLogger(XmiProject.class);

    private List<XmiPersistentEntity> _entities = new ArrayList<>();

    /**
     * @return the entities
     */
    public List<XmiPersistentEntity> getEntitiesList() {
        return _entities;
    }

    public void setEntitiesList(List<XmiPersistentEntity> entities) {
        _entities = entities;
    }

    public static void main(String[] args) {
        XmiProject xmiProject = new XmiProject();
        xmiProject.writeEntityFiles();
    }

    public XmiProject() {
        File xmiFile;
        bootstrapping = PropertiesHandler.getBootstrapping();
        String xmiFilePath = USER_DIR + FILE_SEP + bootstrapping.getString("xmi.file.path");
        String defaultPackageName = bootstrapping.getString("default.package.name");
        try {
            xmiFile = new File(xmiFilePath);
            xmiDomTree = new XmiDomTree(xmiFile.getAbsolutePath(), defaultPackageName);
            _entities = xmiDomTree.getEntitiesList();
        } catch (Exception ex) {
            logger.fatal("Could not parse File " + xmiFilePath, ex);
        }

    }

    @SuppressWarnings("unchecked")
    public void writeEntityFiles() {
        VelocityContext context = newProjectVelocityContext();
        String tempname = "templates/meta/persistent-entity/meta-persistent-entity.vm";
        String filepath = getMetaDirPath() + '/';
        String filename;
        for (XmiPersistentEntity entity : _entities) {
            context.put("javaClassSimpleName", entity.getName());
            String packageName = entity.getPackageName();
            context.put(VC_PACKAGE, packageName);
            if (!packageName.isEmpty()) {
                String packagePath = filepath + packageName.replace(".", "/") + '/';
                File packageDir = new File(packagePath);
                if (FilUtils.isNotVisibleDirectory(packageDir)) {
                    packageDir.mkdirs();
                }
                filename = packagePath + entity.getName() + '.' + "java";
            } else {
                filename = filepath + entity.getName() + '.' + "java";
            }
            context.put(VC_ENTITY, entity);
            write(context, tempname, filename);
        }
    }

    private VelocityContext newProjectVelocityContext() {
        VelocityContext context = new VelocityContext();
        pc1(context);
        return context;
    }

    private void pc1(VelocityContext context) {
        pcx(context, adalid.commons.velocity.VelocityAid.class);
        pcx(context, adalid.commons.util.StrUtils.class);
        pcx(context, org.apache.commons.lang.StringUtils.class);
    }

    private void pcx(VelocityContext context, Class clazz) {
        context.put(clazz.getSimpleName(), clazz);
    }

    private File getMetaDir() {
        String metajavaPath = USER_DIR + FILE_SEP + bootstrapping.getProperty("metajava.path");
        File meta = new File(metajavaPath);
        if (FilUtils.isVisibleDirectory(meta) || meta.mkdirs()) {
            return meta;
        }
        return meta;
    }

    private String getMetaDirPath() {
        return getMetaDir().getPath().replace(FILE_SEP, "/");
    }

    private void write(VelocityContext context, String tempname, String filename) {
        try {
            VelocityEngineer.write(context, tempname, filename);
        } catch (Exception ex) {
            logger.fatal(ThrowableUtils.getString(ex) + "," + tempname + "," + filename, ex);
        }
    }

}
