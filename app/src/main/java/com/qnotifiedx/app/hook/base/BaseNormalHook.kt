package com.qnotifiedx.app.hook.base

import com.github.kyuubiran.ezxhelper.utils.Log
import com.qnotifiedx.core.processctrl.ProcessInfo.isCurrentProc
import com.qnotifiedx.gen.DelayHooks

/**
 * Delay Hooks
 * 在模块获取到宿主全局Context后执行的Hook将继承于此类
 */
abstract class BaseNormalHook : BaseHook() {

    companion object {
        private val delayHooks = DelayHooks.getAnnotatedItemClassList()

        fun initHooks() {
            for (h in delayHooks) {
                if (!h.inited) {
                    for (proc in h.targetProc) {
                        if (proc.isCurrentProc) {
                            try {
                                h.init()
                                h.inited = true
                                Log.i("Initialized delay hook: ${h.javaClass.name}")
                            } catch (e: Exception) {
                                Log.i("Initialization failure delay hook: ${h.javaClass.name}")
                            }
                        }
                    }
                }
            }
        }
    }
}