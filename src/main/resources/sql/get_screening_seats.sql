SELECT se.id,
       se.number,
       true as free
from screenings s
join rooms r on s.room_id = r.id
join seats se on r.id = se.room_id
where s.id = :screeningId
