package smart.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import smart.common.FrameworkConstant;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Cruise on 2017/7/2.
 */
public final class  WebUtil {
    private static final Logger logger = LoggerFactory.getLogger(WebUtil.class);
    private WebUtil(){}

    public static void writeJson(HttpServletResponse response, Object data){
        response.setContentType("application/json");
        response.setCharacterEncoding(FrameworkConstant.UTF_8);
        try {
            PrintWriter writer = response.getWriter();
            writer.write(JSONObject.toJSONString(data));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            logger.error("write json data exeption|",e);
            throw new RuntimeException(e);
        }

    }

    public static void writeHTML(HttpServletResponse response, Object data){
        response.setContentType("text/html");
        response.setContentType(FrameworkConstant.UTF_8);
        try {
            PrintWriter writer = response.getWriter();
            writer.write(JSONObject.toJSONString(data));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            logger.error("write json data exeption|",e);
            throw new RuntimeException(e);
        }
    }

    public static Map<String, Object> getRequestParams(HttpServletRequest request){
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Enumeration<String> enums = request.getParameterNames();
            while(enums.hasMoreElements()){
                String paramName = enums.nextElement();
                if(checkParamName(paramName)){
                    String[] paramValues = request.getParameterValues(paramName);
                    if(ArrayUtils.isNotEmpty(paramValues)){
                        if(paramValues.length == 1){
                            map.put(paramName,paramValues[0]);
                        }else{
                            StringBuffer sb = new StringBuffer("");
                            for(String v:paramValues){
                                sb.append(v).append(StringUtil.SEPARATOR);
                            }
                            String vs = sb.toString();
                            map.put(paramName,vs.substring(0,vs.length()-1));
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("get request param exception|",e);
            throw new RuntimeException(e);
        }

        return map;
    }


    public static void forwardRequest(String path, HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(path).forward(request,response);
    }

    public static void redirectRequest(String path, HttpServletRequest request,HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getContextPath() + "/" + path);
    }

    public static void sendError(int code, String message, HttpServletResponse response) throws IOException {
        response.sendError(code, message);
    }

    public static boolean isAJAX(HttpServletRequest request){
        return request.getHeader("X-Requested-With") != null;
    }

    public static String getRequestPath(HttpServletRequest request){
        String requestPath;
        String servletPath  = request.getServletPath();
        String pathInfo = request.getPathInfo();
        if(StringUtils.isNoneBlank(pathInfo))
            requestPath = servletPath+pathInfo;
        else
            requestPath = servletPath;
        return  requestPath;
    }

    public static String getCookie(HttpServletRequest request, String key){
        String value = "";
        try {
            Cookie[] cookies = request.getCookies();
            if(cookies != null && cookies.length >0){
                for(Cookie ck:cookies){
                    if(key.equals(ck.getName())){
                        value = CodecUtil.decodeURL(ck.getValue());
                        break;
                    }
                }
            }
        } catch (Exception e) {
            logger.error("get cookie exception|",e);
            throw new RuntimeException(e);
        }

        return value;
    }

    public static void downLoadFile(HttpServletResponse response, String filePath){
        try {
            String fileName = FilenameUtils.getName(filePath);
            String downLoadFileName = new String(fileName.getBytes("GBK"), "ISO8859_1");
            response.setContentType("application/octet-stream");
            response.addHeader("Content-Disposition", "attachment;filename=\"" + downLoadFileName + "\"");

            InputStream in = new BufferedInputStream(new FileInputStream(new File(filePath)));

            OutputStream out = new BufferedOutputStream(response.getOutputStream());

            StreamUtil.copyStream(in,out);

        } catch (Exception e) {
            logger.error("down loade file exception|",e);
            throw new RuntimeException(e);
        }
    }

    public static String createCapthca(HttpServletResponse response){
        StringBuffer capthca = new StringBuffer();
        int width = 60;                      // ��֤��ͼƬ�Ŀ��
        int height = 25;                     // ��֤��ͼƬ�ĸ߶�
        int codeCount = 4;                   // ��֤���ַ�����
        int codeX = width / (codeCount + 1); // �ַ�������
        int codeY = height - 4;              // �ַ�������
        int fontHeight = height - 2;         // ����߶�
        int randomSeed = 10;                 // ���������
        char[] codeSequence = {              // ��֤���пɳ��ֵ��ַ�
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
        };

        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bi.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        // ��������
        g.setFont(new Font("Courier New", Font.BOLD, fontHeight));
        // ���Ʊ߿�
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, width - 1, height - 1);
        g.setColor(Color.WHITE);

        // ���������������
        Random random = new Random();
        for (int i = 0; i < 160; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }

        //����������֣������������ɫ
        for(int i=0;i<codeCount;i++){
            int red = random.nextInt(255);
            int green = random.nextInt(255);
            int blue = random.nextInt(255);
            String validateCode = String.valueOf(codeSequence[random.nextInt(randomSeed)]);
            // ��������ɫ����֤����Ƶ�ͼ����
            g.setColor(new Color(red, green, blue));
            g.drawString(validateCode, (i + 1) * codeX - 6, codeY);
            capthca.append(validateCode);
        }

        try {
            // ��ֹͼ�񻺴�
            response.setHeader("Cache-Control", "no-store");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);
            // ������Ӧ����Ϊ JPEG ͼƬ
            response.setContentType("image/jpeg");
            // ������ͼ��д�� Servlet �������
            ServletOutputStream sos = response.getOutputStream();
            ImageIO.write(bi, "jpeg", sos);
            sos.close();
        } catch (IOException e) {
            logger.error("create capthca exception|",e);
            throw new RuntimeException(e);
        }

        return capthca.toString();

    }
    private static boolean checkParamName(String paramName) {
        return !paramName.equals("_"); // ���� jQuery �������
    }
}
