import org.gradle.api.JavaVersion
import org.gradle.kotlin.dsl.DependencyHandlerScope

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

object Project {
    const val version = "1.0.0"
    const val gradleVersion = "6.6.1"
    const val kotlinVersion = "1.4.10"
    val jvmVersion = JavaVersion.VERSION_11.toString()
}

object Plugin {
    const val bintray = "1.8.5"
}

object Library {
    private object Version {
        const val tinylog = "2.1.2"
    }

    val tinylogApi = "org.tinylog:tinylog-api-kotlin:${Version.tinylog}"
    val tinylogImpl = "org.tinylog:tinylog-impl:${Version.tinylog}"
}

fun DependencyHandlerScope.tinylog() {
    "implementation"(Library.tinylogApi)
    "implementation"(Library.tinylogImpl)
}