package com.edgedevstudio.breachvulnerableapp

import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage


/**
 * Created by Olorunleke Opeyemi on 14/01/2019.
 **/
class XposedClass : IXposedHookLoadPackage {
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        XposedBridge.log("Launched App: "+lpparam.packageName)

        val appPackageName = "com.edgedevstudio.vulnerableapp"
        val classToHook = appPackageName + ".MainActivity"
        val functionToHook = "setOutput"

        if (lpparam.packageName.equals(appPackageName, true)) {
            XposedBridge.log("Loaded App: " + lpparam.packageName)

            XposedHelpers.findAndHookMethod(
                classToHook,
                lpparam.classLoader,
                functionToHook,
                Int::class.java,
                object : XC_MethodHook() {
                    override fun beforeHookedMethod(param: MethodHookParam) {
                        super.beforeHookedMethod(param)
                        XposedBridge.log("value of \"d\" before hooking = "+param.args[0])
                        param.args[0] = 1
                        XposedBridge.log("value of \"d\" after hooking = "+param.args[0])
                    }
                })
        }
    }

}