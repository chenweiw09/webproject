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
        int width = 60;                      // 验证码图片的宽度
        int height = 25;                     // 验证码图片的高度
        int codeCount = 4;                   // 验证码字符个数
        int codeX = width / (codeCount + 1); // 字符横向间距
        int codeY = height - 4;              // 字符纵向间距
        int fontHeight = height - 2;         // 字体高度
        int randomSeed = 10;                 // 随机数种子
        char[] codeSequence = {              // 验证码中可出现的字符
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
        };

        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bi.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        // 设置字体
        g.setFont(new Font("Courier New", Font.BOLD, fontHeight));
        // 绘制边框
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, width - 1, height - 1);
        g.setColor(Color.WHITE);

        // 创建随机数生成器
        Random random = new Random();
        for (int i = 0; i < 160; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }

        //创建随机数字，并随机产生颜色
        for(int i=0;i<codeCount;i++){
            int red = random.nextInt(255);
            int green = random.nextInt(255);
            int blue = random.nextInt(255);
            String validateCode = String.valueOf(codeSequence[random.nextInt(randomSeed)]);
            // 将带有颜色的验证码绘制到图像中
            g.setColor(new Color(red, green, blue));
            g.drawString(validateCode, (i + 1) * codeX - 6, codeY);
            capthca.append(validateCode);
        }

        try {
            // 禁止图像缓存
            response.setHeader("Cache-Control", "no-store");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);
            // 设置响应类型为 JPEG 图片
            response.setContentType("image/jpeg");
            // 将缓冲图像写到 Servlet 输出流中
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
        return !paramName.equals("_"); // 忽略 jQuery 缓存参数
    }
}
