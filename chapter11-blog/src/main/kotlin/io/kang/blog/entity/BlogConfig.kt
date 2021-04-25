package io.kang.blog.entity

import com.fasterxml.jackson.annotation.JsonFormat
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "tb_config")
class BlogConfig {
    @Id
    var configName: String = ""
        set(configName) {
            field = configName.trim { it <= ' ' }
        }

    var configValue: String = ""
        set(configValue) {
            field = configValue.trim { it <= ' ' }
        }
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    var createTime: Date = Date()

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    var updateTime: Date = Date()

    override fun toString(): String {
        return "${javaClass.simpleName} [Hash = ${hashCode()}, configName=$configName, " +
                "configValue=$configValue, createTime=$createTime, updateTime=$updateTime]"
    }
}