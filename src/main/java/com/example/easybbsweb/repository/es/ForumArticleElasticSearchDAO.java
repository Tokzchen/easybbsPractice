package com.example.easybbsweb.repository.es;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchAllQuery;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.example.easybbsweb.common.ElasticSearchOption;
import com.example.easybbsweb.repository.entity.ForumArticle;
import jakarta.annotation.Resource;
import org.springframework.data.elasticsearch.client.elc.QueryBuilders;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ForumArticleElasticSearchDAO {
    @Resource
    ElasticsearchClient client;

    public void saveForumArticle(ForumArticle forumArticle) throws IOException {
        IndexResponse response = client.index(i -> i
                .index("youfa")
                .id(forumArticle.getId())
                .document(forumArticle)
        );
    }

    public SearchResponse<ForumArticle> getPages(String key, Integer pageSize, String id, Object otherFieldValue, Integer flag) {
        List<SortOptions> sortOptions = new ArrayList<>();
        if (getSortOptionsByFlag(flag) != null) {
            sortOptions.add(SortOptions.of(s -> s.field(f -> f.field("id"))));
        }
        List<FieldValue> fieldValues = new ArrayList<>();
        if (otherFieldValue instanceof String) {
            fieldValues.add(FieldValue.of((String) otherFieldValue));
        } else if (otherFieldValue instanceof Integer) {
            fieldValues.add(FieldValue.of((Integer) otherFieldValue));
        } else if (otherFieldValue instanceof Date) {
            fieldValues.add(FieldValue.of(((Date) otherFieldValue).getTime()));
        }
        if (id != null) fieldValues.add(FieldValue.of(id));
        return search(key, sortOptions, fieldValues, pageSize);

    }

    private SortOptions getSortOptionsByFlag(Integer flag) {
        switch (flag) {
            case ElasticSearchOption.NEW -> {
                return SortOptions.of(s -> s.field(f -> f.field("createTime").order(SortOrder.Desc)));
            }
            case ElasticSearchOption.OLD -> {
                return   SortOptions.of(s -> s.field(f -> f.field("createTime").order(SortOrder.Asc)));
            }
            case ElasticSearchOption.HOT -> {
                return SortOptions.of(s -> s.field(f -> f.field("visited").order(SortOrder.Desc)));
            }
            case ElasticSearchOption.AGREE -> {
                return SortOptions.of(s -> s.field(f -> f.field("agree").order(SortOrder.Desc)));
            }
            default -> {
                return null;
            }
        }
    }

    private SearchResponse<ForumArticle> search(String key, List<SortOptions> sortOptions, List<FieldValue> fieldValues, Integer pageSize) { // 查询指定key，10条数据
        SearchResponse<ForumArticle> response;
        SearchRequest request = SearchRequest.of(searchBuilder -> searchBuilder.index(ElasticSearchOption.FORUM_ARTICLE_INDEX)
                .sort(sortOptions)
                .size(pageSize)
                .query(queryBuild -> {
                            if (key == null) {  // 如果没有关键词
                                return queryBuild.matchAll(QueryBuilders.matchAllQuery());
                            } else {
                                return queryBuild.match(v -> v.field("content").query(key));
                            }
                        }
                )
        );
        if (fieldValues.size() != 0) {  // 不是第一页
            request.searchAfter().addAll(fieldValues);
        }
        try {
            response = client.search(request, ForumArticle.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return response;
    }

    public void show(String key) throws IOException {
        SortOptions createTime = SortOptions.of(s -> s.field(f -> f.field("createTime")));
        SortOptions id = SortOptions.of(s -> s.field(f -> f.field("id")));
        MatchAllQuery matchAllQuery = QueryBuilders.matchAllQuery();
        SearchRequest request = SearchRequest.of(searchBuilder -> searchBuilder.index(ElasticSearchOption.FORUM_ARTICLE_INDEX)
                .query(queryBuild -> queryBuild.matchAll(QueryBuilders.matchAllQuery())));

    }

}