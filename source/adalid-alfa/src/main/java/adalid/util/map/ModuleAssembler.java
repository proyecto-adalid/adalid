package adalid.util.map;

import java.util.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class ModuleAssembler {

    private static final Logger logger = Logger.getLogger(ModuleAssembler.class);

    public static void main(String[] args) {
        ModuleAssembler assembler = new ModuleAssembler();
        // add tables to modules
        assembler.addModuleTables("CustomerModule", "Customer", "CustomerAddress");
        assembler.addChildlessModuleTables("CustomerModule", "CustomerOrder");
        assembler.addModuleTables("OrdersModule", "CustomerOrder", "CustomerOrderLine");
        // add relations
        assembler.addTableRelations("Customer", "State", "Gender", "Product");
        assembler.addTableRelations("CustomerOrder", "Customer");
        assembler.addTableRelations("CustomerAddress", "Customer", "State");
        assembler.addTableRelations("CustomerPhone", "Customer");
        assembler.addTableRelations("GenderDetail", "Gender");
        assembler.addTableRelations("CustomerMobilePhone", "CustomerPhone");
        assembler.addTableRelations("CustomerOrderLine", "CustomerOrder", "Product");
        assembler.addTableRelations("CustomerOrderLineDetail", "CustomerOrderLine");
        // add other tables
        assembler.addTables("Profession");
        // build
        assembler.buildModules();
    }

    public boolean printTablesPerModule, printModulesPerTable, printManyToOneRelations, printOneToManyRelations, printImmutableModules, printTables;

    public ModuleAssembler() {
        printTablesPerModule = true;
        printModulesPerTable = true;
        printManyToOneRelations = true;
        printOneToManyRelations = true;
        printImmutableModules = true;
        printTables = true;
    }

    /**
     * Map of tables per module
     */
    private final Map<String, Set<String>> tablesPerModule = new TreeMap<>();

    /**
     * Map of modules per table
     */
    private final Map<String, Set<String>> modulesPerTable = new TreeMap<>();

    /**
     * Map of tables per module that should be ignored when adding tables with many-to-one relationships to the module
     */
    private final Map<String, Set<String>> childlessTablesPerModule = new TreeMap<>();

    /**
     * Map of many-to-one relations between tables
     */
    private final Map<String, Set<String>> manyToOne = new TreeMap<>();

    /**
     * Map of one-to-many relations between tables
     */
    private final Map<String, Set<String>> oneToMany = new TreeMap<>();

    /**
     * Set of immutable modules
     */
    private final Set<String> immutableModules = new TreeSet<>();

    /**
     * Set of all tables
     */
    private final Set<String> tables = new TreeSet<>();

    /**
     * Set of tables that do not belong to any module
     */
    private final Set<String> tablesWithoutModule = new TreeSet<>();

    /**
     * Set of tables that belong to just one module
     */
    private final Set<String> tablesInJustOneModule = new TreeSet<>();

    /**
     * Set of tables that belong to more than one module
     */
    private final Set<String> tablesInMoreThanOneModule = new TreeSet<>();

    public void addModuleTables(String moduleName, String... tableNames) {
        addModuleTables(moduleName, Arrays.asList(tableNames));
    }

    public void addModuleTables(String moduleName, Collection<String> tableNames) {
        // add tables
        tables.addAll(tableNames);
        // add tables per module
        Set<String> tpm = tablesPerModule.get(moduleName);
        if (tpm == null) {
            tpm = new TreeSet<>();
        }
        tpm.addAll(tableNames);
        tablesPerModule.put(moduleName, tpm);
        // add modules per table
        for (String tableName : tableNames) {
            Set<String> mpt = modulesPerTable.get(tableName);
            if (mpt == null) {
                mpt = new TreeSet<>();
            }
            mpt.add(moduleName);
            modulesPerTable.put(tableName, mpt);
        }
    }

    public void addChildlessModuleTables(String moduleName, String... tableNames) {
        addChildlessModuleTables(moduleName, Arrays.asList(tableNames));
    }

    public void addChildlessModuleTables(String moduleName, Collection<String> tableNames) {
        // add tables
        addModuleTables(moduleName, tableNames);
        // add tables
        Set<String> tpm = childlessTablesPerModule.get(moduleName);
        if (tpm == null) {
            tpm = new TreeSet<>();
        }
        tpm.addAll(tableNames);
        childlessTablesPerModule.put(moduleName, tpm);
    }

    public void addTableRelations(String tableName, String... referencedTableNames) {
        addTableRelations(tableName, Arrays.asList(referencedTableNames));
    }

    public void addTableRelations(String tableName, Collection<String> referencedTableNames) {
        // add tables
        tables.add(tableName);
        tables.addAll(referencedTableNames);
        // add one to many relations
        Set<String> mto = manyToOne.get(tableName);
        if (mto == null) {
            mto = new TreeSet<>();
        }
        mto.addAll(referencedTableNames);
        manyToOne.put(tableName, mto);
        // add many to one relations
        for (String referencedTableName : referencedTableNames) {
            Set<String> otm = oneToMany.get(referencedTableName);
            if (otm == null) {
                otm = new TreeSet<>();
            }
            otm.add(tableName);
            oneToMany.put(referencedTableName, otm);
        }
    }

    public void addImmutableModules(String... moduleNames) {
        addImmutableModules(Arrays.asList(moduleNames));
    }

    public void addImmutableModules(Collection<String> moduleNames) {
        immutableModules.addAll(moduleNames);
    }

    public void addTables(String... tableNames) {
        addTables(Arrays.asList(tableNames));
    }

    public void addTables(Collection<String> tableNames) {
        tables.addAll(tableNames);
    }

    public String orphanTablesModuleName;

    public void buildModules() {
        setupModulesPerTable();
        printInputCollections();
        buildOtherCollections("");
        int i = 0;
        boolean added;
        do {
            i++;
            added = false;
            added |= phase1(i);
            added |= phase2(i);
        } while (added);
        String moduleName = StringUtils.defaultIfBlank(orphanTablesModuleName, "zymurgy");
        if (tablesWithoutModule.isEmpty()) {
            logger.info("The module " + moduleName + " was not defined because there are no orphan tables");
        } else {
            // Create the orphan tables module to incorporate the remaining tables without a module
            addModuleTables(moduleName, tablesWithoutModule);
            phaseEpilogue(true);
            logger.info("The module " + moduleName + " contains the orphan tables");
        }
        logCircularReferences();
    }

    private void setupModulesPerTable() {
        Set<String> mpt;
        for (String tableName : tables) {
            mpt = modulesPerTable.get(tableName);
            if (mpt == null) {
                modulesPerTable.put(tableName, new TreeSet<>());
            }
        }
    }

    private void printInputCollections() {
        List<String> ikq = new ArrayList<>(), vdu = new ArrayList<>();
        Set<String> childless;
        if (printTablesPerModule) {
            Set<String> tpm;
            logger.info("Tables per Module");
            for (String moduleName : tablesPerModule.keySet()) {
                tpm = tablesPerModule.get(moduleName);
                if (tpm == null || tpm.isEmpty()) {
                    continue;
                }
                ikq.clear();
                vdu.clear();
                childless = childlessTablesPerModule.get(moduleName);
                for (String tableName : tpm) {
                    if (childless == null || !childless.contains(tableName)) {
                        ikq.add(tableName);
                    } else {
                        vdu.add(tableName);
                    }
                }
                if (vdu.isEmpty()) {
                } else {
                    ikq.add("(" + StringUtils.join(vdu, ", ") + ")");
                }
                logger.info("\t" + moduleName);
                logger.info("\t\t[" + StringUtils.join(ikq, ", ") + "]");
            }
        }
        if (printModulesPerTable) {
            Set<String> mpt;
            logger.info("Modules per Table");
            for (String tableName : modulesPerTable.keySet()) {
                mpt = modulesPerTable.get(tableName);
                if (mpt == null || mpt.isEmpty()) {
                    continue;
                }
                ikq.clear();
                vdu.clear();
                for (String moduleName : mpt) {
                    childless = childlessTablesPerModule.get(moduleName);
                    if (childless == null || !childless.contains(tableName)) {
                        ikq.add(moduleName);
                    } else {
                        vdu.add(moduleName);
                    }
                }
                if (vdu.isEmpty()) {
                } else {
                    ikq.add("(" + StringUtils.join(vdu, ", ") + ")");
                }
                logger.info("\t" + tableName);
                logger.info("\t\t[" + StringUtils.join(ikq, ", ") + "]");
            }
        }
        if (printManyToOneRelations) {
            logger.info("Many-to-One relations");
            for (String tableName : manyToOne.keySet()) {
                logger.info("\t" + tableName);
                logger.info("\t\t" + manyToOne.get(tableName));
            }
        }
        if (printOneToManyRelations) {
            logger.info("One-to-Many relations");
            for (String referencedTableName : oneToMany.keySet()) {
                logger.info("\t" + referencedTableName);
                logger.info("\t\t" + oneToMany.get(referencedTableName));
            }
        }
        if (printImmutableModules) {
            Set<String> mutableModules = new TreeSet<>(tablesPerModule.keySet());
            mutableModules.removeAll(immutableModules);
            logger.info("Mutable Modules");
            logger.info("\t" + mutableModules);
            logger.info("Immutable Modules");
            logger.info("\t" + immutableModules);
        }
        if (printTables) {
            logger.info("Tables");
            logger.info("\t" + tables);
        }
    }

    private void buildOtherCollections(String tab) {
        // print the modules per table map and fill the tables without module, tables in just one module and tables in more than one module sets
        tablesWithoutModule.clear();
        tablesInJustOneModule.clear();
        tablesInMoreThanOneModule.clear();
        Set<String> mpt;
        for (String tableName : modulesPerTable.keySet()) {
            mpt = modulesPerTable.get(tableName);
            if (mpt.isEmpty()) {
                tablesWithoutModule.add(tableName);
            } else if (mpt.size() == 1) {
                tablesInJustOneModule.add(tableName);
            } else {
                tablesInMoreThanOneModule.add(tableName);
            }
        }
        // print tables without module, tables in just one module and tables in more than one module sets
        logger.info(tab + "Tables in more than one module");
        logger.info(tab + "\t" + tablesInMoreThanOneModule);
        logger.info(tab + "Tables in just one module");
        logger.info(tab + "\t" + tablesInJustOneModule);
        logger.info(tab + "Tables without module");
        logger.info(tab + "\t" + tablesWithoutModule);
    }

    /**
     * Phase 1: Add tables without modules to those modules that contain tables to which they refer
     */
    private boolean phase1(int i) {
        logger.info("Iteration " + i + ", Phase 1");
        boolean phase = false;
        boolean added;
        int j = 1;
        int k = 0;
        String[] tablesWithoutModuleArray;
        Set<String> mto, mpt;
        do {
            k++;
            added = false;
            // copy set to array to prevent java.util.ConcurrentModificationException
            tablesWithoutModuleArray = tablesWithoutModule.toArray(String[]::new);
            for (String tableName : tablesWithoutModuleArray) {
                mto = manyToOne.get(tableName);
                if (mto != null) {
                    for (String referencedTableName : mto) {
                        mpt = modulesPerTable.get(referencedTableName);
                        if (mpt != null) {
                            for (String moduleName : mpt) {
                                if (immutableModules.contains(moduleName) || childlessTablesPerModule.containsKey(tableName)) {
                                    continue;
                                }
                                add(tableName, moduleName);
                                logger.info(i + "." + j + "." + k + "\t" + tableName + " added to " + moduleName + " because it references " + referencedTableName);
                                added = true;
                                phase = true;
                            }
                        }
                    }
                }
            }
        } while (added);
        phaseEpilogue(phase);
        return phase;
    }

    private void add(String tableName, String moduleName) {
        Set<String> tpm = tablesPerModule.get(moduleName);
        tpm.add(tableName);
        Set<String> mpt = modulesPerTable.get(tableName);
        mpt.add(moduleName);
        tablesWithoutModule.remove(tableName);
    }

    /**
     * Phase 2: Add tables without modules to the only module that contains tables that reference them
     */
    private boolean phase2(int i) {
        logger.info("Iteration " + i + ", Phase 2");
        boolean phase = false;
        boolean added;
        int j = 2;
        int k = 0;
        String[] tablesWithoutModuleArray;
        String moduleName;
        List<String> rml;
        do {
            k++;
            added = false;
            // copy set to array to prevent java.util.ConcurrentModificationException
            tablesWithoutModuleArray = tablesWithoutModule.toArray(String[]::new);
            for (String tableName : tablesWithoutModuleArray) {
                rml = new ArrayList<>(referencingModules(tableName));
                if (rml.size() == 1) {
                    moduleName = rml.get(0);
                    if (immutableModules.contains(moduleName)) {
                        continue;
                    }
                    add(tableName, moduleName);
                    logger.info(i + "." + j + "." + k + "\t" + tableName + " added to " + moduleName + " because it is referenced only from tables in that module");
                    added = true;
                    phase = true;
                }
            }
        } while (added);
        phaseEpilogue(phase);
        return phase;
    }

    /**
     * @param tableName table name
     * @return set of modules that contain at least one table that references the table
     */
    private Set<String> referencingModules(String tableName) {
        Set<String> set = new TreeSet<>();
        // get the set of tables referencing the table
        Set<String> otm = oneToMany.get(tableName);
        if (otm != null) {
            for (String referencingTable : otm) {
                // get the modules containing the referencin table
                set.addAll(modulesPerTable.get(referencingTable));
            }
        }
        // return set of modules that contain at least one table that references the table
//      logger.info(tableName + " is referenced from tables in: " + set);
        return set;
    }

    private void phaseEpilogue(boolean phase) {
        if (phase) {
            logger.info("\tModules");
            for (String key : tablesPerModule.keySet()) {
                logger.info("\t\t" + key);
                logger.info("\t\t\t" + tablesPerModule.get(key));
            }
            buildOtherCollections("\t");
        } else {
            logger.info("\tNo tables were added to any module in this iteration");
        }
    }

    private class TableAtModule {

        private final String table, module;

        private TableAtModule(String table, String module) {
            this.table = table;
            this.module = module;
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof TableAtModule that && this.table.equals(that.table) && this.module.equals(that.module);
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 53 * hash + Objects.hashCode(this.table);
            hash = 53 * hash + Objects.hashCode(this.module);
            return hash;
        }

        @Override
        public String toString() {
            return table + " @ " + module;
        }

    }

    private class CrossModuleRelation {

        private final TableAtModule source, target;

        private CrossModuleRelation(TableAtModule source, TableAtModule target) {
            this.source = source;
            this.target = target;
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof CrossModuleRelation that && this.source.equals(that.source) && this.target.equals(that.target);
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 97 * hash + Objects.hashCode(this.source);
            hash = 97 * hash + Objects.hashCode(this.target);
            return hash;
        }

        @Override
        public String toString() {
            return source + " --> " + target;
        }

    }

    private class CrossModulePath {

        private final List<TableAtModule> nodes = new ArrayList<>();

        private boolean circular;

        private boolean cyclical;

        private CrossModulePath(CrossModuleRelation source, CrossModuleRelation target) {
            nodes.add(source.source);
            nodes.add(source.target);
            init(target);
        }

        private CrossModulePath(CrossModulePath path, CrossModuleRelation target) {
            nodes.addAll(path.nodes);
            init(target);
        }

        private void init(CrossModuleRelation target) {
            circular = nodes.get(0).module.equals(target.target.module);
            cyclical = nodes.contains(target.target);
            nodes.add(target.target);
        }

        @Override
        public String toString() {
            return (circular ? "CIRCULAR" : "") + " " + (cyclical ? "CYCLICAL" : "") + " " + nodes.size() + "\n\t" + nodes;
        }

    }

    List<CrossModuleRelation> crossModuleRelations = new ArrayList<>();

    private final List<CrossModulePath> crossModulePaths = new ArrayList<>();

    /**
     * Check all modules and log the circular and cyclical references
     */
    private void logCircularReferences() {
        addCrossModuleRelations();
        addCrossModulePaths();
        logCrossModulePaths();
    }

    private List<CrossModuleRelation> addCrossModuleRelations() {
        Set<String> tpm, mto, mpt;
        TableAtModule source, target;
        for (String moduleName : tablesPerModule.keySet()) {
            tpm = tablesPerModule.get(moduleName);
            for (String tableName : tpm) {
                mto = manyToOne.get(tableName);
                if (mto == null || mto.isEmpty()) {
                    continue;
                }
                source = new TableAtModule(tableName, moduleName);
                for (String referencedTableName : mto) {
                    if (tpm.contains(referencedTableName)) {
                        continue; // references to tables in the same module are omitted
                    }
                    mpt = modulesPerTable.get(referencedTableName);
                    if (mpt == null || mpt.isEmpty()) {
                        continue; // references to tables without module are omitted
                    }
                    for (String referencedModuleName : mpt) {
                        target = new TableAtModule(referencedTableName, referencedModuleName);
                        crossModuleRelations.add(new CrossModuleRelation(source, target));
                    }
                }
            }
        }
        return crossModuleRelations;
    }

    private void addCrossModulePaths() {
        for (CrossModuleRelation source : crossModuleRelations) {
            for (CrossModuleRelation target : crossModuleRelations) {
                if (source.target.equals(target.source)) {
                    CrossModulePath cmp = new CrossModulePath(source, target);
                    crossModulePaths.add(cmp);
                    addCrossModulePaths(cmp);
                }
            }
        }
    }

    private void addCrossModulePaths(CrossModulePath cmp1) {
        if (cmp1.circular || cmp1.cyclical) {
            return;
        }
        CrossModulePath cmp2;
        TableAtModule lastTableAtModule = cmp1.nodes.get(cmp1.nodes.size() - 1);
        for (CrossModuleRelation target : crossModuleRelations) {
            if (lastTableAtModule.equals(target.source)) {
                cmp2 = new CrossModulePath(cmp1, target);
                crossModulePaths.add(cmp2);
                addCrossModulePaths(cmp2);
            }
        }
    }

    private void logCrossModulePaths() {
        int i = 0;
        for (CrossModulePath cmp : crossModulePaths) {
            if (cmp.circular || cmp.cyclical) {
                logger.warn("CMP#" + ++i + "\t" + cmp);
            }
        }
        if (i == 0) {
            logger.info("No cyclic or circular references between modules were found");
        }
    }

}
