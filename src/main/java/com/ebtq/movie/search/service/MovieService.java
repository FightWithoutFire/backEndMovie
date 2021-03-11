package com.ebtq.movie.search.service;

import com.ebtq.movie.search.vo.SearchParam;

import java.io.IOException;

/**
 * @author yingb
 * @since 2021/3/11
 */
public interface MovieService {
    void search(SearchParam params) throws IOException;

    void suggest(String name);
}
