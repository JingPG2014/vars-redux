package vars.annotation.jpa;

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.NamedQueries
import javax.persistence.NamedQuery
import javax.persistence.OneToMany
import javax.persistence.Table
import javax.persistence.Temporal
import javax.persistence.TemporalType
import javax.persistence.CascadeType
import javax.persistence.Version
import java.sql.Timestamp
import javax.persistence.OrderBy
import javax.persistence.TableGenerator

@Entity(name = "VideoArchiveSet")
@Table(name = "VideoArchiveSet")
@NamedQueries( value = [
    @NamedQuery(name = "VideoArchiveSet.findById", 
                query = "SELECT v FROM VideoArchiveSet v WHERE v.id = :id"),
    @NamedQuery(name = "VideoArchiveSet.findByTrackingNumber",
                query = "SELECT v FROM VideoArchiveSet v WHERE v.trackingNumber = :trackingNumber"),
    @NamedQuery(name = "VideoArchiveSet.findByPlatformName",
                query = "SELECT v FROM VideoArchiveSet v WHERE v.platformName = :platformName"),
    @NamedQuery(name = "VideoArchiveSet.findByFormatCode",
                query = "SELECT v FROM VideoArchiveSet v WHERE v.formatCode = :formatCode"),
    @NamedQuery(name = "VideoArchiveSet.findByStartDate",
                query = "SELECT v FROM VideoArchiveSet v WHERE v.startDate = :startDate"),
    @NamedQuery(name = "VideoArchiveSet.findByEndDate",
                query = "SELECT v FROM VideoArchiveSet v WHERE v.endDate = :endDate")
])
class VideoArchiveSet implements Serializable {

    @Id 
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "VideoArchiveSet_Gen")
    @TableGenerator(name = "VideoArchiveSet_Gen", table = "UniqueID",
            pkColumnName = "TableName", valueColumnName = "NextID",
            pkColumnValue = "VideoArchiveSet", allocationSize = 1)
    Long id

    /** Optimistic lock to prevent concurrent overwrites */
    @Version
    @Column(name = "LAST_UPDATED_TIME")
    private Timestamp updatedTime
    
    @Column(name = "TrackingNumber", length = 7)
    String trackingNumber

    @Column(name = "ShipName", length = 32)
    @Deprecated
    String shipName
    
    @Column(name = "PlatformName", nullable = false, length = 32)
    String platformName
    
    @Column(name = "FormatCode", length = 2)
    String formatCode
    
    @Column(name = "StartDTG")
    @Temporal(value = TemporalType.TIMESTAMP)
    Date startDate
    
    @Column(name = "EndDTG")
    @Temporal(value = TemporalType.TIMESTAMP)
    Date endDate
    
    @OneToMany(targetEntity = VideoArchive.class,
            mappedBy = "videoArchiveSet",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    @OrderBy(value = "name")
    Set<VideoArchive> videoArchives
    
    @OneToMany(targetEntity = CameraDeployment.class,
            mappedBy = "videoArchiveSet",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    Set<CameraDeployment> cameraDeployments

    Set<CameraDeployment> getCameraDeployments() {
        if (cameraDeployments == null) {
            cameraDeployments = new HashSet()
        }
        return cameraDeployments
    }

    Set<VideoArchive> getVideoArchives() {
        if (videoArchives == null) {
            videoArchives = new ArrayList<VideoArchive>()
        }
        return videoArchives
    }

    void addVideoArchive(VideoArchive videoArchive) {
        if (getVideoArchives().find { VideoArchive va -> va.name.equals(videoArchive.name) }) {
            throw new IllegalArgumentException("A VideoArchive named '${va.name} already exists in ${this}")
        }
        videoArchives << videoArchive
        videoArchive.videoArchiveSet = this
    }

    void removeVideoArchive(VideoArchive videoArchive) {
        videoArchives.remove(videoArchive)
        videoArchive.videoArchiveSet = null
    }

    void addCameraDeployment(CameraDeployment cameraDeployment) {
        cameraDeployments << cameraDeployment
        cameraDeployment.videoArchiveSet = this
    }

    void removeCameraDeployment(CameraDeployment cameraDeployment) {
        getCameraDeployments().remove(cameraDeployment)
        cameraDeployment.videoArchiveSet = null
    }

}
