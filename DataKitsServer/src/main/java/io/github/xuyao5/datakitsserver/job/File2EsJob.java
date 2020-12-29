package io.github.xuyao5.datakitsserver.job;

import io.github.xuyao5.dkl.eskits.configuration.xml.File2EsTasks;
import io.github.xuyao5.dkl.eskits.service.File2EsExecutor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.validation.constraints.NotNull;
import javax.xml.bind.JAXB;

import static org.springframework.util.ResourceUtils.CLASSPATH_URL_PREFIX;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 29/12/20 15:01
 * @apiNote TODO 这里输入文件说明
 * @implNote TODO 这里输入实现说明
 */
@Component("file2EsJob")
public final class File2EsJob {

    private static final String FILE2ES_CONFIG_XML = "File2EsTasks.xml";

    @SneakyThrows
    public void doJob(@NotNull String taskId) {
        //获取配置文件并执行
        File2EsTasks file2EsTasks = JAXB.unmarshal(ResourceUtils.getFile(CLASSPATH_URL_PREFIX + FILE2ES_CONFIG_XML), File2EsTasks.class);
        file2EsTasks.seek(taskId).ifPresent(File2EsExecutor.builder().build()::execute);
    }
}