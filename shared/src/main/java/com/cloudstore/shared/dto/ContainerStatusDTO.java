package com.cloudstore.shared.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContainerStatusDTO implements Serializable {
    private String containerId;
    private String hostAddress;
    private int port;
    private boolean isHealthy;
    private double currentLoad;
    private long availableStorageSpace;
}