package com.allin.drools.dmo;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 流水信息
 */
@ToString
public class CashFlow {

  /**
   * 收入
   */
  public static int INCOMING = 1;

  /**
   * 支出
   */
  public static int OUTGOING = 2;

  @Getter @Setter private Date mvtDate;
  @Getter @Setter private double amount;
  @Getter @Setter private int type;
  @Getter @Setter private String accountNo;
}
