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
package adalid.jee2;

import adalid.commons.util.StrUtils;

/**
 * @author Jorge Campins
 */
public class WebApplication {

    private final String _code, _name, _description;

    private final boolean _public;

    private String _numericCode;

    private Long _id;

    /**
     * El método estático of se utiliza para construir una aplicación Web. El método retorna una aplicación Web que puede ser agregada la lista de
     * aplicaciones Web del proyecto generado, utilizando el método addWebApplication.
     *
     * @param code código de la aplicación Web; no puede ser nulo
     *
     * @return aplicación Web
     */
    public static WebApplication of(String code) {
        return new WebApplication(code, null, null, false, null);
    }

    /**
     * El método estático of se utiliza para construir una aplicación Web. El método retorna una aplicación Web que puede ser agregada la lista de
     * aplicaciones Web del proyecto generado, utilizando el método addWebApplication.
     *
     * @param code código de la aplicación Web; no puede ser nulo
     * @param name nombre de la aplicación Web; puede ser nulo; si es nulo se utiliza el código como nombre
     *
     * @return aplicación Web
     */
    public static WebApplication of(String code, String name) {
        return new WebApplication(code, name, null, false, null);
    }

    /**
     * El método estático of se utiliza para construir una aplicación Web. El método retorna una aplicación Web que puede ser agregada la lista de
     * aplicaciones Web del proyecto generado, utilizando el método addWebApplication.
     *
     * @param code código de la aplicación Web; no puede ser nulo
     * @param name nombre de la aplicación Web; puede ser nulo; si es nulo se utiliza el código como nombre
     * @param description descripción de la aplicación Web; puede ser nulo
     *
     * @return aplicación Web
     */
    public static WebApplication of(String code, String name, String description) {
        return new WebApplication(code, name, description, false, null);
    }

    /**
     * El método estático of se utiliza para construir una aplicación Web. El método retorna una aplicación Web que puede ser agregada la lista de
     * aplicaciones Web del proyecto generado, utilizando el método addWebApplication.
     *
     * @param code código de la aplicación Web; no puede ser nulo
     * @param name nombre de la aplicación Web; puede ser nulo; si es nulo se utiliza el código como nombre
     * @param description descripción de la aplicación Web; puede ser nulo
     * @param publicAccess true si la aplicación es pública (no requiere autenticación); de lo contrario, false
     *
     * @return aplicación Web
     */
    public static WebApplication of(String code, String name, String description, boolean publicAccess) {
        return new WebApplication(code, name, description, publicAccess, null);
    }

    /**
     * El método estático of se utiliza para construir una aplicación Web. El método retorna una aplicación Web que puede ser agregada la lista de
     * aplicaciones Web del proyecto generado, utilizando el método addWebApplication.
     *
     * @param code código de la aplicación Web; no puede ser nulo
     * @param name nombre de la aplicación Web; puede ser nulo; si es nulo se utiliza el código como nombre
     * @param description descripción de la aplicación Web; puede ser nulo
     * @param publicAccess true si la aplicación es pública (no requiere autenticación); de lo contrario, false
     * @param id número que identifica de manera única a la aplicación en la tabla de aplicaciones del proyecto; puede ser nulo; si es nulo, se genera
     * automáticamente a partir del código
     *
     * @return aplicación Web
     */
    public static WebApplication of(String code, String name, String description, boolean publicAccess, Long id) {
        return new WebApplication(code, name, description, publicAccess, id);
    }

    private WebApplication(String code, String name, String description, boolean publicAccess, Long id) {
        _code = StrUtils.getIdentifier(code, "-");
        _name = name == null ? null : name.replaceAll("\\p{Cntrl}", " ");
        _description = description == null ? null : description.replaceAll("\\p{Cntrl}", " ");
        _public = publicAccess;
        _numericCode = id == null ? numericCode(_code) : id.toString();
        _id = id;
    }

    private String numericCode(String key) {
        String humplessKey = StrUtils.getHumplessCase(key, '_');
        String numericCode = StrUtils.getLongNumericKeyCode(humplessKey);
        return numericCode;
    }

    public String getCode() {
        return _code;
    }

    public String getName() {
        return _name;
    }

    public String getDescription() {
        return _description;
    }

    public boolean isPublic() {
        return _public;
    }

    public String getNumericCode() {
        return _numericCode;
    }

    public Long getId() {
        return _id;
    }

    public void setId(Long id) {
        _id = id;
        _numericCode = id == null ? numericCode(_code) : id.toString();
    }

    @Override
    public String toString() {
        return super.toString()
            + " {id=" + _numericCode
            + ", code=" + _code
            + ", name=" + _name
            + ", description=" + _description
            + ", public=" + _public
            + "}";
    }

}
