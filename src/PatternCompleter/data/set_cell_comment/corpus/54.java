/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pe.nisira.movil.view.action;

import com.nisira.core.entity.Dpaleta;
import com.nisira.core.entity.Multitabla;
import com.nisira.core.entity.Paleta;
import com.nisira.core.service.PaletaService;
import com.nisira.core.util.ConstantesBD;
import static com.pe.nisira.movil.view.util.NisiraWebLog.log;
import com.pe.nisira.movil.view.util.WebUtil;
import static com.pe.nisira.movil.view.util.WebUtil.getArchivoLista;
import com.sun.prism.Image;
import com.thoughtworks.xstream.XStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.swing.text.Document;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Alex Johel Burgos Dionicio
 */
@ManagedBean
@ViewScoped
public class RegistroPaleta extends AbstactListAction<Paleta> implements Serializable{

    private List<Paleta> listPaleta;
    private List<Paleta> listPaletaUp;
    private List<Dpaleta> listDPaletaUp;
    private String idEmpresa;
    private String idCampania;
    private PaletaService paletaService;
    private String mensaje;
    private UploadedFile file;
    private UploadedFile upFile;
    private List<Object[]> listerros;
    private List<Object[]> listderros;
    public UploadedFile getFile() {
        return file;
    }
    @Override
    public void buscarTodo() {
        try {
            setListaDatos(paletaService.filtrar());
        } catch (Exception ex) {
            Logger.getLogger(RegistroPaleta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getIniciar() {
        this.idEmpresa = ConstantesBD.IDEMPRESA;
        this.idCampania = "";
        this.paletaService = new PaletaService();
        setAnuevo(1);
        return "";
    }

    @Override
    public void nuevo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public void setFile(UploadedFile file) {
        this.file = file;
    }
    
    public void upload() {
        if (file != null) {
            try {
                WebUtil.subirArchivo(file, "archivo");
                FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
                FacesContext.getCurrentInstance().addMessage(null, message);
                ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
                String path = (String) servletContext.getRealPath("archivo");
//              getExcelTablePaletaList(getArchivoLista(path + "/" + file.getFileName()));
                paletaService.grabarByTotalPaletaXml(getExcelTablePaletaXML(getArchivoLista((String) servletContext.getRealPath("archivo/" + file.getFileName()))));
            } catch (IOException ex) {
                mensaje = ex.getMessage();
                Logger.getLogger(RegistroPaleta.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                mensaje = ex.getMessage();
                Logger.getLogger(RegistroPaleta.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public String getExcelTablePaletaXML(List sheetData) {
        String XMLPaleta = null;
        int j;
        try {
            if (sheetData.size() > 0) {
                List<Paleta> listaXml = new ArrayList<Paleta>();
                Paleta paleta;
                for (int i = 1; i < sheetData.size(); i++) {
                    List list = (List) sheetData.get(i);
                    paleta = new Paleta();
                    j = 0;
                    paleta.setIdempresa(list.get(j++).toString());
                    paleta.setIdregistropaleta(list.get(j++).toString());
                    paleta.setIdemisor(list.get(j++).toString());
                    paleta.setIdoperacion(list.get(j++).toString());
                    paleta.setNumoperacion(list.get(j++).toString());
                    paleta.setIdmotivopaleta(list.get(j++).toString());
                    paleta.setIddocumento(list.get(j++).toString());
                    paleta.setSerie(list.get(j++).toString());
                    paleta.setNumero(list.get(j++).toString());
                    paleta.setFecha(list.get(j++).toString());
                    paleta.setPeriodo(list.get(j++).toString());
                    paleta.setIdestado(list.get(j++).toString());
                    paleta.setIdclieprov(list.get(j++).toString());
                    paleta.setNropaleta(list.get(j++).toString());
                    paleta.setIdenvase(list.get(j++).toString());
                    paleta.setIdsucursal(list.get(j++).toString());
                    paleta.setIdalmacen(list.get(j++).toString());
                    paleta.setIdparihuela(list.get(j++).toString());
                    paleta.setIdembalaje(list.get(j++).toString());
                    paleta.setIdcultivo(list.get(j++).toString());
                    paleta.setIdvariedad(list.get(j++).toString());
                    paleta.setDes_variedad(list.get(j++).toString());
                    paleta.setIdclieprov_origen(list.get(j++).toString());
                    paleta.setNropaleta_origen(list.get(j++).toString());
                    paleta.setIdsalida(list.get(j++).toString());
                    paleta.setIdmotivo_ing(list.get(j++).toString());
                    paleta.setIdingreso(list.get(j++).toString());
                    paleta.setIdmotivo_sal(list.get(j++).toString());
                    paleta.setObservaciones(list.get(j++).toString());
                    paleta.setVentana(list.get(j++).toString());
                    paleta.setCantidad(list.get(j++).toString());
                    paleta.setCerrado(list.get(j++).toString());
                    paleta.setSincroniza(list.get(j++).toString());
                    paleta.setFechacreacion(list.get(j++).toString());
                    paleta.setNromanual(list.get(j++).toString());
                    paleta.setDesc_adicional(list.get(j++).toString());
                    paleta.setIdclieprov_destino(list.get(j++).toString());
                    paleta.setTipo(list.get(j++).toString());
                    paleta.setIdturno(list.get(j++).toString());
                    paleta.setIdreqinterno(list.get(j++).toString());
                    listaXml.add(paleta);
                }
                System.out.println("Terminado ...");
                this.listPaleta = listaXml;
                RequestContext.getCurrentInstance().update("datos:tbl");
                //DEFINIENDO XML DE PALETA
                String xml = "<?xml version='1.0' encoding='ISO-8859-1' ?>";
                XStream xStream_paleta = new XStream();
                xStream_paleta.processAnnotations(Paleta.class);
                XMLPaleta = xml + xStream_paleta.toXML(listaXml);
            }
        } catch (Exception ex) {
            XMLPaleta = null;
            this.mensaje = "Error:" + ex.getMessage();
        }
        return XMLPaleta;
    }

    public void getExcelTablePaletaList(List sheetData) {
        try {
            if (sheetData.size() > 0) {
                List<Paleta> lista = new ArrayList<Paleta>();
                Paleta paleta = new Paleta();
                for (int i = 1; i < sheetData.size(); i++) {
                    List list = (List) sheetData.get(i);
                    paleta = new Paleta();
                    for (int j = 0; j < list.size(); j++) {
                        Cell cell = (Cell) list.get(j);
                        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            System.out.print(cell.getNumericCellValue());
                        } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                            System.out.print(cell.getRichStringCellValue());
                        } else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
                            System.out.print(cell.getBooleanCellValue());
                        }
                        switch (j) {
                            case 0:
                                paleta.setIdempresa(cell.getRichStringCellValue().getString());
                                break;
                            case 1:
                                paleta.setIdregistropaleta(cell.getRichStringCellValue().getString());
                                break;
                            case 2:
                                paleta.setIdproducto(cell.getRichStringCellValue().getString());
                                break;
                            case 3:
                                paleta.setNropaleta(cell.getRichStringCellValue().getString());
                                break;
                            case 4:
                                paleta.setDescproducto(cell.getRichStringCellValue().getString());
                                break;
                            case 5:
                                paleta.setIdmedida(cell.getRichStringCellValue().getString());
                                break;
                            case 6:
                                paleta.setCantidad(cell.getRichStringCellValue().getString());
                                break;
                            case 7:
                                paleta.setPeso(Double.parseDouble(cell.getRichStringCellValue().getString()));
                                break;
                            case 8:
                                paleta.setIdlotep(cell.getRichStringCellValue().getString());
                                break;
                            case 9:
                                paleta.setIdcliente(cell.getRichStringCellValue().getString());
                                break;
                            case 10:
                                paleta.setIdclieprov(cell.getRichStringCellValue().getString());
                                break;
                            case 11:
                                paleta.setIdenvase(cell.getRichStringCellValue().getString());
                                break;
                            case 12:
                                paleta.setDescenvase(cell.getRichStringCellValue().getString());
                                break;
                            case 13:
                                paleta.setCerrado(cell.getRichStringCellValue().getString());
                                break;
                            case 14:
                                paleta.setNromanual(cell.getRichStringCellValue().getString());
                                break;
                        }
                        if (j < list.size() - 1) {
                            System.out.print(", ");
                        }
                    }
                    lista.add(paleta);
                    System.out.println("");
                }
                System.out.println("Terminado ...");
                this.listPaleta = lista;
                RequestContext.getCurrentInstance().update("datos:tbl");
            }
        } catch (Exception ex) {

        }
    }

    public RegistroPaleta() {
        this.idEmpresa = "001";
        this.idCampania = "";
        this.paletaService = new PaletaService();
        setAnuevo(1);
    }

    public void recordPaleta() {
        try {
            setListPaleta(getPaletaService().findByTotalPaleta(getIdEmpresa(), getIdCampania()));
        } catch (Exception ex) {
            WebUtil.MensajeAlerta("Registro Vacio");
            this.mensaje = "Registro Vacio";
            log.error(ex, ex);
        }
    }

    public void postProcessXLS(Object document) {
        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow header = sheet.getRow(0);
        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setFillForegroundColor(HSSFColor.GREEN.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
            header.getCell(i).setCellStyle(cellStyle);
        }
    }

    public StreamedContent downFormatExcel() throws Exception {
        InputStream stream = null;
        StreamedContent arch = null;
        try {
            String folder = "C:\\SOLUTION\\WEB\\FORMATOS_IMPORTACION";
            File ruta = new File(folder);
            if (!ruta.isDirectory()) {
                ruta.mkdirs();
            }
            String rutaArchivo = folder + "\\FI_REGISTROPALE.xlsx";
            File fileXls = new File(rutaArchivo);
            if (fileXls.exists()) {
                fileXls.delete();
            }
            fileXls.createNewFile();
            XSSFWorkbook libro = new XSSFWorkbook();
            FileOutputStream file2 = new FileOutputStream(fileXls);
            XSSFSheet hoja = libro.createSheet("IMPORTAR_PALETA");
            CreationHelper factory = libro.getCreationHelper();
            hoja = libro.getSheetAt(0);
            XSSFCellStyle style = libro.createCellStyle();
            Font font = libro.createFont();
            Font font1 = libro.createFont();
            Drawing drawing = hoja.createDrawingPatriarch();
            ClientAnchor anchor1 = factory.createClientAnchor();
            font.setBoldweight(Font.BOLDWEIGHT_BOLD);
            font.setFontHeightInPoints((short) 8);
            font1.setFontHeightInPoints((short) 8);
            font1.setFontName("Arial");
            font.setFontName("Arial");
            style.setFillForegroundColor(new XSSFColor(new java.awt.Color(247, 150, 70)));
            style.setFillPattern(CellStyle.SOLID_FOREGROUND);
            style.setAlignment(CellStyle.VERTICAL_CENTER);
            style.setAlignment(CellStyle.ALIGN_CENTER);
            style.setFont(font);

            XSSFSheet hoja2 = libro.createSheet("IMPORTAR_DET_PALETA");
            CreationHelper factory2 = libro.getCreationHelper();
            hoja2 = libro.getSheetAt(1);
            XSSFCellStyle style2 = libro.createCellStyle();
            Font font2 = libro.createFont();
            Font font12 = libro.createFont();
            Drawing drawing2 = hoja2.createDrawingPatriarch();
            ClientAnchor anchor12 = factory2.createClientAnchor();
            font2.setBoldweight(Font.BOLDWEIGHT_BOLD);
            font2.setFontHeightInPoints((short) 8);
            font12.setFontHeightInPoints((short) 8);
            font12.setFontName("Arial");
            font2.setFontName("Arial");
            style2.setFillForegroundColor(new XSSFColor(new java.awt.Color(247, 150, 70)));
            style2.setFillPattern(CellStyle.SOLID_FOREGROUND);
            style2.setAlignment(CellStyle.VERTICAL_CENTER);
            style2.setAlignment(CellStyle.ALIGN_CENTER);
            style2.setFont(font);
            for (int f = 0; f < 1; f++) {
                XSSFRow fila = hoja.createRow(f);
                for (int c = 0; c < 29; c++) {
                    XSSFCell celda = fila.createCell(c);
                    celda.setCellStyle(style);
                    anchor1.setCol1(celda.getColumnIndex());
                    anchor1.setCol2(celda.getColumnIndex() + 5);
                    anchor1.setRow1(fila.getRowNum());
                    anchor1.setRow2(fila.getRowNum() + 3);
                    Comment comment = drawing.createCellComment(anchor1);
                    if (f == 0 && c == 0) {
                        RichTextString str = factory.createRichTextString("ADM:\nCampo Obligatorio \n - IDEMPRESA");
                        str.applyFont(font1);
                        str.applyFont(0, 19, font);
                        comment.setString(str);
                        comment.setAuthor("ADM");
                        celda.setCellValue("IDEMPRESA");
                        celda.setCellComment(comment);
                    } else if (f == 0 && c == 1) {
                        RichTextString str = factory.createRichTextString("ADM:\nCampo Obligatorio \n - IDREGISTROPALETA. \n Debe de tener (15) caracteres");
                        str.applyFont(font1);
                        str.applyFont(0, 19, font);
                        comment.setString(str);
                        comment.setAuthor("ADM");
                        celda.setCellValue("IDREGISTROPALETA");
                        celda.setCellComment(comment);
                    } else if (f == 0 && c == 2) {
                        RichTextString str = factory.createRichTextString("ADM:\nCampo Obligatorio \n - Id del emisor. \n -Debe tener 3 caracteres.");
                        str.applyFont(font1);
                        str.applyFont(0, 19, font);
                        comment.setString(str);
                        comment.setAuthor("ADM");
                        celda.setCellValue("IDEMISOR");
                        celda.setCellComment(comment);
                    } else if (f == 0 && c == 3) {
                        RichTextString str = factory.createRichTextString("ADM:\nCampo Obligatorio \n - id de la operacion.\n -Debe tener 4 caracteres.");
                        str.applyFont(font1);
                        str.applyFont(0, 19, font);
                        comment.setString(str);
                        comment.setAuthor("ADM");
                        celda.setCellValue("IDOPERACION");
                        celda.setCellComment(comment);
                    } else if (f == 0 && c == 4) {
                        RichTextString str = factory.createRichTextString("ADM:\nCampo Obligatorio \n - Numero de Operacion.\n -Debe tener 10 caracteres.");
                        str.applyFont(font1);
                        str.applyFont(0, 19, font);
                        comment.setString(str);
                        comment.setAuthor("ADM");
                        celda.setCellValue("NUMOPERACION");
                        celda.setCellComment(comment);
                    } else if (f == 0 && c == 5) {
                        RichTextString str = factory.createRichTextString("ADM:\nCampo Obligatorio \n - Id del motivo de Paleta.\n -Debe tener 3 caracteres.");
                        str.applyFont(font1);
                        str.applyFont(0, 19, font);
                        comment.setString(str);
                        comment.setAuthor("ADM");
                        celda.setCellValue("IDMOTIVOPALETA");
                        celda.setCellComment(comment);
                    } else if (f == 0 && c == 6) {
                        RichTextString str = factory.createRichTextString("ADM:\nCampo Obligatorio \n - Id documento. \n -Debe tener 3 caracteres.");
                        str.applyFont(font1);
                        str.applyFont(0, 19, font);
                        comment.setString(str);
                        comment.setAuthor("ADM");
                        celda.setCellValue("IDDOCUMENTO");
                        celda.setCellComment(comment);
                    } else if (f == 0 && c == 7) {
                        RichTextString str = factory.createRichTextString("ADM:\nCampo Obligatorio \n - Serie del Documento. \n -Debe tener 4 caracteres.");
                        str.applyFont(font1);
                        str.applyFont(0, 19, font);
                        comment.setString(str);
                        comment.setAuthor("ADM");
                        celda.setCellValue("SERIE");
                        celda.setCellComment(comment);
                    } else if (f == 0 && c == 8) {
                        RichTextString str = factory.createRichTextString("ADM:\nCampo Obligatorio \n - Numero de Documento.\n -Debe tener 7 caracteres.");
                        str.applyFont(font1);
                        str.applyFont(0, 19, font);
                        comment.setString(str);
                        comment.setAuthor("ADM");
                        celda.setCellValue("NUMERO");
                        celda.setCellComment(comment);
                    } else if (f == 0 && c == 9) {
                        RichTextString str = factory.createRichTextString("ADM:\nCampo Obligatorio \n - Formato YYYY/MM/DD.");
                        str.applyFont(font1);
                        str.applyFont(0, 19, font);
                        comment.setString(str);
                        comment.setAuthor("ADM");
                        celda.setCellValue("FECHA");
                        celda.setCellComment(comment);
                    } else if (f == 0 && c == 10) {
                        RichTextString str = factory.createRichTextString("ADM:\nCampo Obligatorio \n - Perido del año \n - fromato YYYYMM.");
                        str.applyFont(font1);
                        str.applyFont(0, 19, font);
                        comment.setString(str);
                        comment.setAuthor("ADM");
                        celda.setCellValue("PERIODO");
                        celda.setCellComment(comment);
                    } else if (f == 0 && c == 11) {
                        RichTextString str = factory.createRichTextString("ADM:\nCampo Obligatorio \n - Id del estado \n -Debe tener 2 caracteres.");
                        str.applyFont(font1);
                        str.applyFont(0, 19, font);
                        comment.setString(str);
                        comment.setAuthor("ADM");
                        celda.setCellValue("IDESTADO");
                        celda.setCellComment(comment);
                    } else if (f == 0 && c == 12) {
                        RichTextString str = factory.createRichTextString("ADM:\nCampo Obligatorio \n - Id del cliente o proveedor \n -Debe tener 11 caracteres.");
                        str.applyFont(font1);
                        str.applyFont(0, 19, font);
                        comment.setString(str);
                        comment.setAuthor("ADM");
                        celda.setCellValue("IDCLIEPROV");
                        celda.setCellComment(comment);
                    } else if (f == 0 && c == 13) {
                        RichTextString str = factory.createRichTextString("ADM:\nCampo Obligatorio \n - Numero de Paleta \n -Debe tener 20 caracteres.");
                        str.applyFont(font1);
                        str.applyFont(0, 19, font);
                        comment.setString(str);
                        comment.setAuthor("ADM");
                        celda.setCellValue("NROPALETA");
                        celda.setCellComment(comment);
                    } else if (f == 0 && c == 14) {
                        RichTextString str = factory.createRichTextString("ADM:\nCampo Obligatorio \n - Id de envase \n -Debe tener 3 caracteres.");
                        str.applyFont(font1);
                        str.applyFont(0, 19, font);
                        comment.setString(str);
                        comment.setAuthor("ADM");
                        celda.setCellValue("IDENVASE");
                        celda.setCellComment(comment);
                    } else if (f == 0 && c == 15) {
                        RichTextString str = factory.createRichTextString("ADM:\nCampo Obligatorio \n - Id la sucursal \n -Debe tener 3 caracteres.");
                        str.applyFont(font1);
                        str.applyFont(0, 19, font);
                        comment.setString(str);
                        comment.setAuthor("ADM");
                        celda.setCellValue("IDSUCURSAL");
                        celda.setCellComment(comment);
                    } else if (f == 0 && c == 16) {
                        RichTextString str = factory.createRichTextString("ADM:\nCampo Obligatorio \n - Id del almacen. \n -Debe tener 3 caracteres.");
                        str.applyFont(font1);
                        str.applyFont(0, 19, font);
                        comment.setString(str);
                        comment.setAuthor("ADM");
                        celda.setCellValue("IDALMACEN");
                        celda.setCellComment(comment);
                    } else if (f == 0 && c == 17) {
                        RichTextString str = factory.createRichTextString("ADM:\nCampo Obligatorio \n - Id del embalaje. \n -Debe tener 10 caracteres.");
                        str.applyFont(font1);
                        str.applyFont(0, 19, font);
                        comment.setString(str);
                        comment.setAuthor("ADM");
                        celda.setCellValue("IDEMBALAJE");
                        celda.setCellComment(comment);
                    } else if (f == 0 && c == 18) {
                        RichTextString str = factory.createRichTextString("ADM:\nCampo Obligatorio \n - Id de cultivo. \n -Debe tener 4 caracteres.");
                        str.applyFont(font1);
                        str.applyFont(0, 19, font);
                        comment.setString(str);
                        comment.setAuthor("ADM");
                        celda.setCellValue("IDCULTIVO");
                        celda.setCellComment(comment);
                    } else if (f == 0 && c == 19) {
                        RichTextString str = factory.createRichTextString("ADM:\nCampo Obligatorio \n - id de Variadd. \n -Debe tener 3 caracteres.");
                        str.applyFont(font1);
                        str.applyFont(0, 19, font);
                        comment.setString(str);
                        comment.setAuthor("ADM");
                        celda.setCellValue("IDVARIEDAD");
                        celda.setCellComment(comment);
                    } else if (f == 0 && c == 20) {
                        RichTextString str = factory.createRichTextString("ADM:\nCampo Obligatorio \n - Observaciones sobre la paleta \n -como maximo 240 caracteres.");
                        str.applyFont(font1);
                        str.applyFont(0, 19, font);
                        comment.setString(str);
                        comment.setAuthor("ADM");
                        celda.setCellValue("OBSERVACIONES");
                        celda.setCellComment(comment);
                    } else if (f == 0 && c == 21) {
                        RichTextString str = factory.createRichTextString("ADM:\nCampo Obligatorio \n -Nombre de la venta \n como maximo 50 caracteres.");
                        str.applyFont(font1);
                        str.applyFont(0, 19, font);
                        comment.setString(str);
                        comment.setAuthor("ADM");
                        celda.setCellValue("VENTANA");
                        celda.setCellComment(comment);
                    } else if (f == 0 && c == 22) {
                        RichTextString str = factory.createRichTextString("ADM:\nCampo Obligatorio \n - Cantidad. \n - 15 numeros y 2 decimales como maximo.");
                        str.applyFont(font1);
                        str.applyFont(0, 19, font);
                        comment.setString(str);
                        comment.setAuthor("ADM");
                        celda.setCellValue("CANTIDAD");
                        celda.setCellComment(comment);
                    } else if (f == 0 && c == 23) {
                        RichTextString str = factory.createRichTextString("ADM:\nCampo Obligatorio \n - Estado de la paleta \n- 1 = cerrado, 0 = Abierto.");
                        str.applyFont(font1);
                        str.applyFont(0, 19, font);
                        comment.setString(str);
                        comment.setAuthor("ADM");
                        celda.setCellValue("CERRADO");
                        celda.setCellComment(comment);
                    } else if (f == 0 && c == 24) {
                        RichTextString str = factory.createRichTextString("ADM:\nCampo Obligatorio \n - Sincroniza \n - N = no , S = si.");
                        str.applyFont(font1);
                        str.applyFont(0, 19, font);
                        comment.setString(str);
                        comment.setAuthor("ADM");
                        celda.setCellValue("SINCRONIZA");
                        celda.setCellComment(comment);
                    } else if (f == 0 && c == 25) {
                        RichTextString str = factory.createRichTextString("ADM:\nCampo Obligatorio \n - Formato YYYY/MM/DD.");
                        str.applyFont(font1);
                        str.applyFont(0, 19, font);
                        comment.setString(str);
                        comment.setAuthor("ADM");
                        celda.setCellValue("FECHACREACION");
                        celda.setCellComment(comment);
                    } else if (f == 0 && c == 26) {
                        RichTextString str = factory.createRichTextString("ADM:\nCampo Obligatorio \n - Numero de Manural\n Debe tener 10 caracteres.");
                        str.applyFont(font1);
                        str.applyFont(0, 19, font);
                        comment.setString(str);
                        comment.setAuthor("ADM");
                        celda.setCellValue("NROMANUAL");
                        celda.setCellComment(comment);
                    } else if (f == 0 && c == 27) {
                        RichTextString str = factory.createRichTextString("ADM:\nCampo Obligatorio \n - idcliepro-destino\n debe tener 11 caracteres");
                        str.applyFont(font1);
                        str.applyFont(0, 19, font);
                        comment.setString(str);
                        comment.setAuthor("ADM");
                        celda.setCellValue("IDCLIEPROV_DESTINO");
                        celda.setCellComment(comment);
                    } else if (f == 0 && c == 28) {
                        RichTextString str = factory.createRichTextString("ADM:\nCampo Obligatorio \n - Tipo de paleta\n debe tener 1 caraccter..");
                        str.applyFont(font1);
                        str.applyFont(0, 19, font);
                        comment.setString(str);
                        comment.setAuthor("ADM");
                        celda.setCellValue("TIPO");
                        celda.setCellComment(comment);
                    }
                }
            }
            hoja.autoSizeColumn((short) 0);
            hoja.autoSizeColumn((short) 1);
            hoja.autoSizeColumn((short) 2);
            hoja.autoSizeColumn((short) 3);
            hoja.autoSizeColumn((short) 4);
            hoja.autoSizeColumn((short) 5);
            hoja.autoSizeColumn((short) 6);
            hoja.autoSizeColumn((short) 7);
            hoja.autoSizeColumn((short) 8);
            hoja.autoSizeColumn((short) 9);
            hoja.autoSizeColumn((short) 10);
            hoja.autoSizeColumn((short) 11);
            hoja.autoSizeColumn((short) 12);
            hoja.autoSizeColumn((short) 13);
            hoja.autoSizeColumn((short) 14);
            hoja.autoSizeColumn((short) 15);
            hoja.autoSizeColumn((short) 16);
            hoja.autoSizeColumn((short) 17);
            hoja.autoSizeColumn((short) 18);
            hoja.autoSizeColumn((short) 19);
            hoja.autoSizeColumn((short) 20);
            hoja.autoSizeColumn((short) 21);
            hoja.autoSizeColumn((short) 22);
            hoja.autoSizeColumn((short) 23);
            hoja.autoSizeColumn((short) 24);
            hoja.autoSizeColumn((short) 25);
            hoja.autoSizeColumn((short) 26);
            hoja.autoSizeColumn((short) 27);
            hoja.autoSizeColumn((short) 28);
            for (int f = 0; f < 2; f++) {
                XSSFRow fila2 = hoja2.createRow(f);
                if (f == 0) {
                    for (int c = 0; c < 15; c++) {
                        XSSFCell celda2 = fila2.createCell(c);
                        anchor12.setCol1(celda2.getColumnIndex());
                        anchor12.setCol2(celda2.getColumnIndex() + 8);
                        anchor12.setRow1(fila2.getRowNum());
                        anchor12.setRow2(fila2.getRowNum() + 8);
                        Comment comment2 = drawing2.createCellComment(anchor12);
                        RichTextString str;
                        switch (c) {
                            case 0:
                                celda2.setCellStyle(style2);
                                str = factory2.createRichTextString("ADM:\nCampo Obligatorio \n - El Código debe de ser único.");
                                str.applyFont(font12);
                                str.applyFont(0, 19, font2);
                                comment2.setString(str);
                                comment2.setAuthor("ADM");
                                celda2.setCellValue("IDEMPRESA");
                                celda2.setCellComment(comment2);
                                break;
                            case 1:
                                celda2.setCellStyle(style2);
                                str = factory.createRichTextString("ADM:\nCampo Obligatorio ");
                                str.applyFont(font12);
                                str.applyFont(0, 19, font2);
                                comment2.setString(str);
                                comment2.setAuthor("ADM");
                                celda2.setCellValue("IDREGISTROPALETA");
                                celda2.setCellComment(comment2);
                                break;
                            case 2:
                                celda2.setCellStyle(style2);
                                str = factory.createRichTextString("ADM:\nCampo Obligatorio ");
                                str.applyFont(font12);
                                str.applyFont(0, 19, font2);
                                comment2.setString(str);
                                comment2.setAuthor("ADM");
                                celda2.setCellValue("ITEM");
                                celda2.setCellComment(comment2);
                                break;
                            case 3:
                                celda2.setCellStyle(style2);
                                str = factory.createRichTextString("ADM:\nCampo Obligatorio ");
                                str.applyFont(font12);
                                str.applyFont(0, 19, font2);
                                comment2.setString(str);
                                comment2.setAuthor("ADM");
                                celda2.setCellValue("IDCLIEPROV");
                                celda2.setCellComment(comment2);
                                break;
                            case 4:
                                celda2.setCellStyle(style2);
                                str = factory.createRichTextString("ADM:\nCampo Obligatorio");
                                str.applyFont(font12);
                                str.applyFont(0, 19, font2);
                                comment2.setString(str);
                                comment2.setAuthor("ADM");
                                celda2.setCellValue("IDLOTE");
                                celda2.setCellComment(comment2);
                                break;
                            case 5:
                                celda2.setCellStyle(style2);
                                str = factory.createRichTextString("ADM:\nCampo Obligatorio");
                                str.applyFont(font12);
                                str.applyFont(0, 19, font2);
                                comment2.setString(str);
                                comment2.setAuthor("ADM");
                                celda2.setCellValue("IDCONDICION");
                                celda2.setCellComment(comment2);
                                break;
                            case 6:
                                celda2.setCellStyle(style2);
                                str = factory.createRichTextString("ADM:\nCampo Obligatorio");
                                str.applyFont(font12);
                                str.applyFont(0, 19, font2);
                                comment2.setString(str);
                                comment2.setAuthor("ADM");
                                celda2.setCellValue("IDTALLA");
                                celda2.setCellComment(comment2);
                                break;
                            case 7:
                                celda2.setCellStyle(style2);
                                str = factory.createRichTextString("ADM:\nCampo Obligatorio");
                                str.applyFont(font12);
                                str.applyFont(0, 19, font2);
                                comment2.setString(str);
                                comment2.setAuthor("ADM");
                                celda2.setCellValue("IDCOLOR");
                                celda2.setCellComment(comment2);
                                break;
                            case 8:
                                celda2.setCellStyle(style2);
                                str = factory.createRichTextString("ADM:\nCampo Obligatorio");
                                str.applyFont(font12);
                                str.applyFont(0, 19, font2);
                                comment2.setString(str);
                                comment2.setAuthor("ADM");
                                celda2.setCellValue("CANTIDAD");
                                celda2.setCellComment(comment2);
                                break;
                            case 9:
                                celda2.setCellStyle(style2);
                                str = factory.createRichTextString("ADM:\nCampo Obligatorio");
                                str.applyFont(font12);
                                str.applyFont(0, 19, font2);
                                comment2.setString(str);
                                comment2.setAuthor("ADM");
                                celda2.setCellValue("IDEMBALAJE");
                                celda2.setCellComment(comment2);
                                break;
                            case 10:
                                celda2.setCellStyle(style2);
                                str = factory.createRichTextString("ADM:\nCampo Obligatorio");
                                str.applyFont(font12);
                                str.applyFont(0, 19, font2);
                                comment2.setString(str);
                                comment2.setAuthor("ADM");
                                celda2.setCellValue("IDPRODUCTO");
                                celda2.setCellComment(comment2);
                                break;
                            case 11:
                                celda2.setCellStyle(style2);
                                str = factory.createRichTextString("ADM:\nCampo Obligatorio");
                                str.applyFont(font12);
                                str.applyFont(0, 19, font2);
                                comment2.setString(str);
                                comment2.setAuthor("ADM");
                                celda2.setCellValue("IDLOTEP");
                                celda2.setCellComment(comment2);
                                break;
                            case 12:
                                celda2.setCellStyle(style2);
                                str = factory.createRichTextString("ADM:\nCampo Obligatorio");
                                str.applyFont(font12);
                                str.applyFont(0, 19, font2);
                                comment2.setString(str);
                                comment2.setAuthor("ADM");
                                celda2.setCellValue("IDCONSUMIDOR");
                                celda2.setCellComment(comment2);
                                break;
                            case 13:
                                celda2.setCellStyle(style2);
                                str = factory.createRichTextString("ADM:\nCampo Obligatorio");
                                str.applyFont(font12);
                                str.applyFont(0, 19, font2);
                                comment2.setString(str);
                                comment2.setAuthor("ADM");
                                celda2.setCellValue("IDLOTECAMPO");
                                celda2.setCellComment(comment2);
                                break;
                            case 14:
                                celda2.setCellStyle(style2);
                                str = factory.createRichTextString("ADM:\nCampo Obligatorio");
                                str.applyFont(font12);
                                str.applyFont(0, 19, font2);
                                comment2.setString(str);
                                comment2.setAuthor("ADM");
                                celda2.setCellValue("IDPRESENTACION");
                                celda2.setCellComment(comment2);
                                break;
                        }
                    }
                }
            }
            hoja2.autoSizeColumn((short) 0);
            hoja2.autoSizeColumn((short) 1);
            hoja2.autoSizeColumn((short) 2);
            hoja2.autoSizeColumn((short) 3);
            hoja2.autoSizeColumn((short) 4);
            hoja2.autoSizeColumn((short) 5);
            hoja2.autoSizeColumn((short) 6);
            hoja2.autoSizeColumn((short) 7);
            hoja2.autoSizeColumn((short) 8);
            hoja2.autoSizeColumn((short) 9);
            hoja2.autoSizeColumn((short) 10);
            hoja2.autoSizeColumn((short) 11);
            hoja2.autoSizeColumn((short) 12);
            hoja2.autoSizeColumn((short) 13);
            hoja2.autoSizeColumn((short) 14);
            hoja2.autoSizeColumn((short) 15);
            libro.write(file2);
            file2.close();
            stream = new FileInputStream(new File(rutaArchivo));
            arch = new DefaultStreamedContent(stream, "application/xlsx", "FI_REGISTROPALE.xlsx");
        } catch (FileNotFoundException ex) {
            System.out.println("Error al Descargar : " + ex.getMessage());
        }
        return arch;
    }

//    public StreamedContent downExcel() throws ParseException, IOException {
//        InputStream stream = null;
//        StreamedContent arch = null;
////        try {
////            String folder = "C:\\SOLUTION\\WEB\\DATOS_EXPORTACION";
////            File ruta = new File(folder);
////            if (!ruta.isDirectory()) {
////                ruta.mkdirs();
////            }
////            String rutaArchivo = folder + "\\DE_MULTITABLA.xlsx";
////            File fileXls = new File(rutaArchivo);
////            if (fileXls.exists()) {
////                fileXls.delete();
////            }
////            fileXls.createNewFile();
////
////            Workbook libro = new XSSFWorkbook();
////
////            FileOutputStream file = new FileOutputStream(fileXls);
////
////            Sheet hoja = libro.createSheet("MULTITABLA");
////            hoja = libro.getSheetAt(0);
////
////            CellStyle style = libro.createCellStyle();
////            Font font = libro.createFont();
////            font.setBoldweight(Font.BOLDWEIGHT_BOLD);
////            font.setFontHeightInPoints((short) 8);
////            font.setFontName("Arial");
////            style.setAlignment(CellStyle.VERTICAL_CENTER);
////            style.setAlignment(CellStyle.ALIGN_CENTER);
////            style.setFont(font);
////
////            CellStyle style2 = libro.createCellStyle();
////            Font font2 = libro.createFont();
////            font2.setFontHeightInPoints((short) 8);
////            font2.setFontName("Arial");
////            style2.setFont(font2);
////
////            for (int f = 0; f <= listMultitablaTabla.size(); f++) {
////                Row fila = hoja.createRow(f);
////                for (int c = 0; c < 3; c++) {
////                    Cell celda = fila.createCell(c);
////                    if (f == 0) {
////                        celda.setCellStyle(style);
////                        if (c == 0) {
////                            celda.setCellValue("CÓDIGO");
////                        } else if (c == 1) {
////                            celda.setCellValue("DESCRIPCIÓN");
////                        } else if (c == 2) {
////                            celda.setCellValue("ESTADO");
////                        }
////                    } else {
////                        celda.setCellStyle(style2);
////                        if (c == 0) {
////                            celda.setCellValue(getListaDatos().get(f - 1).getIdcultivo());
////                        } else if (c == 1) {
////                            celda.setCellValue(getListaDatos().get(f - 1).getDescripcion());
////                        } else if (c == 2) {
////                            String terminado = "";
////                            if (getListaDatos().get(f - 1).getEstado() == 1) {
////                                terminado = "ACTIVO";
////                            } else {
////                                terminado = "ANULADO";
////                            }
////                            celda.setCellValue(terminado);
////                        }
////                    }
////                }
////            }
////            hoja.autoSizeColumn((short) 0);
////            hoja.autoSizeColumn((short) 1);
////            hoja.autoSizeColumn((short) 2);
////            libro.write(file);
////            file.close();
////            stream = new FileInputStream(new File(rutaArchivo));
////            arch = new DefaultStreamedContent(stream, "application/xlsx", "NSR_MRP_DE_CULTIVO.xlsx");
////        } catch (FileNotFoundException ex) {
////            System.out.println("Error al Obtener Datos : " + ex.getMessage());
////        }
////        return arch;
//    }
    public void upExcel(FileUploadEvent event) throws ParseException, Exception {
        try {
            listPaletaUp = new ArrayList<Paleta>();
            listDPaletaUp = new ArrayList<Dpaleta>();
            listerros = new ArrayList<Object[]>();
            listderros = new ArrayList<Object[]>();
            upFile = event.getFile();
            XSSFWorkbook workBook = new XSSFWorkbook(event.getFile().getInputstream());
            XSSFSheet hssfSheet = workBook.getSheetAt(0);
            XSSFSheet hssfSheetD = workBook.getSheetAt(1);
            Iterator<Row> rowIterator = hssfSheet.rowIterator();            
            boolean exist = false;
            int filaDuplicada = 0;
            int filaDuplicadaD = 0;
            boolean estado = false;
            boolean firstLinea = true;
            int k=2;
            while (rowIterator.hasNext()) {
                Row hssfRow = rowIterator.next();
                
                if (firstLinea) {
                    firstLinea = false;
                } else {
                    Paleta xls = new Paleta();
                    xls.setIdempresa(hssfRow.getCell(0).getStringCellValue());
                    xls.setIdregistropaleta(hssfRow.getCell(1).getStringCellValue());
                    xls.setIdemisor(hssfRow.getCell(2).getStringCellValue());
                    xls.setIdoperacion(hssfRow.getCell(3).getStringCellValue());
                    String tempN = null;
                    if (hssfRow.getCell(4) != null) {
                        tempN = hssfRow.getCell(4).getStringCellValue();
                    }
                    xls.setNumoperacion(tempN);
                    xls.setIdmotivopaleta(hssfRow.getCell(5).getStringCellValue());
                    xls.setIddocumento(hssfRow.getCell(6).getStringCellValue());
                    xls.setSerie(hssfRow.getCell(7).getStringCellValue());
                    xls.setNumero(hssfRow.getCell(8).getStringCellValue());
                    //de string a timestamp y a string denuevo
                    DateFormat formatter;
//                    formatter = new SimpleDateFormat("dd/MM/yyyy");
//                    Date date = (Date) formatter.parse();
                    Timestamp timeStampDate = new Timestamp(hssfRow.getCell(9).getDateCellValue().getTime());

                    SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
                    String Fecha = dateFormat.format(timeStampDate);
                    xls.setFecha(Fecha);
                    xls.setPeriodo(String.valueOf(hssfRow.getCell(10).getNumericCellValue()));
                    xls.setIdestado(hssfRow.getCell(11).getStringCellValue());
                    xls.setIdclieprov(String.valueOf(hssfRow.getCell(12).getNumericCellValue()));
                    xls.setNropaleta(hssfRow.getCell(13).getStringCellValue());
                    xls.setIdenvase(hssfRow.getCell(14).getStringCellValue());
                    hssfRow.getCell(15).setCellType(XSSFCell.CELL_TYPE_STRING);
                    xls.setIdsucursal((String) hssfRow.getCell(15).getStringCellValue());
                    String tempA = null;
                    if (hssfRow.getCell(16) != null) {
                        tempA = hssfRow.getCell(16).getStringCellValue();
                    }
                    xls.setIdalmacen(tempA);
                    String tempEm = null;
                    if (hssfRow.getCell(17) != null) {
                        tempEm = hssfRow.getCell(17).getStringCellValue();
                    }
                    xls.setIdembalaje(tempEm);
                    xls.setIdcultivo(hssfRow.getCell(18).getStringCellValue());
                    xls.setIdvariedad(hssfRow.getCell(19).getStringCellValue());
                    String tempO = null;
                    if (hssfRow.getCell(20) != null) {
                        tempO = hssfRow.getCell(20).getStringCellValue();
                    }
                    xls.setObservaciones(tempO);
                    xls.setVentana(hssfRow.getCell(21).getStringCellValue());
                    xls.setCantidad(String.valueOf(hssfRow.getCell(22).getNumericCellValue()));
                    xls.setCerrado(String.valueOf(hssfRow.getCell(23).getNumericCellValue()));
                    xls.setSincroniza(hssfRow.getCell(24).getStringCellValue());
                    //de string a timestamp y a string denuevo
                    Timestamp timeStampDate2 = new Timestamp(hssfRow.getCell(25).getDateCellValue().getTime());
                    String FechaC = dateFormat.format(timeStampDate2);
                    xls.setFechacreacion(FechaC);
                    String temp = null;
                    if (hssfRow.getCell(26) != null) {
                        temp = hssfRow.getCell(26).getStringCellValue();
                    }
                    xls.setNromanual(temp);
                    hssfRow.getCell(27).setCellType(XSSFCell.CELL_TYPE_STRING);
                    System.out.print(xls.getIdregistropaleta() + " " + (String) hssfRow.getCell(27).getStringCellValue());
                    xls.setIdclieprov_destino((String) hssfRow.getCell(27).getStringCellValue());
                    xls.setTipo(hssfRow.getCell(28).getStringCellValue());
                    exist = false;                   
                    boolean vali = validarPaleExcel(xls,k);
                    if(vali){
                        listPaletaUp.add(xls);
                    }
                     for (int i = 0; i < listPaletaUp.size() - 1; i++) {
                        if ((listPaletaUp.get(i).getIdempresa().equalsIgnoreCase(hssfRow.getCell(0).getStringCellValue()) && listPaletaUp.get(i).getIdregistropaleta().equalsIgnoreCase(hssfRow.getCell(1).getStringCellValue())) && hssfRow.getRowNum() > 1) {
                            exist = true;
                            break;
                        }
                    }
                    if (exist) {
                        filaDuplicada = hssfRow.getRowNum() + 1;
                        break;
                    }
                }
                k++;
            }
            if (exist) {
                WebUtil.MensajeAlerta("Registro Duplicado. Fila : " + filaDuplicada + ". \n Verifique el Excel e Inténtelo otra vez.");
                listPaletaUp.clear();
            }
            Iterator<Row> rowIteratorD = hssfSheetD.rowIterator();
            firstLinea = true;
            exist = false;
            int l = 0;
            while (rowIteratorD.hasNext()) {
                Row hssfRow = rowIteratorD.next();
                if (firstLinea) {
                    firstLinea = false;
                } else {
                    Dpaleta xls = new Dpaleta();
                    xls.setIdempresa((int)hssfRow.getCell(0).getNumericCellValue());
                    xls.setIdregistropaleta(hssfRow.getCell(1).getStringCellValue());
                    xls.setItem(hssfRow.getCell(2).getStringCellValue());
                    xls.setIdclieprov(String.valueOf(hssfRow.getCell(3).getNumericCellValue()));
                    xls.setIdlote(hssfRow.getCell(4).getStringCellValue());
                    xls.setIdcondicion(hssfRow.getCell(5).getStringCellValue());
                    xls.setIdtalla(hssfRow.getCell(6).getStringCellValue());
                    xls.setIdcolor(hssfRow.getCell(7).getStringCellValue());
                    xls.setCantidad(hssfRow.getCell(8).getNumericCellValue());
                    xls.setIdembalaje(hssfRow.getCell(9).getStringCellValue());
                    hssfRow.getCell(10).setCellType(XSSFCell.CELL_TYPE_STRING);
                    xls.setIdproducto(hssfRow.getCell(10).getStringCellValue());
                    hssfRow.getCell(11).setCellType(XSSFCell.CELL_TYPE_STRING);
                    xls.setIdlotep(hssfRow.getCell(11).getStringCellValue());
                    xls.setIdconsumidor(hssfRow.getCell(12).getStringCellValue());
                    xls.setIdlotecampo(hssfRow.getCell(13).getStringCellValue());
                    xls.setIdpresentacion(hssfRow.getCell(14).getStringCellValue());
                    exist = false;
                    boolean vali = ValidarDPaleExcel(xls,l);
                    if(vali){
                        listDPaletaUp.add(xls);
                    }
                    for (int i = 0; i < listDPaletaUp.size() - 1; i++) {
                        if ((listDPaletaUp.get(i).getIdempresa() == (int)hssfRow.getCell(0).getNumericCellValue()&& listDPaletaUp.get(i).getIdregistropaleta().equalsIgnoreCase(hssfRow.getCell(1).getStringCellValue()) && listDPaletaUp.get(i).getItem().equalsIgnoreCase(hssfRow.getCell(2).getStringCellValue())) && hssfRow.getRowNum() > 1) {
                            exist = true;
                            break;
                        }
                    }
                    if (exist) {
                        filaDuplicada = hssfRow.getRowNum() + 1;
                        break;
                    }
                }
                l++;
            }
            if (exist) {
                WebUtil.MensajeAlerta("Registro Duplicado. Fila : " + filaDuplicadaD + ". \n Verifique el Excel De Detalle e Inténtelo otra vez.");
                listPaletaUp.clear();
            }
            RequestContext.getCurrentInstance().update(":datos:tbas:tblRepet_data"); 
            RequestContext.getCurrentInstance().update(":datos:tbas:tblDRepet"); 
            RequestContext.getCurrentInstance().execute("PF('dlgIngrPro').show()");
        } catch (IOException e) {
            System.out.println("Error en el Procesamiento : " + e.getMessage());
        }
    }

    public void saveExcel() throws IOException, Exception {
        String p = null;
        if (listPaletaUp.size() > 0) {
            for (Paleta mul : listPaletaUp) {
                try {
                    List<Dpaleta> temp = new ArrayList<Dpaleta>();
                    if (listDPaletaUp.size() > 0) {
                        for(Dpaleta dp : listDPaletaUp){
                            if(Integer.valueOf(mul.getIdempresa())==dp.getIdempresa() && mul.getIdregistropaleta().equalsIgnoreCase(dp.getIdregistropaleta())){
                                temp.add(dp);
                            }
                        }
                    }
                     p = paletaService.grabarxmlPaleta(mul, temp);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            WebUtil.MensajeAlerta("Registros Importados con Éxito");
            listPaletaUp.clear();
        } else {
            WebUtil.MensajeAlerta("No hay elementos para importar. Intentelo otra vez.");
        }
    }

    public void preProcessPDF(Object document) throws IOException {
        Document pdf = (Document) document;
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String logo = servletContext.getRealPath("") + File.separator + "images" + File.separator + "20314727500.png";
//        pdf.add(Image.getInstance(logo)));
    }
    public boolean validarPaleExcel(Paleta test,int k) throws Exception{
        Object[] obj = new Object[3];
        if(paletaService.valiPale(test)){
            obj[0]=k;
            obj[1]=test.getIdregistropaleta();
            obj[2]="Ya se encuentra en base de Datos";
            listerros.add(obj);
            return false;
        }
        if(test.getCantidad().isEmpty() || test.getCantidad()==null || test.getCantidad().equalsIgnoreCase("0")){
            obj[0]=k;
            obj[1]=test.getIdregistropaleta();
            obj[2]="Ya se encuentra en base de Datos";
            listerros.add(obj);
            return false;
        }
        if(test.getCerrado().isEmpty() || test.getCerrado()==null){
            obj[0]=k;
            obj[1]=test.getIdregistropaleta();
            obj[2]="Ya se encuentra en base de Datos";
            listerros.add(obj);
            return false;
        }
        if(test.getSincroniza().isEmpty() || test.getSincroniza()==null){
            obj[0]=k;
            obj[1]=test.getIdregistropaleta();
            obj[2]="Ya se encuentra en base de Datos";
            listerros.add(obj);
            return false;
        }
        if(test.getFechacreacion().isEmpty() || test.getFechacreacion()==null){
            obj[0]=k;
            obj[1]=test.getIdregistropaleta();
            obj[2]="Ya se encuentra en base de Datos";
            listerros.add(obj);
            return false;
        }
        return true;
    }
    public boolean ValidarDPaleExcel(Dpaleta test,int fil) throws Exception{
        Object[] obj = new Object[3];
        if(paletaService.valiDPale(test)){
            obj[0]=fil;
            obj[1]=test.getIdregistropaleta();
            obj[2]="Ya se encuentra en base de Datos";
            listderros.add(obj);
            return false;
        }
        return true;
    }
    /**
     * @return the listPaleta
     */
    public List<Paleta> getListPaleta() {
        return listPaleta;
    }

    /**
     * @param listPaleta the listPaleta to set
     */
    public void setListPaleta(List<Paleta> listPaleta) {
        this.listPaleta = listPaleta;
    }

    /**
     * @return the idEmpresa
     */
    public String getIdEmpresa() {
        return idEmpresa;
    }

    /**
     * @param idEmpresa the idEmpresa to set
     */
    public void setIdEmpresa(String idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    /**
     * @return the idCampania
     */
    public String getIdCampania() {
        return idCampania;
    }

    /**
     * @param idCampania the idCampania to set
     */
    public void setIdCampania(String idCampania) {
        this.idCampania = idCampania;
    }

    /**
     * @return the paletaService
     */
    public PaletaService getPaletaService() {
        return paletaService;
    }

    /**
     * @param paletaService the paletaService to set
     */
    public void setPaletaService(PaletaService paletaService) {
        this.paletaService = paletaService;
    }

    public UploadedFile getUpFile() {
        return upFile;
    }

    public void setUpFile(UploadedFile upFile) {
        this.upFile = upFile;
    }

    public List<Paleta> getListPaletaUp() {
        return listPaletaUp;
    }

    public void setListPaletaUp(List<Paleta> listPaletaUp) {
        this.listPaletaUp = listPaletaUp;
    }

    public List<Dpaleta> getListDPaletaUp() {
        return listDPaletaUp;
    }

    public void setListDPaletaUp(List<Dpaleta> listDPaletaUp) {
        this.listDPaletaUp = listDPaletaUp;
    }

    public List<Object[]> getListerros() {
        return listerros;
    }

    public void setListerros(List<Object[]> listerros) {
        this.listerros = listerros;
    }

    public List<Object[]> getListderros() {
        return listderros;
    }

    public void setListderros(List<Object[]> listderros) {
        this.listderros = listderros;
    }
    
}
