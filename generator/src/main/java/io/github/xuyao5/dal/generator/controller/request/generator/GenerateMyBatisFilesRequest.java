package io.github.xuyao5.dal.generator.controller.request.generator;

import io.github.xuyao5.dal.generator.controller.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data(staticConstructor = "of")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class GenerateMyBatisFilesRequest extends BaseRequest {
}
