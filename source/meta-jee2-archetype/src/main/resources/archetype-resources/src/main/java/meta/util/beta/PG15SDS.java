#set($dollar = '$')
#set($pound = '#')
#set($backslash = '\')
/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package ${package}.meta.util.beta;

import adalid.commons.properties.PropertiesHandler;
import adalid.commons.util.BitUtils;
import adalid.commons.util.IntUtils;
import adalid.commons.util.StrUtils;
import adalid.util.Utility;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author ADALID meta-jee2-archetype, version 6.0.0
 */
public class PG15SDS extends Utility { // PostgreSQL version 15 Schema Dump Splitter

    private static final Logger logger = org.apache.log4j.Logger.getLogger(Utility.class);

    private static final String LOG = "log";

    private static final String SQL = "sql";

    private static final String[] KEYWORDS = {"SETOF", "RECORD", "TABLE", "TRIGGER", "VOID"};

    // <editor-fold defaultstate="collapsed" desc="parámetros predeterminados">
    private static final String DBNAME = "${artifactId.toLowerCase().replaceAll('^\Qmeta\E[\_\-\.]+', '').replaceAll('[\_\-\.]+\Qmeta\E$', '').replaceAll('[^a-z0-9]', '')}ap101";

    private static final String SCHEMA = "public";

    private static final String FOLDER = ${package}.meta.util.Aid.PROJECT_BASE + "${backslash}${backslash}source${backslash}${backslash}management${backslash}${backslash}backup${backslash}${backslash}postgresql";

    private static final String LATIN1 = StandardCharsets.ISO_8859_1.name();

    private static final boolean OMITIR = false;

    private static final boolean ADALID = false;

    private static final boolean ATENEA = false;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="tablas de la plataforma ADALID/JEE2">
    private static final String[] TABLAS_PLATAFORMA_ADALID = {
        "accion_archivo_cargado",
        "ambiente_prueba",
        "aplicacion",
        "archivo_adjunto",
        "atributo_aplicacion",
        "campo_valor_temporal",
        "caso_prueba",
        "clase_fabricador",
        "clase_java",
        "clase_recurso",
        "condicion_eje_fun",
        "condicion_tarea",
        "conjunto_segmento",
        "dialogo_dinamico_remoto",
        "documento_prueba",
        "documento_prueba_x1",
        "documento_prueba_x2",
        "documento_prueba_x3",
        "documento_prueba_x4",
        "documento_prueba_x5",
        "documento_prueba_x6",
        "dominio",
        "dominio_parametro",
        "ejecucion_linea_prueba",
        "ejecucion_prueba",
        "elemento_segmento",
        "escenario_prueba",
        "filtro_funcion",
        "filtro_funcion_par",
        "formato_pagina_informe",
        "funcion",
        "funcion_parametro",
        "grupo_proceso",
        "grupo_usuario",
        "linea_prueba",
        "mensaje_aplicacion",
        "modulo_aplicacion",
        "operador_com",
        "pagina",
        "pagina_especial",
        "pagina_inicio",
        "pagina_usuario",
        "paquete_prueba",
        "parametro",
        "parametro_linea_prueba",
        "parte_ambiente_prueba",
        "pieza_ambiente_prueba",
        "programa_prueba",
        "rango_agregacion",
        "rango_comparacion",
        "rastro_funcion",
        "rastro_funcion_par",
        "rastro_informe",
        "rastro_proceso",
        "rastro_proceso_temporal",
        "recurso",
        "recurso_valor",
        "rol",
        "rol_filtro_funcion",
        "rol_funcion",
        "rol_funcion_par",
        "rol_pagina",
        "rol_pagina_especial",
        "rol_usuario",
        "rol_vista_funcion",
        "segmento",
        "severidad_mensaje",
        "subtipo_grafico",
        "tarea_usuario",
        "tarea_usuario_correo",
        "tarea_virtual",
        "tipo_agregacion",
        "tipo_clase_recurso",
        "tipo_comparacion",
        "tipo_dato_par",
        "tipo_documento_prueba",
        "tipo_dominio",
        "tipo_funcion",
        "tipo_grafico",
        "tipo_informe",
        "tipo_nodo",
        "tipo_pagina",
        "tipo_parametro",
        "tipo_parametro_dom",
        "tipo_pieza_prueba",
        "tipo_rastro_fun",
        "tipo_recurso",
        "tipo_restriccion_formatos",
        "tipo_resultado_prueba",
        "tipo_rol",
        "tipo_valor",
        "tipo_valor_criterio",
        "transicion_tarea_usuario",
        "usuario",
        "usuario_funcion",
        "usuario_modulo",
        "usuario_segmento",
        "version_adalid",
        "vista_funcion",
        "vista_funcion_col"
    };

    private static final String[] VISTAS_PLATAFORMA_ADALID = {
        "tarea_correo",
        "tarea_virtual",
        "usuario_funcion",
        "vista_autenticacion_1",
        "vista_autenticacion_2",
        "vista_autenticacion_3",
        "vista_catalogo_1"
    };

    /*
    private static final String[] TABLAS_PLATAFORMA_ATENEA = {
        "aplicacion",
        "clase_objeto",
        "clase_recurso",
        "clase_recurso_parametro",
        "columna_clase_objeto",
        "columna_lista_valor",
        "dominio",
        "dominio_paquete",
        "dominio_parametro",
        "filtro_clase_recurso_parametro",
        "funcion",
        "funcion_parametro",
        "grupo_aplicacion",
        "mensaje",
        "miembro_modulo",
        "modulo",
        "objeto",
        "opcion_menu",
        "opcion_sistema",
        "pagina",
        "pagina_funcion",
        "paquete",
        "parametro",
        "propiedad",
        "propiedad_clase",
        "propiedad_objeto",
        "rastro_funcion_parametro",
        "tipo_dato_lista_valor",
        "tipo_dato_propiedad"
    };

    /**/
    private static final String[] TABLAS_PLATAFORMA_ATENEA = {
        "clase_recurso_parametro",
        "opcion_menu",
        "rastro_funcion_parametro"
    };

    private static final String[] CODIGO_PLATAFORMA_ATENEA = {
        "cast_varchar_as",
        "check_procedure",
        "setup_database_upgrade",
        "xcopy1",
        "xcopy2"
    };
    // </editor-fold>

    public static void main(String[] args) {
        PG15SDS pg15sds = new PG15SDS();
        pg15sds.split(args);
    }

    private void split(String[] args) {
        String dbname, schema, folder, encode;
        boolean omitir, adalid, atenea;
        if (args == null) {
            dbname = DBNAME;
            schema = SCHEMA;
            folder = FOLDER;
            encode = LATIN1;
            omitir = OMITIR;
            adalid = ADALID;
            atenea = ATENEA;
        } else {
            dbname = args.length > 0 ? StringUtils.defaultIfBlank(args[0], DBNAME) : DBNAME;
            schema = args.length > 1 ? StringUtils.defaultIfBlank(args[1], SCHEMA) : SCHEMA;
            folder = args.length > 2 ? StringUtils.defaultIfBlank(args[2], FOLDER) : FOLDER;
            encode = args.length > 3 ? StringUtils.defaultIfBlank(args[3], LATIN1) : LATIN1;
            omitir = args.length > 4 ? BitUtils.valueOf(args[4]) : OMITIR;
            adalid = args.length > 5 ? BitUtils.valueOf(args[5]) : ADALID;
            atenea = args.length > 6 ? BitUtils.valueOf(args[6]) : ATENEA;
        }
        /**/
        logger.info("args[0] -> dbname = " + dbname);
        logger.info("args[1] -> schema = " + schema);
        logger.info("args[2] -> folder = " + folder);
        logger.info("args[3] -> encode = " + encode);
        logger.info("args[4] -> omitir = " + omitir);
        logger.info("args[5] -> adalid = " + adalid);
        logger.info("args[6] -> atenea = " + atenea);
        /**/
        split(dbname, schema, folder, encode, omitir, adalid, atenea);
    }

    private final List<String> infos = new ArrayList<>();

    private void split(String dbname, String schema, String folder, String charset, boolean omitir, boolean adalid, boolean atenea) {
        /**/
        File parent = directoryOf(folder);
        File topdir = new File(parent, dbname + FILE_SEP + schema);
        File subdir;
        /**/
        File ddlfile = new File(parent, dbname + "." + schema + "." + SQL);
        File logfile = new File(parent, dbname + "." + schema + "." + LOG);
        File sqlfile = null;
        /**/
        logger.info("pg-dump-ddl=" + ddlfile);
        /**/
        final List<String> lines = new ArrayList<>();
        final List<String> tableNames = adalid ? new ArrayList<>() : tableNames(atenea, schema, ddlfile, charset);
        /**/
        final Map<String, Integer> readings = new TreeMap<>();
        final Map<String, Integer> writings = new TreeMap<>();
        /**/
        final Set<String> excludedTableNames = new TreeSet<>();
        final Set<String> excludedTableAndOtherNames = new TreeSet<>();
        /**/
        Collections.sort(tableNames, new TableNamesComparator());
        if (omitir) {
            if (adalid) {
                excludedTableNames.addAll(new TreeSet<>(Arrays.asList(TABLAS_PLATAFORMA_ADALID)));
            }
            if (atenea) {
                excludedTableNames.addAll(new TreeSet<>(Arrays.asList(TABLAS_PLATAFORMA_ATENEA)));
            }
            excludedTableAndOtherNames.addAll(new TreeSet<>(excludedTableNames));
            if (atenea) {
                excludedTableAndOtherNames.addAll(new TreeSet<>(Arrays.asList(CODIGO_PLATAFORMA_ATENEA)));
            }
        }
        /**/
//      LineIterator it = null;
        try (LineIterator it = FileUtils.lineIterator(ddlfile, charset)) {
            int counter = 0;
            boolean finished = false;
            String line, prefijo;
            String objtype, objname;
            String partype, parname;
            String closure = null;
            String routine = null;
            String dirname, tabname;
            String sqlname, sqltype, subtype;
            String[] token;
            FileUtils.deleteQuietly(topdir);
            FileUtils.forceMkdir(topdir);
            while (it.hasNext()) {
                line = it.next();
                token = split(line);
                if (token != null) {
                    if (sqlfile == null) {
                        if (token.length > 3) {
                            if (line.startsWith("CREATE") && schema.equals(token[2])) {
                                objtype = token[1];
                                parname = token.length > 4 ? StrUtils.disclose(token[4], '"') : null;
                                partype = token.length > 5 ? StrUtils.disclose(token[5], '"') : null;
                                increase(readings, objtype);
                                closure = null;
                                routine = null;
                                dirname = null;
                                sqlname = null;
                                sqlfile = null;
                                subtype = "";
                                if ("FUNCTION".equals(objtype) || "PROCEDURE".equals(objtype)) {
                                    objname = StrUtils.disclose(token[3], '"');
                                    routine = objname;
                                    sqltype = sqltype(/*schema, */token);
                                    subtype = StringUtils.startsWithAny(sqltype.toUpperCase(), KEYWORDS) ? FILE_SEP + sqltype : "";
                                    if (adalid) {
                                        tabname = routine.contains("${dollar}") ? StringUtils.split(routine, '${dollar}')[0] : null;
                                        if (tabname == null || excludedTableNames.contains(tabname)) {
                                        } else {
                                            lines.addAll(replaceWarning(routine));
                                            sqlname = StringUtils.defaultIfBlank(StringUtils.substringAfter(routine, "${dollar}"), routine);
                                            dirname = objtype + FILE_SEP + tabname;
                                            if (subtype.isEmpty()) {
                                                subtype = FILE_SEP + ("bigint".equals(partype) && "_super${dollar}".equals(parname) ? "con-super" : "sin-super");
                                            }
                                        }
                                    } else {
                                        tabname = directoryOf(tableNames, routine);
                                        if (tabname == null) {
                                            sqlname = routine;
                                            tabname = directoryOf(routine, tableNames);
                                            dirname = objtype + FILE_SEP
                                                + (tabname != null
                                                    ? "CONTIENE" + FILE_SEP + tabname
                                                    : "ETCETERA" + FILE_SEP + StringUtils.split(routine, '_')[0]);
                                        } else if (excludedTableAndOtherNames.contains(tabname)) {
                                        } else {
                                            prefijo = tabname + '_';
                                            sqlname = StringUtils.startsWith(routine, prefijo) ? StringUtils.removeStart(routine, prefijo) : routine;
                                            dirname = objtype + FILE_SEP + tabname;
                                            if (subtype.isEmpty()) {
                                                subtype = FILE_SEP + ("bigint".equals(partype) && "rastro".equals(parname) ? "con-rastro" : "sin-rastro");
                                            }
                                        }
                                    }
                                } else if ("SEQUENCE".equals(objtype) || "TABLE".equals(objtype) || "TYPE".equals(objtype) || "VIEW".equals(objtype)) {
                                    objname = StrUtils.disclose(token[3], '"');
                                    if (included(adalid, atenea, excludedTableNames, objtype, objname)) {
                                        sqlname = objname;
                                        dirname = objtype;
                                    }
                                }
                                if (dirname != null && sqlname != null) {
                                    counter = increase(writings, objtype);
                                    subdir = new File(topdir, dirname + subtype);
                                    sqlname = sqlname.replace('+', '_').replace('*', '_').replace('?', '_') + "." + String.format("%04d", counter) + "." + SQL;
                                    sqlfile = new File(subdir, sqlname);
                                    FileUtils.forceMkdir(subdir);
                                    lines.add(line);
                                    infos.add(String.format("%04d", counter) + "${backslash}t" + line);
                                }
                            }
                        }
                    } else {
                        lines.add(line);
                        if (routine != null) {
                            if (closure == null) {
                                if (token.length == 2 && "AS".equals(token[0])) {
                                    closure = token[1] + ";";
                                    infos.add(String.format("%04d", counter) + "${backslash}t" + line);
                                }
                            } else {
                                finished = token.length == 1 && closure.equals(token[0]);
                            }
                        } else {
                            finished = line.endsWith(";");
                        }
                        if (finished) {
                            FileUtils.writeLines(sqlfile, LATIN1, lines);
                            lines.clear();
                            sqlfile = null;
                            finished = false;
                            infos.add(String.format("%04d", counter) + "${backslash}t" + line);
                        }
                    }
                }
            }
            FileUtils.writeLines(logfile, LATIN1, infos);
        } catch (IOException ex) {
            logger.fatal(ex);
        } finally {
//          LineIterator.closeQuietly(it);
        }
        int comandos, archivos, omitidos;
        for (String key : readings.keySet()) {
            comandos = IntUtils.valueOf(readings.get(key), 0);
            archivos = IntUtils.valueOf(writings.get(key), 0);
            omitidos = comandos - archivos;
            logger.info("CREATE " + key);
            logger.info("${backslash}tComandos = " + comandos);
            logger.info("${backslash}tArchivos = " + archivos);
            logger.info("${backslash}tOmitidos = " + omitidos);
        }

    }

    private int increase(Map<String, Integer> map, String key) {
        int counter = IntUtils.valueOf(map.get(key), 0);
        map.put(key, ++counter);
        return counter;
    }

    /*
    private List<String> objectTypes(String schema, File ddlfile, String charset) {
        final Set<String> creates = new TreeSet<>();
        LineIterator it = null;
        try {
            String line;
            String[] token;
            it = FileUtils.lineIterator(ddlfile, charset);
            while (it.hasNext()) {
                line = it.nextLine();
                token = split(line);
                if (token != null && token.length > 2 && line.startsWith("CREATE")) {
                    if (StringUtils.isAllUpperCase(token[1])) {
                        if (schema.equals(token[2])) {
                            creates.add(token[1]);
                        }
                    }
                }
            }
        } catch (IOException ex) {
            logger.fatal(ex);
        } finally {
            LineIterator.closeQuietly(it);
        }
        List<String> list = new ArrayList<>();
        list.addAll(creates);
        logger.info("CREATE = " + list);
        return list;
    }

    /**/
    private List<String> tableNames(boolean atenea, String schema, File ddlfile, String charset) {
        final Set<String> tables = new TreeSet<>();
//      LineIterator it = null;
        try (LineIterator it = FileUtils.lineIterator(ddlfile, charset)) {
            String line, name;
            String[] token;
            while (it.hasNext()) {
                line = it.next();
                token = split(line);
                if (token != null && token.length > 3 && line.startsWith("CREATE") && "TABLE".equals(token[1]) && schema.equals(token[2])) {
                    name = StrUtils.disclose(token[3], '"');
                    tables.add(name);
                }
            }
        } catch (IOException ex) {
            logger.fatal(ex);
        } finally {
//          LineIterator.closeQuietly(it);
        }
        if (atenea) {
            tables.addAll(new TreeSet<>(Arrays.asList(CODIGO_PLATAFORMA_ATENEA)));
        }
        List<String> names = new ArrayList<>();
        names.addAll(tables);
        return names;
    }

    private String[] split(String line) {
        if (line == null) {
            return null;
        }
        String str = line.startsWith("CREATE OR REPLACE") ? line.replace("CREATE OR REPLACE", "CREATE") : line;
        return StringUtils.split(str, "( ).,${backslash}"");
    }

    private String sqltype(/*String schema, */String[] token) {
        String sqltype = "void";
        String SQLTYPE;
        if (token.length > 5) {
            for (int i = 0; i < token.length; i++) {
                if ("RETURNS".equals(token[i])) {
                    int j = i + 1;
                    if (j < token.length) {
                        if ("SETOF".equals(token[j])) {
                            /*
                            int k = j + 1;
                            if (k < token.length) {
                                if (schema.equals(token[k])) {
                                    int l = k + 1;
                                    if (l < token.length) {
                                        sqltype = "SETOF_" + token[l];
                                    }
                                } else {
                                    sqltype = "SETOF_" + token[k];
                                }
                            }
                            /**/
                            sqltype = "SETOF";
                        } else {
                            SQLTYPE = token[j].toUpperCase();
//                          sqltype = StringUtils.startsWithAny(SQLTYPE, KEYWORDS) ? token[j] : "";
                            sqltype = ArrayUtils.contains(KEYWORDS, SQLTYPE) ? token[j].toLowerCase() : "";
                        }
                    }
                    break;
                }
            }
        }
        return StrUtils.ascii(sqltype);
    }

    private File directoryOf(String folder) {
        File rootFolder = PropertiesHandler.getRootFolder();
        logger.info("root-folder=" + rootFolder);
        if (StringUtils.isBlank(folder)) {
            folder = rootFolder.getAbsolutePath();
        }
        File dir = new File(folder);
        if (!dir.isAbsolute()) {
            dir = new File(rootFolder, folder);
        }
        if (dir.isDirectory()) {
            logger.info("working-dir=" + dir);
        } else {
            logger.error(dir + " does not exist or it is not a directory; using the root folder instead");
            dir = rootFolder;
            logger.warn("working-dir=" + rootFolder);
        }
        return dir;
    }

    private String directoryOf(List<String> tables, String routine) {
        for (String table : tables) {
            if (routine.startsWith(table)) {
                return table;
            }
        }
        return null;
    }

    private String directoryOf(String routine, List<String> tables) {
        final String[] etcetera = {"inf_", "informe_"};
        if (routine.contains("_") && !StringUtils.startsWithAny(routine.toLowerCase(), etcetera)) {
            String infix = "_" + routine + "_";
            for (String table : tables) {
                if (infix.contains("_" + table + "_")) {
                    infos.add("....${backslash}t" + routine + " contains " + table);
                    return table;
                }
            }
        }
        return null;
    }

    private final String[] replaceable_function_suffixes = {
        "${dollar}ack", "${dollar}after_check", "${dollar}adv", "${dollar}after_value", "${dollar}bck", "${dollar}before_check", "${dollar}bdv", "${dollar}before_value", "${dollar}biz"
    };

    private List<String> replaceWarning(String routine) {
        final List<String> lines = new ArrayList<>();
        if (excluded(routine)) {
            final String dashes = StringUtils.repeat("-", 147);
            lines.add("-- " + dashes);
            lines.add("-- ADVERTENCIA: esta rutina NO debe ser reemplazada");
            lines.add("-- Las rutinas reemplazables tienen uno de estos sufijos: " + Arrays.toString(replaceable_function_suffixes));
            lines.add("-- " + dashes);
        }
        return lines;
    }

    private boolean excluded(String routine) {
        return !included(routine);
    }

    private boolean included(String routine) {
        return StringUtils.endsWithAny(routine, replaceable_function_suffixes);
    }

    private boolean included(boolean adalid, boolean atenea, Set<String> excludedTableNames, String objtype, String objname) {
        return !excluded(adalid, atenea, excludedTableNames, objtype, objname);
    }

    private boolean excluded(boolean adalid, boolean atenea, Set<String> excludedTableNames, String objtype, String objname) {
        String tabname = null;
        switch (objtype) {
            case "SEQUENCE" -> {
                if (adalid || atenea) {
                    if ("x5".equals(objname)) {
                        return true;
                    }
                }
                if (adalid) {
                    tabname = StringUtils.substringBefore(objname, "_sq___");
                }
            }
            case "TABLE" ->
                tabname = objname;
            case "VIEW" -> {
                if (adalid) {
                    if (ArrayUtils.contains(VISTAS_PLATAFORMA_ADALID, objname)) {
                        return true;
                    }
                    tabname
                        = objname.startsWith("seudo_") ? StringUtils.removeStart(objname, "seudo_")
                        : objname.startsWith("super_") ? StringUtils.removeStart(objname, "super_")
                        : null;
                } else if (atenea) {
                    tabname
                        = objname.startsWith("consulta_") && objname.endsWith("_1")
                        ? StringUtils.removeEnd(StringUtils.removeStart(objname, "consulta_"), "_1")
                        : null;
                }
            }
        }
        return tabname != null && excludedTableNames.contains(tabname);
    }

}
