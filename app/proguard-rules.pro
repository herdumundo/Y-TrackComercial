# Mantener el nombre original de la clase MainActivity
-keep class com.portalgm.y_trackcomercial.MainActivity
# Aplicar ofuscación a todas las clases en el paquete com.ytrack.y_trackcomercial excepto MainActivity
-keep class com.portalgm.y_trackcomercial.** { *; }
-keep class com.portalgm.y_trackcomercial.core.** { *; }
-keep class com.portalgm.y_trackcomercial.data.** { *; }
-keep class com.portalgm.y_trackcomercial.repository.** { *; }
-keep class com.portalgm.y_trackcomercial.services.** { *; }
-keep class com.portalgm.y_trackcomercial.components.** { *; }
-keep class com.portalgm.y_trackcomercial.ui.** { *; }
-keep class com.portalgm.y_trackcomercial.usecases.** { *; }
-keep class com.portalgm.y_trackcomercial.util.** { *; }
# Mantener clases específicas de Jackson (EVITA PROBLEMAS AL VERIFICAR EL TOKEN)
-keep class com.fasterxml.jackson.core.** { *; }
-keep class com.fasterxml.jackson.databind.** { *; }
# Mantener todas las clases en el paquete com.auth0.jwt  (EVITA PROBLEMAS AL VERIFICAR EL TOKEN)
-keep class com.auth0.jwt.** { *; }
-keep class com.auth0.jwt.exceptions.** { *; }
-keep class com.auth0.jwt.interfaces.** { *; }

# Retener generic signatures de TypeToken y sus subclases con R8 versión 3.0 y superior.
-keep,allowobfuscation,allowshrinking class com.google.gson.reflect.TypeToken
-keep,allowobfuscation,allowshrinking class * extends com.google.gson.reflect.TypeToken

# Retrofit realiza reflexión en parámetros genéricos. InnerClasses se requiere para usar Signature y
# EnclosingMethod se requiere para usar InnerClasses.
-keepattributes Signature, InnerClasses, EnclosingMethod

# Retrofit realiza reflexión en anotaciones de método y parámetro.
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations

# Mantener los valores predeterminados de las anotaciones (por ejemplo, retrofit2.http.Field.encoded).
-keepattributes AnnotationDefault

# Retener los parámetros de los métodos del servicio al optimizar.
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}

# Ignorar las anotaciones utilizadas para herramientas de construcción.
# Ignorar las anotaciones JSR 305 utilizadas para incrustar información de nulabilidad.
-dontwarn javax.annotation.**

# Protegido por un try/catch de NoClassDefFoundError y solo se usa cuando está en el classpath.
-dontwarn kotlin.Unit

# Funciones de nivel superior que solo pueden ser utilizadas por Kotlin.
-dontwarn retrofit2.KotlinExtensions
-dontwarn retrofit2.KotlinExtensions$*

# Con el modo completo de R8, no ve subtipos de interfaces de Retrofit ya que se crean con un Proxy
# y reemplaza todos los valores potenciales con nulo. Mantener explícitamente las interfaces evita esto.
-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface <1>
# Mantener servicios heredados.
-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface * extends <1>
# Con el modo completo de R8, se eliminan las firmas genéricas para clases que no se mantienen.
# Las funciones suspendidas se envuelven en continuaciones donde se utiliza el argumento de tipo.
-keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation
# El modo completo de R8 elimina las firmas genéricas de los tipos de retorno si no se mantienen.
-if interface * { @retrofit2.http.* public *** *(...); }
-keep,allowoptimization,allowshrinking,allowobfuscation class <3>
# Con el modo completo de R8, se eliminan las firmas genéricas para clases que no se mantienen.
-keep,allowobfuscation,allowshrinking class retrofit2.Response
# Asegurarse de que las clases utilizadas por JWTDecoder no sean obfuscadas
-dontobfuscate
# Conservar las clases de JavaBeans necesarias
-dontwarn java.beans.**
# Conservar las clases que pueden ser referenciadas por TypeReference
-keepclassmembers class * {
    @com.fasterxml.jackson.annotation.JsonCreator *;
}
# Conservar las clases utilizadas para la reflexión de tipos
-keepattributes Signature