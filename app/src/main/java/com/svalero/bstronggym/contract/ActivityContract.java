package com.svalero.bstronggym.contract;

import com.svalero.bstronggym.domain.Activity;
import java.util.List;

public interface ActivityContract {

    interface View {
        void onActivitiesLoaded(List<Activity> activities);
        void onActivitySaved();
        void onActivityDeleted();
        void onError(String message);
    }

    interface Presenter {
        void loadActivities();
        void loadActivitiesByName(String name);
        void saveActivity(Activity activity);
        void updateActivity(long id, Activity activity);
        void deleteActivity(long id);
    }
}
