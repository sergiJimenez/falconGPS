### Para más información leer la [Wiki](https://github.com/sergiJimenez/falconGPS/wiki).
---
# ¿Cómo ejecutarlo?

## Antes de empezar…

1. Lo primero que tenemos que hacer es asegurarnos de que todos los programas especificados anteriormente han sido correctamente instalados.

## Primero, la base de datos…

1. Una vez el punto anterior haya sido completado abriremos primero MySQL Workbench ya que procederemos a crear la base de datos. Para ello abriremos la conexión que tengamos ya creada.
2. Dentro accederemos al siguiente recurso para obtener la base de datos, copiarla, pegarla y ejecutar el script que hemos pegado.
3. Cuando este haya sido ejecutado ya tendremos nuestra base de datos de “falcongps” lista para ser utilizada.

## Segundo, Arduino…

1. En el momento que ya tengamos la base de datos introducida en nuestra conexión, y lista para utilizar, abriremos Arduino IDE y se nos creará un nuevo sketch si es la primera vez que hacemos esto, sino tendremos que crearlo nosotros.
2. Haremos todas las conexiones especificadas anteriormente.
3. Dentro del nuevo sketch copiaremos y pegaremos el siguiente recurso donde encontraremos el programa que subiremos a Arduino.
4. Seleccionaremos el Arduino que hayamos conectado a nuestro ordenador.
5. Cuando este haya sido pegado le daremos al botón de “Verify” para asegurarnos de que no hay ningún problema.
6. Y para finalizar lo subiremos dandole al botón de “Upload”, justo a la derecha del “Verify”. Cuando aparezca el mensaje de que se ha subido exitosamente podremos pasar al siguiente paso.

## Tercer, y más importante…

1. Abriremos, y crearemos, un nuevo proyecto en Apache NetBeans (o nuestro IDE preferente).
2. Para poder utilizar este proyecto necesitaremos añadir un par de librerias en formato `.jar`. Para eso tendremos que buscar las librerías desde la parte derecha del Apache Netbeans.
3. Localizado dicho directorio haremos click derecho y seleccionaremos la opción de “Add JAR/Folder”. E importaremos las tres librerias que encontraremos dentro del repositorio. La ruta para poder encontrarlas es: falconGPS/java/libraries.
4. En el momento que hayan sido importadas procederemos a copiar y pegar el código facilitado en el siguiente recurso.
Hay que tener cuidado al pegar el código ya que algunas clases no se exactamente igual a como has llamado a tú proyecto.
5. Si todo va bien, y hemos hecho todos los pasos anteriores de forma exitosa, el programa deberia de funcionar a la perfección.
