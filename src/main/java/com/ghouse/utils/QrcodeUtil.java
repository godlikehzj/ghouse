package com.ghouse.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.Date;
import java.util.Hashtable;
import java.util.Random;

/**
 * Created by godlikehzj on 2017/1/15.
 */
public class QrcodeUtil {
    public static final Integer defaultWidth = 100;
    public static final Integer defaultHeight = 100;
    public static final String defaultFormat = "png";
    public static final String characterSet = "utf-8";

    public static BitMatrix updatePadding(BitMatrix matrix, int marginPercent){
        int[] rec = matrix.getEnclosingRectangle();
        int widthMargin = rec[2]*marginPercent/100/2;
        int heightMargin = rec[3]*marginPercent/100/2;
        int resWidth = rec[2] + widthMargin * 2;
        int resHeight = rec[3] + heightMargin * 2;

        BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);
        resMatrix.clear();
        for (int i = widthMargin; i < resWidth - widthMargin; i++) {
            for (int j = heightMargin; j < resHeight - heightMargin; j++) {
                if(matrix.get(i-widthMargin + rec[0], j-heightMargin + rec[1]))
                    resMatrix.set(i, j);
            }
        }
        return resMatrix;
    }

    public static String generateQrcode(){
        Random random = new Random();
        Date date = new Date();
        Long seed = random.nextInt() + date.getTime();
        return DigestUtils.md5Hex(String.valueOf(seed));
    }

    public static void OutputQrcodeStream(String context, OutputStream outputStream, Integer width, Integer height, String format){
        Hashtable hints= new Hashtable();
        hints.put(EncodeHintType.CHARACTER_SET, characterSet);
        try{
            BitMatrix bitMatrix = new MultiFormatWriter().encode(context, BarcodeFormat.QR_CODE, width, height, hints);
            bitMatrix = updatePadding(bitMatrix, 16);
            MatrixToImageWriter.writeToStream(bitMatrix, format, outputStream);
            outputStream.flush();
            outputStream.close();
        }catch (WriterException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void CreateQrcodeFile(String context, String filepath, Integer width, Integer height, String format){
        Hashtable hints= new Hashtable();
        hints.put(EncodeHintType.CHARACTER_SET, characterSet);
        try{
            BitMatrix bitMatrix = new MultiFormatWriter().encode(context, BarcodeFormat.QR_CODE, width, height, hints);
            Path path = new File(filepath).toPath();
            MatrixToImageWriter.writeToPath(bitMatrix, format, path);
        }catch (WriterException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String arg[]){
        String temp = "2.3.4";

        System.out.println(temp.replaceAll("\\.", "_"));
    }
}
