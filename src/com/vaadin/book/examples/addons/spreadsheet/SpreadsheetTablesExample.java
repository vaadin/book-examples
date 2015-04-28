package com.vaadin.book.examples.addons.spreadsheet;

import org.apache.poi.ss.util.CellRangeAddress;

import com.vaadin.addon.spreadsheet.Spreadsheet;
import com.vaadin.addon.spreadsheet.SpreadsheetFilterTable;
import com.vaadin.addon.spreadsheet.SpreadsheetTable;
import com.vaadin.book.examples.AnyBookExampleBundle;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.VerticalLayout;

public class SpreadsheetTablesExample extends CustomComponent implements AnyBookExampleBundle {
    private static final long serialVersionUID = -3175579180596200433L;

    public void basic(VerticalLayout layout) {
        // BEGIN-EXAMPLE: spreadsheet.tables.basic
        // BOOK: spreadsheet
        Spreadsheet sheet = new Spreadsheet() {
            // This attach thing is part of workaround for #17201
            @Override
            public void attach() {
                super.attach();
                Spreadsheet sheet = this;
                
                // Create a table in the region  
                CellRangeAddress range = new CellRangeAddress(1, 7, 1, 4);
                SpreadsheetTable table = new SpreadsheetTable(sheet, range);
                sheet.registerTable(table);
                
                // Enable hiding each column
                for (int col = range.getFirstColumn();
                         col <= range.getLastColumn(); col++) {
                    final int c = col;
                    table.getPopupButton(col).addComponent(
                        new Button("Hide Column", e -> { // Java 8
                            sheet.setColumnHidden(c, true);
                            table.getPopupButton(c).closePopup();
                        }));
                }
            }
        };
        sheet.setWidth("600px"); // Full size by default
        sheet.setHeight("400px");

        // Adding before registering table is workaround for #17201
        layout.addComponent(sheet);
        
        // Have a header row in a region of the sheet
        sheet.createCell(1, 1, "First Name");
        sheet.createCell(1, 2, "Last Name");
        sheet.createCell(1, 3, "Born");
        sheet.createCell(1, 4, "Died");
        
        // Have some data in the region
        Object[][] data = new Object[][] {
            {"Nicolaus", "Copernicus", 1473, 1543},
            {"Galileo", "Galilei", 1564, 1642},
            {"Johannes", "Kepler", 1571, 1630},
            {"Tycho", "Brahe", 1546, 1601},
            {"Giordano", "Bruno", 1548, 1600},
            {"Christiaan", "Huygens", 1629, 1695}};
        for (int row = 0; row < data.length; row++)
            for (int col = 0; col < data[row].length; col++)
                sheet.createCell(row + 2, col + 1, data[row][col]);
        // END-EXAMPLE: spreadsheet.tables.basic
    }

    public void filter(VerticalLayout layout) {
        // BEGIN-EXAMPLE: spreadsheet.tables.filter
        // BOOK: spreadsheet
        Spreadsheet sheet = new Spreadsheet() {
            // This attach thing is part of workaround for #17201
            @Override
            public void attach() {
                super.attach();
                Spreadsheet sheet = this;
                
                // Create a table in the region  
                CellRangeAddress range = new CellRangeAddress(1, 7, 1, 4);
                SpreadsheetFilterTable table = new SpreadsheetFilterTable(sheet, range);
                sheet.registerTable(table);
            }
        };
        sheet.setWidth("600px"); // Full size by default
        sheet.setHeight("400px");

        // Adding before registering table is workaround for #17201
        layout.addComponent(sheet);
        
        // Have a header row in a region of the sheet
        sheet.createCell(1, 1, "First Name");
        sheet.createCell(1, 2, "Last Name");
        sheet.createCell(1, 3, "Born");
        sheet.createCell(1, 4, "Died");
        
        // Have some data in the region
        Object[][] data = new Object[][] {
            {"Nicolaus", "Copernicus", 1473, 1543},
            {"Galileo", "Galilei", 1564, 1642},
            {"Johannes", "Kepler", 1571, 1630},
            {"Tycho", "Brahe", 1546, 1601},
            {"Giordano", "Bruno", 1548, 1600},
            {"Christiaan", "Huygens", 1629, 1695}};
        for (int row = 0; row < data.length; row++)
            for (int col = 0; col < data[row].length; col++)
                sheet.createCell(row + 2, col + 1, data[row][col]);
        // END-EXAMPLE: spreadsheet.tables.filter
    }
}
