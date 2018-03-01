package cn.pfinfo.springbootshiro.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PageImplWrapper<T> extends PageImpl<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7958684619053279831L;
	
	private long totalElements;
    private int number;
    private int size;
    private boolean first;
    private boolean last;
    private Sort sort;
    private List<T> content;
    private int totalPages;


	public PageImplWrapper(List<T> content, Pageable pageable, long total) {
		super(content, pageable, total);
	}
	public PageImplWrapper(List<T> content) {
        super(content);
    }

    public PageImplWrapper() {
        this(new ArrayList<T>());
    }
    private PageImplWrapper(Page<T> page) {
        super(page.getContent(), new PageRequest(page.getNumber(), page.getSize()), page.getTotalElements());
        this.content = page.getContent();
        this.size = super.getSize();
        this.totalElements = super.getTotalElements();
        this.number = super.getNumber();
        this.first = super.isFirst();
        this.last = super.isLast();
        this.sort = super.getSort();
        this.totalPages = super.getTotalPages();
    }
    public static <T> PageImplWrapper<T> copy(Page<T> page) {
    	return new PageImplWrapper<T>(page);
    }


}