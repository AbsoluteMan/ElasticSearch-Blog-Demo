package com.imooc.demo.springboot.es.repository.es;

import com.imooc.demo.springboot.es.entity.es.EsBlog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface EsBlogRepository extends ElasticsearchRepository<EsBlog, Integer> {
}
