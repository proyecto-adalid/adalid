/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core;

import adalid.core.annotations.Allocation;
import adalid.core.annotations.AllocationOverride;
import adalid.core.interfaces.Entity;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;

/**
 * @author Jorge Campins
 */
class FieldAllocationSettings {

    private static boolean DYNAMIC_PARAMETER_ALLOCATION = true;

    private static boolean PARAMETER_ALLOCATION_OVERRIDE = false;

    private int _maxDepth;

    private int _maxRound;

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
        _maxDepth = depth < 1 ? 1 : depth;
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
        _maxRound = round < 0 ? 0 : round;
    }

    /**
     * @param round the max round to set
     */
    private void setMaxRoundMax(int round) {
        _maxRound = Math.max(round, _maxRound);
    }

    FieldAllocationSettings() {
        init();
    }

    FieldAllocationSettings(Field field, Object object, int depth, int round) {
        AllocationSettings settings = TLC.getAllocationSettings();
        if (settings == null) {
            init(field, object);
            createAllocationSettings(depth, round);
        } else if (depth <= settings.getSettingDepth()) {
            init(field, object);
            updateAllocationSettings(settings, depth, round);
        } else {
            init(settings);
        }
    }

    private void init(Field field, Object object) {
        if (object instanceof Entity) {
            Entity entity = (Entity) object;
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
        FieldAllocationSettings settings = getDynamicParameterAllocation(field, object);
        Allocation allocation = getAllocationAnnotation(field);
        if (allocation != null) {
            if (PARAMETER_ALLOCATION_OVERRIDE) {
                if (settings != null) {
                    if (settings.getMaxDepth() > 1 || settings.getMaxRound() > 0) {
                        settings.increaseMaxDepth();
                        settings.setMaxDepthMax(allocation.maxDepth());
                        settings.setMaxRoundMax(allocation.maxRound());
                        init(settings);
                        return;
                    }
                }
            }
            init(allocation);
            return;
        }
        if (settings != null) {
            if (settings.getMaxDepth() > 1 || settings.getMaxRound() > 0) {
                settings.increaseMaxDepth();
                settings.setMaxDepthMax(Project.getDefaultMaxDepth());
                settings.setMaxRoundMax(Project.getDefaultMaxRound());
                init(settings);
                return;
            }
        }
        init();
    }

    private void init() {
        setMaxDepth(Project.getDefaultMaxDepth());
        setMaxRound(Project.getDefaultMaxRound());
    }

    private void init(Allocation allocation) {
        setMaxDepth(allocation.maxDepth());
        setMaxRound(allocation.maxRound());
    }

    private void init(AllocationOverride allocation) {
        setMaxDepth(allocation.maxDepth());
        setMaxRound(allocation.maxRound());
    }

    private void init(AllocationSettings settings) {
        setMaxDepth(settings.getMaxDepth());
        setMaxRound(settings.getMaxRound());
    }

    private void init(FieldAllocationSettings settings) {
        setMaxDepth(settings.getMaxDepth());
        setMaxRound(settings.getMaxRound());
    }

    private FieldAllocationSettings getDynamicParameterAllocation(Field field, Object object) {
        if (DYNAMIC_PARAMETER_ALLOCATION) {
            if (object instanceof Operation) {
                Class<?> fieldType = field.getType();
                if (Entity.class.isAssignableFrom(fieldType)) {
                    FieldAllocationSettings settings = new FieldAllocationSettings();
                    Class<?> entityFieldType;
                    int modifiers;
                    for (Field entityField : fieldType.getFields()) {
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
                    return settings;
                }
            }
        }
        return null;
    }

    private void createAllocationSettings(int depth, int round) {
        AllocationSettings settings = new AllocationSettings();
        updateAllocationSettings(settings, depth, round);
        TLC.setAllocationSettings(settings);
    }

    private void updateAllocationSettings(AllocationSettings settings, int depth, int round) {
        settings.setMaxDepth(_maxDepth);
        settings.setMaxRound(_maxRound);
        settings.setSettingDepth(depth);
        settings.setSettingRound(round);
    }

    private Allocation getAllocationAnnotation(Field field) {
        return field.isAnnotationPresent(Allocation.class) ? field.getAnnotation(Allocation.class) : null;
    }

}
