package com.allin.drools;

import com.allin.drools.dmo.Account;
import com.allin.drools.dmo.AccountingPeriod;
import com.allin.drools.dmo.CashFlow;
import com.allin.drools.dmo.Customer;
import com.allin.drools.dmo.PrivateAccount;
import com.allin.drools.util.DateHelper;
import com.allin.drools.util.KnowledgeSessionHelper;
import com.allin.drools.util.LogHelper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.api.runtime.rule.AgendaFilter;
import org.kie.api.runtime.rule.FactHandle;
import org.kie.api.runtime.rule.Match;

public class TestDrools {

  StatelessKieSession statelessSession = null;
  KieSession statefulSession = null;
  static KieContainer kieContainer;

  @BeforeAll
  public static void beforeClass() {
    kieContainer = KnowledgeSessionHelper.createRuleBase();
  }

  @Test
  public void testLesson1() {

    statefulSession =
        KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, "ksession-lesson1");

    // 添加全局变量
    statefulSession.setGlobal("log", new LogHelper());
    Account account = new Account();
    FactHandle handler = statefulSession.insert(account);
    account.setAccountNo("acc01");
    statefulSession.update(handler, account);

    statefulSession.fireAllRules();
  }

  @Test
  public void testLesson2() throws Exception {
    statefulSession =
        KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, "ksession-lesson2");

    statefulSession.setGlobal("log", new LogHelper());


    // 账户001
    Account acc = new Account();
    acc.setAccountNo("acc001");

    // 流水1
    CashFlow cash1 = new CashFlow();
    cash1.setType(CashFlow.INCOMING);
    cash1.setAccountNo("acc001");
    cash1.setAmount(1000);
    cash1.setMvtDate(DateHelper.getDate("2019-12-08"));

    // 流水2
    CashFlow cash2 = new CashFlow();
    cash2.setType(CashFlow.INCOMING);
    cash2.setAccountNo("acc001");
    cash2.setAmount(700);
    cash2.setMvtDate(DateHelper.getDate("2020-01-08"));

    // 流水3
    CashFlow cash3 = new CashFlow();
    cash3.setType(CashFlow.INCOMING);
    cash3.setAccountNo("acc002");
    cash3.setAmount(2000);
    cash3.setMvtDate(DateHelper.getDate("2020-02-08"));

    // 流水4
    CashFlow cash4 = new CashFlow();
    cash4.setType(CashFlow.OUTGOING);
    cash4.setAccountNo("acc001");
    cash4.setAmount(200);
    cash4.setMvtDate(DateHelper.getDate("2020-02-21"));


    // 统计周期
    AccountingPeriod period = new AccountingPeriod();
    period.setStartDate(DateHelper.getDate("2020-01-01"));
    period.setEndDate(DateHelper.getDate("2020-03-01"));

    statefulSession.insert(acc);
    statefulSession.insert(cash1);
    statefulSession.insert(cash2);
    statefulSession.insert(cash3);
    statefulSession.insert(cash4);
    statefulSession.insert(period);

    statefulSession.fireAllRules();
  }

  @Test
  public void testLesson3_in(){
    statefulSession =
        KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, "ksession-lesson3");

    statefulSession.setGlobal("log", new LogHelper());

    CashFlow cf1 = new CashFlow();
    cf1.setType(CashFlow.OUTGOING);

    CashFlow cf2 = new CashFlow();
    cf2.setType(CashFlow.INCOMING);

    statefulSession.insert(cf1);
    statefulSession.insert(cf2);

    statefulSession.fireAllRules(new RuleNameAgendaFilter("in"));
  }

  @Test
  public void testLesson3_nestedAccessor(){
    statefulSession =
        KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, "ksession-lesson3");

    statefulSession.setGlobal("log", new LogHelper());

    PrivateAccount acc1 = new PrivateAccount();
    Customer jack = new Customer("jack", "zhang", "CN");
    acc1.setOwner(jack);

    PrivateAccount acc2 = new PrivateAccount();
    Customer lisa = new Customer("lisa", "wang", "CN");
    acc2.setOwner(lisa);

    statefulSession.insert(acc1);
    statefulSession.insert(acc2);

    statefulSession.fireAllRules(new RuleNameAgendaFilter("nested accessor"));
  }

  /**
   * 基于规则名称匹配
   */
  class RuleNameAgendaFilter implements AgendaFilter{

    private String ruleName;

    public RuleNameAgendaFilter(String ruleName) {
      this.ruleName = ruleName;
    }

    @Override
    public boolean accept(Match match) {
      return match.getRule().getName().equals(this.ruleName);
    }
  }
}
