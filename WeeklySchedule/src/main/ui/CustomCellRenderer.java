package ui;

import javafx.scene.control.TableCell;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

// A custom cell renderer for JTable
public class CustomCellRenderer extends JLabel implements TableCellRenderer {

    // EFFECTS: Colors cells based on status
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int col) {
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        Component cell = renderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
        String val = (String) value;
        if (val.equals("Completed!")) {
            cell.setBackground(Color.GREEN);
        } else if (!val.equals("")) {
            cell.setBackground(new Color(204, 232, 232));
        }
        return cell;
    }
}
