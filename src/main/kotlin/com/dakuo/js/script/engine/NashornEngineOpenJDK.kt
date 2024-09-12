package com.dakuo.js.script.engine

import com.dakuo.js.script.CompiledScript
import javax.script.Invocable
import javax.script.ScriptEngine


class NashornEngineOpenJDK: AbstractScriptEngine() {

    override fun getEngine(args: Array<String>, classLoader: ClassLoader): ScriptEngine {

        return org.openjdk.nashorn.api.scripting.NashornScriptEngineFactory().getScriptEngine(args,classLoader)
    }

    override fun invoke(
        compiledScript: CompiledScript,
        function: String,
        map: Map<String, Any?>?,
        vararg args: Any
    ): Any? {
        val newObject:  org.openjdk.nashorn.api.scripting.ScriptObjectMirror =
            (compiledScript.scriptEngine as Invocable).invokeFunction("newObject") as  org.openjdk.nashorn.api.scripting.ScriptObjectMirror

        map?.let { (newObject.proto as org.openjdk.nashorn.api.scripting.ScriptObjectMirror).putAll(it) }
        return newObject.callMember(function, *args)
    }

    override fun isFunction(engine: ScriptEngine, func: Any?): Boolean {
        return func is org.openjdk.nashorn.api.scripting.ScriptObjectMirror && func.isFunction
    }

}