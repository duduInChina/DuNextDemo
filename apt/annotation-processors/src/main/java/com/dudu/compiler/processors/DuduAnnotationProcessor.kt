package com.dudu.compiler.processors

import com.dudu.annotation.Title
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration

class DuduAnnotationProcessor(private val environment: SymbolProcessorEnvironment): SymbolProcessor {
    override fun process(resolver: Resolver): List<KSAnnotated> {

        environment.logger.warn(">>>>DuduAnnotationProcessor.process")

        val ksClassList = resolver.getSymbolsWithAnnotation(Title::class.qualifiedName!!)
            .filterIsInstance<KSClassDeclaration>().toList()

        environment.logger.warn("ksClassList2:" + ksClassList.size)

        ksClassList.forEach {
            environment.logger.warn(it.toString())
        }

        return emptyList()
    }
}