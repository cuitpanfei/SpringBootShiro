package cn.pfinfo.springbootshiro.service.impl.common;

import cn.pfinfo.springbootshiro.entity.Attachment;
import cn.pfinfo.springbootshiro.service.common.IAttachmentService;
import org.springframework.stereotype.Service;

/**
 * Created by panfei on 2018/3/14.
 */
@Service("attachmentService")
public class AttachmentServiceImpl implements IAttachmentService{
    @Override
    public Attachment findLike(Attachment info) {
        return null;
    }

    @Override
    public Attachment save(Attachment info) {
        return null;
    }

    /**
     * 通过ID获取一个实例
     *
     * @param id
     * @return
     */
    @Override
    public Attachment getOne(Long id) {
        return null;
    }
}
