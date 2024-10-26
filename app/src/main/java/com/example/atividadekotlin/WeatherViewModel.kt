package com.example.atividade

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.atividade.api.Constant
import com.example.atividade.api.NetworkResponse
import com.example.atividade.api.RetrofitInstance
import com.example.atividade.api.WeatherModel
import com.example.atividadekotlin.backend.DAOViewModel
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    private val weatherApi = RetrofitInstance.weatherApi
    private val daoViewModel = DAOViewModel()


    private val _weatherResult = MutableLiveData<NetworkResponse<WeatherModel>>()
    val weatherResult : LiveData<NetworkResponse<WeatherModel>> = _weatherResult//recebe o resultado

    fun listarLocal() {
        // Chama a função listarPrincipal do DAOViewModel
        daoViewModel.listarPrincipal { localizacao ->
            // Obtém a descrição da localização
            val descricao = localizacao?.descricao!!
            Log.i("uiiuiuiu", descricao)
            getData(descricao)
        }
    }

    fun getData(cidade: String?) {

        if (cidade.isNullOrBlank()) {
            _weatherResult.postValue(NetworkResponse.Error("Digite uma localização"))
            return
        }

        _weatherResult.postValue(NetworkResponse.Loading)
        viewModelScope.launch {
            try {
                val response = weatherApi.getWeather(Constant.apiKey, cidade)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _weatherResult.postValue(NetworkResponse.Sucess(it))
                    }
                } else {
                    _weatherResult.postValue(NetworkResponse.Error("Erro ao pesquisar: ${response.message()}"))
                }
            } catch (e: Exception) {
                _weatherResult.postValue(NetworkResponse.Error("Erro ao pesquisar"))
            }
        }
    }

}