package androidx.media3.common.util

/**
 * Annotates a public API as being unstable (i.e., not subject to the same backward compatibility
 * guarantees as stable APIs).
 */
@Target(
    AnnotationTarget.TYPE,
    AnnotationTarget.CLASS,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.CONSTRUCTOR,
    AnnotationTarget.FILE
)
@Retention(AnnotationRetention.BINARY)
annotation class UnstableApi
