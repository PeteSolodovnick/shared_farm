package address.mains;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.SuperEntity;
import services.EntityService;

import java.time.LocalDate;
import java.util.List;

public class FactoryListEntities<T extends SuperEntity> {
    private T t;
    public FactoryListEntities(T t) {
        this.t = t;
    }
    public ObservableList<T> getListEntities() {
        ObservableList<T> entityList = FXCollections.observableArrayList();
        EntityService<T, Long> service = new EntityService<>();
        for (T entity: service.getAllRows(t)) {
            entityList.add(entity);
        }
        return entityList;
    }
    public ObservableList<T> getDateListEntities() {
        ObservableList<T> entityList = FXCollections.observableArrayList();
        EntityService<T, LocalDate> service = new EntityService<>();
        for (T entity: service.getDateRows(t, LocalDate.now().minusWeeks(1), LocalDate.now())) {
            entityList.add(entity);
        }
        return entityList;
    }
    public ObservableList<T> getSetDateListEntities(LocalDate startDate, LocalDate endDate) {
        ObservableList<T> entityList = FXCollections.observableArrayList();
        EntityService<T, LocalDate> service = new EntityService<>();
        for (T entity: service.getDateRows(t, startDate, endDate)) {
            entityList.add(entity);
        }
        return entityList;
    }
    public ObservableList<T> getSomeListEntities(Long id) {
        ObservableList<T> entityList = FXCollections.observableArrayList();
        EntityService<T, Long> service = new EntityService<>();
        for (T entity: service.getSomeRows(t, id)) {
            entityList.add(entity);
        }
        return entityList;
    }
    public ObservableList<T> getListEntitiesbyId(List<Long> id) {
        ObservableList<T> entityList = FXCollections.observableArrayList();
        EntityService<T, Long> service = new EntityService<>();
        for (Long i : id) {
            entityList.add(service.read(t,i));
        }
        return entityList;
    }
}
