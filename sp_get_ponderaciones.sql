-- =============================================
-- Stored Procedure: sp_get_ponderaciones
-- Description: Returns all ponderaciones (rating scale definitions)
-- =============================================

IF OBJECT_ID('sp_get_ponderaciones', 'P') IS NOT NULL
    DROP PROCEDURE sp_get_ponderaciones;
GO

CREATE PROCEDURE sp_get_ponderaciones
AS
BEGIN
    SET NOCOUNT ON;

    SELECT
        id,
        puntuacion,
        descripcion
    FROM Ponderacion
    ORDER BY puntuacion ASC;
END
GO
