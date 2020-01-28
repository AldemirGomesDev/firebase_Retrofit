package com.example.firebaseretrofit

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.firebaseretrofit.models.MarkPointRequest
import com.example.firebaseretrofit.models.Marking
import com.example.firebaseretrofit.services.APIInterface
import com.example.firebaseretrofit.util.Constants
import com.github.kittinunf.result.coroutines.SuspendableResult
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity(), CoroutineScope {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab_point.setOnClickListener {

            launch {
                withContext(Dispatchers.Main) {
                    async { markPoint() }.await()

                    Log.w("firebaseTeste", "Concluido com sucesso!")

                }
            }

        }
    }

    private suspend fun markPoint() {

        SuspendableResult.of<MarkPointRequest.Response, Exception> {
            markPointFirebase()
        }.fold(
            success = { testResponse ->
                if (testResponse != null) {

                    Log.d("firebaseTeste", "onSuccess ${testResponse.name}")
                    getPoint(testResponse.name)
                }
            },
            failure ={ error ->
                Log.e("firebaseTeste", "falhou $error")

            }
        )
    }

    private suspend fun getPoint(pointID: String?) {

        SuspendableResult.of<MarkPointRequest, Exception> {
            getUserPoint(pointID)
        }.fold(
            success = { testResponse ->
                if (testResponse != null) {

                    Log.d("firebaseTeste", "onSuccess ID: ${testResponse.mark.id}")
                    Log.d("firebaseTeste", "onSuccess NOME: ${testResponse.mark.name}")
                    Log.d("firebaseTeste", "onSuccess DATA: ${testResponse.mark.date}")
                    Log.d("firebaseTeste", "onSuccess HORA: ${testResponse.mark.hour}")
                }
            },
            failure ={ error ->
                Log.e("firebaseTeste", "falhou $error")

            }
        )
    }

    private suspend fun getAllPointFirebase() {
        val point = MarkPointRequest()
        point.mark = Marking()

        SuspendableResult.of<MarkPointRequest.Response, Exception> {
            getAllPoint()
        }.fold(
            success = { testResponse ->
                if (testResponse != null) {

                    Log.d("firebaseTeste", "onSuccess ID: ${listOf(testResponse.name)}")
                }
            },
            failure ={ error ->
                Log.e("firebaseTeste", "falhou $error")

            }
        )
    }

    suspend fun markPointFirebase() :  MarkPointRequest.Response{
        val point = MarkPointRequest()
        point.mark = MarkPointRequest.marking

        return APIInterface.getInstance(Constants.API.BASE_URL_FIREBASE, App.getContext()).markPointFirebase(
            point
        ).await()
    }

    suspend fun getUserPoint(pointID: String?) : MarkPointRequest {

        return APIInterface.getInstance(Constants.API.BASE_URL_FIREBASE, App.getContext()).getUserPointFirebase(pointID)
            .await()

    }

    suspend fun getAllPoint() : MarkPointRequest.Response {

        return APIInterface.getInstance(Constants.API.BASE_URL_FIREBASE, App.getContext()).getAllPointFirebase()
            .await()

    }

    // Um job representa uma ou conjunto de tarefas em backgroud.
    private val job = Job()

    override val coroutineContext
        get() = Dispatchers.IO + job


}
