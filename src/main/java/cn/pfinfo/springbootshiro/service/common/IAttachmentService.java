package cn.pfinfo.springbootshiro.service.common;

import cn.pfinfo.springbootshiro.entity.Attachment;
import cn.pfinfo.springbootshiro.service.IBaseService;

public interface IAttachmentService extends IBaseService<Attachment> {
	Attachment findLike(Attachment info);

	Attachment save(Attachment info);
}
