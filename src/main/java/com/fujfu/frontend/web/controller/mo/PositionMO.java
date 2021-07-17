package com.fujfu.frontend.web.controller.mo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PositionMO {
    private String id;

    /**
     * 岗位工作内容
     */
    private String workContent;
    /**
     * 喜欢人数
     */
    private int likeCount;

    private boolean like;
}
