package com.allin.drools.dmo;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 统计周期
 */
@ToString
public class AccountingPeriod {

  /** 开始日期 */
  @Getter @Setter
  private Date startDate;

  /** 结束日期 */
  @Getter @Setter
  private Date endDate;
}
