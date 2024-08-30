# 数据库





## user_info 用户个人信息

| 字段名        | Data Type    | Description                            |
| ------------- | ------------ | -------------------------------------- |
| user_id       | INT          | 主键，自增，唯一标识每个用户           |
| username      | VARCHAR(50)  | 用户名，用于用户登录和标识             |
| password      | VARCHAR(255) | 用户密码的加密值，用于安全存储用户密码 |
| email_address | VARCHAR(100) | 用户的电子邮件地址，用于账户相关的通信 |
| phone_number  | VARCHAR(20)  | 用户的联系号码，用于联系用户           |

**字段名**:

- `user_id`：主键，自动递增。
- `username`：存储用户名，最大长度为 50。
- `password`：存储用户密码的加密值，最大长度为 255。
- `email_address`：存储用户的电子邮件地址，最大长度为 100。
- `phone_number`：存储用户的联系号码，最大长度为 20。

```sql
CREATE TABLE user_info (
    user_id INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(20) NOT NULL,
    email_address VARCHAR(30),
    phone_number VARCHAR(20),
    PRIMARY KEY (user_id)
);
```



## user_log_info 用户状态日志

| 字段名         | Data Type   | Description                                       |
| -------------- | ----------- | ------------------------------------------------- |
| username       | VARCHAR(10) | 用户名，用于用户登录和标识                        |
| login_time     | DATETIME    | 用户登录时间                                      |
| login_ip       | VARCHAR(50) | 用户登录ip                                        |
| operation_type | VARCHAR(20) | 用户操作类型，如"登录","修改个人信息"，"修改密码" |

```sql
CREATE TABLE `user_log_info` (
    `username` VARCHAR(10) NOT NULL,
    `login_time` DATETIME NOT NULL,
    `login_ip` VARCHAR(50) NOT NULL,
    `operation_type` VARCHAR(20) NOT NULL,
    PRIMARY KEY (`username`, `login_time`)
);
```



# 展示数据

## region_transaction 各地区交易数据

| 字段名             | Data Type      | Description                    |
| ------------------ | -------------- | ------------------------------ |
| transaction_id     | INT            | 主键，自增，唯一标识每笔交易   |
| province           | VARCHAR(100)   | 交易所属的省份，例如“黑龙江省” |
| transaction_count  | INT            | 该地区交易数量                 |
| transaction_amount | DECIMAL(10, 2) | 交易金额，例如 114514.00       |
| transaction_time   | DATETIME       | 交易发生的时间(按天)取开始时间 |
| transaction_type   | VARCHAR(50)    | 交易类型，如 "ATM", "Forex"    |

**字段名**:

- `transaction_id`：主键，自动递增，用于唯一标识每笔交易。
- `province`：存储交易所属的省份，最大长度为 100。
- `transaction_count`：存储该地区的交易数量。
- `transaction_amount`：存储交易金额，使用 `DECIMAL(10, 2)` 表示，确保精度。
- `transaction_time`：存储交易发生的具体时间，按小时取开始时间。
- `transaction_type`：存储交易类型，如 "ATM", "Forex"，最大长度为 50。

```sql
CREATE TABLE region_transaction (
    transaction_id INT NOT NULL AUTO_INCREMENT,
    province VARCHAR(20) NOT NULL,
    transaction_count INT NOT NULL,
    transaction_amount DECIMAL(10, 2) NOT NULL,
    transaction_time DATETIME NOT NULL,
    transaction_type VARCHAR(20) NOT NULL,
    PRIMARY KEY (transaction_id)
);

```



## age_group_transaction 各年龄段交易数据

| 字段名             | Data Type                                     | Description                        |
| ------------------ | --------------------------------------------- | ---------------------------------- |
| transaction_id     | INT                                           | 主键，自增，唯一标识每笔交易       |
| age_group          | enum('18~25','25~35','35~45','45~55','55~60') | 交易所属的年龄段，如“18-25”        |
| transaction_count  | INT                                           | 交易次数                           |
| transaction_amount | DECIMAL(10, 2)                                | 交易金额，例如 114514.00           |
| transaction_time   | DATETIME                                      | 交易发生的时间（按小时）取开始时间 |
| transaction_type   | VARCHAR(50)                                   | 交易类型，如 "ATM", "Forex"        |



```sql
CREATE TABLE age_group_transaction (
    transaction_id INT NOT NULL AUTO_INCREMENT,
    age_group VARCHAR(50) NOT NULL,
    transaction_count INT NOT NULL,
    transaction_amount DECIMAL(10, 2) NOT NULL,
    transaction_time DATETIME NOT NULL,
    transaction_type VARCHAR(20) NOT NULL,
    PRIMARY KEY (transaction_id)
);

ALTER TABLE age_group_transaction
MODIFY COLUMN age_group ENUM('18~25', '25~35', '35~45', '45~55', '55~60') NOT NULL;
```

#### 表结构

- `transaction_id`：交易ID，自动递增的整数，主键。
- `age_group`：年龄组，枚举类型。
  - `'18~25'`：18到25岁
  - `'25~35'`：25到35岁
  - `'35~45'`：35到45岁
  - `'45~55'`：45到55岁
  - `'55~60'`：55到60岁
- `transaction_count`：交易次数，整数。
- `transaction_amount`：交易金额，十进制数，精确到小数点后两位。
- `transaction_time`：交易时间，日期时间类型。
- `transaction_type`：交易类型，字符串。

#### 查询示例

要查询年龄在18到25岁之间的交易数据：

```sql
SELECT * FROM age_group_transaction WHERE age_group = '18~25';
```











## forex_purpose_transaction 外汇目的数据



| 字段名             | Data Type      | Description                        |
| ------------------ | -------------- | ---------------------------------- |
| transaction_id     | INT            | 主键，自增，唯一标识每笔交易       |
| purpose            | VARCHAR(100)   | 交易目的，例如 "教育", "留学"      |
| transaction_count  | INT            | 不同目的的交易次数                 |
| transaction_amount | DECIMAL(10, 2) | 交易金额，例如 114514.00           |
| transaction_time   | DATETIME       | 交易发生的时间（按小时）取开始时间 |

**字段名**: 

- `transaction_id`：主键，自动递增，用于唯一标识每笔交易。
- `purpose`：存储交易目的，最大长度为 100。
- `transaction_count`：存储不同目的的交易次数。
- `transaction_amount`：存储交易金额，使用 `DECIMAL(10, 2)` 表示，确保精度。
- `transaction_time`：存储交易发生的具体时间，按小时取开始时间。

```sql
CREATE TABLE forex_purpose_transaction (
    transaction_id INT NOT NULL AUTO_INCREMENT,
    purpose VARCHAR(100) NOT NULL,
    transaction_count INT NOT NULL,
    transaction_amount DECIMAL(10, 2) NOT NULL,
    transaction_time DATETIME NOT NULL,
    PRIMARY KEY (transaction_id)
);
```



## currency_exchange_type，外汇兑换货币种类

| 字段名             | Data Type      | Description                        |
| ------------------ | -------------- | ---------------------------------- |
| transaction_id     | INT            | 主键，自增，唯一标识每笔交易       |
| exchange_type      | VARCHAR(50)    | 货币种类，如 "欧元", "日元"        |
| transaction_count  | INT            | 不同外汇的兑换次数                 |
| transaction_amount | DECIMAL(10, 2) | 交易金额，例如 114514.00           |
| transaction_time   | DATETIME       | 交易发生的时间（按小时）取开始时间 |

**字段名**: 

- `transaction_id`：主键，自动递增，用于唯一标识每笔交易。
- `exchange_type`：存储货币种类，最大长度为 50。
- `transaction_count`：存储不同外汇的兑换次数。
- `transaction_amount`：存储交易金额，使用 `DECIMAL(10, 2)` 表示，确保精度。
- `transaction_time`：存储交易发生的具体时间，按小时取开始时间。

```sql
CREATE TABLE currency_exchange_type (
    transaction_id INT NOT NULL AUTO_INCREMENT,
    exchange_type VARCHAR(50) NOT NULL,
    transaction_count INT NOT NULL,
    transaction_amount DECIMAL(10, 2) NOT NULL,
    transaction_time DATETIME NOT NULL,
    PRIMARY KEY (transaction_id)
);
```

## amount_range_statistics，金额范围统计

| 字段名            | Data Type   | Description                        |
| ----------------- | ----------- | ---------------------------------- |
| transaction_id    | INT         | 主键，自增，唯一标识每笔交易       |
| amount_range      | VARCHAR(50) | 交易金额范围，例如 "0~10000"       |
| transaction_count | INT         | 符合交易范围的交易次数             |
| transaction_time  | DATETIME    | 交易发生的时间（按小时）取开始时间 |
| transaction_type  | VARCHAR(50) | 交易类型，如 "ATM", "Forex"        |

**字段名**: 

- `transaction_id`：主键，自动递增，用于唯一标识每笔交易。
- `amount_range`：存储交易金额范围，最大长度为 50。
- `transaction_count`：存储符合交易范围的交易次数。
- `transaction_time`：存储交易发生的具体时间，按小时取开始时间。
- `transaction_type`：存储交易类型，如 "ATM", "Forex"，最大长度为 50。

```sql
CREATE TABLE amount_range_statistics (
    transaction_id INT NOT NULL AUTO_INCREMENT,
    amount_range VARCHAR(50) NOT NULL,
    transaction_count INT NOT NULL,
    transaction_time DATETIME NOT NULL,
    transaction_type VARCHAR(50) NOT NULL,
    PRIMARY KEY (transaction_id)
);
```



# 监控数据

## 离线

## bank_alerts 记录银行监控告警系统中的异常情况

```sql
CREATE TABLE bank_alerts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    alert_date DATE NOT NULL,
    large_amount INT DEFAULT 0,
    abnormal_time INT DEFAULT 0,
    internal_response_time INT DEFAULT 0
);

CREATE TABLE bank_alerts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    alert_date DATE NOT NULL,
    large_amount INT DEFAULT 0,
    abnormal_time INT DEFAULT 0,
    ATM_entry_response_time INT DEFAULT 0,
    ATM_server_response_time INT DEFAULT 0,
    core_service_response_time INT DEFAULT 0,
    fx_entry_response_time INT DEFAULT 0,
    fx_server_response_time INT DEFAULT 0,
    fx_core_entry_response_time INT DEFAULT 0
);

-- 插入示例数据
INSERT INTO bank_alerts (alert_date, large_amount, abnormal_time, node1_response_time, node2_response_time, node3_response_time, node4_response_time, node5_response_time, node6_response_time)
VALUES
('2021-12-01', 5, 3, 2, 1, 4, 3, 2, 5),
('2021-12-02', 2, 4, 5, 3, 2, 1, 4, 3);

```

-- 插入示例数据

```sql
INSERT INTO bank_alerts (alert_date, large_amount, abnormal_time, internal_response_time)
VALUES
('2021-12-01', 5, 3, 2),
('2021-12-02', 2, 4, 5);
```

- 字段
  - `id`: 唯一标识符，自增。
  - `alert_date`: 告警日期，格式为YYYY-MM-DD。
  - `large_amount`: 大额交易告警次数。
  - `abnormal_time`: 异常时间告警次数。
  - `internal_response_time`: 内部节点响应时间告警次数。



## 实时

## withdrawal_monitoring，大额取款处理和异常时间段处理监控表

| 字段名             | Data Type      | Description                                                  |
| ------------------ | -------------- | ------------------------------------------------------------ |
| transaction_id     | INT            | 主键，自增，唯一标识每笔交易                                 |
| province           | VARCHAR(100)   | 交易所属的省份，例如“黑龙江省”                               |
| gender             | VARCHAR(10)    | 用户性别，“Male”或“Female”                                   |
| age                | INT            | 用户年龄                                                     |
| transaction_amount | DECIMAL(10, 2) | 交易金额，例如 114514.00                                     |
| transaction_time   | DATETIME       | 交易发生的时间                                               |
| status             | VARCHAR(20)    | 事件状态，“warning”，“severe”，“normal”                      |
| event_type         | VARCHAR(20)    | 事件类型，“HighValue”表示大额取款，“AbnormalTime”表示异常时间取款 |

**字段名**: 

- `transaction_id`：主键，自动递增，用于唯一标识每笔交易。
- `province`：存储交易所属的省份，最大长度为 100。
- `gender`：存储用户性别，可以是 “Male” 或 “Female”，最大长度为 10。
- `age`：存储用户年龄。
- `transaction_amount`：存储交易金额，使用 `DECIMAL(10, 2)` 表示，确保精度。
- `transaction_time`：存储交易发生的具体时间。
- `status`：存储事件状态，可能的值为 “warning”，“severe”，“normal”，最大长度为 20。
- `event_type`：存储事件类型，可能的值为 “HighValue” 表示大额取款，“AbnormalTime” 表示异常时间取款，最大长度为 20。

```sql
CREATE TABLE withdrawal_monitoring (
    transaction_id INT NOT NULL AUTO_INCREMENT,
    province VARCHAR(20) NOT NULL,
    gender VARCHAR(10) NOT NULL,
    age INT NOT NULL,
    transaction_amount DECIMAL(10, 2) NOT NULL,
    transaction_time DATETIME NOT NULL,
    status VARCHAR(20) NOT NULL,
    event_type VARCHAR(20) NOT NULL,
    PRIMARY KEY (transaction_id)
);
```



## single_response_time 单条响应时间表

| 字段名           | Data Type    | Description                                           |
| ---------------- | ------------ | ----------------------------------------------------- |
| node_name        | VARCHAR(50)  | 节点名称，例如 "ATMEntry", "ATMServer", "CoreService" |
| transaction_time | DATETIME     | 交易发生的时间                                        |
| serial_number    | VARCHAR(100) | 流水号，例如 “ATM001_454213691361900”                 |
| response_time    | TIME         | 响应时间                                              |
| status           | VARCHAR(20)  | 事件状态，可能的值为 “warning”，“severe”，“normal”    |

**字段名**: 

- `node_name`：存储节点名称，最大长度为 50。
- `transaction_time`：存储交易发生的具体时间。
-  serial_number：用于存储交易的流水号，最大长度为 100
- `response_time`：存储响应时间，使用 `TIME` 数据类型。
- `status`：存储事件状态，可能的值为 “warning”，“severe”，“normal”，最大长度为 20。

```sql
CREATE TABLE single_response_time (
    node_name VARCHAR(50) NOT NULL,
    transaction_time DATETIME NOT NULL,
    serial_number VARCHAR(100) NOT NULL,
    response_time TIME NOT NULL,
    status VARCHAR(20) NOT NULL
);

```



## average_monitor_data 平均监控数据

| 字段名                | Data Type      | Description                                        |
| --------------------- | -------------- | -------------------------------------------------- |
| transaction_time      | DATETIME       | 交易发生的时间                                     |
| average_response_time | TIME           | 一分钟的平均响应时间                               |
| status                | VARCHAR(20)    | 事件状态，可能的值为 “warning”，“severe”，“normal” |
| transaction_count     | INT            | 交易次数                                           |
| transaction_amount    | DECIMAL(10, 2) | 交易金额，例如 114514.00                           |
| transaction_type      | VARCHAR(20)    | 交易类型，如 "ATM", "Forex"                        |

```sql
CREATE TABLE average_monitor_data (
    transaction_time DATETIME,
    average_response_time TIME,
    status VARCHAR(20),
    transaction_count INT,
    transaction_amount DECIMAL(10, 2),
    transaction_type VARCHAR(20)
);
```

- **transaction_time (`DATETIME`)**: 交易的时间戳，记录每次交易的具体时间。
- **average_response_time (`TIME`)**: 每个时间段的平均响应时间。
- **status (`VARCHAR(20)`)**: 交易状态，可能的值为 “warning”, “severe”, “normal”，表示警告、严重或正常状态。
- **transaction_count (`INT`)**: 该时间段内的交易次数。
- **transaction_amount (`DECIMAL(10, 2)`)**: 该时间段内的交易总金额，金额精确到小数点后两位。



个人信息

 头像 验证码（opt.）

监控

1分钟平均响应时间  取款总金额和笔数   折线图（过去十分钟 每分钟响应时间） 局部日志（v ! x + 类型 + 时间  8条）



日志（）

流水号 时间  节点  省份  性别  年龄  金额  告警类型（大额 异常时间  内部节点响应时间）



大额 异常时间  内部节点响应时间的出现次数（按天计算）



统计

中国地图（ATM/外汇） 对应时间 交易笔数 交易金额

各年龄段

货币种类

换汇目的



#### 