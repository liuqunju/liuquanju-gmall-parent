package com.liuquanju.gmall.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.liuquanju.gmall.api.bean.PmsSkuAttrValue;
import com.liuquanju.gmall.api.bean.search.PmsSearchParam;
import com.liuquanju.gmall.api.bean.search.PmsSearchSkuInfo;
import com.liuquanju.gmall.api.service.ISearchService;
import com.liuquanju.gmall.constant.SearchConstants;
import io.searchbox.client.JestClient;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: yingjie.liu
 * @Date: 2020/03/07/15:48
 */
@Service
public class SearchServiceImpl implements ISearchService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SearchServiceImpl.class);

    @Autowired
    private JestClient jestClient;

    @Override
    public List<PmsSearchSkuInfo> searchInfo(PmsSearchParam pmsSearchParam) {
        String searchSourceBuilder = getSearchDsl(pmsSearchParam);
        LOGGER.info("search dsl :{}", searchSourceBuilder);
        Search build =
                new Search.Builder(searchSourceBuilder).addIndex(SearchConstants.SEARCH_INDEX).addType(SearchConstants.SEARCH_TYPE).build();
        SearchResult executeResult = null;
        try {
            executeResult = jestClient.execute(build);
        } catch (IOException e) {
            LOGGER.error("searchInfo error :{}", e.getMessage());
            e.printStackTrace();
        }

        if (executeResult == null) {
            return Lists.newArrayList();
        }



        List<SearchResult.Hit<PmsSearchSkuInfo, Void>> hits = executeResult.getHits(PmsSearchSkuInfo.class);
        List<PmsSearchSkuInfo> searchSkuInfos = Lists.newArrayList();
        hits.stream().forEach(t -> {
            Map<String, List<String>> highlight = t.highlight;
            if (highlight != null) {
                String skuName = highlight.get("skuName").get(0);
                t.source.setSkuName(skuName);
            }
            searchSkuInfos.add(t.source);
        });

        return searchSkuInfos;
    }

    private String getSearchDsl(PmsSearchParam pmsSearchParam) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();

        /**
         * filter
         */
        if (StringUtils.isNotBlank(pmsSearchParam.getCatalog3Id())) {
            TermQueryBuilder termQueryBuilder = new TermQueryBuilder("catalog3Id", pmsSearchParam.getCatalog3Id());
            boolQueryBuilder.filter(termQueryBuilder);
        }
        List<PmsSkuAttrValue> skuAttrValueList = pmsSearchParam.getSkuAttrValueList();
        if (!CollectionUtils.isEmpty(skuAttrValueList)) {
            skuAttrValueList.stream().forEach(t -> {
                TermQueryBuilder termQueryBuilder = new TermQueryBuilder("skuAttrValueList.valueId", t.getValueId());
                boolQueryBuilder.filter(termQueryBuilder);
            });
        }

        /**
         * must
         */
        if (StringUtils.isNotBlank(pmsSearchParam.getKeyword())) {
            MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("skuName", pmsSearchParam.getKeyword());
            boolQueryBuilder.must(matchQueryBuilder);
        }

        /**
         * query
         */
        searchSourceBuilder.query(boolQueryBuilder);
        searchSourceBuilder.sort("id", SortOrder.DESC);

        /**
         * 搜素的起始位置和条数
         */
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(20);

        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<span style='color:red;'>");
        highlightBuilder.postTags("</ span>");
        highlightBuilder.field("skuName");
        searchSourceBuilder.highlight(highlightBuilder);
        return searchSourceBuilder.toString();
    }
}
