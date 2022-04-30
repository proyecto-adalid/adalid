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
import java.util.List;

/**
 * @author Jorge Campins
 */
public interface SpecialEntityPack {

    Class<? extends Entity> ApplicationMessageEntityClass = meta.entidad.comun.configuracion.basica.MensajeAplicacion.class;

    Class<? extends Entity> SegmentSetFactoryEntityClass = meta.entidad.comun.control.acceso.ClaseFabricador.class;

    Class<? extends Entity> TaskNotificationEntityClass = meta.entidad.comun.operacion.basica.TareaUsuario.class;

    Class<? extends Entity> UploadedFileEntityClass = meta.entidad.comun.auditoria.ArchivoAdjunto.class;

    Class<? extends Entity> UserEntityClass = meta.entidad.comun.control.acceso.Usuario.class;

    Class<? extends Entity> VersionEntityClass = meta.entidad.comun.configuracion.basica.VersionAdalid.class;

    /**
     * @return the application message entity class
     */
    Class<? extends Entity> getApplicationMessageEntityClass();

    /**
     * El método setApplicationMessageEntityClass se utiliza para establecer la clase de la entidad <b>Mensaje de la Aplicación</b> del proyecto.
     *
     * @param clazz clase de la entidad <b>Mensaje de la Aplicación</b> del proyecto
     */
    void setApplicationMessageEntityClass(Class<? extends Entity> clazz);

    /**
     * @return the segment set factory entity class
     */
    Class<? extends Entity> getSegmentSetFactoryEntityClass();

    /**
     * El método setSegmentSetFactoryEntityClass se utiliza para establecer la clase de la entidad <b>Clase de Fabricador</b> del proyecto.
     *
     * @param clazz clase de la entidad <b>Clase de Fabricador</b> del proyecto
     */
    void setSegmentSetFactoryEntityClass(Class<? extends Entity> clazz);

    /**
     * @return the uploaded file entity class
     */
    Class<? extends Entity> getTaskNotificationEntityClass();

    /**
     * El método setTaskNotificationEntityClass se utiliza para establecer la clase de la entidad <b>Notificación de Tarea</b> del proyecto.
     *
     * @param clazz clase de la entidad <b>Notificación de Tarea</b> del proyecto
     */
    void setTaskNotificationEntityClass(Class<? extends Entity> clazz);

    /**
     * @return the uploaded file entity class
     */
    Class<? extends Entity> getUploadedFileEntityClass();

    /**
     * El método setUploadedFileEntityClass se utiliza para establecer la clase de la entidad <b>Archivo Adjunto</b> del proyecto.
     *
     * @param clazz clase de la entidad <b>Archivo Adjunto</b> del proyecto
     */
    void setUploadedFileEntityClass(Class<? extends Entity> clazz);

    /**
     * @return the user entity class
     */
    Class<? extends Entity> getUserEntityClass();

    /**
     * El método setUserEntityClass se utiliza para establecer la clase de la entidad <b>Usuario</b> del proyecto.
     *
     * @param clazz clase de la entidad <b>Usuario</b> del proyecto.
     */
    void setUserEntityClass(Class<? extends Entity> clazz);

    /**
     * @return the version entity class
     */
    Class<? extends Entity> getVersionEntityClass();

    /**
     * El método setVersionEntityClass se utiliza para establecer la clase de la entidad <b>Versión</b> del proyecto.
     *
     * @param clazz clase de la entidad <b>Versión</b> del proyecto
     */
    void setVersionEntityClass(Class<? extends Entity> clazz);

    /**
     * @return the list of unset special entity classes
     */
    List<Class<? extends Entity>> unsetSpecialEntityClasses();

}
