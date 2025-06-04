package uz.pdp.planboard.projection;

public interface DeveloperResult {
    String getPhotoUrl();
    String getName();
    Integer getTotalTasks();
    Integer getProgresTasks();
    Integer getCompTasks();
    Integer getExpiredTasks();

}
