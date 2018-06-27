package com.deleven.imagesearchtask.imagelisting

import com.deleven.imagesearchtask.base.utils.BaseConstants
import com.deleven.imagesearchtask.imagelisting.domain.api.ImageListingApi
import com.deleven.imagesearchtask.imagelisting.models.ApiResponse
import io.reactivex.observers.TestObserver
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Okio
import org.hamcrest.CoreMatchers.`is`
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@RunWith(JUnit4::class)
class ImageListingServiceTest {


    private lateinit var service: ImageListingApi

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val okHttpClient = OkHttpClient.Builder()
                .build()

        service = Retrofit.Builder()
                .baseUrl(mockWebServer.url("/"))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
                .create(ImageListingApi::class.java)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun getImageListing() {
        enqueueResponse("response.json")

        val testObserver = TestObserver<ApiResponse>()

        service.getImageListing(BaseConstants.API_KEY, "rose", BaseConstants.IMAGE_TYPE).subscribe(testObserver)

        testObserver.awaitTerminalEvent(2, TimeUnit.SECONDS)

        //no errors
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1) //one list emitted

        val request = mockWebServer.takeRequest() //get the request that we made
       //Assert.assertThat(request.path, `is` ("")) //wrong path
        Assert.assertThat(request.path, `is` ("/api/?key=9245065-e43e07fe4fe27ac1b7929d4e1&q=rose&image_type=photo"))//correct requested params

        val apiResponse:ApiResponse

        testObserver.assertValue {
          // it.total == 12972 // correct one
             it.total == 129721 //incorrect one
        }

    }


    @Test
    fun getImageListingWithError(){
        enqueueResponseReturnsError("response.json")

        val testObserver = TestObserver<ApiResponse>()

        service.getImageListing(BaseConstants.API_KEY, "rose", BaseConstants.IMAGE_TYPE).subscribe(testObserver)
        testObserver.awaitTerminalEvent(2, TimeUnit.SECONDS)

        //no values
        testObserver.assertNoValues()
        //one error occured
        Assert.assertEquals(1,testObserver.errorCount())// wrong case
        //Assert.assertEquals(0,testObserver.errorCount())// wrong case
    }


    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = javaClass.classLoader.getResourceAsStream(fileName)
        val source = Okio.buffer(Okio.source(inputStream))
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(
                mockResponse
                        .setBody(source.readString(Charsets.UTF_8))
        )
    }

    private fun enqueueResponseReturnsError(fileName: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = javaClass.classLoader.getResourceAsStream(fileName)
        val source = Okio.buffer(Okio.source(inputStream))
        val mockResponse = MockResponse()
        mockResponse.setResponseCode(500)
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(
                mockResponse
                        .setBody(source.readString(Charsets.UTF_8))
        )
    }
}