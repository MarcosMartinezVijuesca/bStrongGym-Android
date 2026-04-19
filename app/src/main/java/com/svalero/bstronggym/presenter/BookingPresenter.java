package com.svalero.bstronggym.presenter;

import com.svalero.bstronggym.api.ApiClient;
import com.svalero.bstronggym.api.ApiService;
import com.svalero.bstronggym.contract.BookingContract;
import com.svalero.bstronggym.domain.Booking;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingPresenter implements BookingContract.Presenter {

    private BookingContract.View view;
    private ApiService apiService;

    public BookingPresenter(BookingContract.View view) {
        this.view = view;
        this.apiService = ApiClient.getApiService();
    }

    @Override
    public void loadBookings() {
        apiService.getBookings().enqueue(new Callback<List<Booking>>() {
            @Override
            public void onResponse(Call<List<Booking>> call, Response<List<Booking>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view.onBookingsLoaded(response.body());
                } else {
                    view.onError("Error al cargar las reservas");
                }
            }

            @Override
            public void onFailure(Call<List<Booking>> call, Throwable t) {
                view.onError("Error de conexión: " + t.getMessage());
            }
        });
    }

    @Override
    public void loadBookingsByMember(long memberId) {
        apiService.getBookingsByMember(memberId).enqueue(new Callback<List<Booking>>() {
            @Override
            public void onResponse(Call<List<Booking>> call, Response<List<Booking>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view.onBookingsLoaded(response.body());
                } else {
                    view.onError("Error al buscar reservas");
                }
            }

            @Override
            public void onFailure(Call<List<Booking>> call, Throwable t) {
                view.onError("Error de conexión: " + t.getMessage());
            }
        });
    }

    @Override
    public void saveBooking(Booking booking) {
        apiService.createBooking(booking).enqueue(new Callback<Booking>() {
            @Override
            public void onResponse(Call<Booking> call, Response<Booking> response) {
                if (response.isSuccessful()) {
                    view.onBookingSaved();
                } else {
                    view.onError("Error al guardar la reserva");
                }
            }

            @Override
            public void onFailure(Call<Booking> call, Throwable t) {
                view.onError("Error de conexión: " + t.getMessage());
            }
        });
    }

    @Override
    public void updateBooking(long id, Booking booking) {
        apiService.updateBooking(id, booking).enqueue(new Callback<Booking>() {
            @Override
            public void onResponse(Call<Booking> call, Response<Booking> response) {
                if (response.isSuccessful()) {
                    view.onBookingSaved();
                } else {
                    view.onError("Error al actualizar la reserva");
                }
            }

            @Override
            public void onFailure(Call<Booking> call, Throwable t) {
                view.onError("Error de conexión: " + t.getMessage());
            }
        });
    }

    @Override
    public void deleteBooking(long id) {
        apiService.deleteBooking(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    view.onBookingDeleted();
                } else {
                    view.onError("Error al eliminar la reserva");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                view.onError("Error de conexión: " + t.getMessage());
            }
        });
    }
}