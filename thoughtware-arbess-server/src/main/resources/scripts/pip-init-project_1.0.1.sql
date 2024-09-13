-- update pcs_prc_function set code = 'version' , id = '64bdf62686a4' where id = 'f79c084575fa';

UPDATE pcs_prc_function
SET code = 'version', id = '64bdf62686a4'
WHERE id = 'f79c084575fa'
  AND NOT EXISTS (
    SELECT 1
    FROM pcs_prc_function
    WHERE id = '64bdf62686a4'
);

DELETE FROM pcs_prc_function
WHERE id = 'f79c084575fa'
  AND EXISTS (
    SELECT 1
    FROM pcs_prc_function
    WHERE id = '64bdf62686a4'
);
