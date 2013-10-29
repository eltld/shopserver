package com.server.web;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;

public class HttpUtil {

    /**
     * 读
     * 
     * @param in
     * @param charset
     * @return
     * @throws Exception
     */
    public static String read(InputStream in, String charset) throws Exception {
        InputStreamReader reader = null;
        StringWriter writer = null;
        try {
            reader = new InputStreamReader(in, charset);
            writer = new StringWriter();
            int len = -1;
            char[] buffer = new char[1024];
            while ((len = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, len);
            }
            writer.flush();
            return writer.toString();
        } finally {
            if (reader != null)
                reader.close();
            if (writer != null)
                writer.close();
        }
    }

    /**
     * 写
     * 
     * @param value
     * @param charset
     * @param out
     * @return
     * @throws Exception
     */
    public static void write(OutputStream out, String value, String charset) throws Exception {
        OutputStreamWriter writer = null;
        try {
            writer = new OutputStreamWriter(out, charset);
            writer.write(value);
            writer.flush();
        } finally {
            if (out != null)
                writer.close();
        }
    }

    /**
     * 写
     * 
     * @param in
     * @param out
     * @throws Exception
     */
    public static void write(OutputStream out, InputStream in) throws Exception {
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(out);
            int len = -1;
            byte[] buffer = new byte[1024];
            while ((len = in.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
            bos.flush();
        } finally {
            if (in != null)
                in.close();
            if (bos != null)
                bos.close();
        }
    }
}
