package address.localities;

import models.RefCityEntity;

public class CityTableContragentController extends SuperCityTableController {
    @Override
    public void setTextEdit() {
        getFarm().getConfigDialogController().getContragentDialogController().setCityEntity((RefCityEntity) getEntityTable().getSelectionModel().getSelectedItem());
        getFarm().getConfigDialogController().getContragentDialogController().getCity().setText(getEntityTable().getSelectionModel().getSelectedItem().getName());
    }
}
