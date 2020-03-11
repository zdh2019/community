package zhangdahu.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDto<T> {
    private List<T> datas;
    private boolean hasPrevious_B;
    private boolean hasNext_B;
    private boolean hasFirstPage_B;
    private boolean hasEndPage_B;
    private Integer currentPage;
    private List<Integer> pages=new ArrayList<>();
    private Integer totalPage;

    public PaginationDto(Integer totalCount, Integer page, Integer size) {
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }
        if(page<1) {
            page=1;
        }
        if(page>totalPage)
        {
            page=totalPage;
        }
        currentPage=page;
        int count = 3;
        for (int i = 1; i <= 3; i++) {
            if (currentPage - count > 0) {
                pages.add(currentPage - count);
            }
            count--;
        }
        pages.add(currentPage);
        count = 1;
        for (int i = 1; i <= 3; i++) {
            if (currentPage + count <= totalPage) {
                pages.add(currentPage + count);
            }
            count++;
        }
        if (currentPage == 1) {
            hasPrevious_B = false;
        } else {
            hasPrevious_B = true;
        }
        if (currentPage == totalPage) {
            hasNext_B = false;
        } else {
            hasNext_B = true;
        }
        //快速转跳第一页
        if (pages.contains(1)) {
            hasFirstPage_B = false;
        } else {
            hasFirstPage_B = true;
        }
        //快速转跳最后一页
        if (pages.contains(totalPage)) {
            hasEndPage_B = false;
        } else {
            hasEndPage_B = true;
        }
    }
}
