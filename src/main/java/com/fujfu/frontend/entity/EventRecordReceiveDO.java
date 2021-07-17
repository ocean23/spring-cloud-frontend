package com.fujfu.frontend.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Jun Luo
 * @date 2021/6/22 3:10 PM
 */
@Getter
@Setter
@ToString(callSuper = true)
@Document(collection = "event_record_receive")
@FieldNameConstants
public class EventRecordReceiveDO extends BaseDO {
    private String eventId;
}
