# adalid
Adalid es un sistema de procesamiento de plantillas de código abierto (open-source) diseñado específicamente para apoyar el desarrollo de aplicaciones de software. Utilizando un motor de plantillas, Adalid combina un modelo de datos abstracto para producir gran variedad de documentos, típicamente código fuente. También puede generar documentación de la aplicación, datos de prueba, archivos de ayuda en línea, control de acceso y otros archivos de configuración, scripts de instalación y actualización, etc. Adalid está diseñado para permitirle construir sus propios generadores, dándole total control sobre el proceso de generación y los documentos producidos.

Los generadores de código han probado ser efectivos en la reducción de costos del desarrollo de software y en la mejora de su calidad. Estas herramientas son particularmente útiles para hacer cumplir patrones y estándares, reducir el tiempo y el esfuerzo requerido para la codificación y las pruebas, garantizar consistencia y minimizar errores. En particular, un sistema de procesamiento de plantillas, como Adalid, también permite escalar el mejor talento de la organización, ya que las habilidades de las personas encargadas de preparar y mantener los modelos y las plantillas se reflejan en cada uno de los documentos generados. Además, dado que Adalid ofrece la capacidad de generar código para múltiples plataformas, cuando se trata de una compañía de software, Adalid permite ampliar el mercado de los productos de la empresa.

Adalid es una adaptación libre y simplificada de la arquitectura dirigida por modelos conocida como MDA, por las siglas en inglés de Model-Driven Architecture. El modelo de datos de Adalid es un modelo de los componentes de la aplicación y su funcionalidad, es independiente de la plataforma y conocido como PIM, por las siglas en inglés de Platform Independent Model. La mayoría de los procesadores de plantillas utilizan una base de datos o archivos de texto, en formatos tales como XML, para almacenar el modelo de datos. En lugar de esto, Adalid provee una librería de clases Java de artefactos que se utilizan para definir los elementos del modelo; y los modelos definidos a su vez se almacenan como librerías de clases Java. Los artefactos de Adalid se extienden para traducir el PIM en uno o más modelos específicos de la plataforma, conocido como PSM por las siglas en inglés de Platform Specific Model. Finalmente, los PSM se combinan con plantillas para generar código fuente para cualquier plataforma de software.

Adalid utiliza Apache Velocity Engine como motor de plantillas, de manera que las plantillas pueden ser elaboradas utilizando el poderoso lenguaje de plantillas de Velocity. La facilidad de extender el modelo de datos y de añadir nuevas plantillas permite virtualmente la generación de cualquier clase de documentos.

La extensibilidad y la reusabilidad son principios fundamentales en Adalid. Como cualquier otra aplicación Java open-source, agregar nuevas funciones y modificar las existentes se puede lograr simplemente extendiendo los artefactos de Adalid. Y, como se mencionó antes, los modelos de datos son definidos y almacenados como librerías de clases Java, de manera que también son fácilmente reutilizados, extendidos, controlados (con cualquier sistema de control de versiones) e incluso organizados en una jerarquía de modelos.


[Haga click aquí para ver las páginas Wiki](../../wiki)
