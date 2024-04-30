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
package adalid.core.data.types;

import adalid.core.*;
import adalid.core.enums.*;
import adalid.core.interfaces.*;
import adalid.core.primitives.*;

/**
 * @author Jorge Campins
 */
public class BinaryData extends BinaryPrimitive {

    public static final String EMPTY = "";

    {
        XS2.setDataClass(this, BinaryData.class);
        XS2.setDataType(this, byte[].class);
    }

    // <editor-fold defaultstate="collapsed" desc="until 06/06/2022">
    /*
    private int _displayWidth = -1; // Constants.DEFAULT_IMAGE_WIDTH;

    public int getDisplayWidth() {
        return _displayWidth;
    }

    public void setDisplayWidth(int width) {
        checkScope();
        _displayWidth = width;
    }

    private int _displayHeight = -1; // Constants.DEFAULT_IMAGE_HEIGHT;

    public int getDisplayHeight() {
        return _displayHeight;
    }

    public void setDisplayHeight(int height) {
        checkScope();
        _displayHeight = height;
    }
    /**/
    // </editor-fold>
    /**/
    // <editor-fold defaultstate="collapsed" desc="since 06/06/2022">
    private int _largeDisplayWidth = Constants.DEFAULT_LARGE_IMAGE_WIDTH;

    public int getLargeDisplayWidth() {
        return _largeDisplayWidth;
    }

    public void setLargeDisplayWidth(int width) {
        checkScope();
        _largeDisplayWidth = width;
    }

    private int _largeDisplayHeight = Constants.DEFAULT_LARGE_IMAGE_HEIGHT;

    public int getLargeDisplayHeight() {
        return _largeDisplayHeight;
    }

    public void setLargeDisplayHeight(int height) {
        checkScope();
        _largeDisplayHeight = height;
    }

    private int _mediumDisplayWidth = Constants.DEFAULT_MEDIUM_IMAGE_WIDTH;

    public int getMediumDisplayWidth() {
        return _mediumDisplayWidth;
    }

    public void setMediumDisplayWidth(int width) {
        checkScope();
        _mediumDisplayWidth = width;
    }

    private int _mediumDisplayHeight = Constants.DEFAULT_MEDIUM_IMAGE_HEIGHT;

    public int getMediumDisplayHeight() {
        return _mediumDisplayHeight;
    }

    public void setMediumDisplayHeight(int height) {
        checkScope();
        _mediumDisplayHeight = height;
    }

    private int _smallDisplayWidth = Constants.DEFAULT_SMALL_IMAGE_WIDTH;

    public int getSmallDisplayWidth() {
        return _smallDisplayWidth;
    }

    public void setSmallDisplayWidth(int width) {
        checkScope();
        _smallDisplayWidth = width;
    }

    private int _smallDisplayHeight = Constants.DEFAULT_SMALL_IMAGE_HEIGHT;

    public int getSmallDisplayHeight() {
        return _smallDisplayHeight;
    }

    public void setSmallDisplayHeight(int height) {
        checkScope();
        _smallDisplayHeight = height;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="since 23/01/2024">
    public int[] getDisplayWidth() {
        return new int[]{_largeDisplayWidth, _mediumDisplayWidth, _smallDisplayWidth};
    }

    public int[] getDisplayHeight() {
        return new int[]{_largeDisplayHeight, _mediumDisplayHeight, _smallDisplayHeight};
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="since 17/03/2024">
    private FetchType _fetchType = FetchType.EAGER;

    public FetchType getFetchType() {
        return _fetchType;
    }

    public void setFetchType(FetchType fetchType) {
        _fetchType = fetchType;
    }
    // </editor-fold>

    public CharacterExpression fileReferenceCalculableValueExpression() {
        return isNotNull().then(getName());
    }

    @Override
    protected boolean isImplicitOverlayImageProperty() {
        return super.isImplicitOverlayImageProperty() && AvatarShape.NONE.equals(_avatarShape);
    }

    private AvatarShape _avatarShape = AvatarShape.NONE;

    public AvatarShape getAvatarShape() {
        return _avatarShape;
    }

    public void setAvatarShape(AvatarShape shape) {
        checkScope();
        _avatarShape = shape == null ? AvatarShape.NONE : shape;
    }

    private AvatarDefault _avatarDefault = AvatarDefault.NONE;

    public AvatarDefault getAvatarDefault() {
        return _avatarDefault;
    }

    public void setAvatarDefault(AvatarDefault defaultAvatar) {
        checkScope();
        _avatarDefault = defaultAvatar == null ? AvatarDefault.NONE : defaultAvatar;
    }

    private int _avatarWidth = 36;

    public int getAvatarWidth() {
        return _avatarWidth;
    }

    public void setAvatarWidth(int size) {
        checkScope();
        _avatarWidth = size < 24 || size > 96 ? 36 : size;
    }

    private int _avatarHeight = 36;

    public int getAvatarHeight() {
        return _avatarHeight;
    }

    public void setAvatarHeight(int size) {
        checkScope();
        _avatarHeight = size < 24 || size > 96 ? 36 : size;
    }

    private boolean _resizable = true;

    public boolean isResizable() {
        return _resizable;
    }

    public void setResizable(boolean resizable) {
        checkScope();
        _resizable = resizable;
    }

}
