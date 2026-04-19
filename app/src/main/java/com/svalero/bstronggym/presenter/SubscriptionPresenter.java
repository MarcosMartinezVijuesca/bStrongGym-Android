package com.svalero.bstronggym.presenter;

import com.svalero.bstronggym.api.ApiClient;
import com.svalero.bstronggym.api.ApiService;
import com.svalero.bstronggym.contract.SubscriptionContract;
import com.svalero.bstronggym.domain.Subscription;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubscriptionPresenter implements SubscriptionContract.Presenter {

    private SubscriptionContract.View view;
    private ApiService apiService;

    public SubscriptionPresenter(SubscriptionContract.View view) {
        this.view = view;
        this.apiService = ApiClient.getApiService();
    }

    @Override
    public void loadSubscriptions() {
        apiService.getSubscriptions().enqueue(new Callback<List<Subscription>>() {
            @Override
            public void onResponse(Call<List<Subscription>> call, Response<List<Subscription>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view.onSubscriptionsLoaded(response.body());
                } else {
                    view.onError("Error al cargar las suscripciones");
                }
            }

            @Override
            public void onFailure(Call<List<Subscription>> call, Throwable t) {
                view.onError("Error de conexión: " + t.getMessage());
            }
        });
    }

    @Override
    public void loadSubscriptionsByMember(long memberId) {
        apiService.getSubscriptionsByMember(memberId).enqueue(new Callback<List<Subscription>>() {
            @Override
            public void onResponse(Call<List<Subscription>> call, Response<List<Subscription>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view.onSubscriptionsLoaded(response.body());
                } else {
                    view.onError("Error al cargar las suscripciones");
                }
            }

            @Override
            public void onFailure(Call<List<Subscription>> call, Throwable t) {
                view.onError("Error de conexión: " + t.getMessage());
            }
        });
    }

    @Override
    public void saveSubscription(Subscription subscription) {
        apiService.createSubscription(subscription).enqueue(new Callback<Subscription>() {
            @Override
            public void onResponse(Call<Subscription> call, Response<Subscription> response) {
                if (response.isSuccessful()) {
                    view.onSubscriptionSaved();
                } else {
                    view.onError("Error al guardar la suscripción");
                }
            }

            @Override
            public void onFailure(Call<Subscription> call, Throwable t) {
                view.onError("Error de conexión: " + t.getMessage());
            }
        });
    }

    @Override
    public void updateSubscription(long id, Subscription subscription) {
        apiService.updateSubscription(id, subscription).enqueue(new Callback<Subscription>() {
            @Override
            public void onResponse(Call<Subscription> call, Response<Subscription> response) {
                if (response.isSuccessful()) {
                    view.onSubscriptionSaved();
                } else {
                    view.onError("Error al actualizar la suscripción");
                }
            }

            @Override
            public void onFailure(Call<Subscription> call, Throwable t) {
                view.onError("Error de conexión: " + t.getMessage());
            }
        });
    }

    @Override
    public void deleteSubscription(long id) {
        apiService.deleteSubscription(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    view.onSubscriptionDeleted();
                } else {
                    view.onError("Error al eliminar la suscripción");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                view.onError("Error de conexión: " + t.getMessage());
            }
        });
    }
}
