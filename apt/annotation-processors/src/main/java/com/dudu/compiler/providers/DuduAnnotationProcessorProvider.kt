package com.dudu.compiler.providers

import com.dudu.compiler.processors.DuduAnnotationProcessor
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider

class DuduAnnotationProcessorProvider: SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
//        println(">>>>>>>>DuduAnnotationProcessorProvider.create")
//        environment.logger.logging("DuduAnnotationProcessor.logging")
//        environment.logger.info("DuduAnnotationProcessor.info")
//        environment.logger.warn("DuduAnnotationProcessor.warn")
//        environment.logger.error("DuduAnnotationProcessor.error")
        return DuduAnnotationProcessor(environment)
    }


}