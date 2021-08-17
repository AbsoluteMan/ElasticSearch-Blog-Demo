package com.imooc.demo.springboot.es;

import com.imooc.demo.springboot.es.entity.es.EsBlog;
import com.imooc.demo.springboot.es.repository.es.EsBlogRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Iterator;

@SpringBootTest
class SpringbootEsApplicationTests {

    @Autowired
    EsBlogRepository blogRepository;

    @Test
    void contextLoads() {
    }

    @Test
    public void testEs() {
        Iterable<EsBlog> all = blogRepository.findAll();
        Iterator<EsBlog> iterator = all.iterator();
        EsBlog next = iterator.next();
        System.out.println("--------" + next.getTitle());
    }

}
