package com.github.thorlauridsen.serializer

import java.util.UUID
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * Custom kotlinx.serialization serializer for [UUID] objects.
 * This is necessary because kotlinx.serialization does not support serializing [UUID] objects out of the box.
 */
object UUIDSerializer : KSerializer<UUID> {

    override val descriptor = PrimitiveSerialDescriptor("UUID", PrimitiveKind.STRING)

    /**
     * Serialize a [UUID] object to a string.
     * @param encoder [Encoder] to use.
     * @param value [UUID] object to serialize.
     */
    override fun serialize(encoder: Encoder, value: UUID) {
        encoder.encodeString(value.toString())
    }

    /**
     * Deserialize a string to a [UUID] object.
     * @param decoder [Decoder] to use.
     * @return [UUID] object.
     */
    override fun deserialize(decoder: Decoder): UUID {
        return UUID.fromString(decoder.decodeString())
    }
}
