package com.ameat.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
 * excel读写工具类 */
public class Jexcel {

    private static Logger logger  = Logger.getLogger(Jexcel.class);
    private final static String xls = "xls";
    private final static String xlsx = "xlsx";
    private final static String excelExportDir = ConfigurationLoader.config("application.excelexportdir");

    /**
     * Read Excel File And Return a List
     * @param file: *.xls, *.xlsx
     * @param sheetIndex: which sheet would be read
     * @return List<String[]>
     */
    public static List<String[]> readExcel(String filePath, int sheetIndex){
		String newFilePath = UnifyFileName.convert(filePath);
        List<String[]> list = new ArrayList<String[]>();

		try {
			//check if file exists
	        checkFile(newFilePath);

	        Workbook workbook = getWorkBook(newFilePath);
	        if(workbook != null){
                Sheet sheet = workbook.getSheetAt(sheetIndex);
                if(sheet != null){
                		int firstRowNum  = sheet.getFirstRowNum();
                    int lastRowNum = sheet.getLastRowNum();
                    for(int rowNum = firstRowNum; rowNum <= lastRowNum; rowNum++){
                        Row row = sheet.getRow(rowNum);
                        if(row == null){
                            continue;
                        }
                        int firstCellNum = row.getFirstCellNum();
                        int lastCellNum = row.getPhysicalNumberOfCells();
                        String[] cells = new String[lastCellNum];
                        for(int cellNum = firstCellNum; cellNum < lastCellNum; cellNum++){
                            Cell cell = row.getCell(cellNum);
                            cells[cellNum] = getCellValue(cell);
                        }
                        list.add(cells);
                    }
                }
	            workbook.close();
	        }
		}catch(IOException e) {
	    		e.printStackTrace();
	    }

		logger.info("read file: " +newFilePath+ " finish!" );

		return list;
    }


    private static void checkFile(String filePath) throws IOException{
    		File file = new File(filePath);

    		if(!file.exists()){
            logger.error("file not exist");
            throw new FileNotFoundException("file not exist！");
        }

        String fileName = file.getName();

        if(!fileName.endsWith(xls) && !fileName.endsWith(xlsx)){
            logger.error(fileName + "not excel file");
            throw new IOException(fileName + "not excel file");
        }
    }

    /**
     *
     * @param filePath
     * @return
     */
    private static Workbook getWorkBook(String filePath) {
    		File file = new File(filePath);

        String fileName = file.getName();
        Workbook workbook = null;
        try {
    			FileInputStream in = new FileInputStream(file);

            if(fileName.endsWith(xls)){
                workbook = new HSSFWorkbook(in);
            }else if(fileName.endsWith(xlsx)){
                workbook = new XSSFWorkbook(in);
            }
        }catch(IOException e){
            logger.info(e.getMessage());
        }

        return workbook;
    }


    private static String getCellValue(Cell cell){
        String cellValue = "";
        if(cell == null){
            return cellValue;
        }
        switch (cell.getCellType()){
            case Cell.CELL_TYPE_NUMERIC: //numeric
            		double value = cell.getNumericCellValue();
            		short format = cell.getCellStyle().getDataFormat();
            		if(format == 14 || format == 31 || format == 57 || format == 58){
            			//date
            			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            			Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);
            			cellValue = sdf.format(date);
            		}else if (format == 20 || format == 32) {
            			//time
            			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            			Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);
            			cellValue = sdf.format(date);
            		} else {
            			cellValue = String.valueOf(cell.getNumericCellValue());
            		}
                break;
            case Cell.CELL_TYPE_STRING: //string
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case Cell.CELL_TYPE_BOOLEAN: //boolean
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA: //formula
                try {
                		cellValue = String.valueOf(cell.getNumericCellValue());
                } catch (Exception e) {
                		cellValue = String.valueOf(cell.getRichStringCellValue());
                }
                break;
            case Cell.CELL_TYPE_BLANK: //blank
                cellValue = "";
                break;
            case Cell.CELL_TYPE_ERROR: //error
                cellValue = "invalid syntax";
                break;
            default:
                cellValue = "unknown error";
                break;
        }
        return cellValue;
    }

    /**
     * Print The Sheet Data
     * @param datas
     */
    public static void print(List<String[]> datas) {
		for(String[] cells: datas) {
			System.out.println(Arrays.toString(cells));
		}
    }


    /**
     * Create Workbook
     * @param type          Excel Type, 97-2003 or 2007
     * @return
     * @throws IOException
     */
    private static Workbook createWorkBook(String type) {
        Workbook workbook = null;

        if(type == xlsx) {
        		workbook = new XSSFWorkbook();
        } else {
        		workbook = new HSSFWorkbook();
        }
        return workbook;
    }

    /**
     * @param wb
     * @return
     */
    private static CellStyle createHeadCellStyle(Workbook wb) {
        CellStyle cellStyle = wb.createCellStyle();
        addAlignStyle(cellStyle, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER);
        addBorderStyle(cellStyle, CellStyle.BORDER_MEDIUM, IndexedColors.BLACK.getIndex());
        addColor(cellStyle, IndexedColors.GREY_25_PERCENT.getIndex(), CellStyle.SOLID_FOREGROUND);
        return cellStyle;
    }

    /**
     * @param wb
     * @return
     */
    private static CellStyle createDefaultCellStyle(Workbook wb) {
        CellStyle cellStyle = wb.createCellStyle();
        addAlignStyle(cellStyle, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER);
        addBorderStyle(cellStyle, CellStyle.BORDER_THIN, IndexedColors.BLACK.getIndex());
        return cellStyle;
    }

    /**
     * @param cellStyle
     * @param halign
     * @param valign
     * @return
     */
    private static CellStyle addAlignStyle(CellStyle cellStyle,
                                        short halign, short valign) {
        cellStyle.setAlignment(halign);
        cellStyle.setVerticalAlignment(valign);
        return cellStyle;
    }

    /**
     * @param cellStyle
     * @return
     */
    private static CellStyle addBorderStyle(CellStyle cellStyle, short borderSize, short colorIndex) {
        cellStyle.setBorderBottom(borderSize);
        cellStyle.setBottomBorderColor(colorIndex);
        cellStyle.setBorderLeft(borderSize);
        cellStyle.setLeftBorderColor(colorIndex);
        cellStyle.setBorderRight(borderSize);
        cellStyle.setRightBorderColor(colorIndex);
        cellStyle.setBorderTop(borderSize);
        cellStyle.setTopBorderColor(colorIndex);
        return cellStyle;
    }

    /**
     * @param cellStyle
     * @param backgroundColor
     * @param fillPattern
     * @return
     */
    private static CellStyle addColor(CellStyle cellStyle,
                                short backgroundColor, short fillPattern ) {
        cellStyle.setFillForegroundColor(backgroundColor);
        cellStyle.setFillPattern(fillPattern);
        return cellStyle;
    }

    private static Row createRow(Sheet sheet, int rowNum) {
        return sheet.createRow(rowNum);
    }

    private static Cell createCell(Row row, int column) {
        return row.createCell(column);
    }

    /**
     * Get Excel Datas
     * @param getDataFunc
     * @return
     */
    @SuppressWarnings("unchecked")
	private static List<Map<String, Object>> getDatas(String getDataFunc, Object[] args) {
        String className = getDataFunc.split(":")[0];
        String methodName = getDataFunc.split(":")[1];
        ArrayList<Map<String, Object>> list = null;

        try {
            Class<?> getDataClass = Class.forName(className);
            Object o = getDataClass.newInstance();

            @SuppressWarnings("rawtypes")
			Class[] argsClass = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                argsClass[i] = args[i].getClass();
            }

            Method method = getDataClass.getMethod(methodName, argsClass);
            list = (ArrayList<Map<String, Object>>) method.invoke(o, args);
        } catch(Exception e) {
        		e.printStackTrace();
        }

        return  list != null ? list : (ArrayList<Map<String, Object>>) new ArrayList<Map<String, Object>>();

    }

    /**
     *
     * @param sheetName
     * @param fileName
     * @param getDataFunc : "wholeClassName:MethodName"
     * @param headers
     */
    public static void writeExcel(String sheetName, String fileName, String getDataFunc, List<HashMap<String, String>> headers, Object[] args) {
        int rowNum = 0;
        int columnNum = 0;

    		Workbook workbook = createWorkBook(xlsx);
        Sheet sheet = workbook.createSheet(sheetName);
        CellStyle cellStyle = createHeadCellStyle(workbook);
        List<Map<String, Object>> datas = getDatas(getDataFunc, args);

        //write header
        Row headerRow = createRow(sheet, rowNum);
        for(HashMap<String, String> header : headers) {
            Cell cell = createCell(headerRow, columnNum);
            cell.setCellValue(String.valueOf(header.get("value")));
            cell.setCellStyle(cellStyle);
            columnNum++;
        }


        //write body
        rowNum = 1;
        CellStyle defaultStyle = createDefaultCellStyle(workbook);
        for(int i = rowNum; i<datas.size(); i++) {
            Row bodyRow = createRow(sheet, i);

            columnNum = 0;
            for(int j = 0; j< headers.size(); j++) {
                String key = headers.get(j).get("key");
                Cell cell = createCell(bodyRow, columnNum);

                if(isNumber(String.valueOf(datas.get(i).get(key)))) {
                		cell.setCellValue(Float.parseFloat(String.valueOf(datas.get(i).get(key))));
                } else {
                		cell.setCellValue(String.valueOf(datas.get(i).get(key)));
                }

                cell.setCellStyle(defaultStyle);
                columnNum++;
            }
        }

        File dir = new File(excelExportDir);
        if(!dir.exists()) {
            dir.mkdirs();
        }
        //default write xlsx
        String filePath = excelExportDir + fileName + "." + xlsx;
        File file = new File(filePath);
        if(!file.exists()) {
            try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }

        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            workbook.write(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(outputStream != null) {
                try {
                    outputStream.close();
                    logger.info("write file :" +filePath+ " finish!");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            if (value.contains("."))
                return true;
            return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isNumber(String value) {
        return isInteger(value) || isDouble(value);
    }


}
