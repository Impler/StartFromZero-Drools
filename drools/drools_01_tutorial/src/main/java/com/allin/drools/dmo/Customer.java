package com.allin.drools.dmo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/** 客户信息 */
@ToString
public class Customer {
  /** 名称 */
  @Getter @Setter private String name;
  /** 姓氏 */
  @Getter @Setter private String surname;
  /** 国籍 */
  @Getter @Setter
  private String country;

  public Customer(String name, String surname, String country) {
    this.name = name;
    this.surname = surname;
    this.country = country;
  }
}
