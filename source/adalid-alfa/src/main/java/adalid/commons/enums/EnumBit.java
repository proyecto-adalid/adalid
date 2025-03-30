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
package adalid.commons.enums;

import adalid.commons.list.*;
import adalid.commons.util.*;

/**
 * @author Jorge Campins
 */
public enum EnumBit implements ListOption {

    TRUE(true),
    FALSE(false);

    public static EnumBit valueOf(boolean b) {
        return b ? TRUE : FALSE;
    }

    public static EnumBit valueOf(Boolean b) {
        return valueOf(BitUtils.valueOf(b));
    }

    public static EnumBit valueOf(char c) {
        return valueOf(BitUtils.valueOf(c));
    }

    public static EnumBit valueOf(Integer i) {
        return valueOf(BitUtils.valueOf(i));
    }

    public static EnumBit valueOf(Long l) {
        return valueOf(BitUtils.valueOf(l));
    }

//  public static boolean valueOf(String s) {
//      return valueOf(BitUtils.valueOf(s));
//  }
//
    public static ListOptions getListOptions() {
        return getListOptions(EnumBitLabelSet.YES_OR_NO);
    }

    public static ListOptions getListOptions(EnumBitLabelSet bls) {
        return getListOptions(bls, false);
    }

    public static ListOptions getListOptions(EnumBitLabelSet bls, boolean anulable) {
        return getListOptions(bls, anulable, false);
    }

    public static ListOptions getListOptions(EnumBitLabelSet bls, boolean anulable, boolean ordenado) {
        ListOptions list = new ListOptions(anulable, ordenado);
        for (EnumBit e : EnumBit.values()) {
            list.add(e.intValue(), e.getLabel(bls));
        }
        return list;
    }

    private final boolean value;

    EnumBit(boolean value) {
        this.value = value;
    }

    public boolean booleanValue() {
        return value;
    }

    public int intValue() {
        return IntUtils.valueOf(value);
    }
//
//  public long longValue() {
//      return LongUtils.valueOf(value);
//  }
//

    public String getLabel() {
        return BitUtils.getLabel(value);
    }

    public String getLabel(EnumBitLabelSet bls) {
        return BitUtils.getLabel(value, bls);
    }

    @Override
    public Object getOptionValue() {
        return intValue();
    }

    @Override
    public String getOptionLabel() {
        return getLabel();
    }

    @Override
    public String getOptionDescription() {
        return null;
    }

    @Override
    public boolean isOptionDisabled() {
        return false;
    }

    @Override
    public boolean isNoSelectionOption() {
        return false;
    }

}
