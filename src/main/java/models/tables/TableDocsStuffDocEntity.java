package models.tables;

import models.documents.DocDocsHeadDocEntity;
import models.references.NomenklEntity;
import javax.persistence.*;

@Entity
@Table(name = "table_docs_stuff", schema = "public", catalog = "farm")
public class TableDocsStuffDocEntity extends SuperTableEntity {
 //   private Integer qty;
 //   private Float sum;
    private NomenklEntity nomenklEntityByNomId;
    private DocDocsHeadDocEntity docDocsHeadByDocId;

/*    @Basic
    @Column(name = "qty", nullable = true)
    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    @Basic
    @Column(name = "sum", nullable = true, precision = 0)
    public Float getSum() {
        return sum;
    }

    public void setSum(Float sum) {
        this.sum = sum;
    } */

    @ManyToOne
    @JoinColumn(name = "nomenkl_id", referencedColumnName = "id", nullable = false)
    public NomenklEntity getNomenklEntityByNomId() {
        return nomenklEntityByNomId;
    }

    public void setNomenklEntityByNomId(NomenklEntity nomenklEntityByNomId) {
        this.nomenklEntityByNomId = nomenklEntityByNomId;
    }
    @ManyToOne
    @JoinColumn(name="doc_id", referencedColumnName = "id", nullable = false)
    @Override
    public DocDocsHeadDocEntity getDocDocsHeadByDocId() {
        return docDocsHeadByDocId;
    }

    @Override
    public void setDocDocsHeadByDocId(DocDocsHeadDocEntity docDocsHeadByDocId) {
        this.docDocsHeadByDocId = docDocsHeadByDocId;
    }
}
