package io.kang.consumer.mapper

import io.kang.consumer.domain.TbUser

interface TbUserMapper {
    fun insert(record: TbUser): Int
}