package address.documents.invoices;

import address.mains.FactoryListEntities;
import address.mains.FarmFX;
import address.mains.SuperDialogEntityController;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;
import models.*;
import models.documents.DocInvoiceHeadDocEntity;
import models.references.*;
import models.tables.TableInvoiceNomDocEntity;
import org.apache.logging.log4j.core.util.JsonUtils;
import org.w3c.dom.ls.LSOutput;
import services.EntityService;

import java.util.ArrayList;
import java.util.List;

public class InvoiceDialogController extends SuperDialogEntityController {
    @FXML
    private TableView<TableInvoiceNomDocEntity> entityTable;
    @FXML
    private DatePicker date;
    @FXML
    private TextField contragent;
    @FXML
    private TextField sumInv;
    @FXML
    private TextField sumVatInv;
    @FXML
    private TextField vat;
    @FXML
    private TextField sum_vat;
    @FXML
    private TableColumn<TableInvoiceNomDocEntity, ClassificationEntity> classification;
    @FXML
    private TableColumn<TableInvoiceNomDocEntity, Long> id;
    @FXML
    private TableColumn<TableInvoiceNomDocEntity, NomenklEntity> nameNomenkl;
    @FXML
    private TableColumn<TableInvoiceNomDocEntity, SizeEntity> size;
    @FXML
    private TableColumn<TableInvoiceNomDocEntity, Float> sum;
    @FXML
    private TableColumn<TableInvoiceNomDocEntity, Float> vatS;
    @FXML
    private TableColumn<TableInvoiceNomDocEntity, Float> sumVat;
    @FXML
    private TableColumn<TableInvoiceNomDocEntity, Integer> qty;

    private ContragentEntity newContragentEntity;
    private DocInvoiceHeadDocEntity newInvoice;
    private RefStatusInvoiceDocEntity statusInvoiceDocEntity;
    private String fileContragent;
    private String fileNomenkl;
    private List<TableInvoiceNomDocEntity> forDelete = new ArrayList<>();

    public InvoiceDialogController() {}
    @FXML
    protected void initialize() {
        entityTable.setEditable(true);
        id.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getNomenklEntityByNomId().getId()));
        nameNomenkl.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getNomenklEntityByNomId()));
        classification.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getNomenklEntityByNomId().getRefClassificationByClassificationId()));
        size.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getNomenklEntityByNomId().getRefSizeBySizeId()));
        qty.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getQty()));
        qty.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter(){
            @Override
            public Integer fromString(String s) {
                try {
                    Integer result = Integer.parseInt(s);
                    return result;
                } catch (NumberFormatException e) {
                    String title = "Error!!!";
                    String headerText = "Element isn't integer number";
                    String content = "Please input an integer number";
                    viewError(title,headerText,content);
                    return 0;
                }
            }
        }));
        qty.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TableInvoiceNomDocEntity, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<TableInvoiceNomDocEntity, Integer> t) {
                t.getTableView().getItems().get(t.getTablePosition().getRow()).setQty(t.getNewValue());
            }
        });
        sum.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getSum()));
        sum.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter(){
            @Override
            public Float fromString(String s) {
                try {
                    Float result = Float.parseFloat(s);
                    return result;
                } catch (NumberFormatException e) {
                    String title = "Error!!!";
                    String headerText = "Element isn't float number";
                    String content = "Please input an float number";
                    viewError(title,headerText,content);
                    return 0.0f;
                }
            }
        }));
        sum.setOnEditCommit(t -> {
            t.getTableView().getItems().get(t.getTablePosition().getRow()).setSum(t.getNewValue());
            t.getTableView().getItems().get(t.getTablePosition().getRow()).setVat((float) (t.getTableView().getItems().get(t.getTablePosition().getRow()).getSum() * FarmFX.vat / 100));
            t.getTableView().getItems().get(t.getTablePosition().getRow()).setSum_vat((float) (t.getTableView().getItems().get(t.getTablePosition().getRow()).getSum() * (1 + FarmFX.vat / 100)));
            calculate();
        });
        vatS.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getSum()*FarmFX.vat/100));
        sumVat.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getSum()*(1 + FarmFX.vat/100)));
    }
 @Override
    public void setFarmFX(FarmFX farm, SuperReferenceEntity selectedInvoice) {
        fileContragent = "/contragentViewer.fxml";
        fileNomenkl = "/nomenklViewer.fxml";
        initArrays(farm);
        vat.setText(String.valueOf(FarmFX.vat));
        statusInvoiceDocEntity = farm.getReferences().getStatusInvoiceData().get(1);
        newInvoice = (DocInvoiceHeadDocEntity) selectedInvoice;
        if (selectedInvoice != null) {
            farm.getReferences().setTableInvoiceData(new FactoryListEntities<TableInvoiceNomDocEntity>(new TableInvoiceNomDocEntity()).getSomeListEntities(newInvoice.getId(),"docInvoiceHeadByInvId"));
            entityTable.setItems(farm.getReferences().getTableInvoiceData());
            date.setValue(newInvoice.getDate());
            contragent.setText(newInvoice.getRefContragentEntityByContragentId().getName());
            sumInv.setText(String.valueOf(newInvoice.getSum()));
            sumVatInv.setText(String.valueOf(newInvoice.getSum()*(1 + FarmFX.vat/100)));
            sum_vat.setText(String.valueOf(newInvoice.getSum()*FarmFX.vat/100));
            newContragentEntity = newInvoice.getRefContragentEntityByContragentId();
            setNew(false);
        } else {
            farm.getReferences().getTableInvoiceData().clear();
            newInvoice = new DocInvoiceHeadDocEntity();
            setNew(true);
        }
        super.setFarmFX(farm, newInvoice);
    }
    private void initArrays(FarmFX farm) {
        farm.getReferences().setStatusInvoiceData(new FactoryListEntities<>(new RefStatusInvoiceDocEntity()).getListEntities());
    }
    @Override
    protected boolean isInputValid() {
        if (super.isInputValid()) {
            String errorMessage = "";
            if (date.getValue() == null) {
                errorMessage += "No valid date\n";
            }
            if (contragent.getText() == null || contragent.getText().length() == 0) {
                errorMessage += "No valid contragent\n";
            }
            if (sumInv.getText() == null || sumInv.getText().length() == 0) {
                errorMessage += "No valid sum\n";
            }
            if (entityTable.getItems().size() == 0) {
                errorMessage += "there are no elements in table\n";
            }
            if (isError(errorMessage))
                return false;
            return true;
        }
        return false;
    }
    @Override
    protected void createEntity() {
        super.createEntity();
        newInvoice.setRefContragentEntityByContragentId(getNewContragentEntity());
        newInvoice.setDate(date.getValue());
        newInvoice.setSum(Float.parseFloat(sumInv.getText()));
        newInvoice.setTableInvoiceNomById(entityTable.getItems());
        newInvoice.setVat(Float.parseFloat(vat.getText()));
        newInvoice.setSum_vat(Float.parseFloat(sum_vat.getText()));
        if (isNew()) {
            newInvoice.setRefStatusInvoiceByStatusId(statusInvoiceDocEntity);
            newInvoice.setEditable(true);
        } else {
            for (TableInvoiceNomDocEntity tableInv: forDelete) {
                EntityService<TableInvoiceNomDocEntity, Long> service = new EntityService<>();
                service.delete(tableInv);
            }
        }
        setNewEntity(newInvoice);
    }
    @FXML
    private void handleContragentChoose() {
        getFarm().getConfigDialogController().setInvoiceDialogController(this);
        getFarm().showEntityOverview(fileContragent);
    }
    @Override
    public void handleOkDialog() {
        super.handleOkDialog();
        getFarm().getConfigDialogController().getInvoiceHeadOverviewController().getEntityTable().refresh();
    }
    @FXML
    public void handleAddProduct() {
        getFarm().getConfigDialogController().setInvoiceDialogController(this);
        getFarm().showEntityOverview(fileNomenkl);
    }
    @FXML
    public void handleMinusProduct() {
        TableInvoiceNomDocEntity selectedEntity = entityTable.getSelectionModel().getSelectedItem();
        if (selectedEntity != null) {
            forDelete.add(selectedEntity);
            getFarm().getReferences().getTableInvoiceData().remove(selectedEntity);
            calculate();
        } else {
            String title = "No selection";
            String headerText = "No one element Selected";
            String content = "Please select an element in the table for seeing information about it";
            viewError(title,headerText,content);
        }
    }
    private void viewError(String title, String headerText, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(getFarm().getPrimaryStage());
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private void calculate() {
        double itemsSum = 0;
        for (int i = 0; i < entityTable.getItems().size(); i++) {
            itemsSum = itemsSum + entityTable.getItems().get(i).getSum();
        }
        sumInv.setText(String.valueOf(itemsSum));
        sumVatInv.setText(String.valueOf(itemsSum * (1 + FarmFX.vat / 100)));
        sum_vat.setText(String.valueOf(itemsSum * FarmFX.vat / 100));
        entityTable.refresh();
    }
    @Override
    public void newEntity() {
        getFarm().getReferences().getInvoiceData().add(getNewInvoice());
        getFarm().getConfigDialogController().getInvoiceHeadOverviewController().getEntities().add(getNewInvoice());
    }

    @Override
    public void editEntity(SuperReferenceEntity newEntity) {

    }

    public ContragentEntity getNewContragentEntity() {
        return newContragentEntity;
    }

    public void setNewContragentEntity(ContragentEntity newContragentEntity) {
        this.newContragentEntity = newContragentEntity;
    }

    public DocInvoiceHeadDocEntity getNewInvoice() {
        return newInvoice;
    }

    public TableView<TableInvoiceNomDocEntity> getEntityTable() {
        return entityTable;
    }

    public void setEntityTable(TableView<TableInvoiceNomDocEntity> entityTable) {
        this.entityTable = entityTable;
    }

    public void setNewInvoice(DocInvoiceHeadDocEntity newInvoice) {
        this.newInvoice = newInvoice;
    }

    public TextField getContragent() {
        return contragent;
    }

    public void setContragent(TextField contragent) {
        this.contragent = contragent;
    }
}
