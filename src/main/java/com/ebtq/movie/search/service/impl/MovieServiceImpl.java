package com.ebtq.movie.search.service.impl;

import com.ebtq.movie.search.config.ElasticSearchConfig;
import com.ebtq.movie.search.constant.EsConstant;
import com.ebtq.movie.search.service.MovieService;
import com.ebtq.movie.search.vo.SearchParam;
import com.ebtq.movie.search.vo.SearchResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.nested.NestedAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author yingb
 * @since 2021/3/11
 */
@Service
@Slf4j
public class MovieServiceImpl implements MovieService {

    @Autowired
    RestHighLevelClient client;


    @Override
    public void search(SearchParam params) throws IOException {
        SearchRequest request = buildSearchRequest(params);
        final SearchResponse response = client.search(request, ElasticSearchConfig.COMMON_OPTIONS);
        SearchResult result = buildSearchResult(response);
    }

    private SearchResult buildSearchResult(SearchResponse response) {
        final SearchResult searchResult = new SearchResult();
        final SearchHits hits = response.getHits();
        hits.getTotalHits();

        return searchResult;
    }

    private SearchRequest buildSearchRequest(SearchParam params) {
        final SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        final BoolQueryBuilder query = QueryBuilders.boolQuery();
        if (StringUtils.isNotEmpty(params.getSearchKey())) {
            query.must(QueryBuilders.matchQuery("title", params.getSearchKey()));
        }
        if(StringUtils.isNotEmpty(params.getGenre())) {
            query.should(QueryBuilders.termQuery("genre", params.getGenre()));
        }
        if (StringUtils.isNotEmpty(params.getYear())) {
            query.should(QueryBuilders.termQuery("year", params.getYear()));
        }
        sourceBuilder.from((params.getPageNum() - 1) * EsConstant.MOVIE_PAGE_SIZE );
        sourceBuilder.size(EsConstant.MOVIE_PAGE_SIZE);

        if (StringUtils.isNotEmpty(params.getSearchKey())) {
            final HighlightBuilder highlightBuilder = new HighlightBuilder();
            highlightBuilder.field("title");
            highlightBuilder.preTags("<b style='color: red'>");
            highlightBuilder.postTags("</b>");
            sourceBuilder.highlighter(highlightBuilder);
        }


        final TermsAggregationBuilder genreAgg = AggregationBuilders.terms("genre_agg");
        genreAgg.field("title").size(50);
        sourceBuilder.aggregation(genreAgg);
        sourceBuilder.query(query);
        return new SearchRequest(new String[]{EsConstant.MOVIE_INDEX}, sourceBuilder);
    }

    @Override
    public void suggest(String name) {

    }
}
