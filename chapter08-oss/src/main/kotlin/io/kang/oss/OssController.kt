package io.kang.oss

import com.aliyun.oss.OSS
import com.aliyun.oss.common.utils.IOUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.web.bind.annotation.RestController
import org.springframework.core.io.WritableResource
import org.springframework.web.bind.annotation.GetMapping
import org.apache.commons.codec.CharEncoding
import org.springframework.util.StreamUtils
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Paths

@RestController
class OssController {
    @Autowired
    lateinit var ossClient: OSS

    @Value("classpath:/oss-test.json")
    lateinit var localFile: Resource

    @Value("oss://kcglobal/test/oss-test.json")
    lateinit var remoteFile: Resource

    @GetMapping("/upload")
    fun upload(): String {
        try {
            val stream = this.javaClass.classLoader.getResourceAsStream("oss-test.json")
            ossClient.putObject("kcglobal", "test/oss-test.json", stream)
        } catch (e: Exception) {
            return "upload fail: " + e.message
        }
        return "upload success"
    }

    @GetMapping("/file-resource")
    fun fileResource(): String {
        return try {
            "get file resource success. content: " +
                    StreamUtils.copyToString(remoteFile.inputStream, Charset.forName(CharEncoding.UTF_8))
        } catch (e: Exception) {
            "get resource fail: " + e.message
        }
    }

    @GetMapping("/download")
    fun download(): String {
        return try {
            val ossObject = ossClient.getObject("kcglobal", "test/oss-test.json")
            "download success, content: " + IOUtils.readStreamAsString(ossObject.objectContent, CharEncoding.UTF_8)
        } catch (e: Exception) {
            "download fail: " + e.message
        }

    }

    @GetMapping("/upload2")
    fun uploadWithOutputStream(): String {
        try {
            (this.remoteFile as WritableResource)
                    .outputStream
                    .use { outputStream ->
                        localFile.inputStream.use { inputStream -> StreamUtils.copy(inputStream, outputStream)
                        }
                    }
        } catch (ex: Exception) {
            return "upload with outputStream failed"
        }

        return "upload success"
    }
}