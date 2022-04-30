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

import adalid.core.expressions.*;
import adalid.core.interfaces.*;
import java.util.Locale;

/**
 * @author Jorge Campins
 */
public class NativeQuerySegment {

    public static NativeQuerySegment of(Segment segment) {
        return segment == null ? null : new NativeQuerySegment(segment);
    }

    private NativeQuerySegment(Segment segment) {
        _segment = segment;
    }

    private final Segment _segment;

    Segment getSegment() {
        return _segment;
    }

    public NativeQuerySegment and(NativeQuerySegment y) {
        BooleanOrderedPairX segment = y == null ? null : XB.Boolean.OrderedPair.and(_segment, y.getSegment());
        return segment == null ? null : new NativeQuerySegment(segment);
    }

    public NativeQuerySegment or(NativeQuerySegment y) {
        BooleanOrderedPairX segment = y == null ? null : XB.Boolean.OrderedPair.or(_segment, y.getSegment());
        return segment == null ? null : new NativeQuerySegment(segment);
    }

    public void setDefaultLabel(String defaultLabel) {
        _segment.setDefaultLabel(defaultLabel);
    }

    public void setDefaultShortLabel(String defaultShortLabel) {
        _segment.setDefaultShortLabel(defaultShortLabel);
    }

    public void setDefaultCollectionLabel(String defaultCollectionLabel) {
        _segment.setDefaultCollectionLabel(defaultCollectionLabel);
    }

    public void setDefaultCollectionShortLabel(String defaultCollectionShortLabel) {
        _segment.setDefaultCollectionShortLabel(defaultCollectionShortLabel);
    }

    public void setDefaultDescription(String defaultDescription) {
        _segment.setDefaultDescription(defaultDescription);
    }

    public void setDefaultShortDescription(String defaultShortDescription) {
        _segment.setDefaultShortDescription(defaultShortDescription);
    }

    public void setLocalizedLabel(Locale locale, String localizedLabel) {
        _segment.setLocalizedLabel(locale, localizedLabel);
    }

    public void setLocalizedShortLabel(Locale locale, String localizedShortLabel) {
        _segment.setLocalizedShortLabel(locale, localizedShortLabel);
    }

    public void setLocalizedCollectionLabel(Locale locale, String localizedCollectionLabel) {
        _segment.setLocalizedCollectionLabel(locale, localizedCollectionLabel);
    }

    public void setLocalizedCollectionShortLabel(Locale locale, String localizedCollectionShortLabel) {
        _segment.setLocalizedCollectionShortLabel(locale, localizedCollectionShortLabel);
    }

    public void setLocalizedDescription(Locale locale, String localizedDescription) {
        _segment.setLocalizedDescription(locale, localizedDescription);
    }

    public void setLocalizedShortDescription(Locale locale, String localizedShortDescription) {
        _segment.setLocalizedShortDescription(locale, localizedShortDescription);
    }

}
