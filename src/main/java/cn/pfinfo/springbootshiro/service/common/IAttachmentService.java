package cn.pfinfo.springbootshiro.service.common;

import cn.pfinfo.springbootshiro.entity.Attachment;
import cn.pfinfo.springbootshiro.service.IBaseService;

public interface IAttachmentService extends IBaseService<Attachment> {
	Attachment findLike(Attachment info);
	/**
	 * 通过附件上传者id以及访问路径获取附件信息
	 * @param authorId 文章作者id
	 * @param path 附件上传路径
	 * @return 附件信息
	 */
	Attachment findOne(Long authorId,String path);

	Attachment save(Attachment info);
}
