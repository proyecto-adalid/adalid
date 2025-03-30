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
package adalid.core;

import adalid.core.annotations.*;
import adalid.core.interfaces.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.Set;

/**
 * @author Jorge Campins
 */
class FieldAllocationSettings {

    private Set<String> _allocationStrings;

    private boolean _dynParam;

    private int _maxDepth;

    private int _maxRound;

    private String _remarks;

    protected Set<String> getAllocationStrings() { // protected avoids method never unused warning
        return _allocationStrings;
    }

    private void setAllocationStrings(Set<String> allocationStrings) {
        _allocationStrings = allocationStrings;
    }

    private void setAllocationStrings(Object object) {
        if (object instanceof EntityReferenceContainer container) {
            _allocationStrings = container.getAllocationStrings();
            /*
            if (_allocationStrings != null && !_allocationStrings.isEmpty()) {
                System.out.println(((Artifact) object).getFullName() + ".allocationStrings=" + _allocationStrings);
            }
            /**/
        }
    }

    boolean isAllocatable(String fullName) {
        if (_allocationStrings != null && !_allocationStrings.isEmpty()) {
            String prefix = fullName + ".";
            for (String string : _allocationStrings) {
                if (string.equals(fullName) || string.startsWith(prefix)) {
//                  System.out.println("********" + fullName + " / " + string);
                    return true;
                }
            }
//          System.out.println("\t" + fullName); // + "(" + _allocationStrings + ")");
        }
        return false;
    }

    /**
     * @return the dynamic parameter indicator
     */
    boolean isDynParam() {
        return _dynParam;
    }

    /**
     * @param dynamic the dynamic parameter indicator to set
     */
    private void setDynParam(boolean dynamic) {
        _dynParam = dynamic;
    }

    /**
     * @return the max depth
     */
    int getMaxDepth() {
        return _maxDepth;
    }

    /**
     * @param depth the max depth to set
     */
    private void setMaxDepth(int depth) {
        int d = Project.getDefaultMaxDepth();
        _maxDepth = depth < d ? d : depth;
    }

    /**
     * @param depth the max depth to set
     */
    private void setMaxDepthMax(int depth) {
        _maxDepth = Math.max(depth, _maxDepth);
    }

    /**
     *
     */
    private void increaseMaxDepth() {
        _maxDepth++;
    }

    /**
     * @return the max round
     */
    int getMaxRound() {
        return _maxRound;
    }

    /**
     * @param round the max round to set
     */
    private void setMaxRound(int round) {
        int r = Project.getDefaultMaxRound();
        _maxRound = round < r ? r : round;
    }

    /**
     * @param round the max round to set
     */
    private void setMaxRoundMax(int round) {
        _maxRound = Math.max(round, _maxRound);
    }

    /**
     *
     */
    private void increaseMaxRound() {
//      _maxRound++;
    }

    public String getRemarks() {
        return _remarks;
    }

    private void setRemarks(Object object) {
        _remarks = "init(" + (object == null ? "" : object.getClass().getSimpleName()) + ")";
    }

    FieldAllocationSettings() {
        init();
    }

    FieldAllocationSettings(Field field, Object object, int depth, int round) {
        AllocationSettings settings = TLC.getAllocationSettings();
        if (settings == null) {
            init(field, object);
            setAllocationStrings(object);
            createAllocationSettings(depth, round);
        } else if (depth == 1 && isParameterField(field, object)) {
            init(field, object);
            setAllocationStrings(object);
//          setDynParam(true); // do not set dynParam here!
            updateAllocationSettings(settings, depth, round);
        } else if (depth == 2 && settings.isDynParam()) {
            init(field, object);
            setAllocationStrings(settings.getAllocationStrings());
            setDynParam(true);
            increaseMaxDepth();
            increaseMaxRound();
            updateAllocationSettings(settings, depth, round);
        } else if (depth <= settings.getSettingDepth()) {
            init(field, object);
            setAllocationStrings(object);
            updateAllocationSettings(settings, depth, round);
        } else {
            init(settings);
        }
    }

    private void init(Field field, Object object) {
        if (isParameterField(field, object)) {
            int d = Project.getDefaultMaxDepth();
            int r = Project.getDefaultMaxRound();
            FieldAllocationSettings settings = getParameterAllocation(field, object);
            if (settings.isDynParam() || settings.getMaxDepth() > d || settings.getMaxRound() > r) {
                settings.increaseMaxDepth();
                settings.increaseMaxRound();
                settings.setMaxDepthMax(d);
                settings.setMaxRoundMax(r);
                init(settings);
                return;
            }
        }
        // get property allocation
        if (object instanceof Entity entity) {
            Map<String, AllocationOverride> map = entity.getAllocationOverridesMap();
            String key = field.getName();
            if (map.containsKey(key)) {
                AllocationOverride allocation = map.get(key);
                if (allocation != null) {
                    init(allocation);
                    return;
                }
            }
        }
        Allocation allocation = getAllocationAnnotation(field);
        if (allocation != null) {
            init(allocation);
            return;
        }
        init();
    }

    private void init() {
        setMaxDepth(Project.getDefaultMaxDepth());
        setMaxRound(Project.getDefaultMaxRound());
        setRemarks(null);
    }

    private void init(Allocation allocation) {
        setMaxDepth(allocation.maxDepth());
        setMaxRound(allocation.maxRound());
        setRemarks(allocation);
    }

    private void init(AllocationOverride allocation) {
        setMaxDepth(allocation.maxDepth());
        setMaxRound(allocation.maxRound());
        setRemarks(allocation);
    }

    private void init(AllocationSettings settings) {
        setAllocationStrings(settings.getAllocationStrings());
        setDynParam(settings.isDynParam());
        setMaxDepth(settings.getMaxDepth());
        setMaxRound(settings.getMaxRound());
        setRemarks(settings);
    }

    private void init(FieldAllocationSettings settings) {
        setDynParam(settings.isDynParam());
        setMaxDepth(settings.getMaxDepth());
        setMaxRound(settings.getMaxRound());
        setRemarks(settings);
    }

    private boolean isParameterField(Field field, Object object) {
        return Entity.class.isAssignableFrom(field.getType()) && object instanceof Operation;
    }

    private FieldAllocationSettings getParameterAllocation(Field field, Object object) {
        assert true : "object=" + object; // this assert avoids unused parameter warning
        FieldAllocationSettings settings = new FieldAllocationSettings();
        Allocation parameterAllocation = getAllocationAnnotation(field);
        if (parameterAllocation == null) {
            settings.setDynParam(true);
            Class<?> entityFieldType;
            int modifiers;
            for (Field entityField : field.getType().getFields()) {
                entityFieldType = entityField.getType();
                if (Entity.class.isAssignableFrom(entityFieldType)) {
                    modifiers = entityFieldType.getModifiers();
                    if (Modifier.isAbstract(modifiers)) {
                        continue;
                    }
                    modifiers = entityField.getModifiers();
                    if (Modifier.isPrivate(modifiers) || Modifier.isStatic(modifiers) || Modifier.isFinal(modifiers)) {
                        continue;
                    }
                    Allocation entityFieldAllocation = getAllocationAnnotation(entityField);
                    if (entityFieldAllocation != null) {
                        settings.setMaxDepthMax(entityFieldAllocation.maxDepth());
                        settings.setMaxRoundMax(entityFieldAllocation.maxRound());
                    }
                }
            }
        } else {
            settings.setMaxDepthMax(parameterAllocation.maxDepth());
            settings.setMaxRoundMax(parameterAllocation.maxRound());
        }
        return settings;
    }

    private void createAllocationSettings(int depth, int round) {
        AllocationSettings settings = new AllocationSettings();
        updateAllocationSettings(settings, depth, round);
        TLC.setAllocationSettings(settings);
    }

    private void updateAllocationSettings(AllocationSettings settings, int depth, int round) {
        settings.setAllocationStrings(_allocationStrings);
        settings.setDynParam(_dynParam);
        settings.setMaxDepth(_maxDepth);
        settings.setMaxRound(_maxRound);
        settings.setSettingDepth(depth);
        settings.setSettingRound(round);
    }

    private Allocation getAllocationAnnotation(Field field) {
        return field.isAnnotationPresent(Allocation.class) ? field.getAnnotation(Allocation.class) : null;
    }

}
