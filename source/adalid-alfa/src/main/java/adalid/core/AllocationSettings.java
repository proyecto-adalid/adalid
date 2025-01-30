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

import java.util.Set;

/**
 * @author Jorge Campins
 */
class AllocationSettings {

    private Set<String> _allocationStrings;

    private boolean _dynParam;

    private int _maxDepth = Project.getDefaultMaxDepth();

    private int _maxRound = Project.getDefaultMaxRound();

    private int _settingDepth;

    private int _settingRound;

    Set<String> getAllocationStrings() {
        return _allocationStrings;
    }

    void setAllocationStrings(Set<String> allocationStrings) {
        _allocationStrings = allocationStrings;
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
    void setDynParam(boolean dynamic) {
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
    void setMaxDepth(int depth) {
        _maxDepth = depth < 1 ? 1 : depth;
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
    void setMaxRound(int round) {
        _maxRound = round < 0 ? 0 : round;
    }

    /**
     * @return the depth
     */
    int getSettingDepth() {
        return _settingDepth;
    }

    /**
     * @param depth the depth to set
     */
    void setSettingDepth(int depth) {
        _settingDepth = depth;
    }

    /**
     * @return the round
     */
    protected int getSettingRound() { // protected avoids method never unused warning
        return _settingRound;
    }

    /**
     * @param round the round to set
     */
    void setSettingRound(int round) {
        _settingRound = round;
    }

}
