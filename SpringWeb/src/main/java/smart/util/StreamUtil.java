package smart.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Created by Cruise on 2017/7/1.
 */
public final class StreamUtil {
    private static final Logger logger = LoggerFactory.getLogger(StreamUtil.class);

    public static void copyStream(InputStream in, OutputStream out){
        int length;
        byte[] bytes = new byte[4*1024];
        try {
            while((length = in.read(bytes,0,bytes.length)) != -1){
                out.write(bytes,0,bytes.length);
            }

            out.flush();
        } catch (IOException e) {
            logger.error("stream copy exception|",e);
            throw new RuntimeException(e);
        } finally {
            try {
                in.close();
                out.close();
            } catch (Exception e) {
                logger.error("close stream exception£¡", e);
            }
        }
    }

    public static String getStrFromStream(InputStream in){
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while((line = reader.readLine()) != null){
                sb.append(line);
            }
        } catch (IOException e) {
            logger.error("read inputStream exception|",e);
            throw new RuntimeException(e);
        }

        return sb.toString();
    }
}
