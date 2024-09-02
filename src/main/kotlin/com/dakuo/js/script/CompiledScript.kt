package com.dakuo.js.script

import java.io.Reader
import javax.script.Invocable
import javax.script.ScriptEngine
import javax.script.SimpleBindings

open class CompiledScript {

    private var compiledScript: javax.script.CompiledScript

    val scriptEngine: ScriptEngine

    constructor(reader: Reader){
        scriptEngine = ScriptManager.engine.getEngine()
        loadLib()
        compiledScript = ScriptManager.engine.compile(reader)
        magicFunction()
    }

    constructor(script: String) {
        scriptEngine = ScriptManager.engine.getEngine()
        loadLib()
        compiledScript = ScriptManager.engine.compile(scriptEngine, script)
        magicFunction()
    }


    fun eval(map: Map<String,Any>?){
        if (map != null) {
            compiledScript.eval(SimpleBindings(map))
        }else {
            compiledScript.eval()
        }
    }

    open fun loadLib(){
        scriptEngine.eval("""
            
            
        """.trimIndent())
    }

    /**
     * 执行脚本中的指定函数
     *
     * @param function 函数名
     * @param map 传入的默认对象
     * @param args 传入对应方法的参数
     * @return 解析值
     */
    fun invoke(function: String, map: Map<String, Any>?, vararg args: Any): Any? {
        return ScriptManager.engine.invoke(this, function, map, *args)
    }

    /**
     * 执行脚本中的指定函数
     *
     * @param function 函数名
     * @param args 传入对应方法的参数
     * @return 解析值
     */
    fun simpleInvoke(function: String, vararg args: Any?): Any? {
        return (scriptEngine as Invocable).invokeFunction(function, *args)
    }



    private fun magicFunction() {
        eval(null)
        scriptEngine.eval(
            """
            function IScriptNumberOne() {}
            IScriptNumberOne.prototype = this
            function newObject() { return new IScriptNumberOne() }
        """
        )
    }

}