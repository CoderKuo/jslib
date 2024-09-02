package com.dakuo.js.script

import com.dakuo.js.script.engine.NashornEngineJDK
import com.dakuo.js.script.engine.NashornEngineOpenJDK
import java.io.File
import java.util.concurrent.ConcurrentHashMap

object ScriptManager {

    val scripts = ConcurrentHashMap<String, File>()

    val engine = when{
        check("jdk.nashorn.api.scripting.NashornScriptEngineFactory") && ((System.getProperty("java.class.version")
            .toDoubleOrNull() ?: 0.0) < 55.0) -> NashornEngineJDK()
        else-> NashornEngineOpenJDK()
    }


    fun check(className:String):Boolean{
        return try {
            Class.forName(className)
            true
        }catch (e:Exception){
            false
        }
    }




}