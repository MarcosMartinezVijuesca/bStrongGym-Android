package com.svalero.bstronggym.presenter;

import com.svalero.bstronggym.api.ApiClient;
import com.svalero.bstronggym.api.ApiService;
import com.svalero.bstronggym.contract.MemberContract;
import com.svalero.bstronggym.domain.Member;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MemberPresenter implements MemberContract.Presenter {

    private MemberContract.View view;
    private ApiService apiService;

    public MemberPresenter(MemberContract.View view) {
        this.view = view;
        this.apiService = ApiClient.getApiService();
    }

    @Override
    public void loadMembers() {
        apiService.getMembers().enqueue(new Callback<List<Member>>() {
            @Override
            public void onResponse(Call<List<Member>> call, Response<List<Member>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view.onMembersLoaded(response.body());
                } else {
                    view.onError("Error al cargar los socios");
                }
            }

            @Override
            public void onFailure(Call<List<Member>> call, Throwable t) {
                view.onError("Error de conexión: " + t.getMessage());
            }
        });
    }

    @Override
    public void loadMembersByName(String name) {
        apiService.getMembersByName(name).enqueue(new Callback<List<Member>>() {
            @Override
            public void onResponse(Call<List<Member>> call, Response<List<Member>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view.onMembersLoaded(response.body());
                } else {
                    view.onError("Error al buscar socios");
                }
            }

            @Override
            public void onFailure(Call<List<Member>> call, Throwable t) {
                view.onError("Error de conexión: " + t.getMessage());
            }
        });
    }

    @Override
    public void saveMember(Member member) {
        apiService.createMember(member).enqueue(new Callback<Member>() {
            @Override
            public void onResponse(Call<Member> call, Response<Member> response) {
                if (response.isSuccessful()) {
                    view.onMemberSaved();
                } else {
                    view.onError("Error al guardar el socio");
                }
            }

            @Override
            public void onFailure(Call<Member> call, Throwable t) {
                view.onError("Error de conexión: " + t.getMessage());
            }
        });
    }

    @Override
    public void updateMember(long id, Member member) {
        apiService.updateMember(id, member).enqueue(new Callback<Member>() {
            @Override
            public void onResponse(Call<Member> call, Response<Member> response) {
                if (response.isSuccessful()) {
                    view.onMemberSaved();
                } else {
                    view.onError("Error al actualizar el socio");
                }
            }

            @Override
            public void onFailure(Call<Member> call, Throwable t) {
                view.onError("Error de conexión: " + t.getMessage());
            }
        });
    }

    @Override
    public void deleteMember(long id) {
        apiService.deleteMember(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    view.onMemberDeleted();
                } else {
                    view.onError("Error al eliminar el socio");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                view.onError("Error de conexión: " + t.getMessage());
            }
        });
    }
}