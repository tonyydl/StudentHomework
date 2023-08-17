package tony.studenthomework.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tony.studenthomework.BuildConfig

object RetrofitManager {

    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val studentService: StudentService = retrofit.create(StudentService::class.java)

    val recordService: RecordService = retrofit.create(RecordService::class.java)
}