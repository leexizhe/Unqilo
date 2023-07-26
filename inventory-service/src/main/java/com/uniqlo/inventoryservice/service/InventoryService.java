package com.uniqlo.inventoryservice.service;

import com.uniqlo.inventoryservice.dto.InventoryResponse;
import java.util.List;

public interface InventoryService {
    List<InventoryResponse> isInstock(List<String> skuCode);
}
