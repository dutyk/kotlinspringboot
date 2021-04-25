package io.kang.provider.mapper

import io.kang.provider.domain.TbUser

interface TbUserMapper {
    fun insert(record: TbUser): Int
}