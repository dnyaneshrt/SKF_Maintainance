package com.a2mee.skfmaintenance.retrofit

import com.a2mee.skfmaintenance.adapter.CompletedAdapter
import com.a2mee.skfmaintenance.response.*
import com.google.gson.JsonObject
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

//val BASE_URL = "http://localhost:8082"
val BASE_URL = "http://assetmanagement.adp.ind.in:8085"
// val BASE_URL = "http://192.168.28.122:8082"

interface NetworkService {

    @POST("/authentication/login")
    fun doLogin(
        @Body body: JsonObject
    ): Call<LoginResponse>

    @GET("/dashbaord/getEngineerMobileDashboard")
    fun getEngineerMobileDashboard(
        @Query("employeeId") employeeId: String,
        @Header("Authorization") token: String
    ): Call<EmployeeDashboardResponse>

    /*
    @GET("/service/getSpindleServiceRequestBySSCNo")
    fun getSpindleServiceRequestBySSCNo(
        @Query("sscNo") sscNo: String,
        @Header("Authorization") token: String
    ) : Call<SpindleServiceRequestBySSCNoResponse>
    */

    @GET("/service/getSpindleServiceRequestBySSCNo")
    suspend fun getSpindleServiceRequestBySSCNo(
        @Query("sscNo") sscNo: String,
        @Header("Authorization") token: String
    ): Response<SpindleServiceRequestBySSCNoResponse>

    @POST("/service/addSpindleServiceRequestByEngineer")
    suspend fun addSpindleServiceRequestByEngineer(
        // @FieldMap(encoded = true) param: HashMap<String, String>,
        @Body body: JsonObject,
        @Header("Authorization") token: String
    ) : Response<JsonObject>

    @GET("/sop/getInProcessSOPByEnginner")
    suspend fun getInProcessSOPByEnginner(
        @Query("employeeId") employeeId: String,
        @Header("Authorization") token: String
    ) : Response<List<InProcessSOPByEnginnerResponse>>

    @GET("/sop/getPendingSOPByEnginner")
    suspend fun getPendingSOPByEnginner(
        @Query("employeeId") employeeId: String,
        @Header("Authorization") token: String
    ) : Response<List<PendingSOPByEnginnerResponse>>

    @GET("/sop/getCompletedSOPByEnginner")
    suspend fun getCompletedSOPByEnginner(
        @Query("employeeId") employeeId: String,
        @Header("Authorization") token: String
    ) : Response<List<CompletedSOPByEnginnerResponse>>

    @GET("/sop/getPrimarySOPStagesBySOP")
    suspend fun getPrimarySOPStagesBySOP(
        @Query("sopId") sopId: Int,
        @Header("Authorization") token: String
    ) : Response<List<PrimarySOPStagesBySOPResponse>>

    companion object {
        val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        val client =
            OkHttpClient.Builder().addInterceptor(interceptor).connectTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES) // write timeout
                .readTimeout(1, TimeUnit.MINUTES).build();

        operator fun invoke(): NetworkService? {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(NetworkService::class.java)
        }
    }
}