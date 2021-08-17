package com.imooc.demo.springboot.es.controller;

import com.imooc.demo.springboot.es.entity.es.EsBlog;
import com.imooc.demo.springboot.es.entity.mysql.MysqlBlog;
import com.imooc.demo.springboot.es.repository.es.EsBlogRepository;
import com.imooc.demo.springboot.es.repository.mysql.MysqlBlogRepository;
import lombok.Data;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
public class DataController {

    @Autowired
    MysqlBlogRepository mysqlBlogRepository;

    @Autowired
    EsBlogRepository esBlogRepository;

    @GetMapping("/blogs")
    public Object blog() {
        List<MysqlBlog> mysqlBlogs = mysqlBlogRepository.queryAll();
        return mysqlBlogs;
    }

    @PostMapping("/search")
    public Object search(@RequestBody Param param) {
        HashMap<String, Object> map = new HashMap<>();
        StopWatch watch = new StopWatch();
        watch.start();
        String type = param.getType();
        if (type.equalsIgnoreCase("mysql")) {
            // mysql
            map.put("list", mysqlBlogRepository.queryBlogs(param.getKeyword()));
        } else if (type.equalsIgnoreCase("es")) {
            // es
            BoolQueryBuilder builder = QueryBuilders.boolQuery();
            builder.should(QueryBuilders.matchPhraseQuery("title", param.getKeyword()));
            builder.should(QueryBuilders.matchPhraseQuery("content", param.getKeyword()));
            String s = builder.toString();
            System.out.println(s);
            Page<EsBlog> search = (Page<EsBlog>) esBlogRepository.search(builder);
            List<EsBlog> content = search.getContent();
            map.put("list", content);
        } else {
            return "i dont understand";
        }
        watch.stop();
        long totalTimeMillis = watch.getTotalTimeMillis();
        map.put("duration", totalTimeMillis);
        return map;
    }

    @GetMapping("/blog/{id}")
    public Object blog(@PathVariable("id") Integer id) {
        Optional<MysqlBlog> byId = mysqlBlogRepository.findById(id);
        MysqlBlog blog = byId.get();
        return blog;
    }

    @Data
    public static class Param {
        private String type;
        private String keyword;
    }

}
