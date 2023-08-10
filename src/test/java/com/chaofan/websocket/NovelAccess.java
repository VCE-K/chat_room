package com.chaofan.websocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.net.ssl.HttpsURLConnection;

public class NovelAccess {

    private String baseUrl;
    private Map<String, String> rules;

    public NovelAccess(String baseUrl) throws IOException {
        this.baseUrl = baseUrl;
        // 从 `https://alanskycn.gitee.io/teachme/Rule/source.html` 中获取并解析小说站点规则
        Map<String, String> siteRules = getSiteRules();
        // 将通用的解析规则合并到小说站点规则中，并保存到成员变量 `rules` 中
        rules = mergeRules(siteRules);
    }

    /**
     * 获取小说详细信息对象
     * @return 小说详细信息对象
     * @throws IOException
     */
    public NovelDetails getDetails() throws IOException {
        Document doc = Jsoup.connect(baseUrl).get();

        // 使用 CSS 选择器解析小说详细信息
        String rule = rules.get("details");
        Element bookDetails = doc.selectFirst(rule);
        ObjectMapper mapper = new ObjectMapper(new JsonFactory());
        NovelDetails details = mapper.readValue(bookDetails.html(), new TypeReference<NovelDetails>() {});

        return details;
    }

    /**
     * 获取章节列表
     * @return 章节列表，键为章节标题，值为章节链接 URL
     * @throws IOException
     */
    public Map<String, String> getChapterList() throws IOException {
        String chapterListUrl = baseUrl + rules.get("chapterListUrl");
        String rule = rules.get("chapterList");
        Map<String, String> chapters = new HashMap<>();
        Document doc = Jsoup.connect(chapterListUrl).get();

        // 使用 CSS 选择器解析章节列表
        Elements chapterLinks = doc.select(rule);
        for (Element chapterLink : chapterLinks) {
            String linkUrl = chapterLink.attr("href");
            String title = chapterLink.text();
            chapters.put(title, linkUrl);
        }

        return chapters;
    }

    /**
     * 获取章节内容
     * @param chapterUrl 章节链接 URL
     * @return 章节内容
     * @throws IOException
     */
    public String getChapterContent(String chapterUrl) throws IOException {
        String rule = rules.get("chapterContent");
        Document doc = Jsoup.connect(chapterUrl).get();

        // 使用 CSS 选择器解析章节内容
        Element contentElement = doc.selectFirst(rule);
        return contentElement.text();
    }

    /**
     * 合并通用解析规则和小说站点规则
     * @param siteRules 小说站点规则
     * @return 合并后的规则
     */
    private Map<String, String> mergeRules(Map<String, String> siteRules) throws IOException {
        // 获取通用解析规则
        Map<String, String> commonRules = getCommonRules();
        // 合并通用解析规则和小说站点规则
        Map<String, String> rules = new HashMap<>();
        rules.putAll(commonRules);
        rules.putAll(siteRules);
        return rules;
    }

    /**
     * 获取通用解析规则
     * @return 通用解析规则
     * @throws IOException
     */
    private Map<String, String> getCommonRules() throws IOException {
        String rulesUrl = "https://alanskycn.gitee.io/teachme/Rule/source.html";
        Document doc = Jsoup.connect(rulesUrl).get();
        Elements tables = doc.select("table");

        Map<String, String> commonRules = new HashMap<>();
        for (Element table : tables) {
            String siteName = table.selectFirst("th").text();
            if (siteName.equals("通用解析规则")) {
                Elements rows = table.select("tr");
                for (Element row : rows) {
                    Elements cells = row.select("td");
                    if (cells.size() == 2) {
                        String key = cells.get(0).text();
                        String value = cells.get(1).html();
                        commonRules.put(key, value);
                    }
                }
                break;
            }
        }

        return commonRules;
    }

    /**
     * 获取小说站点规则
     * @return 小说站点规则
     * @throws IOException
     */
    private Map<String, String> getSiteRules() throws IOException {
        URL url = new URL("https://legado.git.llc/wwsy-qry.txt");
        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
                response.append('\n');
            }

            String[] rulesStr = response.toString().split("\n");
            Map<String, String> siteRules = new HashMap<>();
            for (String rule : rulesStr) {
                if (rule.startsWith("#") || "".equals(rule.trim())) {
                    continue;
                }
                String[] ruleParts = rule.split("->");
                String key = ruleParts[0].trim();
                String value = ruleParts[1].trim();
                siteRules.put(key, value);
            }

            return siteRules;
        }
    }

    public static void main(String[] args) throws IOException {
        String novelUrl = "https://www.biqudu.com/0_1/";
        NovelAccess novelAccess = new NovelAccess(novelUrl);

        // 获取小说详细信息
        NovelDetails details = novelAccess.getDetails();
        System.out.println("小说名称：" + details.getBookName());
        System.out.println("作者：" + details.getAuthor());
        System.out.println("封面图片 URL：" + details.getCoverUrl());
        System.out.println("简介：" + details.getDescription());

        // 获取章节列表
        Map<String, String> chapters = novelAccess.getChapterList();
        System.out.println("章节数量：" + chapters.size());
        chapters.forEach((title, url) -> {
            System.out.println("章节标题：" + title);
            System.out.println("章节链接：" + url);
        });

        // 获取章节内容
        String content = novelAccess.getChapterContent(chapters.values().iterator().next());
        System.out.println("章节内容：" + content);
    }

}