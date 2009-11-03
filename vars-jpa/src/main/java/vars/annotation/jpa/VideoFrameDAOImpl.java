package vars.annotation.jpa;

import vars.jpa.DAO;

import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.HashSet;

import com.google.inject.Inject;
import javax.persistence.EntityManager;
import vars.annotation.VideoFrame;
import vars.annotation.VideoFrameDAO;

/**
 * Created by IntelliJ IDEA.
 * User: brian
 * Date: Aug 7, 2009
 * Time: 4:42:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class VideoFrameDAOImpl extends DAO implements VideoFrameDAO {

    @Inject
    public VideoFrameDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }

    public Set<VideoFrame> findAllByVideoArchivePrimaryKey(Object primaryKey) {
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("primaryKey", primaryKey);
        List<VideoFrame> list= findByNamedQuery("VideoFrame.findByVideoArchivePrimaryKey", params);
        Set<VideoFrame> set = new HashSet<vars.annotation.VideoFrame>();
        set.addAll(list);
        return set;
    }
}
