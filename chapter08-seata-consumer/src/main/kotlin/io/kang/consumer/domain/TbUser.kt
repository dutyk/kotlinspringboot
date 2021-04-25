package io.kang.consumer.domain

import lombok.Data
import java.io.Serializable

@Data
class TbUser : Serializable {
    var id: Int? = null
    var name: String? = null
    var age: Int? = null
}