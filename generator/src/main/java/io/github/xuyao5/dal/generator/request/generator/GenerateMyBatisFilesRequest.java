package io.github.xuyao5.dal.generator.request.generator;

import io.github.xuyao5.dal.generator.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data(staticConstructor = "of")
@EqualsAndHashCode(callSuper = false)
public final class GenerateMyBatisFilesRequest extends BaseRequest {
}
