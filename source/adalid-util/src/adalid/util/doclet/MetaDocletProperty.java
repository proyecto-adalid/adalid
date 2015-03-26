/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.util.doclet;

import com.sun.javadoc.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public class MetaDocletProperty implements Comparable {

    private static final Logger logger = Logger.getLogger(MetaDoclet.class);

    private static final String PROPERTY = "PROPERTY";

    public MetaDocletProperty(String name) {
        this.name = name;
        this.type = null;
        getters = new ArrayList<>();
        setters = new ArrayList<>();
    }

    String name;

    /**
     * @return the meta-property name
     */
    public String name() {
        return name;
    }

    Type type;

    /**
     * @return the meta-property type
     */
    public Type type() {
        return type;
    }

    List<MethodDoc> getters;

    /**
     * @return the meta-property getters list
     */
    public MethodDoc[] getters() {
        return getters.toArray(new MethodDoc[0]);
    }

    List<MethodDoc> setters;

    /**
     * @return the meta-property setters list
     */
    public MethodDoc[] setters() {
        return setters.toArray(new MethodDoc[0]);
    }

    /**
     * @return the meta-property kind
     */
    public MetaDocletPropertyKind kind() {
        return indeterminate() ? MetaDocletPropertyKind.INDETERMINATE : ambiguous() ? MetaDocletPropertyKind.AMBIGUOUS
            : setters.isEmpty() ? MetaDocletPropertyKind.READABLE : MetaDocletPropertyKind.WRITABLE;
    }

    /**
     * @return true if the meta-property kind is indeterminate; false otherwise
     */
    public boolean indeterminate() {
        return type == null;
    }

    /**
     * @return true if the meta-property kind is ambiguous; false otherwise
     */
    public boolean ambiguous() {
        if (type != null) {
            for (MethodDoc methodDoc : getters) {
                if (type.qualifiedTypeName().equals(methodDoc.returnType().qualifiedTypeName())) {
                    continue;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * @return true if the meta-property kind is not indeterminate nor ambiguous; false otherwise
     */
    public boolean valid() {
        return !indeterminate() && !ambiguous();
    }

    @Override
    public String toString() {
        return kind() + " " + PROPERTY + " " + (type == null ? "" : type.simpleTypeName() + " ") + name;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof MetaDocletProperty) {
            MetaDocletProperty that = (MetaDocletProperty) o;
            return this.name.compareTo(that.name);
        }
        throw new IllegalArgumentException("MetaDocletProperty.compareTo(" + o + ")");
    }

}
