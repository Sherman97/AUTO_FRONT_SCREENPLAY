Feature: Creacion de reserva de una locacion

  Scenario: Registrar una reserva de locacion para una reunion de trabajo
    Given que el usuario colaborador se encuentra autenticado en la plataforma
    When realiza la reserva de una locacion para su reunion, indicando la fecha y la franja horaria requeridas
    Then el sistema registra exitosamente la reserva

  Scenario: No registrar una reserva de locacion con horario invalido
    Given que el usuario colaborador se encuentra autenticado en la plataforma
    When realiza la reserva de una locacion para su reunion, indicando una hora inicial posterior a la hora final
    Then el sistema no permite la reserva
    And el sistema informa que la hora de fin debe ser posterior a la hora de inicio
