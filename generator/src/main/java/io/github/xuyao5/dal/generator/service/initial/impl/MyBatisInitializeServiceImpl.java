package io.github.xuyao5.dal.generator.service.initial.impl;

import io.github.xuyao5.dal.generator.service.AbstractService;
import io.github.xuyao5.dal.generator.service.initial.MyBatisInitializeService;
import lombok.extern.log4j.Log4j2;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.config.xml.MyBatisGeneratorConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Log4j2
@Service("myBatisInitializeServiceImpl")
public class MyBatisInitializeServiceImpl extends AbstractService implements MyBatisInitializeService {

    @Override
    public void createGeneratorConfigXmlFile() {
        List<String> warningList = new CopyOnWriteArrayList<>();

        try {
            File configFile = ResourceUtils.getFile("classpath:generatorConfig.xml");
            ConfigurationParser cp = new ConfigurationParser(warningList);
            Configuration config = cp.parseConfiguration(configFile);
            config.validate();
            DefaultShellCallback callback = new DefaultShellCallback(true);
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warningList);
            myBatisGenerator.generate(null);
        } catch (IOException | SQLException | InterruptedException | InvalidConfigurationException | XMLParserException e) {
            log.error(e.getLocalizedMessage());
        }
    }
}
