package io.jamshid.unishop.presentation.feature_main.fragment_worker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.jamshid.unishop.common.Response
import io.jamshid.unishop.data.models.dto.WorkerDto
import io.jamshid.unishop.data.remote.apis.WorkerApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkerViewModel @Inject constructor(
    private val workerApi: WorkerApi
) : ViewModel() {

    init {
        getAllWorkers()
    }


    private var _allWorkers: MutableStateFlow<Response<List<WorkerDto>>> =
        MutableStateFlow(Response.Default())
    val allWorkers: StateFlow<Response<List<WorkerDto>>> get() = _allWorkers

    private fun getAllWorkers() {
        viewModelScope.launch {
            try {
                _allWorkers.emit(Response.Loading())
                val data = workerApi.getAllWorker()
                _allWorkers.emit(Response.Success(data))
            } catch (e: Exception) {
                //_allWorkers.emit(Response.Error(e.localizedMessage!!.toString()))
            }
        }
    }

    fun sortedFromDate(first: Long, second: Long) {
        viewModelScope.launch {
            try {
                _allWorkers.emit(Response.Loading())
                val data = workerApi.getSalesByDate(first, second)
                _allWorkers.emit(Response.Success(data))
            } catch (e: Exception) {
                _allWorkers.emit(Response.Error(e.localizedMessage!!.toString()))
            }
        }
    }

}