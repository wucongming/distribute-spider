package com.ustc.master.spider;

import com.ustc.master.entity.UndoUrlsEntity;
import com.ustc.master.service.UndoUrlsService;
import net.dongliu.requests.Proxies;
import net.dongliu.requests.RawResponse;
import net.dongliu.requests.Requests;
import org.apache.catalina.connector.InputBuffer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class UrlSpider {

    private String ip;
    private String port;
    private String protocol;

    @Autowired
    private UndoUrlsService undoUrlsService;

    @Value("user-agent")
    private String userAgent;

    private static List<String[]> httpProxys;

    private static List<String[]> httpsProxys;

    /**
     * 初始化的时候，读取ip池文件，将ip池中的ip分为两http代理ip和https代理ip
     */
    static {
        httpProxys = Collections.synchronizedList(new ArrayList<>());
        httpsProxys = Collections.synchronizedList(new ArrayList<>());
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(System.getProperty("ips")))));
            List<String> httpsList = reader.lines().filter(e -> e.contains("https")).collect(Collectors.toList());
            for (String item : httpsList) {
                String[] split = item.split(",");
                httpsProxys.add(new String[] {split[2].trim(), split[1].trim()});
            }
            List<String> httpList = reader.lines().filter(e -> !e.contains("https")).collect(Collectors.toList());
            for(String item : httpList) {
                String[] split = item.split(",");
                httpProxys.add(new String[] {split[2].trim(), split[1].trim()});
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {

        }
    }

    public void run() {
        if (ip == null || port == null || protocol == null) changeIP("https");
        while(true) {
            UndoUrlsEntity oneUndo = undoUrlsService.getOneUndo();
            String html = send(oneUndo.getUrl());
            if ("".equals(html) || html == null) continue;

            Document doc = Jsoup.parse(html);
            Elements hrefs = doc.getElementsByAttribute("href");

        }
    }

    /**
     * 发送请求
     * 发送失败时，会重新发送，一个ip最多能重新发送3次。
     * 一个ip发送失败次数超过3次，则自动更换ip。
     * 而最多智能连续更换3次IP。如果经过以上操作，该url还是不能请求成功，则抛弃该url
     *
     * 每次重新发送间隔2秒
     * @param url
     * @return
     */
    public String send(String url) {
        String html = null;
        int ipTime = 3;
        while(ipTime >= 0) {
            int sendTime = 3;
            while(sendTime > 0) {
                try {
                    RawResponse send = Requests.get(protocol + url.replace("https", "")
                            .replace("http", "")).userAgent(userAgent)
                            .proxy(Proxies.httpProxy(ip, Integer.valueOf(port)))
                            .send();
                    html = send.readToText();
                    return html;
                } catch (Exception e) {
                    System.out.println("发送失败， 正在重试..." + sendTime--);
                }
            }
            changeIP(ipTime-- % 2 == 0 ? "https" : "http");
        }

        return html;
    }

    /**
     * 根据传入的http协议类型，来找到对应的类型的代理ip和端口
     * @param ipType
     */
    private synchronized void changeIP(String ipType) {
        List<String[]> list = null;
        if("https".equals(ipType)) {
            list = httpsProxys;
        } else {
            list = httpProxys;
        }
        String[] ipAndPort = list.get(0);
        ip = ipAndPort[0];
        port = ipAndPort[1];
        protocol = ipType;
        list.remove(0);
        list.add(list.size() - 1, ipAndPort);
    }

}
