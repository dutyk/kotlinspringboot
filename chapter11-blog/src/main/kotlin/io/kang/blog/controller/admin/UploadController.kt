package io.kang.blog.controller.admin

import io.kang.blog.config.Constants
import io.kang.blog.util.MyBlogUtils
import io.kang.blog.util.ResultGenerator
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.IOException
import java.net.URI
import java.net.URISyntaxException
import java.text.SimpleDateFormat
import java.util.*
import javax.servlet.http.HttpServletRequest
import io.kang.blog.util.Result
import org.springframework.beans.factory.annotation.Value

@Controller
@RequestMapping("/admin")
class UploadController {

    @Value("\${upload.dir}")
    var uploadDir: String? = null

    @PostMapping("/upload/file")
    @ResponseBody
    @Throws(URISyntaxException::class)
    fun upload(httpServletRequest: HttpServletRequest, @RequestParam("file") file: MultipartFile): Result<Any> {
        val fileName = file.originalFilename
        val suffixName = fileName!!.substring(fileName.lastIndexOf("."))
        //生成文件名称通用方法
        val sdf = SimpleDateFormat("yyyyMMdd_HHmmss")
        val r = Random()
        val tempName = StringBuilder()
        tempName.append(sdf.format(Date())).append(r.nextInt(100)).append(suffixName)
        val newFileName = tempName.toString()
        val fileDirectory = File(uploadDir)
        //创建文件
        val destFile = File(uploadDir + newFileName)
        try {
            if (!fileDirectory.exists()) {
                if (!fileDirectory.mkdir()) {
                    throw IOException("文件夹创建失败,路径为：$fileDirectory")
                }
            }
            file.transferTo(destFile)
            val resultSuccess = ResultGenerator.genSuccessResult()
            resultSuccess.data = MyBlogUtils.getHost(URI(httpServletRequest.requestURL.toString() + "")).toString() +  "/upload/" + newFileName
            return resultSuccess
        } catch (e: IOException) {
            e.printStackTrace()
            return ResultGenerator.genFailResult("文件上传失败")
        }

    }

}
