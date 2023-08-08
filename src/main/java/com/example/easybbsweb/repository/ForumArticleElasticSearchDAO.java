package com.example.easybbsweb.repository;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.example.easybbsweb.common.ElasticSearchConstants;
import com.example.easybbsweb.common.YouFaExceptionStatusCode;
import com.example.easybbsweb.controller.response.forum.ForumArticleRespFromES;
import com.example.easybbsweb.domain.entity.ForumArticle;
import com.example.easybbsweb.exception.YouFaException;
import com.example.easybbsweb.utils.StringUtil;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.qiniu.util.Json;
import jakarta.annotation.Resource;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.client.ElasticsearchClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.Collections.singletonMap;

@Repository
public class ForumArticleElasticSearchDAO {
    @Resource
    RestHighLevelClient restHighLevelClient;
    @Value("${articleConfig.countPerPage}")
    Integer COUNT_PER_PAGE;

    private final String indexName = ElasticSearchConstants.FORUM_ARTICLE_INDEX;

    public void save(ForumArticle ForumArticle) {

        try {
            IndexRequest request = new IndexRequest(indexName)
                    .id(ForumArticle.getId().toString())
                    .source(JSONObject.toJSONString(ForumArticle), XContentType.JSON);
            restHighLevelClient.index(request, RequestOptions.DEFAULT);

        } catch (IOException e) {
            e.printStackTrace();
            throw new YouFaException(YouFaExceptionStatusCode.ELASTIC_SEARCH_ERROR);
        }

    }

    public void updateLike(Long id, Integer likeCount)  {
//        UpdateByQueryRequest request = new UpdateByQueryRequest();
//        request.setQuery(QueryBuilders.matchQuery("id",id));
        UpdateRequest request = new UpdateRequest(indexName,id.toString());

        Map<String, Object> parameters = singletonMap("count", likeCount);

        Script inline = new Script(ScriptType.INLINE, "painless",
                "ctx._source.like = params.count", parameters);
        request.script(inline);
        try {
            restHighLevelClient.update(request,RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            throw new YouFaException(YouFaExceptionStatusCode.ELASTIC_SEARCH_ERROR);
        }
        System.out.println(request);
        System.out.println("执行脚本");
    }


    public void deleteById(String id) {
        try {
            restHighLevelClient.delete(new DeleteRequest().index(indexName).id(id), RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new YouFaException(YouFaExceptionStatusCode.ELASTIC_SEARCH_ERROR);
        }
    }

    public ForumArticle findById(String id) {
        SearchResponse response;
        SearchRequest request = new SearchRequest(indexName);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("id", id));
        request.source(searchSourceBuilder);
        try {
            response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new YouFaException(YouFaExceptionStatusCode.ELASTIC_SEARCH_ERROR);
        }
        return JSON.parseObject(response.getHits().getHits()[0].getSourceAsString(), ForumArticle.class);

    }

    /**
     * @param key           搜索关键字
     * @param pageSize      一页大小
     * @param id            id字段的排序值
     * @param otherValue    其他字段的排序值 (用于分页)
     * @param flag          按照什么字段
     * @param queryBuilders 其他条件 (Nullable)  mustQuery
     * @return response
     */
    public SearchResponse getPages(String key, Integer pageSize, Long id, Object otherValue, Integer flag, List<QueryBuilder> queryBuilders) {
        SearchResponse response;
        SearchRequest request = new SearchRequest(indexName);
        SearchSourceBuilder builder = new SearchSourceBuilder();
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        // 条件查询
        if (key != null && key.length() != 0) {  // 关键字
            boolQuery.should(QueryBuilders.matchQuery("content", key).analyzer("ik_smart"));
            boolQuery.should(QueryBuilders.matchQuery("title", key).analyzer("ik_smart"));
        } else {
            boolQuery.must(QueryBuilders.matchAllQuery());
        }
        if (queryBuilders != null) {
            for (QueryBuilder queryBuilder : queryBuilders) {
                // 其他条件
                boolQuery.must(queryBuilder);
            }
        }
        builder.query(boolQuery);
        // 排序
        FieldSortBuilder fieldSortBuilder2 = new FieldSortBuilder("id");


        builder.sort(getSortOptionsByFlag(flag));
        builder.sort("id", SortOrder.DESC);
        // 分页
        if (otherValue != null) {
            builder.searchAfter(new Object[]{otherValue, id});
        }
        builder.size(pageSize);


        try {
            response = restHighLevelClient.search(request.source(builder), RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new YouFaException(YouFaExceptionStatusCode.ELASTIC_SEARCH_ERROR);
        }

        return response;


    }

    private SortBuilder getSortOptionsByFlag(Integer flag) {
        switch (flag) {
            case ElasticSearchConstants.SEARCH_NEW -> {
                return SortBuilders.fieldSort("createTime").order(SortOrder.DESC);
            }
            case ElasticSearchConstants.SEARCH_OLD -> {
                return SortBuilders.fieldSort("createTime").order(SortOrder.ASC);
            }
            case ElasticSearchConstants.SEARCH_HOT -> {
                return SortBuilders.fieldSort("visited").order(SortOrder.DESC);
            }
            case ElasticSearchConstants.SEARCH_AGREE -> {
                return SortBuilders.fieldSort("like").order(SortOrder.DESC);
            }
            default -> {
                return null;
            }
        }
    }

    public ForumArticleRespFromES SearchResponseToForumArticleRespFromES(SearchResponse response) {
        List<ForumArticle> forumArticles = new ArrayList<>();
        List<Object> lastSortValue = new ArrayList<>();
        for (SearchHit hit : response.getHits().getHits()) {
            ForumArticle forumArticle;
//            System.out.println(hit.getSourceAsString());
            forumArticle = com.alibaba.fastjson2.JSON.parseObject(hit.getSourceAsString(), ForumArticle.class);

            forumArticles.add(forumArticle);
            lastSortValue = Arrays.asList(hit.getSortValues());
        }
        return new ForumArticleRespFromES(forumArticles, lastSortValue);
    }


}
