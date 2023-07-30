package com.example.easybbsweb.repository.es;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.example.easybbsweb.common.ElasticSearchOption;
import com.example.easybbsweb.common.YouFaExceptionStatusCode;
import com.example.easybbsweb.exception.YouFaException;
import com.example.easybbsweb.repository.entity.ForumArticleES;
import com.example.easybbsweb.utils.StringUtil;
import com.example.easybbsweb.repository.entity.ForumArticleES;
import jakarta.annotation.Resource;
import org.springframework.data.elasticsearch.client.elc.QueryBuilders;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ForumArticleElasticSearchDAO {
    @Resource
    private ElasticsearchClient client;
    private final String indexName = ElasticSearchOption.FORUM_ARTICLE_INDEX;

    public void save(ForumArticleES ForumArticleES) {

        try {
            IndexResponse response = client.index(i -> i
                    .index(indexName)
                    .id(ForumArticleES.getId())
                    .document(ForumArticleES)
            );
        } catch (IOException e) {
            throw new YouFaException(YouFaExceptionStatusCode.ELASTIC_SEARCH_ERROR);
        }

    }

    public void deleteById(String id) {
        try {
            client.delete(r -> r.index(indexName).id(id));
        } catch (IOException e) {
            throw new YouFaException(YouFaExceptionStatusCode.ELASTIC_SEARCH_ERROR);
        }
    }

    public ForumArticleES findById(String id) {
        SearchResponse<ForumArticleES> response;
        SearchRequest request = SearchRequest.of(s -> s.query(QueryBuilders.termQueryAsQuery("id", id)));
        try {
            response = client.search(request, ForumArticleES.class);
        } catch (IOException e) {
            throw new YouFaException(YouFaExceptionStatusCode.ELASTIC_SEARCH_ERROR);
        }
        return response.hits().hits().get(0).source();

    }

    public SearchResponse<ForumArticleES> getPages(String key, Integer pageSize, String id, String otherValue, Integer flag) {

        List<SortOptions> sortOptions = new ArrayList<>();
        SortOptions sort = getSortOptionsByFlag(flag);
        if (sort != null) sortOptions.add(sort);
        sortOptions.add(SortOptions.of(s -> s.field(f -> f.field("id"))));

        List<FieldValue> fieldValues = new ArrayList<>();
        if (StringUtil.isNumeric(otherValue)) {
            fieldValues.add(FieldValue.of(Long.parseLong(otherValue)));
        }
        if (id != null) fieldValues.add(FieldValue.of(id));

        return search(key, sortOptions, fieldValues, pageSize);

    }

    private SortOptions getSortOptionsByFlag(Integer flag) {
        switch (flag) {
            case ElasticSearchOption.SEARCH_NEW -> {
                return SortOptions.of(s -> s.field(f -> f.field("createTime").order(SortOrder.Desc)));
            }
            case ElasticSearchOption.SEARCH_OLD -> {
                return SortOptions.of(s -> s.field(f -> f.field("createTime").order(SortOrder.Asc)));
            }
            case ElasticSearchOption.SEARCH_HOT -> {
                return SortOptions.of(s -> s.field(f -> f.field("visited").order(SortOrder.Desc)));
            }
            case ElasticSearchOption.SEARCH_AGREE -> {
                return SortOptions.of(s -> s.field(f -> f.field("agree").order(SortOrder.Desc)));
            }
            default -> {
                return null;
            }
        }
    }

    private SearchResponse<ForumArticleES> search(String key, List<SortOptions> sortOptions, List<FieldValue> fieldValues, Integer pageSize) { // 查询指定key，10条数据
        SearchResponse<ForumArticleES> response;
        SearchRequest request;
        if (fieldValues != null && fieldValues.size() != 0) {

            request = SearchRequest.of(searchBuilder -> searchBuilder.index(ElasticSearchOption.FORUM_ARTICLE_INDEX)
                    .sort(sortOptions)
                    .size(pageSize)
                    .searchAfter(fieldValues)
                    .query(queryBuild -> {
                                if (key == null || key.isEmpty()) {  // 如果没有关键词
                                    return queryBuild.matchAll(QueryBuilders.matchAllQuery());
                                } else {
                                    return queryBuild.match(v -> v.field("content").query(key).analyzer("ik_smart")
                                            .field("title").query(key).analyzer("ik_smart"));
                                }
                            }
                    )
            );
        } else {
            request = SearchRequest.of(searchBuilder -> searchBuilder.index(ElasticSearchOption.FORUM_ARTICLE_INDEX)
                    .sort(sortOptions)
                    .size(pageSize)
                    .query(queryBuild -> {
                                if (key == null || key.isEmpty()) {
                                    return queryBuild.matchAll(QueryBuilders.matchAllQuery());
                                } else {
                                    return queryBuild.match(v -> v.field("content").query(key).analyzer("ik_smart") // 检索内容
                                            .field("title").query(key).analyzer("ik_smart")); // 检索标题
                                }
                            }
                    )
            );
        }
        System.out.println(fieldValues);
        try {
            System.out.println(request);
            response = client.search(request, ForumArticleES.class);
        } catch (IOException e) {
            throw new YouFaException(YouFaExceptionStatusCode.ELASTIC_SEARCH_ERROR);
        }
        return response;
    }

    public void show(String key) throws IOException {
        FieldValue of = FieldValue.of("0");
        SearchRequest request = SearchRequest.of(f -> f.query(q -> q.matchAll(QueryBuilders.matchAllQuery())));
        request.searchAfter().add(of);


    }

}