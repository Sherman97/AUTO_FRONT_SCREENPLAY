# AUTO_FRONT_SCREENPLAY

Proyecto de automatización Front-End en Java con Serenity BDD, Gradle, Cucumber y Screenplay.

## Cobertura

- Reserva exitosa de una locación para una reunión de trabajo.
- Bloqueo de una reserva de locación cuando el horario no está disponible.

## Enfoque

- Screenplay puro con Actores, Tareas, Preguntas y Targets.
- Autenticación preparada por API para aislar el objetivo de negocio de la reserva.
- Datos independientes por escenario.

## Prerrequisitos

- Stack completo disponible en `http://localhost:8080`.
- Google Chrome disponible en la máquina.
- Java 17 o superior.

## Ejecución

```bash
./gradlew clean test
```
