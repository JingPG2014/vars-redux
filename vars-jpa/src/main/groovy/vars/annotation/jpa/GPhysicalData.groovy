package vars.annotation.jpa

import java.sql.Timestamp
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.NamedQueries
import javax.persistence.NamedQuery
import javax.persistence.OneToOne
import javax.persistence.Table
import javax.persistence.TableGenerator
import javax.persistence.Version
import vars.annotation.PhysicalData
import vars.annotation.VideoFrame
import vars.EntitySupportCategory
import vars.jpa.JPAEntity
import javax.persistence.EntityListeners
import org.mbari.jpaxx.TransactionLogger
import vars.jpa.KeyNullifier
import javax.persistence.Transient
import javax.persistence.TemporalType
import javax.persistence.Temporal
import vars.annotation.PhysicalData

@Entity(name = "PhysicalData")
@Table(name = "PhysicalData")
@EntityListeners( value = [TransactionLogger.class, KeyNullifier.class] )
@NamedQueries( value = [
    @NamedQuery(name = "PhysicalData.findById",
                query = "SELECT v FROM PhysicalData v WHERE v.id = :id")
])
class GPhysicalData implements Serializable, PhysicalData, JPAEntity {

    @Transient
    private static final PROPS = Collections.unmodifiableList([PhysicalData.PROP_LATITUDE, PhysicalData.PROP_LONGITUDE,
            PhysicalData.PROP_DEPTH])

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "PhysicalData_Gen")
    @TableGenerator(name = "PhysicalData_Gen", table = "UniqueID",
            pkColumnName = "TableName", valueColumnName = "NextID",
            pkColumnValue = "PhysicalData", allocationSize = 1)
    Long id

    /** Optimistic lock to prevent concurrent overwrites */
    @Version
    @Column(name = "LAST_UPDATED_TIME")
    private Timestamp updatedTime

    @OneToOne(targetEntity = GVideoFrame.class, optional = false)
    @JoinColumn(name = "VideoFrameID_FK")
    VideoFrame videoFrame

    @Column(name = "LogDTG")
    @Temporal(value = TemporalType.TIMESTAMP)
    Date logDate

    Float depth
    Float temperature
    Float salinity
    Float oxygen
    Float light
    Float latitude
    Float longitude

    boolean containsData() {
        return (depth || temperature || salinity || oxygen || light || latitude || longitude);
    }

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