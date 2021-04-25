package io.kang.example.repository

import io.kang.example.entity.Item
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository

interface ItemRepository: ElasticsearchRepository<Item, Long>