/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core;

import adalid.core.annotations.DiscriminatorColumn;
import adalid.core.annotations.EntityClass;
import adalid.core.annotations.EntityConsoleView;
import adalid.core.annotations.EntityDeleteOperation;
import adalid.core.annotations.EntityDetailView;
import adalid.core.annotations.EntityExportOperation;
import adalid.core.annotations.EntityInsertOperation;
import adalid.core.annotations.EntityReferenceSearch;
import adalid.core.annotations.EntityReportOperation;
import adalid.core.annotations.EntitySelectOperation;
import adalid.core.annotations.EntityTableView;
import adalid.core.annotations.EntityTreeView;
import adalid.core.annotations.EntityUpdateOperation;
import adalid.core.enums.Kleenean;
import adalid.core.enums.ResourceType;
import adalid.core.enums.SearchType;
import adalid.core.interfaces.Artifact;
import adalid.core.interfaces.Entity;
import adalid.core.interfaces.PersistentEnumerationEntityReference;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
@EntityClass(resourceType = ResourceType.CONFIGURATION)
@EntitySelectOperation(rowsLimit = 0)
@EntityInsertOperation(enabled = Kleenean.FALSE)
@EntityUpdateOperation(enabled = Kleenean.TRUE)
@EntityDeleteOperation(enabled = Kleenean.FALSE)
@EntityReportOperation(enabled = Kleenean.FALSE)
@EntityExportOperation(enabled = Kleenean.FALSE)
@EntityTableView()
@EntityDetailView(enabled = Kleenean.FALSE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
@EntityReferenceSearch(searchType = SearchType.LIST)
public abstract class AbstractPersistentEnumerationEntity extends AbstractPersistentEntity implements PersistentEnumerationEntityReference {

    public AbstractPersistentEnumerationEntity(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }

    /**
     *
     */
    private static final Logger logger = Logger.getLogger(Entity.class);

    @Override
    public void finalise() {
        super.finalise();
        check();
    }

    private void check() {
        if (isRootInstance()) {
            if (getBusinessKeyProperty() == null) {
                String message = getName() + " does not have a busines key property";
                logger.error(message);
                TLC.getProject().getParser().increaseErrorCount();
            }
        }
    }

    @Override
    protected List<Class<? extends Annotation>> getValidFieldAnnotations() {
        List<Class<? extends Annotation>> valid = super.getValidFieldAnnotations();
        if (isProperty()) {
            valid.add(DiscriminatorColumn.class);
        }
        return valid;
    }

    private Instance[] _removeInstanceArray;

    /**
     * @return the remove instance array
     */
    public Instance[] getRemoveInstanceArray() {
        return _removeInstanceArray;
    }

    /**
     * @param instances the remove instance array to set
     */
    public void setRemoveInstanceArray(Instance... instances) {
        _removeInstanceArray = instances;
        _searchInstanceArray = null;
    }

    private Instance[] _searchInstanceArray;

    /**
     * @return the search instance array
     */
    public Instance[] getSearchInstanceArray() {
        if (_searchInstanceArray == null && _removeInstanceArray != null && _removeInstanceArray.length > 0) {
            _searchInstanceArray = getInsertableRowsList().toArray(new Instance[0]);
            for (Instance instance : _removeInstanceArray) {
                _searchInstanceArray = (Instance[]) ArrayUtils.removeElement(_searchInstanceArray, instance);
            }
        }
        return _searchInstanceArray;
    }

    /**
     * @param instances the search instance array to set
     */
    public void setSearchInstanceArray(Instance... instances) {
        _searchInstanceArray = instances;
    }

}
