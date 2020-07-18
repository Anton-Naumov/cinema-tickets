SELECT se.id,
       se.number,
       NOT EXISTS (
            SELECT 1
            FROM tickets t
            WHERE t.screening_id = s.id
               AND t.seat_id = se.id
       ) as is_free
from screenings s
join rooms r on s.room_id = r.id
join seats se on r.id = se.room_id
where s.id = :screeningId
