/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core;

/**
 * @author Jorge Campins
 */
class AllocationSettings {

    private int _maxDepth = Project.getDefaultMaxDepth();

    private int _maxRound = Project.getDefaultMaxRound();

    private int _settingDepth;

    private int _settingRound;

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
    int getSettingRound() {
        return _settingRound;
    }

    /**
     * @param round the round to set
     */
    void setSettingRound(int round) {
        _settingRound = round;
    }

}
