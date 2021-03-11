package com.ebtq.movie.search.vo;

import lombok.Data;

/**
 * @author yingb
 * @since 2021/3/11
 */
@Data
public class SearchParam {
    private Integer pageNum;
    private String searchKey;
    private String genre;
    private String year;
}
