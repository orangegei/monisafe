package org.example.pojo;

import lombok.Data;

@Data
public class SingleResponseTimeLog extends AlertLog{
    String nodeName;
    String responseTime;
    String serialNumber;

}
