package com.vaadin.book.examples.addons.spreadsheet;

import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;

import com.vaadin.addon.spreadsheet.Spreadsheet;
import com.vaadin.addon.spreadsheet.SpreadsheetComponentFactory;
import com.vaadin.book.examples.AnyBookExampleBundle;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.VerticalLayout;

public class SpreadsheetComponentsExample extends CustomComponent implements AnyBookExampleBundle {
    private static final long serialVersionUID = -8491925619264579452L;

    class MyComponentFactory implements SpreadsheetComponentFactory {
        @Override
        public Component getCustomComponentForCell(Cell cell, int rowIndex,
            int columnIndex, Spreadsheet spreadsheet, Sheet sheet) {
            if (rowIndex == 0 && columnIndex == 1)
                return new PopupDateField();
            else
                return null;
        }

        @Override
        public Component getCustomEditorForCell(Cell cell, int rowIndex,
            int columnIndex, Spreadsheet spreadsheet, Sheet sheet) {
            return null;
        }

        @Override
        public void onCustomEditorDisplayed(Cell cell, int rowIndex,
            int columnIndex, Spreadsheet spreadsheet, Sheet sheet,
            Component customEditor) {
            // TODO Auto-generated method stub
            
        }
    }
    
    public void components(VerticalLayout layout) {
        // BEGIN-EXAMPLE: spreadsheet.components
        // BOOK: spreadsheet
        Spreadsheet sheet = new Spreadsheet();
        sheet.setWidth("400px"); // Full size by default
        sheet.setHeight("250px");
        
        // Set current time
        sheet.createCell(0, 0, new Date());
        
        SpreadsheetComponentFactory customComponentFactory = 
                new MyComponentFactory();
        sheet.setSpreadsheetComponentFactory(customComponentFactory);
        
        layout.addComponent(sheet);
        // END-EXAMPLE: spreadsheet.components
    }
}
