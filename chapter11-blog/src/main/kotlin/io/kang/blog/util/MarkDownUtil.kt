package io.kang.blog.util

import org.commonmark.ext.gfm.tables.TablesExtension
import org.commonmark.parser.Parser
import org.commonmark.renderer.html.HtmlRenderer
import org.springframework.util.StringUtils

object MarkDownUtil {
    fun mdToHtml(markdownString: String?): String {
        if (StringUtils.isEmpty(markdownString))
        {
            return ""
        }

        val extensions = listOf(TablesExtension.create())
        val parser = Parser.builder().extensions(extensions).build()
        val document = parser.parse(markdownString)
        val renderer = HtmlRenderer.builder().extensions(extensions).build()
        return renderer.render(document)
    }
}