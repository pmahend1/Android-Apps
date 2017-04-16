package com.example.praneeth.chitchat;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Created by Praneeth on 3/27/2017.
 */

public class FileUtility {

    // reads text file
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String read(String filePath) {
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(filePath));
            StringBuilder sb = new StringBuilder();

            String line = br.readLine();

            while (line != null) {
                sb.append(line);

                line = br.readLine();
                if (line != null) {
                    sb.append(System.lineSeparator());
                }
            }
            String everything = sb.toString();
            return everything;
        } catch (Exception e) {
            System.out.println("Exception:" + e.getMessage());
            return null;
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    // write integer list<> to file
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void writeIntegerListToFile(String fileName, String extension, List<Integer> inputList) {
        String filePath = fileName + "." + extension;

        Writer out = null;
        try {
            out = new OutputStreamWriter(new FileOutputStream(filePath), StandardCharsets.UTF_16BE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int myInt : inputList) {

            try {
                out.write(myInt);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        try {
            System.out.println("\n************Encoded file "+filePath+" generated*************");
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // writes string to file
    public static void writeStringToFile(String fileName, String extension, String content) {

        String filePath = fileName + "." + extension;

        PrintWriter out=null;

        try {
            out = new PrintWriter(filePath);
            out.print(content);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            out.close();
        }

    }

    //Read file which is in UTF-16BE format
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String readUTF16BEFile(String filePath) {

        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath),StandardCharsets.UTF_16BE));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
                sb.append(System.lineSeparator());
            }
            String fileContent = sb.toString();
            br.close();
            return fileContent;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

}
