package org.geely.controller.dto;

import lombok.Data;
import org.geely.common.enums.PlatformTypeEnum;

import java.util.List;

/**
 * @author ricardo zhou
 */
@Data
public class PlatformDTO {
    private PlatformTypeEnum platformType;
    private List<PairDTO> children;
}
