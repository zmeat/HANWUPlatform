package com.ameat.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;  
import java.io.IOException;  
import java.util.ArrayList;  
import java.util.List;  
  
import org.apache.log4j.Logger;  
import org.apache.poi.hssf.usermodel.HSSFWorkbook;  
import org.apache.poi.ss.usermodel.Cell;  
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
      
    /** 
     * read excel file and return a list 
     * @param file: *.xls, *.xlsx 
     * @throws IOException  
     * @return List<String[]>
     */  
    public static List<String[]> readExcel(String filePath) throws IOException{
		String newFilePath = UnifyFileName.convert(filePath);

        //check if file exists  
        checkFile(newFilePath);  
        
        Workbook workbook = getWorkBook(newFilePath);  
        List<String[]> list = new ArrayList<String[]>();  
        if(workbook != null){  
            for(int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++){  
                Sheet sheet = workbook.getSheetAt(sheetNum);  
                if(sheet == null){  
                    continue;  
                }  
                int firstRowNum  = sheet.getFirstRowNum();  
                int lastRowNum = sheet.getLastRowNum();  
                for(int rowNum = firstRowNum+1;rowNum <= lastRowNum;rowNum++){  
                    Row row = sheet.getRow(rowNum);  
                    if(row == null){  
                        continue;  
                    }  
                    int firstCellNum = row.getFirstCellNum();  
                    int lastCellNum = row.getPhysicalNumberOfCells();  
                    String[] cells = new String[row.getPhysicalNumberOfCells()];  
                    for(int cellNum = firstCellNum; cellNum < lastCellNum;cellNum++){  
                        Cell cell = row.getCell(cellNum);  
                        cells[cellNum] = getCellValue(cell);  
                    }  
                    list.add(cells);  
                }  
            }  
            workbook.close();  
        }  
        return list;  
    }  
    
    
    public static void checkFile(String filePath) throws IOException{  
		File file = new File(filePath);
		
    		if(null == file){  
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
    public static Workbook getWorkBook(String filePath) {
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
        } catch (IOException e) {  
            logger.info(e.getMessage());  
        }
        
        return workbook;  
    }  
    
    
    public static String getCellValue(Cell cell){  
        String cellValue = "";  
        if(cell == null){  
            return cellValue;  
        }  
          
        if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){  
            cell.setCellType(Cell.CELL_TYPE_STRING);  
        }  

        switch (cell.getCellType()){  
            case Cell.CELL_TYPE_NUMERIC: //numeric 
                cellValue = String.valueOf(cell.getNumericCellValue());  
                break;  
            case Cell.CELL_TYPE_STRING: //string  
                cellValue = String.valueOf(cell.getStringCellValue());  
                break;  
            case Cell.CELL_TYPE_BOOLEAN: //boolean  
                cellValue = String.valueOf(cell.getBooleanCellValue());  
                break;  
            case Cell.CELL_TYPE_FORMULA: //formula
                cellValue = String.valueOf(cell.getCellFormula());  
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
}
