package io.kang.blog.service

interface ConfigService {
    /**
     * 修改配置项
     *
     * @param configName
     * @param configValue
     * @return
     */
    fun updateConfig(configName: String, configValue: String): Int

    /**
     * 获取所有的配置项
     *
     * @return
     */
    fun getAllConfigs(): Map<String, String>
}