package io.github.xuyao5.dal.generator.response.generator;

import io.github.xuyao5.dal.generator.response.BaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data(staticConstructor = "of")
@EqualsAndHashCode(callSuper = false)
public final class GenerateMyBatisFilesResponse extends BaseResponse {
}
