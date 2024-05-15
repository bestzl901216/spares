package org.geely.controller.dto.site;

import cn.hutool.core.lang.Assert;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@ApiModel("线下支付")
public class OrderPayOfflineDTO {
    @ApiModelProperty(value = "支付单号" ,required = true)
    @NotBlank(message = "支付单号不能为空")
    private String sn;
    @ApiModelProperty(value = "供应商支付凭证" ,required = true)
    @NotEmpty(message = "支付凭证不能为空")
    @Valid
    private List<OrderPaySupplierApproveDTO> supplierVouchers;

    public void check() {
        Assert.isFalse(supplierVouchers.isEmpty(), "支付凭证不能为空");
        Set<Integer> supplierIds = supplierVouchers.stream().map(OrderPaySupplierApproveDTO::getSupplierId).collect(Collectors.toSet());
        Assert.isTrue(supplierIds.size() == supplierVouchers.size(), "参数错误，重复的supplierId");
    }
}
