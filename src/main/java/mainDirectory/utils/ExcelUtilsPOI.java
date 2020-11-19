package mainDirectory.utils;

import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import mainDirectory.database.model.Person;
import mainDirectory.database.model.Status;
import mainDirectory.database.model.Ticket;
import mainDirectory.dialogs.Dialogs;
import mainDirectory.modelFX.TicketFX;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ExcelUtilsPOI {


    public List<Ticket> importFromExcel() throws IOException {
        List<Ticket> list = new ArrayList<>();
        File file = getFile("open");
        if (file != null) {
            FileInputStream fis = new FileInputStream(file);
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i < sheet.getLastRowNum() +1; i++) {
                Ticket ticket = new Ticket();
                try {
                    ticket.setMaterialName(sheet.getRow(i).getCell(1).getStringCellValue());
                    ticket.setMaterialDescription(sheet.getRow(i).getCell(2,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());
                    Status status = new Status();
                    status.setStatusName(sheet.getRow(i).getCell(3).getStringCellValue());
                    ticket.setStatus(status);
                    ticket.setNotes(sheet.getRow(i).getCell(4,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());
                    ticket.setProject(sheet.getRow(i).getCell(5,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());
                    Person sourcing = new Person();
                    sourcing.setName(sheet.getRow(i).getCell(6).getStringCellValue());
                    ticket.setScmer(sourcing);
                    Person pur = new Person();
                    pur.setName(sheet.getRow(i).getCell(7).getStringCellValue());
                    ticket.setBuyer(pur);
                    Person planning = new Person();
                    planning.setName(sheet.getRow(i).getCell(8).getStringCellValue());
                    ticket.setPlanner(planning);
                    Person author = new Person();
                    author.setName(sheet.getRow(i).getCell(9).getStringCellValue());
                    ticket.setAuthor(author);
                    ticket.setActive(true);
                    ticket.setData(new Date());
//                if (ticket.getMaterialName().equals("") || ticket.getMaterialDescription().equals("") || ticket.getStatus().equals("") || sourcing.getName().equals("") || pur.getName().equals("") || planning.getName().equals("") || author.getName().equals("")) {
//                    break;
//                }
                    list.add(ticket);

                } catch (NullPointerException e) {
                    //Dialogs.alertMessage("Błąd nie wszytskie dane zostały wprowadzone poprawnie");
                    Dialogs.alertMessage("Błąd podano nie wszytskie dane");
                    break;

                }
                finally {
                    fis.close();
                }
            }
            return list;
        } else {
            Dialogs.informationDialog("Nie wybrano żadanego pliku");
            return null;
        }

    }

    public void exportToExcel(ObservableList<TicketFX> list) throws IOException {

        File file = getFile("save");

        if (file != null) {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet();

            Row row = createHeaders(sheet);

            int rownumber = 0;
            for (TicketFX ticketFX : list) {
                rownumber++;
                row = sheet.createRow(rownumber);
                row.createCell(1).setCellValue(ticketFX.materialNameProperty.getValue());
                row.createCell(2).setCellValue(ticketFX.materialDescriptionProperty.getValue());
                row.createCell(3).setCellValue(ticketFX.statusPropertyProperty().get().getNameFX());
                row.createCell(4).setCellValue(ticketFX.notesProperty.getValue());
                row.createCell(5).setCellValue(ticketFX.projectProperty.getValue());
                row.createCell(6).setCellValue(ticketFX.scmerFXPropertyProperty().get().getName()
                        + " "
                        + ticketFX.scmerFXPropertyProperty().get().getSurname());
                row.createCell(7).setCellValue(ticketFX.buyerFXPropertyProperty().get().getName()
                        + " "
                        + ticketFX.buyerFXPropertyProperty().get().getSurname());
                row.createCell(8).setCellValue(ticketFX.plannerFXPropertyProperty().get().getName()
                        + " "
                        + ticketFX.plannerFXPropertyProperty().get().getSurname());
                row.createCell(9).setCellValue(ticketFX.authorFXPropertyProperty().get().getName()
                        + " "
                        + ticketFX.authorFXPropertyProperty().get().getSurname());


            }
            FileOutputStream fos = new FileOutputStream(file);
            workbook.write(fos);
            workbook.close();
            fos.close();
            Dialogs.informationDialog("Plik " + file.getName() + " został zapisany");
        } else {
            Dialogs.informationDialog("Nie wybrano żadanego pliku");
        }


    }


    public void createTemplate(List<String> purFXList, List<String> planningFXList, List<String> scmFxList, String personFXObjectProperty, List<String> statusList) throws IOException {
        File file = getFile("save");
        if (file != null) {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet();
            createHeaders(sheet);
            DataValidationHelper validationHelper = new XSSFDataValidationHelper(sheet);
            //create status list
            sheet.addValidationData(createValidationFromList(validationHelper, statusList, 1, 99, 3, 3));
            //create pur list
            sheet.addValidationData(createValidationFromList(validationHelper, purFXList, 1, 99, 7, 7));
            //create planning list
            sheet.addValidationData(createValidationFromList(validationHelper, planningFXList, 1, 99, 8, 8));
            //create scm list
            sheet.addValidationData(createValidationFromList(validationHelper, scmFxList, 1, 99, 6, 6));
            //create author list
            sheet.addValidationData(createValidationFromList(validationHelper, Arrays.asList(personFXObjectProperty), 1, 99, 9, 9));
            FileOutputStream fos = new FileOutputStream(file);
            workbook.write(fos);
            workbook.close();
            fos.close();


        } else {
            Dialogs.alertMessage("Nie wybrano żadnego pliku");
        }
    }


    private static File getFile(String param) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Plik XLSX (*.xlsx)", "*.xlsx");
        fileChooser.getExtensionFilters().add(extensionFilter);
        if (param.equals("open")) {
            return fileChooser.showOpenDialog(null);
        } else if (param.equals("save")) {
            return fileChooser.showSaveDialog(null);
        } else {
            return null;
        }

    }

    private String[] convertListToArray(List<String> list) {
        String[] array = new String[list.size()];
        int index = 0;
        for (String string : list) {
            array[index] = list.get(index);
            index++;
        }
        return array;

    }

    private DataValidation createValidationFromList(DataValidationHelper validationHelper, List<String> list, int firstRow, int lastRow, int firstCol, int lastCol) {
        CellRangeAddressList cellRangeAddressList = new CellRangeAddressList(firstRow, lastRow, firstCol, lastCol);
        DataValidationConstraint dataValidationConstraint = validationHelper.createExplicitListConstraint(convertListToArray(list));
        DataValidation dataValidation = validationHelper.createValidation(dataValidationConstraint, cellRangeAddressList);
        dataValidation.setSuppressDropDownArrow(true);
        return dataValidation;
    }

    private Row createHeaders(XSSFSheet sheet) {
        Row row = sheet.createRow(0);

        //header
        row.createCell(1).setCellValue("Materiał");
        row.createCell(2).setCellValue("Nazwa");
        row.createCell(3).setCellValue("Status");
        row.createCell(4).setCellValue("Notatki");
        row.createCell(5).setCellValue("Projekt");
        row.createCell(6).setCellValue("Scm");
        row.createCell(7).setCellValue("Zaopatrzenie");
        row.createCell(8).setCellValue("Planowanie");
        row.createCell(9).setCellValue("Autor");
        return row;
    }
}
