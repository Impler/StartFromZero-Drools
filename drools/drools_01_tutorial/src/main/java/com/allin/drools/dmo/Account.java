package com.allin.drools.dmo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 账户信息
 */
@ToString
public class Account {

  /** 账号 */
  @Getter @Setter private String accountNo;

  /** 余额 */
  @Getter @Setter private double balance;
}
