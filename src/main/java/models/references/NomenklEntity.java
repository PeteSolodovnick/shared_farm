package models.references;

import models.tables.JournalOperationsStaffDocEntity;
import models.tables.TableDocLotsDocEntity;
import models.tables.TableDocsStuffDocEntity;
import models.tables.TableInvoiceNomDocEntity;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;

@Entity
@Table(name = "ref_nomenkl", schema = "public", catalog = "farm")
public class NomenklEntity extends SuperReferenceEntity {
    private ClassificationEntity refClassificationByClassificationId;
    private SizeEntity refSizeBySizeId;
    private Collection<TableInvoiceNomDocEntity> tableInvoiceNomDocEntities;
    private Collection<TableDocsStuffDocEntity> tableDocsStuffDocEntities;
    private Collection<TableDocLotsDocEntity> tableDocLotsDocEntities;
    private Collection<JournalOperationsStaffDocEntity> journalOperationsStaffDocEntities = new HashSet<>();

    public void addJournal(JournalOperationsStaffDocEntity journal) {
        journal.setNomenklEntityById(this);
        journalOperationsStaffDocEntities.add(journal);
    }

    @OneToMany(mappedBy = "nomenklEntityById")
    public Collection<JournalOperationsStaffDocEntity> getJournalOperationsStaffDocEntities() {
        return journalOperationsStaffDocEntities;
    }
    public void setJournalOperationsStaffDocEntities(Collection<JournalOperationsStaffDocEntity> journalOperationsStaffDocEntities) {
        this.journalOperationsStaffDocEntities = journalOperationsStaffDocEntities;
    }

    @OneToMany(mappedBy = "nomenklEntityByNomId")
    public Collection<TableDocLotsDocEntity> getTableDocLotsDocEntities() {
        return tableDocLotsDocEntities;
    }

    public void setTableDocLotsDocEntities(Collection<TableDocLotsDocEntity> tableDocLotsDocEntities) {
        this.tableDocLotsDocEntities = tableDocLotsDocEntities;
    }

    @OneToMany(mappedBy = "nomenklEntityByNomId")
    public Collection<TableDocsStuffDocEntity> getTableDocsStuffDocEntities() {
        return tableDocsStuffDocEntities;
    }

    public void setTableDocsStuffDocEntities(Collection<TableDocsStuffDocEntity> tableDocsStuffDocEntities) {
        this.tableDocsStuffDocEntities = tableDocsStuffDocEntities;
    }

    @OneToMany(mappedBy = "nomenklEntityByNomId")
    public Collection<TableInvoiceNomDocEntity> getTableInvoiceNomDocEntities() {
        return tableInvoiceNomDocEntities;
    }

    public void setTableInvoiceNomDocEntities(Collection<TableInvoiceNomDocEntity> tableInvoiceNomDocEntities) {
        this.tableInvoiceNomDocEntities = tableInvoiceNomDocEntities;
    }

    @ManyToOne
    @JoinColumn(name = "classification_id", referencedColumnName = "id", nullable = false)
    public ClassificationEntity getRefClassificationByClassificationId() {
        return refClassificationByClassificationId;
    }

    public void setRefClassificationByClassificationId(ClassificationEntity refClassificationByClassificationId) {
        this.refClassificationByClassificationId = refClassificationByClassificationId;
    }

    @ManyToOne
    @JoinColumn(name = "size_id", referencedColumnName = "id", nullable = false)
    public SizeEntity getRefSizeBySizeId() {
        return refSizeBySizeId;
    }

    public void setRefSizeBySizeId(SizeEntity refSizeBySizeId) {
        this.refSizeBySizeId = refSizeBySizeId;
    }
}
