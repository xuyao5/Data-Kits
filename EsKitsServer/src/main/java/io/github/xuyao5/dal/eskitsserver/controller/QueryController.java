package io.github.xuyao5.dal.eskitsserver.controller;

import io.github.xuyao5.dal.core.util.GsonUtils;
import io.github.xuyao5.dal.eskitsserver.abstr.AbstractController;
import io.github.xuyao5.dal.eskitsserver.controller.context.Greeting;
import io.github.xuyao5.dal.eskitsserver.controller.context.HelloMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import java.util.Optional;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 10/10/20 23:50
 * @apiNote TODO 这里输入文件说明
 * @implNote TODO 这里输入实现说明
 */
@RestController
//@MessageMapping("query")
public final class QueryController extends AbstractController {

    @GetMapping("/hello")
    public String hello() {
        Optional<String> hello = GsonUtils.obj2Json("{你好11}", String.class);
        return hello.isPresent() ? hello.get() : "无";
    }

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return Greeting.of("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }
}
