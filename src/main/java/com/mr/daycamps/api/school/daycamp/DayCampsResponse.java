package com.mr.daycamps.api.school.daycamp;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder(setterPrefix = "set")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DayCampsResponse {

    private List<DayCampResponse> dayCamps;

}
