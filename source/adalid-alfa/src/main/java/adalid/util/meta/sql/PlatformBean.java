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
package adalid.util.meta.sql;

import java.io.File;
import java.util.Map;
import java.util.TreeMap;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public class PlatformBean {

    private static final Logger logger = Logger.getLogger(PlatformBean.class);

    // <editor-fold defaultstate="collapsed" desc="instance fields">
    private final String _name;

    private final File _propertiesFile;

    private final Map<String, TemplateBean> _templates;
    // </editor-fold>

    public PlatformBean(String name, File propertiesFile) {
        _name = name;
        _propertiesFile = propertiesFile;
        _templates = new TreeMap<>();
    }

    // <editor-fold defaultstate="collapsed" desc="instance fields' public getters and setters">
    /**
     * @return the name
     */
    public String getName() {
        return _name;
    }

    /**
     * @return the properties file
     */
    public File getPropertiesFile() {
        return _propertiesFile;
    }

    /**
     * @return the templates
     */
    public Map<String, TemplateBean> getTemplatesMap() {
        return _templates;
    }
    // </editor-fold>

    public void add(TemplateBean template) {
        String path = template.getPropertiesFile().getPath();
        String text;
        if (StringUtils.isBlank(path)) {
            text = "a null path template will not be added to platform " + _name;
            logger.error(text);
        } else if (_templates.containsKey(path)) {
            text = "template " + path + " already added to platform " + _name;
            logger.error(text);
        } else {
            _templates.put(path, template);
        }
    }

}
