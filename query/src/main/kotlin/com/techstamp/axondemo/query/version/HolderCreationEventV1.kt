package com.techstamp.axondemo.query.version

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ObjectNode
import com.techstamp.axondemo.common.events.HolderCreationEvent
import org.axonframework.serialization.SimpleSerializedType
import org.axonframework.serialization.upcasting.event.IntermediateEventRepresentation
import org.axonframework.serialization.upcasting.event.SingleEventUpcaster

class HolderCreationEventV1 : SingleEventUpcaster() {

    private val targetType = SimpleSerializedType(HolderCreationEvent::class.java.typeName, null)

    override fun canUpcast(intermediateRepresentation: IntermediateEventRepresentation): Boolean {
        return intermediateRepresentation.type == targetType
    }

    override fun doUpcast(intermediateRepresentation: IntermediateEventRepresentation): IntermediateEventRepresentation {
        return intermediateRepresentation.upcastPayload(
            SimpleSerializedType(targetType.name, "1.0"),
            JsonNode::class.java
        ) {
            (it as ObjectNode).put("company", "N/A")
        }
    }
}
