package org.geely.domain.common.data;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author cong huang
 */
@Data
public class LoginFailedData implements Serializable {
    private Integer count;
    private LocalDateTime lastTime;
    public LoginFailedData(){
        count = 0;
    }
}
