package com.chaofan.websocket;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class BookParser<T> {
    private String url;

    public BookParser(String url) {
        this.url = url;
    }

    /**
     * 解析书籍详细信息
     * @param rule 详细信息的 CSS 选择器规则
     * @return 书籍详细信息对象
     * @throws IOException
     */
    public T getDetails(String rule) throws IOException {
        Document doc = Jsoup.connect(url).get();

        // 使用 CSS 选择器解析书籍详细信息
        Element bookDetails = doc.selectFirst(rule);
        ObjectMapper mapper = new ObjectMapper(new JsonFactory());
        T details = mapper.readValue(bookDetails.html(), new TypeReference<T>() {});

        return details;
    }

    /**
     * 获取章节列表
     * @param url 章节列表页面的 URL
     * @param rule 章节列表的 CSS 选择器规则
     * @return 章节列表，键为章节标题，值为章节链接 URL
     * @throws IOException
     */
    public Map<String, String> getChapterList(String url, String rule) throws IOException {
        Map<String, String> chapters = new HashMap<>();
        Document doc = Jsoup.connect(url).get();

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
     * @param url 章节内容页面的 URL
     * @param rule 章节内容的 CSS 选择器规则
     * @return 章节内容
     * @throws IOException
     */
    public String getChapterContent(String url, String rule) throws IOException {
        Document doc = Jsoup.connect(url).get();

        // 使用 CSS 选择器解析章节内容
        Element contentElement = doc.selectFirst(rule);
        return contentElement.text();
    }

    /**
     * 发送 GET 请求并获取响应
     * @param urlStr 请求的 URL 字符串
     * @return 响应内容的字符串形式
     * @throws IOException
     */
    private String get(String urlStr) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            int chr;
            while ((chr = in.read()) != -1) {
                response.append((char) chr);
            }
            return response.toString();
        }
    }

    public static void main(String[] args) throws IOException {
        String bookUrl = "https://legado.git.llc/wwsy-qry.txt";
        BookParser<BookDetails> parser = new BookParser<>(bookUrl);

        // 解析书籍详细信息
        String ruleDetails = "#bookDetails";
        BookDetails bookDetails = parser.getDetails(ruleDetails);
        System.out.println("书籍标题：" + bookDetails.getTitle());
        System.out.println("作者：" + bookDetails.getAuthor());
        System.out.println("封面图片 URL：" + bookDetails.getCoverUrl());
        System.out.println("简介：" + bookDetails.getDescription());
        System.out.println();

        // 获取章节列表
        String chapterListUrl = bookDetails.getChapterListUrl();
        String ruleChapterList = "#chapterList a[href]";
        Map<String, String> chapterList = parser.getChapterList(chapterListUrl, ruleChapterList);
        System.out.println(bookDetails.getTitle() + " 的章节列表：");
        chapterList.forEach((title, linkUrl) -> System.out.println(title + "：" + linkUrl));
        System.out.println();

        // 解析章节内容
        String chapterContentUrl = chapterList.entrySet().iterator().next().getValue();
        String ruleChapterContent = "#content";
        String chapterContent = parser.getChapterContent(chapterContentUrl, ruleChapterContent);
        System.out.println("章节内容：\n" + chapterContent);
    }
}