package com.allin.drools.util;

import org.drools.core.event.DebugAgendaEventListener;
import org.drools.core.event.DebugProcessEventListener;
import org.drools.core.event.DebugRuleRuntimeEventListener;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KnowledgeSessionHelper {

  public static final Logger LOG = LoggerFactory.getLogger(KnowledgeSessionHelper.class);

  public static KieContainer createRuleBase() {
    KieServices ks = KieServices.Factory.get();
    KieContainer kieContainer = ks.getKieClasspathContainer();
    return kieContainer;
  }

  public static StatelessKieSession getStatelessKnowledgeSession(
      KieContainer kieContainer, String sessionName) {
    StatelessKieSession kSession = kieContainer.newStatelessKieSession(sessionName);
    return kSession;
  }

  public static KieSession getStatefulKnowledgeSession(
      KieContainer kieContainer, String sessionName) {
    KieSession kieSession = kieContainer.newKieSession(sessionName);

    // 添加规则运行事件监听
    kieSession.addEventListener(new DebugRuleRuntimeEventListener());

    // 添加Agenda事件监听
    kieSession.addEventListener(new DebugAgendaEventListener());

    // 添加处理事件监听
    kieSession.addEventListener(new DebugProcessEventListener());
    return kieSession;
  }
}
