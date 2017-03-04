/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.graph;

import java.awt.Color;
import java.awt.Font;
import java.io.Serializable;
import net.sourceforge.code2uml.graph.EdgeType;

public class ConstructionHints
implements Serializable {
    private boolean fieldsVisible = true;
    private boolean methodsVisible = true;
    private boolean enumsVisible = true;
    private boolean privateVisible = true;
    private boolean packageVisible = true;
    private boolean protectedVisible = true;
    private boolean publicVisible = true;
    private boolean argumentsVisible = true;
    private boolean staticVisible = true;
    private boolean finalVisible = true;
    private boolean realizationDrawn = true;
    private boolean generalizationDrawn = true;
    private boolean hasADrawn = false;
    private boolean nonpublicUnitsVisible = true;
    private EdgeType hasAType = EdgeType.AGGREGATION;
    private Font font = new Font("Dialog", 0, 12);
    private Color backColor = Color.WHITE;
    private String layoutName = "rectangular";
    private String nodeName = "basicNodeComponent";

    public boolean isFieldsVisible() {
        return this.fieldsVisible;
    }

    public void setFieldsVisible(boolean fieldsVisible) {
        this.fieldsVisible = fieldsVisible;
    }

    public boolean isMethodsVisible() {
        return this.methodsVisible;
    }

    public void setMethodsVisible(boolean methodsVisible) {
        this.methodsVisible = methodsVisible;
    }

    public boolean isEnumsVisible() {
        return this.enumsVisible;
    }

    public void setEnumsVisible(boolean enumsVisible) {
        this.enumsVisible = enumsVisible;
    }

    public boolean isPrivateVisible() {
        return this.privateVisible;
    }

    public void setPrivateVisible(boolean privateVisible) {
        this.privateVisible = privateVisible;
    }

    public boolean isPackageVisible() {
        return this.packageVisible;
    }

    public void setPackageVisible(boolean packageVisible) {
        this.packageVisible = packageVisible;
    }

    public boolean isProtectedVisible() {
        return this.protectedVisible;
    }

    public void setProtectedVisible(boolean protectedVisible) {
        this.protectedVisible = protectedVisible;
    }

    public boolean isPublicVisible() {
        return this.publicVisible;
    }

    public void setPublicVisible(boolean publicVisible) {
        this.publicVisible = publicVisible;
    }

    public boolean isArgumentsVisible() {
        return this.argumentsVisible;
    }

    public void setArgumentsVisible(boolean argumentsVisible) {
        this.argumentsVisible = argumentsVisible;
    }

    public Font getFont() {
        return this.font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public Color getBackColor() {
        return this.backColor;
    }

    public void setBackColor(Color backColor) {
        this.backColor = backColor;
    }

    public boolean isStaticVisible() {
        return this.staticVisible;
    }

    public void setStaticVisible(boolean staticVisible) {
        this.staticVisible = staticVisible;
    }

    public boolean isFinalVisible() {
        return this.finalVisible;
    }

    public void setFinalVisible(boolean finalVisible) {
        this.finalVisible = finalVisible;
    }

    public boolean isNonpublicUnitsVisible() {
        return this.nonpublicUnitsVisible;
    }

    public void setNonpublicUnitsVisible(boolean nonpublicUnitsVisible) {
        this.nonpublicUnitsVisible = nonpublicUnitsVisible;
    }

    public String getLayoutName() {
        return this.layoutName;
    }

    public void setLayoutName(String layoutName) {
        this.layoutName = layoutName;
    }

    public String getNodeName() {
        return this.nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public boolean isRealizationDrawn() {
        return this.realizationDrawn;
    }

    public void setRealizationDrawn(boolean realizationDrawn) {
        this.realizationDrawn = realizationDrawn;
    }

    public boolean isGeneralizationDrawn() {
        return this.generalizationDrawn;
    }

    public void setGeneralizationDrawn(boolean generalizationDrawn) {
        this.generalizationDrawn = generalizationDrawn;
    }

    public boolean isHasADrawn() {
        return this.hasADrawn;
    }

    public void setHasADrawn(boolean hasADrawn) {
        this.hasADrawn = hasADrawn;
    }

    public EdgeType getHasAType() {
        return this.hasAType;
    }

    public void setHasAType(EdgeType hasAType) {
        if (hasAType != EdgeType.AGGREGATION && hasAType != EdgeType.COMPOSITION) {
            throw new IllegalArgumentException();
        }
        this.hasAType = hasAType;
    }
}

