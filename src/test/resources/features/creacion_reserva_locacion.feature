# language: es
Característica: Creacion de reserva de una locacion

  Escenario: Registrar una reserva de locacion para una reunion de trabajo
    Dado que el usuario colaborador se encuentra autenticado en la plataforma
    Cuando realiza la reserva de una locacion para su reunion, indicando la fecha y la franja horaria requeridas
    Entonces el sistema registra exitosamente la reserva

  Escenario: No registrar una reserva de locacion con horario invalido
    Dado que el usuario colaborador se encuentra autenticado en la plataforma
    Cuando realiza la reserva de una locacion para su reunion, indicando una hora inicial posterior a la hora final
    Entonces el sistema no permite la reserva
    Y el sistema informa que la hora de fin debe ser posterior a la hora de inicio
