package io.github.xuyao5.datakitsserver.repository;

import io.github.xuyao5.datakitsserver.context.AbstractTest;
import io.github.xuyao5.dkl.eskits.repository.InformationSchemaRepo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class InformationSchemaRepoTest extends AbstractTest {

    private final InformationSchemaRepo informationSchemaRepo = new InformationSchemaRepo();

    @Test
    void test() {
        informationSchemaRepo.query();
    }
}