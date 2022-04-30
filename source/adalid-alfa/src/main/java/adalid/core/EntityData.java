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

import adalid.core.interfaces.*;
import adalid.core.sql.*;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
class EntityData {

    // <editor-fold defaultstate="collapsed" desc="static members">
    private static final Logger logger = Logger.getLogger(EntityData.class);

    static void log(Map<String, EntityData> entities) {
        if (entities != null && !entities.isEmpty()) {
            updateReferenceFields(entities);
            String hyphens = hyphens();
            logger.info(hyphens);
            logger.info(labels1());
            logger.info(hyphens);
            int i = 0;
            int q = 0;
            int s = 0;
            for (String name : entities.keySet()) {
                EntityData ed = entities.get(name);
                i += ed.instances;
                q += ed.queries;
                s += ed.sumColumnCount;
                logger.info(ed);
            }
            logger.info(hyphens);
            logger.info(StringUtils.repeat(" ", 100)
                + StringUtils.repeat(" ", 20)
                + String.format("%10d", i)
                + String.format("%10d", q) + StringUtils.repeat(" ", 20)
                + String.format("%10d", s));
            logger.info(hyphens);
        }
    }

    static void log(List<String> allocations) {
        if (allocations != null && !allocations.isEmpty()) {
            String hyphens = hyphens();
            logger.info(hyphens);
            logger.info(labels2());
            logger.info(hyphens);
            for (String allocation : allocations) {
                logger.info(allocation);
            }
            logger.info(hyphens);
        }
    }

    static String log(String name, int depth, int round, int maxDepth, int maxRound) {
        return StringUtils.rightPad(name, 100)
            + StringUtils.leftPad(depth + "/" + maxDepth, 10)
            + StringUtils.leftPad(round + "/" + maxRound, 10);
    }

    private static void updateReferenceFields(Map<String, EntityData> entities) {
        Project project = TLC.getProject();
        if (project != null) {
            for (String name : entities.keySet()) {
                String simpleName = StringUtils.substringAfterLast(name, ".");
                if (simpleName.isEmpty()) {
                    continue;
                }
                Entity entity = project.getEntity(simpleName);
                if (entity != null) {
                    EntityData ed = entities.get(name);
                    ed.referencesCount = entity.getReferencePropertiesCount();
                    List<Property> references = entity.getReferencesList();
                    if (references != null && !references.isEmpty()) {
                        ed.referredByCount = references.size();
                    }
                }
            }
        }
    }

    private static String labels1() {
        return StringUtils.rightPad("CLASS", 100)
            + StringUtils.leftPad("REFERS", 10)
            + StringUtils.leftPad("REFERRED", 10)
            + StringUtils.leftPad("OBJECTS", 10)
            + StringUtils.leftPad("QUERIES", 10)
            + StringUtils.leftPad("MINCOLS", 10)
            + StringUtils.leftPad("MAXCOLS", 10)
            + StringUtils.leftPad("SUMCOLS", 10);
    }

    private static String labels2() {
        return StringUtils.rightPad("CLASS", 100)
            + StringUtils.leftPad("DEPTH", 10)
            + StringUtils.leftPad("ROUND", 10);
    }

    private static String hyphens() {
        return StringUtils.repeat("-", 100) + StringUtils.repeat(" " + StringUtils.repeat("-", 9), 7);
    }
    // </editor-fold>

    private final String name;

    private int instances;

    private int referencesCount, referredByCount;

    private int queries;

    private int maxColumnCount;

    private int minColumnCount;

    private int sumColumnCount;

    EntityData(Entity entity) {
        name = entity.getClass().getName();
        init(entity);
    }

    EntityData(QueryTable queryTable) {
        name = queryTable.getEntity().getClass().getName();
        init(queryTable);
    }

    private void init(Entity entity) {
        assert entity != null;
        instances = 1;
    }

    private void init(QueryTable queryTable) {
        int columnCount = queryTable.getSelectColumnCount();
        queries = 1;
        maxColumnCount = columnCount;
        minColumnCount = columnCount;
        sumColumnCount = columnCount;
    }

    void add(Entity entity) {
        instances++;
    }

    void add(QueryTable queryTable) {
        int columnCount = queryTable.getSelectColumnCount();
        queries++;
        if (columnCount > maxColumnCount) {
            maxColumnCount = columnCount;
        }
        if (columnCount < minColumnCount) {
            minColumnCount = columnCount;
        } else if (minColumnCount == 0) {
            minColumnCount = columnCount;
        }
        sumColumnCount += columnCount;
    }

    @Override
    public String toString() {
        return StringUtils.rightPad(name, 100)
            + String.format("%10d", referencesCount)
            + String.format("%10d", referredByCount)
            + String.format("%10d", instances)
            + String.format("%10d", queries)
            + String.format("%10d", minColumnCount)
            + String.format("%10d", maxColumnCount)
            + String.format("%10d", sumColumnCount);
    }

}
