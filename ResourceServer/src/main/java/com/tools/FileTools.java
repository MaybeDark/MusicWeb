package com.tools;

import java.io.*;
import java.util.HashMap;

public class FileTools {
//    public static String getFileHeader(File file){
//        byte[] b = new byte[4];
//        FileInputStream fileInputStream = null;
//        try {
//            fileInputStream = new FileInputStream(file);
//            fileInputStream.read(b, 0, 4);
//        } catch (FileNotFoundException e) {
//            return "非文件类型";
//        } catch (IOException e){
//            throw new RuntimeException("读写文件时发生异常");
//        }
//        StringBuilder builder = new StringBuilder();
//        String hv;
//        for (byte value : b) {
//            hv = Integer.toHexString(value & 0xFF).toUpperCase();
//            if (hv.length() < 2) {
//                builder.append(0);
//            }
//            builder.append(hv);
//        }
//        return builder.toString();
//    }
//    public static String getFileHeader(FileInputStream fileInputStream){
//        byte[] b = new byte[4];
//       try {
//            fileInputStream.read(b, 0, 4);
//        } catch (FileNotFoundException e) {
//            return "非文件类型";
//        } catch (IOException e){
//           e.printStackTrace();
//            throw new RuntimeException("读写文件时发生异常");
//        }
//        StringBuilder builder = new StringBuilder();
//        String hv;
//        for (byte value : b) {
//            hv = Integer.toHexString(value & 0xFF).toUpperCase();
//            if (hv.length() < 2) {
//                builder.append(0);
//            }
//            builder.append(hv);
//        }
//        return builder.toString();
//    }

    public static String getFileHeader(BufferedInputStream fileInputStream){
        byte[] b = new byte[4];
        try {
            fileInputStream.mark(10);
            fileInputStream.read(b, 0, 4);
            fileInputStream.reset();
        } catch (FileNotFoundException e) {
            return "非文件类型";
        } catch (IOException e){
            e.printStackTrace();
            throw new RuntimeException("读写文件时发生异常");
        }
        StringBuilder builder = new StringBuilder();
        String hv;
        for (byte value : b) {
            hv = Integer.toHexString(value & 0xFF).toUpperCase();
            if (hv.length() < 2) {
                builder.append(0);
            }
            builder.append(hv);
        }
        return builder.toString();
    }

    public static String guessFileType(File file) {
        byte[] b = new byte[4];
        int num = 0;
        String fileType = "未知文件类型";
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            num = fileInputStream.read(b, 0, 4);
        } catch (FileNotFoundException e) {
            return "非文件类型";
        } catch (IOException e){
            throw new RuntimeException("读写文件时发生异常");
        }
        if (num == 4) {
            StringBuilder builder = new StringBuilder();
            String hv;
            for (byte value : b) {
                hv = Integer.toHexString(value & 0xFF).toUpperCase();
                if (hv.length() < 2) {
                    builder.append(0);
                }
                builder.append(hv);
            }
            fileType = getFileTypeMap().get(builder.toString());
            if(fileType == null || fileType.isEmpty())
                return "未知文件类型";
            else
                return fileType;
        }else {
            return "文本文件";
        }
    }
    public static String guessFileType(FileInputStream fileInputStream) {
        byte[] b = new byte[4];
        int num = 0;
        String fileType = "未知文件类型";
        try {
            num = fileInputStream.read(b, 0, 4);
        } catch (FileNotFoundException e) {
            return "非文件类型";
        } catch (IOException e){
            throw new RuntimeException("读写文件时发生异常");
        }
        if (num == 4) {
            StringBuilder builder = new StringBuilder();
            String hv;
            for (byte value : b) {
                hv = Integer.toHexString(value & 0xFF).toUpperCase();
                if (hv.length() < 2) {
                    builder.append(0);
                }
                builder.append(hv);
            }
            fileType = getFileTypeMap().get(builder.toString());
            if(fileType == null || fileType.isEmpty())
                return "未知文件类型";
            else
                return fileType;
        }else {
            return "文本文件";
        }
    }

    private static HashMap<String, String> getFileTypeMap() {
        HashMap<String, String> FileTypeMap = new HashMap<>();
        FileTypeMap.put("49443303", "mp3");
        FileTypeMap.put("89504E47", "png");
        FileTypeMap.put("FFD8FF", "jpg");
        FileTypeMap.put("47494638", "gif");
        FileTypeMap.put("49492A00", "tif");
        FileTypeMap.put("424D", "bmp");
        FileTypeMap.put("41433130", "dwg");
        FileTypeMap.put("68746D6C3E", "html");
        FileTypeMap.put("7B5C727466", "rtf");
        FileTypeMap.put("3C3F786D6C", "xml");
        FileTypeMap.put("504B0304", "zip");
        FileTypeMap.put("52617221", "rar");
        FileTypeMap.put("38425053", "psd");
        FileTypeMap.put("44656C69766572792D646174653A", "eml");
        FileTypeMap.put("CFAD12FEC5FD746F", "dbx");
        FileTypeMap.put("2142444E", "pst");
        FileTypeMap.put("D0CF11E0", "xls doc");
        FileTypeMap.put("5374616E64617264204A", "mdb");
        FileTypeMap.put("FF575043", "wpd");
        FileTypeMap.put("252150532D41646F6265", "eps ps");
        FileTypeMap.put("255044462D312E", "pdf");
        FileTypeMap.put("AC9EBD8F", "qdf");
        FileTypeMap.put("E3828596", "pwl");
        FileTypeMap.put("57415645", "wav");
        FileTypeMap.put("41564920", "avi");
        FileTypeMap.put("2E7261FD", "ram");
        FileTypeMap.put("2E524D46", "rm");
        FileTypeMap.put("000001BA", "mpg");
        FileTypeMap.put("6D6F6F76", "mov");
        FileTypeMap.put("3026B2758E66CF11", "asf");
        FileTypeMap.put("4D546864", "mid");
        FileTypeMap.put("7061636B", "java");

        return FileTypeMap;
    }
}

