package com.chaofan.websocket.Util;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.chaofan.websocket.Web.MyWebSocket;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;
import org.seimicrawler.xpath.exception.XpathParserException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by Chaofan at 2018-09-26 15:40
 * email:chaofan2685@qq.com
 **/
public class BingImageUtil {

    /**
     * 同步必应壁纸
     * @param index 起始点，0表示今天，1表示昨天，2前天，以此类推
     * @param sum 同步壁纸的数量，最多7张
     */
    public static Integer download(Integer index, Integer sum){
        Integer i = 0;
        String result = HttpUtil.get("https://www.bing.com/HPImageArchive.aspx?format=js&idx="+index+"&n="+sum);
        try{
            JSONObject jsonObject = JSONUtil.parseObj(result);
            JSONArray array = JSONUtil.parseArray(jsonObject.get("images"));
            for (int j = 0; j < array.size(); j++) {
                String url = "http://s.cn.bing.net"+(JSONUtil.parseObj(array.get(j)).get("url").toString());
                if (!MyWebSocket.BingImages.contains(url)){
                    MyWebSocket.BingImages.add(url);
                    i+=1;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
        return i;
    }


    public static void main(String[] args) {


        boolean flag = (2244691 & 0x80000) != 0;
        System.out.println(flag);

        String html = HttpUtil.get("https://github.com/lzwme/chatgpt-sites/blob/main/README.md");
        JXDocument doc = JXDocument.create(html);
        ////article/h2/following-sibling::p[1]
        String xpath = "//ol[@dir=\"auto\"]/li";
        List<JXNode> nodes = doc.selN(xpath);
        System.out.println("item size:"+nodes.size());
        for (Object o:nodes){
            JXDocument itemDoc = JXDocument.create(o.toString());

            String hrefXpath = "//li/a/@href";
            String emojiXpath = "//li/a/g-emoji/text()";
            String nameXpath = "//li/strong/text()";
            String descXpath = "//li/text()[normalize-space()] | //li/code/text()";

            String href = itemDoc.selOne(hrefXpath).toString();
            String emoji = itemDoc.selN(emojiXpath).toString();
            Object name = itemDoc.selOne(nameXpath);
            Object desc = itemDoc.selOne(descXpath);

            if(desc.toString().startsWith(",")) {
                desc = desc.toString().replaceFirst(",", "");
            }

            if(name == null && desc != null) {
                name = desc.toString();
                desc = null;
            }

            System.out.println(href);
            System.out.println(emoji);
            System.out.println(name);
            System.out.println(desc);
            System.out.println();
        }
    }

    public int lengthLastWord(String str){
        int length = 0;
        byte[] cs = str.getBytes();
        for (int i = cs.length - 1;i >= 0; i--){
            byte c = cs[i];
            if (c == ' '){
                return length;
            }else{
                length++;
            }
        }
        return length;
    }


    public static void downloadFile(String fileUrl) throws IOException {
        URL url = new URL(fileUrl);
        InputStream inputStream = url.openStream();

        String saveToPath = "C:\\Users\\VCE\\Desktop\\downloads";
        // 获取文件名
        String fileName = getFileNameFromUrl(fileUrl);

        // 保存文件
        FileOutputStream outputStream = new FileOutputStream(new File(saveToPath, fileName));
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, length);
        }

        inputStream.close();
        outputStream.close();
    }

    // 从URL中获取文件名
    private static String getFileNameFromUrl(String url) {
        int lastSlashIndex = url.lastIndexOf('/');
        if (lastSlashIndex >= 0 && lastSlashIndex < url.length() - 1) {
            url = url.substring(lastSlashIndex + 1);
        }
        return url;
    }



}
