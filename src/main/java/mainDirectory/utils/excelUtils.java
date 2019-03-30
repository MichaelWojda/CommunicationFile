package mainDirectory.utils;

import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import jxl.Workbook;
import jxl.write.*;
import mainDirectory.dialogs.Dialogs;
import mainDirectory.modelFX.TicketFX;
import mainDirectory.modelFX.TicketPlanningModel;

import java.io.File;
import java.io.IOException;

public class excelUtils {
    TicketPlanningModel ticketPlanningModel = new TicketPlanningModel();


    public static void exportToExcel(ObservableList<TicketFX> list) throws IOException, WriteException {

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Plik XLS (*.xls)","*.xls");
        fileChooser.getExtensionFilters().add(extensionFilter);
        File file = fileChooser.showSaveDialog(null);
        WritableWorkbook writableWorkbook = Workbook.createWorkbook(file);
        WritableSheet writableSheet = writableWorkbook.createSheet("Arkusz 1 ", 0);
        WritableCellFormat headerformat = new WritableCellFormat();
        headerformat.setWrap(false);
        WritableFont writableFont = new WritableFont(WritableFont.ARIAL,12,WritableFont.BOLD);
        headerformat.setFont(writableFont);
        Label headerlabel = new Label(0,0,"Nazwa materiału",headerformat);

        writableSheet.addCell(headerlabel);
        headerlabel = new Label(1,0,"Opis",headerformat);
        writableSheet.addCell(headerlabel);
        headerlabel = new Label(2,0,"Projekt",headerformat);
        writableSheet.addCell(headerlabel);
        headerlabel = new Label(3,0,"Notatki",headerformat);
        writableSheet.addCell(headerlabel);
        headerlabel = new Label(4,0,"Status",headerformat);
        writableSheet.addCell(headerlabel);
        headerlabel = new Label(5,0,"Autor",headerformat);
        writableSheet.addCell(headerlabel);

        WritableCellFormat cellFormat = new WritableCellFormat();
        cellFormat.setWrap(false);
        list.forEach(ticketFX -> {
            int row = writableSheet.getRows();
            Label label = new Label(0,row,ticketFX.materialNameProperty.getValue(),cellFormat);
            try {
                writableSheet.addCell(label);
                label = new Label(1,row,ticketFX.materialDescriptionProperty.getValue(),cellFormat);
                writableSheet.addCell(label);
                label = new Label(2,row,ticketFX.projectProperty.getValue(),cellFormat);
                writableSheet.addCell(label);
                label = new Label(3,row,ticketFX.notesProperty.getValue(),cellFormat);
                writableSheet.addCell(label);
                label = new Label(4,row,ticketFX.statusPropertyProperty().get().getNameFX(),cellFormat);
                writableSheet.addCell(label);
                label = new Label(5,row,ticketFX.authorFXProperty.get().getName()+" "+ticketFX.authorFXProperty.get().getSurname(),cellFormat);
                writableSheet.addCell(label);


            } catch (WriteException e) {
                Dialogs.alertMessage("Wystąpił błąd zapisu");
            }


        });
        writableWorkbook.write();
        Dialogs.informationDialog("Plik został zapisany");
        writableWorkbook.close();


    }

}
