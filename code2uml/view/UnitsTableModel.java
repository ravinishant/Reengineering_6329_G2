/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.view;

import javax.swing.table.DefaultTableModel;
import net.sourceforge.code2uml.unitdata.UnitInfo;

class UnitsTableModel
extends DefaultTableModel {
    private static String[] columnHeaders = new String[]{"name", "include?"};

    public UnitsTableModel() {
        super(columnHeaders, 0);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == this.getColumnCount() - 1) {
            return Boolean.class;
        }
        return String.class;
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return column == this.getColumnCount() - 1;
    }

    public void addRow(UnitInfo unit) {
        Object[] arrobject = new Object[2];
        arrobject[0] = unit.getSimpleName();
        arrobject[1] = !unit.getSimpleName().contains("$");
        super.addRow(arrobject);
    }
}

