package com.liuquanju.gmall.search;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.liuquanju.gmall.api.bean.PmsSkuInfo;
import com.liuquanju.gmall.api.bean.search.PmsSearchSkuInfo;
import com.liuquanju.gmall.api.service.SkuService;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.apache.commons.beanutils.BeanUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LiuquanjuGmallSearchServiceApplicationTests {

    @Reference
    SkuService skuService;

    @Autowired
    JestClient jestClient;

    @Test
    public void contextLoads() throws IOException {

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();

        TermQueryBuilder termQueryBuilder = new TermQueryBuilder("skuAttrValueList.valueId", "39");
        boolQueryBuilder.filter(termQueryBuilder);

        MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("skuName", "华为");
        boolQueryBuilder.must(matchQueryBuilder);

        searchSourceBuilder.query(boolQueryBuilder);

        Search build = new Search.Builder(searchSourceBuilder.toString()).addIndex("gmallpms").addType("PmsSkuInfo").build();

        SearchResult executeResult = jestClient.execute(build);
        List<SearchResult.Hit<PmsSearchSkuInfo, Void>> hits = executeResult.getHits(PmsSearchSkuInfo.class);
        List<PmsSearchSkuInfo> searchSkuInfos = Lists.newArrayList();
        for (SearchResult.Hit<PmsSearchSkuInfo, Void> hit : hits) {
            searchSkuInfos.add(hit.source);
        }
        System.out.println(JSONObject.toJSONString(searchSkuInfos));

    }

    @Test
    public void put() throws InvocationTargetException, IllegalAccessException, IOException {
        List<PmsSkuInfo> skuInfoList = skuService.getAllSku(null);
        List<PmsSearchSkuInfo> searchSkuInfoList = Lists.newLinkedList();

        for (PmsSkuInfo pmsSkuInfo: skuInfoList) {
            PmsSearchSkuInfo pmsSearchSkuInfo = new PmsSearchSkuInfo();
            BeanUtils.copyProperties(pmsSearchSkuInfo, pmsSkuInfo);
            searchSkuInfoList.add(pmsSearchSkuInfo);
        }
        //导入es index:库名，type：表明，id：主键，
        for (PmsSearchSkuInfo pmsSearchSkuInfo : searchSkuInfoList) {

            Index build =
                    new Index.Builder(pmsSearchSkuInfo).index("gmallpms").type("PmsSkuInfo").id(String.valueOf(pmsSearchSkuInfo.getId())).build();
            jestClient.execute(build);
        }
    }
}
