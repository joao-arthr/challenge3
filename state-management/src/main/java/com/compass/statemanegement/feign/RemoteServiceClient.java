package com.compass.statemanegement.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name="data-persistence")
public interface RemoteServiceClient {
}
