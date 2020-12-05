package com.egiwon.remote.entity

import com.egiwon.remote.response.ContentResponse
import com.google.gson.Gson
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ContentResponseTest {

    @Test
    fun `영화 데이터 Json을 엔티티로 전환합니다`() {
        val response =
            Gson().fromJson<ContentResponse>(
                Mock.MOCK_MOVIE_RESPONSE_DATA,
                ContentResponse::class.java
            )

        assertThat(response.contentResponseItems.size).isEqualTo(10)
        assertThat(response.contentResponseItems[0].actor).isInstanceOf(String::class.java)
        assertThat(response.contentResponseItems[0].link).isInstanceOf(String::class.java)
        assertThat(response.contentResponseItems[0].image).isInstanceOf(String::class.java)
        assertThat(response.contentResponseItems[0].subtitle).isInstanceOf(String::class.java)
        assertThat(response.contentResponseItems[0].pubDate).isInstanceOf(String::class.java)
        assertThat(response.contentResponseItems[0].director).isInstanceOf(String::class.java)
        assertThat(response.contentResponseItems[0].userRating).isInstanceOf(java.lang.Double::class.java)

        assertThat(response.contentResponseItems[0].director).isNotEmpty()
        assertThat(response.contentResponseItems[0].actor).isNotEmpty()
        assertThat(response.contentResponseItems[0].link).isNotEmpty()
        assertThat(response.contentResponseItems[0].image).isNotEmpty()

    }

    @Test
    fun `블로그 데이터 Json을 엔티티로 전환합니다`() {
        val response =
            Gson().fromJson<ContentResponse>(
                Mock.MOCK_BLOG_RESPONSE_DATA,
                ContentResponse::class.java
            )

        assertThat(response.contentResponseItems[0].title).isInstanceOf(String::class.java)
        assertThat(response.contentResponseItems[0].link).isInstanceOf(String::class.java)
        assertThat(response.contentResponseItems[0].description).isInstanceOf(String::class.java)
        assertThat(response.contentResponseItems[0].bloggername).isInstanceOf(String::class.java)
        assertThat(response.contentResponseItems[0].bloggerlink).isInstanceOf(String::class.java)
        assertThat(response.contentResponseItems[0].postdate).isInstanceOf(String::class.java)

        assertThat(response.contentResponseItems[0].title).isNotEmpty()
        assertThat(response.contentResponseItems[0].link).isNotEmpty()
        assertThat(response.contentResponseItems[0].bloggerlink).isNotEmpty()
        assertThat(response.contentResponseItems[0].postdate).isNotEmpty()

    }

    @Test
    fun `뉴스 데이터 Json을 엔티티로 전환합니다`() {
        val response =
            Gson().fromJson<ContentResponse>(
                Mock.MOCK_NEWS_RESPONSE_DATA,
                ContentResponse::class.java
            )

        assertThat(response.contentResponseItems[0].title).isInstanceOf(String::class.java)
        assertThat(response.contentResponseItems[0].originallink).isInstanceOf(String::class.java)
        assertThat(response.contentResponseItems[0].link).isInstanceOf(String::class.java)
        assertThat(response.contentResponseItems[0].description).isInstanceOf(String::class.java)
        assertThat(response.contentResponseItems[0].pubDate).isInstanceOf(String::class.java)

        assertThat(response.contentResponseItems[0].title).isNotEmpty()
        assertThat(response.contentResponseItems[0].originallink).isNotEmpty()
        assertThat(response.contentResponseItems[0].link).isNotEmpty()
        assertThat(response.contentResponseItems[0].pubDate).isNotEmpty()

    }
}
