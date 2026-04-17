package com.svalero.bstronggym.contract;

import com.svalero.bstronggym.domain.Monitor;
import java.util.List;

public interface MonitorContract {

    interface View {
        void onMonitorsLoaded(List<Monitor> monitors);
        void onMonitorSaved();
        void onMonitorDeleted();
        void onError(String message);
    }

    interface Presenter {
        void loadMonitors();
        void loadMonitorsByName(String name);
        void saveMonitor(Monitor monitor);
        void updateMonitor(long id, Monitor monitor);
        void deleteMonitor(long id);
    }
}
