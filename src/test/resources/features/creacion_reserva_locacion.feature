# language: es
Característica: Creación de reserva de una locación

  Escenario: Registrar una reserva de locación para una reunión de trabajo
    Dado que el usuario colaborador se encuentra autenticado en la plataforma
    Cuando realiza la reserva de una locación para su reunión, indicando la fecha y la franja horaria requeridas
    Entonces el sistema registra exitosamente la reserva

  Escenario: No registrar una reserva de locación con horario inválido
    Dado que el usuario colaborador se encuentra autenticado en la plataforma
    Cuando realiza la reserva de una locación para su reunión, indicando una hora inicial posterior a la hora final
    Entonces el sistema no permite la reserva
    Y el sistema informa que la hora de fin debe ser posterior a la hora de inicio
