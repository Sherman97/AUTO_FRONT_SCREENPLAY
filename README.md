# AUTO_FRONT_SCREENPLAY

Automatizacion E2E Front-End con Java, Serenity BDD, Cucumber y Screenplay.

## HU cubierta

- `HU-02`: Crear reserva manual desde modulo administrador.

Feature principal:
- `src/test/resources/features/hu02_crear_reserva_admin.feature`

Runner:
- `com.sofka.reservas.runners.LocationReservationRunner`

## Escenarios automatizados (HU-02)

- Creacion manual de reserva exitosa.
- Intento de creacion con conflicto de horario (solapamiento).
- Intento de creacion con datos incompletos (boton deshabilitado).

## Configuracion base

- URL por defecto: `http://localhost:5173`
- Login: `/login`
- Vista admin: `/admin-reservations`
- Credenciales admin por defecto:
  - `prueba.admin@demo.com`
  - `admin123`

Parametros sobrescribibles:

```powershell
.\gradlew.bat clean test -Dwebdriver.base.url=http://localhost:5173 -Dadmin.email=prueba.admin@demo.com -Dadmin.password=admin123
```

## Prerrequisitos

- Java 17+
- Chrome instalado
- Frontend y backend levantados

## Ejecucion

Suite completa:

```powershell
.\gradlew.bat clean test aggregate --rerun-tasks
```

Solo HU-02 (runner):

```powershell
.\gradlew.bat test --tests com.sofka.reservas.runners.LocationReservationRunner --rerun-tasks
```

## Reportes

- Gradle: `build/reports/tests/test/index.html`
- Serenity: `target/site/serenity/index.html`
