package com.ebtq.movie.search.vo;

import lombok.Data;

import java.util.List;

/**
 * @author yingb
 * @since 2021/3/11
 */
@Data
public class MovieVo {
    private Long id;
    private String title;
    private Integer year;
    private List<String> genre;
}
