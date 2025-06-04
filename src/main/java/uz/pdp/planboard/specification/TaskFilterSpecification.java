package uz.pdp.planboard.specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import uz.pdp.planboard.entity.Task;
import uz.pdp.planboard.entity.Users;
import uz.pdp.planboard.payload.TaskFilter;

public class TaskFilterSpecification {
    public static Specification<Task> filterSpecification(TaskFilter taskFilter) {
        return (root,query,cb)->{
            Predicate predicate = cb.conjunction();
            if (taskFilter.getTitle() != null && !taskFilter.getTitle().isEmpty()) {
                predicate=cb.and(predicate,cb.like(cb.lower(root.get("title")), "%"+taskFilter.getTitle().toLowerCase()+"%"));
            }
            if (taskFilter.getUserId() != null) {
                Join<Task, Users> userJoin = root.join("users", JoinType.INNER);
                predicate = cb.and(predicate, cb.equal(userJoin.get("id"), taskFilter.getUserId()));
            }

            if (taskFilter.getDeadline() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("deadline"), taskFilter.getDeadline()));
            }

            return predicate;
        };
    }


}
