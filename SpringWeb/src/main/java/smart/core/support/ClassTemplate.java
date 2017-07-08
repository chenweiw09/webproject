package smart.core.support;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import smart.util.ClassUtil;

import java.io.File;
import java.io.FileFilter;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by Cruise on 2017/7/2.
 * get the template class
 */
public abstract class ClassTemplate {
    private static final Logger logger = LoggerFactory.getLogger(ClassTemplate.class);

    protected String packageName;

    protected ClassTemplate(String packageName){
        this.packageName = packageName;
    }

    public final List<Class<?>> getClassList(){
        List<Class<?>> classList = new ArrayList<Class<?>>();
        try {
            Enumeration<URL> urls = ClassUtil.getClassLoader().getResources(packageName.replace(".", "/"));
            while (urls.hasMoreElements()){
                URL url = urls.nextElement();
                if(url != null){
                    // ��ȡЭ��������Ϊ file �� jar��
                    String protocol = url.getProtocol();
                    if(protocol.equals("file")){
                        // ���� class Ŀ¼�У���ִ����������
                        String packagePath = url.getPath().replaceAll("%20", " ");
                        addClass(classList, packagePath, packageName);
                    }else if(protocol.equals("jar")){
                        // ���� jar ���У������ jar ���е� entry
                        JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
                        JarFile jarFile = jarURLConnection.getJarFile();
                        Enumeration<JarEntry> jarEntries = jarFile.entries();
                        while (jarEntries.hasMoreElements()){
                            JarEntry jarEntry = jarEntries.nextElement();
                            String jarEntryName = jarEntry.getName();
                            if(jarEntryName.equals(".class")){
                                // ��ȡ����
                                String className = jarEntryName.substring(0, jarEntryName.lastIndexOf(".")).replaceAll("/", ".");
                                doAddClass(classList, className);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
           logger.error("load class exception|",e);
        }
        return classList;
    }


    private void addClass(List<Class<?>> classList, String packagePath, String packageName){
        File[] files = new File(packagePath).listFiles(new FileFilter() {
            public boolean accept(File file) {
                return (file.isFile() && file.getName().endsWith(".class")) || file.isDirectory();
            }
        });

        for(File file : files){
            String fileName = file.getName();
            if(file.isFile()){
                // get the class name
                String className = fileName.substring(0, fileName.lastIndexOf("."));
                if (StringUtils.isNotEmpty(packageName)) {
                    className = packageName + "." + className;
                }
                doAddClass(classList, className);
            }else{
                //is a directory and need to ��ȡ�Ӱ�
                String subPackagePath = fileName;
                if (StringUtils.isNotEmpty(packagePath)) {
                    subPackagePath = packagePath + "/" + subPackagePath;
                }

                // �Ӱ���
                String subPackageName = fileName;
                if (StringUtils.isNotEmpty(packageName)) {
                    subPackageName = packageName + "." + subPackageName;
                }
                // �ݹ����
                addClass(classList, subPackagePath, subPackageName);
            }
        }

    }
    protected void doAddClass(List<Class<?>> classList, String className){
        Class<?> cls =  ClassUtil.loadClass(className, false);
        if(checkAddClass(cls)){
            classList.add(cls);
        }
    }

    /**
     * ��֤�Ƿ����������
     */
    public abstract boolean checkAddClass(Class<?> cls);
}
