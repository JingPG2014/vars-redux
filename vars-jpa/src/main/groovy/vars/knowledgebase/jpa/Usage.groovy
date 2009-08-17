package vars.knowledgebase.jpa

import javax.persistence.Version
import javax.persistence.Column
import java.sql.Timestamp
import javax.persistence.Id
import javax.persistence.GeneratedValue
import javax.persistence.TableGenerator
import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.NamedQueries
import javax.persistence.NamedQuery
import javax.persistence.Temporal
import javax.persistence.GenerationType
import javax.persistence.TemporalType
import javax.persistence.ManyToOne
import javax.persistence.JoinColumn
import javax.persistence.OneToOne
import vars.knowledgebase.IConceptMetadata
import vars.knowledgebase.IUsage
import vars.jpa.JPAEntity
import vars.EntityToStringCategory
import javax.persistence.EntityListeners;
import org.mbari.jpax.TransactionLogger
import vars.jpa.KeyNullifier
import vars.jpa.KeyNullifier

/**
 * CREATE TABLE USAGE (
 *   ID                   	BIGINT NOT NULL,
 *   CONCEPTDELEGATEID_FK 	BIGINT,
 *   EMBARGOEXPIRATIONDATE	TIMESTAMP,
 *   SPECIFICATION        	VARCHAR(1000),
 *   CONSTRAINT PK_USAGE PRIMARY KEY(ID)
 * )
 * GO
 * CREATE INDEX IDX_CONCEPTDELEGATE7
 *   ON USAGE(CONCEPTDELEGATEID_FK)
 * GO
 */
@Entity(name = "Usage")
@Table(name = "Usage")
@EntityListeners( value = [TransactionLogger.class, KeyNullifier.class] )
@NamedQueries( value = [
    @NamedQuery(name = "Usage.findById",
                query = "SELECT v FROM Usage v WHERE v.id = :id"),
    @NamedQuery(name = "Usage.findByEmbargoExpirationDate",
                query = "SELECT u FROM Usage u WHERE u.embargoExpirationDate = :embargoExpirationDate") ,
    @NamedQuery(name = "Usage.findBySpecification",
                query = "SELECT u FROM Usage u WHERE u.specification = :specification")
])
class Usage implements Serializable, IUsage, JPAEntity {

    @Id
    @Column(name = "id", nullable = false, updatable=false)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "Usage_Gen")
    @TableGenerator(name = "Usage_Gen", table = "UniqueID",
            pkColumnName = "TableName", valueColumnName = "NextID",
            pkColumnValue = "Usage", allocationSize = 1)
    Long id

    /** Optimistic lock to prevent concurrent overwrites */
    @Version
    @Column(name = "LAST_UPDATED_TIME")
    private Timestamp updatedTime

    @Column(name = "EmbargoExpirationDate")
    @Temporal(value = TemporalType.TIMESTAMP)
    Date embargoExpirationDate

    @Column(name = "Specification", length = 1000)
    String specification

    @OneToOne(targetEntity = ConceptMetadata.class, optional = false)
    @JoinColumn(name = "ConceptDelegateID_FK")
    IConceptMetadata conceptMetadata

    @Override
    String toString() {
        return EntityToStringCategory.basicToString(this, [PROP_EMBARGO_EXPIRATION_DATE, PROP_SPECIFICATION])
    }
    
}
