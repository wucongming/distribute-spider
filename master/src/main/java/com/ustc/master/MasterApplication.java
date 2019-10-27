package com.ustc.master;

import net.dongliu.requests.Proxies;
import net.dongliu.requests.RawResponse;
import net.dongliu.requests.Requests;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class MasterApplication {

    public static void main(String[] args) throws IOException {
//        RawResponse send = Requests.get("http://icanhazip.com/").proxy(Proxies.httpProxy("167.71.182.13", 3128)).send();
//        System.out.println(send.readToText());
        File file = new File("1.csv");
//        InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        List<String> strings = reader.lines().collect(Collectors.toList());
        List<String> lists = new ArrayList<>();
        for (String string : strings) {
            String[] split = string.split(",");
            try {
                long start = System.nanoTime();
                RawResponse send = Requests.get(split[0].trim() + "://news.sina.com.cn/w/2019-10-27/doc-iicezzrr5323798.shtml").proxy(Proxies.httpProxy(split[2].trim(), Integer.valueOf(split[1].trim()))).send();
                send.readToText();
                long end = System.nanoTime();
                System.out.println(split[2] + "\t" + (end - start) / Math.pow(10,9));
        lists.add(string + "," + (end - start) / Math.pow(10,9));
    } catch (Exception e) {

    }
}
    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("1.csv"))));
        for(String line : lists) {
            writer.write(line + "\n");
        }
        writer.flush();
        writer.close();
//        SpringApplication.run(MasterApplication.class, args);
    }

}
