package vars.annotation.jpa

import javax.persistence.Id
import javax.persistence.Column
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.NamedQueries
import javax.persistence.NamedQuery
import javax.persistence.Version
import javax.persistence.OneToOne
import javax.persistence.JoinColumn
import javax.persistence.Temporal
import javax.persistence.TemporalType
import java.sql.Timestamp
import javax.persistence.TableGenerator
import vars.annotation.ICameraData
import vars.annotation.IVideoFrame

@Entity(name = "CameraData")
@Table(name = "CameraData")
@NamedQueries( value = [
    @NamedQuery(name = "CameraData.findById",
                query = "SELECT v FROM CameraData v WHERE v.id = :id"),
    @NamedQuery(name = "CameraData.findByName",
                query = "SELECT v FROM CameraData v WHERE v.name = :name"),
    @NamedQuery(name = "CameraData.findByDirection",
                query = "SELECT c FROM CameraData c WHERE c.direction = :direction"),  
    @NamedQuery(name = "CameraData.findByStillImage", query = "SELECT c FROM CameraData c WHERE c.stillImage = :stillImage")      
])
class CameraData implements Serializable, ICameraData {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "CameraData_Gen")
    @TableGenerator(name = "CameraData_Gen", table = "UniqueID",
            pkColumnName = "TableName", valueColumnName = "NextID",
            pkColumnValue = "CameraData", allocationSize = 1)
    Long id

    /** Optimistic lock to prevent concurrent overwrites */
    @Version
    @Column(name = "LAST_UPDATED_TIME")
    private Timestamp updatedTime

    @OneToOne
    @JoinColumn(name = "VideoFrameID_FK")
    private VideoFrame videoFrame

    @Column(name = "Name", length = 50)
    String name

    @Column(name = "Direction", length = 50)
    String direction

    Integer zoom
    Integer focus
    Integer iris
    Double fieldWidth

    @Column(name = "StillImageUrl", length = 1024)
    String stillImage

    @Column(name = "LogDTG")
    @Temporal(value = TemporalType.TIMESTAMP)
    Date logDate

    boolean containsData() {
        return (name || direction || zoon || focus || iris || fieldWidth || stillImage);
    }

    void setVideoFrame(IVideoFrame videoFrame) {
        this.videoFrame = videoFrame
    }

    IVideoFrame getVideoFrame() {
        return videoFrame
    }
}