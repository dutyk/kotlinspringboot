package io.kang.provider.domain

import lombok.Data
import java.io.Serializable

@Data
class TbUser : Serializable {
    var id: Int? = null
    var name: String? = null
    var age: Int? = null
}