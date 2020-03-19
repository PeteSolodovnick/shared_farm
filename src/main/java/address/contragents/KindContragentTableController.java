package address.contragents;

import address.mains.FarmFX;
import address.mains.SuperTableEntityController;
import models.references.KindContragentEntity;
import models.SuperEntity;
import models.references.SuperReferenceEntity;

public class KindContragentTableController extends SuperTableEntityController {
    public KindContragentTableController(){
    }
    @Override
    public void setFarmFX(FarmFX farm) {
        setFile("/kindContraEditDialog.fxml");
        getEntities().addAll(farm.getReferences().getKindContragentData());
        super.setFarmFX(farm);
    }

    @Override
    public void setTextEdit() {
        getFarm().getConfigDialogController().getContragentDialogController().setNewKindContragentEntity((KindContragentEntity) getEntityTable().getSelectionModel().getSelectedItem());
        getFarm().getConfigDialogController().getContragentDialogController().getKind().setText(getEntityTable().getSelectionModel().getSelectedItem().getName());
    }

    @Override
    public void handleNewEntity() {
        getFarm().getConfigDialogController().setKindContragentTableController(this);
        setTitle("New Kind Of Contragent");
        super.handleNewEntity();
    }

    @Override
    public  void handleEditEntity() {
        getFarm().getConfigDialogController().setKindContragentTableController(this);
        setTitle("Edit Kind of Contragent...");
        super.handleEditEntity();
    }

    @Override
    public void deletedFromArray(SuperReferenceEntity selectedEntity) {
        getFarm().getReferences().getKindContragentData().remove(selectedEntity);
    }
}
