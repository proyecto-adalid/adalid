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
package adalid.jee2.features;

import adalid.core.interfaces.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Very Special Project Delegate
 *
 * @author Jorge Campins
 */
public class SpecialEntityPackDelegate implements SpecialEntityPack {

    private Class<? extends Entity> _applicationMessageEntityClass = SpecialEntityPack.ApplicationMessageEntityClass;

    /**
     * @return the application message entity class
     */
    @Override
    public Class<? extends Entity> getApplicationMessageEntityClass() {
        return _applicationMessageEntityClass;
    }

    /**
     * El método setApplicationMessageEntityClass se utiliza para establecer la clase de la entidad <b>Mensaje de la Aplicación</b> del proyecto.
     *
     * @param clazz clase de la entidad <b>Mensaje de la Aplicación</b> del proyecto
     */
    @Override
    public void setApplicationMessageEntityClass(Class<? extends Entity> clazz) {
        _applicationMessageEntityClass = clazz;
    }

    private Class<? extends Entity> _segmentSetFactoryEntityClass = SpecialEntityPack.SegmentSetFactoryEntityClass;

    /**
     * @return the segment set factory entity class
     */
    @Override
    public Class<? extends Entity> getSegmentSetFactoryEntityClass() {
        return _segmentSetFactoryEntityClass;
    }

    /**
     * El método setSegmentSetFactoryEntityClass se utiliza para establecer la clase de la entidad <b>Clase de Fabricador</b> del proyecto.
     *
     * @param clazz clase de la entidad <b>Clase de Fabricador</b> del proyecto
     */
    @Override
    public void setSegmentSetFactoryEntityClass(Class<? extends Entity> clazz) {
        _segmentSetFactoryEntityClass = clazz;
    }

    private Class<? extends Entity> _taskNotificationEntityClass = SpecialEntityPack.TaskNotificationEntityClass;

    /**
     * @return the uploaded file entity class
     */
    @Override
    public Class<? extends Entity> getTaskNotificationEntityClass() {
        return _taskNotificationEntityClass;
    }

    /**
     * El método setTaskNotificationEntityClass se utiliza para establecer la clase de la entidad <b>Archivo Adjunto</b> del proyecto.
     *
     * @param clazz clase de la entidad <b>Archivo Adjunto</b> del proyecto
     */
    @Override
    public void setTaskNotificationEntityClass(Class<? extends Entity> clazz) {
        _taskNotificationEntityClass = clazz;
    }

    private Class<? extends Entity> _uploadedFileEntityClass = SpecialEntityPack.UploadedFileEntityClass;

    /**
     * @return the uploaded file entity class
     */
    @Override
    public Class<? extends Entity> getUploadedFileEntityClass() {
        return _uploadedFileEntityClass;
    }

    /**
     * El método setUploadedFileEntityClass se utiliza para establecer la clase de la entidad <b>Archivo Adjunto</b> del proyecto.
     *
     * @param clazz clase de la entidad <b>Archivo Adjunto</b> del proyecto
     */
    @Override
    public void setUploadedFileEntityClass(Class<? extends Entity> clazz) {
        _uploadedFileEntityClass = clazz;
    }

    private Class<? extends Entity> _userEntityClass = SpecialEntityPack.UserEntityClass;

    /**
     * @return the user entity class
     */
    @Override
    public Class<? extends Entity> getUserEntityClass() {
        return _userEntityClass;
    }

    /**
     * El método setUserEntityClass se utiliza para establecer la clase de la entidad <b>Usuario</b> del proyecto.
     *
     * @param clazz clase de la entidad <b>Usuario</b> del proyecto.
     */
    @Override
    public void setUserEntityClass(Class<? extends Entity> clazz) {
        _userEntityClass = clazz;
    }

    private Class<? extends Entity> _versionEntityClass = SpecialEntityPack.VersionEntityClass;

    /**
     * @return the version entity class
     */
    @Override
    public Class<? extends Entity> getVersionEntityClass() {
        return _versionEntityClass;
    }

    /**
     * El método setVersionEntityClass se utiliza para establecer la clase de la entidad <b>Versión</b> del proyecto.
     *
     * @param clazz clase de la entidad <b>Versión</b> del proyecto
     */
    @Override
    public void setVersionEntityClass(Class<? extends Entity> clazz) {
        _versionEntityClass = clazz;
    }

    /**
     * @return the list of unset special entity classes
     */
    @Override
    public List<Class<? extends Entity>> unsetSpecialEntityClasses() {
        List<Class<? extends Entity>> unset = new ArrayList<>();
        if (_applicationMessageEntityClass == null) {
            unset.add(SpecialEntityPack.ApplicationMessageEntityClass);
        }
        if (_segmentSetFactoryEntityClass == null) {
            unset.add(SpecialEntityPack.SegmentSetFactoryEntityClass);
        }
        if (_uploadedFileEntityClass == null) {
            unset.add(SpecialEntityPack.UploadedFileEntityClass);
        }
        if (_userEntityClass == null) {
            unset.add(SpecialEntityPack.UserEntityClass);
        }
        if (_versionEntityClass == null) {
            unset.add(SpecialEntityPack.VersionEntityClass);
        }
        return unset;
    }

}
