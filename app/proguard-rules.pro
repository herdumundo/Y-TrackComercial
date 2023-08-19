# Mantén el nombre original de la clase MainActivity
-keep class com.ytrack.y_trackcomercial.MainActivity

# Aplica ofuscación a todas las clases en el paquete com.ytrack.y_trackcomercial excepto MainActivity
-keep class com.ytrack.y_trackcomercial.** { *; }
-keep class com.ytrack.y_trackcomercial.components.** { *; }
-keep class com.ytrack.y_trackcomercial.core.** { *; }
-keep class com.ytrack.y_trackcomercial.data.** { *; }
-keep class com.ytrack.y_trackcomercial.repository.** { *; }
-keep class com.ytrack.y_trackcomercial.services.** { *; }
-keep class com.ytrack.y_trackcomercial.ui.** { *; }
-keep class com.ytrack.y_trackcomercial.usecases.** { *; }
-keep class com.ytrack.y_trackcomercial.util.** { *; }
