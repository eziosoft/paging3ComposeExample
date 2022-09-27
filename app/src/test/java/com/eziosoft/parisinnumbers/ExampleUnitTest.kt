package com.eziosoft.parisinnumbers

import org.junit.Test
import java.net.URL
import java.net.URLDecoder

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val link = "https://opendata.paris.fr/api/v2/catalog/datasets/lieux-de-tournage-a-paris/records?limit=1&offset=1&include_app_metas=False"

        val currentPage = splitQuery(URL(link))["offset"]?.toInt()
        println(currentPage)
    }

    fun splitQuery(url: URL): Map<String, String> {
        val query_pairs: MutableMap<String, String> = LinkedHashMap()
        val query = url.query
        val pairs = query.split("&").toTypedArray()
        for (pair in pairs) {
            val idx = pair.indexOf("=")
            query_pairs[URLDecoder.decode(pair.substring(0, idx), "UTF-8")] =
                URLDecoder.decode(pair.substring(idx + 1), "UTF-8")
        }
        return query_pairs
    }
}
