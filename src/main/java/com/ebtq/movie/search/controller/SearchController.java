package com.ebtq.movie.search.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.ebtq.movie.search.service.MovieService;
import com.ebtq.movie.search.vo.SearchParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yingb
 * @since 2021/3/11
 */
@RestController
public class SearchController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/movie/{id}")
    public R movie(@PathVariable("id") Long id, @RequestBody SearchParam params) {
        movieService.search(params);
        return R.ok(null);
    }

    @GetMapping("/movie/suggest")
    public R suggest(String name) {
        movieService.suggest(name);
        return R.ok(null);
    }
}
