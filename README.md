# webproject
#高速文件复制
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class FileCopyTest {
	
	public static void main(String[] args){
		File in = new File("D://hh.jpg");
		File out = new File("D://gg.jpg");
		try {
			long t = System.currentTimeMillis();
			fileCopy(in,out);
			System.out.println(System.currentTimeMillis()-t);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void fileCopy(File in, File out) throws IOException {  
        FileChannel inChannel = new FileInputStream(in).getChannel();  
        FileChannel outChannel = new FileOutputStream(out).getChannel();  
        try {  
        	// magic number for Windows, 64Mb - 32Kb)  
            int maxCount = (64 * 1024 * 1024) - (32 * 1024);  
            long size = inChannel.size();  
            long position = 0;  
            while (position < size) {  
               position += inChannel.transferTo(position, maxCount, outChannel);  
            }  
        }  
        finally {  
            if (inChannel != null){  
               inChannel.close();  
            }  
            if (outChannel != null){  
                outChannel.close();  
            }  
        }  
    }
}
