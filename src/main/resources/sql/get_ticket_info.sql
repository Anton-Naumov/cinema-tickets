SELECT
    t.unique_identifier unique_id,
    m.title movie_title,
    s.time screening_time,
    r.number screening_room,
    se.number seat_number
from tickets t
join screenings s on t.screening_id = s.id
join movies m on s.movie_id = m.id
join rooms r on s.room_id = r.id
join seats se on t.seat_id = se.id
