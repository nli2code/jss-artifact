
package excel1;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Font;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.WorkbookUtil;

import org.apache.poi.ss.util.CellRangeAddress;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author saidjose
 */
public class excel_L_E {

    Workbook workbook;
    //ArrayList<Sheet> listaHojas;
    List listaSheets;
    Sheet Sheet1 ;
    Cell cell;
    Row row;
    
    public excel_L_E( String NombreDelArchivo) {
        //listaHojas = new ArrayList();
        workbook = new HSSFWorkbook();
        List listaSheets = new ArrayList();
        
        Sheet1 = workbook.createSheet("hoja#1") ;
        
        //listaSheets.add(  workbook.createSheet("hoja#1") ) ;
        //listaSheets.add(  workbook.createSheet("hoja#2") ) ;
        //CrearHoja("hoja#1");
        
        //CrearHoja("hoja#2");
        //CrearHoja( WorkbookUtil.createSafeSheetName("$%%&&%$·") ); 
        
        /*
        * WorkbookUtil.createSafeSheetName("$%%&&%$·") 
        * me terminte aceptar 
        * los simbolos a la hora de agregar nombres
        */
        /*
        AddText ( 0 , 0 , 10 );
        AddText ( 0 , 1 , 10 );
        */
        Cell cell0 = Sheet1.createRow(0).createCell(0); // A = 0 , B = 1 , ETC
        Cell cell1 = Sheet1.createRow(0).createCell(1); // A = 0 , B = 1 , ETC
        Cell cell2 = Sheet1.createRow(0).createCell(2); // A = 0 , B = 1 , ETC
        Cell cell3 = Sheet1.createRow(0).createCell(3); // A = 0 , B = 1 , ETC
        Cell cell4 = Sheet1.createRow(0).createCell(4); // A = 0 , B = 1 , ETC
        
        cell0.setCellValue(10);
        cell1.setCellValue(10);
        cell2.setCellFormula("A1+B1");// suma dos casillas
        cell3.setCellFormula("SUM(A1:B1)");// suma dos casillas
        
        cell4.setCellFormula("SUM(A1:D1)");// suma desde una casilla hasta otra
        
        try{
             FileOutputStream fileOutputStream = new FileOutputStream(NombreDelArchivo);
             workbook.write(fileOutputStream);
             fileOutputStream.close();
         }catch(Exception ex){
             ex.printStackTrace();
         }
        
         System.out.println("se creo el archivo: "+ "prueba1.xls");
    }
    
    
    private void CrearHoja (){
         Sheet1 = workbook.createSheet(); // esto megenera una hoja en excel
        //listaHojas.add(Sheet1);
        System.out.println( Sheet1.getSheetName() );
    }
    private void CrearHoja(String texto){
         Sheet1 = workbook.createSheet(texto); // esto megenera una hoja en excel
        //listaHojas.add(Sheet1);
    }
    private void AddText ( int Fila , int Columna , int Text ){
        //Sheet Sheet1 = listaHojas.get(num) ;
        //row = Sheet1.createRow(Fila);
        cell = Sheet1.createRow(Fila).createCell(Columna); // A = 0 , B = 1 , ETC
        cell.setCellValue(Text);
        System.out.println(cell.getRichStringCellValue());
    }
    
    
    
}
