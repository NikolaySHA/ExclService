package com.NikolaySHA.ExclusiveService.web.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {
    @Pointcut("@annotation(com.NikolaySHA.ExclusiveService.web.aop.WarnIfExecutionExceeds)")
    void onWarnIfExecutionTimeExceeds(){
    }
}
