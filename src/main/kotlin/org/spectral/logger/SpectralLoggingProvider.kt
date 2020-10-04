/*
 * Spectral Powered
 * Copyright (C) 2020 Kyle Escobar
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, @see https://www.gnu.org/licenses/.
 */

package org.spectral.logger

import org.tinylog.Level
import org.tinylog.configuration.Configuration
import org.tinylog.configuration.Configuration.replace
import org.tinylog.core.TinylogLoggingProvider
import org.tinylog.format.MessageFormatter
import org.tinylog.provider.ContextProvider
import org.tinylog.provider.LoggingProvider
import java.io.File
import java.nio.file.Path


class SpectralLoggingProvider : LoggingProvider {

    private var provider: TinylogLoggingProvider = TinylogLoggingProvider()

    /**
     * Enable logs to be saved to a rolling file at the [logDirectory] path.
     * The file backups will be prefixed with [prefix].
     *
     * @param logDirectory Path
     * @param prefix String
     */
    @Suppress("UNCHECKED_CAST")
    fun enableFileLogging(logDirectory: Path, prefix: String) {
        provider.shutdown()

        val method = Configuration::class.java.getDeclaredMethod("load")
        method.isAccessible = true

        val config = method.invoke(null) as MutableMap<String, String>

        /*
         * Build the log to file path.
         */
        config["writer2"] = "rolling file"
        config["writer2.level"] = "debug"
        config["writer2.format"] = "{date:yyyy-MM-dd HH:mm:ss.SSS|min-size=10} | {level|min-size=4} | {thread|min-size=4} | {message}"
        config["writer2.file"] = logDirectory.toString() + File.separator + "${prefix}_{date}_{count}.log"
        config["writer2.latest"] = logDirectory.toString() + File.separator + "latest.log"
        config["writer2.charset"] = "UTF-8"
        config["writer2.buffered"] = "true"
        config["writer2.policies"] = "size: 25mb"
        config["writer2.backups"] = "50"
        config["writer2.provider"] = "spectral"

        val frozen = Configuration::class.java.getDeclaredField("frozen")
        frozen.isAccessible = true
        frozen.set(null, false)

        replace(config)

        provider = TinylogLoggingProvider()
    }

    override fun getContextProvider(): ContextProvider {
        return provider.contextProvider
    }

    override fun getMinimumLevel(): Level {
        return provider.minimumLevel
    }

    override fun getMinimumLevel(tag: String?): Level {
        return provider.getMinimumLevel(tag)
    }

    override fun isEnabled(depth: Int, tag: String?, level: Level): Boolean {
        return provider.isEnabled(depth + 1, tag, level)
    }

    override fun log(depth: Int, tag: String?, level: Level?, exception: Throwable?, formatter: MessageFormatter?, obj: Any?, vararg arguments: Any?) {
        return provider.log(depth + 1, tag, level, exception, formatter, obj, arguments)
    }

    override fun log(loggerClassName: String?, tag: String?, level: Level?, exception: Throwable?, formatter: MessageFormatter?, obj: Any?, vararg arguments: Any?) {
        TODO("Not yet implemented")
    }

    override fun shutdown() {
        TODO("Not yet implemented")
    }
}
