package io.github.xuyao5.dal.elasticsearch.controller;

import io.github.xuyao5.dal.core.util.GsonUtils;
import io.github.xuyao5.dal.elasticsearch.abstr.AbstractController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 10/10/20 23:50
 * @apiNote TODO 这里输入文件说明
 * @implNote TODO 这里输入实现说明
 */
@RestController
public final class QueryController extends AbstractController {

    @GetMapping("/hello")
    public String hello() {
        Optional<String> hello = GsonUtils.obj2Json("{你好11}", String.class);
        return hello.isPresent() ? hello.get() : "无";
    }
}
