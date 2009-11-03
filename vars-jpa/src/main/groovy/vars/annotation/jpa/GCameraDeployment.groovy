package vars.annotation.jpa

import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.NamedQueries
import javax.persistence.NamedQuery
import javax.persistence.Id
import javax.persistence.GeneratedValue
import javax.persistence.ManyToOne
import javax.persistence.Column
import javax.persistence.GenerationType
import javax.persistence.JoinColumn
import javax.persistence.Temporal
import javax.persistence.TemporalType
import javax.persistence.Version
import java.sql.Timestamp
import javax.persistence.TableGenerator
import vars.annotation.CameraDeployment
import vars.annotation.VideoArchiveSet
import vars.jpa.JPAEntity
import vars.EntitySupportCategory
import javax.persistence.EntityListeners
import vars.jpa.TransactionLogger
import vars.jpa.KeyNullifier
import javax.persistence.Transient
import vars.annotation.CameraDeployment
import vars.annotation.VideoArchiveSet

@Entity(name = "CameraDeployment")
@Table(name = "CameraPlatformDeployment")
@EntityListeners( value = [TransactionLogger.class, KeyNullifier.class] )
@NamedQueries( value = [
    @NamedQuery(name = "CameraDeployment.findById",
                query = "SELECT v FROM CameraDeployment v WHERE v.id = :id"),
    @NamedQuery(name = "CameraDeployment.findBySequenceNumber",
                query = "SELECT v FROM CameraDeployment v WHERE v.sequenceNumber = :sequenceNumber"),
    @NamedQuery(name = "CameraDeployment.findByChiefScientistName",
                query = "SELECT v FROM CameraDeployment v WHERE v.chiefScientistName = :chiefScientistName"),
    @NamedQuery(name = "CameraDeployment.findByStartDate",
                query = "SELECT v FROM CameraDeployment v WHERE v.startDate = :startDate"),
    @NamedQuery(name = "CameraDeployment.findByEndDate",
                query = "SELECT v FROM CameraDeployment v WHERE v.endDate = :endDate")
])
class GCameraDeployment implements Serializable, CameraDeployment, JPAEntity {

    @Transient
    private static final PROPS = Collections.unmodifiableList([CameraDeployment.PROP_SEQUENCE_NUMBER,
            CameraDeployment.PROP_CHIEF_SCIENTIST_NAME, CameraDeployment.PROP_START_DATE,
            CameraDeployment.PROP_END_DATE])

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "CameraPlatformDeployment_Gen")
    @TableGenerator(name = "CameraPlatformDeployment_Gen", table = "UniqueID",
            pkColumnName = "TableName", valueColumnName = "NextID",
            pkColumnValue = "CameraPlatformDeployment", allocationSize = 1)
    Long id

    /** Optimistic lock to prevent concurrent overwrites */
    @Version
    @Column(name = "LAST_UPDATED_TIME")
    private Timestamp updatedTime

    @ManyToOne(optional = false, targetEntity = GVideoArchiveSet.class)
    @JoinColumn(name = "VideoArchiveSetID_FK")
    VideoArchiveSet videoArchiveSet

    @Column(name = "SeqNumber")
    Integer sequenceNumber

    @Column(name = "ChiefScientist", length = 50)
    String chiefScientistName

    @Column(name = "UsageStartDTG")
    @Temporal(value = TemporalType.TIMESTAMP)
    Date startDate

    @Column(name = "UsageEndDTG")
    @Temporal(value = TemporalType.TIMESTAMP)
    Date endDate

    @Override
    String toString() {
        return EntitySupportCategory.basicToString(this, PROPS)
    }


    @Override
    boolean equals(that) {
        return EntitySupportCategory.equals(this, that, PROPS)
    }

    @Override
    int hashCode() {
        return EntitySupportCategory.hashCode(this, PROPS)
    }


}