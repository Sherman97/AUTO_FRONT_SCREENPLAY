# AUTO_FRONT_SCREENPLAY

Proyecto de automatizacion Front-End en Java con Serenity BDD, Gradle, Cucumber y Screenplay.

## Codigo fuente funcional

Este repositorio incluye el codigo fuente funcional de las pruebas automatizadas E2E para reserva de locaciones.

- Implementacion principal en `src/test/java/com/sofka/reservas/`
- Features Gherkin en `src/test/resources/features/`
- Runner principal: `LocationReservationRunner`
- Configuracion de Serenity: `serenity.conf`

## Cobertura actual

- Reserva exitosa de una locacion para una reunion de trabajo.
- Validacion negativa: no registrar reserva cuando la hora inicial es posterior a la hora final.

## Prerrequisitos

- Java 17 o superior.
- Google Chrome instalado.
- Aplicacion objetivo disponible en `http://localhost:8080`.

## Instrucciones de ejecucion de tests

1. Abrir una terminal en la raiz del proyecto.
2. Ejecutar todas las pruebas:

```bash
./gradlew clean test
```

En Windows PowerShell:

```powershell
.\gradlew.bat clean test
```

3. (Opcional) Ejecutar solo el runner de reservas:

```bash
./gradlew test --tests com.sofka.reservas.runners.LocationReservationRunner
```

En Windows PowerShell:

```powershell
.\gradlew.bat test --tests com.sofka.reservas.runners.LocationReservationRunner
```

## Reportes

- Reporte Serenity generado en `target/site/serenity/index.html`.
