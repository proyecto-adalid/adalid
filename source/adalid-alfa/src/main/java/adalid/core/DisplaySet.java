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
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
public class DisplaySet extends AbstractArtifact implements Comparable<DisplaySet> {

    private static final String EOL = "\n";

    public DisplaySet(String name) {
        super();
        init(name);
    }

    private void init(String name) {
        setDeclared(name);
    }

    private Entity _entity;

    private EntityReference _reference;

    private Entity _master;

    private Map<String, Display> _displays;

    /**
     * @return the entity
     */
    public Entity getEntity() {
        return _entity;
    }

    /**
     * @param entity the entity to set
     */
    public void setEntity(Entity entity) {
        _entity = entity;
    }

    /**
     * @return the entity reference
     */
    public EntityReference getReference() {
        return _reference;
    }

    /**
     * @param reference the reference to set
     */
    public void setReference(EntityReference reference) {
        _reference = reference;
    }

    /**
     * @return the master
     */
    public Entity getMaster() {
        return _master;
    }

    /**
     * @param master the master to set
     */
    public void setMaster(Entity master) {
        _master = master;
    }

    /**
     * @return the displays list
     */
    public List<Display> getDisplaysList() {
        return new ArrayList<>(getDisplaysMap().values());
    }

    /**
     * @return the displays map
     */
    public Map<String, Display> getDisplaysMap() {
        if (_displays == null) {
            _displays = new LinkedHashMap<>();
        }
        return _displays;
    }

    // <editor-fold defaultstate="collapsed" desc="Comparable">
    @Override
    public int compareTo(DisplaySet o) {
        DisplaySet that;
        if (o != null) {
            that = o;
            String thisName = StringUtils.trimToEmpty(this.getName());
            String thatName = StringUtils.trimToEmpty(that.getName());
            return thisName.compareTo(thatName);
        }
        return 0;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="toString">
    @Override
    protected String fieldsToString(int n, String key, boolean verbose, boolean fields, boolean maps) {
        String tab = verbose ? StringUtils.repeat(" ", 4) : "";
        String fee = verbose ? StringUtils.repeat(tab, n) : "";
        String faa = " = ";
        String foo = verbose ? EOL : ", ";
        String string = super.fieldsToString(n, key, verbose, fields, maps);
        if (fields || verbose) {
            if (verbose) {
                if (_entity != null) {
                    string += fee + tab + "entity" + faa + _entity + foo;
                }
                if (_reference != null) {
                    string += fee + tab + "_reference" + faa + _reference + foo;
                }
                if (_master != null) {
                    string += fee + tab + "_master" + faa + _master + foo;
                }
            }
        }
        return string;
    }
    // </editor-fold>

}
