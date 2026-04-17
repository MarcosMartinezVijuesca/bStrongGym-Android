package com.svalero.bstronggym.presenter;

import com.svalero.bstronggym.api.ApiClient;
import com.svalero.bstronggym.api.ApiService;
import com.svalero.bstronggym.contract.ActivityContract;
import com.svalero.bstronggym.domain.Activity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityPresenter implements ActivityContract.Presenter {

    private ActivityContract.View view;
    private ApiService apiService;

    public ActivityPresenter(ActivityContract.View view) {
        this.view = view;
        this.apiService = ApiClient.getApiService();
    }

    @Override
    public void loadActivities() {
        apiService.getActivities().enqueue(new Callback<List<Activity>>() {
            @Override
            public void onResponse(Call<List<Activity>> call, Response<List<Activity>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view.onActivitiesLoaded(response.body());
                } else {
                    view.onError("Error al cargar las actividades");
                }
            }

            @Override
            public void onFailure(Call<List<Activity>> call, Throwable t) {
                view.onError("Error de conexión: " + t.getMessage());
            }
        });
    }

    @Override
    public void loadActivitiesByName(String name) {
        apiService.getActivitiesByName(name).enqueue(new Callback<List<Activity>>() {
            @Override
            public void onResponse(Call<List<Activity>> call, Response<List<Activity>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view.onActivitiesLoaded(response.body());
                } else {
                    view.onError("Error al buscar actividades");
                }
            }

            @Override
            public void onFailure(Call<List<Activity>> call, Throwable t) {
                view.onError("Error de conexión: " + t.getMessage());
            }
        });
    }

    @Override
    public void saveActivity(Activity activity) {
        apiService.createActivity(activity).enqueue(new Callback<Activity>() {
            @Override
            public void onResponse(Call<Activity> call, Response<Activity> response) {
                if (response.isSuccessful()) {
                    view.onActivitySaved();
                } else {
                    view.onError("Error al guardar la actividad");
                }
            }

            @Override
            public void onFailure(Call<Activity> call, Throwable t) {
                view.onError("Error de conexión: " + t.getMessage());
            }
        });
    }

    @Override
    public void updateActivity(long id, Activity activity) {
        apiService.updateActivity(id, activity).enqueue(new Callback<Activity>() {
            @Override
            public void onResponse(Call<Activity> call, Response<Activity> response) {
                if (response.isSuccessful()) {
                    view.onActivitySaved();
                } else {
                    view.onError("Error al actualizar la actividad");
                }
            }

            @Override
            public void onFailure(Call<Activity> call, Throwable t) {
                view.onError("Error de conexión: " + t.getMessage());
            }
        });
    }

    @Override
    public void deleteActivity(long id) {
        apiService.deleteActivity(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    view.onActivityDeleted();
                } else {
                    view.onError("Error al eliminar la actividad");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                view.onError("Error de conexión: " + t.getMessage());
            }
        });
    }
}
