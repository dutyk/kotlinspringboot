package io.kang.blog.util

import org.springframework.util.StringUtils
import java.security.MessageDigest

object MD5Util {
    private fun byteArrayToHexString(b: ByteArray): String {
        val resultSb = StringBuffer()
        for (i in b.indices)
            resultSb.append(byteToHexString(b[i]))

        return resultSb.toString()
    }

    private fun byteToHexString(b: Byte): String {
        var n = b.toInt()
        if (n < 0)
            n += 256
        val d1 = n / 16
        val d2 = n % 16
        return hexDigits[d1] + hexDigits[d2]
    }

    fun MD5Encode(origin: String, charsetname: String): String {
        var resultString: String = origin
        try {
            val md = MessageDigest.getInstance("MD5")
            if (StringUtils.isEmpty(charsetname))
                resultString = byteArrayToHexString(md.digest(resultString!!
                        .toByteArray()))
            else
                resultString = byteArrayToHexString(md.digest(resultString!!
                        .toByteArray(charset(charsetname))))
        } catch (exception: Exception) {
        }

        return resultString
    }

    private val hexDigits = arrayOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f")
}