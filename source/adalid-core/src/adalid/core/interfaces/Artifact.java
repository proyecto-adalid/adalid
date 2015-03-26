/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.interfaces;

import adalid.core.Operation;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

/**
 * @author Jorge Campins
 */
public interface Artifact {

    /**
     * @return true if the artifact is declared
     */
    boolean isDeclared();

    /**
     * @return true if the artifact is not declared
     */
    boolean isNotDeclared();

    /**
     * @return true if the artifact is inherited
     */
    boolean isInherited();

    /**
     * @return true if the artifact is not inherited
     */
    boolean isNotInherited();

    /**
     * @return the name
     */
    String getName();

    /**
     * @return the alias
     */
    String getAlias();

    /**
     * @param alias the alias to set
     */
    void setAlias(String alias);

    /**
     * @return the SQL name
     */
    String getSqlName();

    /**
     * @param sqlName the SQL name to set
     */
    void setSqlName(String sqlName);

    /**
     * @return the defaultLabel
     */
    String getDefaultLabel();

    /**
     * @param defaultLabel the default label to set
     */
    void setDefaultLabel(String defaultLabel);

    /**
     * @return the defaultShortLabel
     */
    String getDefaultShortLabel();

    /**
     * @param defaultShortLabel the default short label to set
     */
    void setDefaultShortLabel(String defaultShortLabel);

    /**
     * @return the default collection label
     */
    String getDefaultCollectionLabel();

    /**
     * @param defaultCollectionLabel the default collection label to set
     */
    void setDefaultCollectionLabel(String defaultCollectionLabel);

    /**
     * @return the default collection short label
     */
    String getDefaultCollectionShortLabel();

    /**
     * @param defaultCollectionShortLabel the default collection short label to set
     */
    void setDefaultCollectionShortLabel(String defaultCollectionShortLabel);

    /**
     * @return the defaultDescription
     */
    String getDefaultDescription();

    /**
     * @param defaultDescription the default description to set
     */
    void setDefaultDescription(String defaultDescription);

    /**
     * @return the defaultShortDescription
     */
    String getDefaultShortDescription();

    /**
     * @param defaultShortDescription the default short description to set
     */
    void setDefaultShortDescription(String defaultShortDescription);

    /**
     * @return the defaultTooltip
     */
    String getDefaultTooltip();

    /**
     * @param defaultTooltip the default tooltip to set
     */
    void setDefaultTooltip(String defaultTooltip);

    /**
     * @return the declaring artifact
     */
    Artifact getDeclaringArtifact();

    /**
     * @return the declaring field
     */
    Field getDeclaringField();

    /**
     * @return the declaring field index
     */
    int getDeclaringFieldIndex();

    /**
     * @return the declaring entity if the artifact directly declared by one, null otherwise
     */
    Entity getDeclaringEntity();

    /**
     * @return the declaring entity if the artifact directly declared by one, null otherwise
     */
    Entity getDeclaringEntityRoot();

    /**
     * @return the declaring entity if the artifact directly declared by one, null otherwise
     */
    PersistentEntity getDeclaringPersistentEntity();

    /**
     * @return the declaring entity if the artifact directly declared by one, null otherwise
     */
    PersistentEntity getDeclaringPersistentEntityRoot();

    /**
     * @return the declaring entity if the artifact directly declared by one, null otherwise
     */
    Entity getDeclaringFieldEntityRoot();

    /**
     * @return the declaring entity if the artifact directly declared by one, null otherwise
     */
    PersistentEntity getDeclaringFieldPersistentEntityRoot();

    /**
     * @return the declaring operation if the artifact directly declared by one, null otherwise
     */
    Operation getDeclaringOperation();

    /**
     * @return the depth
     */
    int depth();

    /**
     * @return the round
     */
    int round();

    /**
     *
     * @param annotation
     * @param field
     * @return
     */
    Field put(Class<? extends Annotation> annotation, Field field);

    /**
     * @return the class path
     */
    String getClassPath();

    /**
     * @param type
     * @return true if type is present in the class path
     */
    boolean isClassInPath(Class<?> type);

    /**
     * @return the path
     */
    List<Artifact> getPathList();

    /**
     * @return the path string
     */
    String getPathString();

    /**
     * @return the full name
     */
    String getFullName();

    /**
     * @return the partial name
     */
    String getPartialName();

    /**
     * @return true if is a Expression; otherwise false
     */
    boolean isExpression();

    /**
     * @param n
     * @return the string representation of the data
     */
    String toString(int n);

    /**
     * @param n
     * @param key
     * @return the string representation of the data
     */
    String toString(int n, String key);

    /**
     * @param n
     * @param key
     * @param verbose
     * @return the string representation of the data
     */
    String toString(int n, String key, boolean verbose);

    /**
     * @param n
     * @param key
     * @param verbose
     * @param fields
     * @param maps
     * @return the string representation of the data
     */
    String toString(int n, String key, boolean verbose, boolean fields, boolean maps);

}
