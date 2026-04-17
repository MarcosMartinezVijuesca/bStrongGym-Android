package com.svalero.bstronggym.presenter;

import com.svalero.bstronggym.api.ApiClient;
import com.svalero.bstronggym.api.ApiService;
import com.svalero.bstronggym.contract.MonitorContract;
import com.svalero.bstronggym.domain.Monitor;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MonitorPresenter implements MonitorContract.Presenter {

    private MonitorContract.View view;
    private ApiService apiService;

    public MonitorPresenter(MonitorContract.View view) {
        this.view = view;
        this.apiService = ApiClient.getApiService();
    }

    @Override
    public void loadMonitors() {
        apiService.getMonitors().enqueue(new Callback<List<Monitor>>() {
            @Override
            public void onResponse(Call<List<Monitor>> call, Response<List<Monitor>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view.onMonitorsLoaded(response.body());
                } else {
                    view.onError("Error al cargar los monitores");
                }
            }

            @Override
            public void onFailure(Call<List<Monitor>> call, Throwable t) {
                view.onError("Error de conexión: " + t.getMessage());
            }
        });
    }

    @Override
    public void loadMonitorsByName(String name) {
        apiService.getMonitorsByName(name).enqueue(new Callback<List<Monitor>>() {
            @Override
            public void onResponse(Call<List<Monitor>> call, Response<List<Monitor>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view.onMonitorsLoaded(response.body());
                } else {
                    view.onError("Error al buscar monitores");
                }
            }

            @Override
            public void onFailure(Call<List<Monitor>> call, Throwable t) {
                view.onError("Error de conexión: " + t.getMessage());
            }
        });
    }

    @Override
    public void saveMonitor(Monitor monitor) {
        apiService.createMonitor(monitor).enqueue(new Callback<Monitor>() {
            @Override
            public void onResponse(Call<Monitor> call, Response<Monitor> response) {
                if (response.isSuccessful()) {
                    view.onMonitorSaved();
                } else {
                    view.onError("Error al guardar el monitor");
                }
            }

            @Override
            public void onFailure(Call<Monitor> call, Throwable t) {
                view.onError("Error de conexión: " + t.getMessage());
            }
        });
    }

    @Override
    public void updateMonitor(long id, Monitor monitor) {
        apiService.updateMonitor(id, monitor).enqueue(new Callback<Monitor>() {
            @Override
            public void onResponse(Call<Monitor> call, Response<Monitor> response) {
                if (response.isSuccessful()) {
                    view.onMonitorSaved();
                } else {
                    view.onError("Error al actualizar el monitor");
                }
            }

            @Override
            public void onFailure(Call<Monitor> call, Throwable t) {
                view.onError("Error de conexión: " + t.getMessage());
            }
        });
    }

    @Override
    public void deleteMonitor(long id) {
        apiService.deleteMonitor(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    view.onMonitorDeleted();
                } else {
                    view.onError("Error al eliminar el monitor");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                view.onError("Error de conexión: " + t.getMessage());
            }
        });
    }
}
