package com.example.evently.dto.tag;

import javax.validation.constraints.NotNull;

public class PatchTagReq {
    @NotNull
    Long[] tagIds;
}
