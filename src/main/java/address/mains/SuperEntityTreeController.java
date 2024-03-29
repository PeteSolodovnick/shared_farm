package address.mains;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import models.*;
import models.references.*;
import services.EntityService;

import java.util.Optional;

public abstract class SuperEntityTreeController extends SuperEntityController {
    @FXML
    private TreeView<SuperReferenceEntity> treeView;
    private TreeItem <SuperReferenceEntity> rootItem = new TreeItem("Entity");
    private ObservableList<SuperReferenceEntity> entitiesTree = FXCollections.observableArrayList();
    private String fileTree;

    public SuperEntityTreeController() {}

    @FXML @Override
    protected void initialize() {
        super.initialize();
        treeView.setRoot(rootItem);
        treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showEntities(newValue));
    }
    @Override
    public void setFarmFX(FarmFX farm) {
        initRoot();
        super.setFarmFX(farm);
    }

    protected void initRoot() {
        for (SuperReferenceEntity entity:entitiesTree) {
            if (entity != null) {
                rootItem.getChildren().add(new TreeItem<SuperReferenceEntity>(entity));
            }
        }
    }

    private void showEntities(TreeItem<SuperReferenceEntity> entity) {
        ObservableList<SuperReferenceEntity> entities = FXCollections.observableArrayList();
        if (entity != null) {
            if (entity.getValue().equals(rootItem.getValue())) {
                getEntityTable().setItems(this.getEntities());
            } else {
                for (SuperReferenceEntity someEntity : this.getEntities()) {
                    switch (someEntity.getClass().getName()) {
                        case "models.references.CityEntity":
                            CityEntity cityEntity = (CityEntity) someEntity;
                            if (cityEntity.getRefTerritoryByTerId().getId() == entity.getValue().getId()) {
                                entities.add(cityEntity);
                            }
                            break;
                        case "models.references.ContragentEntity":
                            ContragentEntity contragentEntity = (ContragentEntity) someEntity;
                            if (contragentEntity.getRefTypeContragentByTypeContraId().getId() == entity.getValue().getId()) {
                                entities.add(contragentEntity);
                            }
                            break;
                        case "models.references.NomenklEntity":
                            NomenklEntity productEntity = (NomenklEntity) someEntity;
                            if (productEntity.getRefClassificationByClassificationId().getId() == entity.getValue().getId()) {
                                entities.add(productEntity);
                            }
                            break;
                        case "models.references.LotsEntity":
                            LotsEntity lotsEntity = (LotsEntity) someEntity;
                            if (lotsEntity.getRefTypeLotsByTypeLotsId().getId() == entity.getValue().getId()) {
                                entities.add(lotsEntity);
                            }
                    }
                }
                getEntityTable().setItems(entities);
            }
        }
    }

    @FXML
    public void handleDeleteTreeEntity() {
        int selectedId = treeView.getSelectionModel().getSelectedIndex();
        if (selectedId>0) {
            SuperReferenceEntity name = treeView.getTreeItem(selectedId).getValue();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete " + rootItem);
            alert.setHeaderText(rootItem.getValue() +" "+ treeView.getTreeItem(selectedId).getValue() + " will be deleted");
            alert.setContentText("Are you sure?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                EntityService<SuperReferenceEntity, Long> service = new EntityService<>();
                try {
                    service.delete(name);
                    entitiesTree.remove(name);
                    deletedFromTreeArray(name);
                    rootItem.getChildren().clear();
                    initRoot();
                } catch (Exception e) {
                    Alert exAlert = new Alert(Alert.AlertType.ERROR);
                    exAlert.setTitle("Error!");
                    exAlert.setHeaderText("Object couldn't be deleted");
                    exAlert.setContentText("Object can't be deleted because others objects\n\n have references to it.");
                    exAlert.showAndWait();
                }
            } else {
                alert.close();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(getFarm().getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No "+rootItem.getValue()+" Selected");
            alert.setContentText("Please select a "+rootItem.getValue()+" in the table for deleting");
            alert.showAndWait();
        }
    }
    public abstract void deletedFromTreeArray(SuperReferenceEntity selectedEntity);
    @FXML
    public void handleNewTreeEntity() {
        getFarm().showEntityDialog(null,getReferenceStage(),fileTree,getTitle());
    }
    @FXML
    public void handleEditTreeEntity() {
        int selectedId = treeView.getSelectionModel().getSelectedIndex();
        SuperReferenceEntity selectedEntity = treeView.getSelectionModel().getSelectedItem().getValue();
        if (selectedId > 0) {
            getFarm().showEntityDialog(selectedEntity,getReferenceStage(),fileTree,getTitle());
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(getFarm().getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No one element Selected");
            alert.setContentText("Please select an element in the tree for editing");
            alert.showAndWait();
        }
    }

    public TreeView<SuperReferenceEntity> getTreeView() {
        return treeView;
    }

    public TreeItem<SuperReferenceEntity> getRootItem() {
        return rootItem;
    }

    public ObservableList<SuperReferenceEntity> getEntitiesTree() {
        return entitiesTree;
    }

    public void setFileTree(String fileTree) {
        this.fileTree = fileTree;
    }

    public String getFileTree() {
        return fileTree;
    }
}
