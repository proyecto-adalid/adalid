/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.commons.properties;

import java.io.File;
import java.util.Properties;
import org.apache.commons.collections.ExtendedProperties;

/**
 * @author Jorge Campins
 */
public class PropertiesBundle {

    private File _propertiesFile;

    private Properties _properties;

    private ExtendedProperties _extendedProperties;

    public PropertiesBundle(File propertiesFile) {
        _propertiesFile = propertiesFile;
        _properties = PropertiesHandler.loadProperties(propertiesFile);
        _extendedProperties = PropertiesHandler.getExtendedProperties(propertiesFile);
    }

    /**
     * @return the properties file
     */
    public File getPropertiesFile() {
        return _propertiesFile;
    }

    /**
     * @param propertiesFile the properties file to set
     */
    public void setPropertiesFile(File propertiesFile) {
        _propertiesFile = propertiesFile;
    }

    /**
     * @return the properties
     */
    public Properties getProperties() {
        return _properties;
    }

    /**
     * @param properties the properties to set
     */
    public void setProperties(Properties properties) {
        _properties = properties;
    }

    /**
     * @return the extended properties
     */
    public ExtendedProperties getExtendedProperties() {
        return _extendedProperties;
    }

    /**
     * @param extendedProperties the extended properties to set
     */
    public void setExtendedProperties(ExtendedProperties extendedProperties) {
        _extendedProperties = extendedProperties;
    }

}
