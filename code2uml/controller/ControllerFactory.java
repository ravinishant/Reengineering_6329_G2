/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.controller;

import net.sourceforge.code2uml.controller.Controller;
import net.sourceforge.code2uml.controller.ControllerImpl;

public class ControllerFactory {
    private static Controller instance = new ControllerImpl();

    private ControllerFactory() {
    }

    public static Controller getInstance() {
        return instance;
    }
}

