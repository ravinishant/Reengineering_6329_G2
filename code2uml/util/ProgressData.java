/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.util;

public class ProgressData {
    private double progress;
    private String message;

    public ProgressData(double progress, String message) {
        if (progress < 0.0) {
            progress = 0.0;
        } else if (progress > 100.0) {
            progress = 100.0;
        }
        this.progress = progress;
        this.message = message;
    }

    public ProgressData(double progress) {
        if (progress < 0.0) {
            progress = 0.0;
        } else if (progress > 100.0) {
            progress = 100.0;
        }
        this.progress = progress;
    }

    public double getProgress() {
        return this.progress;
    }

    public String getMessage() {
        return this.message;
    }
}

