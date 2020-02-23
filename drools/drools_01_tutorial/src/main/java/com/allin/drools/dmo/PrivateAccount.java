package com.allin.drools.dmo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/** 私人账户 */
@ToString(callSuper = true)
public class PrivateAccount extends Account {

  /** 客户 */
  @Getter @Setter private Customer owner;
}
