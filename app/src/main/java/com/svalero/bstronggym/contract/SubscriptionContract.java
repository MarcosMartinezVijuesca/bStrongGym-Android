package com.svalero.bstronggym.contract;

import com.svalero.bstronggym.domain.Subscription;
import java.util.List;

public interface SubscriptionContract {

    interface View {
        void onSubscriptionsLoaded(List<Subscription> subscriptions);
        void onSubscriptionSaved();
        void onSubscriptionDeleted();
        void onError(String message);
    }

    interface Presenter {
        void loadSubscriptions();
        void loadSubscriptionsByMember(long memberId);
        void saveSubscription(Subscription subscription);
        void updateSubscription(long id, Subscription subscription);
        void deleteSubscription(long id);
    }
}