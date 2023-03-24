package io.github.xuyao5.datakitsserver.gpt;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;
import io.github.xuyao5.datakitsserver.context.AbstractTest;
import org.junit.jupiter.api.Test;

/**
 * @author Thomas.XU(xuyao)
 * @version 24/03/23 23:06
 */
final class ChatPgtTest extends AbstractTest {

    @Test
    void test() {
        OpenAiService service = new OpenAiService(PGT_TOKEN);
        CompletionRequest completionRequest = CompletionRequest.builder()
                .prompt("这是我第一个chatGPT测试代码，请给我的小猫起个好听的名字，名字必须可爱!")
                .model("gpt-3.5-turbo")
                .echo(true)
                .user(PGT_USER)
                .n(1)
                .build();
        service.createCompletion(completionRequest).getChoices().forEach(System.out::println);
    }
}