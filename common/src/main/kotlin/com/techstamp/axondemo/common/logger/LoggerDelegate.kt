package com.techstamp.axondemo.common.logger

import java.util.logging.Logger
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class LoggerDelegate : ReadOnlyProperty<Any?, Logger> {
    companion object {
        private fun <T> createLogger(clazz: Class<T>): Logger {
            return Logger.getLogger(clazz.name)
        }
    }

    private lateinit var logger: Logger

    override operator fun getValue(thisRef: Any?, property: KProperty<*>): Logger {
        if (!::logger.isInitialized) {
            logger = createLogger(thisRef!!.javaClass)
        }
        return logger
    }
}
