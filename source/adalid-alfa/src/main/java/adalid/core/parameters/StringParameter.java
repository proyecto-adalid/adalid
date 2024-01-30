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
package adalid.core.parameters;

import adalid.core.annotations.*;
import adalid.core.data.types.*;
import adalid.core.enums.*;
import adalid.core.interfaces.*;
import adalid.core.properties.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
public class StringParameter extends StringData implements AlphanumericParameter {

    @Override
    protected List<Class<? extends Annotation>> getValidFieldAnnotations() {
        List<Class<? extends Annotation>> valid = super.getValidFieldAnnotations();
        valid.add(FileReference.class);
        valid.add(ParameterField.class);
        valid.add(StringField.class);
        return valid;
    }

    /**
     * @return the file-upload auto-start indicator
     */
    @Override // Implements method from: FileReference (StringProperty/StringParameter)
    public boolean isFileUploadAutoStart() {
        Boolean fileUploadAutoStart = fileUploadAutoStart();
        if (fileUploadAutoStart == null) {
            StringProperty linkedProperty = getParameterLinkedProperty();
            if (linkedProperty != null) {
                return linkedProperty.isFileUploadAutoStart();
            }
        }
        return super.isFileUploadAutoStart();
    }

    /**
     * @return the file-upload virus-scan indicator
     */
    @Override // Implements method from: FileReference (StringProperty/StringParameter)
    public boolean isFileUploadVirusScan() {
        Boolean fileUploadVirusScan = fileUploadVirusScan();
        if (fileUploadVirusScan == null) {
            StringProperty linkedProperty = getParameterLinkedProperty();
            if (linkedProperty != null) {
                return linkedProperty.isFileUploadVirusScan();
            }
        }
        return super.isFileUploadVirusScan();
    }

    /**
     * @return the file-upload file limit
     */
    @Override // Implements method from: FileReference (StringProperty/StringParameter)
    public int getFileUploadFileLimit() {
        int fileUploadFileLimit = fileUploadFileLimit();
        if (fileUploadFileLimit < 0) {
            StringProperty linkedProperty = getParameterLinkedProperty();
            if (linkedProperty != null) {
                return linkedProperty.getFileUploadFileLimit();
            }
        }
        return super.getFileUploadFileLimit();
    }

    /**
     * @return the file-upload file limit
     */
    @Override // Implements method from: FileReference (StringProperty/StringParameter)
    public int getFileUploadUndoLimit() {
        int fileUploadUndoLimit = fileUploadUndoLimit();
        if (fileUploadUndoLimit < 0) {
            StringProperty linkedProperty = getParameterLinkedProperty();
            if (linkedProperty != null) {
                return linkedProperty.getFileUploadUndoLimit();
            }
        }
        return super.getFileUploadUndoLimit();
    }

    /**
     * @return the max input file size
     */
    @Override // Implements method from: FileReference (StringProperty/StringParameter)
    public int getMaxInputFileSize() {
        int maxInputFileSize = maxInputFileSize();
        if (maxInputFileSize < 0) {
            StringProperty linkedProperty = getParameterLinkedProperty();
            if (linkedProperty != null) {
                return linkedProperty.getMaxInputFileSize();
            }
        }
        return super.getMaxInputFileSize();
    }

    /**
     * @return the valid input file types
     */
    @Override // Implements method from: FileReference (StringProperty/StringParameter)
    public MimeType[] getValidInputFileTypes() {
        MimeType[] validInputFileTypes = super.getValidInputFileTypes();
        if (validInputFileTypes == null || validInputFileTypes.length == 0) {
            StringProperty linkedProperty = getParameterLinkedProperty();
            if (linkedProperty != null) {
                return linkedProperty.getValidInputFileTypes();
            }
        }
        return validInputFileTypes;
    }

    /**
     * @return the valid input file pattern
     */
    @Override // Implements method from: FileReference (StringProperty/StringParameter)
    public Pattern getValidInputFilePattern() {
        Pattern _validInputFilePattern = super.getValidInputFilePattern();
        if (_validInputFilePattern == null) {
            StringProperty linkedProperty = getParameterLinkedProperty();
            if (linkedProperty != null) {
                return linkedProperty.getValidInputFilePattern();
            }
        }
        return _validInputFilePattern;
    }

    /**
     * @return the upload storage option
     */
    @Override // Implements method from: FileReference (StringProperty/StringParameter)
    public UploadStorageOption getUploadStorageOption() {
        UploadStorageOption uploadStorageOption = super.getUploadStorageOption();
        if (uploadStorageOption == null || uploadStorageOption.equals(UploadStorageOption.UNSPECIFIED)) {
            StringProperty linkedProperty = getParameterLinkedProperty();
            if (linkedProperty != null) {
                return linkedProperty.getUploadStorageOption();
            }
        }
        return uploadStorageOption;
    }

    /**
     * @return the blob field name
     */
    @Override // Implements method from: FileReference (StringProperty/StringParameter)
    public String getPathTemplate() {
        String pathTemplate = super.getPathTemplate();
        if (StringUtils.isBlank(pathTemplate)) {
            StringProperty linkedProperty = getParameterLinkedProperty();
            if (linkedProperty != null) {
                return linkedProperty.getPathTemplate();
            }
        }
        return pathTemplate;
    }

    /**
     * @return the blob field name
     */
    @Override // Implements method from: FileReference (StringProperty/StringParameter)
    public String getBlobFieldName() {
        String blobFieldName = super.getBlobFieldName();
        if (StringUtils.isBlank(blobFieldName)) {
            StringProperty linkedProperty = getParameterLinkedProperty();
            if (linkedProperty != null) {
                return linkedProperty.getBlobFieldName();
            }
        }
        return blobFieldName;
    }

    /**
     * @return the blob field
     */
    @Override // Implements method from: FileReference (StringProperty/StringParameter)
    public Field getBlobField() {
        Field blobField = super.getBlobField();
        if (blobField == null) {
            StringProperty linkedProperty = getParameterLinkedProperty();
            if (linkedProperty != null) {
                return linkedProperty.getBlobField();
            }
        }
        return blobField;
    }

    /**
     * @return the blob property
     */
    @Override // Implements method from: FileReference (StringProperty/StringParameter)
    public Property getBlobProperty() {
        Property blobProperty = super.getBlobProperty();
        if (blobProperty == null) {
            StringProperty linkedProperty = getParameterLinkedProperty();
            if (linkedProperty != null) {
                return linkedProperty.getBlobProperty();
            }
        }
        return blobProperty;
    }

    /**
     * @return the join field name
     */
    @Override // Implements method from: FileReference (StringProperty/StringParameter)
    public String getJoinFieldName() {
        String joinFieldName = super.getJoinFieldName();
        if (StringUtils.isBlank(joinFieldName)) {
            StringProperty linkedProperty = getParameterLinkedProperty();
            if (linkedProperty != null) {
                return linkedProperty.getJoinFieldName();
            }
        }
        return joinFieldName;
    }

    /**
     * @return the join field
     */
    @Override // Implements method from: FileReference (StringProperty/StringParameter)
    public Field getJoinField() {
        Field joinField = super.getJoinField();
        if (joinField == null) {
            StringProperty linkedProperty = getParameterLinkedProperty();
            if (linkedProperty != null) {
                return linkedProperty.getJoinField();
            }
        }
        return joinField;
    }

    /**
     * @return the join property
     */
    @Override // Implements method from: FileReference (StringProperty/StringParameter)
    public Property getJoinProperty() {
        Property joinProperty = super.getJoinProperty();
        if (joinProperty == null) {
            StringProperty linkedProperty = getParameterLinkedProperty();
            if (linkedProperty != null) {
                return linkedProperty.getJoinProperty();
            }
        }
        return joinProperty;
    }

    /**
     * @return the load field name
     */
    @Override // Implements method from: FileReference (StringProperty/StringParameter)
    public String getLoadFieldName() {
        String loadFieldName = super.getLoadFieldName();
        if (StringUtils.isBlank(loadFieldName)) {
            StringProperty linkedProperty = getParameterLinkedProperty();
            if (linkedProperty != null) {
                return linkedProperty.getLoadFieldName();
            }
        }
        return loadFieldName;
    }

    /**
     * @return the load field
     */
    @Override // Implements method from: FileReference (StringProperty/StringParameter)
    public Field getLoadField() {
        Field loadField = super.getLoadField();
        if (loadField == null) {
            StringProperty linkedProperty = getParameterLinkedProperty();
            if (linkedProperty != null) {
                return linkedProperty.getLoadField();
            }
        }
        return loadField;
    }

    /**
     * @return the load property
     */
    @Override // Implements method from: FileReference (StringProperty/StringParameter)
    public Property getLoadProperty() {
        Property loadProperty = super.getLoadProperty();
        if (loadProperty == null) {
            StringProperty linkedProperty = getParameterLinkedProperty();
            if (linkedProperty != null) {
                return linkedProperty.getLoadProperty();
            }
        }
        return loadProperty;
    }

    /**
     * @return the text field name
     */
    @Override // Implements method from: FileReference (StringProperty/StringParameter)
    public String getTextFieldName() {
        String textFieldName = super.getTextFieldName();
        if (StringUtils.isBlank(textFieldName)) {
            StringProperty linkedProperty = getParameterLinkedProperty();
            if (linkedProperty != null) {
                return linkedProperty.getTextFieldName();
            }
        }
        return textFieldName;
    }

    /**
     * @return the text field
     */
    @Override // Implements method from: FileReference (StringProperty/StringParameter)
    public Field getTextField() {
        Field textField = super.getTextField();
        if (textField == null) {
            StringProperty linkedProperty = getParameterLinkedProperty();
            if (linkedProperty != null) {
                return linkedProperty.getTextField();
            }
        }
        return textField;
    }

    /**
     * @return the text property
     */
    @Override // Implements method from: FileReference (StringProperty/StringParameter)
    public Property getTextProperty() {
        Property textProperty = super.getTextProperty();
        if (textProperty == null) {
            StringProperty linkedProperty = getParameterLinkedProperty();
            if (linkedProperty != null) {
                return linkedProperty.getTextProperty();
            }
        }
        return textProperty;
    }

    /**
     * @return the updateable file reference option
     */
    @Override // Implements method from: FileReference (StringProperty/StringParameter)
    public Kleenean getUpdateableFileReference() {
        Kleenean updateableFileReference = super.getUpdateableFileReference();
        if (updateableFileReference == null || updateableFileReference.equals(Kleenean.UNSPECIFIED)) {
            StringProperty linkedProperty = getParameterLinkedProperty();
            if (linkedProperty != null) {
                return linkedProperty.getUpdateableFileReference();
            }
        }
        return updateableFileReference;
    }

    private StringProperty getParameterLinkedProperty() {
        Property linkedProperty = getLinkedProperty();
        return linkedProperty instanceof StringProperty ? (StringProperty) linkedProperty : null;
    }

}
