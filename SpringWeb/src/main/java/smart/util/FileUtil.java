package smart.util;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import smart.common.FrameworkConstant;

import java.io.*;

/**
 * Created by Cruise on 2017/6/30.
 */
public final class FileUtil {

    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    public static File createDir(String dirPath) throws IOException {
        File dir;
        dir = new File(dirPath);
        if (!dir.exists()) {
            FileUtils.forceMkdir(dir);
        }

        return dir;
    }


    public static File createFile(String filePath) throws IOException {
        File file = new File(filePath);
        File parentDir = file.getParentFile();
        if (!parentDir.exists()) {
            FileUtils.forceMkdir(parentDir);
        }

        return file;
    }

    public static void copyDir(String srcPath, String destPath) {
        try {
            File srcDir = new File(srcPath);
            File destDir = new File(destPath);
            if (srcDir.exists() && srcDir.isDirectory()) {
                FileUtils.copyDirectoryToDirectory(srcDir, destDir);
            }
        } catch (Exception e) {
            logger.error("复制目录出错！", e);
            throw new RuntimeException(e);
        }
    }

    public static void copyFile(String srcPath, String destPath) {
        try {
            File srcFile = new File(srcPath);
            File destFile = new File(destPath);
            if (srcFile.exists() && srcFile.isFile()) {
                FileUtils.copyFileToDirectory(srcFile, destFile);
            }
        } catch (IOException e) {
            logger.error("copy file exceptio",e);
            throw new RuntimeException(e);
        }
    }

    public static void deleteDir(String dirPath){
        try {
            File dir = new File(dirPath);
            if(dir.exists() && dir.isDirectory()){
                FileUtils.deleteDirectory(dir);
            }
        } catch (IOException e) {
            logger.error("delete dir exception",e);
            throw new RuntimeException(e);
        }
    }

    public static void deleteFile(String filePath){
        try {
            File file = new File(filePath);
            if(file.exists() && file.isFile()){
                FileUtils.forceDelete(file);
            }
        } catch (IOException e) {
            logger.info("delete file exception", e);
            throw new RuntimeException(e);
        }
    }

    public static void renameFile(String srcPath, String destPath){
        File srcFile = new File(srcPath);
        if (srcFile.exists()) {
            File newFile = new File(destPath);
            boolean result = srcFile.renameTo(newFile);
            if (!result) {
                throw new RuntimeException("重命名文件出错！" + newFile);
            }
        }
    }

    public static void writeFile(String fileName, String content){
        OutputStream out = null;
        Writer w = null;
        try {
            FileUtil.createFile(fileName);
            out = new BufferedOutputStream(new FileOutputStream(fileName));
            w = new OutputStreamWriter(out, FrameworkConstant.UTF_8);
            w.write(content);
            w.flush();
        } catch (IOException e) {
            logger.error("write file exception",e);
            throw new RuntimeException(e);
        }finally {
            if(out!= null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(w != null){
                try {
                    w.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static boolean fileExist(String filePath){
        return new File(filePath).exists();
    }

    public static String readFile(String filePath){
        File file = new File(filePath);
        BufferedReader reader = null;
        StringBuffer content = new StringBuffer();
        try {
            if(file.exists() && file.isFile()){
                reader = new BufferedReader(new FileReader(file));
                String line = "";
                while((line = reader.readLine()) != null){
                    content.append(line).append("\r\n");
                }
            }

        } catch (Exception e) {
            logger.error("read file exception",e);
            throw new RuntimeException(e);
        } finally {
            if(reader!=null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }
}
