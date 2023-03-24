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
                .prompt("这是我第一个测试代码，请告诉我新西兰是怎么样一个国家？")
                .model("gpt-3.5-turbo")
                .echo(true)
                .build();
        service.createCompletion(completionRequest).getChoices().forEach(System.out::println);
    }
}