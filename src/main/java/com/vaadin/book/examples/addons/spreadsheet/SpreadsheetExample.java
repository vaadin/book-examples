package com.vaadin.book.examples.addons.spreadsheet;

import java.util.GregorianCalendar;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;

import com.vaadin.addon.spreadsheet.Spreadsheet;
import com.vaadin.addon.spreadsheet.action.SpreadsheetDefaultActionHandler;
import com.vaadin.book.examples.lib.AnyBookExampleBundle;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.VerticalLayout;

public class SpreadsheetExample extends CustomComponent implements AnyBookExampleBundle {
    private static final long serialVersionUID = -8491925619264579452L;

    public void basic(VerticalLayout layout) {
        // BEGIN-EXAMPLE: spreadsheet.basic
        // BOOK: spreadsheet
        Spreadsheet sheet = new Spreadsheet();
        sheet.setWidth("400px"); // Full size by default
        sheet.setHeight("250px");
        
        // Put customary greeting in a cell
        sheet.createCell(0, 0, "Hello, world");
        
        // Have some numerical data
        sheet.createCell(1, 0, 6);
        sheet.createCell(1, 1, 7);
        
        // Perform a spreadsheet calculation
        sheet.createCell(1, 2, ""); // Set a dummy value
        sheet.getCell(1, 2).setCellFormula("A2*B2");
        
        // Resize a column to fit the cell data
        sheet.autofitColumn(0);
        
        layout.addComponent(sheet);
        // END-EXAMPLE: spreadsheet.basic
    }

    public void empty(VerticalLayout layout) {
        // BEGIN-EXAMPLE: spreadsheet.empty
        // BOOK: spreadsheet
        Spreadsheet sheet = new Spreadsheet();
        sheet.setWidth("400px"); // Full size by default
        sheet.setHeight("250px");

        // Set the name of the initial sheet
        sheet.setSheetName(0, "My Sheet");
        
        layout.addComponent(sheet);
        // END-EXAMPLE: spreadsheet.empty
    }

    public void cellvalues(VerticalLayout layout) {
        // BEGIN-EXAMPLE: spreadsheet.cellvalues
        // BOOK: spreadsheet
        Spreadsheet sheet = new Spreadsheet();
        sheet.setWidth("800px"); // Full size by default
        sheet.setHeight("500px");
        
        // Recreate the initial sheet to configure it
        sheet.createNewSheet("New Sheet", 10, 5);
        sheet.deleteSheet(0);

        sheet.setDefaultRowCount(100);
        sheet.setDefaultColumnCount(10);

        // Create some column captions in the first row
        sheet.createCell(0, 0, "First Name");
        sheet.createCell(0, 1, "Last Name");
        sheet.createCell(0, 2, "Born");
        
        // Set the cells in the first row as bold
        // sheet.getCell(0, 0).getCellStyle().
        Font bold = sheet.getWorkbook().createFont();
        bold.setBold(true);
        for (int col=0; col <= 2; col++)
            sheet.getCell(0, col).getCellStyle().setFont(bold);
        
        // Define a cell style for dates
        CellStyle dateStyle = sheet.getWorkbook().createCellStyle();
        DataFormat format = sheet.getWorkbook().createDataFormat();
        dateStyle.setDataFormat(format.getFormat("yyyy-mm-dd"));
        
        // Add some data rows
        sheet.createCell(1, 0, "Nicolaus");
        sheet.createCell(1, 1, "Copernicus");
        //spread.createCell(1, 2, new GregorianCalendar(1473,2,19).getTime());
        sheet.createCell(1, 2, new GregorianCalendar(1999,2,19).getTime());
        sheet.getCell(1,2).setCellStyle(dateStyle);
        sheet.createCell(2, 0, "Galileo");
        sheet.createCell(2, 1, "Galilei");
        sheet.createCell(2, 2, new GregorianCalendar(1964,2,15).getTime());
        sheet.getCell(2,2).setCellStyle(dateStyle);
        
        layout.addComponent(sheet);
        layout.setWidth("800px");
        layout.setHeight("500px");
        // END-EXAMPLE: spreadsheet.cellvalues
    }
    public void defaultcontextmenu(VerticalLayout layout) {
        // BEGIN-EXAMPLE: spreadsheet.contextmenu.default
        // BOOK: spreadsheet.contextmenu
        Spreadsheet sheet = new Spreadsheet();
        sheet.setWidth("400px"); // Full size by default
        sheet.setHeight("300px");
        
        // Define a cell style for dates
        CellStyle dateStyle = sheet.getWorkbook().createCellStyle();
        DataFormat format = sheet.getWorkbook().createDataFormat();
        dateStyle.setDataFormat(format.getFormat("yyyy-mm-dd"));

        // Add some data rows
        sheet.createCell(1, 0, "Nicolaus");
        sheet.createCell(1, 1, "Copernicus");
        //spread.createCell(1, 2, new GregorianCalendar(1473,2,19).getTime());
        sheet.createCell(1, 2, new GregorianCalendar(1999,2,19).getTime());
        sheet.getCell(1,2).setCellStyle(dateStyle);
        sheet.createCell(2, 0, "Galileo");
        sheet.createCell(2, 1, "Galilei");
        sheet.createCell(2, 2, new GregorianCalendar(1964,2,15).getTime());
        sheet.getCell(2,2).setCellStyle(dateStyle);
        
        sheet.addActionHandler(new SpreadsheetDefaultActionHandler());
        
        layout.addComponent(sheet);
        // END-EXAMPLE: spreadsheet.contextmenu.default
    }

    public void freezepane(VerticalLayout layout) {
        // BEGIN-EXAMPLE: spreadsheet.freezepane
        // BOOK: spreadsheet.configuration.frozen
        Spreadsheet sheet = new Spreadsheet();
        sheet.setWidth("400px"); // Full size by default
        sheet.setHeight("200px");

        // Define a header row (to be frozen)
        sheet.createCell(0, 0, "First Name");
        sheet.createCell(0, 1, "Last Name");
        sheet.createCell(0, 2, "Born");
        
        // Define a cell style for dates
        CellStyle dateStyle = sheet.getWorkbook().createCellStyle();
        DataFormat format = sheet.getWorkbook().createDataFormat();
        dateStyle.setDataFormat(format.getFormat("yyyy-mm-dd"));

        // Add some data rows
        sheet.createCell(1, 0, "Nicolaus");
        sheet.createCell(1, 1, "Copernicus");
        //spread.createCell(1, 2, new GregorianCalendar(1473,2,19).getTime());
        sheet.createCell(1, 2, new GregorianCalendar(1999,2,19).getTime());
        sheet.getCell(1,2).setCellStyle(dateStyle);
        sheet.createCell(2, 0, "Galileo");
        sheet.createCell(2, 1, "Galilei");
        sheet.createCell(2, 2, new GregorianCalendar(1964,2,15).getTime());
        sheet.getCell(2,2).setCellStyle(dateStyle);
        
        sheet.createFreezePane(1, 2);
        
        layout.addComponent(sheet);
        // END-EXAMPLE: spreadsheet.freezepane
    }

    public void cellcomment(VerticalLayout layout) {
        // BEGIN-EXAMPLE: spreadsheet.freezepane
        // BOOK: spreadsheet.configuration.frozen
        Spreadsheet sheet = new Spreadsheet();
        sheet.setWidth("400px"); // Full size by default
        sheet.setHeight("200px");

        // Add some data rows
        sheet.createCell(1, 0, "I'm commented");
        
        // sheet.getCell(1, 0).setCellComment(new Comment());
        sheet.addActionHandler(new SpreadsheetDefaultActionHandler());
        
        layout.addComponent(sheet);
        // END-EXAMPLE: spreadsheet.freezepane
    }

    public void merging(VerticalLayout layout) {
        // BEGIN-EXAMPLE: spreadsheet.merging
        // BOOK: spreadsheet.configuration.frozen
        Spreadsheet sheet = new Spreadsheet();
        sheet.setWidth("400px"); // Full size by default
        sheet.setHeight("200px");

        // This cell will become merged with other cells
        sheet.createCell(1, 0, "I'm a big cell");

        layout.addComponent(sheet);
        // END-EXAMPLE: spreadsheet.merging
    }
}
