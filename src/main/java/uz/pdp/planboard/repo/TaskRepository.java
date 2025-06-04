package uz.pdp.planboard.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.planboard.entity.Task;
import uz.pdp.planboard.projection.Criminals;
import uz.pdp.planboard.projection.DeveloperResult;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer>, JpaSpecificationExecutor<Task> {
    @Query(value = """
                SELECT   u.photo_url as photo_url,
                                                                                                                                                                  u.first_name || ' ' || u.last_name AS name,
                                                                                                                                                                  COUNT(DISTINCT t.id) AS total_tasks,
                                                                                                                                                                  SUM(CASE WHEN t.column_id=8 THEN 1 ELSE 0 END) AS progres_tasks,
                                                                                                                                                                  SUM(CASE WHEN t.column_id=9 THEN 1 ELSE 0 END) AS comp_tasks,
                                                                                                                                                                  SUM(CASE WHEN t.deadline < CURRENT_DATE AND t.deadline IS NOT NULL THEN 1 ELSE 0 END) AS expired_tasks
                                                                                                                                                              FROM task t
                                                                                                                                                                       JOIN task_users tu ON t.id = tu.task_id
                                                                                                                                                                       JOIN users u ON tu.users_id = u.id
                                                                                                                                                              GROUP BY u.photo_url, u.first_name, u.last_name;
            
            """, nativeQuery = true)
    List<DeveloperResult> getDeveloperResults();

    @Query(value = """
             select u.photo_url, u.first_name || ' ' || u.last_name as name,
                                                                                              sum(case when t.deadline < current_date
                                                                                                  then 1 else 0 end ) as expired_task_count
                                                                                              from task t
                                                                                       join task_users tu on t.id = tu.task_id
                                                                                       join users u on tu.users_id = u.id
                                                                                              group by u.photo_url, u.first_name || ' ' || u.last_name
            """, nativeQuery = true)
    List<Criminals> getCriminalResult();
}