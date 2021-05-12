package com.covid19.plasma.model;

import com.covid19.plasma.enums.RequestToDonorStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReceivedPlasmaRequest {
    boolean isPresent;
    RequestToDonorStatus requestToDonorStatus;
}
