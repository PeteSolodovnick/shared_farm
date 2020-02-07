package address.mains;

import address.documents.invoices.DocInvoiceHeadDocEntity;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import models.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;

public class RootLayoutController {
    private static final Logger logger = LogManager.getLogger();
    private final String contragentReference = "/contragent.fxml";
    private final String localityReference = "/cities.fxml";
    private final String productReference = "/classification.fxml";
    private final String lotsReference = "/lots.fxml";
    private final String storageReference = "/storage.fxml";
    private final String invoiceDoc = "/invoice.fxml";
    private FarmFX farm;

     public void setFarm(FarmFX farm) {
        this.farm = farm;
    }

    @FXML
    private void handleLocality() {
        farm.getReferences().getTerritoryData().clear();
        farm.getReferences().getCitiesData().clear();
        farm.getReferences().getTypeCityData().clear();
        farm.getReferences().setCitiesData(new FactoryListEntities<>(new RefCityEntity()).getListEntities());
        farm.getReferences().setTerritoryData(new FactoryListEntities<>(new RefTerritoryEntity()).getListEntities());
        farm.getReferences().setTypeCityData(new FactoryListEntities<>(new RefTypeCityEntity()).getListEntities());
        farm.showEntityOverview(localityReference);
    }
    @FXML
    private void handleContragent() {
        farm.getReferences().getContragentData().clear();
        farm.getReferences().getTypeContragentData().clear();
        farm.getReferences().getMarketViewData().clear();
        farm.getReferences().getPriceData().clear();
        farm.getReferences().getKindContragentData().clear();
        farm.getReferences().getCitiesData().clear();
        farm.getReferences().getTypeCityData().clear();
        farm.getReferences().setContragentData(new FactoryListEntities<>(new RefContragentEntity()).getListEntities());
        farm.getReferences().setTypeContragentData(new FactoryListEntities<>(new RefTypeContragentEntity()).getListEntities());
        farm.getReferences().setMarketViewData(new FactoryListEntities<>(new RefMarketViewEntity()).getListEntities());
        farm.getReferences().setPriceData(new FactoryListEntities<>(new RefPriceEntity()).getListEntities());
        farm.getReferences().setKindContragentData(new FactoryListEntities<>(new RefKindContragentEntity()).getListEntities());
        farm.getReferences().setCitiesData(new FactoryListEntities<>(new RefCityEntity()).getListEntities());
        farm.getReferences().setTypeCityData(new FactoryListEntities<>(new RefTypeCityEntity()).getListEntities());
        farm.showEntityOverview(contragentReference);
    }
    @FXML
    private void handleProduct() {
         farm.getReferences().getProductsData().clear();
         farm.getReferences().getSizeEntitiesData().clear();
         farm.getReferences().getClassificationData().clear();
         farm.getReferences().setProductsData(new FactoryListEntities<>(new RefNomenklEntity()).getListEntities());
         farm.getReferences().setSizeEntitiesData(new FactoryListEntities<>(new RefSizeEntity()).getListEntities());
         farm.getReferences().setClassificationData(new FactoryListEntities<>(new RefClassificationEntity()).getListEntities());
         farm.showEntityOverview(productReference);
    }
    @FXML
    private void handleLots() {
         farm.getReferences().getLotsData().clear();
         farm.getReferences().getKindLotsData().clear();
         farm.getReferences().getTypeLotsData().clear();
         farm.getReferences().setLotsData(new FactoryListEntities<>(new RefLotsEntity()).getListEntities());
         farm.getReferences().setKindLotsData(new FactoryListEntities<>(new RefKindLotsEntity()).getListEntities());
         farm.getReferences().setTypeLotsData(new FactoryListEntities<>(new RefTypeLotsEntity()).getListEntities());
         farm.showEntityOverview(lotsReference);
    }
    @FXML
    private void handleStorage() {
         farm.getReferences().getStorageData().clear();
         farm.getReferences().setStorageData(new FactoryListEntities<>(new RefStorageEntity()).getListEntities());
         farm.showEntityOverview(storageReference);
    }
    @FXML
    private void handleInvoices() {
         farm.getReferences().getInvoiceData().clear();
         farm.getReferences().setInvoiceData(new FactoryListEntities<>(new DocInvoiceHeadDocEntity()).getDateListEntities());
         logger.info(LocalDate.now());
         logger.info(LocalDate.now().minusWeeks(1));

    }

    @FXML
    private void handleExit() {
        System.exit(0);
    }
    @FXML
    private void handleAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("AddressApp");
        alert.setHeaderText("About");
        alert.setContentText("Author: Pete Solodovnick\n Manager of ARM-Trend");
        alert.showAndWait();
    }
}
