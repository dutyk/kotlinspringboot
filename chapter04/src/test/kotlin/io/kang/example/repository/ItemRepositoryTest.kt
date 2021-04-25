package io.kang.example.repository

import io.kang.example.entity.Item
import org.elasticsearch.index.query.QueryBuilders
import org.junit.Assert
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@ExtendWith(SpringExtension::class)
class ItemRepositoryTest {
    @Autowired
    lateinit var elasticsearchTemplate: ElasticsearchTemplate

    @Autowired
    lateinit var itemRepository: ItemRepository

    @Test
    @Order(1)
    fun testCreateIndex() {
        elasticsearchTemplate.createIndex(Item::class.java)
        elasticsearchTemplate.putMapping(Item::class.java)
    }

    @Test
    @Order(2)
    fun testAddNewItem() {
        val item = Item(1L, "Iphone 11", "手机", "苹果", 8000.0, "http://image.baidu.com/13123.jpg")
        itemRepository.save(item)
    }

    @Test
    @Order(3)
    fun testBatchAddNewItems(){
        val items = arrayOf(
                Item(2L, "坚果手机R1", " 手机", "锤子", 3699.00, "http://image.baidu.com/13123.jpg"),
                Item(3L, "华为META10", " 手机", "华为", 4499.00, "http://image.baidu.com/13123.jpg")
        )
        itemRepository.saveAll(items.asList())
    }

    @Test
    @Order(4)
    fun testUpdateItem(){
        val item = Item(1L, "苹果XSMax", " 手机", "小米", 3499.00, "http://image.baidu.com/13123.jpg")
        itemRepository.save(item)
    }

    @Test
    @Order(5)
    fun testFindAll(){
        val items = itemRepository.findAll()
        Assert.assertEquals(3, items.toList().size)
    }

    @Test
    @Order(6)
    fun testFindByPage(){
        val items = itemRepository.findAll(PageRequest.of(1, 2))
        items.forEach { println(it) }
    }

    @Test
    @Order(7)
    fun testFindBySort(){
        val items = itemRepository.findAll(Sort.by("price").descending())
        items.forEach { println(it) }
    }

    @Test
    @Order(8)
    fun testMatchQuery(){
        val queryBuilder = NativeSearchQueryBuilder()
        queryBuilder.withQuery(QueryBuilders.matchQuery("title", "坚果手机"))
        val items = itemRepository.search(queryBuilder.build())
        Assert.assertEquals(1, items.totalElements)
        items.forEach { println(it) }
    }

    @Test
    @Order(9)
    fun testTermQuery(){
        val queryBuilder = NativeSearchQueryBuilder()
        queryBuilder.withQuery(QueryBuilders.termQuery("title", "坚果"))
        val items = itemRepository.search(queryBuilder.build())
        Assert.assertEquals(1, items.totalElements)
        items.forEach { println(it) }
    }

    @Test
    @Order(10)
    fun testFuzzyQuery(){
        val queryBuilder = NativeSearchQueryBuilder()
        queryBuilder.withQuery(QueryBuilders.fuzzyQuery("title", "坚果"))
        val items = itemRepository.search(queryBuilder.build())
        Assert.assertEquals(1, items.totalElements)
        items.forEach { println(it) }
    }

    @Test
    @Order(11)
    fun testBooleanQuery(){
        val queryBuilder = NativeSearchQueryBuilder()
        queryBuilder.withQuery(QueryBuilders.boolQuery()
                .must(QueryBuilders.termQuery("title", "坚果"))
                .must(QueryBuilders.termQuery("brand", "锤子")))

        val items = itemRepository.search(queryBuilder.build())
        Assert.assertEquals(1, items.totalElements)
        items.forEach { println(it) }
    }

    @Test
    @Order(12)
    fun testRangeQuery(){
        val queryBuilder = NativeSearchQueryBuilder()
        queryBuilder.withQuery(QueryBuilders.rangeQuery("price").from(3000).to(4000))

        val items = itemRepository.search(queryBuilder.build())
        Assert.assertEquals(2, items.totalElements)
        items.forEach { println(it) }
    }

    @Test
    @Order(13)
    fun testDelete(){
        itemRepository.deleteById(1)

        Assert.assertEquals(2, itemRepository.count())
    }
}