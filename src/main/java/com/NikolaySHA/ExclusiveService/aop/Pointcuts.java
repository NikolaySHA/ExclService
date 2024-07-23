package com.NikolaySHA.ExclusiveService.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {
    @Pointcut("@annotation(com.NikolaySHA.ExclusiveService.aop.WarnIfExecutionExceeds)")
    void onWarnIfExecutionTimeExceeds(){
    }
}
