package io.kang.config

import com.ctrip.framework.apollo.model.ConfigChangeEvent
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "data")
class ApolloConfig {
    var env: String? = null
    var user: User? = null

    @ApolloConfigChangeListener
    fun configChangeHandlerUserName(configChangeEvent: ConfigChangeEvent) {
        if(configChangeEvent.isChanged("data.user.username")) {
            user?.username = configChangeEvent.getChange("data.user.username").newValue
            println("${user?.username} is change")
        }
    }
}

class User{
    var username: String? = null
    var password: String? = null

    override fun toString(): String {
        return "${username},${password}"
    }
}