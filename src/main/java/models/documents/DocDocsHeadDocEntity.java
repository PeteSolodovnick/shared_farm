package models.documents;

import models.tables.JournalOperationsLotsDocEntity;
import models.tables.JournalOperationsStaffDocEntity;
import models.references.RefKindDocDocEntity;
import models.references.RefTypeDocDocEntity;
import models.tables.TableDocsStuffDocEntity;
import models.references.StorageEntity;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;

@Entity
@Table(name = "doc_docs_head", schema = "public", catalog = "farm")
public class DocDocsHeadDocEntity extends SuperDocumentEntity {
    private RefTypeDocDocEntity refTypeDocByTypeDocId;
    private RefKindDocDocEntity refKindDocByKindDocId;
    private StorageEntity storageInById;
    private StorageEntity storageOutById;
    private Collection<JournalOperationsStaffDocEntity> journalOperationsStaffsById = new HashSet<>();
    private Collection<TableDocsStuffDocEntity> tableDocsStuffsById = new HashSet<>();
    private Collection<JournalOperationsLotsDocEntity> journalOperationsLotsDocEntities = new HashSet<>();

    public void addProduct(TableDocsStuffDocEntity product) {
        product.setDocDocsHeadByDocId(this);
        this.tableDocsStuffsById.add(product);
    }
    public void addJournal(JournalOperationsStaffDocEntity journal) {
        journal.setDocDocsHeadByDocId(this);
        this.journalOperationsStaffsById.add(journal);
    }
    public void addJournalLotOperation(JournalOperationsLotsDocEntity operation) {
        operation.setHeadDocEntity(this);
        this.journalOperationsLotsDocEntities.add(operation);
    }

    @ManyToOne
    @JoinColumn(name = "storage_in_id", referencedColumnName = "id")
    public StorageEntity getStorageInById() {
        return storageInById;
    }
    public void setStorageInById(StorageEntity storageInById) {
        this.storageInById = storageInById;
    }

    @ManyToOne
    @JoinColumn(name = "storage_out_id", referencedColumnName = "id")
    public StorageEntity getStorageOutById() {
        return storageOutById;
    }
    public void setStorageOutById(StorageEntity storageOutById) {
        this.storageOutById = storageOutById;
    }

    @ManyToOne
    @JoinColumn(name = "type_doc_id", referencedColumnName = "id")
    public RefTypeDocDocEntity getRefTypeDocByTypeDocId() {
        return refTypeDocByTypeDocId;
    }

    public void setRefTypeDocByTypeDocId(RefTypeDocDocEntity refTypeDocByTypeDocId) {
        this.refTypeDocByTypeDocId = refTypeDocByTypeDocId;
    }

    @ManyToOne
    @JoinColumn(name = "kind_doc_id", referencedColumnName = "id")
    public RefKindDocDocEntity getRefKindDocByKindDocId() {
        return refKindDocByKindDocId;
    }

    public void setRefKindDocByKindDocId(RefKindDocDocEntity refKindDocByKindDocId) {
        this.refKindDocByKindDocId = refKindDocByKindDocId;
    }

    @OneToMany(mappedBy = "docDocsHeadByDocId", fetch = FetchType.LAZY)
    public Collection<JournalOperationsStaffDocEntity> getJournalOperationsStaffsById() {
        return journalOperationsStaffsById;
    }

    public void setJournalOperationsStaffsById(Collection<JournalOperationsStaffDocEntity> journalOperationsStaffsById) {
        this.journalOperationsStaffsById = journalOperationsStaffsById;
    }

    @OneToMany(mappedBy = "docDocsHeadByDocId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public Collection<TableDocsStuffDocEntity> getTableDocsStuffsById() {
        return tableDocsStuffsById;
    }

    public void setTableDocsStuffsById(Collection<TableDocsStuffDocEntity> tableDocsStuffsById) {
        this.tableDocsStuffsById = tableDocsStuffsById;
    }

    @OneToMany(mappedBy = "headDocEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public Collection<JournalOperationsLotsDocEntity> getJournalOperationsLotsDocEntities() {
        return journalOperationsLotsDocEntities;
    }

    public void setJournalOperationsLotsDocEntities(Collection<JournalOperationsLotsDocEntity> journalOperationsLotsDocEntities) {
        this.journalOperationsLotsDocEntities = journalOperationsLotsDocEntities;
    }
}
