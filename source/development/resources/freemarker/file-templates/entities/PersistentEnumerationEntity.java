<#assign licenseFirst = "/*">
<#assign licensePrefix = " * ">
<#assign licenseLast = " */">
<#include "../Licenses/license-${project.license}.txt">
<#if package?? && package != "">
package ${package};

</#if>
import adalid.core.*;
import adalid.core.annotations.*;
import adalid.core.enums.*;
import adalid.core.expressions.*;
import adalid.core.interfaces.*;
import adalid.core.parameters.*;
import adalid.core.properties.*;
import java.lang.reflect.Field;

/**
 * ${name} Persistent Entity.
<#if user??>
 *
 * @author ${user}
</#if>
 */
public class ${name} extends AbstractPersistentEnumerationEntity {

    // <editor-fold defaultstate="collapsed" desc="code templates">
    //
    // Available entity class annotation code templates:
    //
    // $abstract	Code template for an abstract class annotation
    // $allocover	Code template for a single allocation override annotation
    // $allocovers	Code template for a set of allocation override annotations
    // $console         Code template for an entity console view annotation
    // $delete          Code template for an entity delete operation annotation
    // $detail          Code template for an entity detail view annotation
    // $disval          Code template for a discriminator value annotation
    // $entity          Code template for an entity class annotation
    // $export          Code template for an entity export operation annotation
    // $inhmap          Code template for an entity inheritance mapping annotation
    // $insert          Code template for an entity insert operation annotation
    // $report          Code template for an entity report operation annotation
    // $search          Code template for an entity reference search annotation
    // $select          Code template for an entity select operation annotation
    // $table           Code template for an entity table view annotation
    // $tree            Code template for an entity tree view annotation
    // $triggers	Code template for an entity triggers annotation
    // $update          Code template for an entity update operation annotation
    //
    // Available property field code templates:
    //
    // $bigdecpro	Code template for a big decimal property field
    // $bigintpro	Code template for a big integer property field
    // $boolpro         Code template for a boolean property field
    // $bytepro         Code template for a byte property field
    // $charbizkey	Code template for a character business key property field
    // $charkey         Code template for a character key property field
    // $charpro         Code template for a character property field
    // $code            Code template for a business key property field
    // $datepro         Code template for a date property field
    // $description	Code template for a description property field
    // $doublepro	Code template for a double property field
    // $entpro          Code template for an entity reference property field
    // $filerefpro	Code template for a file reference property field
    // $floatpro	Code template for a float property field
    // $id              Code template for a long primary key property field
    // $inactive	Code template for an inactive indicator property field
    // $intpro          Code template for a integer property field
    // $longpro         Code template for a long property field
    // $many            Code template for a many-to-one entity reference property field
    // $name            Code template for a name property field
    // $number          Code template for an integer primary key property field
    // $numbizkey	Code template for a numeric business key property field
    // $numkey          Code template for a numeric key property field
    // $one             Code template for a one-to-one entity reference property field
    // $owner           Code template for an owner entity reference property field
    // $parent          Code template for a parent entity reference property field
    // $segment         Code template for a segment entity reference property field
    // $shortpro	Code template for a short property field
    // $stringpro	Code template for a string property field
    // $timepro         Code template for a time property field
    // $stamppro        Code template for a timestamp property field
    // $url             Code template for an URL property field
    // $version         Code template for a version property field
    //
    // Available property annotation code templates:
    //
    // $alloc           Code template for an allocation annotation
    // $base            Code template for a base field annotation
    // $bigdec          Code template for a big decimal field annotation
    // $casting         Code template for a casting field annotation
    // $column          Code template for a column field annotation
    // $discol          Code template for a discriminator column annotation
    // $extension	Code template for an extension annotation
    // $fileref         Code template for a file reference annotation
    // $property	Code template for a property field annotation
    // $string          Code template for a string field annotation
    // $time            Code template for a time field annotation
    // $stamp           Code template for a timestamp field annotation
    // $unique          Code template for a unique key annotation
    //
    // Available entity key field code templates:
    //
    // $key             Code template for a key field
    //
    // Available entity tab field code templates:
    //
    // $tab             Code template for a tab field
    //
    // Available entity instance field code templates:
    //
    // $instance	Code template for a instance field
    //
    // Available entity trigger field code templates:
    //
    // $trigger         Code template for a trigger field
    //
    // Available entity expression field code templates:
    //
    // $checkx          Code template for a check expression field
    // $segmentx	Code template for a segment expression field
    // $statex          Code template for a state expression field
    //
    // Available operation class code templates:
    //
    // $exportop        Code template for an export operation
    // $operation       Code template for an operation class annotation
    // $procedure       Code template for a procedure operation
    // $process         Code template for a process operation
    // $reportop        Code template for a report operation
    //
    // Available parameter field code templates:
    //
    // $bigdecpar	Code template for a big decimal parameter field
    // $bigintpar	Code template for a big integer parameter field
    // $boolpar         Code template for a boolean parameter field
    // $bytepar         Code template for a byte parameter field
    // $charpar         Code template for a character parameter field
    // $datepar         Code template for a date parameter field
    // $doublepar	Code template for a double parameter field
    // $entpar          Code template for an entity reference parameter field
    // $filerefpar	Code template for a file reference parameter field
    // $floatpar	Code template for a float parameter field
    // $insrefpar	Code template for an instance reference parameter field
    // $intpar          Code template for a integer parameter field
    // $longpar         Code template for a long parameter field
    // $shortpar	Code template for a short parameter field
    // $stringpar	Code template for a string parameter field
    // $timepar         Code template for a time parameter field
    // $stamppar        Code template for a timestamp parameter field
    //
    // Available parameter annotation code templates:
    //
    // $alloc           Code template for an allocation annotation
    // $bigdec          Code template for a big decimal field annotation
    // $fileref         Code template for a file reference annotation
    // $insref          Code template for an instance reference parameter annotation
    // $parameter	Code template for a parameter field annotation
    // $string          Code template for a string field annotation
    // $time            Code template for a time field annotation
    // $stamp           Code template for a timestamp field annotation
    //
    // Available operation transition field code templates:
    //
    // $transition	Code template for a transition field
    //
    // </editor-fold>
/**/
    // <editor-fold defaultstate="collapsed" desc="class constructors">
    @Deprecated()
    private ${name}() {
        this(null, null);
    }

    public ${name}(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
    }

    @PrimaryKey
    public IntegerProperty numero;

    @BusinessKey
    public StringProperty codigo;

    @Override
    protected void settleProperties() {
        super.settleProperties();
    }

    public Instance A;

    public Instance B;

    public Instance C;

    @Override
    protected void settleInstances() {
        super.settleInstances();
    }

}
